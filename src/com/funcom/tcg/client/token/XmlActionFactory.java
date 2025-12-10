/*    */ package com.funcom.tcg.client.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.action.DFXInvokerAction;
/*    */ import com.funcom.gameengine.model.token.ActionFactory;
/*    */ import com.funcom.gameengine.model.token.ActionFactoryException;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.actions.ZoneAction;
/*    */ import com.funcom.tcg.portals.InteractibleType;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XmlActionFactory
/*    */   implements ActionFactory
/*    */ {
/*    */   private static final String EL_ACTION_FILENAME = "filename";
/*    */   private ResourceGetter resourceGetter;
/*    */   private GameIOHandler ioHandler;
/*    */   private String parentName;
/*    */   private static final String EL_ACTION_DELAY = "delay";
/*    */   
/*    */   public XmlActionFactory(ResourceGetter resourceGetter, GameIOHandler ioHandler) {
/* 26 */     this.resourceGetter = resourceGetter;
/* 27 */     this.ioHandler = ioHandler;
/*    */   }
/*    */   
/*    */   public Action createAction(String parentName, Object descriptionObject) throws ActionFactoryException {
/* 31 */     this.parentName = parentName;
/* 32 */     Element actionElement = (Element)descriptionObject;
/*    */     
/* 34 */     String actionType = actionElement.getAttributeValue("type");
/*    */     
/* 36 */     if ("invoke-dfx".equals(actionType))
/* 37 */       return dfxAction(actionElement); 
/* 38 */     if ("zone".equals(actionType)) {
/* 39 */       return (Action)new ZoneAction(parentName, this.ioHandler, InteractibleType.TELEPORT);
/*    */     }
/* 41 */     throw new ActionFactoryException("Don't know how to build action: " + actionType);
/*    */   }
/*    */   
/*    */   private Action dfxAction(Element actionElement) {
/* 45 */     String fileName = actionElement.getChild("filename").getTextTrim();
/* 46 */     return (Action)new DFXInvokerAction(fileName);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\XmlActionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */