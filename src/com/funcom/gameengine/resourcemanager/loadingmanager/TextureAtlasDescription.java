/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class TextureAtlasDescription
/*     */ {
/*  23 */   ByteBuffer buffer = null;
/*     */ 
/*     */   
/*     */   public class TextureDescription
/*     */   {
/*     */     public String texturefilename;
/*     */     
/*     */     public String atlasfilename;
/*     */     public int atlasid;
/*     */     public String atlastype;
/*     */     public float woffset;
/*     */     public float hoffset;
/*     */     public float doffset;
/*     */     public float width;
/*     */     public float height;
/*     */     public float U;
/*     */     public float V;
/*     */     
/*     */     public void processUV() {
/*  42 */       this.U = this.width;
/*  43 */       this.V = this.height;
/*     */     }
/*     */   }
/*     */   
/*  47 */   private static TextureAtlasDescription textureDescriptions = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextureAtlasDescription getAtlasDescription(String key, ResourceGetter resourceGetter) {
/*  52 */     textureDescriptions = resourceGetter.getTextureAtlasDescription(key, CacheType.CACHE_TEMPORARILY);
/*     */     
/*  54 */     return textureDescriptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureDescription getTextureDescription(String filepath, ResourceGetter resourceGetter) {
/*  59 */     TextureAtlasDescription atlas = textureDescriptions;
/*     */     
/*  61 */     String key = getKey(filepath);
/*  62 */     TextureDescription def = null;
/*     */     
/*  64 */     def = atlas.textureTable.get(key);
/*     */     
/*  66 */     return def;
/*     */   }
/*     */ 
/*     */   
/*  70 */   private ConcurrentHashMap<String, TextureDescription> textureTable = new ConcurrentHashMap<String, TextureDescription>();
/*     */   
/*     */   public TextureAtlasDescription(ByteBuffer buffer) {
/*     */     try {
/*  74 */       byte[] bytearray = new byte[buffer.remaining()];
/*  75 */       buffer.get(bytearray);
/*  76 */       String content = new String(bytearray);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       TextureDescription def = new TextureDescription();
/*     */       
/*  83 */       String[] lines = content.split("\\r?\\n");
/*     */ 
/*     */ 
/*     */       
/*  87 */       for (int linecnt = 1; linecnt < lines.length; linecnt++) {
/*  88 */         String line = lines[linecnt];
/*  89 */         if (line.length() != 0)
/*     */         {
/*     */           
/*  92 */           if (line.charAt(0) != '#') {
/*     */ 
/*     */             
/*  95 */             StringTokenizer token = new StringTokenizer(line);
/*     */             
/*  97 */             if (token.hasMoreTokens()) {
/*  98 */               def.texturefilename = cleanPath(token.nextToken());
/*     */ 
/*     */               
/* 101 */               if (token.hasMoreTokens()) {
/* 102 */                 def.atlasfilename = cleanPath(cleanString(token.nextToken()));
/*     */ 
/*     */                 
/* 105 */                 if (token.hasMoreTokens()) {
/* 106 */                   def.atlasid = Integer.parseInt(cleanString(token.nextToken()));
/*     */ 
/*     */                   
/* 109 */                   if (token.hasMoreTokens()) {
/* 110 */                     def.atlastype = cleanString(token.nextToken());
/*     */ 
/*     */                     
/* 113 */                     if (token.hasMoreTokens()) {
/* 114 */                       def.woffset = Float.parseFloat(cleanString(token.nextToken()));
/*     */ 
/*     */                       
/* 117 */                       if (token.hasMoreTokens()) {
/* 118 */                         def.hoffset = Float.parseFloat(cleanString(token.nextToken()));
/*     */ 
/*     */                         
/* 121 */                         if (token.hasMoreTokens()) {
/* 122 */                           def.doffset = Float.parseFloat(cleanString(token.nextToken()));
/*     */ 
/*     */                           
/* 125 */                           if (token.hasMoreTokens()) {
/* 126 */                             def.width = Float.parseFloat(cleanString(token.nextToken()));
/*     */ 
/*     */                             
/* 129 */                             if (token.hasMoreTokens()) {
/* 130 */                               def.height = Float.parseFloat(token.nextToken());
/*     */ 
/*     */ 
/*     */                               
/* 134 */                               def.processUV();
/* 135 */                               this.textureTable.put(getKey(def.texturefilename), def);
/* 136 */                               def = new TextureDescription();
/*     */                             }
/*     */                           
/*     */                           }
/*     */                         
/*     */                         }
/*     */                       
/*     */                       }
/*     */                     
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 153 */     } catch (Exception e) {
/* 154 */       System.out.printf("Error while processing file.\n", new Object[0]);
/* 155 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getKey(String filepath) {
/* 160 */     String key = "";
/*     */ 
/*     */     
/* 163 */     key = cleanPath(filepath);
/*     */     
/* 165 */     return key;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String cleanString(String str) {
/* 170 */     str = str.trim();
/* 171 */     str = str.substring(0, str.length() - 1);
/* 172 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String cleanPath(String filepath) {
/* 179 */     filepath = filepath.replace('\\', '/');
/* 180 */     filepath = filepath.replace("//", "/");
/* 181 */     filepath = filepath.replace("\\\\", "/");
/*     */     
/* 183 */     int pos = filepath.lastIndexOf("tiles/");
/* 184 */     if (pos >= 0)
/* 185 */       return filepath.substring(pos); 
/* 186 */     return filepath;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\TextureAtlasDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */