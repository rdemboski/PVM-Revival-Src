/*     */ package com.turborilla.jops.jme;
/*     */ 
/*     */ import com.jme.animation.SpatialTransformer;
/*     */ import com.jme.app.AbstractGame;
/*     */ import com.jme.app.SimpleGame;
/*     */ import com.jme.input.FirstPersonHandler;
/*     */ import com.jme.input.InputHandler;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.CullState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.softmed.jops.ParticleManager;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ import org.softmed.jops.fileloading.DataFormatException;
/*     */ 
/*     */ public class TestJopsMonkey
/*     */   extends SimpleGame {
/*     */   private ParticleManager particleManager;
/*     */   
/*     */   protected void simpleUpdate() {
/*  26 */     if (this.tpf < 0.1F) {
/*  27 */       this.particleManager.process(this.tpf);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simpleInitGame() {
/*  36 */     Mouse.setGrabbed(false);
/*  37 */     this.display.setTitle("Jops Test");
/*     */ 
/*     */ 
/*     */     
/*  41 */     JopsNode jopsNode = new JopsNode("JopsNode", true);
/*  42 */     jopsNode.setCamera(this.cam);
/*  43 */     jopsNode.setLocalTranslation(0.0F, 2.0F, 0.0F);
/*     */     
/*  45 */     JopsNode absJopsNode = new JopsNode("absoluteJopsNode", true);
/*  46 */     absJopsNode.setCamera(this.cam);
/*     */     
/*  48 */     JopsNode relJopsNode = new JopsNode("relativeJopsNode", true);
/*  49 */     relJopsNode.setCamera(this.cam);
/*     */ 
/*     */     
/*  52 */     this.rootNode.attachChild((Spatial)jopsNode);
/*  53 */     this.rootNode.attachChild((Spatial)absJopsNode);
/*  54 */     this.rootNode.attachChild((Spatial)relJopsNode);
/*     */     
/*  56 */     SpatialTransformer absJopsNodeController = new SpatialTransformer(1);
/*  57 */     absJopsNodeController.setObject((Spatial)absJopsNode, 0, -1);
/*  58 */     absJopsNodeController.setPosition(0, 0.0F, new Vector3f(-3.0F, 0.0F, 0.0F));
/*  59 */     absJopsNodeController.setPosition(0, 2.0F, new Vector3f(3.0F, 0.0F, 0.0F));
/*  60 */     absJopsNodeController.setRotation(0, 0.0F, new Quaternion());
/*  61 */     absJopsNodeController.setRotation(0, 1.0F, (new Quaternion()).fromAngleAxis(3.1415927F, new Vector3f(1.0F, 0.0F, 0.0F)));
/*  62 */     absJopsNodeController.setRepeatType(2);
/*  63 */     this.rootNode.addController((Controller)absJopsNodeController);
/*     */     
/*  65 */     SpatialTransformer relJopsNodeController = new SpatialTransformer(1);
/*  66 */     relJopsNodeController.setObject((Spatial)relJopsNode, 0, -1);
/*  67 */     relJopsNodeController.setPosition(0, 0.0F, new Vector3f(-3.0F, -2.0F, 0.0F));
/*  68 */     relJopsNodeController.setPosition(0, 2.0F, new Vector3f(3.0F, -2.0F, 0.0F));
/*  69 */     relJopsNodeController.setRotation(0, 0.0F, new Quaternion());
/*  70 */     relJopsNodeController.setRotation(0, 2.0F, (new Quaternion()).fromAngleAxis(3.1415927F, new Vector3f(1.0F, 0.0F, 0.0F)));
/*  71 */     relJopsNodeController.setRepeatType(2);
/*  72 */     this.rootNode.addController((Controller)relJopsNodeController);
/*     */     
/*  74 */     this.particleManager = new ParticleManager();
/*  75 */     ParticleTextureLoaderInstance.setLoader(new ParticleTextureManager());
/*     */     try {
/*  77 */       String name = this.particleManager.load(new File("../resources/particles/jops/systems/jet.ops"));
/*     */       
/*  79 */       ParticleSystem jopsNodeParticleSystem = this.particleManager.getCopyAttached(name);
/*  80 */       jopsNode.setParticleSystem(jopsNodeParticleSystem);
/*  81 */       jopsNode.attachChild((Spatial)jopsNode.getParticleNode());
/*     */       
/*  83 */       ParticleSystem absJopsNodeParticleSystem = this.particleManager.getCopyAttached(name);
/*  84 */       absJopsNode.setParticleSystem(absJopsNodeParticleSystem);
/*  85 */       this.rootNode.attachChild((Spatial)absJopsNode.getParticleNode());
/*     */       
/*  87 */       ParticleSystem relJopsNodeParticleSystem = this.particleManager.getCopyAttached(name);
/*  88 */       relJopsNode.setParticleSystem(relJopsNodeParticleSystem);
/*  89 */       relJopsNode.attachChild((Spatial)relJopsNode.getParticleNode());
/*     */     }
/*  91 */     catch (IOException e) {
/*  92 */       throw new RuntimeException(e);
/*  93 */     } catch (DataFormatException e) {
/*  94 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  97 */     CullState cs = this.display.getRenderer().createCullState();
/*  98 */     cs.setCullFace(CullState.Face.None);
/*  99 */     this.rootNode.setRenderState((RenderState)cs);
/*     */     
/* 101 */     this.input = (InputHandler)new FirstPersonHandler(this.cam, 10.0F, 1.0F);
/*     */     
/* 103 */     this.lightState.setEnabled(false);
/* 104 */     this.rootNode.setLightCombineMode(Spatial.LightCombineMode.Off);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 113 */     TestJopsMonkey app = new TestJopsMonkey();
/* 114 */     app.setConfigShowMode(AbstractGame.ConfigShowMode.ShowIfNoConfig);
/* 115 */     app.start();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\TestJopsMonkey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */