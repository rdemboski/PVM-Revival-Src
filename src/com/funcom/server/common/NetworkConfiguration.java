/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import com.funcom.commons.configuration.InjectFromProperty;
/*     */ import com.funcom.server.tracker.ServerSelectDialog;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkConfiguration
/*     */ {
/*     */   private static final String DEFAULT_HOST = "casual.funcom.com";
/*     */   private static final int DEFAULT_PORT = 7000;
/*     */   private static final String SYSPROPKEY_HOST = "network.host";
/*     */   private static final String SYSPROPKEY_PORT = "network.port";
/*     */   private static final String SYSPROPKEY_CHAT_HOST = "network.chathost";
/*     */   private static final String SYSPROPKEY_CHAT_PORT = "network.chatport";
/*     */   private static final String SYSPROPKEY_PRECOMPILE_MAPS = "precompile.maps";
/*  20 */   private static final boolean IS_NETWORKING = (System.getProperty("network.host", "").trim().length() > 0);
/*     */   
/*     */   private static NetworkConfiguration INSTANCE;
/*     */   
/*     */   @InjectFromProperty(value = "network.host", isRequired = false)
/*     */   private volatile String host;
/*     */   
/*     */   @InjectFromProperty("network.port")
/*     */   private volatile int port;
/*     */   
/*     */   @InjectFromProperty("chat.hostname")
/*     */   private volatile String chatHost;
/*     */   
/*     */   @InjectFromProperty("chat.port")
/*     */   private volatile int chatPort;
/*     */ 
/*     */   
/*     */   public static NetworkConfiguration instance() {
/*  38 */     if (INSTANCE == null) {
/*  39 */       INSTANCE = new NetworkConfiguration();
/*     */     }
/*  41 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   protected NetworkConfiguration() {
/*  45 */     String hostname = prepareServerHost();
/*  46 */     this.host = hostname;
/*     */     
/*  48 */     this.port = 7000;
/*  49 */     if (System.getProperty("network.port") != null) {
/*  50 */       this.port = Integer.parseInt(System.getProperty("network.port"));
/*     */     }
/*     */     
/*  53 */     this.chatHost = hostname;
/*  54 */     if (System.getProperty("network.chathost") != null) {
/*  55 */       this.chatHost = System.getProperty("network.chathost");
/*     */     }
/*     */     
/*  58 */     this.chatPort = 7000;
/*  59 */     if (System.getProperty("network.chatport") != null) {
/*  60 */       this.chatPort = Integer.parseInt(System.getProperty("network.chatport"));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String prepareServerHost() {
/*  65 */     String host = "casual.funcom.com";
/*  66 */     if (System.getProperty("network.host") != null) {
/*  67 */       host = System.getProperty("network.host");
/*     */     }
/*     */     
/*  70 */     if ("tracker.selected".equalsIgnoreCase(host)) {
/*     */       try {
/*  72 */         ServerSelectDialog dialog = new ServerSelectDialog("com.funcom.tcg");
/*  73 */         dialog.toFront();
/*  74 */         dialog.setAlwaysOnTop(true);
/*  75 */         dialog.setVisible(true);
/*  76 */         host = dialog.getSelectedAddress();
/*     */         
/*  78 */         if (host == null) {
/*  79 */           System.err.println("*** Cancelled, exiting client...");
/*  80 */           System.exit(0);
/*     */         } 
/*  82 */       } catch (IOException e) {
/*  83 */         e.printStackTrace();
/*  84 */         host = JOptionPane.showInputDialog("Error while getting host from tracker.\nPlease enter it manually (ip address):");
/*     */       } 
/*     */     }
/*     */     
/*  88 */     return host;
/*     */   }
/*     */   
/*     */   public boolean isNetworking() {
/*  92 */     return IS_NETWORKING;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  96 */     return this.host;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 100 */     return this.port;
/*     */   }
/*     */   
/*     */   public String getChatHost() {
/* 104 */     return this.chatHost;
/*     */   }
/*     */   
/*     */   public int getChatPort() {
/* 108 */     return this.chatPort;
/*     */   }
/*     */   
/*     */   public InetSocketAddress getServerAddress() {
/* 112 */     return new InetSocketAddress(getHost(), getPort());
/*     */   }
/*     */   
/*     */   public boolean shouldPreCompileMaps() {
/* 116 */     return (System.getProperty("precompile.maps") != null && System.getProperty("precompile.maps").equals("true"));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\NetworkConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */