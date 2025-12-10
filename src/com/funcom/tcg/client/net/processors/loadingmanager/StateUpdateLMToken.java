/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateUpdateLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 22 */   StateUpdateMessage updateMessage = null;
/* 23 */   Map<Integer, CreatureData> creatureDataMap = null;
/*    */   
/*    */   public StateUpdateLMToken(StateUpdateMessage updateMessage, Map<Integer, CreatureData> creatureDataMap) {
/* 26 */     this.updateMessage = updateMessage;
/* 27 */     this.creatureDataMap = creatureDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 34 */     CreatureData data = this.creatureDataMap.get(Integer.valueOf(this.updateMessage.getId()));
/* 35 */     Collection<Stat> stats = this.updateMessage.getStats();
/* 36 */     List<StateUpdateMessage.BuffData> buffDatas = this.updateMessage.getBuffDatas();
/*    */     
/* 38 */     if (data != null) {
/* 39 */       if (!stats.isEmpty()) {
/* 40 */         data.updateStats(stats);
/*    */       }
/* 42 */       if (!buffDatas.isEmpty()) {
/* 43 */         data.updateBuffs(buffDatas);
/*    */       
/*    */       }
/*    */     }
/* 47 */     else if (LoadingManager.DEBUG_INFO) {
/* 48 */       System.out.printf("StateUpdateLMToken - couldn't find the creature %d (%s).\n", new Object[] { Integer.valueOf(this.updateMessage.getId()), this.updateMessage.toString() });
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\StateUpdateLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */