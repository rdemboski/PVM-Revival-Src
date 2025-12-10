package com.funcom.commons.jme.md5importer.resource;

import com.jme.image.Texture;
import com.jme.scene.state.TextureState;

public interface TextureFactory {
  TextureState createTexture(String paramString, String[] paramArrayOfString, Texture.MinificationFilter paramMinificationFilter, Texture.MagnificationFilter paramMagnificationFilter, float paramFloat, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\TextureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */