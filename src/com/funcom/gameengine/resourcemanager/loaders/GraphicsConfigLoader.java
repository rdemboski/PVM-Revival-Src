/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.jme.BlendMode;
/*    */ import com.funcom.gameengine.model.GraphicsConfig;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.JDOMException;
/*    */ 
/*    */ public class GraphicsConfigLoader
/*    */   extends AbstractLoader {
/* 17 */   private static final Logger LOG = Logger.getLogger(GraphicsConfigLoader.class.getName());
/* 18 */   private static final GraphicsConfig DEFAULT_GRAPHICS_CONFIG = new GraphicsConfig();
/*    */   
/*    */   public GraphicsConfigLoader() {
/* 21 */     super(GraphicsConfig.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 26 */     String docName = managedResource.getName();
/*    */ 
/*    */     
/* 29 */     InputStream inputStream = null;
/*    */     try {
/* 31 */       inputStream = getFileInputStream(docName);
/* 32 */       Document document = XmlLoader.createDocument(inputStream);
/*    */       
/* 34 */       BlendMode blendMode = BlendMode.NORMAL;
/* 35 */       String blendModeStr = SpatialUtils.getChildText(document, "blendmode");
/* 36 */       if (blendModeStr != null) {
/* 37 */         blendMode = BlendMode.valueOf(blendModeStr.toUpperCase());
/*    */       }
/*    */       
/* 40 */       GraphicsConfig config = new GraphicsConfig(blendMode);
/*    */       
/* 42 */       managedResource.setResource(config);
/* 43 */     } catch (FileNotFoundException e) {
/* 44 */       managedResource.setResource(DEFAULT_GRAPHICS_CONFIG);
/* 45 */     } catch (IOException e) {
/* 46 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 47 */     } catch (JDOMException e) {
/* 48 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 50 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\GraphicsConfigLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */