/*     */ package com.jmex.bui.parser;
/*     */ 
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.bss.BStyleSheetParser;
/*     */ import com.jmex.bui.bss.BStyleSheetParsingUtil;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.property.BackgroundProperty;
/*     */ import com.jmex.bui.property.Property;
/*     */ import com.jmex.bui.provider.ResourceProvider;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProgressBarParser
/*     */   implements BStyleSheetParser
/*     */ {
/*     */   public Object parse(String name, ArrayList args, BStyleSheet styleSheet) {
/*  24 */     if (name.equals("overlay_list")) {
/*  25 */       List<String> images = new ArrayList<String>();
/*     */       
/*  27 */       for (Object arg : args) {
/*  28 */         images.add((String)arg);
/*     */       }
/*  30 */       return new ImageListProperty(images);
/*  31 */     }  if (name.equals("progress_list") || name.equals("active_progress_list"))
/*  32 */       return handleProgressList(args); 
/*  33 */     if (name.equals("background_list")) {
/*  34 */       return handleBackground(args);
/*     */     }
/*     */     
/*  37 */     throw new IllegalArgumentException("Unknown property '" + name + "'");
/*     */   }
/*     */   
/*     */   private Object handleBackground(ArrayList<String> args) {
/*  41 */     List<BackgroundProperty> backgroundList = new ArrayList<BackgroundProperty>();
/*  42 */     for (int i = 0; i < args.size(); i++) {
/*  43 */       BackgroundProperty bprop = new BackgroundProperty();
/*  44 */       bprop.type = args.get(i);
/*  45 */       if (bprop.type.equals("solid")) {
/*  46 */         bprop.color = BStyleSheetParsingUtil.parseColor(args.get(1));
/*  47 */       } else if (bprop.type.equals("image")) {
/*  48 */         i++;
/*  49 */         bprop.ipath = args.get(i);
/*  50 */         if (i + 1 < args.size() && args.get(i + 1) instanceof String) {
/*     */           try {
/*  52 */             String scaleModeStr = args.get(i + 1);
/*  53 */             bprop.scaleMode = ImageBackgroundMode.fromStylesheetAttributeString(scaleModeStr);
/*  54 */             i++;
/*  55 */             if (bprop.scaleMode == ImageBackgroundMode.FRAME_XY && i + 1 < args.size() && args.get(i + 1) instanceof Double) {
/*  56 */               i = handleInsets(args, i, bprop, args.get(i + 1));
/*     */             }
/*  58 */           } catch (IllegalArgumentException e) {}
/*     */         
/*     */         }
/*     */       }
/*  62 */       else if (!bprop.type.equals("blank")) {
/*     */ 
/*     */ 
/*     */         
/*  66 */         throw new IllegalArgumentException("Unknown background type: '" + bprop.type + "'");
/*     */       } 
/*     */       
/*  69 */       backgroundList.add(bprop);
/*     */     } 
/*  71 */     return new BackgroundListProperty(backgroundList);
/*     */   }
/*     */   
/*     */   private Object handleProgressList(ArrayList<String> args) {
/*  75 */     List<ProgressBackgroundProperty> progressList = new ArrayList<ProgressBackgroundProperty>();
/*  76 */     for (int i = 0; i < args.size(); i++) {
/*  77 */       ProgressBackgroundProperty progress = new ProgressBackgroundProperty();
/*  78 */       progress.ipath = args.get(i);
/*  79 */       progressList.add(progress);
/*  80 */       if (args.size() <= i + 1)
/*     */         break; 
/*  82 */       Object value = args.get(i + 1);
/*  83 */       if (value instanceof String) {
/*     */         try {
/*  85 */           progress.scaleMode = ImageBackgroundMode.fromStylesheetAttributeString((String)value);
/*  86 */           i++;
/*     */           
/*  88 */           if (args.size() <= i + 1) {
/*     */             break;
/*     */           }
/*  91 */           Object value2 = args.get(i + 1);
/*  92 */           if (progress.scaleMode == ImageBackgroundMode.FRAME_XY && value2 instanceof Double) {
/*  93 */             i = handleInsets(args, i, progress, value2);
/*     */           }
/*     */         }
/*  96 */         catch (IllegalArgumentException e) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 101 */     return new ProgressListProperty(progressList);
/*     */   }
/*     */ 
/*     */   
/*     */   private int handleInsets(ArrayList args, int i, BackgroundProperty progress, Object value2) {
/* 106 */     progress.frame = new Insets();
/* 107 */     progress.frame.top = BStyleSheetParsingUtil.parseInt(value2);
/* 108 */     i++;
/* 109 */     if (args.size() <= i + 1 || !(args.get(i + 1) instanceof Double)) {
/* 110 */       progress.frame.right = progress.frame.top;
/* 111 */       progress.frame.bottom = progress.frame.top;
/* 112 */       progress.frame.left = progress.frame.right;
/*     */     } else {
/* 114 */       progress.frame.right = BStyleSheetParsingUtil.parseInt(args.get(i + 1));
/* 115 */       i++;
/* 116 */       if (args.size() <= i + 1 || !(args.get(i + 1) instanceof Double)) {
/* 117 */         progress.frame.bottom = progress.frame.top;
/* 118 */         progress.frame.left = progress.frame.right;
/*     */       } else {
/* 120 */         progress.frame.bottom = BStyleSheetParsingUtil.parseInt(args.get(i + 1));
/* 121 */         i++;
/* 122 */         if (args.size() <= i + 1 || !(args.get(i + 1) instanceof Double)) {
/* 123 */           progress.frame.left = progress.frame.right;
/*     */         } else {
/* 125 */           progress.frame.left = BStyleSheetParsingUtil.parseInt(args.get(i + 1));
/* 126 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 130 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canParse(String name) {
/* 135 */     return (name.equals("overlay_list") || name.equals("progress_list") || name.equals("background_list") || name.equals("active_progress_list"));
/*     */   }
/*     */   
/*     */   private class ImageListProperty extends Property implements ImageExtractable {
/*     */     private List<String> imageList;
/*     */     
/*     */     public ImageListProperty(List<String> imageList) {
/* 142 */       this.imageList = imageList;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object resolve(ResourceProvider rsrcprov) {
/* 147 */       List<BImage> images = new ArrayList<BImage>();
/* 148 */       for (String imagePath : this.imageList) {
/*     */         try {
/* 150 */           BImage image = rsrcprov.loadImage(imagePath);
/* 151 */           images.add(image);
/* 152 */         } catch (IOException ioe) {
/* 153 */           System.err.println("Failed to load icon image '" + imagePath + "': " + ioe);
/*     */         } 
/*     */       } 
/* 156 */       return images;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getImages() {
/* 161 */       return this.imageList;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ProgressListProperty extends Property implements ImageExtractable {
/*     */     private List<ProgressBackgroundProperty> progressList;
/*     */     
/*     */     public ProgressListProperty(List<ProgressBackgroundProperty> imageList) {
/* 169 */       this.progressList = imageList;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object resolve(ResourceProvider rsrcprov) {
/* 174 */       return this.progressList;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getImages() {
/* 179 */       ArrayList<String> imagesList = new ArrayList<String>();
/* 180 */       for (BackgroundProperty property : this.progressList) {
/* 181 */         if (property.ipath != null && !property.ipath.isEmpty())
/* 182 */           imagesList.add(property.ipath); 
/* 183 */       }  return imagesList;
/*     */     }
/*     */   }
/*     */   
/*     */   private class BackgroundListProperty extends Property implements ImageExtractable {
/*     */     private List<BackgroundProperty> backgroundList;
/*     */     
/*     */     public BackgroundListProperty(List<BackgroundProperty> backgroundList) {
/* 191 */       this.backgroundList = backgroundList;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object resolve(ResourceProvider rsrcprov) {
/* 196 */       List<BBackground> backgrounds = new ArrayList<BBackground>();
/* 197 */       for (BackgroundProperty property : this.backgroundList)
/* 198 */         backgrounds.add((BBackground)property.resolve(rsrcprov)); 
/* 199 */       return backgrounds;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getImages() {
/* 204 */       ArrayList<String> imagesList = new ArrayList<String>();
/* 205 */       for (BackgroundProperty property : this.backgroundList) {
/* 206 */         if (property.ipath != null && !property.ipath.isEmpty())
/* 207 */           imagesList.add(property.ipath); 
/* 208 */       }  return imagesList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\ProgressBarParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */