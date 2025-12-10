/*     */ package com.funcom.tcg.client.ui.chat;
/*     */ 
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.util.Dimension;
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
/*     */ public class ChatBubble
/*     */ {
/*     */   public static final float CHAT_BUBBLE_BASE_DURATION = 2.0F;
/*     */   public static final float CHAT_BUBBLE_EXTRA_DURATION_PER_LETTER = 0.1F;
/*     */   public static final float CHAT_BUBBLE_FADEOUT_TIME = 0.5F;
/*     */   public static final int maxBubbleHeight = 100;
/*     */   public static final int maxBubbleWidth = 300;
/*     */   public static final int bubbleHoverHeight = 100;
/*     */   public static final float CHAT_BUBBLE_BASE_ALPHA = 0.8F;
/*  31 */   private float timeSpentExisting = 0.0F;
/*     */   
/*     */   private float timeToLive;
/*     */   
/*     */   private PropNode player;
/*     */   
/*     */   private String message;
/*     */   private ChatBubbleWindow chatPane;
/*     */   private ChatBubbleLabel lblMessage;
/*     */   private boolean sizeAdapted = false;
/*     */   private boolean finished = false;
/*     */   private boolean info = false;
/*  43 */   private int xPos = 0, yPos = 0;
/*     */   
/*     */   public ChatBubble(PropNode player, String message, ChatUIController chatUIController, boolean info) {
/*  46 */     this.player = player;
/*  47 */     this.info = info;
/*     */     
/*  49 */     this.message = message;
/*  50 */     this.chatPane = new ChatBubbleWindow(TcgGame.getResourceManager(), 300, 100);
/*     */     
/*  52 */     BuiSystem.addWindow(this.chatPane);
/*     */     
/*  54 */     chatUIController.addToUpdateList(this);
/*     */     
/*  56 */     this.lblMessage = new ChatBubbleLabel(this.message, 0.8F);
/*  57 */     this.lblMessage.setName(message);
/*  58 */     this.lblMessage.setStyleClass(info ? "chatbubble.window.infolabel" : this.chatPane.getStyleClassLabel());
/*     */     
/*  60 */     this.chatPane.addChatComponent((BComponent)this.lblMessage, this.xPos, this.yPos, 300, 100);
/*  61 */     this.lblMessage.setVisible(false);
/*     */     
/*  63 */     this.timeToLive = 2.0F + 0.1F * message.length();
/*  64 */     float ratio = (System.getProperty("tcg.infobubble") != null) ? Float.valueOf(System.getProperty("tcg.infobubble")).floatValue() : 0.4F;
/*  65 */     this.timeToLive *= info ? ratio : 1.0F;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  69 */     this.timeSpentExisting += time;
/*     */ 
/*     */     
/*  72 */     if (this.timeSpentExisting - this.timeToLive > 0.5F || this.player == null) {
/*  73 */       this.chatPane.removeChatBubble((BComponent)this.lblMessage, true);
/*  74 */       if (this.info) MainGameState.getChatUIController().getInfoBubbleMap().remove(this.lblMessage.getText()); 
/*  75 */       this.finished = true;
/*     */     } else {
/*  77 */       updatePosition(this.player);
/*  78 */       if (this.chatPane != null) {
/*  79 */         this.chatPane.moveChatBubble(this.lblMessage, this.xPos, this.yPos, this.lblMessage.getWidth(), this.lblMessage.getHeight());
/*     */ 
/*     */ 
/*     */         
/*  83 */         if (!this.sizeAdapted) {
/*  84 */           Dimension dim = this.lblMessage.getPreferredSize(300, 100);
/*  85 */           this.chatPane.removeChatBubble((BComponent)this.lblMessage, false);
/*  86 */           this.chatPane.addChatComponent((BComponent)this.lblMessage, Math.max(0, this.xPos - dim.width / 2), Math.max(0, this.yPos), dim.width, dim.height);
/*  87 */           this.lblMessage.setAlpha(1.0F);
/*  88 */           this.lblMessage.setVisible(true);
/*  89 */           this.sizeAdapted = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  94 */       if (this.timeSpentExisting - this.timeToLive <= 0.5F) {
/*  95 */         float alpha = 0.8F - (this.timeSpentExisting - this.timeToLive) / 0.5F * 0.8F;
/*  96 */         this.lblMessage.setAlpha(alpha);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePosition(PropNode player) {
/* 103 */     Vector3f playerPos = player.getWorldTranslation();
/* 104 */     playerPos = DisplaySystem.getDisplaySystem().getScreenCoordinates(playerPos);
/*     */     
/* 106 */     this.xPos = (int)playerPos.getX() - this.lblMessage.getWidth() / 2;
/* 107 */     this.yPos = (int)(playerPos.getY() + 100.0F);
/* 108 */     if (this.timeSpentExisting > this.timeToLive / 4.0F && !this.info) {
/* 109 */       this.yPos = (int)(this.yPos + (this.timeSpentExisting - this.timeToLive / 4.0F) * 20.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 114 */     return this.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ChatBubbleLabel
/*     */     extends BLabel
/*     */   {
/*     */     public ChatBubbleLabel(String s, float backgroundAlpha) {
/* 123 */       super(s);
/* 124 */       this._alpha = backgroundAlpha;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getBackgroundAlpha() {
/* 129 */       return this._alpha;
/*     */     }
/*     */     
/*     */     public void setBackgroundAlpha(float newAlpha) {
/* 133 */       this._alpha = newAlpha;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatBubble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */