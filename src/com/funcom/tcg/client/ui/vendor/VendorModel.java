package com.funcom.tcg.client.ui.vendor;

import com.funcom.tcg.client.model.rpg.ClientItem;
import com.funcom.tcg.client.ui.inventory.InventoryItem;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface VendorModel {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  int getCreatureId();
  
  String getName();
  
  List<VendorModelItem> getVendorItems();
  
  List<VendorModelItem> getBuyBackItems();
  
  void addBuyBackItem(VendorModelItem paramVendorModelItem);
  
  void removeBuybackItem(VendorModelItem paramVendorModelItem);
  
  void sellItem(int paramInt1, int paramInt2, int paramInt3);
  
  void buyItem(VendorModelItem paramVendorModelItem);
  
  Set<PriceDesc> getPlayerCurrency();
  
  Iterator<InventoryItem> getPlayerItems();
  
  void disposeModel();
  
  void buyHover(VendorModelItem paramVendorModelItem);
  
  void sellHover(ClientItem paramClientItem);
  
  void hoverExit();
  
  void sellItemToggle(boolean paramBoolean, SellInventoryItem paramSellInventoryItem);
  
  void buyItemToggle(boolean paramBoolean, VendorItemButton paramVendorItemButton);
  
  public static interface ChangeListener {
    void vendorItemsChanged();
    
    void playerCurrencyChanged(String param1String, int param1Int);
    
    void itemSold(VendorModelItem param1VendorModelItem);
    
    void newItemInInventory(String param1String, int param1Int);
    
    void buyItemHoverEnter(VendorModelItem param1VendorModelItem);
    
    void sellItemHoverEnter(ClientItem param1ClientItem);
    
    void ItemHoverExit();
    
    void buyItem(boolean param1Boolean, VendorItemButton param1VendorItemButton);
    
    void sellItem(boolean param1Boolean, SellInventoryItem param1SellInventoryItem);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */