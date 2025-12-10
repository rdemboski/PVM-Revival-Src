/*    */ package com.funcom.tcg.client.ui.chat;
/*    */ 
/*    */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*    */ import com.funcom.tcg.client.ui.BuiUtils;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class ChatBubbleWindow extends BWindow implements PartiallyNotInteractive, ChatWindowPane {
/*    */   public static final String STYLE_CLASS = "chatbubble.window";
/*    */   public static final String STYLE_CLASS_LABEL = "chatbubble.window.label";
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   public ChatBubbleWindow(ResourceManager resourceManager, int width, int height) {
/* 22 */     super(ChatBubbleWindow.class.getSimpleName(), null, (BLayoutManager)new AbsoluteLayout());
/*    */     
/* 24 */     this.width = width;
/* 25 */     this.height = height;
/* 26 */     this._style = BuiUtils.createMergedClassStyleSheets(ChatBubbleWindow.class, new BananaResourceProvider(resourceManager));
/*    */     
/* 28 */     setLayer(-1);
/* 29 */     setStyleClass("chatbubble.window");
/* 30 */     setSize(width, height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addChatComponent(BComponent component, int x, int y, int width, int height) {
/* 35 */     setLocation(x, y);
/* 36 */     add(component, new Rectangle(0, 0, width, height));
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveChatBubble(BLabel label, int x, int y, int width, int height) {
/* 41 */     setLocation(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeChatBubble(BComponent component, boolean dismiss) {
/* 46 */     remove(component);
/* 47 */     BWindow window = BuiSystem.getWindow(ChatBubbleWindow.class.getSimpleName());
/* 48 */     if (dismiss && window != null && equals(window)) {
/* 49 */       dismiss();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 54 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 59 */     return this.height;
/*    */   }
/*    */   
/*    */   public String getStyleClassLabel() {
/* 63 */     return "chatbubble.window.label";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHit() {
/* 68 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatBubbleWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */