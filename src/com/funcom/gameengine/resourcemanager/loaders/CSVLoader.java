/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.commons.configuration.CSVReader;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class CSVLoader
/*     */   extends AbstractLoader {
/*     */   public static final String CSV_PROP_FILE = "configuration/csvfiles.properties";
/*  18 */   private Properties csvProps = new Properties();
/*     */   private boolean initialized = false;
/*     */   
/*     */   public CSVLoader() {
/*  22 */     super(CSVData.class);
/*     */   }
/*     */   
/*     */   static String changeExtension(String originalName, String newExtension) {
/*  26 */     int lastDot = originalName.lastIndexOf(".");
/*  27 */     if (lastDot != -1) {
/*  28 */       return originalName.substring(0, lastDot) + newExtension;
/*     */     }
/*  30 */     return originalName + newExtension;
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("unchecked")
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  35 */     if (!getResourceManager().isUseJars()) {
/*  36 */       CSVReader reader = null;
/*     */       try {
/*  38 */         String path = managedResource.getName();
/*     */         
/*  40 */         boolean bLoaded = false;
/*  41 */         File pathFile = new File(path);
/*  42 */         List<File> files = listFiles(pathFile);
/*     */         
/*  44 */         if (LoadingManager.USE_LIP && files.size() > 0 && !bLoaded) {
/*  45 */           CSVDataLIP csvData = new CSVDataLIP();
/*  46 */           bLoaded = csvData.addFile(files);
/*  47 */           if (bLoaded) {
/*  48 */             ((ManagedResource<CSVDataLIP>)managedResource).setResource(csvData);
/*     */           }
/*     */         } 
/*  51 */         if (!bLoaded) {
/*  52 */           CSVData csvData = new CSVData();
/*  53 */           InputStream[] dataStreams = getInputStreams(path);
/*  54 */           for (InputStream dataStream : dataStreams) {
/*  55 */             reader = new CSVReader(new InputStreamReader(dataStream));
/*     */             String[] record;
/*  57 */             while ((record = reader.readRecord()) != null) {
/*  58 */               csvData.add(record);
/*     */             }
/*  60 */             reader.close();
/*     */           } 
/*     */           
/*  63 */           ((ManagedResource<CSVData>)managedResource).setResource(csvData);
/*     */         } 
/*  65 */       } catch (IOException e) {
/*  66 */         throw new LoadException(getResourceManager(), managedResource, e);
/*  67 */       } catch (Exception e) {
/*  68 */         throw new LoadException(getResourceManager(), managedResource, e);
/*     */       } finally {
/*  70 */         closeSafely(reader, managedResource);
/*     */       } 
/*     */     } else {
/*  73 */       loadDataFromJars(managedResource);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadDataFromJars(ManagedResource<?> managedResource) throws LoadException {
/*  78 */     if (!this.initialized) {
/*  79 */       initCSVProperties();
/*     */     }
/*     */     
/*  82 */     CSVReader csvReader = null;
/*  83 */     CSVData csvData = new CSVData();
/*  84 */     String filepath = managedResource.getName();
/*     */     
/*  86 */     if (isWildCardSearch(filepath)) {
/*  87 */       wildcardAdd(managedResource, csvReader, csvData, filepath);
/*     */     } else {
/*  89 */       readAndAddFile(filepath, csvReader, csvData, managedResource);
/*     */     } 
/*     */   }
/*     */   @SuppressWarnings("unchecked")
/*     */   private void wildcardAdd(ManagedResource<?> managedResource, CSVReader csvReader, CSVData csvData, String filepath) throws LoadException {
/*     */     String[] resources;
/*     */     try {
/*  95 */       String propValue = this.csvProps.getProperty(filepath);
/*     */       
/*  97 */       if (propValue == null || propValue.trim().isEmpty()) {
/*  98 */         resources = new String[0];
/*     */       } else {
/* 100 */         resources = propValue.split(",");
/*     */       } 
/* 102 */     } catch (Exception e) {
/* 103 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } 
/*     */     
/* 106 */     if (resources.length == 0) {
/* 107 */       ((ManagedResource<CSVData>)managedResource).setResource(csvData);
/*     */     } else {
/* 109 */       for (String resource : resources) {
/* 110 */         if (resource.length() > 0) {
/* 111 */           readAndAddFile(getRpgDir(filepath) + resource.trim(), csvReader, csvData, managedResource);
/*     */         } else {
/* 113 */           ((ManagedResource<CSVData>)managedResource).setResource(csvData);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
            @SuppressWarnings("unchecked")
/*     */   private void readAndAddFile(String filePath, CSVReader csvReader, CSVData csvData, ManagedResource<?> managedResource) throws LoadException {
/*     */     try {
/* 120 */       System.out.println("managedResource.getDescriptionId() = " + managedResource.getName() + " filePath = " + filePath);
/* 121 */       InputStream inputStream = getFileInputStream(filePath);
/* 122 */       csvReader = new CSVReader(new InputStreamReader(inputStream));
/*     */       String[] record;
/* 124 */       while ((record = csvReader.readRecord()) != null) {
/* 125 */         csvData.add(record);
/*     */       }
/* 127 */       ((ManagedResource<CSVData>)managedResource).setResource(csvData);
/* 128 */     } catch (IOException e) {
/* 129 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } finally {
/*     */       
/* 132 */       closeSafely(csvReader, managedResource);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getRpgDir(String filepath) {
/* 137 */     return filepath.substring(0, filepath.indexOf("*."));
/*     */   }
/*     */   
/*     */   private boolean isWildCardSearch(String filepath) {
/* 141 */     return filepath.contains("/*.");
/*     */   }
/*     */   
/*     */   private void initCSVProperties() throws LoadException {
/* 145 */     ManagedResource<String> resource = new ManagedResource("configuration/csvfiles.properties", String.class, CacheType.NOT_CACHED);
/* 146 */     InputStream stream = null;
/*     */     try {
/* 148 */       stream = getFileInputStream(resource);
/* 149 */       this.csvProps.load(stream);
/* 150 */       this.initialized = true;
/* 151 */     } catch (IOException e) {
/* 152 */       e.printStackTrace();
/*     */     } finally {
/* 154 */       closeSafely(stream, resource);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\CSVLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */