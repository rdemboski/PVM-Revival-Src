/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.server.common.util.ReflectionUtils;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import com.funcom.tcg.net.StartingPet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
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
/*     */ public class TCGMessageUtils
/*     */ {
/*     */   public static List<Class<? extends Message>> getMessageClasses() {
/*     */     try {
/*  32 */       InputStream messageClassesProps = TCGMessageUtils.class.getResourceAsStream("/com/funcom/tcg/net/message/messages.properties");
/*  33 */       List<Class<? extends Message>> messageClasses = ReflectionUtils.getClasses(Message.class, messageClassesProps);
/*     */       try {
/*  35 */         messageClassesProps.close();
/*  36 */       } catch (IOException e) {}
/*     */ 
/*     */ 
/*     */       
/*  40 */       return messageClasses;
/*  41 */     } catch (ClassNotFoundException e) {
/*  42 */       throw new RuntimeException("Error getting message parser list", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<QuestRewardData> readQuestRewardDataList(ByteBuffer buffer) {
/*  47 */     ArrayList<QuestRewardData> rewardDataList = new ArrayList<QuestRewardData>();
/*  48 */     int size = MessageUtils.readInt(buffer);
/*  49 */     for (int i = 0; i < size; i++) {
/*  50 */       rewardDataList.add(new QuestRewardData(MessageUtils.readStr(buffer), MessageUtils.readInt(buffer), MessageUtils.readInt(buffer), MessageUtils.readInt(buffer), MessageUtils.readBoolean(buffer).booleanValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     return rewardDataList;
/*     */   }
/*     */   
/*     */   public static void writeQuestRewardDataList(ByteBuffer buffer, List<QuestRewardData> rewardDataList) {
/*  62 */     MessageUtils.writeInt(buffer, rewardDataList.size());
/*  63 */     for (QuestRewardData questRewardData : rewardDataList) {
/*  64 */       MessageUtils.writeStr(buffer, questRewardData.getRewardId());
/*  65 */       MessageUtils.writeInt(buffer, questRewardData.getTier());
/*  66 */       MessageUtils.writeInt(buffer, questRewardData.getAmount());
/*  67 */       MessageUtils.writeInt(buffer, questRewardData.getType());
/*  68 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(questRewardData.isChoosable()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getQuestRewardDataListSize(List<QuestRewardData> rewardDataList) {
/*  73 */     int size = MessageUtils.getSizeInt();
/*  74 */     for (QuestRewardData questRewardData : rewardDataList) {
/*  75 */       size += MessageUtils.getSizeStr(questRewardData.getRewardId());
/*  76 */       size += MessageUtils.getSizeInt();
/*  77 */       size += MessageUtils.getSizeInt();
/*  78 */       size += MessageUtils.getSizeInt();
/*  79 */       size += MessageUtils.getSizeBoolean();
/*     */     } 
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static PlayerStartConfig readPlayerStartConfig(ByteBuffer buffer) {
/*  85 */     byte shouldRead = buffer.get();
/*  86 */     if (shouldRead == 0) {
/*  87 */       return null;
/*     */     }
/*     */     
/*  90 */     PlayerDescription playerDescription = readPlayerDescription(buffer);
/*  91 */     SkillId torsoId = readSkillId(buffer);
/*  92 */     SkillId legsId = readSkillId(buffer);
/*  93 */     StartingPet startingPet = StartingPet.valueOfById(buffer.get());
/*  94 */     boolean trial = MessageUtils.readBoolean(buffer).booleanValue();
/*     */     
/*  96 */     return new PlayerStartConfig(playerDescription, startingPet, torsoId, legsId, trial);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPlayerStartConfigSize(PlayerStartConfig playerStartConfig) {
/* 101 */     if (playerStartConfig == null) {
/* 102 */       return 1;
/*     */     }
/* 104 */     return 1 + getPlayerDescriptionSize(playerStartConfig.getPlayerDescription()) + getSkillIdSize(playerStartConfig.getTorsoId()) + getSkillIdSize(playerStartConfig.getLegsId()) + 1 + MessageUtils.getSizeBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writePlayerStartConfig(ByteBuffer buffer, PlayerStartConfig playerStartConfig) {
/* 113 */     if (playerStartConfig == null) {
/* 114 */       buffer.put((byte)0);
/*     */     } else {
/* 116 */       buffer.put((byte)1);
/* 117 */       writePlayerDescription(buffer, playerStartConfig.getPlayerDescription());
/* 118 */       writeSkillId(buffer, playerStartConfig.getTorsoId());
/* 119 */       writeSkillId(buffer, playerStartConfig.getLegsId());
/* 120 */       buffer.put(playerStartConfig.getStartingPet().getId());
/* 121 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(playerStartConfig.isTrial()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getSkillIdSize(SkillId skillId) {
/* 126 */     return MessageUtils.getSizeStr(skillId.getItemId()) + MessageUtils.getSizeInt();
/*     */   }
/*     */   
/*     */   private static void writeSkillId(ByteBuffer buffer, SkillId skillId) {
/* 130 */     MessageUtils.writeStr(buffer, skillId.getItemId());
/* 131 */     buffer.putInt(skillId.getTier());
/*     */   }
/*     */   
/*     */   private static SkillId readSkillId(ByteBuffer buffer) {
/* 135 */     return new SkillId(MessageUtils.readStr(buffer), buffer.getInt());
/*     */   }
/*     */   
/*     */   private static int getPlayerDescriptionSize(PlayerDescription playerDescription) {
/* 139 */     return MessageUtils.getSizeStr(playerDescription.getEyeColorId()) + MessageUtils.getSizeStr(playerDescription.getFaceId()) + MessageUtils.getSizeStr(playerDescription.getHairColorId()) + MessageUtils.getSizeStr(playerDescription.getHairId()) + MessageUtils.getSizeStr(playerDescription.getSkinColorId()) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writePlayerDescription(ByteBuffer buffer, PlayerDescription playerDescription) {
/* 148 */     MessageUtils.writeStr(buffer, playerDescription.getEyeColorId());
/* 149 */     MessageUtils.writeStr(buffer, playerDescription.getFaceId());
/* 150 */     MessageUtils.writeStr(buffer, playerDescription.getHairColorId());
/* 151 */     MessageUtils.writeStr(buffer, playerDescription.getHairId());
/* 152 */     MessageUtils.writeStr(buffer, playerDescription.getSkinColorId());
/* 153 */     buffer.put(playerDescription.getGender().getId());
/*     */   }
/*     */   
/*     */   private static PlayerDescription readPlayerDescription(ByteBuffer buffer) {
/* 157 */     PlayerDescription ret = new PlayerDescription();
/*     */     
/* 159 */     ret.setEyeColorId(MessageUtils.readStr(buffer));
/* 160 */     ret.setFaceId(MessageUtils.readStr(buffer));
/* 161 */     ret.setHairColorId(MessageUtils.readStr(buffer));
/* 162 */     ret.setHairId(MessageUtils.readStr(buffer));
/* 163 */     ret.setSkinColorId(MessageUtils.readStr(buffer));
/* 164 */     ret.setGender(PlayerDescription.Gender.valueOfById(buffer.get()));
/*     */     
/* 166 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeStats(Collection<Stat> stats) {
/* 170 */     int ret = MessageUtils.getSizeShort();
/*     */     
/* 172 */     ret += (MessageUtils.getSizeShort() + MessageUtils.getSizeInt() * 2) * stats.size();
/*     */     
/* 174 */     return ret;
/*     */   }
/*     */   
/*     */   public static void writeStats(ByteBuffer buffer, Collection<Stat> stats) {
/* 178 */     int size = stats.size();
/* 179 */     if (size > 32767) {
/* 180 */       throw new IllegalArgumentException("too many stats, max is 32767");
/*     */     }
/* 182 */     buffer.putShort((short)size);
/* 183 */     for (Stat stat : stats) {
/* 184 */       MessageUtils.writeShort(buffer, stat.getId().shortValue());
/* 185 */       buffer.putInt(stat.getBase());
/* 186 */       buffer.putInt(stat.getModifier());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Collection<Stat> readStats(ByteBuffer buffer) {
/* 191 */     int size = buffer.getShort();
/*     */     
/* 193 */     Collection<Stat> ret = new HashSet<Stat>();
/*     */     
/* 195 */     for (int i = 0; i < size; i++) {
/* 196 */       short id = MessageUtils.readShort(buffer);
/* 197 */       int base = buffer.getInt();
/* 198 */       int modifier = buffer.getInt();
/*     */       
/* 200 */       Stat stat = new Stat(Short.valueOf(id), base);
/* 201 */       stat.setModifier(modifier);
/*     */       
/* 203 */       ret.add(stat);
/*     */     } 
/*     */     
/* 206 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TCGMessageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */