/*    */ package com.funcom.tcg.client.net.creaturebuilders;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.command.Destination;
/*    */ import com.funcom.gameengine.model.command.FixedDestination;
/*    */ import com.funcom.gameengine.model.command.MoveCommand;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.friend.FriendModel;
/*    */ import com.funcom.tcg.net.message.DuelRequestMessage;
/*    */ 
/*    */ public class PlayerActionHandler extends UserActionHandler {
/*    */   private ClientPlayer clientPlayer;
/*    */   private MouseOver mouseOver;
/*    */   
/*    */   public PlayerActionHandler(ClientPlayer clientPlayer, MouseOver mouseOver) {
/* 28 */     this.clientPlayer = clientPlayer;
/* 29 */     this.mouseOver = mouseOver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleLeftMousePress(WorldCoordinate pressedCoord) {
/* 36 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*    */     
/* 38 */     if (localClientPlayer.isDoingAnythingExceptMoving()) {
/*    */       return;
/*    */     }
/*    */     
/* 42 */     if (TcgGame.isAddFriendMode()) {
/* 43 */       FriendModel friendModel = MainGameState.getFriendModel();
/* 44 */       friendModel.searchPlayerResult(this.clientPlayer.getExternalChatId(), this.clientPlayer.getName());
/* 45 */     } else if (TcgGame.isStartDuelMode()) {
/* 46 */       GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 47 */       CreatureData creatureData = currentState.getPlayerData(this.clientPlayer.getId());
/* 48 */       if (Math.abs(((Integer)creatureData.getStats().get(Short.valueOf((short)20))).intValue() - MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20)).intValue()) <= TcgGame.getRpgLoader().getPvPLevelRange()) {
/*    */         try {
/* 50 */           NetworkHandler.instance().getIOHandler().send((Message)new DuelRequestMessage(this.clientPlayer.getId()));
/* 51 */           MainGameState.getMainHud().getDuelCancelButton().setVisible(true);
/* 52 */           DfxTextWindowManager.instance().getWindow("main").showText(String.format(TcgGame.getLocalizedText("duel.request.sent", new String[0]), new Object[] { Integer.valueOf(TcgGame.getRpgLoader().getPvPLevelRange()) }));
/* 53 */         } catch (InterruptedException e) {
/* 54 */           throw new RuntimeException(e);
/*    */         } 
/*    */       } else {
/*    */         
/* 58 */         DfxTextWindowManager.instance().getWindow("main").showText(String.format(TcgGame.getLocalizedText("duel.level.limit", new String[0]), new Object[] { Integer.valueOf(TcgGame.getRpgLoader().getPvPLevelRange()) }));
/*    */       } 
/* 60 */       TcgGame.setStartDuelMode(false);
/*    */     } else {
/*    */       
/* 63 */       FixedDestination destination = new FixedDestination(pressedCoord, (Creature)localClientPlayer);
/* 64 */       localClientPlayer.immediateCommand((Command)new MoveCommand(MainGameState.getCollisionDataProvider().getCollisionRoot(), (Destination)destination));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClickable() {
/* 70 */     return (TcgGame.isAddFriendMode() || TcgGame.isStartDuelMode());
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMouseEnter() {
/* 75 */     if (TcgGame.isAddFriendMode() || TcgGame.isStartDuelMode()) {
/* 76 */       this.mouseOver.mouseEntered();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMouseExit() {
/* 82 */     this.mouseOver.mouseExited();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\PlayerActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */