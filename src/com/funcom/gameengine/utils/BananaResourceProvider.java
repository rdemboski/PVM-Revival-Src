/*     */ package com.funcom.gameengine.utils;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jme.image.Texture;
/*     */ import com.jmex.bui.BCursor;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.TcgBitmapTextFactory;
/*     */ import com.jmex.bui.provider.ResourceProvider;
/*     */ import com.jmex.bui.text.AWTTextFactory;
/*     */ import com.jmex.bui.text.BTextFactory;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BananaResourceProvider
/*     */   implements ResourceProvider
/*     */ {
/*     */   private static final String TEXTUREFONT_PREFIX = "texturefont://";
/*     */   private final ResourceManager resourceManager;
/*     */   private final Map<String, BTextFactory> bitmapTextFactories;
/*     */   
/*     */   public BananaResourceProvider(ResourceManager resourceManager) {
/*  31 */     this.resourceManager = resourceManager;
/*     */     
/*  33 */     this.bitmapTextFactories = new HashMap<String, BTextFactory>();
/*     */   }
/*     */ 
/*     */   
/*     */   public BTextFactory createTextFactory(String family, String style, int size) {
/*  38 */     String cacheKey = family + ":" + style + ":" + size;
/*  39 */     BTextFactory textFactory = this.bitmapTextFactories.get(cacheKey);
/*     */     
/*  41 */     if (textFactory == null) {
/*  42 */       if (family.startsWith("texturefont://")) {
/*  43 */         textFactory = getBitmapFactory(family, size);
/*     */       } else {
/*  45 */         textFactory = getAWTFactory(family, style, size);
/*     */       } 
/*     */       
/*  48 */       this.bitmapTextFactories.put(cacheKey, textFactory);
/*     */     } 
/*     */     
/*  51 */     return textFactory;
/*     */   }
/*     */   
/*     */   private BTextFactory getBitmapFactory(String family, int size) {
/*  55 */     String resourcePath = family.substring("texturefont://".length());
/*     */ 
/*     */     
/*  58 */     Map<Object, Object> props = new HashMap<Object, Object>();
/*  59 */     props.put(Texture.MinificationFilter.class, Texture.MinificationFilter.BilinearNoMipMaps);
/*  60 */     props.put(Texture.MagnificationFilter.class, Texture.MagnificationFilter.Bilinear);
/*     */     
/*  62 */     Texture fontTexture = (Texture)this.resourceManager.getResource(Texture.class, resourcePath, CacheType.CACHE_PERMANENTLY, props);
/*  63 */     TcgBitmapTextFactory bitmapTextFactory = new TcgBitmapTextFactory(fontTexture, size);
/*  64 */     return (BTextFactory)bitmapTextFactory;
/*     */   }
/*     */   
/*     */   private BTextFactory getAWTFactory(String family, String style, int size) {
/*  68 */     int nstyle = 0;
/*  69 */     if (style.equals("bold")) {
/*  70 */       nstyle = 1;
/*  71 */     } else if (style.equals("italic")) {
/*  72 */       nstyle = 2;
/*  73 */     } else if (style.equals("bolditalic")) {
/*  74 */       nstyle = 3;
/*     */     } 
/*     */     
/*  77 */     StringTokenizer advancedDataTok = new StringTokenizer(family, "|");
/*  78 */     family = advancedDataTok.nextToken();
/*  79 */     Font font = (Font)this.resourceManager.getResource(Font.class, family, CacheType.CACHE_PERMANENTLY);
/*     */     
/*  81 */     AWTTextFactory awtTextFactory = new AWTTextFactory(font.deriveFont(nstyle, size), true);
/*  82 */     awtTextFactory.setColorGradientPercent(Float.valueOf(0.8F));
/*  83 */     awtTextFactory.setEffectGradientPercent(Float.valueOf(0.75F));
/*     */     
/*  85 */     if (advancedDataTok.hasMoreTokens()) {
/*  86 */       String type = advancedDataTok.nextToken();
/*  87 */       if ("gradient".equalsIgnoreCase(type)) {
/*  88 */         configureGradient(advancedDataTok, awtTextFactory);
/*     */       } else {
/*  90 */         throw new IllegalArgumentException("unknown advanced font format type: " + type);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return (BTextFactory)awtTextFactory;
/*     */   }
/*     */   
/*     */   private void configureGradient(StringTokenizer advancedDataTok, AWTTextFactory awtTextFactory) {
/*  98 */     String gradientData = advancedDataTok.nextToken();
/*  99 */     StringTokenizer tok = new StringTokenizer(gradientData, ", ");
/* 100 */     List<Float> fractions = new ArrayList<Float>();
/* 101 */     List<Color> colors = new ArrayList<Color>();
/* 102 */     while (tok.hasMoreTokens()) {
/* 103 */       fractions.add(Float.valueOf(Float.parseFloat(tok.nextToken())));
/* 104 */       colors.add(new Color(Integer.parseInt(tok.nextToken(), 16)));
/*     */     } 
/* 106 */     float[] fractionsArr = new float[fractions.size()];
/* 107 */     for (int i = 0, fractionsSize = fractions.size(); i < fractionsSize; i++) {
/* 108 */       fractionsArr[i] = ((Float)fractions.get(i)).floatValue();
/*     */     }
/* 110 */     awtTextFactory.setLinearGradientFractions(fractionsArr);
/* 111 */     awtTextFactory.setLinearGradientColors(colors.<Color>toArray(new Color[colors.size()]));
/*     */   }
/*     */ 
/*     */   
/*     */   public BImage loadImage(String path) throws IOException {
/* 116 */     return (BImage)this.resourceManager.getResource(BImage.class, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BCursor loadCursor(String name) throws IOException {
/* 124 */     throw new IllegalStateException("WAITING TO BE IMPLEMENTED!");
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\BananaResourceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */