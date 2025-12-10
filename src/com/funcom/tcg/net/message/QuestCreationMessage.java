/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.PairedWCandBoolean;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class QuestCreationMessage
/*     */   implements Message
/*     */ {
/*     */   private String questId;
/*     */   private boolean doConfirm;
/*     */   private List<String> questObjectivesIds;
/*     */   private List<Integer> currentAmounts;
/*     */   private List<QuestRewardData> questRewardDatas;
/*     */   private String questGiverId;
/*     */   private String location;
/*     */   private int messageCreationType;
/*     */   private HashMap<String, List<PairedWCandBoolean>> gotoPositions;
/*     */   
/*     */   public QuestCreationMessage() {}
/*     */   
/*     */   public QuestCreationMessage(String questId, boolean doConfirm, List<String> questObjectivesIds, List<Integer> currentAmounts, List<QuestRewardData> questRewardDatas, String questGiverId, String location, int messageCreationType, HashMap<String, List<PairedWCandBoolean>> gotoPositions) {
/*  38 */     this.questId = questId;
/*  39 */     this.doConfirm = doConfirm;
/*  40 */     this.questObjectivesIds = questObjectivesIds;
/*  41 */     this.currentAmounts = currentAmounts;
/*  42 */     this.questRewardDatas = questRewardDatas;
/*  43 */     this.questGiverId = questGiverId;
/*  44 */     this.location = location;
/*  45 */     this.messageCreationType = messageCreationType;
/*  46 */     this.gotoPositions = gotoPositions;
/*     */   }
/*     */   
/*     */   public QuestCreationMessage(ByteBuffer buffer) {
/*  50 */     this.questId = MessageUtils.readStr(buffer);
/*  51 */     this.doConfirm = MessageUtils.readBoolean(buffer).booleanValue();
/*  52 */     readQuestObjectiveIds(buffer);
/*  53 */     readQuestObjectiveAmounts(buffer);
/*  54 */     this.questRewardDatas = TCGMessageUtils.readQuestRewardDataList(buffer);
/*  55 */     this.questGiverId = MessageUtils.readStr(buffer);
/*  56 */     this.location = MessageUtils.readStr(buffer);
/*  57 */     this.messageCreationType = MessageUtils.readInt(buffer);
/*  58 */     readGotoPositions(buffer);
/*     */   }
/*     */   
/*     */   public String getQuestId() {
/*  62 */     return this.questId;
/*     */   }
/*     */   
/*     */   public List<String> getQuestObjectivesIds() {
/*  66 */     return this.questObjectivesIds;
/*     */   }
/*     */   
/*     */   public List<Integer> getCurrentAmounts() {
/*  70 */     return this.currentAmounts;
/*     */   }
/*     */   
/*     */   public boolean doConfirm() {
/*  74 */     return this.doConfirm;
/*     */   }
/*     */   
/*     */   public List<QuestRewardData> getQuestRewardDataContainers() {
/*  78 */     return this.questRewardDatas;
/*     */   }
/*     */   
/*     */   public int getMessageCreationType() {
/*  82 */     return this.messageCreationType;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  87 */     return 39;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  92 */     return new QuestCreationMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSizeObjectiveIds() {
/*  96 */     return MessageUtils.getSizeListStr(this.questObjectivesIds);
/*     */   }
/*     */   
/*     */   public int getSizeCurrentAmounts() {
/* 100 */     return MessageUtils.getSizeInt() + this.currentAmounts.size() * MessageUtils.getSizeInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 105 */     return MessageUtils.getSizeStr(this.questId) + MessageUtils.getSizeBoolean() + getSizeObjectiveIds() + getSizeCurrentAmounts() + TCGMessageUtils.getQuestRewardDataListSize(this.questRewardDatas) + MessageUtils.getSizeStr(this.questGiverId) + MessageUtils.getSizeStr(this.location) + MessageUtils.getSizeInt() + getSizeGotoPositions();
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
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 120 */     MessageUtils.writeStr(buffer, this.questId);
/* 121 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.doConfirm));
/* 122 */     writeObjectiveIds(buffer);
/* 123 */     writeCurrentAmounts(buffer);
/* 124 */     TCGMessageUtils.writeQuestRewardDataList(buffer, this.questRewardDatas);
/* 125 */     MessageUtils.writeStr(buffer, this.questGiverId);
/* 126 */     MessageUtils.writeStr(buffer, this.location);
/* 127 */     MessageUtils.writeInt(buffer, this.messageCreationType);
/* 128 */     writeGotoPositions(buffer, this.gotoPositions);
/* 129 */     return buffer;
/*     */   }
/*     */   
/*     */   private void writeObjectiveIds(ByteBuffer buffer) {
/* 133 */     MessageUtils.writeListStr(buffer, this.questObjectivesIds);
/*     */   }
/*     */   
/*     */   private void writeCurrentAmounts(ByteBuffer buffer) {
/* 137 */     int size = this.currentAmounts.size();
/* 138 */     MessageUtils.writeInt(buffer, size);
/* 139 */     for (int i = 0; i < size; i++) {
/* 140 */       MessageUtils.writeInt(buffer, ((Integer)this.currentAmounts.get(i)).intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private void readQuestObjectiveIds(ByteBuffer buffer) {
/* 145 */     this.questObjectivesIds = MessageUtils.readListStr(buffer);
/*     */   }
/*     */   
/*     */   private void readQuestObjectiveAmounts(ByteBuffer buffer) {
/* 149 */     int size = MessageUtils.readInt(buffer);
/*     */     
/* 151 */     if (this.currentAmounts == null) {
/* 152 */       this.currentAmounts = new ArrayList<Integer>();
/*     */     }
/* 154 */     for (int i = 0; i < size; i++) {
/* 155 */       int amount = MessageUtils.readInt(buffer);
/* 156 */       this.currentAmounts.add(Integer.valueOf(amount));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return "QuestCreationMessage{questId='" + this.questId + '\'' + ", doConfirm=" + this.doConfirm + ", questObjectivesIds=" + this.questObjectivesIds + ", currentAmounts=" + this.currentAmounts + ", questRewardDatas=" + this.questRewardDatas + ", questGiverId='" + this.questGiverId + '\'' + ", location='" + this.location + '\'' + ", messageCreationType=" + this.messageCreationType + ", gotoPositions=" + this.gotoPositions + '}';
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
/*     */   public String getQuestGiverId() {
/* 176 */     return this.questGiverId;
/*     */   }
/*     */   
/*     */   public String getLocation() {
/* 180 */     return this.location;
/*     */   }
/*     */   
/*     */   public enum MessageCreationType {
/* 184 */     NEW(0), COMPLETED(1), UPDATE(2);
/*     */     
/*     */     private int id;
/*     */     
/*     */     MessageCreationType(int id) {
/* 189 */       this.id = id;
/*     */     }
/*     */     
/*     */     public int getId() {
/* 193 */       return this.id;
/*     */     }
/*     */   }
/*     */   
/*     */   public List<PairedWCandBoolean> getGotoPositions(String objectiveID) {
/* 198 */     return this.gotoPositions.get(objectiveID);
/*     */   }
/*     */   
/*     */   private void writeGotoPositions(ByteBuffer buffer, HashMap<String, List<PairedWCandBoolean>> gotoPositions) {
/* 202 */     MessageUtils.writeInt(buffer, gotoPositions.size());
/* 203 */     for (String key : gotoPositions.keySet()) {
/* 204 */       MessageUtils.writeStr(buffer, key);
/* 205 */       List<PairedWCandBoolean> list = gotoPositions.get(key);
/* 206 */       MessageUtils.writeInt(buffer, list.size());
/* 207 */       for (PairedWCandBoolean pairedWCandBoolean : list) {
/* 208 */         MessageUtils.writeWorldCoordinatePartial(buffer, pairedWCandBoolean.getWc());
/* 209 */         MessageUtils.writeStr(buffer, pairedWCandBoolean.getWc().getMapId());
/* 210 */         MessageUtils.writeBoolean(buffer, Boolean.valueOf(pairedWCandBoolean.isShown()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readGotoPositions(ByteBuffer buffer) {
/* 216 */     this.gotoPositions = new HashMap<String, List<PairedWCandBoolean>>();
/* 217 */     int size = MessageUtils.readInt(buffer);
/* 218 */     for (int t = 0; t < size; t++) {
/* 219 */       String key = MessageUtils.readStr(buffer);
/* 220 */       int listsize = MessageUtils.readInt(buffer);
/* 221 */       ArrayList<PairedWCandBoolean> list = new ArrayList<PairedWCandBoolean>(listsize);
/* 222 */       for (int wcs = 0; wcs < listsize; wcs++) {
/* 223 */         WorldCoordinate value = MessageUtils.readWorldCoordinatePartial(buffer);
/*     */         
/* 225 */         value.setMapId(MessageUtils.readStr(buffer));
/* 226 */         boolean shown = MessageUtils.readBoolean(buffer).booleanValue();
/* 227 */         PairedWCandBoolean pair = new PairedWCandBoolean(value, shown);
/* 228 */         list.add(pair);
/*     */       } 
/* 230 */       this.gotoPositions.put(key, list);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getSizeGotoPositions() {
/* 235 */     int size = MessageUtils.getSizeInt();
/* 236 */     for (String key : this.gotoPositions.keySet()) {
/* 237 */       size += MessageUtils.getSizeStr(key);
/* 238 */       size += MessageUtils.getSizeInt();
/* 239 */       for (PairedWCandBoolean pairedWCandBoolean : this.gotoPositions.get(key)) {
/* 240 */         size += MessageUtils.getSizeWorldCoordinate();
/* 241 */         size += MessageUtils.getSizeStr(pairedWCandBoolean.getWc().getMapId());
/* 242 */         size += MessageUtils.getSizeBoolean();
/*     */       } 
/*     */     } 
/* 245 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */