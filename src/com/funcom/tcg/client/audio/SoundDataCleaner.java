/*    */ package com.funcom.tcg.client.audio;
/*    */ 
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundReleaseInactiveDataCallable;
/*    */ import com.funcom.gameengine.utils.LoadingScreenListener;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ public class SoundDataCleaner
/*    */   implements LoadingScreenListener
/*    */ {
/*    */   public void notifyLoadingScreenStarted(String toLoadMapName) {
/* 14 */     if (LoadingManager.USE) {
/* 15 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundReleaseInactiveDataCallable());
/*    */     } else {
/*    */       
/* 18 */       SoundSystemFactory.getSoundSystem().releaseInactiveData();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 24 */     if (LoadingManager.USE) {
/* 25 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundReleaseInactiveDataCallable());
/*    */     } else {
/*    */       
/* 28 */       SoundSystemFactory.getSoundSystem().releaseInactiveData();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\audio\SoundDataCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */