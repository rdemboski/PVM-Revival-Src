/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.jme.md5importer.MD5Config;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.importer.MD5Importer;
/*     */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Md5ModelLoader
/*     */   extends AbstractLoader
/*     */ {
/*     */   public static final String MAPPING_PREFIX = "MAP.";
/*     */   
/*     */   public Md5ModelLoader() {
/*  28 */     super(ModelNode.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startTiming() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void endTiming() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  44 */     startTiming();
/*  45 */     MD5Importer importer = MD5Importer.getInstance();
/*  46 */     InputStream inputStream = null;
/*     */     try {
/*  48 */       inputStream = getFileInputStream(managedResource.getName());
/*  49 */       TextureFactory resourceLoader = getResourceLoader(managedResource);
/*  50 */       importer.setResourceLoader(resourceLoader);
/*     */       
/*  52 */       importer.loadMesh(inputStream, managedResource.getName());
/*  53 */       ModelNode modelNode = importer.getModelNode();
/*     */ 
/*     */       
/*  56 */       if (MD5Config.normalGeneration) {
/*  57 */         modelNode.setNormalsMode(Spatial.NormalsMode.Inherit);
/*     */       } else {
/*  59 */         modelNode.setNormalsMode(Spatial.NormalsMode.Off);
/*     */       } 
/*     */       
/*  62 */       modelNode.setCullHint(Spatial.CullHint.Dynamic);
/*  63 */       modelNode.setRenderQueueMode(3);
/*  64 */       managedResource.setResource(modelNode);
/*  65 */       importer.cleanup();
/*  66 */     } catch (MalformedURLException e) {
/*  67 */       throw new LoadException(getResourceManager(), managedResource, e);
/*  68 */     } catch (IOException e) {
/*  69 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } finally {
/*  71 */       closeSafely(inputStream, managedResource);
/*     */     } 
/*  73 */     endTiming();
/*     */   }
/*     */   
/*     */   private TextureFactory getResourceLoader(ManagedResource<?> managedResource) {
/*  77 */     TextureFactory resourceLoader = (TextureFactory)managedResource.getParameter(TextureFactory.class, null);
/*  78 */     if (resourceLoader == null) {
/*  79 */       resourceLoader = new DirtyTextureFactory(managedResource.getName(), getResourceManager(), this);
/*     */     }
/*  81 */     return resourceLoader;
/*     */   }
/*     */   
/*     */   private static class DirtyTextureFactory implements TextureFactory {
/*     */     private Md5ModelLoader parentLoader;
/*     */     private String parentPath;
/*     */     private ResourceManager resourceManager;
/*     */     
/*     */     private DirtyTextureFactory(String modelPath, ResourceManager resourceManager, Md5ModelLoader parentLoader) {
/*  90 */       this.resourceManager = resourceManager;
/*  91 */       this.parentLoader = parentLoader;
/*  92 */       this.parentPath = FileUtils.getParentPath(modelPath) + "/";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public TextureState createTexture(String resourceName, String[] extensions, Texture.MinificationFilter minFilter, Texture.MagnificationFilter magFilter, float anisoLevel, boolean flipped) {
/*  98 */       if (resourceName.startsWith("MAP.")) {
/*  99 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       Texture texture = (Texture)this.resourceManager.getResource(Texture.class, this.parentPath + resourceName + ".png");
/* 115 */       if (MD5Config.textureWrapping)
/* 116 */         texture.setWrap(Texture.WrapMode.Repeat); 
/* 117 */       TextureState state = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 118 */       state.setTexture(texture);
/* 119 */       return state;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\Md5ModelLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */