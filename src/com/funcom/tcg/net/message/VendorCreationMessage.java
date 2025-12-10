/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.vendor.VendorItem;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VendorCreationMessage
/*     */   implements Message
/*     */ {
/*  19 */   public static String CREATURETYPE_VENDOR = "vendor";
/*  20 */   private static final int MSG_SIZE = MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate();
/*     */   
/*     */   private int creatureId;
/*     */   private String vendorId;
/*     */   private String model;
/*     */   private WorldCoordinate coord;
/*     */   private List<VendorItem> vendorItems;
/*     */   private List<VendorItem> buyBackItems;
/*     */   private double radius;
/*     */   private float angle;
/*     */   
/*     */   public VendorCreationMessage(ByteBuffer buffer) {
/*  32 */     this.vendorId = MessageUtils.readStr(buffer);
/*  33 */     this.model = MessageUtils.readStr(buffer);
/*  34 */     this.creatureId = MessageUtils.readInt(buffer);
/*  35 */     this.coord = MessageUtils.readWorldCoordinatePartial(buffer);
/*  36 */     this.vendorItems = readVendorItems(buffer);
/*  37 */     this.buyBackItems = readVendorItems(buffer);
/*  38 */     this.radius = MessageUtils.readDouble(buffer);
/*  39 */     this.angle = MessageUtils.readFloat(buffer);
/*     */   }
/*     */   
/*     */   public VendorCreationMessage(String vendorId, String model, int creatureId, WorldCoordinate coord, List<VendorItem> items, List<Item> buyBackItems, double radius, float angle) {
/*  43 */     this.vendorId = vendorId;
/*  44 */     this.model = model;
/*  45 */     this.creatureId = creatureId;
/*  46 */     this.coord = coord;
/*  47 */     this.radius = radius;
/*  48 */     this.vendorItems = items;
/*  49 */     this.angle = angle;
/*  50 */     this.buyBackItems = new ArrayList<VendorItem>(buyBackItems.size());
/*  51 */     for (Item buyBackItem : buyBackItems) {
/*  52 */       if (buyBackItem != null) {
/*  53 */         this.buyBackItems.add(new VendorItem(buyBackItem.getDescription().getId(), 1, "", buyBackItem.getDescription().getTier(), 1, 99, buyBackItem.getDescription().getItemValue(), buyBackItem.getDescription().getItemValueAmount()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VendorCreationMessage() {}
/*     */ 
/*     */   
/*     */   public String getVendorId() {
/*  64 */     return this.vendorId;
/*     */   }
/*     */   
/*     */   public String getModel() {
/*  68 */     return this.model;
/*     */   }
/*     */   
/*     */   public int getCreatureId() {
/*  72 */     return this.creatureId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCoord() {
/*  76 */     return this.coord;
/*     */   }
/*     */   
/*     */   public float getAngle() {
/*  80 */     return this.angle;
/*     */   }
/*     */   
/*     */   public List<VendorItem> getVendorItems() {
/*  84 */     return Collections.unmodifiableList(this.vendorItems);
/*     */   }
/*     */   
/*     */   public List<VendorItem> getBuyBackItems() {
/*  88 */     return Collections.unmodifiableList(this.buyBackItems);
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  92 */     return 22;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  96 */     return new VendorCreationMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 100 */     return MessageUtils.getSizeStr(this.vendorId) + MessageUtils.getSizeStr(this.model) + MSG_SIZE + getSizeList(this.vendorItems) + getSizeList(this.buyBackItems) + MessageUtils.getSizeDouble() + MessageUtils.getSizeFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSizeList(List<VendorItem> itemList) {
/* 105 */     int size = MessageUtils.getSizeInt();
/* 106 */     int count = itemList.size();
/* 107 */     for (int i = 0; i < count; i++) {
/* 108 */       VendorItem vendorItem = itemList.get(i);
/* 109 */       size += MessageUtils.getSizeStr(vendorItem.getItemId());
/* 110 */       size += MessageUtils.getSizeInt();
/* 111 */       size += MessageUtils.getSizeStr(vendorItem.getItemDescriptionText());
/* 112 */       size += MessageUtils.getSizeInt();
/* 113 */       size += MessageUtils.getSizeInt();
/* 114 */       size += MessageUtils.getSizeInt();
/* 115 */       size += MessageUtils.getSizeStr(vendorItem.getItemCostId());
/* 116 */       size += MessageUtils.getSizeInt();
/*     */     } 
/* 118 */     return size;
/*     */   }
/*     */   
/*     */   private List<VendorItem> readVendorItems(ByteBuffer byteBuffer) {
/* 122 */     int size = byteBuffer.getInt();
/*     */     
/* 124 */     List<VendorItem> inventoryItems = new ArrayList<VendorItem>(size);
/* 125 */     for (int i = 0; i < size; i++) {
/* 126 */       inventoryItems.add(new VendorItem(MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer)));
/*     */     }
/* 128 */     return inventoryItems;
/*     */   }
/*     */   
/*     */   private void writeItems(ByteBuffer byteBuffer, List<VendorItem> vendorItems) {
/* 132 */     int size = vendorItems.size();
/*     */     
/* 134 */     byteBuffer.putInt(size);
/* 135 */     for (int j = 0; j < size; j++) {
/* 136 */       VendorItem vendorItem = vendorItems.get(j);
/* 137 */       MessageUtils.writeStr(byteBuffer, vendorItem.getItemId());
/* 138 */       MessageUtils.writeInt(byteBuffer, vendorItem.getItemAmount());
/* 139 */       MessageUtils.writeStr(byteBuffer, vendorItem.getItemDescriptionText());
/* 140 */       MessageUtils.writeInt(byteBuffer, vendorItem.getTier());
/* 141 */       MessageUtils.writeInt(byteBuffer, vendorItem.getFromLevel());
/* 142 */       MessageUtils.writeInt(byteBuffer, vendorItem.getToLevel());
/* 143 */       MessageUtils.writeStr(byteBuffer, vendorItem.getItemCostId());
/* 144 */       MessageUtils.writeInt(byteBuffer, vendorItem.getItemCostAmount());
/*     */     } 
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 149 */     MessageUtils.writeStr(buffer, this.vendorId);
/* 150 */     MessageUtils.writeStr(buffer, this.model);
/* 151 */     MessageUtils.writeInt(buffer, this.creatureId);
/* 152 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.coord);
/* 153 */     writeItems(buffer, this.vendorItems);
/* 154 */     writeItems(buffer, this.buyBackItems);
/* 155 */     MessageUtils.writeDouble(buffer, this.radius);
/* 156 */     MessageUtils.writeFloat(buffer, this.angle);
/* 157 */     return buffer;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 161 */     StringBuffer sb = new StringBuffer();
/* 162 */     sb.append(getClass().getName()).append("[").append("vendorId=").append(this.vendorId).append("model=").append(this.model).append(",creatureId=").append(this.creatureId).append(",coord=").append(this.coord).append(",vendorItems=").append(this.vendorItems).append(",buyBackItems=").append(this.buyBackItems).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public double getRadius() {
/* 176 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\VendorCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */