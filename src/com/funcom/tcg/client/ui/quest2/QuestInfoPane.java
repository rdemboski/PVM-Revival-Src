/*    */ package com.funcom.tcg.client.ui.quest2;
/*    */ 
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.ui.TcgBContainer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestInfoPane
/*    */   extends TcgBContainer
/*    */ {
/* 14 */   private static Rectangle R_LABEL_QUESTGIVER = new Rectangle(15, 303, 130, 29);
/* 15 */   private static Rectangle R_DETAIL_QUESTGIVER = new Rectangle(150, 303, 150, 29);
/* 16 */   private static Rectangle R_LABEL_LOCATION = new Rectangle(15, 265, 105, 29);
/* 17 */   private static Rectangle R_DETAIL_LOCATION = new Rectangle(125, 265, 125, 29);
/* 18 */   private static Rectangle R_QUEST_ICON = new Rectangle(303, 257, 48, 48);
/* 19 */   private static Rectangle R_QUEST_TEXT = new Rectangle(28, 28, 330, 204);
/*    */   
/*    */   BLabel lblQuestGiverLabel;
/*    */   
/*    */   BLabel lblQuestGiverDetail;
/*    */   BLabel lblLocationLabel;
/*    */   
/*    */   public QuestInfoPane() {
/* 27 */     this.lblQuestGiverLabel = new BLabel(TcgGame.getLocalizedText("questwindow.questinfopane.questgiverlabel", new String[0]));
/* 28 */     getContentPane().add((BComponent)this.lblQuestGiverLabel, R_LABEL_QUESTGIVER);
/*    */     
/* 30 */     this.lblQuestGiverDetail = new BLabel("Empty");
/* 31 */     getContentPane().add((BComponent)this.lblQuestGiverDetail, R_DETAIL_QUESTGIVER);
/*    */     
/* 33 */     this.lblLocationLabel = new BLabel(TcgGame.getLocalizedText("questwindow.questinfopane.locationlabel", new String[0]));
/* 34 */     getContentPane().add((BComponent)this.lblLocationLabel, R_LABEL_LOCATION);
/*    */     
/* 36 */     this.lblLocationDetail = new BLabel("Empty");
/* 37 */     getContentPane().add((BComponent)this.lblLocationDetail, R_DETAIL_LOCATION);
/*    */     
/* 39 */     this.lblQuestIcon = new BLabel("");
/* 40 */     getContentPane().add((BComponent)this.lblQuestIcon, R_QUEST_ICON);
/*    */     
/* 42 */     this.txaQuestText = new BLabel("");
/* 43 */     getContentPane().add((BComponent)this.txaQuestText, R_QUEST_TEXT);
/* 44 */     this.txaQuestText.setText("Empty by default");
/*    */   }
/*    */   BLabel lblLocationDetail; BLabel lblQuestIcon; BLabel txaQuestText;
/*    */   public void setQuestGiver(String questGiver) {
/* 48 */     this.lblQuestGiverDetail.setText(questGiver);
/*    */   }
/*    */   public void setLocation(String location) {
/* 51 */     this.lblLocationDetail.setText(location);
/*    */   }
/*    */   
/*    */   public void setQuestText(String questText) {
/* 55 */     this.txaQuestText.setText(questText);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\QuestInfoPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */