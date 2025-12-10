/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.HeadlessException;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class RectEditor extends JFrame {
/* 16 */   private static final RectEditor INSTANCE = new RectEditor("FixedLayout Element Editor");
/*    */   
/*    */   public static RectEditor getInstance() {
/* 19 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public RectEditor(String title) throws HeadlessException {
/* 23 */     super(title);
/* 24 */     setLayout(new GridLayout(0, 1));
/*    */   }
/*    */   
/*    */   public void addBComponent(String label, final BComponent debuffButton, final Rectangle rectangle) {
/* 28 */     JPanel pan = new JPanel();
/* 29 */     pan.setBorder(BorderFactory.createTitledBorder(label));
/* 30 */     addField(pan, "X: ", rectangle.x, new ValueSetter()
/*    */         {
/*    */           public void set(int value) {
/* 33 */             rectangle.x = value;
/* 34 */             debuffButton.invalidate();
/*    */           }
/*    */         });
/* 37 */     addField(pan, "Y: ", rectangle.y, new ValueSetter()
/*    */         {
/*    */           public void set(int value) {
/* 40 */             rectangle.y = value;
/* 41 */             debuffButton.invalidate();
/*    */           }
/*    */         });
/* 44 */     addField(pan, "Width: ", rectangle.width, new ValueSetter()
/*    */         {
/*    */           public void set(int value) {
/* 47 */             rectangle.width = value;
/* 48 */             debuffButton.invalidate();
/*    */           }
/*    */         });
/* 51 */     addField(pan, "Height: ", rectangle.height, new ValueSetter()
/*    */         {
/*    */           public void set(int value) {
/* 54 */             rectangle.height = value;
/* 55 */             debuffButton.invalidate();
/*    */           }
/*    */         });
/* 58 */     getContentPane().add(pan);
/*    */     
/* 60 */     pack();
/* 61 */     setVisible(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void addField(JPanel pan, String label, int initValue, final ValueSetter valueSetter) {
/* 67 */     pan.add(new JLabel(label + "(" + initValue + "): "));
/* 68 */     final JTextField field = new JTextField(Integer.toString(initValue), 5);
/* 69 */     field.addActionListener(new ActionListener() {
/*    */           public void actionPerformed(ActionEvent e) {
/* 71 */             int value = Integer.parseInt(field.getText());
/* 72 */             valueSetter.set(value);
/*    */           }
/*    */         });
/* 75 */     pan.add(field);
/*    */   }
/*    */   
/*    */   private static interface ValueSetter {
/*    */     void set(int param1Int);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\RectEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */