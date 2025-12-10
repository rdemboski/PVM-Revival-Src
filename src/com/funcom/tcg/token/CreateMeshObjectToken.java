/*     */ package com.funcom.tcg.token;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.Token;
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
/*     */ import com.funcom.tcg.factories.XmlMeshFactory;
/*     */ import com.funcom.tcg.token.loadingmanager.CreateMeshObjectLMToken;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import java.awt.Point;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ public class CreateMeshObjectToken
/*     */   implements Token
/*     */ {
/*     */   private static final boolean SUPPORT_3DS = false;
/*     */   public static final String MESH_MODEL_NAME = "MeshModel";
/*     */   protected WorldCoordinate coord;
/*     */   protected float scale;
/*     */   protected float angle;
/*     */   protected float z;
/*     */   private String resourceName;
/*     */   private String name;
/*     */   protected TokenTargetNode tokenTargetNode;
/*     */   protected Point tileCoord;
/*     */   private ResourceGetter resourceGetter;
/*     */   protected float[] tintColor;
/*     */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*     */   
/*     */   public CreateMeshObjectToken(WorldCoordinate coord, float scale, float angle, float z, String name, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  48 */     this.coord = coord;
/*  49 */     this.scale = scale;
/*  50 */     this.angle = angle;
/*  51 */     this.z = z;
/*  52 */     this.name = name;
/*  53 */     this.resourceName = resourceName;
/*  54 */     this.tokenTargetNode = tokenTargetNode;
/*  55 */     this.tileCoord = tileCoord;
/*  56 */     this.resourceGetter = resourceGetter;
/*  57 */     this.tintColor = tintColor;
/*  58 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  62 */     return Token.TokenType.GAME_THREAD;
/*     */   }
/*     */   
/*     */   public void process() {
/*  66 */     if (LoadingManager.USE) {
/*  67 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new CreateMeshObjectLMToken(this.resourceGetter, this.resourceName, this.z, this.name, this.coord, this.angle, this.scale, this.tileCoord, this.tintColor, this.tokenTargetNode, this.effectDescriptionFactory), this.coord.getX().intValue(), this.coord.getY().intValue(), this.tokenTargetNode);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  74 */       Document document = this.resourceGetter.getDocument(this.resourceName, CacheType.CACHE_TEMPORARILY);
/*  75 */       Element rootElement = document.getRootElement();
/*     */       
/*  77 */       MeshDescription meshDescription = new MeshDescription(rootElement);
/*  78 */       boolean loadMd5 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       if (loadMd5) {
/*  89 */         loadMD5model(meshDescription);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadStaticMaxModel(MeshDescription meshDescription, String maxMeshPath) {
/*  95 */     Node mesh = this.resourceGetter.getStaticModel(maxMeshPath);
/*  96 */     mesh.setLocalRotation(TcgConstants.MODEL_ROTATION);
/*  97 */     mesh.setLocalScale(meshDescription.getScaleBase() * 0.0025F);
/*  98 */     mesh.setName("MeshModel");
/*     */ 
/*     */ 
/*     */     
/* 102 */     Prop prop = new Prop(meshDescription.getMeshPath());
/* 103 */     PropNode propNode = new PropNode(prop, 3, meshDescription.getMeshPath(), this.effectDescriptionFactory);
/* 104 */     propNode.setRunsDfxs(false);
/*     */     
/* 106 */     propNode.getLocalTranslation().setY(this.z);
/* 107 */     propNode.getProp().setName(this.name);
/* 108 */     propNode.getProp().setPosition(this.coord);
/* 109 */     propNode.setAngle(this.angle);
/* 110 */     propNode.setResourceName(this.resourceName);
/* 111 */     propNode.setScale(this.scale);
/* 112 */     propNode.updatePropVectors(this.tileCoord.x, this.tileCoord.y);
/* 113 */     propNode.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     
/* 115 */     if (this.tintColor != null) {
/* 116 */       propNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 117 */       propNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 122 */     Node representation = new Node();
/* 123 */     representation.attachChild((Spatial)mesh);
/* 124 */     propNode.attachRepresentation((Spatial)representation);
/*     */     
/* 126 */     propNode.updateGeometricState(1.0F, true);
/* 127 */     propNode.updateRenderState();
/*     */     
/* 129 */     this.tokenTargetNode.attachStaticChild((Spatial)propNode);
/*     */   }
/*     */   
/*     */   private String maxifyTheFilename(MeshDescription meshDescription) {
/* 133 */     String meshPath = meshDescription.getMeshPath();
/* 134 */     return meshPath.substring(0, meshPath.lastIndexOf('.')) + ".3DS";
/*     */   }
/*     */   
/*     */   protected void loadMD5model(MeshDescription meshDescription) {
/* 138 */     PropNode propNode = loadMD5Description(meshDescription);
/* 139 */     propNode.setRunsDfxs(false);
/*     */     
/* 141 */     propNode.getLocalTranslation().setY(this.z);
/* 142 */     propNode.getProp().setName(this.name);
/* 143 */     propNode.getProp().setPosition(this.coord);
/* 144 */     propNode.setAngle(this.angle);
/* 145 */     propNode.setResourceName(this.resourceName);
/* 146 */     propNode.setScale(this.scale);
/* 147 */     propNode.updatePropVectors(this.tileCoord.x, this.tileCoord.y);
/* 148 */     propNode.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     
/* 150 */     if (this.tintColor != null) {
/* 151 */       propNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 152 */       propNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */     } 
/*     */     
/* 155 */     JointController jointController = null;
/* 156 */     for (Controller controller : ((Node)propNode.getRepresentation()).getChild(0).getControllers()) {
/* 157 */       if (controller instanceof JointController) {
/* 158 */         jointController = (JointController)controller; break;
/*     */       } 
/*     */     } 
/* 161 */     if (jointController == null || !jointController.isActive()) {
/* 162 */       this.tokenTargetNode.attachStaticChild((Spatial)propNode);
/* 163 */       if (jointController != null)
/* 164 */         ((Node)propNode.getRepresentation()).getChild(0).removeController((Controller)jointController); 
/*     */     } else {
/* 166 */       this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected PropNode loadMD5Description(MeshDescription meshDescription) {
/* 171 */     XmlMeshFactory factory = new XmlMeshFactory(this.effectDescriptionFactory);
/* 172 */     factory.setDescriptor(meshDescription);
/* 173 */     factory.setResourceGetter(this.resourceGetter);
/* 174 */     PropNode propNode = factory.createPropNode(0.0025F, TcgConstants.MODEL_ROTATION);
/* 175 */     return propNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\token\CreateMeshObjectToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */