/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ import org.lwjgl.util.Display;
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
/*     */ public class DisplayResolutionHelper
/*     */ {
/*  22 */   public DisplayMode lowWindowResolution = null;
/*  23 */   public DisplayMode mediumWindowResolution = null;
/*  24 */   public DisplayMode highWindowResolution = null;
/*     */   
/*  26 */   public String[] resolutionList = null;
/*  27 */   ArrayList<String> resolutionArrayList = new ArrayList<String>();
/*     */   
/*  29 */   private static final Logger LOG = Logger.getLogger(DisplayModeHelper.class.getName());
/*  30 */   private double screenSizeAspectRatio = (Toolkit.getDefaultToolkit().getScreenSize()).height / (Toolkit.getDefaultToolkit().getScreenSize()).width;
/*  31 */   DisplayMode[] resolutions = new DisplayMode[0];
/*     */   
/*     */   private static DisplayResolutionHelper instance;
/*     */   
/*  35 */   public int currentUserDisplayChoice = 0;
/*  36 */   public String currentUserDisplayChoiceString = "";
/*     */ 
/*     */   
/*     */   public boolean firstTimeUserDisplayChoice = true;
/*     */   
/*  41 */   int MINIMUM_WIDTH = 1024;
/*  42 */   int MINIMUM_HEIGHT = 768;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DisplayResolutionHelper() {
/*     */     try {
/*  49 */       loadResolutions();
/*  50 */     } catch (LWJGLException e) {
/*  51 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  59 */     instance = new DisplayResolutionHelper();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DisplayResolutionHelper getInstance() {
/*  66 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean currentResolutionIsLow() {
/*  73 */     if ((Toolkit.getDefaultToolkit().getScreenSize()).width < this.MINIMUM_WIDTH || (Toolkit.getDefaultToolkit().getScreenSize()).height < this.MINIMUM_HEIGHT)
/*     */     {
/*     */ 
/*     */       
/*  77 */       return true;
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadResolutions() throws LWJGLException {
/*     */     try {
/*  89 */       this.resolutions = Display.getAvailableDisplayModes(this.MINIMUM_WIDTH, this.MINIMUM_HEIGHT, (Toolkit.getDefaultToolkit().getScreenSize()).width, (Toolkit.getDefaultToolkit().getScreenSize()).height, 32, 32, 50, 2147483647);
/*  90 */       sortResolutions();
/*  91 */       loadResolutionList();
/*  92 */       printResolutionList();
/*  93 */     } catch (LWJGLException e) {
/*  94 */       LOG.error("No resolutions, please upgrade graphics driver.");
/*     */     } 
/*     */     
/*  97 */     this.lowWindowResolution = getLowestResolution();
/*  98 */     printResolution("lowWindowResolution: ", this.lowWindowResolution);
/*     */     
/* 100 */     this.mediumWindowResolution = getMediumResolution();
/* 101 */     printResolution("mediumWindowResolution: ", this.mediumWindowResolution);
/*     */     
/* 103 */     this.highWindowResolution = getHighestResolution();
/* 104 */     printResolution("highWindowResolution: ", this.highWindowResolution);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadResolutionList() {
/* 111 */     String currentResolution = "";
/* 112 */     if (this.resolutions.length > 0) {
/* 113 */       for (int i = 0; i <= this.resolutions.length - 1; i++) {
/* 114 */         currentResolution = "" + this.resolutions[i].getWidth() + " x " + this.resolutions[i].getHeight();
/*     */         
/* 116 */         if (!this.resolutionArrayList.contains(currentResolution)) {
/* 117 */           this.resolutionArrayList.add(currentResolution);
/*     */         }
/*     */       } 
/*     */       
/* 121 */       this.resolutionList = new String[this.resolutionArrayList.size()];
/* 122 */       this.resolutionArrayList.toArray(this.resolutionList);
/*     */     } else {
/* 124 */       this.resolutionList = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printResolutionList() {
/* 133 */     if (this.resolutions.length > 0) {
/* 134 */       for (int i = 0; i <= this.resolutions.length - 1; i++) {
/* 135 */         System.out.println("resolution[" + i + "]: " + this.resolutions[i].getWidth() + ", " + this.resolutions[i].getHeight() + ", " + this.resolutions[i].getBitsPerPixel() + "Bpp, " + this.resolutions[i].getFrequency() + "Hz");
/*     */       }
/*     */     } else {
/* 138 */       System.out.println("no valid resolution found");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printResolution(String Message, DisplayMode resolution) {
/* 147 */     if (resolution != null) {
/* 148 */       System.out.println(Message + ", " + resolution.getWidth() + ", " + resolution.getHeight() + ", " + resolution.getBitsPerPixel() + "Bpp, " + resolution.getFrequency() + "Hz");
/*     */     } else {
/* 150 */       System.out.println(Message + "null");
/*     */     } 
/*     */   }
/*     */   
/*     */   public DisplayMode getBootResolution() {
/* 155 */     if (this.highWindowResolution != null) {
/* 156 */       return this.highWindowResolution;
/*     */     }
/*     */     
/* 159 */     if (this.mediumWindowResolution != null) {
/* 160 */       return this.mediumWindowResolution;
/*     */     }
/*     */     
/* 163 */     if (this.lowWindowResolution != null) {
/* 164 */       return this.lowWindowResolution;
/*     */     }
/*     */     
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayMode getLowestResolution() {
/* 174 */     double resolutionAspectRatio = 0.0D;
/* 175 */     DisplayMode resolution = null;
/* 176 */     boolean resolutionFound = false;
/* 177 */     double minParamsDiff = 1000.0D;
/* 178 */     double paramsDiff = 0.0D;
/*     */ 
/*     */     
/* 181 */     for (int i = this.resolutions.length - 1; i >= 0; i--) {
/* 182 */       resolutionAspectRatio = this.resolutions[i].getHeight() / this.resolutions[i].getWidth();
/*     */       
/* 184 */       paramsDiff = (Math.abs(resolutionAspectRatio - this.screenSizeAspectRatio) + i) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       if (paramsDiff <= minParamsDiff) {
/* 190 */         minParamsDiff = paramsDiff;
/*     */         
/* 192 */         resolution = this.resolutions[i];
/* 193 */         resolutionFound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (resolutionFound)
/* 198 */       return resolution; 
/* 199 */     if (this.resolutions.length > 0) {
/* 200 */       return this.resolutions[0];
/*     */     }
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayMode getMediumResolution() {
/* 210 */     double resolutionAspectRatio = 0.0D;
/* 211 */     DisplayMode resolution = null;
/* 212 */     boolean resolutionFound = false;
/*     */     
/* 214 */     double minParamsDiff = 1000.0D;
/* 215 */     double paramsDiff = 0.0D;
/*     */     
/* 217 */     for (int i = 0; i <= this.resolutions.length - 1; i++) {
/* 218 */       resolutionAspectRatio = this.resolutions[i].getHeight() / this.resolutions[i].getWidth();
/*     */       
/* 220 */       paramsDiff = (Math.abs(resolutionAspectRatio - this.screenSizeAspectRatio) + Math.abs(i - (this.resolutions.length - 1) / 2.0D)) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       if (paramsDiff <= minParamsDiff) {
/* 226 */         minParamsDiff = paramsDiff;
/*     */         
/* 228 */         resolution = this.resolutions[i];
/* 229 */         resolutionFound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     if (resolutionFound)
/* 234 */       return resolution; 
/* 235 */     if (this.resolutions.length > 0) {
/* 236 */       return this.resolutions[this.resolutions.length / 2];
/*     */     }
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayMode getHighestResolution() {
/* 246 */     double resolutionAspectRatio = 0.0D;
/* 247 */     DisplayMode resolution = null;
/* 248 */     boolean resolutionFound = false;
/* 249 */     double minParamsDiff = 1000.0D;
/* 250 */     double paramsDiff = 0.0D;
/*     */     
/* 252 */     for (int i = 0; i <= this.resolutions.length - 1; i++) {
/* 253 */       resolutionAspectRatio = this.resolutions[i].getHeight() / this.resolutions[i].getWidth();
/*     */       
/* 255 */       paramsDiff = (Math.abs(resolutionAspectRatio - this.screenSizeAspectRatio) + Math.abs(i - this.resolutions.length - 1)) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 260 */       if (paramsDiff <= minParamsDiff) {
/* 261 */         minParamsDiff = paramsDiff;
/* 262 */         resolution = this.resolutions[i];
/* 263 */         resolutionFound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (resolutionFound)
/* 268 */       return resolution; 
/* 269 */     if (this.resolutions.length > 0) {
/* 270 */       return this.resolutions[this.resolutions.length - 1];
/*     */     }
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayMode getCloserValidDisplayMode(int w, int h, int bpp, int freq) {
/* 282 */     double resolutionAspectRatio = 0.0D;
/* 283 */     DisplayMode resolution = null;
/* 284 */     boolean resolutionFound = false;
/*     */     
/* 286 */     double minParamsDiff = 1000.0D;
/* 287 */     double paramsDiff = 0.0D;
/*     */     
/* 289 */     double minAspectRatioParamsDiff = 1000.0D;
/* 290 */     double paramsAspectRatioDiff = 0.0D;
/*     */     
/* 292 */     for (int i = 0; i <= this.resolutions.length - 1; i++) {
/* 293 */       resolutionAspectRatio = this.resolutions[i].getHeight() / this.resolutions[i].getWidth();
/*     */ 
/*     */       
/* 296 */       paramsDiff = (Math.abs(this.resolutions[i].getWidth() - w) + Math.abs(this.resolutions[i].getHeight() - h) + Math.abs(this.resolutions[i].getBitsPerPixel() - bpp) + Math.abs(this.resolutions[i].getFrequency() - freq)) / 4.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (paramsDiff <= minParamsDiff) {
/* 307 */         minParamsDiff = paramsDiff;
/*     */ 
/*     */         
/* 310 */         paramsAspectRatioDiff = Math.abs(resolutionAspectRatio - this.screenSizeAspectRatio);
/* 311 */         if (paramsAspectRatioDiff <= minAspectRatioParamsDiff) {
/*     */           
/* 313 */           minAspectRatioParamsDiff = paramsAspectRatioDiff;
/* 314 */           resolution = this.resolutions[i];
/* 315 */           resolutionFound = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     if (resolutionFound) {
/* 321 */       System.out.println("resolution: " + resolution.getWidth() + ", " + resolution.getHeight() + ", " + resolution.getBitsPerPixel() + "Bpp, " + resolution.getFrequency() + "Hz");
/*     */       
/* 323 */       return resolution;
/*     */     } 
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortResolutions() {
/* 333 */     DisplayMode[] aux = (DisplayMode[])this.resolutions.clone();
/* 334 */     mergeSort(aux, this.resolutions, 0, this.resolutions.length, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeSort(DisplayMode[] src, DisplayMode[] dest, int low, int high, int off) {
/* 341 */     int length = high - low;
/*     */ 
/*     */     
/* 344 */     if (length < 7) {
/* 345 */       for (int j = low; j < high; j++) {
/* 346 */         int k = j;
/* 347 */         for (; k > low && compareTo(dest[k - 1], dest[k]) > 0; k--) {
/* 348 */           swap((Object[])dest, k, k - 1);
/*     */         }
/*     */       } 
/*     */       return;
/*     */     } 
/* 353 */     int destLow = low;
/* 354 */     int destHigh = high;
/* 355 */     low += off;
/* 356 */     high += off;
/* 357 */     int mid = low + high >>> 1;
/* 358 */     mergeSort(dest, src, low, mid, -off);
/* 359 */     mergeSort(dest, src, mid, high, -off);
/*     */ 
/*     */ 
/*     */     
/* 363 */     if (compareTo(src[mid - 1], src[mid]) <= 0) {
/* 364 */       System.arraycopy(src, low, dest, destLow, length);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 369 */     for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
/* 370 */       if (q >= high || (p < mid && compareTo(src[p], src[q]) <= 0)) {
/* 371 */         dest[i] = src[p++];
/*     */       } else {
/* 373 */         dest[i] = src[q++];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void swap(Object[] x, int a, int b) {
/* 384 */     Object t = x[a];
/* 385 */     x[a] = x[b];
/* 386 */     x[b] = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int compareTo(DisplayMode src, DisplayMode dst) {
/* 396 */     return src.getWidth() + src.getHeight() + src.getFrequency() - dst.getWidth() - dst.getHeight() - dst.getFrequency();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\DisplayResolutionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */