package com.funcom.commons.chat;

import com.funcom.server.common.Message;
import com.funcom.tcg.net2.message.chat.ChatMessage;
import java.nio.channels.SelectionKey;
import java.util.Set;
import org.apache.log4j.Level;

public interface ChatServer {
  void registerUser(ChatUser paramChatUser, SelectionKey paramSelectionKey);
  
  void chat(ChatMessage paramChatMessage);
  
  void sendMessage(ChatUser paramChatUser, Message paramMessage);
  
  void sendMessage(SelectionKey paramSelectionKey, Message paramMessage);
  
  void log(Level paramLevel, String paramString);
  
  ChatChannel channelByName(String paramString);
  
  ChatChannel channelById(int paramInt);
  
  ChatUser userByKey(SelectionKey paramSelectionKey);
  
  ChatUser userById(int paramInt);
  
  Set<ChatChannel> channels();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChatServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */