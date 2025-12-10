/*     */ package com.funcom.gameengine.utils;
/*     */ 
/*     */ import com.funcom.gameengine.model.factories.XmlChunkTags;
/*     */ import com.funcom.gameengine.model.factories.XmlMk6Tags;
/*     */ import com.funcom.gameengine.resourcemanager.DefaultResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.Text;
/*     */ import org.jdom.input.SAXBuilder;
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
/*     */ public class XmlToBinaryWriter
/*     */ {
/*     */   public static final String BINARY_FILE_EXTENSION = ".bunk";
/*     */   public static final String BINARY_ID_FILE_NAME = "binaryIds.bunk";
/*  48 */   public static final String BINARY_ID_FILE_LOCATION = System.getProperty("tcg.resourcepath") + "/binary/";
/*     */   
/*     */   public static final String BINARY_DIR_NAME = "binary";
/*  51 */   private static final Set<File> savedIdsPath = Collections.synchronizedSet(new HashSet<File>());
/*     */ 
/*     */   
/*     */   private SAXBuilder saxBuilder;
/*     */   
/*  56 */   private Map<String, Integer> binaryTags = new HashMap<String, Integer>();
/*     */   
/*     */   public void init() {
/*  59 */     this.saxBuilder = new SAXBuilder();
/*  60 */     int binaryId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  67 */       Field[] revXmlFields = XmlMk6Tags.class.getDeclaredFields();
/*     */       
/*  69 */       for (int i = 0; i < revXmlFields.length; i++) {
/*  70 */         String fieldName = (String)revXmlFields[i].get(revXmlFields[i].getName());
/*  71 */         Integer id = this.binaryTags.get(fieldName);
/*  72 */         if (id == null) {
/*  73 */           this.binaryTags.put(fieldName, Integer.valueOf(binaryId));
/*  74 */           binaryId++;
/*     */         } 
/*     */       } 
/*     */       
/*  78 */       Field[] revChunkFields = XmlChunkTags.class.getDeclaredFields();
/*     */       
/*  80 */       for (int j = 0; j < revChunkFields.length; j++) {
/*  81 */         String fieldName = (String)revChunkFields[j].get(revChunkFields[j].getName());
/*  82 */         Integer id = this.binaryTags.get(fieldName);
/*  83 */         if (id == null) {
/*  84 */           this.binaryTags.put(fieldName, Integer.valueOf(binaryId));
/*  85 */           binaryId++;
/*     */         } 
/*     */       } 
/*  88 */     } catch (IllegalAccessException e) {
/*  89 */       throw new RuntimeException("cannot init tags", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public File saveBinaryFromXml(InputStream inputStream, File file) throws IOException, JDOMException {
/*  95 */     Document document = this.saxBuilder.build(inputStream);
/*     */     
/*  97 */     File binaryFile = saveBinaryFromXml(document, file);
/*     */     
/*  99 */     saveBinaryIdsOnce(file.getParentFile().getParentFile().getParentFile());
/*     */     
/* 101 */     return binaryFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void cleanForClient(Document doc) {
/* 106 */     Element world = doc.getRootElement();
/* 107 */     if (world == null || world.getName().compareTo("world") != 0) {
/* 108 */       new Exception("Couldn't find 'world' tag in the chunk - cannot process indexed chunk.");
/*     */     }
/* 110 */     Element tile = null;
/* 111 */     List<Element> tilelist = world.getChildren("tile");
/* 112 */     for (int i = 0; i < tilelist.size(); i++) {
/* 113 */       tile = tilelist.get(i);
/*     */ 
/*     */       
/* 116 */       if (tile.getAttributeValue("reftileid") != null)
/*     */       {
/*     */         
/* 119 */         tile.removeContent();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public File saveBinaryFromXml(Document document, File file) throws IOException {
/*     */     BufferedOutputStream outputStream;
/*     */     File binaryFile;
/* 128 */     String fileName = file.getName();
/* 129 */     if (file.getName().endsWith(".chunk")) {
/* 130 */       fileName = fileName.substring(0, fileName.indexOf(".chunk"));
/* 131 */     } else if (fileName.endsWith(".xml")) {
/* 132 */       fileName = fileName.substring(0, fileName.indexOf(".xml"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     File binaryDir = new File(file.getParentFile().getParentFile().getParentFile(), "binary");
/* 138 */     File dir = new File(binaryDir, file.getParentFile().getName());
/* 139 */     boolean dirOK = dir.exists();
/*     */     
/* 141 */     if (!dirOK) {
/* 142 */       dirOK = dir.mkdirs();
/*     */     }
/*     */ 
/*     */     
/* 146 */     if (dirOK) {
/* 147 */       binaryFile = new File(dir, fileName + ".bunk");
/* 148 */       outputStream = new BufferedOutputStream(new FileOutputStream(binaryFile));
/*     */     } else {
/* 150 */       throw new RuntimeException("Could not save binary chunks -- > Unable to create a directory --> wantedDir=" + dir.getAbsolutePath());
/*     */     } 
/*     */     
/* 153 */     saveBinaryFromXml(outputStream, document);
/*     */     
/* 155 */     return binaryFile;
/*     */   }
/*     */   
/*     */   public void saveBinaryFromXml(OutputStream bufferedOutputStream, Document document) throws IOException {
/* 159 */     DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
/*     */     
/* 161 */     cleanForClient(document);
/*     */     
/* 163 */     Element root = document.getRootElement();
/* 164 */     String rootName = root.getName();
/*     */ 
/*     */     
/* 167 */     dataOutputStream.writeInt(getTagId(rootName).intValue());
/*     */     
/* 169 */     List<Element> children = root.getChildren();
/*     */ 
/*     */     
/* 172 */     dataOutputStream.writeInt(children.size());
/*     */     
/* 174 */     writeChildren(dataOutputStream, children);
/* 175 */     dataOutputStream.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer getTagId(String symbol) {
/* 180 */     Integer id = this.binaryTags.get(symbol);
/*     */     
/* 182 */     if (id == null) {
/* 183 */       throw new IllegalArgumentException("unknown symbol/constant/tag: " + symbol);
/*     */     }
/*     */     
/* 186 */     return id;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeChildren(DataOutputStream os, List<Element> children) throws IOException {
/* 191 */     Hashtable<Object, Object> hashMeshes = new Hashtable<Object, Object>();
/* 192 */     DefaultResourceManager defaultResourceManager = new DefaultResourceManager();
/* 193 */     defaultResourceManager.addResourceRoot("../../resources");
/* 194 */     defaultResourceManager.addResourceRoot("../../resources_ab");
/* 195 */     defaultResourceManager.initDefaultLoaders();
/*     */     
/* 197 */     if (LoadingManager.USE_MERGED_MESHES) {
/* 198 */       for (Element element : children) {
/* 199 */         String childName = element.getName();
/* 200 */         if (childName.compareTo("mesh-object") == 0) {
/* 201 */           Element resourceElement = element.getChild("resource");
/* 202 */           String resourceName = resourceElement.getAttributeValue("name");
/* 203 */           ArrayList<Element> list = (ArrayList<Element>)hashMeshes.get(resourceName);
/* 204 */           if (list == null) {
/* 205 */             list = new ArrayList<Element>();
/* 206 */             hashMeshes.put(resourceName, list);
/*     */           } 
/* 208 */           list.add(element);
/*     */           continue;
/*     */         } 
/* 211 */         WriteChild(element, os);
/*     */       } 
/*     */ 
/*     */       
/* 215 */       Set<String> keys = hashMeshes.keySet();
/* 216 */       Iterator<String> iter = keys.iterator();
/* 217 */       while (iter.hasNext()) {
/* 218 */         ArrayList<Element> list = (ArrayList<Element>)hashMeshes.get(iter.next());
/* 219 */         int nNumItems = list.size();
/* 220 */         if (nNumItems == 1) {
/* 221 */           for (int i = 0; i < nNumItems; i++) {
/* 222 */             WriteChild(list.get(i), os);
/*     */           }
/*     */           continue;
/*     */         } 
/* 226 */         for (int n = 0; n < nNumItems; n++) {
/* 227 */           Element element = list.get(n);
/* 228 */           element.setName("merged-mesh-object");
/* 229 */           element.setAttribute("siblings", Integer.toString(nNumItems));
/* 230 */           WriteChild(element, os);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 236 */       for (Element element : children) {
/* 237 */         WriteChild(element, os);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void WriteChild(Element element, DataOutputStream os) throws IOException {
/* 243 */     String childName = element.getName();
/* 244 */     int tagValue = getTagId(childName).intValue();
/*     */ 
/*     */     
/* 247 */     os.writeInt(tagValue);
/*     */     
/* 249 */     List<Attribute> attributes = element.getAttributes();
/*     */     
/* 251 */     os.writeInt(attributes.size());
/*     */     
/* 253 */     if (attributes.size() > 0) {
/* 254 */       for (int j = 0; j < attributes.size(); j++) {
/* 255 */         Attribute attribute = attributes.get(j);
/* 256 */         String attributeName = attribute.getName();
/*     */         
/* 258 */         os.writeInt(getTagId(attributeName).intValue());
/*     */         
/* 260 */         os.writeUTF(attribute.getValue());
/*     */       } 
/*     */     }
/*     */     
/* 264 */     List<Text> content = element.getContent();
/*     */     
/* 266 */     os.writeInt(content.size());
/*     */     
/* 268 */     for (int i = 0; i < content.size(); i++) {
/* 269 */       if (content.get(i) instanceof Text) {
/* 270 */         Text textElement = content.get(i);
/*     */         
/* 272 */         os.writeUTF(textElement.getText());
/*     */       } else {
/* 274 */         os.writeUTF("");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 279 */     os.writeInt(element.getChildren().size());
/* 280 */     if (element.getChildren().size() > 0) {
/* 281 */       writeChildren(os, element.getChildren());
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveBinaryIdsOnce(File base) throws IOException {
/* 286 */     if (!savedIdsPath.contains(base)) {
/* 287 */       File dir = new File(base, "binary");
/*     */       
/* 289 */       if (!dir.exists()) {
/* 290 */         dir.mkdirs();
/*     */       }
/* 292 */       BufferedOutputStream outputStream = null;
/*     */       try {
/* 294 */         outputStream = new BufferedOutputStream(new FileOutputStream(new File(dir, "binaryIds.bunk")));
/* 295 */         DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
/* 296 */         dataOutputStream.writeInt(this.binaryTags.size());
/* 297 */         for (String constantName : this.binaryTags.keySet()) {
/* 298 */           dataOutputStream.writeUTF(constantName);
/* 299 */           dataOutputStream.writeInt(getTagId(constantName).intValue());
/*     */         } 
/*     */       } finally {
/* 302 */         if (outputStream != null) {
/*     */           try {
/* 304 */             outputStream.close();
/* 305 */           } catch (IOException e) {
/* 306 */             System.err.println("Error closing stream: " + e.toString());
/*     */           } 
/*     */         }
/*     */       } 
/* 310 */       savedIdsPath.add(base);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getPathName(String dir) {
/* 315 */     int i = dir.lastIndexOf('/');
/* 316 */     if (i == -1) {
/* 317 */       dir = dir + "\\";
/*     */     }
/* 319 */     return dir;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\XmlToBinaryWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */