/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.commons.utils.GlobalTime;
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.speach.SpeachContext;
/*    */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ 
/*    */ public class ClickSpeachAction
/*    */   extends AbstractAction
/*    */ {
/*    */   private static final long RECLICK_TIME = 3000L;
/*    */   public static final String ACTION_NAME = "click-speach";
/*    */   private SpeachMapping speachMapping;
/*    */   private PropNode propNode;
/* 20 */   private long nextClick = 0L;
/*    */   
/*    */   public ClickSpeachAction(SpeachMapping speachMapping, PropNode propNode) {
/* 23 */     this.speachMapping = speachMapping;
/* 24 */     this.propNode = propNode;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 28 */     return "click-speach";
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 33 */     if (GlobalTime.getInstance().getCurrentTime() > this.nextClick) {
/* 34 */       String key = this.speachMapping.getRandomSpeachForContext(SpeachContext.CLICK);
/* 35 */       if (key != null) {
/* 36 */         String text = JavaLocalization.getInstance().getLocalizedRPGText(key);
/* 37 */         MainGameState.getChatUIController().createChatBubble(text, text, this.propNode);
/*    */       } 
/* 39 */       this.nextClick = GlobalTime.getInstance().getCurrentTime() + 3000L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\ClickSpeachAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */