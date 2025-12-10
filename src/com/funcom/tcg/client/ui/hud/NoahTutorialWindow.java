/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.IrregularWindow;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoahTutorialWindow
/*     */   extends IrregularWindow
/*     */ {
/*     */   private BLabel tutorialTextLabel;
/*     */   private BLabel buttonIconLabel;
/*     */   private BButton nextButton;
/*     */   private HighlightedButton confirmButton;
/*     */   private BButton prevButton;
/*  39 */   private int textIndex = 0;
/*  40 */   private int bufferedLayer = 0;
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   private boolean lastConfirm = false;
/*     */   
/*  46 */   private ArrayList<TutorialText> tutorialTexts = new ArrayList<TutorialText>();
/*     */ 
/*     */   
/*     */   public NoahTutorialWindow(ResourceManager resourceManager, boolean reset) {
/*  50 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  51 */     this.bufferedLayer = getLayer();
/*  52 */     this._style = BuiUtils.createMergedClassStyleSheets(NoahTutorialWindow.class, new BananaResourceProvider(resourceManager));
/*     */     
/*  54 */     this.resourceManager = resourceManager;
/*  55 */     initComponents();
/*     */     
/*  57 */     if (!reset) {
/*  58 */       setText(MainGameState.isPlayerRegistered() ? TcgGame.getLocalizedText("tutorial.welcomeback", new String[0]) : TcgGame.getLocalizedText("tutorial.welcome", new String[0]), QuestTextType.COMPLETION);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/*  65 */     if ((this.nextButton.getAbsoluteX() < mx && this.nextButton.getAbsoluteX() + this.nextButton.getWidth() > mx && this.nextButton.getAbsoluteY() < my && this.nextButton.getAbsoluteY() + this.nextButton.getHeight() > my) || (this.prevButton.getAbsoluteX() < mx && this.prevButton.getAbsoluteX() + this.prevButton.getWidth() > mx && this.prevButton.getAbsoluteY() < my && this.prevButton.getAbsoluteY() + this.prevButton.getHeight() > my) || (this.confirmButton.getAbsoluteX() < mx && this.confirmButton.getAbsoluteX() + this.confirmButton.getWidth() > mx && this.confirmButton.getAbsoluteY() < my && this.confirmButton.getAbsoluteY() + this.confirmButton.getHeight() > my))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  71 */       return super.isHit(mx, my);
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  78 */     boolean showNext = this.nextButton.isVisible();
/*  79 */     boolean showPrev = this.prevButton.isVisible();
/*  80 */     boolean showConfirm = this.confirmButton.isVisible();
/*     */     
/*  82 */     super.setVisible(visible);
/*     */     
/*  84 */     this.nextButton.setVisible(showNext);
/*  85 */     this.prevButton.setVisible(showPrev);
/*  86 */     this.confirmButton.setVisible(showConfirm);
/*     */   }
/*     */   
/*     */   public void initComponents() {
/*  90 */     this.tutorialTextLabel = new BLabel("");
/*  91 */     this.tutorialTextLabel.setStyleClass("tutorial-info-label");
/*     */     
/*  93 */     this.buttonIconLabel = new BLabel("");
/*  94 */     this.buttonIconLabel.setStyleClass("button-icon");
/*     */     
/*  96 */     this.nextButton = new BButton("");
/*  97 */     this.nextButton.setStyleClass("next-button");
/*  98 */     this.nextButton.setVisible(false);
/*  99 */     this.nextButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 102 */             if (NoahTutorialWindow.this.textIndex < NoahTutorialWindow.this.tutorialTexts.size() - 1) {
/* 103 */               NoahTutorialWindow.this.textIndex++;
/*     */             }
/* 105 */             NoahTutorialWindow.this.updateTutorialText();
/*     */           }
/*     */         });
/*     */     
/* 109 */     this.confirmButton = new HighlightedButton();
/* 110 */     this.confirmButton.setStyleClass("confirm-button");
/* 111 */     this.confirmButton.setVisible(false);
/* 112 */     this.confirmButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 115 */             if (TcgGame.isEquipmentTutorial()) {
/* 116 */               BWindow window = TcgUI.getWindowFromClass(CharacterEquipmentWindow.class);
/* 117 */               if (window != null) {
/* 118 */                 NoahTutorialWindow.this.lastConfirm = false;
/* 119 */                 ((CharacterEquipmentWindow)window).nextTutorialStep();
/*     */               } 
/* 121 */             } else if (TcgGame.isPetTutorial()) {
/* 122 */               BWindow window = TcgUI.getWindowFromClass(PetsWindow.class);
/* 123 */               if (window != null) {
/* 124 */                 NoahTutorialWindow.this.lastConfirm = false;
/* 125 */                 ((PetsWindow)window).nextTutorialStep();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 131 */     this.prevButton = new BButton("");
/* 132 */     this.prevButton.setStyleClass("prev-button");
/* 133 */     this.prevButton.setVisible(false);
/* 134 */     this.prevButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 137 */             if (NoahTutorialWindow.this.textIndex > 0) {
/* 138 */               NoahTutorialWindow.this.textIndex--;
/*     */             }
/* 140 */             NoahTutorialWindow.this.updateTutorialText();
/*     */           }
/*     */         });
/*     */     
/* 144 */     add((BComponent)this.tutorialTextLabel, new Rectangle(0, 0, 650, 120));
/* 145 */     add((BComponent)this.buttonIconLabel, new Rectangle(520, 30, 40, 40));
/* 146 */     add((BComponent)this.prevButton, new Rectangle(95, 40, 30, 41));
/* 147 */     add((BComponent)this.nextButton, new Rectangle(600, 40, 30, 41));
/* 148 */     add((BComponent)this.confirmButton, new Rectangle(585, 29, 46, 46));
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateTutorialText() {
/* 153 */     this.tutorialTextLabel.setText(((TutorialText)this.tutorialTexts.get(this.textIndex)).getText());
/* 154 */     this.nextButton.setVisible((this.textIndex != this.tutorialTexts.size() - 1));
/* 155 */     this.prevButton.setVisible((this.textIndex > 0));
/*     */     
/* 157 */     this.confirmButton.setVisible((this.textIndex == this.tutorialTexts.size() - 1 && this.lastConfirm));
/*     */     
/* 159 */     textureButtonIcon(((TutorialText)this.tutorialTexts.get(this.textIndex)).getImgPath());
/*     */   }
/*     */   
/*     */   private void textureButtonIcon(String imgPath) {
/* 163 */     if (!imgPath.equals("")) {
/* 164 */       this.buttonIconLabel.setIcon((BIcon)new ImageIcon((BImage)this.resourceManager.getResource(BImage.class, imgPath, CacheType.NOT_CACHED)));
/*     */     } else {
/*     */       
/* 167 */       this.buttonIconLabel.setIcon(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(String text, QuestTextType textType) {
/* 173 */     if (text.equals("")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     String imgPath = "";
/* 181 */     if (text.contains("[") && text.contains("]")) {
/* 182 */       imgPath = text.substring(text.indexOf("[") + 1, text.indexOf("]"));
/* 183 */       text = text.substring(0, text.indexOf("["));
/*     */     } 
/*     */     
/* 186 */     TutorialText newText = new TutorialText(text, imgPath);
/*     */     
/* 188 */     switch (textType) {
/*     */       case INFO:
/* 190 */         this.lastConfirm = true;
/*     */       case CREATION:
/*     */       case UPDATE:
/*     */       case COMPLETION:
/* 194 */         if (!hasTutorialText(newText)) {
/* 195 */           this.tutorialTexts.add(newText);
/* 196 */           if (!isVisible()) {
/* 197 */             setVisible(true);
/*     */           }
/* 199 */           this.textIndex = this.tutorialTexts.size() - 1;
/*     */         } else {
/*     */           
/* 202 */           this.textIndex = getTutorialTextIndex(newText);
/* 203 */           this.textIndex = (this.textIndex == -1) ? (this.tutorialTexts.size() - 1) : this.textIndex;
/*     */         } 
/* 205 */         updateTutorialText();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasTutorialText(TutorialText text) {
/* 213 */     for (int i = 0; i < this.tutorialTexts.size(); i++) {
/* 214 */       if (text.getText().equals(((TutorialText)this.tutorialTexts.get(i)).getText())) {
/* 215 */         return true;
/*     */       }
/*     */     } 
/* 218 */     return false;
/*     */   }
/*     */   
/*     */   private int getTutorialTextIndex(TutorialText text) {
/* 222 */     for (int i = 0; i < this.tutorialTexts.size(); i++) {
/* 223 */       if (text.getText().equals(((TutorialText)this.tutorialTexts.get(i)).getText())) {
/* 224 */         return i;
/*     */       }
/*     */     } 
/* 227 */     return -1;
/*     */   }
/*     */   
/*     */   public void revertLayer() {
/* 231 */     setLayer(this.bufferedLayer);
/*     */   }
/*     */   
/*     */   public HighlightedButton getConfirmButton() {
/* 235 */     return this.confirmButton;
/*     */   }
/*     */   
/*     */   private class TutorialText
/*     */   {
/* 240 */     private String text = "";
/* 241 */     private String imgPath = "";
/*     */     private boolean confirm = false;
/*     */     
/*     */     TutorialText(String text, String imgPath) {
/* 245 */       this.text = text;
/* 246 */       this.imgPath = imgPath;
/*     */     }
/*     */     
/*     */     public String getText() {
/* 250 */       return this.text;
/*     */     }
/*     */     
/*     */     public void setText(String text) {
/* 254 */       this.text = text;
/*     */     }
/*     */     
/*     */     public String getImgPath() {
/* 258 */       return this.imgPath;
/*     */     }
/*     */     
/*     */     public void setImgPath(String imgPath) {
/* 262 */       this.imgPath = imgPath;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\NoahTutorialWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */