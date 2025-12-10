/*     */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*     */ 
/*     */ import com.funcom.gameengine.model.token.GameTokenProcessor;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureDataSimple
/*     */   implements TextureData
/*     */ {
/*     */   public static final int MIPMAPS = 4;
/*  17 */   private static final ByteBuffer[] buffs = new ByteBuffer[4];
/*     */   
/*     */   private static final int INITIAL_BUFF_BASE_WIDTH = 2048;
/*     */   private static final int INITIAL_BUFF_BASE_HEIGHT = 2048;
/*     */   
/*     */   static {
/*  23 */     int width = 2048;
/*  24 */     int height = 2048;
/*  25 */     for (int i = 0; i < 4; i++) {
/*  26 */       buffs[i] = BufferUtils.createByteBuffer(width * height * 4);
/*  27 */       width /= 2;
/*  28 */       height /= 2;
/*     */     } 
/*     */   }
/*     */   private static final int INITIAL_BUFF_BPP = 4;
/*     */   private String resourceName;
/*     */   private ImageWrapper wrapper;
/*     */   private AsyncTexture2D texture;
/*     */   private int bytesPerPixel;
/*     */   private ByteBuffer[] pixelBuffers;
/*     */   private TextureUploadToken textureUploadToken;
/*     */   
/*     */   public TextureDataSimple(String resourceName, ImageWrapper wrapper, AsyncTexture2D texture) {
/*  40 */     this.resourceName = resourceName;
/*  41 */     this.wrapper = wrapper;
/*  42 */     this.texture = texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  47 */     this.bytesPerPixel = this.wrapper.getBytesPerPixel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws InterruptedException {
/*  53 */     int width = this.wrapper.getPower2Width();
/*  54 */     int height = this.wrapper.getPower2Height();
/*  55 */     this.pixelBuffers = new ByteBuffer[4];
/*  56 */     for (int i = 0; i < 4; i++) {
/*     */       int pixelBufferSize;
/*  58 */       if (i == 0) {
/*  59 */         pixelBufferSize = this.wrapper.getOriginalWidth() * this.wrapper.getOriginalHeight() * this.bytesPerPixel;
/*     */       } else {
/*  61 */         pixelBufferSize = width * height * this.bytesPerPixel;
/*     */       } 
/*     */       
/*  64 */       if (buffs[i] == null || buffs[i].capacity() < pixelBufferSize) {
/*     */         
/*  66 */         if (buffs.length > 1) {
/*  67 */           System.arraycopy(buffs, 0, buffs, 1, buffs.length - 1);
/*     */         }
/*  69 */         buffs[i] = BufferUtils.createByteBuffer(pixelBufferSize);
/*  70 */         GameTokenProcessor.STEPPEDTOKEN_LOG.warn("Created: " + pixelBufferSize + "@" + this.bytesPerPixel);
/*     */       } 
/*  72 */       this.pixelBuffers[i] = buffs[i];
/*     */       
/*  74 */       if (i == 0) {
/*  75 */         this.wrapper.setTargetBuffer(this.pixelBuffers[i]);
/*     */       }
/*     */       
/*  78 */       if (width == 1 && height == 1) {
/*     */         break;
/*     */       }
/*     */       
/*  82 */       width = Math.max(width / 2, 1);
/*  83 */       height = Math.max(height / 2, 1);
/*     */     } 
/*     */     
/*  86 */     for (ByteBuffer pixelBuffer : this.pixelBuffers) {
/*  87 */       if (pixelBuffer != null) {
/*  88 */         pixelBuffer.clear();
/*     */       }
/*     */     } 
/*     */     
/*  92 */     Texture realTexture = this.wrapper.loadTexture(true);
/*     */     
/*  94 */     Image image = realTexture.getImage();
/*  95 */     ByteBuffer srcPixelBuffer = image.getData(0);
/*     */     
/*  97 */     width = image.getWidth();
/*  98 */     height = image.getHeight();
/*  99 */     ByteBuffer lastBuf = srcPixelBuffer;
/* 100 */     lastBuf.rewind();
/*     */     
/* 102 */     if (this.pixelBuffers[0] != lastBuf) {
/* 103 */       int limit = lastBuf.limit();
/* 104 */       int putTo = 0;
/* 105 */       while (putTo < limit) {
/* 106 */         putTo += 256;
/* 107 */         if (putTo > limit) {
/* 108 */           putTo = limit;
/*     */         }
/* 110 */         lastBuf.limit(putTo);
/* 111 */         this.pixelBuffers[0].put(lastBuf);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     int length = this.pixelBuffers.length;
/* 116 */     for (int level = 1; level < length; level++) {
/* 117 */       lastBuf.rewind();
/* 118 */       byte[] lastArray = new byte[lastBuf.limit()];
/* 119 */       lastBuf.get(lastArray);
/* 120 */       int pos = 0;
/* 121 */       int j = 0;
/* 122 */       width = width / 2 * 2;
/* 123 */       height = height / 2 * 2;
/* 124 */       byte[] pixArray = new byte[width / 2 * height / 2 * this.bytesPerPixel];
/* 125 */       for (int y = 0; y < height; y += 2) {
/* 126 */         for (int x = 0; x < width; x += 2) {
/* 127 */           pos += this.bytesPerPixel;
/* 128 */           if (x + 1 < width) {
/* 129 */             for (int k = 0; k < this.bytesPerPixel; k++) {
/* 130 */               pixArray[j++] = lastArray[pos++];
/*     */             }
/*     */           }
/*     */         } 
/*     */         
/* 135 */         if (y + 1 < height) {
/* 136 */           pos += this.bytesPerPixel * width;
/*     */         }
/*     */       } 
/*     */       
/* 140 */       this.pixelBuffers[level].put(pixArray);
/*     */ 
/*     */       
/* 143 */       if (width == 1 && height == 1) {
/*     */         break;
/*     */       }
/*     */       
/* 147 */       width = Math.max(width / 2, 1);
/* 148 */       height = Math.max(height / 2, 1);
/*     */       
/* 150 */       lastBuf = this.pixelBuffers[level];
/*     */     } 
/* 152 */     srcPixelBuffer.rewind();
/* 153 */     srcPixelBuffer.limit(srcPixelBuffer.capacity());
/*     */     
/* 155 */     this.textureUploadToken = new TextureUploadToken(realTexture, this.texture, this.pixelBuffers, this.bytesPerPixel);
/*     */   }
/*     */   
/*     */   private void yieldInterruptible() throws InterruptedException {
/* 159 */     if (Thread.interrupted()) {
/* 160 */       throw new InterruptedException();
/*     */     }
/* 162 */     Thread.yield();
/*     */   }
/*     */   
/*     */   public void finish() throws InterruptedException {
/* 166 */     TokenRegister.instance().addToken((Token)this.textureUploadToken);
/* 167 */     this.textureUploadToken.waitUntilFinish();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 172 */     return this.resourceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return "{resourceName='" + this.resourceName + '\'' + "dimension='" + this.wrapper.getOriginalWidth() + "x" + this.wrapper.getOriginalHeight() + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\TextureDataSimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */