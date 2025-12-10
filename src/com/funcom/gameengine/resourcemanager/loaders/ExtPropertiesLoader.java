/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.commons.configuration.ExtProperties;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtPropertiesLoader
/*    */   extends AbstractLoader
/*    */ {
/* 16 */   private static final Logger LOGGER = Logger.getLogger(ExtPropertiesLoader.class);
/*    */   
/*    */   public ExtPropertiesLoader() {
/* 19 */     super(ExtProperties.class);
/*    */   }
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 23 */     String docName = managedResource.getName();
/*    */ 
/*    */     
/* 26 */     InputStream inputStream = null;
/*    */     try {
/* 28 */       inputStream = getFileInputStream(docName);
/* 29 */       ExtProperties properties = new ExtProperties();
/* 30 */       properties.load(inputStream);
/*    */       
/* 32 */       managedResource.setResource(properties);
/* 33 */     } catch (FileNotFoundException e) {
/* 34 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 35 */     } catch (IOException e) {
/* 36 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 38 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\ExtPropertiesLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */