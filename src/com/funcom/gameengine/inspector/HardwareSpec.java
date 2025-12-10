/*    */ package com.funcom.gameengine.inspector;
/*    */ 
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class HardwareSpec
/*    */   implements Comparable<HardwareSpec>
/*    */ {
/* 11 */   private static final Logger LOGGER = Logger.getLogger(HardwareSpec.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   private Map<Hardware, Object> hardwareSpecMap = new EnumMap<Hardware, Object>(Hardware.class);
/*    */ 
/*    */   
/*    */   public HardwareSpec(int cpu, int cpuCores, int ramAmount, int shaderModel) {
/* 20 */     this();
/* 21 */     putSpec(Hardware.CPU, Integer.valueOf(cpu));
/* 22 */     putSpec(Hardware.CPU_CORES, Integer.valueOf(cpuCores));
/* 23 */     putSpec(Hardware.RAM, Integer.valueOf(ramAmount));
/* 24 */     putSpec(Hardware.SHADER_MODEL, Integer.valueOf(shaderModel));
/*    */   }
/*    */   
/*    */   public void putSpec(Hardware key, Object value) {
/* 28 */     this.hardwareSpecMap.put(key, value);
/*    */   }
/*    */   
/*    */   public Object getHardwareSpec(Hardware hardwareSpec) {
/* 32 */     return this.hardwareSpecMap.get(hardwareSpec);
/*    */   }
/*    */   
/*    */   public int getCpuSpeed() {
/* 36 */     return ((Integer)this.hardwareSpecMap.get(Hardware.CPU)).intValue();
/*    */   }
/*    */   
/*    */   public int getCpuCores() {
/* 40 */     return ((Integer)this.hardwareSpecMap.get(Hardware.CPU_CORES)).intValue();
/*    */   }
/*    */   
/*    */   public int getRamAmount() {
/* 44 */     return ((Integer)this.hardwareSpecMap.get(Hardware.RAM)).intValue();
/*    */   }
/*    */   
/*    */   public int getShaderModel() {
/* 48 */     return ((Integer)this.hardwareSpecMap.get(Hardware.SHADER_MODEL)).intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(HardwareSpec o) {
/* 54 */     if ((getCpuCores() == o.getCpuCores() || getCpuSpeed() == o.getCpuSpeed()) && getRamAmount() == o.getRamAmount() && getShaderModel() == o.getShaderModel())
/*    */     {
/*    */       
/* 57 */       return 0;
/*    */     }
/*    */     
/* 60 */     if ((getCpuCores() < o.getCpuCores() && getCpuSpeed() < o.getCpuSpeed()) || getRamAmount() < o.getRamAmount() || getShaderModel() < o.getShaderModel())
/*    */     {
/*    */       
/* 63 */       return -1;
/*    */     }
/*    */     
/* 66 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 71 */     if (this == o) return true; 
/* 72 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 74 */     HardwareSpec that = (HardwareSpec)o;
/*    */     
/* 76 */     if (!this.hardwareSpecMap.equals(that.hardwareSpecMap)) return false;
/*    */     
/* 78 */     return true;
/*    */   }
/*    */   public HardwareSpec() {}
/*    */   
/*    */   public int hashCode() {
/* 83 */     return this.hardwareSpecMap.hashCode();
/*    */   }
/*    */   
/*    */   public enum Hardware {
/* 87 */     CPU,
/* 88 */     CPU_CORES,
/* 89 */     RAM,
/* 90 */     SHADER_MODEL;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\inspector\HardwareSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */