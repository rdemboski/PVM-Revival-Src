package com.funcom.rpgengine2.loader;

import com.funcom.commons.dfx.DireEffectResourceLoader;
import java.util.List;
import java.util.Map;

public interface DataRecords {
  List<String[]> getItemsFiles();
  
  List<String[]> getPetFiles();
  
  List<String[]> getMonstersFiles();
  
  List<String[]> getShapesFiles();
  
  List<String[]> getRectShapesFiles();
  
  List<String[]> getElementsFiles();
  
  List<String[]> getEffectCreatorsFiles();
  
  List<String[]> getSourceEffectFiltersFiles();
  
  List<String[]> getTargetEffectFiltersFiles();
  
  List<String[]> getLevelStatsFiles();
  
  List<String[]> getStatModifierFiles();
  
  List<String[]> getBuffFiles();
  
  List<String[]> getDebuffFiles();
  
  List<String[]> getProjectileCreatorsFiles();
  
  List<String[]> getTargetedEffectCreatorsFiles();
  
  List<String[]> getConfigFiles();
  
  List<String[]> getLootDescriptionFiles();
  
  List<String[]> getGroupLootDescriptionFiles();
  
  List<String[]> getLootMobTypes();
  
  List<String[]> getMonsterLootGroups();
  
  List<String[]> getVendors();
  
  List<String[]> getVendorItems();
  
  List<String[]> getQuests();
  
  List<String[]> getQuestRewards();
  
  List<String[]> getQuestObjectives();
  
  List<String[]> getDebuffCureFiles();
  
  List<String[]> getStartUpEquipment();
  
  List<String[]> getProjectilePaths();
  
  List<String[]> getPickUpItemsFiles();
  
  DireEffectResourceLoader getDFXResourceLoader();
  
  List<String[]> getMovementManipulatorCreatorsFiles();
  
  List<String[]> getWaypointFiles();
  
  List<String[]> getWaypointDestinationPortals();
  
  List<String[]> getCheckpointDescriptions();
  
  Map<String, List<String[]>> getAiEventFiles();
  
  List<String[]> getSpawnEffectsCreatorFiles();
  
  List<String[]> getPortkeys();
  
  List<String[]> getCustomPortals();
  
  List<String[]> getItemSetsFiles();
  
  List<String[]> getChargedAbilityFiles();
  
  List<String[]> getProjectileReflectionFiles();
  
  List<String[]> getRemoveInfiniteBuffAbilitiesFiles();
  
  List<String[]> getRegionDescriptionFiles();
  
  List<String[]> getCastTimeFiles();
  
  List<String[]> getSpeachFiles();
  
  List<String[]> getSpeachMappingFiles();
  
  List<String[]> getItemSetModifierFiles();
  
  List<String[]> getGivePetAbilitiesFiles();
  
  List<String[]> getArchtypeFiles();
  
  List<String[]> getPetArchtypeFiles();
  
  List<String[]> getEquipmentFiles();
  
  List<String[]> getPetLevelStatsFiles();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\DataRecords.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */