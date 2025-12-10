/*     */ package com.funcom.tcg.client.ui.reward;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.EffectDescription;
/*     */ import com.funcom.commons.dfx.MeshEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.dfx.ClientDFXResourceLoader;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.PreviewPetModularNode;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class RewardWindow extends BWindow implements PartiallyNotInteractive {
/*     */   protected static final float DEFAULT_ANGLE = 0.5235988F;
/*     */   private static final int WINDOW_WIDTH = 700;
/*     */   public static final String FIRST_NUMBER = "firstnumbergoeshere";
/*  47 */   private BLabel youLabel = (BLabel)new BClickthroughLabel("you"); public static final String SECOND_NUMBER = "secondnumbergoeshere"; private ResourceManager resourceManager; private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*  48 */   private BLabel bigText = (BLabel)new BClickthroughLabel("big");
/*  49 */   private BLabel nameLabel = (BLabel)new BClickthroughLabel("name");
/*  50 */   private BLabel tierLabel = (BLabel)new BClickthroughLabel("tier");
/*  51 */   private RewardGeomView geomView = new RewardGeomView();
/*     */   
/*  53 */   private static final Rectangle SIZE_YOU = new Rectangle(0, 368, 700, 52);
/*  54 */   private static final Rectangle SIZE_BIG = new Rectangle(0, 300, 700, 75);
/*  55 */   private static final Rectangle SIZE_GEOM_CONTAINER = new Rectangle(0, 60, 700, 268);
/*  56 */   private static final Rectangle SIZE_NAME = new Rectangle(0, 42, 700, 52);
/*  57 */   private static final Rectangle SIZE_TIER = new Rectangle(0, 0, 700, 52);
/*     */   
/*     */   public RewardWindow(ResourceManager resourceManager, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/*  60 */     super(BuiUtils.createMergedClassStyleSheets(RewardWindow.class, new BananaResourceProvider(resourceManager)), (BLayoutManager)new AbsoluteLayout());
/*  61 */     this.resourceManager = resourceManager;
/*  62 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*     */     
/*  64 */     this.youLabel.setStyleClass("label.small");
/*  65 */     this.bigText.setStyleClass("label.big");
/*  66 */     this.geomView.setStyleClass("geomview-frame");
/*  67 */     this.nameLabel.setStyleClass("label.small");
/*  68 */     this.tierLabel.setStyleClass("label.small");
/*     */     
/*  70 */     add((BComponent)this.youLabel, SIZE_YOU);
/*  71 */     add((BComponent)this.bigText, SIZE_BIG);
/*  72 */     add((BComponent)this.geomView, SIZE_GEOM_CONTAINER);
/*  73 */     add((BComponent)this.nameLabel, SIZE_NAME);
/*  74 */     add((BComponent)this.tierLabel, SIZE_TIER);
/*  75 */     setSize(700, 480);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReward(Object reward) {
/*  80 */     if (reward instanceof ClientPet) {
/*  81 */       ClientPet pet = (ClientPet)reward;
/*  82 */       this.youLabel.setText(TcgGame.getLocalizedText("rewardwindow.youfounda", new String[0]));
/*  83 */       this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.newpet", new String[0]));
/*  84 */       this.nameLabel.setText(pet.getName());
/*  85 */       this.tierLabel.setText("");
/*     */       
/*  87 */       this.geomView.setRotationSpeed(-2.5F);
/*  88 */       this.geomView.setGeometry(createPetNode(pet));
/*  89 */       this.geomView.update(1.0F);
/*  90 */       this.geomView.resetCameraPosition();
/*     */     }
/*  92 */     else if (reward instanceof Integer) {
/*  93 */       this.youLabel.setText(TcgGame.getLocalizedText("rewardwindow.youarenow", new String[0]));
/*  94 */       this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.level", new String[0]) + reward + "!");
/*  95 */       this.nameLabel.setText("");
/*  96 */       this.tierLabel.setText("");
/*  97 */       this.geomView.setRotationSpeed(-2.5F);
/*  98 */       this.geomView.setGeometry(null);
/*  99 */       this.geomView.update(1.0F);
/* 100 */       this.geomView.resetCameraPosition();
/*     */     }
/* 102 */     else if (reward instanceof ItemDescription) {
/* 103 */       ItemDescription itemDesc = (ItemDescription)reward;
/* 104 */       this.nameLabel.setText(itemDesc.getName());
/* 105 */       if (itemDesc.getItemType() == ItemType.SKILL) {
/* 106 */         this.youLabel.setText(TcgGame.getLocalizedText("rewardwindow.youfounda", new String[0]));
/* 107 */         if (itemDesc.getTier() > 1) {
/* 108 */           this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.skillupgrade", new String[0]));
/* 109 */           this.tierLabel.setText(TcgGame.getLocalizedText("rewardwindow.tier", new String[0]) + itemDesc.getTier());
/*     */         } else {
/* 111 */           this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.newskill", new String[0]));
/* 112 */           this.tierLabel.setText("");
/*     */         }
/*     */       
/* 115 */       } else if (itemDesc.getTier() > 1) {
/* 116 */         this.youLabel.setText(TcgGame.getLocalizedText("rewardwindow.youfoundan", new String[0]));
/* 117 */         this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.itemupgrade", new String[0]));
/* 118 */         this.tierLabel.setText(TcgGame.getLocalizedText("rewardwindow.tier", new String[0]) + itemDesc.getTier());
/*     */       } else {
/* 120 */         this.youLabel.setText(TcgGame.getLocalizedText("rewardwindow.youfounda", new String[0]));
/* 121 */         this.bigText.setText(TcgGame.getLocalizedText("rewardwindow.newitem", new String[0]));
/* 122 */         this.tierLabel.setText("");
/*     */       } 
/*     */       
/* 125 */       this.geomView.setRotationSpeed(0.0F);
/* 126 */       this.geomView.setGeometry(createCardNode((ItemDescription)reward));
/* 127 */       this.geomView.update(0.22F);
/* 128 */       this.geomView.resetCameraPosition();
/*     */     } 
/*     */     
/* 131 */     layout();
/*     */   }
/*     */   
/*     */   private PropNode createLevelNode(Integer integer) {
/* 135 */     Prop prop = new Prop("card preview", new WorldCoordinate());
/* 136 */     PropNode propNode = new PropNode(prop, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */     
/* 138 */     propNode.initializeEffects((ParticleSurface)this.geomView);
/*     */     
/* 140 */     if (integer.intValue() < 10) {
/* 141 */       String dfxScript = "xml/dfx/numberstar_singledigit.xml";
/* 142 */       createLevelStarDfx(integer, propNode, dfxScript);
/* 143 */     } else if (integer.intValue() < 100) {
/* 144 */       String dfxScript = "xml/dfx/numberstar_doubledigit.xml";
/* 145 */       createLevelStarDfx(integer, propNode, dfxScript);
/*     */     } 
/*     */     
/* 148 */     propNode.setAngle(0.5235988F);
/* 149 */     return propNode;
/*     */   }
/*     */   
/*     */   private void createLevelStarDfx(Integer integer, PropNode propNode, String dfxScript) {
/* 153 */     Element effectRootElement = (new ClientDFXResourceLoader(TcgGame.getResourceManager(), TcgGame.getParticleProcessor())).getDireEffectData(dfxScript, false);
/* 154 */     DireEffectDescription stateDFXDescription = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(effectRootElement, dfxScript, false);
/* 155 */     if (!stateDFXDescription.isEmpty()) {
/* 156 */       for (EffectDescription effectDescription : stateDFXDescription.getEffectDescriptions()) {
/* 157 */         if (effectDescription instanceof MeshEffectDescription) {
/* 158 */           List<Element> children = ((MeshEffectDescription)effectDescription).getDfx().getChildren("Effect");
/* 159 */           for (Element element : children) {
/* 160 */             if (element.getChild("Type").getValue().equalsIgnoreCase("Mesh")) {
/* 161 */               Element child = element.getChild("Resource");
/* 162 */               String s = child.getText().replace("firstnumbergoeshere", integer.toString().substring(0, 1)).replace("secondnumbergoeshere", integer.toString().substring(1));
/* 163 */               child.setText(s);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 168 */       ((MeshEffectDescription)stateDFXDescription.getEffectDescriptions().get(0)).getDfx().getChildren("Effect");
/* 169 */       propNode.addDfx(stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS));
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropNode createCardNode(ItemDescription description) {
/* 174 */     Prop prop = new Prop("card preview", new WorldCoordinate());
/* 175 */     PropNode propNode = new PropNode(prop, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */     
/* 177 */     propNode.setScale(5.0F);
/* 178 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, "characters/xml/global/itemdesc_card.xml", CacheType.CACHE_TEMPORARILY);
/* 179 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/* 180 */     ModularNode modularNode = new ModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getResourceManager());
/*     */ 
/*     */ 
/*     */     
/* 184 */     modularNode.setTextureLoaderFactoryManager(MainGameState.getTextureLoaderFactoryManager());
/* 185 */     Map<Object, Object> runtimeParams = new HashMap<Object, Object>();
/* 186 */     runtimeParams.put(ItemDescription.class, description);
/* 187 */     modularNode.setTextureLoaderRuntimeParams(runtimeParams);
/*     */     
/* 189 */     modularNode.reloadCharacter();
/* 190 */     propNode.attachRepresentation((Spatial)modularNode);
/* 191 */     propNode.initializeEffects((ParticleSurface)this.geomView);
/*     */     
/* 193 */     return propNode;
/*     */   }
/*     */   
/*     */   private PropNode createPetNode(ClientPet petListItem) {
/* 197 */     ModularDescription modularDescription = petListItem.getPetDescription();
/* 198 */     Prop prop = new Prop("pet preview", new WorldCoordinate());
/* 199 */     PropNode propNode = new PropNode(prop, 3, "", this.direEffectDescriptionFactory);
/*     */     
/* 201 */     PreviewPetModularNode previewPetModularNode = new PreviewPetModularNode(modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, this.resourceManager);
/*     */ 
/*     */     
/* 204 */     previewPetModularNode.reloadCharacter();
/* 205 */     propNode.attachRepresentation((Spatial)previewPetModularNode);
/*     */     
/* 207 */     propNode.setWorldOriginAligned(false);
/* 208 */     propNode.setAngle(0.5235988F);
/*     */     
/* 210 */     propNode.initializeEffects((ParticleSurface)this.geomView);
/* 211 */     CreatureVisualDescription visuals = petListItem.getPetVisuals();
/* 212 */     if (!visuals.getAlwaysOnDfx().isEmpty()) {
/*     */       try {
/* 214 */         DireEffectDescription alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(visuals.getAlwaysOnDfx(), false);
/* 215 */         if (!alwaysOnDfxDescription.isEmpty()) {
/* 216 */           DireEffect dfx = alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 217 */           propNode.addDfx(dfx);
/*     */         } 
/* 219 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */ 
/*     */     
/* 223 */     return propNode;
/*     */   }
/*     */   
/*     */   public void setTopAlpha(float topAlpha) {
/* 227 */     this.youLabel.setAlpha(topAlpha);
/* 228 */     this.bigText.setAlpha(topAlpha);
/*     */   }
/*     */   
/*     */   public void setBottomAlpha(float bottomAlpha) {
/* 232 */     this.nameLabel.setAlpha(bottomAlpha);
/* 233 */     this.tierLabel.setAlpha(bottomAlpha);
/*     */   }
/*     */   
/*     */   public void setGeomAlpha(float geomAlpha) {
/* 237 */     this.geomView.setAlpha(geomAlpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int i, int i1) {
/* 247 */     return null;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 251 */     this.geomView.setGeometry(null);
/* 252 */     this.youLabel.setText("");
/* 253 */     this.bigText.setText("");
/* 254 */     this.nameLabel.setText("");
/* 255 */     this.tierLabel.setText("");
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\reward\RewardWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */