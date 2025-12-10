/*     */ package com.funcom.tcg.client.ui.character;
import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.equipment.ArchType;
/*     */ import com.funcom.rpgengine2.equipment.PetArchType;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.pets.PetDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.ClientStatCalc;
/*     */ import com.funcom.tcg.client.ui.GuiStatType;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.awt.Point;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class EquipmentStatsContainer extends BContainer implements SelectionHandler.DiffChangeListener {
/*  28 */   private static final Object STATKEY_ITEM_LEVEL = new Object();
/*     */   
/*     */   private final Localizer localizer;
/*     */   
/*     */   private BLabel nameLbl;
/*     */   private BLabel levelLbl;
/*     */   private BLabel iconBgd;
/*     */   private BLabel itemIconLabel;
/*     */   private final Map<Object, StatComponent> statComponents;
/*     */   private BLabel[] statIcons;
/*     */   private BLabel[] progressBgds;
/*     */   private BLabel[] statBarGloss;
/*     */   private BLabel[] statChangeLabels;
/*     */   private BProgressBar[] statBars;
/*     */   private BProgressBar[] statIncreaseBars;
/*     */   private BProgressBar[] statDecreaseBars;
/*     */   private BLabel membersOnly;
/*     */   private boolean added;
/*     */   private BImage icon;
/*     */   private Rectangle[] STAT_PROGRESS_SIZE;
/*     */   private SelectionHandler.DiffChangeEvent lastEvent;
/*     */   private boolean viewingPet = false;
/*     */   
/*     */   public EquipmentStatsContainer(CharacterWindowModel windowModel, ResourceManager resourceManager, Localizer localizer) {
/*  52 */     super((BLayoutManager)new AbsoluteLayout());
/*  53 */     this.localizer = localizer;
/*  54 */     this.statComponents = new HashMap<Object, StatComponent>();
/*     */     
/*  56 */     initComponents();
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  60 */     this.nameLbl = new BLabel("Item Name", "item-name-label");
/*  61 */     add((BComponent)this.nameLbl, new Rectangle(0, 90, 455, 50));
/*     */     
/*  63 */     this.levelLbl = new BLabel("", "equipment-stats-level");
/*  64 */     add((BComponent)this.levelLbl, new Rectangle(0, 15, 130, 30));
/*     */     
/*  66 */     this.iconBgd = new BLabel("", "equipment-stats-item-bgd");
/*  67 */     add((BComponent)this.iconBgd, new Rectangle(33, 46, 64, 64));
/*     */     
/*  69 */     this.itemIconLabel = new BLabel("", "equipment-stats-icon");
/*  70 */     add((BComponent)this.itemIconLabel, new Rectangle(41, 54, 48, 48));
/*     */     
/*  72 */     this.membersOnly = new BLabel("", "members_only." + ((System.getProperty("tcg.locale") != null) ? System.getProperty("tcg.locale") : "en"));
/*  73 */     add((BComponent)this.membersOnly, new Rectangle(0, 46, 130, 64));
/*     */     
/*  75 */     int statTotal = (GuiStatType.values()).length;
/*     */     
/*  77 */     this.statIcons = new BLabel[statTotal];
/*  78 */     this.progressBgds = new BLabel[statTotal];
/*  79 */     this.statIncreaseBars = new BProgressBar[statTotal];
/*  80 */     this.statDecreaseBars = new BProgressBar[statTotal];
/*  81 */     this.statBars = new BProgressBar[statTotal];
/*  82 */     this.statBarGloss = new BLabel[statTotal];
/*  83 */     this.statChangeLabels = new BLabel[statTotal];
/*  84 */     this.STAT_PROGRESS_SIZE = new Rectangle[statTotal];
/*  85 */     for (int i = 0; i < statTotal; i++) {
/*  86 */       String statTypeString = GuiStatType.values()[i].toString().toLowerCase();
/*     */       
/*  88 */       this.statIcons[i] = new BLabel("");
/*  89 */       this.statIcons[i].setStyleClass("icon-stat-" + statTypeString);
/*  90 */       this.statIcons[i].setTooltipText(TcgGame.getLocalizedText("charwindow.info." + statTypeString, new String[0]));
/*     */       
/*  92 */       this.progressBgds[i] = new BLabel("");
/*  93 */       this.progressBgds[i].setStyleClass("progress-bgd");
/*     */       
/*  95 */       this.statIncreaseBars[i] = new BProgressBar();
/*  96 */       this.statIncreaseBars[i].setStyleClass("progress-stat-increase");
/*     */       
/*  98 */       this.statDecreaseBars[i] = new BProgressBar(BProgressBar.Direction.WEST);
/*  99 */       this.statDecreaseBars[i].setStyleClass("progress-stat-decrease");
/*     */       
/* 101 */       this.statBars[i] = new BProgressBar();
/* 102 */       this.statBars[i].setStyleClass("progress-stat-" + statTypeString);
/*     */       
/* 104 */       this.statBarGloss[i] = new BLabel("0");
/* 105 */       this.statBarGloss[i].setStyleClass("progress-stat-gloss");
/*     */       
/* 107 */       this.statChangeLabels[i] = new BLabel("");
/* 108 */       this.statChangeLabels[i].setStyleClass("stat-change-label");
/*     */       
/* 110 */       int inverse = statTotal - 1 - i;
/* 111 */       int height = 20;
/*     */       
/* 113 */       Rectangle STAT_ICON_SIZE = new Rectangle(130, 10 + inverse * height, height, height);
/* 114 */       this.STAT_PROGRESS_SIZE[i] = new Rectangle(140 + height, 10 + inverse * height, 276, height);
/*     */       
/* 116 */       add((BComponent)this.statIcons[i], STAT_ICON_SIZE);
/* 117 */       add((BComponent)this.progressBgds[i], this.STAT_PROGRESS_SIZE[i]);
/* 118 */       add((BComponent)this.statIncreaseBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 119 */       add((BComponent)this.statBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 120 */       add((BComponent)this.statDecreaseBars[i], this.STAT_PROGRESS_SIZE[i]);
/* 121 */       add((BComponent)this.statBarGloss[i], this.STAT_PROGRESS_SIZE[i]);
/*     */       
/* 123 */       add((BComponent)this.statChangeLabels[i], this.STAT_PROGRESS_SIZE[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void diffChanged(SelectionHandler.DiffChangeEvent event) {
/* 129 */     if (this.viewingPet) {
/*     */       return;
/*     */     }
/* 132 */     this.lastEvent = event;
/*     */     
/* 134 */     for (BLabel label : this.statChangeLabels) {
/* 135 */       label.setText("");
/*     */     }
/*     */     
/* 138 */     if (event.equippedItem != null && event.wardrobeItem != null) {
/* 139 */       Map<Object, StatComponent> componentsToSet = new HashMap<Object, StatComponent>(this.statComponents);
/*     */ 
/*     */       
/* 142 */       this.nameLbl.setText(event.wardrobeItem.getName());
/* 143 */       this.levelLbl.setText(TcgGame.getLocalizedText("rewardwindow.level", new String[0]).trim() + ": " + event.wardrobeItem.getLevel());
/* 144 */       this.levelLbl.setVisible(!event.wardrobeItem.getItemType().equals(ItemType.EQUIP_HEAD));
/*     */       
/* 146 */       this.icon = (BImage)TcgGame.getResourceManager().getResource(BImage.class, event.wardrobeItem.getIcon());
/*     */       
/* 148 */       this.membersOnly.setVisible((event.wardrobeItem.isSubscriberOnly() && !MainGameState.isPlayerSubscriber()));
/*     */       
/* 150 */       double[] maxStats = ClientStatCalc.getAbilityAmounts(new ArchType("", 2.0D, 2.0D, 2.0D, 2.0D, 1.0D), MainGameState.getPlayerModel().getStatSupport().getLevel(), 5);
/*     */ 
/*     */       
/* 153 */       double[] equippedStats = ClientStatCalc.getStats(event.equippedItemStats);
/* 154 */       double[] wardrobeStats = ClientStatCalc.getStats(event.wardrobeItemStats);
/*     */ 
/*     */       
/* 157 */       for (int i = 0; i < (GuiStatType.values()).length; i++) {
/* 158 */         this.statDecreaseBars[i].setProgress(0.0F);
/* 159 */         this.statIncreaseBars[i].setProgress(0.0F);
/*     */         
/* 161 */         float equippedProgress = (float)(equippedStats[i] / maxStats[i]);
/* 162 */         float warddrobeProgress = (float)(wardrobeStats[i] / maxStats[i]);
/* 163 */         equippedProgress = (equippedProgress > 1.0F) ? 1.0F : ((equippedProgress < 0.0F) ? 0.0F : equippedProgress);
/* 164 */         warddrobeProgress = (warddrobeProgress > 1.0F) ? 1.0F : ((warddrobeProgress < 0.0F) ? 0.0F : warddrobeProgress);
/*     */         
/* 166 */         this.statBars[i].setProgress(equippedProgress);
/* 167 */         if (warddrobeProgress < equippedProgress) {
/* 168 */           remove((BComponent)this.statDecreaseBars[i]);
/* 169 */           int glossIndex = getComponentIndex((BComponent)this.statBarGloss[i]);
/* 170 */           add(glossIndex, (BComponent)this.statDecreaseBars[i], new Rectangle((this.STAT_PROGRESS_SIZE[i]).x, (this.STAT_PROGRESS_SIZE[i]).y, (int)((this.STAT_PROGRESS_SIZE[i]).width * equippedProgress), (this.STAT_PROGRESS_SIZE[i]).height));
/*     */ 
/*     */           
/* 173 */           float decreaseProgress = (float)((equippedStats[i] - wardrobeStats[i]) / equippedStats[i]);
/* 174 */           decreaseProgress = (decreaseProgress > 1.0F) ? 1.0F : ((decreaseProgress < 0.0F) ? 0.0F : decreaseProgress);
/* 175 */           this.statDecreaseBars[i].setProgress(decreaseProgress);
/*     */         } else {
/* 177 */           this.statIncreaseBars[i].setProgress(warddrobeProgress);
/*     */         } 
/*     */         
/* 180 */         int statChange = (int)wardrobeStats[i] - (int)equippedStats[i];
/* 181 */         this.statChangeLabels[i].setColor(0, (statChange < 0) ? ColorRGBA.red : ColorRGBA.green);
/* 182 */         if (statChange != 0) this.statChangeLabels[i].setText("" + ((statChange > 0) ? "+" : "") + statChange);
/*     */         
/* 184 */         this.statBarGloss[i].setText("" + (int)wardrobeStats[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void petHover(boolean entered, ClientPet pet) {
/* 190 */     this.viewingPet = entered;
/* 191 */     if (entered) {
/*     */       
/* 193 */       this.nameLbl.setText(pet.getName());
/* 194 */       this.levelLbl.setText(TcgGame.getLocalizedText("rewardwindow.level", new String[0]).trim() + ": " + pet.getLevel());
/* 195 */       this.levelLbl.setVisible(true);
/*     */       
/* 197 */       this.icon = (BImage)TcgGame.getResourceManager().getResource(BImage.class, pet.getIcon());
/*     */       
/* 199 */       PetDescription desc = TcgGame.getRpgLoader().getPetManager().getPetDescription(pet.getClassId());
/* 200 */       double[] maxStats = ClientStatCalc.getAbilityAmounts((ArchType)new PetArchType("", 2.0D, 2.0D, 2.0D, 2.0D, 1.0D), MainGameState.getPlayerModel().getStatSupport().getLevel(), 1);
/*     */       
/* 202 */       double[] petStats = ClientStatCalc.getAbilityAmounts((ArchType)desc.getArchType(), pet.getLevel(), 1);
/*     */       
/* 204 */       for (int i = 0; i < (GuiStatType.values()).length; i++) {
/* 205 */         this.statDecreaseBars[i].setProgress(0.0F);
/* 206 */         this.statIncreaseBars[i].setProgress(0.0F);
/*     */         
/* 208 */         float value = (float)(petStats[i] / maxStats[i]);
/* 209 */         value = (value > 1.0F) ? 1.0F : ((value < 0.0F) ? 0.0F : value);
/* 210 */         this.statBars[i].setProgress(value);
/*     */         
/* 212 */         this.statBarGloss[i].setText("" + (int)petStats[i]);
/*     */       } 
/* 214 */     } else if (this.lastEvent != null) {
/* 215 */       diffChanged(this.lastEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 221 */     return "equipment-stats-container";
/*     */   }
/*     */   
/*     */   public void setHoverIcon(BImage icon) {
/* 225 */     if (this.added) {
/* 226 */       if (this.icon != null) {
/* 227 */         this.icon.release();
/*     */       }
/* 229 */       if (icon != null) {
/* 230 */         icon.reference();
/*     */       }
/*     */     } 
/* 233 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   protected int getIconSize() {
/* 237 */     Insets insets = this.itemIconLabel.getInsets();
/*     */     
/* 239 */     int w = this.itemIconLabel.getWidth() - insets.getHorizontal();
/* 240 */     int h = this.itemIconLabel.getHeight() - insets.getVertical();
/* 241 */     return Math.min(w, h);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 246 */     super.renderComponent(renderer);
/*     */ 
/*     */ 
/*     */     
/* 250 */     Point pos = new Point(this.itemIconLabel.getAbsoluteX() - getAbsoluteX(), this.itemIconLabel.getAbsoluteY() - getAbsoluteY());
/*     */     
/* 252 */     int size = getIconSize();
/*     */     
/* 254 */     this.iconBgd.setVisible(renderIcon(renderer, pos, size));
/*     */     
/* 256 */     if (this.membersOnly.isVisible()) this.membersOnly.render(renderer); 
/*     */   }
/*     */   
/*     */   protected boolean renderIcon(Renderer renderer, Point pos, int size) {
/* 260 */     if (this.icon != null) {
/* 261 */       this.icon.render(renderer, pos.x, pos.y, size, size, getAlpha());
/*     */       
/* 263 */       return true;
/*     */     } 
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 270 */     this.added = true;
/* 271 */     super.wasAdded();
/* 272 */     if (this.icon != null) {
/* 273 */       this.icon.reference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/* 279 */     if (this.icon != null) {
/* 280 */       this.icon.release();
/*     */     }
/* 282 */     super.wasRemoved();
/* 283 */     this.added = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 288 */     super.setVisible(visible);
/* 289 */     this.membersOnly.setVisible(false);
/* 290 */     if (this.lastEvent != null) {
/* 291 */       this.levelLbl.setVisible(!this.lastEvent.equippedItem.getItemType().equals(ItemType.EQUIP_HEAD));
/*     */     } else {
/* 293 */       this.levelLbl.setVisible(false);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\EquipmentStatsContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */