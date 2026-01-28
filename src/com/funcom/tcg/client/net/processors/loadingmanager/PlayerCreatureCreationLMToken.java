/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
import com.funcom.commons.jme.TcgFont3D;
/*     */ import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.server.common.GameIOHandler;
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
import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.model.rpg.EquipedItemDfxHandler;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.MonsterBuilder;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.PlayerActionHandler;
/*     */ import com.funcom.tcg.client.state.ClientPlayerPetDfxHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.client.view.modular.PetEventListener;
/*     */ import com.funcom.tcg.client.view.modular.PlayerModularDescription;
/*     */ import com.funcom.tcg.net.Friend;
/*     */ import com.funcom.tcg.net.message.PlayerCreatureCreationMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.funcom.tcg.rpg.InventoryItem;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
import com.jme.scene.Spatial;
/*     */ import com.jmex.font3d.Font3D;
/*     */ import com.jmex.font3d.Text3D;

import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PlayerCreatureCreationLMToken extends LoadingManagerToken {
/*  48 */   private static final ColorRGBA PLAYER_TINT_COLOR = new ColorRGBA(0.35F, 0.35F, 0.35F, 1.0F);
/*     */   
/*  50 */   PlayerCreatureCreationMessage playerCreatureCreationMessage = null;
/*  51 */   GameIOHandler ioHandler = null;
/*  52 */   Map<Integer, CreatureData> playerDataMap = null;
/*  53 */   Map<Integer, CreatureData> unknownPlayerDataMap = null;
/*     */ 
/*     */   
/*     */   public PlayerCreatureCreationLMToken(PlayerCreatureCreationMessage playerCreatureCreationMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  57 */     this.playerCreatureCreationMessage = playerCreatureCreationMessage;
/*  58 */     this.ioHandler = ioHandler;
/*  59 */     this.playerDataMap = playerDataMap;
/*  60 */     this.unknownPlayerDataMap = unknownPlayerDataMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  66 */     removeCorpse(this.playerCreatureCreationMessage.getCreatureId());
/*     */     
/*  68 */     WorldCoordinate messageCoordinate = reconstructMessageCoordinate(this.playerCreatureCreationMessage);
/*     */     
/*  70 */     ClientPlayer otherPlayer = new ClientPlayer(this.playerCreatureCreationMessage.getName(), messageCoordinate, null, 0.6000000238418579D);
/*  71 */     otherPlayer.setStat(Short.valueOf((short)10), new Stat(Short.valueOf((short)10), Stat.floatToInt(5.0F)));
/*  72 */     otherPlayer.setStat(Short.valueOf((short)23), new Stat(Short.valueOf((short)23), Stat.floatToInt(200.0F)));
/*  73 */     otherPlayer.setId(this.playerCreatureCreationMessage.getCreatureId());
/*  74 */     otherPlayer.setName(this.playerCreatureCreationMessage.getName());
/*  75 */     otherPlayer.setPosition(messageCoordinate);
/*  76 */     otherPlayer.setRotation(this.playerCreatureCreationMessage.getAngle());
/*  77 */     otherPlayer.setPlayerDescription(this.playerCreatureCreationMessage.getPlayerDescription());
/*  78 */     otherPlayer.setExternalChatId(this.playerCreatureCreationMessage.getExternalChatId());
/*     */     
/*  80 */     PropNode propNode = new PropNode((Prop)otherPlayer, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */     
/*  82 */     boolean hostile = false;
/*  83 */     boolean friend = false;
/*  84 */     if (MainGameState.getFriendModel().getFriendsList().containsKey(Integer.valueOf(otherPlayer.getExternalChatId()))) {
/*  85 */       Friend friend1 = (Friend)MainGameState.getFriendModel().getFriendsList().get(Integer.valueOf(otherPlayer.getExternalChatId()));
/*  86 */       if (friend1 != null && !friend1.isBlocked().booleanValue()) {
/*  87 */         friend = true;
/*     */       }
/*     */     } 
/*  90 */     Faction faction = this.playerCreatureCreationMessage.getFaction();
/*  91 */     if (faction.equivalentTo(BaseFaction.MONSTER) || faction.equivalentTo(BaseFaction.FREE_FOR_ALL) || (faction.equivalentTo(BaseFaction.PLAYER) && !faction.equivalentTo(MainGameState.getPlayerModel().getFaction()))) {
/*     */       
/*  93 */       MonsterMouseOver monsterMouseOver = new MonsterMouseOver(MainGameState.getMouseCursorSetter(), MonsterBuilder.MONSTER_TINT_COLOR);
/*  94 */       monsterMouseOver.setOwnerPropNode(propNode);
/*  95 */       MonsterActionHandler actionHandler = new MonsterActionHandler((Creature)otherPlayer, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)monsterMouseOver);
/*  96 */       propNode.setActionHandler((UserActionHandler)actionHandler);
/*  97 */       hostile = true;
/*  98 */     } else if (faction.equivalentTo(BaseFaction.NO_FIGHT) || faction.equivalentTo(MainGameState.getPlayerModel().getFaction())) {
/*  99 */       PlayerMouseOver playerMouseOver = new PlayerMouseOver(MainGameState.getMouseCursorSetter(), PLAYER_TINT_COLOR);
/* 100 */       playerMouseOver.setOwnerPropNode(propNode);
/* 101 */       propNode.setActionHandler((UserActionHandler)new PlayerActionHandler(otherPlayer, (MouseOver)playerMouseOver));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     PlayerModularDescription playerModularDescription = new PlayerModularDescription(otherPlayer, TcgGame.getVisualRegistry());
/* 107 */     ClientDescribedModularNode playerNode = new ClientDescribedModularNode((ModularDescription)playerModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */     
/* 110 */     playerNode.reloadCharacter();
/*     */     
/* 112 */     propNode.attachRepresentation((Spatial)playerNode);
/* 113 */     SpatialUtils.addShadow(propNode, TcgGame.getResourceManager());
/*     */     
/* 115 */     otherPlayer.addPlayerEventsListener((PlayerEventsListener)new PetEventListener(playerNode));
/* 116 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/* 117 */     otherPlayer.addPlayerEventsListener((PlayerEventsListener)new ClientPlayerPetDfxHandler(otherPlayer, propNode));
/*     */     
/* 119 */     String name = this.playerCreatureCreationMessage.getName();
/* 120 */     if (!this.playerCreatureCreationMessage.getFaction().equals(BaseFaction.NO_FIGHT)) {
/* 121 */       attachName(name, propNode, hostile, otherPlayer.isSubscriber(), friend);
/*     */     }
/*     */     
/* 124 */     otherPlayer.getEquipDoll().addChangeListener((EquipChangeListener)new EquipedItemDfxHandler(propNode));
/* 125 */     for (InventoryItem inventoryItem : this.playerCreatureCreationMessage.getInventoryItems()) {
/* 126 */       ClientItem item = MainGameState.getItemRegistry().getItemForClassID(inventoryItem.getItemId(), inventoryItem.getTier());
/* 127 */       otherPlayer.getEquipDoll().setItem(inventoryItem.getSlotId(), item);
/*     */     } 
/*     */     
/* 130 */     String activePetClassId = this.playerCreatureCreationMessage.getActivePetClassId();
/* 131 */     if (!"".equals(activePetClassId)) {
/* 132 */       ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(activePetClassId);
/* 133 */       ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(activePetClassId + "-upgrade");
/* 134 */       ClientPet activePet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 135 */       activePet.setLevel(this.playerCreatureCreationMessage.getActivePetLevel());
/* 136 */       otherPlayer.setActivePet(activePet);
/*     */     } 
/*     */     
/* 139 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 140 */     TcgGame.getPropNodeRegister().addPropNode(propNode);
/* 141 */     propNode.updateRenderState();
/*     */     
/* 143 */     CreatureData creatureData = new CreatureData(messageCoordinate, this.playerCreatureCreationMessage.getAngle());
/*     */     
/* 145 */     Map<Short, Integer> map = this.playerCreatureCreationMessage.getStats();
/* 146 */     Set<Stat> stats = new HashSet<Stat>();
/* 147 */     for (Map.Entry<Short, Integer> entry : map.entrySet()) {
/* 148 */       Stat stat = new Stat(entry.getKey(), ((Integer)entry.getValue()).intValue());
/* 149 */       stats.add(stat);
/*     */     } 
/* 151 */     creatureData.updateStats(stats);
/*     */     
/* 153 */     CreatureData data = this.unknownPlayerDataMap.remove(Integer.valueOf(this.playerCreatureCreationMessage.getCreatureId()));
/* 154 */     if (data != null)
/* 155 */       creatureData.getNetworkCoord().add(data.getNetworkCoord()); 
/* 156 */     this.playerDataMap.put(Integer.valueOf(this.playerCreatureCreationMessage.getCreatureId()), creatureData);
/*     */ 
/*     */     
/* 159 */     InterpolationController cont = new InterpolationController(propNode);
/* 160 */     cont.setPosition(messageCoordinate);
/* 161 */     cont.setAngle(this.playerCreatureCreationMessage.getAngle());
/* 162 */     propNode.addController((Controller)cont);
/*     */     
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   private WorldCoordinate reconstructMessageCoordinate(PlayerCreatureCreationMessage playerCreatureCreationMessage) {
/* 168 */     WorldCoordinate worldCoordinate = playerCreatureCreationMessage.getCoord();
/* 169 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 170 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 171 */     return worldCoordinate;
/*     */   }
/*     */   
/*     */   private void removeCorpse(int creatureId) {
/* 175 */     PropNodeRegister register = TcgGame.getPropNodeRegister();
/* 176 */     PropNode propNode = register.getPropNode(Integer.valueOf(creatureId));
/*     */     
/* 178 */     if (propNode != null) {
/* 179 */       register.removePropNode(propNode);
/* 180 */       propNode.removeFromParent();
/* 181 */       propNode.getEffects().removeAllParticles();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void attachName(String name, PropNode propNode, boolean hostile, boolean member, boolean friend) {
/* 187 */     Text3D text = new Text3D(TcgFont3D.getFont(), name, 0.15F)
/*     */       {
/*     */         public void updateWorldBound() {}
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 194 */     ColorRGBA textColor = ColorRGBA.white;
/*     */     
/* 196 */     if (member) {
/* 197 */       textColor = ColorRGBA.orange;
/*     */     }
/*     */     
/* 200 */     if (friend) {
/* 201 */       textColor = ColorRGBA.green;
/*     */     }
/*     */     
/* 204 */     if (hostile) {
/* 205 */       textColor = ColorRGBA.red;
/*     */     }
/*     */ 
/*     */     
/* 209 */     text.setFontColor(member ? ColorRGBA.orange : (hostile ? ColorRGBA.red : ColorRGBA.white));
/* 210 */     text.setLocalTranslation(-text.getWidth() / 2.0F, 0.0F, 0.0F);
/* 211 */     propNode.setText(text);
/* 212 */     propNode.setTextColor(member ? ColorRGBA.orange : (hostile ? ColorRGBA.red : ColorRGBA.white));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PlayerCreatureCreationLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */