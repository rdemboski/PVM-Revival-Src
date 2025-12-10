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
/*    */ public class ZoneAction
/*    */   extends AbstractAction
/*    */ {
/* 15 */   private static final Logger LOG = Logger.getLogger(ZoneAction.class.getName());
/*    */   
/*    */   private String interactiblePropId;
/*    */   private GameIOHandler gameIOHandler;
/*    */   private InteractibleType interactibleType;
/*    */   
/*    */   public ZoneAction(String interactiblePropId, GameIOHandler gameIOHandler, InteractibleType interactibleType) {
/* 22 */     this.interactiblePropId = interactiblePropId;
/* 23 */     this.gameIOHandler = gameIOHandler;
/* 24 */     this.interactibleType = interactibleType;
/*    */   }
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 28 */     LOG.info("Zoning to zone: " + this.interactiblePropId);
/*    */     try {
/* 30 */       this.gameIOHandler.send((Message)new InteractiblePropActionInvokedMessage(MainGameState.getPlayerModel().getId(), this.interactiblePropId, this.interactibleType));
/* 31 */     } catch (InterruptedException e) {
/* 32 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return "zone";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\ZoneAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */