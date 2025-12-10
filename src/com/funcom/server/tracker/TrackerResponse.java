/*    */ package com.funcom.server.tracker;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrackerResponse
/*    */ {
/*    */   private String name;
/*    */   private InetAddress addr;
/*    */   
/*    */   public TrackerResponse(String name, InetAddress addr) {
/* 13 */     this.name = name;
/* 14 */     this.addr = addr;
/*    */   }
/*    */   
/*    */   public InetAddress getAddr() {
/* 18 */     return this.addr;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 26 */     return this.name + "/" + this.addr.getHostAddress();
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     if (this == o) {
/* 31 */       return true;
/*    */     }
/* 33 */     if (o == null || getClass() != o.getClass()) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     TrackerResponse that = (TrackerResponse)o;
/*    */     
/* 39 */     if ((this.addr != null) ? !this.addr.equals(that.addr) : (that.addr != null)) {
/* 40 */       return false;
/*    */     }
/* 42 */     if ((this.name != null) ? !this.name.equals(that.name) : (that.name != null)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     int result = (this.name != null) ? this.name.hashCode() : 0;
/* 52 */     result = 31 * result + ((this.addr != null) ? this.addr.hashCode() : 0);
/* 53 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\tracker\TrackerResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */