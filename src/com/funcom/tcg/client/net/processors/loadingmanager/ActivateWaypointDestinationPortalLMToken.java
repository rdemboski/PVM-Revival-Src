/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.DestinationPortalAction;
/*     */ import com.funcom.tcg.client.actions.DestinationPortalMouseOver;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivateWaypointDestianationportalMessage;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivateWaypointDestinationPortalLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  44 */   WaypointDestinationPortalDescription waypointDestinationPortalDescription = null;
/*  45 */   Creature creature = null;
/*  46 */   ActivateWaypointDestianationportalMessage Message = null;
/*  47 */   private Future<PropNode> LoadModelFuture = null;
/*     */   
/*     */   public ActivateWaypointDestinationPortalLMToken(ActivateWaypointDestianationportalMessage Message) {
/*  50 */     this.Message = Message;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  55 */     this.waypointDestinationPortalDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypointDestinationPortalDescription(this.Message.getPortalId());
/*     */ 
/*     */     
/*  58 */     this.creature = new Creature(this.Message.getPropId(), this.Message.getName(), this.Message.getName(), this.Message.getWorldCoordinate(), this.waypointDestinationPortalDescription.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     Callable<PropNode> callable = new LoadModelCallable(this.Message, this.waypointDestinationPortalDescription, this.creature);
/*  66 */     this.LoadModelFuture = LoadingManager.INSTANCE.submitCallable(callable);
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  71 */     return (this.LoadModelFuture == null || this.LoadModelFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  77 */     PropNode propNode = null;
/*  78 */     if (this.LoadModelFuture != null && !this.LoadModelFuture.isCancelled()) {
/*  79 */       propNode = this.LoadModelFuture.get();
/*     */     } else {
/*     */       
/*  82 */       Callable<PropNode> callable = new LoadModelCallable(this.Message, this.waypointDestinationPortalDescription, this.creature);
/*  83 */       propNode = callable.call();
/*     */     } 
/*  85 */     this.LoadModelFuture = null;
/*     */     
/*  87 */     if (propNode != null) {
/*  88 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  89 */       TcgGame.getWaypointDestinationRegister().addPropNode(propNode);
/*  90 */       propNode.updateRenderState();
/*     */     } 
/*     */     
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   private void addInactiveDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/*  97 */     PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 105 */       String dfxScript = waypointDestinationPortalDescription.getIdleDFX();
/* 106 */       if (dfxScript.length() > 1) {
/* 107 */         DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(dfxScript, true);
/* 108 */         DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 109 */         propNode.addDfx(direEffect);
/*     */       } 
/* 111 */     } catch (NoSuchDFXException e) {}
/*     */ 
/*     */ 
/*     */     
/* 115 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 116 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 117 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */     
/* 119 */     attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addActivetedDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/* 124 */     DestinationPortalAction action = new DestinationPortalAction(activateWaypointDestianationportalMessage.getPortalId(), NetworkHandler.instance().getIOHandler(), InteractibleType.DESTINATION_PORTAL);
/* 125 */     creature.addAction((Action)action);
/*     */     
/* 127 */     creature.addMappedDfx(action.getName(), waypointDestinationPortalDescription.getImpactDfx());
/*     */     
/* 129 */     DestinationPortalMouseOver destinationPortalMouseOver = new DestinationPortalMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping());
/*     */ 
/*     */     
/* 132 */     PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)destinationPortalMouseOver));
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
/* 143 */     destinationPortalMouseOver.setOwnerPropNode(propNode);
/*     */ 
/*     */     
/*     */     try {
/* 147 */       DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDestinationPortalDescription.getActivationDfx(), true);
/* 148 */       DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 149 */       propNode.addDfx(direEffect);
/*     */     }
/* 151 */     catch (NoSuchDFXException e) {}
/*     */ 
/*     */     
/* 154 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 155 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 156 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */     
/* 158 */     attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/*     */   }
/*     */   
/*     */   private void attachAndReloadprop(PropNode propNode, ModularNode modularNode) {
/* 162 */     modularNode.reloadCharacter();
/* 163 */     propNode.attachRepresentation((Spatial)modularNode);
/* 164 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/* 166 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 167 */     TcgGame.getWaypointDestinationRegister().addPropNode(propNode);
/* 168 */     propNode.updateRenderState();
/*     */   }
/*     */   
/*     */   public class LoadModelCallable implements Callable {
/*     */     ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage;
/*     */     WaypointDestinationPortalDescription waypointDestinationPortalDescription;
/*     */     Creature creature;
/*     */     
/*     */     public LoadModelCallable(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/* 177 */       this.activateWaypointDestianationportalMessage = activateWaypointDestianationportalMessage;
/* 178 */       this.waypointDestinationPortalDescription = waypointDestinationPortalDescription;
/* 179 */       this.creature = creature;
/*     */     }
/*     */     
/*     */     public PropNode call() {
/* 183 */       if (ActivateWaypointDestinationPortalLMToken.this.Message.isActivated()) {
/* 184 */         return addActivetedDestinationPortal(ActivateWaypointDestinationPortalLMToken.this.Message, this.waypointDestinationPortalDescription, this.creature);
/*     */       }
/* 186 */       return addInactiveDestinationPortal(ActivateWaypointDestinationPortalLMToken.this.Message, this.waypointDestinationPortalDescription, this.creature);
/*     */     }
/*     */ 
/*     */     
/*     */     private PropNode addInactiveDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/* 191 */       PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 199 */         String dfxScript = waypointDestinationPortalDescription.getIdleDFX();
/* 200 */         if (dfxScript.length() > 1) {
/* 201 */           DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(dfxScript, true);
/* 202 */           DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 203 */           propNode.addDfx(direEffect);
/*     */         } 
/* 205 */       } catch (NoSuchDFXException e) {}
/*     */ 
/*     */ 
/*     */       
/* 209 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 210 */       XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 211 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */       
/* 213 */       attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/*     */       
/* 215 */       return propNode;
/*     */     }
/*     */ 
/*     */     
/*     */     private PropNode addActivetedDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/* 220 */       DestinationPortalAction action = new DestinationPortalAction(activateWaypointDestianationportalMessage.getPortalId(), NetworkHandler.instance().getIOHandler(), InteractibleType.DESTINATION_PORTAL);
/* 221 */       creature.addAction((Action)action);
/*     */       
/* 223 */       creature.addMappedDfx(action.getName(), waypointDestinationPortalDescription.getImpactDfx());
/*     */       
/* 225 */       DestinationPortalMouseOver destinationPortalMouseOver = new DestinationPortalMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping());
/*     */ 
/*     */       
/* 228 */       PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)destinationPortalMouseOver));
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
/* 239 */       destinationPortalMouseOver.setOwnerPropNode(propNode);
/*     */ 
/*     */       
/*     */       try {
/* 243 */         DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDestinationPortalDescription.getActivationDfx(), true);
/* 244 */         DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 245 */         propNode.addDfx(direEffect);
/*     */       }
/* 247 */       catch (NoSuchDFXException e) {}
/*     */ 
/*     */       
/* 250 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 251 */       XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 252 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */       
/* 254 */       attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/* 255 */       return propNode;
/*     */     }
/*     */     
/*     */     private void attachAndReloadprop(PropNode propNode, ModularNode modularNode) {
/* 259 */       modularNode.reloadCharacter();
/* 260 */       propNode.attachRepresentation((Spatial)modularNode);
/* 261 */       propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ActivateWaypointDestinationPortalLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */