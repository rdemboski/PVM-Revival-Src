/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.InteractiblePropActionInvokedMessage;
/*    */ import com.funcom.tcg.portals.InteractibleType;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class DestinationPortalAction
/*    */   extends AbstractAction
/*    */ {
/* 15 */   public static final Logger LOG = Logger.getLogger(DestinationPortalAction.class.getName());
/*    */   
/*    */   private String interactiblePropId;
/*    */   private GameIOHandler gameIOHandler;
/*    */   private InteractibleType interactibleType;
/*    */   
/*    */   public DestinationPortalAction(String interactiblePropId, GameIOHandler gameIOHandler, InteractibleType interactibleType) {
/* 22 */     this.interactiblePropId = interactiblePropId;
/* 23 */     this.gameIOHandler = gameIOHandler;
/* 24 */     this.interactibleType = interactibleType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 29 */     MainGameState.getTips().put("tutorial.description.waypointportal");
/*    */     try {
/* 31 */       this.gameIOHandler.send((Message)new InteractiblePropActionInvokedMessage(MainGameState.getPlayerModel().getId(), this.interactiblePropId, this.interactibleType));
/*    */     }
/* 33 */     catch (InterruptedException e) {
/* 34 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 40 */     return "destination";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\DestinationPortalAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */