/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*    */ import com.funcom.commons.jme.md5importer.importer.MD5Importer;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Md5AnimationLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public Md5AnimationLoader() {
/* 17 */     super(JointAnimation.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void startTiming() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void endTiming() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 33 */     startTiming();
/* 34 */     MD5Importer importer = MD5Importer.getInstance();
/* 35 */     InputStream inputStream = null;
/*    */     try {
/* 37 */       inputStream = getFileInputStream(managedResource.getName());
/* 38 */       importer.loadAnim(inputStream, managedResource.getName());
/* 39 */       managedResource.setResource(importer.getAnimation());
/* 40 */       importer.cleanup();
/* 41 */     } catch (MalformedURLException e) {
/* 42 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 43 */     } catch (IOException e) {
/* 44 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 46 */       closeSafely(inputStream, managedResource);
/*    */     } 
/* 48 */     endTiming();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\Md5AnimationLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */