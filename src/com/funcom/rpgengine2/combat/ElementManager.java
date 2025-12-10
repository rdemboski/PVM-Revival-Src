/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElementManager
/*    */ {
/* 10 */   private Map<Element, ElementDescription> elements = new HashMap<Element, ElementDescription>();
/*    */ 
/*    */   
/*    */   public synchronized void putElement(Element element, ElementDescription elementDesc) {
/* 14 */     if (element == null) {
/* 15 */       throw new NullPointerException("Element cannot be null");
/*    */     }
/*    */     
/* 18 */     if (this.elements.containsKey(element)) {
/* 19 */       throw new IllegalArgumentException("Duplicate element:" + element);
/*    */     }
/*    */     
/* 22 */     this.elements.put(element, elementDesc);
/*    */   }
/*    */   
/*    */   public synchronized void clearData() {
/* 26 */     this.elements.clear();
/*    */   }
/*    */   
/*    */   public synchronized ElementDescription getElementDesc(Element element) {
/* 30 */     return this.elements.get(element);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\ElementManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */