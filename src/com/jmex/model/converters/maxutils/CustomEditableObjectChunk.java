/*     */ package com.jmex.model.converters.maxutils;
/*     */ 
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CustomEditableObjectChunk
/*     */   extends ChunkerClass
/*     */ {
/*  14 */   private static final Logger logger = Logger.getLogger(EditableObjectChunk.class.getName());
/*     */   
/*     */   HashMap<String, CustomMaterialBlock> materialBlocks;
/*     */   
/*     */   HashMap namedObjects;
/*     */   float masterScale;
/*     */   float shadowMapRange;
/*     */   float rayTraceBias;
/*     */   Vector3f oConstPlanes;
/*     */   ColorRGBA genAmbientColor;
/*     */   ColorRGBA backGroundColor;
/*     */   String backGroundBigMap;
/*     */   boolean useBackColor;
/*     */   float shadowBias;
/*     */   short shadowMapSize;
/*     */   LayeredFogChunk fogOptions;
/*     */   FogChunk myFog;
/*     */   DistanceQueueChunk distanceQueue;
/*     */   private TextureLoadDelegate textureLoadDelegate;
/*     */   private String modelPath;
/*     */   
/*     */   public CustomEditableObjectChunk(DataInput myIn, ChunkHeader i, TextureLoadDelegate textureLoadDelegate, String modelPath) throws IOException {
/*  36 */     super(myIn);
/*  37 */     this.textureLoadDelegate = textureLoadDelegate;
/*  38 */     this.modelPath = modelPath;
/*  39 */     setHeader(i);
/*  40 */     initializeVariables();
/*  41 */     chunk();
/*     */   }
/*     */   
/*     */   protected void initializeVariables() {
/*  45 */     this.materialBlocks = new HashMap<String, CustomMaterialBlock>();
/*  46 */     this.namedObjects = new HashMap<Object, Object>();
/*     */   } protected boolean processChildChunk(ChunkHeader i) throws IOException {
/*     */     CustomMaterialBlock tempMat;
/*     */     NamedObjectChunk tempOb;
/*  50 */     switch (i.type) {
/*     */       case 15678:
/*  52 */         readMeshVersion();
/*  53 */         return true;
/*     */       case 45055:
/*  55 */         tempMat = new CustomMaterialBlock(this.myIn, i, this.textureLoadDelegate, this.modelPath);
/*  56 */         this.materialBlocks.put(tempMat.name, tempMat);
/*  57 */         return true;
/*     */       
/*     */       case 256:
/*  60 */         readMasterScale();
/*  61 */         return true;
/*     */       
/*     */       case 16384:
/*  64 */         tempOb = new NamedObjectChunk(this.myIn, i);
/*  65 */         this.namedObjects.put(tempOb.name, tempOb);
/*  66 */         return true;
/*     */       case 28673:
/*  68 */         skipSize(i.length);
/*     */         
/*  70 */         return true;
/*     */       
/*     */       case 5200:
/*  73 */         readShadowRange();
/*  74 */         return true;
/*     */       
/*     */       case 5216:
/*  77 */         readRayTraceBias();
/*  78 */         return true;
/*     */       
/*     */       case 5376:
/*  81 */         readOConst();
/*  82 */         return true;
/*     */       
/*     */       case 8448:
/*  85 */         this.genAmbientColor = (new ColorChunk(this.myIn, i)).getBestColor();
/*  86 */         return true;
/*     */       
/*     */       case 4608:
/*  89 */         this.backGroundColor = (new ColorChunk(this.myIn, i)).getBestColor();
/*  90 */         return true;
/*     */       case 4352:
/*  92 */         this.backGroundBigMap = readcStr();
/*  93 */         return true;
/*     */       case 4864:
/*  95 */         skipSize(i.length);
/*  96 */         return true;
/*     */       case 4609:
/*  98 */         this.useBackColor = true;
/*  99 */         return true;
/*     */       case 8704:
/* 101 */         this.myFog = new FogChunk(this.myIn, i);
/* 102 */         return true;
/*     */       case 5120:
/* 104 */         readShadowBias();
/* 105 */         return true;
/*     */       case 5152:
/* 107 */         readShadowMapSize();
/* 108 */         return true;
/*     */       case 8962:
/* 110 */         this.fogOptions = new LayeredFogChunk(this.myIn, i);
/* 111 */         return true;
/*     */       case 8960:
/* 113 */         this.distanceQueue = new DistanceQueueChunk(this.myIn, i);
/* 114 */         return true;
/*     */       case 12288:
/* 116 */         skipSize(i.length);
/* 117 */         return true;
/*     */       case 1:
/* 119 */         skipSize(i.length);
/* 120 */         return true;
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readOConst() throws IOException {
/* 127 */     this.oConstPlanes = new Vector3f(this.myIn.readFloat(), this.myIn.readFloat(), this.myIn.readFloat());
/* 128 */     if (DEBUG || DEBUG_LIGHT) logger.info("Planes:" + this.oConstPlanes); 
/*     */   }
/*     */   
/*     */   private void readRayTraceBias() throws IOException {
/* 132 */     this.rayTraceBias = this.myIn.readFloat();
/* 133 */     if (DEBUG || DEBUG_LIGHT) logger.info("Raytrace bias:" + this.rayTraceBias); 
/*     */   }
/*     */   
/*     */   private void readShadowRange() throws IOException {
/* 137 */     this.shadowMapRange = this.myIn.readFloat();
/* 138 */     if (DEBUG || DEBUG_LIGHT) logger.info("Shadow map range:" + this.shadowMapRange); 
/*     */   }
/*     */   
/*     */   private void readMasterScale() throws IOException {
/* 142 */     this.masterScale = this.myIn.readFloat();
/* 143 */     if (DEBUG || DEBUG_LIGHT) logger.info("Master scale:" + this.masterScale); 
/*     */   }
/*     */   
/*     */   private void readMeshVersion() throws IOException {
/* 147 */     int i = this.myIn.readInt();
/* 148 */     if (DEBUG || DEBUG_LIGHT) logger.info("Mesh version:" + i); 
/*     */   }
/*     */   
/*     */   private void readShadowBias() throws IOException {
/* 152 */     this.shadowBias = this.myIn.readFloat();
/* 153 */     if (DEBUG || DEBUG_LIGHT) logger.info("Bias:" + this.shadowBias); 
/*     */   }
/*     */   
/*     */   private void readShadowMapSize() throws IOException {
/* 157 */     this.shadowMapSize = this.myIn.readShort();
/* 158 */     if (DEBUG || DEBUG_LIGHT) logger.info("Shadow map siz:" + this.shadowMapSize); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\model\converters\maxutils\CustomEditableObjectChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */