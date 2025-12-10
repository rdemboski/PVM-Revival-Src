/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.jme.scene.BillboardNode;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicEffectsNode
/*    */   extends BillboardNode
/*    */   implements Updated
/*    */ {
/*    */   public static final String NAME = "basic-effects-node";
/*    */   public static final float DEFAULT_SPEED = 1.0F;
/*    */   private OverheadIcons overheadIcons;
/*    */   private List<CriticalQuad> criticalQuads;
/*    */   private CriticalQuadFactory criticalQuadFactory;
/*    */   
/*    */   public BasicEffectsNode(ResourceGetter resourceGetter) {
/* 28 */     super("basic-effects-node");
/* 29 */     setAlignment(2);
/* 30 */     setRenderQueueMode(3);
/*    */     
/* 32 */     this.criticalQuadFactory = new CriticalQuadFactory(resourceGetter);
/* 33 */     this.criticalQuadFactory.addTexture("effects/world/critical/critical_boom.png");
/* 34 */     this.criticalQuadFactory.addTexture("effects/world/critical/critical_crit.png");
/* 35 */     this.criticalQuadFactory.addTexture("effects/world/critical/critical_exclamation.png");
/* 36 */     this.criticalQuadFactory.addTexture("effects/world/critical/critical_pow.png");
/* 37 */     this.criticalQuadFactory.addTexture("effects/world/critical/critical_smash.png");
/* 38 */     this.criticalQuadFactory.setTranslationDisplacementLimit(0.5F);
/* 39 */     this.criticalQuadFactory.setSize(0.8F, 0.8F);
/*    */     
/* 41 */     this.overheadIcons = new OverheadIcons(resourceGetter.getTexture("effects/world/quest_markers/transparent.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/question_green_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/exclamination_green_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/vendor/vendor.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/question_red_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/exclamination_red_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/question_yellow_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/exclamination_yellow_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/question_blue_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/exclamination_blue_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/quest_markers/question_grey_128.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/health.png", CacheType.CACHE_PERMANENTLY), resourceGetter.getTexture("effects/world/health_bgd.png", CacheType.CACHE_PERMANENTLY));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     attachChild((Spatial)this.overheadIcons);
/* 57 */     this.criticalQuads = new LinkedList<CriticalQuad>();
/*    */   }
/*    */   
/*    */   public void update(float v) {
/* 61 */     Iterator<CriticalQuad> it = this.criticalQuads.iterator();
/* 62 */     while (it.hasNext()) {
/* 63 */       CriticalQuad criticalQuad = it.next();
/* 64 */       criticalQuad.update(v);
/* 65 */       if (!getChildren().contains(criticalQuad))
/* 66 */         it.remove(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void critical() {
/* 71 */     CriticalQuad criticalQuad = this.criticalQuadFactory.createNew();
/* 72 */     this.criticalQuads.add(criticalQuad);
/* 73 */     attachChild((Spatial)criticalQuad);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void setState(OverheadIcons.State state) {
/* 82 */     this.overheadIcons.setState(state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public OverheadIcons.State getState() {
/* 90 */     return this.overheadIcons.getState();
/*    */   }
/*    */   
/*    */   public void setStats(Map<Short, Integer> stats) {
/* 94 */     this.overheadIcons.setStats(stats);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\BasicEffectsNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */