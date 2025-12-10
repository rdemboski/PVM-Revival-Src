/*    */ package com.funcom.tcg.client.ui.achievements;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.reward.QuestCategory;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.jmex.bui.event.ActionEvent;
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AchievementViewListener
/*    */   implements ActionListener
/*    */ {
/*    */   private QuestCategory category;
/*    */   
/*    */   public AchievementViewListener(QuestCategory category) {
/* 20 */     this.category = category;
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent event) {
/* 25 */     MainGameState.getAchievementWindow().openAchievementView(this.category);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\achievements\AchievementViewListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */