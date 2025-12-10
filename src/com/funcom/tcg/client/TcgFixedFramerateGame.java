/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.gameengine.debug.TimeStamper;
/*     */ import com.funcom.gameengine.resourcemanager.DrawableContextManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.TextureLoader;
/*     */ import com.jme.app.AbstractGame;
/*     */ import com.jme.app.FixedFramerateGame;
/*     */ import com.jme.input.InputSystem;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.Timer;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TcgFixedFramerateGame
/*     */   extends AbstractGame
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(FixedFramerateGame.class.getName());
/*     */ 
/*     */   
/*     */   protected FpsManager fpsManager;
/*     */   
/*     */   protected boolean displayClosing = false;
/*     */   
/*     */   private static final int MIN_TEXTURE_UNITS = 4;
/*     */ 
/*     */   
/*     */   public final void start() {
/*  56 */     logger.info("Application started.");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  62 */       initSystem();
/*     */ 
/*     */       
/*  65 */       Boolean isMultiTextureSupported = Boolean.valueOf(TextureState.isMultiTextureSupported());
/*     */ 
/*     */       
/*  68 */       int numTotalTexUnits = Math.max(TextureState.getNumberOfFragmentTexCoordUnits(), Math.max(TextureState.getNumberOfFixedUnits(), Math.max(TextureState.getNumberOfFragmentUnits(), TextureState.getNumberOfVertexUnits())));
/*     */ 
/*     */ 
/*     */       
/*  72 */       if (!isMultiTextureSupported.booleanValue() || numTotalTexUnits < 4) {
/*     */         
/*  74 */         showVideoCardNotSupportedDialog();
/*  75 */         throw new Exception("Video card doesn't support multitexture");
/*     */       } 
/*     */       
/*  78 */       if (this.display != null) {
/*  79 */         TextureLoader.setTextureState(DisplaySystem.getDisplaySystem().getRenderer().createTextureState());
/*  80 */         DrawableContextManager.create();
/*     */       }
/*     */     
/*  83 */     } catch (Throwable t) {
/*  84 */       logger.fatal("Exception on initSystem", t);
/*     */       
/*  86 */       if (this.display != null) {
/*  87 */         this.display.reset();
/*  88 */         this.display.close();
/*     */       } 
/*     */       
/*  91 */       logger.info("Application ending.");
/*  92 */       quit();
/*  93 */       throw new RuntimeException(t);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       assertDisplayCreated();
/*     */       
/* 102 */       this.fpsManager = new FpsManager(Timer.getTimer());
/* 103 */       this.fpsManager.setFps(60);
/*     */       
/* 105 */       initGame();
/*     */       
/* 107 */       boolean bWindowHasFocus = true;
/*     */ 
/*     */       
/* 110 */       long frame = 0L;
/* 111 */       while (!this.finished && !this.displayClosing) {
/* 112 */         if (DrawableContextManager.set(-1L))
/*     */         {
/* 114 */           TimeStamper.INSTANCE.update();
/* 115 */           TimeStamper.INSTANCE.stamp("FPS");
/*     */           
/* 117 */           if (TimeStamper.INSTANCE.isStampingEnabled()) {
/* 118 */             frame++;
/* 119 */             System.out.printf("****************************** FRAME:" + frame + "\n", new Object[0]);
/*     */           } else {
/*     */             
/* 122 */             frame = 0L;
/*     */           } 
/*     */ 
/*     */           
/* 126 */           bWindowHasFocus = this.display.isActive();
/* 127 */           this.fpsManager.setHasFocus(bWindowHasFocus);
/*     */           
/* 129 */           this.fpsManager.startFrame();
/*     */ 
/*     */ 
/*     */           
/* 133 */           TimeStamper.INSTANCE.stamp("Input");
/*     */           try {
/* 135 */             InputSystem.update();
/* 136 */           } catch (UnsatisfiedLinkError e) {
/* 137 */             TcgJme.showDialog(ConfigErrorType.UNSATISFIED_LINK);
/* 138 */             quit();
/*     */             return;
/*     */           } 
/* 141 */           TimeStamper.INSTANCE.stamp("Input");
/*     */           
/* 143 */           TimeStamper.INSTANCE.stamp("Update");
/*     */           
/* 145 */           update(-1.0F);
/* 146 */           TimeStamper.INSTANCE.stamp("Update");
/*     */           
/* 148 */           TimeStamper.INSTANCE.stamp("Render");
/*     */ 
/*     */ 
/*     */           
/* 152 */           render(-1.0F);
/*     */           
/* 154 */           this.display.getRenderer().displayBackBuffer();
/*     */           
/* 156 */           TimeStamper.INSTANCE.stamp("Render");
/*     */           
/* 158 */           TimeStamper.INSTANCE.stamp("EndFrame");
/* 159 */           long nanoTimeForEndFrame = this.fpsManager.endFrame();
/*     */ 
/*     */           
/* 162 */           updateEndFrame(nanoTimeForEndFrame);
/* 163 */           TimeStamper.INSTANCE.stamp("EndFrame");
/*     */           
/* 165 */           if (this.display.isClosing()) {
/* 166 */             addQuitDialog();
/*     */           }
/*     */           
/* 169 */           DrawableContextManager.unset();
/*     */           
/* 171 */           TimeStamper.INSTANCE.stamp("Sleep");
/* 172 */           startTiming(PerformanceGraphNode.TrackingStat.SLEEP);
/* 173 */           this.fpsManager.sleepExcessFrameTime();
/* 174 */           endTiming(PerformanceGraphNode.TrackingStat.SLEEP);
/* 175 */           TimeStamper.INSTANCE.stamp("Sleep");
/*     */           
/* 177 */           TimeStamper.INSTANCE.stamp("FPS");
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 182 */     } catch (Throwable t) {
/* 183 */       logger.fatal("Exception in game loop", t);
/*     */       
/* 185 */       if (this.display != null) {
/* 186 */         DrawableContextManager.destroy();
/* 187 */         this.display.reset();
/* 188 */         this.display.close();
/*     */       } 
/*     */       
/* 191 */       throw new RuntimeException(t);
/*     */     } finally {
/* 193 */       cleanup();
/*     */     } 
/*     */     
/* 196 */     logger.info("Application ending.");
/*     */     
/* 198 */     if (this.display != null) {
/* 199 */       DrawableContextManager.destroy();
/* 200 */       this.display.reset();
/* 201 */       this.display.close();
/*     */     } 
/*     */     
/* 204 */     quit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/* 211 */     if (Debug.stats) {
/* 212 */       StatCollector.startStat(trackingStat.statType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void endTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/* 220 */     if (Debug.stats) {
/* 221 */       StatCollector.endStat(trackingStat.statType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void quit() {
/* 231 */     if (this.display != null) {
/* 232 */       this.display.close();
/*     */     }
/*     */     
/* 235 */     System.exit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void update(float paramFloat);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void updateEndFrame(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void render(float paramFloat);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void initSystem();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void initGame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void reinit();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void cleanup();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void addQuitDialog();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getAppId();
/*     */ 
/*     */ 
/*     */   
/*     */   private static void showVideoCardNotSupportedDialog() {
/* 286 */     String title = TcgGame.getLocalizedText("dialog.title.error", new String[0]);
/* 287 */     String message = TcgGame.getLocalizedText("dialog.videocard.unsupported", new String[0]);
/* 288 */     JOptionPane.showMessageDialog(null, message, title, 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgFixedFramerateGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */