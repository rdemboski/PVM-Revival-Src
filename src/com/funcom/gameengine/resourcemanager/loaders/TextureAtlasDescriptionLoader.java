/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.TextureAtlasDescription;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureAtlasDescriptionLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public TextureAtlasDescriptionLoader() {
/* 19 */     super(TextureAtlasDescription.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*    */     try {
/* 25 */       ByteBuffer buffer = (ByteBuffer)getResourceManager().getManagedResource(ByteBuffer.class, managedResource.getName()).getResource();
/*    */       
/* 27 */       TextureAtlasDescription description = new TextureAtlasDescription(buffer);
/* 28 */       managedResource.setResource(description);
/* 29 */     } catch (Exception e) {
/* 30 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\TextureAtlasDescriptionLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */