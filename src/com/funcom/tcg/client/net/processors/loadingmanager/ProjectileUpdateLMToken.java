/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.ProjectileUpdateMessage;
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
/*    */ public class ProjectileUpdateLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 31 */   GameIOHandler ioHandler = null;
/* 32 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 33 */   Map<Integer, CreatureData> playerDataMap = null;
/* 34 */   ProjectileUpdateMessage projectileUpdateMessage = null;
/*    */ 
/*    */   
/*    */   public ProjectileUpdateLMToken(ProjectileUpdateMessage projectileUpdateMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 38 */     this.projectileUpdateMessage = projectileUpdateMessage;
/* 39 */     this.ioHandler = ioHandler;
/* 40 */     this.creatureDataMap = creatureDataMap;
/* 41 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 47 */     List<ProjectileUpdateMessage.NewProjectileData> newProjectileDatas = this.projectileUpdateMessage.getNewDataList();
/*    */     
/* 49 */     for (ProjectileUpdateMessage.NewProjectileData projectileData : newProjectileDatas) {
/* 50 */       MainGameState.getProjectileFactory().createProjectile(projectileData);
/*    */     }
/*    */     
/* 53 */     List<ProjectileUpdateMessage.ProjectileUpdateData> dataList = this.projectileUpdateMessage.getProjectileUpdateDataList();
/* 54 */     if (dataList != null && !dataList.isEmpty()) {
/* 55 */       for (ProjectileUpdateMessage.ProjectileUpdateData projectileUpdateData : dataList) {
/* 56 */         MainGameState.getProjectileFactory().updateProjectile(projectileUpdateData);
/*    */       }
/*    */     }
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ProjectileUpdateLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */