/*     */ package com.funcom.server.tracker;
/*     */ import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
import java.util.Set;

import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
import javax.swing.KeyStroke;
/*     */ import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class ServerSelectDialog extends JDialog {
/*     */   private JPanel contentPane;
/*     */   private JButton buttonOK;
/*     */   private JButton buttonCancel;
/*     */   private JList responseList;
/*     */   
/*     */   public ServerSelectDialog(String serviceId) throws IOException {
/*  25 */     super((Frame)null, "Select Server");
/*     */     
/*  27 */     setupUI();
/*     */     
/*  29 */     setContentPane(this.contentPane);
/*  30 */     setModal(true);
/*  31 */     getRootPane().setDefaultButton(this.buttonOK);
/*     */     
/*  33 */     this.responseList.addListSelectionListener(new ListSelectionListener() {
/*     */           public void valueChanged(ListSelectionEvent e) {
/*  35 */             TrackerResponse response = (TrackerResponse) ServerSelectDialog.this.responseList.getSelectedValue();
/*  36 */             if (response != null) {
/*  37 */               ServerSelectDialog.this.hostField.setText(response.getAddr().getHostAddress());
/*     */             }
/*     */           }
/*     */         });
/*     */     
/*  42 */     this.client = new TrackerClient(serviceId);
/*  43 */     this.client.start();
/*  44 */     this.client.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  46 */             ServerSelectDialog.this.reloadList();
/*     */           }
/*     */         });
/*  49 */     this.client.sendRequest();
/*     */     
/*  51 */     this.buttonOK.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  53 */             ServerSelectDialog.this.onOK();
/*     */           }
/*     */         });
/*     */     
/*  57 */     this.buttonCancel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  59 */             ServerSelectDialog.this.onCancel();
/*     */           }
/*     */         });
/*     */     
/*  63 */     this.refreshButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  65 */             ServerSelectDialog.this.client.getResponses().clear();
/*  66 */             ServerSelectDialog.this.responseList.setListData(new Object[0]);
/*     */             try {
/*  68 */               ServerSelectDialog.this.client.sendRequest();
/*  69 */               ServerSelectDialog.this.refreshButton.setEnabled(false);
/*  70 */               Timer timer = new Timer(5000, new ActionListener() {
/*     */                     public void actionPerformed(ActionEvent e) {
/*  72 */                       ServerSelectDialog.this.refreshButton.setEnabled(true);
/*     */                     }
/*     */                   });
/*  75 */               timer.setRepeats(false);
/*  76 */               timer.start();
/*  77 */             } catch (IOException e1) {
/*  78 */               e1.printStackTrace();
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  84 */     setDefaultCloseOperation(0);
/*  85 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent e) {
/*  87 */             ServerSelectDialog.this.onCancel();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  92 */     this.contentPane.registerKeyboardAction(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  94 */             ServerSelectDialog.this.onCancel();
/*     */           }
/*     */         },  KeyStroke.getKeyStroke(27, 0), 1);
/*     */     
/*  98 */     pack();
/*  99 */     setLocationRelativeTo(null);
/*     */   }
/*     */   private JTextField hostField; private JButton refreshButton; private TrackerClient client; private boolean ok;
/*     */   public String getSelectedAddress() {
/* 103 */     if (this.ok) {
/* 104 */       return this.hostField.getText();
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private void reloadList() {
/* 110 */     Set<TrackerResponse> trackerResponses = this.client.getResponses();
/* 111 */     Object[] data = trackerResponses.toArray();
/* 112 */     this.responseList.setListData(data);
/* 113 */     if (data.length > 0 && this.hostField.getText().trim().isEmpty()) {
/* 114 */       this.responseList.setSelectedIndex(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void onOK() {
/* 120 */     this.ok = true;
/* 121 */     dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onCancel() {
/* 126 */     dispose();
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 130 */     super.dispose();
/* 131 */     this.client.stop();
/*     */   }
/*     */   
/*     */   private void setupUI() {
/* 135 */     this.contentPane = new JPanel();
/* 136 */     this.contentPane.setLayout(new GridBagLayout());
/* 137 */     this.contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null));
/* 138 */     JPanel panel1 = new JPanel();
/* 139 */     panel1.setLayout(new FlowLayout(2, 0, 0));
/*     */     
/* 141 */     GridBagConstraints gbc = new GridBagConstraints();
/* 142 */     gbc.gridx = 0;
/* 143 */     gbc.gridy = 2;
/* 144 */     gbc.gridwidth = 3;
/* 145 */     gbc.fill = 1;
/* 146 */     gbc.insets = new Insets(5, 0, 0, 0);
/* 147 */     this.contentPane.add(panel1, gbc);
/* 148 */     JPanel panel2 = new JPanel();
/* 149 */     panel2.setLayout(new GridBagLayout());
/* 150 */     panel1.add(panel2);
/* 151 */     this.buttonOK = new JButton();
/* 152 */     this.buttonOK.setMaximumSize(new Dimension(74, 25));
/* 153 */     this.buttonOK.setMinimumSize(new Dimension(74, 25));
/* 154 */     this.buttonOK.setPreferredSize(new Dimension(74, 25));
/* 155 */     this.buttonOK.setText("OK");
/* 156 */     gbc = new GridBagConstraints();
/* 157 */     gbc.gridx = 0;
/* 158 */     gbc.gridy = 0;
/* 159 */     gbc.weightx = 1.0D;
/* 160 */     gbc.weighty = 1.0D;
/* 161 */     gbc.fill = 2;
/* 162 */     panel2.add(this.buttonOK, gbc);
/* 163 */     this.buttonCancel = new JButton();
/* 164 */     this.buttonCancel.setMaximumSize(new Dimension(74, 25));
/* 165 */     this.buttonCancel.setMinimumSize(new Dimension(74, 25));
/* 166 */     this.buttonCancel.setPreferredSize(new Dimension(74, 25));
/* 167 */     this.buttonCancel.setText("Cancel");
/* 168 */     gbc = new GridBagConstraints();
/* 169 */     gbc.gridx = 1;
/* 170 */     gbc.gridy = 0;
/* 171 */     gbc.weightx = 1.0D;
/* 172 */     gbc.weighty = 1.0D;
/* 173 */     gbc.fill = 2;
/* 174 */     gbc.insets = new Insets(0, 5, 0, 0);
/* 175 */     panel2.add(this.buttonCancel, gbc);
/* 176 */     JPanel panel3 = new JPanel();
/* 177 */     panel3.setLayout(new GridBagLayout());
/* 178 */     gbc = new GridBagConstraints();
/* 179 */     gbc.gridx = 0;
/* 180 */     gbc.gridy = 1;
/* 181 */     gbc.gridwidth = 3;
/* 182 */     gbc.weighty = 1.0D;
/* 183 */     gbc.fill = 1;
/* 184 */     gbc.insets = new Insets(5, 0, 5, 0);
/* 185 */     this.contentPane.add(panel3, gbc);
/* 186 */     JScrollPane scrollPane1 = new JScrollPane();
/* 187 */     gbc = new GridBagConstraints();
/* 188 */     gbc.gridx = 0;
/* 189 */     gbc.gridy = 0;
/* 190 */     gbc.weightx = 1.0D;
/* 191 */     gbc.weighty = 1.0D;
/* 192 */     gbc.fill = 1;
/* 193 */     panel3.add(scrollPane1, gbc);
/* 194 */     this.responseList = new JList();
/* 195 */     scrollPane1.setViewportView(this.responseList);
/* 196 */     this.hostField = new JTextField();
/* 197 */     Dimension minimumSize = this.hostField.getMinimumSize();
/* 198 */     this.hostField.setMinimumSize(new Dimension(128, minimumSize.height));
/* 199 */     this.hostField.setPreferredSize(new Dimension(128, minimumSize.height));
/* 200 */     gbc = new GridBagConstraints();
/* 201 */     gbc.gridx = 1;
/* 202 */     gbc.gridy = 0;
/* 203 */     gbc.weightx = 1.0D;
/* 204 */     gbc.anchor = 17;
/* 205 */     gbc.fill = 2;
/* 206 */     gbc.insets = new Insets(0, 5, 0, 0);
/* 207 */     this.contentPane.add(this.hostField, gbc);
/* 208 */     this.refreshButton = new JButton();
/* 209 */     this.refreshButton.setText("Refresh");
/* 210 */     this.refreshButton.setMnemonic('R');
/* 211 */     this.refreshButton.setDisplayedMnemonicIndex(0);
/* 212 */     gbc = new GridBagConstraints();
/* 213 */     gbc.gridx = 2;
/* 214 */     gbc.gridy = 0;
/* 215 */     gbc.fill = 2;
/* 216 */     gbc.insets = new Insets(0, 5, 0, 0);
/* 217 */     this.contentPane.add(this.refreshButton, gbc);
/* 218 */     JLabel label1 = new JLabel();
/* 219 */     label1.setText("Server Ip Address:");
/* 220 */     label1.setDisplayedMnemonic('A');
/* 221 */     label1.setDisplayedMnemonicIndex(10);
/* 222 */     gbc = new GridBagConstraints();
/* 223 */     gbc.gridx = 0;
/* 224 */     gbc.gridy = 0;
/* 225 */     gbc.anchor = 17;
/* 226 */     this.contentPane.add(label1, gbc);
/* 227 */     label1.setLabelFor(this.hostField);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws IOException {
/* 231 */     ServerSelectDialog dialog = new ServerSelectDialog("dummyService");
/* 232 */     dialog.setVisible(true);
/*     */     
/* 234 */     System.out.println("Selected: " + dialog.getSelectedAddress());
/*     */     
/* 236 */     System.exit(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\tracker\ServerSelectDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */