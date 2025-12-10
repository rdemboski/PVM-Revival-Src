/*   */ package com.funcom.server.common;
/*   */ 
/*   */ public interface LocalGameClient
/*   */ {
/*   */   void notifyDisconnected(GameIOHandler paramGameIOHandler, DisconnectReason paramDisconnectReason, boolean paramBoolean);
/*   */   
/*   */   public enum DisconnectReason {
/* 8 */     NETWORK_PROBLEM, LOGIN_FAILURE, LOGGED_IN_FROM_ANOTHER_CLIENT, SERVER_SHUTDOWN, CLIENT_QUIT;
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\LocalGameClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */