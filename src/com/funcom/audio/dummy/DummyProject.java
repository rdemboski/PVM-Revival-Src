/*    */ package com.funcom.audio.dummy;
/*    */ 
/*    */ import com.funcom.audio.Project;
/*    */ import com.funcom.audio.Sound;
/*    */ 
/*    */ 
/*    */ public class DummyProject
/*    */   implements Project
/*    */ {
/* 10 */   private Sound dummySound = new DummySound();
/*    */ 
/*    */   
/*    */   public Sound getSound(String path) {
/* 14 */     return this.dummySound;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\dummy\DummyProject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */