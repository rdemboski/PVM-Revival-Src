/*    */ package com.funcom.tcg.client.ui.chat;
/*    */ 
/*    */ import com.jmex.bui.BButton;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BTextField;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatInputField
/*    */   extends BContainer
/*    */ {
/*    */   private int xPos;
/*    */   private int yPos;
/* 20 */   private int height = 50;
/* 21 */   private int width = 400;
/*    */   
/* 23 */   private int buttonWidth = 68; private int internalHeight = 40; private int iFieldWidth = 311; private int buttonPos = 318;
/*    */   
/*    */   private BButton btnSend;
/*    */   
/*    */   private BTextField txfInput;
/* 28 */   private float widthOfInputFieldBetween0and1 = 0.7F;
/*    */   
/*    */   public ChatInputField() {
/* 31 */     this(0, 200);
/*    */   }
/*    */   
/*    */   public ChatInputField(int xPos, int yPos) {
/* 35 */     super((BLayoutManager)new AbsoluteLayout());
/*    */     
/* 37 */     this.xPos = xPos;
/* 38 */     this.yPos = yPos;
/*    */     
/* 40 */     this.btnSend = new BButton("Send");
/* 41 */     this.txfInput = new BTextField();
/*    */     
/* 43 */     add((BComponent)this.txfInput, new Rectangle(0, 0, this.iFieldWidth, this.internalHeight));
/* 44 */     add((BComponent)this.btnSend, new Rectangle(this.buttonPos, 0, this.buttonWidth, this.internalHeight));
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 49 */     return "menubuttoncontainer";
/*    */   }
/*    */   
/*    */   public void attachToChatWindowPane(ChatWindowPane chatPane) {
/* 53 */     chatPane.addChatComponent((BComponent)this, this.xPos, this.yPos, this.width, this.height);
/*    */   }
/*    */   
/*    */   public void addSendListener(ComponentListener listener) {
/* 57 */     this.btnSend.addListener(listener);
/* 58 */     this.txfInput.addListener(listener);
/*    */   }
/*    */   
/*    */   public String getInputText() {
/* 62 */     return this.txfInput.getText();
/*    */   }
/*    */   
/*    */   public void clearInputText() {
/* 66 */     this.txfInput.setText("");
/*    */   }
/*    */   
/*    */   public void modifyPos(float currentSlideVisibility) {
/* 70 */     if (currentSlideVisibility < 0.0F || currentSlideVisibility > 1.0F) throw new IllegalArgumentException("Number must be min 0 max 1"); 
/* 71 */     float tempXpos = this.width * currentSlideVisibility - this.width;
/* 72 */     setBounds((int)(this.xPos + tempXpos), this.yPos, this.width, this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasFocus() {
/* 77 */     return this.txfInput.hasFocus();
/*    */   }
/*    */   
/*    */   public int getSendButtonWidth() {
/* 81 */     return this.buttonWidth;
/*    */   }
/*    */   
/*    */   public void setXpos(int xPos) {
/* 85 */     this.xPos = xPos;
/*    */   }
/*    */   
/*    */   public void requestFocusToInputField() {
/* 89 */     this.txfInput.requestFocus();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatInputField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */