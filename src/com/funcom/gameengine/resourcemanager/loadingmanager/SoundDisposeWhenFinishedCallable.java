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
/*    */ public class SoundDisposeWhenFinishedCallable
/*    */   implements Callable
/*    */ {
/* 15 */   Reference<Sound> snd = null;
/*    */   public SoundDisposeWhenFinishedCallable(Reference<Sound> snd) {
/* 17 */     this.snd = snd;
/*    */   }
/*    */   public Reference<Sound> call() {
/* 20 */     if (this.snd.get() != null)
/* 21 */       ((Sound)this.snd.get()).disposeWhenFinished(); 
/* 22 */     return this.snd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundDisposeWhenFinishedCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */