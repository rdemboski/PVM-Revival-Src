/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import com.funcom.commons.utils.ApplicationRelativePathUtil;
/*     */ //import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TcgPreferences
/*     */ {
/*     */   private static final String PREF_USERNAME = "username";
/*     */   private static final String PREF_SOUND = "sound";
/*     */   private static final String PREF_MUSIC = "music";
/*     */   private static final String PREF_TIPS = "tips";
/*     */   private static final String PREF_AA = "aa";
/*     */   private static final String USERNAME_PREFIX = "prefix";
/*     */   private Document document;
/*  38 */   private File xmlFile = new File(ApplicationRelativePathUtil.getSystemDir() + "/preferences.xml"); public TcgPreferences(Preferences preferences) {
/*  39 */     if (!this.xmlFile.exists()) {
/*     */       try {
/*  41 */         this.xmlFile.createNewFile();
/*  42 */         //this.document = new DocumentImpl();
/*  43 */         this.rootElement = this.document.createElement("prefs");
/*  44 */         Node username = this.document.createElement("username");
/*  45 */         Node music = this.document.createElement("music");
/*  46 */         music.setTextContent("false");
/*  47 */         Node sound = this.document.createElement("sound");
/*  48 */         sound.setTextContent("false");
/*  49 */         Node antiAlias = this.document.createElement("aa");
/*  50 */         this.rootElement.appendChild(username);
/*  51 */         this.rootElement.appendChild(music);
/*  52 */         this.rootElement.appendChild(sound);
/*  53 */         this.rootElement.appendChild(antiAlias);
/*  54 */         this.document.appendChild(this.rootElement);
/*  55 */         saveXml();
/*  56 */       } catch (IOException e) {
/*  57 */         System.out.println("Could not create xml file: " + e.getMessage());
/*     */       } 
/*     */     }
/*  60 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*     */     try {
/*  62 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*  63 */       this.document = docBuilder.parse(this.xmlFile);
/*  64 */       this.rootElement = this.document.getDocumentElement();
/*  65 */     } catch (ParserConfigurationException e) {
/*  66 */       System.out.println("Parser configuration: " + e.getMessage());
/*  67 */     } catch (IOException e) {
/*  68 */       System.out.println("IOError parsing xml file: " + e.getMessage());
/*  69 */     } catch (SAXException e) {
/*  70 */       System.out.println("SAXException: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   private Element rootElement;
/*     */   public String loadUserName() {
/*  75 */     return loadUserName(System.getProperty("user.name"));
/*     */   }
/*     */   
/*     */   public String loadUserName(String defaultName) {
/*     */     String username;
/*  80 */     if ((username = getXmlValue("username")) != null && !username.equalsIgnoreCase("")) {
/*  81 */       return username;
/*     */     }
/*     */     
/*  84 */     return defaultName;
/*     */   }
/*     */   
/*     */   public void saveUserName(String charactersName) {
/*  88 */     setXmlValue("username", charactersName);
/*     */   }
/*     */   
/*     */   public boolean getSound() {
/*  92 */     return Boolean.parseBoolean(getXmlValue("sound"));
/*     */   }
/*     */   
/*     */   public void saveSound(boolean value) {
/*  96 */     setXmlValue("sound", Boolean.toString(value));
/*     */   }
/*     */   
/*     */   public void saveAntiAliasing(int value) {
/* 100 */     setXmlValue("aa", Integer.toString(value));
/*     */   }
/*     */   
/*     */   public int getAntiAliasing(int defaultAa) {
/* 104 */     return Integer.valueOf(getXmlValue("aa")).intValue();
/*     */   }
/*     */   
/*     */   public boolean getMusic() {
/* 108 */     return Boolean.parseBoolean(getXmlValue("music"));
/*     */   }
/*     */   
/*     */   public void saveMusic(boolean value) {
/* 112 */     setXmlValue("music", Boolean.toString(value));
/*     */   }
/*     */   
/*     */   public void saveTips(HashMap<String, Boolean> map, String userName) {
/* 116 */     if (userName.equals("123trial123")) {
/*     */       return;
/*     */     }
/* 119 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 121 */     for (String key : map.keySet()) {
/* 122 */       if (stringBuilder.length() > 0) {
/* 123 */         stringBuilder.append("&");
/*     */       }
/* 125 */       Boolean value = map.get(key);
/* 126 */       stringBuilder.append((key != null) ? key : "");
/* 127 */       stringBuilder.append("=");
/* 128 */       stringBuilder.append((value != null) ? String.valueOf(value) : "");
/*     */     } 
/*     */     
/* 131 */     setXmlValue("prefix" + userName + "tips", stringBuilder.toString());
/*     */   }
/*     */   
/*     */   public Map<String, Boolean> getTips(String userName) {
/* 135 */     String input = getXmlValue("prefix" + userName + "tips");
/* 136 */     Map<String, Boolean> map = new HashMap<String, Boolean>();
/* 137 */     if (!input.isEmpty()) {
/* 138 */       String[] nameValuePairs = input.split("&");
/* 139 */       for (String nameValuePair : nameValuePairs) {
/* 140 */         String[] nameValue = nameValuePair.split("=");
/* 141 */         map.put(nameValue[0], Boolean.valueOf((nameValue.length > 1) ? Boolean.valueOf(nameValue[1]).booleanValue() : false));
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return map;
/*     */   }
/*     */   
/*     */   private void saveXml() {
/*     */     try {
/* 150 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 151 */       transformer.setOutputProperty("indent", "yes");
/*     */       
/* 153 */       StreamResult result = new StreamResult(this.xmlFile);
/* 154 */       DOMSource source = new DOMSource(this.document);
/* 155 */       transformer.transform(source, result);
/* 156 */     } catch (TransformerConfigurationException e) {
/* 157 */       e.printStackTrace();
/* 158 */     } catch (TransformerException e) {
/* 159 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getXmlValue(String tagName) {
/* 165 */     NodeList elementList = this.document.getElementsByTagName(tagName);
/* 166 */     Element element = (Element)elementList.item(0);
/* 167 */     if (element != null) {
/* 168 */       return element.getTextContent();
/*     */     }
/* 170 */     setXmlValue(tagName, "");
/* 171 */     return new String("");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setXmlValue(String tag, String value) {
/* 176 */     NodeList elementList = this.document.getElementsByTagName(tag);
/* 177 */     Element element = (Element)elementList.item(0);
/*     */     try {
/* 179 */       element.setTextContent(value);
/* 180 */     } catch (NullPointerException e) {
/* 181 */       Node newElement = this.document.createElement(tag);
/* 182 */       this.rootElement.appendChild(newElement);
/* 183 */       newElement.setNodeValue(value);
/*     */     } 
/* 185 */     saveXml();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgPreferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */