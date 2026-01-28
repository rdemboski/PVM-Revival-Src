/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.peeler.PeelerLocalizer;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jmex.bui.provider.ResourceProvider;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PeelerLoader
/*    */   extends AbstractLoader
/*    */ {
/* 21 */   private static final Logger LOGGER = Logger.getLogger(PeelerLoader.class);
/*    */   private BananaResourceProvider bananaResourceProvider;
/* 23 */   private PeelLoaderLocalizer localizer = new PeelLoaderLocalizer();
/*    */   
/*    */   public PeelerLoader() {
/* 26 */     super(BananaPeel.class);
/*    */   }
/*    */   
/*    */   public void setResourceManager(ResourceManager resourceManager) {
/* 30 */     super.setResourceManager(resourceManager);
/* 31 */     this.bananaResourceProvider = new BananaResourceProvider(resourceManager);
/*    */   }
/*    */   
           @SuppressWarnings("unchecked")
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 35 */     InputStream layoutStream = null;
/* 36 */     InputStream styleStream = null;
/*    */     
/*    */     try {
/* 39 */       String stylePath = managedResource.getName().substring(0, managedResource.getName().length() - 3) + "bss";
/* 40 */       layoutStream = getFileInputStream(managedResource);
/* 41 */       styleStream = getFileInputStream(stylePath);
/*    */       
/* 43 */       BananaPeel peel = new BananaPeel(layoutStream, styleStream, (ResourceProvider)this.bananaResourceProvider, this.localizer);
/* 44 */       ((ManagedResource<BananaPeel>)managedResource).setResource(peel);
/* 45 */     } catch (IOException e) {
/* 46 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 48 */       closeSafely(layoutStream, managedResource);
/* 49 */       closeSafely(styleStream, managedResource);
/*    */     } 
/*    */   }
/*    */   
/*    */   private class PeelLoaderLocalizer
/*    */     implements PeelerLocalizer {
/*    */     public String getLocalizedText(String key) {
/* 56 */       return TcgGame.getLocalizedText(key, new String[0]);
/*    */     }
/*    */     
/*    */     private PeelLoaderLocalizer() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\gameengine\resourcemanager\loaders\PeelerLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */