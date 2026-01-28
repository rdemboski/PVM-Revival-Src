/*    */ package com.funcom.commons.configuration;
/*    */ 
/*    */ import com.funcom.commons.ThreadInvoker;
import com.funcom.commons.configuration.URLFieldProperties.PropertyException;

/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ 
/*    */ public class ReconfigurePropertiesFrame
/*    */   extends JFrame
/*    */ {
/* 14 */   private static final Dimension PREFERRED_BUTTON_SIZE = new Dimension(130, 75);
/*    */   
/*    */   private URLFieldProperties[] propertiesList;
/*    */   private ThreadInvoker threadInvoker;
/*    */   private Runnable reconfigureLambda;
/*    */   
/*    */   public ReconfigurePropertiesFrame(ThreadInvoker threadInvoker, URLFieldProperties... propertiesList) {
/* 21 */     this.propertiesList = propertiesList;
/* 22 */     this.threadInvoker = threadInvoker;
/*    */     
/* 24 */     this.reconfigureLambda = new Runnable() {
/*    */         public void run() {
/*    */           try {
/* 27 */             for (URLFieldProperties urlFieldProperties : ReconfigurePropertiesFrame.this.propertiesList) {
/* 28 */               urlFieldProperties.refresh();
/*    */             }
/* 30 */           } catch (PropertyException e) {
/* 31 */             throw new IllegalStateException(e);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 36 */     JButton button = new JButton("Reconfigure");
/* 37 */     button.setPreferredSize(PREFERRED_BUTTON_SIZE);
/* 38 */     button.addActionListener(new ActionListener() {
/*    */           public void actionPerformed(ActionEvent e) {
/* 40 */             ReconfigurePropertiesFrame.this.threadInvoker.invoke(ReconfigurePropertiesFrame.this.reconfigureLambda);
/*    */           }
/*    */         });
/*    */     
/* 44 */     getContentPane().add(button);
/*    */     
/* 46 */     setDefaultCloseOperation(2);
/* 47 */     pack();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\ReconfigurePropertiesFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */