/*     */ package com.funcom.tcg.client.ui.account;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.PeelWindowEvent;
/*     */ import com.funcom.tcg.client.ui.friend.KeyTypedListener;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.startmenu.HoverPasswordField;
/*     */ import com.funcom.tcg.client.ui.startmenu.MessagedBTextField;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BTextField;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.KeyEvent;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegisterWindow
/*     */   extends BPeelWindow
/*     */ {
/*     */   private MessagedBTextField new_textfield_charname;
/*     */   private HoverPasswordField new_textfield_password;
/*     */   private HoverPasswordField new_textfield_retypepass;
/*     */   private MessagedBTextField new_textfield_parentemail;
/*     */   private MessagedBTextField new_textfield_gamecode;
/*     */   private final AccountRegistrationHandler registrationHandler;
/*     */   
/*     */   public RegisterWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, AccountRegistrationHandler registrationHandler, Localizer localizer) {
/*  44 */     super(windowName, bananaPeel);
/*     */ 
/*     */     
/*  47 */     this.registrationHandler = registrationHandler;
/*  48 */     setLayer(1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     BLabel text_header = (BLabel)findComponent((BContainer)this, "text_header");
/*  54 */     BLabel text_instructions = (BLabel)findComponent((BContainer)this, "text_instructions");
/*     */ 
/*     */     
/*  57 */     BLabel text_charname = (BLabel)findComponent((BContainer)this, "text_charname");
/*  58 */     BLabel text_password = (BLabel)findComponent((BContainer)this, "text_password");
/*  59 */     BLabel text_retypepass = (BLabel)findComponent((BContainer)this, "text_retypepass");
/*  60 */     BLabel text_parentemail = (BLabel)findComponent((BContainer)this, "text_parentemail");
/*     */ 
/*     */     
/*  63 */     BTextField old_textfield_charname = (BTextField)findComponent((BContainer)this, "textfield_charname");
/*  64 */     BTextField old_textfield_password = (BTextField)findComponent((BContainer)this, "textfield_password");
/*  65 */     BTextField old_textfield_retypepass = (BTextField)findComponent((BContainer)this, "textfield_retypepass");
/*  66 */     BTextField old_textfield_parentemail = (BTextField)findComponent((BContainer)this, "textfield_parentemail");
/*  67 */     BTextField old_textfield_gamecode = (BTextField)findComponent((BContainer)this, "textfield_gamecode");
/*     */ 
/*     */     
/*  70 */     this.new_textfield_charname = new MessagedBTextField(new NickValidator());
/*  71 */     overridePeelerComponent((BComponent)this.new_textfield_charname, (BComponent)old_textfield_charname);
/*  72 */     this.new_textfield_charname.setEmptyDisplayText(localizer.getLocalizedText(getClass(), "registrationwindow.text.charactername", new String[0]));
/*     */     
/*  74 */     this.new_textfield_charname.setMaxLength(14);
/*     */ 
/*     */     
/*  77 */     this.new_textfield_password = new HoverPasswordField();
/*  78 */     overridePeelerComponent((BComponent)this.new_textfield_password, (BComponent)old_textfield_password);
/*  79 */     this.new_textfield_password.setMaxLength(20);
/*  80 */     this.new_textfield_password.setText("");
/*     */ 
/*     */     
/*  83 */     this.new_textfield_retypepass = new HoverPasswordField();
/*  84 */     overridePeelerComponent((BComponent)this.new_textfield_retypepass, (BComponent)old_textfield_retypepass);
/*  85 */     this.new_textfield_retypepass.setMaxLength(20);
/*  86 */     this.new_textfield_retypepass.setText("");
/*     */ 
/*     */     
/*  89 */     this.new_textfield_parentemail = new MessagedBTextField(new EmailValidator());
/*  90 */     overridePeelerComponent((BComponent)this.new_textfield_parentemail, (BComponent)old_textfield_parentemail);
/*  91 */     this.new_textfield_parentemail.setEmptyDisplayText(localizer.getLocalizedText(getClass(), "registrationwindow.text.parentsemail", new String[0]));
/*     */     
/*  93 */     this.new_textfield_parentemail.setMaxLength(177);
/*     */ 
/*     */     
/*  96 */     this.new_textfield_gamecode = new MessagedBTextField(new GameCodeValidator());
/*  97 */     overridePeelerComponent((BComponent)this.new_textfield_gamecode, (BComponent)old_textfield_gamecode);
/*  98 */     this.new_textfield_gamecode.setMaxLength(16);
/*     */ 
/*     */     
/* 101 */     text_header.setText(localizer.getLocalizedText(getClass(), "registrationwindow.title", new String[0]));
/* 102 */     text_instructions.setText(localizer.getLocalizedText(getClass(), "registrationwindow.message.text", new String[0]));
/* 103 */     text_charname.setText(localizer.getLocalizedText(getClass(), "registrationwindow.label.charactername", new String[0]));
/* 104 */     text_password.setText(localizer.getLocalizedText(getClass(), "registrationwindow.label.password", new String[0]));
/* 105 */     text_retypepass.setText(localizer.getLocalizedText(getClass(), "registrationwindow.label.passwordretype", new String[0]));
/* 106 */     text_parentemail.setText(localizer.getLocalizedText(getClass(), "registrationwindow.label.parentsemail", new String[0]));
/*     */ 
/*     */     
/* 109 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/* 110 */     eventsWireMap.put("button_checkmark", new PeelWindowEvent[] { new PeelWindowEvent("Ok_Click", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 112 */     eventsWireMap.put("button_close", new PeelWindowEvent[] { new PeelWindowEvent("Close_Click", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 114 */     wireEvents(eventsWireMap, this);
/*     */     
/* 116 */     this.new_textfield_charname.addListener((ComponentListener)new KeyTypedListener()
/*     */         {
/*     */           public void keyPressed(KeyEvent event) {
/* 119 */             super.keyPressed(event);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void keyReleased(KeyEvent event) {
/* 125 */             super.keyReleased(event);
/* 126 */             RegisterWindow.this.new_textfield_charname.setText(RegisterWindow.this.new_textfield_charname.getText().toLowerCase());
/*     */           }
/*     */         });
/*     */     
/* 130 */     this.new_textfield_parentemail.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 133 */             RegisterWindow.this.confirmRegister();
/*     */           }
/*     */         });
/*     */     
/* 137 */     this.new_textfield_gamecode.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 140 */             RegisterWindow.this.confirmRegister();
/*     */           }
/*     */         });
/*     */     
/* 144 */     this.new_textfield_gamecode.addListener((ComponentListener)new KeyTypedListener()
/*     */         {
/*     */           public void keyPressed(KeyEvent event) {
/* 147 */             super.keyPressed(event);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void keyReleased(KeyEvent event) {
/* 153 */             super.keyReleased(event);
/* 154 */             RegisterWindow.this.new_textfield_gamecode.setText(RegisterWindow.this.new_textfield_gamecode.getText().toUpperCase());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static class EmailValidator
/*     */     implements MessagedBTextField.CharValidator {
/*     */     public boolean isValid(char c) {
/* 162 */       return StringUtils.isEmailChar(c);
/*     */     }
/*     */     
/*     */     private EmailValidator() {} }
/*     */   
/*     */   public static class NickValidator implements MessagedBTextField.CharValidator {
/*     */     public boolean isValid(char c) {
/* 169 */       return StringUtils.isCharacterNameChar(c);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GameCodeValidator
/*     */     implements MessagedBTextField.CharValidator {
/*     */     public boolean isValid(char c) {
/* 176 */       return StringUtils.isCharacterNameChar(c);
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeWindow() {
/* 181 */     PanelManager.getInstance().closeWindow((BWindow)this);
/* 182 */     if (MainGameState.getPauseModel().isPaused()) {
/* 183 */       MainGameState.getPauseModel().reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 189 */     super.setVisible(visible);
/* 190 */     if (!visible) {
/* 191 */       MainGameState.getAfkShutdownHandler().setOverride(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void Ok_Click(BPeelWindow window, BComponent component) {
/* 197 */     confirmRegister();
/*     */   }
/*     */   
/*     */   private void confirmRegister() {
/* 201 */     if (this.registrationHandler.checkDataOk(this, this.new_textfield_charname.getText(), this.new_textfield_password.getText(), this.new_textfield_retypepass.getText(), this.new_textfield_parentemail.getText())) {
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
/* 213 */       closeWindow();
/*     */ 
/*     */       
/* 216 */       this.registrationHandler.register(this.new_textfield_charname.getText().trim(), this.new_textfield_password.getText(), this.new_textfield_parentemail.getText().trim().toLowerCase(), this.new_textfield_gamecode.getText().trim());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       HttpMetrics.postEvent(HttpMetrics.Event.CHARACTER_SAVED);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void Close_Click(BPeelWindow window, BComponent component) {
/* 231 */     closeWindow();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\account\RegisterWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */