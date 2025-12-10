/*     */ package com.funcom.gameengine.conanchat;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer16;
/*     */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageFactory;
/*     */ import com.funcom.server.common.UnknownMessageTypeException;
/*     */ import com.funcom.server.common.util.ReflectionUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class ConanChatMessageFactory
/*     */   implements MessageFactory
/*     */ {
/*  22 */   private static final Logger LOGGER = Logger.getLogger(ConanChatMessageFactory.class.getName());
/*     */   private List<ChatMessage> messagePrototypes;
/*     */   
/*     */   public ConanChatMessageFactory() {
/*  26 */     buildPrototypes();
/*     */   }
/*     */   
/*     */   private void buildPrototypes() {
/*  30 */     this.messagePrototypes = new LinkedList<ChatMessage>();
/*  31 */     for (Class<? extends ChatMessage> aClass : getMessageClasses()) {
/*     */       try {
/*  33 */         ChatMessage message = aClass.newInstance();
/*  34 */         this.messagePrototypes.add(message);
/*  35 */       } catch (InstantiationException e) {
/*  36 */         throw new IllegalStateException(e);
/*  37 */       } catch (IllegalAccessException e) {
/*  38 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatMessage toMessage(ByteBuffer buffer) throws UnknownMessageTypeException {
/*  45 */     short messageId = (short)(new Integer16(buffer, Endianess.BIG_ENDIAN)).getIntValue();
/*  46 */     int messageSize = (new Integer16(buffer, Endianess.BIG_ENDIAN)).getIntValue();
/*     */     
/*  48 */     for (ChatMessage messagePrototype : this.messagePrototypes) {
/*  49 */       if (messagePrototype.getMessageType() == messageId) {
/*  50 */         if (LOGGER.isEnabledFor((Priority)Level.DEBUG) && messageId == 60) {
/*  51 */           LOGGER.debug(String.format("Message ID '%s', buffer: %s", new Object[] { Short.valueOf(messageId), buffer.toString() }));
/*     */         }
/*  53 */         return (ChatMessage)messagePrototype.toMessage(buffer);
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     throw new UnknownMessageTypeException(messageId, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer toBuffer(Message message) {
/*  63 */     throw new IllegalStateException("SHOULD NOT BE USED IN NEAR FUTURE!");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getIncomingMessageTypeStats(short messageType) {
/*  68 */     throw new IllegalStateException("SHOULD NOT BE USED IN NEAR FUTURE!");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOutgoingMessageTypeStats(short messageType) {
/*  73 */     throw new IllegalStateException("SHOULD NOT BE USED IN NEAR FUTURE!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLogMessageStats(boolean logMessageStats) {
/*  78 */     throw new IllegalStateException("SHOULD NOT BE USED IN NEAR FUTURE!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearMessageCaches() {
/*  83 */     throw new IllegalStateException("SHOULD NOT BE USED IN NEAR FUTURE!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Class<? extends ChatMessage>> getMessageClasses() {
/*     */     try {
/*  91 */       InputStream messageClassesProps = ConanChatMessageFactory.class.getResourceAsStream("/com/funcom/gameengine/conanchat/conanchat-messages.properties");
/*  92 */       List<Class<? extends ChatMessage>> messageClasses = ReflectionUtils.getClasses(ChatMessage.class, messageClassesProps);
/*     */       try {
/*  94 */         messageClassesProps.close();
/*  95 */       } catch (IOException e) {}
/*     */ 
/*     */ 
/*     */       
/*  99 */       return messageClasses;
/* 100 */     } catch (ClassNotFoundException e) {
/* 101 */       throw new RuntimeException("Error getting message parser list", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\ConanChatMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */