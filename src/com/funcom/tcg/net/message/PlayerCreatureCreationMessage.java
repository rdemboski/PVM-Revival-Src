/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemHolder;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.funcom.tcg.rpg.InventoryItem;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerCreatureCreationMessage
/*     */   extends AbstractFactionMessage
/*     */   implements Message
/*     */ {
/*     */   protected int creatureId;
/*     */   protected WorldCoordinate coord;
/*     */   private float angle;
/*     */   protected String type;
/*     */   protected String name;
/*     */   private boolean dead;
/*     */   protected Map<Short, Integer> stats;
/*     */   protected PlayerDescription playerDescription;
/*     */   private int inventoryType;
/*     */   private List<InventoryItem> inventoryItems;
/*     */   private String activePetClassId;
/*     */   private int activePetLevel;
/*     */   private int externalChatId;
/*     */   private Faction faction;
/*     */   
/*     */   public PlayerCreatureCreationMessage() {}
/*     */   
/*     */   public PlayerCreatureCreationMessage(int creatureId, WorldCoordinate coord, float angle, String type, String name, boolean dead, Map<Short, Integer> stats, PlayerDescription playerDescription, int inventoryType, ItemHolder itemHolder, String activePetClassId, int externalChatId, int activePetLevel, Faction faction) {
/*  46 */     this.creatureId = creatureId;
/*  47 */     this.coord = coord;
/*  48 */     this.angle = angle;
/*  49 */     this.type = type;
/*  50 */     this.name = name;
/*  51 */     this.dead = dead;
/*  52 */     this.stats = stats;
/*  53 */     this.playerDescription = playerDescription;
/*  54 */     this.inventoryType = inventoryType;
/*  55 */     this.activePetClassId = activePetClassId;
/*  56 */     this.externalChatId = externalChatId;
/*  57 */     this.activePetLevel = activePetLevel;
/*  58 */     this.faction = faction;
/*  59 */     this.inventoryItems = new ArrayList<InventoryItem>(itemHolder.getItemCount());
/*  60 */     int index = 0;
/*  61 */     for (Item item : itemHolder) {
/*  62 */       if (item != null) {
/*  63 */         this.inventoryItems.add(new InventoryItem(item.getDescription().getId(), item.getDescription().getTier(), index, item.getAmount(), item.getCostId(), item.getCostAmount()));
/*     */       }
/*     */ 
/*     */       
/*  67 */       index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public PlayerCreatureCreationMessage(ByteBuffer bytebuffer) {
/*  72 */     this.creatureId = MessageUtils.readInt(bytebuffer);
/*  73 */     this.coord = MessageUtils.readWorldCoordinatePartial(bytebuffer);
/*  74 */     this.angle = bytebuffer.getFloat();
/*  75 */     this.type = MessageUtils.readStr(bytebuffer);
/*  76 */     this.name = MessageUtils.readStr(bytebuffer);
/*  77 */     this.dead = MessageUtils.readBoolean(bytebuffer).booleanValue();
/*  78 */     this.stats = MessageUtils.readSimplifiedStats(bytebuffer);
/*  79 */     this.playerDescription = readPlayerDescription(bytebuffer);
/*  80 */     this.inventoryType = MessageUtils.readInt(bytebuffer);
/*  81 */     this.inventoryItems = readInventoryItems(bytebuffer);
/*  82 */     this.activePetClassId = MessageUtils.readStr(bytebuffer);
/*  83 */     this.externalChatId = MessageUtils.readInt(bytebuffer);
/*  84 */     this.activePetLevel = MessageUtils.readInt(bytebuffer);
/*  85 */     this.faction = readFaction(bytebuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer bytebuffer) {
/*  90 */     return new PlayerCreatureCreationMessage(bytebuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  95 */     return 28;
/*     */   }
/*     */   
/*     */   public int getCreatureId() {
/*  99 */     return this.creatureId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCoord() {
/* 103 */     return this.coord;
/*     */   }
/*     */   
/*     */   public float getAngle() {
/* 107 */     return this.angle;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 111 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 115 */     return this.name;
/*     */   }
/*     */   
/*     */   public PlayerDescription getPlayerDescription() {
/* 119 */     return this.playerDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Short, Integer> getStats() {
/* 124 */     return this.stats;
/*     */   }
/*     */   
/*     */   public int getInventoryType() {
/* 128 */     return this.inventoryType;
/*     */   }
/*     */   
/*     */   public Faction getFaction() {
/* 132 */     return this.faction;
/*     */   }
/*     */   
/*     */   public List<InventoryItem> getInventoryItems() {
/* 136 */     return this.inventoryItems;
/*     */   }
/*     */   
/*     */   public String getActivePetClassId() {
/* 140 */     return this.activePetClassId;
/*     */   }
/*     */   
/*     */   public int getActivePetLevel() {
/* 144 */     return this.activePetLevel;
/*     */   }
/*     */   
/*     */   public int getExternalChatId() {
/* 148 */     return this.externalChatId;
/*     */   }
/*     */   
/*     */   private PlayerDescription readPlayerDescription(ByteBuffer bytebuffer) {
/* 152 */     PlayerDescription playerDescription = new PlayerDescription();
/* 153 */     String gender = MessageUtils.readStr(bytebuffer);
/* 154 */     if (gender.equalsIgnoreCase(PlayerDescription.Gender.MALE.name())) {
/* 155 */       playerDescription.setGender(PlayerDescription.Gender.MALE);
/*     */     } else {
/* 157 */       playerDescription.setGender(PlayerDescription.Gender.FEMALE);
/*     */     } 
/* 159 */     playerDescription.setSkinColorId(MessageUtils.readStr(bytebuffer));
/* 160 */     playerDescription.setFaceId(MessageUtils.readStr(bytebuffer));
/* 161 */     playerDescription.setEyeColorId(MessageUtils.readStr(bytebuffer));
/* 162 */     playerDescription.setHairId(MessageUtils.readStr(bytebuffer));
/* 163 */     playerDescription.setHairColorId(MessageUtils.readStr(bytebuffer));
/* 164 */     return playerDescription;
/*     */   }
/*     */   
/*     */   private List<InventoryItem> readInventoryItems(ByteBuffer byteBuffer) {
/* 168 */     int size = byteBuffer.getInt();
/*     */     
/* 170 */     List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>(size);
/* 171 */     for (int i = 0; i < size; i++) {
/* 172 */       inventoryItems.add(new InventoryItem(MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readInt(byteBuffer), MessageUtils.readStr(byteBuffer), MessageUtils.readInt(byteBuffer)));
/*     */     }
/* 174 */     return inventoryItems;
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 178 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeFloat() + MessageUtils.getSizeStr(this.type) + MessageUtils.getSizeStr(this.name) + MessageUtils.getSizeBoolean() + MessageUtils.getSizeSimplifiedStats(this.stats) + getSizePLayerDescription(this.playerDescription) + MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + getSizeInventoryItems() + MessageUtils.getSizeStr(this.activePetClassId) + MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + getSizeFaction(this.faction);
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer byteBuffer) {
/* 196 */     MessageUtils.writeInt(byteBuffer, this.creatureId);
/* 197 */     MessageUtils.writeWorldCoordinatePartial(byteBuffer, this.coord);
/* 198 */     byteBuffer.putFloat(this.angle);
/* 199 */     MessageUtils.writeStr(byteBuffer, this.type);
/* 200 */     MessageUtils.writeStr(byteBuffer, this.name);
/* 201 */     MessageUtils.writeBoolean(byteBuffer, Boolean.valueOf(this.dead));
/* 202 */     MessageUtils.writeSimplifiedStats(byteBuffer, this.stats);
/* 203 */     writePlayerDescription(byteBuffer, this.playerDescription);
/* 204 */     MessageUtils.writeInt(byteBuffer, this.inventoryType);
/* 205 */     writeInventoryItems(byteBuffer, this.inventoryItems);
/* 206 */     MessageUtils.writeStr(byteBuffer, this.activePetClassId);
/* 207 */     MessageUtils.writeInt(byteBuffer, this.externalChatId);
/* 208 */     MessageUtils.writeInt(byteBuffer, this.activePetLevel);
/* 209 */     writeFaction(byteBuffer, this.faction);
/* 210 */     return byteBuffer;
/*     */   }
/*     */   
/*     */   private int getSizeInventoryItems() {
/* 214 */     int size = 0;
/* 215 */     int count = this.inventoryItems.size();
/* 216 */     for (int i = 0; i < count; i++) {
/* 217 */       InventoryItem inventoryItem = this.inventoryItems.get(i);
/* 218 */       size += MessageUtils.getSizeStr(inventoryItem.getItemId());
/* 219 */       size += MessageUtils.getSizeInt();
/* 220 */       size += MessageUtils.getSizeInt();
/* 221 */       size += MessageUtils.getSizeInt();
/* 222 */       size += MessageUtils.getSizeStr(inventoryItem.getCostId());
/* 223 */       size += MessageUtils.getSizeInt();
/*     */     } 
/*     */     
/* 226 */     return size;
/*     */   }
/*     */   
/*     */   private int getSizePLayerDescription(PlayerDescription playerDescription) {
/* 230 */     int size = 0;
/* 231 */     size += MessageUtils.getSizeStr(playerDescription.getGender().name());
/* 232 */     size += MessageUtils.getSizeStr(playerDescription.getSkinColorId());
/* 233 */     size += MessageUtils.getSizeStr(playerDescription.getFaceId());
/* 234 */     size += MessageUtils.getSizeStr(playerDescription.getEyeColorId());
/* 235 */     size += MessageUtils.getSizeStr(playerDescription.getHairId());
/* 236 */     size += MessageUtils.getSizeStr(playerDescription.getHairColorId());
/* 237 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeInventoryItems(ByteBuffer byteBuffer, List<InventoryItem> inventoryItems) {
/* 242 */     int size = inventoryItems.size();
/*     */     
/* 244 */     byteBuffer.putInt(size);
/* 245 */     for (int j = 0; j < size; j++) {
/* 246 */       InventoryItem inventoryItem = inventoryItems.get(j);
/* 247 */       MessageUtils.writeStr(byteBuffer, inventoryItem.getItemId());
/* 248 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getTier());
/* 249 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getSlotId());
/* 250 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getAmount());
/* 251 */       MessageUtils.writeStr(byteBuffer, inventoryItem.getCostId());
/* 252 */       MessageUtils.writeInt(byteBuffer, inventoryItem.getCostAmount());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writePlayerDescription(ByteBuffer byteBuffer, PlayerDescription playerDescription) {
/* 257 */     MessageUtils.writeStr(byteBuffer, playerDescription.getGender().name());
/* 258 */     MessageUtils.writeStr(byteBuffer, playerDescription.getSkinColorId());
/* 259 */     MessageUtils.writeStr(byteBuffer, playerDescription.getFaceId());
/* 260 */     MessageUtils.writeStr(byteBuffer, playerDescription.getEyeColorId());
/* 261 */     MessageUtils.writeStr(byteBuffer, playerDescription.getHairId());
/* 262 */     MessageUtils.writeStr(byteBuffer, playerDescription.getHairColorId());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 266 */     return getClass().getName() + "[id=" + this.creatureId + ",coord=" + this.coord.toString() + ",angle=" + this.angle + ",type=" + this.type + ",name=" + this.name + ",playerDescription=" + this.playerDescription + ",itemContainer=" + this.inventoryType + ",inventoryItems=" + this.inventoryItems + ",externalChatId=" + this.externalChatId + "]";
/*     */   }
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
/*     */   public boolean isDead() {
/* 280 */     return this.dead;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PlayerCreatureCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */