package com.funcom.gameengine.sshshell;

public interface TelnetCommandsExecutor {
  void refreshRequest(int paramInt);
  
  void completeRefreshRequest(int paramInt);
  
  void forceSaveRequest(int paramInt);
  
  void sendServerNotice(String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\TelnetCommandsExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */