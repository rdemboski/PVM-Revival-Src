/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BSlider;
/*    */ import com.jmex.bui.BTitleBar;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.enumeratedConstants.Orientation;
/*    */ import com.jmex.bui.enumeratedConstants.TitleOptions;
/*    */ import com.jmex.bui.headlessWindows.BTitledWindow;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.Justification;
/*    */ import com.jmex.bui.layout.Policy;
/*    */ import com.jmex.bui.layout.VGroupLayout;
/*    */ 
/*    */ public class Parameterizer extends BTitledWindow {
/*    */   public Parameterizer() {
/* 17 */     super("parameterizer", new BTitleBar("Parameterizer", "Parameterizer", TitleOptions.CLOSE), null, BuiSystem.getStyle());
/*    */ 
/*    */ 
/*    */     
/* 21 */     getComponentArea().setLayoutManager((BLayoutManager)new VGroupLayout(Justification.CENTER, Policy.STRETCH));
/*    */   }
/*    */   
/*    */   public void addParameter(Parameter parameter) {
/* 25 */     BSlider slider = new BSlider(Orientation.HORIZONTAL, new ParameterRangeModel(parameter));
/* 26 */     slider.setPreferredSize(300, 50);
/* 27 */     getComponentArea().add((BComponent)slider);
/* 28 */     pack();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\Parameterizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */