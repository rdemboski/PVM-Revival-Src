/*     */ package com.funcom.tcg.client.model.ui;
/*     */ 
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.GameOperationState;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*     */ import com.funcom.tcg.rpg.TCGFaction;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ public class HoverObjectInfoProvider
/*     */   implements RpgDataProvider
/*     */ {
/*     */   private Set<RpgDataProvider.DataListener> listeners;
/*     */   private InteractibleProp hoveredObject;
/*  27 */   private String name = "";
/*     */   private int level;
/*     */   private float healthFraction;
/*  30 */   private RpgDataProvider.RpgType type = RpgDataProvider.RpgType.MOB;
/*     */   
/*     */   private boolean nameChanged = true;
/*     */   
/*     */   private boolean levelChanged = true;
/*     */   
/*     */   private boolean rpgTypeChanged = true;
/*     */   private boolean healthFractionChanged = true;
/*     */   
/*     */   public void addListener(RpgDataProvider.DataListener listener) {
/*  40 */     if (this.listeners == null)
/*  41 */       this.listeners = new HashSet<RpgDataProvider.DataListener>(); 
/*  42 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeListener(RpgDataProvider.DataListener listener) {
/*  46 */     if (this.listeners != null)
/*  47 */       this.listeners.remove(listener); 
/*     */   }
/*     */   
/*     */   private void fireChangedListeners(boolean containsObject, boolean isMonster, boolean isPlayer) {
/*  51 */     if (this.listeners != null)
/*  52 */       for (RpgDataProvider.DataListener listener : this.listeners)
/*  53 */         listener.dataChanged(containsObject, isMonster, isPlayer);  
/*     */   }
/*     */   
/*     */   public void reset() {
/*  57 */     this.rpgTypeChanged = true;
/*  58 */     this.nameChanged = true;
/*  59 */     this.levelChanged = true;
/*  60 */     this.healthFractionChanged = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean rpgTypeChanged() {
/*  65 */     if (this.rpgTypeChanged) {
/*  66 */       this.rpgTypeChanged = false;
/*  67 */       return true;
/*     */     } 
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nameChanged() {
/*  74 */     if (this.nameChanged) {
/*  75 */       this.nameChanged = false;
/*  76 */       return true;
/*     */     } 
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean levelChanged() {
/*  83 */     if (this.levelChanged) {
/*  84 */       this.levelChanged = false;
/*  85 */       return true;
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean healthFractionChanged() {
/*  92 */     if (this.healthFractionChanged) {
/*  93 */       this.healthFractionChanged = false;
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 100 */     return this.name;
/*     */   }
/*     */   
/*     */   private void updateName() {
/* 104 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 105 */     CreatureData data = getCreatureData(currentState);
/* 106 */     if (data == null) {
/* 107 */       setName("");
/* 108 */     } else if (this.hoveredObject instanceof ClientPlayer) {
/* 109 */       setName(this.hoveredObject.getName());
/*     */     } else {
/* 111 */       MonsterDescription description = TcgGame.getRpgLoader().getMonsterManager().getDescription(data.getType());
/* 112 */       setName(description.getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setName(String name) {
/* 117 */     if (!this.name.equals(name)) {
/* 118 */       this.nameChanged = true;
/* 119 */       this.name = name;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 124 */     return this.level;
/*     */   }
/*     */   
/*     */   private void updateLevel() {
/* 128 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 129 */     CreatureData data = getCreatureData(currentState);
/* 130 */     if (data != null && data.getStats() != null && !data.getStats().isEmpty()) {
/* 131 */       setLevel(((Integer)data.getStats().get(Short.valueOf((short)20))).intValue());
/*     */     } else {
/* 133 */       setLevel(0);
/*     */     } 
/*     */   }
/*     */   private void setLevel(int level) {
/* 137 */     if (this.level != level) {
/* 138 */       this.levelChanged = true;
/* 139 */       this.level = level;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getHealthFraction() {
/* 144 */     return this.healthFraction;
/*     */   }
/*     */   
/*     */   private void updateHealthFraction() {
/* 148 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 149 */     CreatureData data = getCreatureData(currentState);
/* 150 */     if (data == null || data.getStats() == null || data.getStats().isEmpty()) {
/* 151 */       setHealthFraction(0.0F);
/*     */     } else {
/* 153 */       Integer health = (Integer)data.getStats().get(Short.valueOf((short)12));
/* 154 */       Integer maxHealth = (Integer)data.getStats().get(Short.valueOf((short)11));
/* 155 */       if (health == null || maxHealth == null) {
/* 156 */         setHealthFraction(0.0F);
/*     */       } else {
/* 158 */         setHealthFraction(Math.min(health.floatValue() / maxHealth.floatValue(), 1.0F));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void setHealthFraction(float healthFraction) {
/* 163 */     if (this.healthFraction != healthFraction) {
/* 164 */       this.healthFractionChanged = true;
/* 165 */       this.healthFraction = healthFraction;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RpgDataProvider.RpgType getRpgType() {
/* 171 */     return this.type;
/*     */   }
/*     */   
/*     */   private void updateRpgType() {
/* 175 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 176 */     CreatureData data = getCreatureData(currentState);
/* 177 */     if (data == null) {
/* 178 */       setRpgType(RpgDataProvider.RpgType.MOB);
/* 179 */     } else if (this.hoveredObject instanceof ClientPlayer) {
/* 180 */       setRpgType(RpgDataProvider.RpgType.PLAYER);
/*     */     } else {
/* 182 */       MonsterDescription description = TcgGame.getRpgLoader().getMonsterManager().getDescription(data.getType());
/* 183 */       setRpgType(RpgDataProvider.RpgType.convertFromHardness(description.getHardness()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setRpgType(RpgDataProvider.RpgType rpgType) {
/* 188 */     if (this.type != rpgType) {
/* 189 */       this.rpgTypeChanged = true;
/* 190 */       this.healthFractionChanged = true;
/* 191 */       this.type = rpgType;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public StateUpdateMessage.BuffData getBuff() {
/* 197 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 198 */     CreatureData data = getCreatureData(currentState);
/* 199 */     if (data == null)
/* 200 */       return null; 
/* 201 */     return (StateUpdateMessage.BuffData)data.getBuffSlots().get(BuffType.DEBUFF);
/*     */   }
/*     */   
/*     */   private CreatureData getCreatureData(GameOperationState currentState) {
/* 205 */     if (this.hoveredObject instanceof ClientPlayer)
/* 206 */       return currentState.getPlayerData(this.hoveredObject.getId()); 
/* 207 */     return currentState.getCreatureData(this.hoveredObject.getId());
/*     */   }
/*     */   
/*     */   public void removeObject() {
/* 211 */     this.hoveredObject = null;
/* 212 */     fireChangedListeners(false, false, false);
/*     */   }
/*     */   
/*     */   public void setHoveredObject(PropNode prop) {
/* 216 */     if (prop.equals(this.hoveredObject)) {
/*     */       return;
/*     */     }
/* 219 */     this.hoveredObject = (InteractibleProp)prop.getProp();
/*     */     
/* 221 */     updateName();
/* 222 */     updateLevel();
/* 223 */     updateHealthFraction();
/*     */     
/* 225 */     setRpgType(RpgDataProvider.RpgType.PLAYER);
/*     */     
/* 227 */     GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 228 */     CreatureData data = getCreatureData(currentState);
/*     */     
/* 230 */     if (data != null) {
/* 231 */       MonsterDescription description = TcgGame.getRpgLoader().getMonsterManager().getDescription(data.getType());
/* 232 */       if (description != null) {
/* 233 */         if (!description.getName().isEmpty() && !TcgGame.isDueling()) {
/* 234 */           String level = (description.getFaction().equals(TCGFaction.NO_FIGHT) || description.getFaction().equals(TCGFaction.INTERACT_KILL)) ? "" : ("(" + description.getLevel() + ")");
/* 235 */           if (LoadingManager.USE) {
/*     */ 
/*     */             
/* 238 */             LoadingManager.INSTANCE.submit(new LoadingChatBubbleLMToken(description.getName() + " " + level, prop));
/*     */           } else {
/*     */             
/* 241 */             MainGameState.getChatUIController().createChatBubble(description.getName() + " " + level, "", prop, true);
/*     */           } 
/*     */         } 
/*     */         
/* 245 */         fireChangedListeners(true, description.getFaction().equals(TCGFaction.MONSTER), false);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 250 */     if (this.hoveredObject instanceof ClientPlayer && !TcgGame.isDueling()) {
/* 251 */       ClientPlayer clientPlayer = (ClientPlayer)this.hoveredObject;
/* 252 */       CreatureData creatureData = currentState.getPlayerData(clientPlayer.getId());
/* 253 */       if (creatureData != null)
/*     */       {
/* 255 */         MainGameState.getChatUIController().createChatBubble(TcgGame.getLocalizedText("rewardwindow.level", new String[0]) + ": " + creatureData.getStats().get(Short.valueOf((short)20)), "", TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(clientPlayer.getId())), true);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       fireChangedListeners(true, false, true);
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     fireChangedListeners(true, false, false);
/*     */   }
/*     */   
/*     */   class LoadingChatBubbleLMToken
/*     */     extends LoadingManagerToken {
/* 270 */     String name = "";
/* 271 */     PropNode prop = null;
/* 272 */     InteractibleProp interactibleProp = null;
/* 273 */     private Future<PropNode> FindNodeFuture = null;
/*     */     
/*     */     public LoadingChatBubbleLMToken(String name, PropNode prop) {
/* 276 */       this.name = name;
/* 277 */       this.prop = prop;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer call() {
/* 282 */       MainGameState.getChatUIController().createChatBubble(this.name, "", this.prop, true);
/* 283 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean processRequestAssets() throws Exception {
/* 289 */       return true;
/*     */     }
/*     */     
/*     */     public boolean processWaitingAssets() throws Exception {
/* 293 */       return (this.FindNodeFuture == null || this.FindNodeFuture.isDone());
/*     */     }
/*     */     
/*     */     public boolean processGame() throws Exception {
/* 297 */       if (this.FindNodeFuture != null && this.FindNodeFuture.isDone() && !this.FindNodeFuture.isCancelled()) {
/* 298 */         MainGameState.getChatUIController().createChatBubble(this.name, "", this.FindNodeFuture.get(), true);
/*     */       } else {
/*     */         
/* 301 */         MainGameState.getChatUIController().createChatBubble(this.name, "", this.prop, true);
/*     */       } 
/* 303 */       return true;
/*     */     }
/*     */     
/*     */     class FindNodeCallable implements Callable {
/*     */       public PropNode call() {
/* 308 */         return TcgGame.getMonsterRegister().getPropNode(HoverObjectInfoProvider.LoadingChatBubbleLMToken.this.interactibleProp);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\mode\\ui\HoverObjectInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */