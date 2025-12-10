/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.loader.StatIdTranslator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGStatIdTranslator
/*    */   implements StatIdTranslator
/*    */ {
/*    */   private static final String RESIST_PREFIX = "RESIST_";
/*    */   
/*    */   public Short translate(String statIdStr) {
/* 15 */     if (statIdStr != null) {
/*    */       
/* 17 */       statIdStr = statIdStr.toUpperCase();
/* 18 */       if (statIdStr.startsWith("RESIST_"))
/*    */       {
/* 20 */         return StatId.getId(Element.valueOf(statIdStr.substring("RESIST_".length())));
/*    */       }
/*    */       
/* 23 */       return StatId.getId(statIdStr);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public List<Short> getRuntimeIds() {
/* 32 */     return StatId.getRuntimeIds();
/*    */   }
/*    */   
/*    */   public List<Short> getAllIds() {
/* 36 */     return StatId.getAllIds();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGStatIdTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */