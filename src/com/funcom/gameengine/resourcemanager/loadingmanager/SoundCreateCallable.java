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
/*    */ 
/*    */ public class SoundCreateCallable
/*    */   implements Callable
/*    */ {
/* 17 */   String res = "";
/* 18 */   Reference<Sound> snd = null;
/*    */   public SoundCreateCallable(Reference<Sound> snd, String res) {
/* 20 */     this.snd = snd;
/* 21 */     this.res = res;
/*    */   }
/*    */   public Reference<Sound> call() {
/* 24 */     this.snd.set(SoundSystemFactory.getSoundSystem().getSound(this.res));
/* 25 */     return this.snd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundCreateCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */