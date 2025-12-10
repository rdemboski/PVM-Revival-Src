/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatureDiedLMToken
/*    */   extends AbstractDeathLMToken
/*    */ {
/*    */   public CreatureDiedLMToken(PropNode CreatureNode, Element element, String impact, PropNodeRegister propNodeRegister) {
/* 20 */     super(CreatureNode, element, impact, propNodeRegister);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 25 */     boolean b = super.processGame();
/*    */     
/* 27 */     if (b && this.corpse != null) {
/* 28 */       TcgGame.getPropNodeRegister().removePropNode(MainGameState.getPlayerNode());
/* 29 */       TcgGame.getPropNodeRegister().addPropNode(this.corpse);
/*    */       
/* 31 */       if (this.creatureNode.getProp() instanceof com.funcom.tcg.client.model.rpg.ClientPlayer) {
/* 32 */         this.propNodeRegister.addPropNode(this.corpse);
/*    */       }
/*    */     } 
/* 35 */     return b;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\CreatureDiedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */