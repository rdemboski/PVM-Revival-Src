/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.controllers.InterpolationController;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.PositionUpdateMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionUpdateLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 27 */   PositionUpdateMessage posMessage = null;
/* 28 */   GameIOHandler ioHandler = null;
/* 29 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 30 */   Map<Integer, CreatureData> playerDataMap = null;
/*    */ 
/*    */   
/*    */   public PositionUpdateLMToken(PositionUpdateMessage posMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 34 */     this.posMessage = posMessage;
/* 35 */     this.ioHandler = ioHandler;
/* 36 */     this.creatureDataMap = creatureDataMap;
/* 37 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/* 43 */     int identity = this.posMessage.getIdentity();
/* 44 */     WorldCoordinate newPosition = reconstructWorldCoordinate(this.posMessage);
/* 45 */     float angle = this.posMessage.getAngle();
/* 46 */     PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(identity));
/*    */     
/* 48 */     if (prop != null) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 55 */       InterpolationController cont = GameOperationState.findInterpolationController(prop);
/* 56 */       if (cont == null) {
/* 57 */         prop.getProp().setPosition(newPosition);
/* 58 */         prop.getProp().setRotation(angle);
/*    */       } else {
/* 60 */         cont.moveToPosition(newPosition, angle);
/*    */       } 
/*    */       
/* 63 */       if (prop.getProp() instanceof Creature) {
/* 64 */         Creature c = (Creature)prop.getProp();
/* 65 */         Command cmd = c.getCurrentCommand();
/* 66 */         if (cmd instanceof com.funcom.gameengine.model.command.MoveCommand) {
/* 67 */           c.clearQueue();
/*    */         }
/*    */       } 
/*    */     } 
/* 71 */     return 3;
/*    */   }
/*    */   
/*    */   private WorldCoordinate reconstructWorldCoordinate(PositionUpdateMessage posMessage) {
/* 75 */     WorldCoordinate worldCoordinate = posMessage.getWorldCoordinate();
/* 76 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 77 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 78 */     return worldCoordinate;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PositionUpdateLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */