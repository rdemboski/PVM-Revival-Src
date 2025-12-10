/*     */ package com.funcom.commons.configuration;
/*     */ 
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.beans.PropertyEditor;
/*     */ import java.beans.PropertyEditorManager;
/*     */ import java.beans.PropertyEditorSupport;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanConfigurator
/*     */ {
/*  41 */   private static final Logger LOG = Logger.getLogger(BeanConfigurator.class.getName());
/*     */   private static final int READ_BUFFER_SIZE = 1024;
/*     */   private Properties properties;
/*     */   
/*     */   static {
/*  46 */     PropertyEditorManager.registerEditor(Date.class, DateEditor.class);
/*  47 */     PropertyEditorManager.registerEditor(Integer.class, IntEditor.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanConfigurator() {}
/*     */   
/*     */   public BeanConfigurator(Properties properties) {
/*  54 */     setProperties(properties);
/*     */   }
/*     */   
/*     */   public void setPropertiesUrl(String url) throws IOException {
/*  58 */     setPropertiesUrl(BeanConfigurator.class.getClassLoader().getResource(url));
/*     */   }
/*     */   
/*     */   public void setPropertiesUrl(URL url) throws IOException {
/*  62 */     if (null == url)
/*  63 */       throw new IllegalArgumentException("no url"); 
/*  64 */     setProperties(url.openStream());
/*     */   }
/*     */   
/*     */   public void setProperties(InputStream inputStream) throws IOException {
/*  68 */     Properties properties = new Properties();
/*  69 */     properties.load(inputStream);
/*  70 */     setProperties(properties);
/*     */   }
/*     */   
/*     */   public void setProperties(Reader reader) throws IOException {
/*  74 */     char[] buffer = new char[1024];
/*  75 */     int read = reader.read(buffer);
/*  76 */     StringBuilder builder = new StringBuilder(512);
/*  77 */     while (read != -1) {
/*  78 */       builder.append(buffer, 0, read);
/*  79 */       read = reader.read(buffer);
/*     */     } 
/*  81 */     setProperties(new ByteArrayInputStream(builder.toString().getBytes()));
/*     */   }
/*     */   
/*     */   public final void setProperties(Properties properties) {
/*  85 */     this.properties = properties;
/*     */   }
/*     */   
/*     */   public void configure(Object bean) throws RuntimeException {
/*  89 */     if (bean == null)
/*  90 */       throw new RuntimeException("No bean object set."); 
/*  91 */     if (this.properties == null)
/*  92 */       throw new RuntimeException("No properties set."); 
/*  93 */     for (Object o : this.properties.keySet()) {
/*  94 */       String propertyName = (String)o;
/*  95 */       String value = this.properties.getProperty(propertyName);
/*  96 */       setProperty(bean, propertyName, value);
/*     */     } 
/*  98 */     LOG.debug("Configured.");
/*     */   }
/*     */   
/*     */   public static void setProperty(Object bean, String propertyName, String value) {
/* 102 */     if (propertyName.contains(".")) {
/* 103 */       int i = propertyName.indexOf('.');
/* 104 */       String beanName = propertyName.substring(0, i);
/* 105 */       Object b = getProperty(bean, beanName);
/* 106 */       setProperty(b, propertyName.substring(i + 1), value);
/*     */       return;
/*     */     } 
/* 109 */     PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
/*     */     try {
/* 111 */       PropertyEditor pe = PropertyEditorManager.findEditor(pd.getPropertyType());
/* 112 */       if (pe == null)
/* 113 */         throw new RuntimeException("Don't know how to convert property: " + pd.getPropertyType() + " " + propertyName); 
/* 114 */       if (value != null) {
/* 115 */         pe.setAsText(value);
/*     */       } else {
/* 117 */         pe.setValue(null);
/* 118 */       }  pd.getWriteMethod().invoke(bean, new Object[] { pe.getValue() });
/* 119 */     } catch (IllegalAccessException e) {
/* 120 */       throw new RuntimeException(e);
/* 121 */     } catch (InvocationTargetException e) {
/* 122 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getProperty(Object bean, String propertyName) {
/* 127 */     PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
/* 128 */     Method readMethod = pd.getReadMethod();
/*     */     try {
/* 130 */       Object o = readMethod.invoke(bean, new Object[0]);
/* 131 */       if (null == o) {
/* 132 */         o = readMethod.getReturnType().newInstance();
/* 133 */         pd.getWriteMethod().invoke(bean, new Object[] { o });
/* 134 */         o = readMethod.invoke(bean, new Object[0]);
/*     */       } 
/* 136 */       return o;
/* 137 */     } catch (IllegalAccessException e) {
/* 138 */       throw new RuntimeException(e);
/* 139 */     } catch (InvocationTargetException e) {
/* 140 */       throw new RuntimeException(e);
/* 141 */     } catch (InstantiationException e) {
/* 142 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static PropertyDescriptor getPropertyDescriptor(Class<?> aClass, String propertyName) {
/*     */     try {
/* 148 */       BeanInfo bi = Introspector.getBeanInfo(aClass);
/* 149 */       for (PropertyDescriptor propertyDescriptor : bi.getPropertyDescriptors()) {
/* 150 */         if (propertyDescriptor.getName().equals(propertyName))
/* 151 */           return propertyDescriptor; 
/* 152 */       }  throw new IllegalStateException("Class " + aClass.getName() + " has no property " + propertyName);
/* 153 */     } catch (IntrospectionException e) {
/* 154 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class DateEditor extends PropertyEditorSupport {
/* 159 */     private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
/*     */ 
/*     */     
/*     */     public void setAsText(String text) throws IllegalArgumentException {
/*     */       try {
/* 164 */         setValue(FORMAT.parse(text));
/* 165 */       } catch (ParseException e) {
/* 166 */         throw new IllegalArgumentException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAsText() {
/* 172 */       Object v = getValue();
/* 173 */       return (null != v) ? FORMAT.format(getValue()) : null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IntEditor
/*     */     extends PropertyEditorSupport {
/*     */     public void setAsText(String text) throws IllegalArgumentException {
/* 180 */       setValue(Integer.valueOf(text));
/*     */     }
/*     */ 
/*     */     
/*     */     public String getJavaInitializationString() {
/* 185 */       return "" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\BeanConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */