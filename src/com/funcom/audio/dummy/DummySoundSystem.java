/*     */ package com.funcom.audio.dummy;
/*     */ import com.funcom.audio.Ear;
/*     */ import com.funcom.audio.Project;
import com.funcom.audio.*;
/*     */ 
/*     */ public class DummySoundSystem implements SoundSystem {
/*   6 */   private Project dummyProject = new DummyProject();
/*   7 */   private Ear dummyEar = new DummyEar();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataLoader(DataLoader dataLoader) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws SoundSystemException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {}
/*     */ 
/*     */   
/*     */   public Project loadSoundProject(String path) {
/*  23 */     return this.dummyProject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIOEnabled(boolean enabled) {}
/*     */ 
/*     */   
/*     */   public boolean isIOEnabled() {
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuditionEnabled(boolean auditionEnabled) {}
/*     */ 
/*     */   
/*     */   public boolean isAuditionEnabled() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfileEnabled(boolean enabled) {}
/*     */ 
/*     */   
/*     */   public boolean isProfileEnabled() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ear getEar() {
/*  59 */     return this.dummyEar;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound(String path) {
/*  64 */     return this.dummyProject.getSound(path);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseInactiveData() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReverbPreset(ReverbPreset preset) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReverb(String reverbPath, boolean turnOffIfMissing) {}
/*     */ 
/*     */   
/*     */   public boolean isMute() {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMute(boolean mute) {}
/*     */ 
/*     */   
/*     */   public boolean isMusicMute() {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicMute(boolean mute) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSfxMute() {
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public void setSfxMute(boolean mute) {}
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\dummy\DummySoundSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */