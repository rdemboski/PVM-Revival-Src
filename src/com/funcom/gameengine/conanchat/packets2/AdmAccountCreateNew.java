/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.MapDatatype;
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
/*    */ public class AdmAccountCreateNew
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 fidid;
/*    */   private Integer32 clientid;
/*    */   private StringDatatype fidnick;
/*    */   private StringDatatype clientnick;
/*    */   private MapDatatype extraDict;
/*    */   
/*    */   public AdmAccountCreateNew() {}
/*    */   
/*    */   public AdmAccountCreateNew(Integer32 fidid, Integer32 clientid, StringDatatype fidnick, StringDatatype clientnick, MapDatatype extraDict) {
/* 31 */     this.fidid = fidid;
/* 32 */     this.clientid = clientid;
/* 33 */     this.fidnick = fidnick;
/* 34 */     this.clientnick = clientnick;
/* 35 */     this.extraDict = extraDict;
/*    */   }
/*    */   
/*    */   public AdmAccountCreateNew(ByteBuffer byteBuffer) {
/* 39 */     this.fidid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.fidnick = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 42 */     this.clientnick = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 43 */     this.extraDict = new MapDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 48 */     return 1002;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 53 */     return new AdmAccountCreateNew(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 58 */     return this.fidid.getSizeInBytes() + this.clientid.getSizeInBytes() + this.fidnick.getSizeInBytes() + this.clientnick.getSizeInBytes() + this.extraDict.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 67 */     this.fidid.toByteBuffer(buffer);
/* 68 */     this.clientid.toByteBuffer(buffer);
/* 69 */     this.fidnick.toByteBuffer(buffer);
/* 70 */     this.clientnick.toByteBuffer(buffer);
/* 71 */     this.extraDict.toByteBuffer(buffer);
/* 72 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getFidid() {
/* 76 */     return this.fidid;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 80 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public StringDatatype getFidnick() {
/* 84 */     return this.fidnick;
/*    */   }
/*    */   
/*    */   public StringDatatype getClientnick() {
/* 88 */     return this.clientnick;
/*    */   }
/*    */   
/*    */   public MapDatatype getExtraDict() {
/* 92 */     return this.extraDict;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmAccountCreateNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */