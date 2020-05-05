package fr.dianox.hawn.utility;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtility {
	
	@SuppressWarnings("deprecation")
	public ItemStack createGuiItemWL(String name, String mat) {
		
		mat = mat.toUpperCase();
		ItemStack i;
		
		if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
			i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
			ItemMeta iMeta = i.getItemMeta();
			iMeta.setDisplayName(name);
	        i.setItemMeta(iMeta);
	        return i;
		} else {
			 if (mat.startsWith("ORANGE") || mat.startsWith("MAGENTA") || mat.startsWith("LIGHT_BLUE") || mat.startsWith("YELLOW")
	    			|| mat.startsWith("LIME") || mat.startsWith("PINK") || mat.startsWith("GRAY") || mat.startsWith("LIGHT_GRAY")
	    			|| mat.startsWith("CYAN") || mat.startsWith("PURPLE") || mat.startsWith("BLUE") || mat.startsWith("BROWN")
	    			|| mat.startsWith("GREEN") || mat.startsWith("RED") || mat.startsWith("BLACK")) {
				
				if (mat.startsWith("ORANGE")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 1);
				} else if (mat.startsWith("MAGENTA")) {
	    			i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 2);
				} else if (mat.startsWith("LIGHT_BLUE")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 3);
				} else if (mat.startsWith("YELLOW")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 4);
				} else if (mat.startsWith("LIME")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 5);
	            } else if (mat.startsWith("PINK")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 6);
	            } else if (mat.startsWith("GRAY")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 7);
	            } else if (mat.startsWith("LIGHT_GRAY")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 8);
	            } else if (mat.startsWith("CYAN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 9);
	            } else if (mat.startsWith("PURPLE")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 10);
	            } else if (mat.startsWith("BLUE")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 11);
	            } else if (mat.startsWith("BROWN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 12);
	            } else if (mat.startsWith("GREEN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 13);
	            } else if (mat.startsWith("RED")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 14);
	            } else if (mat.startsWith("BLACK")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 15);
	            } else {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
	            }
				
				ItemMeta iMeta = i.getItemMeta();
				iMeta.setDisplayName(name);
				i.setItemMeta(iMeta);
				return i;
			} else {
				 i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
                 ItemMeta iMeta = i.getItemMeta();
                 iMeta.setDisplayName(name);
                 i.setItemMeta(iMeta);
                 return i;
			}
		}
    }
	
	@SuppressWarnings("deprecation")
	public ItemStack createGuiItem(String name, ArrayList < String > desc, String mat) {
		
		mat = mat.toUpperCase();
		ItemStack i;
		
		if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
			i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
			ItemMeta iMeta = i.getItemMeta();
			iMeta.setDisplayName(name);
	        i.setItemMeta(iMeta);
	        return i;
		} else {
			 if (mat.startsWith("ORANGE") || mat.startsWith("MAGENTA") || mat.startsWith("LIGHT_BLUE") || mat.startsWith("YELLOW")
	    			|| mat.startsWith("LIME") || mat.startsWith("PINK") || mat.startsWith("GRAY") || mat.startsWith("LIGHT_GRAY")
	    			|| mat.startsWith("CYAN") || mat.startsWith("PURPLE") || mat.startsWith("BLUE") || mat.startsWith("BROWN")
	    			|| mat.startsWith("GREEN") || mat.startsWith("RED") || mat.startsWith("BLACK")) {
				
				if (mat.startsWith("ORANGE")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 1);
				} else if (mat.startsWith("MAGENTA")) {
	    			i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 2);
				} else if (mat.startsWith("LIGHT_BLUE")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 3);
				} else if (mat.startsWith("YELLOW")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 4);
				} else if (mat.startsWith("LIME")) {
					i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 5);
	            } else if (mat.startsWith("PINK")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 6);
	            } else if (mat.startsWith("GRAY")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 7);
	            } else if (mat.startsWith("LIGHT_GRAY")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 8);
	            } else if (mat.startsWith("CYAN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 9);
	            } else if (mat.startsWith("PURPLE")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 10);
	            } else if (mat.startsWith("BLUE")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 11);
	            } else if (mat.startsWith("BROWN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 12);
	            } else if (mat.startsWith("GREEN")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 13);
	            } else if (mat.startsWith("RED")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 14);
	            } else if (mat.startsWith("BLACK")) {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, (short) 15);
	            } else {
	            	i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
	            }
				
				ItemMeta iMeta = i.getItemMeta();
				iMeta.setDisplayName(name);
				i.setItemMeta(iMeta);
				return i;
			} else {
				 i = new ItemStack(XMaterial.getMat(mat, "no info"), 1);
                 ItemMeta iMeta = i.getItemMeta();
                 iMeta.setDisplayName(name);
                 i.setItemMeta(iMeta);
                 return i;
			}
		}
    }

	@SuppressWarnings("deprecation")
	public ItemStack createGuiItemWLShortOldVersions(String name, String mat, short number) {
		
		mat = mat.toUpperCase();
		ItemStack i;
		
		i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, number);
		ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(name);
		i.setItemMeta(iMeta);
		return i;
    }
	
	@SuppressWarnings("deprecation")
	public ItemStack createGuiItemShortOldVersions(String name, String mat, ArrayList < String > desc, short number) {
		
		mat = mat.toUpperCase();
		ItemStack i;
		
		i = new ItemStack(XMaterial.getMat(mat, "no info"), 1, number);
		ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(desc);
		i.setItemMeta(iMeta);
		return i;
    }

}