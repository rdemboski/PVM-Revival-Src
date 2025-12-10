/*     */ package com.funcom.gameengine.model.chunks;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.PassDrawable;
/*     */ import com.funcom.gameengine.pathfinding2.MapPathGraph;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkWorldNode
/*     */   extends Node
/*     */   implements PassDrawable
/*     */ {
/*  35 */   private static final Logger LOGGER = Logger.getLogger(ChunkWorldNode.class.getName());
/*     */   private static final int DEFAULT_VISIBLE_EXTENT = 2;
/*     */   private static final int DEFAULT_RENDER_EXTENT = 1;
/*  38 */   private static final boolean DEBUG_SHOW_LOADED_CHUNKS = "true".equalsIgnoreCase(System.getProperty("tcg.showloadedchunks"));
/*  39 */   private static final Comparator<ChunkNode> CHUNK_ORIGIN_COMPARATOR = new Comparator<ChunkNode>()
/*     */     {
/*     */       public int compare(ChunkNode o1, ChunkNode o2) {
/*  42 */         Point cn1 = o1.getChunkNumber();
/*  43 */         Point cn2 = o2.getChunkNumber();
/*     */         
/*  45 */         if (cn1.x > cn2.x)
/*  46 */           return 1; 
/*  47 */         if (cn1.x < cn2.x) {
/*  48 */           return -1;
/*     */         }
/*  50 */         if (cn1.y > cn2.y)
/*  51 */           return 1; 
/*  52 */         if (cn1.y < cn2.y) {
/*  53 */           return -1;
/*     */         }
/*  55 */         return 0;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private List<ChunkNode> chunks;
/*     */   
/*     */   private Set<Point> queuedChunks;
/*     */   private WorldCoordinate worldCoordinate;
/*     */   private Set<ChunkListener> listeners;
/*     */   private LineNode worldLineNode;
/*     */   private int visibleExtent;
/*     */   private int renderExtent;
/*     */   private int currentChunkX;
/*     */   private int currentChunkY;
/*     */   private ManagedChunkNode managedChunkNode;
/*     */   private ChunkWorldInfo chunkWorldInfo;
/*     */   private MapPathGraph pathGraph;
/*     */   private List<Spatial> childrenToRender;
/*     */   private JFrame chunkFrame;
/*     */   
/*     */   public ChunkWorldNode(String mapId) {
/*  77 */     super("ChunkWorldNode");
/*     */     
/*  79 */     this.worldCoordinate = new WorldCoordinate();
/*  80 */     this.chunks = new ArrayList<ChunkNode>(10);
/*  81 */     this.queuedChunks = new HashSet<Point>();
/*  82 */     this.worldLineNode = new LineNode(new WorldCoordinate(), new WorldCoordinate());
/*  83 */     this.chunkWorldInfo = new ChunkWorldInfo(mapId);
/*     */     
/*  85 */     this.renderExtent = 1;
/*     */     
/*  87 */     this.visibleExtent = 2;
/*  88 */     String extentString = System.getProperty("visibleExtent");
/*  89 */     if (extentString != null)
/*  90 */       this.visibleExtent = Integer.parseInt(extentString); 
/*  91 */     this.currentChunkX = 1073741823;
/*  92 */     this.currentChunkY = 1073741823;
/*     */     
/*  94 */     this.childrenToRender = new ArrayList<Spatial>();
/*     */     
/*  96 */     addChunkListener(new EnvironmentalParticleRemover());
/*     */   }
/*     */   
/*     */   public ChunkWorldInfo getChunkWorldInfo() {
/* 100 */     return this.chunkWorldInfo;
/*     */   }
/*     */   
/*     */   public void setSize(int width, int height) {
/* 104 */     this.chunkWorldInfo.setSize(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetWorldLineNode() {
/* 109 */     this.worldLineNode.setRect(new WorldCoordinate(), new WorldCoordinate(this.chunkWorldInfo.getWorldWidth(), this.chunkWorldInfo.getWorldHeight(), 0.0D, 0.0D, this.chunkWorldInfo.getMapId(), 0));
/*     */     
/* 111 */     this.worldLineNode.clearChildren();
/*     */   }
/*     */   
/*     */   public void addChunkListener(ChunkListener listener) {
/* 115 */     if (this.listeners == null) {
/* 116 */       this.listeners = new HashSet<ChunkListener>();
/*     */     }
/*     */     
/* 119 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeChunkListener(ChunkListener listener) {
/* 123 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   private void fireNewChunkListener(String chunkPath, WorldCoordinate chunkOrigin) {
/* 131 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 135 */     for (ChunkListener listener : this.listeners) {
/* 136 */       listener.newChunk(chunkPath, chunkOrigin, this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireAddedChunkListener(ChunkNode chunkNode) {
/* 141 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 145 */     for (ChunkListener listener : this.listeners) {
/* 146 */       listener.addedChunk(chunkNode);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireRemovedChunkListener(ChunkNode chunkNode) {
/* 151 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     for (ChunkListener listener : this.listeners) {
/* 156 */       listener.removedChunk(chunkNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBasePath(String basePath) {
/* 166 */     this.chunkWorldInfo.setBasePath(basePath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBasePath() {
/* 175 */     return this.chunkWorldInfo.getBasePath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChunkFilename(int x, int y) {
/* 186 */     return this.chunkWorldInfo.getChunkFilename(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChunkFilename(Point point) {
/* 196 */     return this.chunkWorldInfo.getChunkFilename(point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineNode getWorldLineNode() {
/* 206 */     return this.worldLineNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisibleExtent(int visibleExtent) {
/* 217 */     this.visibleExtent = visibleExtent;
/* 218 */     updateWorldChunks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVisibleExtent() {
/* 227 */     return this.visibleExtent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWorldLineNode() {
/* 235 */     this.worldLineNode.clearChildren();
/* 236 */     for (ChunkNode chunk : this.chunks) {
/* 237 */       this.worldLineNode.addChildNode(chunk.getLineRoot());
/*     */     }
/* 239 */     if (this.managedChunkNode != null) {
/* 240 */       this.worldLineNode.addChildNode(this.managedChunkNode.getLineRoot());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkNode getChunkAt(WorldCoordinate worldCoordinate) {
/* 251 */     return getChunkAtTile((worldCoordinate.getTileCoord()).x, (worldCoordinate.getTileCoord()).y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkNode getChunkAt(Point point) {
/* 261 */     for (ChunkNode chunk : this.chunks) {
/* 262 */       if (chunk.getChunkNumber().equals(point))
/* 263 */         return chunk; 
/*     */     } 
/* 265 */     return null;
/*     */   }
/*     */   
/*     */   public ChunkNode getBottomLeftChunk() {
/* 269 */     Collections.sort(this.chunks, CHUNK_ORIGIN_COMPARATOR);
/* 270 */     return this.chunks.get(0);
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
/*     */   public ChunkNode getChunkAtTile(int x, int y) {
/* 282 */     int cx = (int)Math.floor(x / this.chunkWorldInfo.getChunkWidth());
/* 283 */     int cy = (int)Math.floor(y / this.chunkWorldInfo.getChunkHeight());
/* 284 */     return getChunkAt(new Point(cx, cy));
/*     */   }
/*     */   
/*     */   public int getNumberOfActiveChunks() {
/* 288 */     return this.chunks.size();
/*     */   }
/*     */   
/*     */   public int getWorldWidth() {
/* 292 */     return this.chunkWorldInfo.getWorldWidth();
/*     */   }
/*     */   
/*     */   public int getWorldHeight() {
/* 296 */     return this.chunkWorldInfo.getWorldHeight();
/*     */   }
/*     */   
/*     */   public int getWorldChunkWidth() {
/* 300 */     return this.chunkWorldInfo.getWorldChunkWidth();
/*     */   }
/*     */   
/*     */   public int getWorldChunkHeight() {
/* 304 */     return this.chunkWorldInfo.getWorldChunkHeight();
/*     */   }
/*     */   
/*     */   public WorldCoordinate getWorldSize() {
/* 308 */     return this.chunkWorldInfo.getWorldSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearWorldChunks() {
/* 313 */     for (ChunkNode chunk : this.chunks) {
/* 314 */       removeChunk(chunk);
/*     */     }
/* 316 */     clearKnownChunks();
/*     */   }
/*     */   
/*     */   protected void clearKnownChunks() {
/* 320 */     this.chunks.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCoordinateReference(WorldCoordinate worldCoordinate) {
/* 330 */     this.worldCoordinate = worldCoordinate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChunk(ChunkNode chunk) {
/* 341 */     if (chunkExists(chunk.getChunkNumber())) {
/* 342 */       throw new RuntimeException("Chunk already exists at given position");
/*     */     }
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
/* 355 */     chunk.setChunkWorldNode(this);
/* 356 */     removeChunkFromLoading(chunk.getChunkNumber());
/* 357 */     this.chunks.add(chunk);
/* 358 */     fireAddedChunkListener(chunk);
/*     */     
/* 360 */     attachChild((Spatial)chunk);
/*     */     
/* 362 */     realignChunks();
/*     */ 
/*     */     
/* 365 */     chunk.updateRenderState();
/*     */     
/* 367 */     if (this.chunkFrame != null) {
/* 368 */       this.chunkFrame.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkWorldOriginUpdate() {
/* 380 */     int newChunkX = (int)Math.floor((this.worldCoordinate.getTileCoord()).x / this.chunkWorldInfo.getChunkWidth());
/* 381 */     int newChunkY = (int)Math.floor((this.worldCoordinate.getTileCoord()).y / this.chunkWorldInfo.getChunkHeight());
/*     */     
/* 383 */     if (newChunkX != this.currentChunkX || newChunkY != this.currentChunkY) {
/* 384 */       LOGGER.info("chunk transition: " + this.currentChunkX + "," + this.currentChunkY + " -> " + newChunkX + "," + newChunkY + " (" + this.worldCoordinate.getTileCoord() + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       WorldOrigin.instance().set(newChunkX * this.chunkWorldInfo.getChunkWidth(), newChunkY * this.chunkWorldInfo.getChunkHeight());
/*     */ 
/*     */       
/* 392 */       this.currentChunkX = newChunkX;
/* 393 */       this.currentChunkY = newChunkY;
/*     */       
/* 395 */       updateWorldChunks();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWorldChunks() {
/* 404 */     removeDiscardableChunks();
/* 405 */     addNeededChunks(this.currentChunkX, this.currentChunkY);
/* 406 */     realignChunks();
/*     */     
/* 408 */     if (DEBUG_SHOW_LOADED_CHUNKS && this.chunkFrame == null) {
/*     */       
/* 410 */       this.chunkFrame = new JFrame("Chunks");
/* 411 */       JComponent c = new JComponent()
/*     */         {
/*     */           protected void paintComponent(Graphics g) {
/* 414 */             int mul = 5;
/* 415 */             for (ChunkNode chunk : ChunkWorldNode.this.chunks) {
/* 416 */               Point point = chunk.getChunkNumber();
/* 417 */               g.drawRect(point.x * mul, point.y * mul, mul, mul);
/*     */             } 
/*     */           }
/*     */         };
/* 421 */       this.chunkFrame.getContentPane().add(c);
/* 422 */       this.chunkFrame.setSize(400, 400);
/* 423 */       this.chunkFrame.setAlwaysOnTop(true);
/* 424 */       this.chunkFrame.setVisible(true);
/*     */     } 
/*     */     
/* 427 */     if (this.chunkFrame != null) {
/* 428 */       this.chunkFrame.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeDiscardableChunks() {
/* 434 */     int chunkWidth = this.chunkWorldInfo.getChunkWidth();
/* 435 */     int chunkHeight = this.chunkWorldInfo.getChunkHeight();
/* 436 */     int refX = (this.worldCoordinate.getTileCoord()).x;
/* 437 */     int refY = (this.worldCoordinate.getTileCoord()).y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 442 */     int maxDistanceX = chunkWidth * this.visibleExtent + chunkWidth * 3 / 4;
/* 443 */     int maxDistanceY = chunkHeight * this.visibleExtent + chunkHeight * 3 / 4;
/*     */     
/* 445 */     Iterator<ChunkNode> chunkIterator = this.chunks.iterator();
/* 446 */     while (chunkIterator.hasNext()) {
/* 447 */       ChunkNode chunk = chunkIterator.next();
/* 448 */       Point point = chunk.getChunkNumber();
/* 449 */       int chunkCenterX = point.x * chunkWidth + chunkWidth / 2;
/* 450 */       int chunkCenterY = point.y * chunkHeight + chunkHeight / 2;
/*     */       
/* 452 */       int distX = chunkCenterX - refX;
/* 453 */       int distY = chunkCenterY - refY;
/*     */       
/* 455 */       if (Math.abs(distX) > maxDistanceX || Math.abs(distY) > maxDistanceY) {
/* 456 */         removeChunk(chunk);
/* 457 */         chunkIterator.remove();
/* 458 */         LOGGER.info("Disposing chunk: " + chunk.getChunkNumber());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeChunk(ChunkNode chunk) {
/* 487 */     detachChild((Spatial)chunk);
/* 488 */     fireRemovedChunkListener(chunk);
/* 489 */     chunk.destroyChunk();
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
/*     */   protected void addNeededChunks(int chunkX, int chunkY) {
/* 501 */     if (this.chunkWorldInfo.getWorldWidth() == 0) {
/*     */       return;
/*     */     }
/* 504 */     for (int loadRadius = 0; loadRadius <= this.visibleExtent; loadRadius++) {
/* 505 */       for (int x = chunkX - loadRadius; x <= chunkX + loadRadius; x++) {
/* 506 */         for (int y = chunkY - loadRadius; y <= chunkY + loadRadius; y++) {
/* 507 */           Point point = new Point(x, y);
/* 508 */           if (!chunkExists(point) && !this.queuedChunks.contains(point) && x >= 0 && y >= 0 && x < this.chunkWorldInfo.getWorldChunkWidth() && y < this.chunkWorldInfo.getWorldChunkHeight()) {
/*     */ 
/*     */ 
/*     */             
/* 512 */             LOGGER.info("Loading chunk: " + point);
/* 513 */             addChunkForLoading(point);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean chunkExists(Point point) {
/* 521 */     return (getChunkAt(point) != null);
/*     */   }
/*     */   
/*     */   protected boolean isChunkNeeded(Point point) {
/* 525 */     if (chunkExists(point)) {
/* 526 */       return false;
/*     */     }
/*     */     
/* 529 */     if (this.queuedChunks.contains(point)) {
/* 530 */       return false;
/*     */     }
/*     */     
/* 533 */     return (point.x >= this.currentChunkX - this.visibleExtent && point.y >= this.currentChunkY - this.visibleExtent && point.x <= this.currentChunkX + this.visibleExtent && point.y <= this.currentChunkY + this.visibleExtent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addChunkForLoading(Point point) {
/* 539 */     this.queuedChunks.add(point);
/* 540 */     WorldCoordinate chunkOrigin = new WorldCoordinate(point.x * 20, point.y * 20, 0.0D, 0.0D, this.chunkWorldInfo.getMapId(), 0);
/*     */     
/* 542 */     fireNewChunkListener(getChunkFilename(point), chunkOrigin);
/*     */   }
/*     */   
/*     */   protected void removeChunkFromLoading(Point point) {
/* 546 */     this.queuedChunks.remove(point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void realignChunks() {
/* 556 */     if (this.chunkWorldInfo.getWorldWidth() != 0) {
/*     */       
/* 558 */       this.childrenToRender.clear();
/*     */       
/* 560 */       int loadRadius = this.renderExtent;
/* 561 */       for (int x = this.currentChunkX - loadRadius; x <= this.currentChunkX + loadRadius; x++) {
/* 562 */         for (int y = this.currentChunkY - loadRadius; y <= this.currentChunkY + loadRadius; y++) {
/* 563 */           if (x >= 0 && y >= 0) {
/* 564 */             Point point = new Point(x, y);
/* 565 */             ChunkNode chunk = getChunkAt(point);
/* 566 */             if (chunk != null) {
/* 567 */               chunk.updateNodeTranslation();
/* 568 */               this.childrenToRender.add(chunk);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 575 */       for (ChunkNode chunk : this.chunks) {
/* 576 */         chunk.updateNodeTranslation();
/*     */       }
/*     */     } 
/* 579 */     updateWorldLineNode();
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
/*     */   public Set<LineWC> getCollisionLines(RectangleWC rect) {
/* 591 */     Set<LineWC> result = new HashSet<LineWC>();
/*     */     
/* 593 */     for (ChunkNode chunk : this.chunks) {
/* 594 */       RectangleWC bounds = chunk.getBoundsWC();
/* 595 */       if (rect.intersects(bounds)) {
/* 596 */         Set<LineWCHeight> lines = chunk.getCollisionLines(rect);
/* 597 */         result.addAll(lines);
/*     */       } 
/*     */     } 
/*     */     
/* 601 */     if (this.managedChunkNode != null) {
/* 602 */       result.addAll(this.managedChunkNode.getCollisionLines(rect));
/*     */     }
/*     */     
/* 605 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<LineWC> getAllAvailableCollisionLines() {
/* 615 */     Set<LineWC> result = new HashSet<LineWC>();
/*     */     
/* 617 */     for (ChunkNode chunk : this.chunks) {
/* 618 */       result.addAll(chunk.getAllCollisionLines());
/*     */     }
/*     */     
/* 621 */     if (this.managedChunkNode != null) {
/* 622 */       result.addAll(this.managedChunkNode.getAllCollisionLines());
/*     */     }
/*     */     
/* 625 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ChunkNode> getCurrentChunks() {
/* 634 */     return Collections.unmodifiableCollection(this.chunks);
/*     */   }
/*     */   
/*     */   public void setManagedChunkNode(ManagedChunkNode managedChunkNode) {
/* 638 */     if (this.managedChunkNode != null) {
/* 639 */       detachChild((Spatial)this.managedChunkNode);
/*     */     }
/* 641 */     attachChild((Spatial)managedChunkNode);
/*     */ 
/*     */     
/* 644 */     this.managedChunkNode = managedChunkNode;
/*     */   }
/*     */   
/*     */   public ManagedChunkNode getManagedChunkNode() {
/* 648 */     return this.managedChunkNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(Renderer r) {
/* 653 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 658 */     if (this.childrenToRender.size() > 0) {
/* 659 */       drawPassState.onDrawCurrentState(r, this.childrenToRender);
/*     */     } else {
/*     */       
/* 662 */       drawPassState.onDrawCurrentState(r, this.children);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected DrawPassState newPassState() {
/* 667 */     return new DrawPassState();
/*     */   }
/*     */   
/*     */   public int getQueuedChunksSize() {
/* 671 */     return this.queuedChunks.size();
/*     */   }
/*     */   
/*     */   public void invalidateCurrentChunkPos() {
/* 675 */     this.currentChunkX = -1;
/* 676 */     this.currentChunkY = -1;
/*     */   }
/*     */   
/*     */   public void clearQueuedChunks() {
/* 680 */     this.queuedChunks.clear();
/*     */   }
/*     */   
/*     */   public void setPathGraph(MapPathGraph pathGraph) {
/* 684 */     this.pathGraph = pathGraph;
/*     */   }
/*     */   
/*     */   public MapPathGraph getPathGraph() {
/* 688 */     return this.pathGraph;
/*     */   }
/*     */   
/*     */   public boolean isPathgraphAvailable() {
/* 692 */     return (getPathGraph() != null);
/*     */   }
/*     */   
/*     */   public int getMapInstanceId() {
/* 696 */     return this.worldCoordinate.getInstanceReference();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ChunkWorldNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */