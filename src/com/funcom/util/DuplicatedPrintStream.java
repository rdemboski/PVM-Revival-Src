/*     */ package com.funcom.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ public class DuplicatedPrintStream
/*     */   extends PrintStream
/*     */ {
/*  12 */   private static final OutputStream NULL = new NullOutputStream();
/*     */   private PrintStream parent;
/*     */   private PrintStream parent2;
/*     */   
/*     */   public DuplicatedPrintStream(PrintStream parent, PrintStream parent2) {
/*  17 */     super(NULL);
/*  18 */     this.parent = parent;
/*  19 */     this.parent2 = parent2;
/*     */   }
/*     */   
/*     */   public void flush() {
/*  23 */     this.parent.flush();
/*  24 */     this.parent2.flush();
/*     */   }
/*     */   
/*     */   public void close() {
/*  28 */     this.parent.close();
/*  29 */     this.parent2.close();
/*     */   }
/*     */   
/*     */   public boolean checkError() {
/*  33 */     return (this.parent.checkError() && this.parent2.checkError());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) {
/*  38 */     this.parent.write(b);
/*  39 */     this.parent2.write(b);
/*     */   }
/*     */   
/*     */   public void write(byte[] buf, int off, int len) {
/*  43 */     this.parent.write(buf, off, len);
/*  44 */     this.parent2.write(buf, off, len);
/*     */   }
/*     */   
/*     */   public void print(boolean b) {
/*  48 */     this.parent.print(b);
/*  49 */     this.parent2.print(b);
/*     */   }
/*     */   
/*     */   public void print(char c) {
/*  53 */     this.parent.print(c);
/*  54 */     this.parent2.print(c);
/*     */   }
/*     */   
/*     */   public void print(int i) {
/*  58 */     this.parent.print(i);
/*  59 */     this.parent2.print(i);
/*     */   }
/*     */   
/*     */   public void print(long l) {
/*  63 */     this.parent.print(l);
/*  64 */     this.parent2.print(l);
/*     */   }
/*     */   
/*     */   public void print(float f) {
/*  68 */     this.parent.print(f);
/*  69 */     this.parent2.print(f);
/*     */   }
/*     */   
/*     */   public void print(double d) {
/*  73 */     this.parent.print(d);
/*  74 */     this.parent2.print(d);
/*     */   }
/*     */   
/*     */   public void print(char[] s) {
/*  78 */     this.parent.print(s);
/*  79 */     this.parent2.print(s);
/*     */   }
/*     */   
/*     */   public void print(String s) {
/*  83 */     this.parent.print(s);
/*  84 */     this.parent2.print(s);
/*     */   }
/*     */   
/*     */   public void print(Object obj) {
/*  88 */     this.parent.print(obj);
/*  89 */     this.parent2.print(obj);
/*     */   }
/*     */   
/*     */   public void println() {
/*  93 */     this.parent.println();
/*  94 */     this.parent2.println();
/*     */   }
/*     */   
/*     */   public void println(boolean x) {
/*  98 */     this.parent.println(x);
/*  99 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(char x) {
/* 103 */     this.parent.println(x);
/* 104 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(int x) {
/* 108 */     this.parent.println(x);
/* 109 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(long x) {
/* 113 */     this.parent.println(x);
/* 114 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(float x) {
/* 118 */     this.parent.println(x);
/* 119 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(double x) {
/* 123 */     this.parent.println(x);
/* 124 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(char[] x) {
/* 128 */     this.parent.println(x);
/* 129 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(String x) {
/* 133 */     this.parent.println(x);
/* 134 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public void println(Object x) {
/* 138 */     this.parent.println(x);
/* 139 */     this.parent2.println(x);
/*     */   }
/*     */   
/*     */   public PrintStream printf(String format, Object... args) {
/* 143 */     this.parent.printf(format, args);
/* 144 */     this.parent2.printf(format, args);
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream printf(Locale l, String format, Object... args) {
/* 149 */     this.parent.printf(l, format, args);
/* 150 */     this.parent2.printf(l, format, args);
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream format(String format, Object... args) {
/* 155 */     this.parent.format(format, args);
/* 156 */     this.parent2.format(format, args);
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream format(Locale l, String format, Object... args) {
/* 161 */     this.parent.format(l, format, args);
/* 162 */     this.parent2.format(l, format, args);
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream append(CharSequence csq) {
/* 167 */     this.parent.append(csq);
/* 168 */     this.parent2.append(csq);
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream append(CharSequence csq, int start, int end) {
/* 173 */     this.parent.append(csq, start, end);
/* 174 */     this.parent2.append(csq, start, end);
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public PrintStream append(char c) {
/* 179 */     this.parent.append(c);
/* 180 */     this.parent2.append(c);
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   private static class NullOutputStream extends OutputStream {
/*     */     private NullOutputStream() {}
/*     */     
/*     */     public void write(int b) throws IOException {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\DuplicatedPrintStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */