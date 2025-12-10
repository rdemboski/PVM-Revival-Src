/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.chunks.ChunkListener;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistanceLoadingTokenQueue<E>
/*     */   implements ChunkListener
/*     */ {
/*  20 */   private int mLastNearestDistance = Integer.MAX_VALUE;
/*  21 */   private int lastX = 0;
/*  22 */   private int lastY = 0;
/*     */   private List<TokenHolder> mQueue;
/*     */   
/*     */   public DistanceLoadingTokenQueue() {
/*  26 */     this.mQueue = Collections.synchronizedList(new ArrayList<TokenHolder>());
/*     */   }
/*     */   
/*     */   public int size() {
/*  30 */     return this.mQueue.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  34 */     return this.mQueue.isEmpty();
/*     */   }
/*     */   
/*     */   public void add(E token, int x, int y, Object node) {
/*  38 */     this.mQueue.add(new TokenHolder(token, x, y, node));
/*     */   }
/*     */   
/*     */   public void clear() {
/*  42 */     this.mQueue.clear();
/*     */   }
/*     */   
/*     */   public E getToken() {
/*  46 */     E token = null;
/*  47 */     if (!this.mQueue.isEmpty()) {
/*  48 */       TokenHolder holder = this.mQueue.remove(0);
/*  49 */       token = holder.mObj;
/*     */     } 
/*  51 */     return token;
/*     */   }
/*     */ 
/*     */   
/*     */   public E getNearestToken(final int x, final int y) {
/*  56 */     if (this.mQueue.isEmpty()) {
/*  57 */       return null;
/*     */     }
/*  59 */     if (Math.abs(x - this.lastX) + Math.abs(y - this.lastY) > 2) {
/*  60 */       this.lastX = x;
/*  61 */       this.lastY = y;
/*  62 */       synchronized (this.mQueue) {
/*  63 */         Collections.sort(this.mQueue, new Comparator<TokenHolder>()
/*     */             {
/*     */               public int compare(DistanceLoadingTokenQueue<E>.TokenHolder o1, DistanceLoadingTokenQueue<E>.TokenHolder o2) {
/*  66 */                 int nDist = Math.abs(x - o1.x) + Math.abs(y - o1.y);
/*  67 */                 int nDist2 = Math.abs(x - o2.x) + Math.abs(y - o2.y);
/*  68 */                 return nDist - nDist2;
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*  73 */     return ((TokenHolder)this.mQueue.remove(0)).mObj;
/*     */   }
/*     */   
/*     */   public int getLastNearestDistance() {
/*  77 */     return this.mLastNearestDistance;
/*     */   }
/*     */   
/*     */   public class TokenHolder {
/*  81 */     public E mObj = null;
/*     */     public int x;
/*     */     public int y;
/*     */     Object node;
/*     */     
/*     */     public TokenHolder(E Obj, int x, int y, Object node) {
/*  87 */       this.mObj = Obj;
/*  88 */       this.x = x;
/*  89 */       this.y = y;
/*  90 */       this.node = node;
/*     */     }
/*     */     
/*     */     public int getX() {
/*  94 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/*  98 */       return this.y;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newChunk(String chunkPath, WorldCoordinate chunkOrigin, ChunkWorldNode chunkWorldNode) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedChunk(ChunkNode chunkNode) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedChunk(ChunkNode chunkNode) {
/* 114 */     TokenHolder dCurrentToken = null;
/* 115 */     synchronized (this.mQueue) {
/* 116 */       Iterator<TokenHolder> iter = this.mQueue.iterator();
/* 117 */       while (iter.hasNext()) {
/* 118 */         dCurrentToken = iter.next();
/*     */         
/* 120 */         if (dCurrentToken.node == chunkNode)
/* 121 */           iter.remove(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\DistanceLoadingTokenQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */