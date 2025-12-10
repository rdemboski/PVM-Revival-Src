package com.funcom.tcg.net.message;

import com.funcom.server.common.BaseMessageType;
import com.funcom.tcg.net2.message.chat.ChatMessageType;

public interface TCGMessageType extends BaseMessageType, ChatMessageType {
  public static final short REDIRECT = 0;
  
  public static final short SERVER_STATUS_REQUEST = 1;
  
  public static final short SERVER_STATUS_RESPONSE = 2;
  
  public static final short CREATE_CHARACTER_REQUEST = 3;
  
  public static final short CREATE_CHARACTER_RESPONSE = 4;
  
  public static final short LOGIN_REQUEST = 5;
  
  public static final short LOGIN_RESPONSE = 6;
  
  public static final short LOGIN_FINISHED = 7;
  
  public static final short RECONNECT = 8;
  
  public static final short POSITION_UPDATE = 9;
  
  public static final short POSITION_UPDATE_LIST = 10;
  
  public static final short CREATURE_CREATION_MESSAGE = 11;
  
  public static final short CREATURE_DELETION_MESSAGE = 12;
  
  public static final short STATUS_UPDATE_MESSAGE = 13;
  
  public static final short ACCOUNT_REGISTER_REQUEST = 14;
  
  public static final short ACCOUNT_REGISTER_RESPONSE = 15;
  
  public static final short ACCOUNT_SUBSCRIBE_TOKEN_REQUEST = 16;
  
  public static final short ACCOUNT_SUBSCRIBE_TOKEN_RESPONSE = 17;
  
  public static final short REQUEST_INVENTORY_SYNC = 20;
  
  public static final short LOOT_CREATION_MESSAGE = 21;
  
  public static final short VENDOR_CREATION_MESSAGE = 22;
  
  public static final short BUY_ITEM_FROM_VENDOR_MESSAGE = 23;
  
  public static final short SELL_ITEM_TO_VENDOR = 24;
  
  public static final short REMOVE_ITEM = 25;
  
  public static final short RELOAD_DATA = 26;
  
  public static final short MAP_CHANGED_MESSAGE = 27;
  
  public static final short PLAYER_CREATURE_CREATION_MESSAGE = 28;
  
  public static final short ERROR_MESSAGE = 29;
  
  public static final short CRIT_MESSAGE = 30;
  
  public static final short LOADING_COMPLETE = 31;
  
  public static final short INTERACTIBLE_PROP_ACTION_INVOKED_MESSAGE = 32;
  
  public static final short WALK_OVER_LOOT_CREATION_MESSAGE = 33;
  
  public static final short REQUEST_DYNAMIC_OBJECT_MESSAGE = 34;
  
  public static final short DYNAMIC_OBJECTS_LIST_MESSAGE = 35;
  
  public static final short NOTIFY_PET_SELECTED_AND_ACTIVATED_MESSAGE = 36;
  
  public static final short ACTIVATE_PORTAL_MESSAGE = 37;
  
  public static final short VERIFY_TOWN_PORTAL_ACTIVATION_MESSAGE = 38;
  
  public static final short QUEST_CREATION_MESSAGE = 39;
  
  public static final short QUEST_COMPLETED_MESSAGE = 40;
  
  public static final short ACCEPT_QUEST_MESSAGE = 41;
  
  public static final short QUEST_PICKED_REWARD_MESSAGE = 42;
  
  public static final short QUEST_OBJECTIVE_UPDATE_MESSAGE = 43;
  
  public static final short QUEST_OBJECTIVE_UPDATE_LIST_MESSAGE = 44;
  
  public static final short QUEST_UPDATE_REWARD_AMOUNT = 45;
  
  public static final short ACTIVATE_WAYPOINT_DESTINATION_PORTAL_MESSAGE = 46;
  
  public static final short ACTIVATE_WAYPOINT_MESSAGE = 47;
  
  public static final short UPDATE_WAYPOINT_DESTINATION_PORTAL_MESSAGE = 48;
  
  public static final short ACTIVATE_LOADING_SCREEN_MESSAGE = 49;
  
  public static final short CHECKPOINT_ACTIVATED_MESSAGE = 50;
  
  public static final short PROP_REMOVAL_MESSAGE = 51;
  
  public static final short UPDATE_CLIENT_TOWNPORTAL_MESSAGE = 52;
  
  public static final short UPDATE_FRIENDS_MESSAGE = 53;
  
  public static final short PICKUP_LOOT_CONVERTED_MESSAGE = 54;
  
  public static final short RECONNECT_TO_CHAT_MESSAGE = 55;
  
  public static final short TCG_CHAT_MESSAGE = 56;
  
  public static final short TCG_CHAT_REQUEST_FRIEND_MESSAGE = 57;
  
  public static final short TCG_CHAT_FRIEND_REQUEST_MESSAGE = 58;
  
  public static final short TCG_CHAT_FRIEND_RESPONSE_MESSAGE = 59;
  
  public static final short TCG_CHAT_FRIEND_ACCEPTED = 60;
  
  public static final short FRIENDS_MAP_MESSAGE = 61;
  
  public static final short TELEPORT_OBJECTS_LIST_MESSAGE = 62;
  
  public static final short INITIATE_PAUSE_MESSAGE = 63;
  
  public static final short CLIENT_PAUSED_MESSAGE = 64;
  
  public static final short PAUSE_REJECTED_MESSAGE = 65;
  
  public static final short UPDATE_TARGET_EFFECT_MESSAGE = 66;
  
  public static final short POINTER_POSITION_UPDATE = 67;
  
  public static final short CASTTIME_FEEDBACK = 68;
  
  public static final short DFX_SERVER_NOTICE = 69;
  
  public static final short SUBSCRIPTION_STATUS_UPDATE = 70;
  
  public static final short PLAYER_DATA_REFRESH = 71;
  
  public static final short QUEST_COMPLETED_LIST_MESSAGE = 72;
  
  public static final short RELOAD_QUEST_MESSAGE = 73;
  
  public static final short COMPLETE_TUTORIAL_QUEST_OBJECTIVE_MESSAGE = 74;
  
  public static final short SEARCH_PLAYER_BY_NICKNAME_MESSAGE_REQUEST = 75;
  
  public static final short SEARCH_PLAYER_BY_NICKNAME_MESSAGE_RESPONSE = 76;
  
  public static final short SEARCH_PLAYER_TO_TELL_MESSAGE_REQUEST = 77;
  
  public static final short SEARCH_PLAYER_TO_TELL_MESSAGE_RESPONSE = 78;
  
  public static final short START_TUTORIAL_MESSAGE = 79;
  
  public static final short APPLY_SANCTION_MESSAGE_REQUEST = 80;
  
  public static final short SEND_SANCTION_MESSAGE_RESPONSE = 81;
  
  public static final short INTERNAL_CHAT_MESSAGE = 82;
  
  public static final short ONLINE_NOTIFICATION_MESSAGE = 83;
  
  public static final short GRINDER_TELEPORT_MESSSAGE = 97;
  
  public static final short DEBUG_ATTACK_SHAPE_MESSAGE = 98;
  
  public static final short TEST_TIMED_POSITION_UPDATE_MESSAGE = 99;
  
  public static final short __RPG_MSG_ROOT = 200;
  
  public static final short RPG_CHARACTER_BUFFED_MESSAGE = 201;
  
  public static final short RPG_USE_ITEM_MESSAGE = 202;
  
  public static final short RPG_SYNCHRONIZE_INVENTORY_MESSAGE = 203;
  
  public static final short RPG_TRANSFER_ITEM_MESSAGE = 204;
  
  public static final short RPG_SYNCHRONIZE_INVENTORY_LIST_MESSAGE = 205;
  
  public static final short RPG_RESPAWN = 206;
  
  public static final short RPG_CHARACTER_DIED_MESSAGE = 207;
  
  public static final short RPG_COLLECTED_PET_LIST_REQUEST = 208;
  
  public static final short RPG_COLLECTED_PET_LIST_RESPONSE = 209;
  
  public static final short RPG_PET_SKILLS_REQUEST = 210;
  
  public static final short RPG_PET_SKILLS_RESPONSE = 211;
  
  public static final short RPG_PET_SELECTION_UPDATE = 212;
  
  public static final short RPG_PET_ACTIVE_UPDATE = 213;
  
  public static final short RPG_SELECTED_SKILLS_RECONFIGURED = 214;
  
  public static final short RPG_SKILL_LEARNED_REQUEST = 215;
  
  public static final short RPG_EQUIPMENT_CHANGED = 216;
  
  public static final short RPG_REQUEST_MONSTERS_RESISTANCE = 217;
  
  public static final short RPG_UPDATE_MONSTERS_RESISTANCE = 218;
  
  public static final short RPG_ARRANGE_ITEMS_IN_INVENTORY_MESSAGE = 219;
  
  public static final short RPG_AUTO_USE_ITEM = 220;
  
  public static final short RPG_REQUEST_QUEST = 221;
  
  public static final short RPG_REFRESH_QUEST_NPCS = 222;
  
  public static final short RPG_COMPLETE_QUEST = 223;
  
  public static final short RPG_PROJECTILE_UPDATE = 224;
  
  public static final short RPG_DFX_EXECUTE = 225;
  
  public static final short RPG_STAT_EFFECT = 226;
  
  public static final short RPG_DFX_KILL = 227;
  
  public static final short RPG_TARGETED_EFECT_UPDATE = 228;
  
  public static final short RPG_DFX_EXECUTE_LIST = 229;
  
  public static final short RPG_TRY_TO_USE_ITEM = 230;
  
  public static final short RPG_CONNECTED_WITH_MAGNETIC_LOOT = 231;
  
  public static final short RPG_ABANDON_QUEST = 232;
  
  public static final short RPG_PICKUP_LOOT = 233;
  
  public static final short RPG_GIFTBOX_OPEN = 234;
  
  public static final short RPG_GIFTBOX_UPDATE = 235;
  
  public static final short RPG_NEW_PET_COLLECTED_MESSAGE = 236;
  
  public static final short RPG_NEW_ITEM_COLLECTED_MESSAGE = 237;
  
  public static final short RPG_START_PET_TRIAL_MESSAGE = 238;
  
  public static final short RPG_FINISH_PET_TRIAL_MESSAGE = 239;
  
  public static final short RPG_IMMUNITY_EFFECT_MESSAGE = 240;
  
  public static final short RPG_DAILY_QUEST_NUMBER_UPDATE_MESSAGE = 241;
  
  public static final short RPG_TRAIN_PET_MESSAGE = 242;
  
  public static final short DEBUG_SQUARE_ATTACK_SHAPE_MESSAGE = 243;
  
  public static final short RPG_FACTION_CHANGED_MESSAGE = 244;
  
  public static final short DUEL_REQUEST_MESSAGE = 245;
  
  public static final short DUEL_REQUEST_CANCEL_MESSAGE = 246;
  
  public static final short DUEL_INVITATION_MESSAGE = 247;
  
  public static final short DUEL_RESPONSE_MESSAGE = 248;
  
  public static final short DUEL_REJECTION_MESSAGE = 249;
  
  public static final short DUEL_START_MESSAGE = 250;
  
  public static final short DUEL_FINISH_MESSAGE = 251;
  
  public static final short DUEL_CANCEL_MESSAGE = 252;
  
  public static final short CLAIM_QUEST_REWARDS_MESSAGE = 253;
  
  public static final short ACCESS_KEYS_MESSAGE = 254;
  
  public static final short JOIN_PVP_QUEUE_MESSAGE = 255;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TCGMessageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */