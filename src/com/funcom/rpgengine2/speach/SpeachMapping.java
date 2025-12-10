/*    */ package com.funcom.rpgengine2.speach;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpeachMapping
/*    */ {
/*    */   private SpeachDescription speachDescription;
/*    */   private boolean barks;
/*    */   private int minBarkTime;
/*    */   private int barkInterval;
/*    */   
/*    */   public SpeachMapping(SpeachDescription speachDescription, boolean barks, int minBarkInterval, int maxBarkInterval) {
/* 13 */     this.speachDescription = speachDescription;
/* 14 */     this.barks = barks;
/* 15 */     this.minBarkTime = minBarkInterval;
/* 16 */     this.barkInterval = maxBarkInterval - minBarkInterval;
/*    */   }
/*    */   
/*    */   public boolean isBarks() {
/* 20 */     return this.barks;
/*    */   }
/*    */   
/*    */   public String getRandomSpeachForContext(SpeachContext context) {
/* 24 */     return this.speachDescription.getRandomSpeachForContext(context);
/*    */   }
/*    */   
/*    */   public int getBarkInterval() {
/* 28 */     return this.barkInterval;
/*    */   }
/*    */   
/*    */   public int getMinBarkTime() {
/* 32 */     return this.minBarkTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\speach\SpeachMapping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */