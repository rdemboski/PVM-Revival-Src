/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientBuff
/*    */ {
/*    */   private String classId;
/*    */   private String cureElementId;
/*    */   private String icon;
/*    */   private String dfxScript;
/*    */   
/*    */   public ClientBuff(String classId, String cureElementId) {
/* 13 */     this.classId = classId;
/* 14 */     this.cureElementId = cureElementId;
/*    */   }
/*    */   
/*    */   public ClientBuff(String classId) {
/* 18 */     this.classId = classId;
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 22 */     return this.classId;
/*    */   }
/*    */   
/*    */   public String getCureElementId() {
/* 26 */     return this.cureElementId;
/*    */   }
/*    */   
/*    */   public void setCureElementId(String cureElementId) {
/* 30 */     this.cureElementId = cureElementId;
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 34 */     return this.icon;
/*    */   }
/*    */   
/*    */   public void setIcon(String icon) {
/* 38 */     this.icon = icon;
/*    */   }
/*    */   
/*    */   public String getDfxScript() {
/* 42 */     return this.dfxScript;
/*    */   }
/*    */   
/*    */   public void setDfxScript(String dfxScript) {
/* 46 */     this.dfxScript = dfxScript;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientBuff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */