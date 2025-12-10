/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DfxTextWindowManager
/*    */ {
/*  9 */   private Map<String, DfxTextWindow> windows = new HashMap<String, DfxTextWindow>();
/* 10 */   private Map<String, TriggeredDfxTextWindow> tutorialWindows = new HashMap<String, TriggeredDfxTextWindow>();
/*    */   private static DfxTextWindowManager INSTANCE;
/*    */   private RepresentationalNode user;
/*    */   
/*    */   public static DfxTextWindowManager instance() {
/* 15 */     if (INSTANCE == null) {
/* 16 */       INSTANCE = new DfxTextWindowManager();
/*    */     }
/*    */     
/* 19 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void setPlayer(RepresentationalNode user) {
/* 23 */     this.user = user;
/*    */   }
/*    */   
/*    */   public boolean isUser(RepresentationalNode rep) {
/* 27 */     return (rep == this.user);
/*    */   }
/*    */   public float distanceToUser(RepresentationalNode rep) {
/* 30 */     return this.user.getWorldTranslation().distance(rep.getWorldTranslation());
/*    */   }
/*    */   public DfxTextWindow getWindow(String key) {
/* 33 */     return this.windows.get(key);
/*    */   }
/*    */   public TriggeredDfxTextWindow getTutorialWindow(String key) {
/* 36 */     return this.tutorialWindows.get(key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DfxTextWindow createWindow(String key, ResourceManager resourceManager, float screenX, float screenY, float fadeInDuration, float stableDuration, float fadeOutDuration, int numLines, float[] colorRBGA, float scale) {
/* 42 */     DfxTextWindow window = new DfxTextWindow(resourceManager, screenX, screenY, numLines, colorRBGA, scale);
/* 43 */     window.setArriveingDuration(fadeInDuration);
/* 44 */     window.setStableDuration(stableDuration);
/* 45 */     window.setDissapearingDuration(fadeOutDuration);
/* 46 */     this.windows.put(key, window);
/* 47 */     return window;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DfxTextWindow createTutorialWindow(String key, ResourceManager resourceManager, float screenX, float screenY, float fadeInDuration, float stableDuration, float fadeOutDuration, int numLines, float[] colorRBGA, float scale) {
/* 53 */     TriggeredDfxTextWindow window = new TriggeredDfxTextWindow(resourceManager, screenX, screenY, colorRBGA, scale);
/* 54 */     window.setArriveingDuration(fadeInDuration);
/* 55 */     window.setStableDuration(stableDuration);
/* 56 */     window.setDissapearingDuration(fadeOutDuration);
/* 57 */     this.tutorialWindows.put(key, window);
/* 58 */     return window;
/*    */   }
/*    */   
/*    */   public void clearAllText() {
/* 62 */     for (DfxTextWindow window : this.windows.values())
/* 63 */       window.removeAll(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\DfxTextWindowManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */