/*    */ package com.funcom.commons.chat.commands;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ListChannelsRequest;
/*    */ 
/*    */ public class ListChannels extends ChatCommand {
/*    */   public String getCommand() {
/*  8 */     return "list";
/*    */   }
/*    */   
/*    */   public void execute(String[] cmdArgs) {
/* 12 */     sendMessage((Message)new ListChannelsRequest(getMyself().getId()));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\commands\ListChannels.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */