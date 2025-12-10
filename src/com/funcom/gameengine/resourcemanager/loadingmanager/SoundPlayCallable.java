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
/*    */ public class SoundPlayCallable
/*    */   implements Callable
/*    */ {
/* 15 */   Reference<Sound> snd = null;
/*    */   public SoundPlayCallable(Reference<Sound> snd) {
/* 17 */     this.snd = snd;
/*    */   }
/*    */   public Sound call() {
/* 20 */     if (this.snd.get() != null) {
/* 21 */       ((Sound)this.snd.get()).play();
/*    */     }
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundPlayCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */