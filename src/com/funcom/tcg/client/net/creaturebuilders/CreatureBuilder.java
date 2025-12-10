package com.funcom.tcg.client.net.creaturebuilders;

import com.funcom.gameengine.view.PropNode;
import com.funcom.tcg.net.message.CreatureCreationMessage;
import com.funcom.tcg.rpg.Faction;

public interface CreatureBuilder {
  PropNode build(CreatureCreationMessage paramCreatureCreationMessage);
  
  boolean isBuildable(Faction paramFaction);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\CreatureBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */