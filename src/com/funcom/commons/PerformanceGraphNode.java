/*     */ package com.funcom.commons;
/*     */ 
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import com.jme.util.stat.StatType;
/*     */ import com.jme.util.stat.graph.AbstractStatGrapher;
/*     */ import com.jme.util.stat.graph.GraphFactory;
/*     */ import com.jme.util.stat.graph.LineGrapher;
/*     */ import com.jme.util.stat.graph.TabledLabelGrapher;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerformanceGraphNode
/*     */   extends Node
/*     */ {
/*  23 */   private static final Logger LOGGER = Logger.getLogger(PerformanceGraphNode.class);
/*     */   
/*     */   private Quad lineGraph;
/*     */   
/*     */   private Quad labGraph;
/*     */   
/*     */   private LineGrapher lineGrapher;
/*     */   
/*     */   private TabledLabelGrapher tableGrapher;
/*     */   
/*     */   private TrackingStat[] trackingStats;
/*     */   
/*     */   public PerformanceGraphNode() {
/*  36 */     super("FPSGraphNode");
/*  37 */     Debug.updateGraphs = Debug.stats;
/*     */     
/*  39 */     setRenderQueueMode(4);
/*  40 */     setCullHint(Spatial.CullHint.Never);
/*  41 */     setupStatGraphs();
/*  42 */     this.trackingStats = new TrackingStat[] { TrackingStat.RENDER_TIME };
/*  43 */     updateGraphStats();
/*  44 */     updateRenderState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupStatGraphs() {
/*  51 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/*     */ 
/*     */     
/*  54 */     this.lineGraph = new Quad("lineGraph", display.getWidth(), display.getHeight() * 0.75F)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public void draw(Renderer r) {
/*  59 */           StatCollector.pause();
/*  60 */           super.draw(r);
/*  61 */           StatCollector.resume();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  66 */     this.lineGrapher = GraphFactory.makeLineGraph((int)(this.lineGraph.getWidth() + 0.5F), (int)(this.lineGraph.getHeight() + 0.5F), this.lineGraph);
/*     */ 
/*     */     
/*  69 */     this.lineGraph.setLocalTranslation(display.getWidth() * 0.5F, display.getHeight() * 0.625F, 0.0F);
/*  70 */     (this.lineGraph.getDefaultColor()).a = 0.5F;
/*     */ 
/*     */     
/*  73 */     attachChild((Spatial)this.lineGraph);
/*     */ 
/*     */     
/*  76 */     this.labGraph = new Quad("labelGraph", display.getWidth(), display.getHeight() * 0.25F)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public void draw(Renderer r) {
/*  81 */           StatCollector.pause();
/*  82 */           super.draw(r);
/*  83 */           StatCollector.resume();
/*     */         }
/*     */       };
/*     */     
/*  87 */     this.tableGrapher = GraphFactory.makeTabledLabelGraph((int)(this.labGraph.getWidth() + 0.5F), (int)(this.labGraph.getHeight() + 0.5F), this.labGraph);
/*  88 */     this.tableGrapher.setColumns(2);
/*  89 */     this.tableGrapher.setMinimalBackground(false);
/*  90 */     this.tableGrapher.linkTo((AbstractStatGrapher)this.lineGrapher);
/*     */     
/*  92 */     this.labGraph.setLocalTranslation(display.getWidth() * 0.5F, display.getHeight() * 0.125F, 0.0F);
/*     */     
/*  94 */     (this.labGraph.getDefaultColor()).a = 1.0F;
/*  95 */     attachChild((Spatial)this.labGraph);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrackingStats(TrackingStat... trackingStats) {
/* 102 */     this.trackingStats = trackingStats;
/* 103 */     updateGraphStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startTiming(StatType type) {
/* 110 */     if (Debug.stats) {
/* 111 */       StatCollector.startStat(type);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void endTiming(StatType type) {
/* 119 */     if (Debug.stats) {
/* 120 */       StatCollector.endStat(type);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrackingStat[] getTrackingStats() {
/* 128 */     return this.trackingStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateGraphStats() {
/* 135 */     this.lineGrapher.clearConfig();
/* 136 */     this.tableGrapher.clearConfig();
/*     */     
/* 138 */     for (TrackingStat trackingStat : this.trackingStats) {
/*     */       
/* 140 */       this.lineGrapher.addConfig(trackingStat.statType, LineGrapher.ConfigKeys.Color.name(), trackingStat.color);
/* 141 */       this.lineGrapher.addConfig(trackingStat.statType, TabledLabelGrapher.ConfigKeys.FrameAverage.name(), Boolean.valueOf(true));
/* 142 */       this.lineGrapher.addConfig(trackingStat.statType, LineGrapher.ConfigKeys.Width.name(), Integer.valueOf(trackingStat.width));
/* 143 */       this.tableGrapher.addConfig(trackingStat.statType, TabledLabelGrapher.ConfigKeys.Decimals.name(), Integer.valueOf(0));
/* 144 */       this.tableGrapher.addConfig(trackingStat.statType, TabledLabelGrapher.ConfigKeys.Name.name(), trackingStat.niceName);
/* 145 */       this.tableGrapher.addConfig(trackingStat.statType, TabledLabelGrapher.ConfigKeys.FrameAverage.name(), Boolean.valueOf(true));
/* 146 */       this.tableGrapher.addConfig(trackingStat.statType, TabledLabelGrapher.ConfigKeys.TextColor.name(), trackingStat.color);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TrackingStat
/*     */   {
/* 154 */     RENDER_TIME("Render time", StatType.STAT_RENDER_TIMER, 1, (StatType)new ColorRGBA(0.0F, 1.0F, 0.87F, 1.0F), true),
/* 155 */     TRIS_COUNT("Triangles", StatType.STAT_TRIANGLE_COUNT, 1, (StatType)new ColorRGBA(0.7F, 0.0F, 1.0F, 1.0F), false),
/* 156 */     QUAD_COUNT("Quads", StatType.STAT_QUAD_COUNT, 1, (StatType)ColorRGBA.orange, false),
/* 157 */     LINE_COUNT("Lines", StatType.STAT_LINE_COUNT, 1, (StatType)ColorRGBA.yellow, false),
/* 158 */     TEXTURE_BINDS("Textures", StatType.STAT_TEXTURE_BINDS, 1, (StatType)new ColorRGBA(0.98F, 0.0F, 1.0F, 1.0F), false),
/* 159 */     GEOMETRY_COUNT("Geometry", StatType.STAT_GEOM_COUNT, 1, (StatType)new ColorRGBA(0.0F, 1.0F, 0.16F, 1.0F), false),
/* 160 */     CHUNK_MANAGEMENT("Chunk management", "chunkManagement", 1, (String)new ColorRGBA(0.0F, 0.68F, 1.0F, 1.0F), true),
/* 161 */     LOGIC_UPDATE("Logic update", "logicUpdate", 1, (String)new ColorRGBA(0.0F, 0.32F, 1.0F, 1.0F), true),
/* 162 */     NETWORK_HANDLING("Network handling", "networkHandler", 1, (String)new ColorRGBA(1.0F, 0.51F, 0.0F, 1.0F), true),
/* 163 */     BREADCRUMBS("Breadcrumbs", "breadcrumbs", 1, (String)ColorRGBA.pink, true),
/* 164 */     CLIENTS_UPDATE("Clients update", "updateClients", 1, (String)new ColorRGBA(1.0F, 0.17F, 0.36F, 1.0F), true),
/* 165 */     GLOBAL_RENDER_CALL("Global render call", "renderCall", 1, (String)new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), true),
/* 166 */     PARTICLES("Particles", "particles", 1, (String)new ColorRGBA(0.0F, 1.0F, 0.0F, 1.0F), true),
/* 167 */     SOUND("Sound", "sound", 1, (String)new ColorRGBA(1.0F, 0.0F, 0.0F, 1.0F), true),
/* 168 */     UPDATE_QUEUE("Update Queue", "updateQueue", 1, (String)new ColorRGBA(0.0F, 0.5F, 0.5F, 1.0F), true),
/* 169 */     GAME_TOKEN("Game Token", "gameToken", 1, (String)new ColorRGBA(0.5F, 0.0F, 0.5F, 1.0F), true),
/* 170 */     LOADING_SCREEN("Loading Screen", "loadingScreen", 1, (String)new ColorRGBA(0.5F, 0.5F, 0.0F, 1.0F), true),
/* 171 */     SLEEP("Sleep", "sleep", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 172 */     BUI_UPDATE("BUI Update", "buiUpdate", 1, (String)new ColorRGBA(0.75F, 0.25F, 0.0F, 1.0F), true),
/* 173 */     GEOMETRIC_UPDATE("Geometric Update", "geoUpdate", 1, (String)new ColorRGBA(0.75F, 0.0F, 0.25F, 1.0F), true),
/* 174 */     CREATURE_BUILDING("Creature Building", "creatureBuilding", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 175 */     RELOAD_MODULAR("Reload Modular", "reloadModular", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 176 */     TEXTURE_LOAD_TIME("Texture load time", "textureLoadTime", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 177 */     MODULAR_LOADING_TIME("Modular load time", "modularLoadTime", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 178 */     ANIMATION_LOADING_TIME("Animation load time", "animationLoadTime", 1, (String)new ColorRGBA(0.0F, 0.25F, 0.75F, 1.0F), true),
/* 179 */     MOVETO("move to", "move_to", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 180 */     TURNTO("turn to", "turn_to", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 181 */     JOINTS("Joints", "joints", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 182 */     BATCH("Batch", "batch", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 183 */     MANAGED("Managed", "managed", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 184 */     OTHER("Other", "other", 1, (String)new ColorRGBA(0.0F, 0.0F, 1.0F, 1.0F), true),
/* 185 */     FPS("FPS", "fps", 2, (String)new ColorRGBA(1.0F, 0.89F, 0.0F, 1.0F), true);
/*     */ 
/*     */     
/* 188 */     public String niceName = "";
/*     */ 
/*     */     
/*     */     public final StatType statType;
/*     */ 
/*     */     
/*     */     public final int width;
/*     */ 
/*     */     
/*     */     public final ColorRGBA color;
/*     */     
/*     */     public final boolean isTimed;
/*     */ 
/*     */     
/*     */     public void print() {
/* 203 */       System.out.println(this.niceName);
/*     */     }
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
/*     */ 
/*     */ 
/*     */     
/*     */     TrackingStat(String niceName, StatType statType, int width, ColorRGBA color, boolean timed) {
/* 223 */       this.niceName = niceName;
/* 224 */       this.statType = statType;
/* 225 */       this.width = width;
/* 226 */       this.color = color;
/* 227 */       this.isTimed = timed;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\PerformanceGraphNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */