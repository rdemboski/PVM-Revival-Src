/*    */ package com.funcom.audio.fmod;
/*    */ 
/*    */ import java.util.WeakHashMap;
/*    */ import org.jouvieje.FmodEx.Sound;
/*    */ 
/*    */ public class SoundReference
/*    */ {
/*    */   private final Sound sound;
/*    */   private final String waveformName;
/*    */   private final WeakHashMap<Object, Object> weakOwners;
/*    */   
/*    */   public SoundReference(String waveformName, Sound sound) {
/* 13 */     this.sound = sound;
/* 14 */     this.waveformName = waveformName;
/* 15 */     this.weakOwners = new WeakHashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public Sound getSound() {
/* 19 */     return this.sound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void release() {
/* 24 */     this.sound.release();
/*    */   }
/*    */   
/*    */   public boolean isReferenced() {
/* 28 */     return !this.weakOwners.isEmpty();
/*    */   }
/*    */   
/*    */   public void addOwner(Object owner) {
/* 32 */     this.weakOwners.put(owner, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\SoundReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */