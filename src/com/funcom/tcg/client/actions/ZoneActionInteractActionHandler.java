/*     */ package com.funcom.tcg.client.actions;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ZoneActionInteractActionHandler
/*     */   extends DefaultActionInteractActionHandler
/*     */ {
/*     */   private int level;
/*     */   private boolean subscriberOnly;
/*     */   private String onQuestId;
/*     */   private String dfxNoZoningText;
/*     */   private List<String> keys;
/*     */   private String completeQuestId;
/*     */   
/*     */   public ZoneActionInteractActionHandler(InteractibleProp interactibleProp, Creature player, String defaultActionName, LineNode collisionRootLine, MouseOver mouseOver, int level, boolean subscriberOnly, String completeQuestId, String onQuestId, String dfxNoZoningText, List<String> keys) {
/*  33 */     super(interactibleProp, player, defaultActionName, collisionRootLine, mouseOver);
/*  34 */     this.level = level;
/*  35 */     this.subscriberOnly = subscriberOnly;
/*  36 */     this.completeQuestId = completeQuestId;
/*  37 */     this.onQuestId = onQuestId;
/*  38 */     this.dfxNoZoningText = dfxNoZoningText;
/*  39 */     this.keys = keys;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseEnter() {
/*  44 */     if (this.level <= MainGameState.getPlayerModel().getStatSupport().getLevel()) {
/*  45 */       super.handleMouseEnter();
/*     */     } else {
/*     */       
/*  48 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_ATTACK);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseExit() {
/*  54 */     super.handleMouseExit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleLeftMousePress(WorldCoordinate pressedCoord) {
/*  60 */     MainGameState.getTips().put("tutorial.description.normalportal");
/*  61 */     boolean completedQuest = MainGameState.getQuestModel().hasCompletedQuest(this.completeQuestId);
/*  62 */     ClientQuestData data = MainGameState.getQuestModel().getCurrentMissionDescription(this.onQuestId);
/*  63 */     boolean onQuest = (data != null);
/*  64 */     if (!MainGameState.getPlayerModel().isSubscriber() && this.subscriberOnly && !hasAccess()) {
/*     */       
/*  66 */       SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", MainGameState.isPlayerRegistered() ? "popup.select.members.item" : "popup.select.members.item.notsaved");
/*     */ 
/*     */ 
/*     */       
/*  70 */       window.setLayer(101);
/*  71 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */     }
/*  73 */     else if (this.level > MainGameState.getPlayerModel().getStatSupport().getLevel()) {
/*     */       
/*  75 */       DfxTextWindowManager.instance().getWindow("zoning").showText(JavaLocalization.getInstance().getLocalizedRPGText("portalkeys.deniedaccessdfx.toolowlevel"));
/*     */     } else {
/*     */       
/*  78 */       boolean questCompletedConditionExists = !this.completeQuestId.isEmpty();
/*  79 */       boolean questOnConditionExists = !this.onQuestId.isEmpty();
/*  80 */       if ((questCompletedConditionExists || questOnConditionExists) && (!questCompletedConditionExists || !completedQuest) && (!questOnConditionExists || !onQuest)) {
/*     */ 
/*     */ 
/*     */         
/*  84 */         DfxTextWindowManager.instance().getWindow("zoning").showText(this.dfxNoZoningText);
/*     */       } else {
/*  86 */         super.handleLeftMousePress(pressedCoord);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasAccess() {
/*  92 */     List<String> haveKeys = MainGameState.getAccessKeys();
/*  93 */     List<String> accessKeys = new ArrayList<String>(this.keys);
/*  94 */     accessKeys.retainAll(haveKeys);
/*  95 */     if (!accessKeys.isEmpty())
/*  96 */       for (String key : accessKeys) {
/*  97 */         long expireTime = ((Long)MainGameState.getAccessKeyExpireTimes().get(haveKeys.indexOf(key))).longValue();
/*  98 */         if (expireTime == 0L || expireTime >= System.currentTimeMillis()) {
/*  99 */           return true;
/*     */         }
/*     */       }  
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\ZoneActionInteractActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */