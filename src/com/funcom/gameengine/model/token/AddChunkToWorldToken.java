/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ 
/*    */ public class AddChunkToWorldToken implements Token {
/*    */   private ChunkNode chunkNode;
/*    */   private ChunkWorldNode chunkWorldNode;
/*    */   
/*    */   public AddChunkToWorldToken(ChunkNode chunkNode, ChunkWorldNode chunkWorldNode) {
/* 11 */     this.chunkNode = chunkNode;
/* 12 */     this.chunkWorldNode = chunkWorldNode;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 16 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   public void process() {
/* 20 */     this.chunkWorldNode.addChunk(this.chunkNode);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 25 */     return getClass().getName() + "[wChildren:" + this.chunkWorldNode.getQuantity() + ",nChildren:" + this.chunkNode.getQuantity() + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\AddChunkToWorldToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */