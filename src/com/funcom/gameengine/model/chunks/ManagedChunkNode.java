/*     */ package com.funcom.gameengine.model.chunks;
/*     */ 
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.OriginListener;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.DrawPassType;
/*     */ import com.funcom.gameengine.jme.PassDrawable;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.awt.Point;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class ManagedChunkNode
/*     */   extends Node implements PassDrawable, TokenTargetNode, OriginListener {
/*  31 */   private static final Logger LOGGER = Logger.getLogger(ManagedChunkNode.class.getName());
/*  32 */   private final Object SYNC_OBJECT = new Object();
/*     */   
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private Map<Point, Set<ManageableSpatial>> spatials;
/*     */   private LineNode lineRoot;
/*     */   
/*     */   public ManagedChunkNode(ChunkWorldNode chunkWorldNode) {
/*  39 */     super("Managed chunk node");
/*  40 */     this.chunkWorldNode = chunkWorldNode;
/*  41 */     this.spatials = new HashMap<Point, Set<ManageableSpatial>>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachManageableStaticChild(ManageableSpatial child) {
/*  51 */     child.getSpatial().lockMeshes();
/*  52 */     child.getSpatial().lockShadows();
/*  53 */     synchronized (this.SYNC_OBJECT) {
/*  54 */       attachChild(child);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachManageableAnimatedChild(ManageableSpatial child) {
/*  65 */     synchronized (this.SYNC_OBJECT) {
/*  66 */       attachChild(child);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachStaticChild(Spatial child) {
/*  76 */     if (child instanceof ManageableSpatial) {
/*  77 */       attachManageableStaticChild((ManageableSpatial)child);
/*     */     } else {
/*  79 */       throw new IllegalArgumentException("Attached child is not manageable");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachAnimatedChild(Spatial child) {
/*  89 */     if (child instanceof ManageableSpatial) {
/*  90 */       attachManageableAnimatedChild((ManageableSpatial)child);
/*     */     } else {
/*  92 */       throw new IllegalArgumentException("Attached child is not manageable");
/*     */     } 
/*     */   }
/*     */   
/*     */   public int attachChild(Spatial child) {
/*  97 */     LOGGER.warn("You're not supposed to attach children manually to chunk node.");
/*  98 */     return 0;
/*     */   }
/*     */   
/*     */   public int attachChildAt(Spatial child, int index) {
/* 102 */     LOGGER.warn("You're not supposed to attach children manually to chunk node.");
/* 103 */     return 0;
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
/*     */   private void attachChild(ManageableSpatial child) {
/* 119 */     placeManageableSpatial(child);
/*     */ 
/*     */     
/* 122 */     updateSingleSpatial(child);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repositionMangeableSpatial(ManageableSpatial child) {
/* 131 */     unplaceManageableSpatial(child);
/* 132 */     placeManageableSpatial(child);
/* 133 */     updateSingleSpatial(child);
/*     */   }
/*     */   
/*     */   private void unplaceManageableSpatial(ManageableSpatial child) {
/* 137 */     synchronized (this.SYNC_OBJECT) {
/* 138 */       for (Set<ManageableSpatial> manageableSpatials : this.spatials.values()) {
/* 139 */         manageableSpatials.remove(child);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeManageableSpatial(ManageableSpatial child) {
/* 145 */     synchronized (this.SYNC_OBJECT) {
/* 146 */       RectangleWC bounds = child.getBounds();
/* 147 */       int minx = (bounds.getOrigin().getTileCoord()).x / 20;
/* 148 */       int miny = (bounds.getOrigin().getTileCoord()).y / 20;
/* 149 */       int maxx = (bounds.getExtent().getTileCoord()).x / 20;
/* 150 */       int maxy = (bounds.getExtent().getTileCoord()).y / 20;
/*     */ 
/*     */       
/* 153 */       for (int x = minx; x <= maxx; x++) {
/* 154 */         for (int y = miny; y <= maxy; y++) {
/* 155 */           Point point = new Point(x, y);
/*     */           
/* 157 */           Set<ManageableSpatial> spatialSet = this.spatials.get(point);
/* 158 */           if (spatialSet == null) {
/* 159 */             spatialSet = new HashSet<ManageableSpatial>();
/* 160 */             this.spatials.put(point, spatialSet);
/*     */           } 
/*     */           
/* 163 */           spatialSet.add(child);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSingleSpatial(ManageableSpatial spatial) {
/* 170 */     int chunkX = WorldOrigin.instance().getX();
/* 171 */     int chunkY = WorldOrigin.instance().getY();
/*     */     
/* 173 */     Set<ManageableSpatial> wanted = findWantedSpatials(chunkX, chunkY);
/*     */     
/* 175 */     synchronized (this.SYNC_OBJECT) {
/* 176 */       LOGGER.log((Priority)Level.DEBUG, "Trying to remove unwanted spatial: " + spatial);
/* 177 */       detachChild(spatial.getSpatial());
/*     */       
/* 179 */       if (wanted.contains(spatial)) {
/*     */         
/* 181 */         LOGGER.log((Priority)Level.DEBUG, "Adding wanted spatial: " + spatial.getSpatial());
/* 182 */         super.attachChild(spatial.getSpatial());
/* 183 */         spatial.getSpatial().updateRenderState();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void originMoved(int oldX, int oldY, int newX, int newY) {
/* 189 */     updateManagedSpatials(newX, newY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateManagedSpatials(int chunkX, int chunkY) {
/* 199 */     Set<ManageableSpatial> wanted = findWantedSpatials(chunkX, chunkY);
/* 200 */     detachUnwantedSpatials(wanted);
/* 201 */     attachWantedSpatials(wanted);
/* 202 */     updateWantedSpatials(wanted);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ManageableSpatial> findWantedSpatials(int chunkX, int chunkY) {
/* 213 */     synchronized (this.SYNC_OBJECT) {
/* 214 */       Set<ManageableSpatial> wanted = new HashSet<ManageableSpatial>();
/* 215 */       int cx = chunkX / 20;
/* 216 */       int cy = chunkY / 20;
/* 217 */       for (int x = cx - this.chunkWorldNode.getVisibleExtent(); x <= cx + this.chunkWorldNode.getVisibleExtent(); x++) {
/* 218 */         for (int y = cy - this.chunkWorldNode.getVisibleExtent(); y <= cy + this.chunkWorldNode.getVisibleExtent(); y++) {
/* 219 */           Point point = new Point(x, y);
/* 220 */           Set<ManageableSpatial> spatialSet = this.spatials.get(point);
/* 221 */           if (spatialSet != null) {
/* 222 */             wanted.addAll(spatialSet);
/*     */           }
/*     */         } 
/*     */       } 
/* 226 */       return wanted;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void detachUnwantedSpatials(Set<ManageableSpatial> wanted) {
/* 236 */     synchronized (this.SYNC_OBJECT) {
/* 237 */       if (this.children != null) {
/* 238 */         Set<Spatial> unwanted = new HashSet<Spatial>();
/* 239 */         for (Spatial child : this.children) {
/* 240 */           if (!containsSpatial(wanted, (ManageableSpatial)child)) {
/* 241 */             unwanted.add(child);
/*     */           }
/*     */         } 
/* 244 */         for (Spatial child : unwanted) {
/* 245 */           LOGGER.log((Priority)Level.DEBUG, "Removing unwanted spatial: " + child);
/* 246 */           detachChild(child);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attachWantedSpatials(Set<ManageableSpatial> wanted) {
/* 258 */     synchronized (this.SYNC_OBJECT) {
/* 259 */       for (ManageableSpatial manageableSpatial : wanted) {
/* 260 */         if (this.children == null || !this.children.contains(manageableSpatial.getSpatial())) {
/* 261 */           LOGGER.log((Priority)Level.DEBUG, "Adding wanted spatial: " + manageableSpatial.getSpatial());
/* 262 */           super.attachChild(manageableSpatial.getSpatial());
/* 263 */           manageableSpatial.getSpatial().updateRenderState();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateWantedSpatials(Set<ManageableSpatial> wanted) {
/* 275 */     for (ManageableSpatial manageableSpatial : wanted) {
/* 276 */       updateSpatialTranslation(manageableSpatial);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSpatialTranslation(ManageableSpatial manageableSpatial) {
/* 286 */     manageableSpatial.updatePlacement(WorldOrigin.instance().getX(), WorldOrigin.instance().getY());
/* 287 */     manageableSpatial.getSpatial().updateModelBound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean containsSpatial(Set<ManageableSpatial> chunkSpatials, ManageableSpatial spatial) {
/* 298 */     for (ManageableSpatial manageableSpatial : chunkSpatials) {
/* 299 */       if (manageableSpatial.equals(spatial)) {
/* 300 */         return true;
/*     */       }
/*     */     } 
/* 303 */     return false;
/*     */   }
/*     */   
/*     */   public LineNode getLineRoot() {
/* 307 */     return this.lineRoot;
/*     */   }
/*     */   
/*     */   public void setLineRoot(LineNode lineRoot) {
/* 311 */     this.lineRoot = lineRoot;
/*     */   }
/*     */   
/*     */   public Set<LineWCHeight> getCollisionLines(RectangleWC rect) {
/* 315 */     if (this.lineRoot == null) {
/* 316 */       return new HashSet<LineWCHeight>();
/*     */     }
/* 318 */     return this.lineRoot.getLines(rect);
/*     */   }
/*     */   
/*     */   public Set<LineWC> getAllCollisionLines() {
/* 322 */     if (this.lineRoot == null) {
/* 323 */       return new HashSet<LineWC>();
/*     */     }
/* 325 */     return this.lineRoot.getAllLines();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<ManageableSpatial> getContainedSpatials() {
/* 334 */     synchronized (this.SYNC_OBJECT) {
/* 335 */       Set<ManageableSpatial> result = new HashSet<ManageableSpatial>();
/* 336 */       for (Set<ManageableSpatial> manageableSpatials : this.spatials.values()) {
/* 337 */         result.addAll(manageableSpatials);
/*     */       }
/* 339 */       return Collections.unmodifiableSet(result);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<ManageableSpatial> getContainedSpatials(RectangleWC rect) {
/* 350 */     synchronized (this.SYNC_OBJECT) {
/* 351 */       Set<ManageableSpatial> result = new HashSet<ManageableSpatial>();
/* 352 */       for (Set<ManageableSpatial> manageableSpatials : this.spatials.values()) {
/* 353 */         for (ManageableSpatial manageableSpatial : manageableSpatials) {
/* 354 */           if (rect.contains(manageableSpatial.getBounds())) {
/* 355 */             result.add(manageableSpatial);
/*     */           }
/*     */         } 
/*     */       } 
/* 359 */       return result;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detachManagableChildren(Collection<ManageableSpatial> managedSpatials) {
/* 370 */     synchronized (this.SYNC_OBJECT) {
/* 371 */       for (Set<ManageableSpatial> spatialSet : this.spatials.values()) {
/* 372 */         spatialSet.removeAll(managedSpatials);
/*     */       }
/* 374 */       for (ManageableSpatial managedSpatial : managedSpatials) {
/* 375 */         detachChild((Spatial)managedSpatial);
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
/*     */   public void detachManagableChild(ManageableSpatial managedSpatial) {
/* 387 */     detachManagableChildren(Arrays.asList(new ManageableSpatial[] { managedSpatial }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachAllManagableChildren() {
/* 392 */     synchronized (this.SYNC_OBJECT) {
/* 393 */       this.spatials.clear();
/* 394 */       detachAllChildren();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 400 */     if (drawPassState.getType() == DrawPassType.DEVELOPMENT_CONTENT && drawPassState.getStateIndex() == 0)
/*     */     {
/* 402 */       synchronized (this.SYNC_OBJECT) {
/* 403 */         onDraw(r);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float v, boolean b) {
/* 410 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.MANAGED.statType);
/* 411 */     synchronized (this.SYNC_OBJECT) {
/* 412 */       super.updateGeometricState(v, b);
/*     */     } 
/* 414 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.MANAGED.statType);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ManagedChunkNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */