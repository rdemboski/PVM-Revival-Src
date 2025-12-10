/*    */ package com.funcom.gameengine.view;
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.EffectDescription;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.Reference;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundCreateCallable;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundPlayCallable;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundStopCallable;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public class AudioEffectHandler implements EffectHandler {
/* 14 */   private Reference<Sound> sound = new Reference(); private RepresentationalNode representationalNode;
/*    */   private boolean hasExecuted;
/*    */   
/*    */   public AudioEffectHandler(RepresentationalNode representationalNode) {
/* 18 */     this.representationalNode = representationalNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 23 */     EffectDescription description = sourceEffect.getDescription();
/* 24 */     String resource = description.getResource();
/* 25 */     if (LoadingManager.USE) {
/* 26 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundCreateCallable(this.sound, resource));
/* 27 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundRegisterCallable(this.sound, this.representationalNode));
/* 28 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundDisposeWhenFinishedCallable(this.sound));
/* 29 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundPlayCallable(this.sound));
/*    */     } else {
/*    */       
/* 32 */       this.sound.set(SoundSystemFactory.getSoundSystem().getSound(resource));
/* 33 */       if (this.sound != null && this.sound.get() != null) {
/* 34 */         this.representationalNode.registerSound((Sound)this.sound.get());
/* 35 */         ((Sound)this.sound.get()).disposeWhenFinished();
/* 36 */         ((Sound)this.sound.get()).play();
/*    */       } 
/*    */     } 
/* 39 */     this.hasExecuted = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {
/* 44 */     if (this.sound != null) {
/* 45 */       if (LoadingManager.USE) {
/* 46 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundStopCallable(this.sound));
/*    */       }
/* 48 */       else if (this.sound.get() != null) {
/* 49 */         ((Sound)this.sound.get()).stop();
/*    */       } 
/*    */     }
/* 52 */     this.hasExecuted = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 57 */     return (this.hasExecuted && (this.sound == null || (this.sound.get() != null && ((Sound)this.sound.get()).isFinished())));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\AudioEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */