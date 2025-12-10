/*     */ package com.funcom.gameengine.inspector;
/*     */ 
/*     */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class HardwareInspector
/*     */ {
/*  18 */   private static final Logger LOGGER = Logger.getLogger(HardwareInspector.class);
/*  19 */   private static final HardwareInspector INSTANCE = new HardwareInspector();
/*  20 */   private static final String USER_HOME = System.getenv("USERPROFILE");
/*  21 */   private static final String PLUGIN_PATH = System.getProperty("tcg.plugin.path");
/*  22 */   private static final String FILE_NAME = ".funcom" + File.separatorChar + "tcg" + File.separatorChar + "dxdiag_output.txt";
/*     */   
/*     */   private static final String PARAMETERS = " /t ";
/*     */   
/*     */   private static final String BITS64 = " /64bit";
/*     */   private static final String INVOKE_DXDIAG = "dxdiag";
/*  28 */   private static final Pattern CPU_SPEED_MHZ = Pattern.compile("Processor:.*?([0-9]+?)MHz");
/*  29 */   private static final Pattern CPU_SPEED_GHZ = Pattern.compile("Processor:.*?([0-9\\.]+?)GHz");
/*  30 */   private static final Pattern CPU_CORE_NUMBER = Pattern.compile("Processor:.*?([0-9]+?) CPUs");
/*     */   
/*  32 */   private static final Pattern RAM_AMOUNT = Pattern.compile("Memory:.*?([0-9]+?)MB");
/*     */   
/*     */   public static HardwareInspector instance() {
/*  35 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static boolean isSupportingThisPlatform() {
/*  39 */     return System.getProperty("os.name").toLowerCase().contains("win");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean is64Bits() {
/*  44 */     return (System.getenv("ProgramFiles(x86)") != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HardwareSpec createFromDxDiagData(String dxDiagData) {
/*  51 */     int cpuSpeed = -1;
/*  52 */     int cpuCores = -1;
/*  53 */     int ramAmount = -1;
/*  54 */     int shaderModel = -1;
/*     */     
/*  56 */     Matcher cpuSpeedMatcher = CPU_SPEED_MHZ.matcher(dxDiagData);
/*  57 */     if (cpuSpeedMatcher.find()) {
/*  58 */       cpuSpeed = Integer.parseInt(cpuSpeedMatcher.group(1));
/*     */     } else {
/*  60 */       cpuSpeedMatcher = CPU_SPEED_GHZ.matcher(dxDiagData);
/*  61 */       if (cpuSpeedMatcher.find()) {
/*  62 */         cpuSpeed = (int)Math.ceil((Float.parseFloat(cpuSpeedMatcher.group(1)) * 1000.0F));
/*     */       }
/*     */     } 
/*  65 */     Matcher cpuCoreMatcher = CPU_CORE_NUMBER.matcher(dxDiagData);
/*  66 */     if (cpuCoreMatcher.find()) {
/*  67 */       cpuCores = Integer.parseInt(cpuCoreMatcher.group(1));
/*  68 */       System.out.println(cpuCores);
/*     */     } 
/*     */     
/*  71 */     Matcher ramAmountMatcher = RAM_AMOUNT.matcher(dxDiagData);
/*  72 */     if (ramAmountMatcher.find()) {
/*  73 */       ramAmount = Integer.parseInt(ramAmountMatcher.group(1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     HardwareSpec hardwareSpec = new HardwareSpec();
/*  80 */     hardwareSpec.putSpec(HardwareSpec.Hardware.CPU, Integer.valueOf(cpuSpeed));
/*  81 */     hardwareSpec.putSpec(HardwareSpec.Hardware.CPU_CORES, Integer.valueOf(cpuCores));
/*  82 */     hardwareSpec.putSpec(HardwareSpec.Hardware.RAM, Integer.valueOf(ramAmount));
/*  83 */     hardwareSpec.putSpec(HardwareSpec.Hardware.SHADER_MODEL, Integer.valueOf(shaderModel));
/*  84 */     return hardwareSpec;
/*     */   }
/*     */ 
/*     */   
/*     */   public HardwareSpec createFromDxDiagFile(URL url) throws RuntimeException {
/*     */     try {
/*  90 */       StringBuilder dxDiagContents = readDxDiagContents(url.openStream());
/*  91 */       return createFromDxDiagData(dxDiagContents.toString());
/*  92 */     } catch (IOException e) {
/*  93 */       LOGGER.error("IOException during DxDiag read", e);
/*  94 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HardwareSpec createForThisSystem(AchaDoomsdayErrorHandler doomsdayErrorHandler) throws IOException {
/* 102 */     return createForThisSystem(Boolean.valueOf(false), doomsdayErrorHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public HardwareSpec createForThisSystem(Boolean forceDxdiagGeneration, AchaDoomsdayErrorHandler doomsdayErrorHandler) throws IOException {
/* 107 */     if (!isSupportingThisPlatform()) {
/* 108 */       throw new IllegalStateException("This class works only with Windows.");
/*     */     }
/*     */     
/*     */     try {
/*     */       File dxdiagFile;
/* 113 */       if (PLUGIN_PATH != null) {
/* 114 */         dxdiagFile = new File(PLUGIN_PATH, FILE_NAME);
/*     */       } else {
/* 116 */         dxdiagFile = new File(USER_HOME, FILE_NAME);
/* 117 */       }  if (forceDxdiagGeneration.booleanValue() || !dxdiagFile.exists()) {
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
/* 130 */         File parentFile = dxdiagFile.getParentFile();
/* 131 */         if (parentFile != null) {
/* 132 */           parentFile.mkdirs();
/*     */         }
/* 134 */         dxdiagFile.createNewFile();
/*     */ 
/*     */         
/* 137 */         String dxdiagCmd = "dxdiag";
/* 138 */         if (is64Bits()) {
/* 139 */           dxdiagCmd = dxdiagCmd + " /64bit";
/*     */         }
/* 141 */         Process process = Runtime.getRuntime().exec(dxdiagCmd + " /t " + dxdiagFile.getPath());
/* 142 */         LOGGER.debug("Pausing to wait for process end...");
/* 143 */         int errCode = process.waitFor();
/* 144 */         if (errCode != 0) {
/* 145 */           LOGGER.warn("DxDiag execution returned nonzero exit code: " + errCode);
/* 146 */           throw new RuntimeException("DxDiag execution returned nonzero exit code: " + errCode);
/*     */         } 
/*     */       } 
/*     */       
/* 150 */       StringBuilder sb = readDxDiagContents(new FileInputStream(dxdiagFile.getPath()));
/* 151 */       doomsdayErrorHandler.setDxdiagData(sb.toString());
/* 152 */       return createFromDxDiagData(sb.toString());
/*     */     }
/* 154 */     catch (InterruptedException e) {
/* 155 */       LOGGER.error("Process thread interrupted.", e);
/* 156 */       throw new IllegalStateException(e);
/* 157 */     } catch (IOException e) {
/* 158 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDxdiagCreationDate() {
/* 167 */     File dxdiagFile = new File(USER_HOME, FILE_NAME);
/* 168 */     if (dxdiagFile.exists()) {
/* 169 */       return new Date(dxdiagFile.lastModified());
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private StringBuilder readDxDiagContents(InputStream inputStream) throws IOException {
/* 176 */     StringBuilder sb = new StringBuilder();
/* 177 */     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
/*     */     try {
/* 179 */       char[] buffer = new char[512];
/*     */       int read;
/* 181 */       while ((read = br.read(buffer)) != -1)
/* 182 */         sb.append(buffer, 0, read); 
/* 183 */       return sb;
/*     */     } finally {
/* 185 */       br.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\inspector\HardwareInspector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */