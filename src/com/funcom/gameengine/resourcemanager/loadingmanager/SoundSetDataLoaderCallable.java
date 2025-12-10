/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.DataLoader;
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import com.funcom.gameengine.audio.ResourceDataLoader;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundSetDataLoaderCallable
/*    */   implements Callable
/*    */ {
/* 19 */   ResourceDataLoader loader = null;
/*    */   public SoundSetDataLoaderCallable(ResourceDataLoader loader) {
/* 21 */     this.loader = loader;
/*    */   }
/*    */   public Reference<Sound> call() {
/* 24 */     SoundSystemFactory.getSoundSystem().setDataLoader((DataLoader)this.loader);
/* 25 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundSetDataLoaderCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */