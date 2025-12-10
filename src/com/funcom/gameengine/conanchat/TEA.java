/*     */ package com.funcom.gameengine.conanchat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TEA
/*     */ {
/*     */   private static final int SUGAR = -1640531527;
/*     */   private static final int CUPS = 32;
/*     */   private static final int UNSUGAR = -957401312;
/*  35 */   private int[] S = new int[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TEA(byte[] key) {
/*  43 */     if (key == null)
/*  44 */       throw new RuntimeException("Invalid key: Key was null"); 
/*  45 */     if (key.length < 16)
/*  46 */       throw new RuntimeException("Invalid key: Length was less than 16 bytes"); 
/*  47 */     for (int off = 0, i = 0; i < 4; i++) {
/*  48 */       this.S[i] = key[off++] & 0xFF | (key[off++] & 0xFF) << 8 | (key[off++] & 0xFF) << 16 | (key[off++] & 0xFF) << 24;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] clear) {
/*  62 */     int paddedSize = (clear.length / 8 + ((clear.length % 8 == 0) ? 0 : 1)) * 2;
/*  63 */     int[] buffer = new int[paddedSize + 1];
/*  64 */     buffer[0] = clear.length;
/*  65 */     pack(clear, buffer, 1);
/*  66 */     brew(buffer);
/*  67 */     return unpack(buffer, 0, buffer.length * 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] crypt) {
/*  77 */     assert crypt.length % 4 == 0;
/*  78 */     assert crypt.length / 4 % 2 == 1;
/*  79 */     int[] buffer = new int[crypt.length / 4];
/*  80 */     pack(crypt, buffer, 0);
/*  81 */     unbrew(buffer);
/*  82 */     return unpack(buffer, 1, buffer[0]);
/*     */   }
/*     */   
/*     */   void brew(int[] buf) {
/*  86 */     assert buf.length % 2 == 1;
/*     */     
/*  88 */     int i = 1;
/*  89 */     while (i < buf.length) {
/*  90 */       int n = 32;
/*  91 */       int v0 = buf[i];
/*  92 */       int v1 = buf[i + 1];
/*  93 */       int sum = 0;
/*  94 */       while (n-- > 0) {
/*  95 */         sum -= 1640531527;
/*  96 */         v0 += ((v1 << 4) + this.S[0] ^ v1) + (sum ^ v1 >>> 5) + this.S[1];
/*  97 */         v1 += ((v0 << 4) + this.S[2] ^ v0) + (sum ^ v0 >>> 5) + this.S[3];
/*     */       } 
/*  99 */       buf[i] = v0;
/* 100 */       buf[i + 1] = v1;
/* 101 */       i += 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   void unbrew(int[] buf) {
/* 106 */     assert buf.length % 2 == 1;
/*     */     
/* 108 */     int i = 1;
/* 109 */     while (i < buf.length) {
/* 110 */       int n = 32;
/* 111 */       int v0 = buf[i];
/* 112 */       int v1 = buf[i + 1];
/* 113 */       int sum = -957401312;
/* 114 */       while (n-- > 0) {
/* 115 */         v1 -= ((v0 << 4) + this.S[2] ^ v0) + (sum ^ v0 >>> 5) + this.S[3];
/* 116 */         v0 -= ((v1 << 4) + this.S[0] ^ v1) + (sum ^ v1 >>> 5) + this.S[1];
/* 117 */         sum += 1640531527;
/*     */       } 
/* 119 */       buf[i] = v0;
/* 120 */       buf[i + 1] = v1;
/* 121 */       i += 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   void pack(byte[] src, int[] dest, int destOffset) {
/* 126 */     assert destOffset + src.length / 4 <= dest.length;
/* 127 */     int i = 0, shift = 24;
/* 128 */     int j = destOffset;
/* 129 */     dest[j] = 0;
/* 130 */     while (i < src.length) {
/* 131 */       dest[j] = dest[j] | (src[i] & 0xFF) << shift;
/* 132 */       if (shift == 0) {
/* 133 */         shift = 24;
/* 134 */         j++;
/* 135 */         if (j < dest.length) dest[j] = 0; 
/*     */       } else {
/* 137 */         shift -= 8;
/*     */       } 
/* 139 */       i++;
/*     */     } 
/*     */   }
/*     */   
/*     */   byte[] unpack(int[] src, int srcOffset, int destLength) {
/* 144 */     assert destLength <= (src.length - srcOffset) * 4;
/* 145 */     byte[] dest = new byte[destLength];
/* 146 */     int i = srcOffset;
/* 147 */     int count = 0;
/* 148 */     for (int j = 0; j < destLength; j++) {
/* 149 */       dest[j] = (byte)(src[i] >> 24 - 8 * count & 0xFF);
/* 150 */       count++;
/* 151 */       if (count == 4) {
/* 152 */         count = 0;
/* 153 */         i++;
/*     */       } 
/*     */     } 
/* 156 */     return dest;
/*     */   }
/*     */ 
/*     */   
/* 160 */   public static String quote = "Now rise, and show your strength. Be eloquent, and deep, and tender; see, with a clear eye, into Nature, and into life:  spread your white wings of quivering thought, and soar, a god-like spirit, over the whirling world beneath you, up through long lanes of flaming stars to the gates of eternity!";
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 164 */     TEA tea = new TEA("And is there honey still for tea?".getBytes());
/*     */     
/* 166 */     byte[] original = quote.getBytes();
/*     */ 
/*     */     
/* 169 */     byte[] crypt = tea.encrypt(original);
/* 170 */     byte[] result = tea.decrypt(crypt);
/*     */ 
/*     */     
/* 173 */     String test = new String(result);
/* 174 */     if (!test.equals(quote))
/* 175 */       throw new RuntimeException("Fail"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\TEA.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */