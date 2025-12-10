/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AudioPlacementManager
/*    */ {
/* 12 */   private static final AudioPlacementManager INSTANCE = new AudioPlacementManager();
/*    */   private WorldCoordinate reference;
/*    */   
/*    */   public static AudioPlacementManager getInstance() {
/* 16 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void setReferenceCoord(WorldCoordinate reference) {
/* 20 */     this.reference = reference;
/*    */   }
/*    */   
/*    */   public void update(WorldCoordinate sourceCoord, List<Sound> sounds) {
/* 24 */     WorldCoordinate ref = this.reference;
/*    */     
/* 26 */     if (ref != null && sounds != null && !sounds.isEmpty()) {
/* 27 */       float x = (float)((sourceCoord.getX().getTileCoord() - ref.getX().getTileCoord()) + sourceCoord.getX().getTileOffset() - ref.getX().getTileOffset());
/*    */       
/* 29 */       float y = (float)((sourceCoord.getY().getTileCoord() - ref.getY().getTileCoord()) + sourceCoord.getY().getTileOffset() - ref.getY().getTileOffset());
/*    */       
/* 31 */       int i = sounds.size();
/* 32 */       for (int j = 0; j < i; j++) {
/* 33 */         Sound sound = sounds.get(j);
/* 34 */         sound.getPos().setX(x);
/* 35 */         sound.getPos().setZ(y);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update(WorldCoordinate sourceCoord, Sound sound) {
/* 41 */     WorldCoordinate ref = this.reference;
/*    */     
/* 43 */     if (ref != null && sound != null) {
/* 44 */       float x = (float)((sourceCoord.getX().getTileCoord() - ref.getX().getTileCoord()) + sourceCoord.getX().getTileOffset() - ref.getX().getTileOffset());
/*    */       
/* 46 */       float y = (float)((sourceCoord.getY().getTileCoord() - ref.getY().getTileCoord()) + sourceCoord.getY().getTileOffset() - ref.getY().getTileOffset());
/*    */       
/* 48 */       sound.getPos().setX(x);
/* 49 */       sound.getPos().setZ(y);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\AudioPlacementManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */