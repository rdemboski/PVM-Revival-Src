/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BTextComponent;
/*     */ import com.jmex.bui.parser.ImageProperty;
/*     */ import com.jmex.bui.text.BText;
/*     */ import com.jmex.bui.text.BTextFactory;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ public abstract class ScalingSupportLabel
/*     */   extends BTextComponent
/*     */ {
/*     */   private static final String OVERLAY_LABEL = "icon-overlay";
/*     */   private static final String PROP_IMAGE = "image";
/*     */   protected final ResourceManager resourceManager;
/*     */   protected boolean added;
/*     */   private BImage icon;
/*     */   private BImage iconOverlay;
/*     */   private BText[] bTexts;
/*     */   private String text;
/*     */   
/*     */   public ScalingSupportLabel(ResourceManager resourceManager) {
/*  29 */     this.resourceManager = resourceManager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  34 */     super.configureStyle(style);
/*  35 */     ImageProperty imageProperty = (ImageProperty)style.getProperty("icon-overlay", "image");
/*  36 */     if (imageProperty != null) {
/*  37 */       String overlayImagePath = imageProperty.getImages().get(0);
/*  38 */       this.iconOverlay = (BImage)this.resourceManager.getResource(BImage.class, overlayImagePath);
/*     */     } else {
/*  40 */       throw new IllegalStateException("missing property: styleclass=icon-overlay prop=image");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  46 */     return this.text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  51 */     if (this.added && 
/*  52 */       this.bTexts != null) {
/*  53 */       for (BText bText : this.bTexts) {
/*  54 */         bText.wasRemoved();
/*     */       }
/*  56 */       this.bTexts = null;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     this.text = text;
/*     */   }
/*     */   
/*     */   public void setIcon(BImage icon) {
/*  64 */     if (this.added) {
/*  65 */       if (this.icon != null) {
/*  66 */         this.icon.release();
/*     */       }
/*  68 */       if (icon != null) {
/*  69 */         icon.reference();
/*     */       }
/*     */     } 
/*     */     
/*  73 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   protected void renderText(Renderer renderer, Point pos, float scale, boolean centerHorizontal, boolean multiLine) {
/*  77 */     makeBText(multiLine);
/*     */     
/*  79 */     if (this.bTexts != null) {
/*  80 */       int textHeight = (int)(getTotalHeight(this.bTexts) * scale);
/*     */       
/*  82 */       int y = pos.y - textHeight / 2;
/*  83 */       for (int i = this.bTexts.length - 1; i >= 0; i--) {
/*  84 */         BText bText = this.bTexts[i];
/*  85 */         int x = pos.x;
/*     */         
/*  87 */         Dimension textSize = bText.getSize();
/*  88 */         int targetWidth = (int)(textSize.width * scale);
/*  89 */         int targetHeight = (int)(textSize.height * scale);
/*  90 */         if (centerHorizontal) {
/*  91 */           x = pos.x - targetWidth / 2;
/*     */         }
/*     */         
/*  94 */         int flippedY = getHeight() - 1 - targetHeight - y;
/*  95 */         bText.render(renderer, x, flippedY, targetWidth, targetHeight, getAlpha());
/*     */         
/*  97 */         y += targetHeight;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean renderIcon(Renderer renderer, boolean renderOverlay, Point pos, int size) {
/* 103 */     if (this.icon != null) {
/* 104 */       int targetY = getHeight() - size - 1 - pos.y;
/* 105 */       this.icon.render(renderer, pos.x, targetY, size, size, getAlpha());
/*     */       
/* 107 */       if (this.iconOverlay != null && renderOverlay) {
/* 108 */         this.iconOverlay.render(renderer, pos.x, targetY, size + 1, size + 1, getAlpha());
/*     */ 
/*     */ 
/*     */         
/* 112 */         this.iconOverlay.resize(this.iconOverlay.getImageWidth(), this.iconOverlay.getImageHeight());
/*     */       } 
/*     */       
/* 115 */       return true;
/*     */     } 
/* 117 */     return false;
/*     */   }
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
/*     */   private int getTotalHeight(BText[] bTexts) {
/* 132 */     int textHeight = 0;
/* 133 */     for (BText bText : bTexts) {
/* 134 */       textHeight += (bText.getSize()).height;
/*     */     }
/* 136 */     return textHeight;
/*     */   }
/*     */   
/*     */   protected int getIconSize() {
/* 140 */     Insets insets = getInsets();
/*     */     
/* 142 */     int w = getWidth() - insets.getHorizontal();
/* 143 */     int h = getHeight() - insets.getVertical();
/* 144 */     int size = Math.min(w, h);
/* 145 */     return size;
/*     */   }
/*     */   
/*     */   private void makeBText(boolean multiLine) {
/* 149 */     if (this.bTexts == null && getText() != null) {
/* 150 */       int size = getIconSize();
/* 151 */       Insets insets = getInsets();
/* 152 */       int maxWidth = getWidth() - insets.getHorizontal() - size;
/*     */ 
/*     */       
/* 155 */       BTextFactory factory = getTextFactory();
/* 156 */       if (multiLine) {
/* 157 */         this.bTexts = factory.wrapText(getText(), getColor(), getTextEffect(), getEffectSize(), getEffectColor(), maxWidth);
/*     */       } else {
/* 159 */         this.bTexts = new BText[1];
/* 160 */         this.bTexts[0] = factory.createText(getText(), getColor(), getTextEffect(), getEffectSize(), getEffectColor(), false);
/*     */       } 
/*     */       
/* 163 */       for (BText bText : this.bTexts) {
/* 164 */         bText.wasAdded();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 171 */     this.added = true;
/* 172 */     super.wasAdded();
/* 173 */     if (this.icon != null) {
/* 174 */       this.icon.reference();
/*     */     }
/* 176 */     if (this.iconOverlay != null) {
/* 177 */       this.iconOverlay.reference();
/*     */     }
/* 179 */     if (this.bTexts != null) {
/* 180 */       for (BText bText : this.bTexts) {
/* 181 */         bText.wasAdded();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/* 188 */     if (this.bTexts != null) {
/* 189 */       for (BText bText : this.bTexts) {
/* 190 */         bText.wasRemoved();
/*     */       }
/*     */     }
/* 193 */     if (this.iconOverlay != null) {
/* 194 */       this.iconOverlay.release();
/*     */     }
/* 196 */     if (this.icon != null) {
/* 197 */       this.icon.release();
/*     */     }
/* 199 */     super.wasRemoved();
/* 200 */     this.added = false;
/*     */   }
/*     */   
/*     */   protected Point newRenderPos() {
/* 204 */     Point pos = new Point();
/* 205 */     Insets insets = getInsets();
/* 206 */     pos.x = insets.left;
/* 207 */     pos.y = insets.top;
/* 208 */     return pos;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\ScalingSupportLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */