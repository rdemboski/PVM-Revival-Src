package com.funcom.gameengine.model.props;

import com.funcom.gameengine.WorldCoordinate;

public interface MultipleTargetsModel {
  TargetData getClosestToPlayer();
  
  void addSwitchTargetListener(SwitchTargetListener paramSwitchTargetListener);
  
  void removeSwitchTargetListener(SwitchTargetListener paramSwitchTargetListener);
  
  public static interface SwitchTargetListener {
    void targetSwitched(MultipleTargetsModel param1MultipleTargetsModel, MultipleTargetsModel.TargetData param1TargetData);
  }
  
  public static interface TargetData {
    WorldCoordinate getPosition();
    
    boolean isWithinMinimumRange(WorldCoordinate param1WorldCoordinate);
    
    boolean isValid();
    
    boolean show();
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\props\MultipleTargetsModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */