package fr.dianox.hawn.utility.scoreboard;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class WhenPluginUpdateTextEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private UUID playerID;
    private String text;

    public WhenPluginUpdateTextEvent(UUID playerID, String text) {
        this.playerID  = playerID;
        this.text = text;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static HandlerList getHandlersList() {
        return handlers;
    }

    public UUID getPlayerID() {
    	return playerID;
    }
    
    public void setPlayerID(UUID playerID) {
    	this.playerID = playerID;
    }
    
    public String getText() {
        return text;
    }

    public String setText(String text) {
        this.text = text;
        return text;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}