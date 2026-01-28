/*     */ package com.funcom.tcg.client.ui;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.CameraConfig;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.bss.BParsingStyleSheet;
/*     */ import com.jmex.bui.bss.BStyleSheetParser;
/*     */ import com.jmex.bui.bss.DefaultBStyleSheetParser;
/*     */ import com.jmex.bui.parser.ImageParser;
/*     */ import com.jmex.bui.parser.ProgressBarParser;
/*     */ import com.jmex.bui.parser.StarProgressParser;
/*     */ import com.jmex.bui.parser.TieredButtonParser;
/*     */ import com.jmex.bui.parser.TooltipWindowParser;
/*     */ import com.jmex.bui.provider.ResourceProvider;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
import java.util.List;
/*     */ 
/*     */ public abstract class BuiUtils {
/*  27 */   private static List<BStyleSheetParser> parsers = new ArrayList<BStyleSheetParser>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BStyleSheet createMergedClassStyleSheets(Class<?> clazz, BananaResourceProvider buiResourceProvider) {
/*  35 */     StringBuilder buf = new StringBuilder();
/*     */     
/*  37 */     appendClassStyleSheet(clazz, buf);
/*     */     
/*  39 */     return (BStyleSheet)new BParsingStyleSheet(new StringReader(buf.toString()), (ResourceProvider)buiResourceProvider, parsers);
/*     */   }
/*     */   
/*     */   public static BStyleSheet createStyleSheet(String path, BananaResourceProvider buiResourceProvider) {
/*  43 */     StringBuilder buf = new StringBuilder();
/*  44 */     InputStream sheetStream = BuiUtils.class.getResourceAsStream(path);
/*  45 */     if (sheetStream != null) {
/*     */       
/*     */       try {
/*  48 */         char[] dataBuf = new char[1024];
/*  49 */         InputStreamReader in = new InputStreamReader(sheetStream); int read;
/*  50 */         while ((read = in.read(dataBuf)) >= 0) {
/*  51 */           buf.append(dataBuf, 0, read);
/*     */         }
/*  53 */         in.close();
/*  54 */       } catch (IOException e) {
/*  55 */         throw new RuntimeException("cannot load class stylesheet: " + path, e);
/*     */       } 
/*     */     }
/*     */     
/*  59 */     return (BStyleSheet)new BParsingStyleSheet(new StringReader(buf.toString()), (ResourceProvider)buiResourceProvider, parsers);
/*     */   }
/*     */   
/*     */   public static void addStyleSheetParser(BStyleSheetParser parser) {
/*  63 */     parsers.add(parser);
/*     */   }
/*     */   
/*     */   private static void appendClassStyleSheet(Class<?> aClass, StringBuilder stringBuf) {
/*  67 */     if (aClass == BWindow.class) {
/*     */       return;
/*     */     }
/*  70 */     appendClassStyleSheet(aClass.getSuperclass(), stringBuf);
/*     */     
/*  72 */     InputStream sheetStream = aClass.getResourceAsStream(aClass.getSimpleName() + ".bss");
/*  73 */     if (sheetStream != null) {
/*     */       
/*     */       try {
/*  76 */         char[] dataBuf = new char[1024];
/*  77 */         InputStreamReader in = new InputStreamReader(sheetStream); int read;
/*  78 */         while ((read = in.read(dataBuf)) >= 0) {
/*  79 */           stringBuf.append(dataBuf, 0, read);
/*     */         }
/*  81 */         in.close();
/*  82 */       } catch (IOException e) {
/*  83 */         throw new RuntimeException("cannot load class stylesheet: " + aClass, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static BComponent getNextFocus(BContainer parent, BComponent current) {
/*  89 */     int size = parent.getComponentCount();
/*  90 */     int currentIndex = parent.getComponentIndex(current);
/*     */     
/*  92 */     for (int i = 0; i < size - 1; i++) {
/*  93 */       currentIndex++;
/*  94 */       currentIndex %= size;
/*     */       
/*  96 */       BComponent child = parent.getComponent(currentIndex);
/*  97 */       if (child.acceptsFocus()) {
/*  98 */         return child;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return current;
/*     */   }
/*     */   
/*     */   public static void initDefaultStyleSheetParsers() {
/* 106 */     addStyleSheetParser((BStyleSheetParser)new DefaultBStyleSheetParser());
/* 107 */     addStyleSheetParser((BStyleSheetParser)new ProgressBarParser());
/* 108 */     addStyleSheetParser((BStyleSheetParser)new StarProgressParser());
/* 109 */     addStyleSheetParser((BStyleSheetParser)new TieredButtonParser());
/* 110 */     addStyleSheetParser((BStyleSheetParser)new ImageParser());
/* 111 */     addStyleSheetParser((BStyleSheetParser)new TooltipWindowParser());
/*     */   }
/*     */   
/*     */   public static void workaroundForParticleFacing() {
/* 115 */     Camera camera = DisplaySystem.getDisplaySystem().getRenderer().getCamera();
/* 116 */     CameraConfig.instance().setCamera(camera);
/* 117 */     CameraConfig.instance().moveCameraTo(new WorldCoordinate());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BuiUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */