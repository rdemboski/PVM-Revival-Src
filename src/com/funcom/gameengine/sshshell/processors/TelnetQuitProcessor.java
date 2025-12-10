/*    */ package com.funcom.gameengine.sshshell.processors;
/*    */ 
/*    */ import com.funcom.gameengine.sshshell.TelnetCommandsExecutor;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TelnetQuitProcessor
/*    */   extends TelnetCommandProcessor
/*    */ {
/*    */   public TelnetQuitProcessor(String[] message, SelectionKey selectedKey, TelnetCommandsExecutor serverCommandQueue, Logger LOGGER) {
/* 15 */     super(message, selectedKey, serverCommandQueue, LOGGER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process() {
/*    */     try {
/* 21 */       ((SocketChannel)this.selectedKey.channel()).socket().close();
/* 22 */     } catch (IOException e1) {
/* 23 */       e1.printStackTrace();
/*    */     } 
/* 25 */     this.selectedKey.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\processors\TelnetQuitProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */