package com.funcom.gameengine.resourcemanager.downloader;

public interface ResourceDownloaderListener {
  void startedDownloading(String paramString);
  
  void finishedDownloading();
  
  void startedVerifying(String paramString);
  
  void finishedVerifying();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\downloader\ResourceDownloaderListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */