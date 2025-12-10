package com.funcom.rpgengine2.creatures;

public interface RpgEntity {
  <E extends RpgQueryableSupport> E getSupport(Class<E> paramClass);
  
  void fireEvent(SupportEvent paramSupportEvent);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\RpgEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */