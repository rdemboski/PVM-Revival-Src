/*    */ package com.funcom.tcg.client.ui.chat;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BCustomToggleButton;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ import com.jmex.bui.event.MouseListener;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.BorderLayout;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class ChatButton
/*    */   extends BCustomToggleButton {
/*    */   public static final String STYLE_CLASS = "chatwindow-window.chatbutton";
/*    */   public static final String STYLE_CLASS_LABEL = "chatwindow-window.chatbutton.label";
/*    */   public static final String STYLE_CLASS_ICON_LABEL = "chatwindow-window.chatbutton.ico";
/*    */   public static final int ICON_LABEL_WIDTH = 15;
/*    */   public static final int ICON_LABEL_HEIGHT = 30;
/*    */   private Element chatElement;
/*    */   private Element mainMenuElement;
/*    */   private ChatNetworkController chatUIController;
/*    */   private ChatWindow parentWindow;
/*    */   
/*    */   public ChatButton(Element chatElement, Element mainMenuElement, ChatNetworkController chatUIController, ChatWindow parent, int buttonWidth, int buttonHeight) {
/* 26 */     this.chatElement = chatElement;
/* 27 */     this.mainMenuElement = mainMenuElement;
/* 28 */     this.chatUIController = chatUIController;
/* 29 */     this.parentWindow = parent;
/*    */     
/* 31 */     setLayoutManager((BLayoutManager)new BorderLayout());
/* 32 */     setStyleClass("chatwindow-window.chatbutton");
/*    */     
/* 34 */     makeChatButton(chatElement, buttonWidth, buttonHeight);
/*    */   }
/*    */   
/*    */   private void makeChatButton(Element chatElement, int buttonWidth, int buttonHeight) {
/* 38 */     BLabel label = makeNameLabel(chatElement, buttonWidth, buttonHeight);
/* 39 */     add((BComponent)label, BorderLayout.CENTER);
/*    */     
/* 41 */     if (chatElement.getChildren().size() > 0) {
/* 42 */       BLabel icoLabel = makeIcoLabel();
/* 43 */       add((BComponent)icoLabel, BorderLayout.EAST);
/*    */     } 
/*    */     
/* 46 */     addListener((ComponentListener)new ChatButtonMouseListener(this));
/*    */   }
/*    */   
/*    */   private BLabel makeIcoLabel() {
/* 50 */     BLabel label = new BLabel(">");
/* 51 */     label.setPreferredSize(15, 30);
/* 52 */     label.setStyleClass("chatwindow-window.chatbutton.ico");
/* 53 */     label.setHoverEnabled(true);
/* 54 */     return label;
/*    */   }
/*    */   
/*    */   private BLabel makeNameLabel(Element chatElement, int buttonWidth, int buttonHeight) {
/* 58 */     BLabel label = new BLabel(chatElement.getAttributeValue("message"));
/* 59 */     label.setPreferredSize(buttonWidth, buttonHeight);
/* 60 */     label.setStyleClass("chatwindow-window.chatbutton.label");
/* 61 */     return label;
/*    */   }
/*    */   
/*    */   public void sendMessage() {
/* 65 */     boolean messageSent = this.chatUIController.sendMessage(this.mainMenuElement, this.chatElement);
/* 66 */     if (messageSent)
/* 67 */       this.parentWindow.dismiss(); 
/*    */   }
/*    */   
/*    */   private class ChatButtonMouseListener implements MouseListener {
/*    */     private ChatButton chatButton;
/*    */     
/*    */     public ChatButtonMouseListener(ChatButton chatButton) {
/* 74 */       this.chatButton = chatButton;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void mousePressed(MouseEvent event) {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void mouseReleased(MouseEvent event) {
/* 84 */       ChatButton.this.parentWindow.clicked();
/*    */     }
/*    */ 
/*    */     
/*    */     public void mouseEntered(MouseEvent event) {
/* 89 */       ChatButton.this.parentWindow.setSelected(this.chatButton);
/* 90 */       ChatButton.this.parentWindow.deselectOtherButtonsInGroup(this.chatButton);
/* 91 */       ChatButton.this.parentWindow.activateChildContainer(this.chatButton, ChatButton.this.chatElement, ChatButton.this.mainMenuElement, this.chatButton.getParent().getAbsoluteY() + (this.chatButton.getBounds()).y);
/*    */     }
/*    */ 
/*    */     
/*    */     public void mouseExited(MouseEvent event) {
/* 96 */       ChatButton.this.parentWindow.setSelected((ChatButton)null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */