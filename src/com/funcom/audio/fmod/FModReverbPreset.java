/*    */ package com.funcom.audio.fmod;
/*    */ 
/*    */ import com.funcom.audio.ReverbPreset;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.jouvieje.FmodEx.Structures.FMOD_REVERB_PROPERTIES;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FModReverbPreset
/*    */ {
/* 13 */   private static final Logger LOG = Logger.getLogger(FModReverbPreset.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   private static final Map<ReverbPreset, FMOD_REVERB_PROPERTIES> reverbProps = new HashMap<ReverbPreset, FMOD_REVERB_PROPERTIES>(); static {
/* 19 */     reverbProps.put(ReverbPreset.OFF, FMOD_REVERB_PROPERTIES.create(1));
/* 20 */     reverbProps.put(ReverbPreset.GENERIC, FMOD_REVERB_PROPERTIES.create(2));
/* 21 */     reverbProps.put(ReverbPreset.PADDEDCELL, FMOD_REVERB_PROPERTIES.create(3));
/* 22 */     reverbProps.put(ReverbPreset.ROOM, FMOD_REVERB_PROPERTIES.create(4));
/* 23 */     reverbProps.put(ReverbPreset.BATHROOM, FMOD_REVERB_PROPERTIES.create(5));
/* 24 */     reverbProps.put(ReverbPreset.LIVINGROOM, FMOD_REVERB_PROPERTIES.create(6));
/* 25 */     reverbProps.put(ReverbPreset.STONEROOM, FMOD_REVERB_PROPERTIES.create(7));
/* 26 */     reverbProps.put(ReverbPreset.AUDITORIUM, FMOD_REVERB_PROPERTIES.create(8));
/* 27 */     reverbProps.put(ReverbPreset.CONCERTHALL, FMOD_REVERB_PROPERTIES.create(9));
/* 28 */     reverbProps.put(ReverbPreset.CAVE, FMOD_REVERB_PROPERTIES.create(10));
/* 29 */     reverbProps.put(ReverbPreset.ARENA, FMOD_REVERB_PROPERTIES.create(11));
/* 30 */     reverbProps.put(ReverbPreset.HANGAR, FMOD_REVERB_PROPERTIES.create(12));
/* 31 */     reverbProps.put(ReverbPreset.CARPETTEDHALLWAY, FMOD_REVERB_PROPERTIES.create(13));
/* 32 */     reverbProps.put(ReverbPreset.HALLWAY, FMOD_REVERB_PROPERTIES.create(14));
/* 33 */     reverbProps.put(ReverbPreset.STONECORRIDOR, FMOD_REVERB_PROPERTIES.create(15));
/* 34 */     reverbProps.put(ReverbPreset.ALLEY, FMOD_REVERB_PROPERTIES.create(16));
/* 35 */     reverbProps.put(ReverbPreset.FOREST, FMOD_REVERB_PROPERTIES.create(17));
/* 36 */     reverbProps.put(ReverbPreset.CITY, FMOD_REVERB_PROPERTIES.create(18));
/* 37 */     reverbProps.put(ReverbPreset.MOUNTAINS, FMOD_REVERB_PROPERTIES.create(19));
/* 38 */     reverbProps.put(ReverbPreset.QUARRY, FMOD_REVERB_PROPERTIES.create(20));
/* 39 */     reverbProps.put(ReverbPreset.PLAIN, FMOD_REVERB_PROPERTIES.create(21));
/* 40 */     reverbProps.put(ReverbPreset.PARKINGLOT, FMOD_REVERB_PROPERTIES.create(22));
/* 41 */     reverbProps.put(ReverbPreset.SEWERPIPE, FMOD_REVERB_PROPERTIES.create(23));
/* 42 */     reverbProps.put(ReverbPreset.UNDERWATER, FMOD_REVERB_PROPERTIES.create(24));
/* 43 */     reverbProps.put(ReverbPreset.DRUGGED, FMOD_REVERB_PROPERTIES.create(25));
/* 44 */     reverbProps.put(ReverbPreset.DIZZY, FMOD_REVERB_PROPERTIES.create(26));
/* 45 */     reverbProps.put(ReverbPreset.PSYCHOTIC, FMOD_REVERB_PROPERTIES.create(27));
/*    */   }
/*    */   
/*    */   public static FMOD_REVERB_PROPERTIES get(ReverbPreset preset) {
/* 49 */     FMOD_REVERB_PROPERTIES ret = reverbProps.get(preset);
/*    */     
/* 51 */     if (ret == null) {
/* 52 */       LOG.error("unsupported rever preset,  " + preset);
/* 53 */       ret = reverbProps.get(ReverbPreset.OFF);
/*    */     } 
/*    */     
/* 56 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\FModReverbPreset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */