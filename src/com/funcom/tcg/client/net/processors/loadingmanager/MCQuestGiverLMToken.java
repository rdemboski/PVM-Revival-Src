/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.view.OverheadIcons;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCQuestGiverLMToken
/*     */   extends AbstractModularCreatureLMToken
/*     */ {
/*  37 */   public static final ColorRGBA QUESTGIVER_TINT_COLOR = (new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F);
/*     */   
/*     */   private QuestManager questManager;
/*     */   public static final String QUESTGIVER_DFX = "xml/dfx/questgiver.xml";
/*     */   
/*     */   public MCQuestGiverLMToken(CreatureCreationMessage message) {
/*  43 */     super(message);
/*  44 */     this.questManager = TcgGame.getRpgLoader().getQuestManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBuild(Faction faction) throws Exception {
/*  49 */     return (faction.equivalentTo(BaseFaction.NO_FIGHT) || faction.equivalentTo(MainGameState.getPlayerModel().getFaction()));
/*     */   }
/*     */   
/*     */   protected DefaultActionInteractActionHandler createActionHandler(Creature monster, CreatureCreationMessage message, PropNode propNode) {
/*  53 */     QuestDescription questDescription = this.questManager.getQuestDescription(message.getQuest());
/*  54 */     propNode.initializeAllEffects(TcgGame.getResourceGetter(), MainGameState.getWorld().getParticleSurface());
/*  55 */     short questCompletion = message.getQuestCompletion();
/*     */     
/*  57 */     return updateQuestGiverIconsAndGetAction((InteractibleProp)monster, propNode, questDescription, questCompletion, message.getQuestHandIn());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultActionInteractActionHandler updateQuestGiverIconsAndGetAction(InteractibleProp monster, PropNode propNode, QuestDescription questDescription, short questCompletion, String questHandIn) {
/*  67 */     String action = null;
/*  68 */     propNode.killDfx("xml/dfx/questgiver.xml");
/*     */     
/*  70 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(((Creature)monster).getMonsterId());
/*  71 */     if (speachMapping != null && 
/*  72 */       speachMapping.isBarks())
/*     */     {
/*  74 */       propNode.addController((Controller)new BarkingController(propNode, speachMapping));
/*     */     }
/*     */ 
/*     */     
/*  78 */     if (questDescription != null) {
/*  79 */       int levelDifficulty = questDescription.getLevelDifficulty();
/*  80 */       int playerLevel = MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20)).intValue();
/*  81 */       if (questCompletion == 0) {
/*  82 */         if (questDescription.isDaily()) {
/*  83 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_DAILY);
/*  84 */         } else if (levelDifficulty - playerLevel > 3) {
/*  85 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_HIGHER);
/*  86 */         } else if (levelDifficulty - playerLevel >= 0 && levelDifficulty - playerLevel <= 2) {
/*  87 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_NEUTRAL);
/*  88 */         } else if (levelDifficulty - playerLevel < 0) {
/*  89 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_OFFER_LOWER);
/*     */         } 
/*  91 */         action = "quest-window";
/*  92 */         monster.addAction((Action)new QuestWindowAction((short)0, questDescription.getId(), monster.getId(), questHandIn));
/*     */         
/*  94 */         if (MainGameState.getQuestModel().findCurrentActiveQuest() == null) {
/*     */           try {
/*  96 */             DireEffectDescription alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription("xml/dfx/questgiver.xml", false);
/*     */             
/*  98 */             if (!alwaysOnDfxDescription.isEmpty()) {
/*  99 */               DireEffect dfx = alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 100 */               propNode.addDfx(dfx);
/*     */             } 
/* 102 */           } catch (NoSuchDFXException e) {
/* 103 */             e.printStackTrace();
/*     */           } 
/*     */         }
/* 106 */       } else if (questCompletion == 1) {
/* 107 */         propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_DEFAULT);
/* 108 */         action = "quest-window";
/* 109 */         monster.addAction((Action)new QuestWindowAction((short)1, questDescription.getId(), monster.getId(), questHandIn));
/* 110 */       } else if (questCompletion == 2) {
/* 111 */         if (questDescription.isDaily()) {
/* 112 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_DAILY);
/*     */         } else {
/* 114 */           propNode.getBasicEffectsNode().setState(OverheadIcons.State.QUEST_HANDING_NEUTRAL);
/*     */         } 
/* 116 */         action = "quest-window";
/* 117 */         monster.addAction((Action)new QuestWindowAction((short)2, questDescription.getId(), monster.getId(), questHandIn));
/*     */       } 
/*     */     } else {
/* 120 */       propNode.getBasicEffectsNode().setState(OverheadIcons.State.NONE);
/*     */       
/* 122 */       if (speachMapping != null) {
/*     */         
/* 124 */         action = "click-speach";
/* 125 */         monster.addAction((Action)new ClickSpeachAction(speachMapping, propNode));
/*     */       } 
/*     */     } 
/* 128 */     if (action == null) {
/* 129 */       action = "talk";
/* 130 */       monster.addAction((Action)new TalkAction());
/*     */     } 
/*     */     
/* 133 */     QuestGiverMouseOver questGiverMouseOver = new QuestGiverMouseOver(MainGameState.getMouseCursorSetter(), monster, QUESTGIVER_TINT_COLOR);
/*     */     
/* 135 */     questGiverMouseOver.setOwnerPropNode(propNode);
/*     */     
/* 137 */     return new DefaultActionInteractActionHandler(monster, (Creature)MainGameState.getPlayerNode().getProp(), action, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questGiverMouseOver);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\MCQuestGiverLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */