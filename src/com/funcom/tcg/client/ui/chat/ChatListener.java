package com.funcom.tcg.client.ui.chat;

public interface ChatListener {
  void newMessageRecieved(int paramInt, String paramString, ChatNetworkController.MessageFrom paramMessageFrom);
  
  boolean searchPlayerRequest(String paramString);
  
  boolean searchPlayerToTellRequest(String paramString1, String paramString2, String paramString3);
  
  void sendFriendRequest(int paramInt);
  
  void sendFriendRemove(int paramInt);
  
  void acceptFriendResponse(int paramInt);
  
  String getClientNameFromId(long paramLong);
  
  void removeFriend(int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */