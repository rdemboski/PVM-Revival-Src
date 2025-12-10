/*     */ package com.funcom.audio.fmod;
/*     */ 
/*     */ import com.funcom.audio.DataLoader;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jouvieje.FmodEx.Callbacks.FMOD_FILE_CLOSECALLBACK;
/*     */ import org.jouvieje.FmodEx.Callbacks.FMOD_FILE_OPENCALLBACK;
/*     */ import org.jouvieje.FmodEx.Callbacks.FMOD_FILE_READCALLBACK;
/*     */ import org.jouvieje.FmodEx.Callbacks.FMOD_FILE_SEEKCALLBACK;
/*     */ import org.jouvieje.FmodEx.Enumerations.FMOD_RESULT;
/*     */ import org.jouvieje.FmodEx.Misc.ObjectPointer;
/*     */ import org.jouvieje.FmodEx.Misc.Pointer;
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
/*     */ public class MemoryFileSystem
/*     */ {
/*  29 */   private static final Logger LOGGER = Logger.getLogger(MemoryFileSystem.class.getName());
/*     */   private final DataLoader dataLoader;
/*     */   public final FMOD_FILE_OPENCALLBACK jarOpen;
/*     */   public FMOD_FILE_CLOSECALLBACK jarClose;
/*     */   public FMOD_FILE_READCALLBACK jarRead;
/*     */   public FMOD_FILE_SEEKCALLBACK jarSeek;
/*     */   
/*     */   public MemoryFileSystem(DataLoader dataLoader) {
/*  37 */     this.jarOpen = new FMOD_FILE_OPENCALLBACK() {
/*     */         public FMOD_RESULT FMOD_FILE_OPENCALLBACK(String name, int unicode, IntBuffer filesize, Pointer handle, Pointer userdata) {
/*  39 */           if (name == null) {
/*  40 */             return FMOD_RESULT.FMOD_ERR_FILE_NOTFOUND;
/*     */           }
/*     */           
/*  43 */           ByteBuffer filBuffer = MemoryFileSystem.this.dataLoader.load(name);
/*  44 */           if (filBuffer == null) {
/*  45 */             return FMOD_RESULT.FMOD_ERR_FILE_NOTFOUND;
/*     */           }
/*     */           
/*  48 */           filesize.put(0, filBuffer.capacity());
/*  49 */           handle.shareMemory((Pointer)ObjectPointer.create(filBuffer));
/*     */           
/*  51 */           return FMOD_RESULT.FMOD_OK;
/*     */         }
/*     */       };
/*     */     
/*  55 */     this.jarClose = new FMOD_FILE_CLOSECALLBACK() {
/*     */         public FMOD_RESULT FMOD_FILE_CLOSECALLBACK(Pointer handle, Pointer userdata) {
/*  57 */           if (handle == null || handle.isNull()) {
/*  58 */             return FMOD_RESULT.FMOD_ERR_INVALID_PARAM;
/*     */           }
/*     */           
/*  61 */           ObjectPointer objectPointer = ObjectPointer.createView(handle);
/*     */           
/*  63 */           objectPointer.release();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  68 */           return FMOD_RESULT.FMOD_OK;
/*     */         }
/*     */       };
/*     */     
/*  72 */     this.jarRead = new FMOD_FILE_READCALLBACK() {
/*     */         public FMOD_RESULT FMOD_FILE_READCALLBACK(Pointer handle, ByteBuffer buffer, int sizebytes, IntBuffer bytesread, Pointer userdata) {
/*  74 */           if (handle == null || handle.isNull()) {
/*  75 */             return FMOD_RESULT.FMOD_ERR_INVALID_PARAM;
/*     */           }
/*     */           
/*  78 */           ByteBuffer file = (ByteBuffer)ObjectPointer.createView(handle).getObject();
/*  79 */           ByteBuffer fileChunk = file.duplicate();
/*     */ 
/*     */           
/*  82 */           int maxBytes = file.capacity() - fileChunk.position();
/*  83 */           int bytesToRead = (sizebytes > maxBytes) ? maxBytes : sizebytes;
/*     */ 
/*     */           
/*  86 */           fileChunk.limit(fileChunk.position() + bytesToRead);
/*  87 */           if (fileChunk.remaining() != sizebytes) {
/*  88 */             return FMOD_RESULT.FMOD_ERR_FILE_EOF;
/*     */           }
/*  90 */           buffer.put(fileChunk);
/*     */ 
/*     */           
/*  93 */           file.position(file.position() + bytesToRead);
/*  94 */           bytesread.put(0, bytesToRead);
/*     */           
/*  96 */           return FMOD_RESULT.FMOD_OK;
/*     */         }
/*     */       };
/*     */     
/* 100 */     this.jarSeek = new FMOD_FILE_SEEKCALLBACK() {
/*     */         public FMOD_RESULT FMOD_FILE_SEEKCALLBACK(Pointer handle, int pos, Pointer userdata) {
/* 102 */           if (handle == null || handle.isNull()) {
/* 103 */             return FMOD_RESULT.FMOD_ERR_INVALID_PARAM;
/*     */           }
/*     */           
/* 106 */           ByteBuffer file = (ByteBuffer)ObjectPointer.createView(handle).getObject();
/* 107 */           if (pos < 0 || pos > file.capacity()) {
/* 108 */             return FMOD_RESULT.FMOD_ERR_FILE_EOF;
/*     */           }
/* 110 */           file.position(pos);
/*     */           
/* 112 */           return FMOD_RESULT.FMOD_OK;
/*     */         }
/*     */       };
/*     */     this.dataLoader = dataLoader;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\MemoryFileSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */