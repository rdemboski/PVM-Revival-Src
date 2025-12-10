/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BMenuItem;
/*     */ import com.jmex.bui.BPopupMenu;
/*     */ import com.jmex.bui.BTextComponent;
/*     */ import com.jmex.bui.Label;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.GroupLayout;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class TCGComboBox extends BLabel {
/*  21 */   protected int _selidx = -1;
/*  22 */   protected ArrayList<ComboMenuItem> _items = new ArrayList<ComboMenuItem>();
/*     */   protected ComboPopupMenu _menu;
/*     */   protected Dimension _psize;
/*     */   protected int _columns;
/*     */   
/*     */   public TCGComboBox() {
/*  28 */     super("");
/*  29 */     setFit(BLabel.Fit.TRUNCATE);
/*     */   }
/*     */   
/*     */   public TCGComboBox(ComboMenuItem[] items) {
/*  33 */     super("");
/*  34 */     setItems(items);
/*     */   }
/*     */   
/*     */   public TCGComboBox(Iterable<? extends ComboMenuItem> items) {
/*  38 */     super("");
/*  39 */     setItems(items);
/*     */   }
/*     */   
/*     */   public void addItem(ComboMenuItem item) {
/*  43 */     addItem(this._items.size(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(int index, ComboMenuItem item) {
/*  48 */     this._items.add(index, item);
/*  49 */     clearCache();
/*     */   }
/*     */   
/*     */   public void setItems(Iterable<? extends ComboMenuItem> items) {
/*  53 */     clearItems();
/*  54 */     for (ComboMenuItem item : items) {
/*  55 */       addItem(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setItems(ComboMenuItem[] items) {
/*  60 */     clearItems();
/*  61 */     for (ComboMenuItem item : items) {
/*  62 */       addItem(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSelectedIndex() {
/*  67 */     return this._selidx;
/*     */   }
/*     */   
/*     */   public BLabel getSelectedItem() {
/*  71 */     return getItem(this._selidx);
/*     */   }
/*     */   
/*     */   public ComboMenuItem getSelectedValue() {
/*  75 */     return getValue(this._selidx);
/*     */   }
/*     */   
/*     */   public void selectItem(int index) {
/*  79 */     selectItem(index, 0L, 0);
/*     */   }
/*     */   
/*     */   public void selectItem(BLabel item) {
/*  83 */     int selidx = -1;
/*  84 */     for (int ii = 0, ll = this._items.size(); ii < ll; ii++) {
/*  85 */       ComboMenuItem mitem = this._items.get(ii);
/*  86 */       if (mitem.equals(item)) {
/*  87 */         selidx = ii;
/*     */         break;
/*     */       } 
/*     */     } 
/*  91 */     selectItem(selidx);
/*     */   }
/*     */   
/*     */   public void selectValue(BLabel value) {
/*  95 */     selectItem(value);
/*     */   }
/*     */   
/*     */   public int getItemCount() {
/*  99 */     return this._items.size();
/*     */   }
/*     */   
/*     */   public BLabel getItem(int index) {
/* 103 */     return (index < 0 || index >= this._items.size()) ? null : (BLabel)this._items.get(index);
/*     */   }
/*     */   
/*     */   public ComboMenuItem getValue(int index) {
/* 107 */     return (index < 0 || index >= this._items.size()) ? null : this._items.get(index);
/*     */   }
/*     */   
/*     */   public void clearItems() {
/* 111 */     clearCache();
/* 112 */     this._items.clear();
/* 113 */     this._selidx = -1;
/*     */   }
/*     */   
/*     */   public void setPreferredColumns(int columns) {
/* 117 */     this._columns = columns;
/* 118 */     if (this._menu != null) {
/* 119 */       this._menu.setPreferredColumns(columns);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 124 */     if (event instanceof MouseEvent && isEnabled()) {
/* 125 */       MouseEvent mev = (MouseEvent)event;
/* 126 */       switch (mev.getType()) {
/*     */         case 0:
/* 128 */           if (this._menu == null) {
/*     */             
/* 130 */             ComboMenuItem menuItem = this._items.get(0);
/* 131 */             if (menuItem != null && menuItem.getData() instanceof PrioritizedTypeData) {
/* 132 */               Collections.sort(this._items, new Comparator<ComboMenuItem>()
/*     */                   {
/*     */                     public int compare(TCGComboBox.ComboMenuItem o1, TCGComboBox.ComboMenuItem o2) {
/* 135 */                       return ((PrioritizedTypeData)o1.getData()).getPriority() - ((PrioritizedTypeData)o2.getData()).getPriority();
/*     */                     }
/*     */                   });
/* 138 */               Collections.reverse(this._items);
/*     */             } 
/*     */ 
/*     */             
/* 142 */             this._menu = new ComboPopupMenu(this._columns);
/*     */             
/* 144 */             this._menu.popup(getAbsoluteX(), getAbsoluteY() + getHeight() + this._menu.getHeight(), false);
/*     */           } 
/* 146 */           this._menu.popup(getAbsoluteX(), getAbsoluteY() + getHeight() + this._menu.getHeight(), false);
/* 147 */           TcgUI.getUISoundPlayer().play("ClickForward");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 157 */           return true;
/*     */       }  return super.dispatchEvent(event);
/* 159 */     }  return super.dispatchEvent(event);
/*     */   }
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 163 */     return "combobox";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Dimension computePreferredSize(int whint, int hhint) {
/* 170 */     if (this._psize == null) {
/* 171 */       this._psize = new Dimension();
/* 172 */       Label label = new Label((BTextComponent)this);
/* 173 */       for (ComboMenuItem mitem : this._items) {
/* 174 */         label.setIcon(mitem.getIcon());
/* 175 */         label.setText(mitem.getText());
/* 176 */         Dimension lsize = label.computePreferredSize(-1, -1);
/* 177 */         this._psize.width = Math.max(this._psize.width, lsize.width);
/* 178 */         this._psize.height = Math.max(this._psize.height, lsize.height);
/*     */       } 
/*     */     } 
/* 181 */     return new Dimension(this._psize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void selectItem(int index, long when, int modifiers) {
/* 187 */     if (this._selidx == index) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     this._selidx = index;
/*     */     
/* 193 */     BLabel item = getSelectedItem();
/* 194 */     setIcon(item.getIcon());
/* 195 */     setText(item.getText());
/*     */     
/* 197 */     emitEvent((BEvent)new ActionEvent(this, when, modifiers, "selectionChanged"));
/*     */   }
/*     */   
/*     */   protected void clearCache() {
/* 201 */     if (this._menu != null) {
/* 202 */       this._menu.removeAll();
/* 203 */       this._menu = null;
/*     */     } 
/* 205 */     this._psize = null;
/*     */   }
/*     */   
/*     */   public void selectItem(TypeData byData) {
/* 209 */     for (int index = 0; index < this._items.size(); index++) {
/* 210 */       if (((ComboMenuItem)this._items.get(index)).getData().equals(byData))
/* 211 */         selectItem(index); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected class ComboPopupMenu
/*     */     extends BPopupMenu {
/*     */     public ComboPopupMenu(int columns) {
/* 218 */       super(TCGComboBox.this.getWindow(), columns);
/* 219 */       setLayoutManager((BLayoutManager)GroupLayout.makeVStretch().setGap(-16));
/* 220 */       for (TCGComboBox.ComboMenuItem _item : TCGComboBox.this._items) {
/* 221 */         addMenuItem(_item);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void itemSelected(BMenuItem item, long when, int modifiers) {
/* 229 */       if (item.isEnabled()) {
/* 230 */         TCGComboBox.this.selectItem(TCGComboBox.this._items.indexOf(item), when, modifiers);
/* 231 */         TcgUI.getUISoundPlayer().play("ClickForward");
/* 232 */         dismiss();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Dimension computePreferredSize(int whint, int hhint) {
/* 240 */       Dimension d = super.computePreferredSize(whint, hhint);
/* 241 */       d.width = Math.max(d.width, TCGComboBox.this.getWidth() - getInsets().getHorizontal());
/* 242 */       return d;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ComboMenuItem extends BMenuItem {
/*     */     private final TypeData data;
/*     */     
/*     */     public ComboMenuItem(BIcon icon, TypeData data) {
/* 250 */       super(data.getText(), icon, "select");
/* 251 */       this.data = data;
/* 252 */       if (data instanceof KnowsOfOwner) {
/* 253 */         ((KnowsOfOwner)data).setOwner((BComponent)this);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getDefaultStyleClass() {
/* 260 */       return "combobox-item";
/*     */     }
/*     */     
/*     */     public TypeData getData() {
/* 264 */       return this.data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\TCGComboBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */