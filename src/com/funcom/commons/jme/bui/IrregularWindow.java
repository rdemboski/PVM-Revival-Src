/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.image.Image;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public abstract class IrregularWindow
/*     */   extends BWindow
/*     */   implements PartiallyNotInteractive
/*     */ {
/*     */   private static NonProcessedEventsNotifier eventsNotifier;
/*     */   
/*     */   public static void setNonProcessedEventsNotifier(NonProcessedEventsNotifier eventsNotifier) {
/*  36 */     IrregularWindow.eventsNotifier = eventsNotifier;
/*     */   }
/*     */   
/*     */   public IrregularWindow(String s, BStyleSheet bStyleSheet, BLayoutManager bLayoutManager) {
/*  40 */     super(s, bStyleSheet, bLayoutManager);
/*     */   }
/*     */   
/*     */   public IrregularWindow(BStyleSheet bStyleSheet, BLayoutManager bLayoutManager) {
/*  44 */     super(bStyleSheet, bLayoutManager);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/*  62 */     int mx = MouseInput.get().getXAbsolute();
/*  63 */     int my = MouseInput.get().getYAbsolute();
/*  64 */     return isHit(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/*  68 */     boolean windowHit = windowHit(mx, my);
/*  69 */     boolean outerAlphaHit = outerAlphaHit(mx, my);
/*  70 */     return (windowHit && !outerAlphaHit);
/*     */   }
/*     */   
/*     */   private boolean windowHit(int mx, int my) {
/*  74 */     return (mx >= getX() && my >= getY() && mx <= getX() + getWidth() - 1 && my <= getY() + getHeight() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outerAlphaHit(int mx, int my) {
/*  81 */     BImage bImage = getBackgroundImage();
/*  82 */     if (bImage == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     TextureState texState = (TextureState)bImage.getRenderState(5);
/*  86 */     Image image = texState.getTexture().getImage();
/*  87 */     ByteBuffer bBuffer = image.getData(0);
/*  88 */     bBuffer.rewind();
/*     */     
/*  90 */     Point relativeMouseCoords = computeRelativeMouseCoords(mx, my);
/*  91 */     int imageX = relativeMouseCoords.x * bImage.getImageWidth() / this._width;
/*  92 */     int imageY = relativeMouseCoords.y * bImage.getImageHeight() / this._height;
/*  93 */     Image.Format format = image.getFormat();
/*  94 */     if (format == Image.Format.NativeDXT1 || format == Image.Format.NativeDXT1A)
/*  95 */       return false; 
/*  96 */     if (format == Image.Format.NativeDXT5) {
/*  97 */       int texelX = imageX / 4;
/*  98 */       int texelY = imageY / 4;
/*  99 */       int texwidth = image.getWidth() / 4 + ((image.getWidth() % 4 == 0) ? 0 : 1);
/* 100 */       int texPos = texelX + texelY * texwidth;
/* 101 */       int bufStart = texPos * 16;
/* 102 */       bBuffer.position(bufStart);
/* 103 */       int alpha0 = bBuffer.get() & 0xFF;
/* 104 */       int alpha1 = bBuffer.get() & 0xFF;
/* 105 */       if (alpha0 > 0 && alpha1 > 0)
/* 106 */         return false; 
/* 107 */       if (alpha0 == 0 && alpha1 == 0) {
/* 108 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       int offX = imageX % 4;
/* 117 */       int offY = imageY % 4;
/*     */       
/* 119 */       int bitOff = (offX + offY * 4) * 3;
/* 120 */       while (bitOff > 7) {
/* 121 */         bBuffer.get();
/* 122 */         bitOff -= 8;
/*     */       } 
/*     */       
/* 125 */       int curOff = bitOff;
/* 126 */       byte b = bBuffer.get();
/* 127 */       int code = 0;
/* 128 */       for (int i = 0; i < 3; i++) {
/* 129 */         if (0 != (b & (int)Math.pow(2.0D, curOff))) {
/* 130 */           code = (int)(code + Math.pow(2.0D, (3 - i + 1)));
/*     */         }
/* 132 */         curOff++;
/* 133 */         if (i < 2 && curOff > 7) {
/* 134 */           b = bBuffer.get();
/* 135 */           curOff -= 8;
/*     */         } 
/*     */       } 
/* 138 */       if (code == 0)
/* 139 */         return (alpha0 == 0); 
/* 140 */       if (code == 1)
/* 141 */         return (alpha1 == 0); 
/* 142 */       if (alpha0 <= alpha1) {
/* 143 */         if (code == 6)
/* 144 */           return true; 
/* 145 */         if (code == 7) {
/* 146 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       return false;
/*     */     } 
/* 155 */     int bufferPos = (relativeMouseCoords.x + relativeMouseCoords.y * image.getWidth()) * 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (bufferPos < 0 || bufferPos > bBuffer.limit() - 4) {
/* 161 */       return true;
/*     */     }
/*     */     
/* 164 */     bBuffer.position(bufferPos);
/* 165 */     ColorRGBA color = new ColorRGBA((bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F, (bBuffer.get() & 0xFF) / 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return (color.a == 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private Point computeRelativeMouseCoords(int mx, int my) {
/* 177 */     int relativeX = mx - getX();
/* 178 */     int relativeY = my - getY();
/* 179 */     return new Point(relativeX, relativeY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BImage getBackgroundImage() {
/* 189 */     BBackground background = getBackground();
/*     */     
/* 191 */     if (background == null) {
/* 192 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 196 */       Field field = ImageBackground.class.getDeclaredField("_image");
/* 197 */       field.setAccessible(true);
/* 198 */       BImage image = (BImage)field.get(background);
/* 199 */       if (image == null) {
/* 200 */         return null;
/*     */       }
/*     */       
/* 203 */       ImageBackground.class.getDeclaredField("_image").setAccessible(false);
/*     */       
/* 205 */       return image;
/* 206 */     } catch (NoSuchFieldException e) {
/* 207 */       throw new IllegalStateException(e);
/* 208 */     } catch (IllegalAccessException e) {
/* 209 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface NonProcessedEventsNotifier {
/*     */     void didNotProcessTheEvent(IrregularWindow param1IrregularWindow, MouseEvent param1MouseEvent);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\IrregularWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */