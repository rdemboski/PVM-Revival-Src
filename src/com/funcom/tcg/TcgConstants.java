/*    */ package com.funcom.tcg;
/*    */ 
/*    */ import com.jme.math.Quaternion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcgConstants
/*    */ {
/*    */   public static final float MODEL_SCALE = 0.0025F;
/* 13 */   public static final Quaternion MODEL_ROTATION = new Quaternion();
/*    */   
/*    */   static {
/* 16 */     MODEL_ROTATION.fromAngles(-1.5707964F, 1.5707964F, 0.0F);
/*    */   }
/*    */   
/*    */   public static final int COINS_PER_LEVEL_RESPAWN = 1;
/*    */   public static final int PET_SELECTED_COUNT = 3;
/*    */   public static final int SKILLBAR_SLOTS = 5;
/*    */   public static final int BUYBACK_SLOTS = 10;
/*    */   public static final int DEFAULT_SPAWNPOINT_TIMER = 1;
/*    */   public static final String TRANSPARENT_TILE_BACK_IMAGE = "transparent_base";
/*    */   public static final String UNKNOWN_IMAGE_PATH = "gui/icons/icon_qm.png";
/*    */   public static final String EVENT_HOOKS_PROPERTIES = "gui_particles/eventhooks.props";
/*    */   public static final String DEFAULT_RPG_DIR = "rpg";
/*    */   public static final String CONF_TCG_PICKUP_CONVERSION = "tcg_pickup_conversion";
/*    */   public static final String CONF_TCG_LEVEL_UP_REWARD = "tcg_level_up_rewards";
/*    */   public static final String CONF_TCG_GIFT_BOX = "tcg_gift_box";
/*    */   public static final String CONF_TCG_GIFT_BOX_REWARDS = "tcg_gift_box_rewards";
/*    */   public static final String CONF_PVP_MAP_DESC = "pvp_map_desc";
/*    */   public static final String CONF_LEVEL_RANGES = "level_ranges";
/*    */   public static final String CONF_CHATMESSAGES = "configuration/chatmessages.xml";
/*    */   public static final String ITEMID_COIN = "coin";
/*    */   public static final long REFRESH_RATE_DYNAMIC_OBJECTS = 3000L;
/*    */   public static final int MIN_NICK_LENGTH = 6;
/*    */   public static final int MAX_NICK_LENGTH = 14;
/*    */   public static final int MIN_PASSWORD_LENGTH = 6;
/*    */   public static final int MAX_PASSWORD_LENGTH = 20;
/*    */   private static final int __MAX_LOCAL_PART_LENGTH = 64;
/*    */   private static final int __MAX_DOMAIN_PART_LENGTH = 112;
/*    */   public static final int MAX_EMAIL_LENGTH = 177;
/*    */   public static final int MAX_GAMECODE_LENGTH = 16;
/*    */   public static final String SOUND_CONFIG_FILE = "configuration/audio/audioconfig.properties";
/*    */   public static final String SOUNDKEY__PREFIX = "global.sound.";
/*    */   public static final String SOUNDKEY_CRIT = "global.sound.crit";
/*    */   public static final String SOUNDKEY_IMMUNE = "global.sound.immune";
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\TcgConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */