/*     */ package com.funcom.errorhandling;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AchaReportModel
/*     */ {
/*     */   private static final String PROJECT_STRING = "PVM";
/*     */   private static final String UNIQUE_ID_PVM_TAG = "PVM-";
/*     */   private static final String TYPE_CLIENT_CRASH = "ClientCrash";
/*     */   private static final String CATEGORY_DEFAULT = "DefaultCategory";
/*     */   private String uniqueId;
/*     */   private String username;
/*     */   private String type;
/*     */   private String project;
/*     */   private String email;
/*     */   private String category;
/*     */   private List<NameValuePair> nameValuePairs;
/*     */   private List<NameValuePair> attachments;
/*     */   private String universe;
/*     */   
/*     */   public AchaReportModel() {
/*  30 */     this("PVM-0", "UNSET_USERNAME", "UNSET_TYPE", "UNSET_EMAIL@UNSET_SERVER.ORG", "UNSET_CATEGORY", "UNSET_UNIVERSE", "PVM", new LinkedList<NameValuePair>(), new LinkedList<NameValuePair>());
/*     */   }
/*     */   
/*     */   public AchaReportModel(Throwable e, String username, String email, String universe) {
/*  34 */     this(universe + getRootHashCode(e), username, "ClientCrash", email, "DefaultCategory", universe, "PVM", new LinkedList<NameValuePair>(), new LinkedList<NameValuePair>());
/*     */   }
/*     */   
/*     */   public AchaReportModel(Throwable e, String username, String email, String universe, String type) {
/*  38 */     this(universe + getRootHashCode(e), username, type, email, "DefaultCategory", universe, "PVM", new LinkedList<NameValuePair>(), new LinkedList<NameValuePair>());
/*     */   }
/*     */   
/*     */   public AchaReportModel(String uniqueId, String username, String type, String email, String category, String universe, String project, List<NameValuePair> nameValuePairs, List<NameValuePair> attachments) {
/*  42 */     this.uniqueId = uniqueId;
/*  43 */     this.username = username;
/*  44 */     this.type = type;
/*  45 */     this.email = email;
/*  46 */     this.category = category;
/*  47 */     this.universe = universe;
/*  48 */     this.project = project;
/*  49 */     this.nameValuePairs = new ArrayList<NameValuePair>();
/*  50 */     this.nameValuePairs.addAll(nameValuePairs);
/*  51 */     this.attachments = new ArrayList<NameValuePair>();
/*  52 */     this.attachments.addAll(attachments);
/*     */   }
/*     */   
/*     */   private static int getRootHashCode(Throwable e) {
/*  56 */     Throwable rootCause = e;
/*  57 */     while (rootCause.getCause() != null) {
/*  58 */       rootCause = rootCause.getCause();
/*     */     }
/*     */     
/*  61 */     return Arrays.<StackTraceElement>asList(rootCause.getStackTrace()).hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueId() {
/*  66 */     return this.uniqueId;
/*     */   }
/*     */   
/*     */   public void setUniqueId(String uniqueId) {
/*  70 */     this.uniqueId = uniqueId;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  74 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/*  78 */     this.username = username;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  82 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  86 */     this.type = type;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  90 */     return this.email;
/*     */   }
/*     */   
/*     */   public void setEmail(String email) {
/*  94 */     this.email = email;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/*  98 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String category) {
/* 102 */     this.category = category;
/*     */   }
/*     */   
/*     */   public List<NameValuePair> getBodyTags() {
/* 106 */     return this.nameValuePairs;
/*     */   }
/*     */   
/*     */   public void setBodyTags(List<NameValuePair> nameValuePairs) {
/* 110 */     this.nameValuePairs.clear();
/* 111 */     this.nameValuePairs.addAll(nameValuePairs);
/*     */   }
/*     */   
/*     */   public List<NameValuePair> getAttachments() {
/* 115 */     return this.attachments;
/*     */   }
/*     */   
/*     */   public void setAttachments(List<NameValuePair> attachments) {
/* 119 */     this.attachments.clear();
/* 120 */     this.attachments.addAll(attachments);
/*     */   }
/*     */   
/*     */   public void setUniverse(String universe) {
/* 124 */     this.universe = universe;
/*     */   }
/*     */   
/*     */   public String getUniverse() {
/* 128 */     return this.universe;
/*     */   }
/*     */   
/*     */   public static class NameValuePair {
/*     */     private String name;
/*     */     private String value;
/*     */     
/*     */     public NameValuePair(String name, String value) {
/* 136 */       if (name == null)
/* 137 */         throw new IllegalArgumentException("name = null"); 
/* 138 */       if (value == null)
/* 139 */         throw new IllegalArgumentException("value = null"); 
/* 140 */       this.name = name;
/* 141 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 145 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 149 */       return this.value;
/*     */     }
/*     */     
/*     */     public String getBase64Value() {
/* 153 */       return (new BASE64Encoder()).encode(this.value.getBytes());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\AchaReportModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */