package com.funcom.rpgengine2.creatures;

public interface DailyQuestSupport extends RpgQueryableSupport {
  long getNextUpdateTime();
  
  boolean hasDailyQuestPointsLeft();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\DailyQuestSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */