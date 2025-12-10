/*    */ package com.funcom.tcg.client.view.modular.part;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiddenPart
/*    */   implements ModularDescription.Part
/*    */ {
/*    */   private String partName;
/*    */   
/*    */   public HiddenPart(String partName) {
/* 13 */     this.partName = partName;
/*    */   }
/*    */   
/*    */   public String getPartName() {
/* 17 */     return this.partName;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   public String getMeshPath() {
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public List<ModularDescription.TexturePart> getTextureParts() {
/* 29 */     return new LinkedList<ModularDescription.TexturePart>();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\HiddenPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */