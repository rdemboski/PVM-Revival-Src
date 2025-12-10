/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.ui.Localizer;
/*    */ import com.funcom.tcg.client.ui.TCGDialog;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.funcom.tcg.client.ui.startmenu.StartMenuWizard;
/*    */ import com.funcom.tcg.net.AccountResult;
/*    */ import com.jmex.bui.BWindow;
/*    */ 
/*    */ abstract class AbstractCreateCharacterTask
/*    */   extends AbstractResponseTask {
/*    */   private final Localizer localizer;
/*    */   
/*    */   public AbstractCreateCharacterTask(StartMenuWizard wizard, Localizer localizer, TCGDialog waitDialog, AsyncMessageSender<? extends Message> sender) {
/* 16 */     super(sender);
/* 17 */     this.wizard = wizard;
/* 18 */     this.localizer = localizer;
/* 19 */     this.waitDialog = waitDialog;
/*    */   }
/*    */   private final TCGDialog waitDialog; protected final StartMenuWizard wizard;
/*    */   
/*    */   protected void onDone() {
/* 24 */     PanelManager.getInstance().closeWindow((BWindow)this.waitDialog);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onError(AccountResult error) {
/* 29 */     String messageKey = getMessageForErrorCode(error);
/*    */     
/* 31 */     TCGDialog.showMessage("loginwindow.faileddialog.title", messageKey, this.localizer, null);
/*    */ 
/*    */     
/* 34 */     if (error != AccountResult.ERROR_INVALID_NICK && error != AccountResult.ERROR_INVALID_INPUT_DATA)
/*    */     {
/* 36 */       this.wizard.startWizard();
/*    */     }
/*    */   }
/*    */   
/*    */   private String getMessageForErrorCode(AccountResult result) {
/* 41 */     switch (result) {
/*    */       case ERROR_INVALID_NICK:
/* 43 */         return "charcreatewindow.error.invalidnick.text";
/*    */       case ERROR_INVALID_INPUT_DATA:
/* 45 */         return "charcreatewindow.error.servercheckedinvaliddata.text";
/*    */       case ERROR_SERVER_BUSY:
/* 47 */         return "charcreatewindow.error.serverbusy.text";
/*    */       case ERROR_CLIENT_SIDE:
/* 49 */         return "charcreatewindow.error.clientside.text";
/*    */       case ERROR_SERVER_NOT_FOUND:
/* 51 */         return "charcreatewindow.error.servernotfound.text";
/*    */       case ERROR_CREATION_DISABLED:
/* 53 */         return "charcreatewindow.error.creationnotallowed.text";
/*    */     } 
/*    */     
/* 56 */     return "charcreatewindow.error.clientside.text";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\AbstractCreateCharacterTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */