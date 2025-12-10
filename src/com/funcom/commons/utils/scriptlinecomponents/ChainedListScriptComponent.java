/*     */ package com.funcom.commons.utils.scriptlinecomponents;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class ChainedListScriptComponent<T>
/*     */   extends ChainedScriptComponent<T>
/*     */   implements ChainedScriptComponentVisual
/*     */ {
/*  18 */   private String dummy = "Make a selection";
/*  19 */   private T _selected = null;
/*  20 */   private Vector<T> _list = new Vector<T>();
/*  21 */   protected JComboBox _thisVisualComponent = null;
/*     */   
/*     */   public ChainedListScriptComponent(Collection<T> list) {
/*  24 */     setList(list);
/*     */   }
/*     */   
/*     */   public ChainedListScriptComponent(T[] list) {
/*  28 */     this(Arrays.asList(list));
/*     */   }
/*     */   
/*     */   protected void setList(Collection<T> list) {
/*  32 */     this._list.clear();
/*  33 */     final T dataSourceItemDummy = (T)new Object() {
/*     */         public int compareTo(Comparable o2) {
/*  35 */           return 0;
/*     */         }
/*     */         
/*     */         public String toString() {
/*  39 */           return ChainedListScriptComponent.this.dummy;
/*     */         }
/*     */       };
/*  42 */     this._list.add(dataSourceItemDummy);
/*  43 */     this._list.addAll(list);
/*     */     
/*     */     try {
/*  46 */       Collections.sort(this._list, new Comparator<T>()
/*     */           {
/*     */             public int compare(T o1, T o2) {
/*  49 */               if (o1 == dataSourceItemDummy) return -1; 
/*  50 */               if (o2 == dataSourceItemDummy) return 1; 
/*  51 */               return ((Comparable<T>)o1).compareTo(o2);
/*     */             }
/*     */           });
/*  54 */     } catch (ClassCastException e) {
/*  55 */       e.printStackTrace();
/*     */     } 
/*  57 */     if (this._thisVisualComponent != null) {
/*  58 */       this._thisVisualComponent.setSelectedItem(dataSourceItemDummy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean choose(T s) {
/*  64 */     if (s != null) {
/*  65 */       this._selected = s;
/*  66 */       if (!this._selected.equals(this.dummy) && (s instanceof String || s instanceof Comparable) && this._list.contains(s)) {
/*  67 */         ((JComboBox)getJComponent()).setSelectedItem(s);
/*  68 */         return true;
/*     */       } 
/*     */     } 
/*  71 */     this._selected = null;
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean chooseAsString(String o) {
/*  77 */     for (T item : this._list) {
/*  78 */       if (item.toString().equals(o)) {
/*  79 */         this._selected = item;
/*  80 */         ((JComboBox)getJComponent()).setSelectedItem(item);
/*  81 */         return true;
/*     */       } 
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public T getValidItem() {
/*  89 */     return this._selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  94 */     return (this._selected != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     String s = "List: " + this._selected + "[" + isValid() + "]";
/* 100 */     if (getNextScriptComponent() != null) s = s + " : " + getNextScriptComponent(); 
/* 101 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public JComponent getJComponent() {
/* 106 */     if (this._thisVisualComponent == null) {
/* 107 */       this._thisVisualComponent = new JComboBox<T>(this._list);
/* 108 */       this._thisVisualComponent.addActionListener(this);
/* 109 */       choose((T)null);
/*     */     } 
/* 111 */     this._thisVisualComponent.setSelectedItem(this.dummy);
/* 112 */     return this._thisVisualComponent;
/*     */   }
/*     */   
/*     */   public void addActionListener(ActionListener listener) {
/* 116 */     if (this._thisVisualComponent == null) getJComponent(); 
/* 117 */     this._thisVisualComponent.addActionListener(listener);
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 121 */     if (e.getSource() == this._thisVisualComponent) {
/* 122 */       T selectedItem = (T)this._thisVisualComponent.getSelectedItem();
/* 123 */       boolean selectionChanged = (this._selected != selectedItem);
/* 124 */       choose(selectedItem);
/* 125 */       if (!isValid()) { this._thisVisualComponent.setBackground(Color.RED); }
/* 126 */       else { this._thisVisualComponent.setBackground(Color.WHITE); }
/*     */       
/* 128 */       if (getNextScriptComponent() != null && getNextScriptComponent() instanceof MorphicListener)
/* 129 */         ((MorphicListener)getNextScriptComponent()).update(); 
/* 130 */       if (selectionChanged) fireSelectionChanged(this); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\ChainedListScriptComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */