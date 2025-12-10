/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class MessageUtils
/*     */ {
/*     */   public static void writeInteger(ByteBuffer buffer, Integer integer) {
/*  23 */     int value = 0;
/*  24 */     if (integer == null) {
/*  25 */       buffer.put((byte)0);
/*     */     } else {
/*  27 */       buffer.put((byte)1);
/*  28 */       value = integer.intValue();
/*     */     } 
/*     */     
/*  31 */     buffer.putInt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Integer readInteger(ByteBuffer buffer) {
/*  36 */     if (buffer.get() == 0) {
/*  37 */       buffer.getInt();
/*  38 */       return null;
/*     */     } 
/*     */     
/*  41 */     return Integer.valueOf(buffer.getInt());
/*     */   }
/*     */   
/*     */   public static int getSizeInteger() {
/*  45 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeBoolean(ByteBuffer buffer, Boolean b) {
/*  50 */     if (b == null) {
/*  51 */       buffer.put((byte)0);
/*  52 */     } else if (b.booleanValue()) {
/*  53 */       buffer.put((byte)1);
/*     */     } else {
/*  55 */       buffer.put((byte)-1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Boolean readBoolean(ByteBuffer buffer) {
/*  60 */     byte val = buffer.get();
/*     */     
/*  62 */     if (val == 0)
/*  63 */       return null; 
/*  64 */     if (val == 1) {
/*  65 */       return Boolean.TRUE;
/*     */     }
/*  67 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSizeBoolean() {
/*  72 */     return 1;
/*     */   }
/*     */   
/*     */   public static int getSizeWorldCoordinate() {
/*  76 */     return 24;
/*     */   }
/*     */   
/*     */   public static int getSizeVector2d() {
/*  80 */     return 16;
/*     */   }
/*     */   
/*     */   public static int getSizePoint2D() {
/*  84 */     return 16;
/*     */   }
/*     */   
/*     */   public static int getSizeInt() {
/*  88 */     return 4;
/*     */   }
/*     */   
/*     */   public static int getSizeFloat() {
/*  92 */     return 4;
/*     */   }
/*     */   
/*     */   public static int getSizeLong() {
/*  96 */     return 8;
/*     */   }
/*     */   
/*     */   public static int getSizeShort() {
/* 100 */     return 2;
/*     */   }
/*     */   
/*     */   public static int getSizeDouble() {
/* 104 */     return 8;
/*     */   }
/*     */   
/*     */   public static int getSizeStr(String str) {
/* 108 */     int ret = 2;
/*     */     
/* 110 */     for (int i = str.length() - 1; i >= 0; i--) {
/* 111 */       char c = str.charAt(i);
/* 112 */       if (c < '') {
/* 113 */         ret++;
/* 114 */       } else if (c < 'ࠀ') {
/* 115 */         ret += 2;
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 123 */         ret += 3;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeStrArray(String[] str) {
/* 131 */     int ret = 2;
/* 132 */     for (String s : str) {
/* 133 */       ret += getSizeStr(s);
/*     */     }
/* 135 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeBoolArray(Boolean[] booleans) {
/* 139 */     return 2 + booleans.length;
/*     */   }
/*     */   
/*     */   public static int getSizeShortArray(Short[] str) {
/* 143 */     int ret = 2;
/* 144 */     ret += str.length * getSizeShort();
/* 145 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeIntArray(Integer[] str) {
/* 149 */     int ret = 2;
/* 150 */     ret += str.length * getSizeInt();
/* 151 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeLongArray(Long[] str) {
/* 155 */     int ret = 2;
/* 156 */     ret += str.length * getSizeLong();
/* 157 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeDoubleArray(Double[] str) {
/* 161 */     int ret = 2;
/* 162 */     ret += str.length * getSizeDouble();
/* 163 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeFloatArray(Float[] str) {
/* 167 */     int ret = 2;
/* 168 */     ret += str.length * getSizeFloat();
/* 169 */     return ret;
/*     */   }
/*     */   
/*     */   public static int getSizeListStr(List<String> list) {
/* 173 */     String[] strArray = list.<String>toArray(new String[list.size()]);
/* 174 */     return getSizeStrArray(strArray);
/*     */   }
/*     */   
/*     */   public static int getSizeListBool(List<Boolean> list) {
/* 178 */     Boolean[] boolArray = list.<Boolean>toArray(new Boolean[list.size()]);
/* 179 */     return getSizeBoolArray(boolArray);
/*     */   }
/*     */   
/*     */   public static void writeListStr(ByteBuffer buffer, List<String> list) {
/* 183 */     String[] strArray = list.<String>toArray(new String[list.size()]);
/* 184 */     writeStrArray(buffer, strArray);
/*     */   }
/*     */   
/*     */   public static void writeListBool(ByteBuffer buffer, List<Boolean> list) {
/* 188 */     Boolean[] strArray = list.<Boolean>toArray(new Boolean[list.size()]);
/* 189 */     writeBoolArray(buffer, strArray);
/*     */   }
/*     */   
/*     */   public static List<String> readListStr(ByteBuffer buffer) {
/* 193 */     ArrayList<String> list = new ArrayList<String>();
/* 194 */     String[] strArray = readStrArray(buffer);
/* 195 */     list.addAll(Arrays.asList(strArray));
/* 196 */     return list;
/*     */   }
/*     */   
/*     */   public static List<Boolean> readListBoolean(ByteBuffer buffer) {
/* 200 */     ArrayList<Boolean> list = new ArrayList<Boolean>();
/* 201 */     Boolean[] boolArray = readBoolArray(buffer);
/* 202 */     list.addAll(Arrays.asList(boolArray));
/* 203 */     return list;
/*     */   }
/*     */   
/*     */   public static int getSizeListInt(List<Integer> list) {
/* 207 */     Integer[] strArray = list.<Integer>toArray(new Integer[list.size()]);
/* 208 */     return getSizeIntArray(strArray);
/*     */   }
/*     */   
/*     */   public static void writeListInt(ByteBuffer buffer, List<Integer> list) {
/* 212 */     Integer[] strArray = list.<Integer>toArray(new Integer[list.size()]);
/* 213 */     writeIntArray(buffer, strArray);
/*     */   }
/*     */   
/*     */   public static List<Integer> readListInt(ByteBuffer buffer) {
/* 217 */     ArrayList<Integer> list = new ArrayList<Integer>();
/* 218 */     Integer[] strArray = readIntArray(buffer);
/* 219 */     list.addAll(Arrays.asList(strArray));
/* 220 */     return list;
/*     */   }
/*     */   
/*     */   public static int getSizeListLong(List<Long> list) {
/* 224 */     Long[] strArray = list.<Long>toArray(new Long[list.size()]);
/* 225 */     return getSizeLongArray(strArray);
/*     */   }
/*     */   
/*     */   public static void writeListLong(ByteBuffer buffer, List<Long> list) {
/* 229 */     Long[] strArray = list.<Long>toArray(new Long[list.size()]);
/* 230 */     writeLongArray(buffer, strArray);
/*     */   }
/*     */   
/*     */   public static List<Long> readListLong(ByteBuffer buffer) {
/* 234 */     ArrayList<Long> list = new ArrayList<Long>();
/* 235 */     Long[] strArray = readLongArray(buffer);
/* 236 */     list.addAll(Arrays.asList(strArray));
/* 237 */     return list;
/*     */   }
/*     */   
/*     */   public static int getSizeSimplifiedStats(Map<Short, Integer> simplifiedStats) {
/* 241 */     int ret = getSizeShort();
/*     */     
/* 243 */     ret += (getSizeShort() + getSizeInt()) * simplifiedStats.size();
/*     */     
/* 245 */     return ret;
/*     */   }
/*     */   
/*     */   public static void writeSimplifiedStats(ByteBuffer buffer, Map<Short, Integer> stats) {
/* 249 */     int size = stats.size();
/* 250 */     if (size > 32767) {
/* 251 */       throw new IllegalArgumentException("too many stats, max is 32767");
/*     */     }
/* 253 */     buffer.putShort((short)size);
/* 254 */     for (Map.Entry<Short, Integer> entry : stats.entrySet()) {
/* 255 */       writeShort(buffer, ((Short)entry.getKey()).shortValue());
/* 256 */       buffer.putInt(((Integer)entry.getValue()).intValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Map<Short, Integer> readSimplifiedStats(ByteBuffer buffer) {
/* 261 */     int size = buffer.getShort();
/*     */     
/* 263 */     Map<Short, Integer> ret = new HashMap<Short, Integer>();
/*     */     
/* 265 */     for (int i = 0; i < size; i++) {
/* 266 */       short id = readShort(buffer);
/* 267 */       int value = buffer.getInt();
/* 268 */       ret.put(Short.valueOf(id), Integer.valueOf(value));
/*     */     } 
/*     */     
/* 271 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeStr(ByteBuffer buffer, String str) {
/*     */     byte[] arrayOfByte;
/*     */     try {
/* 278 */       arrayOfByte = str.getBytes("UTF-8");
/* 279 */     } catch (UnsupportedEncodingException e) {
/*     */ 
/*     */ 
/*     */       
/* 283 */       arrayOfByte = str.getBytes();
/*     */     } 
/*     */     
/* 286 */     if (arrayOfByte.length > 65535)
/*     */     {
/* 288 */       throw new MessageIOFatalException("string data is too long: length=" + arrayOfByte.length);
/*     */     }
/* 290 */     buffer.putShort((short)arrayOfByte.length);
/* 291 */     buffer.put(arrayOfByte);
/*     */   }
/*     */   
/*     */   public static void writeStrArray(ByteBuffer buffer, String[] str) {
/* 295 */     writeShort(buffer, (short)str.length);
/* 296 */     for (String s : str) {
/* 297 */       writeStr(buffer, s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeBoolArray(ByteBuffer buffer, Boolean[] bool) {
/* 302 */     writeShort(buffer, (short)bool.length);
/* 303 */     for (Boolean b : bool) {
/* 304 */       writeBoolean(buffer, b);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeDoubleArray(ByteBuffer buffer, Double[] str) {
/* 309 */     writeShort(buffer, (short)str.length);
/* 310 */     for (Double s : str) {
/* 311 */       writeDouble(buffer, s.doubleValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeFloatArray(ByteBuffer buffer, Float[] str) {
/* 316 */     writeShort(buffer, (short)str.length);
/* 317 */     for (Float s : str) {
/* 318 */       writeFloat(buffer, s.floatValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static String readStr(ByteBuffer buffer) {
/* 323 */     int len = buffer.getShort() & 0xFFFF;
/* 324 */     byte[] strData = new byte[len];
/*     */     
/* 326 */     buffer.get(strData);
/*     */     
/*     */     try {
/* 329 */       return new String(strData, "UTF-8");
/* 330 */     } catch (UnsupportedEncodingException e) {
/*     */ 
/*     */       
/* 333 */       return new String(strData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] readStrArray(ByteBuffer buffer) {
/* 338 */     int len = buffer.getShort() & 0xFFFF;
/* 339 */     String[] strArray = new String[len];
/* 340 */     for (int i = 0; i < len; i++) {
/* 341 */       strArray[i] = readStr(buffer);
/*     */     }
/* 343 */     return strArray;
/*     */   }
/*     */   
/*     */   public static Boolean[] readBoolArray(ByteBuffer buffer) {
/* 347 */     int len = buffer.getShort() & 0xFFFF;
/* 348 */     Boolean[] boolArray = new Boolean[len];
/* 349 */     for (int i = 0; i < len; i++) {
/* 350 */       boolArray[i] = readBoolean(buffer);
/*     */     }
/* 352 */     return boolArray;
/*     */   }
/*     */   
/*     */   public static Double[] readDoubleArray(ByteBuffer buffer) {
/* 356 */     int len = buffer.getShort() & 0xFFFF;
/* 357 */     Double[] strArray = new Double[len];
/* 358 */     for (int i = 0; i < len; i++) {
/* 359 */       strArray[i] = Double.valueOf(readDouble(buffer));
/*     */     }
/* 361 */     return strArray;
/*     */   }
/*     */   
/*     */   public static Float[] readFloatArray(ByteBuffer buffer) {
/* 365 */     int len = buffer.getShort() & 0xFFFF;
/* 366 */     Float[] strArray = new Float[len];
/* 367 */     for (int i = 0; i < len; i++) {
/* 368 */       strArray[i] = Float.valueOf(readFloat(buffer));
/*     */     }
/* 370 */     return strArray;
/*     */   }
/*     */   
/*     */   public static void writeShortArray(ByteBuffer buffer, Short[] str) {
/* 374 */     writeShort(buffer, (short)str.length);
/* 375 */     for (Short s : str) {
/* 376 */       writeShort(buffer, s.shortValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Short[] readShortArray(ByteBuffer buffer) {
/* 381 */     int len = buffer.getShort() & 0xFFFF;
/* 382 */     Short[] strArray = new Short[len];
/* 383 */     for (int i = 0; i < len; i++) {
/* 384 */       strArray[i] = Short.valueOf(readShort(buffer));
/*     */     }
/* 386 */     return strArray;
/*     */   }
/*     */   
/*     */   public static void writeIntArray(ByteBuffer buffer, Integer[] str) {
/* 390 */     writeShort(buffer, (short)str.length);
/* 391 */     for (Integer s : str) {
/* 392 */       writeInt(buffer, s.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Integer[] readIntArray(ByteBuffer buffer) {
/* 397 */     int len = buffer.getShort() & 0xFFFF;
/* 398 */     Integer[] strArray = new Integer[len];
/* 399 */     for (int i = 0; i < len; i++) {
/* 400 */       strArray[i] = Integer.valueOf(readInt(buffer));
/*     */     }
/* 402 */     return strArray;
/*     */   }
/*     */   
/*     */   public static void writeLongArray(ByteBuffer buffer, Long[] str) {
/* 406 */     writeShort(buffer, (short)str.length);
/* 407 */     for (Long s : str) {
/* 408 */       writeLong(buffer, s.longValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Long[] readLongArray(ByteBuffer buffer) {
/* 413 */     int len = buffer.getShort() & 0xFFFF;
/* 414 */     Long[] strArray = new Long[len];
/* 415 */     for (int i = 0; i < len; i++) {
/* 416 */       strArray[i] = Long.valueOf(readLong(buffer));
/*     */     }
/* 418 */     return strArray;
/*     */   }
/*     */   
/*     */   public static void writeWorldCoordinatePartial(ByteBuffer byteBuffer, WorldCoordinate worldCoordinate) {
/* 422 */     byteBuffer.putInt((worldCoordinate.getTileCoord()).x);
/* 423 */     byteBuffer.putInt((worldCoordinate.getTileCoord()).y);
/* 424 */     byteBuffer.putDouble(worldCoordinate.getTileOffset().getX());
/* 425 */     byteBuffer.putDouble(worldCoordinate.getTileOffset().getY());
/*     */   }
/*     */   
/*     */   public static WorldCoordinate readWorldCoordinatePartial(ByteBuffer byteBuffer) {
/* 429 */     int tileX = byteBuffer.getInt();
/* 430 */     int tileY = byteBuffer.getInt();
/* 431 */     double offsetX = byteBuffer.getDouble();
/* 432 */     double offsetY = byteBuffer.getDouble();
/* 433 */     return new WorldCoordinate(tileX, tileY, offsetX, offsetY, "UNIDENTIFIED_MAP", 0);
/*     */   }
/*     */   
/*     */   public static void writeVector2d(ByteBuffer byteBuffer, Vector2d vector2d) {
/* 437 */     byteBuffer.putDouble(vector2d.getX());
/* 438 */     byteBuffer.putDouble(vector2d.getY());
/*     */   }
/*     */   
/*     */   public static Vector2d readVector2d(ByteBuffer byteBuffer) {
/* 442 */     double x = byteBuffer.getDouble();
/* 443 */     double y = byteBuffer.getDouble();
/* 444 */     return new Vector2d(x, y);
/*     */   }
/*     */   
/*     */   public static void writeInt(ByteBuffer byteBuffer, int i) {
/* 448 */     byteBuffer.putInt(i);
/*     */   }
/*     */   
/*     */   public static int readInt(ByteBuffer byteBuffer) {
/* 452 */     return byteBuffer.getInt();
/*     */   }
/*     */   
/*     */   public static void writeDouble(ByteBuffer bytebuffer, double value) {
/* 456 */     bytebuffer.putDouble(value);
/*     */   }
/*     */   
/*     */   public static double readDouble(ByteBuffer byteBuffer) {
/* 460 */     return byteBuffer.getDouble();
/*     */   }
/*     */   
/*     */   public static void writeFloat(ByteBuffer byteBuffer, float value) {
/* 464 */     byteBuffer.putFloat(value);
/*     */   }
/*     */   
/*     */   public static float readFloat(ByteBuffer byteBuffer) {
/* 468 */     return byteBuffer.getFloat();
/*     */   }
/*     */   
/*     */   public static short readShort(ByteBuffer byteBuffer) {
/* 472 */     return byteBuffer.getShort();
/*     */   }
/*     */   
/*     */   public static void writeShort(ByteBuffer byteBuffer, short value) {
/* 476 */     byteBuffer.putShort(value);
/*     */   }
/*     */   
/*     */   public static void writeLong(ByteBuffer byteBuffer, long value) {
/* 480 */     byteBuffer.putLong(value);
/*     */   }
/*     */   
/*     */   public static long readLong(ByteBuffer byteBuffer) {
/* 484 */     return byteBuffer.getLong();
/*     */   }
/*     */   
/*     */   public static InetSocketAddress readInetSocketAddress(ByteBuffer buffer) {
/* 488 */     byte[] address = readByteArray(buffer);
/* 489 */     InetAddress add = null;
/*     */     try {
/* 491 */       add = InetAddress.getByAddress(address);
/* 492 */     } catch (UnknownHostException e) {
/*     */       
/* 494 */       throw new RuntimeException(e);
/*     */     } 
/* 496 */     return new InetSocketAddress(add, readInt(buffer));
/*     */   }
/*     */   
/*     */   public static void writeInetSocketAddress(ByteBuffer buffer, InetSocketAddress value) {
/* 500 */     writeByteArray(buffer, value.getAddress().getAddress());
/* 501 */     writeInt(buffer, value.getPort());
/*     */   }
/*     */   
/*     */   public static int getSizeInetSocketAddress(InetSocketAddress value) {
/* 505 */     return getSizeByteArray(value.getAddress().getAddress()) + getSizeInt();
/*     */   }
/*     */   
/*     */   public static int getSizeByteArray(byte[] byteArray) {
/* 509 */     return 2 + byteArray.length * 8;
/*     */   }
/*     */   
/*     */   public static byte[] readByteArray(ByteBuffer buffer) {
/* 513 */     short length = buffer.getShort();
/* 514 */     byte[] returnable = new byte[length];
/* 515 */     for (int i = 0; i < returnable.length; i++)
/* 516 */       returnable[i] = buffer.get(); 
/* 517 */     return returnable;
/*     */   }
/*     */   
/*     */   public static void writeByteArray(ByteBuffer buffer, byte[] byteArray) {
/* 521 */     writeShort(buffer, (short)byteArray.length);
/* 522 */     for (int i = 0; i < byteArray.length; i++)
/* 523 */       buffer.put(byteArray[i]); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\MessageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */