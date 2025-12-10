/*     */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*     */ 
/*     */ import com.funcom.gameengine.model.token.SteppedToken;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.lwjgl.records.TextureStateRecord;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ class TextureUploadToken
/*     */   implements SteppedToken
/*     */ {
/*     */   private Texture realTexture;
/*     */   private AsyncTexture2D texture;
/*     */   private ByteBuffer[] pixelBuffers;
/*     */   private int bytesPerPixel;
/*     */   private boolean finished;
/*     */   private boolean initialized;
/*     */   private int[] segmentYs;
/*     */   protected long reserveUsed;
/*     */   protected long pixelBufferUsed;
/*     */   protected long subImageUsed;
/*     */   protected long miscUsedStart;
/*     */   protected long miscUsedEnd;
/*     */   protected long loopUsed;
/*     */   protected long allUsed;
/*  35 */   private String didWhat = "constructed";
/*  36 */   protected CountDownLatch countDownLatch = new CountDownLatch(1);
/*     */   
/*     */   public TextureUploadToken(Texture realTexture, AsyncTexture2D texture, ByteBuffer[] pixelBuffers, int bytesPerPixel) {
/*  39 */     this.realTexture = realTexture;
/*  40 */     this.texture = texture;
/*  41 */     this.pixelBuffers = pixelBuffers;
/*  42 */     this.bytesPerPixel = bytesPerPixel;
/*  43 */     this.segmentYs = new int[4];
/*     */   }
/*     */ 
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  48 */     return Token.TokenType.GAME_THREAD;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/*  53 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   public void process() {
/*  58 */     long startAll = System.currentTimeMillis();
/*  59 */     long start = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (!this.initialized) {
/*     */ 
/*     */       
/*  66 */       TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  67 */       ts.setTexture((Texture)this.texture);
/*  68 */       ts.deleteAll();
/*     */       
/*  70 */       this.texture.copyRealProperties(this.realTexture);
/*     */ 
/*     */       
/*  73 */       Image image1 = this.texture.getImage();
/*  74 */       int m = TextureStateRecord.getGLPixelFormat(image1.getFormat());
/*     */       
/*  76 */       IntBuffer id = BufferUtils.createIntBuffer(1);
/*  77 */       id.clear();
/*  78 */       GL11.glGenTextures(id);
/*  79 */       this.texture.setTextureId(id.get(0));
/*  80 */       GL11.glBindTexture(3553, this.texture.getTextureId());
/*  81 */       GL11.glPixelStorei(3317, 1);
/*     */       
/*  83 */       GL11.glTexParameteri(3553, 33169, 0);
/*  84 */       GL11.glTexParameteri(3553, 33169, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       GL11.glTexParameteri(3553, 33085, 3);
/*     */       
/*  91 */       GL11.glTexParameteri(3553, 33085, 3);
/*     */       
/*  93 */       this.miscUsedStart += System.currentTimeMillis() - start;
/*     */       
/*  95 */       int j = image1.getWidth();
/*  96 */       int k = image1.getHeight();
/*  97 */       int internalFormat = TextureStateRecord.getGLDataFormat(image1.getFormat());
/*  98 */       for (int n = 0; n < 4; n++) {
/*     */         
/* 100 */         start = System.currentTimeMillis();
/*     */         
/* 102 */         GL11.glTexImage2D(3553, n, internalFormat, j, k, 0, m, 5121, (ByteBuffer)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 108 */         this.reserveUsed += System.currentTimeMillis() - start;
/*     */ 
/*     */         
/* 111 */         if (j == 1 && k == 1) {
/*     */           break;
/*     */         }
/*     */         
/* 115 */         j = Math.max(j / 2, 1);
/* 116 */         k = Math.max(k / 2, 1);
/*     */       } 
/* 118 */       this.initialized = true;
/* 119 */       this.didWhat = "Inited";
/*     */       
/*     */       return;
/*     */     } 
/* 123 */     GL11.glBindTexture(3553, this.texture.getTextureId());
/* 124 */     GL11.glPixelStorei(3317, 1);
/*     */     
/* 126 */     Image image = this.texture.getImage();
/* 127 */     int pixelFormat = TextureStateRecord.getGLPixelFormat(image.getFormat());
/* 128 */     int w = image.getWidth();
/* 129 */     int h = image.getHeight();
/* 130 */     long loopStart = System.currentTimeMillis();
/* 131 */     boolean moreToUpdated = false;
/* 132 */     for (int i = 0; i < 4; i++) {
/* 133 */       if (this.segmentYs[i] != -1) {
/* 134 */         start = System.currentTimeMillis();
/* 135 */         this.pixelBufferUsed += System.currentTimeMillis() - start;
/*     */ 
/*     */         
/* 138 */         start = System.currentTimeMillis();
/* 139 */         int linesToUpload = 4096 / w * h;
/* 140 */         if (linesToUpload < 4) {
/* 141 */           linesToUpload = 4;
/*     */         }
/* 143 */         int chunkHeight = linesToUpload;
/* 144 */         if (h - this.segmentYs[i] < linesToUpload) {
/* 145 */           chunkHeight = h - this.segmentYs[i];
/*     */         }
/* 147 */         int y = this.segmentYs[i];
/* 148 */         GL11.glPixelStorei(3317, 1);
/* 149 */         this.pixelBuffers[i].position(y * w * this.bytesPerPixel);
/* 150 */         GL11.glTexSubImage2D(3553, i, 0, y, w, chunkHeight, pixelFormat, 5121, this.pixelBuffers[i]);
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
/*     */         
/* 170 */         this.segmentYs[i] = this.segmentYs[i] + linesToUpload;
/* 171 */         if (this.segmentYs[i] >= h) {
/* 172 */           this.segmentYs[i] = -1;
/*     */         } else {
/* 174 */           moreToUpdated = true;
/*     */         } 
/*     */ 
/*     */         
/* 178 */         this.subImageUsed += System.currentTimeMillis() - start;
/*     */       } 
/*     */       
/* 181 */       if (w == 1 && h == 1) {
/*     */         break;
/*     */       }
/*     */       
/* 185 */       w = Math.max(w / 2, 1);
/* 186 */       h = Math.max(h / 2, 1);
/*     */     } 
/*     */     
/* 189 */     if (!moreToUpdated) {
/* 190 */       start = System.currentTimeMillis();
/* 191 */       this.miscUsedEnd += System.currentTimeMillis() - start;
/* 192 */       this.finished = true;
/*     */     } 
/*     */     
/* 195 */     this.loopUsed += System.currentTimeMillis() - loopStart;
/*     */ 
/*     */ 
/*     */     
/* 199 */     this.allUsed += System.currentTimeMillis() - startAll;
/* 200 */     if (this.finished) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       this.didWhat = "finished";
/* 210 */       this.countDownLatch.countDown();
/*     */     } else {
/* 212 */       this.didWhat = "in progress";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 218 */     return "TextureUploadToken{,didWhat='" + this.didWhat + '\'' + "}@" + Integer.toHexString(System.identityHashCode(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void waitUntilFinish() throws InterruptedException {
/* 226 */     this.countDownLatch.await();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\TextureUploadToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */