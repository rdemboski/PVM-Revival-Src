/*    */ package com.funcom.rpgengine2.speach;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpeachManager
/*    */ {
/* 10 */   private Map<String, SpeachDescription> speachDescriptions = new HashMap<String, SpeachDescription>();
/* 11 */   private Map<String, SpeachMapping> speachMapping = new HashMap<String, SpeachMapping>();
/*    */   
/*    */   private SpeachDescription getSpeachDescription(String id) {
/* 14 */     return this.speachDescriptions.get(id);
/*    */   }
/*    */   
/*    */   public void addSpeachMapping(String npcId, String speachId, boolean barks, int barkMin, int barkMax) {
/* 18 */     SpeachMapping mapping = new SpeachMapping(getSpeachDescription(speachId), barks, barkMin, barkMax);
/* 19 */     this.speachMapping.put(npcId, mapping);
/*    */   }
/*    */   
/*    */   public SpeachMapping getSpeachDescritpionForNpc(String npcId) {
/* 23 */     return this.speachMapping.get(npcId);
/*    */   }
/*    */   
/*    */   public void addSpeachDescription(String id, SpeachContext context, String localizationString) {
/* 27 */     SpeachDescription description = getSpeachDescription(id);
/* 28 */     if (description == null) {
/* 29 */       description = new SpeachDescription();
/* 30 */       this.speachDescriptions.put(id, description);
/*    */     } 
/*    */     
/* 33 */     description.addContext(context, localizationString);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 37 */     this.speachDescriptions.clear();
/* 38 */     this.speachMapping.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\speach\SpeachManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */