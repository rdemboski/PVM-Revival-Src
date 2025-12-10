/*    */ package com.funcom.audio.dummy;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.Vector3f;
/*    */ 
/*    */ public class DummySound
/*    */   implements Sound
/*    */ {
/*  9 */   private Vector3f dummyPos = new Vector3f();
/*    */ 
/*    */ 
/*    */   
/*    */   public void play() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {}
/*    */ 
/*    */   
/*    */   public Vector3f getPos() {
/* 21 */     return this.dummyPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public void disposeWhenFinished() {}
/*    */   
/*    */   public void preInit() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\dummy\DummySound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */