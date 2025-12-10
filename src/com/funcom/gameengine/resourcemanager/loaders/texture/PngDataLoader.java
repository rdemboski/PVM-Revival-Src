/*      */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*      */ 
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*      */ import com.jme.util.NanoTimer;
/*      */ import com.jme.util.geom.BufferUtils;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.SequenceInputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.Enumeration;
/*      */ import java.util.zip.Inflater;
/*      */ import java.util.zip.InflaterInputStream;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PngDataLoader
/*      */ {
/*      */   private static final int TYPE_GRAYSCALE = 0;
/*      */   private static final int TYPE_PALETTE = 3;
/*      */   private static final int TYPE_RBG = 2;
/*      */   private static final int TYPE_GRAYSCALE_ALPHA = 4;
/*      */   private static final int TYPE_RGB_ALPHA = 6;
/*      */   private int dataWidth;
/*      */   private int dataHeight;
/*      */   private ByteBuffer pixels;
/*      */   private boolean multipass;
/*      */   private boolean complete;
/*      */   private BufferedInputStream underlyingStream;
/*      */   private DataInputStream inputStream;
/*      */   private boolean infoAvailable = false;
/*      */   private boolean readUntilPixelData;
/*   53 */   private int compressionMethod = -1;
/*   54 */   private int depth = -1;
/*   55 */   private int colorType = -1;
/*   56 */   private int filterMethod = -1;
/*   57 */   private int interlaceMethod = -1;
/*      */   
/*      */   private int pass;
/*      */   
/*      */   private byte[] palette;
/*      */   
/*      */   private boolean transparency;
/*      */   private int chunkLength;
/*      */   private int chunkType;
/*      */   private int bytesPerPixel;
/*      */   private boolean needChunkInfo = true;
/*      */   static final int CHUNK_bKGD = 1649100612;
/*      */   static final int CHUNK_cHRM = 1665684045;
/*      */   static final int CHUNK_gAMA = 1732332865;
/*      */   static final int CHUNK_hIST = 1749635924;
/*      */   static final int CHUNK_IDAT = 1229209940;
/*      */   static final int CHUNK_IEND = 1229278788;
/*      */   static final int CHUNK_IHDR = 1229472850;
/*      */   static final int CHUNK_PLTE = 1347179589;
/*      */   static final int CHUNK_pHYs = 1883789683;
/*      */   static final int CHUNK_sBIT = 1933723988;
/*      */   static final int CHUNK_tEXt = 1950701684;
/*      */   static final int CHUNK_tIME = 1950960965;
/*      */   static final int CHUNK_tRNS = 1951551059;
/*      */   static final int CHUNK_zTXt = 2052348020;
/*   82 */   static final int[] startingRow = new int[] { 0, 0, 0, 4, 0, 2, 0, 1 };
/*   83 */   static final int[] startingCol = new int[] { 0, 0, 4, 0, 2, 0, 1, 0 };
/*   84 */   static final int[] rowInc = new int[] { 1, 8, 8, 8, 4, 4, 2, 2 };
/*   85 */   static final int[] colInc = new int[] { 1, 8, 8, 4, 4, 2, 2, 1 };
/*   86 */   static final int[] blockHeight = new int[] { 1, 8, 8, 4, 4, 2, 2, 1 };
/*   87 */   static final int[] blockWidth = new int[] { 1, 8, 4, 4, 2, 2, 1, 1 };
/*      */   protected boolean flipped = true;
/*      */   
/*      */   public PngDataLoader(BufferedInputStream is) {
/*   91 */     this.underlyingStream = is;
/*   92 */     this.inputStream = new DataInputStream(this.underlyingStream);
/*      */   }
/*      */   
/*      */   public void loadHeader() throws IOException, InterruptedException {
/*   96 */     if (!this.readUntilPixelData) {
/*      */       
/*   98 */       handleSignature();
/*   99 */       while (!this.readUntilPixelData) {
/*  100 */         handleChunk(true);
/*      */       }
/*  102 */       if (this.transparency || this.colorType == 6 || this.colorType == 4) {
/*      */ 
/*      */         
/*  105 */         this.bytesPerPixel = 4;
/*      */       } else {
/*  107 */         this.bytesPerPixel = 3;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void load() throws IOException, InterruptedException {
/*  118 */     loadHeader();
/*      */     
/*  120 */     boolean bTimeout = false;
/*  121 */     if (LoadingManager.INSTANCE != null && LoadingManager.INSTANCE.getThreadedQueue() != null) {
/*  122 */       bTimeout = (LoadingManager.INSTANCE.getAvailableProcessors() <= 1 && Thread.currentThread().getName().compareTo("main") != 0);
/*      */     }
/*      */     
/*  125 */     NanoTimer nanoTimer = new NanoTimer();
/*  126 */     long nCurrentTime = nanoTimer.getTime();
/*  127 */     long nPreviousTime = nCurrentTime;
/*  128 */     while (!this.complete) {
/*      */       
/*  130 */       nCurrentTime = nanoTimer.getTime();
/*  131 */       if (bTimeout && nCurrentTime - nPreviousTime > LoadingManager.INSTANCE.getThreadedQueue().getReservedTicks()) {
/*  132 */         nPreviousTime = nCurrentTime;
/*  133 */         Thread.yield();
/*      */       } 
/*  135 */       handleChunk(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void dispose() {
/*      */     try {
/*  141 */       if (this.underlyingStream != null) {
/*  142 */         this.underlyingStream.close();
/*  143 */         this.underlyingStream = null;
/*      */       } 
/*      */       
/*  146 */       if (this.inputStream != null) {
/*  147 */         this.inputStream.close();
/*  148 */         this.inputStream = null;
/*      */       } 
/*  150 */     } catch (Exception ignore) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean filterRow(byte[] inbuf, int[] pix, int[] upix, int rowFilter, int boff) {
/*  155 */     int x, rowWidth = pix.length;
/*      */     
/*  157 */     switch (rowFilter) {
/*      */       case 0:
/*  159 */         for (x = 0; x < rowWidth; x++) {
/*  160 */           pix[x] = 0xFF & inbuf[x];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  247 */         return true;case 1: x = 0; for (; x < boff; x++) pix[x] = 0xFF & inbuf[x];  for (; x < rowWidth; x++) pix[x] = 0xFF & inbuf[x] + pix[x - boff];  return true;case 2: if (upix != null) { for (x = 0; x < rowWidth; x++) pix[x] = 0xFF & upix[x] + inbuf[x];  } else { for (x = 0; x < rowWidth; x++) pix[x] = 0xFF & inbuf[x];  }  return true;case 3: if (upix != null) { x = 0; for (; x < boff; x++) { int rval = upix[x]; pix[x] = 0xFF & (rval >> 1) + inbuf[x]; }  for (; x < rowWidth; x++) { int rval = upix[x] + pix[x - boff]; pix[x] = 0xFF & (rval >> 1) + inbuf[x]; }  } else { x = 0; for (; x < boff; x++) pix[x] = 0xFF & inbuf[x];  for (; x < rowWidth; x++) { int rval = pix[x - boff]; pix[x] = 0xFF & (rval >> 1) + inbuf[x]; }  }  return true;case 4: if (upix != null) { x = 0; for (; x < boff; x++) pix[x] = 0xFF & upix[x] + inbuf[x];  for (; x < rowWidth; x++) { int rval, a = pix[x - boff]; int b = upix[x]; int c = upix[x - boff]; int p = a + b - c; int pa = (p > a) ? (p - a) : (a - p); int pb = (p > b) ? (p - b) : (b - p); int pc = (p > c) ? (p - c) : (c - p); if (pa <= pb && pa <= pc) { rval = a; } else if (pb <= pc) { rval = b; } else { rval = c; }  pix[x] = 0xFF & rval + inbuf[x]; }  } else { x = 0; for (; x < boff; x++) pix[x] = 0xFF & inbuf[x];  for (; x < rowWidth; x++) { int rval = pix[x - boff]; pix[x] = 0xFF & rval + inbuf[x]; }  }  return true;
/*      */     } 
/*      */     return false;
/*      */   } private void handleChunk(boolean stopAtiDAT) throws IOException, InterruptedException {
/*  251 */     if (this.needChunkInfo) {
/*  252 */       this.inputStream.mark(8);
/*  253 */       this.chunkLength = this.inputStream.readInt();
/*  254 */       this.chunkType = this.inputStream.readInt();
/*  255 */       if (stopAtiDAT && this.chunkType == 1229209940) {
/*  256 */         this.readUntilPixelData = true;
/*  257 */         this.inputStream.reset();
/*      */         return;
/*      */       } 
/*  260 */       this.needChunkInfo = false;
/*      */     } 
/*      */     
/*  263 */     switch (this.chunkType) {
/*      */       case 1229209940:
/*  265 */         handleIDAT();
/*      */         break;
/*      */       case 1229278788:
/*  268 */         handleIEND();
/*      */         break;
/*      */       case 1229472850:
/*  271 */         handleIHDR();
/*      */         break;
/*      */       case 1347179589:
/*  274 */         handlePLTE();
/*      */         break;
/*      */       case 1951551059:
/*  277 */         handletRNS();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  289 */         skipChunkData();
/*      */         break;
/*      */     } 
/*  292 */     int crc = this.inputStream.readInt();
/*  293 */     this.needChunkInfo = true;
/*      */   }
/*      */   
/*      */   private void skipChunkData() throws IOException {
/*  297 */     int toSkip = this.chunkLength;
/*  298 */     while (toSkip > 0) {
/*  299 */       toSkip = (int)(toSkip - this.inputStream.skip(toSkip));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleIDAT() throws IOException, InterruptedException {
/*  315 */     if (!this.infoAvailable) {
/*  316 */       makePixelBuffer();
/*  317 */       if (this.interlaceMethod != 0) {
/*  318 */         this.multipass = true;
/*      */       }
/*      */     } 
/*      */     
/*  322 */     readImageData();
/*      */   }
/*      */   
/*      */   private void handleIEND() throws IOException {
/*  326 */     this.complete = true;
/*      */   }
/*      */   
/*      */   private void handleIHDR() throws IOException {
/*  330 */     if (this.readUntilPixelData) {
/*  331 */       throw new IOException("Extraneous IHDR chunk encountered.");
/*      */     }
/*  333 */     if (this.chunkLength != 13) {
/*  334 */       throw new IOException("IHDR chunk length wrong: " + this.chunkLength);
/*      */     }
/*  336 */     this.dataWidth = this.inputStream.readInt();
/*  337 */     this.dataHeight = this.inputStream.readInt();
/*  338 */     this.depth = this.inputStream.read();
/*  339 */     this.colorType = this.inputStream.read();
/*  340 */     this.compressionMethod = this.inputStream.read();
/*  341 */     this.filterMethod = this.inputStream.read();
/*  342 */     this.interlaceMethod = this.inputStream.read();
/*      */   }
/*      */   
/*      */   public int getDepth() {
/*  346 */     return this.depth;
/*      */   }
/*      */   
/*      */   private void handlePLTE() throws IOException {
/*  350 */     if (this.colorType == 3) {
/*  351 */       this.palette = new byte[this.chunkLength];
/*  352 */       this.inputStream.readFully(this.palette);
/*      */     } else {
/*      */       
/*  355 */       skipChunkData();
/*      */     }  } private void handletRNS() throws IOException { int transLength; byte trans[], b;
/*      */     int i;
/*      */     byte[] newPalette;
/*      */     int j;
/*  360 */     if (this.palette == null) {
/*  361 */       throw new IOException("tRNS chunk encountered before pLTE");
/*      */     }
/*      */     
/*  364 */     int len = this.palette.length;
/*      */     
/*  366 */     switch (this.colorType) {
/*      */       case 3:
/*  368 */         this.transparency = true;
/*  369 */         transLength = len / 3;
/*  370 */         trans = new byte[transLength];
/*  371 */         this.inputStream.readFully(trans, 0, this.chunkLength);
/*      */         
/*  373 */         b = -1;
/*      */         
/*  375 */         for (i = len; i < transLength; i++) {
/*  376 */           trans[i] = b;
/*      */         }
/*      */         
/*  379 */         newPalette = new byte[len + transLength];
/*      */         
/*  381 */         for (j = newPalette.length; j > 0; ) {
/*  382 */           newPalette[--j] = trans[--transLength];
/*  383 */           newPalette[--j] = this.palette[--len];
/*  384 */           newPalette[--j] = this.palette[--len];
/*  385 */           newPalette[--j] = this.palette[--len];
/*      */         } 
/*      */         
/*  388 */         this.palette = newPalette;
/*      */         return;
/*      */     } 
/*      */     
/*  392 */     skipChunkData(); }
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSignature() throws IOException {
/*  397 */     if (this.inputStream.read() != 137 || this.inputStream.read() != 80 || this.inputStream.read() != 78 || this.inputStream.read() != 71 || this.inputStream.read() != 13 || this.inputStream.read() != 10 || this.inputStream.read() != 26 || this.inputStream.read() != 10)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  405 */       throw new IOException("Not a PNG File");
/*      */     }
/*      */   }
/*      */   
/*      */   private void insertGreyPixels(int[] pix, int offset, int samples) {
/*  410 */     int j, p = pix[0];
/*  411 */     int cInc = colInc[this.pass];
/*  412 */     int rs = 0;
/*  413 */     switch (this.colorType) {
/*      */       case 0:
/*  415 */         switch (this.depth) {
/*      */           case 1:
/*  417 */             for (j = 0; j < samples; j++, offset += cInc) {
/*  418 */               if (rs != 0) {
/*  419 */                 rs--;
/*      */               } else {
/*  421 */                 rs = 7;
/*  422 */                 p = pix[j >> 3];
/*      */               } 
/*  424 */               byte gray = scaleGrayComp(p >> rs & 0x1);
/*  425 */               if (this.flipped) {
/*  426 */                 this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */               } else {
/*  428 */                 this.pixels.position(offset * 3);
/*      */               } 
/*  430 */               this.pixels.put(gray);
/*  431 */               this.pixels.put(gray);
/*  432 */               this.pixels.put(gray);
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 2:
/*  437 */             for (j = 0; j < samples; j++, offset += cInc) {
/*  438 */               if (rs != 0) {
/*  439 */                 rs -= 2;
/*      */               } else {
/*  441 */                 rs = 6;
/*  442 */                 p = pix[j >> 2];
/*      */               } 
/*  444 */               byte gray = scaleGrayComp(p >> rs & 0x3);
/*  445 */               if (this.flipped) {
/*  446 */                 this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */               } else {
/*  448 */                 this.pixels.position(offset * 3);
/*      */               } 
/*  450 */               this.pixels.put(gray);
/*  451 */               this.pixels.put(gray);
/*  452 */               this.pixels.put(gray);
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 4:
/*  457 */             for (j = 0; j < samples; j++, offset += cInc) {
/*  458 */               if (rs != 0) {
/*  459 */                 rs = 0;
/*      */               } else {
/*  461 */                 rs = 4;
/*  462 */                 p = pix[j >> 1];
/*      */               } 
/*  464 */               byte gray = scaleGrayComp(p >> rs & 0xF);
/*  465 */               if (this.flipped) {
/*  466 */                 this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */               } else {
/*  468 */                 this.pixels.position(offset * 3);
/*      */               } 
/*  470 */               this.pixels.put(gray);
/*  471 */               this.pixels.put(gray);
/*  472 */               this.pixels.put(gray);
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8:
/*  477 */             for (j = 0; j < samples; offset += cInc) {
/*  478 */               byte gray = (byte)pix[j++];
/*  479 */               if (this.flipped) {
/*  480 */                 this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */               } else {
/*  482 */                 this.pixels.position(offset * 3);
/*      */               } 
/*  484 */               this.pixels.put(gray);
/*  485 */               this.pixels.put(gray);
/*  486 */               this.pixels.put(gray);
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 16:
/*  491 */             samples <<= 1;
/*  492 */             for (j = 0; j < samples; j += 2, offset += cInc) {
/*  493 */               byte gray = (byte)pix[j++];
/*  494 */               if (this.flipped) {
/*  495 */                 this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */               } else {
/*  497 */                 this.pixels.position(offset * 3);
/*      */               } 
/*  499 */               this.pixels.put(gray);
/*  500 */               this.pixels.put(gray);
/*  501 */               this.pixels.put(gray);
/*      */             } 
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  511 */         if (this.depth == 8) {
/*  512 */           for (j = 0; j < samples; offset += cInc) {
/*  513 */             byte gray = (byte)pix[j++];
/*  514 */             if (this.flipped) {
/*  515 */               this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 4);
/*      */             } else {
/*  517 */               this.pixels.position(offset * 4);
/*      */             } 
/*  519 */             this.pixels.put((byte)pix[j++]);
/*  520 */             this.pixels.put(gray);
/*  521 */             this.pixels.put(gray);
/*  522 */             this.pixels.put(gray);
/*      */           }  break;
/*      */         } 
/*  525 */         samples <<= 1;
/*  526 */         for (j = 0; j < samples; j += 2, offset += cInc) {
/*  527 */           byte gray = (byte)pix[j];
/*  528 */           if (this.flipped) {
/*  529 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 4);
/*      */           } else {
/*  531 */             this.pixels.position(offset * 4);
/*      */           } 
/*  533 */           j += 2; this.pixels.put((byte)pix[j]);
/*  534 */           this.pixels.put(gray);
/*  535 */           this.pixels.put(gray);
/*  536 */           this.pixels.put(gray);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private byte scaleGrayComp(int rawGray) {
/*      */     byte grayComponent;
/*  546 */     switch (this.depth) {
/*      */       case 1:
/*  548 */         grayComponent = (byte)(rawGray * 255);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  569 */         return grayComponent;case 2: grayComponent = (byte)(rawGray * 255 / 3); return grayComponent;case 4: grayComponent = (byte)(rawGray * 255 / 15); return grayComponent;case 8: grayComponent = (byte)rawGray; return grayComponent;case 16: grayComponent = (byte)rawGray; return grayComponent;
/*      */     } 
/*      */     throw new RuntimeException("invalid depth for grayscale: depth=" + this.depth);
/*      */   }
/*      */   private void insertPalettedPixels(int[] pix, int offset, int samples) {
/*  574 */     int j, rs = 0;
/*  575 */     int p = pix[0];
/*  576 */     int cInc = colInc[this.pass];
/*      */     
/*  578 */     switch (this.depth) {
/*      */       case 1:
/*  580 */         for (j = 0; j < samples; j++, offset += cInc) {
/*  581 */           if (rs != 0) {
/*  582 */             rs--;
/*      */           } else {
/*  584 */             rs = 7;
/*  585 */             p = pix[j >> 3];
/*      */           } 
/*      */ 
/*      */           
/*  589 */           int paletteIndex = p >> rs & 0x1;
/*  590 */           if (this.flipped) {
/*  591 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * this.bytesPerPixel);
/*      */           } else {
/*  593 */             this.pixels.position(offset * this.bytesPerPixel);
/*      */           } 
/*  595 */           this.pixels.put(this.palette, paletteIndex * this.bytesPerPixel, this.bytesPerPixel);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 2:
/*  600 */         for (j = 0; j < samples; j++, offset += cInc) {
/*  601 */           if (rs != 0) {
/*  602 */             rs -= 2;
/*      */           } else {
/*  604 */             rs = 6;
/*  605 */             p = pix[j >> 2];
/*      */           } 
/*      */           
/*  608 */           int paletteIndex = p >> rs & 0x3;
/*  609 */           if (this.flipped) {
/*  610 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * this.bytesPerPixel);
/*      */           } else {
/*  612 */             this.pixels.position(offset * this.bytesPerPixel);
/*      */           } 
/*  614 */           this.pixels.put(this.palette, paletteIndex * this.bytesPerPixel, this.bytesPerPixel);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 4:
/*  619 */         for (j = 0; j < samples; j++, offset += cInc) {
/*  620 */           if (rs != 0) {
/*  621 */             rs = 0;
/*      */           } else {
/*  623 */             rs = 4;
/*  624 */             p = pix[j >> 1];
/*      */           } 
/*      */           
/*  627 */           int paletteIndex = p >> rs & 0xF;
/*  628 */           if (this.flipped) {
/*  629 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * this.bytesPerPixel);
/*      */           } else {
/*  631 */             this.pixels.position(offset * this.bytesPerPixel);
/*      */           } 
/*  633 */           this.pixels.put(this.palette, paletteIndex * this.bytesPerPixel, this.bytesPerPixel);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 8:
/*  638 */         for (j = 0; j < samples; j++, offset += cInc) {
/*  639 */           int paletteIndex = pix[j];
/*  640 */           if (this.flipped) {
/*  641 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * this.bytesPerPixel);
/*      */           } else {
/*  643 */             this.pixels.position(offset * this.bytesPerPixel);
/*      */           } 
/*  645 */           this.pixels.put(this.palette, paletteIndex * this.bytesPerPixel, this.bytesPerPixel);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void insertPixels(int[] pix, int offset, int samples) {
/*      */     int j;
/*      */     int cInc;
/*  655 */     switch (this.colorType) {
/*      */       case 0:
/*      */       case 4:
/*  658 */         insertGreyPixels(pix, offset, samples);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  663 */         cInc = colInc[this.pass];
/*  664 */         if (this.depth == 8) {
/*  665 */           processRBG8(offset, samples, cInc, pix);
/*      */           break;
/*      */         } 
/*  668 */         for (j = 0; j < samples; offset += cInc) {
/*  669 */           if (this.flipped) {
/*  670 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */           } else {
/*  672 */             this.pixels.position(offset * 3);
/*      */           } 
/*  674 */           this.pixels.put((byte)pix[j]);
/*  675 */           this.pixels.put((byte)pix[j + 2]);
/*  676 */           this.pixels.put((byte)pix[j + 4]);
/*  677 */           j += 6;
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  683 */         insertPalettedPixels(pix, offset, samples);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/*  688 */         cInc = colInc[this.pass];
/*  689 */         if (this.depth == 8) {
/*  690 */           processRBGA8(offset, samples, cInc, pix);
/*      */           break;
/*      */         } 
/*  693 */         for (j = 0; j < samples; offset += cInc) {
/*  694 */           if (this.flipped) {
/*  695 */             this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 4);
/*      */           } else {
/*      */             
/*  698 */             this.pixels.position(offset * 4);
/*      */           } 
/*  700 */           this.pixels.put((byte)pix[j]);
/*  701 */           this.pixels.put((byte)pix[j + 2]);
/*  702 */           this.pixels.put((byte)pix[j + 4]);
/*  703 */           this.pixels.put((byte)pix[j + 6]);
/*  704 */           j += 8;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processRBG8(int offset, int samples, int cInc, int[] pix) {
/*  716 */     if (cInc == 1) {
/*  717 */       for (int j = 0; j < samples; offset += this.dataWidth) {
/*  718 */         if (this.flipped) {
/*  719 */           this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */         } else {
/*  721 */           this.pixels.position(offset * 3);
/*      */         } 
/*  723 */         byte[] temp = new byte[this.dataWidth * 3];
/*  724 */         for (int i = 0; i < this.dataWidth * 3; i++) {
/*  725 */           temp[i] = (byte)pix[j + i];
/*      */         }
/*  727 */         this.pixels.put(temp);
/*  728 */         j += this.dataWidth * 3;
/*      */       } 
/*      */     } else {
/*  731 */       for (int j = 0; j < samples; offset += cInc) {
/*  732 */         if (this.flipped) {
/*  733 */           this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 3);
/*      */         } else {
/*  735 */           this.pixels.position(offset * 3);
/*      */         } 
/*  737 */         this.pixels.put((byte)pix[j++]);
/*  738 */         this.pixels.put((byte)pix[j++]);
/*  739 */         this.pixels.put((byte)pix[j++]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void processRBGA8(int offset, int samples, int cInc, int[] pix) {
/*  746 */     if (cInc == 1) {
/*  747 */       for (int j = 0; j < samples; offset += this.dataWidth) {
/*  748 */         if (this.flipped) {
/*  749 */           this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 4);
/*      */         } else {
/*  751 */           this.pixels.position(offset * 4);
/*      */         } 
/*  753 */         byte[] temp = new byte[this.dataWidth * 4];
/*  754 */         for (int i = 0; i < this.dataWidth * 4; i++) {
/*  755 */           temp[i] = (byte)pix[j + i];
/*      */         }
/*  757 */         this.pixels.put(temp);
/*  758 */         j += this.dataWidth * 4;
/*      */       } 
/*      */     } else {
/*  761 */       for (int j = 0; j < samples; offset += cInc) {
/*  762 */         if (this.flipped) {
/*  763 */           this.pixels.position(((this.dataHeight - offset / this.dataWidth - 1) * this.dataWidth + offset % this.dataWidth) * 4);
/*      */         } else {
/*  765 */           this.pixels.position(offset * 4);
/*      */         } 
/*  767 */         this.pixels.put((byte)pix[j]);
/*  768 */         this.pixels.put((byte)pix[j + 1]);
/*  769 */         this.pixels.put((byte)pix[j + 2]);
/*  770 */         this.pixels.put((byte)pix[j + 3]);
/*  771 */         j += 4;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readImageData() throws IOException, InterruptedException {
/*      */     int bps;
/*  782 */     InputStream dataStream = new SequenceInputStream(new IDATEnumeration(this));
/*      */     
/*  784 */     DataInputStream dis = new DataInputStream(new InflaterInputStream(dataStream, new Inflater()));
/*      */ 
/*      */ 
/*      */     
/*  788 */     switch (this.colorType) {
/*      */       case 0:
/*      */       case 3:
/*  791 */         bps = this.depth;
/*      */         break;
/*      */       case 2:
/*  794 */         bps = 3 * this.depth;
/*      */         break;
/*      */       case 4:
/*  797 */         bps = this.depth << 1;
/*      */         break;
/*      */       case 6:
/*  800 */         bps = this.depth << 2;
/*      */         break;
/*      */       
/*      */       default:
/*  804 */         throw new IOException("Unknown color type encountered.");
/*      */     } 
/*      */     
/*  807 */     int filterOffset = bps + 7 >> 3;
/*      */     
/*  809 */     for (this.pass = this.multipass ? 1 : 0; this.pass < 8; this.pass++) {
/*  810 */       int pass = this.pass;
/*  811 */       int rInc = rowInc[pass];
/*  812 */       int cInc = colInc[pass];
/*  813 */       int sCol = startingCol[pass];
/*      */       
/*  815 */       int val = (this.dataWidth - sCol + cInc - 1) / cInc;
/*      */       
/*  817 */       int samples = val * filterOffset;
/*  818 */       int rowSize = val * bps >> 3;
/*      */       
/*  820 */       int sRow = startingRow[pass];
/*      */       
/*  822 */       if (this.dataHeight > sRow && rowSize != 0) {
/*      */ 
/*      */ 
/*      */         
/*  826 */         int sInc = rInc * this.dataWidth;
/*      */         
/*  828 */         byte[] inbuf = new byte[rowSize];
/*  829 */         int[] pix = new int[rowSize];
/*  830 */         int[] upix = null;
/*  831 */         int[] temp = new int[rowSize];
/*      */ 
/*      */         
/*  834 */         int rows = 0;
/*  835 */         int rowStart = sRow * this.dataWidth;
/*      */         
/*  837 */         for (int y = sRow; y < this.dataHeight; y += rInc, rowStart += sInc) {
/*  838 */           rows += rInc;
/*      */           
/*  840 */           int rowFilter = dis.read();
/*  841 */           dis.readFully(inbuf);
/*      */           
/*  843 */           if (!filterRow(inbuf, pix, upix, rowFilter, filterOffset)) {
/*  844 */             throw new IOException("Unknown filter type: " + rowFilter);
/*      */           }
/*      */           
/*  847 */           insertPixels(pix, rowStart + sCol, samples);
/*      */           
/*  849 */           if (Thread.interrupted()) {
/*  850 */             throw new InterruptedException();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  857 */           upix = pix;
/*  858 */           pix = temp;
/*  859 */           temp = upix;
/*      */         } 
/*  861 */         if (!this.multipass)
/*      */           break; 
/*      */       } 
/*      */     } 
/*  865 */     while (dis.read() != -1) {
/*  866 */       System.err.println("Leftover data encountered.");
/*      */     }
/*      */   }
/*      */   
/*      */   private void makePixelBuffer() throws IOException {
/*  871 */     int bufferSize, count = this.dataWidth * this.dataHeight;
/*      */     
/*  873 */     switch (this.colorType) {
/*      */       case 3:
/*  875 */         if (this.palette == null) {
/*  876 */           throw new IOException("No palette located");
/*      */         }
/*      */       
/*      */       case 0:
/*      */       case 2:
/*      */       case 4:
/*      */       case 6:
/*  883 */         bufferSize = count * this.bytesPerPixel;
/*  884 */         if (this.pixels == null || this.pixels.capacity() < bufferSize) {
/*      */           
/*  886 */           this.pixels = null;
/*  887 */           this.pixels = BufferUtils.createByteBuffer(bufferSize);
/*      */         } 
/*  889 */         this.pixels.clear();
/*      */         return;
/*      */     } 
/*  892 */     throw new IOException("Image has unknown color type");
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHeight() {
/*  897 */     return this.dataHeight;
/*      */   }
/*      */   
/*      */   public int getWidth() {
/*  901 */     return this.dataWidth;
/*      */   }
/*      */   
/*      */   public void setPixels(ByteBuffer pixels) {
/*  905 */     this.pixels = pixels;
/*      */   }
/*      */   
/*      */   public ByteBuffer getPixels() {
/*  909 */     return this.pixels;
/*      */   }
/*      */   
/*      */   public int getBytesPerPixel() {
/*  913 */     return this.bytesPerPixel;
/*      */   }
/*      */   
/*      */   public boolean isFlipped() {
/*  917 */     return this.flipped;
/*      */   }
/*      */   
/*      */   public void resizeDown(int width, int height) {
/*  921 */     if (width > this.dataWidth || height > this.dataHeight) {
/*  922 */       throw new IllegalArgumentException("can only resize down");
/*      */     }
/*  924 */     if (this.dataWidth != width || this.dataHeight != height) {
/*  925 */       byte[] temp = new byte[width * height * this.bytesPerPixel];
/*  926 */       for (int y = 0; y < height; y++) {
/*  927 */         int sourceY = y * this.dataHeight / height;
/*  928 */         for (int x = 0; x < width; x++) {
/*  929 */           int sourceX = x * this.dataWidth / width;
/*      */           
/*  931 */           int destPos = (y * width + x) * this.bytesPerPixel;
/*  932 */           int sourcePos = (sourceY * this.dataWidth + sourceX) * this.bytesPerPixel;
/*  933 */           for (int i = 0; i < this.bytesPerPixel; i++) {
/*  934 */             temp[destPos + i] = this.pixels.get(sourcePos + i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  939 */       this.pixels.position(0);
/*  940 */       this.pixels.put(temp);
/*      */     } 
/*      */     
/*  943 */     this.dataWidth = width;
/*  944 */     this.dataHeight = height;
/*  945 */     this.pixels.limit(width * height * this.bytesPerPixel);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class IDATEnumeration
/*      */     implements Enumeration<InputStream>
/*      */   {
/*      */     InputStream underlyingStream;
/*      */     
/*      */     PngDataLoader owner;
/*      */     
/*      */     boolean firstStream = true;
/*      */ 
/*      */     
/*      */     public IDATEnumeration(PngDataLoader owner) {
/*  960 */       this.owner = owner;
/*  961 */       this.underlyingStream = owner.underlyingStream;
/*      */     }
/*      */     
/*      */     public InputStream nextElement() {
/*  965 */       this.firstStream = false;
/*  966 */       return new MeteredInputStream(this.underlyingStream, this.owner.chunkLength);
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/*  970 */       DataInputStream dis = new DataInputStream(this.underlyingStream);
/*  971 */       if (!this.firstStream) {
/*      */         try {
/*  973 */           int crc = dis.readInt();
/*  974 */           this.owner.needChunkInfo = false;
/*  975 */           this.owner.chunkLength = dis.readInt();
/*  976 */           this.owner.chunkType = dis.readInt();
/*  977 */         } catch (IOException ioe) {
/*  978 */           return false;
/*      */         } 
/*      */       }
/*  981 */       return (this.owner.chunkType == 1229209940);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) throws Exception {
/*  992 */     File file = new File("C:\\funcom\\resources\\particles\\jops\\textures\\cartoon_smoke.png");
/*      */ 
/*      */ 
/*      */     
/*  996 */     PngDataLoader loader = new PngDataLoader(new BufferedInputStream(new FileInputStream(file)));
/*      */     
/*  998 */     loader.load();
/*      */     
/* 1000 */     ByteBuffer pixels = loader.getPixels();
/* 1001 */     int type = 1;
/* 1002 */     if (loader.getBytesPerPixel() == 4) {
/* 1003 */       type = 2;
/*      */     }
/* 1005 */     BufferedImage img = new BufferedImage(loader.getWidth(), loader.getHeight(), type);
/* 1006 */     pixels.rewind();
/* 1007 */     for (int y = 0; y < img.getHeight(); y++) {
/* 1008 */       for (int x = 0; x < img.getWidth(); x++) {
/*      */         int rgba;
/* 1010 */         if (loader.getBytesPerPixel() == 3) {
/* 1011 */           rgba = (pixels.get() & 0xFF) << 16 | (pixels.get() & 0xFF) << 8 | pixels.get() & 0xFF;
/*      */         }
/*      */         else {
/*      */           
/* 1015 */           byte r = pixels.get();
/* 1016 */           byte g = pixels.get();
/* 1017 */           byte b = pixels.get();
/* 1018 */           byte a = pixels.get();
/* 1019 */           rgba = (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1025 */         img.setRGB(x, y, rgba);
/*      */       } 
/*      */     } 
/*      */     
/* 1029 */     JFrame f = new JFrame();
/* 1030 */     JLabel l = new JLabel(new ImageIcon(img));
/* 1031 */     f.getContentPane().add(l);
/* 1032 */     f.pack();
/* 1033 */     f.setVisible(true);
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\PngDataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */