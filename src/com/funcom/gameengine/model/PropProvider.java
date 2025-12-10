package com.funcom.gameengine.model;

import com.funcom.commons.geom.RectangleWC;
import com.funcom.gameengine.model.props.InteractibleProp;
import java.util.Set;

public interface PropProvider {
  Set<InteractibleProp> getProps(RectangleWC paramRectangleWC);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\PropProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */