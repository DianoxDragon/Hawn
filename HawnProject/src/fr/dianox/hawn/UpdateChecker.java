package fr.Dianox.Hawn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;

public class UpdateChecker {
	
	// Project number
	private int pj = 66907;
	// Check URL
	private URL cU;
	// New version
	private String nV = "";
	private JavaPlugin plugin;
	
	public static String new_number_version = "";

	public UpdateChecker(JavaPlugin plugin, int projectID) {
		this.plugin = plugin;
		this.nV = plugin.getDescription().getVersion();
		this.pj = projectID;
		
		try {
			this.cU = new URL("https://api.spigotmc.org/legacy/update.php?resource="+projectID);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public int getProjectID() {
		return this.pj;
	}
	
	public JavaPlugin getPlugin() {
		return this.plugin;
	}
	  
	public String getLatestVersion() {
		return this.nV;
	}
	  
	public String getResourceURL() {
		return "https://www.spigotmc.org/resources/" + this.pj;
	}
	
	public boolean checkForUpdates() throws Exception {
		URLConnection con = this.cU.openConnection();
		this.nV = new BufferedReader(new InputStreamReader(con.getInputStream()))
				.readLine();
		new_number_version = this.nV;
		return !this.plugin.getDescription().getVersion().equals(this.nV);
	}
	
}
