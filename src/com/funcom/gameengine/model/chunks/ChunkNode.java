/*     */ package com.funcom.gameengine.model.chunks;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.DrawPassType;
/*     */ import com.funcom.gameengine.jme.LayeredElement;
/*     */ import com.funcom.gameengine.jme.PassDrawable;
/*     */ import com.funcom.gameengine.model.token.SteppedToken;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.view.ContentIndentifiable;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Plane;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ChunkNode
/*     */   extends Node
/*     */   implements TokenTargetNode, PassDrawable
/*     */ {
/*  39 */   private static final Logger LOGGER = Logger.getLogger(ChunkNode.class.getName());
/*  40 */   protected static final Plane plane = new Plane(new Vector3f(0.0F, 1.0F, 0.0F), 0.0F);
/*     */   
/*     */   private WorldCoordinate chunkOrigin;
/*     */   
/*     */   private int width;
/*     */   
/*     */   private int height;
/*     */   private String resourceName;
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private Point chunkNumber;
/*     */   private RectangleWC bounds;
/*     */   private transient LineNode lineRoot;
/*     */   private ExposedSpatialList tiles;
/*     */   private List<Spatial> decals;
/*     */   protected ExposedSpatialList others;
/*     */   private List<LayeredList> decalLayeredLists;
/*     */   private boolean resortDecalsByLayer;
/*     */   private boolean pathingGridGenerated;
/*     */   private static boolean drawTiles = true;
/*     */   
/*     */   public ChunkNode(WorldCoordinate chunkOrigin) {
/*  61 */     super("ChunkNode");
/*  62 */     this.bounds = new RectangleWC();
/*  63 */     setChunkOrigin(chunkOrigin);
/*     */     
/*  65 */     this.tiles = new ExposedSpatialList();
/*  66 */     this.decals = new ArrayList<Spatial>();
/*  67 */     this.others = new ExposedSpatialList();
/*     */     
/*  69 */     ArrayList<Spatial> spatials = new BucketArrayList(1);
/*  70 */     this.children = Collections.synchronizedList(spatials);
/*  71 */     this.decalLayeredLists = new ArrayList<LayeredList>();
/*  72 */     this.pathingGridGenerated = false;
/*     */   }
/*     */   
/*     */   public void attachStaticChild(Spatial child) {
/*  76 */     super.attachChild(child);
/*  77 */     child.updateGeometricState(0.0F, false);
/*  78 */     child.updateWorldVectors();
/*  79 */     child.lockMeshes();
/*  80 */     child.lockShadows();
/*     */   }
/*     */   
/*     */   public void attachAnimatedChild(Spatial child) {
/*  84 */     super.attachChild(child);
/*  85 */     child.updateGeometricState(0.0F, false);
/*  86 */     child.updateWorldVectors();
/*     */   }
/*     */   
/*     */   protected void attachChildLocal(Spatial child) {
/*  90 */     super.attachChild(child);
/*     */   }
/*     */   
/*     */   public int attachChild(Spatial child) {
/*  94 */     LOGGER.warn("You're not supposed to attach children manually to chunk node.");
/*  95 */     return super.attachChild(child);
/*     */   }
/*     */   
/*     */   public int attachChildAt(Spatial child, int index) {
/*  99 */     LOGGER.warn("You're not supposed to attach children manually to chunk node.");
/* 100 */     return super.attachChildAt(child, index);
/*     */   }
/*     */   
/*     */   public Point getChunkNumber() {
/* 104 */     return this.chunkNumber;
/*     */   }
/*     */   
/*     */   public void setSize(int width, int height) {
/* 108 */     this.width = width;
/* 109 */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 113 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 117 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setChunkOrigin(WorldCoordinate chunkOrigin) {
/* 121 */     this.chunkOrigin = chunkOrigin;
/* 122 */     this.chunkNumber = new Point((chunkOrigin.getTileCoord()).x / 20, (chunkOrigin.getTileCoord()).y / 20);
/*     */ 
/*     */ 
/*     */     
/* 126 */     setName(this.chunkNumber.x + "_" + this.chunkNumber.y);
/*     */     
/* 128 */     this.bounds.setRect(chunkOrigin, (new WorldCoordinate(chunkOrigin)).addTiles(20, 20));
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldCoordinate getChunkOrigin() {
/* 133 */     return this.chunkOrigin;
/*     */   }
/*     */   
/*     */   public Point getChunkTileOrigin() {
/* 137 */     return this.chunkOrigin.getTileCoord();
/*     */   }
/*     */   
/*     */   public void setResourceName(String resourceName) {
/* 141 */     this.resourceName = resourceName;
/*     */   }
/*     */   
/*     */   public String getResourceName() {
/* 145 */     return this.resourceName;
/*     */   }
/*     */   
/*     */   public void setChunkWorldNode(ChunkWorldNode chunkWorldNode) {
/* 149 */     this.chunkWorldNode = chunkWorldNode;
/*     */   }
/*     */   
/*     */   public ChunkWorldNode getChunkWorldNode() {
/* 153 */     return this.chunkWorldNode;
/*     */   }
/*     */   
/*     */   public void setLineRoot(LineNode lineRoot) {
/* 157 */     this.lineRoot = lineRoot;
/*     */   }
/*     */   
/*     */   public LineNode getLineRoot() {
/* 161 */     return this.lineRoot;
/*     */   }
/*     */   
/*     */   public void destroyChunk() {
/* 165 */     TokenRegister.instance().addToken((Token)new SteppedDestroyChunkToken(this));
/*     */   }
/*     */   
/*     */   protected void detachEntireTree(Spatial spatial) {
/* 169 */     detachTree(spatial, -1);
/*     */   }
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
/*     */   private static int detachTree(Spatial root, int maxDetachCount) {
/* 182 */     if (root instanceof Node) {
/* 183 */       Node node = (Node)root;
/* 184 */       int childCount = node.getQuantity();
/* 185 */       if (childCount > 0) {
/* 186 */         List<Spatial> nodeChildren = node.getChildren();
/* 187 */         for (int i = childCount - 1; maxDetachCount != 0 && i >= 0; i--) {
/*     */           
/* 189 */           if (i >= nodeChildren.size()) {
/* 190 */             i = nodeChildren.size() - 1;
/* 191 */             if (i <= -1) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 196 */           Spatial child = nodeChildren.get(i);
/* 197 */           maxDetachCount = detachTree(child, maxDetachCount);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     if (maxDetachCount != 0) {
/* 203 */       root.unlock();
/* 204 */       root.removeFromParent();
/*     */       
/* 206 */       return maxDetachCount - 1;
/*     */     } 
/*     */     
/* 209 */     return 0;
/*     */   }
/*     */   
/*     */   public void updateNodeTranslation() {
/* 213 */     int x = (this.chunkOrigin.getTileCoord()).x - WorldOrigin.instance().getX();
/* 214 */     int y = (this.chunkOrigin.getTileCoord()).y - WorldOrigin.instance().getY();
/* 215 */     setLocalTranslation(x, 0.0F, y);
/*     */   }
/*     */   
/*     */   public RectangleWC getBoundsWC() {
/* 219 */     return this.bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<LineWCHeight> getCollisionLines(RectangleWC rect) {
/* 231 */     return this.lineRoot.getLines(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<LineWC> getAllCollisionLines() {
/* 242 */     return this.lineRoot.getAllLines();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 246 */     return super.toString() + "[tx=" + (this.chunkOrigin.getTileCoord()).x + ",ty=" + (this.chunkOrigin.getTileCoord()).y + ",lt=" + getLocalTranslation() + ",wt=" + getWorldTranslation() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawChild(Renderer r, DrawPassState drawPassState) {
/*     */     int layerIndex;
/* 255 */     switch (drawPassState.getType()) {
/*     */       case MAP_CONTENT_BELOW_GROUND:
/* 257 */         onDrawSpatials(r, this.others, Boolean.FALSE);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case TILES:
/* 263 */         if (drawTiles) {
/* 264 */           onDrawSpatials(r, this.tiles, (Boolean)null);
/*     */         }
/*     */         break;
/*     */       
/*     */       case DECALS:
/* 269 */         ensureSortedDecalLayers();
/*     */         
/* 271 */         layerIndex = drawPassState.getStateIndex();
/* 272 */         if (layerIndex < this.decalLayeredLists.size()) {
/* 273 */           LayeredList spatialList = this.decalLayeredLists.get(layerIndex);
/* 274 */           for (int i = spatialList.size() - 1; i >= 0; i--) {
/* 275 */             Spatial spatial = spatialList.get(i);
/* 276 */             if (spatial != null) {
/* 277 */               spatial.onDraw(r);
/*     */             }
/*     */           } 
/*     */           
/* 281 */           if (layerIndex < this.decalLayeredLists.size() - 1)
/*     */           {
/* 283 */             drawPassState.keepState();
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case MAP_CONTENT_ABOVE_GROUND:
/* 290 */         onDrawSpatials(r, this.others, Boolean.TRUE);
/*     */         break;
/*     */ 
/*     */       
/*     */       case TRANSPARENT_CONTENT:
/* 295 */         onDrawTransparent(r, this.others);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDrawTransparent(Renderer r, Collection<Spatial> spatialList) {
/* 303 */     Spatial[] tmp = spatialList.<Spatial>toArray(new Spatial[spatialList.size()]);
/* 304 */     for (Spatial spatial : tmp) {
/* 305 */       if (spatial instanceof RepresentationalNode && (
/* 306 */         (RepresentationalNode)spatial).getEffects().isTransparent()) {
/* 307 */         ZBufferState state = (ZBufferState)spatial.getRenderState(7);
/* 308 */         if (state != null && state.isWritable()) {
/* 309 */           state.setWritable(false);
/* 310 */           spatial.updateRenderState();
/*     */         } 
/* 312 */         spatial.onDraw(r);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onDrawSpatials(Renderer r, ExposedSpatialList spatialList, Boolean aboveGround) {
/* 319 */     onDrawSpatials(r, spatialList, aboveGround, true);
/*     */   }
/*     */   
/*     */   protected void onDrawSpatials(Renderer r, ExposedSpatialList spatialList, Boolean aboveGround, boolean checkForTransparency) {
/* 323 */     Spatial[] spatials = spatialList.getInternalArray();
/* 324 */     int size = spatialList.size();
/* 325 */     if (size >= spatials.length) {
/* 326 */       size = spatials.length;
/*     */     }
/*     */ 
/*     */     
/* 330 */     for (int i = size - 1; i >= 0; i--) {
/* 331 */       Spatial spatial = spatials[i];
/* 332 */       if (spatial != null) {
/* 333 */         if (aboveGround != null) {
/* 334 */           if (checkForTransparency && spatial instanceof RepresentationalNode && ((RepresentationalNode)spatial).getEffects().isTransparent()) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 339 */           BoundingVolume volume = spatial.getWorldBound();
/* 340 */           if (volume == null) {
/* 341 */             spatial.updateWorldBound();
/* 342 */             volume = spatial.getWorldBound();
/*     */           } 
/* 344 */           if (volume != null) {
/* 345 */             Plane.Side side = volume.whichSide(plane);
/* 346 */             if (!aboveGround.booleanValue() ? (
/* 347 */               side != Plane.Side.NEGATIVE) : (
/*     */ 
/*     */ 
/*     */               
/* 351 */               side == Plane.Side.NEGATIVE)) {
/*     */               continue;
/*     */             }
/*     */           }
/* 355 */           else if (aboveGround == Boolean.FALSE) {
/*     */             continue;
/*     */           } 
/*     */         } 
/* 359 */         spatial.onDraw(r);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   private void ensureSortedDecalLayers() {
/* 365 */     if (this.resortDecalsByLayer || this.decalLayeredLists.isEmpty()) {
/* 366 */       this.decalLayeredLists.clear();
/*     */       
/* 368 */       for (Spatial decal : this.decals) {
/*     */         
/* 370 */         int layerId = Integer.MAX_VALUE;
/* 371 */         if (decal instanceof LayeredElement) {
/* 372 */           layerId = ((LayeredElement)decal).getLayerId();
/*     */         }
/*     */         
/* 375 */         LayeredList targetList = null;
/* 376 */         for (int i = this.decalLayeredLists.size() - 1; i >= 0; i--) {
/* 377 */           LayeredList tmplist = this.decalLayeredLists.get(i);
/* 378 */           if (tmplist.getLayerId() == layerId) {
/* 379 */             targetList = tmplist;
/*     */             break;
/*     */           } 
/*     */         } 
/* 383 */         if (targetList == null) {
/* 384 */           targetList = new LayeredList(layerId);
/* 385 */           this.decalLayeredLists.add(targetList);
/*     */         } 
/*     */         
/* 388 */         targetList.add(decal);
/*     */       } 
/* 390 */       Collections.sort(this.decalLayeredLists);
/*     */       
/* 392 */       this.resortDecalsByLayer = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 397 */     Spatial.CullHint cm = getCullHint();
/* 398 */     if (cm == Spatial.CullHint.Always) {
/* 399 */       setLastFrustumIntersection(Camera.FrustumIntersect.Outside); return;
/*     */     } 
/* 401 */     if (cm == Spatial.CullHint.Never) {
/* 402 */       setLastFrustumIntersection(Camera.FrustumIntersect.Intersects);
/* 403 */       drawChild(r, drawPassState);
/*     */       
/*     */       return;
/*     */     } 
/* 407 */     Camera camera = r.getCamera();
/* 408 */     int state = camera.getPlaneState();
/*     */ 
/*     */     
/* 411 */     this.frustrumIntersects = (this.parent != null) ? this.parent.getLastFrustumIntersection() : Camera.FrustumIntersect.Intersects;
/*     */ 
/*     */     
/* 414 */     if (cm == Spatial.CullHint.Dynamic && this.frustrumIntersects == Camera.FrustumIntersect.Intersects)
/*     */     {
/* 416 */       this.frustrumIntersects = camera.contains(this.worldBound);
/*     */     }
/*     */     
/* 419 */     if (this.frustrumIntersects != Camera.FrustumIntersect.Outside) {
/* 420 */       drawChild(r, drawPassState);
/*     */     }
/* 422 */     camera.setPlaneState(state);
/*     */   }
/*     */   
/*     */   protected void bucketClear() {
/* 426 */     this.tiles.clear();
/* 427 */     this.decals.clear();
/* 428 */     this.others.clear();
/* 429 */     this.resortDecalsByLayer = true;
/*     */   }
/*     */   
/*     */   protected void bucketRemove(Spatial spatial) {
/* 433 */     if (spatial != null) {
/* 434 */       this.tiles.remove(spatial);
/* 435 */       if (this.decals.remove(spatial)) {
/* 436 */         this.resortDecalsByLayer = true;
/*     */       }
/* 438 */       this.others.remove(spatial);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bucketAdd(Spatial spatial) {
/* 443 */     if (spatial instanceof ContentIndentifiable) {
/* 444 */       int type = ((ContentIndentifiable)spatial).getContentType();
/* 445 */       switch (type) {
/*     */         case -1:
/*     */           return;
/*     */         
/*     */         case 8:
/* 450 */           this.decals.add(spatial);
/* 451 */           this.resortDecalsByLayer = true;
/*     */ 
/*     */         
/*     */         case 1:
/* 455 */           this.tiles.add(spatial);
/*     */       } 
/*     */ 
/*     */       
/* 459 */       addToOthers(spatial);
/*     */     } 
/*     */     
/* 462 */     if (spatial != null) {
/* 463 */       if (spatial instanceof LayeredElement) {
/* 464 */         this.decals.add(spatial);
/* 465 */         this.resortDecalsByLayer = true;
/*     */       } 
/* 467 */       addToOthers(spatial);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addToOthers(Spatial spatial) {
/* 473 */     this.others.add(spatial);
/*     */   }
/*     */   
/*     */   public WorldCoordinate getChunkExtent() {
/* 477 */     return getBoundsWC().getExtent();
/*     */   }
/*     */   
/*     */   public static void setDrawTiles(boolean b) {
/* 481 */     drawTiles = b;
/*     */   }
/*     */   
/*     */   protected class BucketArrayList extends ArrayList<Spatial> {
/*     */     public BucketArrayList(int initialCapacity) {
/* 486 */       super(initialCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(Spatial spatial) {
/* 491 */       ChunkNode.this.bucketAdd(spatial);
/* 492 */       return super.add(spatial);
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, Spatial element) {
/* 497 */       ChunkNode.this.bucketAdd(element);
/* 498 */       super.add(index, element);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Spatial set(int index, Spatial element) {
/* 504 */       Spatial old = super.set(index, element);
/*     */       
/* 506 */       ChunkNode.this.bucketAdd(element);
/* 507 */       ChunkNode.this.bucketRemove(old);
/*     */       
/* 509 */       return old;
/*     */     }
/*     */ 
/*     */     
/*     */     public Spatial remove(int index) {
/* 514 */       Spatial old = super.remove(index);
/* 515 */       ChunkNode.this.bucketRemove(old);
/* 516 */       return old;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 521 */       if (o instanceof Spatial) {
/* 522 */         ChunkNode.this.bucketRemove((Spatial)o);
/*     */       }
/* 524 */       return super.remove(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 529 */       ChunkNode.this.bucketClear();
/* 530 */       super.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Spatial> c) {
/* 535 */       for (Spatial spatial : c) {
/* 536 */         ChunkNode.this.bucketAdd(spatial);
/*     */       }
/* 538 */       return super.addAll(c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Spatial> c) {
/* 543 */       for (Spatial spatial : c) {
/* 544 */         ChunkNode.this.bucketAdd(spatial);
/*     */       }
/* 546 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void removeRange(int fromIndex, int toIndex) {
/* 551 */       if (fromIndex < 0 || toIndex > size()) {
/* 552 */         throw new ArrayIndexOutOfBoundsException();
/*     */       }
/*     */       
/* 555 */       for (int i = fromIndex; i < toIndex; i++) {
/* 556 */         ChunkNode.this.bucketRemove(get(i));
/*     */       }
/* 558 */       super.removeRange(fromIndex, toIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 563 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LayeredList extends ArrayList<Spatial> implements Comparable {
/*     */     private int layerId;
/*     */     
/*     */     public LayeredList(int layerId) {
/* 571 */       this.layerId = layerId;
/*     */     }
/*     */     
/*     */     public int getLayerId() {
/* 575 */       return this.layerId;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Object o) {
/* 580 */       LayeredList other = (LayeredList)o;
/*     */       
/* 582 */       return this.layerId - other.layerId;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SteppedDestroyChunkToken implements SteppedToken {
/*     */     private final ChunkNode chunkNode;
/*     */     
/*     */     public SteppedDestroyChunkToken(ChunkNode chunkNode) {
/* 590 */       this.chunkNode = chunkNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public Token.TokenType getTokenType() {
/* 595 */       return Token.TokenType.GAME_THREAD;
/*     */     }
/*     */ 
/*     */     
/*     */     public void process() {
/* 600 */       ChunkNode.detachTree((Spatial)this.chunkNode, 5);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFinished() {
/* 605 */       boolean noChildren = (this.chunkNode.getQuantity() == 0);
/* 606 */       boolean noParent = (this.chunkNode.getParent() == null);
/*     */       
/* 608 */       return (noChildren && noParent);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ChunkNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */