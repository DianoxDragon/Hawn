package fr.dianox.hawn.utility.load;

import fr.dianox.hawn.utility.world.BasicEventsPW;
import fr.dianox.hawn.utility.world.ChangeWorldPW;
import fr.dianox.hawn.utility.world.CjiPW;
import fr.dianox.hawn.utility.world.CommandsPW;
import fr.dianox.hawn.utility.world.CosmeticsPW;
import fr.dianox.hawn.utility.world.OnJoinPW;
import fr.dianox.hawn.utility.world.OnQuitPW;
import fr.dianox.hawn.utility.world.OtherFeaturesPW;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import fr.dianox.hawn.utility.world.ProtectionPW;
import fr.dianox.hawn.utility.world.WorldPW;

public class WorldList {
	
	public static void clearworldlist() {
		OnJoinPW.motd_world.clear();
		OnJoinPW.jm_world.clear();
		OnQuitPW.qm_world.clear();
		BasicEventsPW.voidtp_world.clear();
		BasicEventsPW.gm_world.clear();
		ProtectionPW.worlds_c_break.clear();
		ProtectionPW.worlds_c_place.clear();
		BasicEventsPW.kgm_world.clear();
		OnJoinPW.worlds_Food.clear();
		OnJoinPW.worlds_Health.clear();
		BasicEventsPW.worlds_kFood.clear();
		BasicEventsPW.worlds_kANTIDAMAGE.clear();
		ProtectionPW.worlds_HagingBreakByEntity.clear();
		ProtectionPW.worlds_PlayerInteractEntity_ItemFrame.clear();
		OtherFeaturesPW.worlds_ColorSign.clear();
		WorldPW.worlds_LightningStrike.clear();
		WorldPW.worlds_ThunderChange.clear();
		WorldPW.worlds_weather.clear();
		WorldPW.worlds_burn_block.clear();
		PlayerEventsPW.worlds_item_drop.clear();
		PlayerEventsPW.worlds_item_move.clear();
		PlayerEventsPW.worlds_item_pickup.clear();
		PlayerEventsPW.worlds_item_damageitem.clear();
		WorldPW.worlds_explosions.clear();
		WorldPW.worlds_LeaveDecay.clear();
		CosmeticsPW.worlds_firework.clear();
		WorldPW.worlds_spawning_mob_animals.clear();
		PlayerEventsPW.worlds_death_message.clear();
		PlayerEventsPW.worlds_respawn.clear();
		PlayerEventsPW.worlds_force_selected_slot.clear();
		WorldPW.worlds_BlockFade.clear();
		PlayerEventsPW.worlds_item_clearondrop.clear();
		OnJoinPW.worlds_inventory.clear();
		OnJoinPW.worlds_clear_chat.clear();
		OnJoinPW.worlds_XP_Exp.clear();
		OnJoinPW.worlds_XP_Lvl.clear();
		OnJoinPW.worlds_sounds_join.clear();
		CosmeticsPW.worlds_jumppads.clear();
		CommandsPW.worlds_JoinCommands_New.clear();
		CommandsPW.worlds_JoinCommands_No_New.clear();
		CommandsPW.worlds_QuitCommands_Console.clear();
		OnJoinPW.worlds_fly.clear();
		PlayerEventsPW.worlds_fun_doublejump.clear();
		OnJoinPW.worlds_join_title.clear();
		OnJoinPW.worlds_first_join_title.clear();
		OnJoinPW.world_speed_on_join.clear();
		OnJoinPW.worlds_first_join_ab.clear();
		OnJoinPW.worlds_join_ab.clear();
		BasicEventsPW.worlds_autobroadcast.clear();
		OnJoinPW.world_pe_blindness.clear();
		OnJoinPW.world_pe_jump.clear();
		ChangeWorldPW.world_change_keepfly.clear();
		ChangeWorldPW.worlds_GM_OnChangeWorld.clear();
		CjiPW.worlds_po_playervisibility_item.clear();
		PlayerEventsPW.worlds_playeroption_join.clear();
		BasicEventsPW.worlds_autobroadcast_ab.clear();
		BasicEventsPW.worlds_autobroadcast_title.clear();
		ChangeWorldPW.worlds_po.clear();
		CosmeticsPW.worlds_ls.clear();
		PlayerEventsPW.worlds_respawncji.clear();
		ProtectionPW.worlds_PlayerInteract_Items_Blocks.clear();
		ChangeWorldPW.worlds_commands.clear();
		CjiPW.worlds_general.clear();
		PlayerEventsPW.worlds_block_off_hand.clear();
		ProtectionPW.worlds_buckets.clear();
		WorldPW.worlds_shears.clear();
		PlayerEventsPW.worlds_block_mount.clear();
	}
	
	public static void setworldlist() {
		OnJoinPW.setGetWorldforJoinMessage();
		OnQuitPW.setGetWorldforQuitMessage();
		OnJoinPW.setWGetWorldforMOTD();
		BasicEventsPW.setWGetWorldforGM();
		BasicEventsPW.setWGetWorldforKGM();
		BasicEventsPW.setWGetWorldforVOIDTP();
		ProtectionPW.setWGetWorldProtectionBreak();
		ProtectionPW.setWGetWorldProtectionPlace();
		OnJoinPW.setWGetWorldFood();
		OnJoinPW.setWGetWorldHealth();
		BasicEventsPW.setWGetWorldkFood();
		BasicEventsPW.setWGetWorldANTIDAMAGE();
		ProtectionPW.setWGetWorldProtectionHagingBreakByEntity();
		ProtectionPW.setWGetWorldProtectionPlayerInteractEntityItemFrame();
		OtherFeaturesPW.setWGetWorldEventColorSign();
		WorldPW.setWGetWorldServerDisableLightningStrike();
		WorldPW.setWGetWorldServerDisableThunderChange();
		WorldPW.setWGetWorldServerDisableWeather();
		WorldPW.setWGetWorldServerDisableBurnBlock();
		PlayerEventsPW.setWGetWorldItemDrop();
		PlayerEventsPW.setWGetWorldItemPickUP();
		PlayerEventsPW.setWGetWorldMoveItem();
		PlayerEventsPW.setWGetWorldItemDamage();
		WorldPW.setWGetWorldServerDisableExplosion();
		WorldPW.setWGetWorldServerDisableLeaveDecay();
		CosmeticsPW.setWGetWorldFirework();
		WorldPW.setWGetWorldServerDisableFireSpread();
		WorldPW.setWGetWorldServerDisableSpawningMobAnimals();
		PlayerEventsPW.setWGetWorldServerDisableDeathMessage();
		PlayerEventsPW.setWRespawnEvent();
		PlayerEventsPW.setWGetWorldForceSelectedJoin();
		WorldPW.setWGetWorldServerDisableblockFade();
		PlayerEventsPW.setWGetWorldClearDropOnDeath();
		OnJoinPW.setWGetWorldInventory();
		OnJoinPW.setWGetWorldClearChat();
		OnJoinPW.setWGetWorldResetExperience();
		OnJoinPW.setWGetWorldResetLevel();
		OnJoinPW.setWGetWorldSoundJoin();
		CosmeticsPW.setWGetWorldJumpPads();
		CommandsPW.setWGetWorldJoinCommandNew();
		CommandsPW.setWGetWorldJoinCommandNoNew();
		CommandsPW.setWGetWorldQuitCommandConsole();
		OnJoinPW.setWGetWorldflyoj();
		PlayerEventsPW.setWGetFunDoubleJump();
		OnJoinPW.setWGetWorldJoinTitle();
		OnJoinPW.setWGetWorldFirstJoinTitle();
		OnJoinPW.setWSOJ();
		OnJoinPW.setWGetWorldFirstJoinab();
		OnJoinPW.setWGetWorldJoinab();
		BasicEventsPW.setWGetWorldautobroadcast();
		OnJoinPW.setWGetWorldblindess();
		OnJoinPW.setWGetWorldjump();
		ChangeWorldPW.setWKEEPFLY();
		ChangeWorldPW.setWGetWorldGamemodeChangeWorld();
		CjiPW.setItemPlayerVisibility();
		PlayerEventsPW.setPlayerOptionJoin();
		BasicEventsPW.setWGetWorldautobroadcast_ab();
		BasicEventsPW.setWGetWorldautobroadcast_title();
		ChangeWorldPW.setWPO();
		CosmeticsPW.setWGetWorldls();
		PlayerEventsPW.setWGetWorldRCJI();
		ProtectionPW.setWGetWorldProtectionPlayerInteractItemsBlocks();
		ChangeWorldPW.setCommands();
		CjiPW.setItemPlayerGeneral();
		PlayerEventsPW.setWBlockOffHand();
		ProtectionPW.setWworldBukets();
		WorldPW.setWShears();
		PlayerEventsPW.setWGetMount();
	}

}