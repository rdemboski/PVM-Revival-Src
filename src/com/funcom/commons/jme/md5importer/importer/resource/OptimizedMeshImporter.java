/*    */ package com.funcom.commons.jme.md5importer.importer.resource;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*    */ import java.io.StreamTokenizer;
/*    */ 
/*    */ public class OptimizedMeshImporter
/*    */   extends MeshImporter
/*    */ {
/*    */   public OptimizedMeshImporter(StreamTokenizer reader, TextureFactory resourceLoader) {
/* 10 */     super(reader, resourceLoader);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void constructSkinMesh() {
/* 15 */     this.modelNode.setJoints(this.joints);
/* 16 */     this.modelNode.setMeshes(this.meshes);
/* 17 */     this.modelNode.initializeMeshes();
/* 18 */     this.joints = null;
/* 19 */     this.meshes = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\OptimizedMeshImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */