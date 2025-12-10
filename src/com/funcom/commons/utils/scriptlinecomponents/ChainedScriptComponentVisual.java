package com.funcom.commons.utils.scriptlinecomponents;

import java.awt.event.ActionListener;
import javax.swing.JComponent;

public interface ChainedScriptComponentVisual extends ActionListener {
  JComponent getJComponent();
  
  void addActionListener(ActionListener paramActionListener);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\ChainedScriptComponentVisual.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */