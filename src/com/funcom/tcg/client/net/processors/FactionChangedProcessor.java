/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.MonsterMouseOver;
/*     */ import com.funcom.tcg.client.actions.PlayerMouseOver;
/*     */ import com.funcom.tcg.client.actions.QuestGiverMouseOver;
/*     */ import com.funcom.tcg.client.actions.QuestUpdateAction;
/*     */ import com.funcom.tcg.client.model.MonsterActionHandler;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.PlayerActionHandler;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.QuestInteractibleMouseOver;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.Friend;
/*     */ import com.funcom.tcg.net.message.FactionChangedMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FactionChangedProcessor implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*     */     PropNode propNode;
/*  37 */     FactionChangedMessage factionChangedMessage = (FactionChangedMessage)message;
/*  38 */     int creatureType = factionChangedMessage.getCreatureType();
/*     */     
/*  40 */     if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  41 */       if (MainGameState.getPlayerModel().getId() == factionChangedMessage.getCreatureId()) {
/*  42 */         propNode = MainGameState.getPlayerNode();
/*     */       } else {
/*  44 */         PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/*  45 */         propNode = propNodeRegister.getPropNode(Integer.valueOf(factionChangedMessage.getCreatureId()));
/*     */       } 
/*     */     } else {
/*  48 */       PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/*  49 */       propNode = propNodeRegister.getPropNode(Integer.valueOf(factionChangedMessage.getCreatureId()));
/*     */     } 
/*  51 */     if (propNode != null) {
/*  52 */       if (propNode == MainGameState.getPlayerNode()) {
/*  53 */         MainGameState.getPlayerModel().setFaction(factionChangedMessage.getFaction());
/*     */       } else {
/*  55 */         Faction faction = factionChangedMessage.getFaction();
/*  56 */         Creature monster = (Creature)propNode.getProp();
/*  57 */         if (faction.equivalentTo(BaseFaction.MONSTER) || faction.equivalentTo(BaseFaction.FREE_FOR_ALL) || (faction.equivalentTo(BaseFaction.PLAYER) && !faction.equivalentTo(MainGameState.getPlayerModel().getFaction()))) {
/*     */           
/*  59 */           addAttackActionHandler(propNode, monster);
/*  60 */         } else if (faction.equivalentTo(BaseFaction.NO_FIGHT) || faction.equivalentTo(MainGameState.getPlayerModel().getFaction())) {
/*     */           
/*  62 */           if (monster instanceof ClientPlayer) {
/*  63 */             addFriendlyPlayerActionHandler(propNode, (ClientPlayer)monster);
/*     */           } else {
/*  65 */             addFriendlyMonsterActionHandler(propNode, monster);
/*     */           } 
/*  67 */         } else if (faction.equivalentTo(BaseFaction.INTERACT_KILL)) {
/*  68 */           addInteractKillActionHandler(propNode, monster);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void addFriendlyPlayerActionHandler(PropNode propNode, ClientPlayer monster) {
/*  75 */     PlayerMouseOver playerMouseOver = new PlayerMouseOver(MainGameState.getMouseCursorSetter(), PlayerCreatureCreationProcessor.PLAYER_TINT_COLOR);
/*  76 */     playerMouseOver.setOwnerPropNode(propNode);
/*  77 */     PlayerActionHandler playerActionHandler = new PlayerActionHandler(monster, (MouseOver)playerMouseOver);
/*  78 */     propNode.setActionHandler((UserActionHandler)playerActionHandler);
/*     */     
/*  80 */     ColorRGBA textColor = ColorRGBA.white;
/*  81 */     if (MainGameState.getFriendModel().getFriendsList().containsKey(Integer.valueOf(monster.getExternalChatId()))) {
/*  82 */       Friend friend = (Friend)MainGameState.getFriendModel().getFriendsList().get(Integer.valueOf(monster.getExternalChatId()));
/*  83 */       if (friend != null && !friend.isBlocked().booleanValue()) {
/*  84 */         textColor = ColorRGBA.green;
/*     */       }
/*     */     } 
/*  87 */     propNode.setTextColor(textColor);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addFriendlyMonsterActionHandler(PropNode propNode, Creature monster) {
/*  92 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(monster.getMonsterId());
/*     */     
/*  94 */     String action = null;
/*  95 */     if (speachMapping != null) {
/*     */       
/*  97 */       action = "click-speach";
/*  98 */       monster.addAction((Action)new ClickSpeachAction(speachMapping, propNode));
/*     */     } 
/* 100 */     if (action == null) {
/* 101 */       action = "talk";
/* 102 */       monster.addAction((Action)new TalkAction());
/*     */     } 
/*     */     
/* 105 */     QuestGiverMouseOver questGiverMouseOver = new QuestGiverMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)monster, QuestGiverBuilder.QUESTGIVER_TINT_COLOR);
/*     */     
/* 107 */     questGiverMouseOver.setOwnerPropNode(propNode);
/*     */     
/* 109 */     DefaultActionInteractActionHandler actionHandler = new DefaultActionInteractActionHandler((InteractibleProp)monster, (Creature)MainGameState.getPlayerNode().getProp(), action, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questGiverMouseOver);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     propNode.setActionHandler((UserActionHandler)actionHandler);
/*     */   }
/*     */   
/*     */   private void addAttackActionHandler(PropNode propNode, Creature monster) {
/* 118 */     MonsterMouseOver monsterMouseOver = new MonsterMouseOver(MainGameState.getMouseCursorSetter(), MonsterBuilder.MONSTER_TINT_COLOR);
/* 119 */     monsterMouseOver.setOwnerPropNode(propNode);
/* 120 */     MonsterActionHandler actionHandler = new MonsterActionHandler(monster, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)monsterMouseOver);
/* 121 */     propNode.setActionHandler((UserActionHandler)actionHandler);
/* 122 */     propNode.setTextColor(ColorRGBA.red);
/*     */   }
/*     */   
/*     */   private void addInteractKillActionHandler(PropNode propNode, Creature monster) {
/* 126 */     QuestUpdateAction action = new QuestUpdateAction(NetworkHandler.instance().getIOHandler(), "" + monster.getId(), monster);
/* 127 */     monster.addAction((Action)action);
/*     */     
/* 129 */     QuestInteractibleMouseOver questInteractibleMouseOver = new QuestInteractibleMouseOver(QuestInteractibleBuilder.QUESTITEM_TINT_COLOR, Effects.TintMode.ADDITIVE, MainGameState.getMouseCursorSetter());
/* 130 */     questInteractibleMouseOver.setOwnerPropNode(propNode);
/*     */     
/* 132 */     DefaultActionInteractActionHandler actionHandler = new DefaultActionInteractActionHandler((InteractibleProp)monster, (Creature)MainGameState.getPlayerNode().getProp(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questInteractibleMouseOver);
/*     */     
/* 134 */     propNode.setActionHandler((UserActionHandler)actionHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 139 */     return 244;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\FactionChangedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */