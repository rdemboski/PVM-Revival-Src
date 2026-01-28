/*     */ package com.funcom.gameengine.sshshell;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.sshshell.processors.TelnetCompleteRefreshProcessor;
/*     */ import com.funcom.gameengine.sshshell.processors.TelnetMessageProcessor;
/*     */ import com.funcom.gameengine.sshshell.processors.TelnetQuitProcessor;
/*     */ import com.funcom.gameengine.sshshell.processors.TelnetRefreshProcessor;
/*     */ import com.funcom.gameengine.sshshell.processors.TelnetSaveProcessor;
/*     */ import com.funcom.server.common.UnknownMessageTypeException;
/*     */ import java.io.IOException;
import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TelnetCommandService implements Updated {
/*  22 */   private static final Logger LOGGER = Logger.getLogger(TelnetCommandService.class);
/*     */   
/*     */   private static final int BUFFER_SIZE = 256;
/*     */   
/*     */   private static final int MESSAGES_PER_UPDATE = 2;
/*     */   private Config config;
/*     */   private Selector selector;
/*     */   private ServerSocketChannel serverChannel;
/*     */   private ByteBuffer buffer;
/*     */   private TelnetCommandsExecutor serverCommandQueue;
/*     */   
/*     */   public TelnetCommandService(Config config, TelnetCommandsExecutor serverCommandQueue) {
/*  34 */     if (config == null)
/*  35 */       throw new IllegalArgumentException("config = null"); 
/*  36 */     if (serverCommandQueue == null) {
/*  37 */       throw new IllegalArgumentException("serverCommandQueue = null");
/*     */     }
/*  39 */     this.config = config;
/*  40 */     config.validate();
/*  41 */     this.serverCommandQueue = serverCommandQueue;
/*  42 */     this.buffer = ByteBuffer.allocate(256);
/*     */   }
/*     */   
/*     */   public void openConnection() throws IOException {
/*     */     try {
/*  47 */       this.selector = Selector.open();
/*     */       
/*  49 */       this.serverChannel = ServerSocketChannel.open();
/*  50 */       this.serverChannel.socket().bind(new InetSocketAddress(this.config.port));
/*  51 */       this.serverChannel.configureBlocking(false);
/*  52 */       this.serverChannel.register(this.selector, 16);
/*  53 */     } catch (IOException e) {
/*  54 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Config getConfig() {
/*  59 */     return this.config;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*     */     try {
/*  64 */       int selectedKeysCount = this.selector.selectNow();
/*  65 */       if (selectedKeysCount == 0) {
/*     */         return;
/*     */       }
/*  68 */       Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
/*  69 */       checkConnections(selectedKeys);
/*  70 */       readIncoming(selectedKeys);
/*  71 */       processMessages(selectedKeys);
/*     */     }
/*  73 */     catch (IOException e) {
/*  74 */       LOGGER.error("Error during shell processing! Trying to reinitialize!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkConnections(Set<SelectionKey> selectedKeys) throws IOException {
/*  79 */     Iterator<SelectionKey> it = selectedKeys.iterator();
/*  80 */     while (it.hasNext()) {
/*  81 */       SelectionKey selectedKey = it.next();
/*  82 */       if (selectedKey.isAcceptable()) {
/*  83 */         SocketChannel newConnection = ((ServerSocketChannel)selectedKey.channel()).accept();
/*  84 */         newConnection.configureBlocking(false);
/*  85 */         newConnection.register(this.selector, 1);
/*     */         
/*  87 */         LOGGER.info("New user connected: " + newConnection.socket().getInetAddress());
/*  88 */         sendGreeting(newConnection);
/*  89 */         sendPrompt(newConnection);
/*  90 */         it.remove(); continue;
/*  91 */       }  if (!selectedKey.isValid()) {
/*  92 */         it.remove();
/*  93 */         LOGGER.info("User disconnected : " + ((SocketChannel)selectedKey.channel()).socket().getInetAddress());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendGreeting(SocketChannel socketChannel) {
/*  99 */     this.buffer.clear();
/* 100 */     this.buffer.put((this.config.welcomeMessage + "\n").getBytes());
/* 101 */     this.buffer.flip();
/*     */     try {
/* 103 */       socketChannel.write(this.buffer);
/* 104 */     } catch (IOException e) {
/* 105 */       LOGGER.error("Failed to send greeting to new connection: " + socketChannel.socket().getInetAddress());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendPrompt(SocketChannel socketChannel) {
/* 110 */     this.buffer.clear();
/* 111 */     this.buffer.put(this.config.prompt.getBytes());
/* 112 */     this.buffer.flip();
/*     */     try {
/* 114 */       socketChannel.write(this.buffer);
/* 115 */     } catch (IOException e) {
/* 116 */       LOGGER.error("Failed to send prompt: " + socketChannel.socket().getInetAddress());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readIncoming(Set<SelectionKey> selectedKeys) {
/* 121 */     Iterator<SelectionKey> it = selectedKeys.iterator();
/* 122 */     while (it.hasNext()) {
/* 123 */       SelectionKey selectedKey = it.next();
/* 124 */       if (selectedKey.isReadable()) {
/*     */         try {
/* 126 */           SocketChannel socketChannel = (SocketChannel)selectedKey.channel();
/*     */           
/* 128 */           this.buffer.clear();
/* 129 */           int read = socketChannel.read(this.buffer);
/* 130 */           if (read == -1) {
/*     */             
/*     */             try {
/* 133 */               socketChannel.socket().close();
/* 134 */             } catch (IOException e1) {
/* 135 */               e1.printStackTrace();
/*     */             } 
/* 137 */             selectedKey.cancel();
/* 138 */             it.remove();
/* 139 */             LOGGER.info("User disconnected : " + ((SocketChannel)selectedKey.channel()).socket().getInetAddress()); continue;
/*     */           } 
/* 141 */           this.buffer.flip();
/*     */           
/* 143 */           String[] commandWithParams = SSHCommandType.createCommand(this.buffer);
/* 144 */           if (selectedKey.attachment() == null) {
/* 145 */             selectedKey.attach(new LinkedList());
/*     */           }
/* 147 */           Queue<String[]> messageQueue = (Queue<String[]>)selectedKey.attachment();
/* 148 */           if (!messageQueue.offer(commandWithParams)) {
/* 149 */             LOGGER.error("Failed to place message on the queue: selectionKey:'" + selectedKey + "', commandWithParams:'" + commandWithParams + "'");
/*     */           }
/* 151 */           this.buffer.clear();
/*     */           
/* 153 */           sendPrompt(socketChannel);
/*     */         }
/* 155 */         catch (UnknownMessageTypeException e) {
/* 156 */           LOGGER.error(e.getMessage(), (Throwable)e);
/* 157 */         } catch (IOException e) {
/*     */           
/*     */           try {
/* 160 */             ((SocketChannel)selectedKey.channel()).socket().close();
/* 161 */           } catch (IOException e1) {
/* 162 */             e1.printStackTrace();
/*     */           } 
/* 164 */           selectedKey.cancel();
/* 165 */           it.remove();
/* 166 */           LOGGER.error(e.getMessage(), e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processMessages(Set<SelectionKey> selectedKeys) {
/* 173 */     Iterator<SelectionKey> it = selectedKeys.iterator();
/* 174 */     while (it.hasNext()) {
/* 175 */       SelectionKey selectedKey = it.next();
/* 176 */       Object attachment = selectedKey.attachment();
/* 177 */       if (attachment != null) {
/* 178 */         Queue<String[]> commandQueue = (Queue<String[]>)attachment;
/*     */         
/* 180 */         for (int i = 0; i < 2 && !commandQueue.isEmpty(); i++) {
/* 181 */           String[] message = commandQueue.remove();
/* 182 */           processCommand(message, selectedKey);
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       it.remove();
/*     */     }  } private void processCommand(String[] message, SelectionKey selectedKey) { TelnetRefreshProcessor telnetRefreshProcessor;
/*     */     TelnetMessageProcessor telnetMessageProcessor;
/*     */     TelnetQuitProcessor telnetQuitProcessor;
/*     */     TelnetCompleteRefreshProcessor telnetCompleteRefreshProcessor;
/*     */     TelnetSaveProcessor telnetSaveProcessor = null;
/* 192 */     SSHCommandType enumValue = SSHCommandType.valueForId(message[0].trim());
/* 193 */     if (enumValue == null) {
/* 194 */       LOGGER.warn("Invalid command: " + message[0]);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 200 */     switch (enumValue) {
/*     */       case REFRESH_PLAYER:
/* 202 */         telnetRefreshProcessor = new TelnetRefreshProcessor(message, selectedKey, this.serverCommandQueue, LOGGER);
/*     */         break;
/*     */       case SERVER_NOTICE:
/* 205 */         telnetMessageProcessor = new TelnetMessageProcessor(message, selectedKey, this.serverCommandQueue, LOGGER);
/*     */         break;
/*     */       case QUIT:
/* 208 */         telnetQuitProcessor = new TelnetQuitProcessor(message, selectedKey, this.serverCommandQueue, LOGGER);
/*     */         break;
/*     */       case COMPLETE_REFRESH_PLAYER:
/* 211 */         telnetCompleteRefreshProcessor = new TelnetCompleteRefreshProcessor(message, selectedKey, this.serverCommandQueue, LOGGER);
/*     */         break;
/*     */       case SAVE_REQUEST:
/* 214 */         telnetSaveProcessor = new TelnetSaveProcessor(message, selectedKey, this.serverCommandQueue, LOGGER);
/*     */         break;
/*     */       default:
/* 217 */         LOGGER.error("Default branch in message processing - we should never end up here, because message should be discarded as unknown way before!!! : " + message);
/*     */         return;
/*     */     } 
/*     */     
/* 221 */     telnetSaveProcessor.process(); }
/*     */ 
/*     */   
/*     */   public void closeConnection() throws IOException {
/* 225 */     this.selector.close();
/* 226 */     this.serverChannel.close();
/*     */   }
/*     */   
/*     */   public static class Config {
/*     */     public int port;
/*     */     public String welcomeMessage;
/*     */     public String prompt;
/*     */     
/*     */     public Config() {
/* 235 */       this.port = 7001;
/* 236 */       this.welcomeMessage = "TCGServer SSH shell.";
/* 237 */       this.prompt = "> ";
/*     */     }
/*     */     
/*     */     public Config(int port, String welcomeMessage, String prompt) {
/* 241 */       this.port = port;
/* 242 */       this.welcomeMessage = welcomeMessage;
/* 243 */       this.prompt = prompt;
/*     */     }
/*     */     
/*     */     public void validate() {
/* 247 */       if (this.welcomeMessage == null || this.welcomeMessage.isEmpty())
/* 248 */         throw new IllegalStateException("Welcome message is null or empty."); 
/* 249 */       if (this.prompt == null || this.prompt.isEmpty())
/* 250 */         throw new IllegalStateException("Prompt string is null or empty."); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\TelnetCommandService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */