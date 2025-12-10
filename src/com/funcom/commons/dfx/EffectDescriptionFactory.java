package com.funcom.commons.dfx;

import org.jdom.Element;

public interface EffectDescriptionFactory {
  public static final String INFINITY = "INF";
  
  EffectDescription createEffect(Element paramElement, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\EffectDescriptionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */