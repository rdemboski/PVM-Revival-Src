package com.funcom.gameengine.conanchat.packets2;

public interface ConanChatMessageType {
  public static final short ADM_ACCOUNT_CREATE_NEW = 1002;
  
  public static final short ADM_ACCOUNT_DELETE = 1001;
  
  public static final short ADM_ACCOUNT_DISABLE = 1004;
  
  public static final short ADM_ACCOUNT_ENABLE = 1005;
  
  public static final short ADM_ACCOUNT_NAME_SET = 1008;
  
  public static final short ADM_ACCOUNT_SET_COOKIE = 1009;
  
  public static final short ADM_MESSAGE_VICINITY = 1010;
  
  public static final short ADM_MESSAGE_ANON_VICINITY = 1011;
  
  public static final short ADM_GROUP_CREATE = 1020;
  
  public static final short ADM_GROUP_DELETE = 1021;
  
  public static final short ADM_GROUP_CREATE_NAMED = 1022;
  
  public static final short ADM_GROUP_JOIN = 1024;
  
  public static final short ADM_GROUP_PART = 1025;
  
  public static final short ADM_GROUP_ANON_MESSAGE = 67;
  
  public static final short ADM_MESSAGE_BROADCAST_SYSTEM = 1030;
  
  public static final short ADM_SHUTDOWN_SERVER = 1300;
  
  public static final short LOGIN_RESPONSE = 0;
  
  public static final short LOGIN_RESPONSE_NICK = 1;
  
  public static final short NAME_LOOKUP = 21;
  
  public static final short MESSAGE_PRIVATE = 30;
  
  public static final short BUDDY_AD = 40;
  
  public static final short BUDDY_REM = 41;
  
  public static final short ONLINESTATUS_SET = 42;
  
  public static final short GROUP_DATA_SET = 64;
  
  public static final short GROUP_MESSAGE = 65;
  
  public static final short GROUP_CLIENTMODE_SET = 66;
  
  public static final short PING = 100;
  
  public static final short FORWARD = 110;
  
  public static final short CC = 120;
  
  public static final short LOGIN_RESULT_OK = 5;
  
  public static final short LOGIN_FAILURE = 6;
  
  public static final short CLIENT_UNKNOWN = 10;
  
  public static final short CLIENT_NAME = 20;
  
  public static final short NAME_LOOKUP_RESULT = 21;
  
  public static final short MESSAGE_VICINITY = 34;
  
  public static final short MESSAGE_ANON_VICINITY = 35;
  
  public static final short MESSAGE_SYSTEM = 36;
  
  public static final short MESSAGE_SYSTEM_LOCAL = 37;
  
  public static final short BUDDY_ADDED = 40;
  
  public static final short BUDDY_REMOVED = 41;
  
  public static final short GROUP_JOIN = 60;
  
  public static final short GROUP_PART = 61;
  
  public static final short GROUP_ANON_MESSAGE = 67;
  
  public static final short PONG = 100;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\ConanChatMessageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */