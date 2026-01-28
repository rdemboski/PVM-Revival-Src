/*     */ package com.funcom.tcg.client.net;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.controllers.InterpolationController;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.net.processors.AccessKeysProcessor;
import com.funcom.tcg.client.net.processors.AccountRegisterResponseProcessor;
import com.funcom.tcg.client.net.processors.AccountSubscribeTokenResponseProcessor;
import com.funcom.tcg.client.net.processors.ActivateLoadingScreenProcessor;
import com.funcom.tcg.client.net.processors.ActivatePortalProcessor;
import com.funcom.tcg.client.net.processors.ActivateWaypointDestianationportalProcessor;
/*     */ import com.funcom.tcg.client.net.processors.ActivateWaypointProcessor;
/*     */ import com.funcom.tcg.client.net.processors.ActivePetUpdateProcessor;
import com.funcom.tcg.client.net.processors.AutoUseItemProcessor;
/*     */ import com.funcom.tcg.client.net.processors.CheckpointActivatedProcessor;
import com.funcom.tcg.client.net.processors.ClientPausedProcessor;
import com.funcom.tcg.client.net.processors.CreatureCreationProcessor;
import com.funcom.tcg.client.net.processors.CreatureDeletionProcessor;
import com.funcom.tcg.client.net.processors.CritMessageProcessor;
import com.funcom.tcg.client.net.processors.DFXExecuteListProcessor;
/*     */ import com.funcom.tcg.client.net.processors.DFXExecuteProcessor;
import com.funcom.tcg.client.net.processors.DFXKillProcessor;
import com.funcom.tcg.client.net.processors.DFXServerNoticeProcessor;
import com.funcom.tcg.client.net.processors.DailyQuestUpdatedNumberProcessor;
import com.funcom.tcg.client.net.processors.DebugAttackShapeProcessor;
import com.funcom.tcg.client.net.processors.DebugSquareAttackShapeProcessor;
import com.funcom.tcg.client.net.processors.DiedProcessor;
import com.funcom.tcg.client.net.processors.DuelFinishProcessor;
import com.funcom.tcg.client.net.processors.DuelInvitationProcessor;
import com.funcom.tcg.client.net.processors.DuelRejectionProcessor;
import com.funcom.tcg.client.net.processors.DuelStartProcessor;
import com.funcom.tcg.client.net.processors.DynamicObjectsListProcessor;
import com.funcom.tcg.client.net.processors.EquipmentChangedProcessor;
import com.funcom.tcg.client.net.processors.ErrorProcessor;
import com.funcom.tcg.client.net.processors.FactionChangedProcessor;
import com.funcom.tcg.client.net.processors.FriendsMapProcessor;
import com.funcom.tcg.client.net.processors.GiftBoxUpdateProcessor;
/*     */ import com.funcom.tcg.client.net.processors.ImmunityEffectProcessor;
import com.funcom.tcg.client.net.processors.InternalChatProcessor;
import com.funcom.tcg.client.net.processors.LoginFinishedProcessor;
import com.funcom.tcg.client.net.processors.LoginResponseProcessor;
/*     */ import com.funcom.tcg.client.net.processors.LootCreationProcessor;
import com.funcom.tcg.client.net.processors.MapChangedProcessor;
import com.funcom.tcg.client.net.processors.NewItemCollectedProcessor;
import com.funcom.tcg.client.net.processors.NewPetCollectedProcessor;
import com.funcom.tcg.client.net.processors.NotifyPetSelectedAndActivatedProcessor;
/*     */ import com.funcom.tcg.client.net.processors.OnlineNotificationProcessor;
import com.funcom.tcg.client.net.processors.PauseRejectedProcessor;
import com.funcom.tcg.client.net.processors.PetTrialFinishedProcessor;
import com.funcom.tcg.client.net.processors.PetTrialStartedProcessor;
import com.funcom.tcg.client.net.processors.PickUpLootConvetedProcessor;
import com.funcom.tcg.client.net.processors.PickUpLootCreationProcessor;
import com.funcom.tcg.client.net.processors.PlayerCreatureCreationProcessor;
import com.funcom.tcg.client.net.processors.PositionUpdateListProcessor;
/*     */ import com.funcom.tcg.client.net.processors.PositionUpdateProcessor;
import com.funcom.tcg.client.net.processors.ProjectileUpdateProcessor;
import com.funcom.tcg.client.net.processors.PropRemovalProcesssor;
import com.funcom.tcg.client.net.processors.QuestCompletedListProcessor;
import com.funcom.tcg.client.net.processors.QuestCompletedProcessor;
import com.funcom.tcg.client.net.processors.QuestCreationProcessor;
import com.funcom.tcg.client.net.processors.QuestObjectiveUpdateProcessor;
import com.funcom.tcg.client.net.processors.QuestsAbandonedProcessor;
import com.funcom.tcg.client.net.processors.ReconnetChatProcessor;
import com.funcom.tcg.client.net.processors.RefreshPlayerDataProcessor;
import com.funcom.tcg.client.net.processors.RefreshQuestGiversProcessor;
import com.funcom.tcg.client.net.processors.ReloadDataProcessor;
import com.funcom.tcg.client.net.processors.ResetQuestsProcessor;
import com.funcom.tcg.client.net.processors.RespawnProcessor;
import com.funcom.tcg.client.net.processors.SearchPlayerByNicknameResponseProcessor;
import com.funcom.tcg.client.net.processors.SearchPlayerToTellResponseProcessor;
import com.funcom.tcg.client.net.processors.SendSanctionResponseProcessor;
import com.funcom.tcg.client.net.processors.StartTutorialProcessor;
import com.funcom.tcg.client.net.processors.StatEffectProcessor;
/*     */ import com.funcom.tcg.client.net.processors.StateUpdateProcessor;
import com.funcom.tcg.client.net.processors.SubscriptionStatusUpdateProcessor;
import com.funcom.tcg.client.net.processors.SyncInventoryListProcessor;
import com.funcom.tcg.client.net.processors.SyncInventoryProcessor;
import com.funcom.tcg.client.net.processors.TargetedEffectUpdateProcessor;
import com.funcom.tcg.client.net.processors.TcgChatFriendAcceptedProcessor;
import com.funcom.tcg.client.net.processors.TcgChatFriendRequestProcessor;
import com.funcom.tcg.client.net.processors.TcgChatProcessor;
import com.funcom.tcg.client.net.processors.TeleportObjectsListProcessor;
import com.funcom.tcg.client.net.processors.UpdateClientTownportalProcessor;
import com.funcom.tcg.client.net.processors.UpdateTargetEffectProcessor;
import com.funcom.tcg.client.net.processors.UpdateWaypointDestianationportalProcessor;
import com.funcom.tcg.client.net.processors.VendorCreationProcessor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.net.message.PositionUpdateMessage;
/*     */ import com.funcom.tcg.net.message.ResponseCollectedPetsMessage;
import com.funcom.util.SizeCheckedArrayList;
/*     */ import com.jme.scene.Controller;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class GameOperationState extends AbstractNetworkHandlerState {
/*  33 */   public static final String STATE_NAME = NotLoggedState.class.getSimpleName();
/*     */   
/*     */   private static final float BROADCAST_POS_DELAY = 0.1F;
/*     */   private static final int MEM_LEAK_WARNNIG_LIMIT = 16;
/*     */   private Map<Integer, CreatureData> creatureDataMap;
/*     */   private Map<Integer, CreatureData> playerDataMap;
/*     */   private Map<Integer, CreatureData> unknownCreatureDataMap;
/*     */   private Map<Integer, CreatureData> unknownPlayerDataMap;
/*     */   private Set<MessageProcessor> messageProcessors;
/*     */   @Deprecated
/*     */   private static Set<Inventory> inventoryUpdates;
/*     */   private List<ResponseCollectedPetsProcessor> responseCollectedPetsProcessors;
/*  45 */   private WorldCoordinate lastPosition = null;
/*  46 */   private float lastAngle = 0.0F;
/*     */   
/*     */   private float broadcastPosTimer;
/*  49 */   private long MinTime = 3000000L;
/*     */   
/*     */   public GameOperationState() {
/*  52 */     super(STATE_NAME);
/*  53 */     this.creatureDataMap = new HashMap<Integer, CreatureData>();
/*  54 */     this.playerDataMap = new HashMap<Integer, CreatureData>();
/*  55 */     this.unknownCreatureDataMap = new HashMap<Integer, CreatureData>();
/*  56 */     this.unknownPlayerDataMap = new HashMap<Integer, CreatureData>();
/*  57 */     this.messageProcessors = new HashSet<MessageProcessor>();
/*  58 */     configureMessageProcessors();
/*     */     
/*  60 */     String str = System.getProperty("Network.MinTime");
/*  61 */     if (str != null && !str.isEmpty()) {
/*  62 */       this.MinTime = (long)Float.parseFloat(str);
/*     */     }
/*     */   }
/*     */   
/*     */   private void configureMessageProcessors() {
/*  67 */     this.messageProcessors.add(new CreatureCreationProcessor());
/*  68 */     this.messageProcessors.add(new CreatureDeletionProcessor());
/*  69 */     this.messageProcessors.add(new DiedProcessor());
/*  70 */     this.messageProcessors.add(new LootCreationProcessor());
/*  71 */     this.messageProcessors.add(new PositionUpdateListProcessor());
/*  72 */     this.messageProcessors.add(new PositionUpdateProcessor());
/*  73 */     this.messageProcessors.add(new RespawnProcessor());
/*  74 */     this.messageProcessors.add(new StateUpdateProcessor());
/*  75 */     this.messageProcessors.add(new SyncInventoryListProcessor());
/*  76 */     this.messageProcessors.add(new SyncInventoryProcessor());
/*  77 */     this.messageProcessors.add(new VendorCreationProcessor());
/*  78 */     this.messageProcessors.add(new ReloadDataProcessor());
/*  79 */     this.messageProcessors.add(new MapChangedProcessor());
/*  80 */     this.messageProcessors.add(new PlayerCreatureCreationProcessor());
/*  81 */     this.messageProcessors.add(new ActivePetUpdateProcessor());
/*  82 */     this.messageProcessors.add(new EquipmentChangedProcessor());
/*  83 */     this.messageProcessors.add(new AutoUseItemProcessor());
/*  84 */     this.messageProcessors.add(new ErrorProcessor());
/*  85 */     this.messageProcessors.add(new RefreshQuestGiversProcessor());
/*  86 */     this.messageProcessors.add(new CritMessageProcessor());
/*  87 */     this.messageProcessors.add(new ProjectileUpdateProcessor());
/*  88 */     this.messageProcessors.add(new DFXExecuteProcessor());
/*  89 */     this.messageProcessors.add(new DFXExecuteListProcessor());
/*  90 */     this.messageProcessors.add(new DFXKillProcessor());
/*  91 */     this.messageProcessors.add(new StatEffectProcessor());
/*  92 */     this.messageProcessors.add(new PickUpLootCreationProcessor());
/*  93 */     this.messageProcessors.add(new TeleportObjectsListProcessor());
/*  94 */     this.messageProcessors.add(new DynamicObjectsListProcessor());
/*  95 */     this.messageProcessors.add(new NotifyPetSelectedAndActivatedProcessor());
/*  96 */     this.messageProcessors.add(new DebugAttackShapeProcessor());
/*  97 */     this.messageProcessors.add(new ActivatePortalProcessor());
/*  98 */     this.messageProcessors.add(new QuestCreationProcessor());
/*  99 */     this.messageProcessors.add(new QuestCompletedProcessor());
/* 100 */     this.messageProcessors.add(new QuestObjectiveUpdateProcessor());
/* 101 */     this.messageProcessors.add(new TargetedEffectUpdateProcessor());
/* 102 */     this.messageProcessors.add(new ActivateWaypointDestianationportalProcessor());
/* 103 */     this.messageProcessors.add(new ActivateWaypointProcessor());
/* 104 */     this.messageProcessors.add(new UpdateWaypointDestianationportalProcessor());
/* 105 */     this.messageProcessors.add(new ActivateLoadingScreenProcessor());
/* 106 */     this.messageProcessors.add(new CheckpointActivatedProcessor());
/* 107 */     this.messageProcessors.add(new PropRemovalProcesssor());
/* 108 */     this.messageProcessors.add(new LoginResponseProcessor());
/* 109 */     this.messageProcessors.add(new QuestsAbandonedProcessor());
/* 110 */     this.messageProcessors.add(new UpdateClientTownportalProcessor());
/* 111 */     this.messageProcessors.add(new GiftBoxUpdateProcessor());
/* 112 */     this.messageProcessors.add(new NewPetCollectedProcessor());
/* 113 */     this.messageProcessors.add(new NewItemCollectedProcessor());
/* 114 */     this.messageProcessors.add(new PickUpLootConvetedProcessor());
/* 115 */     this.messageProcessors.add(new ReconnetChatProcessor());
/* 116 */     this.messageProcessors.add(new TcgChatProcessor());
/* 117 */     this.messageProcessors.add(new TcgChatFriendRequestProcessor());
/* 118 */     this.messageProcessors.add(new TcgChatFriendAcceptedProcessor());
/* 119 */     this.messageProcessors.add(new FriendsMapProcessor());
/* 120 */     this.messageProcessors.add(new LoginFinishedProcessor());
/* 121 */     this.messageProcessors.add(new ClientPausedProcessor());
/* 122 */     this.messageProcessors.add(new PauseRejectedProcessor());
/* 123 */     this.messageProcessors.add(new UpdateTargetEffectProcessor());
/* 124 */     this.messageProcessors.add(new PetTrialStartedProcessor());
/* 125 */     this.messageProcessors.add(new PetTrialFinishedProcessor());
/* 126 */     this.messageProcessors.add(new AccountRegisterResponseProcessor());
/* 127 */     this.messageProcessors.add(new ImmunityEffectProcessor());
/* 128 */     this.messageProcessors.add(new AccountSubscribeTokenResponseProcessor());
/* 129 */     this.messageProcessors.add(new DailyQuestUpdatedNumberProcessor());
/* 130 */     this.messageProcessors.add(new DFXServerNoticeProcessor());
/* 131 */     this.messageProcessors.add(new SubscriptionStatusUpdateProcessor());
/* 132 */     this.messageProcessors.add(new RefreshPlayerDataProcessor());
/* 133 */     this.messageProcessors.add(new QuestCompletedListProcessor());
/* 134 */     this.messageProcessors.add(new ResetQuestsProcessor());
/* 135 */     this.messageProcessors.add(new SearchPlayerByNicknameResponseProcessor());
/* 136 */     this.messageProcessors.add(new SearchPlayerToTellResponseProcessor());
/* 137 */     this.messageProcessors.add(new StartTutorialProcessor());
/* 138 */     this.messageProcessors.add(new SendSanctionResponseProcessor());
/* 139 */     this.messageProcessors.add(new InternalChatProcessor());
/* 140 */     this.messageProcessors.add(new OnlineNotificationProcessor());
/* 141 */     this.messageProcessors.add(new DebugSquareAttackShapeProcessor());
/* 142 */     this.messageProcessors.add(new FactionChangedProcessor());
/* 143 */     this.messageProcessors.add(new DuelInvitationProcessor());
/* 144 */     this.messageProcessors.add(new DuelStartProcessor());
/* 145 */     this.messageProcessors.add(new DuelFinishProcessor());
/* 146 */     this.messageProcessors.add(new DuelRejectionProcessor());
/* 147 */     this.messageProcessors.add(new AccessKeysProcessor());
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 151 */     if (!MainGameState.getWorld().isFullLoading()) {
/* 152 */       broadcastPosition(time);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void broadcastPosition(float time) {
/* 158 */     if (MainGameState.isStateInitialized()) {
/*     */       
/* 160 */       GameIOHandler gameIOHandler = NetworkHandler.instance().getIOHandler();
/*     */       
/* 162 */       if (gameIOHandler != null && gameIOHandler.isConnected() && shouldBroadcast(time)) {
/*     */         
/* 164 */         LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 165 */         if (localClientPlayer != null && localClientPlayer.isAlive() && hasMoved((ClientPlayer)localClientPlayer)) {
/* 166 */           this.lastAngle = localClientPlayer.getRotation();
/* 167 */           this.lastPosition = new WorldCoordinate(localClientPlayer.getPosition());
/* 168 */           PositionUpdateMessage message = new PositionUpdateMessage(localClientPlayer.getId(), localClientPlayer.getPosition(), localClientPlayer.getRotation());
/*     */           
/*     */           try {
/* 171 */             gameIOHandler.send((Message)message);
/* 172 */           } catch (InterruptedException ignore) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceBroadcast() {
/* 181 */     if (MainGameState.isStateInitialized()) {
/*     */       
/* 183 */       GameIOHandler gameIOHandler = NetworkHandler.instance().getIOHandler();
/*     */       
/* 185 */       if (gameIOHandler != null && gameIOHandler.isConnected()) {
/* 186 */         this.broadcastPosTimer = 0.0F;
/* 187 */         LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 188 */         if (localClientPlayer != null && localClientPlayer.isAlive() && hasMoved((ClientPlayer)localClientPlayer)) {
/* 189 */           this.lastAngle = localClientPlayer.getRotation();
/* 190 */           this.lastPosition = new WorldCoordinate(localClientPlayer.getPosition());
/* 191 */           PositionUpdateMessage message = new PositionUpdateMessage(localClientPlayer.getId(), localClientPlayer.getPosition(), localClientPlayer.getRotation());
/*     */           
/*     */           try {
/* 194 */             gameIOHandler.send((Message)message);
/* 195 */           } catch (InterruptedException ignore) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasMoved(ClientPlayer player) {
/* 204 */     return (this.lastPosition == null || this.lastAngle != player.getRotation() || !this.lastPosition.equals(player.getPosition()));
/*     */   }
/*     */   
/*     */   private boolean shouldBroadcast(float time) {
/* 208 */     this.broadcastPosTimer += time;
/* 209 */     if (this.broadcastPosTimer > 0.1F && NetworkHandler.instance().getIOHandler().isConnected()) {
/* 210 */       this.broadcastPosTimer = 0.0F;
/* 211 */       return true;
/*     */     } 
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateByTimeLimit(long nanosLimit) {
/* 219 */     processInputMessages(nanosLimit);
/*     */   }
/*     */   
/*     */   private void processInputMessages(long nanosLimit) {
/* 223 */     long start = System.nanoTime();
/* 224 */     long usedTime = 0L;
/* 225 */     long messageUsed = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     nanosLimit = this.MinTime;
/*     */ 
/*     */     
/* 243 */     Message message = null;
/* 244 */     Queue<Message> inputMessages = getIoHandler().getInputMessages();
/* 245 */     Iterator<Message> messageIterator = inputMessages.iterator();
/* 246 */     while (messageIterator.hasNext()) {
/* 247 */       message = messageIterator.next();
/* 248 */       messageIterator.remove();
/*     */       
/* 250 */       Set<MessageProcessor> processors = getProcessorsForMessage(message);
/*     */       
/* 252 */       long messageStart = System.nanoTime();
/* 253 */       for (MessageProcessor processor : processors) {
/* 254 */         processor.process(message, getIoHandler(), this.creatureDataMap, this.playerDataMap, this.unknownCreatureDataMap, this.unknownPlayerDataMap);
/*     */       }
/*     */       
/* 257 */       if (message instanceof ResponseCollectedPetsMessage) {
/* 258 */         processCollectedPetsMessage((ResponseCollectedPetsMessage)message);
/*     */       }
/*     */       
/* 261 */       messageUsed = System.nanoTime() - messageStart;
/* 262 */       usedTime = System.nanoTime() - start;
/* 263 */       if (usedTime > nanosLimit) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<MessageProcessor> getProcessorsForMessage(Message message) {
/* 275 */     Set<MessageProcessor> processorsForMessage = new HashSet<MessageProcessor>();
/* 276 */     for (MessageProcessor messageProcessor : this.messageProcessors) {
/* 277 */       if (messageProcessor.getMessageType() == message.getMessageType()) {
/* 278 */         processorsForMessage.add(messageProcessor);
/*     */       }
/*     */     } 
/* 281 */     return processorsForMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static InterpolationController findInterpolationController(PropNode prop) {
/* 286 */     for (Controller controller : prop.getControllers()) {
/* 287 */       if (controller instanceof InterpolationController) {
/* 288 */         return (InterpolationController)controller;
/*     */       }
/*     */     } 
/* 291 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static Inventory findForId(int id) {
/* 296 */     for (Inventory inventoryUpdate : inventoryUpdates) {
/* 297 */       if (inventoryUpdate.getId() == id) {
/* 298 */         return inventoryUpdate;
/*     */       }
/*     */     } 
/* 301 */     throw new IllegalStateException("No InventoryUpdate registered for ID: " + id);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void registerForInventoryUpdate(Inventory inventory) {
/* 307 */     if (inventoryUpdates == null) {
/* 308 */       inventoryUpdates = new HashSet<Inventory>();
/*     */     }
/*     */ 
/*     */     
/* 312 */     Iterator<Inventory> it = inventoryUpdates.iterator();
/* 313 */     while (it.hasNext()) {
/* 314 */       Inventory inventoryUpdate = it.next();
/* 315 */       if (inventoryUpdate.getId() == inventory.getId()) {
/* 316 */         it.remove();
/*     */       }
/*     */     } 
/* 319 */     inventoryUpdates.add(inventory);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void unregisterForInventoryUpdate(Inventory inventory) {
/* 324 */     if (inventoryUpdates != null) {
/* 325 */       inventoryUpdates.remove(inventory);
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void unregisterAllForInventoryUpdate() throws StateException {
/* 331 */     if (inventoryUpdates != null) {
/* 332 */       inventoryUpdates.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private void processCollectedPetsMessage(ResponseCollectedPetsMessage responseCollectedPetsMessage) {
/* 337 */     if (this.responseCollectedPetsProcessors != null) {
/* 338 */       for (ResponseCollectedPetsProcessor responseCollectedPetsProcessor : this.responseCollectedPetsProcessors) {
/* 339 */         responseCollectedPetsProcessor.process(responseCollectedPetsMessage);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public CreatureData getCreatureData(int id) {
/* 345 */     return this.creatureDataMap.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   public CreatureData getPlayerData(int id) {
/* 349 */     return this.playerDataMap.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   public void addResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 353 */     if (this.responseCollectedPetsProcessors == null) {
/* 354 */       this.responseCollectedPetsProcessors = (List<ResponseCollectedPetsProcessor>)new SizeCheckedArrayList(4, "responseCollectedPetsProcessors", 16);
/*     */     }
/* 356 */     this.responseCollectedPetsProcessors.add(processor);
/*     */   }
/*     */   
/*     */   public void removeResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 360 */     if (this.responseCollectedPetsProcessors != null)
/* 361 */       this.responseCollectedPetsProcessors.remove(processor); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\GameOperationState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */