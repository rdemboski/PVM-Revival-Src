/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuffRegistry
/*    */ {
/* 18 */   private Map<String, ClientBuff> buffs = new HashMap<String, ClientBuff>();
/*    */   private static final String BUFF_FILE = "rpg/*.buff.csv";
/*    */   
/*    */   public void reload() {
/* 22 */     this.buffs.clear();
/* 23 */     TcgGame.getResourceManager().getManagedResource(CSVData.class, "rpg/*.buff.csv", CacheType.NOT_CACHED).setDirty();
/* 24 */     TcgGame.getResourceManager().getManagedResource(CSVData.class, "rpg/*.debuff.csv", CacheType.NOT_CACHED).setDirty();
/* 25 */     TcgGame.getResourceManager().update();
/* 26 */     readData();
/*    */   }
/*    */   private static final String DEBUFF_FILE = "rpg/*.debuff.csv";
/*    */   public void readData() {
/* 30 */     CSVData records = (CSVData)TcgGame.getResourceManager().getResource(CSVData.class, "rpg/*.buff.csv", CacheType.NOT_CACHED);
/* 31 */     for (String[] record : records) {
/* 32 */       createBuff(record);
/*    */     }
/* 34 */     records = (CSVData)TcgGame.getResourceManager().getResource(CSVData.class, "rpg/*.debuff.csv", CacheType.NOT_CACHED);
/* 35 */     for (String[] record : records) {
/* 36 */       createBuff(record);
/*    */     }
/*    */   }
/*    */   
/*    */   private void createBuff(String[] record) {
/* 41 */     List<String> list = Arrays.asList(record);
/* 42 */     Iterator<String> iterator = list.iterator();
/* 43 */     String classId = iterator.next();
/*    */     
/* 45 */     ClientBuff clientBuff = this.buffs.get(classId);
/* 46 */     if (clientBuff == null) {
/* 47 */       clientBuff = new ClientBuff(classId);
/* 48 */       this.buffs.put(clientBuff.getClassId(), clientBuff);
/*    */       
/* 50 */       String icon = iterator.next();
/* 51 */       clientBuff.setIcon(icon);
/*    */       
/* 53 */       String dfxScript = iterator.next();
/* 54 */       clientBuff.setDfxScript(dfxScript);
/*    */       
/* 56 */       iterator.next();
/* 57 */       iterator.next();
/* 58 */       iterator.next();
/*    */       
/* 60 */       clientBuff.setCureElementId(iterator.next());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientBuff getBuffForClassID(String classId) {
/* 74 */     ClientBuff buff = this.buffs.get(classId);
/* 75 */     if (buff == null) {
/* 76 */       buff = new ClientBuff(classId);
/*    */     }
/* 78 */     return buff;
/*    */   }
/*    */   
/*    */   public boolean containsClassID(String classID) {
/* 82 */     return this.buffs.containsKey(classID);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\BuffRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */