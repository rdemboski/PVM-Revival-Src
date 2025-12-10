/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.GiftBoxCollection;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoSetModel;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.funcom.tcg.client.ui.tips.TipsCollection;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGHudInfoSetModel
/*    */   implements HudInfoSetModel, GiftBoxCollection.ChangeListener, Inventory.ChangeListener
/*    */ {
/*    */   private final GiftBoxCollection giftBoxes;
/*    */   private final Inventory inventory;
/* 19 */   private Collection<HudInfoModel> infoModels = new ArrayList<HudInfoModel>();
/*    */   private static final int TOP_PRIORITY = -10;
/*    */   
/*    */   public TCGHudInfoSetModel(GiftBoxCollection giftBoxes, Inventory inventory, TipsCollection tips) {
/* 23 */     this.giftBoxes = giftBoxes;
/* 24 */     this.inventory = inventory;
/*    */     
/* 26 */     giftBoxes.setChangeListener(this);
/* 27 */     inventory.addChangeListener(this);
/*    */ 
/*    */     
/* 30 */     contentChanged();
/*    */   }
/*    */ 
/*    */   
/*    */   public void contentChanged() {
/* 35 */     this.infoModels.clear();
/* 36 */     this.infoModels.addAll(this.giftBoxes.getAll());
/*    */ 
/*    */     
/* 39 */     for (InventoryItem inventoryItem : this.inventory) {
/* 40 */       if (inventoryItem != null) {
/* 41 */         processItemForAdd(inventoryItem);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<HudInfoModel> getInfoModels() {
/* 48 */     return this.infoModels;
/*    */   }
/*    */ 
/*    */   
/*    */   public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 53 */     if (newItem != null) {
/* 54 */       processItemForAdd(newItem);
/* 55 */     } else if (oldItem != null) {
/* 56 */       processItemForRemove(oldItem);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void processItemForRemove(InventoryItem oldItem) {
/* 61 */     for (HudInfoModel infoModel : this.infoModels) {
/* 62 */       if (infoModel instanceof ItemInfoModelImpl && (
/* 63 */         (ItemInfoModelImpl)infoModel).getItem().getClassId().equals(oldItem.getClassId())) {
/* 64 */         this.infoModels.remove(infoModel);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void processItemForAdd(InventoryItem newItem) {
/* 72 */     for (HudInfoModel infoModel : this.infoModels) {
/* 73 */       if (infoModel instanceof ItemInfoModelImpl && (
/* 74 */         (ItemInfoModelImpl)infoModel).getItem().getClassId().equals(newItem.getClassId())) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 80 */     HudInfoModel itemInfoModel = createItemInfoModel(newItem);
/* 81 */     if (itemInfoModel != null) {
/* 82 */       this.infoModels.add(itemInfoModel);
/*    */     }
/*    */   }
/*    */   
/*    */   private HudInfoModel createItemInfoModel(InventoryItem newItem) {
/* 87 */     if ("coin".equals(newItem.getClassId())) {
/* 88 */       return new ItemInfoModelImpl(newItem, -10);
/*    */     }
/*    */     
/* 91 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TCGHudInfoSetModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */