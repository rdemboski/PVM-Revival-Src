/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.commons.configuration.CSVReader;
/*     */ import com.funcom.gameengine.resourcemanager.JarOpener;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceLoader;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.channels.Channel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public abstract class AbstractLoader
/*     */   implements ResourceLoader {
/*  33 */   protected static final Logger LOGGER = Logger.getLogger(AbstractLoader.class.getName());
/*     */   
/*     */   private Class<?> resourceType;
/*     */   private Set<ResourceLoader> additionalLoaders;
/*     */   private Set<Class<?>> neededLoaders;
/*     */   protected ResourceManager resourceManager;
/*     */   
/*     */   protected AbstractLoader(Class<?> resourceType) {
/*  41 */     if (resourceType == null)
/*  42 */       throw new IllegalArgumentException("resourceType = null"); 
/*  43 */     this.resourceType = resourceType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceManager(ResourceManager resourceManager) {
/*  48 */     this.resourceManager = resourceManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalLoader(ResourceLoader resourceLoader) {
/*  53 */     if (this.additionalLoaders == null)
/*  54 */       this.additionalLoaders = new HashSet<ResourceLoader>(); 
/*  55 */     this.additionalLoaders.add(resourceLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAdditionalLoader(ResourceLoader resourceLoader) {
/*  60 */     if (this.additionalLoaders != null)
/*  61 */       this.additionalLoaders.remove(resourceLoader); 
/*     */   }
/*     */   
/*     */   protected ResourceLoader getAdditionalLoaderByType(Class<?> type) {
/*  65 */     for (ResourceLoader additionalLoader : this.additionalLoaders) {
/*  66 */       if (additionalLoader.getResourceType().equals(type))
/*  67 */         return additionalLoader; 
/*  68 */     }  throw new IllegalArgumentException("No additional loader for type: " + type);
/*     */   }
/*     */   
/*     */   protected Set<ResourceLoader> getAdditionalLoaders() {
/*  72 */     return this.additionalLoaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNeededLoaderType(Class<?> type) {
/*  77 */     if (this.neededLoaders == null)
/*  78 */       this.neededLoaders = new HashSet<Class<?>>(); 
/*  79 */     this.neededLoaders.add(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeNeededLoaderType(Class<?> type) {
/*  84 */     if (this.neededLoaders != null) {
/*  85 */       this.neededLoaders.remove(type);
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Class<?>> getNeededLoaderTypes() {
/*  90 */     return this.neededLoaders;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getResourceType() {
/* 100 */     return this.resourceType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAsyncLoading() {
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public static void closeSafely(CSVReader reader, ManagedResource<?> managedResource) {
/*     */     try {
/* 110 */       if (reader != null)
/* 111 */         reader.close(); 
/* 112 */     } catch (IOException e) {
/* 113 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close CVSReader to: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeSafely(InputStream inputStream, ManagedResource<?> managedResource) {
/*     */     try {
/* 119 */       if (inputStream != null)
/* 120 */         inputStream.close(); 
/* 121 */     } catch (IOException e) {
/* 122 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close stream to: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeSafely(OutputStream outputStream, ManagedResource<?> managedResource) {
/*     */     try {
/* 128 */       if (outputStream != null)
/* 129 */         outputStream.close(); 
/* 130 */     } catch (IOException e) {
/* 131 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close outputStream while loading: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeSafely(Writer writer, ManagedResource<?> managedResource) {
/*     */     try {
/* 137 */       if (writer != null)
/* 138 */         writer.close(); 
/* 139 */     } catch (IOException e) {
/* 140 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close writer while loading: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeSafely(Channel channel, ManagedResource<?> managedResource) {
/*     */     try {
/* 146 */       if (channel != null)
/* 147 */         channel.close(); 
/* 148 */     } catch (IOException e) {
/* 149 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close channel to: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeSafely(Reader reader, ManagedResource<?> managedResource) {
/*     */     try {
/* 155 */       if (reader != null)
/* 156 */         reader.close(); 
/* 157 */     } catch (IOException e) {
/* 158 */       LOGGER.log((Priority)Level.ERROR, "Couldn't close reader to: " + managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ResourceManager getResourceManager() {
/* 163 */     return this.resourceManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadData(ManagedResource<?> managedResource, Object unloadInfo) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getUnloadInfo(ManagedResource<?> managedResource) {
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   protected File getResourceFile(ManagedResource<?> managedResource) throws FileNotFoundException {
/* 178 */     return getResourceFile(managedResource.getName());
/*     */   }
/*     */   
/*     */   protected File getResourceFile(String resourcePath) throws FileNotFoundException {
/* 182 */     Set<String> resourceRoots = this.resourceManager.getResourceRoots();
/*     */     
/* 184 */     for (String resourceRoot : resourceRoots) {
/* 185 */       File tmp = new File(resourceRoot, resourcePath);
/* 186 */       if (tmp.exists()) {
/* 187 */         return tmp;
/*     */       }
/*     */     } 
/* 190 */     throw new FileNotFoundException("Couldn't find file for relative path: " + resourcePath + ", resource roots are: " + resourceRoots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getJarInputStream(String resourcePath) throws IOException {
/* 200 */     String absResourcePath = FileUtils.makeAbsolute(resourcePath);
/*     */     
/* 202 */     Set<String> resourceRoots = this.resourceManager.getResourceRoots();
/*     */     
/* 204 */     for (String resourceRoot : resourceRoots) {
/* 205 */       if (resourceRoot.contains(".jar")) {
/* 206 */         JarFile jarFile = JarOpener.instance().getJarFile(resourceRoot.substring(0, resourceRoot.indexOf(".jar") + 4));
/* 207 */         String pathFromRoot = resourceRoot.substring(resourceRoot.indexOf(".jar") + 5);
/* 208 */         pathFromRoot = pathFromRoot.isEmpty() ? pathFromRoot : (pathFromRoot + "/");
/* 209 */         ZipEntry entry = jarFile.getEntry(pathFromRoot + absResourcePath);
/* 210 */         if (entry != null) {
/* 211 */           return jarFile.getInputStream(entry);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     throw new FileNotFoundException("Couldn't find file for relative path: " + absResourcePath + ", resource roots are: " + resourceRoots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getFileInputStream(String resourcePath) throws IOException {
/* 226 */     if (this.resourceManager.isUseJars()) {
/* 227 */       return getJarInputStream(resourcePath);
/*     */     }
/* 229 */     return new FileInputStream(getResourceFile(resourcePath));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getFileInputStream(ManagedResource<?> resource) throws IOException {
/* 240 */     return getFileInputStream(resource.getName());
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
/*     */   protected InputStream[] getInputStreams(String resourceWildcardStr) throws IOException {
/* 253 */     File pathFile = new File(resourceWildcardStr);
/* 254 */     List<File> files = listFiles(pathFile);
/*     */     
/* 256 */     InputStream[] ret = new InputStream[files.size()];
/*     */     
/* 258 */     int length = files.size();
/* 259 */     for (int i = 0; i < length; i++) {
/* 260 */       ret[i] = new FileInputStream(files.get(i));
/*     */     }
/*     */     
/* 263 */     return ret;
/*     */   }
/*     */   
/*     */   protected List<File> listFiles(File pathFile) {
/* 267 */     String nameRegexp = StringUtils.simplePatternToRegexp(pathFile.getName());
/* 268 */     Pattern namePattern = Pattern.compile(nameRegexp);
/* 269 */     PatternFilter patternFilter = new PatternFilter(namePattern);
/*     */     
/* 271 */     String parent = pathFile.getParent();
/* 272 */     if (parent == null) {
/* 273 */       parent = "";
/*     */     }
/*     */     
/* 276 */     List<File> retNames = new ArrayList<File>();
/* 277 */     Set<String> resourceRoots = this.resourceManager.getResourceRoots();
/* 278 */     for (String resourceRoot : resourceRoots) {
/* 279 */       File tmp = new File(resourceRoot, parent);
/* 280 */       if (tmp.exists()) {
/* 281 */         String[] filenames = tmp.list(patternFilter);
/* 282 */         for (String filename : filenames) {
/* 283 */           retNames.add(new File(tmp, filename));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     return retNames;
/*     */   }
/*     */   
/*     */   private static class PatternFilter implements FilenameFilter {
/*     */     private final Pattern namePattern;
/*     */     
/*     */     public PatternFilter(Pattern namePattern) {
/* 295 */       this.namePattern = namePattern;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean accept(File dir, String name) {
/* 300 */       return this.namePattern.matcher(name).matches();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\AbstractLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */