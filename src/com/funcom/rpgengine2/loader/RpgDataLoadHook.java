package com.funcom.rpgengine2.loader;

import com.funcom.rpgengine2.checkpoints.CheckpointDescription;

public interface RpgDataLoadHook {
  void genericDataLoaded(String paramString, RpgLoader paramRpgLoader);
  
  void checkpointLoaded(CheckpointDescription paramCheckpointDescription, RpgLoader paramRpgLoader);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RpgDataLoadHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */