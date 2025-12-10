/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.jme.util.Timer;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
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
/*    */ public class PlaySoundLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 20 */   Sound sound = null;
/* 21 */   private Future<Sound> PlaySoundFuture = null;
/* 22 */   float RequestedTime = 0.0F;
/*    */   
/*    */   public PlaySoundLMToken(Sound sound) {
/* 25 */     this.sound = sound;
/* 26 */     this.RequestedTime = Timer.getTimer().getTimeInSeconds();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processRequestAssets() {
/* 31 */     if (LoadingManager.THREADED_SOUND) {
/* 32 */       Callable<Integer> callable = new PlaySoundCallable();
/* 33 */       this.PlaySoundFuture = (Future)LoadingManager.INSTANCE.submitSoundCallable(callable);
/*    */     } else {
/*    */       
/* 36 */       this.sound.play();
/*    */     } 
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public boolean processWaitingAssets() {
/* 43 */     return (this.PlaySoundFuture == null || this.PlaySoundFuture.isDone());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 48 */     this.PlaySoundFuture = null;
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public class PlaySoundCallable
/*    */     implements Callable
/*    */   {
/*    */     public Sound call() {
/* 59 */       if (PlaySoundLMToken.this.sound != null && 
/* 60 */         Timer.getTimer().getTimeInSeconds() - PlaySoundLMToken.this.RequestedTime <= LoadingManager.MAX_TIMELAG_SOUNDPLAYING) {
/* 61 */         PlaySoundLMToken.this.sound.play();
/*    */       }
/*    */       
/* 64 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\PlaySoundLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */