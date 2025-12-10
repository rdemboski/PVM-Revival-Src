/*    */ package com.funcom.tcg.client.ui.chat;
/*    */ 
/*    */ import com.funcom.gameengine.GameLoop;
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BToggleButton;
/*    */ import com.jmex.bui.event.ActionEvent;
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ 
/*    */ public class SlidingChatInputController
/*    */   extends BContainer
/*    */   implements Updated
/*    */ {
/*    */   private static final float SLIDE_SPEED = 3.0F;
/* 22 */   private float currentSlideVisibility = 0.0F;
/*    */   
/*    */   private int xPos;
/*    */   
/*    */   private int yPos;
/*    */   private ChatInputField chatInputField;
/*    */   private BToggleButton chatToggleButton;
/*    */   
/*    */   public SlidingChatInputController(int xPos, int yPos, final ChatInputField chatInputField, GameLoop gameLoop) {
/* 31 */     super((BLayoutManager)new AbsoluteLayout());
/* 32 */     this.xPos = xPos;
/* 33 */     this.yPos = yPos;
/*    */     
/* 35 */     this.chatInputField = chatInputField;
/* 36 */     this.chatToggleButton = new BToggleButton("Chat");
/*    */     
/* 38 */     gameLoop.addToUpdateList(this);
/* 39 */     chatInputField.modifyPos(0.0F);
/*    */     
/* 41 */     add((BComponent)this.chatToggleButton, new Rectangle(0, 0, 68, 40));
/*    */     
/* 43 */     this.chatToggleButton.addListener((ComponentListener)new ActionListener()
/*    */         {
/*    */           public void actionPerformed(ActionEvent actionEvent)
/*    */           {
/* 47 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 48 */             if (SlidingChatInputController.this.chatToggleButton.isSelected()) {
/* 49 */               chatInputField.requestFocusToInputField();
/* 50 */               TcgUI.getUISoundPlayer().play("SlideOpen");
/*    */             } else {
/* 52 */               TcgUI.getUISoundPlayer().play("SlideClose");
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 60 */     return "menubuttoncontainer";
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 64 */     if (this.chatToggleButton.isSelected() && this.currentSlideVisibility < 1.0F)
/* 65 */       this.currentSlideVisibility = Math.min(1.0F, this.currentSlideVisibility + 3.0F * time); 
/* 66 */     if (!this.chatToggleButton.isSelected() && this.currentSlideVisibility > 0.0F) {
/* 67 */       this.currentSlideVisibility = Math.max(0.0F, this.currentSlideVisibility - 3.0F * time);
/*    */     }
/* 69 */     this.chatInputField.modifyPos(this.currentSlideVisibility);
/*    */   }
/*    */   
/*    */   public BToggleButton getChatToggleButton() {
/* 73 */     return this.chatToggleButton;
/*    */   }
/*    */   
/*    */   public void attachToChatWindowPane(ChatWindowPane chatPane) {
/* 77 */     chatPane.addChatComponent((BComponent)this, this.xPos, this.yPos, 75, 50);
/*    */   }
/*    */   
/*    */   public void setButtonToggleState(boolean state) {
/* 81 */     this.chatToggleButton.setSelected(state);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\SlidingChatInputController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */