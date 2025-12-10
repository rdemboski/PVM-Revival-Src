/*     */ package com.funcom.tcg.client.audio;
/*     */ import com.funcom.audio.ReverbPreset;
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.audio.SoundSystemFactory;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.Reference;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundCreateCallable;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundDisposeWhenFinishedCallable;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundPlayCallable;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetReverbPresetCallable;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundStopCallable;
/*     */ import com.funcom.gameengine.utils.LoadingScreenListener;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.filter.ElementFilter;
/*     */ import org.jdom.filter.Filter;
/*     */ 
/*     */ public class BackgroundSoundPlayer implements LoadingScreenListener {
/*  28 */   private List<Reference<Sound>> sounds = new ArrayList<Reference<Sound>>();
/*     */   private ResourceManager resourceManager;
/*     */   private String playingAudioId;
/*     */   
/*     */   public BackgroundSoundPlayer(ResourceManager resourceManager) {
/*  33 */     this.resourceManager = resourceManager;
/*  34 */     this.playingAudioId = "";
/*     */   }
/*     */   
/*     */   private void changeMapSounds(String mapName, AudioType audioType) {
/*  38 */     String mapNamePart = mapName + "-";
/*  39 */     String loadingScreenTypePart = "-" + AudioType.LoadingscreenSounds;
/*     */     
/*  41 */     boolean sameAsLastMap = this.playingAudioId.startsWith(mapNamePart);
/*  42 */     boolean wasLoadingScreen = this.playingAudioId.endsWith(loadingScreenTypePart);
/*  43 */     boolean isLoadingScreen = (audioType == AudioType.LoadingscreenSounds);
/*  44 */     boolean switchFromLoadingToAmbient = (wasLoadingScreen && !isLoadingScreen);
/*     */     
/*  46 */     if (!sameAsLastMap || switchFromLoadingToAmbient) {
/*  47 */       String audioId = mapName + "-" + audioType;
/*  48 */       boolean playedLastTime = audioId.equals(this.playingAudioId);
/*  49 */       if (!playedLastTime) {
/*  50 */         this.playingAudioId = audioId;
/*     */         
/*  52 */         stopCurrentSounds();
/*     */         
/*  54 */         Document audioConfig = getConfig(mapName);
/*  55 */         if (audioConfig != null) {
/*  56 */           setReverb(audioConfig, audioType);
/*  57 */           playMapSounds(audioConfig, audioType);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Document getConfig(String mapName) {
/*  64 */     Document audioConfig = getXmlConfig(mapName);
/*     */     
/*  66 */     if (audioConfig == null) {
/*  67 */       audioConfig = getBinaryConfig(mapName);
/*     */     }
/*  69 */     return audioConfig;
/*     */   }
/*     */   
/*     */   private Document getBinaryConfig(String mapName) {
/*     */     try {
/*  74 */       String binaryPath = "binary/" + mapName + "/audio" + ".bunk";
/*  75 */       ByteBuffer blob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, binaryPath, CacheType.NOT_CACHED);
/*  76 */       return BinaryLoader.convertBlobToMap(blob, TcgGame.getResourceGetter());
/*  77 */     } catch (ResourceManagerException ignore) {
/*     */ 
/*     */       
/*  80 */       return null;
/*     */     } 
/*     */   }
/*     */   private Document getXmlConfig(String mapName) {
/*     */     try {
/*  85 */       String originalPath = "xml/" + mapName + "/audio" + ".xml";
/*  86 */       return (Document)this.resourceManager.getResource(Document.class, originalPath, CacheType.NOT_CACHED);
/*  87 */     } catch (ResourceManagerException ignore) {
/*     */ 
/*     */       
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   private void playMapSounds(Document audioConfig, AudioType audioType) {
/*  94 */     Iterator<Element> soundGroups = audioConfig.getDescendants((Filter)new ElementFilter(audioType.getTagName()));
/*  95 */     while (soundGroups.hasNext()) {
/*  96 */       Element soundGroup = soundGroups.next();
/*  97 */       loadAndPlaySounds(soundGroup);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setReverb(Document audioConfig, AudioType audioType) {
/* 102 */     Iterator<Element> soundGroups = audioConfig.getDescendants((Filter)new ElementFilter(audioType.getTagName()));
/* 103 */     if (soundGroups.hasNext()) {
/* 104 */       boolean assignedReverb = false;
/* 105 */       Element soundGroup = soundGroups.next();
/* 106 */       Element reverbElement = soundGroup.getChild("reverb");
/* 107 */       if (reverbElement != null) {
/*     */         
/* 109 */         String reverbId = reverbElement.getTextTrim();
/* 110 */         if (!reverbId.isEmpty()) {
/*     */           
/* 112 */           boolean isPreset = "true".equalsIgnoreCase(reverbElement.getAttributeValue("ispreset"));
/* 113 */           if (isPreset) {
/* 114 */             ReverbPreset reverbPreset = ReverbPreset.valueOf(reverbId.toUpperCase());
/*     */             
/* 116 */             if (LoadingManager.USE) {
/* 117 */               LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetReverbPresetCallable(reverbPreset));
/*     */             } else {
/* 119 */               SoundSystemFactory.getSoundSystem().setReverbPreset(reverbPreset);
/* 120 */             }  assignedReverb = true;
/*     */           } else {
/* 122 */             if (LoadingManager.USE) {
/* 123 */               LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetReverbCallable(reverbId, true));
/*     */             } else {
/* 125 */               SoundSystemFactory.getSoundSystem().setReverb(reverbId, true);
/* 126 */             }  assignedReverb = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       if (!assignedReverb)
/* 132 */         if (LoadingManager.USE) {
/* 133 */           LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetReverbPresetCallable(ReverbPreset.OFF));
/*     */         } else {
/* 135 */           SoundSystemFactory.getSoundSystem().setReverbPreset(ReverbPreset.OFF);
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadAndPlaySounds(Element soundGroup) {
/* 141 */     Iterator<Element> events = soundGroup.getDescendants((Filter)new ElementFilter("resource"));
/* 142 */     while (events.hasNext()) {
/* 143 */       Element event = events.next();
/* 144 */       String eventId = event.getTextTrim();
/* 145 */       if (LoadingManager.USE) {
/* 146 */         Reference<Sound> reference = new Reference();
/* 147 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundCreateCallable(reference, eventId));
/* 148 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundDisposeWhenFinishedCallable(reference));
/* 149 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundPlayCallable(reference));
/* 150 */         this.sounds.add(reference);
/*     */         continue;
/*     */       } 
/* 153 */       Reference<Sound> ambientSound = new Reference();
/* 154 */       ambientSound.set(SoundSystemFactory.getSoundSystem().getSound(eventId));
/* 155 */       if (ambientSound != null) {
/* 156 */         ((Sound)ambientSound.get()).disposeWhenFinished();
/* 157 */         ((Sound)ambientSound.get()).play();
/* 158 */         this.sounds.add(ambientSound);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void stopCurrentSounds() {
/* 165 */     if (LoadingManager.USE) {
/* 166 */       for (Reference<Sound> sound : this.sounds) {
/* 167 */         if (sound.get() != null) {
/* 168 */           LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundStopCallable(sound));
/*     */         }
/*     */       } 
/*     */     } else {
/* 172 */       for (Reference<Sound> sound : this.sounds) {
/* 173 */         if (sound.get() != null)
/* 174 */           ((Sound)sound.get()).stop(); 
/*     */       } 
/*     */     } 
/* 177 */     this.sounds.clear();
/*     */   }
/*     */   
/*     */   public void reset() {
/* 181 */     stopCurrentSounds();
/* 182 */     this.playingAudioId = "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenStarted(String toLoadMapName) {
/* 187 */     changeMapSounds(toLoadMapName, AudioType.LoadingscreenSounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 192 */     changeMapSounds(loadedMapName, AudioType.AmbientSounds);
/*     */   }
/*     */   
/*     */   private enum AudioType {
/* 196 */     AmbientSounds, LoadingscreenSounds;
/*     */     
/*     */     public String getTagName() {
/* 199 */       return name().toLowerCase();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\audio\BackgroundSoundPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */