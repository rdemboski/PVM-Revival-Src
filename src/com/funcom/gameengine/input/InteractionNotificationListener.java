package com.funcom.gameengine.input;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.view.PropNode;
import java.awt.Point;
import java.util.Set;

@Deprecated
public interface InteractionNotificationListener {
  public static final int NOBUTTON = -1;
  
  public static final int LEFT = 0;
  
  public static final int RIGHT = 1;
  
  public static final int WHEEL = 2;
  
  void mousePressed(WorldCoordinate paramWorldCoordinate, Set<PropNode> paramSet, Point paramPoint, int paramInt);
  
  void mouseReleased(WorldCoordinate paramWorldCoordinate, Set<PropNode> paramSet, Point paramPoint, int paramInt);
  
  void wheelMoved(WorldCoordinate paramWorldCoordinate, Set<PropNode> paramSet, Point paramPoint, int paramInt);
  
  void mouseMoved(WorldCoordinate paramWorldCoordinate, Point paramPoint);
  
  void positionUpdated(WorldCoordinate paramWorldCoordinate, Set<PropNode> paramSet, Point paramPoint);
  
  void keyPressed(WorldCoordinate paramWorldCoordinate, Set<PropNode> paramSet, Point paramPoint, int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\InteractionNotificationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */