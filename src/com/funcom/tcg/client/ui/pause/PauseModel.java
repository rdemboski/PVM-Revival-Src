package com.funcom.tcg.client.ui.pause;

import com.funcom.gameengine.Updated;

public interface PauseModel extends Updated {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void confirmPause();
  
  void reset();
  
  void activatePause();
  
  void instantPause();
  
  boolean isPaused();
  
  void pauseRejected();
  
  String getPauseText();
  
  String getPauseCompleteText();
  
  String getPauseRejectedText();
  
  public static interface ChangeListener {
    void progressBarUpdate();
    
    void completeProgressBar();
    
    void close();
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pause\PauseModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */