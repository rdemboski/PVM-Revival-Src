/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*    */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*    */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*    */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ 
/*    */ public class ClientPlayerEventsHandler
/*    */   extends PlayerEventsAdapter implements DefaultSubscriptionState.ChangeListener {
/*    */   public int lastHealth;
/*    */   private PropNode playerNode;
/*    */   private Integer level;
/*    */   private int lastMana;
/*    */   private PetSlot[] petSlots;
/*    */   
/*    */   public ClientPlayerEventsHandler(ClientPlayer clientPlayer, PropNode playerNode) {
/* 22 */     this.playerNode = playerNode;
/* 23 */     this.level = clientPlayer.getStatSum(Short.valueOf((short)20));
/* 24 */     this.lastHealth = clientPlayer.getStatSum(Short.valueOf((short)12)).intValue();
/* 25 */     this.lastMana = clientPlayer.getStatSum(Short.valueOf((short)14)).intValue();
/* 26 */     this.petSlots = clientPlayer.getPetSlots();
/*    */     
/* 28 */     subscriptionStateChanged(clientPlayer.getSubscriptionState());
/*    */   }
/*    */ 
/*    */   
/*    */   public void subscriptionStateChanged(DefaultSubscriptionState subscriptionState) {
/* 33 */     this.petSlots[0].setSelectable(subscriptionState.isSubscriber());
/* 34 */     this.petSlots[2].setSelectable(subscriptionState.isSubscriber());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateHealth(int currentHealth, int maximumHealth) {
/* 40 */     if (damageReceived(currentHealth)) {
/* 41 */       this.playerNode.damageFloatingText(this.lastHealth - currentHealth);
/*    */     }
/*    */ 
/*    */     
/* 45 */     this.lastHealth = currentHealth;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateMana(int currentMana, int maximumMana) {
/* 50 */     if (currentMana > this.lastMana) {
/* 51 */       this.playerNode.gainManaFloatingText(currentMana - this.lastMana);
/*    */     }
/* 53 */     this.lastMana = currentMana;
/*    */   }
/*    */   
/*    */   private boolean wasHealed(int currentHealth) {
/* 57 */     return (currentHealth > this.lastHealth);
/*    */   }
/*    */   
/*    */   public void updateLevel(int currentLevel) {
/* 61 */     if (this.level.intValue() != currentLevel) {
/* 62 */       this.playerNode.playDfx("xml/dfx/levelup.xml");
/* 63 */       MainGameState.getRewardWindowController().addReward(Integer.valueOf(currentLevel));
/* 64 */       this.level = Integer.valueOf(currentLevel);
/* 65 */       MainGameState.getTips().put("tutorial.description.levelup");
/*    */       
/* 67 */       if (!MainGameState.isPlayerSubscriber() && this.level.intValue() >= 30) {
/* 68 */         SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", MainGameState.isPlayerRegistered() ? "popup.subscribe.maxlevel" : "popup.subscribe.maxlevel.notsaved");
/*    */ 
/*    */ 
/*    */         
/* 72 */         window.setLayer(101);
/* 73 */         BuiSystem.getRootNode().addWindow((BWindow)window);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean damageReceived(int currentHealth) {
/* 79 */     return (currentHealth < this.lastHealth);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\ClientPlayerEventsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */