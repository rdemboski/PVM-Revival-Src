/*     */ package com.funcom.tcg.client;
import com.funcom.commons.MessageBox;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.system.GameSettings;
/*     */ import com.jme.system.JmeException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ 
/*     */ public class DisplayModeHelper {
/*     */   private static final int WANTED_WIDTH = 1024;
/*     */   
/*     */   public DisplayModeHelper(String rendererId) {
/*  18 */     this.rendererId = rendererId;
/*     */   }
/*     */   private static final int WANTED_HEIGHT = 768; private String rendererId;
/*     */   public DisplayMode[] prioritizeDisplayModes() {
/*     */     try {
/*  23 */       DisplayMode[] modes = Display.getAvailableDisplayModes();
/*  24 */       List<DisplayMode> sortedModes = new ArrayList<DisplayMode>(modes.length);
/*     */       
/*  26 */       DisplaySystem disp = DisplaySystem.getDisplaySystem(this.rendererId);
/*  27 */       for (DisplayMode mode : modes) {
/*  28 */         if (disp.isValidDisplayMode(mode.getWidth(), mode.getHeight(), mode.getBitsPerPixel(), mode.getFrequency()))
/*     */         {
/*  30 */           sortedModes.add(mode);
/*     */         }
/*     */       } 
/*     */       
/*  34 */       Collections.sort(sortedModes, new DisplayModeComparator());
/*     */       
/*  36 */       return sortedModes.<DisplayMode>toArray(new DisplayMode[sortedModes.size()]);
/*  37 */     } catch (LWJGLException e) {
/*  38 */       throw new RuntimeException("Cannot get available display modes", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayMode getBestDisplayMode() {
/*  45 */     DisplayMode[] modes = prioritizeDisplayModes();
/*     */     
/*  47 */     if (modes.length > 0) {
/*  48 */       return modes[0];
/*     */     }
/*     */     
/*  51 */     return null;
/*     */   }
/*     */   
/*     */   public boolean initDisplay(GameSettings settings) {
/*  55 */     if (settings.getWidth() < (DisplayResolutionHelper.getInstance()).MINIMUM_WIDTH && settings.getHeight() < (DisplayResolutionHelper.getInstance()).MINIMUM_HEIGHT) {
/*     */ 
/*     */ 
/*     */       
/*  59 */       MessageBox.ok(TcgGame.getLocalizedText("text.messagebox.lowresolution", new String[0]) + " " + (DisplayResolutionHelper.getInstance()).MINIMUM_WIDTH + " x " + (DisplayResolutionHelper.getInstance()).MINIMUM_HEIGHT, TcgGame.getLocalizedText("caption.messagebox.lowresolution", new String[0]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  67 */       return false;
/*     */     } 
/*     */     try {
/*  70 */       DisplaySystem display = DisplaySystem.getDisplaySystem();
/*  71 */       display.createWindow(settings.getWidth(), settings.getHeight(), settings.getDepth(), settings.getFrequency(), settings.isFullscreen());
/*  72 */       return true;
/*  73 */     } catch (JmeException ex) {
/*  74 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tryInitWithAllModes(GameSettings settings, JmeException originalException) {
/*  82 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/*  83 */     DisplayMode[] displayModes = prioritizeDisplayModes();
/*     */     
/*  85 */     for (DisplayMode displayMode : displayModes) {
/*     */ 
/*     */ 
/*     */       
/*  89 */       for (int i = 0; i < 4; i++) {
/*  90 */         int frequency; int bpp; if ((i & 0x1) != 0) {
/*  91 */           frequency = -1;
/*     */         } else {
/*  93 */           frequency = displayMode.getFrequency();
/*     */         } 
/*  95 */         if ((i & 0x2) != 0) {
/*  96 */           bpp = -1;
/*     */         } else {
/*  98 */           bpp = displayMode.getBitsPerPixel();
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 103 */           display.close();
/* 104 */         } catch (Exception ignore) {}
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 109 */           Thread.sleep(1000L);
/* 110 */         } catch (InterruptedException ignore) {}
/*     */ 
/*     */         
/*     */         try {
/* 114 */           display.createWindow(displayMode.getWidth(), displayMode.getHeight(), bpp, frequency, settings.isFullscreen());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 120 */             settings.setWidth(displayMode.getWidth());
/* 121 */             settings.setHeight(displayMode.getHeight());
/* 122 */             settings.setDepth(bpp);
/* 123 */             settings.setFrequency(frequency);
/* 124 */             settings.save();
/* 125 */           } catch (IOException e) {
/* 126 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           return;
/* 130 */         } catch (JmeException ignore) {
/* 131 */           System.err.println(ignore.toString() + ":" + " usedFrequency=" + frequency + " bpp=" + bpp + " width=" + displayMode.getWidth() + " height=" + displayMode.getHeight() + " fullscreen=" + settings.isFullscreen());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     throw new RuntimeException("Cannot init display", originalException);
/*     */   }
/*     */   
/*     */   private class DisplayModeComparator implements Comparator<DisplayMode> {
/*     */     private DisplayModeComparator() {}
/*     */     
/*     */     public int compare(DisplayMode mode1, DisplayMode mode2) {
/* 148 */       long diff_1 = calcDimensionScore(mode1);
/* 149 */       long diff_2 = calcDimensionScore(mode2);
/*     */       
/* 151 */       boolean modeOneCloserToSpec = (diff_1 < diff_2);
/* 152 */       boolean modeOneFartherFromSpec = (diff_1 > diff_2);
/*     */       
/* 154 */       if (modeOneCloserToSpec)
/* 155 */         return -1; 
/* 156 */       if (modeOneFartherFromSpec) {
/* 157 */         return 1;
/*     */       }
/* 159 */       if (mode1.getBitsPerPixel() > mode2.getBitsPerPixel())
/* 160 */         return -1; 
/* 161 */       if (mode1.getBitsPerPixel() < mode2.getBitsPerPixel()) {
/* 162 */         return 1;
/*     */       }
/*     */ 
/*     */       
/* 166 */       return 0;
/*     */     }
/*     */     
/*     */     private long calcDimensionScore(DisplayMode o1) {
/* 170 */       long diffW = (o1.getWidth() - 1024);
/* 171 */       long diffH = (o1.getHeight() - 768);
/* 172 */       return diffW * diffW + diffH * diffH;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\DisplayModeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */