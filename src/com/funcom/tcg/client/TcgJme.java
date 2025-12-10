/*     */ package com.funcom.tcg.client;
/*     */ import com.funcom.commons.utils.ApplicationRelativePathUtil;
/*     */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
/*     */ import com.funcom.errorhandling.DoomsdayErrorHandler;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.inspector.HardwareSpec;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.startup.OneInstanceCheck;
import com.funcom.tcg.utils.ConfigurationUtils;
import com.funcom.tcg.utils.ServerConfigurationException;
import com.funcom.util.Browser;
import com.funcom.util.ConsoleOutputUtils;
/*     */ import com.funcom.util.URLDesktopIconBuilder;
import com.jme.system.DisplaySystem;

/*     */ import java.awt.Color;
import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.basic.BasicProgressBarUI;

import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.julbridge.JULLog4jBridge;
/*     */ 
/*     */ public class TcgJme {
/*  36 */   private static final Logger LOGGER = Logger.getLogger(TcgJme.class.getName());
/*     */ 
/*     */   
/*     */   private static final String LOGGING_FILE = "logging_client.xml";
/*     */   
/*     */   private static final String ARG_MAP = "-map=";
/*     */   
/*     */   private static final String ARG_RESOURCE_CONFIG = "resconfig";
/*     */   
/*     */   private static final String ARG_DESKTOP_ICON = "tcgDesktopIcon";
/*     */   
/*     */   private static PvmSplashFrame splash;
/*     */   
/*  49 */   public static HardwareSpec MIN_CONFIG = new HardwareSpec(2000, 2, 768, -1);
/*     */   
/*     */   private TcgGame game;
/*     */   
/*     */   private WorldCoordinate defaultPlayerPosition;
/*     */   private static TcgJme INSTANCE;
/*     */   private Collection<String> resourceRoots;
/*     */   private static ConfigErrorWindow configErrorWindow;
/*     */   private static long ramMBTotal;
/*     */   private static long coresTotal;
/*     */   private static long cpuMHzTotal;
/*     */   
/*     */   public TcgJme() {}
/*     */   
/*     */   public TcgJme(DoomsdayErrorHandler doomsdayErrorHandler) {
/*  64 */     INSTANCE = this;
/*     */ 
/*     */     
/*  67 */     this.defaultPlayerPosition = new WorldCoordinate(5, 5, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0);
/*     */ 
/*     */     
/*  70 */     this.game = new TcgGame();
/*  71 */     ApplicationRelativePathUtil.init(this.game.getAppId());
/*  72 */     this.game.addCrashDataProviders(doomsdayErrorHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseParameters(String[] args) {
/*  77 */     for (String arg : args) {
/*  78 */       if (arg.startsWith("-map=")) {
/*  79 */         this.defaultPlayerPosition.setMapId(arg.substring("-map=".length()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void start() {
/*     */     try {
/*  87 */       String patchProperty = System.getProperty("tcg.patch");
/*  88 */       if (patchProperty != null && patchProperty.equalsIgnoreCase("true")) {
/*  89 */         long timeNow = System.currentTimeMillis();
/*  90 */         int pri = Thread.currentThread().getPriority();
/*  91 */         Thread.currentThread().setPriority(1);
/*  92 */         PatchManager patchManager = new PatchManager();
/*  93 */         patchManager.setPriority(10);
/*  94 */         patchManager.start();
/*  95 */         while (!patchManager.isDownLoadComplete());
/*     */         
/*  97 */         LOGGER.info("Patching took = " + (System.currentTimeMillis() - timeNow) + " ms");
/*  98 */         Thread.currentThread().setPriority(pri);
/*     */       } 
/* 100 */     } catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 105 */     String makeDesktopIcon = System.getProperty("tcgDesktopIcon");
/* 106 */     if (makeDesktopIcon != null && makeDesktopIcon.equalsIgnoreCase("true")) {
/* 107 */       URLDesktopIconBuilder urlDesktopIconBuilder = new URLDesktopIconBuilder();
/* 108 */       urlDesktopIconBuilder.makeDesktopIcon();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (this.resourceRoots == null) {
/*     */       
/* 119 */       this.resourceRoots = new HashSet<String>(Arrays.asList(System.getProperty("resconfig").split(",")));
/*     */       
/* 121 */       if (this.resourceRoots.isEmpty()) {
/* 122 */         throw new IllegalArgumentException("No resource configuration script provided! Use VM option -Dresconfig='<paths>' to specify paths.");
/*     */       }
/*     */       
/* 125 */       Collection<String> tmp = new HashSet<String>(this.resourceRoots);
/* 126 */       this.resourceRoots.clear();
/* 127 */       for (String resourceRoot : tmp) {
/* 128 */         this.resourceRoots.add(ApplicationRelativePathUtil.getApplicationRelativeBasePath(resourceRoot));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.game.setResourceRoots(this.resourceRoots);
/* 134 */     this.game.start();
/*     */ 
/*     */ 
/*     */     
/* 138 */     System.exit(0);
/*     */   }
/*     */   
/*     */   static class ImagePanel
/*     */     extends JComponent {
/*     */     private Image image;
/*     */     
/*     */     public ImagePanel(Image image) {
/* 146 */       this.image = image;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void paintComponent(Graphics g) {
/* 151 */       g.drawImage(this.image, 0, 0, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 157 */     AchaDoomsdayErrorHandler doomsdayErrorHandler = new AchaDoomsdayErrorHandler(new TcgAchaDataFeeder());
/* 158 */     LOGGER.setLevel(Level.INFO);
/*     */     
/* 160 */     Toolkit.getDefaultToolkit();
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
/*     */     try {
/* 182 */       EventQueue.invokeLater(new Runnable() {
/*     */             public void run() {
/* 184 */               TcgJme.splash = new TcgJme.PvmSplashFrame();
/* 185 */               TcgJme.LOGGER.info("Splash screen initialized");
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 190 */       ConsoleOutputUtils.setupFileOutput();
/*     */ 
/*     */       
/* 193 */       configureLogging();
/*     */ 
/*     */ 
/*     */       
/* 197 */       LOGGER.info("Checking Game Already Running - starting");
/* 198 */       checkAppIsAlreadyRunning();
/* 199 */       LOGGER.info("Checking Game Already Running - complete");
/*     */ 
/*     */       
/* 202 */       if (System.getProperty("tcg.ram") != null && System.getProperty("tcg.cpu") != null && System.getProperty("tcg.cores") != null) {
/*     */         
/* 204 */         int cpu = Integer.parseInt(System.getProperty("tcg.cpu"));
/* 205 */         int cores = Integer.parseInt(System.getProperty("tcg.cores"));
/* 206 */         int ram = Integer.parseInt(System.getProperty("tcg.ram"));
/* 207 */         MIN_CONFIG = new HardwareSpec(cpu, cores, ram, -1);
/* 208 */         LOGGER.info("Min config overriden: CPU: " + MIN_CONFIG.getCpuSpeed() + " | CORES: " + MIN_CONFIG.getCpuCores() + " | RAM: " + MIN_CONFIG.getRamAmount());
/*     */       } 
/*     */ 
/*     */       
/* 212 */       LOGGER.info("Check Minimum config - starting");
/* 213 */       checkMinConfig();
/* 214 */       LOGGER.info("Check Minimum config - complete");
/*     */       
/* 216 */       updateSplashProgress(10);
/*     */ 
/*     */ 
/*     */       
/* 220 */       LOGGER.info("Check 64 bit runtime - starting");
/* 221 */       if (is64Runtime()) {
/* 222 */         if (System.getProperty("tcg.skip64") != null && System.getProperty("tcg.skip64").equals("true")) {
/*     */           
/* 224 */           LOGGER.info("Check 64 bit runtime - skip dialog");
/*     */         } else {
/*     */           
/* 227 */           HttpMetrics.postEvent(HttpMetrics.Event.SOUND_64);
/* 228 */           showDialog(ConfigErrorType.NO_SOUND_64);
/* 229 */           configErrorWindow = null;
/*     */         } 
/*     */       }
/* 232 */       LOGGER.info("Check 64 bit runtime - complete");
/*     */ 
/*     */ 
/*     */       
/* 236 */       HttpMetrics.postEvent(HttpMetrics.Event.CLIENT_START);
/* 237 */       updateSplashProgress(20);
/*     */       
/* 239 */       TcgJme app = new TcgJme((DoomsdayErrorHandler)doomsdayErrorHandler);
/* 240 */       app.parseParameters(args);
/* 241 */       app.start();
/*     */     }
/* 243 */     catch (Throwable t) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 248 */       HttpMetrics.postEvent(HttpMetrics.Event.CLIENT_CRASHED);
/* 249 */       doomsdayErrorHandler.uncaughtException(Thread.currentThread(), t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkAppIsAlreadyRunning() {
/* 254 */     OneInstanceCheck oneInstanceCheck = new OneInstanceCheck();
/* 255 */     if (oneInstanceCheck.isAppActive()) {
/* 256 */       HttpMetrics.postEvent(HttpMetrics.Event.ALREADY_RUNNING);
/* 257 */       showDialog(ConfigErrorType.ALREADY_RUNNING);
/* 258 */       System.exit(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String[] getWmiSpecs() {
/*     */     try {
/* 264 */       String wmiScript = "Dim processorComponents, computerComponents,  item, cores, cpu, ram\nSet processorComponents = GetObject(\"winmgmts:\").ExecQuery(\"Select MaxClockSpeed, NumberOfCores from Win32_Processor\")\nSet computerComponents = GetObject(\"winmgmts:\").ExecQuery(\"Select TotalPhysicalMemory from Win32_ComputerSystem\")\nFor Each item in processorComponents\n\tcpu = item.MaxClockSpeed\n\tcores = item.NumberOfCores\nNext\nFor Each item in computerComponents\n\tram = item.TotalPhysicalMemory\nNext\nwscript.echo cpu\nwscript.echo cores\nwscript.echo ram";
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
/* 279 */       String scriptFileName = System.getenv("USERPROFILE").trim() + File.separator + "wmi_script.vbs";
/* 280 */       File scriptFile = new File(scriptFileName);
/* 281 */       FileWriter fileWriter = new FileWriter(scriptFile);
/* 282 */       fileWriter.write(wmiScript);
/* 283 */       fileWriter.flush();
/* 284 */       fileWriter.close();
/*     */       
/* 286 */       ProcessBuilder pb = new ProcessBuilder(new String[] { "cscript.exe", scriptFileName });
/* 287 */       Process process = pb.start();
/* 288 */       BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
/* 289 */       String[] output = new String[3];
/* 290 */       String line = "";
/* 291 */       int index = 0;
/* 292 */       while ((line = input.readLine()) != null) {
/* 293 */         if (!line.contains("Microsoft") && !line.equals("")) {
/* 294 */           output[index] = line.trim();
/* 295 */           index++;
/*     */         } 
/*     */       } 
/* 298 */       process.destroy();
/* 299 */       scriptFile.delete();
/* 300 */       return output;
/* 301 */     } catch (IOException io) {
/*     */       
/* 303 */       return new String[] { "0", "0", "0" };
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkMinConfig() {
/*     */     try {
/* 309 */       if (System.getProperty("os.name").toLowerCase().contains("win")) {
/* 310 */         String[] WMIResults = getWmiSpecs();
/*     */         
/* 312 */         cpuMHzTotal = Long.parseLong(WMIResults[0]);
/* 313 */         coresTotal = Long.parseLong(WMIResults[1]);
/* 314 */         long ramByteTotal = Long.parseLong(WMIResults[2]);
/*     */         
/* 316 */         ramMBTotal = (long)(ramByteTotal / Math.pow(1024.0D, 2.0D));
/*     */         
/* 318 */         if (cpuMHzTotal >= MIN_CONFIG.getCpuSpeed()) {
/*     */ 
/*     */           
/* 321 */           if (ramMBTotal < MIN_CONFIG.getRamAmount()) {
/*     */             
/* 323 */             HttpMetrics.postEvent(HttpMetrics.Event.TOO_LOW_SPEC);
/* 324 */             showDialog(ConfigErrorType.MIN_CONFIG_RAM);
/*     */           } 
/* 326 */         } else if (coresTotal >= MIN_CONFIG.getCpuCores()) {
/*     */ 
/*     */           
/* 329 */           if (ramMBTotal < MIN_CONFIG.getRamAmount()) {
/*     */             
/* 331 */             HttpMetrics.postEvent(HttpMetrics.Event.TOO_LOW_SPEC);
/* 332 */             showDialog(ConfigErrorType.MIN_CONFIG_RAM);
/*     */           } 
/*     */         } else {
/*     */           
/* 336 */           HttpMetrics.postEvent(HttpMetrics.Event.TOO_LOW_SPEC);
/* 337 */           showDialog(ConfigErrorType.MIN_CONFIG_CPU);
/*     */         } 
/*     */       } 
/* 340 */     } catch (Exception e) {
/* 341 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 344 */     LOGGER.info("System config acceptable. CPU " + cpuMHzTotal + " | CORES: " + coresTotal + " | RAM: " + ramMBTotal);
/*     */   }
/*     */   
/*     */   public static void showDialog(ConfigErrorType type) {
/*     */     while (true) {
/* 349 */       if (configErrorWindow == null) {
/*     */         
/* 351 */         EventQueue.invokeLater(new Runnable() {
/*     */               public void run() {
/* 353 */                 if (TcgJme.splash != null) {
/* 354 */                   TcgJme.splash.setDismissState(true);
/* 355 */                   TcgJme.splash.setVisible(false);
/* 356 */                   TcgJme.splash.dispose();
/* 357 */                   HttpMetrics.postEvent(HttpMetrics.Event.SPLASH_DISPOSED);
/*     */                 } 
/*     */               }
/*     */             });
/*     */ 
/*     */         
/* 363 */         configErrorWindow = new ConfigErrorWindow(type, ramMBTotal, cpuMHzTotal);
/* 364 */         configErrorWindow.setVisible(true);
/* 365 */         LOGGER.info("Dialog displayed, TYPE: " + type.name());
/*     */       } 
/*     */       try {
/* 368 */         Thread.sleep(50L);
/* 369 */       } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       if (!configErrorWindow.isOnline()) {
/* 375 */         configErrorWindow.dispose();
/*     */         return;
/*     */       } 
/*     */     }  } private static void configureLogging() {
/* 379 */     JULLog4jBridge.assimilate();
/* 380 */     File loggingConfigFile = null;
/*     */     try {
/* 382 */       loggingConfigFile = ConfigurationUtils.findConfigFile("logging_client.xml");
/* 383 */       DOMConfigurator.configure(loggingConfigFile.getAbsolutePath());
/* 384 */     } catch (ServerConfigurationException e) {
/* 385 */       System.err.println("ERROR: Cannot find logging file: " + loggingConfigFile);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static WorldCoordinate getDefaultPlayerPosition() {
/* 390 */     return INSTANCE.defaultPlayerPosition;
/*     */   }
/*     */   
/*     */   public static boolean is64Runtime() {
/* 394 */     return System.getProperty("os.arch").toLowerCase().contains("64");
/*     */   }
/*     */   
/*     */   public static PvmSplashFrame getSplash() {
/* 398 */     return splash;
/*     */   }
/*     */   
/*     */   public static void updateSplashProgress(final int progress) {
/* 402 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 404 */             if (TcgJme.splash.getProgressBar().isIndeterminate()) {
/* 405 */               TcgJme.splash.getProgressBar().setIndeterminate(false);
/*     */             }
/* 407 */             TcgJme.splash.getProgressBar().setValue(progress);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static class GradientProgressBarUI extends BasicProgressBarUI {
/* 413 */     int width = 0; int height = 0;
/*     */     
/* 415 */     GradientPaint redtowhite = new GradientPaint(0.0F, 0.0F, new Color(99, 154, 0), 0.0F, 30.0F, new Color(197, 226, 46));
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics g, JComponent c) {
/* 420 */       Graphics2D g2 = (Graphics2D)g;
/* 421 */       g2.setPaint(this.redtowhite);
/* 422 */       super.paint(g2, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintDeterminate(Graphics g, JComponent c) {
/* 427 */       this.width = c.getWidth();
/* 428 */       this.height = c.getHeight();
/* 429 */       Graphics2D g2 = (Graphics2D)g;
/* 430 */       g2.setPaint(this.redtowhite);
/* 431 */       g2.fill(new RoundRectangle2D.Double(0.0D, 0.0D, (((JProgressBar)c).getValue() * 8), c.getHeight(), 5.0D, 5.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PvmSplashFrame
/*     */     extends JFrame
/*     */     implements WindowListener
/*     */   {
/*     */     private boolean dismissState = false;
/*     */     
/*     */     private JProgressBar progressBar;
/*     */     
/*     */     private JProgressBar indeterminateProgressBar;
/*     */     
/*     */     private JLabel bgdLabel;
/*     */     
/*     */     public PvmSplashFrame() {
/* 449 */       UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
/* 450 */       UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
/*     */       
/* 452 */       initComponents();
/* 453 */       addWindowListener(this);
/*     */       
/* 455 */       int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.0D - getSize().getWidth() / 2.0D);
/* 456 */       int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.0D - getSize().getHeight() / 2.0D);
/* 457 */       setLocation(x, y);
/* 458 */       setVisible(true);
/* 459 */       toFront();
/* 460 */       setIconImage((new ImageIcon(getClass().getResource("images/PvMIcon_32.jpg"))).getImage());
/*     */     }
/*     */     
/*     */     private void initComponents() {
/* 464 */       this.indeterminateProgressBar = new JProgressBar();
/* 465 */       this.progressBar = new JProgressBar();
/* 466 */       this.bgdLabel = new JLabel();
/* 467 */       getContentPane().setBackground(Color.BLACK);
/*     */       
/* 469 */       setDefaultCloseOperation(3);
/* 470 */       setTitle("Pets vs. Monsters");
/* 471 */       setBackground(new Color(0, 0, 0));
/* 472 */       setResizable(false);
/* 473 */       setUndecorated(true);
/*     */ 
/*     */       
/* 476 */       this.indeterminateProgressBar.setUI(new TcgJme.GradientProgressBarUI());
/* 477 */       this.indeterminateProgressBar.setBackground(new Color(0, 0, 0));
/* 478 */       this.indeterminateProgressBar.setForeground(new Color(167, 214, 11));
/* 479 */       this.indeterminateProgressBar.setIndeterminate(true);
/* 480 */       this.indeterminateProgressBar.setBorder(new LineBorder(Color.BLACK, 1));
/*     */ 
/*     */       
/* 483 */       this.progressBar.setUI(new TcgJme.GradientProgressBarUI());
/* 484 */       this.progressBar.setBackground(new Color(0, 0, 0));
/* 485 */       this.progressBar.setForeground(new Color(132, 212, 65));
/* 486 */       this.progressBar.setMaximum(101);
/* 487 */       this.progressBar.setOpaque(true);
/* 488 */       this.progressBar.setBorder(new LineBorder(Color.BLACK, 1));
/*     */       
/* 490 */       this.bgdLabel.setIcon(new ImageIcon(getClass().getResource("images/splash1.jpg")));
/*     */       
/* 492 */       this.bgdLabel.setPreferredSize(new Dimension(800, 500));
/*     */       
/* 494 */       GroupLayout layout = new GroupLayout(getContentPane());
/* 495 */       getContentPane().setLayout(layout);
/* 496 */       layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.bgdLabel, -1, 800, 32767).addComponent(this.progressBar, GroupLayout.Alignment.TRAILING, -1, 800, 32767).addGroup(layout.createSequentialGroup().addGap(347, 347, 347).addComponent(this.indeterminateProgressBar, -2, 105, -2).addContainerGap(348, 32767)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 505 */       layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.bgdLabel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 56, 32767).addComponent(this.indeterminateProgressBar, -2, 24, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.progressBar, -2, -1, -2)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 515 */       pack();
/*     */     }
/*     */ 
/*     */     
/*     */     public JProgressBar getProgressBar() {
/* 520 */       return this.progressBar;
/*     */     }
/*     */     
/*     */     public boolean isDismissState() {
/* 524 */       return this.dismissState;
/*     */     }
/*     */     
/*     */     public void setDismissState(boolean dismissState) {
/* 528 */       this.dismissState = dismissState;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void windowOpened(WindowEvent event) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void windowActivated(WindowEvent event) {}
/*     */ 
/*     */     
/*     */     public void windowDeactivated(WindowEvent event) {
/* 541 */       if (!this.dismissState && !DisplaySystem.getDisplaySystem().isFullScreen()) {
/* 542 */         toFront();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void windowIconified(WindowEvent event) {}
/*     */ 
/*     */     
/*     */     public void windowDeiconified(WindowEvent event) {}
/*     */ 
/*     */     
/*     */     public void windowClosed(WindowEvent event) {}
/*     */     
/*     */     public void windowClosing(WindowEvent event) {}
/*     */   }
/*     */   
/*     */   static class linkListener
/*     */     implements HyperlinkListener
/*     */   {
/*     */     public void hyperlinkUpdate(HyperlinkEvent e) {
/* 562 */       if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED && 
/* 563 */         e instanceof HyperlinkEvent)
/*     */         try {
/* 565 */           Browser.openUrl(e.getURL().toString());
/* 566 */         } catch (IOException e1) {
/* 567 */           TcgJme.LOGGER.error("Error while t;rying to reach the minimum spec web page: " + e1);
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgJme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */