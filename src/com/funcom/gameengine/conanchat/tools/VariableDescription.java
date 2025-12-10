/*    */ package com.funcom.gameengine.conanchat.tools;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Datatype;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VariableDescription
/*    */ {
/*    */   private Class<? extends Datatype> aClass;
/*    */   private String name;
/*    */   
/*    */   public VariableDescription(Class<? extends Datatype> aClass, String name) {
/* 13 */     this.aClass = aClass;
/* 14 */     this.name = name;
/*    */   }
/*    */   
/*    */   public Class<? extends Datatype> getAClass() {
/* 18 */     return this.aClass;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getUName() {
/* 26 */     return StringUtils.capitalizeFirst(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\tools\VariableDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */