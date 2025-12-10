/*     */ package com.funcom.gameengine.view.particles;
/*     */ 
/*     */ import com.funcom.commons.configuration.ExtProperties;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.renderer.Camera;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ public class GuiParticleJointFactory
/*     */ {
/*  15 */   private static final Logger LOGGER = Logger.getLogger(GuiParticleJointFactory.class);
/*     */   
/*     */   private static final String PARTICLE_SYSTEM_PATH = "particleSystemPath";
/*     */   
/*     */   private static final String USES_CONTROLLER = "usesController";
/*     */   private static final String SCREEN_POSITION_X = "screenPosition.x";
/*     */   private static final String SCREEN_POSITION_Y = "screenPosition.y";
/*     */   private static final String NAME = "name";
/*     */   private static final String CONTROLLER_TYPE = "controllerType";
/*     */   private static final String CONTINUOUS_PLAYING = "continuousPlaying";
/*     */   private static final String CONTROLLER_START_X = "controller.start.x";
/*     */   private static final String CONTROLLER_START_Y = "controller.start.y";
/*     */   private static final String CONTROLLER_START_RELPOS_X = "controller.start.relpos.x";
/*     */   private static final String CONTROLLER_START_RELPOS_Y = "controller.start.relpos.y";
/*     */   private static final String CONTROLLER_END_X = "controller.end.x";
/*     */   private static final String CONTROLLER_END_Y = "controller.end.y";
/*     */   private static final String CONTROLLER_END_RELPOS_X = "controller.end.relpos.x";
/*     */   private static final String CONTROLLER_END_RELPOS_Y = "controller.end.relpos.y";
/*     */   private static final String CONTROLLER_TIME_MILLIS = "controller.timeMillis";
/*     */   private ResourceManager resourceManager;
/*     */   private Camera camera;
/*     */   private static final String CONTROLLER_FUNCTION_START = "controller.function.start";
/*     */   private static final String CONTROLLER_FUNCTION_END = "controller.function.end";
/*     */   
/*     */   public GuiParticleJointFactory(ResourceManager resourceManager, Camera camera) {
/*  40 */     if (resourceManager == null)
/*  41 */       throw new IllegalArgumentException("resourceManager = null"); 
/*  42 */     if (camera == null)
/*  43 */       throw new IllegalArgumentException("camera = null"); 
/*  44 */     this.resourceManager = resourceManager;
/*  45 */     this.camera = camera;
/*     */   }
/*     */   
/*     */   public GuiParticleJoint createFromPath(String path) {
/*  49 */     ExtProperties guiParticleJointDefinition = (ExtProperties)this.resourceManager.getResource(ExtProperties.class, path, CacheType.NOT_CACHED);
/*     */ 
/*     */     
/*  52 */     String name = guiParticleJointDefinition.getProperty("name");
/*  53 */     Vector2f screenPosition = createPosition(guiParticleJointDefinition);
/*  54 */     ParticleSystem particleSystem = (ParticleSystem)this.resourceManager.getResource(ParticleSystem.class, guiParticleJointDefinition.getProperty("particleSystemPath"));
/*     */     
/*  56 */     GuiParticleJoint guiParticleJoint = new GuiParticleJoint(name, screenPosition, particleSystem, this.camera);
/*  57 */     guiParticleJoint.setContinuousPlaying(guiParticleJointDefinition.getBoolean("continuousPlaying"));
/*     */     
/*  59 */     if (guiParticleJointDefinition.getBoolean("usesController")) {
/*  60 */       guiParticleJoint.setController(createController(guiParticleJointDefinition));
/*     */     }
/*  62 */     if (LOGGER.isEnabledFor((Priority)Level.INFO)) {
/*  63 */       LOGGER.info("GUI particle joint created: " + guiParticleJoint);
/*     */     }
/*  65 */     return guiParticleJoint;
/*     */   }
/*     */   
/*     */   private GuiParticleJoint.GuiParticleMotionController createController(ExtProperties guiParticleJointDefinition) {
/*  69 */     float startX = guiParticleJointDefinition.getFloat("controller.start.x");
/*  70 */     float startY = guiParticleJointDefinition.getFloat("controller.start.y");
/*  71 */     GuiParticleRelPos.RelPosX startRelPosX = GuiParticleRelPos.RelPosX.valueOf(guiParticleJointDefinition.getProperty("controller.start.relpos.x"));
/*  72 */     GuiParticleRelPos.RelPosY startRelPosy = GuiParticleRelPos.RelPosY.valueOf(guiParticleJointDefinition.getProperty("controller.start.relpos.y"));
/*  73 */     GuiParticleRelPos start = new GuiParticleRelPos(startRelPosX, startRelPosy, startX, startY);
/*     */     
/*  75 */     float endX = guiParticleJointDefinition.getFloat("controller.end.x");
/*  76 */     float endY = guiParticleJointDefinition.getFloat("controller.end.y");
/*  77 */     GuiParticleRelPos.RelPosX endRelPosX = GuiParticleRelPos.RelPosX.valueOf(guiParticleJointDefinition.getProperty("controller.end.relpos.x"));
/*  78 */     GuiParticleRelPos.RelPosY endRelPosy = GuiParticleRelPos.RelPosY.valueOf(guiParticleJointDefinition.getProperty("controller.end.relpos.y"));
/*  79 */     GuiParticleRelPos end = new GuiParticleRelPos(endRelPosX, endRelPosy, endX, endY);
/*     */     
/*  81 */     float timeSecs = guiParticleJointDefinition.getInt("controller.timeMillis") * 0.001F;
/*     */     
/*  83 */     String controllerType = guiParticleJointDefinition.getProperty("controllerType");
/*  84 */     if ("linear".equals(controllerType))
/*  85 */       return new LinearMotionController(start, end, timeSecs); 
/*  86 */     if ("sine".equals(controllerType)) {
/*  87 */       float functionStart = guiParticleJointDefinition.getFloat("controller.function.start");
/*  88 */       float functionEnd = guiParticleJointDefinition.getFloat("controller.function.end");
/*  89 */       Vector2f range = new Vector2f(functionStart, functionEnd);
/*  90 */       return new SineMotionController(start, end, range, timeSecs);
/*     */     } 
/*  92 */     throw new IllegalArgumentException("Unknown controller type: " + controllerType);
/*     */   }
/*     */   
/*     */   private Vector2f createPosition(ExtProperties guiParticleJointDefinition) {
/*  96 */     if (guiParticleJointDefinition.getBoolean("usesController")) {
/*  97 */       return new Vector2f();
/*     */     }
/*  99 */     float x = guiParticleJointDefinition.getFloat("screenPosition.x");
/* 100 */     float y = guiParticleJointDefinition.getFloat("screenPosition.y");
/* 101 */     return new Vector2f(x, y);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\GuiParticleJointFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */