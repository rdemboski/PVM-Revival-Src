/*    */ package com.funcom.errorhandling;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.stringtemplate.StringTemplateGroup;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AchaDoomsdayErrorHandler
/*    */   extends DoomsdayErrorHandler
/*    */ {
/* 18 */   private static final Logger LOGGER = Logger.getLogger(AchaDoomsdayErrorHandler.class);
/*    */   
/*    */   private static final String BUG_AGENT = "ACHAClient/1.0";
/*    */   
/*    */   private static final String BUG_POST_URL = "http://bugserver.ageofconan.com/bugs/bugsubmit.php";
/*    */   
/*    */   private AchaBugreportDataFeeder dataFeeder;
/*    */   
/*    */   public AchaDoomsdayErrorHandler(AchaBugreportDataFeeder dataFeeder) {
/* 27 */     if (dataFeeder == null)
/* 28 */       throw new IllegalArgumentException("dataFeeder = null"); 
/* 29 */     this.dataFeeder = dataFeeder;
/*    */   }
/*    */   
/*    */   public void sendReports(Thread t, Throwable e) {
/* 33 */     StringTemplateGroup stg = new StringTemplateGroup("doomsday-handler-group");
/* 34 */     StringTemplate template = stg.getInstanceOf("com/funcom/errorhandling/ACHA_crash_report_template");
/* 35 */     StringBuffer buffer = getBufferFromExceptionStacktrace(e);
/* 36 */     AchaReportModel achaReportModel = createAchaModel(e, this.dxdiagData, getLogs(), buffer);
/* 37 */     template.setAttribute("model", achaReportModel);
/* 38 */     sendXml(template);
/*    */   }
/*    */   
/*    */   protected void sendXml(StringTemplate template) {
/* 42 */     String processedContent = template.toString();
/* 43 */     LOGGER.info(processedContent);
/*    */     try {
/* 45 */       URL url = new URL("http://bugserver.ageofconan.com/bugs/bugsubmit.php");
/* 46 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/* 47 */       conn.setDoOutput(true);
/* 48 */       conn.setAllowUserInteraction(false);
/* 49 */       conn.setRequestMethod("POST");
/* 50 */       conn.setRequestProperty("User-Agent", "ACHAClient/1.0");
/* 51 */       conn.setRequestProperty("Content-type", "text/xml");
/* 52 */       conn.setRequestProperty("Content-length", String.valueOf(processedContent.length()));
/*    */       
/* 54 */       PrintWriter writer = new PrintWriter(conn.getOutputStream());
/* 55 */       writer.println(processedContent);
/* 56 */       writer.flush();
/*    */ 
/*    */ 
/*    */       
/* 60 */       InputStream inputStream = conn.getInputStream();
/*    */       
/* 62 */       writer.close();
/* 63 */       conn.disconnect();
/* 64 */     } catch (IOException e) {
/* 65 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected AchaReportModel createAchaModel(Throwable e, String dxdiag, String logs, StringBuffer buffer) {
/* 70 */     AchaReportModel model = getModel(this.dataFeeder, e);
/*    */     
/* 72 */     List<AchaReportModel.NameValuePair> bodyTags = new LinkedList<AchaReportModel.NameValuePair>();
/* 73 */     for (CrashDataProvider crashDataProvider : getCrashDataProviders())
/* 74 */       bodyTags.add(new AchaReportModel.NameValuePair(crashDataProvider.getName(), crashDataProvider.getValueAsString())); 
/* 75 */     model.setBodyTags(bodyTags);
/*    */ 
/*    */ 
/*    */     
/* 79 */     List<AchaReportModel.NameValuePair> array = new ArrayList<AchaReportModel.NameValuePair>();
/* 80 */     array.add(new AchaReportModel.NameValuePair("stacktrace.txt", buffer.toString()));
/*    */     
/* 82 */     if (logs != null) {
/* 83 */       array.add(new AchaReportModel.NameValuePair("logs.txt", logs));
/*    */     }
/* 85 */     if (dxdiag != null) {
/* 86 */       array.add(new AchaReportModel.NameValuePair("dxdiag.txt", dxdiag));
/*    */     }
/* 88 */     model.setAttachments(array);
/*    */     
/* 90 */     return model;
/*    */   }
/*    */   
/*    */   protected AchaReportModel getModel(AchaBugreportDataFeeder dataFeeder, Throwable e) {
/* 94 */     return new AchaReportModel(e, dataFeeder.getUsername(), dataFeeder.getEmail(), dataFeeder.getUniverse());
/*    */   }
/*    */   
/*    */   public static interface AchaBugreportDataFeeder {
/*    */     String getUsername();
/*    */     
/*    */     String getEmail();
/*    */     
/*    */     String getUniverse();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\AchaDoomsdayErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */