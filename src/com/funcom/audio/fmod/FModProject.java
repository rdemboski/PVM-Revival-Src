/*     */ package com.funcom.audio.fmod;
/*     */ 
/*     */ import com.funcom.audio.Project;
/*     */ import com.funcom.audio.Sound;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jouvieje.FmodDesigner.EventGroup;
/*     */ import org.jouvieje.FmodDesigner.EventProject;
/*     */ import org.jouvieje.FmodDesigner.Structures.FMOD_EVENT_LOADINFO;
/*     */ import org.jouvieje.FmodEx.Enumerations.FMOD_RESULT;
/*     */ import org.jouvieje.FmodEx.Misc.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FModProject
/*     */   implements Project
/*     */ {
/*     */   private static final int MEM_LEAK_WARNING_LIMIT = 256;
/*  29 */   private static final Logger LOGGER = Logger.getLogger(FModProject.class.getName());
/*     */   
/*     */   private final FModSoundSystem system;
/*     */   private final String path;
/*     */   private EventProject rawProject;
/*     */   private List<FModSound> soundRefs;
/*     */   private Map<String, WeakReference<EventGroup>> groups;
/*     */   private long lastMemLeakCheckAt;
/*  37 */   private IntBuffer tmpBuf = BufferUtils.newIntBuffer(1);
/*     */   
/*     */   public FModProject(FModSoundSystem system, String path) {
/*  40 */     this.system = system;
/*  41 */     this.path = path;
/*     */     
/*  43 */     this.soundRefs = new ArrayList<FModSound>();
/*  44 */     this.groups = new HashMap<String, WeakReference<EventGroup>>();
/*     */     
/*  46 */     if (system.isIoStarted()) {
/*  47 */       startIo();
/*     */     }
/*     */   }
/*     */   
/*     */   String getPath() {
/*  52 */     return this.path;
/*     */   }
/*     */   
/*     */   EventProject getRawProject() {
/*  56 */     return this.rawProject;
/*     */   }
/*     */ 
/*     */   
/*     */   void startIo() {
/*  61 */     ByteBuffer fevBuffer = this.system.getDataLoader().load(this.path);
/*     */     
/*  63 */     if (fevBuffer != null) {
/*  64 */       FMOD_EVENT_LOADINFO info = FMOD_EVENT_LOADINFO.create();
/*     */       try {
/*  66 */         info.setLoadFromMemoryLength(fevBuffer.capacity());
/*  67 */         EventProject project = new EventProject();
/*  68 */         FMOD_RESULT result = this.system.getEventSystem().load(fevBuffer, info, project);
/*  69 */         if (FModSoundSystem.isOk(result)) {
/*  70 */           this.rawProject = project;
/*     */         } else {
/*  72 */           LOGGER.fatal("cannot load fmod project, is data built by a too old/new fmod designer?: path=" + this.path);
/*     */         } 
/*     */       } finally {
/*  75 */         info.release();
/*     */       } 
/*     */     } else {
/*     */       
/*  79 */       LOGGER.log((Priority)Level.WARN, "Sound System cannot find project: projectpath=" + this.path);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sound getSound(String path) {
/*  84 */     FModSound sound = new FModSound(this.system, this, path);
/*     */     
/*  86 */     this.soundRefs.add(sound);
/*     */     
/*  88 */     return sound;
/*     */   }
/*     */   
/*     */   void stopIo() {
/*  92 */     for (FModSound sound : this.soundRefs) {
/*  93 */       sound.dispose();
/*     */     }
/*  95 */     this.soundRefs.clear();
/*     */     
/*  97 */     this.groups.clear();
/*     */     
/*  99 */     FMOD_RESULT result = this.rawProject.release();
/* 100 */     FModSoundSystem.isOk(result);
/*     */   }
/*     */   
/*     */   public void update() {
/* 104 */     for (Iterator<FModSound> iterator = this.soundRefs.iterator(); iterator.hasNext(); ) {
/* 105 */       FModSound modSound = iterator.next();
/* 106 */       modSound.update();
/* 107 */       if (modSound.isDisposed()) {
/* 108 */         iterator.remove();
/*     */       }
/*     */     } 
/* 111 */     releaseGCedGroups();
/*     */     
/* 113 */     checkMemoryLeak();
/*     */   }
/*     */   
/*     */   private void checkMemoryLeak() {
/* 117 */     if (System.currentTimeMillis() - this.lastMemLeakCheckAt > 5000L) {
/* 118 */       if (this.soundRefs.size() > 256) {
/* 119 */         String errInfo = "Possible memory leak.\nProject=" + this.path + "soundList:";
/* 120 */         for (FModSound fModSound : this.soundRefs) {
/* 121 */           if (fModSound != null) {
/* 122 */             errInfo = errInfo + "\n" + fModSound.getPath();
/*     */           }
/*     */         } 
/* 125 */         (new RuntimeException(errInfo)).printStackTrace();
/*     */       } 
/* 127 */       this.lastMemLeakCheckAt = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseUnusedData() {
/* 132 */     for (Iterator<FModSound> it = this.soundRefs.iterator(); it.hasNext(); ) {
/* 133 */       FModSound modSound = it.next();
/* 134 */       if (modSound.isDisposeWhenFinished() && !modSound.isPlaying(this.tmpBuf)) {
/*     */         
/* 136 */         modSound.dispose();
/* 137 */         it.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     releaseGCedGroups();
/*     */   }
/*     */   
/*     */   private void releaseGCedGroups() {
/* 145 */     for (Iterator<Map.Entry<String, WeakReference<EventGroup>>> it = this.groups.entrySet().iterator(); it.hasNext(); ) {
/* 146 */       Map.Entry<String, WeakReference<EventGroup>> groupEntry = it.next();
/* 147 */       if (((WeakReference)groupEntry.getValue()).get() == null) {
/* 148 */         String groupPath = groupEntry.getKey();
/*     */         
/* 150 */         EventGroup groupToRelease = new EventGroup();
/* 151 */         FMOD_RESULT result = this.rawProject.getGroup(groupPath, false, groupToRelease);
/* 152 */         if (!FModSoundSystem.isOk(result)) {
/* 153 */           LOGGER.fatal("cannot get group for release: groupPath=" + groupPath);
/*     */         } else {
/* 155 */           result = groupToRelease.freeEventData(null, true);
/* 156 */           if (!FModSoundSystem.isOk(result)) {
/* 157 */             LOGGER.fatal("cannot release group: groupPath=" + groupPath);
/*     */           }
/*     */         } 
/* 160 */         it.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 166 */     return (this.soundRefs.isEmpty() && this.groups.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 171 */     return "FModProject{system=" + this.system.getEventSystem() + "system.isNull=" + this.system.getEventSystem().isNull() + ", path='" + this.path + ", rawProject=" + this.rawProject + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 180 */     return (this.rawProject != null && !this.rawProject.isNull());
/*     */   }
/*     */   
/*     */   public EventGroup getParentGroup(String eventPath) {
/* 184 */     EventGroup ret = null;
/* 185 */     int lastSlash = eventPath.lastIndexOf('/');
/*     */     
/* 187 */     if (lastSlash >= 0) {
/* 188 */       String groupPath = eventPath.substring(0, lastSlash);
/* 189 */       WeakReference<EventGroup> groupRef = this.groups.get(groupPath);
/*     */       
/* 191 */       if (groupRef != null) {
/* 192 */         ret = groupRef.get();
/*     */       }
/*     */       
/* 195 */       if (ret == null) {
/* 196 */         ret = new EventGroup();
/* 197 */         FMOD_RESULT result = this.rawProject.getGroup(groupPath, false, ret);
/* 198 */         if (!FModSoundSystem.isOk(result)) {
/* 199 */           return null;
/*     */         }
/* 201 */         this.groups.put(groupPath, new WeakReference<EventGroup>(ret));
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return ret;
/*     */   }
/*     */   
/*     */   public void release() {
/* 209 */     if (this.rawProject != null && !this.rawProject.isNull()) {
/* 210 */       FMOD_RESULT result = this.rawProject.release();
/* 211 */       if (!FModSoundSystem.isOk(result))
/* 212 */         LOGGER.fatal("error while releasing project: path=" + this.path); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\FModProject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */