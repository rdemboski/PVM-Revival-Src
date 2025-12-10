/*     */ package com.funcom.gameengine.resourcemanager;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.debug.TimeStamper;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagedResource<T>
/*     */   implements Comparable<ManagedResource<?>>
/*     */ {
/*     */   private Map<Object, Object> parameters;
/*     */   private T resource;
/*     */   private WeakReference<T> unloadStorage;
/*     */   private Object unloadInfo;
/*     */   private String name;
/*     */   private long lastAccessed;
/*     */   private boolean loading;
/*     */   private boolean dirty;
/*     */   private Class<T> resourceType;
/*     */   private CacheType cacheType;
/*     */   private ResourceListener resourceListener;
/*     */   private Set<ResourceListener> resourceListeners;
/*     */   private ResourceLoader loader;
/*     */   private long LoadingStartTime;
/*     */   
/*     */   public ManagedResource(String name, Class<T> resourceType, CacheType cacheType) {
/*  36 */     this.name = name;
/*  37 */     this.resourceType = resourceType;
/*  38 */     this.cacheType = cacheType;
/*  39 */     this.parameters = new HashMap<Object, Object>();
/*  40 */     this.dirty = false;
/*  41 */     this.LoadingStartTime = -1L;
/*     */   }
/*     */   
/*     */   public ManagedResource(String name, Class<T> resourceType, CacheType cacheType, ResourceListener resourceListener) {
/*  45 */     this(name, resourceType, cacheType);
/*  46 */     this.resourceListener = resourceListener;
/*     */   }
/*     */   
/*     */   public CacheType getCacheType() {
/*  50 */     return this.cacheType;
/*     */   }
/*     */   public String getName() {
/*  53 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setDirty() {
/*  57 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void updateLastAccessed() {
/*  61 */     this.lastAccessed = GlobalTime.getInstance().getCurrentTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAge() {
/*  70 */     return GlobalTime.getInstance().getCurrentTime() - this.lastAccessed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastAccessed() {
/*  79 */     return this.lastAccessed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ManagedResource<?> o) {
/*  84 */     return (int)Math.signum((float)(o.getLastAccessed() - getLastAccessed()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoading() {
/*  93 */     return this.loading;
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/*  97 */     return this.dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoading(boolean loading) {
/* 106 */     this.loading = loading;
/*     */     
/* 108 */     if (this.loading) {
/*     */       
/* 110 */       this.LoadingStartTime = GlobalTime.getInstance().getRealTime();
/*     */     }
/* 112 */     else if (this.LoadingStartTime >= 0L) {
/*     */       
/* 114 */       long EndTime = GlobalTime.getInstance().getRealTime();
/* 115 */       long ElapsedTime = EndTime - this.LoadingStartTime;
/*     */       
/* 117 */       TimeStamper.INSTANCE.event("FPS", "Man_" + ElapsedTime + "_" + getName());
/* 118 */       StatCollector.addAssetLog(getName(), this.LoadingStartTime, EndTime, Double.valueOf(ElapsedTime * 0.001D));
/* 119 */       this.LoadingStartTime = -1L;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (!loading && this.resourceListeners != null) {
/* 124 */       for (ResourceListener<T> listener : this.resourceListeners) {
/* 125 */         listener.resourceLoaded(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setResource(T resource) {
/* 131 */     this.resource = resource;
/* 132 */     updateLastAccessed();
/*     */   }
/*     */   
/*     */   public T getResource() {
/* 136 */     updateLastAccessed();
/* 137 */     return this.resource;
/*     */   }
/*     */   
/*     */   public void unloadPrepare() {
/* 141 */     if (this.resource != null) {
/* 142 */       this.unloadInfo = this.loader.getUnloadInfo(this);
/* 143 */       this.unloadStorage = new WeakReference<T>(this.resource);
/*     */       
/* 145 */       this.resource = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unload() {
/* 156 */     boolean doUnload = beforePreparedUnload();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (!doUnload) {
/* 162 */       doUnload = beforeDirtyUnload();
/*     */     }
/*     */     
/* 165 */     if (doUnload) {
/* 166 */       doUnload(this.unloadInfo);
/*     */     }
/*     */ 
/*     */     
/* 170 */     this.unloadInfo = null;
/* 171 */     this.unloadStorage = null;
/*     */ 
/*     */     
/* 174 */     boolean wasUnloaded = (this.resource == null);
/* 175 */     return wasUnloaded;
/*     */   }
/*     */   
/*     */   private boolean beforePreparedUnload() {
/* 179 */     if (this.unloadStorage != null) {
/* 180 */       this.resource = this.unloadStorage.get();
/* 181 */       boolean wasGCed = (this.resource == null);
/* 182 */       if (wasGCed) {
/* 183 */         return true;
/*     */       }
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   private boolean beforeDirtyUnload() {
/* 190 */     if (this.dirty && 
/* 191 */       this.unloadInfo == null) {
/* 192 */       this.unloadInfo = this.loader.getUnloadInfo(this);
/*     */     }
/*     */     
/* 195 */     return this.dirty;
/*     */   }
/*     */   
/*     */   private void doUnload(Object unloadInfo) {
/* 199 */     this.loader.unloadData(this, unloadInfo);
/* 200 */     this.parameters.clear();
/* 201 */     this.dirty = false;
/*     */   }
/*     */   
/*     */   public boolean isLoaded() {
/* 205 */     return (this.resource != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 210 */     if (this == obj) return true; 
/* 211 */     if (obj == null || getClass() != obj.getClass()) return false;
/*     */     
/* 213 */     ManagedResource<?> that = (ManagedResource)obj;
/*     */     
/* 215 */     if (!this.name.equals(that.name)) return false;
/*     */     
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 227 */     return "ManagedResource{name='" + this.name + '\'' + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getResourceType() {
/* 233 */     return this.resourceType;
/*     */   }
/*     */   
/*     */   public ResourceListener getResourceListener() {
/* 237 */     return this.resourceListener;
/*     */   }
/*     */   
/*     */   public void setParameter(Object paramKey, Object value) {
/* 241 */     updateLastAccessed();
/* 242 */     this.parameters.put(paramKey, value);
/*     */   }
/*     */   
/*     */   public Object getParameter(Object paramKey, Object defaultValue) {
/* 246 */     updateLastAccessed();
/* 247 */     Object ret = this.parameters.get(paramKey);
/*     */     
/* 249 */     if (ret != null) {
/* 250 */       return ret;
/*     */     }
/*     */     
/* 253 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public void setLoader(ResourceLoader loader) {
/* 257 */     this.loader = loader;
/*     */   }
/*     */   
/*     */   public void load() throws LoadException {
/* 261 */     this.loader.loadData(this);
/*     */   }
/*     */   
/*     */   public Map<Object, Object> getParameters() {
/* 265 */     return this.parameters;
/*     */   }
/*     */   
/*     */   public void addListener(ResourceListener listener) {
/* 269 */     if (this.resourceListeners == null) {
/* 270 */       this.resourceListeners = new HashSet<ResourceListener>();
/*     */     }
/* 272 */     this.resourceListeners.add(listener);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ManagedResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */