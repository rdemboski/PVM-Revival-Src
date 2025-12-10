/*     */ package com.funcom.audio.fmod;
/*     */ 
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.audio.Vector3f;
/*     */ import com.funcom.util.DebugManager;
/*     */ import java.nio.IntBuffer;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jouvieje.FmodDesigner.Callbacks.FMOD_EVENT_CALLBACK;
/*     */ import org.jouvieje.FmodDesigner.Enumerations.FMOD_EVENT_CALLBACKTYPE;
/*     */ import org.jouvieje.FmodDesigner.Enumerations.FMOD_EVENT_PROPERTY;
/*     */ import org.jouvieje.FmodDesigner.Event;
/*     */ import org.jouvieje.FmodDesigner.EventGroup;
/*     */ import org.jouvieje.FmodDesigner.EventProject;
/*     */ import org.jouvieje.FmodEx.Enumerations.FMOD_RESULT;
/*     */ import org.jouvieje.FmodEx.Misc.BufferUtils;
/*     */ import org.jouvieje.FmodEx.Misc.Pointer;
/*     */ import org.jouvieje.FmodEx.Structures.FMOD_VECTOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FModSound
/*     */   implements Sound
/*     */ {
/*  27 */   private static final Logger LOGGER = Logger.getLogger(FModSound.class.getName());
/*     */   
/*     */   private static long lastLoadWarningAt;
/*     */   
/*     */   private final FModSoundSystem system;
/*     */   
/*     */   private EventGroup eventGroup;
/*     */   private String path;
/*     */   private final FModProject project;
/*     */   private Event event;
/*     */   private int ioGeneration;
/*     */   private FMOD_EVENT_CALLBACK eventCallback;
/*     */   private FMOD_VECTOR fmodPos;
/*     */   private Vector3f pos;
/*     */   private State state;
/*     */   private boolean disposeWhenFinished;
/*     */   private boolean sound3d;
/*     */   private boolean worldRelative;
/*  45 */   private IntBuffer tempBuffer = BufferUtils.newIntBuffer(1);
/*     */   
/*     */   private static final int LOAD_WARNING_SILENT_MILLIS = 10000;
/*     */   
/*     */   public FModSound(FModSoundSystem system, FModProject project, String path) {
/*  50 */     this.system = system;
/*  51 */     this.project = project;
/*  52 */     this.path = path;
/*     */     
/*  54 */     this.fmodPos = FMOD_VECTOR.create();
/*     */     
/*  56 */     this.eventCallback = new MyFMOD_EVENT_CALLBACK();
/*  57 */     this.pos = new Vector3f();
/*  58 */     this.state = State.READY;
/*     */   }
/*     */   
/*     */   private void init() {
/*  62 */     EventProject rawProject = this.project.getRawProject();
/*     */     
/*  64 */     this.state = State.STOPPED;
/*     */     
/*  66 */     if (rawProject.isNull()) {
/*  67 */       LOGGER.log((Priority)Level.INFO, "NULL FMOD DESIGNER PROJECT: " + this.project.getRawProject());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  72 */     Event newEvent = new Event();
/*     */ 
/*     */     
/*  75 */     FMOD_RESULT result = rawProject.getEvent(this.path, 4, newEvent);
/*  76 */     if (!FModSoundSystem.isOk(result) || newEvent.isNull()) {
/*  77 */       if (result == null && System.currentTimeMillis() - lastLoadWarningAt > 10000L) {
/*     */         
/*  79 */         DebugManager.getInstance().warn("Cannot load sound, please check for missing sound: " + this.project.getPath() + "/" + this.path);
/*  80 */         lastLoadWarningAt = System.currentTimeMillis();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     Integer prop3dPosition = getIntegerProperty(newEvent, FMOD_EVENT_PROPERTY.FMOD_EVENTPROPERTY_3D_POSITION);
/*  86 */     if (prop3dPosition == null) {
/*     */       return;
/*     */     }
/*  89 */     if (prop3dPosition.intValue() == 524288) {
/*  90 */       this.worldRelative = true;
/*     */     }
/*  92 */     Integer propMode = getIntegerProperty(newEvent, FMOD_EVENT_PROPERTY.FMOD_EVENTPROPERTY_MODE);
/*  93 */     if (propMode == null) {
/*     */       return;
/*     */     }
/*  96 */     if (propMode.intValue() == 16) {
/*  97 */       this.sound3d = true;
/*     */     }
/*     */     
/* 100 */     result = updatePos(newEvent);
/* 101 */     if (!FModSoundSystem.isOk(result) || newEvent.isNull()) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     result = rawProject.getEvent(this.path, 0, newEvent);
/* 106 */     if (result == FMOD_RESULT.FMOD_ERR_EVENT_FAILED || result == FMOD_RESULT.FMOD_ERR_EVENT_INFOONLY || !FModSoundSystem.isOk(result) || newEvent.isNull()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 112 */     result = newEvent.setCallback(this.eventCallback, null);
/* 113 */     if (!FModSoundSystem.isOk(result) || newEvent.isNull()) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     this.eventGroup = this.project.getParentGroup(this.path);
/*     */     
/* 119 */     this.state = State.READY;
/* 120 */     this.event = newEvent;
/* 121 */     this.ioGeneration = this.system.getIoGeneration();
/*     */   }
/*     */   
/*     */   private Integer getIntegerProperty(Event event, FMOD_EVENT_PROPERTY eventProperty) {
/* 125 */     int index = eventProperty.asInt();
/*     */     
/* 127 */     this.tempBuffer.clear();
/* 128 */     Pointer value = BufferUtils.createView(this.tempBuffer);
/* 129 */     FMOD_RESULT result = event.getPropertyByIndex(index, value, false);
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (!FModSoundSystem.isOk(result) || event.isNull()) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     return Integer.valueOf(this.tempBuffer.get());
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 141 */     return this.path;
/*     */   }
/*     */   
/*     */   public void preInit() {
/* 145 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     if (this.system.isIoStarted() && (
/* 150 */       this.event == null || this.ioGeneration != this.system.getIoGeneration())) {
/* 151 */       freeEvent();
/* 152 */       init();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/* 159 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 164 */     if (this.system.isIoStarted()) {
/* 165 */       if (this.event == null || this.ioGeneration != this.system.getIoGeneration()) {
/* 166 */         freeEvent();
/* 167 */         init();
/*     */       } 
/*     */       
/* 170 */       if (this.event != null && !this.event.isNull()) {
/* 171 */         FMOD_RESULT result = this.event.start();
/* 172 */         if (!FModSoundSystem.isOk(result)) {
/* 173 */           System.err.println("ERROR STARTING: soundPath=" + this.path);
/* 174 */           freeEvent();
/*     */         } else {
/* 176 */           this.state = State.PLAYING;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector3f getPos() {
/* 183 */     return this.pos;
/*     */   }
/*     */   
/*     */   boolean isDisposed() {
/* 187 */     return (this.state == State.DISPOSED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 192 */     return (!this.system.isIoStarted() || isDisposed() || this.state == State.STOPPED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 197 */     return (!this.system.isIoStarted() || isDisposed() || isStopped());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeWhenFinished() {
/* 203 */     this.disposeWhenFinished = true;
/*     */   }
/*     */   
/*     */   boolean isDisposeWhenFinished() {
/* 207 */     return this.disposeWhenFinished;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 213 */     if (isDone()) {
/* 214 */       if (this.disposeWhenFinished && !isDisposed()) {
/* 215 */         dispose();
/*     */       }
/* 217 */     } else if (this.event != null && !this.event.isNull()) {
/* 218 */       FMOD_RESULT result = updatePos(this.event);
/* 219 */       if (result == FMOD_RESULT.FMOD_ERR_INVALID_HANDLE || result == FMOD_RESULT.FMOD_ERR_CHANNEL_STOLEN) {
/*     */ 
/*     */         
/* 222 */         stop();
/* 223 */       } else if (!FModSoundSystem.isOk(result)) {
/* 224 */         freeEvent();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void dispose() {
/* 230 */     this.state = State.DISPOSED;
/* 231 */     freeEvent();
/* 232 */     freePos();
/*     */   }
/*     */   
/*     */   private FMOD_RESULT updatePos(Event event) {
/* 236 */     if (this.worldRelative && this.sound3d) {
/* 237 */       this.fmodPos.setXYZ(this.pos.getX(), this.pos.getY(), this.pos.getZ());
/* 238 */       return event.set3DAttributes(this.fmodPos, null, null);
/*     */     } 
/* 240 */     return FMOD_RESULT.FMOD_OK;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 244 */     if (!isPlaying(this.tempBuffer)) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     if (this.event != null) {
/* 249 */       FMOD_RESULT result = this.event.stop(false);
/* 250 */       if (result == FMOD_RESULT.FMOD_ERR_INVALID_HANDLE) {
/*     */         
/* 252 */         freeEvent();
/* 253 */       } else if (!FModSoundSystem.isOk(result)) {
/* 254 */         freeEvent();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isPlaying(IntBuffer tmpBuf) {
/* 260 */     if (this.state == State.PLAYING)
/* 261 */       return true; 
/* 262 */     if (this.event != null) {
/* 263 */       tmpBuf.clear();
/* 264 */       FMOD_RESULT result = this.event.getState(tmpBuf);
/* 265 */       if (result == FMOD_RESULT.FMOD_ERR_INVALID_HANDLE || !FModSoundSystem.isOk(result)) {
/*     */         
/* 267 */         dispose();
/* 268 */         return false;
/*     */       } 
/* 270 */       return ((tmpBuf.get(0) & 0x8) != 0);
/*     */     } 
/* 272 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isStopped() {
/* 276 */     return (this.state == State.STOPPED && !isPlaying(this.tempBuffer));
/*     */   }
/*     */ 
/*     */   
/*     */   private void freeEvent() {
/* 281 */     if (this.event != null && !this.event.isNull()) {
/* 282 */       this.event.stop(true);
/* 283 */       this.event.setCallback(null, null);
/*     */     } 
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
/* 297 */     this.eventGroup = null;
/* 298 */     this.event = null;
/*     */   }
/*     */   
/*     */   private void freePos() {
/* 302 */     if (this.fmodPos != null && !this.fmodPos.isNull()) {
/* 303 */       this.fmodPos.release();
/*     */     }
/* 305 */     this.fmodPos = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 310 */     return "FModSound{path='" + this.path + '}';
/*     */   }
/*     */   
/*     */   private class MyFMOD_EVENT_CALLBACK implements FMOD_EVENT_CALLBACK {
/*     */     private MyFMOD_EVENT_CALLBACK() {}
/*     */     
/*     */     public FMOD_RESULT FMOD_EVENT_CALLBACK(Event event, FMOD_EVENT_CALLBACKTYPE type, Pointer param1, Pointer param2, Pointer userdata) {
/* 317 */       if (type == FMOD_EVENT_CALLBACKTYPE.FMOD_EVENT_CALLBACKTYPE_EVENTFINISHED) {
/* 318 */         FModSound.this.state = FModSound.State.STOPPED;
/* 319 */       } else if (type != FMOD_EVENT_CALLBACKTYPE.FMOD_EVENT_CALLBACKTYPE_SOUNDDEF_RELEASE) {
/*     */         
/* 321 */         if (type == FMOD_EVENT_CALLBACKTYPE.FMOD_EVENT_CALLBACKTYPE_SOUNDDEF_CREATE) {
/* 322 */           String waveformName = param1.asString();
/* 323 */           SoundReference soundReference = FModSound.this.system.fetchRawSound(FModSound.this, waveformName);
/*     */           
/* 325 */           if (soundReference != null && !soundReference.getSound().isNull()) {
/* 326 */             param2.shareMemory((Pointer)soundReference.getSound());
/*     */           } else {
/* 328 */             System.err.println("cannot load waveform: " + waveformName);
/* 329 */             event.stop(true);
/* 330 */             return FMOD_RESULT.FMOD_ERR_FILE_NOTFOUND;
/*     */           } 
/*     */         } 
/* 333 */       }  return FMOD_RESULT.FMOD_OK;
/*     */     }
/*     */   }
/*     */   
/*     */   private enum State {
/* 338 */     READY, PLAYING, STOPPED, DISPOSED;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\FModSound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */