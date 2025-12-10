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
/*     */ public class ChainedStringScriptComponent
/*     */   extends ChainedScriptComponent<String>
/*     */   implements ChainedScriptComponentVisual, FocusListener {
/*  17 */   private String _value = "";
/*     */   private static final String DUMMY_INPUT = "Text Not Valid";
/*     */   private boolean _valid = true;
/*  20 */   private String _labelText = null;
/*     */   
/*  22 */   private JTextField _inputField = null;
/*     */   private JComponent _thisVisualComponent;
/*     */   
/*     */   public ChainedStringScriptComponent(String defaultNum) {
/*  26 */     choose(defaultNum);
/*     */   }
/*     */   
/*     */   public ChainedStringScriptComponent(String defaultString, String labelText) {
/*  30 */     choose(defaultString);
/*  31 */     this._labelText = labelText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean choose(String choice) {
/*  39 */     this._valid = (choice != null && choice.length() > 1 && !choice.equalsIgnoreCase("Text Not Valid"));
/*  40 */     this._value = choice;
/*  41 */     if (choice == null || choice.length() <= 1) this._value = "Text Not Valid"; 
/*  42 */     if (this._inputField != null) this._inputField.setText("" + this._value); 
/*  43 */     getJComponent().validate();
/*  44 */     return this._valid;
/*     */   }
/*     */   
/*     */   public boolean chooseAsString(String choice) {
/*  48 */     return choose(choice);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidItem() {
/*  54 */     return this._value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  59 */     return this._valid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  64 */     return "integer: " + this._value + "[" + this._valid + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public JComponent getJComponent() {
/*  69 */     if (this._thisVisualComponent == null) {
/*  70 */       this._inputField = new JTextField(getValidItem().toString());
/*  71 */       this._inputField.addActionListener(this);
/*  72 */       this._inputField.addFocusListener(this);
/*  73 */       if (this._labelText != null)
/*  74 */       { this._thisVisualComponent = new JPanel()
/*     */           {
/*     */             public void paint(Graphics g) {
/*  77 */               if (getParent() != null) setBackground(getParent().getBackground()); 
/*  78 */               super.paint(g);
/*     */             }
/*     */           };
/*  81 */         this._thisVisualComponent.add(new JLabel(this._labelText));
/*  82 */         this._thisVisualComponent.add(this._inputField); }
/*  83 */       else { this._thisVisualComponent = this._inputField; }
/*     */     
/*  85 */     }  return this._thisVisualComponent;
/*     */   }
/*     */   
/*     */   public void addActionListener(ActionListener listener) {
/*  89 */     if (this._thisVisualComponent == null) getJComponent(); 
/*  90 */     this._inputField.addActionListener(listener);
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*  94 */     if (e.getSource() == this._inputField) {
/*  95 */       validateContent();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateContent() {
/*     */     try {
/* 103 */       String temp = this._inputField.getText();
/* 104 */       boolean selectionChanged = (temp != this._value);
/* 105 */       choose(temp);
/* 106 */       if (!isValid()) { this._inputField.setBackground(Color.RED); }
/* 107 */       else { this._inputField.setBackground(Color.WHITE); }
/* 108 */        if (selectionChanged) fireSelectionChanged(this); 
/* 109 */     } catch (NumberFormatException e) {
/* 110 */       this._valid = false;
/* 111 */       this._inputField.setBackground(Color.RED);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent e) {}
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent e) {
/* 121 */     validateContent();
/*     */   }
/*     */   
/*     */   public ChainedStringScriptComponent() {}
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\ChainedStringScriptComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */