/*     */ package com.funcom.tcg.client.ui.maps;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*     */ import com.funcom.gameengine.model.factories.MapObjectBuilder;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*     */ import com.funcom.gameengine.view.MapObject;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.io.File;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapComponent2
/*     */   extends BContainer
/*     */ {
/*     */   private MapWindow2 mapWindow;
/*     */   private BLabel hoverLabel;
/*     */   private ResourceManager resourceManager;
/*     */   private BContainer mainContainer;
/*     */   private WorldMapButton dryplainsaButton;
/*     */   private WorldMapButton dryplainsbButton;
/*     */   private WorldMapButton charredButton;
/*     */   private WorldMapButton hubcityButton;
/*     */   private WorldMapButton lavabossButton;
/*     */   private WorldMapButton mountainButton;
/*     */   private WorldMapButton mushroomButton;
/*     */   private WorldMapButton riversideButton;
/*     */   private WorldMapButton seacaveButton;
/*     */   private WorldMapButton spiderlairButton;
/*     */   private WorldMapButton thawedButton;
/*     */   private WorldMapButton seabedaButton;
/*     */   private WorldMapButton railroadaButton;
/*     */   private WorldMapButton railroadbButton;
/*     */   private WorldMapButton cloudsaButton;
/*     */   private WorldMapButton cloudsbButton;
/*     */   private WorldMapButton ruinsaButton;
/*     */   private WorldMapButton ruinsbButton;
/*     */   private WorldMapButton lavaaButton;
/*     */   private WorldMapButton lavabButton;
/*     */   private WorldMapButton toxicaButton;
/*     */   private WorldMapButton toxicbButton;
/*     */   private WorldMapButton toxiccButton;
/*     */   private WorldMapButton humidaButton;
/*     */   private WorldMapButton humidbButton;
/*     */   private WorldMapButton treetopaButton;
/*     */   private WorldMapButton treetopbButton;
/*     */   private WorldMapButton seabedbButton;
/*     */   private BComponent heroZonesButton;
/*     */   
/*     */   public WorldMapComponent2(BananaPeel bananaPeel, ResourceManager resourceManager, BLabel hoverLabel, MapWindow2 mapWindow) {
/*  70 */     super((BLayoutManager)new AbsoluteLayout());
/*  71 */     this.resourceManager = resourceManager;
/*  72 */     this.hoverLabel = hoverLabel;
/*  73 */     this.mapWindow = mapWindow;
/*  74 */     this.mainContainer = bananaPeel.getTopComponents().iterator().next();
/*  75 */     setSize(this.mainContainer.getWidth(), this.mainContainer.getHeight());
/*  76 */     add((BComponent)this.mainContainer, new Rectangle(0, 0, getWidth(), getHeight()));
/*     */     
/*  78 */     createButtons();
/*     */   }
/*     */   
/*     */   private void createButtons() {
/*  82 */     this.charredButton = new WorldMapButton("026_rgb_charred_forest/");
/*  83 */     setUpButton(this.charredButton, "button_charred");
/*     */     
/*  85 */     this.dryplainsaButton = new WorldMapButton("002_r_dryplains_01/");
/*  86 */     setUpButton(this.dryplainsaButton, "button_dryplains_a");
/*     */     
/*  88 */     this.dryplainsbButton = new WorldMapButton("002_r_dryplains_02/");
/*  89 */     setUpButton(this.dryplainsbButton, "button_dryplains_b");
/*     */     
/*  91 */     this.hubcityButton = new WorldMapButton("001_rbg_research_centre/");
/*  92 */     setUpButton(this.hubcityButton, "button_hubcity");
/*     */     
/*  94 */     this.lavabossButton = new WorldMapButton("015_br_lavaboss/");
/*  95 */     setUpButton(this.lavabossButton, "button_lavaboss");
/*     */     
/*  97 */     this.mountainButton = new WorldMapButton("025_rgb_mountain_fountain/");
/*  98 */     setUpButton(this.mountainButton, "button_mountain");
/*     */     
/* 100 */     this.mushroomButton = new WorldMapButton("027_rgb_mushroom_tunnels/");
/* 101 */     setUpButton(this.mushroomButton, "button_mushroom");
/*     */     
/* 103 */     this.riversideButton = new WorldMapButton("023_rgb_riverside_canyon/");
/* 104 */     setUpButton(this.riversideButton, "button_riverside");
/*     */     
/* 106 */     this.seacaveButton = new WorldMapButton("010_rb_icecave/");
/* 107 */     setUpButton(this.seacaveButton, "button_seacave");
/*     */     
/* 109 */     this.spiderlairButton = new WorldMapButton("005_gb_spiderqueenlair/");
/* 110 */     setUpButton(this.spiderlairButton, "button_spiderlair");
/*     */     
/* 112 */     this.thawedButton = new WorldMapButton("024_rgb_thawing_forest/");
/* 113 */     setUpButton(this.thawedButton, "button_thawed");
/*     */     
/* 115 */     this.seabedaButton = new WorldMapButton("009_b_mysteriousseabed_01/");
/* 116 */     setUpButton(this.seabedaButton, "button_mysteriousseabed_a");
/*     */     
/* 118 */     this.railroadaButton = new WorldMapButton("007_g_railroadplateau_01/");
/* 119 */     setUpButton(this.railroadaButton, "button_railroadplateau_a");
/*     */     
/* 121 */     this.railroadbButton = new WorldMapButton("007_g_railroadplateau_02/");
/* 122 */     setUpButton(this.railroadbButton, "button_railroadplateau_b");
/*     */     
/* 124 */     this.cloudsaButton = new WorldMapButton("012_rg_clouds_01/");
/* 125 */     setUpButton(this.cloudsaButton, "button_clouds_a");
/*     */     
/* 127 */     this.cloudsbButton = new WorldMapButton("012_rg_clouds_02/");
/* 128 */     setUpButton(this.cloudsbButton, "button_clouds_b");
/*     */     
/* 130 */     this.ruinsaButton = new WorldMapButton("004_b_hauntedruins_01/");
/* 131 */     setUpButton(this.ruinsaButton, "button_hauntedruins_a");
/*     */     
/* 133 */     this.ruinsbButton = new WorldMapButton("004_b_hauntedruins_02/");
/* 134 */     setUpButton(this.ruinsbButton, "button_hauntedruins_b");
/*     */     
/* 136 */     this.lavaaButton = new WorldMapButton("014_br_lava_01/");
/* 137 */     setUpButton(this.lavaaButton, "button_lava_a");
/*     */     
/* 139 */     this.lavabButton = new WorldMapButton("014_br_lava_02/");
/* 140 */     setUpButton(this.lavabButton, "button_lava_b");
/*     */     
/* 142 */     this.toxicaButton = new WorldMapButton("013_gb_toxicswamp_01/");
/* 143 */     setUpButton(this.toxicaButton, "button_toxicswamp_a");
/*     */     
/* 145 */     this.toxicbButton = new WorldMapButton("013_gb_toxicswamp_02/");
/* 146 */     setUpButton(this.toxicbButton, "button_toxicswamp_b");
/*     */     
/* 148 */     this.toxiccButton = new WorldMapButton("013_gb_toxicswamp_03/");
/* 149 */     setUpButton(this.toxiccButton, "button_toxicswamp_c");
/*     */     
/* 151 */     this.humidaButton = new WorldMapButton("003_g_humidjungle_01/");
/* 152 */     setUpButton(this.humidaButton, "button_humidjungle_a");
/*     */     
/* 154 */     this.humidbButton = new WorldMapButton("003_g_humidjungle_02/");
/* 155 */     setUpButton(this.humidbButton, "button_humidjungle_b");
/*     */     
/* 157 */     this.treetopaButton = new WorldMapButton("008_r_treetoptrail_01/");
/* 158 */     setUpButton(this.treetopaButton, "button_treetop_a");
/*     */     
/* 160 */     this.treetopbButton = new WorldMapButton("008_r_treetoptrail_02/");
/* 161 */     setUpButton(this.treetopbButton, "button_treetop_b");
/*     */     
/* 163 */     this.seabedbButton = new WorldMapButton("009_b_mysteriousseabed_02/");
/* 164 */     setUpButton(this.seabedbButton, "button_mysteriousseabed_b");
/*     */     
/* 166 */     this.heroZonesButton = findComponent(this, "button_herozones");
/*     */   }
/*     */   
/*     */   public void updateButtons() {
/* 170 */     this.dryplainsaButton.reinitialize();
/* 171 */     this.dryplainsbButton.reinitialize();
/* 172 */     this.charredButton.reinitialize();
/* 173 */     this.hubcityButton.reinitialize();
/* 174 */     this.lavabossButton.reinitialize();
/* 175 */     this.mountainButton.reinitialize();
/* 176 */     this.mushroomButton.reinitialize();
/* 177 */     this.riversideButton.reinitialize();
/* 178 */     this.seacaveButton.reinitialize();
/* 179 */     this.spiderlairButton.reinitialize();
/* 180 */     this.thawedButton.reinitialize();
/* 181 */     this.seabedaButton.reinitialize();
/* 182 */     this.railroadaButton.reinitialize();
/* 183 */     this.railroadbButton.reinitialize();
/* 184 */     this.cloudsaButton.reinitialize();
/* 185 */     this.cloudsbButton.reinitialize();
/* 186 */     this.ruinsaButton.reinitialize();
/* 187 */     this.ruinsbButton.reinitialize();
/* 188 */     this.lavaaButton.reinitialize();
/* 189 */     this.lavabButton.reinitialize();
/* 190 */     this.toxicaButton.reinitialize();
/* 191 */     this.toxicbButton.reinitialize();
/* 192 */     this.toxiccButton.reinitialize();
/* 193 */     this.humidaButton.reinitialize();
/* 194 */     this.humidbButton.reinitialize();
/* 195 */     this.treetopaButton.reinitialize();
/* 196 */     this.treetopbButton.reinitialize();
/* 197 */     this.seabedbButton.reinitialize();
/* 198 */     this.heroZonesButton.setVisible(false);
/*     */   }
/*     */   
/*     */   private void setUpButton(WorldMapButton tempButton, String compName) {
/* 202 */     BComponent tempComponent = findComponent(this, compName);
/* 203 */     tempButton.setStyleClass(tempComponent.getStyleClass());
/* 204 */     add((BComponent)tempButton, tempComponent.getBounds());
/* 205 */     this.mainContainer.remove(tempComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent findComponent(BContainer rootComponent, String componentName) {
/* 210 */     for (int i = 0; i < rootComponent.getComponentCount(); i++) {
/* 211 */       BComponent component = rootComponent.getComponent(i);
/* 212 */       if (componentName.equals(component.getName())) {
/* 213 */         return component;
/*     */       }
/* 215 */       if (component instanceof BContainer) {
/* 216 */         BComponent foundComponent = findComponent((BContainer)component, componentName);
/* 217 */         if (foundComponent != null)
/* 218 */           return foundComponent; 
/*     */       } 
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   private class MapButtonListener implements ActionListener {
/*     */     private MapModel mapModel;
/*     */     
/*     */     private MapButtonListener(MapModel mapModel) {
/* 228 */       this.mapModel = mapModel;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 233 */       TcgUI.getUISoundPlayer().play("ClickForward");
/* 234 */       WorldMapComponent2.this.mapWindow.reset(this.mapModel);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WorldMapButton extends IrregularButton {
/*     */     boolean initialized = false;
/*     */     private String destMap;
/*     */     public MapModel model;
/*     */     
/*     */     public WorldMapButton(String destMap) {
/* 244 */       super("");
/* 245 */       this.destMap = destMap;
/* 246 */       initializeButton(getDestMap());
/*     */     }
/*     */     
/*     */     public void reinitialize() {
/* 250 */       if (!this.initialized)
/* 251 */         initializeButton(getDestMap()); 
/*     */     }
/*     */     
/*     */     protected String getDestMap() {
/* 255 */       return this.destMap;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stateDidChange() {
/* 260 */       super.stateDidChange();
/*     */       
/* 262 */       if (this.initialized) {
/* 263 */         switch (getState()) {
/*     */           case 1:
/* 265 */             WorldMapComponent2.this.hoverLabel.setText(WorldMapComponent2.this.mapWindow.getWindowModel().getLocalizer().getLocalizedText(WorldMapComponent.class, this.model.getNameKey(), new String[0]));
/*     */           
/*     */           case 3:
/*     */             return;
/*     */         } 
/*     */         
/* 271 */         WorldMapComponent2.this.hoverLabel.setText("");
/*     */       } 
/*     */     }
/*     */     
/*     */     private void initializeButton(String destMap) {
/* 276 */       if (meetsBasicCondition(destMap)) {
/* 277 */         setVisible(false);
/*     */         return;
/*     */       } 
/* 280 */       ChunkWorldInfo info = new ChunkWorldInfo(destMap);
/* 281 */       info.setBasePath(destMap);
/* 282 */       String mapConfigName = destMap + "config.bunk";
/*     */       try {
/* 284 */         ByteBuffer blob = (ByteBuffer)WorldMapComponent2.this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */         
/* 286 */         Document mapConfig = BinaryLoader.convertBlobToMap(blob, WorldMapComponent2.this.mapWindow.getWindowModel().getResourceGetter());
/* 287 */         info.loadConfig(mapConfig);
/* 288 */         List<MapObject> mapObjects = (new MapObjectBuilder(WorldMapComponent2.this.mapWindow.getWindowModel().getResourceGetter())).getMapObjectList(info);
/*     */         
/* 290 */         this.model = new MapModel(info, mapObjects);
/* 291 */         addListener((ComponentListener)new WorldMapComponent2.MapButtonListener(this.model));
/* 292 */         setVisible(true);
/* 293 */         this.initialized = true;
/* 294 */       } catch (ResourceManagerException e) {
/*     */         
/* 296 */         setVisible(false);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected boolean meetsBasicCondition(String destMap) {
/* 301 */       return WorldMapComponent2.this.mapWindow.getWindowModel().hasNotVisitedMap(FileUtils.trimTailingSlashes(destMap));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\WorldMapComponent2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */