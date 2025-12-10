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
/*    */ public class ClientName
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private Integer32 gmtaginstance;
/*    */   private StringDatatype clientname;
/*    */   
/*    */   public ClientName() {}
/*    */   
/*    */   public ClientName(Integer32 clientid, Integer32 gmtaginstance, StringDatatype clientname) {
/* 28 */     this.clientid = clientid;
/* 29 */     this.gmtaginstance = gmtaginstance;
/* 30 */     this.clientname = clientname;
/*    */   }
/*    */   
/*    */   public ClientName(ByteBuffer byteBuffer) {
/* 34 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 35 */     this.gmtaginstance = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.clientname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 46 */     return new ClientName(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return this.clientid.getSizeInBytes() + this.gmtaginstance.getSizeInBytes() + this.clientname.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 58 */     this.clientid.toByteBuffer(buffer);
/* 59 */     this.gmtaginstance.toByteBuffer(buffer);
/* 60 */     this.clientname.toByteBuffer(buffer);
/* 61 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 65 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer32 getGmtaginstance() {
/* 69 */     return this.gmtaginstance;
/*    */   }
/*    */   
/*    */   public StringDatatype getClientname() {
/* 73 */     return this.clientname;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\ClientName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */