/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.image.Image;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IrregularLabel
/*     */   extends BLabel
/*     */ {
/*     */   private boolean highlighted = false;
/*     */   
/*     */   public IrregularLabel(String text) {
/*  27 */     super(text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  33 */     if (isVisible() && mx >= this._x && my >= this._y && mx < this._x + this._width && my < this._y + this._height && !outerAlphaHit(mx, my))
/*     */     {
/*  35 */       return (BComponent)this;
/*     */     }
/*  37 */     return null;
/*     */   }
/*     */   
/*     */   private boolean outerAlphaHit(int mx, int my) {
/*  41 */     BImage bImage = getBackgroundImage();
/*  42 */     if (bImage == null) {
/*  43 */       return false;
/*     */     }
/*  45 */     TextureState texState = (TextureState)bImage.getRenderState(RenderState.StateType.Texture);
/*  46 */     Image image = texState.getTexture().getImage();
/*  47 */     ByteBuffer bBuffer = image.getData(0);
/*  48 */     bBuffer.rewind();
/*     */     
/*  50 */     Point relativeMouseCoords = computeRelativeMouseCoords(mx, my);
/*  51 */     int imageX = relativeMouseCoords.x * bImage.getImageWidth() / this._width;
/*  52 */     int imageY = relativeMouseCoords.y * bImage.getImageHeight() / this._height;
/*     */     
/*  54 */     Image.Format format = image.getFormat();
/*  55 */     if (format == Image.Format.NativeDXT1 || format == Image.Format.NativeDXT1A)
/*  56 */       return false; 
/*  57 */     if (format == Image.Format.NativeDXT5) {
/*  58 */       int texelX = imageX / 4;
/*  59 */       int texelY = imageY / 4;
/*  60 */       int texwidth = image.getWidth() / 4 + ((image.getWidth() % 4 == 0) ? 0 : 1);
/*  61 */       int texPos = texelX + texelY * texwidth;
/*  62 */       int bufStart = texPos * 16;
/*  63 */       bBuffer.position(bufStart);
/*  64 */       int alpha0 = bBuffer.get() & 0xFF;
/*  65 */       int alpha1 = bBuffer.get() & 0xFF;
/*  66 */       if (alpha0 > 0 && alpha1 > 0)
/*  67 */         return false; 
/*  68 */       if (alpha0 == 0 && alpha1 == 0) {
/*  69 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       int offX = imageX % 4;
/*  78 */       int offY = imageY % 4;
/*     */       
/*  80 */       int bitOff = (offX + offY * 4) * 3;
/*  81 */       while (bitOff > 7) {
/*  82 */         bBuffer.get();
/*  83 */         bitOff -= 8;
/*     */       } 
/*     */       
/*  86 */       int curOff = bitOff;
/*  87 */       byte b = bBuffer.get();
/*  88 */       int code = 0;
/*  89 */       for (int i = 0; i < 3; i++) {
/*  90 */         if (0 != (b & (int)Math.pow(2.0D, curOff))) {
/*  91 */           code = (int)(code + Math.pow(2.0D, (3 - i + 1)));
/*     */         }
/*  93 */         curOff++;
/*  94 */         if (i < 2 && curOff > 7) {
/*  95 */           b = bBuffer.get();
/*  96 */           curOff -= 8;
/*     */         } 
/*     */       } 
/*  99 */       if (code == 0)
/* 100 */         return (alpha0 == 0); 
/* 101 */       if (code == 1)
/* 102 */         return (alpha1 == 0); 
/* 103 */       if (alpha0 <= alpha1) {
/* 104 */         if (code == 6)
/* 105 */           return true; 
/* 106 */         if (code == 7) {
/* 107 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       return false;
/*     */     } 
/* 116 */     int bufferPos = (imageX + imageY * image.getWidth()) * 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (bufferPos < 0 || bufferPos > bBuffer.limit() - 4) {
/* 122 */       return true;
/*     */     }
/*     */     
/* 125 */     bBuffer.position(bufferPos);
/* 126 */     ColorRGBA color = new ColorRGBA((bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     return (color.a == 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private Point computeRelativeMouseCoords(int mx, int my) {
/* 138 */     int relativeX = mx - getX();
/* 139 */     int relativeY = my - getY();
/* 140 */     return new Point(relativeX, relativeY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BImage getBackgroundImage() {
/* 150 */     BBackground background = getBackground();
/*     */     
/* 152 */     if (this.highlighted) {
/* 153 */       background = this._backgrounds[1];
/*     */     }
/*     */     
/* 156 */     if (background == null) {
/* 157 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 161 */       Field field = ImageBackground.class.getDeclaredField("_image");
/* 162 */       field.setAccessible(true);
/* 163 */       BImage image = (BImage)field.get(background);
/* 164 */       if (image == null) {
/* 165 */         return null;
/*     */       }
/*     */       
/* 168 */       ImageBackground.class.getDeclaredField("_image").setAccessible(false);
/*     */       
/* 170 */       return image;
/* 171 */     } catch (NoSuchFieldException e) {
/* 172 */       throw new IllegalStateException(e);
/* 173 */     } catch (IllegalAccessException e) {
/* 174 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isHighlighted() {
/* 179 */     return this.highlighted;
/*     */   }
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/* 183 */     this.highlighted = highlighted;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\IrregularLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */