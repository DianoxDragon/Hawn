package fr.Dianox.Hawn.Utility;

import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.SQL;
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;

public class PlayerOptionSQLClass {
	
	/*
	 * Player Option Jumpboost
	 */
	// > Save YAML/SQL
	public static void SaveSQLPOJumpBoost(Player p, String boolea) {
		PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		if (boolea.equalsIgnoreCase("FALSE")) {
			PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".Activate", false);
		} else {
			PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".Activate", true);
		}

		PlayerConfig.saveConfigFile();
		
		if (!Main.useyamllistplayer) {
			if (!SQL.tableExists("player_option_jumpboost")) {
				SQL.createTable("player_option_jumpboost", "player TEXT, player_UUID TEXT, Activate TEXT");
			}

			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_jumpboost")) {
				SQL.set("player_option_jumpboost", "Activate", ""+boolea+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_jumpboost", "player", ""+p.getName()+"", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				SQL.insertData("player, player_UUID, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_jumpboost");
			}
		}
	}
	
	// > Get the actual value
	public static String GetSQLPOJumpBoost(Player p) {
		String value = "";

		PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		PlayerConfig.saveConfigFile();

		if (Main.useyamllistplayer) {
			if (!PlayerConfig.getConfig().isSet("player_option_jumpboost."+p.getUniqueId()+".player_name")) {
				PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".Activate", Boolean.valueOf(false));

		        PlayerConfig.saveConfigFile();
			}

			if (PlayerConfig.getConfig().getBoolean("player_option_jumpboost."+p.getUniqueId()+".Activate"))  {
				value = "TRUE";
			} else {
				value = "FALSE";
			}
		} else {
			if (!SQL.tableExists("player_option_jumpboost")) {
				SQL.createTable("player_option_jumpboost", "player TEXT, player_UUID TEXT, Activate TEXT");
			}

			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_jumpboost")) {
				if (!PlayerConfig.getConfig().isSet("player_option_jumpboost."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".Activate", Boolean.valueOf(false));

		            PlayerConfig.saveConfigFile();
				}

				value = String.valueOf(SQL.getInfoString("player_option_jumpboost", "Activate", "" + p.getUniqueId() + ""));
				SQL.set("player_option_jumpboost", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				if (!PlayerConfig.getConfig().isSet("player_option_jumpboost."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_jumpboost."+p.getUniqueId()+".Activate", Boolean.valueOf(false));

			        PlayerConfig.saveConfigFile();
				}

				if (PlayerConfig.getConfig().getBoolean("player_option_jumpboost."+p.getUniqueId()+".Activate"))  {
					value = "TRUE";
					SQL.insertData("player, player_UUID, Activate",
		                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_jumpboost");
				} else {
					value = "FALSE";
					SQL.insertData("player, player_UUID, Activate",
		                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_jumpboost");
				}
			}
		}

		return value;
	}
	
	/*
	 * Player option, keep scoreboard
	 */
	public static String getYmlaMysqlsb(Player p, String option) {
		String value = "";

		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		PlayerConfig.saveConfigFile();

		if (Main.useyamllistplayer) {
			if (option.equalsIgnoreCase("scoreboard")) {
				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));

		            PlayerConfig.saveConfigFile();
		        }

				value = PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard");
			} else if (option.equalsIgnoreCase("keepsb")) {

				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));

		            PlayerConfig.saveConfigFile();
				}

				if (PlayerConfig.getConfig().getBoolean("player_option_keep_sb."+p.getUniqueId()+".Activate"))  {
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
				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));

		            PlayerConfig.saveConfigFile();
				}

				if (option.equalsIgnoreCase("scoreboard")) {
					value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Scoreboard", "" + p.getUniqueId() + ""));
				} else if (option.equalsIgnoreCase("keepsb")) {
					value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Activate", "" + p.getUniqueId() + ""));
				}
				SQL.set("player_option_keep_sb", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				if (option.equalsIgnoreCase("scoreboard")) {
					if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));

			            PlayerConfig.saveConfigFile();
					}

					if (PlayerConfig.getConfig().getBoolean("player_option_keep_sb."+p.getUniqueId()+".Activate"))  {
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', 'TRUE', '"+PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_keep_sb");
					} else {
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', 'FALSE', '"+PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_keep_sb");
					}
					value = PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard");
				} else if (option.equalsIgnoreCase("keepsb")) {
					if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));

			            PlayerConfig.saveConfigFile();
					}

					if (PlayerConfig.getConfig().getBoolean("player_option_keep_sb."+p.getUniqueId()+".Activate"))  {
						value = "TRUE";
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '"+PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_keep_sb");
					} else {
						value = "FALSE";
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '"+PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_keep_sb");
					}
				}
			}
		}

		return value;
	}

	public static void saveSBmysqlyaml(Player p, String sb, String boolea) {
		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		if (boolea.equalsIgnoreCase("FALSE")) {
			PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
		} else {
			PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
		}
		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(sb));

		PlayerConfig.saveConfigFile();

		if (!Main.useyamllistplayer) {
			if (!SQL.tableExists("player_option_keep_sb")) {
				SQL.createTable("player_option_keep_sb", "player TEXT, player_UUID TEXT, Activate TEXT, Scoreboard TEXT");
			}

			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_keep_sb")) {
				SQL.set("player_option_keep_sb", "Activate", ""+boolea+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_keep_sb", "Scoreboard", ""+sb+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_keep_sb", "player", ""+p.getName()+"", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				SQL.insertData("player, player_UUID, Activate, Scoreboard",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "', '"+sb+"' ", "player_option_keep_sb");
			}
		}
	}

}
