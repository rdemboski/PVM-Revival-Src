/*     */ package com.funcom.gameengine.jme.modular;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class XmlModularDescription
/*     */   extends AbstractModularDescription
/*     */ {
/*     */   private static final String ELEMENT_PARTS = "parts";
/*     */   private static final String ELEMENT_ANIMATIONS = "animations";
/*     */   private static final String ELEMENT_MESH = "mesh";
/*     */   private static final String ELEMENT_MAPS = "maps";
/*     */   private static final String ELEMENT_TEXTURE = "texture";
/*     */   private static final String ELEMENT_TEXTURELOADER = "textureloader";
/*     */   private static final String ATTR_SCALE = "scale";
/*     */   private static final String ATTR_NAME = "name";
/*     */   private static final String ATTR_FPS = "fps";
/*     */   private static final String ATTR_TRANSPARENT = "transparent";
/*     */   private static final String VALUE_TRUE = "true";
/*     */   private static final String ELEM_PET = "pet";
/*     */   private static final String ELEM_PLAYER = "player";
/*     */   private Map<String, ElementPart> parts;
/*     */   private Set<ModularDescription.Animation> animations;
/*     */   private float scale;
/*     */   
/*     */   public XmlModularDescription(Document document) {
/*  36 */     this.parts = new HashMap<String, ElementPart>();
/*  37 */     this.animations = new HashSet<ModularDescription.Animation>();
/*     */     
/*  39 */     readScale(document);
/*  40 */     readParts(document);
/*  41 */     readAnimations(document);
/*     */   }
/*     */   
/*     */   private void readScale(Document document) {
/*  45 */     String scaleString = document.getRootElement().getAttributeValue("scale");
/*  46 */     this.scale = StringUtils.parseFloat(scaleString, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModularDescription.Part getPetModel() {
/*  51 */     return null;
/*     */   }
/*     */   
/*     */   private void readParts(Document document) {
/*  55 */     List<Element> partElements = document.getRootElement().getChild("parts").getChildren();
/*  56 */     for (Element partElement : partElements) {
/*  57 */       ElementPart elementPart = new ElementPart(partElement);
/*  58 */       this.parts.put(elementPart.getPartName(), elementPart);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readAnimations(Document document) {
/*  63 */     List<Element> animationElements = document.getRootElement().getChild("animations").getChildren();
/*  64 */     for (Element animationElement : animationElements) {
/*  65 */       ElementAnimation elementAnimation = new ElementAnimation(animationElement);
/*  66 */       this.animations.add(elementAnimation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ModularDescription.Part> getBodyParts() {
/*  72 */     return new HashSet<ModularDescription.Part>(this.parts.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getBodyPartNames() {
/*  77 */     return this.parts.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public ModularDescription.Part getBodyPart(String partName) {
/*  82 */     return this.parts.get(partName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ModularDescription.Animation> getAnimations() {
/*  87 */     return this.animations;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getScale() {
/*  92 */     return this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  97 */     clearChangedListeners();
/*     */   }
/*     */   
/*     */   private static class ElementPart implements ModularDescription.Part {
/*     */     private String partName;
/*     */     private String meshPath;
/*     */     private List<ModularDescription.TexturePart> textureLayers;
/*     */     
/*     */     private ElementPart(Element element) {
/* 106 */       this.textureLayers = new LinkedList<ModularDescription.TexturePart>();
/*     */       
/* 108 */       this.partName = element.getName();
/* 109 */       this.meshPath = element.getChildTextTrim("mesh");
/*     */       
/* 111 */       List<Element> mapElements = element.getChild("maps").getChildren();
/* 112 */       for (Element mapElement : mapElements) {
/* 113 */         this.textureLayers.add(new XmlModularDescription.XmlTexturePart(mapElement));
/*     */       }
/*     */     }
/*     */     
/*     */     public String getPartName() {
/* 118 */       return this.partName;
/*     */     }
/*     */     
/*     */     public boolean isVisible() {
/* 122 */       return true;
/*     */     }
/*     */     
/*     */     public String getMeshPath() {
/* 126 */       return this.meshPath;
/*     */     }
/*     */     
/*     */     public List<ModularDescription.TexturePart> getTextureParts() {
/* 130 */       return this.textureLayers;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class XmlTexturePart implements ModularDescription.TexturePart {
/*     */     private final String name;
/*     */     private final boolean transparent;
/*     */     private final List<String> textures;
/*     */     private final ModularDescription.TextureLoaderDescription loaderDescription;
/*     */     
/*     */     public XmlTexturePart(Element element) {
/* 141 */       this.name = element.getAttributeValue("name");
/*     */       
/* 143 */       this.transparent = "true".equalsIgnoreCase(element.getAttributeValue("transparent"));
/*     */       
/* 145 */       List<Element> textureElements = element.getChildren("texture");
/* 146 */       this.textures = new ArrayList<String>(textureElements.size());
/* 147 */       for (Element textureElement : textureElements) {
/* 148 */         this.textures.add(textureElement.getTextTrim());
/*     */       }
/*     */       
/* 151 */       Element textureLoaderEl = element.getChild("textureloader");
/* 152 */       if (textureLoaderEl != null) {
/* 153 */         this.loaderDescription = new XmlModularDescription.XmlTextureLoaderDescription(textureLoaderEl);
/*     */       } else {
/* 155 */         this.loaderDescription = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTextureMap() {
/* 161 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTransparent() {
/* 166 */       return this.transparent;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextureLayerCount() {
/* 171 */       return this.textures.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getTextureLayers() {
/* 176 */       return this.textures;
/*     */     }
/*     */ 
/*     */     
/*     */     public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/* 181 */       return this.loaderDescription;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class XmlTextureLoaderDescription implements ModularDescription.TextureLoaderDescription {
/*     */     private final String id;
/*     */     private final Map<String, String> params;
/*     */     
/*     */     private XmlTextureLoaderDescription(Element element) {
/* 190 */       this.id = element.getAttributeValue("id");
/* 191 */       List attribs = element.getAttributes();
/* 192 */       this.params = new HashMap<String, String>(attribs.size() + 1, 0.75F);
/* 193 */       for (Object attribObj : attribs) {
/* 194 */         Attribute attribute = (Attribute)attribObj;
/* 195 */         this.params.put(attribute.getName(), attribute.getValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getId() {
/* 201 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, String> getParams() {
/* 206 */       return this.params;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ElementAnimation implements ModularDescription.Animation {
/*     */     private final String animationName;
/*     */     private final String playerAnimationPath;
/*     */     private final String petAnimationPath;
/*     */     private final int playerFps;
/*     */     private final int petFps;
/*     */     
/*     */     private ElementAnimation(Element element) {
/* 218 */       this.animationName = element.getAttributeValue("name").intern();
/*     */       
/* 220 */       if (element.getChild("pet") == null) {
/* 221 */         this.petAnimationPath = null;
/* 222 */         this.petFps = 0;
/* 223 */         this.playerAnimationPath = element.getTextTrim().intern();
/* 224 */         this.playerFps = Integer.parseInt(element.getAttributeValue("fps"));
/*     */       } else {
/* 226 */         this.petAnimationPath = element.getChild("pet").getTextTrim().intern();
/* 227 */         this.petFps = Integer.parseInt(element.getChild("pet").getAttributeValue("fps"));
/* 228 */         this.playerAnimationPath = element.getChild("player").getTextTrim().intern();
/* 229 */         this.playerFps = Integer.parseInt(element.getChild("player").getAttributeValue("fps"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPlayerAnimationPath() {
/* 235 */       return this.playerAnimationPath;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAnimationName() {
/* 240 */       return this.animationName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPlayerFrameRate() {
/* 245 */       return this.playerFps;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPetFrameRate() {
/* 250 */       return this.petFps;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPetAnimationPath() {
/* 255 */       return this.petAnimationPath;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\modular\XmlModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */