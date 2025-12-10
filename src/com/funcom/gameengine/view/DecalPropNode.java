/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.jme.DecalQuad;
/*    */ import com.funcom.gameengine.jme.LayeredElement;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class DecalPropNode
/*    */   extends PropNode
/*    */   implements LayeredElement
/*    */ {
/*    */   public DecalPropNode(Prop prop, String resourceName, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 14 */     super(prop, 8, resourceName, direEffectDescriptionFactory);
/* 15 */     setRunsDfxs(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void attachRepresentation(Spatial spatial) {
/* 20 */     if (!(spatial instanceof DecalQuad)) {
/* 21 */       throw new IllegalArgumentException("Wrong class, " + DecalPropNode.class.getSimpleName() + " expects " + DecalQuad.class.getSimpleName() + " as representation: class=" + spatial.getClass());
/*    */     }
/*    */ 
/*    */     
/* 25 */     super.attachRepresentation(spatial);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLayerId() {
/* 30 */     return ((DecalQuad)getRepresentation()).getLayerId();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\DecalPropNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */