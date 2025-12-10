/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundStopCallable
/*    */   implements Callable
/*    */ {
/* 16 */   Reference<Sound> snd = null;
/*    */   public SoundStopCallable(Reference<Sound> snd) {
/* 18 */     this.snd = snd;
/*    */   }
/*    */   public Reference<Sound> call() {
/* 21 */     if (this.snd.get() != null)
/* 22 */       ((Sound)this.snd.get()).stop(); 
/* 23 */     return this.snd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundStopCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */