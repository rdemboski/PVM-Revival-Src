/*     */ package com.funcom.tcg.client;
/*     */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
import com.funcom.util.Browser;

/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/*     */ import java.io.IOException;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextPane;
/*     */ import javax.swing.LayoutStyle;
import javax.swing.border.LineBorder;
/*     */ 
/*     */ public class ConfigErrorWindow extends JFrame implements WindowListener {
/*     */   private JButton exitButton;
/*     */   private JTextPane infoPane;
/*     */   private JScrollPane infoScrollPane;
/*  22 */   private String creationDate = ""; private JLabel buffer; private JLabel redHeadLabel; private JButton okButton;
/*     */   private AchaDoomsdayErrorHandler doomsdayErrorHandler;
/*     */   private volatile boolean online = true;
/*     */   private ConfigErrorType type;
/*     */   private long ram;
/*     */   private long cpu;
/*     */   
/*     */   public ConfigErrorWindow(ConfigErrorType type, long ram, long cpu) {
/*  30 */     initComponents();
/*     */     
/*  32 */     this.type = type;
/*  33 */     this.ram = ram;
/*  34 */     this.cpu = cpu;
/*     */     
/*  36 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/*  37 */     setLocation((int)(dim.getWidth() / 2.0D - getPreferredSize().getWidth() / 2.0D), (int)(dim.getHeight() / 2.0D - getPreferredSize().getHeight() / 2.0D));
/*     */ 
/*     */     
/*  40 */     setTitle("Pets vs Monsters - " + TcgGame.getLocalizedText("dialog.title.error", new String[0]));
/*  41 */     this.exitButton.setText(TcgGame.getLocalizedText("dialog.toolowspec.exit", new String[0]));
/*  42 */     this.okButton.setText(TcgGame.getLocalizedText("dialog.toolowspec.runagain", new String[0]));
/*     */     
/*  44 */     this.infoPane.setFont(new Font("Tahoma", 0, 18));
/*     */     
/*  46 */     setMode(type);
/*     */   }
/*     */   
/*     */   public void setDoomsdayErrorHandler(AchaDoomsdayErrorHandler doomsdayErrorHandler) {
/*  50 */     this.doomsdayErrorHandler = doomsdayErrorHandler;
/*     */   }
/*     */   
/*     */   public void updateInfoText(String info) {
/*  54 */     this.infoPane.setText(info);
/*     */   }
/*     */   
/*     */   private void setMode(ConfigErrorType type) {
/*  58 */     this.type = type;
/*     */     
/*  60 */     this.okButton.setVisible(false);
/*  61 */     this.buffer.setVisible(false);
/*     */     
/*  63 */     switch (type) {
/*     */       case LOW_RESOLUTION:
/*  65 */         this.infoPane.setText(TcgGame.getLocalizedText("text.messagebox.lowresolution", new String[0]) + " " + (DisplayResolutionHelper.getInstance()).MINIMUM_WIDTH + " x " + (DisplayResolutionHelper.getInstance()).MINIMUM_HEIGHT);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DRIVER_ISSUE:
/*  70 */         this.infoPane.setText(TcgGame.getLocalizedText("dialog.driverissue", new String[0]));
/*     */         break;
/*     */       case MIN_CONFIG_RAM:
/*  73 */         this.infoPane.setText(TcgGame.getLocalizedText("dialog.toolowspec.ram", new String[] { "" + this.ram, "" + TcgJme.MIN_CONFIG.getRamAmount() }));
/*     */       case MIN_CONFIG_CPU:
/*  75 */         if (this.infoPane.getText().isEmpty()) {
/*  76 */           this.infoPane.setText(TcgGame.getLocalizedText("dialog.toolowspec.cpu", new String[] { "" + this.cpu, "" + TcgJme.MIN_CONFIG.getCpuSpeed() }));
/*     */         }
/*  78 */         this.buffer.setVisible(true);
/*  79 */         this.buffer.setText("");
/*  80 */         this.okButton.setVisible(true);
/*     */         
/*  82 */         this.okButton.setText("<html><center>" + TcgGame.getLocalizedText("configerrorwindow.link.minspec", new String[0]) + "</center></html>");
/*     */         break;
/*     */       case ALREADY_RUNNING:
/*  85 */         this.infoPane.setText(TcgGame.getLocalizedText("dialog.alreadyrunning", new String[0]));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case NO_SOUND_64:
/*  91 */         setDefaultCloseOperation(2);
/*  92 */         this.infoPane.setText(TcgGame.getLocalizedText("dialog.nosound", new String[0]));
/*  93 */         this.okButton.setText("OK");
/*  94 */         this.okButton.setVisible(true);
/*  95 */         this.exitButton.setVisible(false);
/*     */         break;
/*     */       case UNSATISFIED_LINK:
/*  98 */         this.infoPane.setText(TcgGame.getLocalizedText("dialog.java", new String[0]));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void exitAction() {
/* 106 */     processWindowEvent(new WindowEvent(this, 201));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 111 */     if (!visible) {
/* 112 */       this.online = false;
/*     */     }
/* 114 */     super.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void okAction() {
/* 118 */     if (this.type.equals(ConfigErrorType.MIN_CONFIG_RAM) || this.type.equals(ConfigErrorType.MIN_CONFIG_CPU)) {
/*     */       try {
/* 120 */         Browser.openUrl(TcgGame.getLocalizedText("configerrorwindow.message.minspec", new String[0]));
/* 121 */       } catch (IOException e) {
/* 122 */         e.printStackTrace();
/*     */       } 
/* 124 */       exitAction();
/*     */     } 
/* 126 */     this.online = false;
/*     */   }
/*     */   
/*     */   public boolean isOnline() {
/* 130 */     return this.online;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 135 */     this.okButton = new JButton();
/* 136 */     this.exitButton = new JButton();
/* 137 */     this.redHeadLabel = new JLabel();
/* 138 */     this.infoScrollPane = new JScrollPane();
/* 139 */     this.infoPane = new JTextPane();
/* 140 */     this.buffer = new JLabel();
/*     */     
/* 142 */     setDefaultCloseOperation(3);
/* 143 */     setTitle("Pets vs Monsters");
/* 144 */     setBackground(new Color(28, 75, 165));
/* 145 */     setResizable(false);
/*     */     
/* 147 */     addWindowListener(this);
/*     */     
/* 149 */     getContentPane().setBackground(new Color(28, 75, 165));
/*     */     
/* 151 */     this.okButton.setBackground(new Color(28, 75, 165));
/* 152 */     this.okButton.setFont(new Font("Tahoma", 1, 16));
/* 153 */     this.okButton.setForeground(new Color(255, 255, 255));
/* 154 */     this.okButton.setIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_default.jpg")));
/*     */     
/* 156 */     this.okButton.setBorder(null);
/* 157 */     this.okButton.setHorizontalTextPosition(0);
/* 158 */     this.okButton.setOpaque(false);
/* 159 */     this.okButton.setPressedIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_pressed.jpg")));
/*     */     
/* 161 */     this.okButton.setRolloverIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_hover.jpg")));
/*     */     
/* 163 */     this.okButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 165 */             ConfigErrorWindow.this.okAction();
/*     */           }
/*     */         });
/*     */     
/* 169 */     this.exitButton.setBackground(new Color(28, 75, 165, 0));
/* 170 */     this.exitButton.setFont(new Font("Tahoma", 1, 16));
/* 171 */     this.exitButton.setForeground(new Color(255, 255, 255));
/* 172 */     this.exitButton.setIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_default.jpg")));
/*     */     
/* 174 */     this.exitButton.setBorder(null);
/* 175 */     this.exitButton.setHorizontalTextPosition(0);
/* 176 */     this.exitButton.setOpaque(false);
/* 177 */     this.exitButton.setPressedIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_pressed.jpg")));
/*     */     
/* 179 */     this.exitButton.setRolloverIcon(new ImageIcon(getClass().getResource("images/button_blue_wide_hover.jpg")));
/*     */     
/* 181 */     this.exitButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 183 */             ConfigErrorWindow.this.exitAction();
/*     */           }
/*     */         });
/*     */     
/* 187 */     this.redHeadLabel.setBackground(new Color(28, 75, 165));
/* 188 */     this.redHeadLabel.setIcon(new ImageIcon(getClass().getResource("images/redhead.jpg")));
/*     */     
/* 190 */     this.infoScrollPane.setBorder(null);
/*     */     
/* 192 */     this.infoPane.setBackground(new Color(28, 75, 165, 0));
/* 193 */     this.infoPane.setBorder(null);
/* 194 */     this.infoPane.setEditable(false);
/* 195 */     this.infoPane.setFont(new Font("Tahoma", 0, 22));
/* 196 */     this.infoPane.setForeground(new Color(255, 255, 255));
/* 197 */     this.infoScrollPane.setViewportView(this.infoPane);
/* 198 */     this.infoScrollPane.setBackground(new Color(28, 75, 165));
/*     */ 
/*     */     
/* 201 */     this.buffer.setBackground(new Color(28, 75, 165));
/* 202 */     this.buffer.setIcon(new ImageIcon(getClass().getResource("images/blue.jpg")));
/* 203 */     this.buffer.setForeground(new Color(200, 200, 200));
/* 204 */     this.buffer.setFont(new Font("Tahoma", 1, 12));
/*     */ 
/*     */     
/* 207 */     this.buffer.setBorder(new LineBorder(new Color(28, 75, 165)));
/* 208 */     this.buffer.setHorizontalTextPosition(0);
/*     */     
/* 210 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 211 */     getContentPane().setLayout(layout);
/* 212 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.redHeadLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.infoScrollPane, -1, 200, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.exitButton, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buffer, -1, 168, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.okButton, -2, 140, -2))).addContainerGap()));
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
/* 230 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.infoScrollPane, -1, 240, 32767).addComponent(this.redHeadLabel, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.okButton, GroupLayout.Alignment.TRAILING, -2, 54, 32767).addComponent(this.exitButton, GroupLayout.Alignment.TRAILING, -2, 54, 32767).addComponent(this.buffer, GroupLayout.Alignment.TRAILING, -1, 54, 32767)).addContainerGap()));
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
/* 249 */     pack();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowOpened(WindowEvent event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void windowActivated(WindowEvent event) {}
/*     */ 
/*     */   
/*     */   public void windowDeactivated(WindowEvent event) {}
/*     */ 
/*     */   
/*     */   public void windowIconified(WindowEvent event) {}
/*     */ 
/*     */   
/*     */   public void windowDeiconified(WindowEvent event) {}
/*     */ 
/*     */   
/*     */   public void windowClosed(WindowEvent event) {}
/*     */ 
/*     */   
/*     */   public void windowClosing(WindowEvent event) {
/* 274 */     this.online = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\ConfigErrorWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */