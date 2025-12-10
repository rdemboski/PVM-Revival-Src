/*     */ package com.funcom.tcg.client.net.creaturebuilders;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.view.OverheadIcons;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.monsters.MonsterManager;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestManager;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.ClickSpeachAction;
/*     */ import com.funcom.tcg.client.actions.QuestGiverMouseOver;
/*     */ import com.funcom.tcg.client.actions.QuestWindowAction;
/*     */ import com.funcom.tcg.client.actions.TalkAction;
/*     */ import com.funcom.tcg.client.controllers.BarkingController;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ 
/*     */ public class QuestGiverBuilder
/*     */   extends AbstractModularCreatureBuilder {
/*  34 */   public static final ColorRGBA QUESTGIVER_TINT_COLOR = (new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F);
/*     */   
/*     */   private QuestManager questManager;
/*     */   public static final String QUESTGIVER_DFX = "xml/dfx/questgiver.xml";
/*     */   
/*     */   public QuestGiverBuilder(MonsterManager monsterManager, QuestManager questManager) {
/*  40 */     super(monsterManager);
/*  41 */     this.questManager = questManager;
/*     */   }
/*     */   
/*     */   protected DefaultActionInteractActionHandler createActionHandler(Creature monster, CreatureCreationMessage message, PropNode propNode) {
/*  45 */     QuestDescription questDescription = this.questManager.getQuestDescription(message.getQuest());
/*  46 */     propNode.initializeAllEffects(TcgGame.getResourceGetter(), MainGameState.getWorld().getParticleSurface());
/*  47 */     short questCompletion = message.getQuestCompletion();
/*     */     
/*  49 */     return updateQuestGiverIconsAndGetAction((InteractibleProp)monster, propNode, questDescription, questCompletion, message.getQuestHandIn());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultActionInteractActionHandler updateQuestGiverIconsAndGetAction(InteractibleProp monster, PropNode propNode, QuestDescription questDescription, short questCompletion, String questHandIn) {
/*  59 */     String action = null;
/*  60 */     propNode.killDfx("xml/dfx/questgiver.xml");
/*     */     
/*  62 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(((Creature)monster).getMonsterId());
/*  63 */     if (speachMapping != null && 
/*  64 */       speachMapping.isBarks())
/*     */     {
/*  66 */       propNode.addController((Controller)new BarkingController(propNode, speachMapping));
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (questDescription != null) {
/*  71 */       int levelDifficulty = questDescription.getLevelDifficulty();
/*  72 */       int playerLevel = MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20)).intValue();
/*  73 */       if (questCompletion == 0) {
/*  74 */         if (questDescription.isDaily()) {
/*  75 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_DAILY);
/*  76 */         } else if (levelDifficulty - playerLevel > 3) {
/*  77 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_HIGHER);
/*  78 */         } else if (levelDifficulty - playerLevel >= 0 && levelDifficulty - playerLevel <= 2) {
/*  79 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_NEUTRAL);
/*  80 */         } else if (levelDifficulty - playerLevel < 0) {
/*  81 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_LOWER);
/*     */         } 
/*  83 */         action = "quest-window";
/*  84 */         monster.addAction((Action)new QuestWindowAction((short)0, questDescription.getId(), monster.getId(), questHandIn));
/*     */         
/*  86 */         if (MainGameState.getQuestModel().findCurrentActiveQuest() == null) {
/*     */           try {
/*  88 */             DireEffectDescription alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription("xml/dfx/questgiver.xml", false);
/*     */             
/*  90 */             if (!alwaysOnDfxDescription.isEmpty()) {
/*  91 */               DireEffect dfx = alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  92 */               propNode.addDfx(dfx);
/*     */             } 
/*  94 */           } catch (NoSuchDFXException e) {
/*  95 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*  98 */       } else if (questCompletion == 1) {
/*  99 */         propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_DEFAULT);
/* 100 */         action = "quest-window";
/* 101 */         monster.addAction((Action)new QuestWindowAction((short)1, questDescription.getId(), monster.getId(), questHandIn));
/* 102 */       } else if (questCompletion == 2) {
/* 103 */         if (questDescription.isDaily()) {
/* 104 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_DAILY);
/*     */         } else {
/* 106 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_NEUTRAL);
/*     */         } 
/* 108 */         action = "quest-window";
/* 109 */         monster.addAction((Action)new QuestWindowAction((short)2, questDescription.getId(), monster.getId(), questHandIn));
/*     */       } 
/*     */     } else {
/* 112 */       propNode.getBasicEffectsNode().setState(OverheadIcons.State.NONE);
/*     */       
/* 114 */       if (speachMapping != null) {
/*     */         
/* 116 */         action = "click-speach";
/* 117 */         monster.addAction((Action)new ClickSpeachAction(speachMapping, propNode));
/*     */       } 
/*     */     } 
/* 120 */     if (action == null) {
/* 121 */       action = "talk";
/* 122 */       monster.addAction((Action)new TalkAction());
/*     */     } 
/*     */     
/* 125 */     QuestGiverMouseOver questGiverMouseOver = new QuestGiverMouseOver(MainGameState.getMouseCursorSetter(), monster, QUESTGIVER_TINT_COLOR);
/*     */     
/* 127 */     questGiverMouseOver.setOwnerPropNode(propNode);
/*     */     
/* 129 */     return new DefaultActionInteractActionHandler(monster, (Creature)MainGameState.getPlayerNode().getProp(), action, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questGiverMouseOver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuildable(Faction faction) {
/* 138 */     return (faction.equivalentTo(BaseFaction.NO_FIGHT) || faction.equivalentTo(MainGameState.getPlayerModel().getFaction()));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\QuestGiverBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */