/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextEffectHandlerFactory
/*    */   implements EffectHandlerFactory
/*    */ {
/*    */   public EffectHandler createHandler(Object handlerSource, Object instanceData) {
/* 12 */     return new TextEffectHandler((RepresentationalNode)handlerSource);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TextEffectHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */