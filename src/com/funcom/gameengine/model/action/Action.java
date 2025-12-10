package com.funcom.gameengine.model.action;

import com.funcom.gameengine.model.props.InteractibleProp;

public interface Action {
  void setParent(InteractibleProp paramInteractibleProp);
  
  InteractibleProp getParent();
  
  void perform(InteractibleProp paramInteractibleProp);
  
  boolean isClickable();
  
  String getName();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\action\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */