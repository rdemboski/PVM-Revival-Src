package com.funcom.tcg.net2.message.chat;

public interface ChatMessageType {
  public static final short __CHAT_ROOT = 100;
  
  @Deprecated
  public static final short REGISTER = 101;
  
  public static final short CHAT_MESSAGE = 102;
  
  public static final short USER_JOINS_CHANNEL = 103;
  
  public static final short USER_LEAVES_CHANNEL = 104;
  
  public static final short __REQRES_ROOT = 110;
  
  public static final short JOIN_CHANNEL_REQUEST = 111;
  
  public static final short JOIN_CHANNEL_RESPONSE = 112;
  
  public static final short LEAVE_CHANNEL_REQUEST = 113;
  
  public static final short LEAVE_CHANNEL_RESPONSE = 114;
  
  public static final short LIST_CHANNELS_REQUEST = 115;
  
  public static final short LIST_CHANNELS_RESPONSE = 116;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\ChatMessageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */