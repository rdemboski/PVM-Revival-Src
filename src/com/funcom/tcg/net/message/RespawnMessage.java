/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RespawnMessage
/*     */   implements Message
/*     */ {
/*     */   public static final int MIN_TIME_TO_RESPAWN_MILIS = 3000;
/*     */   private RespawnType respawnType;
/*     */   private WorldCoordinate position;
/*     */   private float angle;
/*     */   private Collection<Stat> stats;
/*     */   
/*     */   public RespawnMessage() {}
/*     */   
/*     */   public RespawnMessage(RespawnType respawnType) {
/*  25 */     this.respawnType = respawnType;
/*     */   }
/*     */   
/*     */   public RespawnMessage(ByteBuffer byteBuffer) {
/*  29 */     byte typeId = byteBuffer.get();
/*  30 */     this.respawnType = RespawnType.valueById(typeId);
/*     */     
/*  32 */     if (this.respawnType == RespawnType.__REPLY_FROM_SERVER) {
/*  33 */       this.position = MessageUtils.readWorldCoordinatePartial(byteBuffer);
/*  34 */       this.angle = byteBuffer.getFloat();
/*  35 */       this.stats = TCGMessageUtils.readStats(byteBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RespawnMessage(WorldCoordinate position, float angle, Collection<Stat> stats) {
/*  40 */     this.respawnType = RespawnType.__REPLY_FROM_SERVER;
/*  41 */     this.position = position;
/*  42 */     this.angle = angle;
/*  43 */     this.stats = stats;
/*     */   }
/*     */   
/*     */   public RespawnType getRespawnType() {
/*  47 */     return this.respawnType;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getPosition() {
/*  51 */     return this.position;
/*     */   }
/*     */   
/*     */   public float getAngle() {
/*  55 */     return this.angle;
/*     */   }
/*     */   
/*     */   public Collection<Stat> getStats() {
/*  59 */     return this.stats;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  63 */     return 206;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer byteBuffer) {
/*  67 */     return new RespawnMessage(byteBuffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  71 */     int ret = 1;
/*     */     
/*  73 */     if (this.respawnType == RespawnType.__REPLY_FROM_SERVER) {
/*  74 */       ret += MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeFloat() + TCGMessageUtils.getSizeStats(this.stats);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  79 */     return ret;
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer byteBuffer) {
/*  83 */     byteBuffer.put(this.respawnType.id);
/*     */     
/*  85 */     if (this.respawnType == RespawnType.__REPLY_FROM_SERVER) {
/*  86 */       MessageUtils.writeWorldCoordinatePartial(byteBuffer, this.position);
/*  87 */       byteBuffer.putFloat(this.angle);
/*  88 */       TCGMessageUtils.writeStats(byteBuffer, this.stats);
/*     */     } 
/*     */     
/*  91 */     return byteBuffer;
/*     */   }
/*     */   
/*     */   public enum RespawnType
/*     */   {
/*  96 */     __REPLY_FROM_SERVER((byte)-1),
/*  97 */     SAFE_ZONE((byte)0),
/*  98 */     IN_PLACE_WITH_LIFE((byte)1);
/*     */     
/* 100 */     private static final RespawnType[] TYPES = values();
/*     */     
/*     */     private final byte id;
/*     */     
/*     */     RespawnType(byte id) {
/* 105 */       this.id = id;
/*     */     } static {
/*     */     
/*     */     } private static RespawnType valueById(byte id) {
/* 109 */       for (int i = TYPES.length - 1; i >= 0; i--) {
/* 110 */         RespawnType type = TYPES[i];
/* 111 */         if (type.id == id) {
/* 112 */           return type;
/*     */         }
/*     */       } 
/*     */       
/* 116 */       throw new IllegalArgumentException("unknown " + RespawnType.class.getSimpleName() + ": id=" + id);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RespawnMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */