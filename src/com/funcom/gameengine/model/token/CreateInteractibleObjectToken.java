/*     */ package com.funcom.gameengine.model.token;
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
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.turborilla.jops.jme.JopsNode;
/*     */ import java.awt.Point;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class CreateInteractibleObjectToken implements Token {
/*     */   private InteractibleProp prop;
/*     */   private String resourceName;
/*     */   private String xmlResourceName;
/*     */   private float scale;
/*     */   private float angle;
/*     */   private TokenTargetNode tokenTargetNode;
/*     */   private Point tileCoord;
/*     */   private ResourceManager resourceManager;
/*     */   private ResourceGetter resourceGetter;
/*     */   private float[] tintColor;
/*     */   private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*     */   private Creature player;
/*     */   private String defaultAction;
/*     */   private LineNode collisionRoot;
/*     */   private MouseOver mouseOver;
/*     */   private String xmlMeshResourcePath;
/*     */   private String dfxResourcePath;
/*     */   private DefaultActionInteractActionHandler defaultActionInteractActionHandler;
/*     */   private float modelScale;
/*     */   private Quaternion modelRotation;
/*     */   private int level;
/*     */   private DireEffectDescription stateDFXDescription;
/*     */   
/*     */   public CreateInteractibleObjectToken(InteractibleProp prop, String resourceName, String xmlResourceName, float scale, float angle, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceManager resourceManager, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory, Creature player, String defaultAction, LineNode collisionRoot, MouseOver mouseOver, String xmlMeshResourcePath, String dfxResourcePath, DefaultActionInteractActionHandler defaultActionInteractActionHandler, float modelScale, Quaternion modelRotation, int level, DireEffectDescription stateDFXDescription) {
/*  62 */     this.prop = prop;
/*  63 */     this.resourceName = resourceName;
/*  64 */     this.xmlResourceName = xmlResourceName;
/*  65 */     this.scale = scale;
/*  66 */     this.angle = angle;
/*  67 */     this.tokenTargetNode = tokenTargetNode;
/*  68 */     this.tileCoord = tileCoord;
/*  69 */     this.resourceManager = resourceManager;
/*  70 */     this.resourceGetter = resourceGetter;
/*  71 */     this.tintColor = tintColor;
/*  72 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*  73 */     this.player = player;
/*  74 */     this.defaultAction = defaultAction;
/*  75 */     this.collisionRoot = collisionRoot;
/*  76 */     this.mouseOver = mouseOver;
/*  77 */     this.xmlMeshResourcePath = xmlMeshResourcePath;
/*  78 */     this.dfxResourcePath = dfxResourcePath;
/*  79 */     this.defaultActionInteractActionHandler = defaultActionInteractActionHandler;
/*  80 */     this.modelScale = modelScale;
/*  81 */     this.modelRotation = modelRotation;
/*  82 */     this.level = level;
/*  83 */     this.stateDFXDescription = stateDFXDescription;
/*     */   }
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  87 */     return Token.TokenType.GAME_THREAD;
/*     */   }
/*     */   
/*     */   public void process() {
/*  91 */     if (this.xmlMeshResourcePath != null && this.dfxResourcePath != null) {
/*  92 */       makePropNodeModel();
/*     */     } else {
/*     */       
/*  95 */       makeBillboardPropNode();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void makePropNodeModel() {
/* 100 */     PropNode propNode = createPropNode();
/* 101 */     propNode.setResourceName(this.xmlResourceName);
/*     */     try {
/* 103 */       createLevelStarDfx(Integer.valueOf(this.level), propNode);
/* 104 */       propNode.setAngle(this.angle);
/* 105 */       propNode.setScale(this.scale);
/* 106 */       createModularNode(propNode);
/* 107 */     } catch (NoSuchDFXException e) {
/* 108 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createLevelStarDfx(Integer level, PropNode propNode) throws NoSuchDFXException {
/* 114 */     if (!this.stateDFXDescription.isEmpty()) {
/* 115 */       for (EffectDescription effectDescription : this.stateDFXDescription.getEffectDescriptions()) {
/* 116 */         if (effectDescription instanceof MeshEffectDescription) {
/* 117 */           String resource = effectDescription.getResource();
/* 118 */           String newString = resource.replace("firstnumbergoeshere", level.toString().substring(0, 1)).replace("secondnumbergoeshere", level.toString().substring(1));
/* 119 */           ((MeshEffectDescription)effectDescription).setResource(newString);
/*     */         } 
/*     */       } 
/* 122 */       propNode.addDfx(this.stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS));
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropNode createPropNode() {
/* 127 */     PropNode propNode = new PropNode((Prop)this.prop, 3, this.prop.getName(), this.direEffectDescriptionFactory, (UserActionHandler)this.defaultActionInteractActionHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (this.mouseOver != null)
/* 135 */       this.mouseOver.setOwnerPropNode(propNode); 
/* 136 */     return propNode;
/*     */   }
/*     */   
/*     */   private void createModularNode(PropNode propNode) {
/* 140 */     Document document = this.resourceGetter.getDocument(this.xmlMeshResourcePath, CacheType.CACHE_TEMPORARILY);
/* 141 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/*     */     
/* 143 */     ModularNode modularNode = new ModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, this.modelRotation, this.modelScale, this.resourceManager);
/* 144 */     modularNode.reloadCharacter();
/* 145 */     propNode.attachRepresentation((Spatial)modularNode);
/*     */     
/* 147 */     propNode.initializeAllEffects(this.resourceGetter, new InnerParticleSurface((Node)new ParticlePassNode("ParticleContent", new DrawPassState(DrawPassType.TRANSPARENT_CONTENT))));
/* 148 */     this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/*     */   }
/*     */   
/*     */   private void makeBillboardPropNode() {
/* 152 */     PropNode propNode = BillboardFactory.newBillboard(this.resourceGetter, (Prop)this.prop, this.resourceName, this.angle, this.scale, this.tileCoord, SpatialUtils.convertToColorRGBA(this.tintColor), this.direEffectDescriptionFactory);
/*     */     
/* 154 */     propNode.setResourceName(this.xmlResourceName);
/* 155 */     if (!this.prop.getActions().isEmpty()) {
/* 156 */       if (this.mouseOver != null)
/* 157 */         this.mouseOver.setOwnerPropNode(propNode); 
/* 158 */       DefaultActionInteractActionHandler defaultActionInteractActionHandler = new DefaultActionInteractActionHandler(this.prop, this.player, this.defaultAction, this.collisionRoot, this.mouseOver);
/* 159 */       propNode.setActionHandler((UserActionHandler)defaultActionInteractActionHandler);
/*     */     } 
/*     */     
/* 162 */     this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/*     */   }
/*     */   
/*     */   private class InnerParticleSurface implements ParticleSurface {
/*     */     private Node particleNode;
/*     */     
/*     */     private InnerParticleSurface(Node particleNode) {
/* 169 */       this.particleNode = particleNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addWorldParticleEmitter(JopsNode jopsNode) {
/* 174 */       this.particleNode.attachChild((Spatial)jopsNode);
/*     */     }
/*     */     public void addWorldParticles(JopsNode jopsNode) {
/* 177 */       this.particleNode.attachChild((Spatial)jopsNode.getParticleNode());
/*     */     }
/*     */     
/*     */     public void removeWorldParticles(JopsNode jopsNode) {
/* 181 */       this.particleNode.detachChild((Spatial)jopsNode.getParticleNode());
/*     */     }
/*     */ 
/*     */     
/*     */     public void addDisconnectedMeshEffect(PropNode mesh) {
/* 186 */       this.particleNode.attachChild((Spatial)mesh);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateInteractibleObjectToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */