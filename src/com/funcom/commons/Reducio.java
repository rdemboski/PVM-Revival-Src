/*     */ package com.funcom.commons;
/*     */ import com.sun.image.codec.jpeg.JPEGCodec;
/*     */ import com.sun.image.codec.jpeg.JPEGEncodeParam;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class Reducio {
/*  22 */   private static float qualityLevel = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  32 */     if (args.length < 2) {
/*  33 */       printUsage();
/*  34 */       System.exit(1);
/*     */     } 
/*     */     
/*  37 */     File sourceFile = new File(args[0]);
/*  38 */     if (!sourceFile.exists()) {
/*  39 */       printUsage();
/*  40 */       System.exit(1);
/*     */     } 
/*     */     
/*     */     try {
/*  44 */       qualityLevel = Float.parseFloat(args[1]);
/*  45 */     } catch (NumberFormatException e) {
/*  46 */       printUsage();
/*  47 */       e.printStackTrace();
/*  48 */       System.exit(1);
/*     */     } 
/*     */     
/*     */     try {
/*  52 */       String filepath = sourceFile.getPath();
/*  53 */       filepath = filepath.substring(0, filepath.lastIndexOf('.'));
/*     */       
/*  55 */       BufferedImage bufferedImage = ImageIO.read(sourceFile);
/*  56 */       saveRGBasJPG(filepath, bufferedImage, qualityLevel);
/*  57 */       saveAlphaAsPNG(filepath, bufferedImage);
/*  58 */     } catch (IOException e) {
/*  59 */       throw new IllegalStateException("Failure processing: " + Arrays.toString(args), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveAlphaAsPNG(String filename, BufferedImage sourceImage) throws IOException {
/*  67 */     if (!sourceImage.getColorModel().hasAlpha())
/*     */       return; 
/*  69 */     if (sourceImage.getColorModel().getPixelSize() != 32)
/*  70 */       throw new RuntimeException("Unrecognized data format! for file: " + filename + " due to format: " + sourceImage.getColorModel().toString()); 
/*  71 */     int[] pix = new int[sourceImage.getWidth() * sourceImage.getHeight() * 4];
/*  72 */     sourceImage.getData().getPixels(0, 0, sourceImage.getWidth(), sourceImage.getHeight(), pix);
/*  73 */     BufferedImage alphImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), 10);
/*  74 */     WritableRaster r = alphImage.getRaster();
/*  75 */     for (int x = 0; x < sourceImage.getWidth(); x++) {
/*  76 */       for (int y = 0; y < sourceImage.getHeight(); y++) {
/*  77 */         int rgb = pix[(y * sourceImage.getWidth() + x) * 4 + 3];
/*  78 */         rgb |= rgb << 8 | rgb << 16;
/*  79 */         r.setPixel(x, y, new int[] { rgb });
/*     */       } 
/*  81 */     }  alphImage.setData(r);
/*     */ 
/*     */     
/*  84 */     File pngfile = new File(filename + ".alpha.png");
/*  85 */     ImageIO.write(alphImage, "png", pngfile);
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
/*     */   private static void saveRGBasJPG(String filename, BufferedImage sourceImage, float qualityLevel) throws IOException {
/* 111 */     BufferedImage destImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), 1);
/*     */     
/* 113 */     int bytesPerPixel = sourceImage.getColorModel().getPixelSize() / 8;
/* 114 */     if (bytesPerPixel < 3) {
/* 115 */       throw new RuntimeException("Image format not supported! \"" + filename + "\"");
/*     */     }
/* 117 */     int[] colorPix = new int[sourceImage.getWidth() * sourceImage.getHeight() * bytesPerPixel];
/* 118 */     sourceImage.getData().getPixels(0, 0, sourceImage.getWidth(), sourceImage.getHeight(), colorPix);
/* 119 */     int pixelCount = sourceImage.getHeight() * sourceImage.getWidth();
/* 120 */     int x = 0, y = 0;
/* 121 */     int width = sourceImage.getWidth();
/* 122 */     for (int i = 0; i < pixelCount; i++) {
/* 123 */       destImage.setRGB(x, y, colorPix[i * bytesPerPixel] << 16 | colorPix[i * bytesPerPixel + 1] << 8 | colorPix[i * bytesPerPixel + 2]);
/* 124 */       x++;
/* 125 */       if (x == width) {
/* 126 */         x = 0;
/* 127 */         y++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     File file = new File(filename + ".jpeg");
/* 137 */     FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 138 */     JPEGImageEncoder jpeegEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
/* 139 */     JPEGEncodeParam params = jpeegEncoder.getDefaultJPEGEncodeParam(destImage);
/* 140 */     params.setQuality(qualityLevel, false);
/* 141 */     jpeegEncoder.setJPEGEncodeParam(params);
/* 142 */     jpeegEncoder.encode(destImage);
/* 143 */     fileOutputStream.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage loadImage(File file) throws IOException, FileNotFoundException {
/*     */     BufferedImage sourceImage;
/* 149 */     InputStream inputStream = new FileInputStream(file);
/*     */     try {
/* 151 */       sourceImage = ImageIO.read(inputStream);
/*     */     } finally {
/*     */       try {
/* 154 */         inputStream.close();
/* 155 */       } catch (IOException ignore) {}
/*     */     } 
/*     */     
/* 158 */     return sourceImage;
/*     */   }
/*     */   
/*     */   private static void printUsage() {
/* 162 */     System.out.println("Usage: Reducio [srource] [quality level]");
/* 163 */     System.out.println("Quality level should be between 0.5 and 0.95");
/*     */   }
/*     */   
/*     */   public static BufferedImage load(BufferedImage colorChannels, BufferedImage alphaChannel) {
/* 167 */     BufferedImage finishedImage = new BufferedImage(colorChannels.getWidth(), colorChannels.getHeight(), 2);
/* 168 */     WritableRaster raster = finishedImage.getRaster();
/* 169 */     Raster color = colorChannels.getRaster();
/* 170 */     Raster alpha = alphaChannel.getRaster();
/* 171 */     int width = colorChannels.getWidth();
/* 172 */     int[] colorArray = new int[3 * width];
/* 173 */     int[] alphaArray = new int[width];
/* 174 */     int[] resultArray = new int[4 * width];
/* 175 */     int height = colorChannels.getHeight();
/* 176 */     for (int y = 0; y < height; y++) {
/* 177 */       alpha.getSamples(0, y, width, 1, 0, alphaArray);
/* 178 */       color.getPixels(0, y, width, 1, colorArray);
/* 179 */       for (int x = 0; x < width; x++) {
/* 180 */         int alphaSample = alphaArray[x];
/* 181 */         if (alphaSample != 0) {
/* 182 */           resultArray[x * 4] = colorArray[x * 3];
/* 183 */           resultArray[x * 4 + 1] = colorArray[x * 3 + 1];
/* 184 */           resultArray[x * 4 + 2] = colorArray[x * 3 + 2];
/*     */         } 
/* 186 */         resultArray[x * 4 + 3] = alphaSample;
/*     */       } 
/* 188 */       raster.setPixels(0, y, width, 1, resultArray);
/*     */     } 
/* 190 */     return finishedImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage load(String fileName) throws IOException {
/* 198 */     BufferedImage alphaChannel, colorChannels = loadImage(new File(fileName + ".jpeg"));
/*     */     
/*     */     try {
/* 201 */       File path = new File(fileName + ".alpha.jpeg");
/* 202 */       if (!path.exists()) {
/* 203 */         path = new File(fileName + ".alpha.png");
/*     */       }
/* 205 */       alphaChannel = loadImage(path);
/* 206 */     } catch (IllegalArgumentException e) {
/* 207 */       return colorChannels;
/* 208 */     } catch (FileNotFoundException e) {
/* 209 */       return colorChannels;
/*     */     } 
/* 211 */     return load(colorChannels, alphaChannel);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void showResultWindow(String fileName) {
/* 216 */     BufferedImage image = null;
/*     */     try {
/* 218 */       image = load(fileName);
/* 219 */     } catch (IOException e) {
/* 220 */       throw new RuntimeException(e);
/*     */     } 
/* 222 */     JFrame frame = new JFrame("test");
/* 223 */     frame.setBounds(100, 100, 350, 1000);
/* 224 */     frame.setDefaultCloseOperation(3);
/* 225 */     BufferedImage destImage = new BufferedImage(300, 300, 2);
/* 226 */     Graphics2D g = destImage.createGraphics();
/* 227 */     g.setColor(Color.GRAY);
/* 228 */     g.fillRect(0, 0, image.getWidth(), image.getHeight());
/* 229 */     g.setColor(Color.BLUE);
/* 230 */     g.fillRect(100, 100, 100, 100);
/* 231 */     g.drawImage(image, 0, 0, (ImageObserver)null);
/* 232 */     g.dispose();
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
/* 249 */     JLabel jLabel = new JLabel(new ImageIcon(destImage));
/* 250 */     jLabel.setBounds(20, 20, 300, 300);
/*     */ 
/*     */     
/* 253 */     frame.add(jLabel);
/*     */ 
/*     */     
/* 256 */     frame.setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\Reducio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */