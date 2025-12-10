/*    */ package com.funcom.tcg.client.view.modular;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*    */ 
/*    */ public class PetEventListener
/*    */   extends PlayerEventsAdapter
/*    */ {
/*    */   private ClientDescribedModularNode modularNode;
/*    */   
/*    */   public PetEventListener(ClientDescribedModularNode modularNode) {
/* 11 */     this.modularNode = modularNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activePetChanged() {
/* 16 */     this.modularNode.activePetChanged();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\PetEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */