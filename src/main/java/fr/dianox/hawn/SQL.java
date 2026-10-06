package fr.dianox.hawn;

import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * MySQL access.
 *
 * The rows of the online players (every table with a player_UUID column) are loaded before they join, outside the
 * main thread, then read from memory: the join does not wait for the database. Their changes are applied in memory
 * at once and written on the MySQL thread, in order.
 */
public class SQL {

    public static Connection connection;
    private static String url, username, password;
    public boolean useyamllistplayer = false;

    // Every MySQL task that does not need to block the main thread, one after the other
    private static ExecutorService async;

    private static final Object NULL = new Object();
    // Names (lower case) of the tables with a player_UUID column, null until they are known
    private static volatile Set<String> playerTables;
    // Online players: uuid -> table (lower case) -> row (column in lower case -> value or NULL); no entry = no row
    private static final Map<String, Map<String, Map<String, Object>>> players = new ConcurrentHashMap<>();

    private interface SqlCall<T> {
        T run(Connection c) throws SQLException;
    }

    private interface ColumnReader<T> {
        T read(String value);
    }

    public SQL(Main plugin) {
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable")) {
            String host = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Host");
            int port = ConfigGeneral.getConfig().getInt("Plugin.Use.MYSQL.Port");
            String database = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Database");
            username = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Username");
            password = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Password");

            if (host == null || username == null || password == null || database == null) {
                url = null;
            } else if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Use-SSL")) {
                url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            } else {
                url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
            }

            async = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r, "Hawn - MySQL");
                t.setDaemon(true);
                return t;
            });

            useyamllistplayer = true;
            async.execute(() -> {
                openConnection();
                if (!useyamllistplayer) {
                    loadPlayerTables();
                    // Players already online (/reload)
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        loadPlayer(p.getUniqueId().toString());
                    }
                }
            });

            Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler(priority = EventPriority.MONITOR)
                public void onPreLogin(AsyncPlayerPreLoginEvent e) {
                    if (e.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED || useyamllistplayer) {
                        return;
                    }

                    // On the MySQL thread, after the writes of a previous session of this player
                    String uuid = e.getUniqueId().toString();
                    try {
                        async.submit(() -> loadPlayer(uuid)).get(10, TimeUnit.SECONDS);
                    } catch (Exception ignored) {}
                }

                // After every other quit listener: the rows leave the memory once their writes are done
                @EventHandler(priority = EventPriority.MONITOR)
                public void onQuit(PlayerQuitEvent e) {
                    String uuid = e.getPlayer().getUniqueId().toString();
                    async(() -> players.remove(uuid));
                }
            }, plugin);

            // Keeps the connection alive (MySQL closes it after wait_timeout, 8 h by default) and reopens it if lost
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!useyamllistplayer && !isValid()) {
                        reconnect();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 6000L, 6000L);
        } else {
            useyamllistplayer = true;
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (MySQL not enabled)");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
        }
    }

    public void openConnection() {
        if (url == null) {
            return;
        }

        try {
            connect();
            useyamllistplayer = false;
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use MySQL as method for information");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ------------------------------------");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connect Error: " + e.getMessage());
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information");
            useyamllistplayer = true;
        }
    }

    // ------------------------------------------------------------------ connection

    private static synchronized void connect() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Class.forName("com.mysql.jdbc.Driver");
        }

        connection = DriverManager.getConnection(url, username, password);
    }

    private static synchronized boolean reconnect() {
        if (url == null) {
            return false;
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {}
        connection = null;

        try {
            connect();
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Reconnect Error: " + e.getMessage());
            return false;
        }
    }

    private static boolean isValid() {
        try {
            return connection != null && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Runs a query; when the connection was lost, reconnects and tries once more.
     */
    private static <T> T call(SqlCall<T> sql, T fallback) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                if (connection == null || connection.isClosed()) {
                    if (!reconnect()) {
                        return fallback;
                    }
                }
                return sql.run(connection);
            } catch (SQLException e) {
                if (attempt == 0 && !isValid() && reconnect()) {
                    continue;
                }
                e.printStackTrace();
                return fallback;
            }
        }
        return fallback;
    }

    /**
     * Runs the task on the MySQL thread (in order with the other tasks), or right now without MySQL.
     */
    public static void async(Runnable task) {
        if (async == null || async.isShutdown()) {
            task.run();
        } else {
            async.execute(task);
        }
    }

    /**
     * Server stop: ends the waiting writes and closes the connection.
     */
    public static void close() {
        if (async != null) {
            async.shutdown();
            try {
                async.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        players.clear();
        playerTables = null;

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {}
        connection = null;
    }

    // ------------------------------------------------------------------ player rows in memory

    private static String key(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Finds the tables with a player_UUID column, and gives the old ones (created before Hawn 1.3) their primary key.
     */
    private static void loadPlayerTables() {
        List<String> tables = call(c -> {
            List<String> list = new ArrayList<>();
            try (PreparedStatement s = c.prepareStatement("SELECT TABLE_NAME, COLUMN_KEY FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND COLUMN_NAME = 'player_UUID';");
                 ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    String table = rs.getString(1);
                    if (!"PRI".equals(rs.getString(2)) && !addPrimaryKey(c, table)) {
                        continue;
                    }
                    list.add(table);
                }
            }
            return list;
        }, null);

        if (tables == null) {
            return;
        }

        Set<String> names = ConcurrentHashMap.newKeySet();
        for (String table : tables) {
            names.add(key(table));
        }
        playerTables = names;
    }

    /**
     * Old table: one row is kept per player (the first one), in a new table with a primary key.
     * The old table stays as <table>_before_1_3.
     */
    private static boolean addPrimaryKey(Connection c, String table) {
        if (table.toLowerCase(Locale.ROOT).contains("_before_1_3")) {
            return false;
        }

        String backup = table + "_before_1_3";
        String fresh = table + "_hawn_new";

        try (Statement s = c.createStatement()) {
            s.execute("DROP TABLE IF EXISTS " + fresh + ";");
            s.execute("CREATE TABLE " + fresh + " LIKE " + table + ";");
            s.execute("ALTER TABLE " + fresh + " MODIFY player_UUID VARCHAR(36) NOT NULL, ADD PRIMARY KEY (player_UUID);");
            int kept = s.executeUpdate("INSERT IGNORE INTO " + fresh + " SELECT * FROM " + table + " WHERE player_UUID IS NOT NULL;");
            s.execute("RENAME TABLE " + table + " TO " + backup + ", " + fresh + " TO " + table + ";");

            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "| " + ChatColor.YELLOW + "MySQL: the table " + table + " now has one row per player ("
                    + kept + " kept). The old table is saved as " + backup + ".");
            return true;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL: could not add the primary key to " + table + ": " + e.getMessage());
            return false;
        }
    }

    private static void loadPlayer(String uuid) {
        Set<String> tables = playerTables;
        if (tables == null) {
            return;
        }

        Map<String, Map<String, Object>> rows = call(c -> {
            Map<String, Map<String, Object>> found = new ConcurrentHashMap<>();
            for (String table : tables) {
                try (PreparedStatement s = c.prepareStatement("SELECT * FROM " + table + " WHERE player_UUID = ? LIMIT 1;")) {
                    s.setString(1, uuid);
                    try (ResultSet rs = s.executeQuery()) {
                        if (rs.next()) {
                            Map<String, Object> row = new ConcurrentHashMap<>();
                            ResultSetMetaData meta = rs.getMetaData();
                            for (int i = 1; i <= meta.getColumnCount(); i++) {
                                String value = rs.getString(i);
                                row.put(key(meta.getColumnLabel(i)), value == null ? NULL : value);
                            }
                            found.put(table, row);
                        }
                    }
                }
            }
            return found;
        }, null);

        if (rows != null) {
            players.put(uuid, rows);
        }
    }

    /**
     * The row of a player kept in memory: null when the answer must come from the database, empty when there is no row.
     */
    private static Optional<Map<String, Object>> cached(String table, String column, Object uuid) {
        if (uuid == null || column == null || !"player_uuid".equals(key(column))) {
            return null;
        }

        Map<String, Map<String, Object>> rows = players.get(String.valueOf(uuid));
        if (rows == null) {
            return null;
        }
        return Optional.ofNullable(rows.get(key(table)));
    }

    // ------------------------------------------------------------------ queries

    public static void updateSQL(String command) {
        if (command == null) {
            return;
        }

        call(c -> {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(command);
            }
            return null;
        }, null);
    }

    private static void update(String command, Object... values) {
        call(c -> {
            try (PreparedStatement statement = c.prepareStatement(command)) {
                bind(statement, values);
                statement.executeUpdate();
            }
            return null;
        }, null);
    }

    private static void bind(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                statement.setNull(i + 1, Types.VARCHAR);
            } else {
                statement.setString(i + 1, String.valueOf(values[i]));
            }
        }
    }

    public static boolean tableExists(String table) {
        if (table == null) {
            return false;
        }

        // Every Hawn table has a player_UUID column: once they are known, no need to ask the database
        Set<String> tables = playerTables;
        if (tables != null) {
            return tables.contains(key(table));
        }

        return call(c -> {
            try (ResultSet rs = c.getMetaData().getTables(null, null, table, null)) {
                return rs.next();
            }
        }, false);
    }

    /**
     * @param values the values as written in SQL: 'text', with '' for a quote inside
     */
    public static void insertData(String columns, String values, String table) {
        List<String> names = new ArrayList<>();
        for (String column : columns.split(",")) {
            names.add(column.trim());
        }
        List<String> data = parseValues(values);

        if (data == null || data.size() != names.size()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL: wrong insert in " + table + " (" + columns + ")");
            return;
        }

        StringBuilder marks = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            marks.append(i == 0 ? "?" : ", ?");
        }
        String command = "INSERT INTO " + table + " (" + columns + ") VALUES (" + marks + ");";
        Object[] params = data.toArray();

        int uuidIndex = -1;
        for (int i = 0; i < names.size(); i++) {
            if (key(names.get(i)).equals("player_uuid")) {
                uuidIndex = i;
            }
        }

        Optional<Map<String, Object>> row = uuidIndex == -1 ? null : cached(table, "player_UUID", data.get(uuidIndex));
        if (row == null) {
            update(command, params);
            return;
        }

        if (!row.isPresent()) {
            Map<String, Object> created = new ConcurrentHashMap<>();
            for (int i = 0; i < names.size(); i++) {
                created.put(key(names.get(i)), data.get(i) == null ? NULL : data.get(i));
            }
            Map<String, Map<String, Object>> rows = players.get(data.get(uuidIndex));
            if (rows != null) {
                rows.put(key(table), created);
            }
        }
        async(() -> update(command, params));
    }

    // 'a', 'b''s', 12 -> [a, b's, 12]
    private static List<String> parseValues(String values) {
        List<String> list = new ArrayList<>();
        int i = 0;
        int n = values.length();

        while (i < n) {
            while (i < n && (values.charAt(i) == ' ' || values.charAt(i) == ',')) i++;
            if (i >= n) break;

            if (values.charAt(i) == '\'') {
                StringBuilder sb = new StringBuilder();
                i++;
                while (true) {
                    if (i >= n) return null;
                    char ch = values.charAt(i);
                    if (ch == '\'') {
                        if (i + 1 < n && values.charAt(i + 1) == '\'') {
                            sb.append('\'');
                            i += 2;
                            continue;
                        }
                        i++;
                        break;
                    }
                    sb.append(ch);
                    i++;
                }
                list.add(sb.toString());
            } else {
                int start = i;
                while (i < n && values.charAt(i) != ',') i++;
                String bare = values.substring(start, i).trim();
                list.add(bare.equalsIgnoreCase("NULL") ? null : bare);
            }
        }
        return list;
    }

    public static void deleteData(String column, String logic_gate, String data, String table) {
        Optional<Map<String, Object>> row = "=".equals(logic_gate.trim()) ? cached(table, column, data) : null;
        if (row != null) {
            Map<String, Map<String, Object>> rows = players.get(data);
            if (rows != null) {
                rows.remove(key(table));
            }
            async(() -> update("DELETE FROM " + table + " WHERE " + column + logic_gate + "?;", data));
            return;
        }
        update("DELETE FROM " + table + " WHERE " + column + logic_gate + "?;", data);
    }

    public static boolean exists(String column, String data, String table) {
        Optional<Map<String, Object>> row = cached(table, column, data);
        if (row != null) {
            return row.isPresent();
        }

        return call(c -> {
            try (PreparedStatement statement = c.prepareStatement("SELECT " + column + " FROM " + table + " WHERE " + column + " = ? LIMIT 1;")) {
                bind(statement, data);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next() && rs.getString(column) != null;
                }
            }
        }, false);
    }

    public static void deleteTable(String table) {
        removeTable(table);
        updateSQL("DROP TABLE " + table + ";");
    }

    public static void truncateTable(String table) {
        removeTable(table);
    	updateSQL("TRUNCATE TABLE " + table + ";");
    }

    private static void removeTable(String table) {
        for (Map<String, Map<String, Object>> rows : players.values()) {
            rows.remove(key(table));
        }
    }

    /**
     * The new tables get a primary key on player_UUID: one row per player, and fast lookups.
     */
    public static void createTable(String table, String columns) {
        if (!tableExists(table)) {
        	updateSQL("CREATE TABLE " + table + " (" + columns.replace("player_UUID TEXT", "player_UUID VARCHAR(36) NOT NULL PRIMARY KEY") + ");");

            Set<String> tables = playerTables;
            if (tables != null && columns.contains("player_UUID")) {
                tables.add(key(table));
            }
        }
    }

    public static void set(String table, String column, Object newvalue, String columnwhere, String datawhere) {
        String command = "UPDATE " + table + " SET " + column + " = ? WHERE " + columnwhere + " = ?;";

        Optional<Map<String, Object>> row = cached(table, columnwhere, datawhere);
        if (row == null) {
            update(command, newvalue, datawhere);
            return;
        }

        if (row.isPresent()) {
            Object stored = newvalue == null ? NULL : String.valueOf(newvalue);
            // Many getters write the player name again at each read: nothing to send when nothing changes
            if (stored.equals(row.get().put(key(column), stored))) {
                return;
            }
        }
        async(() -> update(command, newvalue, datawhere));
    }

    public static Object get(String selected, String column, String data, String table) {
        return call(c -> {
            try (PreparedStatement statement = c.prepareStatement("SELECT " + selected + " FROM " + table + " WHERE " + column + " = ? LIMIT 1;")) {
                bind(statement, data);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next() ? rs.getObject(selected) : null;
                }
            }
        }, null);
    }

    /**
     * Like ResultSet: no row gives null, a NULL value gives null for a text and 0 for a number.
     */
    private static <T> T getInfo(String table, String column, String player_UUID, ColumnReader<T> reader) {
        Optional<Map<String, Object>> row = cached(table, "player_UUID", player_UUID);
        if (row != null) {
            if (!row.isPresent()) {
                return null;
            }
            Object value = row.get().get(key(column));
            return reader.read(value == null || value == NULL ? null : (String) value);
        }

        return call(c -> {
            try (PreparedStatement statement = c.prepareStatement("SELECT " + column + " FROM " + table + " WHERE player_UUID = ? LIMIT 1;")) {
                bind(statement, player_UUID);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next() ? reader.read(rs.getString(column)) : null;
                }
            }
        }, null);
    }

    private static double number(String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String getInfoString(String table, String column, String player_UUID) {
        return getInfo(table, column, player_UUID, value -> value);
    }

    public static Integer getInfoInt(String table, String column, String player_UUID) {
        return getInfo(table, column, player_UUID, value -> (int) number(value));
    }

    public static Double getInfoDouble(String table, String column, String player_UUID) {
        return getInfo(table, column, player_UUID, SQL::number);
    }

    public static Float getInfoFloat(String table, String column, String player_UUID) {
        return getInfo(table, column, player_UUID, value -> (float) number(value));
    }

    public static ArrayList < Object > listGet(String selected, String column, String logic_gate, String data, String table) {
        return call(c -> {
            ArrayList < Object > array = new ArrayList < Object > ();
            try (PreparedStatement statement = c.prepareStatement("SELECT " + selected + " FROM " + table + " WHERE " + column + logic_gate + "?;")) {
                bind(statement, data);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        array.add(rs.getObject(selected));
                    }
                }
            }
            return array;
        }, new ArrayList<>());
    }

    public int countRows(String table) {
        if (table == null) {
            return 0;
        }

        return call(c -> {
            try (Statement statement = c.createStatement(); ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + table + ";")) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }, 0);
    }

    public static void addColumn(String table, String column_name, String data_type) {
        updateSQL("ALTER TABLE " + table + " ADD " + column_name + " " + data_type + " NULL ;");
    }
}
