/*    */ package com.funcom.tcg.client;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientLauncher
/*    */ {
/* 19 */   private static final Logger LOGGER = Logger.getLogger(ClientLauncher.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 28 */     Process proc = Runtime.getRuntime().exec("java -Djava.library.path=../commons/lib/native/ -Xmx1G -cp Client.jar com.funcom.tcg.client.TcgJme");
/*    */     
/*    */     try {
/* 31 */       Thread inputThread = new MyThread(proc.getInputStream());
/* 32 */       Thread errorThread = new MyThread(proc.getErrorStream());
/* 33 */       inputThread.start();
/* 34 */       errorThread.start();
/*    */       
/* 36 */       proc.waitFor();
/* 37 */     } catch (InterruptedException e) {
/* 38 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static class MyThread extends Thread {
/*    */     private final InputStream inputstream;
/*    */     
/*    */     public MyThread(InputStream proc) {
/* 46 */       this.inputstream = proc;
/*    */     }
/*    */     
/*    */     public void run() {
/* 50 */       BufferedReader in = new BufferedReader(new InputStreamReader(this.inputstream));
/*    */       
/*    */       try {
/*    */         String line;
/* 54 */         while ((line = in.readLine()) != null) {
/* 55 */           ClientLauncher.LOGGER.log((Priority)Level.INFO, line);
/*    */         }
/* 57 */       } catch (IOException e) {
/* 58 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\ClientLauncher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */