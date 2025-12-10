/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.image.Image;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IrregularButton
/*     */   extends BButton
/*     */ {
/*     */   public IrregularButton(String text) {
/*  23 */     super(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  28 */     if (isVisible() && mx >= this._x && my >= this._y && mx < this._x + this._width && my < this._y + this._height && !outerAlphaHit(mx, my))
/*     */     {
/*  30 */       return (BComponent)this;
/*     */     }
/*  32 */     return null;
/*     */   }
/*     */   
/*     */   private boolean outerAlphaHit(int mx, int my) {
/*  36 */     BImage bImage = getBackgroundImage();
/*  37 */     if (bImage == null) {
/*  38 */       return false;
/*     */     }
/*  40 */     TextureState texState = (TextureState)bImage.getRenderState(RenderState.StateType.Texture);
/*  41 */     Image image = texState.getTexture().getImage();
/*  42 */     ByteBuffer bBuffer = image.getData(0);
/*  43 */     bBuffer.rewind();
/*     */     
/*  45 */     Point relativeMouseCoords = computeRelativeMouseCoords(mx, my);
/*  46 */     int imageX = relativeMouseCoords.x * bImage.getImageWidth() / this._width;
/*  47 */     int imageY = relativeMouseCoords.y * bImage.getImageHeight() / this._height;
/*  48 */     Image.Format format = image.getFormat();
/*  49 */     if (format == Image.Format.NativeDXT1 || format == Image.Format.NativeDXT1A)
/*  50 */       return false; 
/*  51 */     if (format == Image.Format.NativeDXT5) {
/*  52 */       int texelX = imageX / 4;
/*  53 */       int texelY = imageY / 4;
/*  54 */       int texwidth = image.getWidth() / 4 + ((image.getWidth() % 4 == 0) ? 0 : 1);
/*  55 */       int texPos = texelX + texelY * texwidth;
/*  56 */       int bufStart = texPos * 16;
/*  57 */       bBuffer.position(bufStart);
/*  58 */       int alpha0 = bBuffer.get() & 0xFF;
/*  59 */       int alpha1 = bBuffer.get() & 0xFF;
/*  60 */       if (alpha0 > 0 && alpha1 > 0)
/*  61 */         return false; 
/*  62 */       if (alpha0 == 0 && alpha1 == 0) {
/*  63 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  71 */       int offX = imageX % 4;
/*  72 */       int offY = imageY % 4;
/*     */       
/*  74 */       int bitOff = (offX + offY * 4) * 3;
/*  75 */       while (bitOff > 7) {
/*  76 */         bBuffer.get();
/*  77 */         bitOff -= 8;
/*     */       } 
/*     */       
/*  80 */       int curOff = bitOff;
/*  81 */       byte b = bBuffer.get();
/*  82 */       int code = 0;
/*  83 */       for (int i = 0; i < 3; i++) {
/*  84 */         if (0 != (b & (int)Math.pow(2.0D, curOff))) {
/*  85 */           code = (int)(code + Math.pow(2.0D, (3 - i + 1)));
/*     */         }
/*  87 */         curOff++;
/*  88 */         if (i < 2 && curOff > 7) {
/*  89 */           b = bBuffer.get();
/*  90 */           curOff -= 8;
/*     */         } 
/*     */       } 
/*  93 */       if (code == 0)
/*  94 */         return (alpha0 == 0); 
/*  95 */       if (code == 1)
/*  96 */         return (alpha1 == 0); 
/*  97 */       if (alpha0 <= alpha1) {
/*  98 */         if (code == 6)
/*  99 */           return true; 
/* 100 */         if (code == 7) {
/* 101 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       return false;
/*     */     } 
/* 110 */     int bufferPos = (imageX + imageY * image.getWidth()) * 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (bufferPos < 0 || bufferPos > bBuffer.limit() - 4) {
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     bBuffer.position(bufferPos);
/* 120 */     ColorRGBA color = new ColorRGBA((bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return (color.a == 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private Point computeRelativeMouseCoords(int mx, int my) {
/* 132 */     int relativeX = mx - getX();
/* 133 */     int relativeY = my - getY();
/* 134 */     return new Point(relativeX, relativeY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BImage getBackgroundImage() {
/* 144 */     BBackground background = getBackground();
/*     */     
/* 146 */     if (background == null) {
/* 147 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 151 */       Field field = ImageBackground.class.getDeclaredField("_image");
/* 152 */       field.setAccessible(true);
/* 153 */       BImage image = (BImage)field.get(background);
/* 154 */       if (image == null) {
/* 155 */         return null;
/*     */       }
/*     */       
/* 158 */       ImageBackground.class.getDeclaredField("_image").setAccessible(false);
/*     */       
/* 160 */       return image;
/* 161 */     } catch (NoSuchFieldException e) {
/* 162 */       throw new IllegalStateException(e);
/* 163 */     } catch (IllegalAccessException e) {
/* 164 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\IrregularButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */