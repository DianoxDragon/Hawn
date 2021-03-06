package fr.dianox.hawn.utility;

import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.SQL;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;

public class PlayerOptionSQLClass {
	
    /*
     * Player Option fly Speed
     */
    // > Save YAML/SQL
    public static void SaveSQLPOFlySpeed(Player p, String boolea, Integer value) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_fly_speed.Activate", boolea.equalsIgnoreCase("TRUE"));

        ConfigPlayerGet.writeInt(uuid, "player_fly_speed.value", value);

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (! SQL.tableExists("player_fly_speed")) {
                SQL.createTable("player_fly_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_fly_speed")) {
                SQL.set("player_fly_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_fly_speed", "value", "" + value + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_fly_speed", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, value, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '" + boolea + "' ", "player_fly_speed");
            }
        }
    }

    public static String GetSQLPOFlySpeed(Player p, String method) {
        String value = "";
        String uuid = p.getUniqueId().toString();

        if (!ConfigPlayerGet.getFile(uuid).isSet("player_fly_speed.Activate")) {
            ConfigPlayerGet.writeBoolean(uuid, "player_fly_speed.Activate", false);
            ConfigPlayerGet.writeInt(uuid, "player_fly_speed.value", OnJoinConfig.getConfig().getInt("Speed.Value"));
        }

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (method.equalsIgnoreCase("VALUE")) {
                value = String.valueOf(ConfigPlayerGet.getFile(uuid).getInt("player_fly_speed.value"));
            } else if (method.equalsIgnoreCase("ACTIVATE")) {
                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_fly_speed.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }
            }
        } else {
            if (!SQL.tableExists("player_fly_speed")) {
                SQL.createTable("player_fly_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_fly_speed")) {
                SQL.set("player_fly_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                if (method.equalsIgnoreCase("VALUE")) {
                    value = String.valueOf(SQL.getInfoInt("player_fly_speed", "value", "" + p.getUniqueId() + ""));
                } else if (method.equalsIgnoreCase("ACTIVATE")) {
                    value = String.valueOf(SQL.getInfoString("player_fly_speed", "Activate", "" + p.getUniqueId() + ""));
                }
            } else {
                if (method.equalsIgnoreCase("VALUE")) {
                    String getac;
                    Integer getva = ConfigPlayerGet.getFile(uuid).getInt("player_fly_speed.value");

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_fly_speed.Activate")) {
                        getac = "TRUE";
                    } else {
                        getac = "FALSE";
                    }

                    SQL.insertData("player, player_UUID, value, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '" + getac + "' ", "player_fly_speed");

                    value = String.valueOf(getva);
                } else if (method.equalsIgnoreCase("ACTIVATE")) {
                    String getac;
                    int getva = ConfigPlayerGet.getFile(uuid).getInt("player_fly_speed.value");

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_fly_speed.Activate")) {
                        getac = "TRUE";
                    } else {
                        getac = "FALSE";
                    }

                    SQL.insertData("player, player_UUID, value, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + getva + "', '" + getac + "' ", "player_fly_speed");

                    value = getac;
                }
            }
        }

        return value;
    }
    
    /*
     * Player Option Speed
     */
    // > Save YAML/SQL
    public static void SaveSQLPOSpeed(Player p, String boolea, Integer value) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_speed.Activate", boolea.equalsIgnoreCase("TRUE"));

        ConfigPlayerGet.writeInt(uuid, "player_speed.value", value);

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (! SQL.tableExists("player_speed")) {
                SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
                SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_speed", "value", "" + value + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_speed", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, value, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '" + boolea + "' ", "player_speed");
            }
        }
    }

    public static String GetSQLPOSpeed(Player p, String method) {
        String value = "";
        String uuid = p.getUniqueId().toString();

        if (!ConfigPlayerGet.getFile(uuid).isSet("player_speed.Activate")) {
            ConfigPlayerGet.writeBoolean(uuid, "player_speed.Activate", false);
            ConfigPlayerGet.writeInt(uuid, "player_speed.value", OnJoinConfig.getConfig().getInt("Speed.Value"));
        }

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (method.equalsIgnoreCase("VALUE")) {
                value = String.valueOf(ConfigPlayerGet.getFile(uuid).getInt("player_speed.value"));
            } else if (method.equalsIgnoreCase("ACTIVATE")) {
                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_speed.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }
            }
        } else {
            if (!SQL.tableExists("player_speed")) {
                SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
                SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                if (method.equalsIgnoreCase("VALUE")) {
                    value = String.valueOf(SQL.getInfoInt("player_speed", "value", "" + p.getUniqueId() + ""));
                } else if (method.equalsIgnoreCase("ACTIVATE")) {
                    value = String.valueOf(SQL.getInfoString("player_speed", "Activate", "" + p.getUniqueId() + ""));
                }
            } else {
                if (method.equalsIgnoreCase("VALUE")) {
                    String getac;
                    Integer getva = ConfigPlayerGet.getFile(uuid).getInt("player_speed.value");

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_speed.Activate")) {
                        getac = "TRUE";
                    } else {
                        getac = "FALSE";
                    }

                    SQL.insertData("player, player_UUID, value, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '" + getac + "' ", "player_speed");

                    value = String.valueOf(getva);
                } else if (method.equalsIgnoreCase("ACTIVATE")) {
                    String getac;
                    int getva = ConfigPlayerGet.getFile(uuid).getInt("player_speed.value");

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_speed.Activate")) {
                        getac = "TRUE";
                    } else {
                        getac = "FALSE";
                    }

                    SQL.insertData("player, player_UUID, value, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + getva + "', '" + getac + "' ", "player_speed");

                    value = getac;
                }
            }
        }

        return value;
    }

    /*
     * Player Option Jumpboost
     */
    // > Save YAML/SQL
    public static void SaveSQLPOJumpBoost(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_jumpboost.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_jumpboost")) {
                SQL.createTable("player_option_jumpboost", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_jumpboost")) {
                SQL.set("player_option_jumpboost", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_jumpboost", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_jumpboost");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPOJumpBoost(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_jumpboost.Activate")) {
                ConfigPlayerGet.writeBoolean(uuid, "player_option_jumpboost.Activate", false);
            }

            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_jumpboost.Activate")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_option_jumpboost")) {
                SQL.createTable("player_option_jumpboost", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_jumpboost")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_jumpboost.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_jumpboost.Activate", false);
                }

                value = String.valueOf(SQL.getInfoString("player_option_jumpboost", "Activate", "" + p.getUniqueId() + ""));
                SQL.set("player_option_jumpboost", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_jumpboost.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_jumpboost.Activate", false);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_jumpboost.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_jumpboost");
            }
        }

        return value;
    }

    /*
     * Player option, keep scoreboard
     */
    public static String getYmlaMysqlsb(Player p, String option) {
        String value = "";
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (option.equalsIgnoreCase("scoreboard")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_keep_sb.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", false);
                    ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", null);
                }

                value = ConfigPlayerGet.getFile(uuid).getString("player_option_keep_sb.Scoreboard");
            } else if (option.equalsIgnoreCase("keepsb")) {

                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_keep_sb.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", false);
                    ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", null);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_keep_sb.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }
            }
        } else {
            if (!SQL.tableExists("player_option_keep_sb")) {
                SQL.createTable("player_option_keep_sb", "player TEXT, player_UUID TEXT, Activate TEXT, Scoreboard TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_keep_sb")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_keep_sb.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", false);
                    ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", null);
                }

                if (option.equalsIgnoreCase("scoreboard")) {
                    value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Scoreboard", "" + p.getUniqueId() + ""));
                } else if (option.equalsIgnoreCase("keepsb")) {
                    value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Activate", "" + p.getUniqueId() + ""));
                }
                SQL.set("player_option_keep_sb", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (option.equalsIgnoreCase("scoreboard")) {
                    if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_keep_sb.Activate")) {
                        ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", false);
                        ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", null);
                    }

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_keep_sb.Activate")) {
                        SQL.insertData("player, player_UUID, Activate, Scoreboard",
                            " '" + p.getName() + "', '" + p.getUniqueId() + "', 'TRUE', '" + ConfigPlayerGet.getFile(uuid).getString("player_option_keep_sb.Scoreboard") + "' ", "player_option_keep_sb");
                    } else {
                        SQL.insertData("player, player_UUID, Activate, Scoreboard",
                            " '" + p.getName() + "', '" + p.getUniqueId() + "', 'FALSE', '" + ConfigPlayerGet.getFile(uuid).getString("player_option_keep_sb.Scoreboard") + "' ", "player_option_keep_sb");
                    }

                    value = ConfigPlayerGet.getFile(uuid).getString("player_option_keep_sb.Scoreboard");
                } else if (option.equalsIgnoreCase("keepsb")) {
                    if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_keep_sb.Activate")) {
                        ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", false);
                        ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", null);
                    }

                    if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_keep_sb.Activate")) {
                        value = "TRUE";
                    } else {
                        value = "FALSE";
                    }

                    SQL.insertData("player, player_UUID, Activate, Scoreboard",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '" + ConfigPlayerGet.getFile(uuid).getString("player_option_keep_sb.Scoreboard") + "' ", "player_option_keep_sb");
                }
            }
        }

        return value;
    }

    public static void saveSBmysqlyaml(Player p, String sb, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_keep_sb.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        ConfigPlayerGet.writeString(uuid, "player_option_keep_sb.Scoreboard", sb);

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_keep_sb")) {
                SQL.createTable("player_option_keep_sb", "player TEXT, player_UUID TEXT, Activate TEXT, Scoreboard TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_keep_sb")) {
                SQL.set("player_option_keep_sb", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_keep_sb", "Scoreboard", "" + sb + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_keep_sb", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate, Scoreboard",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "', '" + sb + "' ", "player_option_keep_sb");
            }
        }
    }

    /*
     * Fly option
     */
    // > Save YAML/SQL
    public static void SaveSQLPOFly(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_fly.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_fly")) {
                SQL.createTable("player_option_fly", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_fly")) {
                SQL.set("player_option_fly", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_fly", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_fly");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPOFly(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_fly.Activate")) {
                ConfigPlayerGet.writeBoolean(uuid, "player_option_fly.Activate", false);
            }

            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_fly.Activate")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_option_fly")) {
                SQL.createTable("player_option_fly", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_fly")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_fly.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_fly.Activate", false);
                }

                value = String.valueOf(SQL.getInfoString("player_option_fly", "Activate", "" + p.getUniqueId() + ""));
                SQL.set("player_option_fly", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_fly.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_fly.Activate", false);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_fly.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_fly");
            }
        }

        return value;
    }

    /*
     * DoubleJump option
     */
    // > Save YAML/SQL
    public static void SaveSQLPODoubleJump(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_doublejump.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_doublejump")) {
                SQL.createTable("player_option_doublejump", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_doublejump")) {
                SQL.set("player_option_doublejump", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_doublejump", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_doublejump");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPODoubleJump(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_doublejump.Activate")) {
                ConfigPlayerGet.writeBoolean(uuid, "player_option_doublejump.Activate", false);
            }

            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_doublejump.Activate")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_option_doublejump")) {
                SQL.createTable("player_option_doublejump", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_doublejump")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_doublejump.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_doublejump.Activate", false);
                }

                value = String.valueOf(SQL.getInfoString("player_option_doublejump", "Activate", "" + p.getUniqueId() + ""));
                SQL.set("player_option_doublejump", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_doublejump.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_doublejump.Activate", false);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_doublejump.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_doublejump");
            }
        }

        return value;
    }

    /*
     * Player visibility
     */
    public static String getValueMysqlYaml(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_pv.Activate")) {
            ConfigPlayerGet.writeBoolean(uuid, "player_option_pv.Activate", PlayerVisibility.PVPlayer.contains(p));
        }

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_pv.Activate")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_option_pv")) {
                SQL.createTable("player_option_pv", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_pv")) {
                value = String.valueOf(SQL.getInfoString("player_option_pv", "Activate", "" + p.getUniqueId() + ""));
                SQL.set("player_option_pv", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_pv.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_pv");
            }
        }

        return value;
    }

    public static void onMysqlYamlCJIChange(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_pv.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_pv")) {
                SQL.createTable("player_option_pv", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_pv")) {
                SQL.set("player_option_pv", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_pv", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_pv");
            }
        }

    }

    /*
     * Vanish option
     */
    // > Save YAML/SQL
    public static void SaveSQLPOVanish(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_vanish.vanished", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_vanish")) {
                SQL.createTable("player_vanish", "player TEXT, player_UUID TEXT, vanished TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_vanish")) {
                SQL.set("player_vanish", "vanished", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_vanish", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, vanished",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_vanish");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPOVanish(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_vanish.vanished")) {
                ConfigPlayerGet.writeBoolean(uuid, "player_vanish.vanished", false);
            }

            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_vanish.vanished")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_vanish")) {
                SQL.createTable("player_vanish", "player TEXT, player_UUID TEXT, vanished TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_vanish")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_vanish.vanished")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_vanish.vanished", false);
                }

                value = String.valueOf(SQL.getInfoString("player_vanish", "vanished", "" + p.getUniqueId() + ""));
                SQL.set("player_vanish", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_vanish.vanished")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_vanish.vanished", false);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_vanish.vanished")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, vanished",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_vanish");
            }
        }

        return value;
    }

    /*
     * Gamemode
     */
    // > Save the actual value
    public static void SaveSQLPOGamemode(Player p, Integer i) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeInt(uuid, "player_gamemode.gamemode_state", i);

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_gamemode")) {
                SQL.createTable("player_gamemode", "player TEXT, player_UUID TEXT, gamemode_state INT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_gamemode")) {
                SQL.set("player_gamemode", "gamemode_state", "" + i + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_gamemode", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, gamemode_state",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + i + "' ", "player_gamemode");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPOGamemode(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (!ConfigPlayerGet.getFile(uuid).isSet("player_gamemode.gamemode_state")) {
            ConfigPlayerGet.writeInt(uuid, "player_gamemode.gamemode_state", 1);
        }

        if (Main.getInstance().getSql().useyamllistplayer) {

            value = String.valueOf(ConfigPlayerGet.getFile(uuid).getInt("player_gamemode.gamemode_stat"));

        } else {
            if (!SQL.tableExists("player_gamemode")) {
                SQL.createTable("player_gamemode", "player TEXT, player_UUID TEXT, gamemode_state INT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_gamemode")) {

                value = String.valueOf(SQL.getInfoInt("player_gamemode", "gamemode_state", "" + p.getUniqueId() + ""));
                SQL.set("player_gamemode", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {

                value = String.valueOf(ConfigPlayerGet.getFile(uuid).getInt("player_gamemode.gamemode_stat"));
                SQL.insertData("player, player_UUID, gamemode_state",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_gamemode");
            }
        }

        return value;
    }
    
    /*
     * Player Option autobc
     */
    // > Save YAML/SQL
    public static void SaveSQLPOautobc(Player p, String boolea) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeBoolean(uuid, "player_option_autobc.Activate", ! boolea.equalsIgnoreCase("FALSE"));

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_autobc")) {
                SQL.createTable("player_option_autobc", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_autobc")) {
                SQL.set("player_option_autobc", "Activate", "" + boolea + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_autobc", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_autobc");
            }
        }
    }

    // > Get the actual value
    public static String GetSQLPOautobc(Player p) {
        String value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_autobc.Activate")) {
                ConfigPlayerGet.writeBoolean(uuid, "player_option_autobc.Activate", true);
            }

            if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_autobc.Activate")) {
                value = "TRUE";
            } else {
                value = "FALSE";
            }
        } else {
            if (!SQL.tableExists("player_option_autobc")) {
                SQL.createTable("player_option_autobc", "player TEXT, player_UUID TEXT, Activate TEXT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_autobc")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_autobc.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_autobc.Activate", true);
                }

                value = String.valueOf(SQL.getInfoString("player_option_autobc", "Activate", "" + p.getUniqueId() + ""));
                SQL.set("player_option_autobc", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_autobc.Activate")) {
                    ConfigPlayerGet.writeBoolean(uuid, "player_option_autobc.Activate", true);
                }

                if (ConfigPlayerGet.getFile(uuid).getBoolean("player_option_autobc.Activate")) {
                    value = "TRUE";
                } else {
                    value = "FALSE";
                }

                SQL.insertData("player, player_UUID, Activate",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_autobc");
            }
        }

        return value;
    }
    
    /*
     * Number of connection
     */
    // > Save YAML/SQL
    public static void SaveSQLNumberofConnections(Player p, Integer inte) {
        String uuid = p.getUniqueId().toString();

        ConfigPlayerGet.writeInt(uuid, "player_option_number_connections.number", inte);

        if (!Main.getInstance().getSql().useyamllistplayer) {
            if (!SQL.tableExists("player_option_number_connections")) {
                SQL.createTable("player_option_number_connections", "player TEXT, player_UUID TEXT, number INT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_number_connections")) {
                SQL.set("player_option_number_connections", "number", "" + inte + "", "player_UUID", "" + p.getUniqueId() + "");
                SQL.set("player_option_number_connections", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                SQL.insertData("player, player_UUID, number",
                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + inte + "' ", "player_option_number_connections");
            }
        }
    }

    // > Get the actual value
    public static Integer getNumberOfConnection(Player p) {
        Integer value;
        String uuid = p.getUniqueId().toString();

        if (Main.getInstance().getSql().useyamllistplayer) {
            if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_number_connections.number")) {
                ConfigPlayerGet.writeInt(uuid, "player_option_number_connections.number", 0);
            }

            value = ConfigPlayerGet.getFile(uuid).getInt("player_option_number_connections.number");
        } else {
            if (!SQL.tableExists("player_option_number_connections")) {
                SQL.createTable("player_option_number_connections", "player TEXT, player_UUID TEXT, number INT");
            }

            if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_number_connections")) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_number_connections.number")) {
                    ConfigPlayerGet.writeInt(uuid, "player_option_number_connections.number", 0);
                }
                
                value = SQL.getInfoInt("player_option_number_connections", "number", "" + p.getUniqueId() + "");
                SQL.set("player_option_number_connections", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
            } else {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_option_number_connections.number")) {
                    ConfigPlayerGet.writeInt(uuid, "player_option_number_connections.number", 0);
                }

                value = ConfigPlayerGet.getFile(uuid).getInt("player_option_number_connections.number");
                
                SQL.insertData("player, player_UUID, number",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_number_connections");
            }
        }

        return value;
    }
}