/*     */ package com.funcom.gameengine.input;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.ai.PickResultsProvider;
/*     */ import com.funcom.gameengine.model.SpatializedWorld;
/*     */ import com.funcom.gameengine.utils.SpatialZComparator;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.intersection.BoundingPickResults;
/*     */ import com.jme.intersection.PickData;
/*     */ import com.jme.intersection.PickResults;
/*     */ import com.jme.math.Plane;
/*     */ import com.jme.math.Ray;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Geometry;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.dragndrop.BDragNDrop;
/*     */ import java.awt.Point;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class SpatialInputAdapter
/*     */   implements InputSourceListener, PickResultsProvider
/*     */ {
/*     */   private List<InteractionNotificationListener> listeners;
/*     */   private PickResults pickResult;
/*     */   private DisplaySystem display;
/*     */   @Deprecated
/*     */   private Node worldNode;
/*     */   private Plane worldPlane;
/*     */   private Vector2f screenPos;
/*     */   private Vector3f planePosition;
/*     */   private Vector3f worldCoords1;
/*     */   private Vector3f worldCoords2;
/*     */   private Ray mouseRay;
/*     */   private boolean filterPropResult;
/*     */   
/*     */   public SpatialInputAdapter(DisplaySystem display, SpatializedWorld world) {
/*  55 */     this(display, world.getWorldNode());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public SpatialInputAdapter(DisplaySystem display, Node worldNode) {
/*  60 */     this.display = display;
/*  61 */     this.worldNode = worldNode;
/*     */     
/*  63 */     this.pickResult = (PickResults)new BoundingPickResults();
/*  64 */     this.pickResult.setCheckDistance(false);
/*     */     
/*  66 */     this.listeners = new LinkedList<InteractionNotificationListener>();
/*  67 */     this.filterPropResult = true;
/*  68 */     this.mouseRay = new Ray();
/*  69 */     this.worldPlane = new Plane(new Vector3f(0.0F, 1.0F, 0.0F), 0.0F);
/*  70 */     this.screenPos = new Vector2f();
/*  71 */     this.planePosition = new Vector3f();
/*  72 */     this.worldCoords1 = new Vector3f();
/*  73 */     this.worldCoords2 = new Vector3f();
/*     */   }
/*     */   
/*     */   public void setFilterPropResult(boolean filterPropResult) {
/*  77 */     this.filterPropResult = filterPropResult;
/*     */   }
/*     */   
/*     */   public void addListener(InteractionNotificationListener listener) {
/*  81 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeListener(InteractionNotificationListener listener) {
/*  85 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void onButton(int button, boolean pressed, int x, int y) {
/*  89 */     updateMouseRay(x, y);
/*  90 */     updateRayCollision();
/*  91 */     if (pressed) {
/*  92 */       fireMousePressed(getPointingCoordinate(), getPointingProps(), new Point(x, y), button);
/*     */     } else {
/*  94 */       fireMouseReleased(getPointingCoordinate(), getPointingProps(), new Point(x, y), button);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onWheel(int wheelDelta, int x, int y) {
/*  99 */     updateMouseRay(x, y);
/* 100 */     updateRayCollision();
/* 101 */     fireWheelMoved(getPointingCoordinate(), getPointingProps(), new Point(x, y), wheelDelta);
/*     */   }
/*     */   
/*     */   public void onMove(int xDelta, int yDelta, int newX, int newY) {
/* 105 */     updateMouseRay(newX, newY);
/* 106 */     fireMouseMoved(getPointingCoordinate(), new Point(newX, newY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKey(char character, int keyCode, boolean pressed) {
/* 111 */     if (pressed) {
/* 112 */       int x = MouseInput.get().getXAbsolute();
/* 113 */       int y = MouseInput.get().getYAbsolute();
/*     */       
/* 115 */       updateMouseRay(x, y);
/* 116 */       updateRayCollision();
/* 117 */       fireKeyPressed(getPointingCoordinate(), getPointingProps(), new Point(x, y), keyCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdate(float time) {
/* 122 */     if (BDragNDrop.instance().isDragging()) {
/*     */       return;
/*     */     }
/* 125 */     int x = MouseInput.get().getXAbsolute();
/* 126 */     int y = MouseInput.get().getYAbsolute();
/*     */     
/* 128 */     updateMouseRay(x, y);
/* 129 */     updateRayCollision();
/*     */     
/* 131 */     firePositionUpdated(getPointingCoordinate(), getPointingProps(), new Point(x, y));
/*     */   }
/*     */   
/*     */   private void updateMouseRay(float x, float y) {
/* 135 */     this.screenPos.set(x, y);
/* 136 */     this.display.getWorldCoordinates(this.screenPos, 0.0F, this.worldCoords1);
/* 137 */     this.display.getWorldCoordinates(this.screenPos, 1.0F, this.worldCoords2);
/*     */ 
/*     */     
/* 140 */     this.mouseRay.setOrigin(this.worldCoords1);
/* 141 */     this.mouseRay.setDirection(this.worldCoords2.subtractLocal(this.worldCoords1).normalizeLocal());
/*     */ 
/*     */     
/* 144 */     this.mouseRay.intersectsWherePlane(this.worldPlane, this.planePosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRayCollision() {
/* 149 */     this.pickResult.clear();
/* 150 */     this.worldNode.findPick(this.mouseRay, this.pickResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate getPointingCoordinate() {
/* 159 */     float x = this.planePosition.getX() + WorldOrigin.instance().getX();
/* 160 */     float y = this.planePosition.getZ() + WorldOrigin.instance().getY();
/* 161 */     return new WorldCoordinate(WorldOrigin.instance().getX(), WorldOrigin.instance().getY(), this.planePosition.getX(), this.planePosition.getZ(), "UNIDENTIFIED_MAP", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuiHit() {
/* 167 */     for (BWindow window : BuiSystem.getRootNode().getAllWindows()) {
/* 168 */       if (isHit(window)) {
/* 169 */         return true;
/*     */       }
/*     */     } 
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropNode getTopClickableProp() {
/* 177 */     return getTopPointingProp();
/*     */   }
/*     */   
/*     */   private boolean isHit(BWindow window) {
/* 181 */     int mouseX = MouseInput.get().getXAbsolute();
/* 182 */     int mouseY = MouseInput.get().getYAbsolute();
/* 183 */     int winX = window.getAbsoluteX();
/* 184 */     int winY = window.getAbsoluteY();
/*     */     
/* 186 */     return (mouseX > winX && mouseX < winX + window.getWidth() && mouseY > winY && mouseY < winY + window.getHeight());
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
/*     */   private PropNode getPropNode(Spatial node) {
/* 200 */     if (node != null && node.getParent() instanceof PropNode) {
/* 201 */       PropNode propNode = (PropNode)node.getParent();
/* 202 */       if (propNode.getRepresentation() == node) {
/* 203 */         if (!this.filterPropResult || propNode.hasActionHandler()) {
/* 204 */           return propNode;
/*     */         }
/*     */       } else {
/* 207 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 212 */     if (node != null) {
/* 213 */       return getPropNode((Spatial)node.getParent());
/*     */     }
/*     */     
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<PropNode> getPointingProps() {
/* 226 */     Set<PropNode> result = new HashSet<PropNode>();
/*     */ 
/*     */     
/* 229 */     for (int pickNumber = 0; pickNumber < this.pickResult.getNumber(); pickNumber++) {
/* 230 */       PickData pickData = this.pickResult.getPickData(pickNumber);
/* 231 */       Geometry geometry = pickData.getTargetMesh();
/*     */ 
/*     */       
/* 234 */       PropNode propNode = getPropNode((Spatial)geometry);
/*     */       
/* 236 */       if (propNode != null) {
/* 237 */         result.add(propNode);
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return result;
/*     */   }
/*     */   
/*     */   public PropNode getTopPointingProp() {
/* 245 */     return SpatialZComparator.getTopProp(getPointingProps());
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearPickedData() {
/* 250 */     this.pickResult.clear();
/*     */   }
/*     */   
/*     */   private void fireMousePressed(WorldCoordinate wc, Set<PropNode> props, Point mousePosition, int buttonPressed) {
/* 254 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 255 */       interactionNotificationListener.mousePressed(wc, props, mousePosition, buttonPressed); 
/*     */   }
/*     */   
/*     */   private void fireMouseReleased(WorldCoordinate wc, Set<PropNode> props, Point mousePosition, int buttonPressed) {
/* 259 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 260 */       interactionNotificationListener.mouseReleased(wc, props, mousePosition, buttonPressed); 
/*     */   }
/*     */   
/*     */   private void fireWheelMoved(WorldCoordinate wc, Set<PropNode> props, Point mousePosition, int wheelDelta) {
/* 264 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 265 */       interactionNotificationListener.wheelMoved(wc, props, mousePosition, wheelDelta); 
/*     */   }
/*     */   
/*     */   private void fireMouseMoved(WorldCoordinate wc, Point mousePosition) {
/* 269 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 270 */       interactionNotificationListener.mouseMoved(wc, mousePosition); 
/*     */   }
/*     */   
/*     */   private void firePositionUpdated(WorldCoordinate wc, Set<PropNode> props, Point mousePosition) {
/* 274 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 275 */       interactionNotificationListener.positionUpdated(wc, props, mousePosition); 
/*     */   }
/*     */   
/*     */   private void fireKeyPressed(WorldCoordinate wc, Set<PropNode> props, Point mousePosition, int keyCode) {
/* 279 */     for (InteractionNotificationListener interactionNotificationListener : this.listeners)
/* 280 */       interactionNotificationListener.keyPressed(wc, props, mousePosition, keyCode); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\SpatialInputAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */