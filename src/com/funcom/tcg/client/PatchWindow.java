/*     */ package com.funcom.tcg.client;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ 
/*     */ class PatchWindow extends JFrame {
/*  17 */   private static final Dimension CONF_DEFAULT_WINDOW_SETTINGS = new Dimension(1024, 768);
/*     */   
/*     */   public static final String CONF_DEFAULT_LOADING_IMG = "loading_001.png";
/*     */   
/*     */   public static final String CONF_DEFAULT_PROGRESS_IMG = "catgif.gif";
/*     */   private BufferedImage backgroundImage;
/*     */   
/*     */   public PatchWindow() throws MalformedURLException, LWJGLException {
/*     */     final Dimension windowSize;
/*  26 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  27 */     setTitle(ResourceBundle.getBundle("gameplay-text").getString("downloader.title"));
/*  28 */     setDefaultCloseOperation(3);
/*  29 */     setResizable(false);
/*  30 */     DisplayMode displayMode = Display.getDisplayMode();
/*     */ 
/*     */     
/*  33 */     if (displayMode.getWidth() < CONF_DEFAULT_WINDOW_SETTINGS.getWidth() && displayMode.getHeight() < CONF_DEFAULT_WINDOW_SETTINGS.getHeight()) {
/*     */       
/*  35 */       windowSize = new Dimension(displayMode.getWidth(), displayMode.getHeight());
/*     */     } else {
/*  37 */       windowSize = new Dimension(CONF_DEFAULT_WINDOW_SETTINGS.width, CONF_DEFAULT_WINDOW_SETTINGS.height);
/*     */     } 
/*     */     
/*     */     try {
/*  41 */       this.backgroundImage = ImageIO.read(new URL(PatchManager.getPatchPath() + "loading_001.png"));
/*  42 */     } catch (IOException e) {
/*  43 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  46 */     JPanel mainPanel = new JPanel(null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         protected void paintComponent(Graphics g)
/*     */         {
/*  53 */           g.drawImage(PatchWindow.this.backgroundImage, 0, 0, windowSize.width, windowSize.height - 50, null);
/*  54 */           super.paintComponent(g);
/*     */         }
/*     */       };
/*     */     
/*  58 */     mainPanel.setCursor(Cursor.getPredefinedCursor(3));
/*     */     
/*  60 */     ImageIcon icon = new ImageIcon(new URL(PatchManager.getPatchPath() + "catgif.gif"));
/*     */     
/*  62 */     JLabel loadingTextLabel = new JLabel("Downloading...");
/*  63 */     loadingTextLabel.setHorizontalAlignment(0);
/*  64 */     loadingTextLabel.setFont(new Font("SansSerif", 1, 12));
/*  65 */     loadingTextLabel.setBackground(Color.yellow);
/*  66 */     loadingTextLabel.setForeground(Color.white);
/*     */     
/*  68 */     Rectangle loadingTxtRec = new Rectangle(windowSize.width - icon.getIconWidth() - 40, windowSize.height - loadingTextLabel.getHeight() - 90, icon.getIconWidth(), 40);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     loadingTextLabel.setBounds(loadingTxtRec);
/*     */     
/*  77 */     mainPanel.add(loadingTextLabel);
/*     */     
/*  79 */     JLabel progressImageLabel = new JLabel(icon);
/*     */     
/*  81 */     Rectangle imageRec = new Rectangle(windowSize.width - icon.getIconWidth() - 40, windowSize.height - icon.getIconHeight() - 90, icon.getIconWidth(), icon.getIconHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     progressImageLabel.setBounds(imageRec);
/*     */ 
/*     */ 
/*     */     
/*  91 */     mainPanel.add(progressImageLabel);
/*     */     
/*  93 */     getContentPane().add(mainPanel, "Center");
/*     */     
/*  95 */     JLabel jLabel = new JLabel("THIS MAY TAKE SEVERAL MINUTES");
/*  96 */     jLabel.setHorizontalAlignment(0);
/*  97 */     jLabel.setFont(new Font("SansSerif", 1, 32));
/*     */     
/*  99 */     jLabel.setForeground(Color.white);
/* 100 */     getContentPane().setBackground(Color.black);
/* 101 */     getContentPane().add(jLabel, "South");
/*     */ 
/*     */     
/* 104 */     setSize(windowSize);
/* 105 */     setLocation((toolkit.getScreenSize()).width / 2 - getWidth() / 2, (toolkit.getScreenSize()).height / 2 - getHeight() / 2);
/* 106 */     setVisible(true);
/* 107 */     toFront();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws MalformedURLException, LWJGLException {
/* 113 */     new PatchWindow();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\PatchWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */