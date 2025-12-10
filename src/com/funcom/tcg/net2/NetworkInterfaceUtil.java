/*    */ package com.funcom.tcg.net2;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.net.SocketException;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public class NetworkInterfaceUtil
/*    */ {
/*    */   public static InetAddress getRealInetAddressForLocalHost() {
/* 11 */     InetAddress inetAddress = null;
/*    */     try {
/* 13 */       Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
/* 14 */       while (interfaces.hasMoreElements()) {
/* 15 */         NetworkInterface networkInterface = interfaces.nextElement();
/* 16 */         Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
/* 17 */         while (inetAddressEnumeration.hasMoreElements()) {
/* 18 */           InetAddress tmpInetAddress = inetAddressEnumeration.nextElement();
/* 19 */           if (tmpInetAddress instanceof java.net.Inet4Address && !tmpInetAddress.getHostName().equals("127.0.0.1") && !tmpInetAddress.getHostName().equals("localhost")) {
/* 20 */             inetAddress = tmpInetAddress;
/*    */           }
/*    */         }
/*    */       
/*    */       } 
/* 25 */     } catch (SocketException e) {
/* 26 */       e.printStackTrace();
/*    */     } 
/* 28 */     return inetAddress;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\NetworkInterfaceUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */