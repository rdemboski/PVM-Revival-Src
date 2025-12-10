/*     */ package com.funcom.tcg.factories;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.gameengine.model.GraphicsConfig;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class XmlMeshFactory
/*     */   implements MeshFactory
/*     */ {
/*     */   private static final String MESH_PROP_NAME = "MeshProp";
/*     */   private static final String MESH_MODEL_NAME = "MeshNode";
/*     */   private MeshDescription description;
/*     */   private ResourceGetter resourceGetter;
/*     */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*     */   
/*     */   public XmlMeshFactory(DireEffectDescriptionFactory effectDescriptionFactory) {
/*  30 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public void setDescriptor(Object descriptor) {
/*  34 */     if (!(descriptor instanceof MeshDescription))
/*  35 */       throw new IllegalStateException("This factory uses MeshDescription as descriptor for everything."); 
/*  36 */     this.description = (MeshDescription)descriptor;
/*     */   }
/*     */   
/*     */   public void setResourceGetter(ResourceGetter resourceGetter) {
/*  40 */     this.resourceGetter = resourceGetter;
/*     */   }
/*     */   
/*     */   public PropNode createPropNode(float modelScale, Quaternion modelRotation) {
/*  44 */     Prop prop = new Prop("MeshProp");
/*  45 */     PropNode propNode = new PropNode(prop, 3, this.description.getMeshPath(), this.effectDescriptionFactory);
/*  46 */     propNode.setRunsDfxs(false);
/*     */ 
/*     */     
/*  49 */     ModelNode modelNode = this.resourceGetter.getModelNode(this.description.getMeshPath());
/*     */     
/*  51 */     TokenRegister.instance().addToken(new CreateGraphicConfigToken((Spatial)modelNode));
/*     */     
/*  53 */     modelNode.setLocalRotation(modelRotation);
/*  54 */     modelNode.setName("MeshNode");
/*     */     
/*  56 */     List<MeshDescription.AnimationDescription> animations = this.description.getAnimations();
/*  57 */     for (MeshDescription.AnimationDescription animation : animations) {
/*  58 */       JointAnimation jointAnimation = this.resourceGetter.getModelAnimation(animation.getPath());
/*  59 */       jointAnimation.setName(animation.getName());
/*  60 */       modelNode.addAnimation(jointAnimation);
/*     */     } 
/*  62 */     JointController jointController = null;
/*  63 */     for (Controller controller : modelNode.getControllers()) {
/*  64 */       if (controller instanceof JointController) {
/*  65 */         jointController = (JointController)controller;
/*     */         break;
/*     */       } 
/*     */     } 
/*  69 */     if (jointController != null) {
/*  70 */       boolean active = false;
/*  71 */       for (JointAnimation animation : jointController.getAnimations().values()) {
/*  72 */         if ((animation.getFrames()).length > 2) {
/*  73 */           active = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*  77 */       jointController.update(0.0F);
/*  78 */       jointController.setActive(active);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  83 */     modelNode.setLocalScale(this.description.getScaleBase() * modelScale);
/*  84 */     Node representation = new Node();
/*  85 */     representation.attachChild((Spatial)modelNode);
/*  86 */     propNode.attachRepresentation((Spatial)representation);
/*     */ 
/*     */     
/*  89 */     return propNode;
/*     */   }
/*     */   
/*     */   private class CreateGraphicConfigToken implements Token {
/*     */     private Spatial modelNode;
/*     */     
/*     */     private CreateGraphicConfigToken(Spatial modelNode) {
/*  96 */       this.modelNode = modelNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public Token.TokenType getTokenType() {
/* 101 */       return Token.TokenType.GAME_THREAD;
/*     */     }
/*     */ 
/*     */     
/*     */     public void process() {
/* 106 */       GraphicsConfig config = GraphicsConfig.getModelConfig(XmlMeshFactory.this.resourceGetter, XmlMeshFactory.this.description.getMeshPath());
/* 107 */       this.modelNode.setRenderState(config.getBlendMode().getRenderState());
/* 108 */       this.modelNode.updateRenderState();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\factories\XmlMeshFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */