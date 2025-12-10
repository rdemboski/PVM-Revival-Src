package com.funcom.tcg.client.ui.chat;

import com.funcom.gameengine.conanchat.DefaultChatUser;

public interface ChatOutput {
  void sendMessage(int paramInt, String paramString);
  
  void sendFriendRequest(int paramInt);
  
  void sendFriendRemove(int paramInt);
  
  void sendFriendResponse(int paramInt);
  
  void addFriend(int paramInt);
  
  void removeFriend(int paramInt);
  
  DefaultChatUser getChatUser();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */