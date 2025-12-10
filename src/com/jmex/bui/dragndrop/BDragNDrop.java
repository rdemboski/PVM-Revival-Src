/*     */ package com.jmex.bui.dragndrop;
/*     */ 
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.EventListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public final class BDragNDrop
/*     */   implements EventListener
/*     */ {
/*  17 */   private static final BDragNDrop INSTANCE = new BDragNDrop();
/*     */   
/*     */   public static BDragNDrop instance() {
/*  20 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<DragNDropListener> listeners;
/*     */   
/*     */   private BDragEvent potentialBDrag;
/*     */   
/*     */   private BIcon potentialDragIcon;
/*     */   
/*     */   private BDragEvent currentDraggingEventB;
/*     */   
/*     */   private BDragIconWindow BDragIconWindow;
/*     */   
/*     */   private Point dragIconDisplacement;
/*     */ 
/*     */   
/*     */   private BDragNDrop() {
/*  39 */     BuiSystem.getRootNode().addGlobalEventListener(this);
/*  40 */     this.BDragIconWindow = new BDragIconWindow();
/*  41 */     this.dragIconDisplacement = new Point(0, 0);
/*     */   }
/*     */   
/*     */   public void addDragNDropListener(DragNDropListener listener) {
/*  45 */     if (this.listeners == null)
/*  46 */       this.listeners = new HashSet<DragNDropListener>(); 
/*  47 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeDragNDropListener(DragNDropListener listener) {
/*  51 */     if (this.listeners != null)
/*  52 */       this.listeners.remove(listener); 
/*     */   }
/*     */   
/*     */   private void detachNotifierWindow() {
/*  56 */     if (BuiSystem.getRootNode().getAllWindows().contains(this.BDragIconWindow))
/*  57 */       BuiSystem.getRootNode().removeWindow(this.BDragIconWindow); 
/*     */   }
/*     */   
/*     */   public boolean isDragging() {
/*  61 */     return (this.currentDraggingEventB != null);
/*     */   }
/*     */   
/*     */   private void fireDragInitiated() {
/*  65 */     if (this.listeners != null)
/*  66 */       for (DragNDropListener listener : this.listeners)
/*  67 */         listener.dragInitiated(this, this.currentDraggingEventB);  
/*     */   }
/*     */   
/*     */   private void fireDropped(BDropEvent eventB) {
/*  71 */     if (this.listeners != null)
/*  72 */       for (DragNDropListener listener : this.listeners)
/*  73 */         listener.dropped(this, eventB);  
/*     */   }
/*     */   
/*     */   public void setPotentialDrag(BComponent source, Object dragObject, BIcon dragIcon) {
/*  77 */     if (this.potentialBDrag != null && this.potentialBDrag.getSource().equals(source))
/*     */       return; 
/*  79 */     this.potentialBDrag = new BDragEvent(source, dragObject);
/*  80 */     this.potentialDragIcon = dragIcon;
/*     */   }
/*     */   
/*     */   public void removePotentialDrag(BComponent source) {
/*  84 */     if (this.potentialBDrag != null && this.potentialBDrag.getSource().equals(source))
/*  85 */       this.potentialBDrag = null; 
/*     */   }
/*     */   
/*     */   public Point getDragIconDisplacement() {
/*  89 */     return this.dragIconDisplacement;
/*     */   }
/*     */   
/*     */   public void setDragIconDisplacement(int x, int y) {
/*  93 */     this.dragIconDisplacement.x = x;
/*  94 */     this.dragIconDisplacement.y = y;
/*     */   }
/*     */   
/*     */   public void eventDispatched(BEvent event) {
/*  98 */     if (event instanceof MouseEvent) {
/*  99 */       MouseEvent e = (MouseEvent)event;
/* 100 */       if (leftButtonPressed(e) && this.potentialBDrag != null && this.potentialBDrag.getSource().isVisible() && insideComponent(e.getX(), e.getY(), this.potentialBDrag.getSource())) {
/* 101 */         startDrag(this.potentialBDrag);
/* 102 */         updateIconWindowLocation(e);
/* 103 */       } else if (leftButtonReleased(e) && isDragging()) {
/* 104 */         drop(e.getX(), e.getY());
/* 105 */       } else if (isDragging() && mouseIsMovingWithButtonDown(e)) {
/* 106 */         updateIconWindowLocation(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean insideComponent(int x, int y, BComponent source) {
/* 112 */     return (source.getAbsoluteX() <= x && source.getAbsoluteY() <= y && source.getAbsoluteX() + source.getWidth() >= x && source.getAbsoluteY() + source.getHeight() >= y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean leftButtonReleased(MouseEvent e) {
/* 118 */     return (e.getType() == 1 && e.getButton() == 0);
/*     */   }
/*     */   
/*     */   private boolean leftButtonPressed(MouseEvent e) {
/* 122 */     return (e.getType() == 0 && e.getButton() == 0);
/*     */   }
/*     */   
/*     */   private boolean mouseIsMovingWithButtonDown(MouseEvent e) {
/* 126 */     return (e.getType() == 5);
/*     */   }
/*     */   
/*     */   private void updateIconWindowLocation(MouseEvent e) {
/* 130 */     this.BDragIconWindow.setLocation(e.getX() + this.dragIconDisplacement.x, e.getY() + this.dragIconDisplacement.y);
/*     */   }
/*     */   
/*     */   private void startDrag(BDragEvent eventB) {
/* 134 */     this.currentDraggingEventB = eventB;
/* 135 */     attachNotifierWindowToMouse();
/* 136 */     fireDragInitiated();
/*     */   }
/*     */   
/*     */   private void attachNotifierWindowToMouse() {
/* 140 */     if (this.BDragIconWindow != null)
/* 141 */       this.BDragIconWindow.setIcon(this.potentialDragIcon); 
/* 142 */     BuiSystem.getRootNode().addWindow(this.BDragIconWindow);
/*     */   }
/*     */   
/*     */   private void drop(int x, int y) {
/* 146 */     detachNotifierWindow();
/* 147 */     BComponent hitComponent = notifyHitComponent(x, y);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (this.potentialBDrag == null || !this.potentialBDrag.getSource().equals(hitComponent))
/* 154 */       this.potentialBDrag = null; 
/* 155 */     this.currentDraggingEventB = null;
/*     */   }
/*     */   
/*     */   private BComponent notifyHitComponent(int x, int y) {
/* 159 */     BComponent hitComponent = null;
/* 160 */     Iterable<BWindow> allWindows = BuiSystem.getRootNode().getAllWindows();
/*     */     
/* 162 */     for (BWindow allWindow : allWindows) {
/* 163 */       BComponent possibleHitComponent = allWindow.getHitComponent(x, y);
/* 164 */       if (possibleHitComponent != null) {
/* 165 */         hitComponent = possibleHitComponent;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     if (hitComponent != null) {
/* 170 */       BDropEvent bDropEvent = new BDropEvent(hitComponent, this.currentDraggingEventB);
/* 171 */       hitComponent.dispatchEvent(bDropEvent);
/* 172 */       fireDropped(bDropEvent);
/* 173 */       return hitComponent;
/*     */     } 
/* 175 */     BDropEvent dropEvent = new BDropEvent(BDropEvent.NOTHING, this.currentDraggingEventB);
/* 176 */     fireDropped(dropEvent);
/*     */     
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   public static interface DragNDropListener {
/*     */     void dragInitiated(BDragNDrop param1BDragNDrop, BDragEvent param1BDragEvent);
/*     */     
/*     */     void dropped(BDragNDrop param1BDragNDrop, BDropEvent param1BDropEvent);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDragNDrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */