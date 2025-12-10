/*    */ package com.funcom.tcg.client.ui.chat;
/*    */ 
/*    */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jme.input.MouseInput;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel2;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatPane
/*    */   extends BWindow
/*    */   implements PartiallyNotInteractive
/*    */ {
/*    */   public static final String WINDOW_NAME = "chat-window";
/*    */   private ChatInputField chatInputField;
/*    */   private int xSize;
/*    */   private int ySize;
/* 28 */   private int xOffset = 0, yOffset = 0;
/*    */   
/*    */   public ChatPane(ChatInputField chatInputField) {
/* 31 */     super(TcgUI.getGameStylesheet(), (BLayoutManager)new AbsoluteLayout());
/* 32 */     setName("chat-window");
/* 33 */     setStyleClass("none");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     this.xSize = DisplaySystem.getDisplaySystem().getWidth();
/* 43 */     this.ySize = DisplaySystem.getDisplaySystem().getHeight();
/*    */     
/* 45 */     setSize(this.xSize, this.ySize);
/*    */     
/* 47 */     setLocation(-this.xOffset, -this.yOffset);
/*    */     
/* 49 */     this.chatInputField = chatInputField;
/*    */     
/* 51 */     BuiSystem.addWindow(this);
/* 52 */     setVisible(true);
/*    */   }
/*    */   
/*    */   public void addComponent(BComponent component, Rectangle rectangle) {
/* 56 */     if (rectangle.x < 0) rectangle.x = 0; 
/* 57 */     if (rectangle.y < 0) rectangle.y = 0; 
/* 58 */     add(component, rectangle);
/*    */   }
/*    */   
/*    */   public void removeComponent(BComponent component) {
/* 62 */     remove(component);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isHit() {
/* 70 */     int mx = MouseInput.get().getXAbsolute();
/* 71 */     int my = MouseInput.get().getYAbsolute();
/* 72 */     return (this.chatInputField.getHitComponent(mx + this.xOffset, my + this.yOffset) != null);
/*    */   }
/*    */   
/*    */   public void moveItem(BLabel2 item, int x, int y, int width, int height) {
/* 76 */     if (x < 0) x = 0; 
/* 77 */     if (y < 0) y = 0; 
/* 78 */     if (x > this.xSize - width / 2) x = this.xSize - width / 2; 
/* 79 */     if (y > this.ySize - height / 2) y = this.ySize - height / 2; 
/* 80 */     item.setBounds(x, y, width, height);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */