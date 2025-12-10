/*    */ package com.funcom.commons.jme.md5importer.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidAnimationException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -7115839715066462208L;
/*    */   private static final String message = "Animation does not match skeleton system.";
/*    */   
/*    */   public InvalidAnimationException(int joints, int animationJoints) {
/* 19 */     super("Animation does not match skeleton system. skeleton joints=" + joints + " animation joints=" + animationJoints);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\exception\InvalidAnimationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */