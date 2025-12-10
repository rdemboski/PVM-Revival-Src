/*     */ package com.funcom.commons.jme;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.jme.input.InputHandler;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiFontWorkaround;
/*     */ import com.jmex.bui.PolledRootNode;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TcgPolledRootNode
/*     */   extends PolledRootNode
/*     */ {
/*  24 */   private static final Logger LOGGER = Logger.getLogger(TcgPolledRootNode.class);
/*     */   
/*     */   public TcgPolledRootNode(Timer timer, InputHandler handler) {
/*  27 */     super(timer, handler);
/*     */   }
/*     */   
/*     */   public TcgPolledRootNode(Timer timer) {
/*  31 */     super(timer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWindow(BWindow window) {
/*  36 */     BuiFontWorkaround.reconfigureLabels((BComponent)window);
/*  37 */     super.addWindow(window);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWorldData(float timePerFrame) {
/*  42 */     for (BWindow window : this._windows) {
/*  43 */       if (!window.isValid()) {
/*  44 */         BuiFontWorkaround.reconfigureLabels((BComponent)window);
/*     */       }
/*     */     } 
/*  47 */     super.updateWorldData(timePerFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(Renderer renderer) {
/*  52 */     BWindow modalWin = null;
/*  53 */     if (this._modalShade != null) {
/*  54 */       for (int i = this._windows.size() - 1; i >= 0; i--) {
/*  55 */         BWindow win = this._windows.get(i);
/*  56 */         if (win.shouldShadeBehind()) {
/*  57 */           modalWin = win;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  64 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/*  65 */     int width = displaySystem.getWidth();
/*  66 */     int height = displaySystem.getHeight();
/*  67 */     for (int ii = 0, ll = this._windows.size(); ii < ll; ii++) {
/*  68 */       BWindow win = this._windows.get(ii);
/*     */       try {
/*  70 */         if (win == modalWin) {
/*     */ 
/*     */           
/*  73 */           GL11.glDisable(3089);
/*  74 */           GL11.glScissor(0, 0, width, height);
/*  75 */           renderModalShade();
/*     */         } 
/*     */ 
/*     */         
/*  79 */         GL11.glDisable(3089);
/*  80 */         GL11.glScissor(0, 0, width, height);
/*  81 */         win.render(renderer);
/*  82 */       } catch (Throwable t) {
/*  83 */         LOGGER.warn(win + " failed in render()", t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateHoverComponent(int mx, int my) {
/*  93 */     BComponent newHoverComponent = null;
/*  94 */     for (int ii = this._windows.size() - 1; ii >= 0; ii--) {
/*  95 */       BWindow window = this._windows.get(ii);
/*     */       
/*  97 */       if (window instanceof PartiallyNotInteractive) {
/*  98 */         PartiallyNotInteractive irregularWindow = (PartiallyNotInteractive)window;
/*  99 */         if (!irregularWindow.isHit()) {
/*     */           continue;
/*     */         }
/*     */       } 
/* 103 */       newHoverComponent = window.getHitComponent(mx, my);
/* 104 */       if (newHoverComponent != null && newHoverComponent.getWindow() != this._tipwin) {
/*     */         break;
/*     */       }
/*     */       
/* 108 */       if (window.isModal()) {
/*     */         break;
/*     */       }
/*     */       
/*     */       continue;
/*     */     } 
/* 114 */     if (this._hcomponent != newHoverComponent) {
/*     */       
/* 116 */       if (this._hcomponent != null && this._hcomponent.isHoverEnabled()) {
/* 117 */         this._hcomponent.dispatchEvent((BEvent)new MouseEvent(this, getTickStamp(), this._modifiers, 3, mx, my));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 122 */       if (newHoverComponent != null && newHoverComponent.isHoverEnabled()) {
/* 123 */         newHoverComponent.dispatchEvent((BEvent)new MouseEvent(this, getTickStamp(), this._modifiers, 2, mx, my));
/*     */       }
/*     */ 
/*     */       
/* 127 */       this._hcomponent = newHoverComponent;
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (this._hcomponent == null || !this._hcomponent.isHoverEnabled() || this._hcomponent.getWindow() != this._tipwin)
/*     */       {
/* 133 */         clearTipWindow();
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\TcgPolledRootNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */