/*    */ package com.funcom.server.tracker;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.InetAddress;
/*    */ import java.net.MulticastSocket;
/*    */ import java.net.UnknownHostException;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TrackerServer
/*    */   implements Runnable
/*    */ {
/* 15 */   private static final Logger LOG = Logger.getLogger(TrackerServer.class.getName());
/*    */   
/*    */   private String serviceId;
/*    */   private Thread thread;
/*    */   private MulticastSocket socket;
/*    */   private int port;
/*    */   private InetAddress group;
/*    */   
/*    */   public TrackerServer(String serviceId) {
/* 24 */     LOG.setLevel(Level.INFO);
/* 25 */     this.serviceId = serviceId;
/* 26 */     this.port = 4446;
/*    */     try {
/* 28 */       this.group = InetAddress.getByName("230.0.0.1");
/* 29 */     } catch (UnknownHostException e) {
/* 30 */       throw new RuntimeException("should not happen", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void start() {
/* 35 */     this.thread = new Thread(this, "TrackerServer");
/* 36 */     this.thread.start();
/*    */   }
/*    */   
/*    */   public void stop() {
/* 40 */     this.thread = null;
/* 41 */     MulticastSocket s = this.socket;
/* 42 */     if (s != null)
/* 43 */       s.close(); 
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     byte[] accountBytes;
/* 48 */     Thread myself = Thread.currentThread();
/* 49 */     String accountName = System.getProperty("user.name");
/*    */     
/*    */     try {
/* 52 */       if (accountName.length() > 128) {
/* 53 */         accountName = accountName.substring(0, 128);
/*    */       }
/* 55 */       accountBytes = accountName.getBytes("ISO-8859-1");
/* 56 */     } catch (UnsupportedEncodingException e) {
/* 57 */       throw new RuntimeException("should not happen", e);
/*    */     } 
/*    */     
/*    */     try {
/* 61 */       this.socket = new MulticastSocket(this.port);
/* 62 */       this.socket.joinGroup(this.group);
/* 63 */       LOG.info("Started Tracker Server: port=" + this.port + " multicastIp=" + this.group);
/*    */ 
/*    */       
/* 66 */       while (myself == this.thread) {
/* 67 */         byte[] buf = new byte[256];
/* 68 */         DatagramPacket packet = new DatagramPacket(buf, buf.length);
/* 69 */         this.socket.receive(packet);
/*    */         
/* 71 */         String requestId = null;
/*    */         try {
/* 73 */           requestId = new String(packet.getData(), 0, packet.getLength(), "ISO-8859-1");
/* 74 */         } catch (Exception e) {
/* 75 */           e.printStackTrace();
/*    */         } 
/*    */         
/* 78 */         if (requestId != null && this.serviceId.equals(requestId)) {
/* 79 */           InetAddress fromAddress = packet.getAddress();
/* 80 */           int fromPort = packet.getPort();
/*    */ 
/*    */           
/* 83 */           DatagramPacket responsePacket = new DatagramPacket(accountBytes, accountBytes.length, fromAddress, fromPort);
/* 84 */           this.socket.send(responsePacket);
/*    */         } 
/*    */       } 
/* 87 */     } catch (IOException e) {
/* 88 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 91 */     LOG.info("Stopped Tracker Server");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\tracker\TrackerServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */