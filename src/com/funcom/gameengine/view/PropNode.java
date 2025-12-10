/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.UpdatedController;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.BillboardNode;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.font3d.Text3D;
/*     */ import java.awt.Point;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropNode
/*     */   extends RepresentationalNode
/*     */   implements Comparable<PropNode>
/*     */ {
/*     */   public static final String PROPERTY_ANY = "*";
/*  38 */   public static final Vector3f DOWN_VEC = new Vector3f(0.0F, -1.0F, 0.0F);
/*  39 */   public static final Point NULL_POINT = new Point(0, 0);
/*     */   
/*     */   public static final int CONTENTTYPE_IGNORABLE = -1;
/*     */   public static final int CONTENTTYPE_NOTHING = 0;
/*     */   public static final int CONTENTTYPE_TILE = 1;
/*     */   public static final int CONTENTTYPE_BILLBOARD = 2;
/*     */   public static final int CONTENTTYPE_MODEL = 3;
/*     */   public static final int CONTENTTYPE_COLLISION = 5;
/*     */   public static final int CONTENTTYPE_SPAWNPOINT = 6;
/*     */   public static final int CONTENTTYPE_AREA = 7;
/*     */   public static final int CONTENTTYPE_DECAL = 8;
/*     */   public static final int CONTENTTYPE_WATERLINE = 9;
/*     */   public static final int CONTENTTYPE_WATERPOND = 10;
/*     */   public static final int CONTENTTYPE_ANCHOR = 11;
/*     */   public static final int CONTENTTYPE_SOUND = 12;
/*     */   public static final int CONTENTTYPE_VENDOR = 13;
/*     */   public static final int CONTENTTYPE_PATROL = 14;
/*     */   public static final int CONTENTTYPE_MAP_CORNER_1 = 15;
/*     */   public static final int CONTENTTYPE_MAP_CORNER_2 = 16;
/*     */   public static final int CONTENTTYPE_ENVIRONMENTAL_DFX = 17;
/*     */   public static final int CONTENTTYPE_CHECKPOINT = 18;
/*     */   public static final int CONTENTTYPE_STARTPOINT = 19;
/*     */   public static final int CONTENTTYPE_QUEST_GOTO_PROP = 20;
/*     */   public static final int CONTENTTYPE_REGION = 21;
/*     */   public static final int CONTENTTYPE_BREADCRUMB = 22;
/*  64 */   private static final ColorRGBA COLOR_MANA_GAIN = new ColorRGBA(0.40234375F, 0.4765625F, 1.0F, 0.0F);
/*     */   
/*     */   @Deprecated
/*     */   private Map<String, VisualEffect> visualEffects;
/*     */   private UserActionHandler userActionHandler;
/*     */   private Prop prop;
/*     */   private String resourceName;
/*     */   private boolean worldOriginAligned;
/*     */   private BasicEffectsNode basicEffectsNode;
/*  73 */   private Set<Spatial> detachedChildren = new HashSet<Spatial>();
/*     */   
/*     */   private Text3D text;
/*     */   private FloatingTextSpatial floatingTextSpatial;
/*     */   private ColorRGBA damageTextColor;
/*     */   private ColorRGBA healTextColor;
/*     */   private ColorRGBA gainManaColor;
/*     */   private ColorRGBA critTextColor;
/*     */   private ColorRGBA immunityTextColor;
/*  82 */   private String damageIndicator = "";
/*  83 */   private String healIndicator = "";
/*  84 */   private String gainManaIndicator = "";
/*     */   private boolean enableManaGainFloatingText = false;
/*     */   private List<DisposeListener> disposeListeners;
/*     */   
/*     */   public PropNode(Prop prop, int contentType, String resourceName, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  89 */     this(prop, contentType, resourceName, effectDescriptionFactory, new NoopActionHandler(null));
/*  90 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public PropNode(Prop prop, int contentType, String resourceName, DireEffectDescriptionFactory effectDescriptionFactory, UserActionHandler userActionHandler) {
/*  94 */     super(prop.getName() + "_node", effectDescriptionFactory, contentType);
/*  95 */     this.resourceName = resourceName;
/*  96 */     this.prop = prop;
/*  97 */     this.userActionHandler = userActionHandler;
/*  98 */     this.worldOriginAligned = true;
/*  99 */     this.disposeListeners = (List<DisposeListener>)new SizeCheckedArrayList(10, "PropNode.disposeListeners", 16);
/* 100 */     this.visualEffects = new HashMap<String, VisualEffect>();
/*     */     
/* 102 */     this.visualEffects.put("*", new VisualEffectList());
/*     */     
/* 104 */     this.damageTextColor = ColorRGBA.white;
/* 105 */     this.healTextColor = ColorRGBA.green;
/* 106 */     this.gainManaColor = COLOR_MANA_GAIN;
/* 107 */     this.critTextColor = ColorRGBA.white;
/* 108 */     this.immunityTextColor = new ColorRGBA(0.6901961F, 0.75686276F, 0.9098039F, 1.0F);
/*     */     
/* 110 */     updatePropVectors();
/*     */     
/* 112 */     prop.addPropChangeListener((Prop.PropChangeListener)new VisualEffectMapper(this));
/* 113 */     prop.addPropChangeListener((Prop.PropChangeListener)new UpdateVectorsListener(this));
/* 114 */     initDfxPlayVisual();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float time, boolean initiator) {
/* 119 */     updatePropVectors();
/* 120 */     super.updateGeometricState(time, initiator);
/* 121 */     getProp().update(time);
/* 122 */     if (this.basicEffectsNode != null) {
/* 123 */       this.basicEffectsNode.updateGeometricState(time, false);
/*     */     }
/* 125 */     if (getEffects().getTintMode().equals(Effects.TintMode.FLASH)) {
/* 126 */       float flashTime = getEffects().getFlashTime();
/* 127 */       flashTime -= time;
/* 128 */       getEffects().setFlashTime(flashTime);
/* 129 */       if (flashTime <= 0.0F) {
/* 130 */         getEffects().tint(Effects.TintMode.OFF);
/* 131 */         getEffects().resetFlashTime();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void initDfxPlayVisual() {
/* 137 */     setVisualsToProperty("*", new DfxPlayVisual(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParent(Node node) {
/* 142 */     if (!this.detachedChildren.isEmpty())
/* 143 */       if (node != null) {
/* 144 */         for (Spatial spatial : this.detachedChildren)
/* 145 */           node.attachChild(spatial); 
/*     */       } else {
/* 147 */         for (Spatial spatial : this.detachedChildren) {
/* 148 */           spatial.removeFromParent();
/*     */         }
/*     */       }  
/* 151 */     super.setParent(node);
/*     */   }
/*     */   
/*     */   public void addDisposeListener(DisposeListener disposeListener) {
/* 155 */     this.disposeListeners.add(disposeListener);
/*     */   }
/*     */   
/*     */   public void removeDisposeListeners(DisposeListener disposeListener) {
/* 159 */     this.disposeListeners.remove(disposeListener);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setVisualsToProperty(String propertyName, VisualEffect visualEffect) {
/* 164 */     if (propertyName.equals("*")) {
/* 165 */       ((VisualEffectList)this.visualEffects.get("*")).addVisualEffect(visualEffect);
/*     */     } else {
/* 167 */       this.visualEffects.put(propertyName, visualEffect);
/*     */     } 
/*     */   }
/*     */   @Deprecated
/*     */   public VisualEffect getVisualEffect(String propertyName) {
/* 172 */     return this.visualEffects.get(propertyName);
/*     */   }
/*     */   
/*     */   public void setActionHandler(UserActionHandler userActionHandler) {
/* 176 */     this.userActionHandler = userActionHandler;
/*     */   }
/*     */   
/*     */   public boolean hasActionHandler() {
/* 180 */     return (this.userActionHandler != null && !(this.userActionHandler instanceof NoopActionHandler));
/*     */   }
/*     */   
/*     */   public void handleLeftMousePress(WorldCoordinate pressedCoord) {
/* 184 */     this.userActionHandler.handleLeftMousePress(pressedCoord);
/*     */   }
/*     */   
/*     */   public void handleRightMousePress() {
/* 188 */     this.userActionHandler.handleRightMousePress();
/*     */   }
/*     */   
/*     */   public void handleKeyPress(int keycode) {
/* 192 */     this.userActionHandler.handleKeyPress(keycode);
/*     */   }
/*     */   
/*     */   public void handleMouseEnter() {
/* 196 */     this.userActionHandler.handleMouseEnter();
/*     */   }
/*     */   
/*     */   public void handleMouseExit() {
/* 200 */     this.userActionHandler.handleMouseExit();
/*     */   }
/*     */   
/*     */   public Prop getProp() {
/* 204 */     return this.prop;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getPosition() {
/* 208 */     return this.prop.getPosition();
/*     */   }
/*     */   
/*     */   public void setAngle(float angle) {
/* 212 */     this.prop.setRotation(angle);
/*     */   }
/*     */   
/*     */   public float getAngle() {
/* 216 */     return this.prop.getRotation();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateSounds() {
/* 221 */     super.updateSounds();
/* 222 */     AudioPlacementManager.getInstance().update(this.prop.getPosition(), this.sounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSound(Sound sound) {
/* 227 */     AudioPlacementManager.getInstance().update(this.prop.getPosition(), sound);
/* 228 */     super.registerSound(sound);
/*     */   }
/*     */   
/*     */   public void updatePropVectors(int offsetx, int offsety) {
/* 232 */     float x = WorldUtils.getScreenX(this.prop.getPosition(), offsetx);
/* 233 */     float y = WorldUtils.getScreenY(this.prop.getPosition(), offsety);
/*     */     
/* 235 */     this.localTranslation.set(x, this.localTranslation.getY(), y);
/*     */     
/* 237 */     if (getParent() != null) {
/* 238 */       this.localTranslation.subtractLocal(getParent().getWorldTranslation());
/*     */     }
/*     */     
/* 241 */     this.localRotation.fromAngleNormalAxis(this.prop.getRotation(), DOWN_VEC);
/*     */     
/* 243 */     updateWorldTranslation();
/* 244 */     updateWorldRotation();
/*     */   }
/*     */   
/*     */   public void updatePropVectors(Point offset) {
/* 248 */     updatePropVectors(offset.x, offset.y);
/*     */   }
/*     */   
/*     */   public void updatePropVectors() {
/* 252 */     if (this.worldOriginAligned) {
/* 253 */       updatePropVectors(WorldOrigin.instance().getX(), WorldOrigin.instance().getY());
/*     */     } else {
/* 255 */       updatePropVectors(0, 0);
/*     */     } 
/*     */   }
/*     */   public void setResourceName(String name) {
/* 259 */     this.resourceName = name;
/*     */   }
/*     */   
/*     */   public String getResourceName() {
/* 263 */     return this.resourceName;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 267 */     String propName = getProp().getName();
/* 268 */     if (propName.contains("/"))
/* 269 */       return propName.substring(propName.lastIndexOf("/") + 1); 
/* 270 */     return propName;
/*     */   }
/*     */   
/*     */   public int compareTo(PropNode other) {
/* 274 */     return getName().compareTo(other.getName());
/*     */   }
/*     */   
/*     */   public void setWorldOriginAligned(boolean worldOriginAligned) {
/* 278 */     this.worldOriginAligned = worldOriginAligned;
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 282 */     fireDisposeListeners();
/* 283 */     this.disposeListeners.clear();
/* 284 */     getEffects().setParticleSurface(null);
/*     */   }
/*     */   
/*     */   private void fireDisposeListeners() {
/* 288 */     for (DisposeListener disposeListener : this.disposeListeners) {
/* 289 */       disposeListener.disposed(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isWorldOriginAligned() {
/* 294 */     return this.worldOriginAligned;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableManaGainFloatingText(boolean enable) {
/* 300 */     this.enableManaGainFloatingText = enable;
/*     */   }
/*     */   
/*     */   public boolean isClickable() {
/* 304 */     return this.userActionHandler.isClickable();
/*     */   }
/*     */   
/*     */   private static class UpdateVectorsListener extends Prop.PropChangeAdapter {
/*     */     private PropNode propNode;
/*     */     
/*     */     private UpdateVectorsListener(PropNode propNode) {
/* 311 */       this.propNode = propNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void positionChanged(Prop prop, WorldCoordinate newPosition) {
/* 316 */       this.propNode.updatePropVectors();
/*     */     }
/*     */ 
/*     */     
/*     */     public void rotationChanged(Prop prop, float newAngle) {
/* 321 */       this.propNode.updatePropVectors();
/*     */     }
/*     */   }
/*     */   
/*     */   public void lockMeshAndShadow() {
/* 326 */     updateWorldVectors();
/* 327 */     lockMeshes();
/* 328 */     lockShadows();
/*     */   }
/*     */   
/*     */   public void unlockMeshAndShadow() {
/* 332 */     updateWorldVectors();
/* 333 */     unlockMeshes();
/* 334 */     unlockShadows();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(Text3D text) {
/* 340 */     this.text = text;
/*     */ 
/*     */     
/* 343 */     BillboardNode node = new BillboardNode("Name container");
/* 344 */     node.setRenderQueueMode(3);
/* 345 */     node.setAlignment(0);
/* 346 */     TrackPropNodeController nodeController = new OverheadTrackPropNodeController(this, (Spatial)node);
/* 347 */     nodeController.setDisplacer(new Vector3f(0.0F, 0.2F, 0.0F));
/* 348 */     text.addController(nodeController);
/* 349 */     node.attachChild((Spatial)this.text);
/* 350 */     addDetachedChild((Spatial)node);
/*     */   }
/*     */   
/*     */   public void setTextColor(ColorRGBA color) {
/* 354 */     if (this.text != null)
/* 355 */       this.text.setFontColor(color); 
/*     */   }
/*     */   
/*     */   public void addDetachedChild(Spatial child) {
/* 359 */     this.detachedChildren.add(child);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicEffectsNode getBasicEffectsNode() {
/* 368 */     return this.basicEffectsNode;
/*     */   }
/*     */   
/*     */   public void transferFloatingText(PropNode from) {
/* 372 */     from.detachedChildren.remove(from.floatingTextSpatial);
/* 373 */     this.floatingTextSpatial = from.floatingTextSpatial;
/* 374 */     this.detachedChildren.add(this.floatingTextSpatial);
/* 375 */     addController((Controller)new UpdatedController(this.floatingTextSpatial));
/*     */   }
/*     */   
/*     */   public void initializeAllEffects(ResourceGetter resourceGetter, ParticleSurface ps) {
/* 379 */     initalizeBasicEffects(resourceGetter);
/* 380 */     initializeEffects(ps);
/*     */   }
/*     */   
/*     */   private void initalizeBasicEffects(ResourceGetter resourceGetter) {
/* 384 */     initializeFloatingText2D(resourceGetter);
/* 385 */     ZBufferState zBufferOff = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 386 */     zBufferOff.setEnabled(false);
/*     */     
/* 388 */     this.basicEffectsNode = new BasicEffectsNode(resourceGetter);
/* 389 */     this.basicEffectsNode.setRenderState((RenderState)zBufferOff);
/* 390 */     this.basicEffectsNode.addController((Controller)new UpdatedController(this.basicEffectsNode));
/*     */     
/* 392 */     TrackPropNodeController nodeController = new OverheadTrackPropNodeController(this, (Spatial)this.basicEffectsNode);
/*     */     
/* 394 */     nodeController.setDisplacer(new Vector3f(0.0F, 0.6F, 0.0F));
/* 395 */     this.basicEffectsNode.addController(nodeController);
/*     */     
/* 397 */     this.detachedChildren.add(this.basicEffectsNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeFloatingText2D(ResourceGetter resourceGetter) {
/* 402 */     ZBufferState zBufferOff = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 403 */     zBufferOff.setEnabled(false);
/*     */ 
/*     */     
/* 406 */     this.floatingTextSpatial = new FloatingTextSpatial(resourceGetter);
/* 407 */     this.floatingTextSpatial.setCenterText(true);
/* 408 */     this.floatingTextSpatial.setDisplacer(0.0F, 130.0F);
/* 409 */     this.floatingTextSpatial.setRenderState((RenderState)zBufferOff);
/* 410 */     addController((Controller)new UpdatedController(this.floatingTextSpatial));
/*     */     
/* 412 */     this.detachedChildren.add(this.floatingTextSpatial);
/*     */   }
/*     */   
/*     */   public void setDamageIndicator(String damageIndicator) {
/* 416 */     this.damageIndicator = damageIndicator;
/*     */   }
/*     */   
/*     */   public void setHealIndicator(String healIndicator) {
/* 420 */     this.healIndicator = healIndicator;
/*     */   }
/*     */   
/*     */   public void setGainManaIndicator(String gainManaIndicator) {
/* 424 */     this.gainManaIndicator = gainManaIndicator;
/*     */   }
/*     */   
/*     */   public void damageFloatingText(int damage) {
/* 428 */     if (this.floatingTextSpatial != null) {
/* 429 */       this.floatingTextSpatial.showText(this.damageIndicator + String.valueOf(damage), this.damageTextColor, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void critFloatingText(int damage) {
/* 434 */     if (this.floatingTextSpatial != null) {
/* 435 */       this.floatingTextSpatial.showText(this.damageIndicator + String.valueOf(damage), this.critTextColor, this, this.floatingTextSpatial.getScale() * 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void processImmunityText(String text, boolean scaleDown) {
/* 440 */     if (this.floatingTextSpatial != null)
/* 441 */       if (scaleDown) {
/* 442 */         this.floatingTextSpatial.showText(text.toUpperCase(), this.immunityTextColor, this, this.floatingTextSpatial.getScale() / 2.0F);
/*     */       } else {
/* 444 */         this.floatingTextSpatial.showText(text.toUpperCase(), this.immunityTextColor, this, this.floatingTextSpatial.getScale());
/*     */       }  
/*     */   }
/*     */   
/*     */   public void healFloatingText(int heal) {
/* 449 */     if (this.floatingTextSpatial != null) {
/* 450 */       this.floatingTextSpatial.showText(this.healIndicator + String.valueOf(heal), this.healTextColor, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gainManaFloatingText(int gainedMana) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printXpPickupText(String text) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printCoinPickupText(String text) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTextColor(ColorRGBA damageTextColor) {
/* 473 */     this.damageTextColor = damageTextColor;
/*     */   }
/*     */   
/*     */   public void setHealTextColor(ColorRGBA healTextColor) {
/* 477 */     this.healTextColor = healTextColor;
/*     */   }
/*     */   
/*     */   private static class NoopActionHandler extends UserActionHandler {
/*     */     private NoopActionHandler() {}
/*     */     
/*     */     public void handleLeftMousePress(WorldCoordinate pressedCoord) {}
/*     */     
/*     */     public boolean isClickable() {
/* 486 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class VisualEffectMapper
/*     */     extends Prop.PropChangeAdapter
/*     */   {
/*     */     private PropNode propNode;
/*     */ 
/*     */ 
/*     */     
/*     */     private VisualEffectMapper(PropNode propNode) {
/* 500 */       this.propNode = propNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void propertyChanged(Prop prop, String propertyName, Object newValue, Object oldValue) {
/* 505 */       VisualEffect visualEffect = this.propNode.getVisualEffect(propertyName);
/* 506 */       if (visualEffect != null) {
/* 507 */         visualEffect.apply(oldValue, newValue);
/*     */       }
/*     */       
/* 510 */       this.propNode.getVisualEffect("*").apply(oldValue, newValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface DisposeListener {
/*     */     void disposed(PropNode param1PropNode);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\PropNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */