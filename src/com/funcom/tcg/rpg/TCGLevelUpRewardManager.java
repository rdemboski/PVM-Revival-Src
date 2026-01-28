/*    */ package com.funcom.tcg.rpg;
/*    */ import com.funcom.rpgengine2.quests.reward.QuestRewardDescription;

import java.util.ArrayList;
/*    */ import java.util.Collection;
import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
import java.util.Map;
/*    */ 
/*    */ public class TCGLevelUpRewardManager {
/*  8 */   private Map<Integer, List<QuestRewardDescription>> rewardsByLevel = new HashMap<Integer, List<QuestRewardDescription>>();
/*    */   
/*    */   public void clear() {
/* 11 */     this.rewardsByLevel.clear();
/*    */   }
/*    */   
/*    */   public void add(int level, List<QuestRewardDescription> rewards) {
/* 15 */     List<QuestRewardDescription> mappedRewards = this.rewardsByLevel.get(Integer.valueOf(level));
/* 16 */     if (mappedRewards == null) {
/* 17 */       mappedRewards = new ArrayList<QuestRewardDescription>();
/* 18 */       this.rewardsByLevel.put(Integer.valueOf(level), mappedRewards);
/*    */     } 
/* 20 */     mappedRewards.addAll(rewards);
/*    */   }
/*    */   
/*    */   public List<QuestRewardDescription> get(int level) {
/* 24 */     List<QuestRewardDescription> ret = this.rewardsByLevel.get(Integer.valueOf(level));
/*    */     
/* 26 */     if (ret != null) {
/* 27 */       return ret;
/*    */     }
/*    */     
/* 30 */     return Collections.EMPTY_LIST;
/*    */   }
/*    */   
/*    */   public Collection<List<QuestRewardDescription>> getAll() {
/* 34 */     return this.rewardsByLevel.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGLevelUpRewardManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */