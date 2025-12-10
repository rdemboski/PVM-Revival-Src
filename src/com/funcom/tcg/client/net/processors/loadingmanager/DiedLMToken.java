/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.DiedMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiedLMToken
/*    */   extends AbstractDeathLMToken
/*    */ {
/* 19 */   private DiedMessage diedMessage = null;
/*    */   
/*    */   public DiedLMToken(DiedMessage diedMessage, PropNode CreatureNode, Element element, String impact, PropNodeRegister propNodeRegister) {
/* 22 */     super(CreatureNode, element, impact, propNodeRegister);
/* 23 */     this.diedMessage = diedMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 28 */     TcgGame.getPropNodeRegister().removePropNode(MainGameState.getPlayerNode());
/*    */     
/* 30 */     return super.processGame();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\DiedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */