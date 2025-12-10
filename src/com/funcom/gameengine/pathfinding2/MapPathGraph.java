/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.pathfinding2.debug.DebugListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MapPathGraph extends AbstractPathGraph {
/*  19 */   private static final Logger LOGGER = Logger.getLogger(MapPathGraph.class);
/*  20 */   private static final WorldCoordinate[] NODE_SIZES = new WorldCoordinate[6];
/*     */   
/*     */   static {
/*  23 */     NODE_SIZES[0] = new WorldCoordinate(0, 0, 0.25D, 0.25D, "UNIDENTIFIED_MAP", 0);
/*  24 */     NODE_SIZES[1] = new WorldCoordinate(0, 0, 0.5D, 0.5D, "UNIDENTIFIED_MAP", 0);
/*  25 */     NODE_SIZES[2] = new WorldCoordinate(1, 1, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*  26 */     NODE_SIZES[3] = new WorldCoordinate(2, 2, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*  27 */     NODE_SIZES[4] = new WorldCoordinate(4, 4, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*  28 */     NODE_SIZES[5] = new WorldCoordinate(8, 8, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*     */   }
/*     */   
/*     */   private Set<WorldBoundsGraphNode> graphNodes;
/*     */   
/*     */   public MapPathGraph(Set<WorldBoundsGraphNode> graphNodes) {
/*  34 */     this.graphNodes = new HashSet<WorldBoundsGraphNode>();
/*  35 */     this.graphNodes.addAll(graphNodes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathGraphNode toGraphNode(WorldCoordinate worldCoordinate) {
/*  41 */     if (worldCoordinate == null)
/*  42 */       throw new IllegalArgumentException("worldCoordinate = null"); 
/*  43 */     if (this.graphNodes.isEmpty()) {
/*  44 */       throw new IllegalArgumentException("Graph is empty.");
/*     */     }
/*     */     
/*  47 */     String anyNodeMapId = ((WorldBoundsGraphNode)this.graphNodes.iterator().next()).getBounds().getCenter().getMapId();
/*  48 */     if (!anyNodeMapId.equals(worldCoordinate.getMapId())) {
/*  49 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     Iterator<WorldBoundsGraphNode> iterator = this.graphNodes.iterator();
/*  56 */     WorldBoundsGraphNode closestGraphNode = iterator.next();
/*  57 */     float lastDistance = (float)closestGraphNode.getBounds().getCenter().distanceTo(worldCoordinate);
/*     */     
/*  59 */     while (iterator.hasNext()) {
/*  60 */       WorldBoundsGraphNode graphNode = iterator.next();
/*  61 */       float currentDistance = (float)graphNode.getBounds().getCenter().distanceTo(worldCoordinate);
/*  62 */       if (currentDistance < lastDistance) {
/*  63 */         lastDistance = currentDistance;
/*  64 */         closestGraphNode = graphNode;
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     if (closestGraphNode == null) {
/*  69 */       throw new IllegalStateException("Pathgraph has no nodes!");
/*     */     }
/*  71 */     return closestGraphNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<WorldBoundsGraphNode> getAllNodes() {
/*  78 */     return this.graphNodes;
/*     */   }
/*     */   
/*     */   public static MapPathGraph create(List<LineWC> collisionLines, WorldCoordinate startingPosition, DebugListener debugListener, boolean optimizeGraphSize) {
/*  82 */     LOGGER.info("Graph flood fill started...");
/*     */ 
/*     */     
/*  85 */     List<WorldBoundsGraphNode> graphNodes = new LinkedList<WorldBoundsGraphNode>();
/*  86 */     HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap = new HashMap<StupidBound, List<WorldBoundsGraphNode>>();
/*  87 */     initialGraphCreation(startingPosition, graphNodes, graphNodesMap, collisionLines, debugListener);
/*  88 */     LOGGER.info("Initial creation done, nodes created: " + graphNodes.size());
/*     */     
/*  90 */     if (optimizeGraphSize) {
/*  91 */       LOGGER.info("Optimization enabled, proceeding with node merge: " + graphNodes.size());
/*  92 */       quadTreeLikeMerging(graphNodes, graphNodesMap, debugListener);
/*  93 */       LOGGER.info("Merging done, nodes: " + graphNodes.size());
/*     */     } 
/*     */     
/*  96 */     assertWhatWeCan(graphNodes, graphNodesMap);
/*  97 */     LOGGER.info("Done, graph nodes count: " + graphNodes.size());
/*  98 */     return new MapPathGraph(new HashSet<WorldBoundsGraphNode>(graphNodes));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void assertWhatWeCan(List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap) {
/* 103 */     for (PathGraphNode graphNode : graphNodes) {
/* 104 */       for (PathGraphNode node : graphNode.getNeighbors()) {
/* 105 */         assert node != null : "Neighbour is null!";
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initialGraphCreation(WorldCoordinate startingPosition, List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap, List<LineWC> collisionLines, DebugListener debugListener) {
/* 115 */     Queue<WorldBoundsGraphNode> nonProcessedNodesQueue = new LinkedList<WorldBoundsGraphNode>();
/*     */     
/* 117 */     RectangleWC bounds = createBounds((new WorldCoordinate(startingPosition)).floor(), NODE_SIZES[0]);
/* 118 */     WorldBoundsGraphNode startingGraphNode = new WorldBoundsGraphNode(bounds);
/* 119 */     graphNodes.add(startingGraphNode);
/* 120 */     debugListener.quadAdded(startingGraphNode);
/* 121 */     nonProcessedNodesQueue.add(startingGraphNode);
/*     */     
/* 123 */     StupidBound bound = new StupidBound(bounds.getOrigin().getTileX(), bounds.getOrigin().getTileY(), bounds.getExtent().getTileX(), bounds.getExtent().getTileY());
/* 124 */     List<WorldBoundsGraphNode> nodeList = new ArrayList<WorldBoundsGraphNode>();
/* 125 */     graphNodesMap.put(bound, nodeList);
/* 126 */     nodeList.add(startingGraphNode);
/*     */     
/* 128 */     WorldCoordinate topVector = new WorldCoordinate();
/* 129 */     topVector.subtractVertical(NODE_SIZES[0]);
/*     */     
/* 131 */     WorldCoordinate topRightVector = new WorldCoordinate();
/* 132 */     topRightVector.addHorizontal(NODE_SIZES[0]);
/* 133 */     topRightVector.subtractVertical(NODE_SIZES[0]);
/*     */     
/* 135 */     WorldCoordinate rightVector = new WorldCoordinate();
/* 136 */     rightVector.addHorizontal(NODE_SIZES[0]);
/*     */     
/* 138 */     WorldCoordinate bottomRightVector = new WorldCoordinate();
/* 139 */     bottomRightVector.addHorizontal(NODE_SIZES[0]);
/* 140 */     bottomRightVector.addVertical(NODE_SIZES[0]);
/*     */     
/* 142 */     WorldCoordinate bottomVector = new WorldCoordinate();
/* 143 */     bottomVector.addVertical(NODE_SIZES[0]);
/*     */     
/* 145 */     WorldCoordinate bottomLeftVector = new WorldCoordinate();
/* 146 */     bottomLeftVector.subtractHorizontal(NODE_SIZES[0]);
/* 147 */     bottomLeftVector.addVertical(NODE_SIZES[0]);
/*     */     
/* 149 */     WorldCoordinate leftVector = new WorldCoordinate();
/* 150 */     leftVector.subtractHorizontal(NODE_SIZES[0]);
/*     */     
/* 152 */     WorldCoordinate topLeftVector = new WorldCoordinate();
/* 153 */     topLeftVector.subtractHorizontal(NODE_SIZES[0]);
/* 154 */     topLeftVector.subtractVertical(NODE_SIZES[0]);
/*     */ 
/*     */ 
/*     */     
/* 158 */     while (!nonProcessedNodesQueue.isEmpty()) {
/* 159 */       WorldBoundsGraphNode currentGraphNode = nonProcessedNodesQueue.remove();
/*     */ 
/*     */ 
/*     */       
/* 163 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, topVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 164 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, topRightVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 165 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, rightVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 166 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, bottomRightVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 167 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, bottomVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 168 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, bottomLeftVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 169 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, leftVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/* 170 */       processNeighbor(nonProcessedNodesQueue, currentGraphNode, topLeftVector, NODE_SIZES[0], graphNodes, graphNodesMap, collisionLines, debugListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void quadTreeLikeMerging(List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap, DebugListener debugListener) {
/* 177 */     for (int i = 1; i < NODE_SIZES.length; i++)
/* 178 */       mergeWithLevel(graphNodes, graphNodesMap, debugListener, i); 
/*     */   }
/*     */   
/*     */   private static void mergeWithLevel(List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap, DebugListener debugListener, int level) {
/* 182 */     LinkedList<WorldBoundsGraphNode> nonProcessedNodesQueue = new LinkedList<WorldBoundsGraphNode>();
/*     */     
/* 184 */     WorldCoordinate topVector = new WorldCoordinate();
/* 185 */     topVector.subtractVertical(NODE_SIZES[level]);
/*     */     
/* 187 */     WorldCoordinate topRightVector = new WorldCoordinate();
/* 188 */     topRightVector.addHorizontal(NODE_SIZES[level]);
/* 189 */     topRightVector.subtractVertical(NODE_SIZES[level]);
/*     */     
/* 191 */     WorldCoordinate rightVector = new WorldCoordinate();
/* 192 */     rightVector.addHorizontal(NODE_SIZES[level]);
/*     */     
/* 194 */     WorldCoordinate bottomRightVector = new WorldCoordinate();
/* 195 */     bottomRightVector.addHorizontal(NODE_SIZES[level]);
/* 196 */     bottomRightVector.addVertical(NODE_SIZES[level]);
/*     */     
/* 198 */     WorldCoordinate bottomVector = new WorldCoordinate();
/* 199 */     bottomVector.addVertical(NODE_SIZES[level]);
/*     */     
/* 201 */     WorldCoordinate bottomLeftVector = new WorldCoordinate();
/* 202 */     bottomLeftVector.subtractHorizontal(NODE_SIZES[level]);
/* 203 */     bottomLeftVector.addVertical(NODE_SIZES[level]);
/*     */     
/* 205 */     WorldCoordinate leftVector = new WorldCoordinate();
/* 206 */     leftVector.subtractHorizontal(NODE_SIZES[level]);
/*     */     
/* 208 */     WorldCoordinate topLeftVector = new WorldCoordinate();
/* 209 */     topLeftVector.subtractHorizontal(NODE_SIZES[level]);
/* 210 */     topLeftVector.subtractVertical(NODE_SIZES[level]);
/*     */     
/* 212 */     for (WorldBoundsGraphNode node : graphNodes) {
/* 213 */       RectangleWC bounds = createBounds(node.getBounds().getOrigin(), NODE_SIZES[level]);
/*     */       
/* 215 */       WorldBoundsGraphNode startingGraphNode = new WorldBoundsGraphNode(bounds);
/* 216 */       nonProcessedNodesQueue.add(startingGraphNode);
/*     */     } 
/*     */     
/* 219 */     while (!nonProcessedNodesQueue.isEmpty()) {
/* 220 */       WorldBoundsGraphNode currentMergeNode = nonProcessedNodesQueue.remove();
/*     */ 
/*     */       
/* 223 */       Set<WorldBoundsGraphNode> encompassedNodes = getEncompassNodes(currentMergeNode, graphNodesMap);
/*     */       
/* 225 */       if (encompassedNodes.size() != 4) {
/*     */         continue;
/*     */       }
/*     */       
/* 229 */       graphNodes.removeAll(encompassedNodes);
/*     */ 
/*     */       
/* 232 */       graphNodes.add(currentMergeNode);
/* 233 */       debugListener.merge(currentMergeNode);
/*     */ 
/*     */       
/* 236 */       for (WorldBoundsGraphNode encompassedNode : encompassedNodes) {
/* 237 */         for (PathGraphNode neighboringNode : encompassedNode.getNeighbors()) {
/* 238 */           WorldBoundsGraphNode boundsGraphNode = (WorldBoundsGraphNode)neighboringNode;
/*     */           
/* 240 */           boundsGraphNode.removeAllNeighbors(encompassedNodes);
/*     */           
/* 242 */           if (!encompassedNodes.contains(boundsGraphNode)) {
/* 243 */             boundsGraphNode.addNeighbor(currentMergeNode);
/* 244 */             currentMergeNode.addNeighbor(boundsGraphNode);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 251 */       assert currentMergeNode.getNeighbors().size() <= Math.pow(((int)Math.pow(2.0D, level) + 2), 2.0D) - Math.pow((int)Math.pow(2.0D, level), 2.0D) : "Merge node DOESN'T contain correct number of nodes for the level:\nlevel: " + level + "\n" + "expected nodes: " + (Math.pow(((int)Math.pow(2.0D, level) + 2), 2.0D) - Math.pow((int)Math.pow(2.0D, level), 2.0D)) + "\n" + "nodes in neighbor: " + currentMergeNode.getNeighbors().size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       addMergeNeighbor(nonProcessedNodesQueue, topVector, currentMergeNode, NODE_SIZES[level]);
/* 258 */       addMergeNeighbor(nonProcessedNodesQueue, topRightVector, currentMergeNode, NODE_SIZES[level]);
/* 259 */       addMergeNeighbor(nonProcessedNodesQueue, rightVector, currentMergeNode, NODE_SIZES[level]);
/* 260 */       addMergeNeighbor(nonProcessedNodesQueue, bottomRightVector, currentMergeNode, NODE_SIZES[level]);
/* 261 */       addMergeNeighbor(nonProcessedNodesQueue, bottomVector, currentMergeNode, NODE_SIZES[level]);
/* 262 */       addMergeNeighbor(nonProcessedNodesQueue, bottomLeftVector, currentMergeNode, NODE_SIZES[level]);
/* 263 */       addMergeNeighbor(nonProcessedNodesQueue, leftVector, currentMergeNode, NODE_SIZES[level]);
/* 264 */       addMergeNeighbor(nonProcessedNodesQueue, topLeftVector, currentMergeNode, NODE_SIZES[level]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Set<WorldBoundsGraphNode> getEncompassNodes(WorldBoundsGraphNode currentMergeNode, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap) {
/* 269 */     Set<WorldBoundsGraphNode> encompassedNodes = new HashSet<WorldBoundsGraphNode>();
/* 270 */     RectangleWC encompassNodeBounds = currentMergeNode.getBounds();
/*     */     
/* 272 */     WorldCoordinate centerCoord = encompassNodeBounds.getCenter();
/* 273 */     RectangleWC topLeft = new RectangleWC(encompassNodeBounds.getOrigin(), centerCoord);
/*     */     
/* 275 */     StupidBound topLeftBound = new StupidBound(encompassNodeBounds.getOrigin().getTileX(), encompassNodeBounds.getOrigin().getTileY(), centerCoord.getTileX(), centerCoord.getTileY());
/*     */     
/* 277 */     RectangleWC topRight = new RectangleWC(new WorldCoordinate(centerCoord.getTileX(), encompassNodeBounds.getOrigin().getTileY(), centerCoord.getTileOffX(), encompassNodeBounds.getOrigin().getTileOffY(), centerCoord.getMapId(), 0), new WorldCoordinate(encompassNodeBounds.getExtent().getTileX(), centerCoord.getTileY(), encompassNodeBounds.getExtent().getTileOffX(), centerCoord.getTileOffY(), centerCoord.getMapId(), 0));
/*     */ 
/*     */ 
/*     */     
/* 281 */     StupidBound topRightBound = new StupidBound(centerCoord.getTileX(), encompassNodeBounds.getOrigin().getTileY(), encompassNodeBounds.getExtent().getTileX(), centerCoord.getTileY());
/*     */     
/* 283 */     RectangleWC bottomLeft = new RectangleWC(new WorldCoordinate(encompassNodeBounds.getOrigin().getTileX(), centerCoord.getTileY(), encompassNodeBounds.getOrigin().getTileOffX(), centerCoord.getTileOffY(), centerCoord.getMapId(), 0), new WorldCoordinate(centerCoord.getTileX(), encompassNodeBounds.getExtent().getTileY(), centerCoord.getTileOffX(), encompassNodeBounds.getExtent().getTileOffY(), centerCoord.getMapId(), 0));
/*     */ 
/*     */ 
/*     */     
/* 287 */     StupidBound bottomLeftBound = new StupidBound(encompassNodeBounds.getOrigin().getTileX(), centerCoord.getTileY(), centerCoord.getTileX(), encompassNodeBounds.getExtent().getTileY());
/*     */     
/* 289 */     RectangleWC bottomRight = new RectangleWC(centerCoord, encompassNodeBounds.getExtent());
/*     */     
/* 291 */     StupidBound bottomRightBound = new StupidBound(centerCoord.getTileX(), centerCoord.getTileY(), encompassNodeBounds.getExtent().getTileX(), encompassNodeBounds.getExtent().getTileY());
/*     */     
/* 293 */     List<WorldBoundsGraphNode> topLeftList = graphNodesMap.get(topLeftBound);
/* 294 */     if (topLeftList == null) {
/* 295 */       return new HashSet<WorldBoundsGraphNode>();
/*     */     }
/* 297 */     boolean found = false;
/*     */     
/* 299 */     for (WorldBoundsGraphNode graphNode : topLeftList) {
/* 300 */       if (topLeft.equals(graphNode.getBounds())) {
/* 301 */         if (found) {
/* 302 */           return new HashSet<WorldBoundsGraphNode>();
/*     */         }
/* 304 */         found = true;
/* 305 */         encompassedNodes.add(graphNode);
/*     */       } 
/*     */     } 
/*     */     
/* 309 */     found = false;
/*     */     
/* 311 */     List<WorldBoundsGraphNode> topRightList = graphNodesMap.get(topRightBound);
/* 312 */     if (topRightList == null) {
/* 313 */       return new HashSet<WorldBoundsGraphNode>();
/*     */     }
/* 315 */     for (WorldBoundsGraphNode graphNode : topRightList) {
/* 316 */       if (topRight.equals(graphNode.getBounds())) {
/* 317 */         if (found) {
/* 318 */           return new HashSet<WorldBoundsGraphNode>();
/*     */         }
/* 320 */         found = true;
/* 321 */         encompassedNodes.add(graphNode);
/*     */       } 
/*     */     } 
/*     */     
/* 325 */     found = false;
/*     */     
/* 327 */     List<WorldBoundsGraphNode> bottomLeftList = graphNodesMap.get(bottomLeftBound);
/* 328 */     if (bottomLeftList == null) {
/* 329 */       return new HashSet<WorldBoundsGraphNode>();
/*     */     }
/* 331 */     for (WorldBoundsGraphNode graphNode : bottomLeftList) {
/* 332 */       if (bottomLeft.equals(graphNode.getBounds())) {
/* 333 */         if (found) {
/* 334 */           return new HashSet<WorldBoundsGraphNode>();
/*     */         }
/* 336 */         found = true;
/* 337 */         encompassedNodes.add(graphNode);
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     found = false;
/*     */     
/* 343 */     List<WorldBoundsGraphNode> bottomRightList = graphNodesMap.get(bottomRightBound);
/* 344 */     if (bottomRightList == null) {
/* 345 */       return new HashSet<WorldBoundsGraphNode>();
/*     */     }
/* 347 */     for (WorldBoundsGraphNode graphNode : bottomRightList) {
/* 348 */       if (bottomRight.equals(graphNode.getBounds())) {
/* 349 */         if (found) {
/* 350 */           return new HashSet<WorldBoundsGraphNode>();
/*     */         }
/* 352 */         found = true;
/* 353 */         encompassedNodes.add(graphNode);
/*     */       } 
/*     */     } 
/*     */     
/* 357 */     if (encompassedNodes.size() == 4) {
/*     */       
/* 359 */       topLeftList.removeAll(encompassedNodes);
/* 360 */       topRightList.removeAll(encompassedNodes);
/* 361 */       bottomLeftList.removeAll(encompassedNodes);
/* 362 */       bottomRightList.removeAll(encompassedNodes);
/*     */ 
/*     */       
/* 365 */       StupidBound bound = new StupidBound(encompassNodeBounds.getOrigin().getTileX(), encompassNodeBounds.getOrigin().getTileY(), encompassNodeBounds.getExtent().getTileX(), encompassNodeBounds.getExtent().getTileY());
/* 366 */       List<WorldBoundsGraphNode> list = graphNodesMap.get(bound);
/* 367 */       if (list == null) {
/* 368 */         list = new ArrayList<WorldBoundsGraphNode>();
/* 369 */         graphNodesMap.put(bound, list);
/*     */       } 
/* 371 */       list.add(currentMergeNode);
/*     */     } 
/*     */     
/* 374 */     return encompassedNodes;
/*     */   }
/*     */   
/*     */   private static void addMergeNeighbor(LinkedList<WorldBoundsGraphNode> nonProcessedNodesQueue, WorldCoordinate directionVector, WorldBoundsGraphNode currentMergeNode, WorldCoordinate sizeVector) {
/* 378 */     WorldCoordinate neighborOrigin = new WorldCoordinate(currentMergeNode.getBounds().getOrigin());
/* 379 */     neighborOrigin.add(directionVector);
/* 380 */     nonProcessedNodesQueue.addFirst(new WorldBoundsGraphNode(createBounds(neighborOrigin, sizeVector)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processNeighbor(Queue<WorldBoundsGraphNode> nonProcessedNodesQueue, WorldBoundsGraphNode currentGraphNode, WorldCoordinate vector, WorldCoordinate size, List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap, List<LineWC> collisionLines, DebugListener debugListener) {
/* 391 */     WorldCoordinate neighbor = new WorldCoordinate(currentGraphNode.getBounds().getOrigin());
/* 392 */     neighbor.add(vector);
/*     */     
/* 394 */     RectangleWC bounds = createBounds(neighbor, size);
/*     */     
/* 396 */     WorldBoundsGraphNode neighborGraphNode = getOrCreateNeighbor(bounds, graphNodes, graphNodesMap, collisionLines);
/* 397 */     if (neighborGraphNode == null) {
/*     */       return;
/*     */     }
/* 400 */     currentGraphNode.addNeighbor(neighborGraphNode);
/* 401 */     neighborGraphNode.addNeighbor(currentGraphNode);
/*     */ 
/*     */     
/* 404 */     if (!graphNodes.contains(neighborGraphNode)) {
/*     */       
/* 406 */       nonProcessedNodesQueue.add(neighborGraphNode);
/* 407 */       graphNodes.add(neighborGraphNode);
/* 408 */       debugListener.quadAdded(neighborGraphNode);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static WorldBoundsGraphNode getOrCreateNeighbor(RectangleWC bounds, List<WorldBoundsGraphNode> graphNodes, HashMap<StupidBound, List<WorldBoundsGraphNode>> graphNodesMap, List<LineWC> collisionLines) {
/* 413 */     WorldBoundsGraphNode neighborGraphNode = null;
/* 414 */     StupidBound bound = new StupidBound(bounds.getOrigin().getTileX(), bounds.getOrigin().getTileY(), bounds.getExtent().getTileX(), bounds.getExtent().getTileY());
/* 415 */     List<WorldBoundsGraphNode> nodesList = graphNodesMap.get(bound);
/* 416 */     if (nodesList != null) {
/* 417 */       for (WorldBoundsGraphNode graphNode : nodesList) {
/* 418 */         if (graphNode.getBounds().equals(bounds)) {
/* 419 */           neighborGraphNode = graphNode;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 424 */       nodesList = new ArrayList<WorldBoundsGraphNode>();
/* 425 */       graphNodesMap.put(bound, nodesList);
/*     */     } 
/* 427 */     if (neighborGraphNode == null && checkPassable(bounds, collisionLines)) {
/* 428 */       neighborGraphNode = new WorldBoundsGraphNode(bounds);
/* 429 */       nodesList.add(neighborGraphNode);
/*     */     } 
/* 431 */     return neighborGraphNode;
/*     */   }
/*     */   
/*     */   private static RectangleWC createBounds(WorldCoordinate startingPosition, WorldCoordinate sizeVector) {
/* 435 */     WorldCoordinate origin = new WorldCoordinate(startingPosition);
/* 436 */     WorldCoordinate extent = (new WorldCoordinate(origin)).add(sizeVector);
/* 437 */     return new RectangleWC(origin, extent);
/*     */   }
/*     */   
/*     */   private static boolean checkPassable(RectangleWC bounds, List<LineWC> collisionLines) {
/* 441 */     for (LineWC collisionLine : collisionLines) {
/* 442 */       if (collisionLine.intersects(bounds))
/* 443 */         return false; 
/*     */     } 
/* 445 */     return true;
/*     */   }
/*     */   
/*     */   private static class StupidBound {
/*     */     private int x1;
/*     */     private int y1;
/*     */     private int x2;
/*     */     private int y2;
/*     */     
/*     */     private StupidBound(int x1, int y1, int x2, int y2) {
/* 455 */       this.x1 = x1;
/* 456 */       this.y1 = y1;
/* 457 */       this.x2 = x2;
/* 458 */       this.y2 = y2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 463 */       if (this == o) return true; 
/* 464 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 466 */       StupidBound that = (StupidBound)o;
/*     */       
/* 468 */       if (this.x1 != that.x1) return false; 
/* 469 */       if (this.x2 != that.x2) return false; 
/* 470 */       if (this.y1 != that.y1) return false; 
/* 471 */       if (this.y2 != that.y2) return false;
/*     */       
/* 473 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 478 */       int result = this.x1;
/* 479 */       result = 31 * result + this.y1;
/* 480 */       result = 31 * result + this.x2;
/* 481 */       result = 31 * result + this.y2;
/* 482 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\MapPathGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */