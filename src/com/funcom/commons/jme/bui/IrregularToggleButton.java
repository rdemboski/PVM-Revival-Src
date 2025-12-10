/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.image.Image;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IrregularToggleButton
/*     */   extends BToggleButton
/*     */ {
/*     */   public IrregularToggleButton(String text) {
/*  25 */     super(text);
/*     */   }
/*     */   
/*     */   public IrregularToggleButton(String text, String action) {
/*  29 */     super(text, action);
/*     */   }
/*     */   
/*     */   public IrregularToggleButton(BIcon icon, String action) {
/*  33 */     super(icon, action);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  38 */     if (isVisible() && mx >= this._x && my >= this._y && mx < this._x + this._width && my < this._y + this._height && !outerAlphaHit(mx, my))
/*     */     {
/*  40 */       return (BComponent)this;
/*     */     }
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   private boolean outerAlphaHit(int mx, int my) {
/*  46 */     BImage bImage = getBackgroundImage();
/*  47 */     if (bImage == null) {
/*  48 */       return false;
/*     */     }
/*  50 */     TextureState texState = (TextureState)bImage.getRenderState(RenderState.StateType.Texture);
/*  51 */     Image image = texState.getTexture().getImage();
/*  52 */     ByteBuffer bBuffer = image.getData(0);
/*  53 */     bBuffer.rewind();
/*     */     
/*  55 */     Point relativeMouseCoords = computeRelativeMouseCoords(mx, my);
/*  56 */     int imageX = relativeMouseCoords.x * bImage.getImageWidth() / this._width;
/*  57 */     int imageY = relativeMouseCoords.y * bImage.getImageHeight() / this._height;
/*  58 */     Image.Format format = image.getFormat();
/*  59 */     if (format == Image.Format.NativeDXT1 || format == Image.Format.NativeDXT1A)
/*  60 */       return false; 
/*  61 */     if (format == Image.Format.NativeDXT5) {
/*  62 */       int texelX = imageX / 4;
/*  63 */       int texelY = imageY / 4;
/*  64 */       int texwidth = image.getWidth() / 4 + ((image.getWidth() % 4 == 0) ? 0 : 1);
/*  65 */       int texPos = texelX + texelY * texwidth;
/*  66 */       int bufStart = texPos * 16;
/*  67 */       bBuffer.position(bufStart);
/*  68 */       int alpha0 = bBuffer.get() & 0xFF;
/*  69 */       int alpha1 = bBuffer.get() & 0xFF;
/*  70 */       if (alpha0 > 0 && alpha1 > 0)
/*  71 */         return false; 
/*  72 */       if (alpha0 == 0 && alpha1 == 0) {
/*  73 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       int offX = imageX % 4;
/*  82 */       int offY = imageY % 4;
/*     */       
/*  84 */       int bitOff = (offX + offY * 4) * 3;
/*  85 */       while (bitOff > 7) {
/*  86 */         bBuffer.get();
/*  87 */         bitOff -= 8;
/*     */       } 
/*     */       
/*  90 */       int curOff = bitOff;
/*  91 */       byte b = bBuffer.get();
/*  92 */       int code = 0;
/*  93 */       for (int i = 0; i < 3; i++) {
/*  94 */         if (0 != (b & (int)Math.pow(2.0D, curOff))) {
/*  95 */           code = (int)(code + Math.pow(2.0D, (3 - i + 1)));
/*     */         }
/*  97 */         curOff++;
/*  98 */         if (i < 2 && curOff > 7) {
/*  99 */           b = bBuffer.get();
/* 100 */           curOff -= 8;
/*     */         } 
/*     */       } 
/* 103 */       if (code == 0)
/* 104 */         return (alpha0 == 0); 
/* 105 */       if (code == 1)
/* 106 */         return (alpha1 == 0); 
/* 107 */       if (alpha0 <= alpha1) {
/* 108 */         if (code == 6)
/* 109 */           return true; 
/* 110 */         if (code == 7) {
/* 111 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       return false;
/*     */     } 
/* 120 */     int bufferPos = (imageX + imageY * image.getWidth()) * 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (bufferPos < 0 || bufferPos > bBuffer.limit() - 4) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     bBuffer.position(bufferPos);
/* 130 */     ColorRGBA color = new ColorRGBA((bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return (color.a == 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private Point computeRelativeMouseCoords(int mx, int my) {
/* 142 */     int relativeX = mx - getX();
/* 143 */     int relativeY = my - getY();
/* 144 */     return new Point(relativeX, relativeY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BImage getBackgroundImage() {
/* 154 */     BBackground background = getBackground();
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
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\IrregularToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */