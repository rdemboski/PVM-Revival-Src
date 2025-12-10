package com.funcom.rpgengine2.pets;

import com.funcom.rpgengine2.creatures.RpgQueryableSupport;

public abstract class PetSupport implements RpgQueryableSupport {
  public abstract int getActivePetLevel();
  
  public abstract boolean collectNewPetAndNotifyClient(PetDescription paramPetDescription);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pets\PetSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */