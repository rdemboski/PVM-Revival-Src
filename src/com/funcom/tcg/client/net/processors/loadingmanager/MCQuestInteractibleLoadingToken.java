/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.TintMouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.tcg.client.actions.QuestUpdateAction;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*    */ import com.funcom.tcg.rpg.BaseFaction;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ public class MCQuestInteractibleLoadingToken extends AbstractModularCreatureLMToken {
/* 24 */   public static final ColorRGBA QUESTITEM_TINT_COLOR = (new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F);
/*    */   
/*    */   public MCQuestInteractibleLoadingToken(CreatureCreationMessage message) {
/* 27 */     super(message);
/*    */   }
/*    */   
/*    */   public static boolean canBuild(Faction faction) throws Exception {
/* 31 */     return faction.equivalentTo(BaseFaction.INTERACT_KILL);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserActionHandler createActionHandler(Creature monster, CreatureCreationMessage message, PropNode propNode) {
/* 37 */     QuestUpdateAction action = new QuestUpdateAction(NetworkHandler.instance().getIOHandler(), "" + monster.getId(), monster);
/* 38 */     monster.addAction((Action)action);
/*    */     
/* 40 */     QuestInteractibleMouseOver questInteractibleMouseOver = new QuestInteractibleMouseOver(QUESTITEM_TINT_COLOR, Effects.TintMode.ADDITIVE, MainGameState.getMouseCursorSetter());
/* 41 */     questInteractibleMouseOver.setOwnerPropNode(propNode);
/*    */     
/* 43 */     return (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)monster, (Creature)MainGameState.getPlayerNode().getProp(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questInteractibleMouseOver);
/*    */   }
/*    */   
/*    */   private class QuestInteractibleMouseOver
/*    */     extends TintMouseOver {
/*    */     private MouseCursorSetter cursorSetter;
/*    */     
/*    */     public QuestInteractibleMouseOver(ColorRGBA tintColor, Effects.TintMode tintMode, MouseCursorSetter cursorSetter) {
/* 51 */       super(tintColor, tintMode);
/* 52 */       this.cursorSetter = cursorSetter;
/*    */     }
/*    */ 
/*    */     
/*    */     public void mouseEntered() {
/* 57 */       super.mouseEntered();
/*    */       
/* 59 */       this.cursorSetter.setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_QUEST_INTERACT);
/*    */     }
/*    */ 
/*    */     
/*    */     public void mouseExited() {
/* 64 */       super.mouseExited();
/*    */       
/* 66 */       this.cursorSetter.setDefaultCursor();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\MCQuestInteractibleLoadingToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */