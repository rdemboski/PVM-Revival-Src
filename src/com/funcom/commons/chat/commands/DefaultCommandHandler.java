/*    */ package com.funcom.commons.chat.commands;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatCommunicator;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class DefaultCommandHandler
/*    */   implements CommandHandler
/*    */ {
/* 16 */   private static final Pattern COMMAND_PATTERN = Pattern.compile("^\\/([a-zA-Z]+)($|(.+?)$)");
/*    */   private Set<ChatCommand> commands;
/*    */   private ChatCommunicator communicator;
/*    */   private ChatUser myself;
/*    */   private Iterable<ChatChannel> chatChannels;
/*    */   
/*    */   public DefaultCommandHandler(ChatCommunicator communicator, ChatUser myself, Iterable<ChatChannel> chatChannels) {
/* 23 */     this.communicator = communicator;
/* 24 */     this.myself = myself;
/* 25 */     this.chatChannels = chatChannels;
/* 26 */     this.commands = new HashSet<ChatCommand>();
/* 27 */     registerKnownCommands();
/*    */   }
/*    */   
/*    */   private void registerKnownCommands() {
/* 31 */     this.commands.add(new Say());
/* 32 */     this.commands.add(new Join());
/* 33 */     this.commands.add(new Leave());
/* 34 */     this.commands.add(new ListChannels());
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(String text, ChatChannel chatChannel) {
/* 39 */     String[] cmdArgs = getCommandAndArguments(text);
/* 40 */     if (cmdArgs == null) {
/* 41 */       cmdArgs = new String[] { "say", text };
/*    */     }
/* 43 */     for (ChatCommand cmd : this.commands) {
/* 44 */       if (cmd.getCommand().equals(cmdArgs[0])) {
/* 45 */         cmd.setChatChannels(this.chatChannels);
/* 46 */         cmd.setMyself(this.myself);
/* 47 */         cmd.setCommunicator(this.communicator);
/* 48 */         cmd.setSelectedChannel(chatChannel);
/* 49 */         cmd.execute(cmdArgs);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   private String[] getCommandAndArguments(String data) {
/* 55 */     Matcher matcher = COMMAND_PATTERN.matcher(data);
/* 56 */     if (!matcher.matches())
/* 57 */       return null; 
/* 58 */     List<String> commandAndArguments = new LinkedList<String>();
/* 59 */     for (int i = 1; i < matcher.groupCount(); i++) {
/* 60 */       String group = matcher.group(i).trim();
/* 61 */       if (!group.isEmpty())
/* 62 */         commandAndArguments.add(group); 
/*    */     } 
/* 64 */     return commandAndArguments.<String>toArray(new String[commandAndArguments.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\commands\DefaultCommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */