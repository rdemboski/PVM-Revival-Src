/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.server.common.LoginResponseMessage;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*     */ import com.funcom.tcg.net.SubscriptionState;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class LoginResponseMessage
/*     */   extends AbstractFactionMessage
/*     */   implements LoginResponseMessage
/*     */ {
/*  24 */   private static final Logger LOGGER = Logger.getLogger(LoginResponseMessage.class.getName());
/*     */   
/*  26 */   public static final LoginResponseMessage LOGIN_FAILED_BANNED = new LoginResponseMessage(ResponseResult.ERROR_BANNED);
/*  27 */   public static final LoginResponseMessage LOGIN_FAILED_USER_PWD = new LoginResponseMessage(ResponseResult.ERROR_USER_PWD);
/*  28 */   public static final LoginResponseMessage LOGIN_FAILED_SYSTEM_ERROR = new LoginResponseMessage(ResponseResult.ERROR_SYSTEM_ERROR);
/*  29 */   public static final LoginResponseMessage LOGIN_FAILED_INVALID_SESSION_ID = new LoginResponseMessage(ResponseResult.ERROR_INVALID_SESSIONID);
/*  30 */   public static final LoginResponseMessage LOGIN_FAILED_NEED_CREATE_CHARACTER = new LoginResponseMessage(ResponseResult.ERROR_NEED_CREATE_CHARACTER);
/*  31 */   public static final LoginResponseMessage LOGIN_FAILED_CHARACTER_CREATION_DISABLED = new LoginResponseMessage(ResponseResult.ERROR_CREATION_DISABLED);
/*     */   
/*     */   private ResponseResult result;
/*     */   
/*     */   private Integer acceptedId;
/*     */   
/*     */   private String nick;
/*     */   private String email;
/*     */   private WorldCoordinate position;
/*     */   private float angle;
/*     */   private Collection<Stat> stats;
/*     */   private int inventoryId;
/*     */   private String[] selectedPetClassIds;
/*     */   private String activePetClassId;
/*     */   private PlayerDescription playerDescription;
/*     */   private String mapId;
/*     */   private List<String> mapsVisited;
/*     */   private long chatClientId;
/*     */   private long chatClientCookie;
/*     */   private String serverDomain;
/*     */   private SubscriptionState subscriptionState;
/*     */   private String[] collectedPetClassIds;
/*     */   private Integer[] collectedPetClassLevels;
/*     */   private Integer[] collectedPetClassXp;
/*     */   private Faction faction;
/*     */   
/*     */   public LoginResponseMessage() {}
/*     */   
/*     */   public LoginResponseMessage(ByteBuffer buffer) {
/*  60 */     byte resultId = buffer.get();
/*  61 */     this.result = ResponseResult.getById(resultId);
/*     */     
/*  63 */     if (isOK()) {
/*  64 */       this.acceptedId = MessageUtils.readInteger(buffer);
/*  65 */       this.nick = MessageUtils.readStr(buffer);
/*  66 */       this.email = MessageUtils.readStr(buffer);
/*  67 */       this.position = MessageUtils.readWorldCoordinatePartial(buffer);
/*  68 */       this.angle = buffer.getFloat();
/*  69 */       this.stats = TCGMessageUtils.readStats(buffer);
/*  70 */       this.inventoryId = MessageUtils.readInt(buffer);
/*  71 */       this.selectedPetClassIds = MessageUtils.readStrArray(buffer);
/*  72 */       this.activePetClassId = MessageUtils.readStr(buffer);
/*  73 */       this.playerDescription = readPlayerDescription(buffer);
/*  74 */       this.mapId = MessageUtils.readStr(buffer);
/*  75 */       this.position.setMapId(this.mapId);
/*  76 */       this.chatClientId = MessageUtils.readLong(buffer);
/*  77 */       this.chatClientCookie = MessageUtils.readLong(buffer);
/*  78 */       this.serverDomain = MessageUtils.readStr(buffer);
/*  79 */       this.mapsVisited = MessageUtils.readListStr(buffer);
/*  80 */       this.subscriptionState = (SubscriptionState)new DefaultSubscriptionState(MessageUtils.readInt(buffer));
/*  81 */       this.collectedPetClassIds = MessageUtils.readStrArray(buffer);
/*  82 */       this.collectedPetClassLevels = MessageUtils.readIntArray(buffer);
/*  83 */       this.collectedPetClassXp = MessageUtils.readIntArray(buffer);
/*  84 */       this.faction = readFaction(buffer);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginResponseMessage(Integer acceptedId, String nick, String email, WorldCoordinate position, float angle, Collection<Stat> stats, int inventoryId, String[] selectedPetClassIds, String activePetClassId, PlayerDescription playerDescription, String mapId, long chatClientId, long chatClientCookie, String serverDomain, List<String> mapsVisited, SubscriptionState subscriptionState, String[] collectedPetClassIds, Integer[] collectedPetClassLevels, Integer[] collectedPetClassXp, Faction faction) {
/* 106 */     this.result = ResponseResult.OK;
/*     */     
/* 108 */     this.stats = stats;
/* 109 */     this.acceptedId = acceptedId;
/* 110 */     this.nick = nick;
/* 111 */     this.email = email;
/* 112 */     this.position = position;
/* 113 */     this.angle = angle;
/* 114 */     this.inventoryId = inventoryId;
/* 115 */     this.selectedPetClassIds = selectedPetClassIds;
/* 116 */     this.activePetClassId = activePetClassId;
/* 117 */     this.playerDescription = playerDescription;
/* 118 */     this.mapId = mapId;
/* 119 */     this.mapsVisited = mapsVisited;
/* 120 */     this.chatClientId = chatClientId;
/* 121 */     this.chatClientCookie = chatClientCookie;
/* 122 */     this.serverDomain = serverDomain;
/* 123 */     this.subscriptionState = subscriptionState;
/* 124 */     this.collectedPetClassIds = collectedPetClassIds;
/* 125 */     this.collectedPetClassLevels = collectedPetClassLevels;
/* 126 */     this.collectedPetClassXp = collectedPetClassXp;
/* 127 */     this.faction = faction;
/*     */   }
/*     */   
/*     */   public LoginResponseMessage(ResponseResult result) {
/* 131 */     this.result = result;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/* 135 */     return (Message)new LoginResponseMessage(buffer);
/*     */   }
/*     */   
/*     */   public boolean getAccepted() {
/* 139 */     return (this.acceptedId != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getAcceptedId() {
/* 144 */     return this.acceptedId;
/*     */   }
/*     */   
/*     */   public String getNick() {
/* 148 */     return this.nick;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/* 152 */     return this.email;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getPosition() {
/* 156 */     return this.position;
/*     */   }
/*     */   
/*     */   public float getAngle() {
/* 160 */     return this.angle;
/*     */   }
/*     */   
/*     */   public Collection<Stat> getStats() {
/* 164 */     return this.stats;
/*     */   }
/*     */   
/*     */   public String[] getSelectedPetClassIds() {
/* 168 */     return this.selectedPetClassIds;
/*     */   }
/*     */   
/*     */   public String getActivePetClassId() {
/* 172 */     return this.activePetClassId;
/*     */   }
/*     */   
/*     */   public PlayerDescription getPlayerDescription() {
/* 176 */     return this.playerDescription;
/*     */   }
/*     */   
/*     */   public String getMapId() {
/* 180 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public SubscriptionState getSubscriptionState() {
/* 184 */     return this.subscriptionState;
/*     */   }
/*     */   
/*     */   public long getChatClientCookie() {
/* 188 */     return this.chatClientCookie;
/*     */   }
/*     */   
/*     */   public long getChatClientId() {
/* 192 */     return this.chatClientId;
/*     */   }
/*     */   
/*     */   public String getServerDomain() {
/* 196 */     return this.serverDomain;
/*     */   }
/*     */   
/*     */   public String[] getCollectedPetClassIds() {
/* 200 */     return this.collectedPetClassIds;
/*     */   }
/*     */   
/*     */   public Integer[] getCollectedPetClassLevels() {
/* 204 */     return this.collectedPetClassLevels;
/*     */   }
/*     */   
/*     */   public Integer[] getCollectedPetClassXp() {
/* 208 */     return this.collectedPetClassXp;
/*     */   }
/*     */   
/*     */   public Faction getFaction() {
/* 212 */     return this.faction;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 216 */     return 6;
/*     */   }
/*     */   
/*     */   private PlayerDescription readPlayerDescription(ByteBuffer bytebuffer) {
/* 220 */     PlayerDescription playerDescription = new PlayerDescription();
/* 221 */     String gender = MessageUtils.readStr(bytebuffer);
/* 222 */     if (gender.equalsIgnoreCase(PlayerDescription.Gender.MALE.name())) {
/* 223 */       playerDescription.setGender(PlayerDescription.Gender.MALE);
/*     */     } else {
/* 225 */       playerDescription.setGender(PlayerDescription.Gender.FEMALE);
/*     */     } 
/* 227 */     playerDescription.setSkinColorId(MessageUtils.readStr(bytebuffer));
/* 228 */     playerDescription.setFaceId(MessageUtils.readStr(bytebuffer));
/* 229 */     playerDescription.setEyeColorId(MessageUtils.readStr(bytebuffer));
/* 230 */     playerDescription.setHairId(MessageUtils.readStr(bytebuffer));
/* 231 */     playerDescription.setHairColorId(MessageUtils.readStr(bytebuffer));
/* 232 */     return playerDescription;
/*     */   }
/*     */   
/*     */   private void writePlayerDescription(ByteBuffer byteBuffer, PlayerDescription playerDescription) {
/* 236 */     MessageUtils.writeStr(byteBuffer, playerDescription.getGender().name());
/* 237 */     MessageUtils.writeStr(byteBuffer, playerDescription.getSkinColorId());
/* 238 */     MessageUtils.writeStr(byteBuffer, playerDescription.getFaceId());
/* 239 */     MessageUtils.writeStr(byteBuffer, playerDescription.getEyeColorId());
/* 240 */     MessageUtils.writeStr(byteBuffer, playerDescription.getHairId());
/* 241 */     MessageUtils.writeStr(byteBuffer, playerDescription.getHairColorId());
/*     */   }
/*     */   
/*     */   private int getSizePlayerDescription(PlayerDescription playerDescription) {
/* 245 */     int size = 0;
/* 246 */     size += MessageUtils.getSizeStr(playerDescription.getGender().name());
/* 247 */     size += MessageUtils.getSizeStr(playerDescription.getSkinColorId());
/* 248 */     size += MessageUtils.getSizeStr(playerDescription.getFaceId());
/* 249 */     size += MessageUtils.getSizeStr(playerDescription.getEyeColorId());
/* 250 */     size += MessageUtils.getSizeStr(playerDescription.getHairId());
/* 251 */     size += MessageUtils.getSizeStr(playerDescription.getHairColorId());
/* 252 */     return size;
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 256 */     if (isOK())
/*     */     {
/* 258 */       return 1 + MessageUtils.getSizeInteger() + MessageUtils.getSizeStr(this.nick) + MessageUtils.getSizeStr(this.email) + MessageUtils.getSizeDouble() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeFloat() + TCGMessageUtils.getSizeStats(this.stats) + MessageUtils.getSizeInt() + MessageUtils.getSizeStrArray(this.selectedPetClassIds) + MessageUtils.getSizeStr(this.activePetClassId) + getSizePlayerDescription(this.playerDescription) + MessageUtils.getSizeStr(this.mapId) + MessageUtils.getSizeLong() + MessageUtils.getSizeLong() + MessageUtils.getSizeStr(this.serverDomain) + MessageUtils.getSizeListStr(this.mapsVisited) + MessageUtils.getSizeInt() + MessageUtils.getSizeStrArray(this.collectedPetClassIds) + MessageUtils.getSizeIntArray(this.collectedPetClassXp) + MessageUtils.getSizeIntArray(this.collectedPetClassLevels) + getSizeFaction(this.faction);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 286 */     buffer.put(this.result.getId());
/*     */     
/* 288 */     if (isOK()) {
/* 289 */       LOGGER.log((Priority)Level.INFO, "active: " + this.activePetClassId);
/*     */       
/* 291 */       MessageUtils.writeInteger(buffer, this.acceptedId);
/* 292 */       MessageUtils.writeStr(buffer, this.nick);
/* 293 */       MessageUtils.writeStr(buffer, this.email);
/* 294 */       MessageUtils.writeWorldCoordinatePartial(buffer, this.position);
/* 295 */       buffer.putFloat(this.angle);
/* 296 */       TCGMessageUtils.writeStats(buffer, this.stats);
/* 297 */       MessageUtils.writeInt(buffer, this.inventoryId);
/* 298 */       MessageUtils.writeStrArray(buffer, this.selectedPetClassIds);
/* 299 */       MessageUtils.writeStr(buffer, this.activePetClassId);
/* 300 */       writePlayerDescription(buffer, this.playerDescription);
/* 301 */       MessageUtils.writeStr(buffer, this.mapId);
/* 302 */       MessageUtils.writeLong(buffer, this.chatClientId);
/* 303 */       MessageUtils.writeLong(buffer, this.chatClientCookie);
/* 304 */       MessageUtils.writeStr(buffer, this.serverDomain);
/* 305 */       MessageUtils.writeListStr(buffer, this.mapsVisited);
/* 306 */       MessageUtils.writeInt(buffer, this.subscriptionState.getFlags());
/* 307 */       MessageUtils.writeStrArray(buffer, this.collectedPetClassIds);
/* 308 */       MessageUtils.writeIntArray(buffer, this.collectedPetClassLevels);
/* 309 */       MessageUtils.writeIntArray(buffer, this.collectedPetClassXp);
/* 310 */       writeFaction(buffer, this.faction);
/*     */     } 
/*     */     
/* 313 */     return buffer;
/*     */   }
/*     */   
/*     */   public String printResponse() {
/* 317 */     return "[acceptedId=" + this.acceptedId + ",nick=" + this.nick + ",email=" + this.email + ",position=" + this.position + ",angle=" + this.angle + ",stats=" + this.stats + ",mapId=" + this.mapId + ",chatClientId=" + this.chatClientId + ",chatClientCookie=" + this.chatClientCookie + ",serverDomain=" + this.serverDomain;
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
/*     */   public String toString() {
/* 332 */     return "LoginResponseMessage{result=" + this.result + ", acceptedId=" + this.acceptedId + ", nick='" + this.nick + '\'' + ", email='" + this.email + '\'' + ", position=" + this.position + ", angle=" + this.angle + ", stats=" + this.stats + ", inventoryId=" + this.inventoryId + ", selectedPetClassIds=" + ((this.selectedPetClassIds == null) ? null : (String)Arrays.<String>asList(this.selectedPetClassIds)) + ", activePetClassId='" + this.activePetClassId + '\'' + ", playerDescription=" + this.playerDescription + ", mapId='" + this.mapId + '\'' + ", chatClientId=" + this.chatClientId + ", chatClientCookie=" + this.chatClientCookie + ", serverDomain=" + this.serverDomain + ", mapsVisited=" + this.mapsVisited + '}';
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOK() {
/* 353 */     return (this.result == ResponseResult.OK);
/*     */   }
/*     */   
/*     */   public int getInventoryId() {
/* 357 */     return this.inventoryId;
/*     */   }
/*     */   
/*     */   public ResponseResult getResult() {
/* 361 */     return this.result;
/*     */   }
/*     */   
/*     */   public List<String> getMapsVisited() {
/* 365 */     return this.mapsVisited;
/*     */   }
/*     */   
/*     */   public enum ResponseResult {
/* 369 */     OK((byte)1), ERROR_USER_PWD((byte)2), ERROR_SYSTEM_ERROR((byte)3), ERROR_INVALID_SESSIONID((byte)4),
/* 370 */     ERROR_NEED_CREATE_CHARACTER((byte)5), ERROR_BANNED((byte)6), ERROR_CREATION_DISABLED((byte)7);
/*     */     
/* 372 */     private static final ResponseResult[] RESPONSE_RESULTS = values();
/*     */     
/*     */     private final byte id;
/*     */     
/*     */     ResponseResult(byte id) {
/* 377 */       this.id = id;
/*     */     } static {
/*     */     
/*     */     } public byte getId() {
/* 381 */       return this.id;
/*     */     }
/*     */     
/*     */     private static ResponseResult getById(byte id) {
/* 385 */       for (ResponseResult result : RESPONSE_RESULTS) {
/* 386 */         if (result.getId() == id) {
/* 387 */           return result;
/*     */         }
/*     */       } 
/*     */       
/* 391 */       throw new IllegalArgumentException("unknown " + ResponseResult.class + " id:" + id);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\LoginResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */