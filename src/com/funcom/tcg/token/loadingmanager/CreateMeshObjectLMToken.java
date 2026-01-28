/*     */ package com.funcom.tcg.token.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.GraphicsConfig;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.Effects;
/*     */ import com.funcom.gameengine.view.PropNode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateMeshObjectLMToken
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
/*  51 */   private Future<SLoaded> LoadModelFuture = null;
/*     */   
/*  53 */   private ResourceGetter resourceGetter = null;
/*  54 */   private String resourceName = "";
/*     */   private static final String MESH_MODEL_NAME = "MeshNode";
/*     */   
/*     */   public CreateMeshObjectLMToken(ResourceGetter resourceGetter, String resourceName, float z, String name, WorldCoordinate coord, float angle, float scale, Point tileCoord, float[] tintColor, TokenTargetNode tokenTargetNode, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  58 */     this.z = z;
/*  59 */     this.name = name;
/*  60 */     this.coord = coord;
/*  61 */     this.angle = angle;
/*  62 */     this.scale = scale;
/*  63 */     this.tileCoord = tileCoord;
/*  64 */     this.tintColor = tintColor;
/*  65 */     this.tokenTargetNode = tokenTargetNode;
/*  66 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*  67 */     this.resourceGetter = resourceGetter;
/*  68 */     this.resourceName = resourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
              Callable<SLoaded> callable = new LoadModelCallable(TcgConstants.MODEL_ROTATION);
              this.LoadModelFuture = (Future<SLoaded>) LoadingManager.INSTANCE.submitCallable(callable);

/*  78 */     if (this.LoadModelFuture == null) {
/*  79 */       throw new Exception("processWaitingAssets: the LoadModelFuture is null.");
/*     */     }
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
/*  94 */     PropNode propNode = createPropNode(0.0025F, TcgConstants.MODEL_ROTATION);
/*     */     
/*  96 */     propNode.setRunsDfxs(false);
/*     */     
/*  98 */     propNode.getLocalTranslation().setY(this.z);
/*  99 */     propNode.getProp().setName(this.name);
/* 100 */     propNode.getProp().setPosition(this.coord);
/* 101 */     propNode.setAngle(this.angle);
/* 102 */     propNode.setResourceName(this.resourceName);
/* 103 */     propNode.setScale(this.scale);
/* 104 */     propNode.updatePropVectors(this.tileCoord.x, this.tileCoord.y);
/* 105 */     propNode.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     
/* 107 */     if (this.tintColor != null) {
/* 108 */       propNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 109 */       propNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */     } 
/*     */     
/* 112 */     JointController jointController = null;
/* 113 */     for (Controller controller : ((Node)propNode.getRepresentation()).getChild(0).getControllers()) {
/* 114 */       if (controller instanceof JointController) {
/* 115 */         jointController = (JointController)controller; break;
/*     */       } 
/*     */     } 
/* 118 */     if (jointController == null || !jointController.isActive()) {
/* 119 */       this.tokenTargetNode.attachStaticChild((Spatial)propNode);
/* 120 */       if (jointController != null)
/* 121 */         ((Node)propNode.getRepresentation()).getChild(0).removeController((Controller)jointController); 
/*     */     } else {
/* 123 */       this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/*     */     } 
/*     */     
/* 126 */     propNode.propagateBoundToRoot();
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PropNode createPropNode(float modelScale, Quaternion modelRotation) throws Exception {
/* 134 */     MeshDescription meshDescription = null;
/* 135 */     ModelNode modelNode = null;
/* 136 */     PropNode propNode = null;
/*     */     
/* 138 */     if (this.LoadModelFuture != null && !this.LoadModelFuture.isCancelled() && this.LoadModelFuture.get() != null) {
/*     */       
/* 140 */       SLoaded l = this.LoadModelFuture.get();
/* 141 */       propNode = l.propNode;
/* 142 */       meshDescription = l.meshDescription;
/* 143 */       modelNode = l.node;
/*     */       
/* 145 */       Node representation = new Node();
/* 146 */       representation.attachChild((Spatial)modelNode);
/* 147 */       propNode.attachRepresentation((Spatial)representation);
/*     */       
/* 149 */       modelNode.updateRenderState();
/*     */     } else {
/*     */       
/* 152 */       Document document = this.resourceGetter.getDocument(this.resourceName, CacheType.CACHE_TEMPORARILY);
/* 153 */       Element rootElement = document.getRootElement();
/* 154 */       meshDescription = new MeshDescription(rootElement);
/*     */       
/* 156 */       Prop prop = new Prop("MeshNode");
/* 157 */       propNode = new PropNode(prop, 3, meshDescription.getMeshPath(), this.effectDescriptionFactory);
/* 158 */       propNode.setRunsDfxs(false);
/*     */       
/* 160 */       modelNode = this.resourceGetter.getModelNode(meshDescription.getMeshPath());
/*     */       
/* 162 */       modelNode.setLocalRotation(modelRotation);
/* 163 */       modelNode.setName("MeshNode");
/*     */       
/* 165 */       List<MeshDescription.AnimationDescription> animations = meshDescription.getAnimations();
/* 166 */       for (MeshDescription.AnimationDescription animation : animations) {
/* 167 */         JointAnimation jointAnimation = this.resourceGetter.getModelAnimation(animation.getPath());
/* 168 */         jointAnimation.setName(animation.getName());
/* 169 */         modelNode.addAnimation(jointAnimation);
/*     */       } 
/* 171 */       JointController jointController = null;
/* 172 */       for (Controller controller : modelNode.getControllers()) {
/* 173 */         if (controller instanceof JointController) {
/* 174 */           jointController = (JointController)controller;
/*     */           break;
/*     */         } 
/*     */       } 
/* 178 */       if (jointController != null) {
/* 179 */         boolean active = false;
/* 180 */         for (JointAnimation animation : jointController.getAnimations().values()) {
/* 181 */           if ((animation.getFrames()).length > 2) {
/* 182 */             active = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 186 */         jointController.update(0.0F);
/* 187 */         jointController.setActive(active);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 192 */       modelNode.setLocalScale(meshDescription.getScaleBase() * modelScale);
/* 193 */       Node representation = new Node();
/* 194 */       representation.attachChild((Spatial)modelNode);
/* 195 */       propNode.attachRepresentation((Spatial)representation);
/*     */ 
/*     */       
/* 198 */       GraphicsConfig config = GraphicsConfig.getModelConfig(this.resourceGetter, meshDescription.getMeshPath());
/* 199 */       modelNode.setRenderState(config.getBlendMode().getRenderState());
/* 200 */       modelNode.updateRenderState();
/*     */     } 
/* 202 */     this.LoadModelFuture = null;
/*     */     
/* 204 */     return propNode;
/*     */   }
/*     */   
/*     */   public class LoadModelCallable
/*     */     implements Callable<SLoaded>
/*     */   {
              Quaternion modelRotation = null;
                    
              public LoadModelCallable(Quaternion modelRotation) {
                this.modelRotation = modelRotation;
              }
/*     */ 
/*     */     
/*     */     public SLoaded call() {
/* 218 */       Document document = CreateMeshObjectLMToken.this.resourceGetter.getDocument(CreateMeshObjectLMToken.this.resourceName, CacheType.CACHE_TEMPORARILY);
/* 219 */       Element rootElement = document.getRootElement();
/* 220 */       MeshDescription meshDescription = new MeshDescription(rootElement);
/*     */       
/* 222 */       ModelNode modelNode = CreateMeshObjectLMToken.this.resourceGetter.getModelNode(meshDescription.getMeshPath());
/*     */       
/* 224 */       modelNode.setLocalRotation(this.modelRotation);
/* 225 */       modelNode.setName("MeshNode");
/*     */       
/* 227 */       List<MeshDescription.AnimationDescription> animations = meshDescription.getAnimations();
/* 228 */       for (MeshDescription.AnimationDescription animation : animations) {
/* 229 */         JointAnimation jointAnimation = CreateMeshObjectLMToken.this.resourceGetter.getModelAnimation(animation.getPath());
/* 230 */         jointAnimation.setName(animation.getName());
/* 231 */         modelNode.addAnimation(jointAnimation);
/*     */       } 
/* 233 */       JointController jointController = null;
/* 234 */       for (Controller controller : modelNode.getControllers()) {
/* 235 */         if (controller instanceof JointController) {
/* 236 */           jointController = (JointController)controller;
/*     */           break;
/*     */         } 
/*     */       } 
/* 240 */       if (jointController != null) {
/* 241 */         boolean active = false;
/* 242 */         for (JointAnimation animation : jointController.getAnimations().values()) {
/* 243 */           if ((animation.getFrames()).length > 2) {
/* 244 */             active = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 248 */         jointController.update(0.0F);
/* 249 */         jointController.setActive(active);
/*     */       } 
/*     */       
/* 252 */       Prop prop = new Prop("MeshNode");
/* 253 */       PropNode propNode = new PropNode(prop, 3, meshDescription.getMeshPath(), CreateMeshObjectLMToken.this.effectDescriptionFactory);
/* 254 */       propNode.setRunsDfxs(false);
/*     */ 
/*     */ 
/*     */       
/* 258 */       modelNode.setLocalScale(meshDescription.getScaleBase() * 0.0025F);
/*     */ 
/*     */       
/* 261 */       GraphicsConfig config = GraphicsConfig.getModelConfig(CreateMeshObjectLMToken.this.resourceGetter, meshDescription.getMeshPath());
/* 262 */       modelNode.setRenderState(config.getBlendMode().getRenderState());
/*     */       
/* 264 */       return new CreateMeshObjectLMToken.SLoaded(meshDescription, modelNode, propNode);
/*     */     }
/*     */   }
/*     */   
/*     */   class SLoaded {
/* 269 */     public MeshDescription meshDescription = null;
/* 270 */     public ModelNode node = null;
/* 271 */     public PropNode propNode = null;
/*     */     
/*     */     public SLoaded(MeshDescription meshDescription, ModelNode node, PropNode propNode) {
/* 274 */       this.meshDescription = meshDescription;
/* 275 */       this.node = node;
/* 276 */       this.propNode = propNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\token\loadingmanager\CreateMeshObjectLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */