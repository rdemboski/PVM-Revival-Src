/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.SoundSystem;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundUpdateCallable
/*    */   implements Callable
/*    */ {
/*    */   public SoundSystem call() {
/* 17 */     SoundSystemFactory.getSoundSystem().update();
/* 18 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundUpdateCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */