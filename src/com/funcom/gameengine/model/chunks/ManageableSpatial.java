package com.funcom.gameengine.model.chunks;

import com.funcom.commons.geom.RectangleWC;
import com.funcom.gameengine.view.ContentIndentifiable;
import com.jme.scene.Spatial;

public interface ManageableSpatial extends ContentIndentifiable {
  void updatePlacement(int paramInt1, int paramInt2);
  
  void translate(int paramInt1, int paramInt2);
  
  RectangleWC getBounds();
  
  Spatial getSpatial();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ManageableSpatial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */