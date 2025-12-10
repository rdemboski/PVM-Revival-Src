package com.funcom.tcg.client.model.rpg;

public interface EquipChangeListener {
  void itemEquipped(ClientEquipDoll paramClientEquipDoll, int paramInt, ClientItem paramClientItem1, ClientItem paramClientItem2);
  
  void itemUnequipped(ClientEquipDoll paramClientEquipDoll, int paramInt, ClientItem paramClientItem);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\EquipChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */