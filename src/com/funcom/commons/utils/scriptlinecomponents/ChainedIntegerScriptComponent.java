/*     */ package com.funcom.commons.utils.scriptlinecomponents;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChainedIntegerScriptComponent
/*     */   extends ChainedScriptComponent<Integer>
/*     */   implements ChainedScriptComponentVisual, FocusListener
/*     */ {
/*  23 */   private int _value = 0;
/*     */   private boolean _valid = true;
/*  25 */   private String _labelText = null;
/*     */   
/*  27 */   private JTextField _inputField = null;
/*     */   private JComponent _thisVisualComponent;
/*     */   
/*     */   public ChainedIntegerScriptComponent(Integer defaultNum) {
/*  31 */     choose(defaultNum);
/*     */   }
/*     */   public ChainedIntegerScriptComponent(Integer defaultNum, String labelText) {
/*  34 */     choose(defaultNum);
/*  35 */     this._labelText = labelText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean choose(Integer choice) {
/*  43 */     boolean isValid = true;
/*  44 */     int temp = choice.intValue();
/*  45 */     if (temp >= 0) {
/*  46 */       this._value = temp;
/*     */     } else {
/*  48 */       isValid = false;
/*  49 */     }  this._valid = isValid;
/*  50 */     if (this._inputField != null) this._inputField.setText("" + this._value); 
/*  51 */     getJComponent().validate();
/*  52 */     return isValid;
/*     */   }
/*     */   
/*     */   public boolean chooseAsString(String choice) {
/*  56 */     if (choice instanceof String)
/*     */       
/*     */       try {
/*  59 */         int temp = Integer.parseInt(choice);
/*  60 */         return choose(Integer.valueOf(temp));
/*  61 */       } catch (NumberFormatException e) {
/*  62 */         return false;
/*     */       }  
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getValidItem() {
/*  70 */     return Integer.valueOf(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  80 */     return this._valid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     return "integer: " + this._value + "[" + this._valid + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public JComponent getJComponent() {
/*  90 */     if (this._thisVisualComponent == null) {
/*  91 */       this._inputField = new JTextField(getValidItem().toString(), 3);
/*  92 */       this._inputField.addActionListener(this);
/*  93 */       this._inputField.addFocusListener(this);
/*  94 */       if (this._labelText != null) {
/*  95 */         this._thisVisualComponent = new JPanel()
/*     */           {
/*     */             public void paint(Graphics g) {
/*  98 */               if (getParent() != null) setBackground(getParent().getBackground()); 
/*  99 */               super.paint(g);
/*     */             }
/*     */           };
/* 102 */         this._thisVisualComponent.add(new JLabel(this._labelText));
/* 103 */         this._thisVisualComponent.add(this._inputField);
/*     */       } else {
/* 105 */         this._thisVisualComponent = this._inputField;
/*     */       } 
/* 107 */     }  return this._thisVisualComponent;
/*     */   }
/*     */   
/*     */   public void addActionListener(ActionListener listener) {
/* 111 */     if (this._thisVisualComponent == null) getJComponent(); 
/* 112 */     this._inputField.addActionListener(listener);
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 116 */     if (e.getSource() == this._inputField) {
/* 117 */       validateContent();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateContent() {
/*     */     try {
/* 125 */       Integer temp = Integer.valueOf(Integer.parseInt(this._inputField.getText()));
/* 126 */       boolean selectionChanged = (temp.intValue() != this._value);
/* 127 */       choose(temp);
/* 128 */       if (!isValid()) { this._inputField.setBackground(Color.RED); }
/* 129 */       else { this._inputField.setBackground(Color.WHITE); }
/* 130 */        if (selectionChanged) fireSelectionChanged(this); 
/* 131 */     } catch (NumberFormatException e) {
/* 132 */       this._valid = false;
/* 133 */       this._inputField.setBackground(Color.RED);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent e) {}
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent e) {
/* 143 */     validateContent();
/*     */   }
/*     */   
/*     */   public ChainedIntegerScriptComponent() {}
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\ChainedIntegerScriptComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */