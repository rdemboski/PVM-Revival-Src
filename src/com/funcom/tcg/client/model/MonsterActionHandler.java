/*    */ package com.funcom.tcg.client.model;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.command.Destination;
/*    */ import com.funcom.gameengine.model.command.MoveCommand;
/*    */ import com.funcom.gameengine.model.command.RangeMovement;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.spatial.LineNode;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ public class MonsterActionHandler
/*    */   extends UserActionHandler
/*    */ {
/*    */   private Creature monster;
/*    */   private LineNode rootCollisionLine;
/*    */   private MouseOver mouseOver;
/*    */   
/*    */   public MonsterActionHandler(Creature monster, LineNode rootCollisionLine, MouseOver mouseOver) {
/* 26 */     this.monster = monster;
/* 27 */     this.rootCollisionLine = rootCollisionLine;
/* 28 */     this.mouseOver = mouseOver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleLeftMousePress(WorldCoordinate pressedCoord) {
/* 33 */     moveAndPeform(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleRightMousePress() {
/* 38 */     performSkill(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleKeyPress(int keycode) {
/* 43 */     if (keycode == 2) {
/* 44 */       performSkill(2);
/* 45 */     } else if (keycode == 3) {
/* 46 */       performSkill(3);
/* 47 */     } else if (keycode == 4) {
/* 48 */       performSkill(4);
/*    */     } 
/*    */   }
/*    */   public void handleMouseEnter() {
/* 52 */     this.mouseOver.mouseEntered();
/*    */   }
/*    */   
/*    */   public void handleMouseExit() {
/* 56 */     this.mouseOver.mouseExited();
/*    */   }
/*    */   
/*    */   private void performSkill(int slotId) {
/* 60 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 61 */     MainGameState.getPlayerModel().immediateCommand(new UseSkillCommand(slotId, (ClientPlayer)localClientPlayer, TcgGame.getDireEffectDescriptionFactory(), new WorldCoordinateDistanceCalculator(localClientPlayer.getPosition(), this.monster.getPosition())));
/*    */   }
/*    */   
/*    */   private void moveAndPeform(int slotId) {
/* 65 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*    */ 
/*    */     
/* 68 */     if (localClientPlayer.isDoingAnythingExceptMoving()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 73 */     RangeMovement rangeMovement = new RangeMovement((InteractibleProp)this.monster, (Creature)localClientPlayer, this.monster.getRadius() + localClientPlayer.getRadius(), 0.0D);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     if (rangeMovement.getTarget().distanceTo(localClientPlayer.getPosition()) >= 0.15D || Math.abs(rangeMovement.getPreferredAngle() - localClientPlayer.getRotation()) > 0.15D)
/*    */     {
/* 84 */       localClientPlayer.immediateCommand((Command)new MoveCommand(this.rootCollisionLine, (Destination)rangeMovement, localClientPlayer.getSpeed()));
/*    */     }
/*    */     
/* 87 */     if (!localClientPlayer.isDoingAnything() || !localClientPlayer.hasQueuedCommands())
/* 88 */       localClientPlayer.queueCommand(new UseSkillCommand(slotId, (ClientPlayer)localClientPlayer, TcgGame.getDireEffectDescriptionFactory(), new WorldCoordinateDistanceCalculator(localClientPlayer.getPosition(), this.monster.getPosition()))); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\MonsterActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */