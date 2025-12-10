package com.funcom.rpgengine2.deprecated;

import com.funcom.commons.dfx.DireEffectResourceLoader;
import java.util.List;

@Deprecated
public interface DataReader {
  List<DataFile> getItemsFiles();
  
  List<DataFile> getPetFiles();
  
  List<DataFile> getMonstersFiles();
  
  List<DataFile> getShapesFiles();
  
  List<DataFile> getElementsFiles();
  
  List<DataFile> getEffectCreatorsFiles();
  
  List<DataFile> getSourceEffectFiltersFiles();
  
  List<DataFile> getTargetEffectFiltersFiles();
  
  List<DataFile> getLevelStatsFiles();
  
  List<DataFile> getStatModifierFiles();
  
  List<DataFile> getBuffFiles();
  
  List<DataFile> getDebuffFiles();
  
  List<DataFile> getProjectileCreatorsFiles();
  
  List<DataFile> getTargetedEffectCreatorsFiles();
  
  List<DataFile> getConfigFiles();
  
  List<DataFile> getLootDescriptionFiles();
  
  List<DataFile> getGroupLootDescriptionFiles();
  
  List<DataFile> getLootMobTypes();
  
  List<DataFile> getMonsterLootGroups();
  
  List<DataFile> getVendors();
  
  List<DataFile> getVendorItems();
  
  List<DataFile> getQuests();
  
  List<DataFile> getQuestRewards();
  
  List<DataFile> getQuestObjectives();
  
  List<DataFile> getDebuffCureFiles();
  
  List<DataFile> getStartUpEquipment();
  
  List<DataFile> getProjectilePaths();
  
  List<DataFile> getPickUpItemsFiles();
  
  DireEffectResourceLoader getDFXResourceLoader();
  
  List<DataFile> getMovementManipulatorCreatorsFiles();
  
  List<DataFile> getWaypointFiles();
  
  List<DataFile> getWaypointDestinationPortals();
  
  List<DataFile> getCheckpointDescriptions();
  
  List<DataFile> getAiEventFiles();
  
  List<DataFile> getSpawnEffectsCreatorFiles();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\deprecated\DataReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */