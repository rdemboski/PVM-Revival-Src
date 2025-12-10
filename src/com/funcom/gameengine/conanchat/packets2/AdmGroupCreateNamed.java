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
/*    */ public class AdmGroupCreateNamed
/*    */   implements ChatMessage
/*    */ {
/*    */   private StringDatatype groupname;
/*    */   private Integer32 groupflags;
/*    */   
/*    */   public AdmGroupCreateNamed() {}
/*    */   
/*    */   public AdmGroupCreateNamed(StringDatatype groupname, Integer32 groupflags) {
/* 27 */     this.groupname = groupname;
/* 28 */     this.groupflags = groupflags;
/*    */   }
/*    */   
/*    */   public AdmGroupCreateNamed(ByteBuffer byteBuffer) {
/* 32 */     this.groupname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 33 */     this.groupflags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 1022;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 43 */     return new AdmGroupCreateNamed(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return this.groupname.getSizeInBytes() + this.groupflags.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     this.groupname.toByteBuffer(buffer);
/* 55 */     this.groupflags.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public StringDatatype getGroupname() {
/* 60 */     return this.groupname;
/*    */   }
/*    */   
/*    */   public Integer32 getGroupflags() {
/* 64 */     return this.groupflags;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmGroupCreateNamed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */