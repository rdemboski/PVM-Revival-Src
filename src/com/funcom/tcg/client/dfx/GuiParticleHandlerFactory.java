/*    */ package com.funcom.tcg.client.dfx;
/*    */ 
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ 
/*    */ public class GuiParticleHandlerFactory
/*    */   implements EffectHandlerFactory {
/*    */   public EffectHandler createHandler(Object handlerSource, Object instanceData) {
/* 10 */     return new GUIParticleHandler((RepresentationalNode)handlerSource);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\dfx\GuiParticleHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */