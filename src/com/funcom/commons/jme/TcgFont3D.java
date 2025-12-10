/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jmex.font3d.Font3D;
/*    */ import java.awt.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcgFont3D
/*    */ {
/*    */   private static Font3D font;
/*    */   
/*    */   public static Font3D getFont() {
/* 14 */     if (font == null) {
/* 15 */       font = new Font3D(new Font("Tahoma", 1, 1), 9.999999747378752E-5D, false, true, false);
/* 16 */       font.getRenderNode().unlockMeshes();
/*    */     } 
/* 18 */     return font;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\TcgFont3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */