/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.GraphicsEnvironment;
/*    */ import java.awt.Image;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.ImageObserver;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AWTImageLoader extends AbstractLoader {
/* 15 */   private static final Logger LOG = Logger.getLogger(AWTImageLoader.class.getName());
/*    */   
/*    */   public AWTImageLoader() {
/* 18 */     super(Image.class);
/*    */   }
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 22 */     LOG.debug("Loading image: " + managedResource.getName());
/* 23 */     InputStream inputStream = null;
/*    */     try {
/* 25 */       inputStream = getFileInputStream(managedResource.getName());
/* 26 */       Image image = loadImage(inputStream);
/* 27 */       if (!validateImage(image)) {
/* 28 */         throw new LoadException(getResourceManager(), managedResource, new RuntimeException("Loaded invalid image!"));
/*    */       }
/* 30 */       managedResource.setResource(image);
/* 31 */     } catch (IOException e) {
/* 32 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 34 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private Image loadImage(InputStream inputStream) throws IOException {
/* 40 */     BufferedImage origigyImage = ImageIO.read(inputStream);
/* 41 */     BufferedImage comIm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(origigyImage.getWidth(), origigyImage.getHeight(), 3);
/* 42 */     Graphics2D g = comIm.createGraphics();
/* 43 */     g.drawImage(origigyImage, 0, 0, (ImageObserver)null);
/* 44 */     g.dispose();
/* 45 */     return comIm;
/*    */   }
/*    */   
/*    */   private boolean validateImage(Image image) {
/* 49 */     return (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\AWTImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */