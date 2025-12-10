/*     */ package com.funcom.commons.jme.md5importer.importer;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.AnimImporter;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.OptimizedMeshImporter;
/*     */ import com.funcom.commons.jme.md5importer.resource.DefaultTextureFactory;
/*     */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Controller;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.net.URL;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class MD5Importer
/*     */ {
/*  34 */   public static final Quaternion base = new Quaternion(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*     */   public static final int version = 10;
/*     */   
/*  38 */   private static final Logger logger = Logger.getLogger(MD5Importer.class.getName());
/*     */ 
/*     */ 
/*     */   
/*  42 */   private static final String[] extensions = new String[] { ".jpg", ".tga", ".png", ".dds", ".gif", ".bmp" };
/*     */   
/*  44 */   private static Texture.MinificationFilter MM_Filter = Texture.MinificationFilter.BilinearNearestMipMap;
/*     */   
/*  46 */   private static Texture.MagnificationFilter FM_Filter = Texture.MagnificationFilter.Bilinear;
/*     */   
/*  48 */   private static int anisotropic = 16;
/*     */ 
/*     */   
/*     */   private static boolean orientedBounding;
/*     */ 
/*     */   
/*     */   private StreamTokenizer reader;
/*     */ 
/*     */   
/*     */   private ModelNode modelNode;
/*     */ 
/*     */   
/*     */   private JointAnimation animation;
/*     */   
/*  62 */   private TextureFactory resourceLoader = (TextureFactory)new DefaultTextureFactory();
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
/*     */   public static MD5Importer getInstance() {
/*  78 */     return new MD5Importer();
/*     */   }
/*     */   
/*     */   public void setResourceLoader(TextureFactory resourceLoader) {
/*  82 */     this.resourceLoader = resourceLoader;
/*     */   }
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
/*     */   public void load(URL md5mesh, String modelName, URL md5anim, String animName, int repeatType) throws IOException {
/*  96 */     loadMesh(md5mesh, modelName);
/*  97 */     loadAnim(md5anim, animName);
/*  98 */     assignAnimation(repeatType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadMesh(URL md5mesh, String name) throws IOException {
/* 109 */     InputStream inputStream = md5mesh.openStream();
/* 110 */     loadMesh(inputStream, name);
/* 111 */     inputStream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadMesh(File md5mesh, String name) throws IOException {
/* 122 */     FileInputStream fis = new FileInputStream(md5mesh);
/* 123 */     loadMesh(fis, name);
/* 124 */     fis.close();
/*     */   }
/*     */   
/*     */   public void loadMesh(InputStream inputStream, String name) throws IOException {
/* 128 */     setupReader(inputStream);
/* 129 */     OptimizedMeshImporter optimizedMeshImporter = new OptimizedMeshImporter(this.reader, this.resourceLoader);
/*     */     
/* 131 */     this.modelNode = optimizedMeshImporter.loadMesh(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadAnim(File md5anim, String name) throws IOException {
/* 142 */     FileInputStream inputStream = new FileInputStream(md5anim);
/* 143 */     loadAnim(inputStream, name);
/* 144 */     inputStream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadAnim(URL md5anim, String name) throws IOException {
/* 155 */     InputStream inputStream = md5anim.openStream();
/* 156 */     loadAnim(inputStream, name);
/* 157 */     inputStream.close();
/*     */   }
/*     */   
/*     */   public void loadAnim(InputStream inputStream, String name) throws IOException {
/* 161 */     setupReader(inputStream);
/* 162 */     AnimImporter animImporter = new AnimImporter(this.reader);
/* 163 */     this.animation = animImporter.loadAnim(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupReader(InputStream stream) {
/* 175 */     InputStream filter = new BufferedInputStream(stream, 65536);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     this.reader = new StreamTokenizer(filter);
/*     */     
/* 183 */     this.reader.slashStarComments(false);
/* 184 */     this.reader.eolIsSignificant(true);
/* 185 */     this.reader.whitespaceChars(41, 41);
/* 186 */     this.reader.whitespaceChars(40, 40);
/* 187 */     this.reader.wordChars(125, 125);
/* 188 */     this.reader.quoteChar(34);
/*     */ 
/*     */     
/* 191 */     this.reader.parseNumbers();
/* 192 */     this.reader.slashSlashComments(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void assignAnimation(int repeatType) {
/* 201 */     JointController controller = new JointController(this.modelNode.getJoints());
/* 202 */     controller.setRepeatType(repeatType);
/* 203 */     controller.addAnimation(this.animation);
/* 204 */     controller.setActive(true);
/* 205 */     this.modelNode.addController((Controller)controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMMFilter(Texture.MinificationFilter mm) {
/* 214 */     if (mm == Texture.MinificationFilter.NearestNeighborLinearMipMap || mm == Texture.MinificationFilter.BilinearNearestMipMap || mm == Texture.MinificationFilter.BilinearNoMipMaps || mm == MM_Filter || mm == Texture.MinificationFilter.NearestNeighborNearestMipMap || mm == Texture.MinificationFilter.NearestNeighborNoMipMaps || mm == Texture.MinificationFilter.Trilinear)
/*     */     
/* 216 */     { MM_Filter = mm; }
/* 217 */     else { logger.info("Invalid MM_Texture filter. Default bi-linear filter used."); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFMFilter(Texture.MagnificationFilter fm) {
/* 226 */     if (fm == Texture.MagnificationFilter.NearestNeighbor || fm == Texture.MagnificationFilter.Bilinear)
/* 227 */     { FM_Filter = fm; }
/* 228 */     else { logger.info("Invalid FM_Texture filter. Default linear fileter used."); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnisotropic(int aniso) {
/* 237 */     if (aniso >= 0) { this; anisotropic = aniso; }
/* 238 */     else { logger.info("Invalid Anisotropic filter level. Default 16 used."); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrientedBounding(boolean orientedBounding) {
/* 247 */     this; MD5Importer.orientedBounding = orientedBounding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getExtensions() {
/* 256 */     this; return extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Texture.MinificationFilter getMMFilter() {
/* 265 */     this; return MM_Filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Texture.MagnificationFilter getFMFilter() {
/* 274 */     this; return FM_Filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAnisotropic() {
/* 283 */     this; return anisotropic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode getModelNode() {
/* 292 */     return this.modelNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JointAnimation getAnimation() {
/* 301 */     return this.animation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOriented() {
/* 310 */     this; return orientedBounding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 315 */     this.reader = null;
/* 316 */     this.modelNode = null;
/* 317 */     this.animation = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DirectLatin1Reader
/*     */     extends Reader
/*     */   {
/*     */     private final InputStream in;
/*     */ 
/*     */ 
/*     */     
/*     */     public DirectLatin1Reader(InputStream in) {
/* 331 */       this.in = in;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 336 */       return this.in.read();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(char[] cbuf, int off, int len) throws IOException {
/* 341 */       int readCount = 0;
/* 342 */       while (len-- > 0) {
/* 343 */         int readByte = this.in.read();
/* 344 */         if (readByte <= -1) {
/* 345 */           if (readCount == 0) {
/* 346 */             return readByte;
/*     */           }
/* 348 */           return readCount;
/*     */         } 
/*     */         
/* 351 */         cbuf[off++] = (char)(readByte & 0xFF);
/* 352 */         readCount++;
/*     */       } 
/* 354 */       return readCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 359 */       this.in.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\MD5Importer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */