/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import com.funcom.server.common.util.IntegerIDMap;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseMessageFactory
/*     */   implements MessageFactory
/*     */ {
/*  17 */   private static final Logger LOG = Logger.getLogger(BaseMessageFactory.class.getName());
/*     */   
/*     */   protected static final int ID_SIZE = 2;
/*     */   
/*     */   private int bufferSize;
/*     */   
/*     */   protected IOBufferManager bufferManager;
/*     */   
/*  25 */   protected IntegerIDMap<Message> prototypes = new IntegerIDMap();
/*     */ 
/*     */   
/*     */   private boolean logMessageStats = false;
/*     */ 
/*     */   
/*  31 */   private ConcurrentHashMap<Short, Double> incomingMessageTypeMap = new ConcurrentHashMap<Short, Double>();
/*  32 */   private ConcurrentHashMap<Short, Double> outgoingMessageTypeMap = new ConcurrentHashMap<Short, Double>();
/*     */   
/*     */   public BaseMessageFactory(IOBufferManager bufferManager, int bufferSize, List<Class<? extends Message>> messageClasses) {
/*  35 */     this.bufferManager = bufferManager;
/*  36 */     this.bufferSize = bufferSize;
/*     */     
/*  38 */     if (messageClasses != null) {
/*     */       try {
/*  40 */         for (Class<? extends Message> messageClass : messageClasses) {
/*  41 */           Message prototype = messageClass.newInstance();
/*  42 */           short messageType = prototype.getMessageType();
/*  43 */           if (!this.prototypes.contains(messageType)) {
/*  44 */             this.prototypes.put(messageType, prototype); continue;
/*     */           } 
/*  46 */           throw new RuntimeException("Duplicate message for message id: " + MessageTypeManager.getName(Short.valueOf(messageType)) + "/" + messageType);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  51 */         if (this.prototypes.getCapacity() <= 1) {
/*  52 */           LOG.warn("Too few message types? Parser Count <= " + this.prototypes.getCapacity());
/*     */         }
/*  54 */       } catch (IllegalAccessException e) {
/*  55 */         throw new RuntimeException(e);
/*  56 */       } catch (InstantiationException e) {
/*  57 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLogMessageStats(boolean logMessageStats) {
/*  63 */     this.logMessageStats = logMessageStats;
/*  64 */     if (logMessageStats) {
/*  65 */       List<Short> messagetype = MessageTypeManager.values();
/*  66 */       for (Short messageType : messagetype) {
/*  67 */         this.incomingMessageTypeMap.put(messageType, Double.valueOf(0.0D));
/*  68 */         this.outgoingMessageTypeMap.put(messageType, Double.valueOf(0.0D));
/*     */       } 
/*     */     } else {
/*  71 */       this.incomingMessageTypeMap.clear();
/*  72 */       this.outgoingMessageTypeMap.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearMessageCaches() {
/*  77 */     this.incomingMessageTypeMap.clear();
/*  78 */     this.outgoingMessageTypeMap.clear();
/*  79 */     setLogMessageStats(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getIncomingMessageTypeStats(short messageType) {
/*  84 */     Double stat = this.incomingMessageTypeMap.get(Short.valueOf(messageType));
/*  85 */     if (stat != null) {
/*  86 */       return stat.doubleValue();
/*     */     }
/*  88 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOutgoingMessageTypeStats(short messageType) {
/*  93 */     Double stat = this.outgoingMessageTypeMap.get(Short.valueOf(messageType));
/*  94 */     if (stat != null) {
/*  95 */       return stat.doubleValue();
/*     */     }
/*  97 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public ByteBuffer toBuffer(Message message) {
/*     */     ByteBuffer outBuffer;
/* 102 */     if (this.logMessageStats) {
/* 103 */       Double value = this.outgoingMessageTypeMap.get(Short.valueOf(message.getMessageType()));
/* 104 */       this.outgoingMessageTypeMap.put(Short.valueOf(message.getMessageType()), Double.valueOf(((value != null) ? value.doubleValue() : 0.0D) + 1.0D));
/*     */     } 
/*     */     
/* 107 */     int size = message.getSerializedSize();
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       try {
/* 113 */         outBuffer = append(message, size); break;
/* 114 */       } catch (BufferOverflowException e) {
/*     */         
/* 116 */         size *= 2;
/* 117 */         if (size <= 0) {
/* 118 */           size = 16;
/*     */         }
/* 120 */         LOG.warn("Buffer overflow retry with larger buffer for message! Please check this class for miscalculation in size: " + message + ", buffersize:" + this.bufferSize + ", ID_SIZE: " + '\002' + ",size was: " + size);
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     this.bufferManager.prepareForWrite(outBuffer);
/* 125 */     return outBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuffer getWriteBuffer(int size) {
/* 130 */     return this.bufferManager.getForWrite(size);
/*     */   }
/*     */   
/*     */   protected ByteBuffer append(Message message, int size) {
/* 134 */     ByteBuffer buffer = getWriteBuffer(size + 2);
/* 135 */     buffer.putShort(message.getMessageType());
/* 136 */     message.serialize(buffer);
/*     */     
/* 138 */     return buffer;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) throws UnknownMessageTypeException {
/* 142 */     short msgID = buffer.getShort();
/*     */     
/* 144 */     if (this.logMessageStats) {
/* 145 */       Double value = this.incomingMessageTypeMap.remove(Short.valueOf(msgID));
/* 146 */       this.incomingMessageTypeMap.put(Short.valueOf(msgID), Double.valueOf(((value != null) ? value.doubleValue() : 0.0D) + 1.0D));
/*     */     } 
/*     */     
/* 149 */     switch (msgID) {
/*     */       case -2:
/* 151 */         return new LogoutMessage(buffer);
/*     */       case -3:
/* 153 */         return KeepAliveMessage.INSTANCE;
/*     */     } 
/*     */     
/* 156 */     Message ret = toCustomMessage(msgID, buffer);
/*     */     
/* 158 */     if (ret != null) {
/* 159 */       return ret;
/*     */     }
/*     */ 
/*     */     
/* 163 */     throw new UnknownMessageTypeException(msgID, buffer);
/*     */   }
/*     */   
/*     */   protected Message toCustomMessage(short msgID, ByteBuffer buffer) {
/* 167 */     Message prototype = (Message)this.prototypes.get(msgID);
/* 168 */     if (prototype != null) {
/* 169 */       return prototype.toMessage(buffer);
/*     */     }
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\BaseMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */