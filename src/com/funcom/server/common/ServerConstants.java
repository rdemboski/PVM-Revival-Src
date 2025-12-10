/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ServerConstants
/*     */ {
/*     */   public static final String SERVER_PROPERTIES = "server.properties";
/*     */   public static final int BUFFER_SIZE = 1024;
/*     */   public static final int MAX_USER_INPUT_BUFFER_SIZE = 25000;
/*  30 */   public static final long DISCONNECT_TIME_PERIOD = TimeUnit.SECONDS.toMillis(30L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static final long AUTHENTICATION_FAILED_TIME_PERIOD = TimeUnit.SECONDS.toMillis(2L);
/*     */ 
/*     */   
/*  40 */   public static final long MAX_CLIENT_LOADING_TIME = TimeUnit.MINUTES.toMillis(5L);
/*     */ 
/*     */   
/*     */   public static final long MAX_LEVEL = 45L;
/*     */ 
/*     */   
/*  46 */   public static final long MAX_CLIENT_NO_NETWORK_MSG_TIME = TimeUnit.MINUTES.toMillis(15L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final long LOGOUT_TIME_PERIOD = TimeUnit.SECONDS.toMillis(2L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALLOWED_OUTPUT_BUFFERS = 110;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double APPROX_POS_VIEW_WIDTH = 12.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double APPROX_POS_VIEW_HEIGHT = 16.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_CHARACTER_RADIUS = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_NPC_RADIUS = 24;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float COLLISION_RADIUS = 0.6F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float COLLISION_RADIUS_MAX = 4.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SPAWN_RADIUS = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SPAWN_MAX = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final long RECONNECTION_MINIMUM_DELAY = TimeUnit.SECONDS.toMillis(10L);
/*     */ 
/*     */   
/*     */   public static final int INTIMACY_ZONE_INTEGER = 2;
/*     */   
/*     */   public static final double INTIMACY_ZONE_DECIMAL = 0.5D;
/*     */   
/*     */   public static final int CHECK_RATE = 5;
/*     */   
/*     */   public static final String PASSWORD_DEFAULT_ALGORITHM = "MD5";
/*     */   
/*     */   public static final String EVENT_AI_FILES_DIR = "eventai";
/*     */   
/*     */   public static final int MAX_EQUIP_DOLL_SLOTS = 10;
/*     */   
/*     */   public static final int DEFAULT_START_LEVEL = 1;
/*     */   
/*     */   public static final String CHUNKLOADER_BASE_PATH = "map/";
/*     */ 
/*     */   
/*     */   public enum RpgConfigFile
/*     */   {
/* 131 */     ITEMS("items"),
/* 132 */     PETS("pets"),
/* 133 */     MONSTERS("monsters"),
/* 134 */     EFFECT_CREATOR("effect"),
/* 135 */     SOURCE_EFFECT_CREATOR("source_effect"),
/* 136 */     TARGET_EFFECT_CREATOR("target_effect"),
/* 137 */     STAT_MODIFIERS("stat_modifiers"),
/* 138 */     LEVEL_STATS("level_stats"),
/* 139 */     BUFF("buff"),
/* 140 */     DEBUFF("debuff"),
/* 141 */     SHAPES("shapes"),
/* 142 */     RECT_SHAPES("rect_shapes"),
/* 143 */     ELEMENTS("elements"),
/* 144 */     CONFIG("config"),
/* 145 */     LOOT_DESCRIPTION("lootDescriptions"),
/* 146 */     LOOT_MOB_TYPES("lootMobTypes"),
/* 147 */     GROUP_LOOT_DESCRIPTION("grouplootdescriptions"),
/* 148 */     MONSTER_GROUP_LOOT("monstergrouploot"),
/* 149 */     VENDOR_ITEMS("vendorItems"),
/* 150 */     VENDORS("vendors"),
/* 151 */     QUESTS("quests"),
/* 152 */     DEBUFF_CURE("debuffcure"),
/* 153 */     PROJECTILE_CREATOR("projectile"),
/* 154 */     QUEST_REWARDS("questrewards"),
/* 155 */     START_EQUIPMENT("startitems"),
/* 156 */     PROJECTILE_PATHS("projectilePaths"),
/* 157 */     PICKUP_ITEMS("pickup_items"),
/* 158 */     MOVEMENT_MANIPULATOR_CREATOR("movementmanipulators"),
/* 159 */     QUEST_OBJECTIVES("questobjectives"),
/* 160 */     TARGETED_EFFECTS("targeted_effect"),
/* 161 */     CHARGED_ABILITY("charged_ability"),
/* 162 */     WAYPOINT("waypoints"),
/* 163 */     WAYPOINT_DESTINATION_PORTALS("waypointDestinationPortals"),
/* 164 */     CHECKPOINT_DESCRIPTION("checkpoints"),
/* 165 */     SPAWN_EFFECTS("spawns"),
/* 166 */     PORTKEYS("portkeys"),
/* 167 */     CUSTOM_PORTALS("customPortals"),
/* 168 */     ITEM_SETS("sets"),
/* 169 */     PROJECTILE_REFLECTION("projectile_reflections"),
/* 170 */     REMOVE_INFINITE_BUFF_ABILITIES("removeInfiniteBuffAbilities"),
/* 171 */     GIVE_PET_ABILITIES("give_pet_abilities"),
/* 172 */     REGION_DESCRIPTIONS("regionDescriptions"),
/* 173 */     SPEACH("speach"),
/* 174 */     SPEACH_MAPPING("speachmapping"),
/* 175 */     ITEM_SET_MODIFIER("item_set_modifier"),
/* 176 */     ARCHTYPES("archtype"),
/* 177 */     PET_ARCHETYPES("pet.archetype"),
/* 178 */     EQUIPMENT("equipment"),
/* 179 */     PET_LEVEL_STATS("pet_level_stats"),
/* 180 */     CASTTIME("casttime");
/*     */     
/*     */     public static final String CONF_RGPLOADER_REGEXP_PATTERN = ".*?\\.(.*)\\..*";
/*     */     
/*     */     public final String fileType;
/*     */     
/*     */     RpgConfigFile(String fileType) {
/* 187 */       this.fileType = fileType;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   public static final WorldCoordinate SAFE_STARTPOINT = new WorldCoordinate(20, 40, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*     */ 
/*     */   
/*     */   public static final String NO_LOOT_DROP = "no-loot-drop";
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_DESPAWN_TIME = 300000;
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_DESPAWN_TIME_BOSS = 3000000;
/*     */   
/*     */   public static final long DEFAULT_INSTANCE_JANITOR_SLEEP_TIME = 1800000L;
/*     */   
/*     */   public static final long TOWN_PORTAL_EXPIRED_TIME = 1800000L;
/*     */   
/* 230 */   public static final Random RAND = new Random(System.nanoTime());
/*     */   public static final String BINARY_ROOT_MAP_PATH = "binary";
/*     */   public static final String ASCII_ROOT_MAP_PATH = "xml";
/*     */   public static final String PAUSE_DFX = "xml/dfx/player_paused.xml";
/*     */   public static final String KEY_ALGORITHM = "RSA";
/*     */   public static final int KEY_SIZE = 2048;
/*     */   public static final String PUBLIC_KEY_PATH = "configuration/public.key";
/*     */   public static final String PUBLIC_KEY_PATH_LR = "configuration/public.key";
/*     */   public static final String PRIVATE_KEY_FILE = "private.key";
/*     */   public static final String PRIVATE_KEY_PATH = "resources/dist/config/private.key";
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\ServerConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */