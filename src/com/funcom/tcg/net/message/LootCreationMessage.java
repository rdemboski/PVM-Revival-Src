/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LootCreationMessage
/*     */   implements Message
/*     */ {
/*     */   private int lootId;
/*     */   private int containerId;
/*     */   private int hardness;
/*  21 */   private String lootOwnerName = "Owen";
/*     */   
/*     */   private WorldCoordinate worldCoordinate;
/*  24 */   private static final int MSG_SIZE = MessageUtils.getSizeInt() * 3 + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeDouble();
/*     */   
/*     */   private double radius;
/*     */   
/*     */   public LootCreationMessage(ByteBuffer buffer) {
/*  29 */     this.lootId = MessageUtils.readInt(buffer);
/*  30 */     this.containerId = MessageUtils.readInt(buffer);
/*  31 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/*  32 */     this.hardness = MessageUtils.readInt(buffer);
/*  33 */     this.radius = MessageUtils.readDouble(buffer);
/*     */   }
/*     */   
/*     */   public LootCreationMessage(int lootId, int containerId, WorldCoordinate worldCoordinate, int hardness, double radius) {
/*  37 */     this.lootId = lootId;
/*  38 */     this.containerId = containerId;
/*  39 */     this.worldCoordinate = worldCoordinate;
/*  40 */     this.hardness = hardness;
/*  41 */     this.radius = radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootCreationMessage() {}
/*     */   
/*     */   public int getLootId() {
/*  48 */     return this.lootId;
/*     */   }
/*     */   
/*     */   public int getContainerId() {
/*  52 */     return this.containerId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getWorldCoordinate() {
/*  56 */     return this.worldCoordinate;
/*     */   }
/*     */   
/*     */   public int getHardness() {
/*  60 */     return this.hardness;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  64 */     return 21;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  68 */     return new LootCreationMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  72 */     return MSG_SIZE;
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  76 */     MessageUtils.writeInt(buffer, this.lootId);
/*  77 */     MessageUtils.writeInt(buffer, this.containerId);
/*  78 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/*  79 */     MessageUtils.writeInt(buffer, this.hardness);
/*  80 */     MessageUtils.writeDouble(buffer, this.radius);
/*  81 */     return buffer;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append("[").append("lootId=").append(this.lootId).append(",containerId=").append(this.containerId).append(",worldCoordinate=").append(this.worldCoordinate).append(",hardness=").append(this.hardness).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getLootOwnerName() {
/*  97 */     return this.lootOwnerName;
/*     */   }
/*     */   
/*     */   public double getRadius() {
/* 101 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\LootCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */