/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.MapDatatype;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Forward
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private MapDatatype userDict;
/*    */   
/*    */   public Forward() {}
/*    */   
/*    */   public Forward(Integer32 clientid, MapDatatype userDict) {
/* 27 */     this.clientid = clientid;
/* 28 */     this.userDict = userDict;
/*    */   }
/*    */   
/*    */   public Forward(ByteBuffer byteBuffer) {
/* 32 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 33 */     this.userDict = new MapDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 110;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 43 */     return new Forward(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return this.clientid.getSizeInBytes() + this.userDict.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     this.clientid.toByteBuffer(buffer);
/* 55 */     this.userDict.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 60 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public MapDatatype getUserDict() {
/* 64 */     return this.userDict;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\Forward.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */