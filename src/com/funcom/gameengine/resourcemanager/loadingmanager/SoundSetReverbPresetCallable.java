/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.ReverbPreset;
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
/*    */ public class SoundSetReverbPresetCallable
/*    */   implements Callable
/*    */ {
/* 18 */   ReverbPreset reverbPreset = null;
/*    */   public SoundSetReverbPresetCallable(ReverbPreset reverbPreset) {
/* 20 */     this.reverbPreset = reverbPreset;
/*    */   }
/*    */   
/*    */   public Sound call() {
/* 24 */     SoundSystemFactory.getSoundSystem().setReverbPreset(this.reverbPreset);
/* 25 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundSetReverbPresetCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */