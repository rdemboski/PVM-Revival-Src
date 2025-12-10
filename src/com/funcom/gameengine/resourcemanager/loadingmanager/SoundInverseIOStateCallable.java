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
/*    */ public class SoundInverseIOStateCallable
/*    */   implements Callable
/*    */ {
/*    */   public Reference<Sound> call() {
/* 17 */     boolean newState = !SoundSystemFactory.getSoundSystem().isIOEnabled();
/* 18 */     SoundSystemFactory.getSoundSystem().setIOEnabled(newState);
/* 19 */     System.err.println("Toggled SoundSystem io: " + (newState ? "enabled" : "disabled"));
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundInverseIOStateCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */