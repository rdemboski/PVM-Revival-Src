/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatureVisualDescription
/*    */ {
/*    */   private String id;
/*    */   private String icon;
/*    */   private String idleDfx;
/*    */   private String walkDfx;
/*    */   private String deathDfx;
/*    */   private String interactDfx;
/*    */   private String alwaysOnDfx;
/*    */   private String xmlDocumentPath;
/*    */   private boolean hasShadow;
/*    */   
/*    */   public String getId() {
/* 21 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 25 */     return this.icon;
/*    */   }
/*    */   
/*    */   public String getIdleDfx() {
/* 29 */     return this.idleDfx;
/*    */   }
/*    */   
/*    */   public String getWalkDfx() {
/* 33 */     return this.walkDfx;
/*    */   }
/*    */   
/*    */   public String getDeathDfx() {
/* 37 */     return this.deathDfx;
/*    */   }
/*    */   
/*    */   public String getInteractDfx() {
/* 41 */     return this.interactDfx;
/*    */   }
/*    */   
/*    */   public String getAlwaysOnDfx() {
/* 45 */     return this.alwaysOnDfx;
/*    */   }
/*    */   
/*    */   public String getXmlDocumentPath() {
/* 49 */     return this.xmlDocumentPath;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 53 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void setIcon(String icon) {
/* 57 */     this.icon = icon;
/*    */   }
/*    */   
/*    */   public void setIdleDfx(String idleDfx) {
/* 61 */     this.idleDfx = idleDfx;
/*    */   }
/*    */   
/*    */   public void setWalkDfx(String walkDfx) {
/* 65 */     this.walkDfx = walkDfx;
/*    */   }
/*    */   
/*    */   public void setDeathDfx(String deathDfx) {
/* 69 */     this.deathDfx = deathDfx;
/*    */   }
/*    */   
/*    */   public void setInteractDfx(String interactDfx) {
/* 73 */     this.interactDfx = interactDfx;
/*    */   }
/*    */   
/*    */   public void setAlwaysOnDfx(String alwaysOnDfx) {
/* 77 */     this.alwaysOnDfx = alwaysOnDfx;
/*    */   }
/*    */   
/*    */   public void setXmlDocumentPath(String xmlDocumentPath) {
/* 81 */     this.xmlDocumentPath = xmlDocumentPath;
/*    */   }
/*    */   
/*    */   public void setShadow(boolean hasShadow) {
/* 85 */     this.hasShadow = hasShadow;
/*    */   }
/*    */   
/*    */   public boolean hasShadow() {
/* 89 */     return this.hasShadow;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 93 */     return "CreatureVisualDescription{id='" + this.id + '\'' + ", icon='" + this.icon + '\'' + ", idleDfx='" + this.idleDfx + '\'' + ", walkDfx='" + this.walkDfx + '\'' + ", deathDfx='" + this.deathDfx + '\'' + ", interactDfx='" + this.interactDfx + '\'' + ", alwaysOnDfx='" + this.alwaysOnDfx + '\'' + ", xmlDocumentPath='" + this.xmlDocumentPath + '\'' + ", hasShadow=" + this.hasShadow + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\CreatureVisualDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */