/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.DebugWindow;
/*     */ import com.funcom.tcg.client.ui.TCGDialog;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.account.RegisterWindow;
/*     */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*     */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*     */ import com.funcom.tcg.client.ui.chat.BasicChatWindow;
/*     */ import com.funcom.tcg.client.ui.chat.ChatWindow;
/*     */ import com.funcom.tcg.client.ui.duel.DuelHealthBarWindow;
/*     */ import com.funcom.tcg.client.ui.friend.FriendsWindow;
/*     */ import com.funcom.tcg.client.ui.giftbox.GiftBoxWindow;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryWindow;
/*     */ import com.funcom.tcg.client.ui.mainmenu.MainMenuWindow;
/*     */ import com.funcom.tcg.client.ui.mainmenu.OptionsWindow;
/*     */ import com.funcom.tcg.client.ui.maps.MapWindow2;
import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*     */ import com.funcom.tcg.client.ui.quest2.QuestWindow2;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*     */ import com.funcom.tcg.client.ui.waypoint.WaypointWindow;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PanelManager implements Updated {
/*  35 */   private static final Rectangle CLOSE_BOUNDS_RIGHT = new Rectangle(10, 10, 80, 80);
/*  36 */   private static final Rectangle CLOSE_BOUNDS_LEFT = new Rectangle(316, 10, 80, 80);
/*  37 */   private static final Rectangle CLOSE_BOUNDS_NONE = null;
/*     */   
/*     */   public static final int STANDARD_PANEL_WIDTH = 440;
/*     */   
/*     */   public static final int STANDARD_PANEL_HEIGHT = 677;
/*     */   
/*     */   public static final int OFFSET_FROM_BOTTOM = 91;
/*     */   
/*     */   private static final float SLIDE_SPEED = 3.0F;
/*     */   private static PanelManager instance;
/*  47 */   private List<BWindowInfo> movingWindows = new LinkedList<BWindowInfo>();
/*     */   private Map<Class<? extends BWindow>, Location> panelMap;
/*  49 */   private List<Class<? extends BWindow>> keyInputWindowClasses = new ArrayList<Class<? extends BWindow>>();
/*     */   
/*     */   private PanelManager() {
/*  52 */     initKeyInputWindowClasses();
/*  53 */     initLocations();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initKeyInputWindowClasses() {
/*  58 */     this.keyInputWindowClasses.add(RegisterWindow.class);
/*  59 */     this.keyInputWindowClasses.add(FriendsWindow.class);
/*  60 */     this.keyInputWindowClasses.add(BasicChatWindow.class);
/*     */   }
/*     */   
/*     */   private void initLocations() {
/*  64 */     this.panelMap = new HashMap<Class<? extends BWindow>, Location>();
/*  65 */     this.panelMap.put(CharacterEquipmentWindow.class, Location.CENTER_NO_DISPOSE);
/*  66 */     this.panelMap.put(VendorFullWindow.class, Location.CENTER_NO_DISPOSE);
/*  67 */     this.panelMap.put(DuelHealthBarWindow.class, Location.CENTER_NO_DISPOSE);
/*     */     
/*  69 */     this.panelMap.put(DebugWindow.class, Location.LEFT);
/*     */     
/*  71 */     this.panelMap.put(InventoryWindow.class, Location.RIGHT);
/*     */     
/*  73 */     this.panelMap.put(MapWindow2.class, Location.CENTER_NO_DISPOSE);
/*     */     
/*  75 */     this.panelMap.put(QuestWindow2.class, Location.CENTER_NO_DISPOSE);
/*  76 */     this.panelMap.put(QuestPickUpWindow2.class, Location.CENTER);
/*  77 */     this.panelMap.put(QuestFinishWindow2.class, Location.CENTER);
/*     */     
/*  79 */     this.panelMap.put(WaypointWindow.class, Location.LEFT_NO_SLIDE);
/*     */     
/*  81 */     this.panelMap.put(MainMenuWindow.class, Location.CENTER);
/*  82 */     this.panelMap.put(OptionsWindow.class, Location.FULL);
/*  83 */     this.panelMap.put(PetsWindow.class, Location.CENTER_NO_DISPOSE);
/*  84 */     this.panelMap.put(AchievementsWindow.class, Location.CENTER_NO_DISPOSE);
/*     */ 
/*     */     
/*  87 */     this.panelMap.put(GiftBoxWindow.class, Location.CENTER);
/*     */     
/*  89 */     this.panelMap.put(ChatWindow.class, Location.LEFT_NO_SLIDE);
/*  90 */     this.panelMap.put(BasicChatWindow.class, Location.LEFT_NO_SLIDE);
/*  91 */     this.panelMap.put(FriendsWindow.class, Location.LEFT_NO_SLIDE);
/*     */     
/*  93 */     this.panelMap.put(TCGDialog.class, Location.CENTER);
/*     */     
/*  95 */     this.panelMap.put(RegisterWindow.class, Location.CENTER_NO_DISPOSE);
/*     */   }
/*     */   
/*     */   public static synchronized PanelManager getInstance() {
/*  99 */     if (instance == null) {
/* 100 */       instance = new PanelManager();
/*     */     }
/* 102 */     return instance;
/*     */   }
/*     */   
/*     */   public void closeAll() {
/* 106 */     for (Map.Entry<Class<? extends BWindow>, Location> entry : this.panelMap.entrySet()) {
/* 107 */       BWindow window = TcgUI.getWindowFromClass(entry.getKey());
/* 108 */       if (window == null || (
/* 109 */         window instanceof DuelHealthBarWindow && TcgGame.isDueling())) {
/*     */         continue;
/*     */       }
/* 112 */       closeWindow(window);
/*     */     } 
/*     */     
/* 115 */     if (MainGameState.getPauseModel().isPaused()) {
/* 116 */       MainGameState.getPauseModel().reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 126 */     if (this.movingWindows.isEmpty()) {
/*     */       return;
/*     */     }
/* 129 */     Iterator<BWindowInfo> bWindowIterator = this.movingWindows.iterator();
/* 130 */     while (bWindowIterator.hasNext()) {
/* 131 */       float visibleAmount; BWindowInfo windowInfo = bWindowIterator.next();
/* 132 */       BWindow movingWindow = windowInfo.getWindow();
/* 133 */       windowInfo.progress += time * 3.0F;
/*     */       
/* 135 */       if (windowInfo.progress > 1.0F) {
/* 136 */         bWindowIterator.remove();
/* 137 */         windowInfo.progress = 1.0F;
/*     */         
/* 139 */         if (!windowInfo.open) {
/* 140 */           movingWindow.setVisible(false);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 145 */       if (windowInfo.open) {
/* 146 */         visibleAmount = (float)(1.0D - Math.sin(windowInfo.progress * Math.PI / 2.0D));
/*     */       } else {
/* 148 */         visibleAmount = (float)Math.sin(windowInfo.progress * Math.PI / 2.0D);
/*     */       } 
/*     */       
/* 151 */       Location location = this.panelMap.get(movingWindow.getClass());
/* 152 */       int x = location.getVisibleX(movingWindow, visibleAmount);
/*     */       
/* 154 */       Insets insets = movingWindow.getInsets();
/*     */ 
/*     */       
/* 157 */       movingWindow.setBounds(x, 91 + insets.top, 440, 677);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWindow(BWindow window) {
/* 163 */     window.setVisible(true);
/* 164 */     Location location = this.panelMap.get(window.getClass());
/*     */     
/* 166 */     BWindowInfo info = setWindowLocation(window, location, 1.0F);
/*     */     
/* 168 */     if (!(window instanceof BasicChatWindow)) {
/* 169 */       for (Map.Entry<Class<? extends BWindow>, Location> entry : this.panelMap.entrySet()) {
/* 170 */         if (entry.getValue() == location || (location.isCoversLeft() && ((Location)entry.getValue()).isCoversLeft()) || (location.isCoversRight() && ((Location)entry.getValue()).isCoversRight())) {
/*     */           
/* 172 */           BWindow windowInPanel = TcgUI.getWindowFromClass(entry.getKey());
/* 173 */           if (windowInPanel != null && windowInPanel.isVisible() && window != windowInPanel) {
/*     */             
/* 175 */             if (windowInPanel instanceof BasicChatWindow) {
/*     */               continue;
/*     */             }
/* 178 */             closeWindow(windowInPanel);
/*     */             
/* 180 */             boolean largeWindowPrev = (windowInPanel instanceof com.funcom.tcg.client.ui.AbstractFauxWindow || windowInPanel instanceof VendorFullWindow || windowInPanel instanceof AchievementsWindow);
/* 181 */             boolean largeWindowNext = (window instanceof com.funcom.tcg.client.ui.AbstractFauxWindow || window instanceof VendorFullWindow || window instanceof AchievementsWindow);
/* 182 */             if (largeWindowPrev && !largeWindowNext && 
/* 183 */               MainGameState.getPauseModel().isPaused()) {
/* 184 */               MainGameState.getPauseModel().reset();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 192 */     if (location.isMoves()) {
/* 193 */       TcgUI.getUISoundPlayer().play("SlideOpen");
/* 194 */       if (this.movingWindows.contains(info)) {
/* 195 */         this.movingWindows.remove(info);
/*     */       }
/* 197 */       this.movingWindows.add(info);
/*     */     } else {
/* 199 */       TcgUI.getUISoundPlayer().play("WindowOpen");
/*     */     } 
/* 201 */     if (!window.isAdded()) {
/* 202 */       BuiSystem.addWindow(window);
/*     */     }
/* 204 */     if (window instanceof BasicChatWindow) {
/* 205 */       ((BasicChatWindow)window).getChatField().requestFocus();
/*     */     }
/*     */   }
/*     */   
/*     */   public BWindowInfo setWindowLocation(BWindow window, Location location, float visibleAmount) {
/* 210 */     int windowWidth = 440;
/* 211 */     int windowHeight = 677;
/*     */     
/* 213 */     int startX = location.getVisibleX(window, visibleAmount);
/* 214 */     BWindowInfo info = new BWindowInfo(window, true);
/* 215 */     Insets insets = window.getInsets();
/* 216 */     int y = 91 + insets.top;
/*     */ 
/*     */     
/* 219 */     if (location == Location.CENTER) {
/* 220 */       windowWidth = window.getWidth();
/* 221 */       windowHeight = window.getHeight();
/* 222 */       startX = (DisplaySystem.getDisplaySystem().getWidth() - windowWidth) / 2;
/* 223 */       y = (DisplaySystem.getDisplaySystem().getHeight() - windowHeight) / 2 + 30 + window.getY();
/* 224 */       if (window instanceof QuestPickUpWindow2 || window instanceof QuestFinishWindow2) {
/* 225 */         y += 27;
/*     */       }
/* 227 */     } else if (location == Location.FULL) {
/* 228 */       windowWidth = DisplaySystem.getDisplaySystem().getWidth();
/* 229 */       windowHeight = DisplaySystem.getDisplaySystem().getHeight();
/* 230 */       startX = 0;
/* 231 */       y = 0;
/* 232 */     } else if (location == Location.LEFT_NO_SLIDE) {
/* 233 */       windowWidth = window.getWidth();
/* 234 */       windowHeight = window.getHeight();
/* 235 */       startX = window.getAbsoluteX();
/* 236 */       y = window.getAbsoluteY();
/* 237 */     } else if (location == Location.CENTER_NO_DISPOSE) {
/* 238 */       windowWidth = window.getWidth();
/* 239 */       windowHeight = window.getHeight();
/* 240 */       startX = (DisplaySystem.getDisplaySystem().getWidth() - windowWidth) / 2;
/* 241 */       y = (DisplaySystem.getDisplaySystem().getHeight() - windowHeight) / 2;
/* 242 */       if (window instanceof com.funcom.tcg.client.ui.BPeelWindow) {
/* 243 */         y += 29;
/*     */       }
/*     */     } 
/* 246 */     window.setBounds(startX, y, windowWidth, windowHeight);
/* 247 */     return info;
/*     */   }
/*     */   
/*     */   public void closeWindow(BWindow window) {
/* 251 */     Location panel = this.panelMap.get(window.getClass());
/*     */     
/* 253 */     if (panel.isDispose()) {
/* 254 */       window.setVisible(false);
/* 255 */       window.dismiss();
/* 256 */       TcgUI.getUISoundPlayer().play("WindowClose");
/*     */       return;
/*     */     } 
/* 259 */     if (panel.isMoves()) {
/* 260 */       TcgUI.getUISoundPlayer().play("SlideClose");
/* 261 */       BWindowInfo info = new BWindowInfo(window, false);
/* 262 */       if (this.movingWindows.contains(info)) {
/* 263 */         this.movingWindows.remove(info);
/*     */       }
/* 265 */       this.movingWindows.add(info);
/*     */     } else {
/* 267 */       TcgUI.getUISoundPlayer().play("WindowClose");
/* 268 */       window.setVisible(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getLocation(Class<? extends BWindow> windowClass) {
/*     */     Location location;
/* 275 */     while ((location = this.panelMap.get(windowClass)) == null) {
/* 276 */       windowClass = (Class)windowClass.getSuperclass();
/*     */     }
/* 278 */     return location;
/*     */   }
/*     */   
/*     */   public boolean isKeyInputWindowOpen() {
/* 282 */     for (Class<? extends BWindow> keyInputWindowClass : this.keyInputWindowClasses) {
/* 283 */       if (TcgUI.isWindowOpen(keyInputWindowClass)) {
/* 284 */         BWindow windowFromClass = TcgUI.getWindowFromClass(keyInputWindowClass);
/* 285 */         if (windowFromClass instanceof FriendsWindow) {
/* 286 */           FriendsWindow window = (FriendsWindow)windowFromClass;
/* 287 */           if (!window.getSearchFriendField().hasFocus()) {
/* 288 */             return false;
/*     */           }
/* 290 */         } else if (windowFromClass instanceof BasicChatWindow) {
/* 291 */           BasicChatWindow window = (BasicChatWindow)windowFromClass;
/* 292 */           if (!window.getChatField().hasFocus() && !window.getTellField().hasFocus()) {
/* 293 */             return false;
/*     */           }
/*     */         } 
/* 296 */         return true;
/*     */       } 
/*     */     } 
/* 299 */     return false;
/*     */   }
/*     */   
/*     */   private static class BWindowInfo {
/*     */     private BWindow window;
/* 304 */     private float progress = 0.0F;
/*     */     public boolean open = true;
/*     */     
/*     */     public BWindowInfo(BWindow window, boolean open) {
/* 308 */       this.window = window;
/* 309 */       this.open = open;
/*     */     }
/*     */     
/*     */     public BWindow getWindow() {
/* 313 */       return this.window;
/*     */     }
/*     */     
/*     */     public float getProgress() {
/* 317 */       return this.progress;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 322 */       if (this == o) {
/* 323 */         return true;
/*     */       }
/* 325 */       if (o == null || getClass() != o.getClass()) {
/* 326 */         return false;
/*     */       }
/*     */       
/* 329 */       BWindowInfo that = (BWindowInfo)o;
/*     */       
/* 331 */       if ((this.window != null) ? !this.window.equals(that.window) : (that.window != null)) {
/* 332 */         return false;
/*     */       }
/*     */       
/* 335 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Location {
/* 340 */     LEFT(-1, PanelManager.CLOSE_BOUNDS_LEFT, false, true, false, true)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 343 */         return insets.left;
/*     */       }
/*     */     },
/* 346 */     RIGHT(1, PanelManager.CLOSE_BOUNDS_RIGHT, false, false, true, true)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 349 */         return insets.right;
/*     */       }
/*     */     },
/* 352 */     CENTER(1, PanelManager.CLOSE_BOUNDS_NONE, true, true, true, false)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 355 */         return insets.right;
/*     */       }
/*     */     },
/* 358 */     FULL(1, PanelManager.CLOSE_BOUNDS_NONE, true, true, true, false)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 361 */         return 0;
/*     */       }
/*     */     },
/* 364 */     CENTER_NO_DISPOSE(1, PanelManager.CLOSE_BOUNDS_NONE, false, true, true, false)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 367 */         return insets.right;
/*     */       }
/*     */     },
/* 370 */     LEFT_NO_SLIDE(-1, PanelManager.CLOSE_BOUNDS_NONE, true, true, false, false)
/*     */     {
/*     */       protected int getHorizontalPadding(Insets insets) {
/* 373 */         return 0;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final int hideXDirection;
/*     */     private final Rectangle closeButtonBounds;
/*     */     private boolean dispose;
/*     */     private boolean coversLeft;
/*     */     private boolean coversRight;
/*     */     private boolean moves;
/*     */     
/*     */     Location(int hideXDirection, Rectangle closeButtonBounds, boolean dispose, boolean coversLeft, boolean coversRight, boolean moves) {
/* 386 */       this.hideXDirection = hideXDirection;
/* 387 */       this.closeButtonBounds = closeButtonBounds;
/* 388 */       this.dispose = dispose;
/* 389 */       this.coversLeft = coversLeft;
/* 390 */       this.coversRight = coversRight;
/* 391 */       this.moves = moves;
/*     */     }
/*     */     
/*     */     public Rectangle getCloseButtonBounds() {
/* 395 */       return this.closeButtonBounds;
/*     */     }
/*     */     
/*     */     public boolean isDispose() {
/* 399 */       return this.dispose;
/*     */     }
/*     */     
/*     */     public int getVisibleX(BWindow movingWindow, float visibleAmount) {
/* 403 */       int x = 0;
/* 404 */       if (equals(RIGHT)) {
/* 405 */         x = DisplaySystem.getDisplaySystem().getWidth() - 440;
/*     */       }
/* 407 */       Insets insets = movingWindow.getInsets();
/* 408 */       int interpolateLength = movingWindow.getWidth();
/* 409 */       return (int)((x + getHorizontalPadding(insets) * this.hideXDirection) + (interpolateLength * this.hideXDirection) * visibleAmount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCoversLeft() {
/* 416 */       return this.coversLeft;
/*     */     }
/*     */     
/*     */     public boolean isCoversRight() {
/* 420 */       return this.coversRight;
/*     */     }
/*     */     
/*     */     public boolean isMoves() {
/* 424 */       return this.moves;
/*     */     }
/*     */     
/*     */     protected abstract int getHorizontalPadding(Insets param1Insets);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\PanelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */