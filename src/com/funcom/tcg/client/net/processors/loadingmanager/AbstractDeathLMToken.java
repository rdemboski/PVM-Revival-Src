/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ import com.funcom.commons.dfx.AnimationEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.DieCommand;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.intersection.CollisionResults;
/*     */ import com.jme.intersection.PickResults;
/*     */ import com.jme.math.Ray;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ 
/*     */ public class AbstractDeathLMToken extends LoadingManagerToken {
/*  28 */   private Element element = null;
/*  29 */   private String impact = null;
/*     */   
/*  31 */   protected PropNode creatureNode = null;
/*  32 */   protected PropNodeRegister propNodeRegister = null;
/*  33 */   protected PropNode corpse = null;
/*     */   
/*     */   public AbstractDeathLMToken(PropNode creatureNode, Element element, String impact, PropNodeRegister propNodeRegister) {
/*  36 */     this.creatureNode = creatureNode;
/*  37 */     this.element = element;
/*  38 */     this.impact = impact;
/*  39 */     this.propNodeRegister = propNodeRegister;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() {
/*  44 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processWaitingAssets() {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  55 */     Creature creature = (Creature)this.creatureNode.getProp();
/*  56 */     String deathDFX = "die";
/*  57 */     deathDFX = creature.getMappedDfx(deathDFX);
/*  58 */     DireEffectDescription stateDFXDescription = null;
/*     */     try {
/*  60 */       stateDFXDescription = this.creatureNode.getEffectDescriptionFactory().getDireEffectDescription(deathDFX, false);
/*  61 */     } catch (NoSuchDFXException e) {}
/*     */ 
/*     */     
/*  64 */     removeCreature(this.creatureNode);
/*  65 */     this.corpse = addCorpseVisual(this.creatureNode.getRepresentation(), this.creatureNode, stateDFXDescription, this.element, this.impact);
/*  66 */     this.corpse.updateRenderState();
/*  67 */     if (this.corpse != null && creature instanceof com.funcom.tcg.client.model.rpg.ClientPlayer) {
/*  68 */       this.propNodeRegister.addPropNode(this.corpse);
/*     */     }
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private PropNode addCorpseVisual(Spatial representation, PropNode propNode, DireEffectDescription stateDFXDescription, Element element, String impact) {
/*  75 */     Creature creature = (Creature)propNode.getProp();
/*     */     
/*  77 */     PropNode corpse = new PropNode((Prop)creature, 3, "corpse", TcgGame.getDireEffectDescriptionFactory());
/*  78 */     corpse.setScale(propNode.getScale());
/*     */     
/*  80 */     DireEffect deathDfx = null;
/*  81 */     if (stateDFXDescription != null && !stateDFXDescription.isEmpty()) {
/*  82 */       deathDfx = stateDFXDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*  83 */       if (stateDFXDescription.getEffectDescriptions(AnimationEffectDescription.class).size() > 0)
/*     */       {
/*  85 */         corpse.attachRepresentation(representation);
/*     */       }
/*     */     } 
/*     */     
/*  89 */     if (corpse.getRepresentation() == null) {
/*  90 */       BoundingVolume bounds = propNode.getRepresentation().getWorldBound();
/*  91 */       if (bounds instanceof BoundingBox) {
/*  92 */         Vector3f vector = ((BoundingBox)bounds).getExtent(null);
/*  93 */         Spatial dummyNode = new StupidlyBoundEmptySpatial("dummy node");
/*     */         
/*  95 */         dummyNode.setModelBound((BoundingVolume)new BoundingBox(bounds.getCenter().clone(), vector.getX(), vector.getY(), vector.getZ()));
/*  96 */         corpse.attachRepresentation(dummyNode);
/*  97 */         dummyNode.updateWorldBound();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 102 */     DireEffect elementalDfx = null;
/* 103 */     String elementalDfxStr = TcgGame.getRpgLoader().getElementManager().getElementDesc(element).getDeathDfx();
/* 104 */     if (!elementalDfxStr.isEmpty()) {
/*     */       try {
/* 106 */         DireEffectDescription elementalDFXDescription = corpse.getEffectDescriptionFactory().getDireEffectDescription(elementalDfxStr, false);
/*     */         
/* 108 */         if (!elementalDFXDescription.isEmpty()) {
/* 109 */           elementalDfx = elementalDFXDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*     */         }
/* 111 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     DireEffect impactDfx = null;
/* 118 */     if (!impact.isEmpty()) {
/*     */       try {
/* 120 */         DireEffectDescription impactDfxStrDescription = corpse.getEffectDescriptionFactory().getDireEffectDescription(impact, false);
/*     */         
/* 122 */         if (!impactDfxStrDescription.isEmpty()) {
/* 123 */           impactDfx = impactDfxStrDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*     */         }
/* 125 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (deathDfx == null && elementalDfx == null && impactDfx == null) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     creature.immediateCommand((Command)new DieCommand(corpse, deathDfx, elementalDfx, impactDfx, 0.0F));
/* 135 */     corpse.initializeEffects(MainGameState.getWorld().getParticleSurface());
/* 136 */     corpse.transferFloatingText(propNode);
/* 137 */     MainGameState.getWorld().addObject((RepresentationalNode)corpse);
/*     */     
/* 139 */     return corpse;
/*     */   }
/*     */   
/*     */   protected void removeCreature(PropNode propNode) {
/* 143 */     propNode.removeFromParent();
/* 144 */     propNode.getEffects().removeAllParticles();
/*     */   }
/*     */   
/*     */   private class StupidlyBoundEmptySpatial
/*     */     extends Spatial {
/*     */     BoundingVolume volume;
/*     */     
/*     */     public StupidlyBoundEmptySpatial(String s) {
/* 152 */       super(s);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateModelBound() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setModelBound(BoundingVolume boundingVolume) {
/* 162 */       this.volume = boundingVolume;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getVertexCount() {
/* 167 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTriangleCount() {
/* 172 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(Renderer renderer) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateWorldBound() {
/* 182 */       if (this.worldBound == null) {
/* 183 */         this.worldBound = this.volume.clone(this.worldBound);
/*     */       } else {
/* 185 */         this.worldBound.mergeLocal(this.volume);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void findCollisions(Spatial spatial, CollisionResults collisionResults, int i) {}
/*     */     
/*     */     public boolean hasCollision(Spatial spatial, boolean b, int i) {
/* 192 */       return false;
/*     */     }
/*     */     
/*     */     public void findPick(Ray ray, PickResults pickResults, int i) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\AbstractDeathLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */