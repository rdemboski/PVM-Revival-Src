package com.funcom.rpgengine2.loader;

import java.util.List;

public interface StatIdTranslator {
  Short translate(String paramString);
  
  List<Short> getRuntimeIds();
  
  List<Short> getAllIds();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\StatIdTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */