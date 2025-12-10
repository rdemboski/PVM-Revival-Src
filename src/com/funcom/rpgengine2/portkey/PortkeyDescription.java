/*    */ package com.funcom.rpgengine2.portkey;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PortkeyDescription
/*    */ {
/*    */   private String id;
/*    */   private String name;
/*    */   private int levelReq;
/*    */   private String completeQuestId;
/*    */   private String onQuestId;
/*    */   private List<String> accessKeys;
/*    */   private String meshResource;
/*    */   private String dfxResource;
/*    */   private boolean subscriptionNeeded;
/*    */   private String dfxText;
/*    */   
/*    */   public PortkeyDescription(String id, String name, int levelReq, String meshResource, String dfxResource, boolean subscriptionNeeded, String dfxText, String completeQuestId, String onQuestId, List<String> accessKeys) {
/* 20 */     this.id = id;
/* 21 */     this.name = name;
/* 22 */     this.levelReq = levelReq;
/* 23 */     this.meshResource = meshResource;
/* 24 */     this.dfxResource = dfxResource;
/* 25 */     this.subscriptionNeeded = subscriptionNeeded;
/* 26 */     this.dfxText = dfxText;
/* 27 */     this.completeQuestId = completeQuestId;
/* 28 */     this.onQuestId = onQuestId;
/* 29 */     this.accessKeys = accessKeys;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 37 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*    */   }
/*    */   
/*    */   public int getLevelReq() {
/* 41 */     return this.levelReq;
/*    */   }
/*    */   
/*    */   public String getMeshResource() {
/* 45 */     return this.meshResource;
/*    */   }
/*    */   
/*    */   public String getDfxResource() {
/* 49 */     return this.dfxResource;
/*    */   }
/*    */   
/*    */   public boolean isSubscriptionNeeded() {
/* 53 */     return this.subscriptionNeeded;
/*    */   }
/*    */   
/*    */   public String getDfxText() {
/* 57 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.dfxText);
/*    */   }
/*    */   
/*    */   public String getCompleteQuestId() {
/* 61 */     return this.completeQuestId;
/*    */   }
/*    */   
/*    */   public String getOnQuestId() {
/* 65 */     return this.onQuestId;
/*    */   }
/*    */   
/*    */   public List<String> getAccessKeys() {
/* 69 */     return this.accessKeys;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return "PortkeyDescription{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", levelReq=" + this.levelReq + ", meshResource='" + this.meshResource + '\'' + ", dfxResource='" + this.dfxResource + '\'' + ", subscriptionNeeded=" + this.subscriptionNeeded + ", dfxText='" + this.dfxText + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\portkey\PortkeyDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */