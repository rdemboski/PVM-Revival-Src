/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.command.Destination;
/*    */ import com.funcom.gameengine.model.command.InteractCommand;
/*    */ import com.funcom.gameengine.model.command.MoveCommand;
/*    */ import com.funcom.gameengine.model.command.RangeMovement;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.spatial.LineNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultActionInteractActionHandler
/*    */   extends UserActionHandler
/*    */ {
/*    */   private InteractibleProp interactibleProp;
/*    */   private String defaultActionName;
/*    */   private Creature player;
/*    */   private LineNode collisionRootLine;
/*    */   private MouseOver mouseOver;
/*    */   
/*    */   public DefaultActionInteractActionHandler(InteractibleProp interactibleProp, Creature player, String defaultActionName, LineNode collisionRootLine) {
/* 29 */     this(interactibleProp, player, defaultActionName, collisionRootLine, null);
/*    */   }
/*    */   
/*    */   public DefaultActionInteractActionHandler(InteractibleProp interactibleProp, Creature player, String defaultActionName, LineNode collisionRootLine, MouseOver mouseOver) {
/* 33 */     this.player = player;
/* 34 */     this.interactibleProp = interactibleProp;
/* 35 */     this.defaultActionName = defaultActionName;
/* 36 */     this.collisionRootLine = collisionRootLine;
/* 37 */     this.mouseOver = mouseOver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleLeftMousePress(WorldCoordinate pressedCoord) {
/* 42 */     if (this.player.hasQueuedCommands())
/*    */       return; 
/* 44 */     double startSpeed = 0.0D;
/* 45 */     if (this.player.getCurrentCommand() instanceof MoveCommand) {
/* 46 */       MoveCommand command = (MoveCommand)this.player.getCurrentCommand();
/* 47 */       startSpeed = command.getCurrentSpeed();
/*    */     } 
/* 49 */     this.player.immediateCommand((Command)new MoveCommand(this.collisionRootLine, (Destination)new RangeMovement(this.interactibleProp, this.player, this.player.getRadius() + this.interactibleProp.getRadius(), 0.0D), startSpeed));
/*    */     
/* 51 */     this.player.queueCommand((Command)new InteractCommand(this.interactibleProp, this.player, this.defaultActionName));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClickable() {
/* 56 */     if (this.interactibleProp.hasAction(this.defaultActionName)) {
/* 57 */       Action action = this.interactibleProp.getActionByName(this.defaultActionName);
/* 58 */       return action.isClickable();
/*    */     } 
/* 60 */     return super.isClickable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMouseEnter() {
/* 65 */     if (this.mouseOver != null) {
/* 66 */       this.mouseOver.mouseEntered();
/*    */     }
/*    */   }
/*    */   
/*    */   public void handleMouseExit() {
/* 71 */     if (this.mouseOver != null)
/* 72 */       this.mouseOver.mouseExited(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\DefaultActionInteractActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */