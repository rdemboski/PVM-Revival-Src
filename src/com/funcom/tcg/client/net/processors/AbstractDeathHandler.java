/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.DieCommand;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.intersection.CollisionResults;
/*     */ import com.jme.intersection.PickResults;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Spatial;
/*     */ 
/*     */ public abstract class AbstractDeathHandler {
/*     */   protected PropNode createCorpse(PropNode propNode, Element element, String impact) {
/*  23 */     Creature creature = (Creature)propNode.getProp();
/*  24 */     String deathDFX = "die";
/*  25 */     deathDFX = creature.getMappedDfx(deathDFX);
/*  26 */     DireEffectDescription stateDFXDescription = null;
/*     */     try {
/*  28 */       stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(deathDFX, false);
/*  29 */     } catch (NoSuchDFXException e) {}
/*     */ 
/*     */     
/*  32 */     PropNode retNode = addCorpseVisual(propNode.getRepresentation(), propNode, stateDFXDescription, element, impact);
/*  33 */     removeCreature(propNode);
/*     */     
/*  35 */     return retNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private PropNode addCorpseVisual(Spatial representation, PropNode propNode, DireEffectDescription stateDFXDescription, Element element, String impact) {
/*  40 */     Creature creature = (Creature)propNode.getProp();
/*     */     
/*  42 */     PropNode corpse = new PropNode((Prop)creature, 3, "corpse", TcgGame.getDireEffectDescriptionFactory());
/*  43 */     corpse.setScale(propNode.getScale());
/*     */     
/*  45 */     DireEffect deathDfx = null;
/*  46 */     if (stateDFXDescription != null && !stateDFXDescription.isEmpty()) {
/*  47 */       deathDfx = stateDFXDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*  48 */       if (stateDFXDescription.getEffectDescriptions(AnimationEffectDescription.class).size() > 0)
/*     */       {
/*  50 */         corpse.attachRepresentation(representation);
/*     */       }
/*     */     } 
/*     */     
/*  54 */     if (corpse.getRepresentation() == null) {
/*  55 */       BoundingVolume bounds = propNode.getRepresentation().getWorldBound();
/*  56 */       if (bounds instanceof BoundingBox) {
/*  57 */         Vector3f vector = ((BoundingBox)bounds).getExtent(null);
/*  58 */         Spatial dummyNode = new StupidlyBoundEmptySpatial("dummy node");
/*     */         
/*  60 */         dummyNode.setModelBound((BoundingVolume)new BoundingBox(bounds.getCenter().clone(), vector.getX(), vector.getY(), vector.getZ()));
/*  61 */         corpse.attachRepresentation(dummyNode);
/*  62 */         dummyNode.updateWorldBound();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  67 */     DireEffect elementalDfx = null;
/*  68 */     String elementalDfxStr = TcgGame.getRpgLoader().getElementManager().getElementDesc(element).getDeathDfx();
/*  69 */     if (!elementalDfxStr.isEmpty()) {
/*     */       try {
/*  71 */         DireEffectDescription elementalDFXDescription = corpse.getEffectDescriptionFactory().getDireEffectDescription(elementalDfxStr, false);
/*     */         
/*  73 */         if (!elementalDFXDescription.isEmpty()) {
/*  74 */           elementalDfx = elementalDFXDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*     */         }
/*  76 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     DireEffect impactDfx = null;
/*  83 */     if (!impact.isEmpty()) {
/*     */       try {
/*  85 */         DireEffectDescription impactDfxStrDescription = corpse.getEffectDescriptionFactory().getDireEffectDescription(impact, false);
/*     */         
/*  87 */         if (!impactDfxStrDescription.isEmpty()) {
/*  88 */           impactDfx = impactDfxStrDescription.createInstance(corpse, UsageParams.EMPTY_PARAMS);
/*     */         }
/*  90 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (deathDfx == null && elementalDfx == null && impactDfx == null) {
/*  96 */       return null;
/*     */     }
/*     */     
/*  99 */     creature.immediateCommand((Command)new DieCommand(corpse, deathDfx, elementalDfx, impactDfx, 0.0F));
/* 100 */     corpse.initializeEffects(MainGameState.getWorld().getParticleSurface());
/* 101 */     corpse.transferFloatingText(propNode);
/* 102 */     MainGameState.getWorld().addObject((RepresentationalNode)corpse);
/*     */     
/* 104 */     return corpse;
/*     */   }
/*     */   
/*     */   protected void removeCreature(PropNode propNode) {
/* 108 */     propNode.removeFromParent();
/* 109 */     propNode.getEffects().removeAllParticles();
/*     */   }
/*     */   
/*     */   private class StupidlyBoundEmptySpatial extends Spatial {
/*     */     BoundingVolume volume;
/*     */     
/*     */     public StupidlyBoundEmptySpatial(String s) {
/* 116 */       super(s);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateModelBound() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setModelBound(BoundingVolume boundingVolume) {
/* 126 */       this.volume = boundingVolume;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getVertexCount() {
/* 131 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTriangleCount() {
/* 136 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(Renderer renderer) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateWorldBound() {
/* 146 */       if (this.worldBound == null) {
/* 147 */         this.worldBound = this.volume.clone(this.worldBound);
/*     */       } else {
/* 149 */         this.worldBound.mergeLocal(this.volume);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void findCollisions(Spatial spatial, CollisionResults collisionResults, int i) {}
/*     */     
/*     */     public boolean hasCollision(Spatial spatial, boolean b, int i) {
/* 156 */       return false;
/*     */     }
/*     */     
/*     */     public void findPick(Ray ray, PickResults pickResults, int i) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\AbstractDeathHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */