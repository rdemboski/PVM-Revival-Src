/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.IrregularWindow;
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.ai.PickResultsProvider;
/*     */ import com.funcom.gameengine.input.ButtonStateTracker;
/*     */ import com.funcom.gameengine.model.SpatializedWorld;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.utils.SpatialZComparator;
/*     */ import com.funcom.gameengine.view.CameraConfig;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.Controls;
/*     */ import com.funcom.tcg.token.TCGWorld;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.input.controls.Binding;
/*     */ import com.jme.input.controls.GameControl;
/*     */ import com.jme.input.controls.GameControlManager;
/*     */ import com.jme.input.controls.binding.KeyboardBinding;
/*     */ import com.jme.input.controls.binding.MouseAxisBinding;
/*     */ import com.jme.input.controls.binding.MouseButtonBinding;
/*     */ import com.jme.intersection.BoundingPickResults;
/*     */ import com.jme.intersection.PickData;
/*     */ import com.jme.intersection.PickResults;
/*     */ import com.jme.math.Ray;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Geometry;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.EventListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCGGameControlsController
/*     */   implements Updated, PickResultsProvider, IrregularWindow.NonProcessedEventsNotifier
/*     */ {
/*     */   private static final float MIN_ZOOM_FACTOR = 7.5F;
/*     */   private static final float MAX_ZOOM_FACTOR = 27.4F;
/*     */   private PickResults pickResult;
/*     */   private GameControlManager gcm;
/*     */   private SpatializedWorld spatializedWorld;
/*     */   private Set<PropNode> pickedNodes;
/*     */   private WorldCoordinate pickedCoordinate;
/*     */   private ButtonStateTracker guiButtonsTracker;
/*     */   private boolean guiStealingInput;
/*     */   private boolean didNotProcessLastEvent = false;
/*     */   
/*     */   public TCGGameControlsController(GameControlManager gcm, SpatializedWorld spatializedWorld) {
/*  61 */     this.gcm = gcm;
/*  62 */     this.spatializedWorld = spatializedWorld;
/*     */     
/*  64 */     gcm.addControl(Controls.CID_ZOOM_POSITIVE.id).addBinding((Binding)new MouseAxisBinding(3, false));
/*  65 */     gcm.addControl(Controls.CID_ZOOM_NEGATIVE.id).addBinding((Binding)new MouseAxisBinding(3, true));
/*  66 */     gcm.addControl(Controls.CID_CONTEXTUAL_CONTROL.id).addBinding((Binding)new MouseButtonBinding(0));
/*     */     
/*  68 */     gcm.addControl(Controls.CID_FORCE_ATTACK.id).addBinding((Binding)new KeyboardBinding(Controls.CID_FORCE_ATTACK.keyCode));
/*     */ 
/*     */     
/*  71 */     GameControl secondSkillSlotControl = gcm.addControl(Controls.CID_SKILL_SLOT_2.id);
/*  72 */     secondSkillSlotControl.addBinding((Binding)new MouseButtonBinding(1));
/*  73 */     secondSkillSlotControl.addBinding((Binding)new KeyboardBinding(Controls.CID_SKILL_SLOT_2.keyCode));
/*     */     
/*  75 */     gcm.addControl(Controls.CID_SKILL_SLOT_1.id).addBinding((Binding)new KeyboardBinding(Controls.CID_SKILL_SLOT_1.keyCode));
/*     */     
/*  77 */     gcm.addControl(Controls.CID_PET_SLOT1.id).addBinding((Binding)new KeyboardBinding(Controls.CID_PET_SLOT1.keyCode));
/*  78 */     gcm.addControl(Controls.CID_PET_SLOT2.id).addBinding((Binding)new KeyboardBinding(Controls.CID_PET_SLOT2.keyCode));
/*  79 */     gcm.addControl(Controls.CID_PET_SLOT3.id).addBinding((Binding)new KeyboardBinding(Controls.CID_PET_SLOT3.keyCode));
/*     */     
/*  81 */     gcm.addControl(Controls.CID_PAUSE.id).addBinding((Binding)new KeyboardBinding(Controls.CID_PAUSE.keyCode));
/*     */     
/*  83 */     gcm.addControl(Controls.CID_USE_MANA_POTION.id).addBinding((Binding)new KeyboardBinding(Controls.CID_USE_MANA_POTION.keyCode));
/*  84 */     gcm.addControl(Controls.CID_USE_HEALTH_POTION.id).addBinding((Binding)new KeyboardBinding(Controls.CID_USE_HEALTH_POTION.keyCode));
/*     */     
/*  86 */     gcm.addControl(Controls.CID_GUI_OPEN_CHARACTER.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_CHARACTER.keyCode));
/*     */     
/*  88 */     gcm.addControl(Controls.CID_GUI_OPEN_PETS.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_PETS.keyCode));
/*     */     
/*  90 */     gcm.addControl(Controls.CID_GUI_OPEN_MAP.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_MAP.keyCode));
/*  91 */     gcm.addControl(Controls.CID_GUI_OPEN_FRIENDS.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_FRIENDS.keyCode));
/*  92 */     gcm.addControl(Controls.CID_GUI_OPEN_ACHIEVEMENTS.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_ACHIEVEMENTS.keyCode));
/*  93 */     gcm.addControl(Controls.CID_GUI_OPEN_DUEL.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_DUEL.keyCode));
/*  94 */     gcm.addControl(Controls.CID_GUI_OPEN_QUESTS.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_OPEN_QUESTS.keyCode));
/*     */     
/*  96 */     gcm.addControl(Controls.CID_GUI_CLOSE_ALL_OR_MAINMENU.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_CLOSE_ALL_OR_MAINMENU.keyCode));
/*     */     
/*  98 */     gcm.addControl(Controls.CID_GUI_TOGGLE_FULLSCREEN.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_TOGGLE_FULLSCREEN.keyCode));
/*  99 */     gcm.addControl(Controls.CID_GUI_FOCUS_TO_CHAT.id).addBinding((Binding)new KeyboardBinding(Controls.CID_GUI_FOCUS_TO_CHAT.keyCode));
/*     */ 
/*     */     
/* 102 */     this.pickResult = (PickResults)new BoundingPickResults();
/* 103 */     this.pickResult.setCheckDistance(false);
/* 104 */     this.pickedNodes = new HashSet<PropNode>();
/* 105 */     this.pickedCoordinate = new WorldCoordinate();
/*     */     
/* 107 */     setupGuiButtonsTracker();
/* 108 */     zoomHack();
/*     */   }
/*     */   
/*     */   private void setupGuiButtonsTracker() {
/* 112 */     this.guiButtonsTracker = new TcgButtonStateTracker(this.gcm);
/*     */   }
/*     */ 
/*     */   
/*     */   private void zoomHack() {
/* 117 */     CameraConfig.instance().setZoomFactor(27.4F);
/*     */     
/* 119 */     BuiSystem.addGlobalEventListener(new EventListener() {
/*     */           public void eventDispatched(BEvent event) {
/* 121 */             if (event instanceof MouseEvent) {
/* 122 */               if (TCGGameControlsController.this.isGuiHit()) {
/*     */                 return;
/*     */               }
/* 125 */               MouseEvent e = (MouseEvent)event;
/* 126 */               if (e.getType() == 6) {
/* 127 */                 CameraConfig cameraConfig = CameraConfig.instance();
/* 128 */                 float newZoomFactor = cameraConfig.getZoomFactor() - e.getDelta() * 0.001F * cameraConfig.getZoomFactor();
/*     */                 
/* 130 */                 if (newZoomFactor < 27.4F && newZoomFactor > 7.5F)
/* 131 */                   cameraConfig.setZoomFactor(newZoomFactor); 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public GameControl getContextualControl() {
/* 139 */     return this.gcm.getControl(Controls.CID_CONTEXTUAL_CONTROL.id);
/*     */   }
/*     */   
/*     */   public GameControl getPrimarySkill() {
/* 143 */     return this.gcm.getControl(Controls.CID_SKILL_SLOT_1.id);
/*     */   }
/*     */   
/*     */   public GameControl getSecondarySkill() {
/* 147 */     return this.gcm.getControl(Controls.CID_SKILL_SLOT_2.id);
/*     */   }
/*     */   
/*     */   public GameControl getForceAttack() {
/* 151 */     return this.gcm.getControl(Controls.CID_FORCE_ATTACK.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float v) {
/* 156 */     updateRayCollision();
/* 157 */     updateHover();
/* 158 */     this.guiButtonsTracker.update(v);
/*     */   }
/*     */   
/*     */   private void updateHover() {
/* 162 */     PropNode propNode = getTopPointingProp();
/* 163 */     if (propNode == null) {
/* 164 */       MainGameState.getHoverInfoProvider().removeObject();
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     Prop prop = propNode.getProp();
/* 169 */     if (prop instanceof com.funcom.gameengine.model.props.InteractibleProp)
/* 170 */       MainGameState.getHoverInfoProvider().setHoveredObject(propNode); 
/*     */   }
/*     */   
/*     */   private void updateRayCollision() {
/* 174 */     Vector2f screenPos = new Vector2f(MouseInput.get().getXAbsolute(), MouseInput.get().getYAbsolute());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/* 180 */     Vector3f worldCoords1 = display.getWorldCoordinates(screenPos, 0.0F);
/* 181 */     Vector3f worldCoords2 = display.getWorldCoordinates(screenPos, 1.0F);
/*     */ 
/*     */     
/* 184 */     Ray mouseRay = new Ray(worldCoords1, worldCoords2.subtractLocal(worldCoords1).normalizeLocal());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     Vector3f planePosition = new Vector3f();
/* 190 */     mouseRay.intersectsWherePlane(TCGWorld.WORLD_PLANE, planePosition);
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.pickResult.clear();
/* 195 */     this.spatializedWorld.findPick(mouseRay, this.pickResult);
/*     */     
/* 197 */     this.pickedCoordinate.set(WorldOrigin.instance().getX(), WorldOrigin.instance().getY(), planePosition.getX(), planePosition.getZ());
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
/*     */   public Set<PropNode> getPointingProps() {
/* 210 */     this.pickedNodes.clear();
/* 211 */     for (int pickNumber = 0; pickNumber < this.pickResult.getNumber(); pickNumber++) {
/* 212 */       PickData pickData = this.pickResult.getPickData(pickNumber);
/* 213 */       Geometry geometry = pickData.getTargetMesh();
/*     */       
/* 215 */       PropNode propNode = getPropNode((Spatial)geometry);
/* 216 */       if (propNode != null) {
/* 217 */         this.pickedNodes.add(propNode);
/*     */       }
/*     */     } 
/*     */     
/* 221 */     return this.pickedNodes;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropNode getTopPointingProp() {
/* 226 */     return SpatialZComparator.getTopProp(getPointingProps());
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearPickedData() {
/* 231 */     this.pickResult.clear();
/* 232 */     this.pickedNodes.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldCoordinate getPointingCoordinate() {
/* 237 */     return this.pickedCoordinate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuiHit() {
/* 244 */     boolean isHit = false;
/* 245 */     for (BWindow window : BuiSystem.getRootNode().getAllWindows()) {
/* 246 */       if (isHit(window)) {
/* 247 */         isHit = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 251 */     if (!isHit) {
/* 252 */       return false;
/*     */     }
/* 254 */     if (this.didNotProcessLastEvent) {
/* 255 */       this.didNotProcessLastEvent = false;
/* 256 */       return false;
/*     */     } 
/*     */     
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropNode getTopClickableProp() {
/* 264 */     Set<PropNode> props = getPointingProps();
/* 265 */     for (Iterator<PropNode> it = props.iterator(); it.hasNext(); ) {
/* 266 */       PropNode prop = it.next();
/* 267 */       if (!prop.isClickable())
/* 268 */         it.remove(); 
/*     */     } 
/* 270 */     return SpatialZComparator.getTopProp(props);
/*     */   }
/*     */   
/*     */   private boolean isHit(BWindow window) {
/* 274 */     int mouseX = MouseInput.get().getXAbsolute();
/* 275 */     int mouseY = MouseInput.get().getYAbsolute();
/* 276 */     int winX = window.getAbsoluteX();
/* 277 */     int winY = window.getAbsoluteY();
/*     */ 
/*     */     
/* 280 */     if (window instanceof PartiallyNotInteractive) {
/* 281 */       return (window.isVisible() && ((PartiallyNotInteractive)window).isHit());
/*     */     }
/* 283 */     boolean insideWindow = (window.isVisible() && mouseX > winX && mouseX < winX + window.getWidth() && mouseY > winY && mouseY < winY + window.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     return (window.isModal() || insideWindow);
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
/*     */   private PropNode getPropNode(Spatial node) {
/* 300 */     if (node != null && node.getParent() instanceof PropNode) {
/* 301 */       PropNode propNode = (PropNode)node.getParent();
/* 302 */       if (propNode.getRepresentation() == node) {
/* 303 */         if (propNode.hasActionHandler()) {
/* 304 */           return propNode;
/*     */         }
/*     */       } else {
/* 307 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 312 */     if (node != null) {
/* 313 */       return getPropNode((Spatial)node.getParent());
/*     */     }
/*     */     
/* 316 */     return null;
/*     */   }
/*     */   
/*     */   public GameControl getPet1() {
/* 320 */     return this.gcm.getControl(Controls.CID_PET_SLOT1.id);
/*     */   }
/*     */   
/*     */   public GameControl getPet2() {
/* 324 */     return this.gcm.getControl(Controls.CID_PET_SLOT2.id);
/*     */   }
/*     */   
/*     */   public GameControl getPet3() {
/* 328 */     return this.gcm.getControl(Controls.CID_PET_SLOT3.id);
/*     */   }
/*     */   
/*     */   public boolean isGuiStealingInput() {
/* 332 */     return this.guiStealingInput;
/*     */   }
/*     */   
/*     */   public void guiStealingInput(boolean b) {
/* 336 */     this.guiStealingInput = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void didNotProcessTheEvent(IrregularWindow irregularWindow, MouseEvent mouseEvent) {
/* 341 */     this.didNotProcessLastEvent = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TCGGameControlsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */