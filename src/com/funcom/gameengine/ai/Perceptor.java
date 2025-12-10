package com.funcom.gameengine.ai;

import java.util.Collection;

public interface Perceptor {
  Collection<? extends Entity> getDetectedEntities(BrainControlled paramBrainControlled);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\Perceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */