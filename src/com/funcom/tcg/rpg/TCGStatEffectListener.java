package com.funcom.tcg.rpg;

import com.funcom.rpgengine2.StatEffect;
import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;

public interface TCGStatEffectListener {
  void handleKilledByOther(RpgEntity paramRpgEntity, StatEffect paramStatEffect);
  
  void handleAttackedByOther(RpgEntity paramRpgEntity, StatEffect paramStatEffect);
  
  @Deprecated
  void notifyNewLevel(int paramInt1, int paramInt2);
  
  void enqueueKilledOther(RpgSourceProviderEntity paramRpgSourceProviderEntity);
  
  void enqueueDamagedOther(RpgSourceProviderEntity paramRpgSourceProviderEntity, int paramInt, boolean paramBoolean);
  
  void notifyManaUsed(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGStatEffectListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */