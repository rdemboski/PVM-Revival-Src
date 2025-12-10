/*     */ package com.funcom.tcg.client;
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
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class PatchManager extends Thread {
/*  21 */   private static final String AUTHSTRING = "Basic " + (new BASE64Encoder()).encode("tcg:tcgx".getBytes());
/*     */   public static final String DEPLOY_DIR = "deploy";
/*     */   private static final String PROPERTIES_FILE = "build_number.properties";
/*     */   private static final String DEFAULT_DISPLAY_PROPERTIES_FILE = "properties.cfg";
/*     */   public static final String PROPERTY_TCG_LOCAL_PATH = "tcg.local.path";
/*     */   public static final String PROPERTY_USER_HOME = "user.home";
/*     */   public static final String PROPERTY_TCG_BINARY_RESOURCE_PATH = "tcg.binaryresourcepath";
/*     */   public static final String PROPERTY_TCG_RESOURCE_PATH = "tcg.resourcepath";
/*     */   public static final String CONF_BUILD_NUMBER = "build.number";
/*     */   public static final String ARG_RESOURCE_CONFIG = "resconfig";
/*     */   
/*     */   public static String getPatchPath() {
/*  33 */     String devPath = "http://casual2/tcg/";
/*  34 */     String livePath = "http://pvm.funcom.com/tcg/";
/*     */     
/*  36 */     boolean liveLaunch = Boolean.parseBoolean(System.getProperty("live", "true"));
/*  37 */     return liveLaunch ? "http://pvm.funcom.com/tcg/" : "http://casual2/tcg/";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean downLoadComplete = false;
/*     */   
/*     */   public void run() {
/*  44 */     PatchWindow pd = null;
/*     */     try {
/*  46 */       pd = new PatchWindow();
/*  47 */     } catch (MalformedURLException e) {
/*  48 */       e.printStackTrace();
/*  49 */     } catch (LWJGLException e) {
/*  50 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  53 */     String localPath = null;
/*     */     try {
/*  55 */       localPath = setupDirsAndSystemProps();
/*  56 */     } catch (IOException e) {
/*  57 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  60 */     boolean downLoadFiles = false;
/*     */     try {
/*  62 */       downLoadFiles = buildIsNew(localPath);
/*  63 */     } catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     } 
/*  66 */     if (downLoadFiles) {
/*  67 */       downloadDefaultDisplayProperties(localPath);
/*  68 */       downloadAndUnpackJar("deploy", "config.jar", localPath);
/*  69 */       downloadAndUnpackJar("deploy", "resources.jar", localPath);
/*  70 */       downloadAndUnpackJar("deploy", "resources_ab.jar", localPath);
/*     */     } 
/*     */     
/*  73 */     if (pd != null) {
/*  74 */       pd.dispose();
/*     */     }
/*  76 */     this.downLoadComplete = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private String setupDirsAndSystemProps() throws IOException {
/*  81 */     System.setProperty("resconfig", System.getProperty("tcg.binaryresourcepath") + "," + System.getProperty("tcg.resourcepath"));
/*     */     
/*  83 */     System.setProperty("tcg.local.path", System.getProperty("user.home") + System.getProperty("tcg.local.path"));
/*  84 */     String localPath = System.getProperty("tcg.local.path");
/*     */     
/*  86 */     File systemdir = new File(localPath);
/*  87 */     if (!systemdir.exists()) {
/*  88 */       systemdir.mkdirs();
/*     */     }
/*     */     
/*  91 */     File alienBrainDir = new File(System.getProperty("tcg.local.path") + System.getProperty("tcg.binaryresourcepath"));
/*  92 */     if (!alienBrainDir.exists()) {
/*  93 */       alienBrainDir.mkdirs();
/*     */     }
/*  95 */     System.setProperty("tcg.binaryresourcepath", alienBrainDir.getCanonicalPath() + File.separator);
/*     */     
/*  97 */     File resourceDir = new File(System.getProperty("tcg.local.path") + System.getProperty("tcg.resourcepath"));
/*  98 */     if (!resourceDir.exists()) {
/*  99 */       resourceDir.mkdirs();
/*     */     }
/* 101 */     System.setProperty("tcg.resourcepath", resourceDir.getCanonicalPath());
/*     */     
/* 103 */     File configDir = new File(System.getProperty("tcg.local.path") + File.separator + "config");
/* 104 */     if (!configDir.exists()) {
/* 105 */       configDir.mkdirs();
/*     */     }
/* 107 */     return localPath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void downloadDefaultDisplayProperties(String localPath) {
/*     */     try {
/* 114 */       URL url = new URL(getPatchPath() + "properties.cfg");
/* 115 */       URLConnection urlCon = url.openConnection();
/* 116 */       InputStream is = (InputStream)urlCon.getContent();
/*     */       
/* 118 */       File f = new File(localPath + File.separator + "properties.cfg");
/*     */       
/* 120 */       OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
/*     */       
/* 122 */       byte[] buffer = new byte[1024];
/* 123 */       int len = 0;
/* 124 */       int completeLength = urlCon.getContentLength();
/* 125 */       int read = 0;
/* 126 */       while ((len = is.read(buffer)) > 0) {
/* 127 */         if (completeLength != -1) {
/* 128 */           read += len;
/*     */         }
/* 130 */         out.write(buffer, 0, len);
/*     */       } 
/* 132 */       out.close();
/* 133 */       is.close();
/* 134 */     } catch (MalformedURLException e) {
/* 135 */       e.printStackTrace();
/* 136 */     } catch (IOException e) {
/* 137 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean buildIsNew(String localPath) {
/*     */     try {
/* 146 */       URL url = new URL(getPatchPath() + "deploy" + "/" + "build_number.properties");
/* 147 */       URLConnection urlCon = url.openConnection();
/* 148 */       urlCon.setRequestProperty("Authorization", AUTHSTRING);
/* 149 */       InputStream is = (InputStream)urlCon.getContent();
/*     */       
/* 151 */       File oldFile = new File(localPath + File.separator + "build_number.properties");
/*     */       
/* 153 */       int oldBuildNumber = 0;
/* 154 */       if (oldFile.exists()) {
/* 155 */         Properties oldProps = new Properties();
/* 156 */         FileInputStream stream = new FileInputStream(oldFile);
/* 157 */         oldProps.load(stream);
/* 158 */         stream.close();
/*     */         
/*     */         try {
/* 161 */           oldBuildNumber = Integer.parseInt(oldProps.getProperty("build.number"));
/* 162 */         } catch (NumberFormatException e) {
/* 163 */           return true;
/*     */         } 
/* 165 */         oldFile.renameTo(new File(localPath + File.separator + "build_number.properties" + ".old"));
/*     */       } 
/*     */       
/* 168 */       File f = new File(localPath + File.separator + "build_number.properties");
/*     */       
/* 170 */       OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
/*     */       
/* 172 */       byte[] buffer = new byte[1024];
/* 173 */       int len = 0;
/* 174 */       int completeLength = urlCon.getContentLength();
/* 175 */       int read = 0;
/* 176 */       while ((len = is.read(buffer)) > 0) {
/* 177 */         if (completeLength != -1) {
/* 178 */           read += len;
/*     */         }
/* 180 */         out.write(buffer, 0, len);
/*     */       } 
/* 182 */       out.close();
/* 183 */       is.close();
/*     */       
/* 185 */       Properties newProperties = new Properties();
/* 186 */       FileInputStream inStream = new FileInputStream(f);
/* 187 */       newProperties.load(inStream);
/* 188 */       inStream.close();
/* 189 */       int newBuildNumber = 0;
/*     */       try {
/* 191 */         newBuildNumber = Integer.parseInt(newProperties.getProperty("build.number"));
/* 192 */       } catch (NumberFormatException e) {
/* 193 */         return true;
/*     */       } 
/* 195 */       return (newBuildNumber > oldBuildNumber);
/* 196 */     } catch (MalformedURLException e) {
/* 197 */       e.printStackTrace();
/* 198 */     } catch (IOException e) {
/* 199 */       e.printStackTrace();
/*     */     } 
/* 201 */     return true;
/*     */   }
/*     */   
/*     */   public void downloadAndUnpackJar(String libcontainer, String jarName, String localPath) {
/* 205 */     InputStream is = null;
/*     */     try {
/* 207 */       URL url = new URL(getPatchPath() + libcontainer + "/" + jarName);
/* 208 */       URLConnection urlCon = url.openConnection();
/*     */       
/* 210 */       urlCon.setRequestProperty("Authorization", AUTHSTRING);
/* 211 */       is = (InputStream)urlCon.getContent();
/*     */       
/* 213 */       File f = new File(localPath + File.separator + jarName);
/*     */       
/* 215 */       OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
/*     */       
/* 217 */       byte[] buffer = new byte[1024];
/* 218 */       int len = 0;
/* 219 */       int completeLength = urlCon.getContentLength();
/* 220 */       int read = 0;
/* 221 */       while ((len = is.read(buffer)) > 0) {
/* 222 */         if (completeLength != -1) {
/* 223 */           read += len;
/*     */         }
/* 225 */         out.write(buffer, 0, len);
/*     */       } 
/* 227 */       out.close();
/* 228 */       is.close();
/*     */       
/* 230 */       JarFile jf = new JarFile(f);
/*     */       
/* 232 */       extractFiles(is, buffer, jf, localPath);
/*     */       
/* 234 */       jf.close();
/* 235 */       f.delete();
/*     */     }
/* 237 */     catch (IOException e) {
/* 238 */       e.printStackTrace();
/* 239 */     } catch (InterruptedException e) {
/* 240 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 243 */         if (is != null) {
/* 244 */           is.close();
/*     */         }
/* 246 */       } catch (IOException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractFiles(InputStream is, byte[] buffer, JarFile jf, String localPath) throws IOException, InterruptedException {
/* 253 */     Enumeration<JarEntry> entries = jf.entries();
/* 254 */     while (entries.hasMoreElements()) {
/* 255 */       JarEntry file = entries.nextElement();
/* 256 */       if (file.getName().startsWith("META-INF")) {
/*     */         continue;
/*     */       }
/* 259 */       File f2 = new File(localPath + File.separator + file.getName());
/* 260 */       if (file.isDirectory()) {
/* 261 */         f2.mkdirs();
/*     */       } else {
/* 263 */         InputStream is2 = new BufferedInputStream(jf.getInputStream(file));
/* 264 */         OutputStream fos = new BufferedOutputStream(new FileOutputStream(f2)); int len;
/* 265 */         while ((len = is2.read(buffer)) > 0) {
/* 266 */           fos.write(buffer, 0, len);
/*     */         }
/* 268 */         fos.close();
/* 269 */         is.close();
/*     */       } 
/* 271 */       sleep(5L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDownLoadComplete() {
/* 277 */     return this.downLoadComplete;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\PatchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */