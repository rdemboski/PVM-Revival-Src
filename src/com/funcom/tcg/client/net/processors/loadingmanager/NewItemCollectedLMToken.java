/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.NewItemCollectedMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewItemCollectedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 17 */   NewItemCollectedMessage itemMessage = null;
/*    */   
/*    */   public NewItemCollectedLMToken(NewItemCollectedMessage itemMessage) {
/* 20 */     this.itemMessage = itemMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/* 25 */     ItemDescription itemDesc = TcgGame.getRpgLoader().getItemManager().getDescription(this.itemMessage.getItemId(), this.itemMessage.getTier());
/*    */     
/* 27 */     MainGameState.getRenderPassManager().getGuiParticlesRenderPass().triggerParticleEffect("itemCollected");
/* 28 */     MainGameState.getHudModel().triggerItemPickup(itemDesc);
/*    */     
/* 30 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\NewItemCollectedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */