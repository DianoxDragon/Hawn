package fr.dianox.hawn.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.HelpCommandConfig;

public class HelpCommand extends BukkitCommand {
		
	public HelpCommand(String name) {
		super(name);
		this.description = "Help";
        this.usageMessage = "/help";
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("§cDo /bukkit:help");
			return true;
		}
		
		
		
		Player p = (Player) sender;
		
				if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Enable")) {
					if ((args.length == 0)) {
						if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
							if (!p.hasPermission("hawn.command.help")) {
								String Permission = "hawn.command.help";
								MessageUtils.MessageNoPermission(p, Permission);
								return true;
							}
							
							if (HelpCommandConfig.getConfig().getString("Help-Command.Categories.default").contentEquals("CHANGE ME")) {
								p.sendMessage("You have to change the default help on Help-Command.Categories.default on Commands/Help.yml");
							} else {
								String defaultCHelp = HelpCommandConfig.getConfig().getString("Help-Command.Categories.default");
								
								if (!p.hasPermission("hawn.command.help."+defaultCHelp)) {
									String Permission = "hawn.command.help."+defaultCHelp;
									MessageUtils.MessageNoPermission(p, Permission);
									return true;
								}
								
								for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+".1")) {
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
							}
							
						} else {
							if (HelpCommandConfig.getConfig().getString("Help-Command.Categories.default").contentEquals("CHANGE ME")) {
								p.sendMessage("You have to change the default help on Help-Command.Categories.default on Commands/Help.yml");
							} else {
								String defaultCHelp = HelpCommandConfig.getConfig().getString("Help-Command.Categories.default");
								
								for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+".1")) {
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
							}
						}
					} else if ((args.length == 1)) {
						try {
							int i = Integer.parseInt(args[0]);
							
							String defaultCHelp = HelpCommandConfig.getConfig().getString("Help-Command.Categories.default");
							
							if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
								if (!p.hasPermission("hawn.command.help")) {
									String Permission = "hawn.command.help";
									MessageUtils.MessageNoPermission(p, Permission);
									return true;
								}
								if (!p.hasPermission("hawn.command.help."+defaultCHelp)) {
									String Permission = "hawn.command.help."+defaultCHelp;
									MessageUtils.MessageNoPermission(p, Permission);
									return true;
								}
								
								if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+defaultCHelp+"."+args[0])) {
									for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+"."+args[0])) {
										ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
									}
								} else {
									MessageUtils.NoPageFound(p);
								}
							} else {
								if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+defaultCHelp+"."+args[0])) {
									for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+"."+args[0])) {
										ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
									}
								} else {
									MessageUtils.NoPageFound(p);
								}
							}
						} catch (NumberFormatException e) {
							if (!args[0].equalsIgnoreCase("page")) {
								if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0])) {
									if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
										if (!p.hasPermission("hawn.command.help")) {
											String Permission = "hawn.command.help";
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										if (!p.hasPermission("hawn.command.help."+args[0])) {
											String Permission = "hawn.command.help."+args[0];
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+".1")) {
											ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
										}
									} else {
										for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+".1")) {
											ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
										}
									}
								} else {
									MessageUtils.UseNumber(p);
								}
							} else {
								p.sendMessage("§c/help page <number>");
							}
						}
					} else if ((args.length == 2)) {
						if (args[0].equalsIgnoreCase("page")) {
							try {
								int i = Integer.parseInt(args[1]);
								
								if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
									if (!p.hasPermission("hawn.command.help")) {
										String Permission = "hawn.command.help";
										MessageUtils.MessageNoPermission(p, Permission);
										return true;
									}
									
									if (HelpCommandConfig.getConfig().getString("Help-Command.Categories.default").contentEquals("CHANGE ME")) {
										p.sendMessage("You have to change the default help on Help-Command.Categories.default on Commands/Help.yml");
									} else {
										String defaultCHelp = HelpCommandConfig.getConfig().getString("Help-Command.Categories.default");
										
										if (!p.hasPermission("hawn.command.help."+defaultCHelp)) {
											String Permission = "hawn.command.help."+defaultCHelp;
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+defaultCHelp+"."+args[1])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+"."+args[1])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
									}
									
								} else {
									if (HelpCommandConfig.getConfig().getString("Help-Command.Categories.default").contentEquals("CHANGE ME")) {
										p.sendMessage("You have to change the default help on Help-Command.Categories.default on Commands/Help.yml");
									} else {
										String defaultCHelp = HelpCommandConfig.getConfig().getString("Help-Command.Categories.default");
										
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+defaultCHelp+"."+args[1])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+defaultCHelp+"."+args[1])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
									}
								}
								
							} catch (NumberFormatException e) {
								MessageUtils.UseNumber(p);
							}
						} else {
							try {
								int i = Integer.parseInt(args[1]);
								
								if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0])) {
									if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
										if (!p.hasPermission("hawn.command.help")) {
											String Permission = "hawn.command.help";
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										if (!p.hasPermission("hawn.command.help."+args[0])) {
											String Permission = "hawn.command.help."+args[0];
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0]+"."+args[1])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+"."+args[1])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
									} else {
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0]+"."+args[1])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+"."+args[1])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
									}
								} else {
									MessageUtils.NoCategory(p);
								}
							} catch (NumberFormatException e) {
								MessageUtils.UseNumber(p);
							}
						}
					} else if ((args.length == 3)) {
						if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0])) {
							if (args[1].equalsIgnoreCase("page")) {
								try {
									int i = Integer.parseInt(args[2]);
									
									if (HelpCommandConfig.getConfig().getBoolean("Help-Command.Use-Permissions")) {
										if (!p.hasPermission("hawn.command.help")) {
											String Permission = "hawn.command.help";
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										if (!p.hasPermission("hawn.command.help."+args[0])) {
											String Permission = "hawn.command.help."+args[0];
											MessageUtils.MessageNoPermission(p, Permission);
											return true;
										}
										
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0]+"."+args[2])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+"."+args[2])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
										
									} else {
										if (HelpCommandConfig.getConfig().isSet("Help-Command.Categories."+args[0]+"."+args[2])) {
											for (String msg: HelpCommandConfig.getConfig().getStringList("Help-Command.Categories."+args[0]+"."+args[2])) {
												ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
											}
										} else {
											MessageUtils.NoPageFound(p);
										}
									}
								} catch (NumberFormatException e) {
									MessageUtils.UseNumber(p);
								}
							} else {
								p.sendMessage("§c/help <category> page <number>");
							}
						} else {
							MessageUtils.NoCategory(p);
						}
					} else {
						p.sendMessage("§c/help");
					}
				}

		
		
		return false;
	}

}
