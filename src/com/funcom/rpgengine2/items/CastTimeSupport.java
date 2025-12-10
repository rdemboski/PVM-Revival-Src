/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import com.funcom.commons.utils.GlobalTime;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.ItemUsageSupport;
/*    */ import com.funcom.rpgengine2.creatures.MapSupport;
/*    */ import com.funcom.rpgengine2.creatures.RpgQueryableSupport;
/*    */ import com.funcom.rpgengine2.creatures.RpgUpdateable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class CastTimeSupport
/*    */   implements RpgQueryableSupport, RpgUpdateable
/*    */ {
/* 16 */   private static final int[] UPDATE_PRIORITIES = new int[] { 499998 };
/* 17 */   public static final Logger LOGGER = Logger.getLogger(CastTimeSupport.class);
/*    */   private Item castingItem;
/*    */   private UsageParams usageParams;
/*    */   private long triggerTime;
/*    */   private WorldCoordinate pointingCoordinate;
/*    */   private CastTimeFeedback feedback;
/*    */   
/*    */   public CastTimeSupport(CastTimeFeedback feedback) {
/* 25 */     this.feedback = feedback;
/* 26 */     this.pointingCoordinate = new WorldCoordinate();
/*    */   }
/*    */   
/*    */   public void startCastTime(Item item, UsageParams usageParams) {
/* 30 */     if (item == null) {
/* 31 */       throw new NullPointerException("item cannot be null");
/*    */     }
/* 33 */     if (usageParams == null) {
/* 34 */       throw new NullPointerException("usageParams cannot be null");
/*    */     }
/*    */     
/* 37 */     this.castingItem = item;
/* 38 */     this.usageParams = usageParams;
/* 39 */     this.triggerTime = GlobalTime.getInstance().getCurrentTime() + (long)(item.getDescription().getCastTime() * 1000.0F);
/* 40 */     this.feedback.startCastTime(item);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 45 */     return UPDATE_PRIORITIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 50 */     long timeUpdate = GlobalTime.getInstance().getCurrentTime();
/* 51 */     if (this.castingItem != null && this.usageParams != null && timeUpdate >= this.triggerTime) {
/* 52 */       WorldCoordinate ownerPos = ((MapSupport)this.castingItem.getOwner().getSupport(MapSupport.class)).getPosition();
/*    */       
/* 54 */       this.usageParams = new UsageParams(ownerPos.distanceTo(this.pointingCoordinate), this.usageParams.getSourcePositionOverride(ownerPos), UsageParams.AngleType.DEFINED, (float)ownerPos.angleTo(this.pointingCoordinate));
/* 55 */       long currentTime = GlobalTime.getInstance().getCurrentTime();
/* 56 */       boolean success = ((ItemUsageSupport)this.castingItem.getOwner().getSupport(ItemUsageSupport.class)).checkUseItemPermission(this.castingItem, currentTime);
/* 57 */       if (success) {
/* 58 */         this.castingItem.triggerEffect(this.usageParams, currentTime);
/*    */       }
/* 60 */       this.feedback.endCastTime(this.castingItem, success);
/* 61 */       this.usageParams = null;
/* 62 */       this.castingItem = null;
/* 63 */       this.triggerTime = 0L;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isCasting() {
/* 68 */     return (this.castingItem != null);
/*    */   }
/*    */   
/*    */   public void setTarget(WorldCoordinate pointingCoordinate) {
/* 72 */     this.pointingCoordinate = pointingCoordinate;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\CastTimeSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */