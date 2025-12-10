package com.funcom.commons;

public interface ProgressListener {
  void progressPositionChanged(double paramDouble);
  
  void progressDescriptionChanged(String paramString);
  
  public static class ProgressAdapter implements ProgressListener {
    public void progressPositionChanged(double progress) {}
    
    public void progressDescriptionChanged(String description) {}
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\ProgressListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */