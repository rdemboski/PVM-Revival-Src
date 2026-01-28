/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.jme.scene.state.FragmentProgramState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ 
/*    */ public class FragmentShaderLoader
/*    */   extends AbstractLoader {
/*    */   public FragmentShaderLoader(Class<?> resourceType) {
/* 15 */     super(resourceType);
/*    */   }
/*    */ 
/*    */   @SuppressWarnings("unchecked")
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 20 */     BufferedInputStream bis = null;
/*    */     try {
/* 22 */       InputStream inputStream = getFileInputStream(managedResource.getName());
/* 23 */       bis = new BufferedInputStream(inputStream);
/* 24 */       byte[] data = new byte[bis.available()];
/* 25 */       bis.read(data);
/*    */       
/* 27 */       FragmentProgramState frag = DisplaySystem.getDisplaySystem().getRenderer().createFragmentProgramState();
/* 28 */       frag.load(new String(data));
/* 29 */       frag.setEnabled(true);
/*    */       
/* 31 */       ((ManagedResource<FragmentProgramState>)managedResource).setResource(frag);
/* 32 */     } catch (MalformedURLException e) {
/* 33 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 34 */     } catch (IOException e) {
/* 35 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 37 */       closeSafely(bis, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\FragmentShaderLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */