/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PointerPosMessage
/*    */   implements Message
/*    */ {
/* 15 */   public static final Logger LOGGER = Logger.getLogger(PointerPosMessage.class);
/*    */   private WorldCoordinate pointingCoordinate;
/*    */   private static final short TYPE_ID = 67;
/* 18 */   private static final int MSG_SIZE = MessageUtils.getSizeWorldCoordinate();
/*    */ 
/*    */   
/*    */   public PointerPosMessage(WorldCoordinate pointingCoordinate) {
/* 22 */     this.pointingCoordinate = pointingCoordinate;
/*    */   }
/*    */ 
/*    */   
/*    */   public PointerPosMessage() {}
/*    */   
/*    */   public PointerPosMessage(ByteBuffer buffer) {
/* 29 */     this.pointingCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 33 */     return MSG_SIZE;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 67;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.pointingCoordinate);
/* 42 */     return buffer;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 46 */     return new PointerPosMessage(buffer);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     return getClass().getName() + this.pointingCoordinate + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getPointingCoordinate() {
/* 55 */     return this.pointingCoordinate;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PointerPosMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */