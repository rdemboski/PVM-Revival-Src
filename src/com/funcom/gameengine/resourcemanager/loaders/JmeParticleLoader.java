/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.ZBufferState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jme.util.export.binary.BinaryImporter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class JmeParticleLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public JmeParticleLoader(Class<?> resourceType) {
/* 17 */     super(resourceType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 22 */     InputStream inputStream = null;
/*    */     try {
/* 24 */       inputStream = getFileInputStream(managedResource.getName());
/* 25 */       managedResource.setResource(loadParticles(inputStream));
/* 26 */     } catch (IOException e) {
/* 27 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 29 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */   
/*    */   private Spatial loadParticles(InputStream inputStream) throws IOException {
/* 34 */     Spatial spatial = (Spatial)BinaryImporter.getInstance().load(inputStream);
/* 35 */     spatial.setRenderState((RenderState)zstate);
/* 36 */     return spatial;
/*    */   }
/*    */ 
/*    */   
/* 40 */   private static ZBufferState zstate = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState(); static {
/* 41 */     zstate.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
/* 42 */     zstate.setEnabled(true);
/* 43 */     zstate.setWritable(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\JmeParticleLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */