/*     */ package com.funcom.gameengine.conanchat.tools;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Datatype;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer32Array;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer40;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer8;
/*     */ import com.funcom.gameengine.conanchat.datatypes.MapDatatype;
/*     */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
/*     */ import com.funcom.gameengine.conanchat.datatypes.StringDatatypeArray;
/*     */ import com.funcom.gameengine.conanchat.packets2.ConanChatMessageType;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
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
/*     */ public class MessageGenerator
/*     */   implements Runnable
/*     */ {
/*  46 */   private static final Pattern REGEX_PYTHON_LINE_PATTERN = Pattern.compile("^.*?\\('(.+?)'.+?([0-9]+?),.+?'([a-zA-Z]+?)'.+?#(.+?)$");
/*     */   
/*     */   private static final int REGEX_GROUP_NAME = 1;
/*     */   
/*     */   private static final int REGEX_GROUP_ID = 2;
/*     */   private static final int REGEX_GROUP_DEFINITION = 3;
/*     */   private static final int REGEX_GROUP_DEFINITION_NAMES = 4;
/*     */   private static final String TEMPLATE_IMPORTS = "imports";
/*     */   private static final String TEMPLATE_JAVADOC_REFERENCE_LINE = "javadocreferenceline";
/*     */   private static final String TEMPLATE_CLASS_NAME = "classname";
/*     */   private static final String TEMPLATE_MESSAGE_DATA = "messagedata";
/*     */   private static final String TEMPLATE_MESSAGE_TYPE = "messagetype";
/*     */   private String definitionString;
/*     */   private StringTemplate messageClassTemplate;
/*     */   private String messageClassName;
/*     */   private List<VariableDescription> typeNamePairs;
/*     */   private String messageType;
/*     */   private String outputDirectory;
/*     */   
/*     */   public MessageGenerator(String outputDirectory, String definitionString) {
/*  66 */     this.outputDirectory = outputDirectory;
/*  67 */     this.definitionString = definitionString;
/*  68 */     findDataFromInputString(definitionString);
/*     */     
/*  70 */     StringTemplateGroup group = new StringTemplateGroup("messages-group");
/*  71 */     this.messageClassTemplate = group.getInstanceOf("com/funcom/gameengine/conanchat/tools/message-class-template");
/*     */   }
/*     */   
/*     */   private void findDataFromInputString(String definitionString) {
/*  75 */     Matcher matcher = REGEX_PYTHON_LINE_PATTERN.matcher(definitionString);
/*  76 */     if (!matcher.matches()) {
/*  77 */       throw new IllegalStateException(String.format("ERROR: Input line\n\t%s\ndoesn't matches regex\n\t%s\n", new Object[] { definitionString, REGEX_PYTHON_LINE_PATTERN.pattern() }));
/*     */     }
/*     */     
/*  80 */     this.messageClassName = StringUtils.capitalizeFirst(StringUtils.styleC2CamelHumps(matcher.group(1)));
/*     */ 
/*     */     
/*  83 */     findMessageTypeConstant(matcher);
/*     */ 
/*     */     
/*  86 */     String definition = matcher.group(3);
/*  87 */     String[] definitionNames = matcher.group(4).split(",");
/*  88 */     findVariables(definition, definitionNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findVariables(String definition, String[] definitionNames) {
/*  98 */     List<Class<? extends Datatype>> datatypeClasses = getMessageDatatypes(definition);
/*  99 */     this.typeNamePairs = new ArrayList<VariableDescription>(definition.length());
/*     */     
/* 101 */     for (int i = 0; i < datatypeClasses.size(); i++) {
/* 102 */       Class<? extends Datatype> aClass = datatypeClasses.get(i);
/* 103 */       this.typeNamePairs.add(new VariableDescription(aClass, StringUtils.styleC2CamelHumps(definitionNames[i])));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void findMessageTypeConstant(Matcher matcher) {
/* 108 */     int messageId = Integer.parseInt(matcher.group(2));
/* 109 */     Field[] fields = ConanChatMessageType.class.getDeclaredFields();
/* 110 */     int soughtModifiers = 25;
/*     */     try {
/* 112 */       for (Field field : fields) {
/* 113 */         if ((field.getModifiers() & soughtModifiers) == soughtModifiers && field.getInt(null) == messageId)
/*     */         {
/* 115 */           this.messageType = field.getName(); } 
/*     */       } 
/* 117 */     } catch (IllegalAccessException e) {
/* 118 */       throw new IllegalStateException(e);
/*     */     } 
/* 120 */     if (this.messageType == null)
/* 121 */       System.err.println("Cannot find constant defined in ConanChatMessageTypes for ID: " + messageId + ", using hardcoded number!"); 
/*     */   }
/*     */   
/*     */   private List<Class<? extends Datatype>> getMessageDatatypes(String definition) {
/* 125 */     List<Class<? extends Datatype>> messageTypes = new ArrayList<Class<? extends Datatype>>();
/* 126 */     StringReader sr = new StringReader(definition);
/*     */     try {
/* 128 */       int currentChar = sr.read();
/*     */       do {
/* 130 */         switch (currentChar) {
/*     */           case 66:
/* 132 */             messageTypes.add(Integer8.class);
/*     */             break;
/*     */           case 73:
/* 135 */             messageTypes.add(Integer32.class);
/*     */             break;
/*     */           case 71:
/* 138 */             messageTypes.add(Integer40.class);
/*     */             break;
/*     */           case 83:
/* 141 */             messageTypes.add(StringDatatype.class);
/*     */             break;
/*     */           case 68:
/* 144 */             messageTypes.add(Data.class);
/*     */             break;
/*     */           case 77:
/* 147 */             messageTypes.add(MapDatatype.class);
/*     */             break;
/*     */           case 105:
/* 150 */             messageTypes.add(Integer32Array.class);
/*     */             break;
/*     */           case 115:
/* 153 */             messageTypes.add(StringDatatypeArray.class);
/*     */             break;
/*     */           default:
/* 156 */             throw new IllegalStateException("Unrecognized datatype: " + (char)currentChar);
/*     */         } 
/* 158 */         currentChar = sr.read();
/* 159 */       } while (currentChar != -1);
/* 160 */     } catch (IOException e) {
/* 161 */       System.err.println("Failed to read from stream!");
/*     */     } 
/*     */     
/* 164 */     return messageTypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 169 */     this.messageClassTemplate.setAttribute("imports", Arrays.asList(new String[] { "com.funcom.gameengine.conanchat.packets2.ChatMessage", "com.funcom.gameengine.conanchat.packets2.ConanChatMessageType", "com.funcom.gameengine.conanchat.datatypes.*" }));
/*     */ 
/*     */     
/* 172 */     this.messageClassTemplate.setAttribute("javadocreferenceline", this.definitionString);
/* 173 */     this.messageClassTemplate.setAttribute("classname", this.messageClassName);
/* 174 */     this.messageClassTemplate.setAttribute("messagedata", this.typeNamePairs);
/* 175 */     this.messageClassTemplate.setAttribute("messagetype", this.messageType);
/*     */     
/*     */     try {
/* 178 */       File file = new File(this.outputDirectory + "/" + this.messageClassTemplate.getAttribute("classname") + ".java");
/* 179 */       int i = 0;
/* 180 */       while (file.exists()) {
/* 181 */         i++;
/* 182 */         File newFile = new File(this.outputDirectory + "/" + this.messageClassTemplate.getAttribute("classname") + i + ".java");
/* 183 */         System.err.println(String.format("FILE ALREADY EXISTS: %s, INCREMENTING INDEX TO OUTPUT, NEW FILENAME: %s", new Object[] { file.getName(), newFile }));
/* 184 */         file = newFile;
/*     */       } 
/*     */       
/* 187 */       FileWriter fw = new FileWriter(file);
/* 188 */       fw.write(this.messageClassTemplate.toString());
/* 189 */       fw.close();
/*     */       
/* 191 */       System.out.println(file.getName());
/* 192 */     } catch (IOException e) {
/* 193 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 200 */     if (args.length < 2) {
/* 201 */       System.out.println("Please copy paste definition line as first and only argument and give output directory (example: ('group_message',                  65, 'GSD'),   # groupid, message body, extra data)");
/* 202 */       System.exit(1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 207 */     String outputDirectory = args[0].trim();
/* 208 */     String definitionString = args[1].trim();
/*     */     try {
/* 210 */       MessageGenerator mg = new MessageGenerator(outputDirectory, definitionString);
/* 211 */       mg.run();
/* 212 */     } catch (Exception e) {
/* 213 */       System.err.println("***** ERROR while working on: " + definitionString);
/* 214 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\tools\MessageGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */