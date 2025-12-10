/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundSetIOEnabledCallable
/*    */   implements Callable
/*    */ {
/*    */   boolean state = false;
/*    */   
/*    */   public SoundSetIOEnabledCallable(boolean state) {
/* 19 */     this.state = state;
/*    */   }
/*    */   
/*    */   public Reference<Sound> call() {
/* 23 */     SoundSystemFactory.getSoundSystem().setIOEnabled(this.state);
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundSetIOEnabledCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */