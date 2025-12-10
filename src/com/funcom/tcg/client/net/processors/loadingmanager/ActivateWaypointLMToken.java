/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.WaypointWindowAction;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivateWaypointMessage;
/*     */ import com.jme.scene.Spatial;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class ActivateWaypointLMToken extends LoadingManagerToken {
/*  33 */   ActivateWaypointMessage activateWaypointMessage = null;
/*     */   
/*     */   public ActivateWaypointLMToken(ActivateWaypointMessage activateWaypointMessage) {
/*  36 */     this.activateWaypointMessage = activateWaypointMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  43 */     String[] portalsId = this.activateWaypointMessage.getDestinationPortalsId();
/*  44 */     boolean[] portalsLockingStatus = this.activateWaypointMessage.getDestianPortalsLockingStatus();
/*     */     
/*  46 */     WaypointDescription waypointDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypoint(this.activateWaypointMessage.getWaypointID());
/*     */     
/*  48 */     if (waypointDescription == null) {
/*  49 */       return true;
/*     */     }
/*  51 */     Creature creature = new Creature(this.activateWaypointMessage.getPropId(), waypointDescription.getId(), waypointDescription.getId(), waypointDescription.getWorldCoordinate(), waypointDescription.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     WaypointWindowAction waypointWindowAction = new WaypointWindowAction(waypointDescription, portalsId, portalsLockingStatus);
/*  61 */     creature.addAction((Action)waypointWindowAction);
/*     */     
/*  63 */     String dfxResource = waypointDescription.getDfxImpactResource();
/*  64 */     if (dfxResource != null && dfxResource.length() > 1) {
/*  65 */       creature.addMappedDfx(waypointWindowAction.getName(), dfxResource);
/*     */     }
/*     */     
/*  68 */     PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), waypointWindowAction.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping())));
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
/*     */     try {
/*  82 */       DireEffectDescription dfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDescription.getDfxIdleResource(), true);
/*  83 */       DireEffect direEffect = dfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  84 */       propNode.addDfx(direEffect);
/*  85 */     } catch (NoSuchDFXException e) {
/*  86 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  89 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDescription.getMeshResource());
/*  90 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/*  91 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */     
/*  93 */     clientDescribedModularNode.reloadCharacter();
/*  94 */     propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*  95 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/*  97 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  98 */     TcgGame.getWaypointRegister().addPropNode(propNode);
/*  99 */     propNode.updateRenderState();
/*     */     
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ActivateWaypointLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */