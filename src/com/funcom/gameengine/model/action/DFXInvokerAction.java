/*    */ package com.funcom.gameengine.model.action;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class DFXInvokerAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "invoke-dfx";
/* 13 */   private static final Logger LOG = Logger.getLogger(DFXInvokerAction.class.getName());
/*    */   
/*    */   private String dfxName;
/*    */   
/*    */   public DFXInvokerAction(String dfxName) {
/* 18 */     this.dfxName = dfxName;
/*    */   }
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 22 */     LOG.info("Playing DGX on invoker: " + this.dfxName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDfxName() {
/* 27 */     return this.dfxName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return "invoke-dfx";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\action\DFXInvokerAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */