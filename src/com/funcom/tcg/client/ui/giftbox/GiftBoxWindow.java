/*    */ package com.funcom.tcg.client.ui.giftbox;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.AbstractTcgWindow;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.funcom.tcg.client.ui.quest2.RewardCardsContainer;
/*    */ import com.jmex.bui.BButton;
/*    */ import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.event.ActionEvent;
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class GiftBoxWindow extends AbstractTcgWindow {
/* 18 */   public static final Rectangle REWARD_ITEMS_BOUNDS = new Rectangle(0, 100, 500, 113);
/* 19 */   public static final Rectangle REWARD_CARD_SIZE = new Rectangle(0, 0, 81, 120);
/*    */   
/*    */   private GiftBoxModel giftBoxModel;
/*    */   private RewardCardsContainer rewardCardsPane;
/*    */   
/*    */   public void setBounds(int x, int y, int width, int height) {
/* 25 */     super.setBounds(x, y, width, height);
/*    */   }
/*    */   
/*    */   public GiftBoxWindow(GiftBoxModel giftBoxModel, ResourceManager resourceManager) {
/* 29 */     super(resourceManager);
/* 30 */     this.giftBoxModel = giftBoxModel;
/* 31 */     BContainer clientArea = getClientArea();
/*    */     
/* 33 */     setBounds(0, 0, 500, 300);
/*    */     
/* 35 */     this.rewardCardsPane = new RewardCardsContainer(resourceManager, MainGameState.getToolTipManager());
/* 36 */     this.rewardCardsPane.setStyleClass("reward-cards-container");
/* 37 */     add((BComponent)this.rewardCardsPane, REWARD_ITEMS_BOUNDS);
/*    */     
/* 39 */     BButton button = new BButton("");
/* 40 */     button.setStyleClass("ok_button");
/* 41 */     add((BComponent)button, new Rectangle(223, 20, 54, 54));
/* 42 */     button.addListener((ComponentListener)new ActionListener()
/*    */         {
/*    */           public void actionPerformed(ActionEvent event) {
/* 45 */             PanelManager.getInstance().closeWindow((BWindow)GiftBoxWindow.this);
/*    */           }
/*    */         });
/*    */     
/* 49 */     enableTitle(false);
/*    */     
/* 51 */     BLabel titleLabel = new BLabel(giftBoxModel.getTitle());
/* 52 */     BImage icon = (BImage)resourceManager.getResource(BImage.class, giftBoxModel.getIcon());
/* 53 */     titleLabel.setIcon((BIcon)new ImageIcon(icon));
/* 54 */     titleLabel.setStyleClass("title-label");
/* 55 */     add((BComponent)titleLabel, new Rectangle(getWidth() / 2 - 215, 235, 430, 50));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 61 */     this.rewardCardsPane.setCardDataList(this.giftBoxModel.getRewardDataList());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\giftbox\GiftBoxWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */