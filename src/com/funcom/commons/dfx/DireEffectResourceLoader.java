package com.funcom.commons.dfx;

import org.jdom.Element;

public interface DireEffectResourceLoader {
  Element getDireEffectData(String paramString, boolean paramBoolean);
  
  Object getParticleSystem(String paramString, boolean paramBoolean);
  
  Object getModelNode(String paramString);
  
  Object getJointAnimation(String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\DireEffectResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */