package fr.dianox.hawn;

import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;

public class SQL {

    public static Connection connection;
    private String host,
            database,
            username,
            password;
    private int port;
    private static Statement statement;
    public boolean useyamllistplayer = false;

    public SQL(Main plugin) {
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable")) {
            host = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Host");
            port = ConfigGeneral.getConfig().getInt("Plugin.Use.MYSQL.Port");
            database = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Database");
            username = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Username");
            password = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Password");

            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        useyamllistplayer = true;
                        openConnection();
                        statement = connection.createStatement();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        useyamllistplayer = true;
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (ClassNotFoundException)");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        useyamllistplayer = true;
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (SQLException)");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
                    }
                }
            };

            r.runTaskAsynchronously(plugin);
        } else {
            useyamllistplayer = true;
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (MySQL not enabled)");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
        }
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        if (host == null || username == null || password == null || database == null) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            try {
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Use-SSL")) {
                    connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
                } else {
                    connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?useSSL=false", this.username, this.password);
                }
                useyamllistplayer = false;
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ------------------------------------");
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use MySQL as method for information");
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ------------------------------------");
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connect Error: " + e.getMessage());
                useyamllistplayer = true;
            }
        }
    }

    public static void updateSQL(String command) {
        if (command == null) {
            return;
        }

        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet querySQL(String command) {
        if (command == null) {
            return null;
        }

        ResultSet rs = null;

        try {
            rs = statement.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean tableExists(String table) {
        if (table == null) {
            return false;
        }
        try {
            if (connection == null) {
                return false;
            }
            DatabaseMetaData metadata = connection.getMetaData();
            if (metadata == null) {
                return false;
            }
            ResultSet rs = metadata.getTables(null, null, table, null);
            if (rs.next()) {
                return true;
            }
        } catch (Exception ignored) {}

        return false;
    }

    public static void insertData(String columns, String values, String table) {
        updateSQL("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
    }

    public static void deleteData(String column, String logic_gate, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }

        updateSQL("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
    }

    public static boolean exists(String column, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = querySQL("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
            while (rs.next()) {
                if (rs.getString(column) != null) {
                    return true;
                }
            }
        } catch (Exception ignored) {}


        return false;
    }


    public static void deleteTable(String table) {
        updateSQL("DROP TABLE " + table + ";");
    }



    public static void truncateTable(String table) {
    	updateSQL("TRUNCATE TABLE " + table + ";");
    }


    public static void createTable(String table, String columns) {
        if (!tableExists(table)) {
        	updateSQL("CREATE TABLE " + table + " (" + columns + ");");
        }
    }

    public static void set(String table, String column, Object newvalue, String columnwhere, String datawhere) {
        if (newvalue != null) {
        	newvalue = "'" + newvalue + "'";
        }
        updateSQL("UPDATE " + table + " SET " + column + "=" + newvalue + " WHERE " + columnwhere + " = \"" + datawhere + "\" ;");
    }

    public static Object get(String selected, String column, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = querySQL("SELECT * FROM " + table + " WHERE " + column + " = " + data + ";");
            if (rs.next()) {
                return rs.getObject(selected);
            }
        } catch (Exception ignored) {}


        return null;
    }

    public static String getInfoString(String table, String column, String player_UUID) {
    	 try {
             ResultSet rs = querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

             if (rs.next()) {
            	 return rs.getString(column);
             }

         } catch (Exception ignored) {}

    	 return null;
    }

    public static Integer getInfoInt(String table, String column, String player_UUID) {
   	 try {
            ResultSet rs = querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

            while (rs.next()) {
            	return rs.getInt(column);
            }

        } catch (Exception ignored) {}

   	 return null;
   }

    public static Double getInfoDouble(String table, String column, String player_UUID) {
      	 try {
               ResultSet rs = querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

               while (rs.next()) {
               	return rs.getDouble(column);
               }

           } catch (Exception ignored) {}

      	 return null;
    }

    public static Float getInfoFloat(String table, String column, String player_UUID) {
     	 try {
              ResultSet rs = querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

              while (rs.next()) {
              	return rs.getFloat(column);
              }

          } catch (Exception ignored) {}

     	 return null;
     }

    public static ArrayList < Object > listGet(String selected, String column, String logic_gate, String data, String table) {
        ArrayList < Object > array = new ArrayList < Object > ();

        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = querySQL("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
            while (rs.next()) {
                array.add(rs.getObject(selected));
            }
        } catch (Exception ignored) {}


        return array;
    }

    public int countRows(String table) {
        int i = 0;
        if (table == null) {
            return i;
        }
        ResultSet rs = querySQL("SELECT * FROM " + table + ";");
        try {
            while (rs.next()) {
                i++;
            }
        } catch (Exception ignored) {}


        return i;
    }

    public static void addColumn(String table, String column_name, String data_type) {
        updateSQL("ALTER TABLE " + table + " ADD " + column_name + " " + data_type + " NULL ;");
    }
}
