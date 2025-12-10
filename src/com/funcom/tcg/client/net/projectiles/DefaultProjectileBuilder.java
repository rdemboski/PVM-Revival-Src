/*    */ package com.funcom.tcg.client.net.projectiles;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.WorldOrigin;
/*    */ import com.funcom.gameengine.WorldUtils;
/*    */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*    */ import com.funcom.gameengine.view.DefaultProjectile;
/*    */ import com.funcom.gameengine.view.Projectile;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.ProjectileUpdateMessage;
/*    */ import com.jme.math.Vector3f;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultProjectileBuilder
/*    */   implements ProjectileBuilder
/*    */ {
/*    */   String dfxName;
/*    */   protected Map<Integer, Projectile> projectileMap;
/*    */   
/*    */   public DefaultProjectileBuilder(String dfxName) {
/* 31 */     this.dfxName = dfxName;
/* 32 */     this.projectileMap = new ConcurrentHashMap<Integer, Projectile>();
/*    */   }
/*    */   
/*    */   public Projectile loadAndFireProjectile(ProjectileUpdateMessage.NewProjectileData projectileData) {
/*    */     DefaultProjectile defaultProjectile;
/* 37 */     Projectile projectile = this.projectileMap.get(Integer.valueOf(projectileData.getId()));
/* 38 */     if (projectile == null) {
/*    */       
/*    */       try {
/* 41 */         DireEffectDescription description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(this.dfxName, false);
/*    */         
/* 43 */         defaultProjectile = new DefaultProjectile(projectileData.getId(), description, this.dfxName, MainGameState.getWorld(), this.projectileMap, TcgGame.getDireEffectDescriptionFactory());
/*    */         
/* 45 */         WorldCoordinate p = projectileData.getPos();
/* 46 */         Vector2d v = projectileData.getVelocity();
/*    */         
/* 48 */         float fromY = WorldUtils.getScreenY(p);
/* 49 */         float fromX = WorldUtils.getScreenX(p);
/*    */         
/* 51 */         Vector2d travelDistance = v.multRet(projectileData.getAliveTimerMillis() / 1000.0D);
/*    */         
/* 53 */         float toX = (float)(fromX + travelDistance.getX());
/* 54 */         float toY = (float)(fromY + travelDistance.getY());
/*    */         
/* 56 */         float aliveTime = (float)(projectileData.getAliveTimerMillis() / 1000.0D);
/*    */         
/* 58 */         defaultProjectile.launchProjectile(MainGameState.getWorld(), new Vector3f(WorldOrigin.instance().getX() + fromX, 0.0F, WorldOrigin.instance().getY() + fromY), new Vector3f(WorldOrigin.instance().getX() + toX, 0.0F, WorldOrigin.instance().getY() + toY), aliveTime);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 64 */         this.projectileMap.put(Integer.valueOf(projectileData.getId()), defaultProjectile);
/* 65 */       } catch (NoLocatorException e) {
/* 66 */         e.printStackTrace();
/* 67 */       } catch (NoSuchDFXException e) {
/* 68 */         e.printStackTrace();
/*    */       } 
/*    */     }
/* 71 */     return (Projectile)defaultProjectile;
/*    */   }
/*    */   
/*    */   public void killProjectile(ProjectileUpdateMessage.RemovedProjectileData projectileData) {
/* 75 */     Projectile projectile = this.projectileMap.get(Integer.valueOf(projectileData.getId()));
/* 76 */     if (projectile != null) {
/* 77 */       projectile.killProjectile(MainGameState.getWorld());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateProjectile(ProjectileUpdateMessage.ProjectileUpdateData projectileUpdateData) {
/* 83 */     Projectile projectile = this.projectileMap.get(Integer.valueOf(projectileUpdateData.getId()));
/* 84 */     if (projectile != null) {
/* 85 */       WorldCoordinate targetPostion = projectileUpdateData.getTargetPostion();
/* 86 */       float targetX = WorldUtils.getScreenX(targetPostion);
/* 87 */       float targetY = WorldUtils.getScreenY(targetPostion);
/*    */       
/* 89 */       double aliveTime = projectileUpdateData.getAliveTimerMillis() / 1000.0D;
/*    */       
/* 91 */       projectile.updateProjectileTarget(new Vector3f(WorldOrigin.instance().getX() + targetX, 0.0F, WorldOrigin.instance().getY() + targetY), aliveTime);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isRunning(int id) {
/* 96 */     return this.projectileMap.containsKey(Integer.valueOf(id));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\projectiles\DefaultProjectileBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */