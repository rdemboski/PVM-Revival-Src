/*     */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*     */ 
/*     */ import com.funcom.gameengine.model.token.GameTokenProcessor;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.SynchronizedTextureLoader;
/*     */ import com.funcom.util.DebugManager;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.image.Texture2D;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PngWrapper
/*     */   implements ImageWrapper
/*     */ {
/*  23 */   private static final byte[] SIGNATURE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   private String id;
/*     */   private BufferedInputStream inputStream;
/*     */   private final TextureSetup setup;
/*     */   private int power2Width;
/*     */   private int power2Height;
/*     */   private PngDataLoader loader;
/*     */   private int originalHeight;
/*     */   private int originalWidth;
/*     */   
/*     */   public PngWrapper(String id, BufferedInputStream inputStream, TextureSetup setup) {
/*  34 */     this.id = id;
/*  35 */     this.inputStream = inputStream;
/*  36 */     this.setup = setup;
/*     */   }
/*     */   
/*     */   public void init() throws IOException, InterruptedException {
/*  40 */     this.loader = new PngDataLoader(this.inputStream);
/*  41 */     this.loader.loadHeader();
/*     */     
/*  43 */     if (this.loader.getDepth() > 8) {
/*  44 */       String message = "********************************************\n*** PNG has high depth, with each element\n*** using '" + this.loader.getDepth() + "' bits.\n" + "*** file: " + this.id + "\n" + "*** CONTACT ART!\n" + "********************************************";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  50 */       DebugManager.getInstance().warn(message);
/*     */     } 
/*     */     
/*  53 */     this.originalHeight = this.loader.getHeight();
/*  54 */     this.originalWidth = this.loader.getWidth();
/*  55 */     this.power2Width = ImageWrapperFactory.smallestPower2(this.originalWidth);
/*  56 */     this.power2Height = ImageWrapperFactory.smallestPower2(this.originalHeight);
/*     */     
/*  58 */     if (SynchronizedTextureLoader.SMALL_IMAGE) {
/*  59 */       if (this.power2Width > 256) {
/*  60 */         this.power2Width /= 4;
/*  61 */       } else if (this.power2Width > 4) {
/*  62 */         this.power2Width /= 2;
/*     */       } 
/*  64 */       if (this.power2Height > 256) {
/*  65 */         this.power2Height /= 4;
/*  66 */       } else if (this.power2Height > 4) {
/*  67 */         this.power2Height /= 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPngSignature(byte[] signature) {
/*  73 */     int length = signature.length;
/*  74 */     for (int i = 0; i < length; i++) {
/*  75 */       if (signature[i] != SIGNATURE[i]) {
/*  76 */         return false;
/*     */       }
/*     */     } 
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPower2Width() {
/*  84 */     return this.power2Width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPower2Height() {
/*  89 */     return this.power2Height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginalWidth() {
/*  94 */     return this.originalWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginalHeight() {
/*  99 */     return this.originalHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytesPerPixel() {
/* 104 */     return this.loader.getBytesPerPixel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Texture loadTexture(boolean toPowerOf2) throws InterruptedException {
/*     */     try {
/* 111 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("--- " + this.id + " - " + this.originalWidth + "x" + this.originalHeight + " ---");
/* 112 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("  Loading");
/* 113 */       long start = System.currentTimeMillis();
/* 114 */       this.loader.load();
/* 115 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("    used: " + (System.currentTimeMillis() - start));
/*     */       
/* 117 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("  Resizing");
/* 118 */       start = System.currentTimeMillis();
/* 119 */       if (toPowerOf2) {
/* 120 */         this.loader.resizeDown(this.power2Width, this.power2Height);
/*     */       }
/* 122 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("    used: " + (System.currentTimeMillis() - start));
/*     */       
/* 124 */       Image imageData = new Image();
/* 125 */       imageData.setFormat((this.loader.getBytesPerPixel() == 4) ? Image.Format.RGBA8 : Image.Format.RGB8);
/* 126 */       imageData.setWidth(this.loader.getWidth());
/* 127 */       imageData.setHeight(this.loader.getHeight());
/* 128 */       imageData.setData(this.loader.getPixels());
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
/* 140 */       Texture2D texture2D = new Texture2D();
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
/*     */       
/* 161 */       texture2D.setImage(imageData);
/* 162 */       texture2D.setAnisotropicFilterPercent(0.0F);
/* 163 */       this.setup.setup((Texture)texture2D);
/*     */       
/* 165 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("    Return Texture");
/* 166 */       return (Texture)texture2D;
/* 167 */     } catch (IOException e) {
/* 168 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTargetBuffer(ByteBuffer pixelBuffer) {
/* 174 */     this.loader.setPixels(pixelBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getTargetBuffer() {
/* 179 */     return this.loader.getPixels();
/*     */   }
/*     */   
/*     */   public static boolean isPng(BufferedInputStream bufferedInputStream) throws IOException {
/*     */     try {
/* 184 */       bufferedInputStream.mark(8);
/* 185 */       byte[] signature = new byte[8];
/* 186 */       (new DataInputStream(bufferedInputStream)).readFully(signature);
/* 187 */       return isPngSignature(signature);
/*     */     } finally {
/* 189 */       bufferedInputStream.reset();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\PngWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */