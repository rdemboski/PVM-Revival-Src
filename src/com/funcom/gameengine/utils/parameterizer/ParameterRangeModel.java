/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ import com.jmex.bui.BoundedRangeModel;
/*    */ 
/*    */ public class ParameterRangeModel
/*    */   extends BoundedRangeModel {
/*    */   private static final int EXTENT = 1;
/*    */   private Parameter parameter;
/*    */   
/*    */   public ParameterRangeModel(Parameter parameter) {
/* 11 */     super(parameter.getMinimum(), parameter.getAsIntegerValue(), 1, parameter.getMaximum());
/* 12 */     this.parameter = parameter;
/*    */   }
/*    */   
/*    */   public int getMinimum() {
/* 16 */     return this.parameter.getMinimum();
/*    */   }
/*    */   
/*    */   public int getMaximum() {
/* 20 */     return this.parameter.getMaximum();
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 24 */     return this.parameter.getAsIntegerValue();
/*    */   }
/*    */   
/*    */   public void setValue(int i) {
/* 28 */     if (i >= getMinimum() && i <= getMaximum())
/* 29 */       this.parameter.setAsIntegerValue(i); 
/*    */   }
/*    */   
/*    */   public int getScrollIncrement() {
/* 33 */     return 1;
/*    */   }
/*    */   
/*    */   public void setMinimum(int i) {
/* 37 */     throw new IllegalStateException("THIS CANNOT BE CALLED MANUALLY!");
/*    */   }
/*    */   
/*    */   public void setMaximum(int i) {
/* 41 */     throw new IllegalStateException("THIS CANNOT BE CALLED MANUALLY!");
/*    */   }
/*    */   
/*    */   public void setExtent(int i) {
/* 45 */     throw new IllegalStateException("THIS CANNOT BE CALLED MANUALLY!");
/*    */   }
/*    */   
/*    */   public boolean setRange(int i, int i1, int i2, int i3) {
/* 49 */     throw new IllegalStateException("THIS CANNOT BE CALLED MANUALLY!");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\ParameterRangeModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */