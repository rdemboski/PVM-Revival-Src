/*    */ package com.funcom.tcg.client.net.projectiles;
/*    */ 
/*    */ import com.funcom.gameengine.view.Projectile;
/*    */ import com.funcom.tcg.net.message.ProjectileUpdateMessage;
/*    */ import com.funcom.tcg.rpg.AbstractTCGRpgLoader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProjectilesFactory
/*    */ {
/*    */   private Map<String, ProjectileBuilder> projectileBuilders;
/*    */   private Map<String, String> projectilePaths;
/*    */   
/*    */   public ProjectilesFactory(AbstractTCGRpgLoader rpgLoader) {
/* 20 */     this.projectileBuilders = new HashMap<String, ProjectileBuilder>();
/* 21 */     this.projectilePaths = new HashMap<String, String>();
/* 22 */     loadProjectilePaths(rpgLoader);
/*    */   }
/*    */   
/*    */   private void loadProjectilePaths(AbstractTCGRpgLoader rpgLoader) {
/* 26 */     this.projectilePaths.putAll(rpgLoader.getProjectilePathMap());
/*    */   }
/*    */   
/*    */   public Projectile createProjectile(ProjectileUpdateMessage.NewProjectileData projectileData) {
/* 30 */     String projectileId = projectileData.getProjectileId();
/*    */     
/* 32 */     if (!this.projectilePaths.containsKey(projectileId)) {
/* 33 */       throw new IllegalStateException("Don't know how to build projectile for this ID: " + projectileData.getProjectileId());
/*    */     }
/*    */     
/* 36 */     ProjectileBuilder projectileBuilder = this.projectileBuilders.get(projectileId);
/* 37 */     if (projectileBuilder == null) {
/* 38 */       projectileBuilder = new DefaultProjectileBuilder(this.projectilePaths.get(projectileId));
/* 39 */       this.projectileBuilders.put(projectileId, projectileBuilder);
/*    */     } 
/* 41 */     return projectileBuilder.loadAndFireProjectile(projectileData);
/*    */   }
/*    */   
/*    */   public void killProjectile(ProjectileUpdateMessage.RemovedProjectileData projectileData) {
/* 45 */     for (ProjectileBuilder projectileBuilder : this.projectileBuilders.values()) {
/* 46 */       if (projectileBuilder.isRunning(projectileData.getId())) {
/* 47 */         projectileBuilder.killProjectile(projectileData);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void updateProjectile(ProjectileUpdateMessage.ProjectileUpdateData projectileUpdateData) {
/* 54 */     for (ProjectileBuilder projectileBuilder : this.projectileBuilders.values()) {
/* 55 */       if (projectileBuilder.isRunning(projectileUpdateData.getId())) {
/* 56 */         projectileBuilder.updateProjectile(projectileUpdateData);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\projectiles\ProjectilesFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */