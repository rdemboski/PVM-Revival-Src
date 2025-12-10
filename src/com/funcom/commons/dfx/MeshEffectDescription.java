/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class MeshEffectDescription
/*    */   extends PositionalEffectDescription
/*    */ {
/*    */   private Element subDfx;
/*    */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*    */   private boolean disconnectMesh;
/*    */   
/*    */   public Element getDfx() {
/* 13 */     return this.subDfx;
/*    */   }
/*    */   
/*    */   public void setDfx(Element subDfx) {
/* 17 */     this.subDfx = subDfx;
/*    */   }
/*    */   
/*    */   public DireEffectDescriptionFactory getEffectDescriptionFactory() {
/* 21 */     return this.effectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public void setEffectDescriptionFactory(DireEffectDescriptionFactory effectDescriptionFactory) {
/* 25 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public void setDisconnectMesh(boolean disconnectMesh) {
/* 29 */     this.disconnectMesh = disconnectMesh;
/*    */   }
/*    */   
/*    */   public boolean isDisconnectMesh() {
/* 33 */     return this.disconnectMesh;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\MeshEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */