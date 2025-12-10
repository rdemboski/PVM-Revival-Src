/*     */ package com.funcom.gameengine.resourcemanager;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.commons.utils.RealSystemTime;
/*     */ import com.funcom.commons.utils.TimeSystem;
/*     */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
/*     */ import com.funcom.errorhandling.DefaultDataFeeder;
/*     */ import com.funcom.gameengine.resourcemanager.downloader.JarCrcTags;
/*     */ import com.funcom.gameengine.resourcemanager.downloader.ResourceDownloaderListener;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.CheckedInputStream;
/*     */ import java.util.zip.CheckedOutputStream;
/*     */ import org.jdom.Content;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ public class ResourceDownloader
/*     */   implements Runnable, JarCrcTags {
/*     */   private static final int MAX_ATTEMPTS = 5;
/*  45 */   private List<ResourceDownloaderListener> listeners = new ArrayList<ResourceDownloaderListener>();
/*     */   private Document currentDocument;
/*     */   private ResourceManager resourceManager;
/*  48 */   private Map<String, Long> serverCrcs = new HashMap<String, Long>();
/*  49 */   private Map<String, Long> crcs = new HashMap<String, Long>();
/*  50 */   private BadLinkedBlockingQueue<String> downloadRequestQueue = new BadLinkedBlockingQueue<String>();
/*  51 */   private Set<String> downloadRequests = Collections.synchronizedSet(new HashSet<String>());
/*  52 */   private Set<String> completedRequests = Collections.synchronizedSet(new HashSet<String>());
/*  53 */   private List<String> dependencyList = new LinkedList<String>();
/*     */   private String path;
/*     */   private String downloadToPath;
/*     */   private boolean running = true;
/*     */   private long verifyLength;
/*     */   private long verifiedSize;
/*     */   private int downloadFileLength;
/*     */   private int currentDownloadFileSize;
/*     */   private int currentNumberOfAttempts;
/*  62 */   private String lastCrashedOn = null;
/*     */   
/*     */   public ResourceDownloader(ResourceManager resourceManager) {
/*  65 */     this.resourceManager = resourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     System.setProperty("java.net.preferIPv4Stack", "true");
/*     */ 
/*     */     
/*  73 */     loadDownloaderProperties();
/*  74 */     Set<String> resourceRoots = resourceManager.getResourceRoots();
/*  75 */     for (String str : resourceRoots) {
/*  76 */       for (String dependency : this.dependencyList) {
/*  77 */         if (str.equals(this.downloadToPath + dependency + "/")) {
/*  78 */           this.completedRequests.add(dependency);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  84 */     File file = new File(this.downloadToPath + "crc-values.xml");
/*  85 */     if (file.exists()) {
/*     */       
/*     */       try {
/*  88 */         InputStream inputStream = new FileInputStream(file);
/*     */         try {
/*  90 */           SAXBuilder saxBuilder = new SAXBuilder();
/*  91 */           this.currentDocument = saxBuilder.build(inputStream);
/*     */         } finally {
/*  93 */           inputStream.close();
/*     */         } 
/*  95 */       } catch (JDOMException e) {
/*  96 */         throw new RuntimeException(e);
/*  97 */       } catch (IOException e) {
/*  98 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/* 101 */       this.currentDocument = new Document();
/* 102 */       this.currentDocument.setRootElement(new Element("CRCS"));
/*     */     } 
/*     */     
/* 105 */     Element root = this.currentDocument.getRootElement();
/* 106 */     List<Element> jars = root.getChildren("jar-crc");
/* 107 */     for (Element jar : jars) {
/* 108 */       long value; String name = jar.getAttributeValue("file-name");
/*     */       
/*     */       try {
/* 111 */         value = jar.getAttribute("crc-value").getLongValue();
/* 112 */       } catch (DataConversionException e) {
/* 113 */         throw new RuntimeException(e);
/*     */       } 
/* 115 */       this.crcs.put(name, Long.valueOf(value));
/*     */     } 
/*     */     
/*     */     try {
/* 119 */       downloadServerXml();
/* 120 */     } catch (IOException e) {
/* 121 */       throw new RuntimeException(e);
/* 122 */     } catch (JDOMException e) {
/* 123 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadDownloaderProperties() {
/* 128 */     DependencyMapList dependencyMapList = DependencyMapList.instance(this.resourceManager);
/* 129 */     List<String> mapsList = dependencyMapList.getMapList();
/*     */     
/* 131 */     for (String map : mapsList) {
/* 132 */       this.dependencyList.add(map + ".jar");
/*     */     }
/*     */     
/* 135 */     this.path = dependencyMapList.getDownloaderPath();
/*     */     
/* 137 */     this.downloadToPath = dependencyMapList.getDownloadToPath();
/*     */   }
/*     */   
/*     */   public boolean isDownloaded(String fileName) {
/* 141 */     return this.completedRequests.contains(fileName);
/*     */   }
/*     */   
/*     */   public boolean isCompletelyDownloaded(String fileName) {
/* 145 */     int index = this.dependencyList.indexOf(fileName);
/* 146 */     for (int i = 0; i <= index; i++) {
/* 147 */       if (!isDownloaded(this.dependencyList.get(i)))
/* 148 */         return false; 
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public void downloadMapAndDependencies(String fileName) {
/* 154 */     int index = this.dependencyList.indexOf(fileName);
/* 155 */     for (int i = 0; i <= index; i++) {
/* 156 */       String file = this.dependencyList.get(i);
/* 157 */       addRequest(file);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getJarIndex(String fileName) {
/* 162 */     return this.dependencyList.indexOf(fileName);
/*     */   }
/*     */   
/*     */   private void addCompletedRequest(String fileName) {
/* 166 */     this.completedRequests.add(fileName);
/* 167 */     this.resourceManager.addResourceRoot(this.downloadToPath + fileName + "/");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addRequest(String request) {
/* 172 */     if (this.completedRequests.contains(request) || this.downloadRequests.contains(request)) {
/*     */       return;
/*     */     }
/* 175 */     if (alreadyDownloaded(request)) {
/* 176 */       addCompletedRequest(request); return;
/*     */     } 
/* 178 */     if (!this.serverCrcs.containsKey(request)) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     this.downloadRequests.add(request);
/* 183 */     this.downloadRequestQueue.add(request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alreadyDownloaded(String request) {
/* 189 */     File file = new File(this.downloadToPath + request);
/* 190 */     if (file.exists()) {
/*     */       
/* 192 */       Long beleivedCrc = this.crcs.get(request);
/* 193 */       Long serverCrc = this.serverCrcs.get(request);
/* 194 */       if (serverCrc == null || !serverCrc.equals(beleivedCrc)) {
/* 195 */         if (!file.delete()) {
/* 196 */           throw new RuntimeException("File " + file.getAbsolutePath() + " can not be deleted.");
/*     */         }
/* 198 */         return false;
/*     */       } 
/* 200 */       notifyStartedVerifying(request);
/* 201 */       this.verifyLength = file.length();
/*     */       
/* 203 */       CRC32 crc = new CRC32();
/*     */       
/*     */       try {
/* 206 */         InputStream stream = new CheckedInputStream(new BufferedInputStream(new FileInputStream(file)), crc);
/*     */ 
/*     */ 
/*     */         
/* 210 */         boolean done = false;
/* 211 */         this.verifiedSize = 0L;
/*     */         try {
/* 213 */           while (!done) {
/* 214 */             byte[] buffer = new byte[4096];
/* 215 */             int read = stream.read(buffer);
/* 216 */             this.verifiedSize += read;
/* 217 */             done = (read == -1);
/*     */           } 
/*     */         } finally {
/* 220 */           stream.close();
/*     */         } 
/* 222 */       } catch (IOException e) {
/* 223 */         throw new RuntimeException(e);
/*     */       } 
/* 225 */       notifyFinishedVerifiying();
/* 226 */       this.verifyLength = 0L;
/* 227 */       this.verifiedSize = 0L;
/*     */       
/* 229 */       long actualCrcValue = crc.getValue();
/* 230 */       return beleivedCrc.equals(Long.valueOf(actualCrcValue));
/*     */     } 
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 237 */     Thread.currentThread().setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new AchaDoomsdayErrorHandler((AchaDoomsdayErrorHandler.AchaBugreportDataFeeder)new DefaultDataFeeder("ResourceDownloader", "NO_EMAIL", "ResourceDownloader")));
/* 238 */     while (this.running) {
/* 239 */       processNextRequest();
/*     */     }
/*     */   }
/*     */   
/*     */   private void processNextRequest() {
/* 244 */     String request = null;
/*     */     try {
/* 246 */       request = this.downloadRequestQueue.take();
/* 247 */       this.crcs.put(request, this.serverCrcs.get(request));
/* 248 */       saveDocument();
/*     */       
/* 250 */       URL url = new URL(this.path + request);
/* 251 */       URLConnection connection = url.openConnection();
/* 252 */       connection.setUseCaches(false);
/* 253 */       System.out.println("Downloader opened connection for request: " + request);
/* 254 */       File file = new File(this.downloadToPath + request);
/* 255 */       System.out.println("File created/opened for request: " + request);
/* 256 */       if (file.exists() && 
/* 257 */         !file.delete()) {
/* 258 */         final String path = "can not delete file: " + file.getPath();
/* 259 */         GameTaskQueueManager.getManager().getQueue("update").enqueue(new Callable()
/*     */             {
/*     */               public Object call() throws Exception {
/* 262 */                 throw new RuntimeException(path);
/*     */               }
/*     */             });
/* 265 */         throw new RuntimeException(path);
/*     */       } 
/* 267 */       long currentPosition = file.exists() ? file.length() : -1L;
/* 268 */       CRC32 crcOut = new CRC32();
/* 269 */       OutputStream fOut = new CheckedOutputStream(new BufferedOutputStream(new FileOutputStream(file, file.exists())), crcOut);
/* 270 */       this.currentDownloadFileSize = Math.max((int)currentPosition, 0);
/*     */       
/* 272 */       if (currentPosition > 0L)
/* 273 */         connection.setRequestProperty("Range", "bytes=" + currentPosition + "-"); 
/* 274 */       this.downloadFileLength = connection.getContentLength() + this.currentDownloadFileSize;
/*     */       
/* 276 */       CRC32 crc = new CRC32();
/* 277 */       InputStream inputStream = null;
/*     */       try {
/* 279 */         inputStream = new CheckedInputStream(new BufferedInputStream(connection.getInputStream()), crc);
/* 280 */         notifyStartedDownloading(request);
/*     */         
/* 282 */         boolean done = false;
/* 283 */         while (!done && this.running) {
/* 284 */           byte[] buffer = new byte[1024];
/* 285 */           int read = inputStream.read(buffer);
/* 286 */           this.currentDownloadFileSize += read;
/* 287 */           done = (read == -1);
/* 288 */           if (!done)
/* 289 */             fOut.write(buffer, 0, read); 
/*     */         } 
/* 291 */         fOut.flush();
/*     */       } finally {
/* 293 */         fOut.close();
/* 294 */         if (inputStream != null)
/* 295 */           inputStream.close(); 
/*     */       } 
/* 297 */       long inCrcValue = crc.getValue();
/* 298 */       long outCrcValue = crcOut.getValue();
/*     */       
/* 300 */       if (inCrcValue == outCrcValue && inCrcValue == ((Long)this.crcs.get(request)).longValue()) {
/* 301 */         addCompletedRequest(request);
/* 302 */         this.downloadRequests.remove(request);
/*     */       } else {
/* 304 */         this.downloadRequestQueue.addToFront(request);
/*     */       } 
/* 306 */       notifyFinishedDownloading();
/* 307 */       this.downloadFileLength = 0;
/* 308 */       this.currentDownloadFileSize = 0;
/* 309 */     } catch (MalformedURLException e) {
/* 310 */       throw new RuntimeException(e);
/* 311 */     } catch (IOException e) {
/* 312 */       if (!request.equals(this.lastCrashedOn)) {
/* 313 */         this.currentNumberOfAttempts = 0;
/* 314 */         this.lastCrashedOn = request;
/*     */       } 
/* 316 */       this.currentNumberOfAttempts++;
/* 317 */       if (this.currentNumberOfAttempts < 5) {
/*     */         
/* 319 */         this.downloadRequestQueue.addToFront(request);
/*     */         return;
/*     */       } 
/* 322 */       String errorString = "Server returned HTTP response code: ";
/* 323 */       if (e.getMessage().contains(errorString + 'Ǹ') || e.getMessage().contains(errorString + 'Ƙ') || e.getMessage().contains("Connection timed out")) {
/*     */         
/* 325 */         this.downloadRequestQueue.addToFront(request);
/*     */         return;
/*     */       } 
/* 328 */       throw new RuntimeException(e);
/* 329 */     } catch (InterruptedException e) {
/* 330 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveDocument() {
/* 335 */     Element root = this.currentDocument.getRootElement();
/* 336 */     root.removeContent();
/* 337 */     for (String fileName : this.crcs.keySet()) {
/* 338 */       Element jarElement = new Element("jar-crc");
/* 339 */       jarElement.setAttribute("file-name", fileName);
/* 340 */       jarElement.setAttribute("crc-value", ((Long)this.crcs.get(fileName)).toString());
/* 341 */       root.addContent((Content)jarElement);
/*     */     } 
/* 343 */     XMLOutputter outputter = new XMLOutputter();
/*     */     
/*     */     try {
/* 346 */       FileOutputStream outputStream = new FileOutputStream(this.downloadToPath + "crc-values.xml");
/* 347 */       outputter.output(this.currentDocument, outputStream);
/* 348 */       outputStream.close();
/* 349 */     } catch (IOException e) {
/* 350 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 355 */     ResourceManager resourceManager = new DefaultResourceManager();
/* 356 */     resourceManager.addResourceRoot(System.getProperty("tcg.resourcepath") + "");
/* 357 */     resourceManager.initDefaultLoaders();
/* 358 */     GlobalTime.setInstance((TimeSystem)new RealSystemTime());
/* 359 */     ResourceDownloader downloader = new ResourceDownloader(resourceManager);
/* 360 */     downloader.path = "file:../";
/*     */     try {
/* 362 */       downloader.downloadServerXml();
/* 363 */     } catch (IOException e) {
/* 364 */       e.printStackTrace();
/* 365 */     } catch (JDOMException e) {
/* 366 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 369 */     downloader.downloadMapAndDependencies("011_rb_icecaveboss.jar");
/* 370 */     Thread thread = new Thread(downloader);
/* 371 */     thread.setPriority(2);
/* 372 */     thread.start();
/*     */   }
/*     */   
/*     */   private void downloadServerXml() throws IOException, JDOMException {
/* 376 */     URL url = new URL(this.path + "crc-values.xml");
/*     */     
/* 378 */     InputStream inputStream = url.openStream();
/* 379 */     SAXBuilder saxBuilder = new SAXBuilder();
/* 380 */     Document doc = saxBuilder.build(inputStream);
/*     */     
/* 382 */     inputStream.close();
/*     */     
/* 384 */     Element root = doc.getRootElement();
/* 385 */     List<Element> jars = root.getChildren("jar-crc");
/* 386 */     for (Element jar : jars) {
/* 387 */       String name = jar.getAttributeValue("file-name");
/* 388 */       long value = jar.getAttribute("crc-value").getLongValue();
/* 389 */       this.serverCrcs.put(name, Long.valueOf(value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addListener(ResourceDownloaderListener listener) {
/* 394 */     synchronized (this.listeners) {
/* 395 */       this.listeners.add(listener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeListener(ResourceDownloaderListener listener) {
/* 400 */     synchronized (this.listeners) {
/* 401 */       this.listeners.remove(listener);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyStartedDownloading(String fileName) {
/* 406 */     synchronized (this.listeners) {
/* 407 */       for (ResourceDownloaderListener listener : this.listeners)
/* 408 */         listener.startedDownloading(fileName); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyFinishedDownloading() {
/* 413 */     synchronized (this.listeners) {
/* 414 */       for (ResourceDownloaderListener listener : this.listeners)
/* 415 */         listener.finishedDownloading(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyStartedVerifying(String fileName) {
/* 420 */     synchronized (this.listeners) {
/* 421 */       for (ResourceDownloaderListener listener : this.listeners)
/* 422 */         listener.startedVerifying(fileName); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyFinishedVerifiying() {
/* 427 */     synchronized (this.listeners) {
/* 428 */       for (ResourceDownloaderListener listener : this.listeners)
/* 429 */         listener.finishedVerifying(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getVerifiedPercent() {
/* 434 */     return (float)(this.verifiedSize * 1000L / this.verifyLength * 1000L) / 10.0F;
/*     */   }
/*     */   
/*     */   public int getFileDownloadLength() {
/* 438 */     return this.downloadFileLength;
/*     */   }
/*     */   
/*     */   public int getCurrentFileDownloadSize() {
/* 442 */     return this.currentDownloadFileSize;
/*     */   }
/*     */   
/*     */   public int getNumberOfDownloadRequestsQueued() {
/* 446 */     return this.downloadRequestQueue.size();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 450 */     this.running = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */