/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
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
/*    */ public class NameLookupResult
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private StringDatatype clientname;
/*    */   
/*    */   public NameLookupResult() {}
/*    */   
/*    */   public NameLookupResult(Integer32 clientid, StringDatatype clientname) {
/* 27 */     this.clientid = clientid;
/* 28 */     this.clientname = clientname;
/*    */   }
/*    */   
/*    */   public NameLookupResult(ByteBuffer byteBuffer) {
/* 32 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 33 */     this.clientname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 21;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 43 */     return new NameLookupResult(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return this.clientid.getSizeInBytes() + this.clientname.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     this.clientid.toByteBuffer(buffer);
/* 55 */     this.clientname.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 60 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public StringDatatype getClientname() {
/* 64 */     return this.clientname;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\NameLookupResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */