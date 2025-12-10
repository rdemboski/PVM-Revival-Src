/*    */ package com.funcom.tcg.client.ui.tips;
/*    */ 
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoAction;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class TipInfoModel
/*    */   implements HudInfoModel, HudInfoAction
/*    */ {
/* 14 */   private static final Logger LOGGER = Logger.getLogger(TipInfoModel.class.getName());
/*    */   
/*    */   public static final String ICON_PATH = "gui/icons/items/alert.png";
/*    */   private static final int TIP_PRIORITY = -1;
/*    */   private final String tutorialText;
/*    */   
/*    */   public TipInfoModel(String tutorialText) {
/* 21 */     this.tutorialText = tutorialText;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 26 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 31 */     return "gui/icons/items/alert.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 36 */     return TcgGame.getLocalizedText("tutorial.click", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActivatable() {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void activate() {
/* 47 */     ErrorWindowCreator.instance().createErrorMessage(TcgGame.getLocalizedText("tutorial.heading", new String[0]), TcgGame.getLocalizedText(this.tutorialText, new String[0]));
/* 48 */     MainGameState.getTips().remove(this.tutorialText);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\tips\TipInfoModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */