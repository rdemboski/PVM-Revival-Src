/*     */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeteredInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   int bytesLeft;
/*     */   int marked;
/*     */   
/*     */   public MeteredInputStream(InputStream in, int size) {
/*  22 */     super(in);
/*  23 */     this.bytesLeft = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int read() throws IOException {
/*  34 */     if (this.bytesLeft > 0) {
/*  35 */       int val = this.in.read();
/*  36 */       if (val != -1) {
/*  37 */         this.bytesLeft--;
/*     */       }
/*  39 */       return val;
/*     */     } 
/*  41 */     return -1;
/*     */   }
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
/*     */   public final int read(byte[] b) throws IOException {
/*  54 */     return read(b, 0, b.length);
/*     */   }
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
/*     */   public final int read(byte[] b, int off, int len) throws IOException {
/*  72 */     if (this.bytesLeft > 0) {
/*  73 */       len = (len > this.bytesLeft) ? this.bytesLeft : len;
/*  74 */       int read = this.in.read(b, off, len);
/*  75 */       if (read > 0) {
/*  76 */         this.bytesLeft -= read;
/*     */       }
/*  78 */       return read;
/*     */     } 
/*  80 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long skip(long n) throws IOException {
/*  91 */     n = (n > this.bytesLeft) ? this.bytesLeft : n;
/*  92 */     long skipped = this.in.skip(n);
/*  93 */     if (skipped > 0L) {
/*  94 */       this.bytesLeft = (int)(this.bytesLeft - skipped);
/*     */     }
/*  96 */     return skipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int available() throws IOException {
/* 106 */     int n = this.in.available();
/* 107 */     return (n > this.bytesLeft) ? this.bytesLeft : n;
/*     */   }
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
/*     */   public final void close() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mark(int readlimit) {
/* 130 */     this.marked = this.bytesLeft;
/* 131 */     this.in.mark(readlimit);
/*     */   }
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
/*     */   public final void reset() throws IOException {
/* 147 */     this.in.reset();
/* 148 */     this.bytesLeft = this.marked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean markSupported() {
/* 155 */     return this.in.markSupported();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\MeteredInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */