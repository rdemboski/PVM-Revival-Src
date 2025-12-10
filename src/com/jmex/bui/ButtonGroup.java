/*     */ package com.jmex.bui;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ButtonGroup
/*     */ {
/*  24 */   private Set<BCustomToggleButton> buttons = new HashSet<BCustomToggleButton>();
/*  25 */   private BCustomToggleButton selection = null;
/*     */   private Set<GroupListener> groupListeners;
/*     */   
/*     */   public void addGroupListener(GroupListener listener) {
/*  29 */     if (this.groupListeners == null) {
/*  30 */       this.groupListeners = new HashSet<GroupListener>();
/*     */     }
/*  32 */     this.groupListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeGroupListener(GroupListener listener) {
/*  36 */     if (this.groupListeners == null) {
/*     */       return;
/*     */     }
/*  39 */     this.groupListeners.remove(listener);
/*     */   }
/*     */   
/*     */   private void fireSelectionChanged(BCustomToggleButton button) {
/*  43 */     if (this.groupListeners == null) {
/*     */       return;
/*     */     }
/*  46 */     for (GroupListener groupListener : this.groupListeners) {
/*  47 */       groupListener.groupSelectionChanged(button);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(BCustomToggleButton button) {
/*  57 */     if (button == null) {
/*     */       return;
/*     */     }
/*  60 */     this.buttons.add(button);
/*     */     
/*  62 */     if (button.isSelected()) {
/*  63 */       if (this.selection == null) {
/*  64 */         setSelected(button, true);
/*     */       } else {
/*  66 */         button.setSelected(false);
/*     */       } 
/*     */     }
/*     */     
/*  70 */     button.setButtonGroup(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(BCustomToggleButton button) {
/*  79 */     if (button == null) {
/*     */       return;
/*     */     }
/*  82 */     this.buttons.remove(button);
/*  83 */     if (button == this.selection) {
/*  84 */       this.selection = null;
/*     */     }
/*  86 */     button.setButtonGroup(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAll() {
/*  91 */     for (BCustomToggleButton button : this.buttons) {
/*  92 */       button.setButtonGroup(null);
/*     */     }
/*  94 */     this.buttons.clear();
/*  95 */     this.selection = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelection() {
/* 103 */     if (this.selection != null) {
/* 104 */       BCustomToggleButton oldSelection = this.selection;
/* 105 */       this.selection = null;
/* 106 */       oldSelection.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<BCustomToggleButton> getElements() {
/* 117 */     return Collections.unmodifiableSet(this.buttons);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BCustomToggleButton getSelection() {
/* 126 */     return this.selection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(BCustomToggleButton button, boolean selected) {
/* 138 */     if (selected && button != null && button != this.selection) {
/* 139 */       BCustomToggleButton oldSelection = this.selection;
/* 140 */       this.selection = button;
/* 141 */       if (oldSelection != null) {
/* 142 */         oldSelection.setSelected(false);
/*     */       }
/* 144 */       button.setSelected(true);
/* 145 */       fireSelectionChanged(button);
/*     */     } 
/* 147 */     if (!selected && this.selection == button) {
/* 148 */       this.selection = null;
/* 149 */       fireSelectionChanged(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected(BCustomToggleButton button) {
/* 161 */     return (button == this.selection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getButtonCount() {
/* 170 */     if (this.buttons == null) {
/* 171 */       return 0;
/*     */     }
/* 173 */     return this.buttons.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\ButtonGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */