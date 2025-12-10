/*     */ package com.funcom.gameengine.breadcrumbs;
/*     */ 
/*     */ import com.funcom.commons.configuration.InjectFromProperty;
/*     */ import com.funcom.commons.configuration.URLFieldProperties;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.World;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ @Deprecated
/*     */ class Spawner
/*     */   implements Updated {
/*  25 */   private static final Logger LOGGER = Logger.getLogger(Spawner.class);
/*  26 */   static final URLFieldProperties PROPERTIES = new URLFieldProperties(ClassLoader.getSystemResource("breadcrumb.properties"), Spawner.class);
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float spawnRadius;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float spawnDistance;
/*     */   @InjectFromProperty
/*     */   private static int lookAheadWaypoint;
/*     */   @InjectFromProperty
/*     */   private static float stepDisplacer;
/*     */   @InjectFromProperty
/*     */   private static float forwardAngle;
/*     */   private List<BreadcrumbModel> breadcrumbModels;
/*     */   private Map<BreadcrumbModel, BreadcrumbNode> modelToViewPairs;
/*     */   private ResourceManager resourceManager;
/*     */   private boolean visible;
/*     */   private List<WorldCoordinate> waypoints;
/*     */   private WorldCoordinate currentTargetWaypoint;
/*     */   private Prop player;
/*     */   private World world;
/*     */   private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*     */   private boolean currentStepIsLeftPaw;
/*     */   private WorldCoordinate playerPositionAtLastBreadcrumbCreationTime;
/*     */   private boolean previousDirection;
/*     */   
/*     */   Spawner(ResourceManager resourceManager, Prop player) {
/*  53 */     this.player = player;
/*  54 */     this.resourceManager = resourceManager;
/*     */     
/*  56 */     this.waypoints = new LinkedList<WorldCoordinate>();
/*  57 */     this.breadcrumbModels = new LinkedList<BreadcrumbModel>();
/*  58 */     this.modelToViewPairs = new HashMap<BreadcrumbModel, BreadcrumbNode>();
/*  59 */     this.visible = true;
/*  60 */     this.currentStepIsLeftPaw = true;
/*     */   }
/*     */   
/*     */   public void setWorld(World world) {
/*  64 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void setDireEffectDescriptionFactory(DireEffectDescriptionFactory direEffectDescriptionFactory) {
/*  68 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public void setWaypoints(List<WorldCoordinate> waypoints) {
/*  72 */     this.waypoints.clear();
/*  73 */     this.waypoints.addAll(waypoints);
/*     */   }
/*     */   
/*     */   public Set<BreadcrumbModel> getVisibleBreadcrumbs() {
/*  77 */     return this.modelToViewPairs.keySet();
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  81 */     if (this.waypoints.isEmpty()) {
/*     */       return;
/*     */     }
/*  84 */     if (!this.visible) {
/*  85 */       clearAllVisible();
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     WorldCoordinate closestWaypoint = findTargetWaypoint();
/*  95 */     if (closestWaypoint == null) {
/*  96 */       clearAllVisible();
/*  97 */       this.currentTargetWaypoint = null;
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     if (!closestWaypoint.equals(this.currentTargetWaypoint)) {
/* 102 */       this.currentTargetWaypoint = closestWaypoint;
/* 103 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 104 */         LOGGER.debug("Target node is now: " + this.currentTargetWaypoint);
/*     */       }
/*     */     } 
/* 107 */     removeBreadcrumbsUpdate();
/* 108 */     addBreadcrumbIfNeeded();
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
/*     */   private void addBreadcrumbIfNeeded() {
/* 121 */     if (this.playerPositionAtLastBreadcrumbCreationTime != null) {
/* 122 */       float distance = (float)this.player.getPosition().distanceTo(this.playerPositionAtLastBreadcrumbCreationTime);
/* 123 */       if (distance < spawnDistance)
/*     */         return; 
/*     */     } else {
/* 126 */       this.playerPositionAtLastBreadcrumbCreationTime = new WorldCoordinate(this.player.getPosition());
/*     */     } 
/*     */ 
/*     */     
/* 130 */     WorldCoordinate playerHeadingVector = (new WorldCoordinate(this.playerPositionAtLastBreadcrumbCreationTime)).subtract(this.player.getPosition()).normalize();
/*     */ 
/*     */     
/* 133 */     WorldCoordinate waypointHeadingVector = (new WorldCoordinate(this.player.getPosition())).subtract(this.currentTargetWaypoint).normalize();
/*     */ 
/*     */     
/* 136 */     WorldCoordinate wherePlayerShouldGoDirectionVector = (new WorldCoordinate(this.currentTargetWaypoint)).subtract(this.player.getPosition()).normalize();
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
/* 147 */     boolean headingTowardsWaypoint = isHeadingTowardsWaypoint(playerHeadingVector, waypointHeadingVector);
/* 148 */     if (this.previousDirection != headingTowardsWaypoint) {
/* 149 */       clearAllVisible();
/*     */     }
/* 151 */     this.previousDirection = headingTowardsWaypoint;
/*     */     
/* 153 */     if (headingTowardsWaypoint) {
/* 154 */       WorldCoordinate breadcrumbPosition = (new WorldCoordinate(wherePlayerShouldGoDirectionVector)).multLocal(spawnRadius).add(this.player.getPosition());
/*     */ 
/*     */       
/* 157 */       addNewBreadcrumb(breadcrumbPosition, playerHeadingVector.orthogonal(), false);
/*     */     } else {
/* 159 */       WorldCoordinate breadcrumbPosition = (new WorldCoordinate(this.player.getPosition())).add(wherePlayerShouldGoDirectionVector);
/*     */       
/* 161 */       addNewBreadcrumb(breadcrumbPosition, playerHeadingVector.orthogonal(), true);
/*     */     } 
/*     */     
/* 164 */     this.playerPositionAtLastBreadcrumbCreationTime = new WorldCoordinate(this.player.getPosition());
/*     */   }
/*     */   
/*     */   private boolean isHeadingTowardsWaypoint(WorldCoordinate playerHeadingVector, WorldCoordinate waypointHeadingVector) {
/* 168 */     float headingAngle = transferAngle((float)playerHeadingVector.angle());
/* 169 */     float waypointAngle = transferAngle((float)waypointHeadingVector.angle());
/*     */     
/* 171 */     float rads = 0.017453292F * forwardAngle * 0.5F;
/* 172 */     float headingAngleRangeStart = headingAngle - rads;
/* 173 */     float headingAngleRangeEnd = headingAngle + rads;
/* 174 */     return (waypointAngle > headingAngleRangeStart && waypointAngle < headingAngleRangeEnd);
/*     */   }
/*     */   
/*     */   private void addNewBreadcrumb(WorldCoordinate breadcrumbPosition, WorldCoordinate orthoDisplacer, boolean reverseChainLinks) {
/* 178 */     this.currentStepIsLeftPaw = !this.currentStepIsLeftPaw;
/* 179 */     orthoDisplacer.multLocal(stepDisplacer);
/* 180 */     if (this.currentStepIsLeftPaw) {
/* 181 */       breadcrumbPosition.add(orthoDisplacer);
/*     */     } else {
/* 183 */       breadcrumbPosition.subtract(orthoDisplacer);
/*     */     } 
/* 185 */     BreadcrumbModel newBreadcrumbModel = new BreadcrumbModel(breadcrumbPosition, this.player);
/*     */     
/* 187 */     if (!this.breadcrumbModels.isEmpty()) {
/* 188 */       BreadcrumbModel lastModel = this.breadcrumbModels.get(this.breadcrumbModels.size() - 1);
/* 189 */       if (!reverseChainLinks) {
/* 190 */         lastModel.setNext(newBreadcrumbModel);
/* 191 */         newBreadcrumbModel.setPrevious(lastModel);
/*     */       } else {
/* 193 */         lastModel.setPrevious(newBreadcrumbModel);
/* 194 */         newBreadcrumbModel.setNext(lastModel);
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     this.breadcrumbModels.add(newBreadcrumbModel);
/*     */     
/* 200 */     BreadcrumbNode node = new BreadcrumbNode(newBreadcrumbModel, this.player, this.resourceManager, this.direEffectDescriptionFactory);
/* 201 */     this.world.addObject((RepresentationalNode)node);
/* 202 */     this.modelToViewPairs.put(newBreadcrumbModel, node);
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeBreadcrumbsUpdate() {
/* 207 */     if (this.currentTargetWaypoint == null || this.modelToViewPairs.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     WorldCoordinate playerPosition = this.player.getPosition();
/* 212 */     double angleToNextWaypoint = transferAngle((float)playerPosition.angleTo(this.currentTargetWaypoint));
/*     */ 
/*     */     
/* 215 */     double angleRangeStart = angleToNextWaypoint - 1.5707963705062866D;
/* 216 */     double angleRangeEnd = angleToNextWaypoint + 1.5707963705062866D;
/*     */     
/* 218 */     Iterator<BreadcrumbModel> iterator = this.breadcrumbModels.iterator();
/* 219 */     while (iterator.hasNext()) {
/* 220 */       BreadcrumbModel breadcrumbModel = iterator.next();
/* 221 */       double angleToBreadcrumb = transferAngle((float)playerPosition.angleTo(breadcrumbModel.getPosition()));
/*     */ 
/*     */ 
/*     */       
/* 225 */       boolean shouldRemove = (angleToBreadcrumb < angleRangeStart || angleToBreadcrumb > angleRangeEnd);
/*     */ 
/*     */ 
/*     */       
/* 229 */       if (shouldRemove) {
/* 230 */         BreadcrumbNode node = this.modelToViewPairs.remove(breadcrumbModel);
/* 231 */         this.world.removeObject((RepresentationalNode)node);
/* 232 */         iterator.remove();
/*     */ 
/*     */         
/* 235 */         BreadcrumbModel myNext = breadcrumbModel.getNext();
/* 236 */         BreadcrumbModel myPrevious = breadcrumbModel.getPrevious();
/* 237 */         if (myNext != null)
/* 238 */           myNext.setPrevious(myPrevious); 
/* 239 */         if (myPrevious != null) {
/* 240 */           myPrevious.setNext(myNext);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float transferAngle(float angle) {
/* 247 */     if (angle < 0.0F)
/* 248 */       return 6.2831855F + angle; 
/* 249 */     return angle;
/*     */   }
/*     */   
/*     */   private WorldCoordinate findTargetWaypoint() {
/* 253 */     WorldCoordinate playerPosition = this.player.getPosition();
/* 254 */     double lowestDistance = Double.MAX_VALUE;
/* 255 */     int nearestIndex = -1;
/*     */     
/* 257 */     for (int i = 0; i < this.waypoints.size(); i++) {
/* 258 */       WorldCoordinate worldCoordinate = this.waypoints.get(i);
/* 259 */       double currentDistance = worldCoordinate.distanceTo(playerPosition);
/* 260 */       if (currentDistance < lowestDistance) {
/* 261 */         lowestDistance = currentDistance;
/* 262 */         nearestIndex = i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 267 */     if (nearestIndex == -1) {
/* 268 */       return null;
/*     */     }
/*     */     
/* 271 */     if (nearestIndex + lookAheadWaypoint > this.waypoints.size() - 1) {
/* 272 */       return this.waypoints.get(this.waypoints.size() - 1);
/*     */     }
/*     */     
/* 275 */     return this.waypoints.get(nearestIndex + lookAheadWaypoint);
/*     */   }
/*     */   
/*     */   private void clearAllVisible() {
/* 279 */     for (BreadcrumbNode node : this.modelToViewPairs.values())
/* 280 */       this.world.removeObject((RepresentationalNode)node); 
/* 281 */     this.modelToViewPairs.clear();
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 285 */     this.visible = visible;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\breadcrumbs\Spawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */