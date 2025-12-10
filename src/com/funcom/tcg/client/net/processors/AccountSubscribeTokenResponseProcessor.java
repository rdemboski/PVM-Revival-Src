/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.AccountSubscribeTokenResponseMessage;
/*    */ import java.awt.Desktop;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URI;
/*    */ import java.net.URLEncoder;
/*    */ import java.nio.charset.Charset;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class AccountSubscribeTokenResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     AccountSubscribeTokenResponseMessage responseMessage = (AccountSubscribeTokenResponseMessage)message;
/*    */     
/* 25 */     String urlPatter = MainGameState.getInstance().getLocalizedText(getClass(), "accountsubscribe.actualweblink", new String[0]);
/* 26 */     String encodedToken = encodeToken(responseMessage);
/* 27 */     String fullUrlStr = MessageFormat.format(urlPatter, new Object[] { encodedToken });
/*    */     try {
/* 29 */       Desktop.getDesktop().browse(URI.create(fullUrlStr));
/* 30 */     } catch (IOException e) {
/* 31 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private String encodeToken(AccountSubscribeTokenResponseMessage responseMessage) {
/* 36 */     String encodedToken = "";
/*    */     try {
/* 38 */       encodedToken = URLEncoder.encode(responseMessage.getToken(), "UTF-8");
/* 39 */     } catch (UnsupportedEncodingException e) {
/* 40 */       e.printStackTrace();
/*    */       try {
/* 42 */         encodedToken = URLEncoder.encode(responseMessage.getToken(), Charset.defaultCharset().name());
/* 43 */       } catch (UnsupportedEncodingException e1) {
/* 44 */         e1.printStackTrace();
/*    */       } 
/*    */     } 
/* 47 */     return encodedToken;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 51 */     return 17;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\AccountSubscribeTokenResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */