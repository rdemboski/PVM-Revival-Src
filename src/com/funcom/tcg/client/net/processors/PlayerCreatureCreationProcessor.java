/*     */ package com.funcom.tcg.client.net.processors;
import com.funcom.commons.jme.TcgFont3D;
/*     */ import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.MonsterMouseOver;
/*     */ import com.funcom.tcg.client.actions.PlayerMouseOver;
/*     */ import com.funcom.tcg.client.controllers.InterpolationController;
/*     */ import com.funcom.tcg.client.model.MonsterActionHandler;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.model.rpg.EquipedItemDfxHandler;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.MonsterBuilder;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.PlayerActionHandler;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.PlayerCreatureCreationLMToken;
/*     */ import com.funcom.tcg.client.state.ClientPlayerPetDfxHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.client.view.modular.PetEventListener;
/*     */ import com.funcom.tcg.client.view.modular.PlayerModularDescription;
/*     */ import com.funcom.tcg.net.message.PlayerCreatureCreationMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.funcom.tcg.rpg.InventoryItem;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
import com.jme.scene.Spatial;
/*     */ import com.jmex.font3d.Font3D;
/*     */ import com.jmex.font3d.Text3D;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PlayerCreatureCreationProcessor extends CreatureCreationProcessor implements MessageProcessor {
/*  55 */   public static final ColorRGBA PLAYER_TINT_COLOR = new ColorRGBA(0.35F, 0.35F, 0.35F, 1.0F);
/*     */   
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  58 */     PlayerCreatureCreationMessage playerCreatureCreationMessage = (PlayerCreatureCreationMessage)message;
/*     */     
/*  60 */     if (LoadingManager.USE) {
/*  61 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new PlayerCreatureCreationLMToken(playerCreatureCreationMessage, ioHandler, playerDataMap, unknownPlayerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*     */     }
/*     */     else {
/*     */       
/*  65 */       removeCorpse(playerCreatureCreationMessage.getCreatureId());
/*     */       
/*  67 */       WorldCoordinate messageCoordinate = reconstructMessageCoordinate(playerCreatureCreationMessage);
/*     */       
/*  69 */       ClientPlayer otherPlayer = new ClientPlayer(playerCreatureCreationMessage.getName(), messageCoordinate, null, 0.6000000238418579D);
/*  70 */       otherPlayer.setStat(Short.valueOf((short)10), new Stat(Short.valueOf((short)10), Stat.floatToInt(5.0F)));
/*  71 */       otherPlayer.setStat(Short.valueOf((short)23), new Stat(Short.valueOf((short)23), Stat.floatToInt(200.0F)));
/*  72 */       otherPlayer.setId(playerCreatureCreationMessage.getCreatureId());
/*  73 */       otherPlayer.setName(playerCreatureCreationMessage.getName());
/*  74 */       otherPlayer.setPosition(messageCoordinate);
/*  75 */       otherPlayer.setRotation(playerCreatureCreationMessage.getAngle());
/*  76 */       otherPlayer.setPlayerDescription(playerCreatureCreationMessage.getPlayerDescription());
/*  77 */       otherPlayer.setExternalChatId(playerCreatureCreationMessage.getExternalChatId());
/*     */       
/*  79 */       PropNode propNode = new PropNode((Prop)otherPlayer, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */       
/*  81 */       Faction faction = playerCreatureCreationMessage.getFaction();
/*  82 */       boolean hostile = false;
/*  83 */       if (faction.equivalentTo(BaseFaction.MONSTER) || faction.equivalentTo(BaseFaction.FREE_FOR_ALL) || (faction.equivalentTo(BaseFaction.PLAYER) && !faction.equivalentTo(MainGameState.getPlayerModel().getFaction()))) {
/*     */         
/*  85 */         MonsterMouseOver monsterMouseOver = new MonsterMouseOver(MainGameState.getMouseCursorSetter(), MonsterBuilder.MONSTER_TINT_COLOR);
/*  86 */         monsterMouseOver.setOwnerPropNode(propNode);
/*  87 */         MonsterActionHandler actionHandler = new MonsterActionHandler((Creature)otherPlayer, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)monsterMouseOver);
/*  88 */         propNode.setActionHandler((UserActionHandler)actionHandler);
/*  89 */         hostile = true;
/*  90 */       } else if (faction.equivalentTo(BaseFaction.NO_FIGHT) || faction.equivalentTo(MainGameState.getPlayerModel().getFaction())) {
/*  91 */         PlayerMouseOver playerMouseOver = new PlayerMouseOver(MainGameState.getMouseCursorSetter(), PLAYER_TINT_COLOR);
/*  92 */         playerMouseOver.setOwnerPropNode(propNode);
/*  93 */         propNode.setActionHandler((UserActionHandler)new PlayerActionHandler(otherPlayer, (MouseOver)playerMouseOver));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  98 */       PlayerModularDescription playerModularDescription = new PlayerModularDescription(otherPlayer, TcgGame.getVisualRegistry());
/*  99 */       ClientDescribedModularNode playerNode = new ClientDescribedModularNode((ModularDescription)playerModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */       
/* 102 */       playerNode.reloadCharacter();
/*     */       
/* 104 */       propNode.attachRepresentation((Spatial)playerNode);
/* 105 */       SpatialUtils.addShadow(propNode, TcgGame.getResourceManager());
/*     */       
/* 107 */       otherPlayer.addPlayerEventsListener((PlayerEventsListener)new PetEventListener(playerNode));
/* 108 */       propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/* 109 */       otherPlayer.addPlayerEventsListener((PlayerEventsListener)new ClientPlayerPetDfxHandler(otherPlayer, propNode));
/* 110 */       attachName(playerCreatureCreationMessage.getName(), propNode, hostile);
/*     */       
/* 112 */       otherPlayer.getEquipDoll().addChangeListener((EquipChangeListener)new EquipedItemDfxHandler(propNode));
/* 113 */       for (InventoryItem inventoryItem : playerCreatureCreationMessage.getInventoryItems()) {
/* 114 */         ClientItem item = MainGameState.getItemRegistry().getItemForClassID(inventoryItem.getItemId(), inventoryItem.getTier());
/* 115 */         otherPlayer.getEquipDoll().setItem(inventoryItem.getSlotId(), item);
/*     */       } 
/*     */       
/* 118 */       String activePetClassId = playerCreatureCreationMessage.getActivePetClassId();
/* 119 */       if (!"".equals(activePetClassId)) {
/* 120 */         ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(activePetClassId);
/* 121 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(activePetClassId + "-upgrade");
/* 122 */         ClientPet activePet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 123 */         activePet.setLevel(playerCreatureCreationMessage.getActivePetLevel());
/* 124 */         otherPlayer.setActivePet(activePet);
/*     */       } 
/*     */       
/* 127 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 128 */       TcgGame.getPropNodeRegister().addPropNode(propNode);
/* 129 */       propNode.updateRenderState();
/*     */       
/* 131 */       CreatureData creatureData = new CreatureData(messageCoordinate, playerCreatureCreationMessage.getAngle());
/*     */       
/* 133 */       Map<Short, Integer> map = playerCreatureCreationMessage.getStats();
/* 134 */       Set<Stat> stats = new HashSet<Stat>();
/* 135 */       for (Map.Entry<Short, Integer> entry : map.entrySet()) {
/* 136 */         Stat stat = new Stat(entry.getKey(), ((Integer)entry.getValue()).intValue());
/* 137 */         stats.add(stat);
/*     */       } 
/* 139 */       creatureData.updateStats(stats);
/*     */       
/* 141 */       CreatureData data = unknownPlayerDataMap.remove(Integer.valueOf(playerCreatureCreationMessage.getCreatureId()));
/* 142 */       if (data != null)
/* 143 */         creatureData.getNetworkCoord().add(data.getNetworkCoord()); 
/* 144 */       playerDataMap.put(Integer.valueOf(playerCreatureCreationMessage.getCreatureId()), creatureData);
/*     */ 
/*     */       
/* 147 */       InterpolationController cont = new InterpolationController(propNode);
/* 148 */       cont.setPosition(messageCoordinate);
/* 149 */       cont.setAngle(playerCreatureCreationMessage.getAngle());
/* 150 */       propNode.addController((Controller)cont);
/*     */     } 
/*     */   }
/*     */   
/*     */   private WorldCoordinate reconstructMessageCoordinate(PlayerCreatureCreationMessage playerCreatureCreationMessage) {
/* 155 */     WorldCoordinate worldCoordinate = playerCreatureCreationMessage.getCoord();
/* 156 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 157 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 158 */     return worldCoordinate;
/*     */   }
/*     */   
/*     */   private void removeCorpse(int creatureId) {
/* 162 */     PropNodeRegister register = TcgGame.getPropNodeRegister();
/* 163 */     PropNode propNode = register.getPropNode(Integer.valueOf(creatureId));
/*     */     
/* 165 */     if (propNode != null) {
/* 166 */       register.removePropNode(propNode);
/* 167 */       propNode.removeFromParent();
/* 168 */       propNode.getEffects().removeAllParticles();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void attachName(String name, PropNode propNode, boolean hostile) {
/* 175 */     Text3D text = new Text3D(TcgFont3D.getFont(), name, 0.15F)
/*     */       {
/*     */         public void updateWorldBound() {}
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 182 */     text.setFontColor(hostile ? ColorRGBA.red : ColorRGBA.white);
/* 183 */     text.setLocalTranslation(-text.getWidth() / 2.0F, 0.0F, 0.0F);
/* 184 */     propNode.setText(text);
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 188 */     return 28;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PlayerCreatureCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */