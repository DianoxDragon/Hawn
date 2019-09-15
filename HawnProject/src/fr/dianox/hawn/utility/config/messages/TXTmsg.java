package fr.dianox.hawn.utility.config.messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import fr.dianox.hawn.Main;

public class TXTmsg {
	
	public static void onCreateInfoMsgAdmin() {
	    File file = new File(Main.getInstance().getDataFolder(), "Messages/" + Main.LanguageType + "/info.txt");
	    if(!file.exists()){
	    	try {
	    		file.createNewFile();
	    	} catch (Exception e) {}
	    }
	    
	    PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void onWrite() {
		
		try (FileWriter fw = new FileWriter("plugins/Hawn/Messages/" + Main.LanguageType + "/info.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			
			out.println("------------------------------------");
			out.println("This folder is divided into 2 parts.");
			out.println("------------------------------------");
			out.println("------------------------------------");
			out.println("The first part concerns the administration");
			out.println("You will find all the messages");
			out.println("Which concerns the administration");
			out.println("Like for the command /hawn");
			out.println("------------------------------------");
			out.println("------------------------------------");
			out.println("The last folder concerns all messages");
			out.println("That a classic player can see");
			out.println("------------------------------------");
			
			
		} catch (IOException e) {}
	}
	
	// Others
	public static void onCreateInfoMsgAdminMain() {
	    File file = new File(Main.getInstance().getDataFolder(), "Messages/info.txt");
	    if(!file.exists()){
	    	try {
	    		file.createNewFile();
	    	} catch (Exception e) {}
	    }
	    
	    PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void onWriteMain() {
		
		try (FileWriter fw = new FileWriter("plugins/Hawn/Messages/info.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			
			out.println("------------------------------------");
			out.println("You can edit as many languages as you like.");
			out.println("------------------------------------");
			out.println("You can can create folders for new languages");
			out.println("By changing the language type");
			out.println("------------------------------------");

			
		} catch (IOException e) {}
	}

}
