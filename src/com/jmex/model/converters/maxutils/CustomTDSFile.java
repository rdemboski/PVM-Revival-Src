/*     */ package com.jmex.model.converters.maxutils;
/*     */ 
/*     */ import com.jme.animation.SpatialTransformer;
/*     */ import com.jme.light.Light;
/*     */ import com.jme.light.PointLight;
/*     */ import com.jme.light.SpotLight;
/*     */ import com.jme.math.TransformMatrix;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.LightState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.system.JmeException;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CustomTDSFile
/*     */   extends ChunkerClass
/*     */ {
/*  33 */   private static final Logger logger = Logger.getLogger(TDSFile.class.getName());
/*     */ 
/*     */   
/*  36 */   private CustomEditableObjectChunk objects = null;
/*  37 */   private KeyframeChunk keyframes = null;
/*     */   private List<Spatial> spatialNodes;
/*     */   private List<String> spatialNodesNames;
/*     */   private SpatialTransformer st;
/*     */   private List<Light> spatialLights;
/*     */   private BlendState alpha;
/*     */   private TextureLoadDelegate textureLoadDelegate;
/*     */   private String modelPath;
/*     */   
/*     */   public CustomTDSFile(DataInput myIn, TextureLoadDelegate textureLoadDelegate, String modelPath) throws IOException {
/*  47 */     super(myIn);
/*  48 */     this.textureLoadDelegate = textureLoadDelegate;
/*  49 */     this.modelPath = modelPath;
/*  50 */     ChunkHeader c = new ChunkHeader(myIn);
/*  51 */     if (c.type != 19789)
/*  52 */       throw new IOException("Header doesn't match 0x4D4D; Header=" + Integer.toHexString(c.type)); 
/*  53 */     c.length -= 6;
/*  54 */     setHeader(c);
/*     */     
/*  56 */     chunk();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processChildChunk(ChunkHeader i) throws IOException {
/*  61 */     switch (i.type) {
/*     */       case 2:
/*  63 */         readVersion();
/*  64 */         return true;
/*     */       case 15677:
/*  66 */         this.objects = new CustomEditableObjectChunk(this.myIn, i, this.textureLoadDelegate, this.modelPath);
/*  67 */         return true;
/*     */       case 45056:
/*  69 */         this.keyframes = new KeyframeChunk(this.myIn, i);
/*  70 */         return true;
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readVersion() throws IOException {
/*  78 */     int version = this.myIn.readInt();
/*  79 */     if (DEBUG || DEBUG_LIGHT) logger.info("Version:" + version); 
/*     */   }
/*     */   
/*     */   public Node buildScene() throws IOException {
/*  83 */     buildObject();
/*  84 */     putTranslations();
/*  85 */     Node uberNode = new Node("TDS Scene");
/*  86 */     for (Spatial spatialNode : this.spatialNodes) {
/*  87 */       if (spatialNode != null) {
/*  88 */         Spatial toAttach = spatialNode;
/*  89 */         if (toAttach.getParent() == null) {
/*  90 */           uberNode.attachChild(toAttach);
/*     */         }
/*     */       } 
/*     */     } 
/*  94 */     LightState ls = null;
/*  95 */     for (Light spatialLight : this.spatialLights) {
/*  96 */       if (ls == null) {
/*  97 */         ls = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
/*  98 */         ls.setEnabled(true);
/*     */       } 
/* 100 */       ls.attach(spatialLight);
/*     */     } 
/* 102 */     if (ls != null) {
/* 103 */       uberNode.setRenderState((RenderState)ls);
/*     */     }
/*     */     
/* 106 */     if (this.keyframes != null) {
/* 107 */       this.st.interpolateMissing();
/* 108 */       if (this.st.keyframes.size() == 1) {
/*     */         
/* 110 */         this.st.update(0.0F);
/*     */       } else {
/*     */         
/* 113 */         uberNode.addController((Controller)this.st);
/* 114 */         this.st.setActive(true);
/*     */       } 
/*     */     } 
/* 117 */     return uberNode;
/*     */   }
/*     */   
/*     */   private void putTranslations() {
/* 121 */     if (this.keyframes == null)
/* 122 */       return;  int spatialCount = 0;
/* 123 */     for (Spatial spatialNode : this.spatialNodes) {
/* 124 */       if (spatialNode != null) {
/* 125 */         spatialCount++;
/*     */       }
/*     */     } 
/* 128 */     this.st = new SpatialTransformer(spatialCount);
/* 129 */     spatialCount = 0;
/* 130 */     for (int i = 0; i < this.spatialNodes.size(); i++) {
/* 131 */       if (this.spatialNodes.get(i) != null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 136 */         this.st.setObject(this.spatialNodes.get(i), spatialCount++, -1);
/*     */       }
/*     */     } 
/* 139 */     Object[] keysetKeyframe = this.keyframes.objKeyframes.keySet().toArray();
/* 140 */     for (Object aKeysetKeyframe : keysetKeyframe) {
/* 141 */       KeyframeInfoChunk thisOne = (KeyframeInfoChunk)this.keyframes.objKeyframes.get(aKeysetKeyframe);
/* 142 */       if (!"$$$DUMMY".equals(thisOne.name)) {
/*     */ 
/*     */         
/* 145 */         int indexInST = findIndex(thisOne.name);
/* 146 */         for (Object aTrack : thisOne.track) {
/* 147 */           KeyframeInfoChunk.KeyPointInTime thisTime = (KeyframeInfoChunk.KeyPointInTime)aTrack;
/* 148 */           if (thisTime.rot != null) {
/* 149 */             this.st.setRotation(indexInST, thisTime.frame, thisTime.rot);
/*     */           }
/* 151 */           if (thisTime.position != null) {
/* 152 */             this.st.setPosition(indexInST, thisTime.frame, thisTime.position);
/*     */           }
/* 154 */           if (thisTime.scale != null)
/* 155 */             this.st.setScale(indexInST, thisTime.frame, thisTime.scale); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 159 */     this.st.setSpeed(10.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findIndex(String name) {
/* 164 */     int j = 0;
/* 165 */     for (int i = 0; i < this.spatialNodesNames.size(); i++) {
/* 166 */       if (((String)this.spatialNodesNames.get(i)).equals(name)) return j; 
/* 167 */       if (this.spatialNodes.get(i) != null) j++; 
/*     */     } 
/* 169 */     throw new JmeException("Logic error.  Unknown keyframe name " + name);
/*     */   }
/*     */   
/*     */   private int getParentIndex(int objectIndex) {
/* 173 */     if (this.keyframes.objKeyframes.get(this.spatialNodesNames.get(objectIndex)) == null)
/* 174 */       return -2; 
/* 175 */     short parentID = ((KeyframeInfoChunk)this.keyframes.objKeyframes.get(this.spatialNodesNames.get(objectIndex))).parent;
/* 176 */     if (parentID == -1) return -1; 
/* 177 */     Object[] objs = this.keyframes.objKeyframes.keySet().toArray();
/* 178 */     for (int i = 0; i < objs.length; i++) {
/* 179 */       if (((KeyframeInfoChunk)this.keyframes.objKeyframes.get(objs[i])).myID == parentID)
/* 180 */         return i; 
/*     */     } 
/* 182 */     throw new JmeException("Logic error.  Unknown parent ID for " + objectIndex);
/*     */   }
/*     */   
/*     */   private void buildObject() throws IOException {
/* 186 */     this.spatialNodes = new ArrayList<Spatial>();
/* 187 */     this.spatialLights = new ArrayList<Light>();
/* 188 */     this.spatialNodesNames = new ArrayList<String>();
/* 189 */     Map<Short, Node> nodesByID = new HashMap<Short, Node>();
/* 190 */     if (this.keyframes != null) {
/* 191 */       for (Object o : this.keyframes.objKeyframes.entrySet()) {
/* 192 */         Map.Entry entry = (Map.Entry)o;
/* 193 */         String name = (String)entry.getKey();
/* 194 */         if (!this.objects.namedObjects.containsKey(name)) {
/* 195 */           KeyframeInfoChunk info = (KeyframeInfoChunk)entry.getValue();
/* 196 */           Node node = new Node(info.name);
/* 197 */           nodesByID.put(Short.valueOf(info.myID), node);
/* 198 */           this.spatialNodesNames.add(name);
/* 199 */           this.spatialNodes.add(node);
/*     */         } 
/*     */       } 
/*     */     }
/* 203 */     for (Object o1 : this.objects.namedObjects.entrySet()) {
/* 204 */       Map.Entry entry = (Map.Entry)o1;
/* 205 */       String objectKey = (String)entry.getKey();
/* 206 */       NamedObjectChunk noc = (NamedObjectChunk)entry.getValue();
/*     */       
/* 208 */       KeyframeInfoChunk kfInfo = null;
/* 209 */       if (this.keyframes != null && this.keyframes.objKeyframes != null) {
/* 210 */         kfInfo = (KeyframeInfoChunk)this.keyframes.objKeyframes.get(objectKey);
/*     */       }
/* 212 */       if (noc.whatIAm instanceof TriMeshChunk) {
/* 213 */         Node node1, myNode = new Node(objectKey);
/*     */ 
/*     */         
/* 216 */         if (kfInfo == null) {
/* 217 */           putChildMeshes(myNode, (TriMeshChunk)noc.whatIAm, new Vector3f(0.0F, 0.0F, 0.0F));
/* 218 */           Spatial spatial = usedSpatial(myNode);
/*     */         } else {
/* 220 */           putChildMeshes(myNode, (TriMeshChunk)noc.whatIAm, kfInfo.pivot);
/* 221 */           node1 = myNode;
/* 222 */           nodesByID.put(Short.valueOf(kfInfo.myID), myNode);
/*     */         } 
/*     */         
/* 225 */         this.spatialNodesNames.add(noc.name);
/* 226 */         this.spatialNodes.add(node1); continue;
/*     */       } 
/* 228 */       if (noc.whatIAm instanceof LightChunk) {
/* 229 */         this.spatialLights.add(createChildLight((LightChunk)noc.whatIAm));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (this.keyframes != null) {
/* 235 */       for (Object o : this.keyframes.objKeyframes.entrySet()) {
/* 236 */         Map.Entry entry = (Map.Entry)o;
/* 237 */         KeyframeInfoChunk kfInfo = (KeyframeInfoChunk)entry.getValue();
/* 238 */         if (kfInfo.parent != -1) {
/* 239 */           Node node = nodesByID.get(Short.valueOf(kfInfo.myID));
/* 240 */           if (node != null) {
/* 241 */             Node parentNode = nodesByID.get(Short.valueOf(kfInfo.parent));
/* 242 */             if (parentNode != null) {
/* 243 */               parentNode.attachChild((Spatial)node); continue;
/*     */             } 
/* 245 */             throw new JmeException("Parent node (id=" + kfInfo.parent + ") not foudn!");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Spatial usedSpatial(Node myNode) {
/*     */     Node node;
/* 255 */     if (myNode.getQuantity() == 1) {
/* 256 */       myNode.getChild(0).setName(myNode.getName());
/* 257 */       Spatial spatial = myNode.getChild(0);
/* 258 */       myNode.detachChild(spatial);
/*     */     } else {
/* 260 */       node = myNode;
/*     */     } 
/* 262 */     return (Spatial)node;
/*     */   }
/*     */ 
/*     */   
/*     */   private Light createChildLight(LightChunk lightChunk) {
/* 267 */     if (lightChunk.spotInfo != null) {
/* 268 */       SpotLight spotLight = new SpotLight();
/* 269 */       spotLight.setLocation(lightChunk.myLoc);
/* 270 */       spotLight.setDiffuse(lightChunk.lightColor);
/* 271 */       spotLight.setAmbient(ColorRGBA.black.clone());
/* 272 */       spotLight.setSpecular(ColorRGBA.white.clone());
/* 273 */       Vector3f tempDir = lightChunk.myLoc.subtract(lightChunk.spotInfo.target).multLocal(-1.0F);
/* 274 */       tempDir.normalizeLocal();
/* 275 */       spotLight.setDirection(tempDir);
/*     */       
/* 277 */       spotLight.setAngle(180.0F);
/* 278 */       spotLight.setEnabled(true);
/* 279 */       return (Light)spotLight;
/*     */     } 
/*     */     
/* 282 */     PointLight toReturn = new PointLight();
/* 283 */     toReturn.setLocation(lightChunk.myLoc);
/* 284 */     toReturn.setDiffuse(lightChunk.lightColor);
/* 285 */     toReturn.setAmbient(ColorRGBA.black.clone());
/* 286 */     toReturn.setSpecular(ColorRGBA.white.clone());
/* 287 */     toReturn.setEnabled(true);
/* 288 */     return (Light)toReturn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void putChildMeshes(Node parentNode, TriMeshChunk whatIAm, Vector3f pivotLoc) throws IOException {
/* 293 */     FacesChunk myFace = whatIAm.face;
/* 294 */     if (myFace == null)
/* 295 */       return;  boolean[] faceHasMaterial = new boolean[myFace.nFaces];
/* 296 */     int noMaterialCount = myFace.nFaces;
/* 297 */     ArrayList<Vector3f> normals = new ArrayList<Vector3f>(myFace.nFaces);
/* 298 */     ArrayList<Vector3f> vertexes = new ArrayList<Vector3f>(myFace.nFaces);
/* 299 */     Vector3f tempNormal = new Vector3f();
/* 300 */     ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>(myFace.nFaces);
/* 301 */     if (whatIAm.coordSystem == null)
/* 302 */       whatIAm.coordSystem = new TransformMatrix(); 
/* 303 */     whatIAm.coordSystem.inverse();
/* 304 */     for (Vector3f vertexe : whatIAm.vertexes) {
/* 305 */       whatIAm.coordSystem.multPoint(vertexe);
/* 306 */       vertexe.subtractLocal(pivotLoc);
/*     */     } 
/* 308 */     Vector3f[] faceNormals = new Vector3f[myFace.nFaces];
/* 309 */     calculateFaceNormals(faceNormals, whatIAm.vertexes, whatIAm.face.faces);
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (DEBUG || DEBUG_LIGHT) logger.info("Precaching"); 
/* 314 */     int[] vertexCount = new int[whatIAm.vertexes.length];
/* 315 */     for (int i = 0; i < myFace.nFaces; i++) {
/* 316 */       for (int n = 0; n < 3; n++) {
/* 317 */         vertexCount[myFace.faces[i][n]] = vertexCount[myFace.faces[i][n]] + 1;
/*     */       }
/*     */     } 
/* 320 */     int[][] realNextFaces = new int[whatIAm.vertexes.length][];
/* 321 */     for (int j = 0; j < realNextFaces.length; j++) {
/* 322 */       realNextFaces[j] = new int[vertexCount[j]];
/*     */     }
/* 324 */     for (int k = 0; k < myFace.nFaces; k++) {
/* 325 */       for (int n = 0; n < 3; n++) {
/* 326 */         int vertexIndex = myFace.faces[k][n];
/* 327 */         vertexCount[vertexIndex] = vertexCount[vertexIndex] - 1; realNextFaces[vertexIndex][vertexCount[vertexIndex] - 1] = k;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 332 */     if (DEBUG || DEBUG_LIGHT) logger.info("Precaching done");
/*     */ 
/*     */     
/* 335 */     int[] indexes = new int[myFace.nFaces * 3];
/*     */     
/* 337 */     for (int m = 0; m < myFace.materialIndexes.size(); m++) {
/* 338 */       String matName = myFace.materialNames.get(m);
/* 339 */       int[] appliedFacesIndexes = myFace.materialIndexes.get(m);
/* 340 */       if (DEBUG_LIGHT || DEBUG)
/* 341 */         logger.info("On material " + matName + " with " + appliedFacesIndexes.length + " faces."); 
/* 342 */       if (appliedFacesIndexes.length != 0) {
/* 343 */         TriMesh part = new TriMesh(parentNode.getName() + "##" + m);
/* 344 */         normals.clear();
/* 345 */         vertexes.clear();
/* 346 */         texCoords.clear();
/* 347 */         int curPosition = 0;
/* 348 */         for (int n = 0; n < appliedFacesIndexes.length; n++) {
/* 349 */           if (DEBUG && n % 500 == 0) logger.info("Face:" + n); 
/* 350 */           int actuallFace = appliedFacesIndexes[n];
/* 351 */           if (!faceHasMaterial[actuallFace]) {
/* 352 */             faceHasMaterial[actuallFace] = true;
/* 353 */             noMaterialCount--;
/*     */           } 
/* 355 */           for (int i1 = 0; i1 < 3; i1++) {
/*     */             
/* 357 */             int vertexIndex = myFace.faces[actuallFace][i1];
/* 358 */             tempNormal.set(faceNormals[actuallFace]);
/* 359 */             calcFacesWithVertexAndSmoothGroup(realNextFaces[vertexIndex], faceNormals, myFace, tempNormal, actuallFace);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 369 */             normals.add(new Vector3f(tempNormal));
/* 370 */             vertexes.add(whatIAm.vertexes[vertexIndex]);
/* 371 */             if (whatIAm.texCoords != null)
/* 372 */               texCoords.add(whatIAm.texCoords[vertexIndex]); 
/* 373 */             indexes[curPosition++] = normals.size() - 1;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 379 */         Vector3f[] newVerts = new Vector3f[vertexes.size()];
/* 380 */         for (int indexV = 0; indexV < newVerts.length; indexV++)
/* 381 */           newVerts[indexV] = vertexes.get(indexV); 
/* 382 */         part.setVertexBuffer(BufferUtils.createFloatBuffer(newVerts));
/* 383 */         part.setNormalBuffer(BufferUtils.createFloatBuffer(normals.<Vector3f>toArray(new Vector3f[0])));
/*     */         
/* 385 */         if (whatIAm.texCoords != null) {
/* 386 */           part.setTextureCoords(TexCoords.makeNew(texCoords.<Vector2f>toArray(new Vector2f[0])));
/*     */         }
/* 388 */         int[] intIndexes = new int[curPosition];
/* 389 */         System.arraycopy(indexes, 0, intIndexes, 0, curPosition);
/* 390 */         part.setIndexBuffer(BufferUtils.createIntBuffer(intIndexes));
/*     */         
/* 392 */         CustomMaterialBlock myMaterials = this.objects.materialBlocks.get(matName);
/* 393 */         if (matName == null)
/* 394 */           throw new IOException("Couldn't find the correct name of " + myMaterials); 
/* 395 */         if (myMaterials.myMatState.isEnabled()) {
/* 396 */           part.setRenderState((RenderState)myMaterials.myMatState);
/* 397 */           if ((myMaterials.myMatState.getDiffuse()).a < 1.0F) {
/* 398 */             part.setRenderQueueMode(3);
/*     */             
/* 400 */             if (this.alpha == null) {
/* 401 */               this.alpha = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 402 */               this.alpha.setEnabled(true);
/* 403 */               this.alpha.setBlendEnabled(true);
/* 404 */               this.alpha.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
/* 405 */               this.alpha.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
/* 406 */               this.alpha.setTestEnabled(true);
/* 407 */               this.alpha.setTestFunction(BlendState.TestFunction.GreaterThan);
/*     */             } 
/* 409 */             part.setRenderState((RenderState)this.alpha);
/*     */           } 
/*     */         } 
/* 412 */         if (myMaterials.myTexState.isEnabled())
/* 413 */           part.setRenderState((RenderState)myMaterials.myTexState); 
/* 414 */         if (myMaterials.myWireState.isEnabled())
/* 415 */           part.setRenderState((RenderState)myMaterials.myWireState); 
/* 416 */         parentNode.attachChild((Spatial)part);
/*     */       } 
/*     */     } 
/* 419 */     if (noMaterialCount != 0) {
/* 420 */       int[] noMaterialIndexes = new int[noMaterialCount * 3];
/* 421 */       int partCount = 0;
/* 422 */       for (int n = 0; n < whatIAm.face.nFaces; n++) {
/* 423 */         if (!faceHasMaterial[n]) {
/* 424 */           noMaterialIndexes[partCount++] = myFace.faces[n][0];
/* 425 */           noMaterialIndexes[partCount++] = myFace.faces[n][1];
/* 426 */           noMaterialIndexes[partCount++] = myFace.faces[n][2];
/*     */         } 
/*     */       } 
/* 429 */       TriMesh noMaterials = new TriMesh(parentNode.getName() + "-1");
/* 430 */       noMaterials.setVertexBuffer(BufferUtils.createFloatBuffer(whatIAm.vertexes));
/* 431 */       noMaterials.setIndexBuffer(BufferUtils.createIntBuffer(noMaterialIndexes));
/* 432 */       parentNode.attachChild((Spatial)noMaterials);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void calculateFaceNormals(Vector3f[] faceNormals, Vector3f[] vertexes, int[][] faces) {
/* 437 */     Vector3f tempa = new Vector3f(), tempb = new Vector3f();
/*     */     
/* 439 */     for (int i = 0; i < faceNormals.length; i++) {
/* 440 */       tempa.set(vertexes[faces[i][0]]);
/* 441 */       tempa.subtractLocal(vertexes[faces[i][1]]);
/* 442 */       tempb.set(vertexes[faces[i][0]]);
/* 443 */       tempb.subtractLocal(vertexes[faces[i][2]]);
/* 444 */       faceNormals[i] = tempa.cross(tempb).normalizeLocal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcFacesWithVertexAndSmoothGroup(int[] thisVertexTable, Vector3f[] faceNormals, FacesChunk myFace, Vector3f tempNormal, int faceIndex) {
/* 451 */     int smoothingGroupValue = myFace.smoothingGroups[faceIndex];
/* 452 */     if (smoothingGroupValue == 0)
/*     */       return; 
/* 454 */     for (int arrayFace : thisVertexTable) {
/* 455 */       if (arrayFace != faceIndex)
/*     */       {
/*     */         
/* 458 */         if ((myFace.smoothingGroups[arrayFace] & smoothingGroupValue) != 0)
/* 459 */           tempNormal.addLocal(faceNormals[arrayFace]); 
/*     */       }
/*     */     } 
/* 462 */     tempNormal.normalizeLocal();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\model\converters\maxutils\CustomTDSFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */