package com.funcom.tcg.factories;

import com.funcom.gameengine.model.ResourceGetter;
import com.funcom.gameengine.view.PropNode;
import com.jme.math.Quaternion;

public interface MeshFactory {
  void setDescriptor(Object paramObject);
  
  void setResourceGetter(ResourceGetter paramResourceGetter);
  
  PropNode createPropNode(float paramFloat, Quaternion paramQuaternion);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\factories\MeshFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */