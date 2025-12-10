/*    */ package com.funcom.commons.chat.commands;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class Say extends ChatCommand {
/*    */   public String getCommand() {
/*  9 */     return "say";
/*    */   }
/*    */   
/*    */   public void execute(String[] cmdArgs) {
/* 13 */     if (getSelectedChannel() == null) {
/* 14 */       log(Level.WARN, "No channel selected.");
/*    */     } else {
/* 16 */       sendMessage((Message)new ChatMessage(getMyself().getId(), getSelectedChannel().getId(), cmdArgs[1]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\commands\Say.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */