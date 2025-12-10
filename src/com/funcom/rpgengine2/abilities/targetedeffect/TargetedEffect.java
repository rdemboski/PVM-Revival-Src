/*    */ package com.funcom.rpgengine2.abilities.targetedeffect;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.AbstractBuffSupport;
/*    */ import com.funcom.rpgengine2.creatures.DFXSupport;
/*    */ import com.funcom.rpgengine2.creatures.MapSupport;
/*    */ import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*    */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public abstract class TargetedEffect extends OwnedRpgObject {
/* 15 */   private static final AtomicInteger nextTargetedEffectId = new AtomicInteger();
/*    */   private DireEffect dfx;
/*    */   private final Integer id;
/*    */   private float angle;
/*    */   
/*    */   public TargetedEffect(Item item) {
/* 21 */     this.id = Integer.valueOf(nextTargetedEffectId.getAndIncrement());
/* 22 */     item.setOwner((RpgSourceProviderEntity)this);
/* 23 */     initSupport();
/* 24 */     this.dfx = item.getDescription().getDfxDescription().createInstance(item, UsageParams.EMPTY_PARAMS);
/*    */   }
/*    */   
/*    */   protected void initSupport() {
/* 28 */     addSupport(new DFXSupport());
/*    */   }
/*    */   
/*    */   protected void startDFX() {
/* 32 */     ((DFXSupport)getSupport(DFXSupport.class)).add(this.dfx);
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setAngle(float angle) {
/* 40 */     this.angle = angle;
/*    */   }
/*    */   
/*    */   public float getAngle() {
/* 44 */     return this.angle;
/*    */   }
/*    */   
/*    */   public double getTime() {
/* 48 */     return this.dfx.getLocalTime();
/*    */   }
/*    */   
/*    */   public String getDfxId() {
/* 52 */     return this.dfx.getDescription().getId();
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 56 */     return ((MapSupport)getSupport(MapSupport.class)).getPosition();
/*    */   }
/*    */   
/*    */   public boolean isDestroyed() {
/* 60 */     return (((DFXSupport)getSupport(DFXSupport.class)).getNumberOfRunningDFX() == 0 && ((AbstractBuffSupport)getSupport(AbstractBuffSupport.class)).isEmpty());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\targetedeffect\TargetedEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */