/*     */ package org.pleasantnightmare.noraxidium.util;
/*     */ 
/*     */ import com.funcom.errorhandling.DoomsdayErrorHandler;
/*     */ import com.funcom.util.DebugManager;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.border.LineBorder;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class CrashWindow
/*     */   extends JFrame
/*     */ {
/*  43 */   private double widthScale = 1.78D; private double heightScale = 1.86D;
/*     */   
/*     */   private JButton crashIconButton;
/*     */   
/*     */   private JToggleButton infoToggleButton;
/*     */   
/*     */   private JButton notNowButton;
/*     */   
/*     */   private JButton sendButton;
/*  52 */   private static final Logger LOGGER = Logger.getLogger(CrashWindow.class);
/*  53 */   JFrame crashInfoFrame = new JFrame(); private String message;
/*     */   private String sendButtonText;
/*     */   private String noButtonText;
/*     */   private String stacktrace;
/*     */   private DoomsdayErrorHandler errorHandler;
/*     */   private Thread thread;
/*     */   private Throwable throwable;
/*     */   
/*     */   public CrashWindow(String message, String sendButtonText, String noButtonText, Thread t, Throwable e, String stacktrace, DoomsdayErrorHandler errorHandler) {
/*  62 */     this.message = message;
/*  63 */     this.sendButtonText = sendButtonText;
/*  64 */     this.noButtonText = noButtonText;
/*  65 */     this.stacktrace = stacktrace;
/*  66 */     this.errorHandler = errorHandler;
/*  67 */     this.thread = t;
/*  68 */     this.throwable = e;
/*     */ 
/*     */ 
/*     */     
/*  72 */     initComponents();
/*     */     
/*  74 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/*  76 */     setLocation((int)(dim.getWidth() / 2.0D - 300.0D), (int)(dim.getHeight() / 2.0D - 250.0D));
/*  77 */     setAlwaysOnTop(true);
/*  78 */     getContentPane().setBackground(new Color(28, 75, 165));
/*     */     
/*  80 */     this.infoToggleButton.setVisible(DebugManager.getInstance().isDebugEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   private ImageIcon zoom(ImageIcon imI, double width, double height) {
/*  85 */     Image source = imI.getImage();
/*     */ 
/*     */     
/*  88 */     BufferedImage dest = new BufferedImage((int)(imI.getIconWidth() * width), (int)(imI.getIconHeight() * height), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     AffineTransform ta = new AffineTransform();
/*  94 */     ta.scale(width, height);
/*     */     
/*  96 */     Graphics2D g2d = dest.createGraphics();
/*  97 */     g2d.drawImage(source, ta, (ImageObserver)null);
/*  98 */     g2d.dispose();
/*     */     
/* 100 */     return new ImageIcon(dest);
/*     */   }
/*     */   
/*     */   private void send() {
/*     */     try {
/* 105 */       this.errorHandler.sendReports(this.thread, this.throwable);
/* 106 */       System.err.println("Report sent without errors.");
/* 107 */     } catch (Exception e1) {
/* 108 */       System.err.println("Failed to send report!");
/* 109 */       e1.printStackTrace();
/*     */     } 
/* 111 */     this.crashInfoFrame.dispose();
/* 112 */     dispose();
/* 113 */     System.exit(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 119 */     this.sendButton = new JButton();
/* 120 */     this.notNowButton = new JButton();
/* 121 */     this.crashIconButton = new JButton();
/* 122 */     this.infoToggleButton = new JToggleButton();
/*     */     
/* 124 */     setDefaultCloseOperation(3);
/* 125 */     setTitle("Pets vs Monsters - Crash!");
/* 126 */     setBackground(new Color(28, 75, 165));
/* 127 */     setResizable(false);
/* 128 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentMoved(ComponentEvent evt) {
/* 130 */             CrashWindow.this.formComponentMoved(evt);
/*     */           }
/*     */         });
/* 133 */     addFocusListener(new FocusAdapter() {
/*     */           public void focusGained(FocusEvent evt) {
/* 135 */             CrashWindow.this.formFocusGained(evt);
/*     */           }
/*     */         });
/*     */     
/* 139 */     this.sendButton.setBackground(new Color(28, 75, 165));
/* 140 */     this.sendButton.setFont(new Font("Tahoma", 1, 40));
/* 141 */     this.sendButton.setForeground(new Color(255, 255, 255));
/* 142 */     this.sendButton.setText(this.sendButtonText);
/* 143 */     this.sendButton.setBorder(null);
/* 144 */     this.sendButton.setHorizontalTextPosition(0);
/* 145 */     this.sendButton.setOpaque(false);
/* 146 */     this.sendButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 148 */             CrashWindow.this.sendButtonActionPerformed(evt);
/*     */           }
/*     */         });
/* 151 */     this.sendButton.setIcon(zoom(new ImageIcon(getClass().getResource("images/button_green_wide_default.jpg")), this.widthScale, this.heightScale));
/*     */ 
/*     */     
/* 154 */     this.sendButton.setRolloverIcon(zoom(new ImageIcon(getClass().getResource("images/button_green_wide_hover.jpg")), this.widthScale, this.heightScale));
/*     */ 
/*     */     
/* 157 */     this.sendButton.setSelectedIcon(zoom(new ImageIcon(getClass().getResource("images/button_green_wide_pressed.jpg")), this.widthScale, this.heightScale));
/*     */ 
/*     */ 
/*     */     
/* 161 */     this.notNowButton.setBackground(new Color(28, 75, 165));
/* 162 */     this.notNowButton.setFont(new Font("Tahoma", 1, 18));
/* 163 */     this.notNowButton.setForeground(new Color(200, 200, 200));
/* 164 */     this.notNowButton.setIcon(new ImageIcon(getClass().getResource("images/button_red_wide_default.jpg")));
/*     */     
/* 166 */     this.notNowButton.setText(this.noButtonText);
/* 167 */     this.notNowButton.setBorder(null);
/* 168 */     this.notNowButton.setHorizontalTextPosition(0);
/* 169 */     this.notNowButton.setRolloverIcon(new ImageIcon(getClass().getResource("images/button_red_wide_hover.jpg")));
/*     */     
/* 171 */     this.notNowButton.setSelectedIcon(new ImageIcon(getClass().getResource("images/button_red_wide_pressed.jpg")));
/*     */     
/* 173 */     this.notNowButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 175 */             CrashWindow.this.notNowButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 179 */     this.crashIconButton.setBackground(new Color(28, 75, 165));
/* 180 */     this.crashIconButton.setFont(new Font("Tahoma", 1, 48));
/* 181 */     this.crashIconButton.setForeground(new Color(255, 255, 255));
/* 182 */     this.crashIconButton.setIcon(new ImageIcon(getClass().getResource("images/magmacron_crash.jpg")));
/*     */     
/* 184 */     this.crashIconButton.setText(this.message);
/* 185 */     this.crashIconButton.setBorder(null);
/* 186 */     this.crashIconButton.setHorizontalAlignment(2);
/* 187 */     this.crashIconButton.setHorizontalTextPosition(0);
/* 188 */     this.crashIconButton.setOpaque(false);
/* 189 */     this.crashIconButton.setVerticalAlignment(1);
/*     */     
/* 191 */     this.infoToggleButton.setBackground(new Color(28, 75, 165));
/* 192 */     this.infoToggleButton.setIcon(new ImageIcon(getClass().getResource("images/help_default.jpg")));
/*     */     
/* 194 */     this.infoToggleButton.setBorder(null);
/* 195 */     this.infoToggleButton.setOpaque(false);
/* 196 */     this.infoToggleButton.setRolloverIcon(new ImageIcon(getClass().getResource("images/help_hover.jpg")));
/*     */     
/* 198 */     this.infoToggleButton.setSelectedIcon(new ImageIcon(getClass().getResource("images/help_hover.jpg")));
/*     */     
/* 200 */     this.infoToggleButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 202 */             CrashWindow.this.infoToggleButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 206 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 207 */     getContentPane().setLayout(layout);
/* 208 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.notNowButton, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.infoToggleButton, -2, 54, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 123, 32767).addComponent(this.sendButton, -2, 250, -2).addContainerGap()).addComponent(this.crashIconButton, -1, 593, 32767));
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
/* 222 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.crashIconButton, -2, -1, -2).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.infoToggleButton, GroupLayout.Alignment.TRAILING, -1, 54, 32767).addComponent(this.notNowButton, GroupLayout.Alignment.TRAILING, -2, 54, 32767)).addComponent(this.sendButton, -2, 95, -2)).addContainerGap()));
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
/* 239 */     pack();
/*     */   }
/*     */   
/*     */   private void infoToggleButtonActionPerformed(ActionEvent evt) {
/* 243 */     int x = (int)(getLocationOnScreen().getX() + (getWidth() / 2) - 250.0D);
/* 244 */     int y = (int)(getLocationOnScreen().getY() + (getHeight() / 2) - 200.0D);
/*     */     
/* 246 */     this.crashInfoFrame.dispose();
/*     */     
/* 248 */     this.crashInfoFrame = new JFrame();
/* 249 */     this.crashInfoFrame.setSize(500, 300);
/* 250 */     this.crashInfoFrame.setLocation(x, y);
/* 251 */     this.crashInfoFrame.setUndecorated(true);
/* 252 */     this.crashInfoFrame.setVisible(this.infoToggleButton.isSelected());
/* 253 */     this.crashInfoFrame.setAlwaysOnTop(true);
/*     */ 
/*     */     
/* 256 */     JTextArea crashArea = new JTextArea();
/* 257 */     crashArea.setText(this.stacktrace);
/* 258 */     crashArea.setEditable(false);
/*     */ 
/*     */     
/* 261 */     JScrollPane areaScrollPane = new JScrollPane(crashArea);
/* 262 */     areaScrollPane.setVerticalScrollBarPolicy(22);
/*     */     
/* 264 */     areaScrollPane.setPreferredSize(new Dimension(250, 250));
/* 265 */     areaScrollPane.setBorder(new LineBorder(Color.BLACK, 5));
/*     */     
/* 267 */     this.crashInfoFrame.add(areaScrollPane, "Center");
/*     */   }
/*     */   
/*     */   private void notNowButtonActionPerformed(ActionEvent evt) {
/* 271 */     this.crashInfoFrame.dispose();
/* 272 */     dispose();
/* 273 */     System.exit(-1);
/*     */   }
/*     */   
/*     */   private void sendButtonActionPerformed(ActionEvent evt) {
/* 277 */     send();
/*     */   }
/*     */   
/*     */   private void formComponentMoved(ComponentEvent evt) {
/* 281 */     if (this.crashInfoFrame.isVisible()) {
/* 282 */       this.infoToggleButton.setSelected(false);
/* 283 */       this.crashInfoFrame.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void formFocusGained(FocusEvent evt) {
/* 288 */     if (this.crashInfoFrame.isVisible()) {
/* 289 */       this.infoToggleButton.setSelected(false);
/* 290 */       this.crashInfoFrame.dispose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\org\pleasantnightmare\noraxidiu\\util\CrashWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */