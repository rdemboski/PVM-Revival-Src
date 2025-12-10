package com.funcom.rpgengine2.creatures;

import com.funcom.rpgengine2.abilities.BuffType;
import com.funcom.rpgengine2.buffs.Buff;
import com.funcom.rpgengine2.combat.Element;

public interface BuffQueue extends RpgQueryableSupport {
  void enqueue(Buff paramBuff);
  
  void enqueueFinish(BuffType paramBuffType, Element paramElement, String paramString);
  
  void enqueueFinnishBuff(String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\BuffQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */