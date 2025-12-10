/*     */ package com.funcom.tcg.client.model;
/*     */ 
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.AbstractBrain;
/*     */ import com.funcom.gameengine.ai.BrainControlled;
/*     */ import com.funcom.gameengine.ai.PickResultsProvider;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionDataProvider;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.command.Destination;
/*     */ import com.funcom.gameengine.model.command.FixedDestination;
/*     */ import com.funcom.gameengine.model.command.MoveCommand;
/*     */ import com.funcom.gameengine.model.command.RotateToWorldCoordCommand;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGGameControlsController;
/*     */ import com.jme.input.controls.GameControl;
/*     */ import com.jmex.bui.dragndrop.BDragNDrop;
/*     */ import java.util.Arrays;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserInputBrain
/*     */   extends AbstractBrain
/*     */ {
/*  32 */   private static final Logger LOGGER = Logger.getLogger(UserInputBrain.class.getName());
/*     */   
/*     */   public static final String BRAIN_NAME = "user-controlled-brain";
/*     */   
/*     */   private CollisionDataProvider collisionDataProvider;
/*     */   private GameControl contextualControl;
/*     */   private GameControl primarySkillControl;
/*     */   private GameControl secondSkillControl;
/*     */   private PickResultsProvider pickResultsProvider;
/*     */   private GameControl forceAttack;
/*     */   private PropNode curMouseOverPropNode;
/*     */   private boolean mousePressed = false;
/*  44 */   private PropNode propnodeTarget = null;
/*     */ 
/*     */   
/*     */   private GameControl pet1Control;
/*     */ 
/*     */   
/*     */   private GameControl pet2Control;
/*     */ 
/*     */   
/*     */   private GameControl pet3Control;
/*     */ 
/*     */   
/*     */   private long petSwitchSpamPreventionTime;
/*     */   
/*     */   private static final long PET_SWITHC_SPAM_PREVENT_CD = 450L;
/*     */ 
/*     */   
/*     */   public UserInputBrain(CollisionDataProvider collisionDataProvider, PickResultsProvider pickResultsProvider, GameControl contextualControl, GameControl primarySkillControl, GameControl secondSkillControl, GameControl forceAttack, GameControl pet1Control, GameControl pet2Control, GameControl pet3Control) {
/*  62 */     this.collisionDataProvider = collisionDataProvider;
/*  63 */     this.contextualControl = contextualControl;
/*  64 */     this.primarySkillControl = primarySkillControl;
/*  65 */     this.secondSkillControl = secondSkillControl;
/*  66 */     this.pickResultsProvider = pickResultsProvider;
/*  67 */     this.forceAttack = forceAttack;
/*     */     
/*  69 */     this.pet1Control = pet1Control;
/*  70 */     this.pet2Control = pet2Control;
/*  71 */     this.pet3Control = pet3Control;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  76 */     return "user-controlled-brain";
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  80 */     if (BDragNDrop.instance().isDragging()) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS.statType);
/*  85 */     TCGGameControlsController controls = MainGameState.getTcgGameControlsController();
/*  86 */     if (controls.isGuiStealingInput()) {
/*  87 */       PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS.statType);
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     if (this.pickResultsProvider.isGuiHit()) {
/*  92 */       if (this.curMouseOverPropNode != null) {
/*  93 */         this.curMouseOverPropNode.handleMouseExit();
/*  94 */         this.curMouseOverPropNode = null;
/*     */       } 
/*  96 */       PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS.statType);
/*     */       return;
/*     */     } 
/*  99 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS.statType);
/* 100 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.ANIMATION_LOADING_TIME.statType);
/* 101 */     updateSkillControls();
/* 102 */     updateMouseControls();
/* 103 */     updateMouseOverStatus();
/* 104 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.ANIMATION_LOADING_TIME.statType);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMouseControls() {
/* 109 */     if (isControlActive(this.contextualControl) && !this.mousePressed) {
/* 110 */       mouseDown();
/* 111 */       this.mousePressed = true;
/* 112 */     } else if (!isControlActive(this.contextualControl) && this.mousePressed) {
/* 113 */       mouseUp();
/* 114 */       this.mousePressed = false;
/* 115 */     } else if (isControlActive(this.contextualControl) && this.mousePressed) {
/* 116 */       mouseDragged();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSkillControls() {
/* 121 */     if (isControlActive(this.primarySkillControl)) {
/* 122 */       useSkill(0);
/* 123 */     } else if (isControlActive(this.secondSkillControl)) {
/* 124 */       useSkill(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateKeyControls() {
/* 129 */     if (isControlActive(this.pet1Control)) {
/* 130 */       switchPet(0);
/* 131 */     } else if (isControlActive(this.pet2Control)) {
/* 132 */       switchPet(1);
/* 133 */     } else if (isControlActive(this.pet3Control)) {
/* 134 */       switchPet(2);
/*     */     } 
/*     */   }
/*     */   private void mouseDragged() {
/* 138 */     if (isControlActive(this.forceAttack)) {
/* 139 */       useSkill(0);
/*     */       
/*     */       return;
/*     */     } 
/* 143 */     if (this.propnodeTarget != null && !this.propnodeTarget.getProp().isDead()) {
/* 144 */       if (this.propnodeTarget.getProp() instanceof Creature)
/* 145 */         this.propnodeTarget.handleLeftMousePress(new WorldCoordinate(this.pickResultsProvider.getPointingCoordinate())); 
/* 146 */     } else if (this.propnodeTarget != null && this.propnodeTarget.getProp().isDead()) {
/* 147 */       useSkill(0);
/*     */     } else {
/* 149 */       if (getControlled().isDoingAnythingExceptMoving())
/*     */         return; 
/* 151 */       moveToPosition(new WorldCoordinate(this.pickResultsProvider.getPointingCoordinate()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void mouseUp() {
/* 156 */     this.propnodeTarget = null;
/*     */   }
/*     */   
/*     */   private void mouseDown() {
/* 160 */     if (isControlActive(this.forceAttack)) {
/* 161 */       useSkill(0);
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     if (MainGameState.getPauseModel().isPaused()) {
/*     */       return;
/*     */     }
/* 168 */     PropNode propNode = this.pickResultsProvider.getTopClickableProp();
/*     */     
/* 170 */     if (propNode == null) {
/* 171 */       if (getControlled().isDoingAnythingExceptMoving())
/*     */         return; 
/* 173 */       moveToPosition(new WorldCoordinate(this.pickResultsProvider.getPointingCoordinate()));
/* 174 */       this.propnodeTarget = null;
/*     */     } else {
/* 176 */       propNode.handleLeftMousePress(new WorldCoordinate(this.pickResultsProvider.getPointingCoordinate()));
/* 177 */       this.propnodeTarget = propNode;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void useSkill(int slot) {
/* 182 */     PropNode propNode = this.pickResultsProvider.getTopPointingProp();
/* 183 */     WorldCoordinate targetPos = MainGameState.getTcgGameControlsController().getPointingCoordinate();
/*     */     
/* 185 */     if (propNode != null) {
/* 186 */       targetPos = propNode.getPosition();
/*     */     }
/* 188 */     if (!getControlled().isDoingAnythingExceptMoving()) {
/* 189 */       getControlled().immediateCommands(Arrays.asList(new Command[] { (Command)new RotateToWorldCoordCommand(targetPos), new UseSkillCommand(slot, (ClientPlayer)getControlled(), TcgGame.getDireEffectDescriptionFactory(), new WorldCoordinateDistanceCalculator(targetPos, getControlled().getPosition())) }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void switchPet(int slot) {
/* 197 */     if (this.petSwitchSpamPreventionTime > GlobalTime.getInstance().getCurrentTime() || getControlled().isDoingAnythingExceptMoving())
/*     */       return; 
/* 199 */     PetSlot petSlot = ((ClientPlayer)getControlled()).getPetSlot(slot);
/* 200 */     if (petSlot.isSelectable() && petSlot.getPet() != null) {
/* 201 */       ((ClientPlayer)getControlled()).setActivePet(petSlot.getPet());
/* 202 */       this.petSwitchSpamPreventionTime = GlobalTime.getInstance().getCurrentTime() + 450L;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isControlActive(GameControl control) {
/* 207 */     return (control.getValue() != 0.0F);
/*     */   }
/*     */   
/*     */   private void updateMouseOverStatus() {
/* 211 */     PropNode propNode = this.pickResultsProvider.getTopPointingProp();
/*     */     
/* 213 */     if (this.curMouseOverPropNode != null)
/* 214 */       this.curMouseOverPropNode.handleMouseExit(); 
/* 215 */     if (propNode != null)
/* 216 */       propNode.handleMouseEnter(); 
/* 217 */     this.curMouseOverPropNode = propNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveToPosition(WorldCoordinate wc) {
/* 223 */     if (MainGameState.getPauseModel().isPaused()) {
/*     */       return;
/*     */     }
/* 226 */     Creature controlled = getControlled();
/*     */     
/* 228 */     double moveDist = controlled.getPosition().distanceTo(wc);
/* 229 */     if (moveDist <= 0.15D) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     FixedDestination destination = new FixedDestination(wc, controlled);
/* 237 */     double currentSpeed = 0.0D;
/* 238 */     if (controlled.getCurrentCommand() instanceof MoveCommand) {
/* 239 */       MoveCommand command = (MoveCommand)controlled.getCurrentCommand();
/* 240 */       currentSpeed = command.getCurrentSpeed();
/*     */     } 
/* 242 */     controlled.immediateCommand((Command)new MoveCommand(this.collisionDataProvider.getCollisionRoot(), (Destination)destination, currentSpeed));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getControlled() {
/* 248 */     return (Creature)super.getControlled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setControlled(BrainControlled controlled) {
/* 253 */     if (!(controlled instanceof Creature))
/* 254 */       throw new IllegalStateException("This brain works only on Creatures."); 
/* 255 */     super.setControlled(controlled);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\UserInputBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */