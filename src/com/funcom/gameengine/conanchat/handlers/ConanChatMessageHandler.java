package com.funcom.gameengine.conanchat.handlers;

import com.funcom.gameengine.conanchat.packets2.ChatMessage;

public interface ConanChatMessageHandler {
  void handle(ChatMessage paramChatMessage) throws NotHandledException;
  
  void setNext(ConanChatMessageHandler paramConanChatMessageHandler);
  
  ConanChatMessageHandler getNext(ConanChatMessageHandler paramConanChatMessageHandler);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\handlers\ConanChatMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */