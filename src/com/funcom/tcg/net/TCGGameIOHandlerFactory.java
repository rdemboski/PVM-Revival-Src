/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ import com.funcom.server.common.CryptoMessageFactory;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.IOBufferManager;
/*    */ import com.funcom.server.common.LocalGameClient;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageFactory;
/*    */ import com.funcom.server.common.NetworkGameIOHandler;
/*    */ import com.funcom.tcg.net.message.TCGMessageUtils;
/*    */ import java.net.SocketAddress;
/*    */ import java.security.PublicKey;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGGameIOHandlerFactory
/*    */ {
/*    */   public static GameIOHandler createIOHandler(LocalGameClient localGameClient, SocketAddress serverAddress, PublicKey publicKey) {
/* 20 */     NetworkGameIOHandler handler = new NetworkGameIOHandler();
/*    */     
/* 22 */     handler.setBufferManager(new IOBufferManager());
/*    */     
/* 24 */     List<Class<? extends Message>> parserClasses = TCGMessageUtils.getMessageClasses();
/*    */     
/* 26 */     handler.setMessageFactory((MessageFactory)new CryptoMessageFactory(new IOBufferManager(), 1024, parserClasses, CryptoMessageFactory.CryptoRole.CLIENT, null, publicKey));
/*    */ 
/*    */     
/* 29 */     handler.setRemoteAddress(serverAddress);
/* 30 */     handler.setLocalGameClient(localGameClient);
/*    */     
/* 32 */     return (GameIOHandler)handler;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\TCGGameIOHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */