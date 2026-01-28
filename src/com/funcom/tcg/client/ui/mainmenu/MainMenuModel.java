package com.funcom.tcg.client.ui.mainmenu;

import org.lwjgl.opengl.DisplayMode;

public interface MainMenuModel {
  void cancel();
  
  void music();
  
  void sfx();
  
  void fullscreen();
  
  void quitToLogin();
  
  void quit();
  
  String getMusicLbl();
  
  String getSfxLbl();
  
  String getFullscreenLbl();
  
  void options();
  
  void help();
  
  void mainMenu();
  
  void setRes(DisplayMode paramDisplayMode, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\mainmenu\MainMenuModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */