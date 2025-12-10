/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.EffectDescription;
/*    */ import com.funcom.commons.dfx.RpgKeepAliveEffectDescription;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RpgDireEffectDescription
/*    */   extends DireEffectDescription
/*    */ {
/*    */   public RpgDireEffectDescription(String dfxScript) {
/* 14 */     super(dfxScript);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addEffect(EffectDescription effect) {
/* 19 */     if (effect instanceof RpgKeepAliveEffectDescription) {
/* 20 */       boolean foundKeepAlive = false;
/*    */       
/* 22 */       List<EffectDescription> descriptions = getEffectDescriptions();
/* 23 */       for (EffectDescription existingDesc : descriptions) {
/* 24 */         if (existingDesc instanceof RpgKeepAliveEffectDescription) {
/* 25 */           ((RpgKeepAliveEffectDescription)existingDesc).merge((RpgKeepAliveEffectDescription)effect);
/* 26 */           foundKeepAlive = true;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 31 */       if (!foundKeepAlive) {
/* 32 */         super.addEffect(effect);
/*    */       }
/*    */     } else {
/* 35 */       super.addEffect(effect);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RpgDireEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */