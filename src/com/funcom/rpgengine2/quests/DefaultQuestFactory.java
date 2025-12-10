/*    */ package com.funcom.rpgengine2.quests;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ 
/*    */ 
/*    */ public class DefaultQuestFactory
/*    */   implements QuestFactory
/*    */ {
/*  9 */   public static final QuestFactory INSTANCE = new DefaultQuestFactory();
/*    */   
/*    */   public Quest getInstance(QuestDescription questDescription, RpgEntity questOwner) {
/* 12 */     return new Quest(questDescription);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\DefaultQuestFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */