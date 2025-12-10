package com.funcom.gameengine.pathfinding2;

import com.funcom.gameengine.WorldCoordinate;
import java.util.List;

public interface WaypointBuilder {
  List<WorldCoordinate> makeWaypoints(AStarNode paramAStarNode);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\WaypointBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */