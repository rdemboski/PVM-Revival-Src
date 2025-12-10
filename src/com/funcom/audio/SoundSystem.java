package com.funcom.audio;

public interface SoundSystem {
  void setDataLoader(DataLoader paramDataLoader);
  
  void init() throws SoundSystemException;
  
  void shutdown();
  
  Project loadSoundProject(String paramString);
  
  void setIOEnabled(boolean paramBoolean);
  
  boolean isIOEnabled();
  
  void setAuditionEnabled(boolean paramBoolean);
  
  boolean isAuditionEnabled();
  
  void update();
  
  void setProfileEnabled(boolean paramBoolean);
  
  boolean isProfileEnabled();
  
  Ear getEar();
  
  Sound getSound(String paramString);
  
  void releaseInactiveData();
  
  void setReverbPreset(ReverbPreset paramReverbPreset);
  
  void setReverb(String paramString, boolean paramBoolean);
  
  boolean isMute();
  
  void setMute(boolean paramBoolean);
  
  boolean isMusicMute();
  
  void setMusicMute(boolean paramBoolean);
  
  boolean isSfxMute();
  
  void setSfxMute(boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\SoundSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */