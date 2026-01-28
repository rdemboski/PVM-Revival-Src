/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.model.GraphicsConfig;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.Effects;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.gameengine.view.TransparentAlphaState;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.factories.MeshDescription;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import java.awt.Point;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientMeshObjectLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*     */   protected float z;
/*     */   private String name;
/*     */   protected WorldCoordinate coord;
/*     */   protected float angle;
/*     */   protected float scale;
/*     */   protected Point tileCoord;
/*     */   protected float[] tintColor;
/*     */   protected TokenTargetNode tokenTargetNode;
/*     */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*  49 */   private Future LoadModelFuture = null;
/*     */   
/*  51 */   private ResourceGetter resourceGetter = null;
/*  52 */   private String resourceName = "";
/*     */   
/*     */   private static final String MESH_MODEL_NAME = "MeshNode";
/*     */   
/*     */   public ClientMeshObjectLMToken(ResourceGetter resourceGetter, String resourceName, float z, String name, WorldCoordinate coord, float angle, float scale, Point tileCoord, float[] tintColor, TokenTargetNode tokenTargetNode, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  57 */     this.z = z;
/*  58 */     this.name = name;
/*  59 */     this.coord = coord;
/*  60 */     this.angle = angle;
/*  61 */     this.scale = scale;
/*  62 */     this.tileCoord = tileCoord;
/*  63 */     this.tintColor = tintColor;
/*  64 */     this.tokenTargetNode = tokenTargetNode;
/*  65 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*  66 */     this.resourceGetter = resourceGetter;
/*  67 */     this.resourceName = resourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  76 */     Callable<SLoaded> callable = new LoadModelCallable(TcgConstants.MODEL_ROTATION);
/*  77 */     this.LoadModelFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*  78 */     if (this.LoadModelFuture == null) {
/*  79 */       throw new Exception("processWaitingAssets: the LoadModelFuture is null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  88 */     return (this.LoadModelFuture == null || this.LoadModelFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  94 */     RepresentationalNode representationalNode = createPropNode(0.0025F, TcgConstants.MODEL_ROTATION);
/*     */     
/*  96 */     representationalNode.setRunsDfxs(false);
/*     */     
/*  98 */     representationalNode.getLocalRotation().fromAngleNormalAxis(this.angle, PropNode.DOWN_VEC);
/*  99 */     float x = WorldUtils.getScreenX(this.coord, this.tileCoord.x);
/* 100 */     float y = WorldUtils.getScreenY(this.coord, this.tileCoord.y);
/* 101 */     representationalNode.setLocalTranslation(x, this.z, y);
/*     */ 
/*     */ 
/*     */     
/* 105 */     representationalNode.setScale(this.scale);
/* 106 */     representationalNode.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     
/* 108 */     if (this.tintColor != null) {
/* 109 */       representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 110 */       representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */     } 
/*     */     
/* 113 */     JointController jointController = null;
/* 114 */     for (Controller controller : ((Node)representationalNode.getRepresentation()).getChild(0).getControllers()) {
/* 115 */       if (controller instanceof JointController) {
/* 116 */         jointController = (JointController)controller; break;
/*     */       } 
/*     */     } 
/* 119 */     if (jointController == null || !jointController.isActive()) {
/* 120 */       this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/* 121 */       if (jointController != null)
/* 122 */         ((Node)representationalNode.getRepresentation()).getChild(0).removeController((Controller)jointController); 
/*     */     } else {
/* 124 */       this.tokenTargetNode.attachAnimatedChild((Spatial)representationalNode);
/*     */     } 
/* 126 */     representationalNode.propagateBoundToRoot();
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RepresentationalNode createPropNode(float modelScale, Quaternion modelRotation) throws Exception {
/*     */     RepresentationalNode propNode;
/* 137 */     if (this.LoadModelFuture != null && !this.LoadModelFuture.isCancelled() && this.LoadModelFuture.get() != null) {
/*     */       
/* 139 */       SLoaded l = (SLoaded)this.LoadModelFuture.get();
/* 140 */       MeshDescription meshDescription = l.meshDescription;
/* 141 */       propNode = new RepresentationalNode(meshDescription.getMeshPath(), this.effectDescriptionFactory, 3);
/* 142 */       propNode.setRunsDfxs(false);
/* 143 */       ModelNode modelNode = l.node;
/*     */       
/* 145 */       Node representation = new Node();
/* 146 */       representation.attachChild((Spatial)modelNode);
/* 147 */       propNode.attachRepresentation((Spatial)representation);
/*     */       
/* 149 */       modelNode.updateRenderState();
/*     */     } else {
/* 151 */       Document document = this.resourceGetter.getDocument(this.resourceName, CacheType.CACHE_TEMPORARILY);
/* 152 */       Element rootElement = document.getRootElement();
/* 153 */       MeshDescription meshDescription = new MeshDescription(rootElement);
/*     */       
/* 155 */       propNode = new RepresentationalNode(meshDescription.getMeshPath(), this.effectDescriptionFactory, 3);
/* 156 */       propNode.setRunsDfxs(false);
/*     */       
/* 158 */       ModelNode modelNode = this.resourceGetter.getModelNode(meshDescription.getMeshPath());
/*     */       
/* 160 */       modelNode.setLocalRotation(modelRotation);
/* 161 */       modelNode.setName("MeshNode");
/*     */       
/* 163 */       List<MeshDescription.AnimationDescription> animations = meshDescription.getAnimations();
/* 164 */       for (MeshDescription.AnimationDescription animation : animations) {
/* 165 */         JointAnimation jointAnimation = this.resourceGetter.getModelAnimation(animation.getPath());
/* 166 */         jointAnimation.setName(animation.getName());
/* 167 */         modelNode.addAnimation(jointAnimation);
/*     */       } 
/* 169 */       JointController jointController = null;
/* 170 */       for (Controller controller : modelNode.getControllers()) {
/* 171 */         if (controller instanceof JointController) {
/* 172 */           jointController = (JointController)controller;
/*     */           break;
/*     */         } 
/*     */       } 
/* 176 */       if (jointController != null) {
/* 177 */         boolean active = false;
/* 178 */         for (JointAnimation animation : jointController.getAnimations().values()) {
/* 179 */           if ((animation.getFrames()).length > 2) {
/* 180 */             active = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 184 */         jointController.update(0.0F);
/* 185 */         jointController.setActive(active);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 190 */       modelNode.setLocalScale(meshDescription.getScaleBase() * modelScale);
/* 191 */       Node representation = new Node();
/* 192 */       representation.attachChild((Spatial)modelNode);
/* 193 */       propNode.attachRepresentation((Spatial)representation);
/*     */ 
/*     */       
/* 196 */       GraphicsConfig config = GraphicsConfig.getModelConfig(this.resourceGetter, meshDescription.getMeshPath());
/* 197 */       modelNode.setRenderState(config.getBlendMode().getRenderState());
/* 198 */       modelNode.updateRenderState();
/*     */     } 
/* 200 */     this.LoadModelFuture = null;
/*     */     
/* 202 */     return propNode;
/*     */   }
/*     */   
/*     */   public class LoadModelCallable
/*     */     implements Callable<SLoaded>
/*     */   {
/* 208 */     Quaternion modelRotation = null;
/*     */ 
/*     */     
/*     */     public LoadModelCallable(Quaternion modelRotation) {
/* 212 */       this.modelRotation = modelRotation;
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientMeshObjectLMToken.SLoaded call() {
/* 217 */       Document document = ClientMeshObjectLMToken.this.resourceGetter.getDocument(ClientMeshObjectLMToken.this.resourceName, CacheType.CACHE_TEMPORARILY);
/* 218 */       Element rootElement = document.getRootElement();
/* 219 */       MeshDescription meshDescription = new MeshDescription(rootElement);
/*     */       
/* 221 */       ModelNode modelNode = ClientMeshObjectLMToken.this.resourceGetter.getModelNode(meshDescription.getMeshPath());
/*     */       
/* 223 */       modelNode.setLocalRotation(this.modelRotation);
/* 224 */       modelNode.setName("MeshNode");
/*     */       
/* 226 */       List<MeshDescription.AnimationDescription> animations = meshDescription.getAnimations();
/* 227 */       for (MeshDescription.AnimationDescription animation : animations) {
/* 228 */         JointAnimation jointAnimation = ClientMeshObjectLMToken.this.resourceGetter.getModelAnimation(animation.getPath());
/* 229 */         jointAnimation.setName(animation.getName());
/* 230 */         modelNode.addAnimation(jointAnimation);
/*     */       } 
/* 232 */       JointController jointController = null;
/* 233 */       for (Controller controller : modelNode.getControllers()) {
/* 234 */         if (controller instanceof JointController) {
/* 235 */           jointController = (JointController)controller;
/*     */           break;
/*     */         } 
/*     */       } 
/* 239 */       if (jointController != null) {
/* 240 */         boolean active = false;
/* 241 */         for (JointAnimation animation : jointController.getAnimations().values()) {
/* 242 */           if ((animation.getFrames()).length > 2) {
/* 243 */             active = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 247 */         jointController.update(0.0F);
/* 248 */         jointController.setActive(active);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 253 */       modelNode.setLocalScale(meshDescription.getScaleBase() * 0.0025F);
/*     */       
/* 255 */       GraphicsConfig config = GraphicsConfig.getModelConfig(ClientMeshObjectLMToken.this.resourceGetter, meshDescription.getMeshPath());
/* 256 */       modelNode.setRenderState(config.getBlendMode().getRenderState());
/*     */       
/* 258 */       return new ClientMeshObjectLMToken.SLoaded(meshDescription, modelNode);
/*     */     }
/*     */   }
/*     */   
/*     */   class SLoaded {
/* 263 */     public MeshDescription meshDescription = null;
/* 264 */     public ModelNode node = null;
/*     */     
/*     */     public SLoaded(MeshDescription meshDescription, ModelNode node) {
/* 267 */       this.meshDescription = meshDescription;
/* 268 */       this.node = node;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ClientMeshObjectLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */