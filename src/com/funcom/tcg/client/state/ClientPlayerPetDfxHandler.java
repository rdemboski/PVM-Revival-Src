/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.utils.LoadingScreenListener;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*    */ 
/*    */ public class ClientPlayerPetDfxHandler
/*    */   extends PlayerEventsAdapter
/*    */   implements LoadingScreenListener
/*    */ {
/*    */   private PropNode playerNode;
/*    */   private ClientPlayer clientPlayer;
/* 18 */   private DireEffect petAlwaysOnDfx = null;
/*    */   
/*    */   public ClientPlayerPetDfxHandler(ClientPlayer clientPlayer, PropNode playerNode) {
/* 21 */     this.playerNode = playerNode;
/* 22 */     this.clientPlayer = clientPlayer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void activePetChanged() {
/* 29 */     this.playerNode.playDfx("xml/dfx/switchpet.xml");
/* 30 */     updatePetAlwaysOnDfx();
/*    */   }
/*    */ 
/*    */   
/*    */   public void respawned() {
/* 35 */     updatePetAlwaysOnDfx();
/*    */   }
/*    */   
/*    */   private void updatePetAlwaysOnDfx() {
/* 39 */     if (this.petAlwaysOnDfx != null)
/*    */     {
/* 41 */       this.petAlwaysOnDfx.forceFinish();
/*    */     }
/*    */ 
/*    */     
/* 45 */     this.petAlwaysOnDfx = null;
/* 46 */     String alwaysOnDfxPath = this.clientPlayer.getActivePet().getPetVisuals().getAlwaysOnDfx();
/* 47 */     if (alwaysOnDfxPath.isEmpty())
/*    */       return; 
/*    */     try {
/* 50 */       DireEffectDescription alwaysOnDfxDescription = this.playerNode.getEffectDescriptionFactory().getDireEffectDescription(alwaysOnDfxPath, false);
/* 51 */       if (!alwaysOnDfxDescription.isEmpty()) {
/*    */         
/* 53 */         this.petAlwaysOnDfx = alwaysOnDfxDescription.createInstance(this.playerNode, UsageParams.EMPTY_PARAMS);
/* 54 */         this.playerNode.addDfx(this.petAlwaysOnDfx);
/*    */       } 
/* 56 */     } catch (NoSuchDFXException e) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void notifyLoadingScreenStarted(String toLoadMapName) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 68 */     updatePetAlwaysOnDfx();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\ClientPlayerPetDfxHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */