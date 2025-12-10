/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PortalDefinition
/*    */ {
/*    */   private String id;
/* 13 */   private List<WorldCoordinate> positions = new LinkedList<WorldCoordinate>();
/*    */   private WorldCoordinate destination;
/*    */   private String portalFilename;
/*    */   
/*    */   public PortalDefinition(String id) {
/* 18 */     if (id == null)
/* 19 */       throw new IllegalArgumentException("id = null"); 
/* 20 */     this.id = id;
/*    */   }
/*    */   
/*    */   public PortalDefinition(String id, WorldCoordinate position, WorldCoordinate destination, String portalFilename) {
/* 24 */     if (id == null)
/* 25 */       throw new IllegalArgumentException("id = null"); 
/* 26 */     if (position == null)
/* 27 */       throw new IllegalArgumentException("position = null"); 
/* 28 */     if (destination == null)
/* 29 */       throw new IllegalArgumentException("destination = null"); 
/* 30 */     if (portalFilename == null)
/* 31 */       throw new IllegalArgumentException("portalFilename = null"); 
/* 32 */     this.id = id;
/*    */     
/* 34 */     this.positions = new ArrayList<WorldCoordinate>(1);
/* 35 */     this.positions.add(position);
/* 36 */     this.destination = destination;
/* 37 */     this.portalFilename = portalFilename;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getDestination() {
/* 41 */     return this.destination;
/*    */   }
/*    */   
/*    */   public void setDestination(WorldCoordinate destination) {
/* 45 */     this.destination = destination;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 49 */     return this.id;
/*    */   }
/*    */   
/*    */   public List<WorldCoordinate> getPositions() {
/* 53 */     return this.positions;
/*    */   }
/*    */   
/*    */   public void addPosition(WorldCoordinate position) {
/* 57 */     this.positions.add(position);
/*    */   }
/*    */   
/*    */   public void setPortalFilename(String portalFilename) {
/* 61 */     this.portalFilename = portalFilename;
/*    */   }
/*    */   
/*    */   public String getPortalFilename() {
/* 65 */     return this.portalFilename;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 69 */     return String.format("PortalDefinition{id='%s', position=%s, destination=%s, portalFilename='%s'}'", new Object[] { this.id, this.positions, this.destination, this.portalFilename });
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\PortalDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */