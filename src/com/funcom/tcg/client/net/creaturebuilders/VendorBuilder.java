/*     */ package com.funcom.tcg.client.net.creaturebuilders;
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*     */ import com.funcom.gameengine.model.input.ActionExistsDecorator;
/*     */ import com.funcom.gameengine.model.input.CombinedMouseOver;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.TintMouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.rpgengine2.vendor.VendorDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.controllers.BarkingController;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.VendorCreationMessage;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class VendorBuilder {
/*     */   public PropNode build(VendorCreationMessage message) {
/*  35 */     startTiming();
/*  36 */     VendorDescription description = TcgGame.getRpgLoader().getVendorManager().getVendorDescription(message.getVendorId());
/*  37 */     Creature vendorCreature = new Creature(message.getCreatureId(), description.getName(), message.getVendorId(), message.getCoord(), message.getRadius());
/*  38 */     vendorCreature.setRotation(message.getAngle());
/*  39 */     vendorCreature.addAction((Action)new OpenVendorGuiAction(description.getName(), message.getVendorItems(), message.getBuyBackItems()));
/*     */ 
/*     */     
/*  42 */     PropNode vendorNode = buildModel(vendorCreature, message.getModel());
/*  43 */     vendorNode.setActionHandler((UserActionHandler)vendorActionHandler(vendorCreature, vendorNode));
/*     */ 
/*     */ 
/*     */     
/*  47 */     vendorNode.getBasicEffectsNode().setState(OverheadIcons.State.VENDOR);
/*  48 */     SpatialUtils.addShadow(vendorNode, TcgGame.getResourceManager());
/*     */     
/*  50 */     endTiming();
/*  51 */     return vendorNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void startTiming() {
/*  56 */     if (Debug.stats)
/*  57 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private static void endTiming() {
/*  61 */     if (Debug.stats)
/*  62 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private DefaultActionInteractActionHandler vendorActionHandler(Creature vendorCreature, PropNode vendorNode) {
/*  66 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(vendorCreature.getMonsterId());
/*  67 */     if (speachMapping != null && 
/*  68 */       speachMapping.isBarks())
/*     */     {
/*  70 */       vendorNode.addController((Controller)new BarkingController(vendorNode, speachMapping));
/*     */     }
/*     */ 
/*     */     
/*  74 */     TintMouseOver tintMouseOver = new TintMouseOver((new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F), Effects.TintMode.ADDITIVE);
/*     */     
/*  76 */     CombinedMouseOver combinedMouseOver = new CombinedMouseOver(new MouseOver[] { (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)vendorCreature, MainGameState.getActionNameToCursorMapping()), (MouseOver)new ActionExistsDecorator((InteractibleProp)vendorCreature, "open-vendor-gui", (MouseOver)tintMouseOver) });
/*     */ 
/*     */ 
/*     */     
/*  80 */     combinedMouseOver.setOwnerPropNode(vendorNode);
/*     */     
/*  82 */     return new DefaultActionInteractActionHandler((InteractibleProp)vendorCreature, (Creature)MainGameState.getPlayerNode().getProp(), "open-vendor-gui", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)combinedMouseOver);
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
/*     */   public boolean isBuildable(String type) {
/*  97 */     return VendorCreationMessage.CREATURETYPE_VENDOR.equals(type);
/*     */   }
/*     */   
/*     */   private PropNode buildModel(Creature creature, String classId) {
/* 101 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, classId + ".xml");
/* 102 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/*     */     
/* 104 */     PropNode propNode = new PropNode((Prop)creature, 3, "", TcgGame.getDireEffectDescriptionFactory());
/* 105 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */     
/* 108 */     clientDescribedModularNode.reloadCharacter();
/* 109 */     propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*     */     
/* 111 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/* 112 */     propNode.updateGeometricState(0.0F, true);
/*     */     
/* 114 */     return propNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\VendorBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */