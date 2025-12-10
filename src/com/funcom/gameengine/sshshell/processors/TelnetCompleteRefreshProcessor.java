/*    */ package com.funcom.gameengine.sshshell.processors;
/*    */ 
/*    */ import com.funcom.gameengine.sshshell.TelnetCommandsExecutor;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TelnetCompleteRefreshProcessor
/*    */   extends TelnetCommandProcessor
/*    */ {
/*    */   public TelnetCompleteRefreshProcessor(String[] message, SelectionKey selectedKey, TelnetCommandsExecutor serverCommandQueue, Logger LOGGER) {
/* 13 */     super(message, selectedKey, serverCommandQueue, LOGGER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process() {
/* 18 */     if (this.message.length == 1) {
/* 19 */       this.LOGGER.error("Invalid command form, 'id' argument missing.");
/*    */       
/*    */       return;
/*    */     } 
/*    */     try {
/* 24 */       int id = Integer.parseInt(this.message[1].trim());
/* 25 */       this.LOGGER.debug("Complete refresh player requested, ID: " + this.message[1]);
/* 26 */       this.serverCommandQueue.completeRefreshRequest(id);
/* 27 */     } catch (NumberFormatException e) {
/* 28 */       this.LOGGER.error("Invalid command form, argument '" + this.message[1] + "' unparseable as integer.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\processors\TelnetCompleteRefreshProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */