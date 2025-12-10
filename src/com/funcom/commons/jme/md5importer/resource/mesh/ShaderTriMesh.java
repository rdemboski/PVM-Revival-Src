/*    */ package com.funcom.commons.jme.md5importer.resource.mesh;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.TriMesh;
/*    */ import com.jme.scene.state.GLSLShaderObjectsState;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.util.shader.ShaderVariable;
/*    */ import java.util.Collection;
/*    */ import org.lwjgl.opengl.ARBVertexProgram;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderTriMesh
/*    */   extends TriMesh
/*    */ {
/*    */   private static final boolean CREATION_STACK_TRACE = false;
/* 31 */   private final String stack = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean predraw(Renderer r) {
/* 37 */     if (getVertexBuffer() != null) {
/* 38 */       return super.predraw(r);
/*    */     }
/* 40 */     System.err.println("*** ERROR, drawing mesh without vertices ***");
/*    */ 
/*    */ 
/*    */     
/* 44 */     System.err.println("Turn on CREATION_STACK_TRACE and debug this!");
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void postdraw(Renderer r) {
/* 51 */     super.postdraw(r);
/* 52 */     GLSLShaderObjectsState state = (GLSLShaderObjectsState)getRenderState(RenderState.StateType.GLSLShaderObjects);
/*    */     
/* 54 */     if (state != null) {
/* 55 */       Collection<ShaderVariable> shaderAttributes = state.getShaderAttributes();
/* 56 */       for (ShaderVariable shaderAttribute : shaderAttributes) {
/* 57 */         if (shaderAttribute.variableID != -1)
/* 58 */           ARBVertexProgram.glDisableVertexAttribArrayARB(shaderAttribute.variableID); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\ShaderTriMesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */