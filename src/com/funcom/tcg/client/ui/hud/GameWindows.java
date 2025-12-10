/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*    */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*    */ import com.funcom.tcg.client.ui.chat.BasicChatWindow;
/*    */ import com.funcom.tcg.client.ui.chat.ChatWindow;
/*    */ import com.funcom.tcg.client.ui.duel.DuelHealthBarWindow;
/*    */ import com.funcom.tcg.client.ui.friend.FriendsWindow;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryWindow;
/*    */ import com.funcom.tcg.client.ui.mainmenu.MainMenuWindow;
/*    */ import com.funcom.tcg.client.ui.maps.MapWindow2;
/*    */ import com.funcom.tcg.client.ui.pause.PauseWindow;
/*    */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*    */ import com.funcom.tcg.client.ui.quest2.QuestWindow2;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*    */ import com.jmex.bui.BWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum GameWindows
/*    */ {
/* 22 */   INVENTORY((Class)InventoryWindow.class, false),
/* 23 */   CHARACTER((Class)CharacterEquipmentWindow.class, true),
/* 24 */   WARDROBE((Class)CharacterEquipmentWindow.class, true),
/* 25 */   MAP((Class)MapWindow2.class, true),
/* 26 */   QUEST_WINDOW((Class)QuestWindow2.class, true),
/* 27 */   MAIN_MENU((Class)MainMenuWindow.class, true),
/* 28 */   PETS_AND_SKILLS((Class)PetsWindow.class, true),
/* 29 */   VENDOR((Class)VendorFullWindow.class, true),
/* 30 */   CHAT((Class)BasicChatWindow.class, false),
/* 31 */   CANNED_CHAT((Class)ChatWindow.class, false),
/* 32 */   PAUSE((Class)PauseWindow.class, false),
/* 33 */   FRIENDS((Class)FriendsWindow.class, false),
/* 34 */   ACHIEVEMENTS((Class)AchievementsWindow.class, true),
/* 35 */   DUEL_HEALTH_BARS((Class)DuelHealthBarWindow.class, false);
/*    */   
/*    */   private Class<? extends BWindow> windowClass;
/*    */   private boolean fullScreen;
/*    */   
/*    */   GameWindows(Class<? extends BWindow> windowClass, boolean fullScreen) {
/* 41 */     this.windowClass = windowClass;
/* 42 */     this.fullScreen = fullScreen;
/*    */   }
/*    */   
/*    */   public Class<? extends BWindow> getWindowClass() {
/* 46 */     return this.windowClass;
/*    */   }
/*    */   
/*    */   public boolean isFullScreen() {
/* 50 */     return this.fullScreen;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\GameWindows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */