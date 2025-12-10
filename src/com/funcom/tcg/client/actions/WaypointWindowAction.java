/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.funcom.tcg.client.ui.waypoint.WaypointModel;
/*    */ import com.funcom.tcg.client.ui.waypoint.WaypointWindow;
/*    */ import com.jmex.bui.BWindow;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaypointWindowAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "waypoint";
/*    */   private WaypointDescription waypointDescription;
/*    */   private String[] portalsId;
/*    */   private boolean[] portalsLockingStatus;
/*    */   
/*    */   public WaypointWindowAction(WaypointDescription waypointDescription, String[] portalsId, boolean[] portalsLockingStatus) {
/* 29 */     this.waypointDescription = waypointDescription;
/* 30 */     this.portalsId = portalsId;
/* 31 */     this.portalsLockingStatus = portalsLockingStatus;
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 36 */     Map<String, Boolean> lockStatus = new HashMap<String, Boolean>();
/* 37 */     for (int i = 0; i < this.portalsId.length; i++) {
/* 38 */       String id = this.portalsId[i];
/* 39 */       lockStatus.put(id, Boolean.valueOf(this.portalsLockingStatus[i]));
/*    */     } 
/*    */     
/* 42 */     WaypointModel waypointModel = new WaypointModel(this.waypointDescription, lockStatus);
/* 43 */     WaypointWindow waypointWindow = (WaypointWindow)TcgUI.getWindowFromClass(WaypointWindow.class);
/* 44 */     if (TcgUI.isWindowOpen(WaypointWindow.class)) {
/* 45 */       if (!waypointWindow.getWaypointModel().equals(waypointModel)) {
/* 46 */         waypointWindow.setWaypointModel(waypointModel);
/*    */       }
/*    */     } else {
/* 49 */       if (waypointWindow == null) {
/* 50 */         waypointWindow = new WaypointWindow();
/*    */       }
/* 52 */       waypointWindow.setWaypointModel(waypointModel);
/* 53 */       PanelManager.getInstance().addWindow((BWindow)waypointWindow);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 59 */     return "waypoint";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\WaypointWindowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */