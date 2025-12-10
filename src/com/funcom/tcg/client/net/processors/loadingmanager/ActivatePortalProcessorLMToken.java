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
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.TownPortalMouseOver;
/*     */ import com.funcom.tcg.client.actions.ZoneAction;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivatePortalMessage;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import com.funcom.tcg.portals.TownPortalPropertyReader;
/*     */ import com.jme.scene.Spatial;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivatePortalProcessorLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  42 */   ActivatePortalMessage activatePortalMessage = null;
/*     */   
/*     */   public ActivatePortalProcessorLMToken(ActivatePortalMessage activatePortalMessage) {
/*  45 */     this.activatePortalMessage = activatePortalMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  52 */     Creature creature = new Creature(this.activatePortalMessage.getPortalId(), this.activatePortalMessage.getPortalName(), this.activatePortalMessage.getPortalName(), this.activatePortalMessage.getDestinationCoords(), this.activatePortalMessage.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     PropNode propNode = createPropNode(this.activatePortalMessage, creature);
/*     */     try {
/*  62 */       addDfx(propNode, this.activatePortalMessage);
/*  63 */     } catch (NoSuchDFXException e) {
/*  64 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  67 */     ModularNode modularNode = getModularNode(this.activatePortalMessage, propNode);
/*  68 */     modularNode.reloadCharacter();
/*  69 */     propNode.attachRepresentation((Spatial)modularNode);
/*  70 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/*  72 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  73 */     switch (this.activatePortalMessage.getPortalType()) {
/*     */       case CUSTOM_PORTAL:
/*  75 */         TcgGame.getCustomPortalRegister().addPropNode(propNode);
/*     */         break;
/*     */       case RETURN_POINT:
/*  78 */         TcgGame.getReturnPointRegister().addPropNode(propNode);
/*     */         break;
/*     */       case TOWN_PORTAL:
/*  81 */         TcgGame.getTownPortalRegister().addPropNode(propNode);
/*     */         break;
/*     */     } 
/*  84 */     propNode.updateRenderState();
/*     */     
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   private PropNode createPropNode(ActivatePortalMessage activatePortalMessage, Creature prop) {
/*     */     PropNode propNode;
/*  91 */     if (activatePortalMessage.getPlayerId() == MainGameState.getPlayerModel().getId()) {
/*     */       
/*  93 */       ZoneAction zoneAction = addAction((InteractibleProp)prop, activatePortalMessage);
/*     */       
/*  95 */       TownPortalMouseOver mouseOver = new TownPortalMouseOver(MainGameState.getMouseCursorSetter(), prop, MainGameState.getActionNameToCursorMapping());
/*  96 */       propNode = new PropNode((Prop)prop, 3, prop.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)prop, (Creature)MainGameState.getPlayerModel(), zoneAction.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)mouseOver));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       mouseOver.setOwnerPropNode(propNode);
/*     */     } else {
/* 108 */       propNode = new PropNode((Prop)prop, 3, prop.getName(), TcgGame.getDireEffectDescriptionFactory());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     return propNode;
/*     */   }
/*     */   
/*     */   private ZoneAction addAction(InteractibleProp prop, ActivatePortalMessage activatePortalMessage) {
/* 117 */     ZoneAction action = new ZoneAction(prop.getName(), NetworkHandler.instance().getIOHandler(), activatePortalMessage.getPortalType());
/* 118 */     prop.addAction((Action)action);
/* 119 */     return action;
/*     */   }
/*     */   
/*     */   private void addDfx(PropNode propNode, ActivatePortalMessage activatePortalMessage) throws NoSuchDFXException {
/*     */     DireEffectDescription description;
/* 124 */     TownPortalPropertyReader portalPropertyReader = MainGameState.getTownPortalPropertyReader();
/* 125 */     if (activatePortalMessage.getPlayerId() == MainGameState.getPlayerModel().getId()) {
/*     */       
/* 127 */       switch (activatePortalMessage.getPortalType()) {
/*     */         case RETURN_POINT:
/* 129 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("return_point_dfx"), true);
/*     */           break;
/*     */         case TOWN_PORTAL:
/* 132 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_dfx"), true);
/*     */           break;
/*     */         default:
/* 135 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_dfx"), true);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 140 */       description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_other_dfx"), true);
/*     */     } 
/* 142 */     DireEffect effect = description.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 143 */     propNode.addDfx(effect);
/*     */   }
/*     */   
/*     */   private ModularNode getModularNode(ActivatePortalMessage activatePortalMessage, PropNode propNode) {
/* 147 */     TownPortalPropertyReader portalPropertyReader = MainGameState.getTownPortalPropertyReader();
/*     */     
/* 149 */     switch (activatePortalMessage.getPortalType())
/*     */     { case CUSTOM_PORTAL:
/* 151 */         document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_xml"));
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
/* 164 */         xmlModularDescription = new XmlModularDescription(document);
/* 165 */         return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());case RETURN_POINT: document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("return_point_xml")); xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());case TOWN_PORTAL: document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_xml")); xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager()); }  Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_other_xml")); XmlModularDescription xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ActivatePortalProcessorLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */