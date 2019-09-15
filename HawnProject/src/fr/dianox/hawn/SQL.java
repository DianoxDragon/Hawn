package fr.dianox.hawn;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQL {

    public static boolean tableExists(String table) {
        if (table == null) {
            return false;
        }
        try {
            if (Main.connection == null) {
                return false;
            }
            DatabaseMetaData metadata = Main.connection.getMetaData();
            if (metadata == null) {
                return false;
            }
            ResultSet rs = metadata.getTables(null, null, table, null);
            if (rs.next()) {
                return true;
            }
        } catch (Exception exception) {}

        return false;
    }

    public static void insertData(String columns, String values, String table) {
        Main.updateSQL("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
    }

    public static void deleteData(String column, String logic_gate, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }

        Main.updateSQL("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
    }

    public static boolean exists(String column, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = Main.querySQL("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
            while (rs.next()) {
                if (rs.getString(column) != null) {
                    return true;
                }
            }
        } catch (Exception exception) {}


        return false;
    }


    public static void deleteTable(String table) {
        Main.updateSQL("DROP TABLE " + table + ";");
    }



    public static void truncateTable(String table) {
    	Main.updateSQL("TRUNCATE TABLE " + table + ";");
    }


    public static void createTable(String table, String columns) {
        if (!tableExists(table)) {
        	Main.updateSQL("CREATE TABLE " + table + " (" + columns + ");");
        }
    }

    public static void set(String table, String column, Object newvalue, String columnwhere, String datawhere) {
        if (newvalue != null) {
        	newvalue = "'" + newvalue + "'";
        }
        Main.updateSQL("UPDATE " + table + " SET " + column + "=" + newvalue + " WHERE " + columnwhere + " = \"" + datawhere + "\" ;");
    }

    public static Object get(String selected, String column, String data, String table) {
        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = Main.querySQL("SELECT * FROM " + table + " WHERE " + column + " = " + data + ";");
            if (rs.next()) {
                return rs.getObject(selected);
            }
        } catch (Exception exception) {}


        return null;
    }

    public static String getInfoString(String table, String column, String player_UUID) {
    	 try {
             ResultSet rs = Main.querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

             if (rs.next()) {
            	 return rs.getString(column);
             }

         } catch (Exception exception) {}

    	 return null;
    }

    public static Integer getInfoInt(String table, String column, String player_UUID) {
   	 try {
            ResultSet rs = Main.querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

            while (rs.next()) {
            	return rs.getInt(column);
            }

        } catch (Exception exception) {}

   	 return null;
   }

    public static Double getInfoDouble(String table, String column, String player_UUID) {
      	 try {
               ResultSet rs = Main.querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

               while (rs.next()) {
               	return rs.getDouble(column);
               }

           } catch (Exception exception) {}

      	 return null;
    }

    public static Float getInfoFloat(String table, String column, String player_UUID) {
     	 try {
              ResultSet rs = Main.querySQL("SELECT " + column + " FROM " + table + " WHERE player_UUID = \"" + player_UUID + "\" ;");

              while (rs.next()) {
              	return rs.getFloat(column);
              }

          } catch (Exception exception) {}

     	 return null;
     }

    public static ArrayList < Object > listGet(String selected, String column, String logic_gate, String data, String table) {
        ArrayList < Object > array = new ArrayList < Object > ();

        if (data != null) {
            data = "'" + data + "'";
        }
        try {
            ResultSet rs = Main.querySQL("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
            while (rs.next()) {
                array.add(rs.getObject(selected));
            }
        } catch (Exception exception) {}


        return array;
    }

    public int countRows(String table) {
        int i = 0;
        if (table == null) {
            return i;
        }
        ResultSet rs = Main.querySQL("SELECT * FROM " + table + ";");
        try {
            while (rs.next()) {
                i++;
            }
        } catch (Exception exception) {}


        return i;
    }

    public static void addColumn(String table, String column_name, String data_type) {
        Main.updateSQL("ALTER TABLE " + table + " ADD " + column_name + " " + data_type + " NULL ;");
    }
}
