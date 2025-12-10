/*     */ package com.funcom.tcg.client.ui.mainmenu;
/*     */ 
/*     */ import com.funcom.audio.SoundSystemFactory;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.DisplayResolutionHelper;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuListener;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.io.IOException;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptionsWindow
/*     */   extends BPeelWindow
/*     */ {
/*     */   private static int CENTER_OFFSET_X;
/*     */   private static int CENTER_OFFSET_Y;
/*     */   private static Rectangle SIZE_PLAY_BUTTON;
/*     */   private static Rectangle SIZE_BACK_BUTTON;
/*     */   private BLabel optionsTitleLabel;
/*     */   private BLabel optionsSeparatorLabel;
/*     */   private BLabel screenSizeHeaderLabel;
/*     */   private BLabel audioHeaderLabel;
/*     */   private BLabel screenSizeIconLabel;
/*     */   private BLabel audioIconLabel;
/*     */   private BLabel screenSizeSeparatorLabel;
/*     */   private BLabel audioSeparatorLabel;
/*     */   private BLabel screenSizeSectionLabel;
/*     */   private BLabel fullscreenSectionLabel;
/*     */   private BLabel soundSectionLabel;
/*     */   private BLabel musicSectionLabel;
/*     */   private BLabel fullscreenOnOffBgdLabel;
/*     */   private BLabel soundOnOffBgdLabel;
/*  50 */   private ScreenSizeType screenSizeType = ScreenSizeType.Small, oldScreenSize = ScreenSizeType.Small, bufferedScreenSize = ScreenSizeType.Small; private BLabel musicOnOffBgdLabel; private BLabel screenBgd; private BLabel audioBgdLabel; private BToggleButton screenSizeToggleButton; private BToggleButton fullscreenOnButton; private BToggleButton fullscreenOffButton; private BToggleButton soundOnButton; private BToggleButton soundOffButton; private BToggleButton musicOnButton; private BToggleButton musicOffButton; private BButton backButton; private BButton applyButton; private StartMenuListener menuListener; private MainMenuModel model; private BContainer screenSizeOptions; private BToggleButton screenSizeLowButton;
/*     */   private BToggleButton screenSizeMediumButton;
/*     */   private BToggleButton screenSizeHighButton;
/*     */   
/*     */   public OptionsWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, StartMenuListener menuListener, MainMenuModel model) {
/*  55 */     super(windowName, bananaPeel);
/*  56 */     this.menuListener = menuListener;
/*  57 */     this.model = model;
/*     */     
/*  59 */     setLocation(0, 0);
/*  60 */     int WIDTH = DisplaySystem.getDisplaySystem().getWidth();
/*  61 */     int HEIGHT = (DisplaySystem.getDisplaySystem().getHeight() < 768) ? 768 : DisplaySystem.getDisplaySystem().getHeight();
/*  62 */     setSize(WIDTH, HEIGHT);
/*  63 */     CENTER_OFFSET_X = (WIDTH - 1024) / 2;
/*  64 */     CENTER_OFFSET_Y = (HEIGHT - 768) / 2;
/*     */     
/*  66 */     int buttonSize = System.getProperty("tcg.locale").equals("en") ? 170 : 215;
/*  67 */     SIZE_PLAY_BUTTON = new Rectangle(CENTER_OFFSET_X + 1024 - buttonSize, CENTER_OFFSET_Y, buttonSize, 69);
/*  68 */     SIZE_BACK_BUTTON = new Rectangle(CENTER_OFFSET_X, CENTER_OFFSET_Y, buttonSize, 69);
/*     */     
/*  70 */     initComponents();
/*  71 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  78 */     BContainer container = new BContainer();
/*  79 */     BComponent placeholder = findComponent((BContainer)this, "main_container");
/*  80 */     placeholder.setSize(getWidth(), getHeight());
/*  81 */     overridePeelerComponent((BComponent)container, placeholder);
/*     */     
/*  83 */     this.optionsTitleLabel = new BLabel(TcgGame.getLocalizedText("startmenu.loginmethod.button.options", new String[0]).toUpperCase());
/*  84 */     placeholder = findComponent((BContainer)this, "label_options_title");
/*  85 */     offsetPlaceholder(placeholder);
/*  86 */     overridePeelerComponent((BComponent)this.optionsTitleLabel, placeholder);
/*     */     
/*  88 */     this.optionsSeparatorLabel = new BLabel("");
/*  89 */     placeholder = findComponent((BContainer)this, "options_header_separator");
/*  90 */     offsetPlaceholder(placeholder);
/*  91 */     overridePeelerComponent((BComponent)this.optionsSeparatorLabel, placeholder);
/*     */ 
/*     */     
/*  94 */     this.screenBgd = new BLabel("");
/*  95 */     placeholder = findComponent((BContainer)this, "screen_bgd");
/*  96 */     offsetPlaceholder(placeholder);
/*  97 */     overridePeelerComponent((BComponent)this.screenBgd, placeholder);
/*     */     
/*  99 */     this.screenSizeHeaderLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.screensize", new String[0]));
/* 100 */     placeholder = findComponent((BContainer)this, "label_screen_header");
/* 101 */     offsetPlaceholder(placeholder);
/* 102 */     overridePeelerComponent((BComponent)this.screenSizeHeaderLabel, placeholder);
/*     */     
/* 104 */     this.screenSizeIconLabel = new BLabel("");
/* 105 */     placeholder = findComponent((BContainer)this, "label_screen_icon");
/* 106 */     offsetPlaceholder(placeholder);
/* 107 */     overridePeelerComponent((BComponent)this.screenSizeIconLabel, placeholder);
/*     */     
/* 109 */     this.screenSizeSeparatorLabel = new BLabel("");
/* 110 */     placeholder = findComponent((BContainer)this, "screen_header_separator");
/* 111 */     offsetPlaceholder(placeholder);
/* 112 */     overridePeelerComponent((BComponent)this.screenSizeSeparatorLabel, placeholder);
/*     */     
/* 114 */     this.screenSizeSectionLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.size", new String[0]));
/* 115 */     placeholder = findComponent((BContainer)this, "label_section_size");
/* 116 */     offsetPlaceholder(placeholder);
/* 117 */     overridePeelerComponent((BComponent)this.screenSizeSectionLabel, placeholder);
/*     */     
/* 119 */     this.screenSizeToggleButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.screensize.small", new String[0]));
/* 120 */     placeholder = findComponent((BContainer)this, "button_screen_size");
/* 121 */     offsetPlaceholder(placeholder);
/* 122 */     overridePeelerComponent((BComponent)this.screenSizeToggleButton, placeholder);
/* 123 */     this.screenSizeToggleButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.expand", new String[0]));
/*     */     
/* 125 */     this.fullscreenSectionLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.fullscreen", new String[0]));
/* 126 */     placeholder = findComponent((BContainer)this, "label_fullscreen_section");
/* 127 */     offsetPlaceholder(placeholder);
/* 128 */     overridePeelerComponent((BComponent)this.fullscreenSectionLabel, placeholder);
/*     */     
/* 130 */     this.fullscreenOnOffBgdLabel = new BLabel("");
/* 131 */     placeholder = findComponent((BContainer)this, "label_onoff_bgd_fullscreen");
/* 132 */     offsetPlaceholder(placeholder);
/* 133 */     overridePeelerComponent((BComponent)this.fullscreenOnOffBgdLabel, placeholder);
/*     */     
/* 135 */     this.fullscreenOnButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.on", new String[0]).toUpperCase());
/* 136 */     placeholder = findComponent((BContainer)this, "button_fullscreen_on");
/* 137 */     offsetPlaceholder(placeholder);
/* 138 */     overridePeelerComponent((BComponent)this.fullscreenOnButton, placeholder);
/* 139 */     this.fullscreenOnButton.setSelected(TcgGame.getGameSettings().isFullscreen());
/*     */     
/* 141 */     this.fullscreenOffButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.off", new String[0]).toUpperCase());
/* 142 */     placeholder = findComponent((BContainer)this, "button_fullscreen_off");
/* 143 */     offsetPlaceholder(placeholder);
/* 144 */     overridePeelerComponent((BComponent)this.fullscreenOffButton, placeholder);
/* 145 */     this.fullscreenOffButton.setSelected(!TcgGame.getGameSettings().isFullscreen());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     this.audioBgdLabel = new BLabel("");
/* 151 */     placeholder = findComponent((BContainer)this, "audio_bgd");
/* 152 */     offsetPlaceholder(placeholder);
/* 153 */     overridePeelerComponent((BComponent)this.audioBgdLabel, placeholder);
/*     */     
/* 155 */     this.audioHeaderLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.audio", new String[0]));
/* 156 */     placeholder = findComponent((BContainer)this, "label_audio_header");
/* 157 */     offsetPlaceholder(placeholder);
/* 158 */     overridePeelerComponent((BComponent)this.audioHeaderLabel, placeholder);
/*     */     
/* 160 */     this.audioIconLabel = new BLabel("");
/* 161 */     placeholder = findComponent((BContainer)this, "label_audio_icon");
/* 162 */     offsetPlaceholder(placeholder);
/* 163 */     overridePeelerComponent((BComponent)this.audioIconLabel, placeholder);
/*     */     
/* 165 */     this.audioSeparatorLabel = new BLabel("");
/* 166 */     placeholder = findComponent((BContainer)this, "audio_header_separator");
/* 167 */     offsetPlaceholder(placeholder);
/* 168 */     overridePeelerComponent((BComponent)this.audioSeparatorLabel, placeholder);
/*     */     
/* 170 */     this.soundSectionLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.sound", new String[0]));
/* 171 */     placeholder = findComponent((BContainer)this, "label_sound_section");
/* 172 */     offsetPlaceholder(placeholder);
/* 173 */     overridePeelerComponent((BComponent)this.soundSectionLabel, placeholder);
/*     */     
/* 175 */     this.soundOnOffBgdLabel = new BLabel("");
/* 176 */     placeholder = findComponent((BContainer)this, "label_onoff_bgd_sound");
/* 177 */     offsetPlaceholder(placeholder);
/* 178 */     overridePeelerComponent((BComponent)this.soundOnOffBgdLabel, placeholder);
/*     */     
/* 180 */     this.soundOnButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.on", new String[0]).toUpperCase());
/* 181 */     placeholder = findComponent((BContainer)this, "button_sound_on");
/* 182 */     offsetPlaceholder(placeholder);
/* 183 */     overridePeelerComponent((BComponent)this.soundOnButton, placeholder);
/* 184 */     this.soundOnButton.setSelected(!SoundSystemFactory.getSoundSystem().isSfxMute());
/*     */     
/* 186 */     this.soundOffButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.off", new String[0]).toUpperCase());
/* 187 */     placeholder = findComponent((BContainer)this, "button_sound_off");
/* 188 */     offsetPlaceholder(placeholder);
/* 189 */     overridePeelerComponent((BComponent)this.soundOffButton, placeholder);
/* 190 */     this.soundOffButton.setSelected(SoundSystemFactory.getSoundSystem().isSfxMute());
/*     */     
/* 192 */     this.musicSectionLabel = new BLabel(TcgGame.getLocalizedText("optionswindow.music", new String[0]));
/* 193 */     placeholder = findComponent((BContainer)this, "label_music_section");
/* 194 */     offsetPlaceholder(placeholder);
/* 195 */     overridePeelerComponent((BComponent)this.musicSectionLabel, placeholder);
/*     */     
/* 197 */     this.musicOnOffBgdLabel = new BLabel("");
/* 198 */     placeholder = findComponent((BContainer)this, "label_onoff_bgd_music");
/* 199 */     offsetPlaceholder(placeholder);
/* 200 */     overridePeelerComponent((BComponent)this.musicOnOffBgdLabel, placeholder);
/*     */     
/* 202 */     this.musicOnButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.on", new String[0]).toUpperCase());
/* 203 */     placeholder = findComponent((BContainer)this, "button_music_on");
/* 204 */     offsetPlaceholder(placeholder);
/* 205 */     overridePeelerComponent((BComponent)this.musicOnButton, placeholder);
/* 206 */     this.musicOnButton.setSelected(!SoundSystemFactory.getSoundSystem().isMusicMute());
/*     */     
/* 208 */     this.musicOffButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.fullscreen.off", new String[0]).toUpperCase());
/* 209 */     placeholder = findComponent((BContainer)this, "button_music_off");
/* 210 */     offsetPlaceholder(placeholder);
/* 211 */     overridePeelerComponent((BComponent)this.musicOffButton, placeholder);
/* 212 */     this.musicOffButton.setSelected(SoundSystemFactory.getSoundSystem().isMusicMute());
/*     */     
/* 214 */     this.applyButton = new BButton("");
/* 215 */     placeholder = findComponent((BContainer)this, "button_apply");
/* 216 */     if (this.menuListener != null) {
/* 217 */       this.applyButton.setText(TcgGame.getLocalizedText("optionswindow.apply", new String[0]).toUpperCase());
/* 218 */       offsetPlaceholder(placeholder);
/* 219 */       overridePeelerComponent((BComponent)this.applyButton, placeholder);
/* 220 */       this.applyButton.getParent().remove((BComponent)this.applyButton);
/* 221 */       add((BComponent)this.applyButton, SIZE_PLAY_BUTTON);
/*     */     } else {
/* 223 */       placeholder.getParent().remove(placeholder);
/* 224 */       this.applyButton.setStyleClass("button_accept");
/* 225 */       int y = (DisplaySystem.getDisplaySystem().getHeight() < 880) ? 115 : 10;
/* 226 */       add((BComponent)this.applyButton, new Rectangle(DisplaySystem.getDisplaySystem().getWidth() - 64, y, 54, 54));
/* 227 */       this.applyButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.accept", new String[0]));
/*     */     } 
/*     */ 
/*     */     
/* 231 */     this.backButton = new BButton("");
/* 232 */     placeholder = findComponent((BContainer)this, "button_back");
/* 233 */     if (this.menuListener != null) {
/* 234 */       this.backButton.setText(TcgGame.getLocalizedText("mainmenuwindow.back", new String[0]).toUpperCase());
/* 235 */       overridePeelerComponent((BComponent)this.backButton, placeholder);
/* 236 */       this.backButton.getParent().remove((BComponent)this.backButton);
/* 237 */       add((BComponent)this.backButton, SIZE_BACK_BUTTON);
/*     */     } else {
/* 239 */       placeholder.getParent().remove(placeholder);
/* 240 */       this.backButton.setStyleClass("close-button");
/*     */       
/* 242 */       int y = (DisplaySystem.getDisplaySystem().getHeight() < 880) ? 115 : 10;
/* 243 */       add((BComponent)this.backButton, new Rectangle(10, y, 54, 54));
/* 244 */       this.backButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.cancel", new String[0]));
/*     */     } 
/*     */     
/* 247 */     this.screenSizeOptions = new BContainer((BLayoutManager)new AbsoluteLayout());
/* 248 */     this.screenSizeOptions.setStyleClass("options_container");
/* 249 */     this.screenSizeOptions.setSize(240, 135);
/* 250 */     this.screenSizeLowButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.screensize.small", new String[0]));
/* 251 */     this.screenSizeLowButton.setStyleClass("button");
/* 252 */     this.screenSizeMediumButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.screensize.medium", new String[0]));
/* 253 */     this.screenSizeMediumButton.setStyleClass("button");
/* 254 */     this.screenSizeHighButton = new BToggleButton(TcgGame.getLocalizedText("optionswindow.screensize.large", new String[0]));
/* 255 */     this.screenSizeHighButton.setStyleClass("button");
/*     */     
/* 257 */     this.screenSizeOptions.add((BComponent)this.screenSizeHighButton, new Rectangle(10, 0, 230, 50));
/* 258 */     this.screenSizeOptions.add((BComponent)this.screenSizeMediumButton, new Rectangle(10, 45, 230, 50));
/* 259 */     this.screenSizeOptions.add((BComponent)this.screenSizeLowButton, new Rectangle(10, 90, 230, 50));
/*     */     
/* 261 */     add((BComponent)this.screenSizeOptions, new Rectangle(this.screenSizeToggleButton.getX() - 10, this.screenSizeToggleButton.getY() - this.screenSizeOptions.getHeight(), this.screenSizeOptions.getWidth(), this.screenSizeOptions.getHeight()));
/*     */ 
/*     */ 
/*     */     
/* 265 */     this.screenSizeOptions.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void offsetPlaceholder(BComponent placeholder) {
/* 270 */     if (placeholder != null) {
/* 271 */       placeholder.setLocation(placeholder.getX() + CENTER_OFFSET_X, placeholder.getY() + CENTER_OFFSET_Y);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 276 */     this.backButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 279 */             if (OptionsWindow.this.menuListener != null) {
/* 280 */               TcgUI.getUISoundPlayer().play("ClickBackward");
/* 281 */               OptionsWindow.this.menuListener.optionsBack();
/*     */             } else {
/* 283 */               OptionsWindow.this.model.mainMenu();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 288 */     this.fullscreenOffButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 291 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.FULLSCREEN, false);
/*     */           }
/*     */         });
/*     */     
/* 295 */     this.fullscreenOnButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 298 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.FULLSCREEN, true);
/*     */           }
/*     */         });
/*     */     
/* 302 */     this.soundOffButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 305 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.SOUND, false);
/*     */           }
/*     */         });
/*     */     
/* 309 */     this.soundOnButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 312 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.SOUND, true);
/*     */           }
/*     */         });
/*     */     
/* 316 */     this.musicOffButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 319 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.MUSIC, false);
/*     */           }
/*     */         });
/*     */     
/* 323 */     this.musicOnButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 326 */             OptionsWindow.this.toggle(OptionsWindow.ToggleType.MUSIC, true);
/*     */           }
/*     */         });
/*     */     
/* 330 */     this.screenSizeToggleButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 333 */             if (OptionsWindow.this.screenSizeToggleButton.isSelected()) {
/* 334 */               TcgUI.getUISoundPlayer().play("ClickForward");
/* 335 */               OptionsWindow.this.screenSizeOptions.setVisible(true);
/* 336 */               OptionsWindow.this.screenSizeChange(OptionsWindow.this.screenSizeType);
/* 337 */               OptionsWindow.this.screenSizeToggleButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.collapse", new String[0]));
/*     */             } else {
/* 339 */               TcgUI.getUISoundPlayer().play("ClickBackward");
/* 340 */               OptionsWindow.this.screenSizeOptions.setVisible(false);
/* 341 */               OptionsWindow.this.screenSizeToggleButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.expand", new String[0]));
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 346 */     this.screenSizeLowButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 349 */             OptionsWindow.this.screenSizeChange(OptionsWindow.ScreenSizeType.Small);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 354 */     this.screenSizeMediumButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 357 */             OptionsWindow.this.screenSizeChange(OptionsWindow.ScreenSizeType.Medium);
/*     */           }
/*     */         });
/*     */     
/* 361 */     this.screenSizeHighButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 364 */             OptionsWindow.this.screenSizeChange(OptionsWindow.ScreenSizeType.Large);
/*     */           }
/*     */         });
/*     */     
/* 368 */     this.applyButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 371 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 372 */             OptionsWindow.this.applyChanges();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void applyChanges() {
/*     */     try {
/* 379 */       boolean oldFullscreen = DisplaySystem.getDisplaySystem().isFullScreen();
/* 380 */       boolean newFullscreen = this.fullscreenOnButton.isSelected();
/*     */ 
/*     */       
/* 383 */       if (!this.oldScreenSize.equals(this.screenSizeType) || oldFullscreen != newFullscreen) {
/* 384 */         switch (this.screenSizeType) {
/*     */           case SOUND:
/* 386 */             if (this.menuListener != null)
/* 387 */             { this.menuListener.setRes((DisplayResolutionHelper.getInstance()).lowWindowResolution, newFullscreen); }
/* 388 */             else { this.model.setRes((DisplayResolutionHelper.getInstance()).lowWindowResolution, newFullscreen); }
/* 389 */              TcgGame.getGameSettings().setWidth((DisplayResolutionHelper.getInstance()).lowWindowResolution.getWidth());
/* 390 */             TcgGame.getGameSettings().setHeight((DisplayResolutionHelper.getInstance()).lowWindowResolution.getHeight());
/*     */             break;
/*     */           case MUSIC:
/* 393 */             if (this.menuListener != null)
/* 394 */             { this.menuListener.setRes((DisplayResolutionHelper.getInstance()).mediumWindowResolution, newFullscreen); }
/* 395 */             else { this.model.setRes((DisplayResolutionHelper.getInstance()).mediumWindowResolution, newFullscreen); }
/* 396 */              TcgGame.getGameSettings().setWidth((DisplayResolutionHelper.getInstance()).mediumWindowResolution.getWidth());
/* 397 */             TcgGame.getGameSettings().setHeight((DisplayResolutionHelper.getInstance()).mediumWindowResolution.getHeight());
/*     */             break;
/*     */           
/*     */           case FULLSCREEN:
/* 401 */             if (this.menuListener != null)
/* 402 */             { this.menuListener.setRes((DisplayResolutionHelper.getInstance()).highWindowResolution, newFullscreen); }
/* 403 */             else { this.model.setRes((DisplayResolutionHelper.getInstance()).highWindowResolution, newFullscreen); }
/* 404 */              TcgGame.getGameSettings().setWidth((DisplayResolutionHelper.getInstance()).highWindowResolution.getWidth());
/* 405 */             TcgGame.getGameSettings().setHeight((DisplayResolutionHelper.getInstance()).highWindowResolution.getHeight());
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 412 */       TcgGame.getGameSettings().setFullscreen(this.fullscreenOnButton.isSelected());
/*     */       
/* 414 */       TcgGame.getPreferences().saveSound(this.soundOnButton.isSelected());
/* 415 */       SoundSystemFactory.getSoundSystem().setSfxMute(!this.soundOnButton.isSelected());
/* 416 */       TcgGame.getPreferences().saveMusic(this.musicOnButton.isSelected());
/* 417 */       SoundSystemFactory.getSoundSystem().setMusicMute(!this.musicOnButton.isSelected());
/*     */       
/* 419 */       TcgGame.getGameSettings().save();
/* 420 */       if (this.menuListener != null) { this.menuListener.optionsBack(); }
/* 421 */       else { this.model.mainMenu(); } 
/* 422 */     } catch (IOException e) {
/* 423 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 429 */     super.setVisible(visible);
/* 430 */     this.screenSizeToggleButton.setSelected(false);
/* 431 */     this.screenSizeToggleButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.expand", new String[0]));
/* 432 */     this.screenSizeOptions.setVisible(false);
/*     */     
/* 434 */     if (visible) {
/* 435 */       DisplayMode currentRes = new DisplayMode(TcgGame.getGameSettings().getWidth(), TcgGame.getGameSettings().getHeight());
/*     */       
/* 437 */       DisplayMode lowRes = DisplayResolutionHelper.getInstance().getLowestResolution();
/* 438 */       DisplayMode mediumRes = DisplayResolutionHelper.getInstance().getMediumResolution();
/* 439 */       DisplayMode hiRes = DisplayResolutionHelper.getInstance().getHighestResolution();
/*     */       
/* 441 */       boolean isFullscreen = TcgGame.getGameSettings().isFullscreen();
/* 442 */       toggle(ToggleType.FULLSCREEN, isFullscreen);
/*     */       
/* 444 */       boolean isMusic = TcgGame.getPreferences().getMusic();
/* 445 */       toggle(ToggleType.MUSIC, isMusic);
/*     */       
/* 447 */       boolean isSfx = TcgGame.getPreferences().getSound();
/* 448 */       toggle(ToggleType.SOUND, isSfx);
/*     */       
/* 450 */       if (currentRes.getWidth() == lowRes.getWidth() && currentRes.getHeight() == lowRes.getHeight()) {
/* 451 */         this.screenSizeType = ScreenSizeType.Small;
/* 452 */       } else if (currentRes.getWidth() == mediumRes.getWidth() && currentRes.getHeight() == mediumRes.getHeight()) {
/* 453 */         this.screenSizeType = ScreenSizeType.Medium;
/* 454 */       } else if (currentRes.getWidth() == hiRes.getWidth() && currentRes.getHeight() == hiRes.getHeight()) {
/* 455 */         this.screenSizeType = ScreenSizeType.Large;
/*     */       } else {
/* 457 */         this.screenSizeType = ScreenSizeType.Other;
/*     */       } 
/* 459 */       this.screenSizeToggleButton.setText(this.screenSizeType.getName());
/* 460 */       this.oldScreenSize = this.screenSizeType;
/* 461 */       this.bufferedScreenSize = this.screenSizeType;
/*     */       
/* 463 */       this.fullscreenOnButton.setEnabled(hiRes.isFullscreenCapable());
/* 464 */       this.fullscreenOffButton.setEnabled(hiRes.isFullscreenCapable());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void viewOptions(OptionType type, boolean visible) {
/* 469 */     switch (type) {
/*     */       case SOUND:
/* 471 */         this.screenSizeSectionLabel.setVisible(visible);
/* 472 */         this.screenSizeHeaderLabel.setVisible(visible);
/* 473 */         this.screenSizeToggleButton.setVisible(visible);
/*     */         break;
/*     */       case MUSIC:
/* 476 */         this.soundSectionLabel.setVisible(visible);
/* 477 */         this.soundOffButton.setVisible(visible);
/* 478 */         this.soundOnButton.setVisible(visible);
/* 479 */         this.musicSectionLabel.setVisible(visible);
/* 480 */         this.musicOffButton.setVisible(visible);
/* 481 */         this.musicOnButton.setVisible(visible);
/*     */         break;
/*     */     } 
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
/*     */ 
/*     */   
/*     */   private void aaChange(AAType type) {}
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
/*     */   private void screenSizeChange(ScreenSizeType type) {
/* 526 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 527 */     this.applyButton.setEnabled(true);
/*     */     
/* 529 */     this.screenSizeLowButton.setSelected(false);
/* 530 */     this.screenSizeMediumButton.setSelected(false);
/* 531 */     this.screenSizeHighButton.setSelected(false);
/*     */     
/* 533 */     if (!type.equals(ScreenSizeType.Large) && this.fullscreenOnButton.isSelected()) {
/* 534 */       toggle(ToggleType.FULLSCREEN, false);
/*     */     }
/*     */     
/* 537 */     switch (type) {
/*     */       case SOUND:
/* 539 */         this.screenSizeLowButton.setSelected(true);
/*     */         break;
/*     */       case MUSIC:
/* 542 */         this.screenSizeMediumButton.setSelected(true);
/*     */         break;
/*     */       case FULLSCREEN:
/* 545 */         this.screenSizeHighButton.setSelected(true);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 550 */     this.screenSizeOptions.setVisible(this.screenSizeType.equals(type));
/* 551 */     this.screenSizeToggleButton.setSelected(this.screenSizeOptions.isVisible());
/* 552 */     this.screenSizeToggleButton.setText(TcgGame.getLocalizedText("tooltips.achievements." + (this.screenSizeOptions.isVisible() ? "collapse" : "expand"), new String[0]));
/*     */     
/* 554 */     this.screenSizeToggleButton.setText(type.getName());
/* 555 */     this.screenSizeType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggle(ToggleType type, boolean on) {
/* 561 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 562 */     this.applyButton.setEnabled(true);
/*     */     
/* 564 */     switch (type) {
/*     */       case SOUND:
/* 566 */         this.soundOffButton.setSelected(!on);
/* 567 */         this.soundOnButton.setSelected(on);
/*     */         break;
/*     */       case MUSIC:
/* 570 */         this.musicOffButton.setSelected(!on);
/* 571 */         this.musicOnButton.setSelected(on);
/*     */         break;
/*     */       case FULLSCREEN:
/* 574 */         this.fullscreenOffButton.setSelected(!on);
/* 575 */         this.fullscreenOnButton.setSelected(on);
/*     */         
/* 577 */         if (on) {
/* 578 */           this.bufferedScreenSize = this.screenSizeType;
/*     */           
/* 580 */           if (!this.screenSizeType.equals(ScreenSizeType.Large)) {
/* 581 */             screenSizeChange(ScreenSizeType.Large);
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   enum ToggleType
/*     */   {
/* 590 */     FULLSCREEN,
/* 591 */     SOUND,
/* 592 */     MUSIC;
/*     */   }
/*     */   
/*     */   enum AAType
/*     */   {
/* 597 */     NONE(TcgGame.getLocalizedText("optionswindow.antialias.none", new String[0]), 0),
/* 598 */     LOW(TcgGame.getLocalizedText("optionswindow.antialias.low", new String[0]), 2),
/* 599 */     MEDIUM(TcgGame.getLocalizedText("optionswindow.antialias.medium", new String[0]), 4),
/* 600 */     HIGH(TcgGame.getLocalizedText("optionswindow.antialias.high", new String[0]), 8);
/*     */     
/*     */     final String name;
/*     */     final int samples;
/*     */     
/*     */     AAType(String name, int samples) {
/* 606 */       this.name = name;
/* 607 */       this.samples = samples;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 611 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getSamples() {
/* 615 */       return this.samples;
/*     */     }
/*     */   }
/*     */   
/*     */   enum ScreenSizeType {
/* 620 */     Small(TcgGame.getLocalizedText("optionswindow.screensize.small", new String[0])),
/* 621 */     Medium(TcgGame.getLocalizedText("optionswindow.screensize.medium", new String[0])),
/* 622 */     Large(TcgGame.getLocalizedText("optionswindow.screensize.large", new String[0])),
/* 623 */     Other(TcgGame.getLocalizedText("optionswindow.screensize.other", new String[0]));
/*     */     
/*     */     private String name;
/*     */     
/*     */     ScreenSizeType(String name) {
/* 628 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 632 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   enum OptionType {
/* 637 */     GRAPHICS,
/* 638 */     SOUND,
/* 639 */     CONTROLS;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\mainmenu\OptionsWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */