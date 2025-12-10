/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ import com.funcom.commons.utils.ClientUtils;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.ui.Localizer;
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ import com.funcom.tcg.net.message.GiftBoxOpenMessage;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class ClientGiftBox implements HudInfoModel, HudInfoAction {
/* 14 */   private static final Logger LOGGER = Logger.getLogger(ClientGiftBox.class.getName());
/*    */   
/*    */   private static final String TIME_TAG = "[time]";
/*    */   
/*    */   private final Integer id;
/*    */   
/*    */   private final String iconPathLocked;
/*    */   
/*    */   private final String iconPathUnlocked;
/*    */   
/*    */   private final Localizer localizer;
/*    */   
/*    */   private long untilOpenMillis;
/*    */   private long updatedAt;
/*    */   private String cachedText;
/*    */   private long cachedTextHash;
/*    */   
/*    */   public ClientGiftBox(int id, String iconPathLocked, String iconPathUnlocked, Localizer localizer) {
/* 32 */     this.id = Integer.valueOf(id);
/* 33 */     this.iconPathLocked = iconPathLocked;
/* 34 */     this.iconPathUnlocked = iconPathUnlocked;
/* 35 */     this.localizer = localizer;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */   public void update(long untilOpenMillis) {
/* 43 */     this.untilOpenMillis = untilOpenMillis;
/* 44 */     this.updatedAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 49 */     return (int)this.untilOpenMillis;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 54 */     if (isActivatable()) {
/* 55 */       return this.iconPathUnlocked;
/*    */     }
/* 57 */     return this.iconPathLocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 62 */     long secondsUntilOpen = ClientUtils.calcPassedSeconds(this.untilOpenMillis, this.updatedAt);
/* 63 */     long textHash = 0L;
/* 64 */     if (secondsUntilOpen > 0L) {
/* 65 */       textHash = -secondsUntilOpen;
/*    */     }
/* 67 */     if (this.cachedTextHash != textHash || this.cachedText == null) {
/* 68 */       if (secondsUntilOpen > 0L) {
/* 69 */         this.cachedText = TcgGame.getLocalizedText("hudinfo.giftbox.untilopen.text", new String[0]).replace("[time]", "\n" + ClientUtils.toTimeString(secondsUntilOpen, this.localizer, ClientGiftBox.class));
/*    */       } else {
/* 71 */         this.cachedText = TcgGame.getLocalizedText("hudinfo.giftbox.open.text", new String[0]);
/*    */       } 
/* 73 */       this.cachedTextHash = textHash;
/*    */     } 
/* 75 */     return this.cachedText;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActivatable() {
/* 80 */     return (this.untilOpenMillis <= 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate() {
/*    */     try {
/* 86 */       NetworkHandler.instance().getIOHandler().send((Message)new GiftBoxOpenMessage(this.id.intValue()));
/* 87 */     } catch (InterruptedException e) {
/* 88 */       LOGGER.log((Priority)Level.ERROR, "Failed to send GiftBoxOpenMessage", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientGiftBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */