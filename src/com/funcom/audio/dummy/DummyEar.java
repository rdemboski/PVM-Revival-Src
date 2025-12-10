/*    */ package com.funcom.audio.dummy;
/*    */ 
/*    */ import com.funcom.audio.Ear;
/*    */ import com.funcom.audio.Vector3f;
/*    */ 
/*    */ 
/*    */ public class DummyEar
/*    */   implements Ear
/*    */ {
/* 10 */   private Vector3f dummyPos = new Vector3f();
/*    */ 
/*    */   
/*    */   public Vector3f getPos() {
/* 14 */     return this.dummyPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\dummy\DummyEar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */