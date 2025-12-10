package com.funcom.tcg.client.ui.friend;

import com.funcom.tcg.client.ui.chat.ChatListener;
import com.funcom.tcg.net.Friend;
import java.util.Map;

public interface FriendModel {
  String getLabelText();
  
  String getRequestFriendButtonText();
  
  String getAcceptButtonText();
  
  String getCancelButtonText();
  
  String getFriendListTitle();
  
  String getAcceptFriendLabelText();
  
  boolean searchPlayer(String paramString);
  
  void searchPlayerResult(int paramInt, String paramString);
  
  void sendFriendRequestToPlayer(Integer paramInteger);
  
  ChatListener getChatMessageListener();
  
  void addIdToFriendList(int paramInt, boolean paramBoolean);
  
  void blockPlayer(int paramInt);
  
  void addChangeListener(ChangeListener paramChangeListener);
  
  Map<Integer, Friend> getFriendsList();
  
  void removeFriend(int paramInt);
  
  void fireDfxFriendAdded(int paramInt);
  
  void fireModelChanged();
  
  public static interface ChangeListener {
    void friendsListChanged();
    
    void searchResult(int param1Int, String param1String);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\friend\FriendModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */