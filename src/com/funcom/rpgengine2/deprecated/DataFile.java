package com.funcom.rpgengine2.deprecated;

import java.io.FileNotFoundException;
import java.io.Reader;

@Deprecated
public interface DataFile {
  Reader getReader() throws FileNotFoundException;
  
  String getFileName();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\deprecated\DataFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */