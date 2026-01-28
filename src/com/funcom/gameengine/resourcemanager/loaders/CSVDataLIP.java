/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class CSVDataLIP
/*     */   extends CSVData
/*     */ {
/*  19 */   private List<String[]> data = null;
/*  20 */   private List<Integer> ids = new ArrayList<Integer>();
/*  21 */   private List<String> paths = new ArrayList<String>();
/*  22 */   private List<ByteBuffer> bytes = new ArrayList<ByteBuffer>();
/*     */   
/*     */   private static boolean CUSTOM_ITERATOR = false;
/*     */   
/*     */   private static boolean DATA_ARRAY = false;
/*     */   private static boolean LOAD_FROM_MEMORY = true;
/*     */   private static boolean USE_MAP = true;
/*  29 */   private static Map<String, List<String[]>> datas = new HashMap<String, List<String[]>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addFile(List<File> files) throws Exception {
/*  36 */     if (LOAD_FROM_MEMORY) {
/*     */       
/*  38 */       for (int f = 0; f < files.size(); f++) {
/*  39 */         String path = ((File)files.get(f)).getPath();
/*  40 */         path.intern();
/*     */         
/*  42 */         String str = ((File)files.get(f)).getName();
/*  43 */         str = str.replace(".", "_");
/*  44 */         str = str.replace("-", "_");
/*     */         
/*  46 */         String strName = str;
/*  47 */         str = str + ".lip";
/*  48 */         str = ((File)files.get(f)).getParent() + "/" + str;
/*     */         
/*  50 */         if (datas.containsKey(strName)) {
/*  51 */           this.data = datas.get(strName);
/*     */         } else {
/*     */           
/*  54 */           int id = 0;
/*  55 */           if (id == 0) {
/*  56 */             FileInputStream dataStream = null;
/*     */             try {
/*  58 */               dataStream = new FileInputStream(str);
/*     */             }
/*  60 */             catch (FileNotFoundException e) {
/*  61 */               return false;
/*     */             } 
/*     */             
/*  64 */             ByteBuffer buf = ByteBuffer.allocateDirect(dataStream.available());
/*  65 */             dataStream.getChannel().read(buf, 0L);
/*  66 */             id = load(path, buf);
/*  67 */             if (id == -1 || id == 0) {
/*  68 */               throw new IOException("Couldn't load LIP file '" + path + "'.");
/*     */             }
/*  70 */             this.ids.add(Integer.valueOf(id));
/*  71 */             this.bytes.add(buf);
/*  72 */             this.paths.add(path);
/*     */           } 
/*     */           
/*  75 */           if (!CUSTOM_ITERATOR) {
/*  76 */             populateData(id);
/*     */           }
/*  78 */           datas.put(strName, this.data);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*  85 */         for (int f = 0; f < files.size(); f++) {
/*  86 */           String path = ((File)files.get(f)).getPath();
/*     */           
/*  88 */           int id = load(path);
/*  89 */           if (id != -1 && id != 0) {
/*  90 */             this.ids.add(Integer.valueOf(id));
/*  91 */             this.paths.add(path);
/*     */             
/*  93 */             if (!CUSTOM_ITERATOR) {
/*  94 */               populateData(id);
/*     */             }
/*     */           } else {
/*     */             
/*  98 */             return false;
/*     */           }
/*     */         
/*     */         } 
/* 102 */       } catch (Exception e) {
/* 103 */         LoadingManager.INSTANCE.sendCrash(e);
/*     */       } 
/*     */     } 
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   private void populateData(int id) {
/* 110 */     if (this.data == null) {
/* 111 */       this.data = (List)new ArrayList<String>();
/*     */     }
/* 113 */     Map<Integer, String> strings = null;
/* 114 */     if (USE_MAP) {
/* 115 */       strings = new HashMap<Integer, String>();
/*     */     }
/*     */     
/* 118 */     int rows = getNumRows(id);
/* 119 */     int cols = getNumCols(id);
/*     */     
/* 121 */     String[] record = null;
/* 122 */     if (DATA_ARRAY) {
/* 123 */       for (int row = 0; row < rows; row++) {
/* 124 */         record = getDataRow(id, row);
/* 125 */         add(record);
/*     */       }
/*     */     
/* 128 */     } else if (USE_MAP) {
/*     */       
/* 130 */       int offset = 0;
/*     */       
/* 132 */       for (int row = 0; row < rows; row++) {
/* 133 */         record = new String[cols];
/* 134 */         for (int col = 0; col < cols; col++) {
/* 135 */           offset = getOffset(id, row, col);
/* 136 */           if (strings.containsKey(Integer.valueOf(offset))) {
/* 137 */             record[col] = strings.get(Integer.valueOf(offset));
/*     */           } else {
/* 139 */             String temp = getData(id, row, col);
/* 140 */             strings.put(Integer.valueOf(offset), temp);
/* 141 */             record[col] = temp;
/*     */           } 
/*     */         } 
/* 144 */         add(record);
/*     */       } 
/* 146 */       strings.clear();
/*     */     } else {
/*     */       
/* 149 */       for (int row = 0; row < rows; row++) {
/* 150 */         record = new String[cols];
/* 151 */         for (int col = 0; col < cols; col++) {
/* 152 */           record[col] = getData(id, row, col);
/*     */         }
/* 154 */         add(record);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   private static int totalStringCreationTime = 0;
/* 164 */   private static int numStrings = 0;
/*     */   
/*     */   public void add(String[] record) {
/* 167 */     this.data.add(record);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String[]> iterator() {
/* 172 */     if (CUSTOM_ITERATOR)
/* 173 */       return new LIPIterator(this.ids); 
/* 174 */     return (Iterator)this.data.iterator();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 178 */     return this.data.isEmpty();
/*     */   }
/*     */ 
/*     */   
            public String toString() {
                return "CSVData{dataRows=" + ((this.data == null) ? null : String.valueOf(this.data.size())) + '}';
            }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void LoadDataInJava() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int isLoaded(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int load(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int load(String paramString, ByteBuffer paramByteBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native String getData(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native String[] getDataRow(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int getSize(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int getNumRows(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int getNumCols(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int getOffset(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class LIPIterator
/*     */     implements Iterator<String[]>
/*     */   {
/* 263 */     List<IdSet> sets = new ArrayList<IdSet>();
/* 264 */     List<Integer> ids = null;
/* 265 */     int current = 0;
/*     */     
/*     */     public LIPIterator(List<Integer> ids) {
/* 268 */       this.ids = ids;
/*     */       
/* 270 */       for (int i = 0; i < ids.size(); i++) {
/* 271 */         int id = ((Integer)ids.get(i)).intValue();
/* 272 */         if (id != -1) {
/* 273 */           this.sets.add(new IdSet(id));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     IdSet getCurrent() {
/* 279 */       if (this.current >= this.sets.size() || this.current < 0)
/* 280 */         return null; 
/* 281 */       return this.sets.get(this.current);
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 285 */       if (this.current < this.sets.size()) {
/* 286 */         IdSet set = getCurrent();
/* 287 */         while (set != null && !set.hasNext()) {
/* 288 */           this.current++;
/* 289 */           set = getCurrent();
/*     */         } 
/* 291 */         return (set != null);
/*     */       } 
/* 293 */       return false;
/*     */     }
/*     */     
/*     */     public String[] next() {
/* 297 */       IdSet set = getCurrent();
/* 298 */       if (set != null)
/* 299 */         return set.next(); 
/* 300 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {}
/*     */     
/*     */     class IdSet
/*     */     {
/* 308 */       int id = -1;
/* 309 */       int row = 0;
/* 310 */       int col = 0;
/* 311 */       int rows = 0;
/* 312 */       int cols = 0;
/*     */       
/*     */       public IdSet(int id) {
/* 315 */         this.id = id;
/* 316 */         if (id != -1) {
/* 317 */           this.rows = CSVDataLIP.this.getNumRows(id);
/* 318 */           this.cols = CSVDataLIP.this.getNumCols(id);
/*     */         } 
/*     */       }
/*     */       
/*     */       public String[] next() {
/* 323 */         String[] record = null;
/* 324 */         if (CSVDataLIP.DATA_ARRAY) {
/* 325 */           record = CSVDataLIP.this.getDataRow(this.id, this.row);
/*     */         } else {
/*     */           
/* 328 */           record = new String[this.cols];
/* 329 */           for (int col = 0; col < this.cols; col++) {
/* 330 */             record[col] = CSVDataLIP.this.getData(this.id, this.row, col);
/*     */           }
/*     */         } 
/* 333 */         this.row++;
/* 334 */         return record;
/*     */       }
/*     */       
/*     */       boolean hasNext() {
/* 338 */         return (this.row < this.rows);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\CSVDataLIP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */