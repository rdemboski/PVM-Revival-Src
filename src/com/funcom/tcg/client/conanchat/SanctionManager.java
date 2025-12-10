/*     */ package com.funcom.tcg.client.conanchat;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.net.SanctionType;
/*     */ import com.funcom.tcg.net.message.ApplySanctionRequestMessage;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SanctionManager
/*     */ {
/*  20 */   private static final Logger LOGGER = Logger.getLogger(SanctionManager.class.getName());
/*     */   
/*  22 */   private static SanctionManager INSTANCE = null;
/*     */   
/*  24 */   private SanctionType currentSanction = SanctionType.NOTHING;
/*  25 */   private long sanctionedUntil = 0L;
/*  26 */   private String sanctionMessage = "";
/*     */ 
/*     */   
/*     */   private static final String DISPLAY_PATTERN = "%s (%s %s %s).";
/*     */ 
/*     */ 
/*     */   
/*     */   public static SanctionManager getInstance() {
/*  34 */     if (INSTANCE == null) {
/*  35 */       INSTANCE = new SanctionManager();
/*     */     }
/*  37 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public SanctionType getCurrentSanction() {
/*  41 */     return this.currentSanction;
/*     */   }
/*     */   
/*     */   public void setCurrentSanction(SanctionType currentSanction) {
/*  45 */     this.currentSanction = currentSanction;
/*     */   }
/*     */   
/*     */   public long getSanctionedUntil() {
/*  49 */     return this.sanctionedUntil;
/*     */   }
/*     */   
/*     */   public void setSanctionedUntil(long sanctionedUntil) {
/*  53 */     this.sanctionedUntil = sanctionedUntil;
/*     */   }
/*     */   
/*     */   public String getSanctionMessage() {
/*  57 */     return this.sanctionMessage;
/*     */   }
/*     */   
/*     */   public void setSanctionMessage(String sanctionMessage) {
/*  61 */     this.sanctionMessage = sanctionMessage;
/*     */   }
/*     */   
/*     */   public void setDefaultSanctionMessage(String sanctionMessage) {
/*  65 */     if (sanctionMessage.isEmpty()) {
/*  66 */       switch (this.currentSanction) { case SUSPEND:
/*  67 */           this.sanctionMessage = TcgGame.getLocalizedText("chat.sanction.suspend.default", new String[0]); break;
/*  68 */         case SILENCE: this.sanctionMessage = TcgGame.getLocalizedText("chat.sanction.silence.default", new String[0]); break;
/*  69 */         case BAN: this.sanctionMessage = TcgGame.getLocalizedText("chat.sanction.ban.default", new String[0]); break;
/*  70 */         case KICK: this.sanctionMessage = TcgGame.getLocalizedText("chat.sanction.kick.default", new String[0]); break; }
/*     */     
/*     */     } else {
/*  73 */       this.sanctionMessage = sanctionMessage;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() {
/*  78 */     this.currentSanction = SanctionType.NOTHING;
/*  79 */     this.sanctionedUntil = 0L;
/*  80 */     this.sanctionMessage = "";
/*     */   }
/*     */   
/*     */   public boolean isAllowedToReceive() {
/*  84 */     if (this.currentSanction == SanctionType.BAN || this.currentSanction == SanctionType.KICK || (this.currentSanction == SanctionType.SUSPEND && !isSanctionExpired())) {
/*     */ 
/*     */       
/*  87 */       LOGGER.info("not allowed to receive messages");
/*  88 */       return false;
/*     */     } 
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isAllowedToSend() {
/*  94 */     if (this.currentSanction == SanctionType.BAN || this.currentSanction == SanctionType.KICK || (this.currentSanction == SanctionType.SUSPEND && !isSanctionExpired()) || (this.currentSanction == SanctionType.SILENCE && !isSanctionExpired())) {
/*     */ 
/*     */ 
/*     */       
/*  98 */       LOGGER.info("not allowed to send messages");
/*  99 */       return false;
/*     */     } 
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSanctionExpired() {
/* 105 */     if (this.sanctionedUntil == 0L) {
/* 106 */       return true;
/*     */     }
/* 108 */     Calendar calendar = Calendar.getInstance();
/* 109 */     Timestamp now = new Timestamp(calendar.getTime().getTime());
/* 110 */     Timestamp sanction = new Timestamp(this.sanctionedUntil);
/* 111 */     if (now.after(sanction)) {
/* 112 */       LOGGER.info("sanction expired, removing restrictions");
/* 113 */       init();
/* 114 */       persistToDB();
/* 115 */       return true;
/*     */     } 
/* 117 */     return false;
/*     */   }
/*     */   
/*     */   public String displaySanctionMessage() {
/* 121 */     if ((this.currentSanction == SanctionType.SUSPEND || this.currentSanction == SanctionType.SILENCE) && this.sanctionedUntil != 0L && !isSanctionExpired()) {
/*     */       
/* 123 */       Calendar calendar = Calendar.getInstance();
/* 124 */       long now = calendar.getTime().getTime();
/* 125 */       long timeLeft = (this.sanctionedUntil - now) / 1000L;
/*     */ 
/*     */ 
/*     */       
/* 129 */       long remaining = timeLeft / 86400L;
/* 130 */       if (remaining > 0L) {
/* 131 */         return String.format("%s (%s %s %s).", new Object[] { this.sanctionMessage, Long.valueOf(remaining), TcgGame.getLocalizedText("chat.sanction.duration.days", new String[0]), TcgGame.getLocalizedText("chat.sanction.duration.remaining", new String[0]) });
/*     */       }
/*     */       
/* 134 */       remaining = timeLeft / 3600L;
/* 135 */       if (remaining > 0L) {
/* 136 */         return String.format("%s (%s %s %s).", new Object[] { this.sanctionMessage, Long.valueOf(remaining), TcgGame.getLocalizedText("chat.sanction.duration.hours", new String[0]), TcgGame.getLocalizedText("chat.sanction.duration.remaining", new String[0]) });
/*     */       }
/*     */       
/* 139 */       remaining = timeLeft / 60L;
/* 140 */       if (remaining > 0L) {
/* 141 */         return String.format("%s (%s %s %s).", new Object[] { this.sanctionMessage, Long.valueOf(remaining), TcgGame.getLocalizedText("chat.sanction.duration.minutes", new String[0]), TcgGame.getLocalizedText("chat.sanction.duration.remaining", new String[0]) });
/*     */       }
/*     */       
/* 144 */       if (timeLeft > 1L) {
/* 145 */         return String.format("%s (%s %s %s).", new Object[] { this.sanctionMessage, Long.valueOf(timeLeft), TcgGame.getLocalizedText("chat.sanction.duration.seconds", new String[0]), TcgGame.getLocalizedText("chat.sanction.duration.remaining", new String[0]) });
/*     */       }
/*     */     } 
/* 148 */     return this.sanctionMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSanction(String command, String sanctionMessage, String duration) {
/* 153 */     if (SanctionType.WARN.toString().equalsIgnoreCase(command.trim())) {
/*     */       return;
/*     */     }
/* 156 */     init();
/*     */ 
/*     */     
/* 159 */     if (SanctionType.SILENCE.toString().equalsIgnoreCase(command.trim())) {
/* 160 */       setCurrentSanction(SanctionType.SILENCE);
/*     */     }
/* 162 */     else if (SanctionType.KICK.toString().equalsIgnoreCase(command.trim())) {
/* 163 */       setCurrentSanction(SanctionType.KICK);
/*     */     }
/* 165 */     else if (SanctionType.SUSPEND.toString().equalsIgnoreCase(command.trim())) {
/* 166 */       setCurrentSanction(SanctionType.SUSPEND);
/*     */     }
/* 168 */     else if (SanctionType.BAN.toString().equalsIgnoreCase(command.trim())) {
/* 169 */       setCurrentSanction(SanctionType.BAN);
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (duration.length() > 0) {
/*     */       try {
/* 175 */         Calendar calendar = Calendar.getInstance();
/* 176 */         calendar.add(13, Integer.parseInt(duration));
/* 177 */         Date sanctionedUntil = calendar.getTime();
/* 178 */         setSanctionedUntil(sanctionedUntil.getTime());
/* 179 */       } catch (NumberFormatException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (sanctionMessage.length() > 0) {
/* 186 */       setSanctionMessage(sanctionMessage);
/*     */     } else {
/*     */       
/* 189 */       setDefaultSanctionMessage(sanctionMessage);
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (getCurrentSanction() == SanctionType.BAN || (getCurrentSanction() == SanctionType.SUSPEND && getSanctionedUntil() != 0L) || (getCurrentSanction() == SanctionType.SILENCE && getSanctionedUntil() != 0L))
/*     */     {
/*     */       
/* 196 */       persistToDB();
/*     */     }
/*     */   }
/*     */   
/*     */   public void persistToDB() {
/* 201 */     ApplySanctionRequestMessage ufm = new ApplySanctionRequestMessage(getCurrentSanction(), getSanctionedUntil(), getSanctionMessage());
/*     */     try {
/* 203 */       NetworkHandler.instance().getIOHandler().send((Message)ufm);
/* 204 */     } catch (InterruptedException e) {
/* 205 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\SanctionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */