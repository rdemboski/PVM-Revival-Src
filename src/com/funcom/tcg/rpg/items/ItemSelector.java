package com.funcom.tcg.rpg.items;

import com.funcom.rpgengine2.items.Item;

public interface ItemSelector extends ItemTester {
  Item getItem();
  
  void triggerRefresh();
  
  void update(long paramLong);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\items\ItemSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */