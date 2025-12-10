/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.server.common.util.CoordDecoder;
/*     */ import com.funcom.server.common.util.CoordEncoder;
/*     */ import com.funcom.server.common.util.LongDeltaDecoder;
/*     */ import com.funcom.server.common.util.LongDeltaEncoder;
/*     */ import java.awt.Point;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionUpdateListMessage
/*     */   implements Message
/*     */ {
/*     */   private static final float TWO_PI = 6.2831855F;
/*     */   private static final float ERROR_THRESHOLD = 0.02F;
/*     */   public static final int MAX_ALLOWED_ENTRIES = 255;
/*     */   private byte[] posData;
/*     */   private int entryCount;
/*     */   private byte[] idData;
/*     */   private byte[] angleData;
/*     */   private Integer[] creatureTypes;
/*     */   private int posDataSize;
/*     */   private int idDataSize;
/*     */   
/*     */   public PositionUpdateListMessage() {}
/*     */   
/*     */   public PositionUpdateListMessage(int entryCount, byte[] idData, short idDataSize, byte[] posData, short posDataSize, byte[] angleData, Integer[] creatureTypes) {
/*  37 */     this.entryCount = entryCount;
/*  38 */     this.idData = idData;
/*  39 */     this.idDataSize = idDataSize;
/*  40 */     this.posData = posData;
/*  41 */     this.posDataSize = posDataSize;
/*  42 */     this.angleData = angleData;
/*  43 */     this.creatureTypes = creatureTypes;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  47 */     return 10;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  51 */     int _count = buffer.get() & 0xFF;
/*     */     
/*  53 */     int _idDataSize = buffer.getShort() & 0xFFFF;
/*  54 */     byte[] _idData = new byte[_idDataSize];
/*  55 */     buffer.get(_idData);
/*     */     
/*  57 */     int _posDataSize = buffer.getShort() & 0xFFFF;
/*  58 */     byte[] _posData = new byte[_posDataSize];
/*  59 */     buffer.get(_posData);
/*     */     
/*  61 */     byte[] angleData = new byte[_count];
/*  62 */     buffer.get(angleData);
/*     */     
/*  64 */     Integer[] creatureType = MessageUtils.readIntArray(buffer);
/*     */     
/*  66 */     return new PositionUpdateListMessage(_count, _idData, (short)_idDataSize, _posData, (short)_posDataSize, angleData, creatureType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  73 */     return 1 + MessageUtils.getSizeShort() * 2 + this.idDataSize + this.posDataSize + this.entryCount + MessageUtils.getSizeIntArray(this.creatureTypes);
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  77 */     buffer.put((byte)this.entryCount);
/*  78 */     buffer.putShort((short)this.idDataSize);
/*  79 */     buffer.put(this.idData, 0, this.idDataSize);
/*  80 */     buffer.putShort((short)this.posDataSize);
/*  81 */     buffer.put(this.posData, 0, this.posDataSize);
/*  82 */     buffer.put(this.angleData, 0, this.entryCount);
/*  83 */     MessageUtils.writeIntArray(buffer, this.creatureTypes);
/*     */     
/*  85 */     return buffer;
/*     */   }
/*     */   
/*     */   public PositionIterator getPositionIterator() {
/*  89 */     return new PositionIterator();
/*     */   }
/*     */   
/*     */   public class PositionIterator
/*     */   {
/*     */     private int index;
/*  95 */     private CoordDecoder coordDecoder = new CoordDecoder(PositionUpdateListMessage.this.posData, PositionUpdateListMessage.this.posDataSize, 0.02F);
/*  96 */     private LongDeltaDecoder idDecoder = new LongDeltaDecoder(PositionUpdateListMessage.this.idData);
/*     */ 
/*     */     
/*     */     private boolean evalAvailable;
/*     */     
/*     */     private long prevValue;
/*     */ 
/*     */     
/*     */     public boolean hasMoreIDs() {
/* 105 */       return (this.index < PositionUpdateListMessage.this.entryCount);
/*     */     }
/*     */     
/*     */     public int nextID() {
/* 109 */       if (!hasMoreIDs()) {
/* 110 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 113 */       this.evalAvailable = true;
/* 114 */       this.index++;
/*     */       
/* 116 */       this.prevValue = this.idDecoder.decode(this.prevValue);
/* 117 */       return (int)this.prevValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean evalPosition(WorldCoordinate coord) {
/* 125 */       if (!this.evalAvailable) {
/* 126 */         throw new IllegalStateException("Call nextID() to evaluate next position");
/*     */       }
/*     */       
/* 129 */       return this.coordDecoder.decode(coord.getTileCoord(), coord.getTileOffset());
/*     */     }
/*     */ 
/*     */     
/*     */     public float getAngle() {
/* 134 */       return PositionUpdateListMessage.angleByteToFloat(PositionUpdateListMessage.this.angleData[this.index - 1]);
/*     */     }
/*     */     
/*     */     public int getCreatureType() {
/* 138 */       return PositionUpdateListMessage.this.creatureTypes[this.index - 1].intValue();
/*     */     }
/*     */     
/*     */     private PositionIterator() {} }
/*     */   
/*     */   public static class Builder {
/*     */     private int maxEntries;
/*     */     private int count;
/* 146 */     private LongDeltaEncoder idEncoder = new LongDeltaEncoder();
/*     */     
/*     */     private CoordEncoder coordEncoder;
/*     */     private byte[] angleData;
/*     */     private Integer[] creatureType;
/*     */     private long lastId;
/*     */     
/*     */     public Builder() {
/* 154 */       this(255);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder(int maxEntries) {
/* 159 */       if (maxEntries > 255) {
/* 160 */         throw new IllegalArgumentException("Too many entries, max allowed is 255");
/*     */       }
/*     */       
/* 163 */       this.maxEntries = maxEntries;
/* 164 */       this.angleData = new byte[maxEntries / 32 + 1];
/* 165 */       this.creatureType = new Integer[maxEntries / 32 + 1];
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionUpdateListMessage nextMessage() {
/* 170 */       byte[] idData = this.idEncoder.finalizeData();
/* 171 */       int idDataSize = this.idEncoder.getFinalizedDataSize();
/*     */       
/* 173 */       byte[] posData = this.coordEncoder.finalizeData();
/* 174 */       int posDataSize = this.coordEncoder.getFinalizedDataSize();
/* 175 */       Integer[] creatureData = Arrays.<Integer>copyOf(this.creatureType, this.count);
/*     */       
/* 177 */       int entryCount = this.count;
/*     */ 
/*     */       
/* 180 */       this.coordEncoder = null;
/* 181 */       this.count = 0;
/* 182 */       this.idEncoder.reset();
/* 183 */       this.creatureType = new Integer[5];
/*     */ 
/*     */       
/* 186 */       if (posDataSize > 65535) {
/* 187 */         throw new IllegalStateException("delta encoding data is too big");
/*     */       }
/*     */       
/* 190 */       return new PositionUpdateListMessage(entryCount, idData, (short)idDataSize, posData, (short)posDataSize, this.angleData, creatureData);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMessage() {
/* 197 */       return (this.coordEncoder != null);
/*     */     }
/*     */     
/*     */     public boolean isMessageFull() {
/* 201 */       return (this.count == this.maxEntries || (this.coordEncoder != null && this.coordEncoder.getFinalizedDataSize() >= 65526));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean evalEntry(int id, WorldCoordinate real, float realAngle, WorldCoordinate proxy, float proxyAngle, WorldCoordinate origCoord, boolean moved, int creatureType) {
/* 207 */       if (isMessageFull()) {
/* 208 */         throw new IllegalStateException("Message is full");
/*     */       }
/*     */       
/* 211 */       Vector2d proxyOffset = proxy.getTileOffset();
/* 212 */       Point proxyCoord = proxy.getTileCoord();
/* 213 */       Vector2d realOffset = real.getTileOffset();
/* 214 */       Point realCoord = real.getTileCoord();
/*     */       
/* 216 */       if (moved || proxyAngle != realAngle || !proxyCoord.equals(realCoord) || proxyOffset.getX() != realOffset.getX() || proxyOffset.getY() != realOffset.getY()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         if (this.coordEncoder == null) {
/* 222 */           this.coordEncoder = new CoordEncoder(0.02F);
/*     */         }
/*     */         
/* 225 */         this.idEncoder.encode(this.lastId, id);
/* 226 */         this.lastId = id;
/* 227 */         if (this.angleData.length <= this.count) {
/* 228 */           this.angleData = Arrays.copyOf(this.angleData, this.angleData.length * 2);
/* 229 */           this.creatureType = Arrays.<Integer>copyOf(this.creatureType, this.creatureType.length * 2);
/*     */         } 
/* 231 */         this.angleData[this.count] = PositionUpdateListMessage.angleFloatToByte(realAngle);
/* 232 */         this.creatureType[this.count] = Integer.valueOf(creatureType);
/* 233 */         this.coordEncoder.encode(origCoord.getTileCoord(), origCoord.getTileOffset(), realCoord, realOffset);
/*     */         
/* 235 */         this.count++;
/* 236 */         return true;
/*     */       } 
/* 238 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte angleFloatToByte(float angle) {
/* 245 */     if (angle < 0.0F || angle >= 6.2831855F) {
/* 246 */       angle %= 6.2831855F;
/* 247 */       if (angle < 0.0F) {
/* 248 */         angle += 6.2831855F;
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return (byte)(int)(256.0F * angle / 6.2831855F);
/*     */   }
/*     */   
/*     */   private static float angleByteToFloat(byte angleByte) {
/* 256 */     return (angleByte & 0xFF) * 6.2831855F / 256.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PositionUpdateListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */