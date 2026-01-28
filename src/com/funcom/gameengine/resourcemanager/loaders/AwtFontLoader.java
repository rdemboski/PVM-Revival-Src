/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class AwtFontLoader
/*     */   extends AbstractLoader {
/*     */   private static final String FONTCONFIG_PATH = "ui/fonts/fontaliasconfig.properties";
/*     */   private static final String PREFIX_FILE = "file";
/*     */   private static final String PREFIX_FONT_BY_NAME = "fontname";
/*     */   private Map<String, AliasLoader> fontMapping;
/*     */   
/*     */   public AwtFontLoader() {
/*  21 */     super(Font.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  26 */     InputStream inputStream = null;
/*     */     
/*     */     try {
/*  29 */       inputStream = getFileInputStream("ui/fonts/fontaliasconfig.properties");
/*  30 */       Properties fileConfig = new Properties();
/*  31 */       fileConfig.load(inputStream);
/*  32 */       this.fontMapping = new HashMap<String, AliasLoader>();
/*  33 */       for (Map.Entry<Object, Object> fontEntry : fileConfig.entrySet()) {
/*     */         AliasLoader loader;
/*  35 */         String alias = fontEntry.getKey().toString().toLowerCase();
/*  36 */         String fontReference = fontEntry.getValue().toString();
/*     */         
/*  38 */         String[] parts = fontReference.split(":");
/*  39 */         String type = parts[0];
/*  40 */         String filePath = parts[1];
/*     */ 
/*     */         
/*  43 */         if ("file".equals(type)) {
/*  44 */           loader = new FileAliasLoader(filePath, this);
/*  45 */         } else if ("fontname".equals(type)) {
/*  46 */           loader = new BuiltInFontAliasLoader(filePath);
/*     */         } else {
/*  48 */           throw new RuntimeException("unknown font reference type: reference=" + fontReference);
/*     */         } 
/*     */         
/*  51 */         this.fontMapping.put(alias, loader);
/*     */       } 
/*  53 */     } catch (IOException e) {
/*  54 */       throw new RuntimeException("cannot read font config: path=ui/fonts/fontaliasconfig.properties", e);
/*     */     } finally {
/*  56 */       closeSafely(inputStream, (ManagedResource<?>)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("unchecked")
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  62 */     InputStream inputStream = null; try {
/*     */       Font font;
/*  64 */       String fontName = managedResource.getName();
/*  65 */       AliasLoader aliasLoader = this.fontMapping.get(fontName.toLowerCase());
/*     */ 
/*     */       
/*  68 */       if (aliasLoader != null) {
/*  69 */         font = aliasLoader.load(this);
/*     */       } else {
/*  71 */         font = new Font(fontName, 0, 1);
/*     */       } 
/*     */       
/*  74 */       ((ManagedResource<Font>)managedResource).setResource(font);
/*  75 */     } catch (IOException e) {
/*  76 */       throw new LoadException(getResourceManager(), managedResource, e);
/*  77 */     } catch (FontFormatException e) {
/*  78 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } finally {
/*  80 */       closeSafely(inputStream, managedResource);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface AliasLoader {
/*     */     Font load(AwtFontLoader param1AwtFontLoader) throws IOException, FontFormatException;
/*     */   }
/*     */   
/*     */   private static class FileAliasLoader implements AliasLoader {
/*     */     private final String filePath;
/*     */     
/*     */     public FileAliasLoader(String filePath, AwtFontLoader awtFontLoader) {
/*  92 */       this.filePath = filePath;
/*  93 */       ensureCanRead(awtFontLoader);
/*     */     }
/*     */     
/*     */     private void ensureCanRead(AwtFontLoader awtFontLoader) {
/*  97 */       InputStream checkStream = null;
/*     */       try {
/*  99 */         checkStream = awtFontLoader.getFileInputStream(this.filePath);
/* 100 */       } catch (IOException e) {
/* 101 */         throw new RuntimeException("cannot find font file: file=" + this.filePath);
/*     */       } finally {
/* 103 */         AbstractLoader.closeSafely(checkStream, (ManagedResource<?>)null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Font load(AwtFontLoader parentLoader) throws IOException, FontFormatException {
/* 109 */       InputStream inputStream = null;
/*     */       try {
/* 111 */         inputStream = parentLoader.getFileInputStream(this.filePath);
/* 112 */         return Font.createFont(0, inputStream);
/*     */       } finally {
/* 114 */         AbstractLoader.closeSafely(inputStream, (ManagedResource<?>)null);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BuiltInFontAliasLoader implements AliasLoader {
/*     */     private final String fontName;
/*     */     
/*     */     public BuiltInFontAliasLoader(String fontName) {
/* 123 */       this.fontName = fontName;
/*     */     }
/*     */ 
/*     */     
/*     */     public Font load(AwtFontLoader parentLoader) throws IOException, FontFormatException {
/* 128 */       return new Font(this.fontName, 0, 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\AwtFontLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */