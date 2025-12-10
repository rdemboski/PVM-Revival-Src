/*     */ package com.funcom.gameengine.breadcrumbs;
/*     */ 
/*     */ import com.funcom.commons.JMEQueueInvoker;
/*     */ import com.funcom.commons.ThreadInvoker;
/*     */ import com.funcom.commons.configuration.InjectFromProperty;
/*     */ import com.funcom.commons.configuration.ReconfigurePropertiesFrame;
/*     */ import com.funcom.commons.configuration.URLFieldProperties;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.World;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.pathfinding2.BezierInterpolator;
/*     */ import com.funcom.gameengine.pathfinding2.CenterPointWaypointBuilder;
/*     */ import com.funcom.gameengine.pathfinding2.HierarchicalPathFinder;
/*     */ import com.funcom.gameengine.pathfinding2.LineOfSightWaypointBuilder;
/*     */ import com.funcom.gameengine.pathfinding2.LinearInterpolator;
/*     */ import com.funcom.gameengine.pathfinding2.MapConnectivityGraph;
/*     */ import com.funcom.gameengine.pathfinding2.PathGraph;
/*     */ import com.funcom.gameengine.pathfinding2.PathResult;
/*     */ import com.funcom.gameengine.pathfinding2.WaypointBuilder;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BreadcrumbManager
/*     */   implements Updated
/*     */ {
/*  44 */   private static final Logger LOGGER = Logger.getLogger(BreadcrumbManager.class);
/*  45 */   static final URLFieldProperties PROPERTIES = new URLFieldProperties(ClassLoader.getSystemResource("breadcrumb.properties"), BreadcrumbManager.class);
/*     */   
/*     */   @InjectFromProperty
/*     */   private static boolean enabled;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static boolean showReconfigureDialog;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static String mapConnectivityGraphPath;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static String waypointBuilderType;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static String interpolatorType;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float interpolatorSpacing;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float repathingTriggerDistance;
/*     */   
/*     */   private World world;
/*     */   
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   
/*     */   private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*     */   
/*     */   public BreadcrumbManager(ResourceManager resourceManager, Prop player) {
/*  75 */     if (resourceManager == null)
/*  76 */       throw new IllegalArgumentException("resourceManager = null"); 
/*  77 */     if (player == null) {
/*  78 */       throw new IllegalArgumentException("player = null");
/*     */     }
/*  80 */     if (showReconfigureDialog) {
/*  81 */       (new ReconfigurePropertiesFrame((ThreadInvoker)new JMEQueueInvoker(), new URLFieldProperties[] { PROPERTIES, BreadcrumbNode.PROPERTIES })).setVisible(true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.resourceManager = resourceManager;
/*  87 */     this.player = player;
/*  88 */     this.taskExecutor = Executors.newSingleThreadExecutor();
/*  89 */     this.breadcrumbs = new LinkedList<BreadcrumbModel>();
/*  90 */     setupWaypointBuilder();
/*  91 */     setupInterpolator();
/*  92 */     setupPathfinder();
/*     */   }
/*     */   private ResourceManager resourceManager; private Prop player; private MapConnectivityGraph mapConnectivityGraph; private ExecutorService taskExecutor; private Future<PathResult> currentPathfindingFuture; private PathfindingTask currentPathfindingTask; private PathResult currentPathResult; private WorldCoordinate start; private WorldCoordinate end; private WaypointBuilder waypointBuilder; private List<BreadcrumbModel> breadcrumbs;
/*     */   public String getNextMap() {
/*  96 */     if (this.currentPathResult != null && this.currentPathResult.getPathSections().size() > 1) {
/*  97 */       return ((PathResult.PathSection)this.currentPathResult.getPathSections().get(1)).getMapId();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   private void setupWaypointBuilder() {
/* 103 */     if ("centerpoint".equals(waypointBuilderType)) {
/* 104 */       this.waypointBuilder = (WaypointBuilder)new CenterPointWaypointBuilder();
/* 105 */     } else if ("los".equals(waypointBuilderType)) {
/* 106 */       this.waypointBuilder = (WaypointBuilder)new LineOfSightWaypointBuilder();
/*     */     } else {
/* 108 */       throw new IllegalStateException("Invalid waypoint builder type: " + waypointBuilderType);
/*     */     } 
/*     */   }
/*     */   private void setupInterpolator() {
/* 112 */     if ("linear".equals(interpolatorType))
/* 113 */     { this.waypointBuilder = (WaypointBuilder)new LinearInterpolator(this.waypointBuilder, interpolatorSpacing); }
/* 114 */     else if ("bezier".equals(interpolatorType))
/* 115 */     { this.waypointBuilder = (WaypointBuilder)new BezierInterpolator(this.waypointBuilder, interpolatorSpacing); }
/* 116 */     else { if ("none".equals(interpolatorType)) {
/*     */         return;
/*     */       }
/* 119 */       throw new IllegalStateException("Invalid interpolator type: " + interpolatorType); }
/*     */   
/*     */   }
/*     */   
/*     */   private void setupPathfinder() {
/* 124 */     this.mapConnectivityGraph = (MapConnectivityGraph)this.resourceManager.getResource(MapConnectivityGraph.class, mapConnectivityGraphPath);
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 128 */     return enabled;
/*     */   }
/*     */   
/*     */   public void setWorld(World world) {
/* 132 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void setChunkWorldNode(ChunkWorldNode chunkWorldNode) {
/* 136 */     this.chunkWorldNode = chunkWorldNode;
/*     */   }
/*     */   
/*     */   public void setDireEffectDescriptionFactory(DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 140 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 144 */     return (this.world != null && this.chunkWorldNode != null && this.direEffectDescriptionFactory != null && this.chunkWorldNode.isPathgraphAvailable());
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 148 */     if (this.world == null)
/* 149 */       throw new IllegalArgumentException("world = null"); 
/* 150 */     if (this.chunkWorldNode == null)
/* 151 */       throw new IllegalArgumentException("chunkWorldNode = null"); 
/* 152 */     if (this.direEffectDescriptionFactory == null) {
/* 153 */       throw new IllegalArgumentException("direEffectDescriptionFactory = null");
/*     */     }
/* 155 */     postRepathingIfNeeded();
/* 156 */     recreateBreadcrumbsOnDemand();
/*     */   }
/*     */   
/*     */   private void postRepathingIfNeeded() {
/* 160 */     if (this.breadcrumbs.isEmpty() || isAlreadyPathing()) {
/*     */       return;
/*     */     }
/* 163 */     float minDistance = Float.MAX_VALUE;
/*     */ 
/*     */     
/* 166 */     for (BreadcrumbModel breadcrumb : this.breadcrumbs) {
/* 167 */       float distance = breadcrumb.distanceTo(this.player);
/* 168 */       if (distance < minDistance) {
/* 169 */         minDistance = distance;
/*     */       }
/*     */     } 
/* 172 */     if (minDistance > repathingTriggerDistance) {
/* 173 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 174 */         LOGGER.debug(String.format("Player moved away from breadcrumb trail, repathing! distanceToNearest:'%s' | repathingTriggerDistance:'%s'", new Object[] { Float.valueOf(minDistance), Float.valueOf(repathingTriggerDistance) }));
/*     */       }
/* 176 */       repathLocalSection();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void repathLocalSection() {
/* 182 */     this.currentPathfindingTask = new PathfindingTask((PathGraph)this.mapConnectivityGraph, this.player.getPosition(), ((PathResult.PathSection)this.currentPathResult.getPathSections().get(0)).getClosestEnd(this.player.getPosition()), ((PathResult.PathSection)this.currentPathResult.getPathSections().get(0)).getEnds(), this.currentPathResult);
/* 183 */     this.currentPathfindingFuture = this.taskExecutor.submit(this.currentPathfindingTask);
/* 184 */     LOGGER.debug("New breadcrumb pathfinding task submitted.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recreateBreadcrumbsOnDemand() {
/* 191 */     if (this.currentPathfindingFuture != null && this.currentPathfindingFuture.isDone()) {
/*     */ 
/*     */       
/*     */       try {
/* 195 */         hideBreadcrumbs();
/* 196 */         deleteAllBreadcrumbs();
/*     */         
/* 198 */         this.currentPathResult = this.currentPathfindingFuture.get();
/*     */ 
/*     */         
/* 201 */         PathResult.PathSection section = this.currentPathResult.getPathSections().get(0);
/* 202 */         List<WorldCoordinate> waypoints = this.waypointBuilder.makeWaypoints(section.getPath());
/* 203 */         addNewBreadcrumbs(waypoints);
/*     */ 
/*     */         
/* 206 */         this.currentPathfindingFuture = null;
/* 207 */         this.currentPathfindingTask = null;
/* 208 */         LOGGER.info("Waypoints recreated.");
/*     */       }
/* 210 */       catch (InterruptedException e) {
/*     */         
/* 212 */         LOGGER.warn("Pathfinding thread was interrupted - this should NEVER happen! Resubmitting the pathfinding task!", e);
/* 213 */         this.currentPathfindingFuture = this.taskExecutor.submit(this.currentPathfindingTask);
/* 214 */         hideBreadcrumbs();
/*     */       }
/* 216 */       catch (ExecutionException e) {
/*     */ 
/*     */         
/* 219 */         if (e.getCause() instanceof com.funcom.gameengine.pathfinding2.NoPathException) {
/* 220 */           LOGGER.error("Couldn't find path! That's super weird, and it shouldn't happen? Pathgraph might be invalid, or we're searching outside bounds..", e);
/* 221 */           this.currentPathfindingFuture = null;
/* 222 */           this.currentPathfindingTask = null;
/*     */         } else {
/* 224 */           throw new IllegalStateException("Pathfinding fail because of unexpected error (a.k.a. exception :-D)!", e);
/*     */         } 
/*     */       } 
/* 227 */       showBreadcrumbs();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addNewBreadcrumbs(List<WorldCoordinate> waypoints) {
/* 232 */     BreadcrumbModel previous = null;
/* 233 */     for (WorldCoordinate waypoint : waypoints) {
/* 234 */       BreadcrumbModel breadcrumbModel = createNewBreadcrumbModel(waypoint, previous);
/* 235 */       previous = breadcrumbModel;
/*     */       
/* 237 */       BreadcrumbNode node = new BreadcrumbNode(breadcrumbModel, this.player, this.resourceManager, this.direEffectDescriptionFactory);
/* 238 */       breadcrumbModel.setVisible(true);
/* 239 */       this.world.addObject((RepresentationalNode)node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 244 */     hideBreadcrumbs();
/* 245 */     deleteAllBreadcrumbs();
/*     */     
/* 247 */     this.currentPathfindingFuture = null;
/* 248 */     this.currentPathfindingTask = null;
/* 249 */     this.currentPathResult = null;
/* 250 */     this.start = null;
/* 251 */     this.end = null;
/*     */   }
/*     */   
/*     */   private void deleteAllBreadcrumbs() {
/* 255 */     for (BreadcrumbModel breadcrumb : this.breadcrumbs) {
/* 256 */       breadcrumb.removeFromWorld();
/*     */     }
/* 258 */     this.breadcrumbs.clear();
/*     */   }
/*     */   
/*     */   private BreadcrumbModel createNewBreadcrumbModel(WorldCoordinate breadcrumbPosition, BreadcrumbModel previous) {
/* 262 */     BreadcrumbModel newBreadcrumbModel = new BreadcrumbModel(breadcrumbPosition, this.player);
/* 263 */     this.breadcrumbs.add(newBreadcrumbModel);
/*     */     
/* 265 */     newBreadcrumbModel.setPrevious(previous);
/* 266 */     if (previous != null) {
/* 267 */       previous.setNext(newBreadcrumbModel);
/*     */     }
/* 269 */     return newBreadcrumbModel;
/*     */   }
/*     */   
/*     */   public void hideBreadcrumbs() {
/* 273 */     for (BreadcrumbModel breadcrumb : this.breadcrumbs) {
/* 274 */       breadcrumb.setVisible(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void showBreadcrumbs() {
/* 279 */     for (BreadcrumbModel breadcrumb : this.breadcrumbs) {
/* 280 */       breadcrumb.setVisible(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mapLoaded() {
/* 285 */     if (this.start != null && this.end != null)
/* 286 */       findNewPath(this.start, this.end); 
/*     */   }
/*     */   
/*     */   public void findNewPath(WorldCoordinate start, WorldCoordinate end) {
/* 290 */     if (this.chunkWorldNode == null)
/* 291 */       throw new IllegalArgumentException("chunkWorldNode = null"); 
/* 292 */     if (start == null)
/* 293 */       throw new IllegalArgumentException("start = null"); 
/* 294 */     if (end == null) {
/* 295 */       throw new IllegalArgumentException("end = null");
/*     */     }
/* 297 */     this.start = start;
/* 298 */     this.end = end;
/*     */ 
/*     */     
/* 301 */     this.currentPathfindingTask = new PathfindingTask((PathGraph)this.mapConnectivityGraph, start, end, Arrays.asList(new WorldCoordinate[] { end }, ), null);
/* 302 */     this.currentPathfindingFuture = this.taskExecutor.submit(this.currentPathfindingTask);
/* 303 */     LOGGER.debug("New breadcrumb pathfinding task submitted.");
/*     */   }
/*     */   
/*     */   public boolean isAlreadyPathing() {
/* 307 */     return (this.currentPathfindingTask != null);
/*     */   }
/*     */   
/*     */   public boolean isAlreadyPathing(WorldCoordinate start, WorldCoordinate end) {
/* 311 */     return (this.currentPathfindingTask != null && start.equals(this.currentPathfindingTask.start) && end.equals(this.currentPathfindingTask.end));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGraphs() {
/* 320 */     this.taskExecutor.submit(new Runnable() {
/*     */           public void run() {
/* 322 */             BreadcrumbManager.this.mapConnectivityGraph.removeAllChildren();
/* 323 */             BreadcrumbManager.this.mapConnectivityGraph.addChildGraph((PathGraph)BreadcrumbManager.this.chunkWorldNode.getPathGraph());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static final class PathfindingTask implements Callable<PathResult> {
/* 329 */     private static final Logger LOGGER = Logger.getLogger(PathfindingTask.class);
/*     */     private WorldCoordinate start;
/*     */     private WorldCoordinate end;
/*     */     private HierarchicalPathFinder pathFinder;
/*     */     private List<WorldCoordinate> allEnds;
/*     */     private PathResult oldPath;
/*     */     
/*     */     private PathfindingTask(PathGraph pathGraph, WorldCoordinate start, WorldCoordinate end, List<WorldCoordinate> allEnds, PathResult oldPath) {
/* 337 */       if (start == null) {
/* 338 */         throw new IllegalArgumentException("start == null");
/*     */       }
/* 340 */       if (end == null) {
/* 341 */         throw new IllegalArgumentException("end = null");
/*     */       }
/* 343 */       if (allEnds == null || allEnds.isEmpty()) {
/* 344 */         throw new IllegalArgumentException("invalid ends");
/*     */       }
/*     */       
/* 347 */       this.start = start;
/* 348 */       this.end = end;
/* 349 */       this.allEnds = allEnds;
/* 350 */       this.oldPath = oldPath;
/* 351 */       this.pathFinder = new HierarchicalPathFinder();
/* 352 */       this.pathFinder.setRootPathGraph(pathGraph);
/* 353 */       LOGGER.debug("Pathfinding task constructed: #" + hashCode());
/*     */     }
/*     */     
/*     */     public PathResult call() throws Exception {
/* 357 */       LOGGER.debug("Starting pathfind task: #" + hashCode());
/*     */       
/* 359 */       if (LOGGER.isDebugEnabled()) {
/* 360 */         this.pathFinder.enableStatistics(true);
/*     */       } else {
/* 362 */         this.pathFinder.enableStatistics(false);
/*     */       } 
/*     */       
/* 365 */       PathResult pathResult = this.pathFinder.find(this.start, this.end, this.allEnds);
/* 366 */       if (LOGGER.isDebugEnabled()) {
/* 367 */         LOGGER.debug("Pathing finished, stats:\n" + this.pathFinder.getStatistics().toString());
/*     */       }
/*     */       
/* 370 */       if (!pathResult.isComplete()) {
/* 371 */         PathResult.PathSection firstPathSection = pathResult.getPathSections().get(0);
/* 372 */         this.pathFinder.find(firstPathSection);
/* 373 */         if (LOGGER.isDebugEnabled()) {
/* 374 */           LOGGER.debug("Pathing finished, stats:\n" + this.pathFinder.getStatistics().toString());
/*     */         }
/*     */       } 
/* 377 */       if (this.oldPath != null) {
/* 378 */         List<PathResult.PathSection> pathSections = this.oldPath.getPathSections();
/* 379 */         for (int i = 1; i < pathSections.size(); i++) {
/* 380 */           pathResult.getPathSections().add(pathSections.get(i));
/*     */         }
/*     */       } 
/*     */       
/* 384 */       return pathResult;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AddBreadcrumbsTask
/*     */     implements Runnable {
/*     */     private List<BreadcrumbModel> breadcrumbModels;
/*     */     
/*     */     public void run() {
/* 393 */       throw new IllegalStateException("WAITING TO BE IMPLEMENTED!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\breadcrumbs\BreadcrumbManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */