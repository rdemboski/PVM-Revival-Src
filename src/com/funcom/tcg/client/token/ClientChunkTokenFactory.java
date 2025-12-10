/*     */ package com.funcom.tcg.client.token;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*     */ import com.funcom.gameengine.model.input.ActionExistsDecorator;
/*     */ import com.funcom.gameengine.model.input.CombinedMouseOver;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.TintMouseOver;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.CreateInteractibleObjectToken;
/*     */ import com.funcom.gameengine.model.token.CreateParticleToken;
/*     */ import com.funcom.gameengine.model.token.SpatialChunkTokenFactory;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.CreateParticleLMToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*     */ import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
/*     */ import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
/*     */ import com.funcom.rpgengine2.portkey.PortkeyDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.ZoneActionInteractActionHandler;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.ClientDecalLMToken;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.ClientStaticObjectLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class ClientChunkTokenFactory extends SpatialChunkTokenFactory {
/*     */   public ClientChunkTokenFactory(DireEffectDescriptionFactory effectDescriptionFactory, ResourceManager resourceManager) {
/*  42 */     super(effectDescriptionFactory);
/*  43 */     this.resourceManager = resourceManager;
/*     */   }
/*     */   
/*     */   private final ResourceManager resourceManager;
/*     */   
/*     */   public Token createStaticObjectToken(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  49 */     if (LoadingManager.USE) {
/*  50 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new ClientStaticObjectLMToken(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter), coord.getX().intValue(), coord.getY().intValue(), tokenTargetNode);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  55 */       return (Token)new CreateClientStaticObjectToken(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*     */     } 
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Token createDecalToken(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  62 */     if (LoadingManager.USE) {
/*  63 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new ClientDecalLMToken(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, this.effectDescriptionFactory), coord.getX().intValue(), coord.getY().intValue(), tokenTargetNode);
/*     */       
/*  65 */       return null;
/*     */     } 
/*     */     
/*  68 */     return (Token)new CreateClientDecalToken(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, this.effectDescriptionFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Token createLayeredTextureTileToken(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, String xmlBackground, String xmlCorner1, String xmlCorner2, String xmlCorner3, String xmlCorner4, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  73 */     return (Token)new CreateClientLayeredTextureTileToken(x, y, backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, tokenTargetNode, tileCoord, resourceGetter);
/*     */   }
/*     */ 
/*     */   
/*     */   public Token createMeshObjectToken(WorldCoordinate coord, float scale, float angle, float z, String name, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  78 */     if (LoadingManager.USE) {
/*  79 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new ClientMeshObjectLMToken(resourceGetter, resourceName, z, name, coord, angle, scale, tileCoord, tintColor, tokenTargetNode, this.effectDescriptionFactory), coord.getX().intValue(), coord.getY().intValue(), tokenTargetNode);
/*     */ 
/*     */       
/*  82 */       return null;
/*     */     } 
/*     */     
/*  85 */     return (Token)new CreateClientMeshObjectToken(coord, scale, angle, z, name, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, TcgGame.getDireEffectDescriptionFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Token createMergedMeshObjectToken(ArrayList<Element> meshElements, TokenTargetNode tokenTargetNode, ResourceGetter resourceGetter) {
/*  91 */     if (LoadingManager.USE) {
/*  92 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new CreateMergedMeshLMToken(meshElements, tokenTargetNode, resourceGetter, TcgGame.getDireEffectDescriptionFactory()), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_MAP);
/*  93 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Token createInteractibleObjectToken(InteractibleProp prop, String defaultActionName, WorldCoordinate coord, float scale, float angle, String resourceName, String xmlResourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, String rpgId) {
/* 102 */     TintMouseOver mouseOver = new TintMouseOver((new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F), Effects.TintMode.ADDITIVE);
/*     */     
/* 104 */     PortkeyDescription description = TcgGame.getRpgLoader().getPortkeyManager().getPortkeyDescription(rpgId);
/* 105 */     String xmlMeshResource = null;
/* 106 */     String dfxResources = null;
/* 107 */     String dfxText = null;
/* 108 */     int level = -1;
/* 109 */     boolean subscriberOnly = false;
/* 110 */     List<String> keys = new ArrayList<String>();
/* 111 */     String completeQuest = "";
/* 112 */     String onQuest = "";
/* 113 */     DireEffectDescription stateDFXDescription = null;
/* 114 */     if (description != null) {
/* 115 */       xmlMeshResource = description.getMeshResource();
/* 116 */       dfxResources = description.getDfxResource();
/* 117 */       level = description.getLevelReq();
/* 118 */       dfxText = description.getDfxText();
/* 119 */       subscriberOnly = description.isSubscriptionNeeded();
/* 120 */       keys = description.getAccessKeys();
/* 121 */       completeQuest = description.getCompleteQuestId();
/* 122 */       onQuest = description.getOnQuestId();
/* 123 */       Element effectRootElement = (new ClientDFXResourceLoader(this.resourceManager, TcgGame.getParticleProcessor())).getDireEffectData(dfxResources, false);
/* 124 */       stateDFXDescription = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(effectRootElement, dfxResources, false);
/*     */     } 
/*     */     
/* 127 */     CombinedMouseOver combinedMouseOver = new CombinedMouseOver(new MouseOver[] { (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), prop, MainGameState.getActionNameToCursorMapping()), (MouseOver)new ActionExistsDecorator(prop, "zone", (MouseOver)mouseOver) });
/*     */ 
/*     */ 
/*     */     
/* 131 */     ZoneActionInteractActionHandler interactActionHandler = new ZoneActionInteractActionHandler(prop, (Creature)MainGameState.getPlayerModel(), defaultActionName, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)combinedMouseOver, level, subscriberOnly, completeQuest, onQuest, dfxText, keys);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (LoadingManager.USE) {
/* 146 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new InteractibleObjectLMToken(prop, resourceName, xmlResourceName, scale, angle, tintColor, tokenTargetNode, tileCoord, this.resourceManager, resourceGetter, TcgGame.getDireEffectDescriptionFactory(), (Creature)MainGameState.getPlayerModel(), defaultActionName, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)combinedMouseOver, xmlMeshResource, dfxResources, (DefaultActionInteractActionHandler)interactActionHandler, 0.0025F, TcgConstants.MODEL_ROTATION, level, stateDFXDescription));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       return null;
/*     */     } 
/*     */     
/* 172 */     return (Token)new CreateInteractibleObjectToken(prop, resourceName, xmlResourceName, scale, angle, tintColor, tokenTargetNode, tileCoord, this.resourceManager, resourceGetter, TcgGame.getDireEffectDescriptionFactory(), (Creature)MainGameState.getPlayerModel(), defaultActionName, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)combinedMouseOver, xmlMeshResource, dfxResources, (DefaultActionInteractActionHandler)interactActionHandler, 0.0025F, TcgConstants.MODEL_ROTATION, level, stateDFXDescription);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token createWaterLineToken(float height, float transparency, int shoreType, int textureDistribution, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterLineCoordinateSet> waterLines, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 207 */     return (Token)new CreateClientWaterLineToken(height, transparency, shoreType, textureDistribution, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, waterLines, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, resourceGetter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token createWaterPondToken(float height, float transparency, boolean useShoreTexture, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterPondCoordinateSet> pondPoints, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 228 */     return (Token)new CreateClientWaterPondToken(height, transparency, useShoreTexture, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, pondPoints, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, resourceGetter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token createParticleObjectToken(String name, WorldCoordinate coord, float scale, float angle, float z, String resourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 242 */     if (LoadingManager.USE) {
/* 243 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new CreateParticleLMToken(name, coord, scale, angle, z, resourceName, tokenTargetNode, tileCoord, this.effectDescriptionFactory), coord.getX().intValue(), coord.getY().intValue(), tokenTargetNode);
/*     */       
/* 245 */       return null;
/*     */     } 
/* 247 */     return (Token)new CreateParticleToken(name, coord, scale, angle, z, resourceName, tokenTargetNode, tileCoord, this.effectDescriptionFactory);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\ClientChunkTokenFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */