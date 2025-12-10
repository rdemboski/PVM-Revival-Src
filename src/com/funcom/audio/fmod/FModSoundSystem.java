/*     */ package com.funcom.audio.fmod;
/*     */ 
/*     */ import com.funcom.audio.DataLoader;
/*     */ import com.funcom.audio.Ear;
/*     */ import com.funcom.audio.Project;
/*     */ import com.funcom.audio.ReverbPreset;
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.audio.SoundSystem;
/*     */ import com.funcom.audio.SoundSystemException;
/*     */ import com.funcom.audio.SoundSystemFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jouvieje.FmodDesigner.Defines.VERSIONS;
/*     */ import org.jouvieje.FmodDesigner.EventCategory;
/*     */ import org.jouvieje.FmodDesigner.EventSystem;
/*     */ import org.jouvieje.FmodDesigner.FmodDesigner;
/*     */ import org.jouvieje.FmodDesigner.InitFmodDesigner;
/*     */ import org.jouvieje.FmodEx.Defines.VERSIONS;
/*     */ import org.jouvieje.FmodEx.Enumerations.FMOD_RESULT;
/*     */ import org.jouvieje.FmodEx.Exceptions.InitException;
/*     */ import org.jouvieje.FmodEx.FmodEx;
/*     */ import org.jouvieje.FmodEx.Init;
/*     */ import org.jouvieje.FmodEx.Misc.BufferUtils;
/*     */ import org.jouvieje.FmodEx.Sound;
/*     */ import org.jouvieje.FmodEx.Structures.FMOD_CREATESOUNDEXINFO;
/*     */ import org.jouvieje.FmodEx.Structures.FMOD_REVERB_PROPERTIES;
/*     */ import org.jouvieje.FmodEx.System;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FModSoundSystem
/*     */   implements SoundSystem
/*     */ {
/*  43 */   private static final Logger LOGGER = Logger.getLogger(FModSoundSystem.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EventSystem eventSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private Map<String, FModProject> projects = Collections.synchronizedMap(new HashMap<String, FModProject>()); private boolean ioEnabled; private boolean auditionEnabled; private boolean profileEnabled; private boolean wantMute; private boolean wantMusicMute; private boolean wantSfxMute; private boolean ioStarted; private boolean auditionStarted; private boolean profileStarted;
/*  75 */   private Map<String, SoundReference> rawSounds = new HashMap<String, SoundReference>(); private boolean muted; private boolean musicMuted; private boolean sfxMuted; private boolean ambientMuted; private boolean guiMuted; private int ioGeneration; private FModEar ear; private System system; private static boolean loadedLibs; private DataLoader dataLoader;
/*     */   private EventCategory masterCategory;
/*     */   private static final String LINUX_MISSING_LIBRARIES = "libfmodex.so: cannot open shared object file: No such file or directory";
/*     */   
/*     */   public void setDataLoader(DataLoader dataLoader) {
/*  80 */     this.dataLoader = dataLoader;
/*     */   }
/*     */   
/*     */   public DataLoader getDataLoader() {
/*  84 */     return this.dataLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() throws SoundSystemException {
/*  89 */     String useDummySoundSystem = System.getProperty("sound.dummy");
/*  90 */     if (useDummySoundSystem != null && useDummySoundSystem.equals("true")) {
/*  91 */       SoundSystemFactory.setDummyEnabled(true);
/*     */       return;
/*     */     } 
/*     */     try {
/*  95 */       loadLibs();
/*  96 */       initialize();
/*  97 */       setIOEnabled(true);
/*  98 */       this.ear = new FModEar(this);
/*  99 */       update();
/* 100 */     } catch (SoundSystemException e) {
/* 101 */       SoundSystemFactory.setDummyEnabled(true);
/* 102 */       if (e.getCause() instanceof InitException)
/* 103 */       { InitException cause = (InitException)e.getCause();
/* 104 */         if (cause.getMessage().startsWith("java.lang.UnsatisfiedLinkError") && cause.getMessage().endsWith("libfmodex.so: cannot open shared object file: No such file or directory")) {
/* 105 */           LOGGER.error("Linux dynamic link libraries missing: " + cause.getMessage());
/*     */         } else {
/* 107 */           throw e;
/*     */         }  }
/* 109 */       else { throw e; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 115 */     if (this.eventSystem != null) {
/* 116 */       System system = new System();
/* 117 */       this.eventSystem.getSystemObject(system);
/* 118 */       system.setFileSystem(null, null, null, null, -1);
/*     */       
/* 120 */       FMOD_RESULT result = this.eventSystem.release();
/* 121 */       isOk(result);
/*     */     } 
/*     */   }
/*     */   
/*     */   System getSystem() {
/* 126 */     return this.system;
/*     */   }
/*     */   
/*     */   EventSystem getEventSystem() {
/* 130 */     return this.eventSystem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIOEnabled(boolean enabled) {
/* 135 */     this.ioEnabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIOEnabled() {
/* 140 */     return this.ioEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProfileEnabled(boolean enabled) {
/* 145 */     this.profileEnabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProfileEnabled() {
/* 150 */     return this.profileEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ear getEar() {
/* 155 */     return this.ear;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound(String path) {
/* 160 */     int index = Math.max(path.indexOf('/'), 0);
/* 161 */     String projectName = path.substring(0, index);
/* 162 */     Project project = loadSoundProject(projectName + ".fev");
/*     */     
/* 164 */     if (project != null) {
/* 165 */       return project.getSound(path.substring(index + 1));
/*     */     }
/*     */     
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAuditionEnabled(boolean auditionEnabled) {
/* 173 */     this.auditionEnabled = auditionEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAuditionEnabled() {
/* 178 */     return this.auditionEnabled;
/*     */   }
/*     */   
/*     */   private void startIo() {
/* 182 */     this.ioGeneration++;
/*     */     
/* 184 */     synchronized (this.projects) {
/* 185 */       for (FModProject project : this.projects.values()) {
/* 186 */         project.startIo();
/*     */       }
/*     */     } 
/*     */     
/* 190 */     this.ioStarted = true;
/* 191 */     LOGGER.log((Priority)Level.INFO, "Started Io");
/*     */   }
/*     */   
/*     */   private void stopIo() {
/* 195 */     if (this.ioStarted) {
/* 196 */       this.ioStarted = false;
/*     */       
/* 198 */       synchronized (this.projects) {
/* 199 */         for (FModProject project : this.projects.values()) {
/* 200 */           project.stopIo();
/*     */         }
/*     */       } 
/*     */       
/* 204 */       for (SoundReference soundReference : this.rawSounds.values()) {
/* 205 */         soundReference.release();
/*     */       }
/* 207 */       this.rawSounds.clear();
/*     */       
/* 209 */       LOGGER.log((Priority)Level.INFO, "Stopped Io");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Project loadSoundProject(String path) {
/* 215 */     FModProject ret = this.projects.get(path);
/*     */     
/* 217 */     if (ret == null) {
/* 218 */       ret = new FModProject(this, path);
/*     */       
/* 220 */       if (ret.isValid()) {
/* 221 */         this.projects.put(path, ret);
/*     */       } else {
/* 223 */         ret = null;
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseInactiveData() {
/* 232 */     cleanupProjects();
/*     */     
/* 234 */     cleanupSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReverbPreset(ReverbPreset preset) {
/* 239 */     FMOD_REVERB_PROPERTIES props = FModReverbPreset.get(preset);
/* 240 */     this.eventSystem.setReverbProperties(props);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReverb(String reverbPath, boolean turnOffIfMissing) {
/* 245 */     FMOD_REVERB_PROPERTIES props = null;
/*     */     try {
/* 247 */       int index = Math.max(reverbPath.indexOf('/'), 0);
/* 248 */       if (index >= 0) {
/* 249 */         String projectName = reverbPath.substring(0, index);
/* 250 */         String reverbName = reverbPath.substring(index + 1);
/* 251 */         Project project = loadSoundProject(projectName + ".fev");
/*     */         
/* 253 */         if (project != null) {
/* 254 */           props = FMOD_REVERB_PROPERTIES.create();
/* 255 */           FMOD_RESULT result = this.eventSystem.getReverbPreset(reverbName, props, null);
/* 256 */           if (!isOk(result)) {
/* 257 */             LOGGER.fatal("Missing Reverb: " + reverbPath);
/* 258 */             if (turnOffIfMissing) {
/* 259 */               setReverbPreset(ReverbPreset.OFF);
/*     */             }
/*     */           } else {
/* 262 */             this.eventSystem.setReverbProperties(props);
/*     */           } 
/*     */         } else {
/* 265 */           LOGGER.fatal("Reverb Path Error, Cannot Load Project: " + reverbPath);
/*     */         } 
/*     */       } else {
/* 268 */         LOGGER.fatal("Reverb Path Error, Missing Project Name: " + reverbPath);
/*     */       } 
/*     */     } finally {
/* 271 */       if (props != null) {
/* 272 */         props.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMute() {
/* 279 */     return this.wantMute;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMute(boolean mute) {
/* 284 */     this.wantMute = mute;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMusicMute() {
/* 289 */     return this.wantMusicMute;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMusicMute(boolean mute) {
/* 294 */     this.wantMusicMute = mute;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSfxMute() {
/* 299 */     return this.wantSfxMute;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSfxMute(boolean mute) {
/* 304 */     this.wantSfxMute = mute;
/*     */   }
/*     */   
/*     */   private void cleanupSounds() {
/* 308 */     Iterator<Map.Entry<String, SoundReference>> soundsIterator = this.rawSounds.entrySet().iterator();
/* 309 */     while (soundsIterator.hasNext()) {
/* 310 */       Map.Entry<String, SoundReference> soundEntry = soundsIterator.next();
/* 311 */       SoundReference soundRef = soundEntry.getValue();
/* 312 */       if (!soundRef.isReferenced()) {
/* 313 */         soundRef.release();
/* 314 */         soundsIterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanupProjects() {
/* 320 */     Set<Map.Entry<String, FModProject>> projectEntries = this.projects.entrySet();
/* 321 */     synchronized (this.projects) {
/* 322 */       Iterator<Map.Entry<String, FModProject>> projectIterator = projectEntries.iterator();
/* 323 */       while (projectIterator.hasNext()) {
/* 324 */         Map.Entry<String, FModProject> projectEntry = projectIterator.next();
/* 325 */         FModProject project = projectEntry.getValue();
/* 326 */         project.releaseUnusedData();
/*     */         
/* 328 */         if (project.isEmpty()) {
/* 329 */           project.release();
/* 330 */           projectIterator.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 341 */     if (this.profileEnabled != this.profileStarted) {
/* 342 */       stopIo();
/* 343 */       shutdown();
/*     */       try {
/* 345 */         initialize();
/* 346 */       } catch (SoundSystemException e) {
/* 347 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 350 */     if (this.ioEnabled) {
/* 351 */       if (!this.ioStarted) {
/* 352 */         startIo();
/*     */       }
/*     */       
/* 355 */       if (this.wantMute && !this.muted) {
/* 356 */         this.masterCategory.setVolume(0.0F);
/* 357 */         this.muted = true;
/* 358 */       } else if (!this.wantMute && this.muted) {
/* 359 */         this.masterCategory.setVolume(1.0F);
/* 360 */         this.muted = false;
/*     */       } 
/*     */       
/* 363 */       if (this.wantMusicMute && !this.musicMuted) {
/* 364 */         int volume = 0;
/* 365 */         if (!this.musicMuted)
/* 366 */           this.musicMuted = setCategoryVolume("music", volume); 
/* 367 */       } else if (!this.wantMusicMute && this.musicMuted) {
/* 368 */         int volume = 1;
/* 369 */         if (this.musicMuted) {
/* 370 */           this.musicMuted = !setCategoryVolume("music", volume);
/*     */         }
/*     */       } 
/* 373 */       if (this.wantSfxMute && (!this.sfxMuted || !this.guiMuted || !this.ambientMuted)) {
/* 374 */         int volume = 0;
/* 375 */         if (!this.sfxMuted) {
/* 376 */           this.sfxMuted = setCategoryVolume("sfx", volume);
/*     */         }
/* 378 */         if (!this.ambientMuted) {
/* 379 */           this.ambientMuted = setCategoryVolume("ambient", volume);
/*     */         }
/* 381 */         if (!this.guiMuted) {
/* 382 */           this.guiMuted = setCategoryVolume("gui", volume);
/*     */         }
/* 384 */       } else if (!this.wantSfxMute && (this.sfxMuted || this.ambientMuted || this.guiMuted)) {
/* 385 */         int volume = 1;
/* 386 */         if (this.sfxMuted) {
/* 387 */           this.sfxMuted = !setCategoryVolume("sfx", volume);
/*     */         }
/* 389 */         if (this.ambientMuted) {
/* 390 */           this.ambientMuted = !setCategoryVolume("ambient", volume);
/*     */         }
/* 392 */         if (this.guiMuted) {
/* 393 */           this.guiMuted = !setCategoryVolume("gui", volume);
/*     */         }
/*     */       } 
/* 396 */       synchronized (this.projects) {
/* 397 */         for (FModProject project : this.projects.values()) {
/* 398 */           project.update();
/*     */         }
/*     */       } 
/*     */       
/* 402 */       this.ear.update();
/*     */     }
/* 404 */     else if (this.ioStarted) {
/* 405 */       stopIo();
/*     */     } 
/*     */ 
/*     */     
/* 409 */     FMOD_RESULT result = this.eventSystem.update();
/* 410 */     isOk(result);
/*     */ 
/*     */     
/* 413 */     if (this.auditionEnabled) {
/* 414 */       if (!this.auditionStarted) {
/* 415 */         startDebug();
/*     */       }
/*     */       
/* 418 */       result = FmodDesigner.NetEventSystem_Update();
/* 419 */       isOk(result);
/*     */     }
/* 421 */     else if (this.auditionStarted) {
/* 422 */       stopDebug();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setCategoryVolume(String categoryName, int volume) {
/* 428 */     EventCategory musicCategory = new EventCategory();
/* 429 */     FMOD_RESULT result = this.eventSystem.getCategory(categoryName, musicCategory);
/* 430 */     if (result == FMOD_RESULT.FMOD_OK) {
/* 431 */       musicCategory.setVolume(volume);
/* 432 */       return true;
/*     */     } 
/* 434 */     return false;
/*     */   }
/*     */   
/*     */   private static void loadLibs() throws SoundSystemException {
/* 438 */     if (!loadedLibs) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 443 */         Init.loadLibraries(1);
/* 444 */         InitFmodDesigner.loadLibraries(2);
/*     */       }
/* 446 */       catch (InitException e) {
/* 447 */         throw new SoundSystemException(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 453 */       if (VERSIONS.NATIVEFMODEX_LIBRARY_VERSION != VERSIONS.NATIVEFMODEX_JAR_VERSION) {
/* 454 */         throw new SoundSystemException("Error!  NativeFmodEx library version (" + VERSIONS.NATIVEFMODEX_LIBRARY_VERSION + ")" + " is different to jar version (" + VERSIONS.NATIVEFMODEX_JAR_VERSION + ")");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 461 */       if (VERSIONS.NATIVEFMODDESIGNER_LIBRARY_VERSION != VERSIONS.NATIVEFMODDESIGNER_JAR_VERSION) {
/* 462 */         throw new SoundSystemException("Error!  NativeFmodDesigner library version (" + VERSIONS.NATIVEFMODDESIGNER_LIBRARY_VERSION + ")" + " is different to jar version (" + VERSIONS.NATIVEFMODDESIGNER_JAR_VERSION + ")");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 467 */       loadedLibs = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() throws SoundSystemException {
/* 473 */     this.eventSystem = new EventSystem();
/* 474 */     FMOD_RESULT result = FmodDesigner.EventSystem_Create(this.eventSystem);
/* 475 */     throwOnError(result);
/*     */ 
/*     */ 
/*     */     
/* 479 */     ByteBuffer buffer = BufferUtils.newByteBuffer(4);
/*     */     
/* 481 */     result = this.eventSystem.getVersion(buffer.asIntBuffer());
/* 482 */     int version = buffer.getInt(0);
/* 483 */     throwOnError(result);
/* 484 */     if (version < VERSIONS.FMOD_EVENT_VERSION) {
/* 485 */       throw new RuntimeException("Error!  You are using an old version of FMOD EVENT " + version + "." + "  This program requires " + VERSIONS.FMOD_EVENT_VERSION);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     int flags = 0;
/* 492 */     if (this.profileEnabled) {
/* 493 */       flags |= 0x20;
/*     */     }
/* 495 */     result = this.eventSystem.init(64, flags, null, 1);
/*     */ 
/*     */     
/* 498 */     throwOnError(result);
/*     */ 
/*     */     
/* 501 */     this.system = new System();
/* 502 */     this.eventSystem.getSystemObject(this.system);
/* 503 */     if (this.dataLoader == null) {
/* 504 */       throw new NullPointerException("data loader not assigned");
/*     */     }
/* 506 */     MemoryFileSystem fileSystem = new MemoryFileSystem(this.dataLoader);
/* 507 */     result = this.system.setFileSystem(fileSystem.jarOpen, fileSystem.jarClose, fileSystem.jarRead, fileSystem.jarSeek, -1);
/*     */     
/* 509 */     throwOnError(result);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     this.masterCategory = new EventCategory();
/* 516 */     result = this.eventSystem.getCategory("master", this.masterCategory);
/* 517 */     throwOnError(result);
/*     */     
/* 519 */     this.profileStarted = this.profileEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   private void startDebug() {
/* 524 */     FMOD_RESULT result = FmodDesigner.NetEventSystem_Init(this.eventSystem);
/* 525 */     isOk(result);
/*     */     
/* 527 */     this.auditionStarted = true;
/*     */     
/* 529 */     LOGGER.log((Priority)Level.INFO, "Started Debug");
/*     */   }
/*     */ 
/*     */   
/*     */   private void stopDebug() {
/* 534 */     FMOD_RESULT result = FmodDesigner.NetEventSystem_Shutdown();
/* 535 */     isOk(result);
/*     */     
/* 537 */     this.auditionStarted = false;
/*     */     
/* 539 */     LOGGER.log((Priority)Level.INFO, "Stopped Debug");
/*     */   }
/* 541 */   public static String currentPath = "";
/*     */   
/*     */   static boolean isOk(FMOD_RESULT result) {
/* 544 */     if (result == FMOD_RESULT.FMOD_OK) {
/* 545 */       return true;
/*     */     }
/* 547 */     if (result != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 566 */       (new Exception("FMOD error! (" + result.asInt() + ") " + FmodEx.FMOD_ErrorString(result))).printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 586 */       (new Exception("FMOD error! Result is null")).printStackTrace();
/*     */     } 
/* 588 */     return false;
/*     */   }
/*     */   
/*     */   static void throwOnError(FMOD_RESULT result) throws SoundSystemException {
/* 592 */     if (result != FMOD_RESULT.FMOD_OK) {
/* 593 */       throw new SoundSystemException("FMOD error! (" + result.asInt() + ") " + FmodEx.FMOD_ErrorString(result));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isIoStarted() {
/* 598 */     return this.ioStarted;
/*     */   }
/*     */   
/*     */   public int getIoGeneration() {
/* 602 */     return this.ioGeneration;
/*     */   }
/*     */   
/*     */   public SoundReference fetchRawSound(FModSound owner, String waveformName) {
/* 606 */     SoundReference soundReference = this.rawSounds.get(waveformName);
/*     */     
/* 608 */     if (soundReference == null) {
/*     */       
/* 610 */       Sound sound = new Sound();
/* 611 */       String oggName = waveformName;
/* 612 */       int dotIndex = waveformName.lastIndexOf('.');
/* 613 */       if (dotIndex >= 0) {
/* 614 */         oggName = waveformName.substring(0, dotIndex) + ".ogg";
/*     */       }
/* 616 */       ByteBuffer soundBuffer = this.dataLoader.load(oggName);
/* 617 */       if (soundBuffer == null) {
/* 618 */         soundBuffer = this.dataLoader.load(waveformName);
/*     */       }
/*     */       
/* 621 */       if (soundBuffer != null) {
/* 622 */         FMOD_CREATESOUNDEXINFO exinfo = FMOD_CREATESOUNDEXINFO.create();
/* 623 */         exinfo.setLength(soundBuffer.capacity());
/* 624 */         FMOD_RESULT result = this.system.createSound(soundBuffer, 2112, exinfo, sound);
/* 625 */         exinfo.release();
/* 626 */         if (!sound.isNull()) {
/* 627 */           if (isOk(result)) {
/* 628 */             soundReference = new SoundReference(waveformName, sound);
/* 629 */             this.rawSounds.put(waveformName, soundReference);
/*     */           } else {
/* 631 */             sound.release();
/*     */           } 
/*     */         }
/*     */       } else {
/* 635 */         System.err.println("Cannot find sound file: " + waveformName);
/* 636 */         LOGGER.log((Priority)Level.INFO, "Cannot find sound file: " + waveformName);
/*     */       } 
/*     */     } else {
/* 639 */       LOGGER.log((Priority)Level.INFO, "Loaded from cache: " + waveformName);
/*     */     } 
/*     */     
/* 642 */     if (soundReference != null) {
/* 643 */       soundReference.addOwner(owner);
/* 644 */       return soundReference;
/*     */     } 
/*     */     
/* 647 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\FModSoundSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */