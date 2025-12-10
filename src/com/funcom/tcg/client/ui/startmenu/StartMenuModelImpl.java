/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.rpgengine2.startupequipment.StartUpEquipmentDescription;
/*     */ import com.funcom.rpgengine2.startupequipment.StartUpEquipmentManager;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItemVisual;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.PlayerVisualFactory;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import com.funcom.tcg.net.StartingPet;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionType;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionVisual;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class StartMenuModelImpl implements StartMenuModel {
/*     */   private Collection<ClientDescriptionVisual> hairClassIds;
/*     */   private Collection<ClientDescriptionVisual> hairColorList;
/*     */   private Collection<ClientDescriptionVisual> faceList;
/*     */   private Collection<ItemDescription> torsoList;
/*     */   private Collection<ItemDescription> legList;
/*     */   private StartingPet startingPet;
/*     */   private final CharacterCreationModularDescription modularDescription;
/*     */   private final PetRegistry petRegistry;
/*     */   
/*     */   public StartMenuModelImpl(ResourceManager resourceManager, VisualRegistry visualRegistry, StartUpEquipmentManager startUpManager) {
/*  41 */     Map<String, ClientDescriptionVisual> hairVisuals = visualRegistry.getDescriptionVisualsForType(ClientDescriptionType.HAIR);
/*  42 */     this.hairClassIds = new LinkedHashSet<ClientDescriptionVisual>();
/*  43 */     Map<String, ClientDescriptionVisual> hairCollection = new HashMap<String, ClientDescriptionVisual>();
/*  44 */     for (Map.Entry<String, ClientDescriptionVisual> hairEntry : hairVisuals.entrySet()) {
/*  45 */       ClientDescriptionVisual visual = hairEntry.getValue();
/*     */       
/*  47 */       hairCollection.put(parseHairId(visual), visual);
/*     */     } 
/*  49 */     this.hairClassIds.addAll(hairCollection.values());
/*     */     
/*  51 */     Map<String, ClientDescriptionVisual> hairColorVisuals = visualRegistry.getDescriptionVisualsForType(ClientDescriptionType.HAIRCOLOR);
/*  52 */     this.hairColorList = new LinkedHashSet<ClientDescriptionVisual>();
/*  53 */     for (ClientDescriptionVisual hairVisual : hairColorVisuals.values()) {
/*  54 */       this.hairColorList.add(hairVisual);
/*     */     }
/*     */     
/*  57 */     Map<String, ClientDescriptionVisual> faceVisuals = visualRegistry.getDescriptionVisualsForType(ClientDescriptionType.FACE);
/*  58 */     this.faceList = new LinkedHashSet<ClientDescriptionVisual>();
/*  59 */     for (ClientDescriptionVisual faceVisual : faceVisuals.values()) {
/*  60 */       this.faceList.add(faceVisual);
/*     */     }
/*     */     
/*  63 */     this.torsoList = new LinkedList<ItemDescription>();
/*  64 */     this.legList = new LinkedList<ItemDescription>();
/*     */     
/*  66 */     this.startUpEquipmentDescription = startUpManager.getDescriptionById("default");
/*  67 */     addEquipments(this.startUpEquipmentDescription.getAllEquipedItems());
/*     */ 
/*     */     
/*  70 */     ItemRegistry itemRegistry = null;
/*  71 */     if (MainGameState.getInstance() != null) {
/*  72 */       itemRegistry = MainGameState.getItemRegistry();
/*     */     }
/*  74 */     this.petRegistry = new PetRegistry(resourceManager, visualRegistry, itemRegistry);
/*  75 */     this.petRegistry.readPetDescriptionData();
/*     */     
/*  77 */     this.playerDescription = new PlayerDescription();
/*  78 */     this.playerDescription.setGender(PlayerDescription.Gender.MALE);
/*  79 */     this.playerDescription.setHairId(parseHairId(this.hairClassIds.iterator().next()));
/*  80 */     this.playerDescription.setHairColorId(((ClientDescriptionVisual)this.hairColorList.iterator().next()).getClassId());
/*  81 */     this.playerDescription.setFaceId(((ClientDescriptionVisual)this.faceList.iterator().next()).getClassId());
/*  82 */     this.playerDescription.setSkinColorId("pale");
/*  83 */     this.playerDescription.setEyeColorId("blue");
/*     */     
/*  85 */     this.torsoItemDescription = this.torsoList.iterator().next();
/*  86 */     this.legsItemDescription = this.legList.iterator().next();
/*     */     
/*  88 */     PlayerVisualFactory creationVisualFactory = new CreationPlayerVisualFactory(visualRegistry);
/*  89 */     this.modularDescription = new CharacterCreationModularDescription(this.playerDescription, visualRegistry, creationVisualFactory);
/*     */     
/*  91 */     int randomStartPet = (new Random()).nextInt((StartingPet.values()).length);
/*  92 */     setStartingPet(StartingPet.valueOfById((byte)randomStartPet));
/*     */   }
/*     */   private final PlayerDescription playerDescription; private ItemDescription torsoItemDescription; private ItemDescription legsItemDescription; private String charactersName; private String password; private String parentsEmail; private StartUpEquipmentDescription startUpEquipmentDescription; private PlayerStartConfig startConfig;
/*     */   private String parseHairId(ClientDescriptionVisual visual) {
/*  96 */     String hairClassId = visual.getClassId();
/*  97 */     String[] parts = hairClassId.split("-");
/*  98 */     return parts[0];
/*     */   }
/*     */   
/*     */   private void addEquipments(List<ItemDescription> equipedItemDescriptions) {
/* 102 */     for (ItemDescription equipedItemDescription : equipedItemDescriptions) {
/* 103 */       if (equipedItemDescription.getItemType() == ItemType.EQUIP_TORSO) {
/* 104 */         this.torsoList.add(equipedItemDescription); continue;
/* 105 */       }  if (equipedItemDescription.getItemType() == ItemType.EQUIP_LEGS) {
/* 106 */         this.legList.add(equipedItemDescription);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ClientDescriptionVisual> getHairList() {
/* 113 */     return this.hairClassIds;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ClientDescriptionVisual> getHairColorList() {
/* 118 */     return this.hairColorList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ClientDescriptionVisual> getFaceList() {
/* 123 */     return this.faceList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ItemDescription> getTorsoList() {
/* 128 */     return this.torsoList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ItemDescription> getLegList() {
/* 133 */     return this.legList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGender(PlayerDescription.Gender gender) {
/* 138 */     this.playerDescription.setGender(gender);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartingPet(StartingPet startingPet) {
/* 143 */     if (this.startingPet != startingPet) {
/*     */       
/* 145 */       this.startingPet = startingPet;
/*     */       
/* 147 */       Map<String, StartUpEquipmentDescription.StartUpPet> defaultPets = this.startUpEquipmentDescription.getPets();
/* 148 */       Collection<StartUpEquipmentDescription.StartUpPet> startUpPets = defaultPets.values();
/*     */       
/* 150 */       Collection<StartUpEquipmentDescription.StartUpPet> selectedPets = PlayerStartConfig.getStartupPets(startingPet, startUpPets);
/*     */ 
/*     */       
/* 153 */       String petId = ((StartUpEquipmentDescription.StartUpPet)selectedPets.iterator().next()).getPetDescription().getId();
/*     */       
/* 155 */       ClientPetDescription pet = this.petRegistry.getPetForClassId(petId);
/*     */       
/* 157 */       this.modularDescription.setActivePet(pet);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHair(ClientDescriptionVisual hair) {
/* 163 */     this.playerDescription.setHairId(parseHairId(hair));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHairColor(ClientDescriptionVisual hairColor) {
/* 168 */     this.playerDescription.setHairColorId(hairColor.getClassId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFace(ClientDescriptionVisual face) {
/* 173 */     this.playerDescription.setFaceId(face.getClassId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTorso(ItemDescription torsoItemDescription) {
/* 178 */     this.torsoItemDescription = torsoItemDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemDescription getTorso() {
/* 183 */     return this.torsoItemDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLegs(ItemDescription legsItemDescription) {
/* 188 */     this.legsItemDescription = legsItemDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemDescription getLegs() {
/* 193 */     return this.legsItemDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerDescription.Gender getGender() {
/* 198 */     return this.playerDescription.getGender();
/*     */   }
/*     */ 
/*     */   
/*     */   public StartingPet getStartingPet() {
/* 203 */     return this.startingPet;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModularDescription getModularDescription() {
/* 208 */     return (ModularDescription)this.modularDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreatureVisualDescription getPetVisuals() {
/* 213 */     return this.modularDescription.getActivePet().getPetVisuals();
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerDescription getPlayerDescription() {
/* 218 */     return this.playerDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerStartConfig makeStartConfig() {
/* 223 */     return new PlayerStartConfig(getPlayerDescription(), getStartingPet(), getTorso().getSkillId(), getLegs().getSkillId(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerStartConfig getStartConfig() {
/* 231 */     return this.startConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerStartConfig(PlayerStartConfig config) {
/* 236 */     this.startConfig = config;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentsEmail(String parentsEmail) {
/* 241 */     this.parentsEmail = parentsEmail;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParentsEmail() {
/* 246 */     return this.parentsEmail;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPassword(String password) {
/* 251 */     this.password = password;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 256 */     return this.password;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPasswordHash() {
/* 261 */     return StringUtils.hashString(this.password);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharactersName(String charactersName) {
/* 266 */     this.charactersName = charactersName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCharactersName() {
/* 271 */     return this.charactersName;
/*     */   }
/*     */   
/*     */   public static class TestPart implements ModularDescription.Part {
/*     */     private String partName;
/*     */     
/*     */     public TestPart(String partName) {
/* 278 */       this.partName = partName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPartName() {
/* 283 */       return this.partName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isVisible() {
/* 288 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMeshPath() {
/* 293 */       return "";
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ModularDescription.TexturePart> getTextureParts() {
/* 298 */       return new LinkedList<ModularDescription.TexturePart>();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TestTexturePart implements ModularDescription.TexturePart {
/*     */     private String textureMap;
/*     */     
/*     */     public TestTexturePart(String textureMap) {
/* 306 */       this.textureMap = textureMap;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTextureMap() {
/* 311 */       return this.textureMap;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTransparent() {
/* 316 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextureLayerCount() {
/* 321 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/* 326 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getTextureLayers() {
/* 331 */       return new LinkedList<String>();
/*     */     }
/*     */   }
/*     */   
/*     */   private class CreationPlayerVisualFactory
/*     */     implements PlayerVisualFactory {
/*     */     private final VisualRegistry visualRegistry;
/*     */     
/*     */     public CreationPlayerVisualFactory(VisualRegistry visualRegistry) {
/* 340 */       this.visualRegistry = visualRegistry;
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientItemVisual getItemVisual(ItemType itemType) {
/* 345 */       switch (itemType) {
/*     */         case EQUIP_TORSO:
/* 347 */           return this.visualRegistry.getItemVisualForClassID(StartMenuModelImpl.this.torsoItemDescription.getVisualId());
/*     */         
/*     */         case EQUIP_LEGS:
/* 350 */           return this.visualRegistry.getItemVisualForClassID(StartMenuModelImpl.this.legsItemDescription.getVisualId());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 356 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\StartMenuModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */