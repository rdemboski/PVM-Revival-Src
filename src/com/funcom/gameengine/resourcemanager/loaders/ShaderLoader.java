/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceLoader;
/*    */ import com.jme.scene.state.GLSLShaderObjectsState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.MalformedURLException;
/*    */ 
/*    */ public class ShaderLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   private static final String VERTEX_SHADER_TYPE = "VERT_SHADER";
/*    */   private static final String FRAGMENT_SHADER_TYPE = "FRAG_SHADER";
/*    */   
/*    */   public ShaderLoader(Class<?> resourceType) {
/* 22 */     super(resourceType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 27 */     BufferedReader bufferedReader = null;
/*    */     try {
/* 29 */       InputStream inputStream = getFileInputStream(managedResource.getName());
/* 30 */       bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*    */       
/* 32 */       String vertFile = bufferedReader.readLine();
/* 33 */       String fragFile = bufferedReader.readLine();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 39 */       GLSLShaderObjectsState so = DisplaySystem.getDisplaySystem().getRenderer().createGLSLShaderObjectsState();
/*    */ 
/*    */       
/* 42 */       managedResource.setResource(so);
/*    */     }
/* 44 */     catch (MalformedURLException e) {
/* 45 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 46 */     } catch (IOException e) {
/* 47 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 49 */       closeSafely(bufferedReader, managedResource);
/*    */     } 
/*    */   }
/*    */   
/*    */   private String readProgram(String programFile, Class<?> loaderType) throws LoadException {
/* 54 */     ResourceLoader loader = getAdditionalLoaderByType(loaderType);
/* 55 */     ManagedResource managedResource = new ManagedResource(programFile, loader.getResourceType(), CacheType.NOT_CACHED);
/* 56 */     loader.loadData(managedResource);
/* 57 */     return (String)managedResource.getResource();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\ShaderLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */