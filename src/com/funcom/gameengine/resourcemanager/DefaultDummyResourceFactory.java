/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultDummyResourceFactory
/*    */   implements DummyResourceFactory
/*    */ {
/* 16 */   private Map<Class<?>, Object> dummyResources = new HashMap<Class<?>, Object>();
/*    */ 
/*    */   
/*    */   public void setDummyResource(Class<?> type, Object dummyResource) {
/* 20 */     if (this.dummyResources.containsKey(type))
/* 21 */       this.dummyResources.remove(type); 
/* 22 */     this.dummyResources.put(type, dummyResource);
/*    */   }
/*    */   
/*    */   public Object getResourceForType(Class<?> type) {
/* 26 */     Object dummy = this.dummyResources.get(type);
/* 27 */     if (dummy == null)
/* 28 */       throw new IllegalStateException("Dummy resource not registered for type:" + dummy); 
/* 29 */     return dummy;
/*    */   }
/*    */   
/*    */   public void registerDummyImageAs(Class<?> type) {
/* 33 */     BufferedImage im = createDummyImage();
/* 34 */     this.dummyResources.put(type, im);
/*    */   }
/*    */   
/*    */   public void registerDummyImageArrayAs(Class<?> type) {
/* 38 */     Image[] imageArray = { createDummyImage() };
/* 39 */     this.dummyResources.put(type, imageArray);
/*    */   }
/*    */   
/*    */   private BufferedImage createDummyImage() {
/* 43 */     BufferedImage failsafeImage = new BufferedImage(1, 1, 2);
/* 44 */     Graphics graphics = failsafeImage.getGraphics();
/* 45 */     graphics.setColor(new Color(0, 0, 0, 255));
/* 46 */     graphics.fillRect(0, 0, 1, 1);
/* 47 */     graphics.dispose();
/* 48 */     return failsafeImage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\DefaultDummyResourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */