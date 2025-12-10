/*     */ package com.funcom.commons.configuration;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CSVReader
/*     */ {
/*     */   private final LineNumberReader l;
/*     */   private final PushbackReader r;
/*     */   private int fieldCount;
/*     */   private boolean reachedEORecord;
/*     */   private boolean reachedEOF;
/*  25 */   private Map<String, String> strings = new HashMap<String, String>();
/*     */   
/*     */   public CSVReader(Reader r) {
/*  28 */     this.l = new LineNumberReader(r);
/*  29 */     this.r = new PushbackReader(this.l, 1);
/*     */   }
/*     */   
/*     */   public void setStringInstancesChecked(boolean checkStringInstances) {
/*  33 */     if (checkStringInstances) {
/*  34 */       if (this.strings == null) {
/*  35 */         this.strings = new HashMap<String, String>();
/*     */       }
/*     */     } else {
/*  38 */       this.strings = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStringInstancesChecked() {
/*  43 */     return (this.strings != null);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  47 */     this.r.close();
/*     */   }
/*     */   
/*     */   public List<String[]> readRecords() throws IOException {
/*  51 */     List<String[]> ret = (List)new ArrayList<String>();
/*     */     
/*     */     String[] record;
/*     */     
/*  55 */     while ((record = readRecord()) != null) {
/*  56 */       ret.add(record);
/*     */     }
/*     */     
/*  59 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] readRecord() throws IOException {
/*  64 */     String[] ret = null;
/*  65 */     int index = 0;
/*     */     
/*  67 */     boolean isFirstField = true;
/*     */     String field;
/*  69 */     while ((field = readField(isFirstField)) != null) {
/*     */       
/*  71 */       if (this.strings != null) {
/*  72 */         String existingInstance = this.strings.get(field);
/*  73 */         if (existingInstance == null) {
/*  74 */           this.strings.put(field, field);
/*     */         } else {
/*  76 */           field = existingInstance;
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (ret == null) {
/*  81 */         ret = new String[this.fieldCount];
/*     */       }
/*     */       
/*  84 */       if (ret.length <= index) {
/*  85 */         ret = Arrays.<String>copyOf(ret, index + 1);
/*     */       }
/*     */       
/*  88 */       ret[index++] = field;
/*  89 */       isFirstField = false;
/*     */     } 
/*     */     
/*  92 */     if (this.fieldCount < index) {
/*  93 */       this.fieldCount = index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private String readField(boolean firstField) throws IOException {
/* 107 */     if (this.reachedEOF) {
/* 108 */       return null;
/*     */     }
/* 110 */     if (this.reachedEORecord) {
/* 111 */       this.reachedEORecord = false;
/* 112 */       return null;
/*     */     } 
/*     */     
/* 115 */     StringBuilder buf = new StringBuilder();
/* 116 */     ParseState state = ParseState.START;
/*     */ 
/*     */     
/*     */     while (true) {
/* 120 */       int c = read();
/*     */       
/* 122 */       switch (state) {
/*     */         case START:
/* 124 */           if (c == 44 || c == 10 || c <= -1) {
/*     */             
/* 126 */             this.reachedEORecord = (c == 10 || c == -1);
/* 127 */             if (this.reachedEORecord && firstField) {
/* 128 */               this.reachedEOF = isNextCharEOF();
/* 129 */               return this.reachedEOF ? null : "";
/*     */             } 
/* 131 */             return "";
/* 132 */           }  if (!Character.isWhitespace((char)c)) {
/* 133 */             if (c == 34) {
/* 134 */               state = ParseState.READ_UNTIL_QUOTE; continue;
/*     */             } 
/* 136 */             state = ParseState.READ_UNTIL_COMMA_NL;
/* 137 */             buf.append((char)c);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case READ_UNTIL_COMMA_NL:
/* 144 */           if (c == 44 || c == 10 || c == -1) {
/*     */             
/* 146 */             this.reachedEORecord = (c == 10);
/* 147 */             reintroduceLinefeeds(buf);
/* 148 */             return buf.toString();
/*     */           } 
/* 150 */           buf.append((char)c);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case READ_UNTIL_QUOTE:
/* 156 */           if (c <= -1) {
/* 157 */             throw new EOFException("Premature EOF");
/*     */           }
/* 159 */           if (c == 34) {
/* 160 */             int next = read();
/*     */             
/* 162 */             if (next == 34) {
/* 163 */               buf.append((char)c);
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 168 */             while (next != -1 && next != 10 && next != 44) {
/* 169 */               next = read();
/*     */             }
/* 171 */             this.reachedEORecord = (next == 10);
/* 172 */             this.reachedEOF = isNextCharEOF();
/* 173 */             reintroduceLinefeeds(buf);
/* 174 */             return buf.toString();
/*     */           } 
/*     */           
/* 177 */           buf.append((char)c);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reintroduceLinefeeds(StringBuilder buf) {
/* 187 */     int index = buf.indexOf("\\n");
/* 188 */     while (index != -1) {
/* 189 */       buf.replace(index, index + 2, "\n");
/* 190 */       index = buf.indexOf("\\n");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int read() throws IOException {
/* 196 */     int c = this.r.read();
/*     */     
/* 198 */     if (c <= -1) {
/* 199 */       this.reachedEOF = true;
/*     */     }
/* 201 */     return c;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isNextCharEOF() throws IOException {
/* 206 */     int c = this.r.read();
/*     */     
/* 208 */     if (c == -1) {
/* 209 */       return true;
/*     */     }
/*     */     
/* 212 */     this.r.unread(c);
/*     */     
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   private enum ParseState {
/* 218 */     START,
/* 219 */     READ_UNTIL_COMMA_NL,
/* 220 */     READ_UNTIL_QUOTE;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\CSVReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */