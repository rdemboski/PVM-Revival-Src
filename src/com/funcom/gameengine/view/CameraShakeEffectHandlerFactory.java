/*   */ package com.funcom.gameengine.view;
/*   */ 
/*   */ import com.funcom.commons.dfx.EffectHandler;
/*   */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*   */ 
/*   */ public class CameraShakeEffectHandlerFactory
/*   */   implements EffectHandlerFactory {
/*   */   public EffectHandler createHandler(Object handlerSource, Object instanceData) {
/* 9 */     return new CameraShakeEffectHandler((RepresentationalNode)handlerSource);
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CameraShakeEffectHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */