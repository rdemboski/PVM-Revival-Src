/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.TargetProviderMap;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MapSupport
/*    */   implements MapSupportable, LifeCycleAware {
/*    */   private final RpgTargetProviderEntity owner;
/*    */   private final WorldCoordinate position;
/*    */   private TargetProviderMap currentMap;
/*    */   private boolean movementOverride = false;
/*    */   private int instanceId;
/*    */   
/*    */   public MapSupport(RpgTargetProviderEntity owner, WorldCoordinate position) {
/* 18 */     this.owner = owner;
/* 19 */     this.position = position;
/*    */   }
/*    */   
/*    */   public List<? extends TargetProvider> findObjects(RectangleWC rect) {
/* 23 */     return this.currentMap.findObjects(rect);
/*    */   }
/*    */   
/*    */   public List<? extends TargetProvider> getAll() {
/* 27 */     return this.currentMap.getAll();
/*    */   }
/*    */   
/*    */   public TargetProviderMap getCurrentMap() {
/* 31 */     return this.currentMap;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 35 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCurrentMap(TargetProviderMap currentMap) {
/* 40 */     if (this.currentMap != null) {
/* 41 */       this.currentMap.remove(this.owner);
/*    */     }
/*    */     
/* 44 */     if (currentMap != null) {
/* 45 */       currentMap.add(this.owner);
/* 46 */       this.position.setMapId(currentMap.getMapId());
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 51 */       this.position.setMapId(null);
/*    */     } 
/*    */     
/* 54 */     this.currentMap = currentMap;
/*    */   }
/*    */   
/*    */   public void add(RpgTargetProviderEntity owner) {
/* 58 */     this.currentMap.add(owner);
/*    */   }
/*    */   
/*    */   public void remove(RpgTargetProviderEntity owner) {
/* 62 */     this.currentMap.remove(owner);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMapId() {
/* 67 */     return this.currentMap.getMapId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws Exception {}
/*    */   
/*    */   public void dispose() {
/* 74 */     setCurrentMap(null);
/*    */   }
/*    */   
/*    */   public boolean isMovementOverride() {
/* 78 */     return this.movementOverride;
/*    */   }
/*    */   
/*    */   public void setMovementOverride(boolean movementOverride) {
/* 82 */     this.movementOverride = movementOverride;
/*    */   }
/*    */   
/*    */   public int getInstanceId() {
/* 86 */     return this.instanceId;
/*    */   }
/*    */   
/*    */   public void setInstanceId(int instanceId) {
/* 90 */     this.instanceId = instanceId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\MapSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */