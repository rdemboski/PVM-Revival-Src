/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemHolder;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.rpg.InventoryItem;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncInventoryListMessage
/*     */   implements Message
/*     */ {
/*     */   private int id;
/*     */   private int inventoryType;
/*     */   private int capacity;
/*     */   private List<InventoryItem> inventoryItems;
/*     */   
/*     */   public SyncInventoryListMessage(ByteBuffer byteBuffer) {
/*  25 */     this.id = MessageUtils.readInt(byteBuffer);
/*  26 */     this.inventoryType = MessageUtils.readInt(byteBuffer);
/*  27 */     this.capacity = MessageUtils.readInt(byteBuffer);
/*  28 */     this.inventoryItems = readInventoryItems(byteBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public SyncInventoryListMessage() {}
/*     */   
/*     */   public SyncInventoryListMessage(int id, int inventoryType, int capacity, ItemHolder itemHolder) {
/*  35 */     this.id = id;
/*  36 */     this.inventoryType = inventoryType;
/*  37 */     this.capacity = capacity;
/*     */     
/*  39 */     this.inventoryItems = new ArrayList<InventoryItem>(itemHolder.getItemCount());
/*  40 */     int index = 0;
/*  41 */     for (Item item : itemHolder) {
/*  42 */       if (item != null) {
/*  43 */         this.inventoryItems.add(new InventoryItem(item.getDescription().getId(), item.getDescription().getTier(), index, item.getAmount(), item.getCostId(), item.getCostAmount()));
/*     */       }
/*     */ 
/*     */       
/*  47 */       index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  52 */     return 205;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  56 */     return new SyncInventoryListMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  60 */     return MessageUtils.getSizeInt() * 4 + getSizeInventoryItems();
/*     */   }
/*     */   
/*     */   public List<InventoryItem> getInventoryItems() {
/*  64 */     return this.inventoryItems;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  68 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getInventoryType() {
/*  72 */     return this.inventoryType;
/*     */   }
/*     */   
/*     */   public int getCapacity() {
/*  76 */     return this.capacity;
/*     */   }
/*     */   
/*     */   private int getSizeInventoryItems() {
/*  80 */     int size = 0;
/*  81 */     int count = this.inventoryItems.size();
/*  82 */     for (int i = 0; i < count; i++) {
/*  83 */       InventoryItem inventoryItem = this.inventoryItems.get(i);
/*  84 */       size += MessageUtils.getSizeStr(inventoryItem.getItemId());
/*  85 */       size += MessageUtils.getSizeInt();
/*  86 */       size += MessageUtils.getSizeInt();
/*  87 */       size += MessageUtils.getSizeInt();
/*  88 */       size += MessageUtils.getSizeStr(inventoryItem.getCostId());
/*  89 */       size += MessageUtils.getSizeInt();
/*     */     } 
/*     */     
/*  92 */     return size;
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  96 */     MessageUtils.writeInt(buffer, this.id);
/*  97 */     MessageUtils.writeInt(buffer, this.inventoryType);
/*  98 */     MessageUtils.writeInt(buffer, this.capacity);
/*  99 */     writeInventoryItems(buffer, this.inventoryItems);
/* 100 */     return buffer;
/*     */   }
/*     */   
/*     */   private List<InventoryItem> readInventoryItems(ByteBuffer byteBuffer) {
/* 104 */     int size = byteBuffer.getInt();
/*     */     
/* 106 */     List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>(size);
/* 107 */     for (int i = 0; i < size; i++) {
/* 108 */       inventoryItems.add(new InventoryItem(MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer)));
/*     */     }
/*     */     
/* 111 */     return inventoryItems;
/*     */   }
/*     */   
/*     */   private void writeInventoryItems(ByteBuffer byteBuffer, List<InventoryItem> inventoryItems) {
/* 115 */     int size = inventoryItems.size();
/*     */     
/* 117 */     byteBuffer.putInt(size);
/* 118 */     for (int j = 0; j < size; j++) {
/* 119 */       InventoryItem inventoryItem = inventoryItems.get(j);
/* 120 */       MessageUtils.writeStr(byteBuffer, inventoryItem.getItemId());
/* 121 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getTier());
/* 122 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getSlotId());
/* 123 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getAmount());
/* 124 */       MessageUtils.writeStr(byteBuffer, inventoryItem.getCostId());
/* 125 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getCostAmount());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     StringBuffer sb = new StringBuffer();
/* 132 */     sb.append("[").append("id=").append(this.id).append(",itemContainer=").append(this.inventoryType).append(",capacity=").append(this.capacity).append(",(itemCount)=").append(this.inventoryItems.size()).append(",{inventoryItems=").append(this.inventoryItems.toString()).append("}").append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SyncInventoryListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */