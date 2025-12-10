/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*    */ import com.funcom.commons.dfx.EffectDescription;
/*    */ import com.funcom.commons.dfx.GameplayEffectDescription;
/*    */ 
/*    */ public class RpgDireEffectDescriptionFactory
/*    */   extends DireEffectDescriptionFactory
/*    */ {
/*    */   private GameplayEffectDescription defaultGameEffectDesc;
/*    */   
/*    */   public RpgDireEffectDescriptionFactory(DireEffectResourceLoader dfxResourceLoader, GameplayEffectDescription defaultEffectDescription) {
/* 15 */     super(dfxResourceLoader);
/* 16 */     this.defaultGameEffectDesc = defaultEffectDescription;
/*    */   }
/*    */   
/*    */   protected DireEffectDescription newDFXDescription(String dfxScript, boolean impact) {
/* 20 */     return new RpgDireEffectDescription(dfxScript);
/*    */   }
/*    */   
/*    */   protected void checkDFXDescription(DireEffectDescription ret, boolean impact) {
/* 24 */     if (!impact && ret.getEffectDescriptions(GameplayEffectDescription.class).size() == 0)
/* 25 */       ret.addEffect((EffectDescription)this.defaultGameEffectDesc); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RpgDireEffectDescriptionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */