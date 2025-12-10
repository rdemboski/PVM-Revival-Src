/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class AbstractGameIOHandler implements GameIOHandler, Runnable {
/*  15 */   private static final Logger LOG = Logger.getLogger(AbstractGameIOHandler.class.getName());
/*     */   
/*     */   private List<GameIOHandler.MessageListener> listeners;
/*     */   
/*     */   protected MessageFactory messageFactory;
/*  20 */   protected IOBufferManager bufferManager = new IOBufferManager();
/*     */   
/*  22 */   protected ConcurrentLinkedQueue<Message> inputMessages = new ConcurrentLinkedQueue<Message>();
/*     */   
/*     */   protected BlockingQueue<ByteBuffer> outputQueue;
/*     */   
/*     */   protected LocalGameClient localGameClient;
/*     */   protected Thread thread;
/*     */   private boolean initialized;
/*     */   private int threadPriority;
/*     */   
/*     */   protected AbstractGameIOHandler(int maxOutputQueueSize) {
/*  32 */     this.outputQueue = new ArrayBlockingQueue<ByteBuffer>(maxOutputQueueSize);
/*  33 */     this.threadPriority = 5;
/*  34 */     this.listeners = new LinkedList<GameIOHandler.MessageListener>();
/*     */   }
/*     */   
/*     */   public void addMessageListener(GameIOHandler.MessageListener listener) {
/*  38 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeMessageListener(GameIOHandler.MessageListener listener) {
/*  42 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public int getThreadPriority() {
/*  46 */     return this.threadPriority;
/*     */   }
/*     */   
/*     */   public void setThreadPriority(int threadPriority) {
/*  50 */     this.threadPriority = threadPriority;
/*     */   }
/*     */   
/*     */   public IOBufferManager getBufferManager() {
/*  54 */     return this.bufferManager;
/*     */   }
/*     */   
/*     */   public void setBufferManager(IOBufferManager bufferManager) {
/*  58 */     this.bufferManager = bufferManager;
/*     */   }
/*     */   
/*     */   public void setMessageFactory(MessageFactory messageFactory) {
/*  62 */     this.messageFactory = messageFactory;
/*     */   }
/*     */   
/*     */   public void setLocalGameClient(LocalGameClient localGameClient) {
/*  66 */     this.localGameClient = localGameClient;
/*     */   }
/*     */   
/*     */   public ConcurrentLinkedQueue<Message> getInputMessages() {
/*  70 */     return this.inputMessages;
/*     */   }
/*     */   
/*     */   public void send(Message msg) throws InterruptedException {
/*  74 */     if (isOutputQueueFull()) {
/*  75 */       LOG.warn("output queue is full, send call will block");
/*     */     }
/*     */     
/*  78 */     this.outputQueue.put(this.messageFactory.toBuffer(msg));
/*  79 */     fireMessageSent(msg);
/*     */   }
/*     */   
/*     */   public boolean isOutputQueueFull() {
/*  83 */     return (this.outputQueue.remainingCapacity() <= 0);
/*     */   }
/*     */   
/*     */   public int getOutPutQueueSize() {
/*  87 */     return this.outputQueue.size();
/*     */   }
/*     */   
/*     */   public synchronized void start() throws IOException {
/*  91 */     if (!this.initialized) {
/*  92 */       init();
/*  93 */       this.initialized = true;
/*     */     } 
/*     */     
/*  96 */     if (this.thread == null) {
/*  97 */       initBeforeStart();
/*  98 */       this.thread = new Thread(this, "GameIOHandler");
/*  99 */       this.thread.setPriority(this.threadPriority);
/* 100 */       this.thread.start();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stop() {
/*     */     try {
/* 106 */       stop(true, TimeUnit.SECONDS.toMillis(10L));
/* 107 */     } catch (IOException e) {
/*     */       
/* 109 */       LOG.warn("Error at stopping game io handler: " + e);
/* 110 */     } catch (InterruptedException e) {
/*     */       
/* 112 */       LOG.warn("Error at stopping game io handler: " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopNow() {
/*     */     try {
/* 118 */       stop(false, 0L);
/* 119 */     } catch (IOException e) {
/*     */       
/* 121 */       LOG.warn("Error at stopping game io handler: " + e);
/* 122 */     } catch (InterruptedException e) {
/*     */       
/* 124 */       LOG.warn("Error at stopping game io handler: " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop(boolean disconnectGracefully, long waitMillis) throws IOException, InterruptedException {
/* 131 */     if (this.thread != null) {
/*     */       
/* 133 */       if (disconnectGracefully) {
/* 134 */         send(new LogoutMessage());
/*     */         
/* 136 */         long endAt = System.currentTimeMillis() + waitMillis;
/*     */         
/*     */         do {
/* 139 */           Message msg = getInputMessages().poll();
/*     */           
/* 141 */           if (msg instanceof LogoutMessage) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 146 */           wait(100L);
/* 147 */         } while (System.currentTimeMillis() <= endAt);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       this.thread = null;
/* 154 */       disconnectNow();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void run() {
/* 159 */     Thread myself = Thread.currentThread();
/*     */     
/* 161 */     while (this.thread == myself) {
/* 162 */       update();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireMessageReceived(Message msg) {
/* 167 */     for (GameIOHandler.MessageListener listener : this.listeners) {
/* 168 */       listener.messageReceived(this, msg);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireMessageSent(Message msg) {
/* 173 */     for (GameIOHandler.MessageListener listener : this.listeners)
/* 174 */       listener.messageSent(this, msg); 
/*     */   }
/*     */   
/*     */   protected abstract void disconnectNow() throws IOException;
/*     */   
/*     */   protected abstract void init();
/*     */   
/*     */   protected abstract void initBeforeStart() throws IOException;
/*     */   
/*     */   protected abstract void update();
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\AbstractGameIOHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */