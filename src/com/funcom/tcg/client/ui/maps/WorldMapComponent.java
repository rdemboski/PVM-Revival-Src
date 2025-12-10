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
/*     */ public class WorldMapComponent
/*     */   extends BContainer
/*     */ {
/*     */   private MapWindow2 mapWindow;
/*     */   private BLabel hoverLabel;
/*     */   private ResourceManager resourceManager;
/*     */   public WorldMapButton seabedButton;
/*     */   public WorldMapButton toxicSwampButton;
/*     */   public WorldMapButton lavaButton;
/*     */   public WorldMapButton dryPlainsButton;
/*     */   public WorldMapButton humidJungleButton;
/*     */   public WorldMapButton hauntedRuinsButton;
/*     */   public WorldMapButton railroadButton;
/*     */   public WorldMapButton cloudsButton;
/*     */   public WorldMapButton treetopButton;
/*  42 */   private static final Rectangle seabedBounds = new Rectangle(125, 142, 250, 225);
/*  43 */   private static final Rectangle toxicSwampBounds = new Rectangle(516, 256, 236, 162);
/*  44 */   private static final Rectangle lavaBounds = new Rectangle(385, 250, 216, 221);
/*  45 */   private static final Rectangle dryPlainsBounds = new Rectangle(277, 0, 339, 208);
/*  46 */   private static final Rectangle humidJungleBounds = new Rectangle(518, 32, 271, 202);
/*  47 */   private static final Rectangle hauntedRuinsBounds = new Rectangle(598, 142, 238, 197);
/*  48 */   private static final Rectangle railroadBounds = new Rectangle(375, 92, 287, 260);
/*  49 */   private static final Rectangle cloudsBounds = new Rectangle(535, 340, 318, 159);
/*  50 */   private static final Rectangle treetopBounds = new Rectangle(248, 173, 241, 249);
/*     */ 
/*     */   
/*     */   public WorldMapComponent(MapWindow2 mapWindow, BLabel hoverLabel, ResourceManager resourceManager) {
/*  54 */     super((BLayoutManager)new AbsoluteLayout());
/*  55 */     this.mapWindow = mapWindow;
/*  56 */     this.hoverLabel = hoverLabel;
/*  57 */     this.resourceManager = resourceManager;
/*  58 */     setSize(955, 505);
/*     */ 
/*     */     
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  66 */     return "worldmapcomp";
/*     */   }
/*     */   
/*     */   public void updateButtons() {
/*  70 */     this.seabedButton.reinitialize();
/*  71 */     this.toxicSwampButton.reinitialize();
/*  72 */     this.lavaButton.reinitialize();
/*  73 */     this.dryPlainsButton.reinitialize();
/*  74 */     this.humidJungleButton.reinitialize();
/*  75 */     this.hauntedRuinsButton.reinitialize();
/*  76 */     this.railroadButton.reinitialize();
/*  77 */     this.cloudsButton.reinitialize();
/*  78 */     this.treetopButton.reinitialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  82 */     this.lavaButton = new WorldMapButton("014_br_lava/");
/*  83 */     this.lavaButton.setStyleClass("lava.button");
/*  84 */     add((BComponent)this.lavaButton, lavaBounds);
/*  85 */     this.toxicSwampButton = new WorldMapButton("013_gb_toxicswamp/");
/*  86 */     this.toxicSwampButton.setStyleClass("toxic.button");
/*  87 */     add((BComponent)this.toxicSwampButton, toxicSwampBounds);
/*  88 */     this.cloudsButton = new WorldMapButton("012_rg_clouds/");
/*  89 */     this.cloudsButton.setStyleClass("clouds.button");
/*  90 */     add((BComponent)this.cloudsButton, cloudsBounds);
/*  91 */     this.hauntedRuinsButton = new WorldMapButton("004_b_hauntedruins/");
/*  92 */     this.hauntedRuinsButton.setStyleClass("hauntedruins.button");
/*  93 */     add((BComponent)this.hauntedRuinsButton, hauntedRuinsBounds);
/*  94 */     this.seabedButton = new WorldMapButton("009_b_mysteriousseabed/");
/*  95 */     this.seabedButton.setStyleClass("seabed.button");
/*  96 */     add((BComponent)this.seabedButton, seabedBounds);
/*  97 */     this.treetopButton = new WorldMapButton("008_r_treetoptrail/");
/*  98 */     this.treetopButton.setStyleClass("treetop.button");
/*  99 */     add((BComponent)this.treetopButton, treetopBounds);
/* 100 */     this.railroadButton = new WorldMapButton("007_g_railroadplateau/");
/* 101 */     this.railroadButton.setStyleClass("railroad.button");
/* 102 */     add((BComponent)this.railroadButton, railroadBounds);
/* 103 */     this.humidJungleButton = new WorldMapButton("003_g_humidjungle/");
/* 104 */     this.humidJungleButton.setStyleClass("humidjungle.button");
/* 105 */     add((BComponent)this.humidJungleButton, humidJungleBounds);
/* 106 */     this.dryPlainsButton = new WorldMapButton("002_r_dryplains/")
/*     */       {
/*     */         protected String getDestMap()
/*     */         {
/* 110 */           if (WorldMapComponent.this.mapWindow.getWindowModel().showTutorialMap())
/* 111 */             return super.getDestMap(); 
/* 112 */           return "002_r_dryplains_copy/";
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean meetsBasicCondition(String destMap) {
/* 117 */           return WorldMapComponent.this.mapWindow.getWindowModel().hasNotVisitedMap(FileUtils.trimTailingSlashes(super.getDestMap()));
/*     */         }
/*     */       };
/* 120 */     this.dryPlainsButton.setStyleClass("dryplains.button");
/* 121 */     add((BComponent)this.dryPlainsButton, dryPlainsBounds);
/*     */   }
/*     */   
/*     */   private class MapButtonListener implements ActionListener {
/*     */     private MapModel mapModel;
/*     */     
/*     */     private MapButtonListener(MapModel mapModel) {
/* 128 */       this.mapModel = mapModel;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 133 */       WorldMapComponent.this.mapWindow.reset(this.mapModel);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WorldMapButton extends IrregularButton {
/*     */     boolean initialized = false;
/*     */     private String destMap;
/*     */     public MapModel model;
/*     */     
/*     */     public WorldMapButton(String destMap) {
/* 143 */       super("");
/* 144 */       this.destMap = destMap;
/* 145 */       initializeButton(getDestMap());
/*     */     }
/*     */     
/*     */     public void reinitialize() {
/* 149 */       if (!this.initialized)
/* 150 */         initializeButton(getDestMap()); 
/*     */     }
/*     */     
/*     */     protected String getDestMap() {
/* 154 */       return this.destMap;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void stateDidChange() {
/* 159 */       super.stateDidChange();
/*     */       
/* 161 */       if (this.initialized) {
/* 162 */         switch (getState()) {
/*     */           case 1:
/* 164 */             WorldMapComponent.this.hoverLabel.setText(WorldMapComponent.this.mapWindow.getWindowModel().getLocalizer().getLocalizedText(WorldMapComponent.class, this.model.getNameKey(), new String[0]));
/*     */           
/*     */           case 3:
/*     */             return;
/*     */         } 
/* 169 */         WorldMapComponent.this.hoverLabel.setText("");
/*     */       } 
/*     */     }
/*     */     
/*     */     private void initializeButton(String destMap) {
/* 174 */       if (meetsBasicCondition(destMap)) {
/* 175 */         setEnabled(false);
/*     */         return;
/*     */       } 
/* 178 */       ChunkWorldInfo info = new ChunkWorldInfo(destMap);
/* 179 */       info.setBasePath(destMap);
/* 180 */       String mapConfigName = destMap + "config.bunk";
/*     */       try {
/* 182 */         ByteBuffer blob = (ByteBuffer)WorldMapComponent.this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/* 183 */         Document mapConfig = BinaryLoader.convertBlobToMap(blob, WorldMapComponent.this.mapWindow.getWindowModel().getResourceGetter());
/* 184 */         info.loadConfig(mapConfig);
/* 185 */         List<MapObject> mapObjects = (new MapObjectBuilder(WorldMapComponent.this.mapWindow.getWindowModel().getResourceGetter())).getMapObjectList(info);
/* 186 */         this.model = new MapModel(info, mapObjects);
/* 187 */         addListener((ComponentListener)new WorldMapComponent.MapButtonListener(this.model));
/* 188 */         setEnabled(true);
/* 189 */         this.initialized = true;
/* 190 */       } catch (ResourceManagerException e) {
/*     */         
/* 192 */         setEnabled(false);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected boolean meetsBasicCondition(String destMap) {
/* 197 */       return WorldMapComponent.this.mapWindow.getWindowModel().hasNotVisitedMap(FileUtils.trimTailingSlashes(destMap));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\WorldMapComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */