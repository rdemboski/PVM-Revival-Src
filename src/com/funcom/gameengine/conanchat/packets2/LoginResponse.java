/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class LoginResponse
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 protocolVersion;
/*    */   private Integer32 clientid;
/*    */   private Integer32 cookie;
/*    */   
/*    */   public LoginResponse() {}
/*    */   
/*    */   public LoginResponse(Integer32 protocolVersion, Integer32 clientid, Integer32 cookie) {
/* 27 */     this.protocolVersion = protocolVersion;
/* 28 */     this.clientid = clientid;
/* 29 */     this.cookie = cookie;
/*    */   }
/*    */   
/*    */   public LoginResponse(ByteBuffer byteBuffer) {
/* 33 */     this.protocolVersion = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 34 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 35 */     this.cookie = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 45 */     return new LoginResponse(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 50 */     return this.protocolVersion.getSizeInBytes() + this.clientid.getSizeInBytes() + this.cookie.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 57 */     this.protocolVersion.toByteBuffer(buffer);
/* 58 */     this.clientid.toByteBuffer(buffer);
/* 59 */     this.cookie.toByteBuffer(buffer);
/* 60 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getProtocolVersion() {
/* 64 */     return this.protocolVersion;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 68 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer32 getCookie() {
/* 72 */     return this.cookie;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\LoginResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */