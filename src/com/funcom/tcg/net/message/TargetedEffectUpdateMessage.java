/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetedEffectUpdateMessage
/*     */   implements Message
/*     */ {
/*  20 */   private List<NewTargetedEffectData> newDataList = new ArrayList<NewTargetedEffectData>();
/*  21 */   private List<Integer> removedList = new ArrayList<Integer>();
/*     */ 
/*     */   
/*     */   public TargetedEffectUpdateMessage(ByteBuffer buffer) {
/*  25 */     this();
/*     */ 
/*     */     
/*  28 */     int count = buffer.getShort() & 0xFFFF;
/*  29 */     for (int i = 0; i < count; i++) {
/*  30 */       this.newDataList.add(new NewTargetedEffectData(buffer));
/*     */     }
/*     */     
/*  33 */     int removedCount = buffer.getShort();
/*  34 */     for (int j = 0; j < removedCount; j++) {
/*  35 */       this.removedList.add(Integer.valueOf(buffer.getInt()));
/*     */     }
/*     */   }
/*     */   
/*     */   public TargetedEffectUpdateMessage() {}
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  42 */     buffer.putShort((short)this.newDataList.size());
/*  43 */     int size = this.newDataList.size();
/*  44 */     for (int i = 0; i < size; i++) {
/*  45 */       NewTargetedEffectData targetedEffectData = this.newDataList.get(i);
/*  46 */       targetedEffectData.write(buffer);
/*     */     } 
/*     */     
/*  49 */     int removedSize = this.removedList.size();
/*  50 */     buffer.putShort((short)removedSize);
/*     */     
/*  52 */     for (int j = 0; j < removedSize; j++) {
/*  53 */       buffer.putInt(((Integer)this.removedList.get(j)).intValue());
/*     */     }
/*     */     
/*  56 */     return buffer;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  60 */     return 228;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  64 */     return new TargetedEffectUpdateMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  68 */     return 2 + getSizeNewDataList() + 2 + getSizeRemovedList();
/*     */   }
/*     */   
/*     */   private int getSizeNewDataList() {
/*  72 */     int counter = 0;
/*     */     
/*  74 */     int size = this.newDataList.size();
/*  75 */     for (int i = 0; i < size; i++) {
/*  76 */       counter += ((NewTargetedEffectData)this.newDataList.get(i)).getSize();
/*     */     }
/*     */     
/*  79 */     return counter;
/*     */   }
/*     */   
/*     */   private int getSizeRemovedList() {
/*  83 */     int counter = 0;
/*     */     
/*  85 */     int size = this.removedList.size();
/*  86 */     for (int i = 0; i < size; i++) {
/*  87 */       counter += MessageUtils.getSizeInt();
/*     */     }
/*  89 */     return counter;
/*     */   }
/*     */   
/*     */   public void addNew(int id, double aliveTime, float angle, WorldCoordinate pos, String targetedEffectId) {
/*  93 */     this.newDataList.add(new NewTargetedEffectData(id, aliveTime, angle, pos, targetedEffectId));
/*     */   }
/*     */   
/*     */   public List<NewTargetedEffectData> getNewDataList() {
/*  97 */     return this.newDataList;
/*     */   }
/*     */   
/*     */   public void addRemoved(Integer id) {
/* 101 */     this.removedList.add(id);
/*     */   }
/*     */   
/*     */   public List<Integer> getRemovedList() {
/* 105 */     return this.removedList;
/*     */   }
/*     */   
/*     */   public static class NewTargetedEffectData
/*     */   {
/*     */     private final int id;
/*     */     private final double aliveTime;
/*     */     private final float angle;
/*     */     private final WorldCoordinate pos;
/*     */     private String targetedEffectId;
/*     */     
/*     */     public NewTargetedEffectData(int id, double aliveTime, float angle, WorldCoordinate pos, String targetedEffectId) {
/* 117 */       this.id = id;
/* 118 */       this.aliveTime = aliveTime;
/* 119 */       this.angle = angle;
/* 120 */       this.pos = pos;
/* 121 */       this.targetedEffectId = targetedEffectId;
/*     */     }
/*     */     
/*     */     public NewTargetedEffectData(ByteBuffer buffer) {
/* 125 */       this.id = buffer.getInt();
/* 126 */       this.aliveTime = buffer.getDouble();
/* 127 */       this.angle = buffer.getFloat();
/* 128 */       this.pos = MessageUtils.readWorldCoordinatePartial(buffer);
/* 129 */       this.targetedEffectId = MessageUtils.readStr(buffer);
/*     */     }
/*     */     
/*     */     public void write(ByteBuffer buffer) {
/* 133 */       buffer.putInt(this.id);
/* 134 */       buffer.putDouble(this.aliveTime);
/* 135 */       buffer.putFloat(this.angle);
/* 136 */       MessageUtils.writeWorldCoordinatePartial(buffer, this.pos);
/* 137 */       MessageUtils.writeStr(buffer, this.targetedEffectId);
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 141 */       return MessageUtils.getSizeInt() + MessageUtils.getSizeDouble() + MessageUtils.getSizeFloat() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeStr(this.targetedEffectId);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getId() {
/* 149 */       return this.id;
/*     */     }
/*     */     
/*     */     public double getAliveTime() {
/* 153 */       return this.aliveTime;
/*     */     }
/*     */     
/*     */     public float getAngle() {
/* 157 */       return this.angle;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getPos() {
/* 161 */       return this.pos;
/*     */     }
/*     */     
/*     */     public String getTargetedEffectId() {
/* 165 */       return this.targetedEffectId;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 169 */       return "ProjectileData{id=" + this.id + ", aliveTime=" + this.aliveTime + ", pos=" + this.pos + ", targetedEffectId=" + this.targetedEffectId + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TargetedEffectUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */