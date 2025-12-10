/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jmex.bui.enumeratedConstants.TextEffect;
/*    */ import com.jmex.bui.text.AWTTextFactory;
/*    */ import com.jmex.bui.text.BText;
/*    */ import java.awt.Font;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class InGameFontFactory
/*    */ {
/* 15 */   private final Map<Integer, AWTTextFactory> awtTextFactories = new HashMap<Integer, AWTTextFactory>();
/*    */   private final ResourceManager resourceManager;
/*    */   private final String fontName;
/*    */   
/*    */   public InGameFontFactory(ResourceManager resourceManager, String fontName) {
/* 20 */     this.resourceManager = resourceManager;
/* 21 */     this.fontName = fontName;
/*    */   }
/*    */   
/*    */   public TextSpatial createText(String text, ColorRGBA color, float scale) {
/* 25 */     AWTTextFactory awtTextFactory = getFactory(scale);
/*    */     
/* 27 */     int effectSize = (int)scale;
/* 28 */     if (effectSize <= 1) {
/* 29 */       effectSize = 2;
/*    */     }
/* 31 */     BText text1 = awtTextFactory.createText(text, color, TextEffect.OUTLINE, effectSize, ColorRGBA.black, false);
/*    */     
/* 33 */     TextSpatial textSpatial = new TextSpatial(text1);
/*    */     
/* 35 */     return textSpatial;
/*    */   }
/*    */   
/*    */   private AWTTextFactory getFactory(float scale) {
/* 39 */     int fontSize = (int)(13.0F * scale);
/* 40 */     if (fontSize < 1) {
/* 41 */       fontSize = 1;
/*    */     }
/* 43 */     AWTTextFactory awtTextFactory = this.awtTextFactories.get(Integer.valueOf(fontSize));
/* 44 */     if (awtTextFactory == null) {
/* 45 */       Font font = (Font)this.resourceManager.getResource(Font.class, this.fontName, CacheType.CACHE_PERMANENTLY);
/*    */       
/* 47 */       awtTextFactory = new AWTTextFactory(font.deriveFont(1, fontSize), true);
/* 48 */       awtTextFactory.setColorGradientPercent(Float.valueOf(0.6F));
/* 49 */       awtTextFactory.setEffectGradientPercent(Float.valueOf(0.75F));
/* 50 */       this.awtTextFactories.put(Integer.valueOf(fontSize), awtTextFactory);
/*    */     } 
/* 52 */     return awtTextFactory;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\InGameFontFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */