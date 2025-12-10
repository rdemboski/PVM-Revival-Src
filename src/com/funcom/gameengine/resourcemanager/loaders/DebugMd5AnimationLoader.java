/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.net.MalformedURLException;
/*    */ 
/*    */ public class DebugMd5AnimationLoader extends Md5AnimationLoader {
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 14 */     InputStream inputStream = null;
/*    */     try {
/* 16 */       String path = managedResource.getName();
/* 17 */       File file = new File(System.getProperty("java.io.tmpdir") + "/pvm.tmp", path + ".bin");
/*    */       
/* 19 */       if (file.exists()) {
/* 20 */         ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
/* 21 */         JointAnimation anim = (JointAnimation)in.readObject();
/* 22 */         in.close();
/*    */         
/* 24 */         managedResource.setResource(anim);
/*    */       } else {
/* 26 */         super.loadData(managedResource);
/*    */         
/* 28 */         JointAnimation jointAnimation = (JointAnimation)managedResource.getResource();
/*    */         
/* 30 */         file.getParentFile().mkdirs();
/* 31 */         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
/* 32 */         out.writeObject(jointAnimation);
/* 33 */         out.close();
/*    */       } 
/* 35 */     } catch (MalformedURLException e) {
/* 36 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 37 */     } catch (IOException e) {
/* 38 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 39 */     } catch (ClassNotFoundException e) {
/* 40 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 42 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\DebugMd5AnimationLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */