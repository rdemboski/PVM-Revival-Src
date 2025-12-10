/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OverheadIcons
/*     */   extends Node
/*     */   implements VisualEffect
/*     */ {
/*     */   private static final float QUAD_SIZE = 0.48F;
/*     */   private State state;
/*     */   private Quad realObject;
/*     */   private Quad bgdObject;
/*     */   private TextureState noneTextureState;
/*     */   private TextureState questLowerLevelHandingTextureState;
/*     */   private TextureState questLowerLevelOfferTextureState;
/*     */   private TextureState questHigherLevelHandingTextureState;
/*     */   private TextureState questHigherLevelOfferTextureState;
/*     */   private TextureState questNeutralLevelHandingTextureState;
/*     */   private TextureState questNeutralLevelOfferTextureState;
/*     */   private TextureState questDailyHandingTextureState;
/*     */   private TextureState questDailyOfferTextureState;
/*     */   private TextureState questDefaultHandingTextureState;
/*     */   private TextureState vendorTextureState;
/*     */   private TextureState healthTextureState;
/*     */   private TextureState healthBgdTextureState;
/*  42 */   private Map<Short, Integer> stats = null;
/*     */ 
/*     */   
/*     */   private float previousHealthFraction;
/*     */ 
/*     */ 
/*     */   
/*     */   public OverheadIcons(Texture noneTexture, Texture questLowerLevelHandinTexture, Texture questLowerLevelOfferTexture, Texture vendorTexture, Texture questHigherLevelHandinTexture, Texture questHigherLevelOfferTexture, Texture questNeutralLevelHandinTexture, Texture questNeutralLevelOfferTexture, Texture questDailyHandingTexture, Texture questDailyOfferTexture, Texture questDefaultHandingTexture, Texture healthTexture, Texture healthBgdTexture) {
/*  50 */     super("billboard-quest-marker");
/*  51 */     createRealObject();
/*     */     
/*  53 */     BlendState as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  54 */     as.setEnabled(true);
/*  55 */     as.setBlendEnabled(true);
/*  56 */     setRenderState((RenderState)as);
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.noneTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  61 */     this.noneTextureState.setEnabled(true);
/*  62 */     this.noneTextureState.setTexture(noneTexture);
/*     */     
/*  64 */     this.questLowerLevelHandingTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  65 */     this.questLowerLevelHandingTextureState.setEnabled(true);
/*  66 */     this.questLowerLevelHandingTextureState.setTexture(questLowerLevelHandinTexture);
/*     */     
/*  68 */     this.questLowerLevelOfferTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  69 */     this.questLowerLevelOfferTextureState.setEnabled(true);
/*  70 */     this.questLowerLevelOfferTextureState.setTexture(questLowerLevelOfferTexture);
/*     */     
/*  72 */     this.vendorTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  73 */     this.vendorTextureState.setEnabled(true);
/*  74 */     this.vendorTextureState.setTexture(vendorTexture);
/*     */     
/*  76 */     this.questHigherLevelHandingTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  77 */     this.questHigherLevelHandingTextureState.setEnabled(true);
/*  78 */     this.questHigherLevelHandingTextureState.setTexture(questHigherLevelHandinTexture);
/*     */     
/*  80 */     this.questHigherLevelOfferTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  81 */     this.questHigherLevelOfferTextureState.setEnabled(true);
/*  82 */     this.questHigherLevelOfferTextureState.setTexture(questHigherLevelOfferTexture);
/*     */     
/*  84 */     this.questNeutralLevelHandingTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  85 */     this.questNeutralLevelHandingTextureState.setEnabled(true);
/*  86 */     this.questNeutralLevelHandingTextureState.setTexture(questNeutralLevelHandinTexture);
/*     */     
/*  88 */     this.questNeutralLevelOfferTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  89 */     this.questNeutralLevelOfferTextureState.setEnabled(true);
/*  90 */     this.questNeutralLevelOfferTextureState.setTexture(questNeutralLevelOfferTexture);
/*     */     
/*  92 */     this.questDailyHandingTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  93 */     this.questDailyHandingTextureState.setEnabled(true);
/*  94 */     this.questDailyHandingTextureState.setTexture(questDailyHandingTexture);
/*     */     
/*  96 */     this.questDailyOfferTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  97 */     this.questDailyOfferTextureState.setEnabled(true);
/*  98 */     this.questDailyOfferTextureState.setTexture(questDailyOfferTexture);
/*     */     
/* 100 */     this.questDefaultHandingTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 101 */     this.questDefaultHandingTextureState.setEnabled(true);
/* 102 */     this.questDefaultHandingTextureState.setTexture(questDefaultHandingTexture);
/*     */     
/* 104 */     this.healthTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 105 */     this.healthTextureState.setEnabled(true);
/* 106 */     this.healthTextureState.setTexture(healthTexture);
/*     */     
/* 108 */     this.healthBgdTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 109 */     this.healthBgdTextureState.setEnabled(true);
/* 110 */     this.healthBgdTextureState.setTexture(healthBgdTexture);
/*     */     
/* 112 */     setState(State.NONE);
/*     */   }
/*     */   
/*     */   public void setStats(Map<Short, Integer> stats) {
/* 116 */     this.stats = stats;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float time, boolean initiator) {
/* 121 */     updateHealthFraction();
/* 122 */     super.updateGeometricState(time, initiator);
/*     */   }
/*     */   
/*     */   private void updateHealthFraction() {
/* 126 */     if (this.stats != null && (this.state == State.HEALTH_LARGE || this.state == State.HEALTH_MEDIUM || this.state == State.HEALTH_SMALL || this.state == State.HEALTH_XL)) {
/* 127 */       Integer maxHealth = this.stats.get(Short.valueOf((short)11));
/* 128 */       Integer health = this.stats.get(Short.valueOf((short)12));
/* 129 */       if (health == null || maxHealth == null)
/*     */         return; 
/* 131 */       float fraction = health.intValue() / maxHealth.floatValue();
/* 132 */       fraction = Math.max(Math.min(fraction, 1.0F), 0.0F);
/* 133 */       if (fraction == this.previousHealthFraction)
/*     */         return; 
/* 135 */       this.previousHealthFraction = fraction;
/* 136 */       float width = 0.48F;
/* 137 */       switch (this.state) {
/*     */         case HEALTH_SMALL:
/* 139 */           width *= 0.5F;
/*     */           break;
/*     */         case HEALTH_MEDIUM:
/* 142 */           width *= 1.0F;
/*     */           break;
/*     */         case HEALTH_LARGE:
/* 145 */           width *= 1.5F;
/*     */           break;
/*     */         case HEALTH_XL:
/* 148 */           width *= 2.0F;
/*     */           break;
/*     */         default:
/* 151 */           width *= 0.0F;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 156 */       if (this.bgdObject == null) {
/* 157 */         this.bgdObject = new Quad("billboard-quadbgd");
/* 158 */         this.bgdObject.updateGeometry(width, 0.48F);
/* 159 */         this.bgdObject.setSolidColor(ColorRGBA.black);
/* 160 */         this.bgdObject.setLocalTranslation(new Vector3f(0.0F, -0.4F, -0.1F));
/* 161 */         attachChild((Spatial)this.bgdObject);
/* 162 */         this.bgdObject.setZOrder(0);
/* 163 */         updateRenderState();
/*     */       } 
/*     */       
/* 166 */       this.realObject.updateGeometry(fraction * width, 0.48F);
/* 167 */       this.realObject.setLocalTranslation(new Vector3f(-(1.0F - fraction) / 2.0F * width, -0.4F, 0.0F));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createRealObject() {
/* 172 */     this.realObject = new Quad("billboard-quad");
/* 173 */     this.realObject.initialize(0.48F, 0.48F);
/* 174 */     attachChild((Spatial)this.realObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(Object oldValue, Object newValue) {
/* 179 */     State state = (State)newValue;
/* 180 */     setState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setState(State state) {
/* 189 */     if (state == this.state) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     this.state = state;
/* 194 */     switch (state) {
/*     */       case NONE:
/* 196 */         setRenderState((RenderState)this.noneTextureState);
/* 197 */         setLocalScale(0.0F);
/*     */         break;
/*     */       case QUEST_HANDING_LOWER:
/* 200 */         setRenderState((RenderState)this.questLowerLevelHandingTextureState);
/* 201 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_HANDING_NEUTRAL:
/* 204 */         setRenderState((RenderState)this.questNeutralLevelHandingTextureState);
/* 205 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_HANDING_HIGHER:
/* 208 */         setRenderState((RenderState)this.questHigherLevelHandingTextureState);
/* 209 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_HANDING_DEFAULT:
/* 212 */         setRenderState((RenderState)this.questDefaultHandingTextureState);
/* 213 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_OFFER_LOWER:
/* 216 */         setRenderState((RenderState)this.questLowerLevelOfferTextureState);
/* 217 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_OFFER_NEUTRAL:
/* 220 */         setRenderState((RenderState)this.questNeutralLevelOfferTextureState);
/* 221 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_OFFER_HIGHER:
/* 224 */         setRenderState((RenderState)this.questHigherLevelOfferTextureState);
/* 225 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_OFFER_DAILY:
/* 228 */         setRenderState((RenderState)this.questDailyOfferTextureState);
/* 229 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case QUEST_HANDING_DAILY:
/* 232 */         setRenderState((RenderState)this.questDailyHandingTextureState);
/* 233 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case VENDOR:
/* 236 */         setRenderState((RenderState)this.vendorTextureState);
/* 237 */         setLocalScale(1.0F);
/*     */         break;
/*     */       case HEALTH_SMALL:
/*     */       case HEALTH_MEDIUM:
/*     */       case HEALTH_LARGE:
/*     */       case HEALTH_XL:
/* 243 */         setRenderState((RenderState)this.healthTextureState);
/* 244 */         setLocalScale(1.0F);
/* 245 */         this.previousHealthFraction = 0.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 249 */     updateGeometricState(0.0F, true);
/* 250 */     updateRenderState();
/*     */   }
/*     */   
/*     */   public void setQuestOfferTexture(Texture questOfferTexture) {
/* 254 */     this.questLowerLevelOfferTextureState.setTexture(questOfferTexture);
/* 255 */     this.questLowerLevelOfferTextureState.setNeedsRefresh(true);
/*     */   }
/*     */   
/*     */   public void setQuestHandingTexture(Texture questHandingTexture) {
/* 259 */     this.questLowerLevelHandingTextureState.setTexture(questHandingTexture);
/* 260 */     this.questLowerLevelHandingTextureState.setNeedsRefresh(true);
/*     */   }
/*     */   
/*     */   public State getState() {
/* 264 */     return this.state;
/*     */   }
/*     */   
/*     */   public enum State {
/* 268 */     QUEST_OFFER_LOWER,
/* 269 */     QUEST_OFFER_NEUTRAL,
/* 270 */     QUEST_OFFER_HIGHER,
/* 271 */     QUEST_OFFER_DAILY,
/* 272 */     QUEST_HANDING_LOWER,
/* 273 */     QUEST_HANDING_NEUTRAL,
/* 274 */     QUEST_HANDING_HIGHER,
/* 275 */     QUEST_HANDING_DAILY,
/* 276 */     QUEST_HANDING_DEFAULT,
/* 277 */     VENDOR,
/* 278 */     HEALTH_SMALL,
/* 279 */     HEALTH_MEDIUM,
/* 280 */     HEALTH_LARGE,
/* 281 */     HEALTH_XL,
/* 282 */     NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\OverheadIcons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */