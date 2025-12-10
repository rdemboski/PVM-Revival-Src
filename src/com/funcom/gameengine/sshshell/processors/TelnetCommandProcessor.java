/*    */ package com.funcom.gameengine.sshshell.processors;
/*    */ 
/*    */ import com.funcom.gameengine.sshshell.TelnetCommandsExecutor;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TelnetCommandProcessor
/*    */ {
/*    */   protected String[] message;
/*    */   protected SelectionKey selectedKey;
/*    */   protected TelnetCommandsExecutor serverCommandQueue;
/*    */   protected Logger LOGGER;
/*    */   
/*    */   public TelnetCommandProcessor(String[] message, SelectionKey selectedKey, TelnetCommandsExecutor serverCommandQueue, Logger LOGGER) {
/* 19 */     this.message = message;
/* 20 */     this.selectedKey = selectedKey;
/* 21 */     this.serverCommandQueue = serverCommandQueue;
/* 22 */     this.LOGGER = LOGGER;
/*    */   }
/*    */   
/*    */   public abstract void process();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\processors\TelnetCommandProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */