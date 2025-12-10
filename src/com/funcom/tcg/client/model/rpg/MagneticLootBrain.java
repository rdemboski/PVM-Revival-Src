/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.ai.AbstractBrain;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.ConnectedWithMagneticLootMessage;
/*    */ 
/*    */ public class MagneticLootBrain
/*    */   extends AbstractBrain
/*    */ {
/* 14 */   private float speed = 0.0F;
/*    */   
/*    */   private boolean triggered = false;
/*    */   private Creature creature;
/*    */   private int lootID;
/*    */   
/*    */   public MagneticLootBrain(Creature creature, int lootID) {
/* 21 */     this.creature = creature;
/* 22 */     this.lootID = lootID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 27 */     return "Magnetic Loot Brain";
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 32 */     if (this.triggered)
/*    */       return; 
/* 34 */     WorldCoordinate worldCoordinate = this.creature.getPosition();
/* 35 */     WorldCoordinate wcTarget = MainGameState.getPlayerNode().getPosition().clone().subtract(worldCoordinate);
/* 36 */     this.speed += time;
/* 37 */     wcTarget.normalize();
/* 38 */     wcTarget.multLocal((this.speed / 100.0F));
/*    */     
/* 40 */     double realDistance = MainGameState.getPlayerNode().getPosition().distanceTo(this.creature.getPosition());
/*    */     
/* 42 */     double radius = MainGameState.getPlayerModel().getRadius();
/* 43 */     if (realDistance < radius) sendContactMessage();
/*    */     
/* 45 */     worldCoordinate.add(wcTarget);
/* 46 */     this.creature.setPosition(worldCoordinate);
/*    */   }
/*    */ 
/*    */   
/*    */   private void sendContactMessage() {
/* 51 */     this.triggered = true;
/*    */     
/* 53 */     ConnectedWithMagneticLootMessage connectedWithMagneticLootMessage = new ConnectedWithMagneticLootMessage(this.lootID);
/*    */     
/*    */     try {
/* 56 */       NetworkHandler.instance().getIOHandler().send((Message)connectedWithMagneticLootMessage);
/* 57 */     } catch (InterruptedException e) {
/* 58 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\MagneticLootBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */