/*     */ package com.jmex.model.converters.maxutils;
/*     */ 
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.state.MaterialState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.WireframeState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CustomMaterialBlock
/*     */   extends ChunkerClass
/*     */ {
/*  16 */   private static final Logger logger = Logger.getLogger(MaterialBlock.class.getName());
/*     */   
/*     */   String name;
/*     */   
/*     */   MaterialState myMatState;
/*     */   TextureState myTexState;
/*     */   WireframeState myWireState;
/*     */   private TextureLoadDelegate textureLoadDelegate;
/*     */   private String modelPath;
/*     */   
/*     */   public CustomMaterialBlock(DataInput myIn, ChunkHeader i, TextureLoadDelegate textureLoadDelegate, String modelPath) throws IOException {
/*  27 */     super(myIn);
/*  28 */     this.textureLoadDelegate = textureLoadDelegate;
/*  29 */     this.modelPath = modelPath;
/*     */     
/*  31 */     setHeader(i);
/*  32 */     initializeVariables();
/*  33 */     chunk();
/*     */   }
/*     */   
/*     */   protected void initializeVariables() {
/*  37 */     this.myMatState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
/*  38 */     this.myMatState.setEnabled(false);
/*  39 */     this.myWireState = DisplaySystem.getDisplaySystem().getRenderer().createWireframeState();
/*  40 */     this.myWireState.setEnabled(false);
/*  41 */     this.myTexState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  42 */     this.myTexState.setEnabled(false);
/*     */   }
/*     */   protected boolean processChildChunk(ChunkHeader i) throws IOException {
/*     */     float alpha;
/*  46 */     switch (i.type) {
/*     */       case 40960:
/*  48 */         readMatName();
/*  49 */         return true;
/*     */       
/*     */       case 40976:
/*  52 */         this.myMatState.setAmbient((new ColorChunk(this.myIn, i)).getBestColor());
/*  53 */         this.myMatState.setEnabled(true);
/*  54 */         if (DEBUG || DEBUG_LIGHT) logger.info("Ambient color:" + this.myMatState.getAmbient()); 
/*  55 */         return true;
/*     */       
/*     */       case 40992:
/*  58 */         this.myMatState.setDiffuse((new ColorChunk(this.myIn, i)).getBestColor());
/*  59 */         this.myMatState.setEnabled(true);
/*  60 */         if (DEBUG || DEBUG_LIGHT) logger.info("Diffuse color:" + this.myMatState.getDiffuse()); 
/*  61 */         return true;
/*     */       
/*     */       case 41008:
/*  64 */         this.myMatState.setSpecular((new ColorChunk(this.myIn, i)).getBestColor());
/*  65 */         this.myMatState.setEnabled(true);
/*  66 */         if (DEBUG || DEBUG_LIGHT) logger.info("Diffuse color:" + this.myMatState.getSpecular()); 
/*  67 */         return true;
/*     */       case 41024:
/*  69 */         this.myMatState.setShininess(128.0F * (new PercentChunk(this.myIn, i)).percent);
/*  70 */         this.myMatState.setEnabled(true);
/*  71 */         if (DEBUG || DEBUG_LIGHT) logger.info("Shinniness:" + this.myMatState.getShininess()); 
/*  72 */         return true;
/*     */       case 41025:
/*  74 */         new PercentChunk(this.myIn, i);
/*  75 */         return true;
/*     */       case 41040:
/*  77 */         alpha = 1.0F - (new PercentChunk(this.myIn, i)).percent;
/*  78 */         (this.myMatState.getDiffuse()).a = alpha;
/*  79 */         (this.myMatState.getEmissive()).a = alpha;
/*  80 */         (this.myMatState.getAmbient()).a = alpha;
/*  81 */         this.myMatState.setEnabled(true);
/*  82 */         if (DEBUG || DEBUG_LIGHT) logger.info("Alpha:" + alpha); 
/*  83 */         return true;
/*     */       case 41042:
/*  85 */         new PercentChunk(this.myIn, i);
/*  86 */         return true;
/*     */       case 41043:
/*  88 */         new PercentChunk(this.myIn, i);
/*  89 */         return true;
/*     */       case 41216:
/*  91 */         this.myIn.readShort();
/*  92 */         return true;
/*     */       case 41092:
/*  94 */         new PercentChunk(this.myIn, i);
/*  95 */         return true;
/*     */       case 41095:
/*  97 */         this.myWireState.setLineWidth(this.myIn.readFloat());
/*  98 */         if (DEBUG || DEBUG_LIGHT) logger.info("Wireframe size:" + this.myWireState.getLineWidth()); 
/*  99 */         return true;
/*     */       case 41098:
/* 101 */         return true;
/*     */       case 41472:
/* 103 */         readTextureMapOne(i);
/* 104 */         return true;
/*     */       case 41520:
/* 106 */         readTextureBumpMap(i);
/* 107 */         return true;
/*     */       
/*     */       case 41100:
/* 110 */         return true;
/*     */       case 41760:
/* 112 */         this.myIn.readFully(new byte[i.length]);
/* 113 */         return true;
/*     */       case 41552:
/* 115 */         if (DEBUG) logger.info("Material blur present");
/*     */         
/* 117 */         return true;
/*     */       case 41102:
/* 119 */         if (DEBUG) logger.info("Using absolute wire in units");
/*     */         
/* 121 */         return true;
/*     */       case 41504:
/* 123 */         readReflectMap(i);
/* 124 */         return true;
/*     */       case 41764:
/* 126 */         this.myIn.readFully(new byte[i.length]);
/* 127 */         return true;
/*     */       case 41089:
/* 129 */         this.myMatState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);
/*     */         
/* 131 */         return true;
/*     */       case 41536:
/* 133 */         if (DEBUG) logger.info("Using material falloff");
/*     */         
/* 135 */         return true;
/*     */       case 41093:
/* 137 */         if (DEBUG) logger.info("Material wireframe is active"); 
/* 138 */         this.myWireState.setEnabled(true);
/* 139 */         return true;
/*     */       case 41786:
/* 141 */         readTextureMapTwo(i);
/* 142 */         return true;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readTextureMapOne(ChunkHeader i) throws IOException {
/* 149 */     TextureChunk tc = new TextureChunk(this.myIn, i);
/* 150 */     Texture t = this.textureLoadDelegate.createTexture(this.modelPath, tc);
/* 151 */     this.myTexState.setTexture(t, 0);
/* 152 */     this.myTexState.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void readTextureMapTwo(ChunkHeader i) throws IOException {
/* 156 */     TextureChunk tc = new TextureChunk(this.myIn, i);
/* 157 */     Texture t = this.textureLoadDelegate.createTexture(this.modelPath, tc);
/* 158 */     this.myTexState.setTexture(t, 1);
/* 159 */     this.myTexState.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void readReflectMap(ChunkHeader i) throws IOException {
/* 163 */     TextureChunk tc = new TextureChunk(this.myIn, i);
/* 164 */     Texture t = this.textureLoadDelegate.createTexture(this.modelPath, tc);
/* 165 */     this.myTexState.setTexture(t, 2);
/* 166 */     this.myTexState.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void readTextureBumpMap(ChunkHeader i) throws IOException {
/* 170 */     TextureChunk tc = new TextureChunk(this.myIn, i);
/* 171 */     Texture t = this.textureLoadDelegate.createTexture(this.modelPath, tc);
/* 172 */     this.myTexState.setTexture(t, 3);
/* 173 */     this.myTexState.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void readMatName() throws IOException {
/* 177 */     this.name = readcStr();
/* 178 */     if (DEBUG || DEBUG_LIGHT) logger.info("read material name:" + this.name); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\model\converters\maxutils\CustomMaterialBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */