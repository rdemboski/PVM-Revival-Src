/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.ui.inventory.DragItemContent;
/*    */ import com.jmex.bui.BClickthroughLabel;
/*    */ import com.jmex.bui.dragndrop.BDragEvent;
/*    */ import com.jmex.bui.dragndrop.BDragNDrop;
/*    */ import com.jmex.bui.dragndrop.BDropEvent;
/*    */ import com.jmex.bui.event.MouseAdapter;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighlightBlobOnItemDragOver
/*    */   extends MouseAdapter
/*    */   implements BDragNDrop.DragNDropListener
/*    */ {
/*    */   private boolean itemTypeIsDragged = false;
/*    */   private ItemType[] itemTypes;
/*    */   private BClickthroughLabel componentToControl;
/*    */   
/*    */   public HighlightBlobOnItemDragOver(BClickthroughLabel componentToControl, ItemType... itemTypes) {
/* 26 */     this.itemTypes = itemTypes;
/* 27 */     this.componentToControl = componentToControl;
/*    */     
/* 29 */     BDragNDrop.instance().addDragNDropListener(this);
/*    */   }
/*    */   
/*    */   private void setHighlight(boolean highlight) {
/* 33 */     this.componentToControl.setVisible((this.itemTypeIsDragged && highlight));
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseEntered(MouseEvent mouseEvent) {
/* 38 */     setHighlight(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseExited(MouseEvent mouseEvent) {
/* 43 */     setHighlight(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dragInitiated(BDragNDrop container, BDragEvent eventB) {
/* 48 */     if (eventB.getDraggedObject() instanceof DragItemContent) {
/* 49 */       DragItemContent itemButtonContent = (DragItemContent)eventB.getDraggedObject();
/* 50 */       ItemType itemType = ((ClientItem)itemButtonContent.getItem()).getItemType();
/* 51 */       for (ItemType type : this.itemTypes) {
/* 52 */         if (type == itemType) this.itemTypeIsDragged = true;
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void dropped(BDragNDrop container, BDropEvent eventB) {
/* 59 */     if (this.itemTypeIsDragged) {
/* 60 */       this.itemTypeIsDragged = false;
/* 61 */       setHighlight(false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\HighlightBlobOnItemDragOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */