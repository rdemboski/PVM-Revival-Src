package com.funcom.gameengine.pathfinding2;

import com.funcom.gameengine.WorldCoordinate;
import java.util.Collection;
import java.util.Set;

public interface PathGraph {
  PathGraph getParent();
  
  boolean hasParent();
  
  Set<PathGraph> getChildren();
  
  boolean isLeaf();
  
  PathGraphNode toGraphNode(WorldCoordinate paramWorldCoordinate);
  
  PathGraph toChildGraphForCoord(WorldCoordinate paramWorldCoordinate);
  
  Collection<? extends PathGraphNode> getAllNodes();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\PathGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */