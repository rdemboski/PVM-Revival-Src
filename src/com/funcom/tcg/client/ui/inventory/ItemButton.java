/*     */ package com.funcom.tcg.client.ui.inventory;
/*     */ 
/*     */ import com.funcom.gameengine.jme.text.HTMLView2;
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud2.VendorWindow;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BCustomButton;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.dragndrop.BDragNDrop;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.StackedLayout;
/*     */ 
/*     */ public class ItemButton
/*     */   extends BCustomButton {
/*     */   private static final String SHINE_LABEL_CLASS = "itembutton.label.shine";
/*     */   protected BLabel quantityLabel;
/*     */   protected BLabel iconLabel;
/*     */   protected BLabel shineLabel;
/*     */   private int id;
/*     */   private InventoryItem item;
/*     */   private String text;
/*     */   
/*     */   public ItemButton(int id) {
/*  28 */     this(id, "");
/*     */   }
/*     */   
/*     */   public ItemButton(int id, String text) {
/*  32 */     setLayoutManager((BLayoutManager)new StackedLayout());
/*  33 */     this.iconLabel = new BLabel("");
/*  34 */     this.quantityLabel = new BLabel("");
/*  35 */     this.quantityLabel.setStyleClass(getDefaultStyleClass() + ".quantity");
/*  36 */     this.shineLabel = new BLabel("");
/*  37 */     this.shineLabel.setStyleClass("itembutton.label.shine");
/*  38 */     add((BComponent)this.iconLabel);
/*  39 */     add((BComponent)this.shineLabel);
/*  40 */     add((BComponent)this.quantityLabel);
/*     */     
/*  42 */     this.text = text;
/*  43 */     this.id = id;
/*     */     
/*  45 */     initialize();
/*     */   }
/*     */   
/*     */   protected void stateDidChange() {
/*  49 */     if (getItem() == null || BDragNDrop.instance().isDragging()) {
/*     */       return;
/*     */     }
/*  52 */     super.stateDidChange();
/*  53 */     switch (getState()) {
/*     */       case 1:
/*  55 */         if (TcgUI.isWindowOpen(VendorWindow.class)) {
/*  56 */           MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_BUY);
/*     */         } else {
/*  58 */           MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*     */         } 
/*     */         return;
/*     */       case 3:
/*  62 */         if (TcgUI.isWindowOpen(VendorWindow.class)) {
/*  63 */           MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_BUY);
/*     */         } else {
/*  65 */           MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*     */         } 
/*     */         return;
/*     */     } 
/*  69 */     MainGameState.getMouseCursorSetter().setDefaultCursor();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  74 */     this.item = null;
/*  75 */     this.iconLabel.setIcon(null);
/*  76 */     this.quantityLabel.setText(this.text);
/*     */   }
/*     */   
/*     */   public InventoryItem getItem() {
/*  80 */     return this.item;
/*     */   }
/*     */   
/*     */   public void setItem(InventoryItem item) {
/*  84 */     this.item = item;
/*  85 */     if (item == null) {
/*  86 */       this.iconLabel.setIcon(null);
/*  87 */       this.quantityLabel.setText(this.text);
/*     */     } else {
/*  89 */       this.iconLabel.setIcon(TcgUI.getIconProvider().getIconForItem(item));
/*  90 */       this.quantityLabel.setText(String.valueOf(item.getAmount()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public InventoryItem removeItem() {
/*  95 */     InventoryItem item = this.item;
/*     */     
/*  97 */     setItem((InventoryItem)null);
/*  98 */     return item;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 107 */     return "itembutton";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltipText() {
/* 112 */     if (this.item == null) {
/* 113 */       return null;
/*     */     }
/* 115 */     return MainGameState.getToolTipManager().getItemHtml(getItem().getClassId(), getItem().getTier());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BComponent createTooltipComponent(String tiptext) {
/* 120 */     return (BComponent)new HTMLView2(tiptext, TcgGame.getResourceManager());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\ItemButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */