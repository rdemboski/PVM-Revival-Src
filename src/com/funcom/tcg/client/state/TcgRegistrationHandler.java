/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TCGDialog;
/*     */ import com.funcom.tcg.client.ui.account.AccountRegistrationHandler;
/*     */ import com.funcom.tcg.client.ui.account.RegisterWindow;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.net.message.AccountRegisterRequestMessage;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import java.util.Locale;
/*     */ 
/*     */ class TcgRegistrationHandler
/*     */   implements AccountRegistrationHandler
/*     */ {
/*     */   private static final String LOGINWINDOW_TITLE_KEY = "dialog.title.error";
/*     */   private static final String LOCALIZATIONKEY_ERROR_PASSWORD_MISMATCH = "registrationwindow.passwordmismatch.text";
/*     */   private static final String LOCALIZATIONKEY_ERROR_MAIL_PATTERN_INVALID = "charcreatewindow.error.invalidemail.text";
/*     */   private static final String LOCALIZATIONKEY_ERROR_TOO_SHORT_NAME = "charcreatewindow.error.tooshortname.text";
/*     */   private static final String LOCALIZATIONKEY_ERROR_INVALID_NAME = "charcreatewindow.error.invalidname.text";
/*     */   private static final String LOCALIZATIONKEY_ERROR_TOO_SHORT_PASSWORD = "charcreatewindow.error.tooshortpassword.text";
/*     */   private static final String LOCALIZATIONKEY_ERROR_USED = "accountregisterdialog.error";
/*     */   private final Localizer localizer;
/*     */   
/*     */   public TcgRegistrationHandler(Localizer localizer) {
/*  30 */     this.localizer = localizer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(String name, String plainPassword, String email, String gamecode) {
/*     */     try {
/*  37 */       AccountRegisterRequestMessage message = new AccountRegisterRequestMessage(name, plainPassword, email, Locale.getDefault().getLanguage(), gamecode);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  42 */       NetworkHandler.instance().getIOHandler().send((Message)message);
/*  43 */     } catch (InterruptedException ignore) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkDataOk(RegisterWindow caller, String name, String password, String retypePassword, String email) {
/*  50 */     String wantedName = name.trim();
/*  51 */     if (wantedName.isEmpty()) {
/*  52 */       TCGDialog.showMessage("dialog.title.error", "startmenu.model.errors.noname", this.localizer, new ReopenHandler(caller));
/*     */       
/*  54 */       return false;
/*     */     } 
/*     */     
/*  57 */     if (wantedName.length() < 6) {
/*  58 */       TCGDialog.showMessage("dialog.title.error", "charcreatewindow.error.tooshortname.text", this.localizer, new ReopenHandler(caller));
/*     */       
/*  60 */       return false;
/*     */     } 
/*     */     
/*  63 */     if (wantedName.toLowerCase().startsWith("trial") || wantedName.toLowerCase().startsWith("pvm")) {
/*  64 */       TCGDialog.showMessage("dialog.title.error", "accountregisterdialog.error", this.localizer, new ReopenHandler(caller));
/*     */       
/*  66 */       return false;
/*     */     } 
/*     */     
/*  69 */     if (!StringUtils.isCharacterNameFormat(wantedName)) {
/*  70 */       TCGDialog.showMessage("dialog.title.error", "charcreatewindow.error.invalidname.text", this.localizer, new ReopenHandler(caller));
/*     */       
/*  72 */       return false;
/*     */     } 
/*     */     
/*  75 */     if (password.length() < 6) {
/*  76 */       TCGDialog.showMessage("dialog.title.error", "charcreatewindow.error.tooshortpassword.text", this.localizer, new ReopenHandler(caller));
/*     */       
/*  78 */       return false;
/*     */     } 
/*     */     
/*  81 */     if (!password.equals(retypePassword)) {
/*  82 */       TCGDialog.showMessage("dialog.title.error", "registrationwindow.passwordmismatch.text", this.localizer, new ReopenHandler(caller));
/*     */       
/*  84 */       return false;
/*     */     } 
/*     */     
/*  87 */     String parentsEmailStr = email.trim().toLowerCase();
/*  88 */     if (!StringUtils.isEmailFormat(parentsEmailStr)) {
/*  89 */       TCGDialog.showMessage("dialog.title.error", "charcreatewindow.error.invalidemail.text", this.localizer, new ReopenHandler(caller));
/*     */       
/*  91 */       return false;
/*     */     } 
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private class ReopenHandler
/*     */     implements ActionListener
/*     */   {
/*     */     private final RegisterWindow caller;
/*     */ 
/*     */     
/*     */     public ReopenHandler(RegisterWindow caller) {
/* 104 */       this.caller = caller;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 109 */       PanelManager.getInstance().addWindow((BWindow)this.caller);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TcgRegistrationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */