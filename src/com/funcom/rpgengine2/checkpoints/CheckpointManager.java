/*    */ package com.funcom.rpgengine2.checkpoints;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.DataRecords;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckpointManager
/*    */ {
/*    */   public static final String DEFAULT_CHECKPOINT_ID = ".*";
/* 18 */   private Map<String, CheckpointDescription> checkpointDescriptionMap = new HashMap<String, CheckpointDescription>();
/*    */   
/*    */   public void createCheckpointDescription(DataRecords dataRecords) {
/* 21 */     for (String[] fields : dataRecords.getCheckpointDescriptions()) {
/* 22 */       int index = 0;
/* 23 */       String id = fields[index++];
/* 24 */       String name = fields[index++];
/* 25 */       String dfxText = fields[index++];
/* 26 */       String dfxScript = fields[index++];
/* 27 */       boolean savable = Boolean.parseBoolean(fields[index++]);
/*    */       
/* 29 */       this.checkpointDescriptionMap.put(id, new CheckpointDescription(id, name, dfxText, dfxScript, savable));
/*    */     } 
/*    */   }
/*    */   
/*    */   public CheckpointDescription getCheckpointDescription(String id) {
/* 34 */     CheckpointDescription checkpointDescription = this.checkpointDescriptionMap.get(id);
/* 35 */     if (checkpointDescription == null) {
/* 36 */       checkpointDescription = this.checkpointDescriptionMap.get(".*");
/*    */     }
/* 38 */     return checkpointDescription;
/*    */   }
/*    */   
/*    */   public void clearData() {
/* 42 */     this.checkpointDescriptionMap.clear();
/*    */   }
/*    */   
/*    */   public Iterable<? extends CheckpointDescription> getAllCheckpointDescriptions() {
/* 46 */     return this.checkpointDescriptionMap.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\checkpoints\CheckpointManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */