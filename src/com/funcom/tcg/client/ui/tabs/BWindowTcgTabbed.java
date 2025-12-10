/*     */ package com.funcom.tcg.client.ui.tabs;
/*     */ 
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.funcom.tcg.client.ui.BWindowTcg;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.ButtonGroup;
/*     */ import com.jmex.bui.GroupListener;
/*     */ import com.jmex.bui.dragndrop.BDragNDrop;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BWindowTcgTabbed extends BWindowTcg {
/*     */   private List<Tab> tabs;
/*     */   private BContainer tabContainer;
/*     */   private ButtonGroup buttonGroup;
/*     */   
/*     */   public BWindowTcgTabbed(String title) {
/*  23 */     super(title);
/*  24 */     this.tabs = new ArrayList<Tab>();
/*  25 */     this.buttonGroup = new ButtonGroup();
/*     */     
/*  27 */     this.tabContainer = new BContainer(new TabLayoutManager());
/*  28 */     this.tabContainer.setStyleClass("bwindowtcg.tabcontainer");
/*  29 */     add((BComponent)this.tabContainer, BorderLayout.WEST);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(String action) {
/*  33 */     return addTab((BIcon)null, action, (String)null);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(String action, String toolTipText) {
/*  37 */     return addTab((BIcon)null, action, toolTipText);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(BIcon icon, String action) {
/*  41 */     return addTab(icon, action, (String)null);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(BIcon icon, String action, String toolTipText) {
/*  45 */     return addTab(icon, (BIcon)null, action, toolTipText);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(BIcon icon, BIcon overlay, String action) {
/*  49 */     return addTab(icon, overlay, action, (String)null);
/*     */   }
/*     */   
/*     */   public BTabToggleButton addTab(BIcon icon, BIcon overlay, String action, String toolTipText) {
/*  53 */     BTabToggleButton tabButton = new BTabToggleButton(icon, overlay, action) {
/*     */         protected void fireAction(long when, int modifiers) {
/*  55 */           if (!isSelected())
/*  56 */             super.fireAction(when, modifiers); 
/*     */         }
/*     */         
/*     */         protected void stateDidChange() {
/*  60 */           if (BDragNDrop.instance().isDragging())
/*     */             return; 
/*  62 */           super.stateDidChange();
/*  63 */           switch (getState()) {
/*     */             case 1:
/*  65 */               MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*     */               return;
/*     */             case 3:
/*  68 */               MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*     */               return;
/*     */           } 
/*  71 */           MainGameState.getMouseCursorSetter().setDefaultCursor();
/*     */         }
/*     */       };
/*     */     
/*  75 */     tabButton.setTooltipText(toolTipText);
/*  76 */     tabButton.setSelected(true);
/*     */     
/*  78 */     this.buttonGroup.add(tabButton);
/*  79 */     this.tabContainer.add((BComponent)tabButton);
/*  80 */     this.tabs.add(new Tab(tabButton));
/*     */     
/*  82 */     return tabButton;
/*     */   }
/*     */   
/*     */   public void removeAllTabs() {
/*  86 */     this.buttonGroup.removeAll();
/*  87 */     this.tabContainer.removeAll();
/*  88 */     this.tabs.clear();
/*     */   }
/*     */   
/*     */   public int getTabCount() {
/*  92 */     return this.tabs.size();
/*     */   }
/*     */   
/*     */   public Tab getTab(int index) {
/*  96 */     return this.tabs.get(index);
/*     */   }
/*     */   
/*     */   public BTabToggleButton getTabComponent(int index) {
/* 100 */     return ((Tab)this.tabs.get(index)).component;
/*     */   }
/*     */   
/*     */   public ButtonGroup getButtonGroup() {
/* 104 */     return this.buttonGroup;
/*     */   }
/*     */   
/*     */   public void addGroupListener(GroupListener listener) {
/* 108 */     this.buttonGroup.addGroupListener(listener);
/*     */   }
/*     */   
/*     */   public void removeGroupListener(GroupListener listener) {
/* 112 */     this.buttonGroup.removeGroupListener(listener);
/*     */   }
/*     */   
/*     */   private static class Tab {
/*     */     public BTabToggleButton component;
/*     */     
/*     */     private Tab(BTabToggleButton component) {
/* 119 */       this.component = component;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\tabs\BWindowTcgTabbed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */