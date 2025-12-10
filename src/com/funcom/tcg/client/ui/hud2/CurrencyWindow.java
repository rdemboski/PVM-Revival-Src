/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.ui.BuiUtils;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.jmex.bui.BClickthroughLabel;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class CurrencyWindow
/*    */   extends BWindow
/*    */   implements Inventory.ChangeListener
/*    */ {
/*    */   private BClickthroughLabel coinTotal;
/*    */   private BClickthroughLabel tokenTotal;
/*    */   
/*    */   public CurrencyWindow(ResourceManager resourceManager, Inventory inventory, int x, int y, int coins, int tokens) {
/* 27 */     super(null, (BLayoutManager)new AbsoluteLayout());
/* 28 */     this._style = BuiUtils.createMergedClassStyleSheets(CurrencyWindow.class, new BananaResourceProvider(resourceManager));
/*    */     
/* 30 */     initComponents();
/* 31 */     setBounds(x, y, 300, 30);
/* 32 */     inventory.addChangeListener(this);
/*    */     
/* 34 */     this.coinTotal.setText("" + coins);
/* 35 */     this.tokenTotal.setText("" + tokens);
/*    */   }
/*    */   
/*    */   private void initComponents() {
/* 39 */     BLabel coinIcon = new BLabel("");
/* 40 */     coinIcon.setStyleClass("label.icon.coin");
/* 41 */     coinIcon.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.coins", new String[0]));
/*    */     
/* 43 */     this.coinTotal = new BClickthroughLabel("");
/* 44 */     this.coinTotal.setStyleClass("label.total.coin");
/*    */     
/* 46 */     BLabel tokenIcon = new BLabel("");
/* 47 */     tokenIcon.setStyleClass("label.icon.token");
/* 48 */     tokenIcon.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.tokens", new String[0]));
/*    */     
/* 50 */     this.tokenTotal = new BClickthroughLabel("");
/* 51 */     this.tokenTotal.setStyleClass("label.total.token");
/*    */     
/* 53 */     add((BComponent)coinIcon, new Rectangle(0, 0, 30, 30));
/* 54 */     add((BComponent)this.coinTotal, new Rectangle(35, 5, 100, 20));
/* 55 */     add((BComponent)tokenIcon, new Rectangle(140, 0, 30, 30));
/* 56 */     add((BComponent)this.tokenTotal, new Rectangle(175, 5, 100, 20));
/*    */   }
/*    */ 
/*    */   
/*    */   public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 61 */     if ((newItem != null && newItem.getItemType().equals(ItemType.CRYSTAL)) || (oldItem != null && oldItem.getItemType().equals(ItemType.CRYSTAL))) {
/*    */       int coinAmount;
/*    */       
/*    */       String type;
/* 65 */       if (newItem == null) {
/* 66 */         ClientItem clientItem = (ClientItem)oldItem;
/* 67 */         type = clientItem.getItemDescription().getId();
/* 68 */         coinAmount = 0;
/*    */       } else {
/* 70 */         ClientItem clientItem = (ClientItem)newItem;
/* 71 */         type = clientItem.getItemDescription().getId();
/* 72 */         coinAmount = clientItem.getAmount();
/*    */       } 
/* 74 */       updateItem(coinAmount, type);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateItem(int coinAmount, String type) {
/* 79 */     if (type.equals("coin")) {
/* 80 */       this.coinTotal.setText(coinAmount + "");
/* 81 */     } else if (type.equals("pet-token")) {
/* 82 */       this.tokenTotal.setText(coinAmount + "");
/*    */     } 
/*    */   }
/*    */   
/*    */   public BLabel getTokenTotal() {
/* 87 */     return (BLabel)this.tokenTotal;
/*    */   }
/*    */   
/*    */   public BLabel getCoinTotal() {
/* 91 */     return (BLabel)this.coinTotal;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\CurrencyWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */