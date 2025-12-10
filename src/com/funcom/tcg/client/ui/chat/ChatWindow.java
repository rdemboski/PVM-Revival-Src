/*     */ package com.funcom.tcg.client.ui.chat;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BCustomToggleButton;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Point;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class ChatWindow
/*     */   extends BWindow {
/*  21 */   private static final Point WINDOW_LOCATION = new Point(55, 90);
/*     */   private static final String STYLE_CLASS = "chatwindow-window";
/*  23 */   private int buttonHeight = 40; private int buttonWidth = 220;
/*     */   
/*     */   private ChatNetworkController chatUIController;
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   private ChatContainer mainContainer;
/*     */   private ChatButton selectedButton;
/*     */   private Dimension dynamicWindowSize;
/*     */   
/*     */   public ChatWindow(ChatNetworkController chatUIController, ResourceManager resourceManager) {
/*  33 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  34 */     this.chatUIController = chatUIController;
/*  35 */     this.resourceManager = resourceManager;
/*     */     
/*  37 */     this._style = BuiUtils.createMergedClassStyleSheets(ChatWindow.class, new BananaResourceProvider(resourceManager));
/*  38 */     this.dynamicWindowSize = new Dimension(this.buttonWidth, chatUIController.getRootElement().getChildren().size() * this.buttonHeight);
/*     */     
/*  40 */     setStyleClass("chatwindow-window");
/*  41 */     setLocation(WINDOW_LOCATION.x, WINDOW_LOCATION.y);
/*  42 */     setSize(this.dynamicWindowSize.width, this.dynamicWindowSize.height);
/*     */     
/*  44 */     layoutMainChatComponents();
/*  45 */     setLayer(3);
/*     */   }
/*     */   
/*     */   public void layoutMainChatComponents() {
/*  49 */     this.mainContainer = new ChatContainer();
/*  50 */     this.mainContainer.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*     */     
/*  52 */     LinkedList<Element> mainMenuElements = getSortedChildrenList(this.chatUIController.getRootElement());
/*     */     
/*  54 */     for (int i = 0; i < mainMenuElements.size(); i++) {
/*  55 */       Element mainMenuElement = mainMenuElements.get(i);
/*  56 */       Rectangle constraints = new Rectangle(0, i * this.buttonHeight, this.buttonWidth, this.buttonHeight);
/*  57 */       if (mainMenuElement.getName().equalsIgnoreCase("cm")) {
/*  58 */         ChatButton button = new ChatButton(mainMenuElement, mainMenuElement, this.chatUIController, this, this.buttonWidth, this.buttonHeight);
/*  59 */         this.mainContainer.add((BComponent)button, constraints);
/*     */       }
/*  61 */       else if (mainMenuElement.getName().equalsIgnoreCase("friend")) {
/*     */       
/*     */       } 
/*     */     } 
/*  65 */     add((BComponent)this.mainContainer, new Rectangle(0, 0, this.dynamicWindowSize.width, this.dynamicWindowSize.height));
/*     */   }
/*     */ 
/*     */   
/*     */   public void activateChildContainer(BCustomToggleButton bCustomToggleButton, Element chatElement, Element mainMenuElement, int yPos) {
/*  70 */     ChatContainer parent = (ChatContainer)bCustomToggleButton.getParent();
/*     */     
/*  72 */     ChatContainer childContainer = (ChatContainer)parent.getChildContainer();
/*  73 */     if (childContainer != null && childContainer != this.mainContainer) {
/*  74 */       removeChildContainers(childContainer);
/*  75 */       parent.setChildContainer((BContainer)null);
/*     */     } 
/*     */     
/*  78 */     if (chatElement != null && chatElement.getChildren().size() > 0) {
/*  79 */       makeNewChildContainer(chatElement, mainMenuElement, yPos, parent);
/*     */     } else {
/*     */       
/*  82 */       parent.setChildContainer((BContainer)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private LinkedList<Element> getSortedChildrenList(Element parent) {
/*  87 */     LinkedList<Element> childs = new LinkedList<Element>();
/*  88 */     List<Element> children = parent.getChildren();
/*  89 */     for (Element child : children) {
/*  90 */       childs.addFirst(child);
/*     */     }
/*  92 */     return childs;
/*     */   }
/*     */   
/*     */   private void makeNewChildContainer(Element chatElement, Element mainMenuElement, int yPos, ChatContainer parent) {
/*  96 */     ChatContainer chatContainer = new ChatContainer();
/*  97 */     chatContainer.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*     */     
/*  99 */     updateScreenSize(chatElement);
/*     */     
/* 101 */     List<Element> children = getSortedChildrenList(chatElement);
/* 102 */     for (int i = 0; i < children.size(); i++) {
/* 103 */       Element childElement = children.get(i);
/* 104 */       if (childElement.getName().equalsIgnoreCase("cm")) {
/* 105 */         ChatButton button = new ChatButton(childElement, mainMenuElement, this.chatUIController, this, this.buttonWidth, this.buttonHeight);
/* 106 */         chatContainer.add((BComponent)button, new Rectangle(0, i * this.buttonHeight, this.buttonWidth, this.buttonHeight));
/*     */       }
/* 108 */       else if (childElement.getName().equalsIgnoreCase("friend")) {
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     Rectangle constraints = getConstraints(yPos, children);
/* 114 */     add((BComponent)chatContainer, constraints);
/* 115 */     parent.setChildContainer(chatContainer);
/*     */   }
/*     */   
/*     */   private void updateScreenSize(Element chatElement) {
/* 119 */     this.dynamicWindowSize.width += this.buttonWidth;
/* 120 */     if (chatElement.getChildren().size() * this.buttonHeight > this.dynamicWindowSize.height) {
/* 121 */       this.dynamicWindowSize.height = chatElement.getChildren().size() * this.buttonHeight;
/*     */     }
/* 123 */     setSize(this.dynamicWindowSize.width, this.dynamicWindowSize.height);
/*     */   }
/*     */   
/*     */   private Rectangle getConstraints(int yPos, List<Element> children) {
/* 127 */     int newYPosition = yPos - WINDOW_LOCATION.y;
/*     */     
/* 129 */     if (newYPosition < 0) {
/* 130 */       newYPosition = 0;
/*     */     }
/* 132 */     else if (newYPosition + children.size() * this.buttonHeight > this.dynamicWindowSize.height) {
/* 133 */       newYPosition = this.dynamicWindowSize.height - children.size() * this.buttonHeight;
/*     */     } 
/*     */     
/* 136 */     return new Rectangle(this.dynamicWindowSize.width - this.buttonWidth, newYPosition, this.buttonWidth, children.size() * this.buttonHeight);
/*     */   }
/*     */   
/*     */   private void removeChildContainers(ChatContainer childContainer) {
/* 140 */     for (int i = 0; i < childContainer.getComponentCount(); i++) {
/* 141 */       if (childContainer.getComponent(i) instanceof BCustomToggleButton) {
/* 142 */         BCustomToggleButton bToggleButton = (BCustomToggleButton)childContainer.getComponent(i);
/* 143 */         ChatContainer chatContainer = (ChatContainer)bToggleButton.getParent();
/* 144 */         if (chatContainer.getChildContainer() != null) {
/* 145 */           removeChildContainers((ChatContainer)chatContainer.getChildContainer());
/*     */         }
/* 147 */         remove((BComponent)childContainer);
/* 148 */         this.dynamicWindowSize.width -= this.buttonWidth;
/* 149 */         setSize(this.dynamicWindowSize.width, this.dynamicWindowSize.height);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deselectOtherButtonsInGroup(BCustomToggleButton bCustomToggleButton) {
/* 156 */     BContainer parent = bCustomToggleButton.getParent();
/* 157 */     for (int i = 0; i < parent.getComponentCount(); i++) {
/* 158 */       BComponent component = parent.getComponent(i);
/* 159 */       if (component instanceof BCustomToggleButton && !component.equals(bCustomToggleButton)) {
/* 160 */         ((BCustomToggleButton)component).setSelected(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelected(ChatButton chatButton) {
/* 166 */     this.selectedButton = chatButton;
/* 167 */     if (chatButton != null)
/* 168 */       chatButton.setSelected(true); 
/*     */   }
/*     */   
/*     */   public void clicked() {
/* 172 */     if (this.selectedButton != null)
/* 173 */       this.selectedButton.sendMessage(); 
/*     */   }
/*     */   
/*     */   public class ChatContainer extends BContainer {
/*     */     private BContainer childContainer;
/*     */     
/*     */     private void setChildContainer(BContainer childContainer) {
/* 180 */       this.childContainer = childContainer;
/*     */     }
/*     */     
/*     */     public BContainer getChildContainer() {
/* 184 */       return this.childContainer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */