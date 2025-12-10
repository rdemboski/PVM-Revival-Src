/*     */ package com.funcom.gameengine.debug;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ 
/*     */ public enum TimeStamper {
/*     */   INSTANCE;
/*     */   
/*     */   private ArrayList<ArrayList<Long>> data;
/*     */   private boolean stampingStarted;
/*     */   private Double[] dataDebug;
/*     */   private ArrayList<ArrayList<stEvent>> dataEvents;
/*     */   private AtomicIntegerArray dataSizes;
/*     */   private long[] dataTemp;
/*     */   private Map<String, Integer> dataIds;
/*     */   TimeStamper() {
/*  22 */     this.MAX_NUMBER_OF_STAMPS = 100;
/*     */     
/*  24 */     this.stampingEnabled = false;
/*  25 */     this.stampingStarted = false;
/*  26 */     this.data = new ArrayList<ArrayList<Long>>();
/*  27 */     this.dataIds = new HashMap<String, Integer>(this.MAX_NUMBER_OF_STAMPS);
/*     */     
/*  29 */     this.dataTemp = new long[this.MAX_NUMBER_OF_STAMPS];
/*  30 */     this.dataSizes = new AtomicIntegerArray(this.MAX_NUMBER_OF_STAMPS);
/*  31 */     this.dataEvents = new ArrayList<ArrayList<stEvent>>(this.MAX_NUMBER_OF_STAMPS);
/*     */     
/*  33 */     this.dataDebug = new Double[this.MAX_NUMBER_OF_STAMPS];
/*     */   } private volatile boolean stampingEnabled; private int MAX_NUMBER_OF_STAMPS;
/*     */   static {
/*     */     try {
/*  37 */       INSTANCE.setName("FPS");
/*  38 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public void update() {
/*  45 */     if (this.stampingStarted && !this.stampingEnabled) {
/*  46 */       this.stampingStarted = false;
/*  47 */     } else if (!this.stampingStarted && this.stampingEnabled) {
/*  48 */       this.stampingStarted = true;
/*     */     } 
/*     */   }
/*     */   public void enable(boolean b) {
/*  52 */     boolean printing = false;
/*  53 */     if (this.stampingEnabled && !b)
/*  54 */       printing = true; 
/*  55 */     this.stampingEnabled = b;
/*  56 */     if (printing) {
/*  57 */       printToFile("");
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  62 */     this.data.clear();
/*  63 */     this.dataIds.clear();
/*  64 */     this.dataEvents = new ArrayList<ArrayList<stEvent>>(this.MAX_NUMBER_OF_STAMPS);
/*  65 */     for (int i = 0; i < this.MAX_NUMBER_OF_STAMPS; i++) {
/*  66 */       this.dataTemp[i] = 0L;
/*  67 */       this.dataSizes.set(i, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setName(String str) throws Exception {
/*  72 */     if (this.data.size() + 1 < this.MAX_NUMBER_OF_STAMPS) {
/*  73 */       this.data.add(new ArrayList<Long>());
/*  74 */       this.dataEvents.add(new ArrayList<stEvent>());
/*  75 */       this.dataIds.put(str, Integer.valueOf(this.data.size() - 1));
/*  76 */       this.dataTemp[this.data.size() - 1] = 0L;
/*     */     } else {
/*     */       
/*  79 */       throw new Exception("Reached maximum number of stamps (" + this.MAX_NUMBER_OF_STAMPS + ").");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stamp(String name) {
/*  84 */     if (!this.stampingStarted) {
/*     */       return;
/*     */     }
/*  87 */     if (!this.dataIds.containsKey(name)) {
/*     */       try {
/*  89 */         setName(name);
/*     */       }
/*  91 */       catch (Exception e) {
/*  92 */         LoadingManager.INSTANCE.sendCrash(e);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*  97 */     int id = ((Integer)this.dataIds.get(name)).intValue();
/*  98 */     if (id >= this.data.size()) {
/*  99 */       this.dataTemp[id] = 0L;
/* 100 */       this.data.add(new ArrayList<Long>());
/*     */     } 
/*     */     
/* 103 */     if (this.dataTemp[id] == 0L) {
/* 104 */       this.dataTemp[id] = System.nanoTime();
/*     */     } else {
/*     */       
/* 107 */       ((ArrayList<Long>)this.data.get(id)).add(Long.valueOf(System.nanoTime() - this.dataTemp[id]));
/* 108 */       this.dataTemp[id] = 0L;
/* 109 */       this.dataSizes.set(id, ((ArrayList)this.data.get(id)).size());
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getId(String name) {
/* 114 */     if (this.dataIds.containsKey(name)) {
/* 115 */       return ((Integer)this.dataIds.get(name)).intValue();
/*     */     }
/* 117 */     return -1;
/*     */   }
/*     */   
/*     */   public void event(String name, String event) {
/* 121 */     if (!this.stampingStarted) {
/*     */       return;
/*     */     }
/* 124 */     int id = 0;
/* 125 */     synchronized (this.dataIds) {
/* 126 */       if (!this.dataIds.containsKey(name))
/*     */         return; 
/* 128 */       id = ((Integer)this.dataIds.get(name)).intValue();
/*     */     } 
/* 130 */     event(id, event);
/*     */   }
/*     */   
/*     */   public void event(int id, String event) {
/* 134 */     if (!this.stampingStarted) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     int position = this.dataSizes.get(id);
/* 144 */     ((ArrayList<stEvent>)this.dataEvents.get(id)).add(new stEvent(position, event));
/*     */   }
/*     */   
/*     */   public void setDebugData(int id, Double data) {
/* 148 */     if (!this.stampingStarted) {
/*     */       return;
/*     */     }
/* 151 */     this.dataDebug[id] = data;
/*     */   }
/*     */   
/*     */   public Double getDebugData(int id) {
/* 155 */     if (!this.stampingStarted) {
/* 156 */       return Double.valueOf(0.0D);
/*     */     }
/* 158 */     return this.dataDebug[id];
/*     */   }
/*     */ 
/*     */   
/*     */   private String now() {
/* 163 */     Calendar cal = Calendar.getInstance();
/* 164 */     String str = "" + cal.get(1) + cal.get(2) + cal.get(5) + "_";
/*     */     
/* 166 */     if (cal.get(11) < 10)
/* 167 */       str = str + "0"; 
/* 168 */     str = str + cal.get(11);
/*     */     
/* 170 */     if (cal.get(12) < 10)
/* 171 */       str = str + "0"; 
/* 172 */     str = str + cal.get(12) + "_";
/*     */     
/* 174 */     if (cal.get(13) < 10)
/* 175 */       str = str + "0"; 
/* 176 */     str = str + cal.get(13);
/*     */     
/* 178 */     if (cal.get(14) < 10)
/* 179 */       str = str + "0"; 
/* 180 */     if (cal.get(14) < 100)
/* 181 */       str = str + "0"; 
/* 182 */     str = str + cal.get(14);
/* 183 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public void printToFile(String strName) {
/*     */     try {
/* 189 */       String strFilename = null;
/* 190 */       if (strName == null || strName == "" || strName.compareTo("") == 0) {
/* 191 */         strFilename = "TimingData_" + now() + ".csv";
/*     */       } else {
/* 193 */         strFilename = strName;
/* 194 */       }  File file = new File(System.getProperty("user.home"), ".tcg/" + strFilename);
/* 195 */       FileWriter fstream = new FileWriter(file.getPath());
/* 196 */       BufferedWriter out = new BufferedWriter(fstream);
/*     */       
/* 198 */       int maxColumns = 0;
/*     */ 
/*     */       
/* 201 */       Collection<String> c = this.dataIds.keySet();
/*     */       
/* 203 */       String name = "";
/* 204 */       Iterator<String> iter = c.iterator();
/* 205 */       while (iter.hasNext()) {
/* 206 */         name = iter.next();
/* 207 */         ArrayList<Long> ds = this.data.get(((Integer)this.dataIds.get(name)).intValue());
/* 208 */         if (ds.size() > maxColumns) {
/* 209 */           maxColumns = ds.size();
/*     */         }
/*     */       } 
/* 212 */       printNumbers(out, maxColumns, true);
/* 213 */       out.write("\n");
/*     */ 
/*     */       
/* 216 */       c = this.dataIds.keySet();
/* 217 */       name = "";
/* 218 */       iter = c.iterator();
/* 219 */       while (iter.hasNext()) {
/* 220 */         name = iter.next();
/* 221 */         out.write(name + ",");
/* 222 */         ArrayList<Long> ds = this.data.get(((Integer)this.dataIds.get(name)).intValue());
/* 223 */         for (int m = 0; m < ds.size(); m++) {
/* 224 */           out.write(((Long)ds.get(m)).toString() + ",");
/*     */         }
/* 226 */         out.write("\n");
/*     */       } 
/*     */ 
/*     */       
/* 230 */       c = this.dataIds.keySet();
/* 231 */       name = "";
/* 232 */       iter = c.iterator();
/*     */       
/* 234 */       while (iter.hasNext()) {
/* 235 */         name = iter.next();
/* 236 */         int id = ((Integer)this.dataIds.get(name)).intValue();
/* 237 */         ArrayList<stEvent> ds = this.dataEvents.get(id);
/*     */         
/* 239 */         if (ds.size() == 0) {
/*     */           continue;
/*     */         }
/* 242 */         out.write("EVENT:" + name + "\n");
/* 243 */         stEvent event = null;
/* 244 */         int writingcpt = 0;
/* 245 */         for (int m = 0; m < ds.size(); m++) {
/* 246 */           event = ds.get(m);
/* 247 */           while (writingcpt < event.position) {
/* 248 */             out.write("" + writingcpt + ",\n");
/* 249 */             writingcpt++;
/*     */           } 
/* 251 */           out.write("" + writingcpt + "," + event.event + "\n");
/*     */         } 
/* 253 */         out.write("\n\n");
/*     */       } 
/*     */       
/* 256 */       out.close();
/*     */     }
/* 258 */     catch (Exception e) {
/* 259 */       System.err.println("Error: " + e.getMessage());
/*     */     } 
/*     */     
/* 262 */     clear();
/*     */   }
/*     */   
/*     */   private void printNumbers(BufferedWriter out, int numColumns, boolean startAt0) throws IOException {
/* 266 */     if (startAt0) {
/* 267 */       for (int i = 0; i < numColumns; i++) {
/* 268 */         out.write("" + i + ",");
/*     */       }
/*     */     } else {
/* 271 */       for (int i = 1; i <= numColumns; i++) {
/* 272 */         out.write("" + i + ",");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStampingEnabled() {
/* 278 */     return this.stampingEnabled;
/*     */   }
/*     */   private class stEvent { public String event; public long position;
/*     */     
/*     */     public stEvent(int position, String event) {
/* 283 */       this.position = position;
/* 284 */       this.event = event;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\debug\TimeStamper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */