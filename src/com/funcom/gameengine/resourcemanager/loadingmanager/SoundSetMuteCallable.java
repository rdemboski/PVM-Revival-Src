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
/*    */ public class SoundSetMuteCallable
/*    */   implements Callable
/*    */ {
/*    */   boolean MusicMute = false;
/*    */   boolean SoundMute = false;
/*    */   
/*    */   public SoundSetMuteCallable(boolean MusicMute, boolean SoundMute) {
/* 20 */     this.MusicMute = MusicMute;
/* 21 */     this.SoundMute = SoundMute;
/*    */   }
/*    */   
/*    */   public Reference<Sound> call() {
/* 25 */     SoundSystemFactory.getSoundSystem().setMusicMute(this.MusicMute);
/* 26 */     SoundSystemFactory.getSoundSystem().setSfxMute(this.SoundMute);
/*    */     
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundSetMuteCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */