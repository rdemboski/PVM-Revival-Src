/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import javax.imageio.ImageIO;
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
/*     */ public class GraphicsUtilities
/*     */ {
/*  73 */   private static final GraphicsConfiguration CONFIGURATION = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
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
/*     */ 
/*     */   
/*     */   public static BufferedImage createColorModelCompatibleImage(BufferedImage image) {
/*  92 */     ColorModel cm = image.getColorModel();
/*  93 */     return new BufferedImage(cm, cm.createCompatibleWritableRaster(image.getWidth(), image.getHeight()), cm.isAlphaPremultiplied(), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createCompatibleImage(BufferedImage image) {
/* 115 */     return createCompatibleImage(image, image.getWidth(), image.getHeight());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
/* 137 */     return CONFIGURATION.createCompatibleImage(width, height, image.getTransparency());
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
/*     */   
/*     */   public static BufferedImage createCompatibleImage(int width, int height) {
/* 156 */     return CONFIGURATION.createCompatibleImage(width, height);
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
/*     */   
/*     */   public static BufferedImage createTranslucentCompatibleImage(int width, int height) {
/* 175 */     return CONFIGURATION.createCompatibleImage(width, height, 3);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
/* 196 */     BufferedImage image = ImageIO.read(resource);
/* 197 */     return toCompatibleImage(image);
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
/*     */   public static BufferedImage toCompatibleImage(BufferedImage image) {
/* 215 */     if (image.getColorModel().equals(CONFIGURATION.getColorModel())) {
/* 216 */       return image;
/*     */     }
/*     */     
/* 219 */     BufferedImage compatibleImage = CONFIGURATION.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
/*     */     
/* 221 */     Graphics g = compatibleImage.getGraphics();
/* 222 */     g.drawImage(image, 0, 0, null);
/* 223 */     g.dispose();
/*     */     
/* 225 */     return compatibleImage;
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
/*     */   public static BufferedImage createThumbnailFast(BufferedImage image, int newSize) {
/* 253 */     int width = image.getWidth();
/* 254 */     int height = image.getHeight();
/*     */     
/* 256 */     if (width > height) {
/* 257 */       if (newSize >= width) {
/* 258 */         throw new IllegalArgumentException("newSize must be lower than the image width");
/*     */       }
/* 260 */       if (newSize <= 0) {
/* 261 */         throw new IllegalArgumentException("newSize must be greater than 0");
/*     */       }
/*     */ 
/*     */       
/* 265 */       float ratio = width / height;
/* 266 */       width = newSize;
/* 267 */       height = (int)(newSize / ratio);
/*     */     } else {
/* 269 */       if (newSize >= height) {
/* 270 */         throw new IllegalArgumentException("newSize must be lower than the image height");
/*     */       }
/* 272 */       if (newSize <= 0) {
/* 273 */         throw new IllegalArgumentException("newSize must be greater than 0");
/*     */       }
/*     */ 
/*     */       
/* 277 */       float ratio = height / width;
/* 278 */       height = newSize;
/* 279 */       width = (int)(newSize / ratio);
/*     */     } 
/*     */     
/* 282 */     BufferedImage temp = createCompatibleImage(image, width, height);
/* 283 */     Graphics2D g2 = temp.createGraphics();
/* 284 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*     */     
/* 286 */     g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
/* 287 */     g2.dispose();
/*     */     
/* 289 */     return temp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createThumbnailFast(BufferedImage image, int newWidth, int newHeight) {
/* 316 */     if (newWidth >= image.getWidth() || newHeight >= image.getHeight())
/*     */     {
/* 318 */       throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
/*     */     }
/*     */     
/* 321 */     if (newWidth <= 0 || newHeight <= 0) {
/* 322 */       throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
/*     */     }
/*     */ 
/*     */     
/* 326 */     BufferedImage temp = createCompatibleImage(image, newWidth, newHeight);
/* 327 */     Graphics2D g2 = temp.createGraphics();
/* 328 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*     */     
/* 330 */     g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
/* 331 */     g2.dispose();
/*     */     
/* 333 */     return temp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createThumbnail(BufferedImage image, int newSize) {
/* 359 */     int width = image.getWidth();
/* 360 */     int height = image.getHeight();
/*     */     
/* 362 */     boolean isWidthGreater = (width > height);
/*     */     
/* 364 */     if (isWidthGreater) {
/* 365 */       if (newSize >= width) {
/* 366 */         throw new IllegalArgumentException("newSize must be lower than the image width");
/*     */       }
/*     */     }
/* 369 */     else if (newSize >= height) {
/* 370 */       throw new IllegalArgumentException("newSize must be lower than the image height");
/*     */     } 
/*     */ 
/*     */     
/* 374 */     if (newSize <= 0) {
/* 375 */       throw new IllegalArgumentException("newSize must be greater than 0");
/*     */     }
/*     */ 
/*     */     
/* 379 */     float ratioWH = width / height;
/* 380 */     float ratioHW = height / width;
/*     */     
/* 382 */     BufferedImage thumb = image;
/*     */     
/*     */     do {
/* 385 */       if (isWidthGreater) {
/* 386 */         width /= 2;
/* 387 */         if (width < newSize) {
/* 388 */           width = newSize;
/*     */         }
/* 390 */         height = (int)(width / ratioWH);
/*     */       } else {
/* 392 */         height /= 2;
/* 393 */         if (height < newSize) {
/* 394 */           height = newSize;
/*     */         }
/* 396 */         width = (int)(height / ratioHW);
/*     */       } 
/*     */ 
/*     */       
/* 400 */       BufferedImage temp = createCompatibleImage(image, width, height);
/* 401 */       Graphics2D g2 = temp.createGraphics();
/* 402 */       g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*     */       
/* 404 */       g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
/* 405 */       g2.dispose();
/*     */       
/* 407 */       thumb = temp;
/* 408 */     } while (newSize != (isWidthGreater ? width : height));
/*     */     
/* 410 */     return thumb;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createThumbnail(BufferedImage image, int newWidth, int newHeight) {
/* 435 */     int width = image.getWidth();
/* 436 */     int height = image.getHeight();
/*     */     
/* 438 */     if (newWidth >= width || newHeight >= height) {
/* 439 */       throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
/*     */     }
/*     */     
/* 442 */     if (newWidth <= 0 || newHeight <= 0) {
/* 443 */       throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
/*     */     }
/*     */ 
/*     */     
/* 447 */     BufferedImage thumb = image;
/*     */     
/*     */     do {
/* 450 */       if (width > newWidth) {
/* 451 */         width /= 2;
/* 452 */         if (width < newWidth) {
/* 453 */           width = newWidth;
/*     */         }
/*     */       } 
/*     */       
/* 457 */       if (height > newHeight) {
/* 458 */         height /= 2;
/* 459 */         if (height < newHeight) {
/* 460 */           height = newHeight;
/*     */         }
/*     */       } 
/*     */       
/* 464 */       BufferedImage temp = createCompatibleImage(image, width, height);
/* 465 */       Graphics2D g2 = temp.createGraphics();
/* 466 */       g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*     */       
/* 468 */       g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
/* 469 */       g2.dispose();
/*     */       
/* 471 */       thumb = temp;
/* 472 */     } while (width != newWidth || height != newHeight);
/*     */     
/* 474 */     return thumb;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
/* 497 */     if (w == 0 || h == 0) {
/* 498 */       return new int[0];
/*     */     }
/*     */     
/* 501 */     if (pixels == null) {
/* 502 */       pixels = new int[w * h];
/* 503 */     } else if (pixels.length < w * h) {
/* 504 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     } 
/*     */ 
/*     */     
/* 508 */     int imageType = img.getType();
/* 509 */     if (imageType == 2 || imageType == 1) {
/*     */       
/* 511 */       Raster raster = img.getRaster();
/* 512 */       return (int[])raster.getDataElements(x, y, w, h, pixels);
/*     */     } 
/*     */ 
/*     */     
/* 516 */     return img.getRGB(x, y, w, h, pixels, 0, w);
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
/*     */ 
/*     */   
/*     */   public static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
/* 536 */     if (pixels == null || w == 0 || h == 0)
/*     */       return; 
/* 538 */     if (pixels.length < w * h) {
/* 539 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     }
/*     */ 
/*     */     
/* 543 */     int imageType = img.getType();
/* 544 */     if (imageType == 2 || imageType == 1) {
/*     */       
/* 546 */       WritableRaster raster = img.getRaster();
/* 547 */       raster.setDataElements(x, y, w, h, pixels);
/*     */     } else {
/*     */       
/* 550 */       img.setRGB(x, y, w, h, pixels, 0, w);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\GraphicsUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */