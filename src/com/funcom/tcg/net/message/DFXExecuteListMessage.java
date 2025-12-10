/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.server.common.util.LongDeltaDecoder;
/*     */ import com.funcom.server.common.util.LongDeltaEncoder;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DFXExecuteListMessage
/*     */   implements Message
/*     */ {
/*     */   public static final int MAX_ALLOWED_ENTRIES = 255;
/*     */   private int entryCount;
/*     */   private byte[] idData;
/*     */   private int idDataSize;
/*     */   private Integer[] creatureTypes;
/*     */   private String[] dfxScripts;
/*     */   private Float[] localTimes;
/*     */   
/*     */   public DFXExecuteListMessage() {}
/*     */   
/*     */   public DFXExecuteListMessage(int entryCount, byte[] idData, short idDataSize, String[] dfxScript, Float[] localTime, Integer[] creatureTypes) {
/*  30 */     this.entryCount = entryCount;
/*  31 */     this.idData = idData;
/*  32 */     this.idDataSize = idDataSize;
/*  33 */     this.dfxScripts = dfxScript;
/*  34 */     this.localTimes = localTime;
/*  35 */     this.creatureTypes = creatureTypes;
/*     */   }
/*     */   
/*     */   public DFXExecuteListMessage(ByteBuffer buffer) {
/*  39 */     this.entryCount = buffer.get() & 0xFF;
/*     */     
/*  41 */     this.idDataSize = buffer.getShort() & 0xFFFF;
/*  42 */     this.idData = new byte[this.idDataSize];
/*  43 */     buffer.get(this.idData);
/*     */     
/*  45 */     this.dfxScripts = MessageUtils.readStrArray(buffer);
/*  46 */     this.localTimes = MessageUtils.readFloatArray(buffer);
/*  47 */     this.creatureTypes = MessageUtils.readIntArray(buffer);
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  51 */     return 229;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  55 */     return new DFXExecuteListMessage(buffer);
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  59 */     buffer.put((byte)this.entryCount);
/*  60 */     buffer.putShort((short)this.idDataSize);
/*  61 */     buffer.put(this.idData, 0, this.idDataSize);
/*     */     
/*  63 */     MessageUtils.writeStrArray(buffer, this.dfxScripts);
/*  64 */     MessageUtils.writeFloatArray(buffer, this.localTimes);
/*  65 */     MessageUtils.writeIntArray(buffer, this.creatureTypes);
/*     */     
/*  67 */     return buffer;
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  71 */     return 1 + MessageUtils.getSizeShort() + this.idDataSize + MessageUtils.getSizeStrArray(this.dfxScripts) + MessageUtils.getSizeFloatArray(this.localTimes) + MessageUtils.getSizeIntArray(this.creatureTypes);
/*     */   }
/*     */   
/*     */   public DfxIterator getDfxIterator() {
/*  75 */     return new DfxIterator();
/*     */   }
/*     */   
/*     */   public class DfxIterator {
/*     */     private int index;
/*  80 */     private LongDeltaDecoder idDecoder = new LongDeltaDecoder(DFXExecuteListMessage.this.idData);
/*     */ 
/*     */     
/*     */     private long prevValue;
/*     */ 
/*     */     
/*     */     public boolean hasMoreIDs() {
/*  87 */       return (this.index < DFXExecuteListMessage.this.entryCount);
/*     */     }
/*     */     
/*     */     public int nextID() {
/*  91 */       if (!hasMoreIDs()) {
/*  92 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/*  95 */       this.index++;
/*     */       
/*  97 */       this.prevValue = this.idDecoder.decode(this.prevValue);
/*  98 */       return (int)this.prevValue;
/*     */     }
/*     */     
/*     */     public int getCreatureType() {
/* 102 */       return DFXExecuteListMessage.this.creatureTypes[this.index - 1].intValue();
/*     */     }
/*     */     
/*     */     public String getDfxId() {
/* 106 */       return DFXExecuteListMessage.this.dfxScripts[this.index - 1];
/*     */     }
/*     */     
/*     */     public float getLocalTime() {
/* 110 */       return DFXExecuteListMessage.this.localTimes[this.index - 1].floatValue();
/*     */     }
/*     */     
/*     */     private DfxIterator() {}
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private boolean hasMessages = false;
/* 118 */     private LongDeltaEncoder idEncoder = new LongDeltaEncoder(); private int maxEntries; private int count;
/* 119 */     private String[] dfxs = new String[5];
/* 120 */     private Float[] times = new Float[5];
/* 121 */     private Integer[] creatureTypes = new Integer[5];
/*     */     private long lastId;
/*     */     
/*     */     public Builder() {
/* 125 */       this(255);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder(int maxEntries) {
/* 130 */       if (maxEntries > 255) {
/* 131 */         throw new IllegalArgumentException("Too many entries, max allowed is 255");
/*     */       }
/*     */       
/* 134 */       this.maxEntries = maxEntries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DFXExecuteListMessage nextMessage() {
/* 140 */       byte[] idData = this.idEncoder.finalizeData();
/* 141 */       int idDataSize = this.idEncoder.getFinalizedDataSize();
/* 142 */       String[] dfxData = Arrays.<String>copyOf(this.dfxs, this.count);
/* 143 */       Float[] timeData = Arrays.<Float>copyOf(this.times, this.count);
/* 144 */       Integer[] creatureData = Arrays.<Integer>copyOf(this.creatureTypes, this.count);
/* 145 */       int entryCount = this.count;
/*     */ 
/*     */       
/* 148 */       this.hasMessages = false;
/* 149 */       this.idEncoder.reset();
/* 150 */       this.count = 0;
/* 151 */       this.dfxs = new String[5];
/* 152 */       this.times = new Float[5];
/* 153 */       this.creatureTypes = new Integer[5];
/*     */       
/* 155 */       return new DFXExecuteListMessage(entryCount, idData, (short)idDataSize, dfxData, timeData, creatureData);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMessage() {
/* 161 */       return this.hasMessages;
/*     */     }
/*     */     
/*     */     public boolean isMessageFull() {
/* 165 */       return (this.count == this.maxEntries);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean evalEntry(int id, String dfx, double time, int creatureType) {
/* 170 */       if (isMessageFull()) {
/* 171 */         throw new IllegalStateException("Message is full");
/*     */       }
/*     */       
/* 174 */       this.hasMessages = true;
/*     */       
/* 176 */       this.idEncoder.encode(this.lastId, id);
/* 177 */       this.lastId = id;
/* 178 */       if (this.dfxs.length <= this.count) {
/* 179 */         this.dfxs = Arrays.<String>copyOf(this.dfxs, this.dfxs.length * 2);
/* 180 */         this.times = Arrays.<Float>copyOf(this.times, this.times.length * 2);
/* 181 */         this.creatureTypes = Arrays.<Integer>copyOf(this.creatureTypes, this.creatureTypes.length * 2);
/*     */       } 
/* 183 */       this.dfxs[this.count] = dfx;
/* 184 */       this.creatureTypes[this.count] = Integer.valueOf(creatureType);
/* 185 */       this.times[this.count] = Float.valueOf((float)time);
/* 186 */       this.count++;
/* 187 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DFXExecuteListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */