/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CSVData implements Iterable<String[]> {
/*  8 */   private List<String[]> data = (List)new ArrayList<String>();
/*    */   
/*    */   public void add(String[] record) {
/* 11 */     this.data.add(record);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<String[]> iterator() {
/* 16 */     return (Iterator)this.data.iterator();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 20 */     return this.data.isEmpty();
/*    */   }
/*    */ 
/*    */   
            public String toString() {
                return "CSVData{dataRows=" + ((this.data == null) ? null : String.valueOf(this.data.size())) + '}';
            }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\CSVData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */