/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.OverheadIcons;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*     */ import com.funcom.rpgengine2.monsters.MonsterHardness;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.controllers.InterpolationController;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractModularCreatureLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  51 */   protected CreatureCreationMessage creatureMessage = null;
/*  52 */   protected Future<PropNode> LoadModelFuture = null;
/*  53 */   protected Creature monster = null;
/*  54 */   protected CreatureVisualDescription monsterVisuals = null;
/*  55 */   protected PropNode propNode = null;
/*     */   
/*     */   public AbstractModularCreatureLMToken(CreatureCreationMessage creatureMessage) {
/*  58 */     this.creatureMessage = creatureMessage;
/*     */   }
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  62 */     Callable<PropNode> callable = new LoadModelCallable(this.monster, this.monsterVisuals);
/*  63 */     this.LoadModelFuture = LoadingManager.INSTANCE.submitCallable(callable);
/*     */     
/*  65 */     if (this.LoadModelFuture == null) {
/*  66 */       throw new Exception("processWaitingAssets: the LoadModelFuture is null.");
/*     */     }
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  74 */     return (this.LoadModelFuture == null || this.LoadModelFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  82 */     if (this.LoadModelFuture != null && !this.LoadModelFuture.isCancelled()) {
/*  83 */       this.propNode = this.LoadModelFuture.get();
/*     */     } else {
/*     */       
/*  86 */       Callable<PropNode> callable = new LoadModelCallable(this.monster, this.monsterVisuals);
/*  87 */       this.propNode = callable.call();
/*     */     } 
/*  89 */     this.LoadModelFuture = null;
/*     */     
/*  91 */     if (this.propNode != null) {
/*  92 */       UserActionHandler actionHandler = null;
/*  93 */       actionHandler = createActionHandler(this.monster, this.creatureMessage, this.propNode);
/*  94 */       this.propNode.setActionHandler(actionHandler);
/*     */       
/*  96 */       MainGameState.getWorld().addObject((RepresentationalNode)this.propNode);
/*  97 */       TcgGame.getMonsterRegister().addPropNode(this.propNode);
/*  98 */       this.propNode.updateRenderState();
/*     */       
/* 100 */       WorldCoordinate wc = this.creatureMessage.getCoord();
/* 101 */       float angle = this.creatureMessage.getAngle();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       CreatureData creatureData = new CreatureData(wc, angle);
/* 107 */       creatureData.setType(this.creatureMessage.getType());
/* 108 */       Map<Integer, CreatureData> creatureDataMap = (Map<Integer, CreatureData>)this.creatureMessage.getExtraParam();
/* 109 */       creatureDataMap.put(Integer.valueOf(this.creatureMessage.getCreatureId()), creatureData);
/*     */       
/* 111 */       this.propNode.getBasicEffectsNode().setStats(creatureData.getStats());
/*     */ 
/*     */       
/* 114 */       InterpolationController cont = new InterpolationController(this.propNode);
/* 115 */       cont.setPosition(wc);
/* 116 */       cont.setAngle(this.creatureMessage.getAngle());
/* 117 */       this.propNode.addController((Controller)cont);
/*     */     } 
/*     */     
/* 120 */     if (this.propNode != null) {
/* 121 */       this.propNode.updateGeometricState(0.0F, true);
/*     */     }
/*     */     
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PropNode buildModel(Creature creature, CreatureVisualDescription monsterVisuals) {
/* 130 */     String xmlDocumentPath = monsterVisuals.getXmlDocumentPath();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 135 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, xmlDocumentPath, CacheType.CACHE_TEMPORARILY);
/* 136 */       XmlModularDescription modularDescription = new XmlModularDescription(document);
/*     */       
/* 138 */       PropNode propNode = new PropNode((Prop)creature, 3, "", TcgGame.getDireEffectDescriptionFactory());
/* 139 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */       
/* 142 */       clientDescribedModularNode.reloadCharacter();
/* 143 */       propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/* 144 */       if (monsterVisuals.hasShadow()) {
/* 145 */         SpatialUtils.addShadow(propNode, TcgGame.getResourceManager());
/*     */       }
/* 147 */       propNode.initializeAllEffects(TcgGame.getResourceGetter(), MainGameState.getWorld().getParticleSurface());
/* 148 */       if (this.creatureMessage != null && this.creatureMessage.getFaction().equals(BaseFaction.MONSTER) && (
/* 149 */         System.getProperty("tcg.nohud") == null || !System.getProperty("tcg.nohud").equals(String.valueOf(true))))
/*     */       
/*     */       { 
/*     */         
/* 153 */         MonsterDescription description = TcgGame.getRpgLoader().getMonsterManager().getDescription(this.creatureMessage.getType());
/* 154 */         if (description != null)
/* 155 */         { switch (description.getHardness())
/*     */           { case TRASH:
/*     */             case NOLOOT:
/* 158 */               propNode.getBasicEffectsNode().setState(OverheadIcons.State.HEALTH_SMALL);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 196 */               return propNode;case MINI_BOSS: propNode.getBasicEffectsNode().setState(OverheadIcons.State.HEALTH_LARGE); return propNode;case BOSS: propNode.getBasicEffectsNode().setState(OverheadIcons.State.HEALTH_XL); return propNode; }  propNode.getBasicEffectsNode().setState(OverheadIcons.State.HEALTH_MEDIUM); } else { propNode.getBasicEffectsNode().setState(OverheadIcons.State.HEALTH_MEDIUM); }  }  return propNode;
/* 197 */     } catch (ResourceManagerException e) {
/* 198 */       throw new RuntimeException("Error loading creature with modular description: " + xmlDocumentPath + ", " + monsterVisuals, e);
/* 199 */     } catch (Exception e) {
/* 200 */       throw new RuntimeException("Error loading creature with modular description: " + xmlDocumentPath, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract UserActionHandler createActionHandler(Creature paramCreature, CreatureCreationMessage paramCreatureCreationMessage, PropNode paramPropNode);
/*     */   
/* 206 */   public class LoadModelCallable implements Callable { Creature creature = null;
/* 207 */     CreatureVisualDescription monsterVisuals = null;
/*     */     
/*     */     public LoadModelCallable(Creature creature, CreatureVisualDescription monsterVisuals) {
/* 210 */       this.creature = creature;
/* 211 */       this.monsterVisuals = monsterVisuals;
/*     */     }
/*     */     
/*     */     public PropNode call() {
/* 215 */       return create();
/*     */     }
/*     */     
/*     */     public PropNode create() {
/* 219 */       AbstractModularCreatureLMToken.this.monster = new Creature(AbstractModularCreatureLMToken.this.creatureMessage.getCreatureId(), AbstractModularCreatureLMToken.this.creatureMessage.getName(), AbstractModularCreatureLMToken.this.creatureMessage.getType(), AbstractModularCreatureLMToken.this.creatureMessage.getCoord(), AbstractModularCreatureLMToken.this.creatureMessage.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       if (MainGameState.getPlayerModel() == null) {
/* 228 */         return null;
/*     */       }
/* 230 */       AbstractModularCreatureLMToken.this.monster.getPosition().setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 231 */       AbstractModularCreatureLMToken.this.monster.getPosition().setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/*     */       
/* 233 */       MonsterDescription description = TcgGame.getRpgLoader().getMonsterManager().getDescription(AbstractModularCreatureLMToken.this.creatureMessage.getType());
/* 234 */       CreatureVisualDescription monsterVisuals = TcgGame.getVisualRegistry().getCreatureVisualForClassId(description.getModelXml());
/*     */       
/* 236 */       if (monsterVisuals == null) {
/* 237 */         System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n!!!\n!!! NO MONSTER VISUAL FOR MODEl: " + description.getModelXml() + "\n!!!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
/*     */ 
/*     */         
/* 240 */         monsterVisuals = new CreatureVisualDescription();
/* 241 */         monsterVisuals.setAlwaysOnDfx("");
/* 242 */         monsterVisuals.setDeathDfx("");
/* 243 */         monsterVisuals.setIcon("");
/* 244 */         monsterVisuals.setId(description.getModelXml());
/* 245 */         monsterVisuals.setIdleDfx("");
/* 246 */         monsterVisuals.setInteractDfx("");
/* 247 */         monsterVisuals.setWalkDfx("");
/* 248 */         monsterVisuals.setXmlDocumentPath(description.getModelXml() + ".xml");
/*     */       } 
/*     */       
/* 251 */       AbstractModularCreatureLMToken.this.monster.addMappedDfx("move", monsterVisuals.getWalkDfx());
/* 252 */       AbstractModularCreatureLMToken.this.monster.addMappedDfx("idle", monsterVisuals.getIdleDfx());
/* 253 */       AbstractModularCreatureLMToken.this.monster.addMappedDfx("interact", monsterVisuals.getInteractDfx());
/* 254 */       AbstractModularCreatureLMToken.this.monster.addMappedDfx("die", monsterVisuals.getDeathDfx());
/* 255 */       AbstractModularCreatureLMToken.this.propNode = AbstractModularCreatureLMToken.this.buildModel(AbstractModularCreatureLMToken.this.monster, monsterVisuals);
/* 256 */       AbstractModularCreatureLMToken.this.propNode.setAngle(AbstractModularCreatureLMToken.this.creatureMessage.getAngle());
/*     */       
/* 258 */       if (!monsterVisuals.getAlwaysOnDfx().isEmpty()) {
/*     */         try {
/* 260 */           DireEffectDescription alwaysOnDfxDescription = AbstractModularCreatureLMToken.this.propNode.getEffectDescriptionFactory().getDireEffectDescription(monsterVisuals.getAlwaysOnDfx(), false);
/* 261 */           if (!alwaysOnDfxDescription.isEmpty()) {
/* 262 */             DireEffect dfx = alwaysOnDfxDescription.createInstance(AbstractModularCreatureLMToken.this.propNode, UsageParams.EMPTY_PARAMS);
/* 263 */             AbstractModularCreatureLMToken.this.propNode.addDfx(dfx);
/*     */           } 
/* 265 */         } catch (NoSuchDFXException e) {}
/*     */       }
/*     */ 
/*     */       
/* 269 */       return AbstractModularCreatureLMToken.this.propNode;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\AbstractModularCreatureLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */