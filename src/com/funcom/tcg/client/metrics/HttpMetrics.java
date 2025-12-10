/*     */ package com.funcom.tcg.client.metrics;
/*     */ 
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpMetrics
/*     */ {
/*  19 */   private static final Logger LOGGER = Logger.getLogger(HttpMetrics.class);
/*     */   
/*     */   private static final String URL_BASE = "http://ctrack.ageofconan.com/pvmevent";
/*     */   private static boolean eventTrackingEnabled = false;
/*  23 */   private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
/*     */   
/*     */   static {
/*  26 */     eventTrackingEnabled = Boolean.valueOf(System.getProperty("httpmetrics", "false")).booleanValue();
/*     */   }
/*     */   
/*     */   public static void enableTracking() {
/*  30 */     eventTrackingEnabled = true;
/*     */   }
/*     */   
/*     */   public static void postEvent(Event event) {
/*  34 */     if (!eventTrackingEnabled) {
/*  35 */       LOGGER.warn("Event recorded, but since tracking is disabled, its not posted: " + event);
/*     */       
/*     */       return;
/*     */     } 
/*  39 */     EXECUTOR.execute(new URLCallTask(event));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class URLCallTask
/*     */     implements Runnable
/*     */   {
/*     */     private final HttpMetrics.Event event;
/*     */     
/*     */     private URLCallTask(HttpMetrics.Event event) {
/*  49 */       if (event == null)
/*  50 */         throw new IllegalArgumentException("event = null"); 
/*  51 */       this.event = event;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/*  56 */         String finalUrl = "http://ctrack.ageofconan.com/pvmevent?" + this.event.getParameterString();
/*  57 */         URL url = new URL(finalUrl);
/*  58 */         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/*  59 */         urlConnection.setRequestMethod("GET");
/*     */         
/*  61 */         urlConnection.connect();
/*  62 */         String responseMessage = getResponseMessage(urlConnection);
/*  63 */         urlConnection.disconnect();
/*     */         
/*  65 */         if (HttpMetrics.LOGGER.isInfoEnabled()) {
/*  66 */           HttpMetrics.LOGGER.info(String.format("Event recorded and posted: %s, parameters were: '%s', server responded: '%s'", new Object[] { this.event, finalUrl, responseMessage }));
/*     */         }
/*     */       }
/*  69 */       catch (IOException e) {
/*  70 */         HttpMetrics.LOGGER.error("Failure in posting event: " + this.event, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static String getResponseMessage(HttpURLConnection urlConnection) throws IOException {
/*  75 */       InputStreamReader inputStream = null; try {
/*     */         int read;
/*  77 */         inputStream = new InputStreamReader(urlConnection.getInputStream());
/*  78 */         StringBuilder responseMessage = new StringBuilder();
/*  79 */         int bufferSize = 256;
/*  80 */         char[] buffer = new char[256];
/*     */         
/*     */         do {
/*  83 */           read = inputStream.read(buffer);
/*  84 */           responseMessage.append(buffer, 0, read);
/*  85 */         } while (read == 256);
/*     */         
/*  87 */         return responseMessage.toString();
/*     */       } finally {
/*  89 */         if (inputStream != null)
/*  90 */           inputStream.close(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Event
/*     */   {
/*  97 */     public static final Event CLIENT_START = new Event("START", null, false);
/*  98 */     public static final Event CHARACTER_CREATION = new Event("CHAR_CREATE", null, false);
/*  99 */     public static final Event CHARACTER_BOY = new Event("CHAR_BOY", null, false);
/* 100 */     public static final Event CHARACTER_GIRL = new Event("CHAR_GIRL", null, false);
/* 101 */     public static final Event HAVE_CHARACTER = new Event("HAVE_CHARACTER", null, false);
/* 102 */     public static final Event CLICK_PLAY = new Event("CLICK_PLAY", null, false);
/* 103 */     public static final Event CLICK_PLAY_LOGIN = new Event("CLICK_PLAY_LOGIN", null, false);
/* 104 */     public static final Event GAME_LOADED = new Event("LOAD", null, true);
/* 105 */     public static final Event SAVE_CHARACTER_CLICKED = new Event("SAVECHARACTER", null, true, true, true);
/* 106 */     public static final Event CHARACTER_SAVED = new Event("SAVECHARACTERCOMPLETE", null, true, true, true);
/* 107 */     public static final Event CLIENT_CRASHED = new Event("CLIENT_CRASH", null, false, true, true);
/* 108 */     public static final Event TOO_LOW_SPEC = new Event("TOO_LOW_SPEC", null, false);
/* 109 */     public static final Event ACCEPT_TOS = new Event("ACCEPT_TOS", null, false);
/* 110 */     public static final Event SKIP_COMIC = new Event("SKIP_COMIC", null, false);
/* 111 */     public static final Event SOUND_64 = new Event("SOUND_64", null, false);
/* 112 */     public static final Event DISPLAY_LOADED = new Event("DISPLAY_LOADED", null, false);
/* 113 */     public static final Event SPLASH_DISPOSED = new Event("SPLASH_DISPOSED", null, false);
/* 114 */     public static final Event MAIN_MENU_LOADED = new Event("MAIN_MENU_LOADED", null, false);
/* 115 */     public static final Event TOS_LOADED = new Event("TOS_LOADED", null, false);
/* 116 */     public static final Event PET_TRAINING_LVL_2 = new Event("PET_TRAINING_LVL_2", null, false);
/* 117 */     public static final Event PET_TRAINING_LVL_3 = new Event("PET_TRAINING_LVL_3", null, false);
/* 118 */     public static final Event TOO_LOW_RESOLUTION = new Event("TOO_LOW_RESOLUTION", null, false);
/* 119 */     public static final Event DRIVER_ISSUE = new Event("DRIVER_ISSUE", null, false);
/* 120 */     public static final Event LOGIN_FAILED = new Event("LOGIN_FAILED", null, false);
/* 121 */     public static final Event ALREADY_RUNNING = new Event("ALREADY_RUNNING", null, false);
/* 122 */     public static final Event CONFIG_TEST_AGAIN = new Event("CONFIG_TEST_AGAIN", null, false);
/* 123 */     public static final Event CONFIG_TEST_AGAIN_SUCCESS = new Event("CONFIG_TEST_AGAIN_SUCCESS", null, false);
/* 124 */     public static final Event CONFIG_TEST_AGAIN_FAILED = new Event("CONFIG_TEST_AGAIN_FAILED", null, false);
/*     */ 
/*     */     
/* 127 */     private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
/*     */     
/*     */     private static final String CATEGORY = "CLIENT";
/*     */     private final String action;
/*     */     private final String value;
/*     */     private final boolean sendPlayfield;
/*     */     private final boolean sendLevel;
/*     */     private final boolean sendsSubscriptionId;
/*     */     
/*     */     public Event(Event event, String customValue) {
/* 137 */       this(event.action, customValue, event.sendsSubscriptionId);
/*     */     }
/*     */     
/*     */     public Event(Event event, Date date) {
/* 141 */       this(event.action, DATE_FORMATTER.format(date), event.sendsSubscriptionId);
/*     */     }
/*     */     
/*     */     private Event(String action, String value, boolean sendsSubscriptionId) {
/* 145 */       this(action, value, sendsSubscriptionId, false, false);
/*     */     }
/*     */     
/*     */     private Event(String action, String value, boolean sendsSubscriptionId, boolean sendLevel, boolean sendPlayfield) {
/* 149 */       this.action = action;
/* 150 */       this.value = value;
/* 151 */       this.sendsSubscriptionId = sendsSubscriptionId;
/* 152 */       this.sendLevel = sendLevel;
/* 153 */       this.sendPlayfield = sendPlayfield;
/*     */     }
/*     */     
/*     */     public String getParameterString() {
/* 157 */       StringBuilder parameterBuilder = new StringBuilder();
/* 158 */       parameterBuilder.append("category=").append("CLIENT");
/* 159 */       parameterBuilder.append("&action=").append(this.action);
/*     */       
/* 161 */       if (this.value != null)
/* 162 */         parameterBuilder.append("&value=").append(this.value); 
/* 163 */       if (this.sendsSubscriptionId)
/* 164 */         parameterBuilder.append("&subscription_id=").append(MainGameState.getPlayerModel().getId()); 
/* 165 */       if (this.sendLevel && MainGameState.getPlayerNode() != null)
/* 166 */         parameterBuilder.append("&evalue1=").append(MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20))); 
/* 167 */       if (this.sendPlayfield && MainGameState.getPlayerNode() != null) {
/* 168 */         parameterBuilder.append("&evalue2=").append(MainGameState.getPlayerNode().getPosition().getMapId());
/*     */       }
/* 170 */       return parameterBuilder.toString();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 174 */       return '{' + getParameterString() + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\metrics\HttpMetrics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */