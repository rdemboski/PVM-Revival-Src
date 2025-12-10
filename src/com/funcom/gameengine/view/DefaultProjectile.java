/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.model.World;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultProjectile
/*     */   extends AbstractProjectile
/*     */   implements Projectile
/*     */ {
/*     */   private int id;
/*     */   private Map<Integer, Projectile> projectilesMap;
/*     */   
/*     */   public DefaultProjectile(int id, DireEffectDescription direEffect, String name, World world, Map<Integer, Projectile> projectilesMap, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  26 */     super(name, direEffect, world, effectDescriptionFactory);
/*  27 */     this.id = id;
/*  28 */     this.projectilesMap = projectilesMap;
/*     */   }
/*     */   
/*     */   protected void setAliveTime(float aliveTime) {
/*  32 */     this.aliveTime = aliveTime;
/*     */   }
/*     */   
/*     */   public void launchProjectile(World world, Vector3f source, Vector3f target, float aliveTime) {
/*  36 */     Vector3f vec = target.subtract(source);
/*  37 */     Vector2f xzVec = new Vector2f(vec.getX(), vec.getZ());
/*  38 */     float angle = xzVec.getAngle();
/*     */     
/*  40 */     Quaternion rotation = (new Quaternion()).fromAngleAxis(angle, new Vector3f(0.0F, 1.0F, 0.0F));
/*  41 */     setLocalRotation(rotation);
/*     */     
/*  43 */     setAliveTime(aliveTime);
/*  44 */     setProjectileControllerProperties(source, target, aliveTime);
/*     */   }
/*     */   
/*     */   public void setProjectileControllerProperties(Vector3f source, Vector3f target, float aliveTime) {
/*  48 */     getProjectileController().setObject((Spatial)this, 0, -1);
/*  49 */     getProjectileController().setPosition(0, 0.0F, source);
/*  50 */     getProjectileController().setPosition(0, aliveTime, target);
/*  51 */     getProjectileController().interpolateMissing();
/*  52 */     updateRenderState();
/*     */   }
/*     */   
/*     */   protected void cleanup() {
/*  56 */     this.projectilesMap.remove(Integer.valueOf(this.id));
/*     */   }
/*     */   
/*     */   public void killProjectile(World world) {
/*  60 */     clearFromWorld(world);
/*     */   }
/*     */   
/*     */   public void updateProjectileTarget(Vector3f target, double aliveTime) {
/*  64 */     getProjectileController().update(0.0F);
/*  65 */     setAliveTime((float)aliveTime);
/*  66 */     (getProjectileController()).keyframes.clear();
/*     */     
/*  68 */     Vector3f currentPos = getLocalTranslation();
/*     */     
/*  70 */     Vector3f vec = target.subtract(currentPos);
/*  71 */     Vector2f xzVec = new Vector2f(vec.getX(), vec.getZ());
/*  72 */     float angle = xzVec.getAngle();
/*     */     
/*  74 */     Quaternion rotation = (new Quaternion()).fromAngleAxis(angle, new Vector3f(0.0F, 1.0F, 0.0F));
/*  75 */     setLocalRotation(rotation);
/*  76 */     getProjectileController().setRotation(0, (float)aliveTime, rotation);
/*     */     
/*  78 */     getProjectileController().setPosition(0, 0.0F, currentPos);
/*  79 */     getProjectileController().setPosition(0, (float)aliveTime, target);
/*  80 */     getProjectileController().interpolateMissing();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateSounds() {
/*  86 */     super.updateSounds();
/*     */     
/*  88 */     WorldCoordinate logicalCoord = getLogicalCoord();
/*  89 */     AudioPlacementManager.getInstance().update(logicalCoord, this.sounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSound(Sound sound) {
/*  94 */     WorldCoordinate logicalCoord = getLogicalCoord();
/*  95 */     AudioPlacementManager.getInstance().update(logicalCoord, sound);
/*     */     
/*  97 */     super.registerSound(sound);
/*     */   }
/*     */   
/*     */   private WorldCoordinate getLogicalCoord() {
/* 101 */     WorldCoordinate tmp = new WorldCoordinate();
/* 102 */     Vector3f view = getWorldTranslation();
/*     */     
/* 104 */     WorldOrigin.instance().viewToLogicCoordX(view.x, tmp);
/* 105 */     WorldOrigin.instance().viewToLogicCoordZ(view.z, tmp);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     return tmp;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\DefaultProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */