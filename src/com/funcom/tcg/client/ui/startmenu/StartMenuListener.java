package com.funcom.tcg.client.ui.startmenu;

import com.jmex.bui.util.Rectangle;
import org.lwjgl.opengl.DisplayMode;

public interface StartMenuListener {
  Rectangle getBackButtonSize();
  
  void comicBack();
  
  void eulaAccepted();
  
  void eulaDeclined();
  
  void eulaBack();
  
  void eulaTos();
  
  void eulaPP();
  
  void loginBack();
  
  void showOptions();
  
  void optionsBack();
  
  void characterCreationBack();
  
  void playerDataBack();
  
  void forgotPasswordForm();
  
  void playerDataAccepted();
  
  void characterVisualsCreated();
  
  void setRes(DisplayMode paramDisplayMode, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\StartMenuListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */