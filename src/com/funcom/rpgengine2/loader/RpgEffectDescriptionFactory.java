/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.commons.dfx.EffectDescription;
/*    */ import com.funcom.commons.dfx.EffectDescriptionFactory;
/*    */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*    */ import com.funcom.commons.dfx.GameplayEffectDescription;
/*    */ import com.funcom.commons.dfx.RpgKeepAliveEffectDescription;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ class RpgEffectDescriptionFactory implements EffectDescriptionFactory {
/*    */   private EffectHandlerFactory itemDFXHandlerFactory;
/*    */   
/*    */   public RpgEffectDescriptionFactory(EffectHandlerFactory itemDFXHandlerFactory) {
/* 14 */     this.itemDFXHandlerFactory = itemDFXHandlerFactory;
/*    */   }
/*    */   
/*    */   public EffectDescription createEffect(Element element, boolean cacheDFX) {
/* 18 */     String type = element.getChildTextTrim("Type");
/*    */     
/* 20 */     if ("GameplayEffect".equalsIgnoreCase(type)) {
/* 21 */       GameplayEffectDescription ret = new GameplayEffectDescription();
/*    */       
/* 23 */       String startTimeStr = element.getChildTextTrim("StartTime");
/* 24 */       double startTime = Double.parseDouble(startTimeStr);
/* 25 */       String resource = element.getChildTextTrim("Resource");
/*    */       
/* 27 */       ret.setStartTime(startTime);
/* 28 */       ret.setEndTime(startTime);
/* 29 */       ret.setResource(resource);
/* 30 */       ret.setHandlerFactory(this.itemDFXHandlerFactory);
/*    */       
/* 32 */       return (EffectDescription)ret;
/* 33 */     }  if ("Animation".equalsIgnoreCase(type) || "ParticleEffect".equalsIgnoreCase(type) || "Mesh".equalsIgnoreCase(type)) {
/*    */ 
/*    */       
/* 36 */       String startTimeStr = element.getChildTextTrim("StartTime");
/* 37 */       double startTime = Double.parseDouble(startTimeStr);
/*    */       
/* 39 */       Element effectProps = element.getChild("EffectSpecificProperties");
/* 40 */       if (effectProps != null) {
/* 41 */         Element durationElement = effectProps.getChild("Duration");
/* 42 */         if (durationElement != null) {
/* 43 */           String durationStr = durationElement.getText();
/* 44 */           double duration = getDuration(durationStr);
/* 45 */           return (EffectDescription)new RpgKeepAliveEffectDescription(startTime + duration);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   private double getDuration(String durationStr) {
/*    */     double duration;
/* 55 */     if (durationStr.equalsIgnoreCase("INF")) {
/* 56 */       duration = Double.POSITIVE_INFINITY;
/*    */     } else {
/* 58 */       duration = Double.parseDouble(durationStr);
/*    */     } 
/* 60 */     return duration;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RpgEffectDescriptionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */