/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.icon.ImageIcon;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ 
/*    */ public class ItemCard
/*    */   extends BContainer {
/*    */   public ItemCard(InventoryItem item) {
/* 20 */     super((BLayoutManager)new AbsoluteLayout());
/* 21 */     BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/card_misc_item.xml", CacheType.NOT_CACHED);
/* 22 */     BContainer mainContainer = (BContainer)bananaPeel.getTopComponents().iterator().next();
/* 23 */     setSize(mainContainer.getWidth(), mainContainer.getHeight());
/* 24 */     add((BComponent)mainContainer, mainContainer.getBounds());
/*    */     
/* 26 */     BLabel iconLabel = (BLabel)findComponent(this, "graphic_card_icon");
/* 27 */     iconLabel.setIcon((BIcon)new ImageIcon((BImage)TcgGame.getResourceManager().getResource(BImage.class, item.getIcon(), CacheType.NOT_CACHED)));
/*    */     
/* 29 */     BLabel amountLabel = (BLabel)findComponent(this, "text_amount");
/* 30 */     if (item.getItemType().equals(ItemType.CRYSTAL)) {
/* 31 */       if (item.getClassId().equals("coin")) {
/* 32 */         amountLabel.setStyleClass("text_amount_coins");
/* 33 */       } else if (item.getClassId().equals("pet-token")) {
/* 34 */         amountLabel.setStyleClass("text_amount_tokens");
/*    */       } 
/*    */     }
/*    */     
/* 38 */     String amountString = String.valueOf(item.getAmount());
/* 39 */     if (item.getAmount() >= 1000) {
/* 40 */       if (item.getAmount() >= 1000000) {
/* 41 */         amountString = String.valueOf((item.getAmount() / 1000000) + "M");
/*    */       } else {
/* 43 */         amountString = String.valueOf((item.getAmount() / 1000) + "K");
/*    */       } 
/*    */     }
/*    */     
/* 47 */     amountLabel.setText(amountString);
/*    */   }
/*    */   
/*    */   public BComponent findComponent(BContainer rootComponent, String componentName) {
/* 51 */     for (int i = 0; i < rootComponent.getComponentCount(); i++) {
/* 52 */       BComponent component = rootComponent.getComponent(i);
/* 53 */       if (componentName.equals(component.getName())) {
/* 54 */         return component;
/*    */       }
/* 56 */       if (component instanceof BContainer) {
/* 57 */         BComponent foundComponent = findComponent((BContainer)component, componentName);
/* 58 */         if (foundComponent != null)
/* 59 */           return foundComponent; 
/*    */       } 
/*    */     } 
/* 62 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\ItemCard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */