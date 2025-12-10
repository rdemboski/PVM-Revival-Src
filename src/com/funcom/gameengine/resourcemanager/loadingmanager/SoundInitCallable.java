/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemException;
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
/*    */ public class SoundInitCallable
/*    */   implements Callable
/*    */ {
/*    */   public Reference<Sound> call() {
/*    */     try {
/* 22 */       SoundSystemFactory.getSoundSystem().init();
/*    */     }
/* 24 */     catch (SoundSystemException e) {
/* 25 */       String str = e.getMessage();
/* 26 */       if (str != null && str != "") {
/* 27 */         if (str.compareTo("Can't find dependent libraries") == -1) {
/* 28 */           LoadingManager.INSTANCE.sendCrash((Exception)e);
/*    */         
/*    */         }
/* 31 */         else if (LoadingManager.DEBUG_INFO) {
/* 32 */           System.out.printf("LoadingManager::SoundInitCallable - ignored crash for 64 bits machine (dependent libraries missing).\n", new Object[0]);
/* 33 */           e.printStackTrace();
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundInitCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */