package com.funcom.gameengine.pathfinding2;

import java.util.Set;

public interface PathGraphNode {
  float recalculateCost(AStarNode paramAStarNode1, AStarNode paramAStarNode2);
  
  Set<PathGraphNode> getNeighbors();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\PathGraphNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */