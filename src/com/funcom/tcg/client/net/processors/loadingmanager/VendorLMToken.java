/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
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
import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.rpgengine2.vendor.VendorDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.controllers.BarkingController;
/*     */ import com.funcom.tcg.client.model.actions.OpenVendorGuiAction;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.VendorCreationMessage;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.Document;
import com.funcom.gameengine.view.Effects;
import com.funcom.gameengine.view.OverheadIcons;
/*     */ 
/*     */ public class VendorLMToken extends LoadingManagerToken {
/*  38 */   private VendorCreationMessage creationMessage = null;
/*  39 */   private Future LoadVendorFuture = null;
/*     */   
/*     */   public VendorLMToken(VendorCreationMessage creationMessage) {
/*  42 */     this.creationMessage = creationMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  48 */     Callable<PropNode> callable = new LoadVendorCallable();
/*  49 */     this.LoadVendorFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*     */     
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  56 */     return (this.LoadVendorFuture == null || this.LoadVendorFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  62 */     PropNode propNode = null;
/*  63 */     if (this.LoadVendorFuture != null && !this.LoadVendorFuture.isCancelled()) {
/*  64 */       propNode = (PropNode)this.LoadVendorFuture.get();
/*     */     } else {
/*     */       
/*  67 */       Callable<PropNode> callable = new LoadVendorCallable();
/*  68 */       propNode = callable.call();
/*     */     } 
/*  70 */     this.LoadVendorFuture = null;
/*     */     
/*  72 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  73 */     TcgGame.getVendorRegister().addPropNode(propNode);
/*  74 */     propNode.updateRenderState();
/*     */     
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   private DefaultActionInteractActionHandler vendorActionHandler(Creature vendorCreature, PropNode vendorNode) {
/*  80 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(vendorCreature.getMonsterId());
/*  81 */     if (speachMapping != null && 
/*  82 */       speachMapping.isBarks())
/*     */     {
/*  84 */       vendorNode.addController((Controller)new BarkingController(vendorNode, speachMapping));
/*     */     }
/*     */ 
/*     */     
/*  88 */     TintMouseOver tintMouseOver = new TintMouseOver((new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F), Effects.TintMode.ADDITIVE);
/*     */     
/*  90 */     CombinedMouseOver combinedMouseOver = new CombinedMouseOver(new MouseOver[] { (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)vendorCreature, MainGameState.getActionNameToCursorMapping()), (MouseOver)new ActionExistsDecorator((InteractibleProp)vendorCreature, "open-vendor-gui", (MouseOver)tintMouseOver) });
/*     */ 
/*     */ 
/*     */     
/*  94 */     combinedMouseOver.setOwnerPropNode(vendorNode);
/*     */     
/*  96 */     if (MainGameState.getPlayerNode() != null) {
/*  97 */       return new DefaultActionInteractActionHandler((InteractibleProp)vendorCreature, (Creature)MainGameState.getPlayerNode().getProp(), "open-vendor-gui", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)combinedMouseOver);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class LoadVendorCallable
/*     */     implements Callable<PropNode>
/*     */   {
/*     */     public PropNode call() {
/* 114 */       VendorDescription description = TcgGame.getRpgLoader().getVendorManager().getVendorDescription(VendorLMToken.this.creationMessage.getVendorId());
/* 115 */       Creature vendorCreature = new Creature(VendorLMToken.this.creationMessage.getCreatureId(), description.getName(), VendorLMToken.this.creationMessage.getVendorId(), VendorLMToken.this.creationMessage.getCoord(), VendorLMToken.this.creationMessage.getRadius());
/*     */       
/* 117 */       vendorCreature.setRotation(VendorLMToken.this.creationMessage.getAngle());
/* 118 */       vendorCreature.addAction((Action)new OpenVendorGuiAction(description.getName(), VendorLMToken.this.creationMessage.getVendorItems(), VendorLMToken.this.creationMessage.getBuyBackItems()));
/*     */ 
/*     */       
/* 121 */       PropNode propNode = new PropNode((Prop)vendorCreature, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */       
/* 123 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, VendorLMToken.this.creationMessage.getModel() + ".xml");
/* 124 */       XmlModularDescription modularDescription = new XmlModularDescription(document);
/*     */       
/* 126 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */       
/* 129 */       clientDescribedModularNode.reloadCharacter();
/* 130 */       propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*     */       
/* 132 */       propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/* 133 */       propNode.updateGeometricState(0.0F, true);
/*     */       
/* 135 */       propNode.getBasicEffectsNode().setState(OverheadIcons.State.VENDOR);
/* 136 */       SpatialUtils.addShadow(propNode, TcgGame.getResourceManager());
/*     */       
/* 138 */       DefaultActionInteractActionHandler hdl = VendorLMToken.this.vendorActionHandler(vendorCreature, propNode);
/* 139 */       if (hdl != null) {
/* 140 */         propNode.setActionHandler((UserActionHandler)hdl);
/*     */       }
/*     */       
/* 143 */       return propNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\VendorLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */