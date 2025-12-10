/*     */ package com.funcom.gameengine.view;
/*     */ import com.funcom.commons.dfx.AbstractEffectDescription;
import com.funcom.commons.dfx.AnimationEffectDescription;
/*     */ import com.funcom.commons.dfx.CameraShakeEffectDescription;
import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.EffectDescription;
import com.funcom.commons.dfx.EffectDescriptionFactory;
import com.funcom.commons.dfx.EffectHandlerFactory;
import com.funcom.commons.dfx.GUIParticleDescription;
/*     */ import com.funcom.commons.dfx.LightSourceEffectDescription;
import com.funcom.commons.dfx.MeshEffectDescription;
/*     */ import com.funcom.commons.dfx.ParticleEffectDescription;
/*     */ import com.funcom.commons.dfx.PositionalEffectDescription;
/*     */ import com.funcom.commons.dfx.TextEffectDescription;

import org.jdom.Attribute;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class XmlEffectDescriptionFactory implements EffectDescriptionFactory {
/*     */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*     */   
/*     */   public XmlEffectDescriptionFactory(DireEffectDescriptionFactory effectDescriptionFactory, EffectHandlerFactory guiEffectFactory) {
/*  15 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*  16 */     this.guiEffectFactory = guiEffectFactory;
/*     */   }
/*     */   private EffectHandlerFactory guiEffectFactory;
/*     */   public EffectDescription createTextEffect(Element effectRootElement) {
/*  20 */     TextEffectDescription textEffectDescription = new TextEffectDescription();
/*  21 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)textEffectDescription);
/*     */     
/*  23 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/*     */     
/*  25 */     textEffectDescription.setEndTime(textEffectDescription.getStartTime());
/*     */     
/*  27 */     String playSpeedStr = effectSpecificProperties.getChildText("Scale");
/*  28 */     float scale = Float.parseFloat(playSpeedStr);
/*  29 */     textEffectDescription.setScale(scale);
/*     */     
/*  31 */     Element colorElement = effectSpecificProperties.getChild("Color");
/*  32 */     float r = Float.parseFloat(colorElement.getAttribute("R").getValue());
/*  33 */     float g = Float.parseFloat(colorElement.getAttribute("G").getValue());
/*  34 */     float b = Float.parseFloat(colorElement.getAttribute("B").getValue());
/*  35 */     textEffectDescription.setColor(r, g, b);
/*     */     
/*  37 */     String window = effectSpecificProperties.getChildText("Window");
/*  38 */     textEffectDescription.setWindow(window);
/*     */     
/*  40 */     String broadcastStr = effectSpecificProperties.getChildText("Broadcast");
/*  41 */     boolean broadcast = Boolean.parseBoolean(broadcastStr);
/*  42 */     textEffectDescription.setBroadcast(broadcast);
/*     */     
/*  44 */     textEffectDescription.setHandlerFactory(new TextEffectHandlerFactory());
/*  45 */     return (EffectDescription)textEffectDescription;
/*     */   }
/*     */   
/*     */   public EffectDescription createAnimationEffect(Element effectRootElement) {
/*  49 */     AnimationEffectDescription animationEffectDescription = new AnimationEffectDescription();
/*  50 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)animationEffectDescription);
/*     */     
/*  52 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/*     */     
/*  54 */     String durationStr = effectSpecificProperties.getChildTextTrim("Duration");
/*  55 */     double duration = getDuration(durationStr);
/*  56 */     animationEffectDescription.setEndTime(animationEffectDescription.getStartTime() + duration);
/*     */     
/*  58 */     String playSpeedStr = effectSpecificProperties.getChildText("PlaySpeed");
/*  59 */     float playSpeed = Float.parseFloat(playSpeedStr);
/*  60 */     animationEffectDescription.setPlaySpeed(playSpeed);
/*     */     
/*  62 */     String loopedStr = effectSpecificProperties.getChildText("Looped");
/*  63 */     boolean looped = Boolean.parseBoolean(loopedStr);
/*  64 */     animationEffectDescription.setLooped(looped);
/*  65 */     animationEffectDescription.setHandlerFactory(new AnimationEffectHandlerFactory());
/*  66 */     return (EffectDescription)animationEffectDescription;
/*     */   }
/*     */   
/*     */   private double getDuration(String durationStr) {
/*     */     double duration;
/*  71 */     if (durationStr.equalsIgnoreCase("INF")) {
/*  72 */       duration = Double.POSITIVE_INFINITY;
/*     */     } else {
/*  74 */       duration = Double.parseDouble(durationStr);
/*     */     } 
/*  76 */     return duration;
/*     */   }
/*     */   
/*     */   public EffectDescription createParticleEffect(Element effectRootElement, boolean cacheDFX) {
/*  80 */     ParticleEffectDescription particleEffectDescription = new ParticleEffectDescription();
/*  81 */     particleEffectDescription.setCache(cacheDFX);
/*  82 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)particleEffectDescription);
/*  83 */     particleEffectDescription.setHandlerFactory(new ParticleEffectHandlerFactory());
/*     */     
/*  85 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/*  86 */     getPostitionalEffectProperties((PositionalEffectDescription)particleEffectDescription, effectSpecificProperties);
/*     */     
/*  88 */     String disconnectParticlesStr = effectSpecificProperties.getChildTextTrim("DisconnectParticles");
/*  89 */     boolean disconnectParticles = false;
/*  90 */     if (disconnectParticlesStr != null)
/*  91 */       disconnectParticles = Boolean.parseBoolean(disconnectParticlesStr); 
/*  92 */     particleEffectDescription.setDisconnectParticles(disconnectParticles);
/*     */     
/*  94 */     String disconnectEmitterStr = effectSpecificProperties.getChildTextTrim("DisconnectEmitter");
/*  95 */     boolean disconnectEmitter = false;
/*  96 */     if (disconnectEmitterStr != null)
/*  97 */       disconnectEmitter = Boolean.parseBoolean(disconnectEmitterStr); 
/*  98 */     particleEffectDescription.setDisconnectEmitter(disconnectEmitter);
/*     */     
/* 100 */     String killParticlesStr = effectSpecificProperties.getChildTextTrim("KillParticles");
/* 101 */     boolean killParticles = true;
/* 102 */     if (killParticlesStr != null)
/* 103 */       killParticles = Boolean.parseBoolean(killParticlesStr); 
/* 104 */     particleEffectDescription.setKillInstantly(killParticles);
/*     */     
/* 106 */     return (EffectDescription)particleEffectDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public EffectDescription createMeshEffect(Element effectRootElement) {
/* 111 */     MeshEffectDescription meshEffectDescription = new MeshEffectDescription();
/* 112 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)meshEffectDescription);
/* 113 */     meshEffectDescription.setHandlerFactory(new MeshEffectHandlerFactory());
/*     */     
/* 115 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/* 116 */     getPostitionalEffectProperties((PositionalEffectDescription)meshEffectDescription, effectSpecificProperties);
/*     */     
/* 118 */     String disconnectMeshStr = effectSpecificProperties.getChildTextTrim("DisconnectMesh");
/* 119 */     boolean disconnectMesh = false;
/* 120 */     if (disconnectMeshStr != null)
/* 121 */       disconnectMesh = Boolean.parseBoolean(disconnectMeshStr); 
/* 122 */     meshEffectDescription.setDisconnectMesh(disconnectMesh);
/*     */     
/* 124 */     Element subDfx = effectSpecificProperties.getChild("DFX");
/* 125 */     meshEffectDescription.setDfx(subDfx);
/* 126 */     meshEffectDescription.setEffectDescriptionFactory(this.effectDescriptionFactory);
/* 127 */     return (EffectDescription)meshEffectDescription;
/*     */   }
/*     */   
/*     */   private void getPostitionalEffectProperties(PositionalEffectDescription positionalEffectDescription, Element effectSpecificProperties) {
/* 131 */     String durationStr = effectSpecificProperties.getChildTextTrim("Duration");
/* 132 */     double duration = getDuration(durationStr);
/* 133 */     positionalEffectDescription.setEndTime(positionalEffectDescription.getStartTime() + duration);
/* 134 */     Element scaleElem = effectSpecificProperties.getChild("Scale");
/* 135 */     if (scaleElem != null) {
/* 136 */       String scaleStr = scaleElem.getText();
/* 137 */       if (scaleStr != null) {
/* 138 */         double scale = Double.parseDouble(scaleStr);
/* 139 */         positionalEffectDescription.setScale(scale);
/*     */       } 
/* 141 */       Attribute relativeAttribute = scaleElem.getAttribute("relative");
/* 142 */       if (relativeAttribute != null) {
/* 143 */         positionalEffectDescription.setRelativeScale(Boolean.parseBoolean(relativeAttribute.getValue()));
/*     */       }
/*     */     } 
/*     */     
/* 147 */     String boneStr = effectSpecificProperties.getChildTextTrim("Bone");
/* 148 */     if (boneStr != null) {
/* 149 */       positionalEffectDescription.setBone(boneStr);
/*     */     } else {
/* 151 */       positionalEffectDescription.setBone("None");
/*     */     } 
/* 153 */     Element offsetPosElement = effectSpecificProperties.getChild("OffsetPosition");
/* 154 */     if (offsetPosElement != null) {
/* 155 */       float[] offsetPos = getVector(offsetPosElement);
/* 156 */       positionalEffectDescription.setOffsetPos(offsetPos);
/* 157 */       Attribute relativeAttribute = offsetPosElement.getAttribute("relative");
/* 158 */       if (relativeAttribute != null) {
/* 159 */         positionalEffectDescription.setRelativeOffset(Boolean.parseBoolean(relativeAttribute.getValue()));
/*     */       }
/*     */     } 
/* 162 */     Element offsetAngleElement = effectSpecificProperties.getChild("OffsetAngle");
/* 163 */     if (offsetAngleElement != null) {
/* 164 */       float[] offsetAngle = getEuler(offsetAngleElement);
/* 165 */       positionalEffectDescription.setOffsetAngle(offsetAngle);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float[] getVector(Element child) {
/* 170 */     float[] vector = new float[3];
/* 171 */     vector[0] = Float.parseFloat(child.getChildText("X"));
/* 172 */     vector[1] = Float.parseFloat(child.getChildText("Y"));
/* 173 */     vector[2] = Float.parseFloat(child.getChildText("Z"));
/* 174 */     return vector;
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getEuler(Element child) {
/* 179 */     float[] angles = new float[3];
/* 180 */     angles[0] = Float.parseFloat(child.getChildText("Pitch"));
/* 181 */     angles[1] = Float.parseFloat(child.getChildText("Yaw"));
/* 182 */     angles[2] = Float.parseFloat(child.getChildText("Roll"));
/* 183 */     return angles;
/*     */   }
/*     */ 
/*     */   
/*     */   public EffectDescription createEffect(Element effectRootElement, boolean cacheDFX) {
/* 188 */     String elementType = effectRootElement.getChildText("Type");
/* 189 */     if (elementType.equals("Animation"))
/* 190 */       return createAnimationEffect(effectRootElement); 
/* 191 */     if (elementType.equals("ParticleEffect"))
/* 192 */       return createParticleEffect(effectRootElement, cacheDFX); 
/* 193 */     if (elementType.equals("Mesh"))
/* 194 */       return createMeshEffect(effectRootElement); 
/* 195 */     if (elementType.equals("Audio"))
/* 196 */       return createAudioEffect(effectRootElement); 
/* 197 */     if (elementType.equals("Text"))
/* 198 */       return createTextEffect(effectRootElement); 
/* 199 */     if (elementType.equals("CameraShake"))
/* 200 */       return createCameraShakeEffect(effectRootElement); 
/* 201 */     if (elementType.equals("GUIParticle"))
/* 202 */       return createGUIParticle(effectRootElement, cacheDFX); 
/* 203 */     if (elementType.equals("LightSource")) {
/* 204 */       return createLightSource(effectRootElement, cacheDFX);
/*     */     }
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   private EffectDescription createLightSource(Element effectRootElement, boolean cacheDFX) {
/* 210 */     LightSourceEffectDescription desc = new LightSourceEffectDescription();
/* 211 */     desc.setHandlerFactory(new LightSourceHandlerFactory());
/* 212 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)desc);
/* 213 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/* 214 */     getPostitionalEffectProperties((PositionalEffectDescription)desc, effectSpecificProperties);
/*     */ 
/*     */     
/* 217 */     Element ambientElement = effectSpecificProperties.getChild("Ambient");
/* 218 */     float[] ambient = getColor(ambientElement);
/* 219 */     desc.setAmbient(ambient);
/* 220 */     Element diffuseElement = effectSpecificProperties.getChild("Diffuse");
/* 221 */     float[] diffuse = getColor(diffuseElement);
/* 222 */     desc.setDiffuse(diffuse);
/* 223 */     Element specularElement = effectSpecificProperties.getChild("Specular");
/* 224 */     float[] specular = getColor(specularElement);
/* 225 */     desc.setSpecular(specular);
/*     */     
/* 227 */     Element attenuationEllement = effectSpecificProperties.getChild("Attenuation");
/* 228 */     if (attenuationEllement != null) {
/* 229 */       if (desc.getResource().equals("DirectionalLight")) {
/* 230 */         throw new RuntimeException("Directional lights can not have attenuation!");
/*     */       }
/* 232 */       String constantText = attenuationEllement.getChildTextTrim("Constant");
/* 233 */       if (constantText != null) {
/* 234 */         desc.setConstantAttenuation(Float.parseFloat(constantText));
/*     */       }
/* 236 */       String linearText = attenuationEllement.getChildTextTrim("Linear");
/* 237 */       if (linearText != null) {
/* 238 */         desc.setLinearAttenuation(Float.parseFloat(linearText));
/*     */       }
/* 240 */       String quadratictText = attenuationEllement.getChildTextTrim("Quadratic");
/* 241 */       if (quadratictText != null) {
/* 242 */         desc.setQuadraticAttenuation(Float.parseFloat(quadratictText));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 247 */     return (EffectDescription)desc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] getColor(Element colorElement) {
/* 253 */     if (colorElement == null) {
/* 254 */       return new float[3];
/*     */     }
/* 256 */     float[] ret = new float[3];
/* 257 */     ret[0] = Float.parseFloat(colorElement.getAttribute("R").getValue());
/* 258 */     ret[1] = Float.parseFloat(colorElement.getAttribute("G").getValue());
/* 259 */     ret[2] = Float.parseFloat(colorElement.getAttribute("B").getValue());
/* 260 */     return ret;
/*     */   }
/*     */   
/*     */   private EffectDescription createGUIParticle(Element effectRootElement, boolean cacheDFX) {
/* 264 */     GUIParticleDescription desc = new GUIParticleDescription();
/* 265 */     desc.setHandlerFactory(this.guiEffectFactory);
/* 266 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)desc);
/* 267 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/*     */     
/* 269 */     String name = effectSpecificProperties.getChildTextTrim("Name");
/* 270 */     desc.setName(name);
/*     */     
/* 272 */     String referencePoint = effectSpecificProperties.getChildTextTrim("ReferencePoint");
/* 273 */     desc.setReferencePoint(referencePoint);
/*     */     
/* 275 */     Element offset = effectSpecificProperties.getChild("Offset");
/* 276 */     int x = Integer.parseInt(offset.getChildTextTrim("X"));
/* 277 */     int y = Integer.parseInt(offset.getChildTextTrim("Y"));
/* 278 */     desc.setOffset(x, y);
/* 279 */     desc.setDFXCache(cacheDFX);
/* 280 */     return (EffectDescription)desc;
/*     */   }
/*     */   
/*     */   private EffectDescription createCameraShakeEffect(Element effectRootElement) {
/* 284 */     CameraShakeEffectDescription cameraShakeEffectDescription = new CameraShakeEffectDescription();
/* 285 */     cameraShakeEffectDescription.setHandlerFactory(new CameraShakeEffectHandlerFactory());
/* 286 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)cameraShakeEffectDescription);
/* 287 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/* 288 */     String durationStr = effectSpecificProperties.getChildTextTrim("Duration");
/* 289 */     double duration = getDuration(durationStr);
/* 290 */     cameraShakeEffectDescription.setEndTime(cameraShakeEffectDescription.getStartTime() + duration);
/* 291 */     String amplitudeStr = effectSpecificProperties.getChildTextTrim("AmplitudeX");
/* 292 */     if (amplitudeStr != null)
/* 293 */       cameraShakeEffectDescription.setAmplitudeX(Double.parseDouble(amplitudeStr)); 
/* 294 */     String speedStr = effectSpecificProperties.getChildTextTrim("CyclesX");
/* 295 */     if (speedStr != null)
/* 296 */       cameraShakeEffectDescription.setCyclesX(Double.parseDouble(speedStr)); 
/* 297 */     amplitudeStr = effectSpecificProperties.getChildTextTrim("AmplitudeY");
/* 298 */     if (amplitudeStr != null)
/* 299 */       cameraShakeEffectDescription.setAmplitudeY(Double.parseDouble(amplitudeStr)); 
/* 300 */     speedStr = effectSpecificProperties.getChildTextTrim("CyclesY");
/* 301 */     if (speedStr != null) {
/* 302 */       cameraShakeEffectDescription.setCyclesY(Double.parseDouble(speedStr));
/*     */     }
/* 304 */     String broadcastStr = effectSpecificProperties.getChildText("Broadcast");
/* 305 */     boolean broadcast = Boolean.parseBoolean(broadcastStr);
/* 306 */     cameraShakeEffectDescription.setBroadcast(broadcast);
/*     */     
/* 308 */     String rangeStr = effectSpecificProperties.getChildTextTrim("Range");
/* 309 */     if (rangeStr != null) {
/* 310 */       cameraShakeEffectDescription.setRange(Double.parseDouble(rangeStr));
/*     */     }
/* 312 */     return (EffectDescription)cameraShakeEffectDescription;
/*     */   }
/*     */   
/*     */   public EffectDescription createAudioEffect(Element effectRootElement) {
/* 316 */     AudioEffectDescription audioEffectDescription = new AudioEffectDescription();
/* 317 */     readStandardProperties(effectRootElement, (AbstractEffectDescription)audioEffectDescription);
/*     */     
/* 319 */     Element effectSpecificProperties = effectRootElement.getChild("EffectSpecificProperties");
/* 320 */     if (effectSpecificProperties != null) {
/* 321 */       getPostitionalEffectProperties(audioEffectDescription, effectSpecificProperties);
/*     */     }
/*     */     
/* 324 */     audioEffectDescription.setHandlerFactory(new AudioEffectHandlerFactory());
/* 325 */     return (EffectDescription)audioEffectDescription;
/*     */   }
/*     */   
/*     */   private void readStandardProperties(Element effectRootElement, AbstractEffectDescription effectDescription) {
/* 329 */     String startTimeStr = effectRootElement.getChildTextTrim("StartTime");
/* 330 */     double startTime = Double.parseDouble(startTimeStr);
/* 331 */     effectDescription.setStartTime(startTime);
/*     */     
/* 333 */     effectDescription.setEndTime(startTime);
/*     */     
/* 335 */     String resource = effectRootElement.getChildText("Resource");
/* 336 */     effectDescription.setResource(resource);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\XmlEffectDescriptionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */