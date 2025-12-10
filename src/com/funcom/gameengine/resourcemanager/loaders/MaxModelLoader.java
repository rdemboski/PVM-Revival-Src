/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.view.MaxNode;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.util.LittleEndien;
/*    */ import com.jmex.model.converters.maxutils.CustomTDSFile;
/*    */ import com.jmex.model.converters.maxutils.ReducioLoadDelegate;
/*    */ import com.jmex.model.converters.maxutils.TextureLoadDelegate;
/*    */ import java.io.DataInput;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class MaxModelLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   private ReducioLoadDelegate reducioLoadDelegate;
/*    */   
/*    */   public MaxModelLoader() {
/* 25 */     super(MaxNode.class);
/*    */   }
/*    */   
/*    */   public void setResourceManager(ResourceManager resourceManager) {
/* 29 */     super.setResourceManager(resourceManager);
/* 30 */     this.reducioLoadDelegate = new ReducioLoadDelegate(getResourceManager());
/*    */   }
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 34 */     InputStream inStream = null;
/*    */     try {
/* 36 */       inStream = getFileInputStream(managedResource.getName());
/*    */       
/* 38 */       LittleEndien maxDataInput = new LittleEndien(inStream);
/* 39 */       CustomTDSFile chunkedTDS = new CustomTDSFile((DataInput)maxDataInput, (TextureLoadDelegate)this.reducioLoadDelegate, managedResource.getName());
/* 40 */       Node child = chunkedTDS.buildScene();
/*    */       
/* 42 */       MaxNode maxNode = new MaxNode();
/* 43 */       for (Spatial spatial : new ArrayList(child.getChildren())) {
/* 44 */         maxNode.attachChild(spatial);
/*    */       }
/* 46 */       maxNode.updateGeometricState(1.0F, true);
/* 47 */       maxNode.updateRenderState();
/*    */       
/* 49 */       managedResource.setResource(maxNode);
/* 50 */     } catch (IOException e) {
/* 51 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 53 */       closeSafely(inStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\MaxModelLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */