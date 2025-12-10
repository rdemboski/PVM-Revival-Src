/*     */ package com.funcom.gameengine.conanchat;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ 
/*     */ public class CommandLineClient
/*     */   implements Runnable
/*     */ {
/*     */   private volatile boolean running;
/*     */   private ChatClient chatClient;
/*     */   private String currentInput;
/*     */   
/*     */   public CommandLineClient(ChatClient chatClient) {
/*  18 */     this.chatClient = chatClient;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  23 */     this.running = true;
/*  24 */     runInputFeeder();
/*  25 */     System.out.println("ComanLineClient Running, user: " + this.chatClient.getChatUser());
/*     */     
/*  27 */     while (this.running) {
/*     */       try {
/*  29 */         this.chatClient.update();
/*  30 */       } catch (IOException e) {
/*  31 */         e.printStackTrace();
/*     */       } 
/*  33 */       processInput();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runInputFeeder() {
/*  38 */     Thread th = new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*  41 */             while (CommandLineClient.this.running) {
/*  42 */               BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
/*     */               
/*     */               try {
/*  45 */                 CommandLineClient.this.currentInput = input.readLine();
/*  46 */               } catch (Exception e) {
/*  47 */                 System.err.println("Something bad happened with stdin?");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         },  "input-feeder");
/*  52 */     th.start();
/*     */   }
/*     */   
/*     */   private void processInput() {
/*  56 */     if (this.currentInput == null) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     if ("login".equals(this.currentInput)) {
/*  61 */       this.chatClient.getChatUser().logon();
/*     */     }
/*  63 */     else if (this.currentInput.startsWith("create_user")) {
/*  64 */       String[] tokens = this.currentInput.split(" ");
/*  65 */       long id = Long.parseLong(tokens[1]);
/*  66 */       String nick = tokens[2];
/*  67 */       ((ChatAdminUser)this.chatClient.getChatUser()).createUser(id, id, nick, nick);
/*     */     }
/*  69 */     else if (this.currentInput.startsWith("set_cookie")) {
/*  70 */       String[] tokens = this.currentInput.split(" ");
/*  71 */       long clientId = Long.parseLong(tokens[1]);
/*  72 */       long cookie = Long.parseLong(tokens[2]);
/*     */       try {
/*  74 */         ((ChatAdminUser)this.chatClient.getChatUser()).setCookie(clientId, cookie, InetAddress.getLocalHost(), "todo", "todo");
/*  75 */       } catch (UnknownHostException e) {
/*  76 */         throw new IllegalStateException(e);
/*     */       }
/*     */     
/*  79 */     } else if (this.currentInput.startsWith("message_private")) {
/*  80 */       String[] tokens = this.currentInput.split(" ");
/*  81 */       long clientId = Long.parseLong(tokens[1]);
/*  82 */       String message = tokens[2];
/*  83 */       this.chatClient.getChatUser().messagePrivate(clientId, message);
/*     */     }
/*  85 */     else if (this.currentInput.startsWith("message_group")) {
/*  86 */       String[] tokens = this.currentInput.split(" ");
/*  87 */       long groupId = Long.parseLong(tokens[1]);
/*  88 */       String message = tokens[2];
/*  89 */       this.chatClient.getChatUser().messageGroup(groupId, message, new byte[0]);
/*     */     }
/*  91 */     else if (this.currentInput.startsWith("create_group")) {
/*  92 */       String[] tokens = this.currentInput.split(" ");
/*  93 */       long groupId = Long.parseLong(tokens[1]);
/*  94 */       String groupName = tokens[2];
/*  95 */       ((ChatAdminUser)this.chatClient.getChatUser()).createGroup(groupId, groupName, 4L);
/*     */     }
/*  97 */     else if (this.currentInput.startsWith("join_group")) {
/*  98 */       String[] tokens = this.currentInput.split(" ");
/*  99 */       long groupId = Long.parseLong(tokens[1]);
/* 100 */       long clientId = Long.parseLong(tokens[2]);
/* 101 */       ((ChatAdminUser)this.chatClient.getChatUser()).joinGroup(groupId, clientId);
/*     */     } else {
/*     */       
/* 104 */       System.out.println("Invalid command!");
/* 105 */     }  this.currentInput = null;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws IOException {
/*     */     CommandLineClient commandLineClient;
/* 110 */     if ("admin".equals(args[0])) {
/* 111 */       ChatClient client = new ChatClient(new ChatAdminUser(), "localhost", 7999);
/* 112 */       client.establishConnection();
/* 113 */       commandLineClient = new CommandLineClient(client);
/*     */     } else {
/* 115 */       int id = Integer.parseInt(args[0]);
/* 116 */       int cookie = Integer.parseInt(args[1]);
/* 117 */       commandLineClient = new CommandLineClient(new ChatClient(new DefaultChatUser(id, cookie), "localhost", 7999));
/*     */     } 
/*     */ 
/*     */     
/* 121 */     commandLineClient.run();
/* 122 */     System.out.println("Bye bye!");
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\CommandLineClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */