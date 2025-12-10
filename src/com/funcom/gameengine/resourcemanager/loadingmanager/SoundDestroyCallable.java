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
/*    */ 
/*    */ 
/*    */ public class SoundDestroyCallable
/*    */   implements Callable
/*    */ {
/*    */   public Reference<Sound> call() {
/* 20 */     SoundSystemFactory.getSoundSystem().shutdown();
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundDestroyCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */