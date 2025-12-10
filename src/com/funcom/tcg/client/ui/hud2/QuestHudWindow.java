/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoSetWindow;
/*    */ import com.funcom.tcg.client.ui.giftbox.SimpleInfoComponent;
/*    */ import com.funcom.tcg.client.ui.quest2.QuestWindowModel;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.layout.BorderLayout;
/*    */ import com.jmex.bui.layout.Justification;
/*    */ 
/*    */ 
/*    */ public class QuestHudWindow
/*    */   extends HudInfoSetWindow
/*    */   implements QuestWindowModel.QuestWindowModelListener
/*    */ {
/*    */   private QuestHudModel model;
/*    */   
/*    */   public QuestHudWindow(QuestHudModel model, ResourceManager resourceManager) {
/* 20 */     super(QuestHudWindow.class.getSimpleName(), model, resourceManager);
/* 21 */     this.model = model;
/* 22 */     model.addQuestWindowModelListener(this);
/* 23 */     setAlignment(Justification.RIGHT, Justification.TOP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BComponent createComponent(HudInfoModel infoModel) {
/* 28 */     SimpleInfoComponent component = new SimpleInfoComponent(infoModel, this.resourceManager);
/* 29 */     component.setIconButtonBorderAlignment(BorderLayout.EAST);
/* 30 */     return (BComponent)component;
/*    */   }
/*    */ 
/*    */   
/*    */   public void dismiss() {
/* 35 */     this.model.removeQuestWindowModelListener(this);
/* 36 */     super.dismiss();
/*    */   }
/*    */ 
/*    */   
/*    */   public void questWindowModelChanged(QuestWindowModel questWindowModel) {
/* 41 */     updateInfoComponents();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHit() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public BComponent getHitComponent(int i, int i1) {
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\QuestHudWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */