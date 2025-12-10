/*    */ package com.funcom.tcg.client.ui.waypoint;
/*    */ 
/*    */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaypointModel
/*    */ {
/*    */   private WaypointDescription waypointDescription;
/* 12 */   private Map<String, Boolean> portalLockStatus = new HashMap<String, Boolean>();
/*    */ 
/*    */   
/*    */   public WaypointModel(WaypointDescription waypointDescription, Map<String, Boolean> portalLockStatus) {
/* 16 */     this.waypointDescription = waypointDescription;
/* 17 */     this.portalLockStatus = portalLockStatus;
/*    */   }
/*    */   
/*    */   public WaypointDescription getWaypointDescription() {
/* 21 */     return this.waypointDescription;
/*    */   }
/*    */   
/*    */   public boolean getUnlockStatus(String id) {
/* 25 */     return (this.portalLockStatus.get(id) != null && ((Boolean)this.portalLockStatus.get(id)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     if (this == o) return true; 
/* 31 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 33 */     WaypointModel that = (WaypointModel)o;
/*    */     
/* 35 */     if ((this.portalLockStatus != null) ? !this.portalLockStatus.equals(that.portalLockStatus) : (that.portalLockStatus != null))
/* 36 */       return false; 
/* 37 */     if ((this.waypointDescription != null) ? !this.waypointDescription.equals(that.waypointDescription) : (that.waypointDescription != null)) {
/* 38 */       return false;
/*    */     }
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 45 */     int result = (this.waypointDescription != null) ? this.waypointDescription.hashCode() : 0;
/* 46 */     result = 31 * result + ((this.portalLockStatus != null) ? this.portalLockStatus.hashCode() : 0);
/* 47 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\waypoint\WaypointModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */