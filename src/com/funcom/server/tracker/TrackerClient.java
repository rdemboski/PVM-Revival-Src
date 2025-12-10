/*    */ package com.funcom.server.tracker;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class TrackerClient
/*    */   implements Runnable
/*    */ {
/*    */   private String serviceId;
/*    */   private Set<TrackerResponse> responses;
/*    */   private Thread thread;
/*    */   
/*    */   public TrackerClient(String serviceId) {
/* 26 */     this.serviceId = serviceId;
/*    */     
/* 28 */     this.port = 4446;
/*    */     try {
/* 30 */       this.group = InetAddress.getByName("230.0.0.1");
/* 31 */     } catch (UnknownHostException e) {
/* 32 */       throw new RuntimeException("should not happen", e);
/*    */     } 
/* 34 */     this.listeners = Collections.synchronizedList(new ArrayList<ActionListener>());
/* 35 */     this.responses = Collections.synchronizedSet(new HashSet<TrackerResponse>());
/*    */   }
/*    */   private int port; private DatagramSocket socket; private InetAddress group; private List<ActionListener> listeners;
/*    */   public void sendRequest() throws IOException {
/* 39 */     byte[] buf = this.serviceId.getBytes("ISO-8859-1");
/* 40 */     DatagramPacket packet = new DatagramPacket(buf, buf.length, this.group, this.port);
/* 41 */     this.socket.send(packet);
/*    */   }
/*    */   
/*    */   public Set<TrackerResponse> getResponses() {
/* 45 */     return this.responses;
/*    */   }
/*    */   
/*    */   public void start() throws IOException {
/* 49 */     this.socket = new DatagramSocket();
/*    */     
/* 51 */     this.thread = new Thread(this, "TrackerClient");
/* 52 */     this.thread.start();
/*    */   }
/*    */   
/*    */   public void stop() {
/* 56 */     this.thread = null;
/* 57 */     DatagramSocket s = this.socket;
/* 58 */     if (s != null) {
/* 59 */       s.close();
/*    */     }
/*    */   }
/*    */   
/*    */   public void run() {
/* 64 */     Thread myself = Thread.currentThread();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 71 */       while (myself == Thread.currentThread()) {
/* 72 */         byte[] buf = new byte[256];
/* 73 */         DatagramPacket packet = new DatagramPacket(buf, buf.length);
/* 74 */         this.socket.receive(packet);
/*    */         
/*    */         try {
/* 77 */           String accountName = new String(packet.getData(), 0, packet.getLength(), "ISO-8859-1");
/* 78 */           TrackerResponse response = new TrackerResponse(accountName, packet.getAddress());
/* 79 */           this.responses.add(response);
/*    */           
/* 81 */           ActionEvent event = new ActionEvent(this, 1001, this.serviceId);
/* 82 */           for (ActionListener listener : this.listeners) {
/* 83 */             listener.actionPerformed(event);
/*    */           }
/* 85 */         } catch (UnsupportedEncodingException e) {
/* 86 */           e.printStackTrace();
/*    */         } 
/*    */       } 
/* 89 */     } catch (IOException e) {
/* 90 */       if (this.thread != null) {
/* 91 */         e.printStackTrace();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void addActionListener(ActionListener listener) {
/* 98 */     this.listeners.add(listener);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\tracker\TrackerClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */