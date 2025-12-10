/*     */ package com.funcom.gameengine.utils;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class AnimationStripeCreator implements Runnable {
/*  22 */   private static final Logger LOGGER = Logger.getLogger(AnimationStripeCreator.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private File[] imageFiles;
/*     */ 
/*     */ 
/*     */   
/*     */   private File destinationFile;
/*     */ 
/*     */   
/*     */   private static final String ACTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<animation>\n  <imagefile>{0}</imagefile>\n  <frames>{1}</frames>\n  <hitframe>{2}</hitframe>\n  <fps>{3}</fps>\n  <origin x=\"{4}\" y=\"{5}\"/>\n</animation>";
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseArguments(String[] args) {
/*  38 */     if (args.length < 3)
/*  39 */       errorQuit("Minimum 3 arguments: destFile image1 image2 ..."); 
/*  40 */     LOGGER.log((Priority)Level.INFO, Arrays.toString((Object[])args));
/*  41 */     createDestinationFile(args);
/*  42 */     collectFilenames(args);
/*     */   }
/*     */   
/*     */   private void createDestinationFile(String[] args) {
/*  46 */     this.destinationFile = new File(args[0]);
/*  47 */     if (this.destinationFile.exists())
/*  48 */       errorQuit("File " + this.destinationFile.getName() + " exists!"); 
/*     */   }
/*     */   
/*     */   public void run() {
/*  52 */     Image[] images = loadAllImages();
/*  53 */     BufferedImage finalImage = createBlankCanvas(images);
/*  54 */     paintStripe(finalImage, images);
/*  55 */     saveFinalImage(finalImage);
/*  56 */     saveXml(this.destinationFile.getAbsolutePath(), images.length, 0, 10, 0, 0);
/*     */   }
/*     */   
/*     */   private void saveXml(String imageFile, int frames, int hitframe, int fps, int originx, int originy) {
/*     */     try {
/*  61 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(this.destinationFile.getParent(), this.destinationFile.getName() + ".xml"))));
/*  62 */       writer.write(MessageFormat.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<animation>\n  <imagefile>{0}</imagefile>\n  <frames>{1}</frames>\n  <hitframe>{2}</hitframe>\n  <fps>{3}</fps>\n  <origin x=\"{4}\" y=\"{5}\"/>\n</animation>", new Object[] { imageFile, Integer.valueOf(frames), Integer.valueOf(hitframe), Integer.valueOf(fps), Integer.valueOf(originx), Integer.valueOf(originy) }));
/*  63 */       writer.close();
/*  64 */     } catch (FileNotFoundException e) {
/*  65 */       throw new RuntimeException(e);
/*  66 */     } catch (IOException e) {
/*  67 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveFinalImage(BufferedImage finalImage) {
/*     */     try {
/*  73 */       ImageIO.write(finalImage, "png", this.destinationFile);
/*  74 */     } catch (IOException e) {
/*  75 */       errorQuit(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintStripe(BufferedImage finalImage, Image[] images) {
/*  81 */     Graphics2D g = finalImage.createGraphics();
/*  82 */     for (int i = 0; i < images.length; i++)
/*  83 */       g.drawImage(images[i], i * images[i].getWidth(null), 0, (ImageObserver)null); 
/*  84 */     g.dispose();
/*     */   }
/*     */   
/*     */   private int transparentPixelColor(Image[] images, int i) {
/*  88 */     int[] pixels = new int[1];
/*  89 */     PixelGrabber px = new PixelGrabber(images[i], 0, 0, 1, 1, pixels, 0, 1);
/*     */     try {
/*  91 */       px.grabPixels();
/*  92 */     } catch (InterruptedException e) {
/*  93 */       throw new RuntimeException(e);
/*     */     } 
/*  95 */     return pixels[0];
/*     */   }
/*     */   
/*     */   private BufferedImage createBlankCanvas(Image[] images) {
/*  99 */     return new BufferedImage(images[0].getWidth(null) * images.length, images[0].getHeight(null), 6);
/*     */   }
/*     */ 
/*     */   
/*     */   private Image[] loadAllImages() {
/* 104 */     Image[] images = new Image[this.imageFiles.length];
/* 105 */     for (int i = 0; i < images.length; i++) {
/*     */       try {
/* 107 */         images[i] = ImageIO.read(this.imageFiles[i]);
/* 108 */       } catch (IOException e) {
/* 109 */         errorQuit("Failed to read: " + this.imageFiles[i]);
/*     */       } 
/* 111 */     }  return images;
/*     */   }
/*     */   
/*     */   private void collectFilenames(String[] args) {
/* 115 */     this.imageFiles = new File[args.length - 1];
/* 116 */     for (int i = 0; i < this.imageFiles.length; i++) {
/* 117 */       this.imageFiles[i] = new File(args[i + 1]);
/* 118 */       if (!this.imageFiles[i].exists() || !this.imageFiles[i].isFile())
/* 119 */         errorQuit("Wrong image file: " + args[i + 1]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void errorQuit(String message) {
/* 124 */     System.err.println(message);
/* 125 */     System.exit(-1);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 129 */     AnimationStripeCreator asc = new AnimationStripeCreator();
/* 130 */     asc.parseArguments(args);
/* 131 */     asc.run();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\AnimationStripeCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */