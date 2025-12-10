package com.funcom.tcg.client.ui.vendor;

import com.funcom.tcg.client.ui.Item;
import java.util.Set;

public interface VendorModelItem extends Item {
  Set<PriceDesc> getPrice();
  
  int getAvailableAmount();
  
  boolean isWithinLevel(int paramInt);
  
  int getTier();
  
  int getItemAmount();
  
  String getItemDescriptionText();
  
  int getLevel();
  
  boolean isSubscriberOnly();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorModelItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */