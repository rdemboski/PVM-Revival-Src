/*    */ package com.funcom.rpgengine2.portkey;
/*    */ import com.funcom.rpgengine2.loader.DataRecords;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
import java.util.Map;
/*    */ 
/*    */ public class PortkeyManager {
/*  9 */   private Map<String, PortkeyDescription> portkeyDescriptions = new HashMap<String, PortkeyDescription>();
/*    */   
/*    */   public void createPortkeyDescriptions(DataRecords dataRecords) {
/* 12 */     for (String[] fields : dataRecords.getPortkeys()) {
/* 13 */       int index = 0;
/* 14 */       String id = fields[index++];
/* 15 */       String name = fields[index++];
/* 16 */       int levelReq = Integer.parseInt(fields[index++]);
/* 17 */       String keys = fields[index++];
/* 18 */       String completeQuestId = fields[index++];
/* 19 */       String onQuestId = fields[index++];
/* 20 */       String meshResource = fields[index++];
/* 21 */       String dfxResource = fields[index++];
/* 22 */       boolean subscriptionNeeded = Boolean.parseBoolean(fields[index++]);
/* 23 */       String dfxText = fields[index++];
/*    */       
/* 25 */       List<String> accessKeys = Collections.unmodifiableList(Arrays.asList(keys.split(".")));
/*    */       
/* 27 */       PortkeyDescription portkeyDescription = new PortkeyDescription(id, name, levelReq, meshResource, dfxResource, subscriptionNeeded, dfxText, completeQuestId, onQuestId, accessKeys);
/* 28 */       this.portkeyDescriptions.put(id, portkeyDescription);
/*    */     } 
/*    */   }
/*    */   
/*    */   public PortkeyDescription getPortkeyDescription(String id) {
/* 33 */     return this.portkeyDescriptions.get(id);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 37 */     this.portkeyDescriptions.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\portkey\PortkeyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */