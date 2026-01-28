/*     */ package com.funcom.tcg.net.message;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoUseItemMessage
/*     */   implements Message
/*     */ {
/*     */   private static final byte MSGTYPE_REQUEST = 0;
/*     */   private static final byte MSGTYPE_RESPONSE = 1;
/*     */   private boolean isRequest;
/*     */   private Type type;
/*     */   private String itemDescriptionId;
/*     */   private int tier;
/*     */   
/*     */   public AutoUseItemMessage() {}
/*     */   
/*     */   public AutoUseItemMessage(Type type) {
/*  31 */     this.type = type;
/*     */     
/*  33 */     this.isRequest = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoUseItemMessage(Type type, String itemDescriptionId, int tier) {
/*  43 */     this.type = type;
/*  44 */     this.itemDescriptionId = itemDescriptionId;
/*  45 */     this.tier = tier;
/*     */     
/*  47 */     this.isRequest = false;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  51 */     return 220;
/*     */   }
/*     */   
/*     */   public String getItemDescriptionId() {
/*  55 */     return this.itemDescriptionId;
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  59 */     return this.type;
/*     */   } public Message toMessage(ByteBuffer buffer) {
/*     */     byte id;
/*     */     String itemDescId;
/*  63 */     byte msgType = buffer.get();
/*     */     
/*  65 */     switch (msgType) {
/*     */       case 0:
/*  67 */         id = buffer.get();
/*  68 */         return new AutoUseItemMessage(Type.fromId(id));
/*     */ 
/*     */       
/*     */       case 1:
/*  72 */         id = buffer.get();
/*  73 */         itemDescId = MessageUtils.readStr(buffer);
/*  74 */         return new AutoUseItemMessage(Type.fromId(id), itemDescId, MessageUtils.readInt(buffer));
/*     */     } 
/*     */ 
/*     */     
/*  78 */     throw new IllegalArgumentException("Unknown message type: msgType=" + msgType);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  83 */     if (this.isRequest) {
/*  84 */       return 2;
/*     */     }
/*  86 */     return 2 + MessageUtils.getSizeStr(this.itemDescriptionId) + MessageUtils.getSizeInt();
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  90 */     if (this.isRequest) {
/*  91 */       buffer.put((byte)0);
/*  92 */       buffer.put(this.type.getId());
/*     */     } else {
/*  94 */       buffer.put((byte)1);
/*  95 */       buffer.put(this.type.getId());
/*  96 */       MessageUtils.writeStr(buffer, this.itemDescriptionId);
/*  97 */       MessageUtils.writeInt(buffer, this.tier);
/*     */     } 
/*     */     
/* 100 */     return buffer;
/*     */   }
/*     */   
/*     */   public int getTier() {
/* 104 */     return this.tier;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 108 */     CURE_HEALTH((byte)0),
/* 109 */     CURE_MANA((byte)1),
/* 110 */     CURE_DEBUFF((byte)2),
/* 111 */     SOMETHING((byte)3);
/*     */ 
/*     */     
/* 114 */     private static Type[] ALL = values();
/*     */     
/*     */     private final byte id;
/*     */     
/*     */     Type(byte id) {
/* 119 */       this.id = id;
/*     */     } static {
/*     */     
/*     */     } 

/*     */     public byte getId() {
/* 123 */       return this.id;
/*     */     }

/*     */     public static Type fromId(byte id) {
/*     */       for (Type t : ALL) {
/*     */         if (t.id == id) return t;
/*     */       }
/*     */       throw new IllegalArgumentException("Unknown Type id: " + id);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AutoUseItemMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */