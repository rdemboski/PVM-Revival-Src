/*    */ package com.funcom.rpgengine2;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SkillId
/*    */   implements Serializable
/*    */ {
/*    */   private int tier;
/*    */   private String itemId;
/*    */   
/*    */   public SkillId() {}
/*    */   
/*    */   public SkillId(String itemId, int tier) {
/* 14 */     this.itemId = itemId;
/* 15 */     this.tier = tier;
/*    */   }
/*    */   
/*    */   public void setTier(int tier) {
/* 19 */     this.tier = tier;
/*    */   }
/*    */   
/*    */   public void setItemId(String itemId) {
/* 23 */     this.itemId = itemId;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 27 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 31 */     return this.tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     int result = this.tier;
/* 37 */     result = 31 * result + ((this.itemId != null) ? this.itemId.hashCode() : 0);
/* 38 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 44 */     if (!(obj instanceof SkillId))
/* 45 */       return false; 
/* 46 */     SkillId skillId = (SkillId)obj;
/* 47 */     return (this.itemId.equals(skillId.itemId) && this.tier == skillId.tier);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 53 */     return "Skill{" + this.itemId + ", tier: " + this.tier + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\SkillId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */