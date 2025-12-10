/*    */ package com.funcom.gameengine.sshshell.processors;
/*    */ 
/*    */ import com.funcom.gameengine.sshshell.TelnetCommandsExecutor;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TelnetMessageProcessor
/*    */   extends TelnetCommandProcessor
/*    */ {
/*    */   public TelnetMessageProcessor(String[] message, SelectionKey selectedKey, TelnetCommandsExecutor serverCommandQueue, Logger LOGGER) {
/* 13 */     super(message, selectedKey, serverCommandQueue, LOGGER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process() {
/* 18 */     if (this.message.length == 1) {
/* 19 */       this.LOGGER.error("Invalid command form, 'message' argument missing.");
/*    */       
/*    */       return;
/*    */     } 
/* 23 */     StringBuilder builder = new StringBuilder();
/* 24 */     for (int i = 1; i < this.message.length; i++)
/* 25 */       builder.append(this.message[i]).append(" "); 
/* 26 */     String serverWideMessage = builder.toString().trim();
/*    */     
/* 28 */     this.LOGGER.debug("Server-wide message requested: " + serverWideMessage);
/* 29 */     this.serverCommandQueue.sendServerNotice(serverWideMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\processors\TelnetMessageProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */