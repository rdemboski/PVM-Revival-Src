/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface MessageProcessor
/*    */ {
/* 17 */   public static final Logger LOGGER = Logger.getLogger(MessageProcessor.class.getName());
/*    */   
/*    */   void process(Message paramMessage, GameIOHandler paramGameIOHandler, Map<Integer, CreatureData> paramMap1, Map<Integer, CreatureData> paramMap2, Map<Integer, CreatureData> paramMap3, Map<Integer, CreatureData> paramMap4);
/*    */   
/*    */   short getMessageType();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\MessageProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */