/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import com.jme.input.controls.GameControl;
/*     */ import com.jme.input.controls.GameControlManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Controls
/*     */ {
/*  16 */   CID_EXIT("exit-game", 62),
/*     */ 
/*     */   
/*  19 */   CID_ZOOM_POSITIVE("zoom-camera-forward"),
/*  20 */   CID_ZOOM_NEGATIVE("zoom-camera-backward"),
/*  21 */   CID_CONTEXTUAL_CONTROL("contextual-control"),
/*     */ 
/*     */   
/*  24 */   CID_FORCE_ATTACK("force-attack", 42),
/*     */   
/*  26 */   CID_SKILL_SLOT_1("skill-slot-1", 79),
/*  27 */   CID_SKILL_SLOT_2("skill-slot-2", 80),
/*     */   
/*  29 */   CID_PET_SLOT1("pet-slot-1", 2),
/*  30 */   CID_PET_SLOT2("pet-slot-2", 3),
/*  31 */   CID_PET_SLOT3("pet-slot-3", 4),
/*     */   
/*  33 */   CID_PAUSE("pause", 57),
/*     */   
/*  35 */   CID_USE_MANA_POTION("use-mana-potion", 60),
/*  36 */   CID_USE_HEALTH_POTION("use-health-potion", 59),
/*     */ 
/*     */   
/*  39 */   CID_GUI_OPEN_PETS("open-pets", 25),
/*  40 */   CID_GUI_OPEN_CHARACTER("open-character", 46),
/*  41 */   CID_GUI_OPEN_MAP("open-map", 50),
/*  42 */   CID_GUI_OPEN_FRIENDS("open-friends", 33),
/*  43 */   CID_GUI_OPEN_ACHIEVEMENTS("open-achievements", 30),
/*  44 */   CID_GUI_OPEN_DUEL("open-duel", 32),
/*  45 */   CID_GUI_OPEN_QUESTS("open-quests", 16),
/*  46 */   CID_GUI_CLOSE_ALL_OR_MAINMENU("close-all", 1),
/*     */ 
/*     */ 
/*     */   
/*  50 */   CID_GUI_TOGGLE_FULLSCREEN("toggle-fullscreen", 197),
/*  51 */   CID_GUI_FOCUS_TO_CHAT("focus-to-chat", 28),
/*     */ 
/*     */   
/*  54 */   CID_DEBUG_SCENE_MONITOR("debug-toggle-scene-monitor", 68),
/*  55 */   CID_DEBUG_CRITICAL("debug-critical", 65),
/*  56 */   CID_DEBUG_DAMAGE("debug-damage", 66),
/*  57 */   CID_DEBUG_HEAL("debug-heal", 67),
/*  58 */   CID_DEBUG_QUESTSWITCH("debug-questswitch", 64),
/*  59 */   CID_DEBUG_OPENDEBUGWINDOW("debug-opendebugwindow", 87),
/*  60 */   CID_DEBUG_TOGGLESOUNDSYSTEMIO("debug-togglesoundsystemio", 88),
/*  61 */   CID_DEBUG_DFXTEXT("debug-dfxtext", 63),
/*  62 */   CID_DEBUG_OPEN_INVENTORY("open-inventory", 23),
/*  63 */   CID_DEBUG_TOGGLETIMESTAMPING("debug-toggletimestamping", 61),
/*     */   
/*  65 */   CID_DEBUG_SERVER("debug-server", 177);
/*     */ 
/*     */   
/*     */   private static final int INVALID = -1;
/*     */ 
/*     */   
/*     */   public final String id;
/*     */   
/*     */   public final int keyCode;
/*     */ 
/*     */   
/*     */   Controls(String id, int keyCode) {
/*  77 */     this.id = id;
/*  78 */     this.keyCode = keyCode;
/*     */   }
/*     */   
/*     */   public boolean is(GameControl gameControl) {
/*  82 */     return this.id.equals(gameControl.getName());
/*     */   }
/*     */   
/*     */   public boolean is(String gameControlId) {
/*  86 */     return this.id.equals(gameControlId);
/*     */   }
/*     */   
/*     */   public static Controls[] getGameplayControlConstants() {
/*  90 */     return new Controls[] { CID_ZOOM_POSITIVE, CID_ZOOM_NEGATIVE, CID_CONTEXTUAL_CONTROL, CID_SKILL_SLOT_2, CID_FORCE_ATTACK };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Controls[] getGuiControlConstants() {
/* 100 */     return new Controls[] { CID_PET_SLOT1, CID_PET_SLOT2, CID_PET_SLOT3, CID_GUI_OPEN_PETS, CID_GUI_OPEN_CHARACTER, CID_GUI_OPEN_MAP, CID_GUI_OPEN_FRIENDS, CID_GUI_OPEN_ACHIEVEMENTS, CID_GUI_OPEN_DUEL, CID_GUI_OPEN_QUESTS, CID_GUI_CLOSE_ALL_OR_MAINMENU, CID_GUI_TOGGLE_FULLSCREEN, CID_GUI_FOCUS_TO_CHAT, CID_USE_MANA_POTION, CID_USE_HEALTH_POTION, CID_PAUSE };
/*     */   }
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
/*     */   public static Controls[] getDebugControlConstants() {
/* 121 */     return new Controls[] { CID_DEBUG_SCENE_MONITOR, CID_DEBUG_CRITICAL, CID_DEBUG_DAMAGE, CID_DEBUG_HEAL, CID_DEBUG_QUESTSWITCH, CID_DEBUG_OPENDEBUGWINDOW, CID_DEBUG_DFXTEXT, CID_DEBUG_OPEN_INVENTORY, CID_DEBUG_TOGGLETIMESTAMPING, CID_DEBUG_TOGGLESOUNDSYSTEMIO };
/*     */   }
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
/*     */   public static GameControl[] controlConstantsToGameControls(GameControlManager gameControlManager, Controls[] controls) {
/* 137 */     GameControl[] returnable = new GameControl[controls.length];
/* 138 */     for (int i = 0; i < controls.length; i++)
/* 139 */       returnable[i] = gameControlManager.getControl((controls[i]).id); 
/* 140 */     return returnable;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\Controls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */