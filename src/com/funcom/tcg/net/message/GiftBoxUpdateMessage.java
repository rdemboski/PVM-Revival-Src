/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GiftBoxUpdateMessage
/*     */   implements Message
/*     */ {
/*     */   private int giftBoxId;
/*     */   private long untilOpenMillis;
/*     */   private UpdateType updateType;
/*     */   private String giftBoxDescriptionId;
/*     */   private List<QuestRewardData> rewardDataList;
/*     */   
/*     */   public GiftBoxUpdateMessage() {}
/*     */   
/*     */   public GiftBoxUpdateMessage(int giftBoxId, long untilOpenMillis, UpdateType updateType, String giftBoxDescriptionId, List<QuestRewardData> rewardDataList) {
/*  25 */     this.giftBoxId = giftBoxId;
/*  26 */     this.untilOpenMillis = untilOpenMillis;
/*  27 */     this.updateType = updateType;
/*  28 */     this.giftBoxDescriptionId = giftBoxDescriptionId;
/*  29 */     this.rewardDataList = rewardDataList;
/*     */   }
/*     */   
/*     */   public GiftBoxUpdateMessage(ByteBuffer buffer) {
/*  33 */     this.giftBoxId = buffer.getInt();
/*  34 */     this.untilOpenMillis = buffer.getLong();
/*  35 */     this.updateType = UpdateType.getById(buffer.get());
/*  36 */     this.giftBoxDescriptionId = MessageUtils.readStr(buffer);
/*  37 */     this.rewardDataList = TCGMessageUtils.readQuestRewardDataList(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  42 */     return new GiftBoxUpdateMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getGiftBoxId() {
/*  46 */     return this.giftBoxId;
/*     */   }
/*     */   
/*     */   public String getGiftBoxDescriptionId() {
/*  50 */     return this.giftBoxDescriptionId;
/*     */   }
/*     */   
/*     */   public long getUntilOpenMillis() {
/*  54 */     return this.untilOpenMillis;
/*     */   }
/*     */   
/*     */   public UpdateType getRemoveReason() {
/*  58 */     return this.updateType;
/*     */   }
/*     */   
/*     */   public List<QuestRewardData> getRewardDataList() {
/*  62 */     return this.rewardDataList;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  67 */     return 235;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  72 */     return 13 + MessageUtils.getSizeStr(this.giftBoxDescriptionId) + TCGMessageUtils.getQuestRewardDataListSize(this.rewardDataList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  79 */     buffer.putInt(this.giftBoxId);
/*  80 */     buffer.putLong(this.untilOpenMillis);
/*  81 */     buffer.put(this.updateType.id);
/*  82 */     MessageUtils.writeStr(buffer, this.giftBoxDescriptionId);
/*  83 */     TCGMessageUtils.writeQuestRewardDataList(buffer, this.rewardDataList);
/*  84 */     return buffer;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  88 */     return (this.updateType == UpdateType.UPDATE);
/*     */   }
/*     */   
/*     */   public enum UpdateType {
/*  92 */     UPDATE((byte)0),
/*  93 */     EXPIRED((byte)1),
/*  94 */     OPENED((byte)2);
/*     */     
/*  96 */     private static final UpdateType[] ALL = values();
/*     */     private final byte id;
/*     */     
/*     */     UpdateType(byte id) {
/* 100 */       this.id = id;
/*     */     } static {
/*     */     
/*     */     } private static UpdateType getById(byte id) {
/* 104 */       for (UpdateType reason : ALL) {
/* 105 */         if (reason.id == id) {
/* 106 */           return reason;
/*     */         }
/*     */       } 
/*     */       
/* 110 */       throw new IllegalArgumentException("unknown " + UpdateType.class.getName() + ".id: id=" + id);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\GiftBoxUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */