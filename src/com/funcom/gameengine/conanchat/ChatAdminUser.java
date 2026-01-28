/*    */ package com.funcom.gameengine.conanchat;
import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer40;
/*    */ import com.funcom.gameengine.conanchat.datatypes.MapDatatype;
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmAccountCreateNew;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmAccountDelete;
import com.funcom.gameengine.conanchat.packets2.AdmAccountDisable;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmAccountSetCookie;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmGroupCreate;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmGroupJoin;
/*    */ import com.funcom.gameengine.conanchat.packets2.AdmGroupPart;
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ import java.net.InetAddress;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ChatAdminUser extends DefaultChatUser {
/*    */   private static final int ADMIN_CLIENT_ID = 0;
/*    */   
/*    */   public ChatAdminUser() {
/* 20 */     super(0L, 0L);
/*    */   }
/*    */   private static final long ADMIN_CLIENT_COOKIE = 0L;
/*    */   public void createGroup(long groupId, String groupName, long flags) {
/* 24 */     checkLogged();
/* 25 */     queueMessage((ChatMessage)new AdmGroupCreate(new Integer40(groupId), new StringDatatype(groupName), new Integer32(flags)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void joinGroup(long groupId, long clientId) {
/* 33 */     queueMessage((ChatMessage)new AdmGroupJoin(new Integer40(groupId), new Integer32(clientId)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void partGroup(long groupId, long clientId) {
/* 40 */     queueMessage((ChatMessage)new AdmGroupPart(new Integer40(groupId), new Integer32(clientId)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void createUser(long fid, long clientId, String fidNickname, String clientNickname) {
/* 47 */     queueMessage((ChatMessage)new AdmAccountCreateNew(new Integer32(fid), new Integer32(clientId), new StringDatatype(fidNickname), new StringDatatype(clientNickname), new MapDatatype()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCookie(long clientId, long cookie, long ipAddress, String nickname, String eMail) {
/* 55 */     checkLogged();
/*    */     
/* 57 */     queueMessage((ChatMessage)new AdmAccountSetCookie(new Integer32(clientId), new Integer32(cookie), new Integer32(ipAddress), new StringDatatype(nickname), new StringDatatype(eMail)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCookie(long clientId, long cookie, InetAddress ipAddress, String nickname, String eMail) {
/* 67 */     checkLogged();
/*    */     
/* 69 */     byte[] addressArray = ipAddress.getAddress();
/* 70 */     Integer32 i = new Integer32(ByteBuffer.wrap(addressArray), Endianess.BIG_ENDIAN);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     setCookie(clientId, cookie, i.getValue(), nickname, eMail);
/*    */   }
/*    */   
/*    */   public void disableClient(long clientId) {
/* 82 */     queueMessage((ChatMessage)new AdmAccountDisable(new Integer32(clientId)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disconnectClient(long clientId) {
/* 88 */     queueMessage((ChatMessage)new AdmAccountDelete(new Integer32(clientId)));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\ChatAdminUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */