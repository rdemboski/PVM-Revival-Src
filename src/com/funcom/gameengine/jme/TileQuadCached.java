/*     */ package com.funcom.gameengine.jme;
/*     */ 
/*     */ import com.funcom.gameengine.view.ContentIndentifiable;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class TileQuadCached
/*     */   extends TileQuad
/*     */   implements ContentIndentifiable
/*     */ {
/*     */   private CachedTileData cachedTileData;
/*     */   
/*     */   public void setTileConfigData(CachedTileData cachedTileData) {
/*  19 */     this.cachedTileData = cachedTileData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lockMeshes(Renderer r) {
/*  24 */     if (getDisplayListID() == -1) {
/*  25 */       if (this.cachedTileData.hasDisplayListId()) {
/*  26 */         setDisplayListID(this.cachedTileData.getDisplayListId());
/*     */       } else {
/*  28 */         super.lockMeshes(r);
/*  29 */         this.cachedTileData.setDisplayListId(getDisplayListID());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockMeshes(Renderer r) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CachedTileData
/*     */   {
/*     */     private boolean initialized;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     private int displayListId = -1;
/*     */     private TextureState textureState;
/*     */     private ZBufferState zBufferState;
/*  58 */     private ArrayList<TexCoords> texBuf = null;
/*     */     
/*     */     public void init(TextureState textureState, ZBufferState zBufferState, ArrayList<TexCoords> texBuf) {
/*  61 */       if (this.initialized) {
/*  62 */         throw new IllegalStateException("already initialized");
/*     */       }
/*     */       
/*  65 */       this.textureState = textureState;
/*  66 */       this.zBufferState = zBufferState;
/*  67 */       this.initialized = true;
/*  68 */       this.texBuf = texBuf;
/*     */     }
/*     */     
/*     */     public void setDisplayListId(int displayListId) {
/*  72 */       this.displayListId = displayListId;
/*     */     }
/*     */     
/*     */     public boolean hasDisplayListId() {
/*  76 */       return (this.displayListId != -1);
/*     */     }
/*     */     
/*     */     public int getDisplayListId() {
/*  80 */       return this.displayListId;
/*     */     }
/*     */     
/*     */     public RenderState getTextureState() {
/*  84 */       return (RenderState)this.textureState;
/*     */     }
/*     */     
/*     */     public RenderState getZBufferState() {
/*  88 */       return (RenderState)this.zBufferState;
/*     */     }
/*     */     
/*     */     public boolean isInitialized() {
/*  92 */       return this.initialized;
/*     */     }
/*     */     
/*     */     public ArrayList<TexCoords> getTextureCoords() {
/*  96 */       return this.texBuf;
/*     */     }
/*     */     
/*     */     public TexCoords getTextureCoords(int textureUnit) {
/* 100 */       if (this.texBuf == null)
/* 101 */         return null; 
/* 102 */       if (textureUnit >= this.texBuf.size())
/* 103 */         return null; 
/* 104 */       return this.texBuf.get(textureUnit);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\TileQuadCached.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */