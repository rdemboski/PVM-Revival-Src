/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatureCreationMessage
/*     */   extends AbstractFactionMessage
/*     */   implements Message
/*     */ {
/*     */   private static final short TYPE_ID = 11;
/*     */   protected int creatureId;
/*     */   protected WorldCoordinate coord;
/*     */   private float angle;
/*     */   protected String type;
/*     */   protected String name;
/*     */   protected String quest;
/*     */   protected String questHandIn;
/*     */   protected short questCompletion;
/*     */   private Faction faction;
/*     */   protected Map<Short, Integer> stats;
/*     */   private float radius;
/*     */   protected Object ExtraParam;
/*     */   
/*     */   public void setExtraParam(Object obj) {
/*  40 */     this.ExtraParam = obj;
/*     */   }
/*     */   
/*     */   public Object getExtraParam() {
/*  44 */     return this.ExtraParam;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreatureCreationMessage() {}
/*     */ 
/*     */   
/*     */   public CreatureCreationMessage(int creatureId, WorldCoordinate coord, float angle, float radius, String type, String name, String quest, String questHandIn, short questCompletion, Map<Short, Integer> stats, Faction faction) {
/*  52 */     this.creatureId = creatureId;
/*  53 */     this.coord = coord;
/*  54 */     this.angle = angle;
/*  55 */     this.radius = radius;
/*  56 */     this.type = type;
/*  57 */     this.name = name;
/*  58 */     this.questHandIn = questHandIn;
/*  59 */     this.stats = stats;
/*  60 */     this.quest = quest;
/*  61 */     this.questCompletion = questCompletion;
/*  62 */     this.faction = faction;
/*  63 */     this.ExtraParam = null;
/*     */   }
/*     */   
/*     */   public CreatureCreationMessage(ByteBuffer bytebuffer) {
/*  67 */     this.creatureId = MessageUtils.readInt(bytebuffer);
/*  68 */     this.coord = MessageUtils.readWorldCoordinatePartial(bytebuffer);
/*  69 */     this.angle = bytebuffer.getFloat();
/*  70 */     this.radius = bytebuffer.getFloat();
/*  71 */     this.type = MessageUtils.readStr(bytebuffer);
/*  72 */     this.name = MessageUtils.readStr(bytebuffer);
/*  73 */     this.quest = MessageUtils.readStr(bytebuffer);
/*  74 */     this.questHandIn = MessageUtils.readStr(bytebuffer);
/*  75 */     this.questCompletion = MessageUtils.readShort(bytebuffer);
/*  76 */     this.stats = MessageUtils.readSimplifiedStats(bytebuffer);
/*  77 */     this.faction = readFaction(bytebuffer);
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer bytebuffer) {
/*  81 */     return new CreatureCreationMessage(bytebuffer);
/*     */   }
/*     */   
/*     */   public int getCreatureId() {
/*  85 */     return this.creatureId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCoord() {
/*  89 */     return this.coord;
/*     */   }
/*     */   
/*     */   public float getAngle() {
/*  93 */     return this.angle;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  97 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 101 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getQuest() {
/* 105 */     return this.quest;
/*     */   }
/*     */   
/*     */   public String getQuestHandIn() {
/* 109 */     return this.questHandIn;
/*     */   }
/*     */   
/*     */   public short getQuestCompletion() {
/* 113 */     return this.questCompletion;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 117 */     return 11;
/*     */   }
/*     */   
/*     */   public Map<Short, Integer> getStats() {
/* 121 */     return this.stats;
/*     */   }
/*     */   
/*     */   public Faction getFaction() {
/* 125 */     return this.faction;
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 129 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeFloat() + MessageUtils.getSizeFloat() + MessageUtils.getSizeStr(this.type) + MessageUtils.getSizeStr(this.name) + MessageUtils.getSizeStr(this.quest) + MessageUtils.getSizeStr(this.questHandIn) + MessageUtils.getSizeShort() + MessageUtils.getSizeSimplifiedStats(this.stats) + getSizeFaction(this.faction);
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
/*     */   public ByteBuffer serialize(ByteBuffer byteBuffer) {
/* 143 */     MessageUtils.writeInt(byteBuffer, this.creatureId);
/* 144 */     MessageUtils.writeWorldCoordinatePartial(byteBuffer, this.coord);
/* 145 */     byteBuffer.putFloat(this.angle);
/* 146 */     byteBuffer.putFloat(this.radius);
/* 147 */     MessageUtils.writeStr(byteBuffer, this.type);
/* 148 */     MessageUtils.writeStr(byteBuffer, this.name);
/* 149 */     MessageUtils.writeStr(byteBuffer, this.quest);
/* 150 */     MessageUtils.writeStr(byteBuffer, this.questHandIn);
/* 151 */     MessageUtils.writeShort(byteBuffer, this.questCompletion);
/* 152 */     MessageUtils.writeSimplifiedStats(byteBuffer, this.stats);
/* 153 */     writeFaction(byteBuffer, this.faction);
/* 154 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 159 */     return "CreatureCreationMessage{creatureId=" + this.creatureId + ", coord=" + this.coord + ", angle=" + this.angle + ", type='" + this.type + '\'' + ", name='" + this.name + '\'' + ", quest='" + this.quest + '\'' + ", questHandIn='" + this.questHandIn + '\'' + ", questCompletion=" + this.questCompletion + ", stats=" + this.stats + ", radius=" + this.radius + '}';
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
/*     */   public double getRadius() {
/* 174 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CreatureCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */