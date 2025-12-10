/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.UnresolvedAddressException;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class NetworkGameIOHandler
/*     */   extends AbstractGameIOHandler
/*     */   implements InputBufferListener {
/*     */   private static final boolean DEBUG = false;
/*  23 */   private static final Logger LOG = Logger.getLogger(NetworkGameIOHandler.class.getName());
/*     */   
/*     */   private static final int DEFAULT_MAX_OUTPUT_QUEUE_SIZE = 300;
/*     */   
/*  27 */   private static final ChannelOpener DEFAULT_OPENER = new ChannelOpener()
/*     */     {
/*     */       public void open(NetworkGameIOHandler parentHandler) throws IOException
/*     */       {
/*  31 */         NetworkGameIOHandler.LOG.info("Connecting to: " + parentHandler.remoteAddress);
/*  32 */         int tries = 20;
/*     */         
/*  34 */         SocketChannel channel = null;
/*  35 */         while (channel == null) {
/*     */           try {
/*  37 */             channel = SocketChannel.open(parentHandler.remoteAddress);
/*  38 */           } catch (ConnectException e) {
/*  39 */             NetworkGameIOHandler.LOG.info("Could not connect, waiting for server...");
/*     */             try {
/*  41 */               Thread.sleep(1000L);
/*  42 */             } catch (InterruptedException ignore) {}
/*     */             
/*  44 */             tries--;
/*  45 */             if (tries <= 0) {
/*  46 */               throw e;
/*     */             }
/*     */           }
/*  49 */           catch (UnresolvedAddressException e) {
/*  50 */             NetworkGameIOHandler.LOG.info("Could not resolve server address, retrying to connect...");
/*     */             try {
/*  52 */               Thread.sleep(1000L);
/*  53 */             } catch (InterruptedException ignore) {}
/*     */             
/*  55 */             tries--;
/*  56 */             if (tries <= 0) {
/*  57 */               throw e;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/*  62 */         parentHandler.socketChannel = channel;
/*  63 */         parentHandler.socketChannel.configureBlocking(false);
/*  64 */         parentHandler.selectionKey = parentHandler.socketChannel.register(parentHandler.selector, 8);
/*     */       }
/*     */     };
/*     */   Selector selector; SelectionKey selectionKey; SocketChannel socketChannel;
/*     */   private InputBufferHandler inputBufferHandler;
/*     */   
/*     */   public static interface ChannelOpener {
/*     */     void open(NetworkGameIOHandler param1NetworkGameIOHandler) throws IOException; }
/*     */   
/*  73 */   private ChannelOpener channelOpener = DEFAULT_OPENER;
/*     */   SocketAddress remoteAddress;
/*     */   private LogoutMessage logoutMessage;
/*     */   private boolean loginResponded;
/*     */   long lastWrite;
/*     */   
/*     */   public NetworkGameIOHandler() {
/*  80 */     this(300);
/*     */   }
/*     */   public void setChannelOpener(ChannelOpener channelOpener) { this.channelOpener = channelOpener; } protected void init() { this.inputBufferHandler = new InputBufferHandler(this.bufferManager, this);
/*     */     this.logoutMessage = null;
/*  84 */     this.loginResponded = false; } public NetworkGameIOHandler(int maxOutputQueueSize) { super(maxOutputQueueSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     this.lastWrite = GlobalTime.getInstance().getCurrentTime(); }
/*     */   protected void initBeforeStart() throws IOException { this.selector = SelectorProvider.provider().openSelector(); this.channelOpener.open(this); if (LOG.isEnabledFor((Priority)Level.INFO))
/*     */       LOG.info("socketChannel.isConnected() = " + this.socketChannel.isConnected() + " to : " + this.remoteAddress);  }
/* 167 */   public void send(Message msg) throws InterruptedException { super.send(msg); if (this.selector != null) { this.selector.wakeup(); } else if (LOG.isEnabledFor((Priority)Level.ERROR)) { LOG.error(NetworkGameIOHandler.class.getSimpleName() + "> selector is null, cannot wakeup"); }  } protected void update() { Exception exception = null;
/*     */     
/*     */     try {
/* 170 */       if (!this.outputQueue.isEmpty())
/*     */       {
/* 172 */         if (!this.selectionKey.isWritable())
/*     */         {
/* 174 */           this.selectionKey.interestOps(4);
/*     */         }
/*     */       }
/*     */       
/* 178 */       this.selector.select();
/*     */ 
/*     */       
/* 181 */       if (GlobalTime.getInstance().getCurrentTime() - this.lastWrite > 2000L) {
/* 182 */         this.outputQueue.offer(this.messageFactory.toBuffer(KeepAliveMessage.INSTANCE));
/*     */       }
/*     */       
/* 185 */       if (this.selectionKey.isReadable()) {
/* 186 */         read();
/* 187 */       } else if (this.selectionKey.isWritable()) {
/* 188 */         send();
/* 189 */         this.lastWrite = GlobalTime.getInstance().getCurrentTime();
/*     */       }
/*     */     
/* 192 */     } catch (IllegalArgumentException e) {
/* 193 */       exception = e;
/* 194 */     } catch (CancelledKeyException e) {
/* 195 */       exception = e;
/* 196 */     } catch (IOException e) {
/* 197 */       exception = e;
/*     */     } 
/*     */     
/* 200 */     if (exception != null) {
/* 201 */       boolean wasAlreadyStopped = (this.thread == null);
/* 202 */       LOG.fatal("NetworkGameIOHandler error, handler stopped: " + exception);
/*     */       try {
/* 204 */         stop(false, 0L);
/* 205 */       } catch (IOException e) {
/*     */       
/* 207 */       } catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (!wasAlreadyStopped && this.localGameClient != null)
/*     */       
/*     */       { 
/*     */         
/* 215 */         LocalGameClient.DisconnectReason reason = LocalGameClient.DisconnectReason.NETWORK_PROBLEM;
/* 216 */         boolean tryReconnect = false;
/*     */         
/* 218 */         if (this.logoutMessage != null) {
/* 219 */           if (this.logoutMessage.isLoggedInFromAnotherClient()) {
/* 220 */             reason = LocalGameClient.DisconnectReason.LOGGED_IN_FROM_ANOTHER_CLIENT;
/* 221 */           } else if (this.logoutMessage.isServerShuttingDown()) {
/* 222 */             reason = LocalGameClient.DisconnectReason.SERVER_SHUTDOWN;
/*     */           } else {
/* 224 */             reason = LocalGameClient.DisconnectReason.LOGIN_FAILURE;
/*     */           }
/*     */         
/* 227 */         } else if (!this.loginResponded) {
/* 228 */           reason = LocalGameClient.DisconnectReason.LOGIN_FAILURE;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         this.localGameClient.notifyDisconnected(this, reason, tryReconnect); }
/*     */       else
/* 238 */       { LOG.log((Priority)Level.INFO, "ignored disconnection"); } 
/*     */     }  } public SocketAddress getTargetAddres() { return this.socketChannel.socket().getRemoteSocketAddress(); }
/*     */   public InetAddress getInetAddress() { return this.socketChannel.socket().getInetAddress(); }
/*     */   public int getTargetPort() { return this.socketChannel.socket().getPort(); }
/*     */   protected void disconnectNow() throws IOException { if (this.selectionKey != null)
/*     */       this.selectionKey.cancel();  if (this.socketChannel != null)
/*     */       this.socketChannel.close();  }
/* 245 */   public boolean isConnected() { return (this.socketChannel != null && this.socketChannel.isOpen()); }
/*     */ 
/*     */   
/*     */   public void offer(ByteBuffer completeBuffer) {
/* 249 */     Message msg = this.messageFactory.toMessage(completeBuffer);
/*     */     
/* 251 */     if (msg instanceof LogoutMessage) {
/* 252 */       this.logoutMessage = (LogoutMessage)msg;
/* 253 */       LOG.log((Priority)Level.INFO, "Got Logout Message");
/*     */       try {
/* 255 */         stop(false, 0L);
/* 256 */         this.localGameClient.notifyDisconnected(this, this.logoutMessage.getReason(), false);
/* 257 */       } catch (IOException e) {
/*     */       
/* 259 */       } catch (InterruptedException e) {}
/*     */     
/*     */     }
/* 262 */     else if (msg instanceof LoginResponseMessage) {
/* 263 */       this.loginResponded = true;
/*     */     } 
/*     */     
/* 266 */     this.inputMessages.offer(msg);
/* 267 */     fireMessageReceived(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private void read() throws IOException {
/* 272 */     this.socketChannel.read(this.inputBufferHandler.getReadBuffer());
/* 273 */     this.inputBufferHandler.process();
/*     */   }
/*     */ 
/*     */   
/*     */   private void send() throws IOException {
/*     */     ByteBuffer buffer;
/* 279 */     while ((buffer = this.outputQueue.peek()) != null) {
/*     */       
/* 281 */       this.socketChannel.write(buffer);
/*     */       
/* 283 */       if (buffer.hasRemaining()) {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 291 */           Thread.sleep(50L);
/* 292 */         } catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 301 */       this.outputQueue.poll();
/* 302 */       this.bufferManager.putForReuse(buffer);
/*     */     } 
/*     */     
/* 305 */     if (this.outputQueue.isEmpty())
/*     */     {
/* 307 */       this.selectionKey.interestOps(1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRemoteAddress(SocketAddress remoteAddress) {
/* 312 */     this.remoteAddress = remoteAddress;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\NetworkGameIOHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */