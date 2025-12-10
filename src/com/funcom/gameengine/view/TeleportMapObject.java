/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportMapObject
/*    */   extends MapObject
/*    */ {
/* 11 */   private ArrayList<String> destinationMaps = new ArrayList<String>();
/*    */   
/*    */   public TeleportMapObject(String descriptionId, WorldCoordinate worldCoordinate, String imagePath) {
/* 14 */     super(descriptionId, worldCoordinate, imagePath);
/*    */   }
/*    */   
/*    */   public ArrayList<String> getDestinationMaps() {
/* 18 */     return this.destinationMaps;
/*    */   }
/*    */   
/*    */   public void addDestination(String dest) {
/* 22 */     this.destinationMaps.add(dest);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TeleportMapObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */