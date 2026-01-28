/*     */ package com.funcom.tcg.client.state;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
import com.funcom.server.common.NetworkConfiguration;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.NetworkHandlerException;
/*     */ import com.funcom.tcg.net.AccountResult;
/*     */ import com.funcom.tcg.net.message.RedirectMessage;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ public class AsyncMessageSender<E extends Message> {
/*  17 */   private static ExecutorService executorService = Executors.newSingleThreadExecutor();
/*     */   
/*     */   private final Class<E> responseClass;
/*     */   private final Message messageToSend;
/*     */   private Future<Object> futureResult;
/*     */   
/*     */   public AsyncMessageSender(Message messageToSend, Class<E> responseClass) {
/*  24 */     this.messageToSend = messageToSend;
/*  25 */     this.responseClass = responseClass;
/*     */   }
/*     */   
/*     */   public synchronized void startSend() {
/*  29 */     if (this.futureResult == null) {
/*  30 */       SendTask<E> sendTask = new SendTask<E>(this.messageToSend, this.responseClass);
/*  31 */       this.futureResult = executorService.submit(sendTask);
/*     */     } else {
/*  33 */       throw new IllegalStateException("cannot restart sender");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  38 */     return this.futureResult.isDone();
/*     */   }
/*     */   
/*     */   public boolean isOk() {
/*  42 */     Object obj = getResult();
/*  43 */     return !(obj instanceof AccountResult);
/*     */   }
/*     */   
/*     */   public AccountResult getError() {
/*  47 */     return (AccountResult)getResult();
/*     */   }
/*     */   
/*     */   public E getMessage() {
/*  51 */     return (E)getResult();
/*     */   }
/*     */   
/*     */   private Object getResult() {
/*     */     try {
/*  56 */       return this.futureResult.get();
/*  57 */     } catch (InterruptedException e) {
/*  58 */       throw new RuntimeException("should not happen");
/*  59 */     } catch (ExecutionException e) {
/*  60 */       throw new RuntimeException("error while getting response", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class SendTask<E extends Message> implements Callable<Object> {
/*     */     private Message messageToSend;
/*     */     private Class<E> responseClass;
/*     */     
/*     */     public SendTask(Message messageToSend, Class<E> responseClass) {
/*  69 */       this.messageToSend = messageToSend;
/*  70 */       this.responseClass = responseClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call() throws Exception {
/*     */       try {
/*  76 */         int totalTries = 5;
/*  77 */         boolean isFirst = true;
/*  78 */         InetSocketAddress serverAddress = NetworkConfiguration.instance().getServerAddress();
/*     */         
/*  80 */         while (totalTries-- > 0) {
/*     */           
/*  82 */           boolean connected = false;
/*  83 */           if (NetworkHandler.instance().getIOHandler() != null) {
/*  84 */             connected = NetworkHandler.instance().getIOHandler().isConnected();
/*     */           }
/*     */           
/*  87 */           if (!isFirst || !connected)
/*     */           {
/*  89 */             NetworkHandler.instance().resetIOHandler(serverAddress);
/*     */           }
/*  91 */           isFirst = false;
/*     */           
/*  93 */           GameIOHandler gameIOHandler = NetworkHandler.instance().getIOHandler();
/*     */           
/*  95 */           gameIOHandler.send(this.messageToSend);
/*     */           
/*     */           Message responseMessage;
/*  98 */           while ((responseMessage = findResponseMessage(gameIOHandler.getInputMessages())) == null) {
/*  99 */             Thread.sleep(100L);
/* 100 */             if (!gameIOHandler.isConnected()) {
/* 101 */               return AccountResult.ERROR_SERVER_NOT_FOUND;
/*     */             }
/*     */           } 
/* 104 */           gameIOHandler.stopNow();
/*     */           
/* 106 */           if (responseMessage instanceof RedirectMessage) {
/*     */             
/* 108 */             System.out.println("Redirecting");
/* 109 */             serverAddress = ((RedirectMessage)responseMessage).getNewServer(); continue;
/*     */           } 
/* 111 */           return responseMessage;
/*     */         } 
/*     */         
/* 114 */         return AccountResult.ERROR_SERVER_BUSY;
/* 115 */       } catch (NetworkHandlerException e) {
/* 116 */         e.printStackTrace();
/* 117 */         return AccountResult.ERROR_SERVER_NOT_FOUND;
/* 118 */       } catch (Exception e) {
/* 119 */         e.printStackTrace();
/* 120 */         return AccountResult.ERROR_CLIENT_SIDE;
/*     */       } 
/*     */     }
/*     */     
/*     */     private Message findResponseMessage(Queue<Message> inputMessages) {
/* 125 */       for (Iterator<Message> iterator = inputMessages.iterator(); iterator.hasNext(); ) {
/* 126 */         Message inputMessage = iterator.next();
/* 127 */         if (this.responseClass.isAssignableFrom(inputMessage.getClass()) || inputMessage instanceof RedirectMessage) {
/*     */           
/* 129 */           iterator.remove();
/* 130 */           return inputMessage;
/*     */         } 
/*     */       } 
/* 133 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\AsyncMessageSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */