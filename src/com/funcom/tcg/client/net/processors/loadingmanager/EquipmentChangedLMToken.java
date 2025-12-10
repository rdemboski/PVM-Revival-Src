/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.EquipmentChangedMessage;
/*    */ 
/*    */ 
/*    */ public class EquipmentChangedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/*    */   EquipmentChangedMessage equipmentChangedMessage;
/*    */   
/*    */   public EquipmentChangedLMToken(EquipmentChangedMessage equipmentChangedMessage) {
/* 18 */     this.equipmentChangedMessage = equipmentChangedMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 24 */     PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(this.equipmentChangedMessage.getPlayerId()));
/* 25 */     if (prop != null) {
/* 26 */       ClientPlayer otherPlayer = (ClientPlayer)prop.getProp();
/*    */       
/* 28 */       if (this.equipmentChangedMessage.isEquipped()) {
/* 29 */         ClientItem item = MainGameState.getItemRegistry().getItemForClassID(this.equipmentChangedMessage.getItemId(), this.equipmentChangedMessage.getTier());
/* 30 */         otherPlayer.getEquipDoll().setItem(item);
/*    */       } else {
/* 32 */         otherPlayer.getEquipDoll().removeItem(this.equipmentChangedMessage.getPlacement());
/*    */       } 
/*    */     } 
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\EquipmentChangedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */