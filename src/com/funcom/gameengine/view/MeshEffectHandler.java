/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.MeshEffectDescription;
/*    */ import com.funcom.commons.dfx.PositionalEffectDescription;
/*    */ import com.funcom.commons.jme.md5importer.ModelNode;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.view.particles.Md5JointFollowController;
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ 
/*    */ public class MeshEffectHandler extends PositionalEffectHandler {
/*    */   private RepresentationalNode representationalNode;
/*    */   
/*    */   public MeshEffectHandler(RepresentationalNode target) {
/* 21 */     super(target);
/*    */   }
/*    */   private PropNode disconnectedNode;
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 26 */     MeshEffectDescription meshEffectDescription = (MeshEffectDescription)sourceEffect.getDescription();
/* 27 */     this.representationalNode = new RepresentationalNode(meshEffectDescription.getResource(), meshEffectDescription.getEffectDescriptionFactory(), 3);
/* 28 */     ModelNode modelNode = (ModelNode)meshEffectDescription.getResourceFetcher().getModelNode(meshEffectDescription.getResource());
/* 29 */     modelNode.setRenderState((RenderState)TransparentAlphaState.get());
/* 30 */     modelNode.setRenderQueueMode(3);
/* 31 */     this.representationalNode.attachRepresentation((Spatial)modelNode);
/*    */     
/* 33 */     float scale = (float)meshEffectDescription.getScale();
/* 34 */     float parentScale = getTarget().getLocalScale().length();
/* 35 */     Vector3f offsetPos = getOffsetPos((PositionalEffectDescription)meshEffectDescription).divide(parentScale);
/*    */     
/* 37 */     if (getTarget().getRepresentation() != null) {
/* 38 */       BoundingVolume volume = getTarget().getRepresentation().getWorldBound();
/* 39 */       if (meshEffectDescription.getRelativeScale()) {
/* 40 */         double parentSize = Math.pow(volume.getVolume(), 0.3333333333333333D);
/* 41 */         scale = (float)(scale * parentSize);
/*    */       } 
/* 43 */       if (meshEffectDescription.isRelativeOffset()) {
/* 44 */         Vector3f extent = ((BoundingBox)volume).getExtent(new Vector3f());
/* 45 */         offsetPos.multLocal(extent);
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     this.representationalNode.setLocalScale(scale / parentScale);
/* 50 */     this.representationalNode.setLocalTranslation(offsetPos);
/* 51 */     this.representationalNode.setLocalRotation(getOffsetAngle(meshEffectDescription.getOffsetAngle()));
/* 52 */     this.representationalNode.getEffects().setParticleSurface(getTarget().getEffects().getParticleSurface());
/*    */     
/* 54 */     if (meshEffectDescription.isDisconnectMesh()) {
/*    */       
/* 56 */       PropNode parentsNode = getPropNodeParent(getTarget());
/* 57 */       Prop prop = new Prop("Disconnected Mesh Effect", parentsNode.getPosition().clone());
/* 58 */       this.disconnectedNode = new PropNode(prop, 3, "", parentsNode.getEffectDescriptionFactory());
/* 59 */       this.disconnectedNode.setAngle(parentsNode.getAngle());
/* 60 */       this.disconnectedNode.attachChild((Spatial)this.representationalNode);
/*    */     }
/* 62 */     else if (meshEffectDescription.getBone() != null && !meshEffectDescription.getBone().equalsIgnoreCase("None")) {
/* 63 */       Md5JointFollowController followController = new Md5JointFollowController((Spatial)this.representationalNode, getTarget().getFollowJoint(meshEffectDescription.getBone()));
/* 64 */       followController.setTranslationDisplacement(getOffsetPos((PositionalEffectDescription)meshEffectDescription).divide(scale));
/* 65 */       followController.setRotationDisplacement(getOffsetAngle(meshEffectDescription.getOffsetAngle()));
/* 66 */       this.representationalNode.addController((Controller)followController);
/* 67 */       ((Node)getTarget().getRepresentation()).attachChild((Spatial)this.representationalNode);
/*    */     } else {
/* 69 */       getTarget().attachChild((Spatial)this.representationalNode);
/*    */     } 
/*    */ 
/*    */     
/* 73 */     this.representationalNode.updateRenderState();
/*    */     
/* 75 */     if (meshEffectDescription.getDfx() != null) {
/* 76 */       this.representationalNode.playDfx(meshEffectDescription.getDfx());
/*    */     }
/*    */     
/* 79 */     if (this.disconnectedNode != null) {
/* 80 */       getPropNodeParent(getTarget()).getEffects().getParticleSurface().addDisconnectedMeshEffect(this.disconnectedNode);
/* 81 */       this.disconnectedNode.updateRenderState();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {
/* 87 */     this.representationalNode.removeFromParent();
/* 88 */     if (this.disconnectedNode != null) {
/* 89 */       this.disconnectedNode.removeFromParent();
/*    */     }
/*    */   }
/*    */   
/*    */   private PropNode getPropNodeParent(Node node) {
/* 94 */     Node currentNode = node;
/* 95 */     while (currentNode != null && !(currentNode instanceof PropNode)) {
/* 96 */       currentNode = currentNode.getParent();
/*    */     }
/*    */     
/* 99 */     return (PropNode)currentNode;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\MeshEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */