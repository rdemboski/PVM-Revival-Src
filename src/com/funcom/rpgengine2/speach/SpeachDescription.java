/*    */ package com.funcom.rpgengine2.speach;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class SpeachDescription {
/*  9 */   public static final Random RANDOM = new Random(System.nanoTime());
/* 10 */   private Map<SpeachContext, List<String>> contextMap = new HashMap<SpeachContext, List<String>>();
/*    */   
/*    */   public void addContext(SpeachContext context, String localizationString) {
/* 13 */     List<String> speachList = this.contextMap.get(context);
/* 14 */     if (speachList == null) {
/* 15 */       speachList = new ArrayList<String>();
/* 16 */       this.contextMap.put(context, speachList);
/*    */     } 
/* 18 */     speachList.add(localizationString);
/*    */   }
/*    */   
/*    */   public String getRandomSpeachForContext(SpeachContext context) {
/* 22 */     List<String> speachList = this.contextMap.get(context);
/* 23 */     if (speachList == null) {
/* 24 */       return null;
/*    */     }
/* 26 */     return speachList.get(RANDOM.nextInt(speachList.size()));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\speach\SpeachDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */