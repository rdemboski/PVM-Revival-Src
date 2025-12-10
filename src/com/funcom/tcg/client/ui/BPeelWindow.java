/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class BPeelWindow
/*     */   extends BWindow
/*     */ {
/*  24 */   private static final Logger LOGGER = Logger.getLogger(BPeelWindow.class);
/*     */   private EventWiringObject eventWiringObject;
/*     */   private BananaPeel bananaPeel;
/*     */   
/*     */   public BPeelWindow(String windowName, BananaPeel bananaPeel) {
/*  29 */     super(windowName, bananaPeel.getStyleSheet(), (BLayoutManager)new AbsoluteLayout());
/*  30 */     this.bananaPeel = bananaPeel;
/*  31 */     BContainer mainContainer = bananaPeel.getTopComponents().iterator().next();
/*  32 */     setSize(mainContainer.getWidth(), mainContainer.getHeight());
/*  33 */     add((BComponent)mainContainer, new Rectangle(0, 0, getWidth(), getHeight()));
/*  34 */     center();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/*  39 */     super.dismiss();
/*  40 */     if (this.eventWiringObject != null)
/*  41 */       this.eventWiringObject.dispose(); 
/*     */   }
/*     */   
/*     */   public void wireEvents(Map<String, PeelWindowEvent[]> eventsWiringMap, Object eventObject) {
/*  45 */     if (this.eventWiringObject != null) {
/*  46 */       this.eventWiringObject.dispose();
/*     */     }
/*  48 */     this.eventWiringObject = new EventWiringObject(this, eventsWiringMap, eventObject);
/*  49 */     this.eventWiringObject.wireUp();
/*     */   }
/*     */   
/*     */   public BComponent findComponent(BContainer rootComponent, String componentName) {
/*  53 */     for (int i = 0; i < rootComponent.getComponentCount(); i++) {
/*  54 */       BComponent component = rootComponent.getComponent(i);
/*  55 */       if (componentName.equals(component.getName())) {
/*  56 */         return component;
/*     */       }
/*  58 */       if (component instanceof BContainer) {
/*  59 */         BComponent foundComponent = findComponent((BContainer)component, componentName);
/*  60 */         if (foundComponent != null)
/*  61 */           return foundComponent; 
/*     */       } 
/*     */     } 
/*  64 */     return null;
/*     */   }
/*     */   
/*     */   public BContainer findContainer(String containerName) {
/*  68 */     BComponent component = this.bananaPeel.getComponent(containerName);
/*  69 */     if (component instanceof BContainer) {
/*  70 */       return (BContainer)component;
/*     */     }
/*  72 */     return null;
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
/*     */   public void overridePeelerComponent(BComponent newComponent, BComponent placeholder) {
/*  84 */     String style = placeholder.getStyleClass();
/*  85 */     newComponent.setStyleClass(style);
/*  86 */     newComponent.setSize(placeholder.getWidth(), placeholder.getHeight());
/*  87 */     newComponent.setLocation(placeholder.getX(), placeholder.getY());
/*     */     
/*  89 */     if (placeholder instanceof BContainer && newComponent instanceof BContainer) {
/*  90 */       BContainer container = (BContainer)placeholder;
/*  91 */       for (int i = 0; i < container.getComponentCount(); i++) {
/*  92 */         BComponent component = container.getComponent(i);
/*  93 */         component.setParent(null);
/*  94 */         ((BContainer)newComponent).add(component);
/*     */       } 
/*     */     } 
/*  97 */     placeholder.getParent().add(placeholder.getParent().getComponentIndex(placeholder), newComponent, new Rectangle(placeholder.getX(), placeholder.getY(), placeholder.getWidth(), placeholder.getHeight()));
/*     */     
/*  99 */     placeholder.getParent().remove(placeholder);
/*     */   }
/*     */   
/*     */   private static class EventWiringObject
/*     */   {
/*     */     private Map<String, PeelWindowEvent[]> eventsWiringMap;
/*     */     private Object eventObject;
/*     */     private BPeelWindow peelWindow;
/*     */     
/*     */     private EventWiringObject(BPeelWindow peelWindow, Map<String, PeelWindowEvent[]> eventsWiringMap, Object eventObject) {
/* 109 */       if (eventsWiringMap == null)
/* 110 */         throw new IllegalArgumentException("eventsWiringMap = null"); 
/* 111 */       if (eventObject == null)
/* 112 */         throw new IllegalArgumentException("eventObject = null"); 
/* 113 */       this.eventsWiringMap = eventsWiringMap;
/* 114 */       this.eventObject = eventObject;
/* 115 */       this.peelWindow = peelWindow;
/*     */     }
/*     */     
/*     */     public void wireUp() {
/* 119 */       for (Map.Entry<String, PeelWindowEvent[]> entry : this.eventsWiringMap.entrySet()) {
/* 120 */         String componentName = entry.getKey();
/* 121 */         BComponent component = this.peelWindow.findComponent((BContainer)this.peelWindow, componentName);
/* 122 */         PeelWindowEvent[] eventList = entry.getValue();
/* 123 */         for (PeelWindowEvent event : eventList) {
/* 124 */           switch (event.getType()) {
/*     */             case ACTION:
/* 126 */               component.addListener((ComponentListener)new WiredActionListener(this.peelWindow, component, this.eventObject, event.getEventName()));
/*     */               break;
/*     */             
/*     */             case MOUSE_DOWN:
/* 130 */               component.addListener((ComponentListener)new WiredMouseDownListener(this.peelWindow, component, this.eventObject, event.getEventName()));
/*     */               break;
/*     */             
/*     */             case MOUSE_UP:
/* 134 */               component.addListener((ComponentListener)new WiredMouseUpListener(this.peelWindow, component, this.eventObject, event.getEventName()));
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void dispose() {
/* 142 */       for (Map.Entry<String, PeelWindowEvent[]> entry : this.eventsWiringMap.entrySet()) {
/* 143 */         String componentName = entry.getKey();
/* 144 */         BComponent component = this.peelWindow.findComponent((BContainer)this.peelWindow, componentName);
/* 145 */         component.removeAllListeners();
/*     */       } 
/*     */     }
/*     */     
/*     */     private static class WiredActionListener
/*     */       implements ActionListener
/*     */     {
/*     */       private String eventMethodName;
/*     */       private BComponent component;
/*     */       private Object eventObject;
/*     */       private BPeelWindow peelWindow;
/*     */       
/*     */       private WiredActionListener(BPeelWindow peelWindow, BComponent component, Object eventObject, String eventMethodName) {
/* 158 */         this.peelWindow = peelWindow;
/* 159 */         this.eventMethodName = eventMethodName;
/* 160 */         this.component = component;
/* 161 */         this.eventObject = eventObject;
/*     */       }
/*     */ 
/*     */       
/*     */       public void actionPerformed(ActionEvent event) {
/*     */         try {
/* 167 */           Method method = this.eventObject.getClass().getMethod(this.eventMethodName, new Class[] { BPeelWindow.class, BComponent.class });
/*     */           
/* 169 */           method.invoke(this.eventObject, new Object[] { this.peelWindow, this.component });
/* 170 */         } catch (NoSuchMethodException e) {
/* 171 */           throw new IllegalStateException(e);
/* 172 */         } catch (IllegalAccessException e) {
/* 173 */           throw new IllegalStateException(e);
/* 174 */         } catch (InvocationTargetException e) {
/* 175 */           throw new IllegalStateException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     private class WiredMouseDownListener
/*     */       extends MouseAdapter
/*     */     {
/*     */       private BPeelWindow peelWindow;
/*     */       private BComponent component;
/*     */       private Object eventObject;
/*     */       private String eventName;
/*     */       
/*     */       public WiredMouseDownListener(BPeelWindow peelWindow, BComponent component, Object eventObject, String eventName) {
/* 189 */         this.peelWindow = peelWindow;
/* 190 */         this.component = component;
/* 191 */         this.eventObject = eventObject;
/* 192 */         this.eventName = eventName;
/*     */       }
/*     */ 
/*     */       
/*     */       public void mousePressed(MouseEvent event) {
/*     */         try {
/* 198 */           Method method = this.eventObject.getClass().getMethod(this.eventName, new Class[] { BPeelWindow.class, BComponent.class });
/* 199 */           method.invoke(this.eventObject, new Object[] { this.peelWindow, this.component });
/* 200 */         } catch (NoSuchMethodException e) {
/* 201 */           throw new IllegalStateException(e);
/* 202 */         } catch (IllegalAccessException e) {
/* 203 */           throw new IllegalStateException(e);
/* 204 */         } catch (InvocationTargetException e) {
/* 205 */           throw new IllegalStateException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     private class WiredMouseUpListener
/*     */       extends MouseAdapter
/*     */     {
/*     */       private BPeelWindow peelWindow;
/*     */       private BComponent component;
/*     */       private Object eventObject;
/*     */       private String eventName;
/*     */       
/*     */       public WiredMouseUpListener(BPeelWindow peelWindow, BComponent component, Object eventObject, String eventName) {
/* 219 */         this.peelWindow = peelWindow;
/* 220 */         this.component = component;
/* 221 */         this.eventObject = eventObject;
/* 222 */         this.eventName = eventName;
/*     */       }
/*     */ 
/*     */       
/*     */       public void mouseReleased(MouseEvent event) {
/*     */         try {
/* 228 */           Method method = this.eventObject.getClass().getMethod(this.eventName, new Class[] { BPeelWindow.class, BComponent.class });
/* 229 */           method.invoke(this.eventObject, new Object[] { this.peelWindow, this.component });
/* 230 */         } catch (NoSuchMethodException e) {
/* 231 */           throw new IllegalStateException(e);
/* 232 */         } catch (IllegalAccessException e) {
/* 233 */           throw new IllegalStateException(e);
/* 234 */         } catch (InvocationTargetException e) {
/* 235 */           throw new IllegalStateException(e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BPeelWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */