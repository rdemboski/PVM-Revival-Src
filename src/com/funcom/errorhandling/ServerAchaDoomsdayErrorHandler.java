/*    */ package com.funcom.errorhandling;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.stringtemplate.StringTemplateGroup;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerAchaDoomsdayErrorHandler
/*    */   extends AchaDoomsdayErrorHandler
/*    */ {
/* 12 */   private HashSet<String> errors = new HashSet<String>();
/*    */   
/*    */   public ServerAchaDoomsdayErrorHandler(String serverAddress) {
/* 15 */     super(new AchaDoomsdayErrorHandler.AchaBugreportDataFeeder(serverAddress)
/*    */         {
/*    */           public String getUsername() {
/* 18 */             return "not used";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getEmail() {
/* 23 */             return "not used";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getUniverse() {
/* 28 */             return serverAddress;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected AchaReportModel getModel(AchaDoomsdayErrorHandler.AchaBugreportDataFeeder dataFeeder, Throwable e) {
/* 34 */     return new AchaReportModel(e, dataFeeder.getUsername(), dataFeeder.getEmail(), dataFeeder.getUniverse(), "ServerCrash");
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendReports(Thread t, Throwable e) {
/* 39 */     StringBuffer buffer = getBufferFromExceptionStacktrace(e);
/* 40 */     if (this.errors.contains(buffer.toString()))
/*    */       return; 
/* 42 */     this.errors.add(buffer.toString());
/*    */     
/* 44 */     StringTemplateGroup stg = new StringTemplateGroup("doomsday-handler-group");
/* 45 */     StringTemplate template = stg.getInstanceOf("com/funcom/errorhandling/ACHA_crash_report_template");
/* 46 */     AchaReportModel achaReportModel = createAchaModel(e, this.dxdiagData, getLogs(), buffer);
/* 47 */     template.setAttribute("model", achaReportModel);
/* 48 */     sendXml(template);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\ServerAchaDoomsdayErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */