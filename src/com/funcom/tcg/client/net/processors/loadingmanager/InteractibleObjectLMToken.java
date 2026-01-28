/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.EffectDescription;
/*     */ import com.funcom.commons.dfx.MeshEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.DrawPassType;
/*     */ import com.funcom.gameengine.jme.ParticlePassNode;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.factories.BillboardFactory;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.turborilla.jops.jme.JopsNode;
/*     */ import java.awt.Point;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class InteractibleObjectLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  47 */   private Future LoadModelFuture = null;
/*     */   
/*     */   InteractibleProp prop;
/*     */   
/*     */   String resourceName;
/*     */   
/*     */   String xmlResourceName;
/*     */   
/*     */   float scale;
/*     */   
/*     */   float angle;
/*     */   
/*     */   float[] tintColor;
/*     */   
/*     */   TokenTargetNode tokenTargetNode;
/*     */   Point tileCoord;
/*     */   ResourceManager resourceManager;
/*     */   ResourceGetter resourceGetter;
/*     */   DireEffectDescriptionFactory direEffectDescriptionFactory;
/*     */   Creature player;
/*     */   String defaultAction;
/*     */   LineNode collisionRoot;
/*     */   MouseOver mouseOver;
/*     */   String xmlMeshResourcePath;
/*     */   String dfxResourcePath;
/*     */   DefaultActionInteractActionHandler defaultActionInteractActionHandler;
/*     */   float modelScale;
/*     */   Quaternion modelRotation;
/*     */   int level;
/*     */   DireEffectDescription stateDFXDescription;
/*     */   
/*     */   public InteractibleObjectLMToken(InteractibleProp prop, String resourceName, String xmlResourceName, float scale, float angle, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceManager resourceManager, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory, Creature player, String defaultAction, LineNode collisionRoot, MouseOver mouseOver, String xmlMeshResourcePath, String dfxResourcePath, DefaultActionInteractActionHandler defaultActionInteractActionHandler, float modelScale, Quaternion modelRotation, int level, DireEffectDescription stateDFXDescription) {
/*  79 */     this.prop = prop;
/*  80 */     this.resourceName = resourceName;
/*  81 */     this.xmlResourceName = xmlResourceName;
/*  82 */     this.scale = scale;
/*  83 */     this.angle = angle;
/*  84 */     this.tintColor = tintColor;
/*  85 */     this.tokenTargetNode = tokenTargetNode;
/*  86 */     this.tileCoord = tileCoord;
/*  87 */     this.resourceManager = resourceManager;
/*  88 */     this.resourceGetter = resourceGetter;
/*  89 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*  90 */     this.player = player;
/*  91 */     this.defaultAction = defaultAction;
/*  92 */     this.collisionRoot = collisionRoot;
/*  93 */     this.mouseOver = mouseOver;
/*  94 */     this.xmlMeshResourcePath = xmlMeshResourcePath;
/*  95 */     this.dfxResourcePath = dfxResourcePath;
/*  96 */     this.defaultActionInteractActionHandler = defaultActionInteractActionHandler;
/*  97 */     this.modelScale = modelScale;
/*  98 */     this.modelRotation = modelRotation;
/*  99 */     this.level = level;
/* 100 */     this.stateDFXDescription = stateDFXDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/* 108 */     Callable<PropNode> callable = new LoadModelCallable();
/* 109 */     this.LoadModelFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/* 110 */     if (this.LoadModelFuture == null) {
/* 111 */       throw new Exception("processRequestAssets: the LoadModelFuture is null.");
/*     */     }
/*     */ 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/* 119 */     return (this.LoadModelFuture == null || this.LoadModelFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/* 125 */     PropNode propNode = null;
/* 126 */     if (this.LoadModelFuture != null && !this.LoadModelFuture.isCancelled()) {
/* 127 */       propNode = (PropNode)this.LoadModelFuture.get();
/*     */     
/*     */     }
/* 130 */     else if (this.xmlMeshResourcePath != null && this.dfxResourcePath != null) {
/* 131 */       propNode = makePropNodeModel();
/*     */     } else {
/*     */       
/* 134 */       propNode = makeBillboardPropNode();
/*     */     } 
/*     */     
/* 137 */     this.LoadModelFuture = null;
/*     */     
/* 139 */     this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/* 140 */     MainGameState.getWorld().addCollisionObject((RepresentationalNode)propNode);
/*     */     
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private PropNode makePropNodeModel() {
/* 147 */     PropNode propNode = createPropNode();
/*     */     try {
/* 149 */       createLevelStarDfx(Integer.valueOf(this.level), propNode);
/* 150 */       propNode.setAngle(this.angle);
/* 151 */       propNode.setScale(this.scale);
/* 152 */       createModularNode(propNode);
/* 153 */     } catch (NoSuchDFXException e) {
/* 154 */       e.printStackTrace();
/*     */     } 
/* 156 */     return propNode;
/*     */   }
/*     */   
/*     */   private void createLevelStarDfx(Integer level, PropNode propNode) throws NoSuchDFXException {
/* 160 */     if (!this.stateDFXDescription.isEmpty()) {
/* 161 */       for (EffectDescription effectDescription : this.stateDFXDescription.getEffectDescriptions()) {
/* 162 */         if (effectDescription instanceof MeshEffectDescription) {
/* 163 */           String resource = effectDescription.getResource();
/* 164 */           String newString = resource.replace("firstnumbergoeshere", level.toString().substring(0, 1)).replace("secondnumbergoeshere", level.toString().substring(1));
/* 165 */           ((MeshEffectDescription)effectDescription).setResource(newString);
/*     */         } 
/*     */       } 
/* 168 */       propNode.addDfx(this.stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS));
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropNode createPropNode() {
/* 173 */     PropNode propNode = new PropNode((Prop)this.prop, 3, this.prop.getName(), this.direEffectDescriptionFactory, (UserActionHandler)this.defaultActionInteractActionHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     this.mouseOver.setOwnerPropNode(propNode);
/* 181 */     return propNode;
/*     */   }
/*     */   
/*     */   private void createModularNode(PropNode propNode) {
/* 185 */     Document document = this.resourceGetter.getDocument(this.xmlMeshResourcePath, CacheType.CACHE_TEMPORARILY);
/* 186 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/*     */     
/* 188 */     ModularNode modularNode = new ModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, this.modelRotation, this.modelScale, this.resourceManager);
/* 189 */     modularNode.reloadCharacter();
/* 190 */     propNode.attachRepresentation((Spatial)modularNode);
/*     */     
/* 192 */     propNode.initializeAllEffects(this.resourceGetter, new InnerParticleSurface((Node)new ParticlePassNode("ParticleContent", new DrawPassState(DrawPassType.TRANSPARENT_CONTENT))));
/*     */   }
/*     */ 
/*     */   
/*     */   private PropNode makeBillboardPropNode() {
/* 197 */     PropNode propNode = BillboardFactory.newBillboard(this.resourceGetter, (Prop)this.prop, this.resourceName, this.angle, this.scale, this.tileCoord, SpatialUtils.convertToColorRGBA(this.tintColor), this.direEffectDescriptionFactory);
/*     */     
/* 199 */     propNode.setResourceName(this.xmlResourceName);
/* 200 */     if (!this.prop.getActions().isEmpty()) {
/* 201 */       if (this.mouseOver != null)
/* 202 */         this.mouseOver.setOwnerPropNode(propNode); 
/* 203 */       DefaultActionInteractActionHandler defaultActionInteractActionHandler = new DefaultActionInteractActionHandler(this.prop, this.player, this.defaultAction, this.collisionRoot, this.mouseOver);
/* 204 */       propNode.setActionHandler((UserActionHandler)defaultActionInteractActionHandler);
/*     */     } 
/*     */ 
/*     */     
/* 208 */     return propNode;
/*     */   }
/*     */   
/*     */   private class InnerParticleSurface implements ParticleSurface {
/*     */     private Node particleNode;
/*     */     
/*     */     private InnerParticleSurface(Node particleNode) {
/* 215 */       this.particleNode = particleNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addWorldParticleEmitter(JopsNode jopsNode) {
/* 220 */       this.particleNode.attachChild((Spatial)jopsNode);
/*     */     }
/*     */     public void addWorldParticles(JopsNode jopsNode) {
/* 223 */       this.particleNode.attachChild((Spatial)jopsNode.getParticleNode());
/*     */     }
/*     */     
/*     */     public void removeWorldParticles(JopsNode jopsNode) {
/* 227 */       this.particleNode.detachChild((Spatial)jopsNode.getParticleNode());
/*     */     }
/*     */ 
/*     */     
/*     */     public void addDisconnectedMeshEffect(PropNode mesh) {
/* 232 */       this.particleNode.attachChild((Spatial)mesh);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class LoadModelCallable
/*     */     implements Callable<PropNode>
/*     */   {
/*     */     public PropNode call() {
/* 242 */       PropNode propNode = null;
/* 243 */       if (InteractibleObjectLMToken.this.xmlMeshResourcePath != null && InteractibleObjectLMToken.this.dfxResourcePath != null) {
/* 244 */         propNode = InteractibleObjectLMToken.this.makePropNodeModel();
/*     */       } else {
/*     */         
/* 247 */         propNode = InteractibleObjectLMToken.this.makeBillboardPropNode();
/*     */       } 
/*     */       
/* 250 */       return propNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\InteractibleObjectLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */