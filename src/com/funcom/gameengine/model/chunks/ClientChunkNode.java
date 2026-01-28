/*     */ package com.funcom.gameengine.model.chunks;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.DrawPassType;
/*     */ import com.funcom.gameengine.view.ContentIndentifiable;
/*     */ import com.funcom.gameengine.view.Effects;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ClientChunkNode
/*     */   extends ChunkNode {
/*     */   private final ExposedSpatialList othersTransparent;
/*     */   private final ExposedSpatialList othersNonTransparent;
/*     */   private final Node staticObjectNode;
/*     */   
/*     */   public ClientChunkNode(WorldCoordinate chunkOrigin) {
/*  22 */     super(chunkOrigin);
/*  23 */     this.staticObjectNode = new BucketArrayNode("static objects");
/*  24 */     this.staticObjectNode.setCullHint(Spatial.CullHint.Dynamic);
/*  25 */     attachChildLocal((Spatial)this.staticObjectNode);
/*     */     
/*  27 */     this.othersTransparent = new ExposedSpatialList();
/*  28 */     this.othersNonTransparent = new ExposedSpatialList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocalTranslation(float x, float y, float z) {
/*  33 */     Vector3f localTranslation = getLocalTranslation();
/*  34 */     if (localTranslation.x != x || localTranslation.y != y || localTranslation.z != z) {
/*     */ 
/*     */       
/*  37 */       super.setLocalTranslation(x, y, z);
/*  38 */       this.staticObjectNode.unlockBranch();
/*  39 */       this.staticObjectNode.updateGeometricState(0.0F, false);
/*  40 */       this.staticObjectNode.lockBranch();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void attachStaticChild(Spatial child) {
/*  46 */     this.staticObjectNode.attachChild(child);
/*  47 */     child.updateGeometricState(0.0F, false);
/*  48 */     child.updateWorldVectors();
/*  49 */     child.lockMeshes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawChild(Renderer r, DrawPassState drawPassState) {
/*  54 */     super.drawChild(r, drawPassState);
/*     */     
/*  56 */     switch (drawPassState.getType()) {
/*     */       case MAP_CONTENT_BELOW_GROUND:
/*  58 */         onDrawSpatials(r, this.othersNonTransparent, Boolean.FALSE, false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case MAP_CONTENT_ABOVE_GROUND:
/*  63 */         onDrawSpatials(r, this.othersNonTransparent, Boolean.TRUE, false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case TRANSPARENT_CONTENT:
/*  68 */         onDrawTransparent(r, this.othersTransparent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bucketClear() {
/*  75 */     super.bucketClear();
/*  76 */     this.othersTransparent.clear();
/*  77 */     this.othersNonTransparent.clear();
/*     */   }
/*     */   
/*     */   protected void bucketRemove(Spatial spatial) {
/*  81 */     super.bucketRemove(spatial);
/*  82 */     if (spatial != null) {
/*  83 */       this.othersTransparent.remove(spatial);
/*  84 */       this.othersNonTransparent.remove(spatial);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addToOthers(Spatial spatial) {
/*  89 */     List<Spatial> targetList = this.others;
/*  90 */     if (spatial instanceof RepresentationalNode) {
/*  91 */       Effects effects = ((RepresentationalNode)spatial).getEffects();
/*  92 */       if (effects.isTintLock()) {
/*  93 */         if (effects.isTransparent()) {
/*  94 */           targetList = this.othersTransparent;
/*     */         } else {
/*  96 */           targetList = this.othersNonTransparent;
/*     */         } 
/*     */       }
/*     */     } else {
/* 100 */       targetList = this.othersNonTransparent;
/*     */     } 
/*     */     
/* 103 */     targetList.add(spatial);
/*     */   }
/*     */   
/*     */   private class BucketArrayNode extends Node implements ContentIndentifiable {
/*     */     private BucketArrayNode(String s) {
/* 108 */       super(s);
/* 109 */       initialize();
/*     */     }
/*     */     
/*     */     private void initialize() {
/* 113 */       this.children = new ChunkNode.BucketArrayList(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getContentType() {
/* 118 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ClientChunkNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */