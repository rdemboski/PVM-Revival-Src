/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.combat.RpgStatus;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateUpdateMessage
/*     */   implements Message
/*     */ {
/*     */   private static final int MAX_BUFF_COUNT = 255;
/*     */   private int id;
/*     */   private int creatureType;
/*  25 */   private Collection<Stat> stats = new HashSet<Stat>();
/*  26 */   private List<BuffData> buffDatas = new ArrayList<BuffData>(2);
/*     */   private Set<RpgStatus> changedRpgStatus;
/*     */   
/*     */   public StateUpdateMessage(ByteBuffer byteBuffer) {
/*  30 */     this.id = MessageUtils.readInt(byteBuffer);
/*     */     
/*  32 */     this.stats = TCGMessageUtils.readStats(byteBuffer);
/*     */     
/*  34 */     int buffCount = byteBuffer.get() & 0xFF;
/*  35 */     for (int i = 0; i < buffCount; i++) {
/*  36 */       this.buffDatas.add(new BuffData(byteBuffer));
/*     */     }
/*  38 */     this.changedRpgStatus = new HashSet<RpgStatus>();
/*  39 */     readRpgStatus(byteBuffer);
/*  40 */     this.creatureType = MessageUtils.readInt(byteBuffer);
/*     */   }
/*     */   
/*     */   public StateUpdateMessage() {
/*  44 */     this.changedRpgStatus = new HashSet<RpgStatus>();
/*     */   }
/*     */   
/*     */   public StateUpdateMessage(int id, int creatureType) {
/*  48 */     this.id = id;
/*  49 */     this.creatureType = creatureType;
/*  50 */     this.changedRpgStatus = new HashSet<RpgStatus>();
/*     */   }
/*     */   
/*     */   public int getId() {
/*  54 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getCreatureType() {
/*  58 */     return this.creatureType;
/*     */   }
/*     */   
/*     */   public Collection<Stat> getStats() {
/*  62 */     return this.stats;
/*     */   }
/*     */   
/*     */   public List<BuffData> getBuffDatas() {
/*  66 */     return this.buffDatas;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  70 */     return 13;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  74 */     return new StateUpdateMessage(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  79 */     int ret = MessageUtils.getSizeInt() * 2;
/*     */     
/*  81 */     ret += TCGMessageUtils.getSizeStats(this.stats);
/*     */     
/*  83 */     ret++;
/*  84 */     int size = this.buffDatas.size();
/*  85 */     for (int i = 0; i < size; i++) {
/*  86 */       BuffData buffData = this.buffDatas.get(i);
/*  87 */       ret += buffData.getSerializedSize();
/*     */     } 
/*  89 */     ret++;
/*  90 */     ret += this.changedRpgStatus.size();
/*  91 */     return ret;
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  95 */     MessageUtils.writeInt(buffer, this.id);
/*     */     
/*  97 */     TCGMessageUtils.writeStats(buffer, this.stats);
/*     */     
/*  99 */     int size = this.buffDatas.size();
/* 100 */     buffer.put((byte)size);
/* 101 */     for (int i = 0; i < size; i++) {
/* 102 */       BuffData buffData = this.buffDatas.get(i);
/* 103 */       buffData.serialize(buffer);
/*     */     } 
/*     */     
/* 106 */     writeRpgStatus(buffer);
/* 107 */     MessageUtils.writeInt(buffer, this.creatureType);
/*     */     
/* 109 */     return buffer;
/*     */   }
/*     */   
/*     */   private void writeRpgStatus(ByteBuffer buffer) {
/* 113 */     buffer.put((byte)this.changedRpgStatus.size());
/* 114 */     for (RpgStatus changedRpgStat : this.changedRpgStatus) {
/* 115 */       buffer.put((byte)changedRpgStat.ordinal());
/*     */     }
/*     */   }
/*     */   
/*     */   private void readRpgStatus(ByteBuffer buffer) {
/* 120 */     int size = buffer.get();
/* 121 */     for (int i = 0; i < size; i++) {
/* 122 */       this.changedRpgStatus.add(RpgStatus.values()[buffer.get()]);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 127 */     StringBuffer sb = new StringBuffer();
/* 128 */     sb.append("[Id:").append(this.id).append(this.stats).append("]");
/*     */ 
/*     */     
/* 131 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStat(Stat stat) {
/* 136 */     this.stats.add(stat);
/*     */     
/* 138 */     if (this.stats.size() > 32767) {
/* 139 */       throw new IllegalStateException("too many stats: max=32767");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addBuff(BuffType type, String buffId, int duration, int buffTimer) {
/* 144 */     this.buffDatas.add(new BuffData(type, buffId, duration, buffTimer));
/*     */     
/* 146 */     if (this.buffDatas.size() > 255) {
/* 147 */       throw new IllegalStateException("Too many buffs, please use/implement more bytes for buff count");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 152 */     return (this.stats.isEmpty() && this.buffDatas.isEmpty() && this.changedRpgStatus.isEmpty());
/*     */   }
/*     */   
/*     */   public void addRpgStatusChanges(Set<RpgStatus> changedRpgStatus) {
/* 156 */     this.changedRpgStatus = changedRpgStatus;
/*     */   }
/*     */   
/*     */   public Set<RpgStatus> getRpgStatus() {
/* 160 */     return this.changedRpgStatus;
/*     */   }
/*     */   
/*     */   public static class BuffData {
/*     */     private BuffType type;
/*     */     private String id;
/*     */     private int duration;
/*     */     private int timer;
/*     */     
/*     */     public BuffData(BuffType type, String id, int duration, int timer) {
/* 170 */       this.type = type;
/* 171 */       this.duration = duration;
/* 172 */       this.id = (id != null) ? id : "";
/* 173 */       this.timer = timer;
/*     */     }
/*     */     
/*     */     public BuffData(ByteBuffer buffer) {
/* 177 */       this.type = BuffType.valueById(buffer.get());
/* 178 */       this.id = MessageUtils.readStr(buffer);
/* 179 */       this.duration = buffer.getInt();
/* 180 */       this.timer = buffer.getInt();
/*     */     }
/*     */     
/*     */     public void serialize(ByteBuffer buffer) {
/* 184 */       buffer.put(this.type.getId());
/* 185 */       MessageUtils.writeStr(buffer, this.id);
/* 186 */       buffer.putInt(this.duration);
/* 187 */       buffer.putInt(this.timer);
/*     */     }
/*     */     
/*     */     public int getSerializedSize() {
/* 191 */       return 1 + MessageUtils.getSizeStr(this.id) + MessageUtils.getSizeInt() * 2;
/*     */     }
/*     */     
/*     */     public BuffType getType() {
/* 195 */       return this.type;
/*     */     }
/*     */     
/*     */     public String getId() {
/* 199 */       return this.id;
/*     */     }
/*     */     
/*     */     public int getDuration() {
/* 203 */       return this.duration;
/*     */     }
/*     */     
/*     */     public int getTimer() {
/* 207 */       return this.timer;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 211 */       return "BuffData{type=" + this.type + ", id='" + this.id + '\'' + ", duration=" + this.duration + ", timer=" + this.timer + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\StateUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */