/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.ai.DeadBrain;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.equipment.ArchType;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.CenteredGeomView;
/*     */ import com.funcom.tcg.client.ui.ClientStatCalc;
/*     */ import com.funcom.tcg.client.ui.GuiStatType;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.OverlayedContainer;
/*     */ import com.funcom.tcg.client.ui.UpdatedGeomView;
/*     */ import com.funcom.tcg.client.ui.hud2.PreviewModularDescription;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CharacterContainer
/*     */   extends OverlayedContainer
/*     */   implements SelectionHandler.DiffChangeListener {
/*     */   private final ClientPlayer clientPlayer;
/*     */   private final Localizer localizer;
/*     */   private final Map<Object, StatComponent> statComponents;
/*     */   private CenteredGeomView character3DView;
/*     */   private Rectangle character3DBounds;
/*     */   private BLabel[] statIcons;
/*     */   private BLabel[] progressBgds;
/*     */   
/*     */   public CharacterContainer(ClientPlayer clientPlayer, ResourceManager resourceManager, DireEffectDescriptionFactory direEffectDescriptionFactory, VisualRegistry visualRegistry, Localizer localizer, int width, int height) {
/*  53 */     this.clientPlayer = clientPlayer;
/*  54 */     this.localizer = localizer;
/*  55 */     this.statComponents = new HashMap<Object, StatComponent>();
/*  56 */     this.WIDTH = width;
/*  57 */     this.HEIGHT = height;
/*     */     
/*  59 */     initCharacter3DView(clientPlayer, resourceManager, direEffectDescriptionFactory, visualRegistry);
/*  60 */     initLabels();
/*  61 */     initPlayerNameLabel();
/*     */     
/*  63 */     add((BComponent)new BClickthroughLabel("", "character-border"), new Rectangle(0, 0, this.WIDTH, this.HEIGHT));
/*     */   }
/*     */   private BLabel[] statBarGloss; private BLabel[] statChangeLabels; private BProgressBar[] statBars; private BProgressBar[] statIncreaseBars; private BProgressBar[] statDecreaseBars; private Rectangle[] STAT_PROGRESS_SIZE; private final int WIDTH; private final int HEIGHT;
/*     */   private void initCharacter3DView(ClientPlayer clientPlayer, ResourceManager resourceManager, DireEffectDescriptionFactory direEffectDescriptionFactory, VisualRegistry visualRegistry) {
/*  67 */     BLabel bgd = new BLabel("", "character-bgd");
/*     */     
/*  69 */     this.character3DView = new CenteredGeomView(false);
/*  70 */     this.character3DView.setStyleClass("character-view");
/*  71 */     this.character3DView.setGeometry(createPlayerNodeCopy(clientPlayer, direEffectDescriptionFactory, resourceManager, visualRegistry));
/*     */     
/*  73 */     this.character3DView.setLookAtAngle(10.0F);
/*  74 */     this.character3DView.setZoom(0.8F);
/*  75 */     this.character3DView.update(1.0F);
/*  76 */     this.character3DView.resetCameraPosition();
/*  77 */     this.character3DView.addListener((ComponentListener)new UpdatedGeomView.CharacterRotationListener((UpdatedGeomView)this.character3DView));
/*     */ 
/*     */     
/*  80 */     BuiUtils.workaroundForParticleFacing();
/*     */     
/*  82 */     this.character3DBounds = new Rectangle(0, 60, this.WIDTH, this.HEIGHT - 65);
/*  83 */     add((BComponent)bgd, new Rectangle(0, 0, this.WIDTH, this.HEIGHT));
/*     */ 
/*     */     
/*  86 */     add((BComponent)this.character3DView, this.character3DBounds);
/*     */   }
/*     */   
/*     */   private void initPlayerNameLabel() {
/*  90 */     BClickthroughLabel characterNameLbl = new BClickthroughLabel(this.clientPlayer.getName(), "character-name");
/*  91 */     add((BComponent)characterNameLbl, new Rectangle(0, this.HEIGHT - 50, this.WIDTH, 36));
/*     */   }
/*     */   
/*     */   private void initLabels() {
/*  95 */     int statTotal = (GuiStatType.values()).length;
/*     */     
/*  97 */     this.statIcons = new BLabel[statTotal];
/*  98 */     this.progressBgds = new BLabel[statTotal];
/*  99 */     this.statIncreaseBars = new BProgressBar[statTotal];
/* 100 */     this.statDecreaseBars = new BProgressBar[statTotal];
/* 101 */     this.statBars = new BProgressBar[statTotal];
/* 102 */     this.statBarGloss = new BLabel[statTotal];
/* 103 */     this.statChangeLabels = new BLabel[statTotal];
/* 104 */     this.STAT_PROGRESS_SIZE = new Rectangle[statTotal];
/*     */     
/* 106 */     for (int i = 0; i < statTotal; i++) {
/* 107 */       String guiStatString = GuiStatType.values()[i].toString().toLowerCase();
/*     */       
/* 109 */       this.statIcons[i] = new BLabel("");
/* 110 */       this.statIcons[i].setStyleClass("icon-stat-" + guiStatString);
/* 111 */       this.statIcons[i].setTooltipText(TcgGame.getLocalizedText("charwindow.info." + guiStatString, new String[0]));
/*     */       
/* 113 */       this.progressBgds[i] = new BLabel("");
/* 114 */       this.progressBgds[i].setStyleClass("progress-bgd");
/*     */       
/* 116 */       this.statIncreaseBars[i] = new BProgressBar();
/* 117 */       this.statIncreaseBars[i].setStyleClass("progress-stat-increase");
/*     */       
/* 119 */       this.statDecreaseBars[i] = new BProgressBar(BProgressBar.Direction.WEST);
/* 120 */       this.statDecreaseBars[i].setStyleClass("progress-stat-decrease");
/*     */       
/* 122 */       this.statBars[i] = new BProgressBar();
/* 123 */       this.statBars[i].setStyleClass("progress-stat-" + guiStatString);
/*     */       
/* 125 */       this.statBarGloss[i] = new BLabel("0");
/* 126 */       this.statBarGloss[i].setStyleClass("progress-stat-gloss");
/*     */       
/* 128 */       this.statChangeLabels[i] = new BLabel("");
/* 129 */       this.statChangeLabels[i].setStyleClass("stat-change-label");
/*     */       
/* 131 */       int inverse = statTotal - 1 - i;
/* 132 */       int height = 20;
/*     */       
/* 134 */       Rectangle STAT_ICON_SIZE = new Rectangle(10, 10 + inverse * height, height, height);
/* 135 */       this.STAT_PROGRESS_SIZE[i] = new Rectangle(20 + height, 10 + inverse * height, 340, height);
/*     */       
/* 137 */       add((BComponent)this.statIcons[i], STAT_ICON_SIZE);
/* 138 */       add((BComponent)this.progressBgds[i], this.STAT_PROGRESS_SIZE[i]);
/* 139 */       add((BComponent)this.statIncreaseBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 140 */       add((BComponent)this.statBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 141 */       add((BComponent)this.statDecreaseBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 142 */       add((BComponent)this.statBarGloss[i], this.STAT_PROGRESS_SIZE[i]);
/* 143 */       add((BComponent)this.statChangeLabels[i], this.STAT_PROGRESS_SIZE[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 150 */     return "character-view-container";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 155 */     super.renderComponent(renderer);
/*     */   }
/*     */   
/*     */   private PropNode createPlayerNodeCopy(ClientPlayer clientPlayer, DireEffectDescriptionFactory direEffectDescriptionFactory, ResourceManager resourceManager, VisualRegistry visualRegistry) {
/* 159 */     ClientPlayer previewPlayer = new ClientPlayer("previewPlayer", new WorldCoordinate(), (Brain)new DeadBrain(), 0.0D);
/* 160 */     PropNode propNode = new PropNode((Prop)previewPlayer, 3, "", direEffectDescriptionFactory);
/*     */     
/* 162 */     PreviewModularDescription previewModularDescription = new PreviewModularDescription(clientPlayer, visualRegistry);
/* 163 */     ModularNode previewPlayerNode = new ModularNode((ModularDescription)previewModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, resourceManager);
/*     */ 
/*     */     
/* 166 */     previewPlayerNode.reloadCharacter();
/* 167 */     propNode.attachRepresentation((Spatial)previewPlayerNode);
/*     */     
/* 169 */     propNode.addDisposeListener(new PropNode.DisposeListener()
/*     */         {
/*     */           public void disposed(PropNode disposingNode) {
/* 172 */             ((ModularNode)disposingNode.getRepresentation()).dispose();
/*     */           }
/*     */         });
/*     */     
/* 176 */     propNode.setWorldOriginAligned(false);
/* 177 */     propNode.setAngle(0.0F);
/* 178 */     propNode.updateRenderState();
/*     */     
/* 180 */     return propNode;
/*     */   }
/*     */   
/*     */   private StatComponent.Builder buildStatLabel() {
/* 184 */     return new StatComponent.Builder((BContainer)this, this.localizer, this.statComponents, StatComponent.InfoMode.CHARACTER_STAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 189 */     BComponent component = super.getHitComponent(mx, my);
/* 190 */     if (component == this) {
/* 191 */       return null;
/*     */     }
/* 193 */     return component;
/*     */   }
/*     */ 
/*     */   
/*     */   public void diffChanged(SelectionHandler.DiffChangeEvent event) {
/* 198 */     int attack = this.clientPlayer.getStatSum(Short.valueOf((short)29)).intValue();
/*     */     
/* 200 */     short armorId = (short)(100 + Element.MONSTER.ordinal());
/*     */     
/* 202 */     int armour = this.clientPlayer.getStatSupport().getStatCollection().get(Short.valueOf(armorId)).getSum();
/*     */     
/* 204 */     int health = this.clientPlayer.getStatSum(Short.valueOf((short)11)).intValue();
/* 205 */     int mana = this.clientPlayer.getStatSum(Short.valueOf((short)13)).intValue();
/* 206 */     int crit = this.clientPlayer.getStatSum(Short.valueOf((short)17)).intValue();
/*     */ 
/*     */     
/* 209 */     int[] playerStats = { health, mana, armour, attack, crit };
/*     */     
/* 211 */     for (BLabel label : this.statChangeLabels) {
/* 212 */       label.setText("");
/*     */     }
/*     */     
/* 215 */     if (event.equippedItem != null && event.wardrobeItem != null) {
/* 216 */       double[] maxStats = ClientStatCalc.getAbilityAmounts(new ArchType("", 4.0D, 4.0D, 4.0D, 4.0D, 2.0D), MainGameState.getPlayerModel().getStatSupport().getLevel(), 1);
/*     */ 
/*     */       
/* 219 */       double[] equippedStats = ClientStatCalc.getStats(event.equippedItemStats);
/* 220 */       double[] wardrobeStats = ClientStatCalc.getStats(event.wardrobeItemStats);
/*     */       
/* 222 */       for (int i = 0; i < (GuiStatType.values()).length; i++) {
/* 223 */         this.statDecreaseBars[i].setProgress(0.0F);
/* 224 */         this.statIncreaseBars[i].setProgress(0.0F);
/*     */         
/* 226 */         wardrobeStats[i] = playerStats[i] - equippedStats[i] + wardrobeStats[i];
/* 227 */         equippedStats[i] = playerStats[i];
/*     */         
/* 229 */         float equippedProgress = (float)(equippedStats[i] / maxStats[i]);
/* 230 */         float warddrobeProgress = (float)(wardrobeStats[i] / maxStats[i]);
/* 231 */         equippedProgress = (equippedProgress > 1.0F) ? 1.0F : ((equippedProgress < 0.0F) ? 0.0F : equippedProgress);
/* 232 */         warddrobeProgress = (warddrobeProgress > 1.0F) ? 1.0F : ((warddrobeProgress < 0.0F) ? 0.0F : warddrobeProgress);
/*     */         
/* 234 */         this.statBars[i].setProgress(equippedProgress);
/* 235 */         if (warddrobeProgress < equippedProgress) {
/* 236 */           remove((BComponent)this.statDecreaseBars[i]);
/* 237 */           int glossIndex = getComponentIndex((BComponent)this.statBarGloss[i]);
/* 238 */           add(glossIndex, (BComponent)this.statDecreaseBars[i], new Rectangle((this.STAT_PROGRESS_SIZE[i]).x, (this.STAT_PROGRESS_SIZE[i]).y, (int)((this.STAT_PROGRESS_SIZE[i]).width * equippedProgress), (this.STAT_PROGRESS_SIZE[i]).height));
/*     */ 
/*     */           
/* 241 */           float decreaseProgress = (float)((equippedStats[i] - wardrobeStats[i]) / equippedStats[i]);
/* 242 */           decreaseProgress = (decreaseProgress > 1.0F) ? 1.0F : ((decreaseProgress < 0.0F) ? 0.0F : decreaseProgress);
/* 243 */           this.statDecreaseBars[i].setProgress(decreaseProgress);
/*     */         } else {
/* 245 */           this.statIncreaseBars[i].setProgress(warddrobeProgress);
/*     */         } 
/*     */         
/* 248 */         int statChange = (int)wardrobeStats[i] - (int)equippedStats[i];
/*     */         
/* 250 */         this.statBarGloss[i].setText("" + (playerStats[i] + statChange));
/*     */         
/* 252 */         this.statChangeLabels[i].setColor(0, (statChange < 0) ? ColorRGBA.red : ColorRGBA.green);
/* 253 */         if (statChange != 0) this.statChangeLabels[i].setText("" + ((statChange > 0) ? "+" : "") + statChange);
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CenteredGeomView getCharacter3DView() {
/* 262 */     return this.character3DView;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\CharacterContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */