/*     */ package com.funcom.gameengine.jme.text;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.MutableAttributeSet;
/*     */ import javax.swing.text.Position;
/*     */ import javax.swing.text.SimpleAttributeSet;
/*     */ import javax.swing.text.StyledDocument;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.ViewFactory;
/*     */ import javax.swing.text.html.HTML;
/*     */ import javax.swing.text.html.HTMLDocument;
/*     */ import javax.swing.text.html.StyleSheet;
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
/*     */ public class ImageViewCustom
/*     */   extends View
/*     */   implements ImageObserver, MouseListener, MouseMotionListener
/*     */ {
/*     */   public static final String TOP = "top";
/*     */   public static final String TEXTTOP = "texttop";
/*     */   public static final String MIDDLE = "middle";
/*     */   public static final String ABSMIDDLE = "absmiddle";
/*     */   public static final String CENTER = "center";
/*     */   public static final String BOTTOM = "bottom";
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   public ImageViewCustom(Element elem, ResourceManager resourceManager) {
/*  66 */     super(elem);
/*  67 */     this.resourceManager = resourceManager;
/*  68 */     initialize(elem);
/*  69 */     StyleSheet sheet = getStyleSheet();
/*  70 */     this.attr = sheet.getViewAttributes(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize(Element elem) {
/*  75 */     synchronized (this) {
/*  76 */       this.loading = true;
/*  77 */       this.fWidth = this.fHeight = 0;
/*     */     } 
/*  79 */     int width = 0;
/*  80 */     int height = 0;
/*  81 */     boolean customWidth = false;
/*  82 */     boolean customHeight = false;
/*     */     try {
/*  84 */       this.fElement = elem;
/*     */ 
/*     */       
/*  87 */       AttributeSet attr = elem.getAttributes();
/*  88 */       if (!isURL()) {
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
/* 100 */         String src = (String)this.fElement.getAttributes().getAttribute(HTML.Attribute.SRC);
/*     */ 
/*     */ 
/*     */         
/* 104 */         this.fImage = (Image)this.resourceManager.getResource(Image.class, src);
/*     */         try {
/* 106 */           waitForImage();
/*     */         }
/* 108 */         catch (InterruptedException e) {
/* 109 */           this.fImage = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       height = getIntAttr(HTML.Attribute.HEIGHT, -1);
/* 117 */       customHeight = (height > 0);
/* 118 */       if (!customHeight && this.fImage != null)
/* 119 */         height = this.fImage.getHeight(this); 
/* 120 */       if (height <= 0) {
/* 121 */         height = 32;
/*     */       }
/* 123 */       width = getIntAttr(HTML.Attribute.WIDTH, -1);
/* 124 */       customWidth = (width > 0);
/* 125 */       if (!customWidth && this.fImage != null)
/* 126 */         width = this.fImage.getWidth(this); 
/* 127 */       if (width <= 0) {
/* 128 */         width = 32;
/*     */       }
/*     */       
/* 131 */       if (this.fImage != null) {
/* 132 */         if (customWidth && customHeight) {
/* 133 */           Toolkit.getDefaultToolkit().prepareImage(this.fImage, height, width, this);
/*     */         } else {
/*     */           
/* 136 */           Toolkit.getDefaultToolkit().prepareImage(this.fImage, -1, -1, this);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       synchronized (this) {
/* 156 */         this.loading = false;
/* 157 */         if (customWidth || this.fWidth == 0) {
/* 158 */           this.fWidth = width;
/*     */         }
/* 160 */         if (customHeight || this.fHeight == 0) {
/* 161 */           this.fHeight = height;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isURL() {
/* 169 */     String src = (String)this.fElement.getAttributes().getAttribute(HTML.Attribute.SRC);
/*     */     
/* 171 */     return (src.toLowerCase().startsWith("file") || src.toLowerCase().startsWith("http"));
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
/*     */   private void waitForImage() throws InterruptedException {
/*     */     while (true) {
/* 184 */       int w = (this.fImage == null) ? 0 : this.fImage.getWidth(this);
/* 185 */       int h = (this.fImage == null) ? 0 : this.fImage.getHeight(this);
/*     */       
/* 187 */       int flags = Toolkit.getDefaultToolkit().checkImage(this.fImage, w, h, this);
/*     */       
/* 189 */       if ((flags & 0x40) != 0 || (flags & 0x80) != 0)
/* 190 */         throw new InterruptedException(); 
/* 191 */       if ((flags & 0x30) != 0)
/*     */         return; 
/* 193 */       Thread.sleep(10L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getAttributes() {
/* 205 */     return this.attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLink() {
/* 212 */     AttributeSet anchorAttr = (AttributeSet)this.fElement.getAttributes().getAttribute(HTML.Tag.A);
/*     */     
/* 214 */     if (anchorAttr != null) {
/* 215 */       return anchorAttr.isDefined(HTML.Attribute.HREF);
/*     */     }
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   int getBorder() {
/* 222 */     return getIntAttr(HTML.Attribute.BORDER, isLink() ? 2 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   int getSpace(int axis) {
/* 227 */     return getIntAttr((axis == 0) ? HTML.Attribute.HSPACE : HTML.Attribute.VSPACE, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Color getBorderColor() {
/* 233 */     StyledDocument doc = (StyledDocument)getDocument();
/* 234 */     return doc.getForeground(getAttributes());
/*     */   }
/*     */ 
/*     */   
/*     */   float getVerticalAlignment() {
/* 239 */     String align = (String)this.fElement.getAttributes().getAttribute(HTML.Attribute.ALIGN);
/* 240 */     if (align != null) {
/* 241 */       align = align.toLowerCase();
/* 242 */       if (align.equals("top") || align.equals("texttop"))
/* 243 */         return 0.0F; 
/* 244 */       ImageViewCustom imageViewCustom = this; if (align.equals("center") || align.equals("middle") || align.equals("absmiddle"))
/*     */       {
/* 246 */         return 0.5F; } 
/*     */     } 
/* 248 */     return 1.0F;
/*     */   }
/*     */   
/*     */   boolean hasPixels(ImageObserver obs) {
/* 252 */     return (this.fImage != null && this.fImage.getHeight(obs) > 0 && this.fImage.getWidth(obs) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL getSourceURL() {
/* 262 */     String src = (String)this.fElement.getAttributes().getAttribute(HTML.Attribute.SRC);
/* 263 */     if (src == null) return null;
/*     */     
/* 265 */     URL reference = ((HTMLDocument)getDocument()).getBase();
/*     */     try {
/* 267 */       URL u = new URL(reference, src);
/* 268 */       return u;
/* 269 */     } catch (MalformedURLException e) {
/* 270 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIntAttr(HTML.Attribute name, int deflt) {
/* 276 */     AttributeSet attr = this.fElement.getAttributes();
/* 277 */     if (attr.isDefined(name)) {
/*     */       int i;
/* 279 */       String val = (String)attr.getAttribute(name);
/* 280 */       if (val == null) {
/* 281 */         i = deflt;
/*     */       } else {
/*     */         try {
/* 284 */           i = Math.max(0, Integer.parseInt(val));
/* 285 */         } catch (NumberFormatException x) {
/* 286 */           i = deflt;
/*     */         } 
/* 288 */       }  return i;
/*     */     } 
/* 290 */     return deflt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(View parent) {
/* 299 */     super.setParent(parent);
/* 300 */     this.fContainer = (parent != null) ? getContainer() : null;
/* 301 */     if (parent == null && this.fComponent != null) {
/* 302 */       this.fComponent.getParent().remove(this.fComponent);
/* 303 */       this.fComponent = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
/* 310 */     super.changedUpdate(e, a, f);
/* 311 */     float align = getVerticalAlignment();
/*     */     
/* 313 */     int height = this.fHeight;
/* 314 */     int width = this.fWidth;
/*     */     
/* 316 */     initialize(getElement());
/*     */     
/* 318 */     boolean hChanged = (this.fHeight != height);
/* 319 */     boolean wChanged = (this.fWidth != width);
/* 320 */     if (hChanged || wChanged || getVerticalAlignment() != align)
/*     */     {
/* 322 */       getParent().preferenceChanged(this, hChanged, wChanged);
/*     */     }
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
/*     */   public void paint(Graphics g, Shape a) {
/* 337 */     Color oldColor = g.getColor();
/* 338 */     this.fBounds = a.getBounds();
/* 339 */     int border = getBorder();
/* 340 */     int x = this.fBounds.x + border + getSpace(0);
/* 341 */     int y = this.fBounds.y + border + getSpace(1);
/* 342 */     int width = this.fWidth;
/* 343 */     int height = this.fHeight;
/* 344 */     int sel = getSelectionState();
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
/* 358 */     if (!hasPixels(this)) {
/* 359 */       g.setColor(Color.lightGray);
/* 360 */       g.drawRect(x, y, width - 1, height - 1);
/* 361 */       g.setColor(oldColor);
/* 362 */       loadIcons();
/* 363 */       Icon icon = (this.fImage == null) ? sMissingImageIcon : sPendingImageIcon;
/* 364 */       if (icon != null) {
/* 365 */         icon.paintIcon(getContainer(), g, x, y);
/*     */       }
/*     */     } 
/*     */     
/* 369 */     if (this.fImage != null) {
/* 370 */       g.drawImage(this.fImage, x, y, width, height, this);
/*     */     }
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
/* 387 */     Color bc = getBorderColor();
/* 388 */     if (sel == 2) {
/*     */       
/* 390 */       int delta = 2 - border;
/* 391 */       if (delta > 0) {
/* 392 */         x += delta;
/* 393 */         y += delta;
/* 394 */         width -= delta << 1;
/* 395 */         height -= delta << 1;
/* 396 */         border = 2;
/*     */       } 
/* 398 */       bc = null;
/* 399 */       g.setColor(Color.black);
/*     */       
/* 401 */       g.fillRect(x + width - 5, y + height - 5, 5, 5);
/*     */     } 
/*     */ 
/*     */     
/* 405 */     if (border > 0) {
/* 406 */       if (bc != null) g.setColor(bc);
/*     */       
/* 408 */       for (int i = 1; i <= border; i++)
/* 409 */         g.drawRect(x - i, y - i, width - 1 + i + i, height - 1 + i + i); 
/* 410 */       g.setColor(oldColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void repaint(long delay) {
/* 419 */     if (this.fContainer != null && this.fBounds != null) {
/* 420 */       this.fContainer.repaint(delay, this.fBounds.x, this.fBounds.y, this.fBounds.width, this.fBounds.height);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSelectionState() {
/* 432 */     int p0 = this.fElement.getStartOffset();
/* 433 */     int p1 = this.fElement.getEndOffset();
/* 434 */     if (this.fContainer instanceof JTextComponent) {
/* 435 */       JTextComponent textComp = (JTextComponent)this.fContainer;
/* 436 */       int start = textComp.getSelectionStart();
/* 437 */       int end = textComp.getSelectionEnd();
/* 438 */       if (start <= p0 && end >= p1) {
/* 439 */         if (start == p0 && end == p1 && isEditable()) {
/* 440 */           return 2;
/*     */         }
/* 442 */         return 1;
/*     */       } 
/*     */     } 
/* 445 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean isEditable() {
/* 449 */     return (this.fContainer instanceof JEditorPane && ((JEditorPane)this.fContainer).isEditable());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getHighlightColor() {
/* 455 */     JTextComponent textComp = (JTextComponent)this.fContainer;
/* 456 */     return textComp.getSelectionColor();
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
/*     */   public boolean imageUpdate(Image img, int flags, int x, int y, int width, int height) {
/* 469 */     if (this.fImage == null || this.fImage != img) {
/* 470 */       return false;
/*     */     }
/*     */     
/* 473 */     if ((flags & 0xC0) != 0) {
/* 474 */       this.fImage = null;
/* 475 */       repaint(0L);
/* 476 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 480 */     short changed = 0;
/* 481 */     if ((flags & 0x2) != 0 && 
/* 482 */       !getElement().getAttributes().isDefined(HTML.Attribute.HEIGHT)) {
/* 483 */       changed = (short)(changed | 0x1);
/*     */     }
/* 485 */     if ((flags & 0x1) != 0 && 
/* 486 */       !getElement().getAttributes().isDefined(HTML.Attribute.WIDTH)) {
/* 487 */       changed = (short)(changed | 0x2);
/*     */     }
/* 489 */     synchronized (this) {
/* 490 */       if ((changed & 0x1) == 1) {
/* 491 */         this.fWidth = width;
/*     */       }
/* 493 */       if ((changed & 0x2) == 2) {
/* 494 */         this.fHeight = height;
/*     */       }
/* 496 */       if (this.loading)
/*     */       {
/*     */         
/* 499 */         return true;
/*     */       }
/*     */     } 
/* 502 */     if (changed != 0) {
/*     */ 
/*     */ 
/*     */       
/* 506 */       Document doc = getDocument();
/*     */       try {
/* 508 */         if (doc instanceof AbstractDocument) {
/* 509 */           ((AbstractDocument)doc).readLock();
/*     */         }
/* 511 */         preferenceChanged(this, true, true);
/*     */       } finally {
/* 513 */         if (doc instanceof AbstractDocument) {
/* 514 */           ((AbstractDocument)doc).readUnlock();
/*     */         }
/*     */       } 
/*     */       
/* 518 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 522 */     if ((flags & 0x30) != 0) {
/* 523 */       repaint(0L);
/* 524 */     } else if ((flags & 0x8) != 0 && 
/* 525 */       sIsInc) {
/* 526 */       repaint(sIncRate);
/*     */     } 
/* 528 */     return ((flags & 0x20) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean sIsInc = true;
/*     */ 
/*     */ 
/*     */   
/* 538 */   private static int sIncRate = 100;
/*     */   
/*     */   private AttributeSet attr;
/*     */   
/*     */   private Element fElement;
/*     */   
/*     */   private Image fImage;
/*     */   private int fHeight;
/*     */   private int fWidth;
/*     */   private Container fContainer;
/*     */   private Rectangle fBounds;
/*     */   private Component fComponent;
/*     */   private Point fGrowBase;
/*     */   private boolean fGrowProportionally;
/*     */   
/*     */   public float getPreferredSpan(int axis) {
/* 554 */     int extra = 2 * (getBorder() + getSpace(axis));
/* 555 */     switch (axis) {
/*     */       case 0:
/* 557 */         return (this.fWidth + extra);
/*     */       case 1:
/* 559 */         return (this.fHeight + extra);
/*     */     } 
/* 561 */     throw new IllegalArgumentException("Invalid axis: " + axis);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean loading;
/*     */   
/*     */   private static Icon sPendingImageIcon;
/*     */   
/*     */   private static Icon sMissingImageIcon;
/*     */   private static final String PENDING_IMAGE_SRC = "icons/image-delayed.gif";
/*     */   private static final String MISSING_IMAGE_SRC = "icons/image-failed.gif";
/*     */   private static final boolean DEBUG = false;
/*     */   static final String IMAGE_CACHE_PROPERTY = "imageCache";
/*     */   private static final int DEFAULT_WIDTH = 32;
/*     */   private static final int DEFAULT_HEIGHT = 32;
/*     */   private static final int DEFAULT_BORDER = 2;
/*     */   
/*     */   public float getAlignment(int axis) {
/* 579 */     switch (axis) {
/*     */       case 1:
/* 581 */         return getVerticalAlignment();
/*     */     } 
/* 583 */     return super.getAlignment(axis);
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
/*     */ 
/*     */   
/*     */   public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
/* 599 */     int p0 = getStartOffset();
/* 600 */     int p1 = getEndOffset();
/* 601 */     if (pos >= p0 && pos <= p1) {
/* 602 */       Rectangle r = a.getBounds();
/* 603 */       if (pos == p1) {
/* 604 */         r.x += r.width;
/*     */       }
/* 606 */       r.width = 0;
/* 607 */       return r;
/*     */     } 
/* 609 */     return null;
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
/*     */   
/*     */   public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
/* 624 */     Rectangle alloc = (Rectangle)a;
/* 625 */     if (x < (alloc.x + alloc.width)) {
/* 626 */       bias[0] = Position.Bias.Forward;
/* 627 */       return getStartOffset();
/*     */     } 
/* 629 */     bias[0] = Position.Bias.Backward;
/* 630 */     return getEndOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(float width, float height) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resize(int width, int height) {
/* 649 */     if (width == this.fWidth && height == this.fHeight) {
/*     */       return;
/*     */     }
/* 652 */     this.fWidth = width;
/* 653 */     this.fHeight = height;
/*     */ 
/*     */     
/* 656 */     MutableAttributeSet attr = new SimpleAttributeSet();
/* 657 */     attr.addAttribute(HTML.Attribute.WIDTH, Integer.toString(width));
/* 658 */     attr.addAttribute(HTML.Attribute.HEIGHT, Integer.toString(height));
/* 659 */     ((StyledDocument)getDocument()).setCharacterAttributes(this.fElement.getStartOffset(), this.fElement.getEndOffset(), attr, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MouseEvent e) {
/* 669 */     Dimension size = this.fComponent.getSize();
/* 670 */     if (e.getX() >= size.width - 7 && e.getY() >= size.height - 7 && getSelectionState() == 2) {
/*     */ 
/*     */ 
/*     */       
/* 674 */       Point loc = this.fComponent.getLocationOnScreen();
/* 675 */       this.fGrowBase = new Point(loc.x + e.getX() - this.fWidth, loc.y + e.getY() - this.fHeight);
/*     */       
/* 677 */       this.fGrowProportionally = e.isShiftDown();
/*     */     } else {
/*     */       
/* 680 */       this.fGrowBase = null;
/* 681 */       JTextComponent comp = (JTextComponent)this.fContainer;
/* 682 */       int start = this.fElement.getStartOffset();
/* 683 */       int end = this.fElement.getEndOffset();
/* 684 */       int mark = comp.getCaret().getMark();
/* 685 */       int dot = comp.getCaret().getDot();
/* 686 */       if (e.isShiftDown()) {
/*     */         
/* 688 */         if (mark <= start) {
/* 689 */           comp.moveCaretPosition(end);
/*     */         } else {
/* 691 */           comp.moveCaretPosition(start);
/*     */         } 
/*     */       } else {
/* 694 */         if (mark != start)
/* 695 */           comp.setCaretPosition(start); 
/* 696 */         if (dot != end) {
/* 697 */           comp.moveCaretPosition(end);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent e) {
/* 704 */     if (this.fGrowBase != null) {
/* 705 */       Point loc = this.fComponent.getLocationOnScreen();
/* 706 */       int width = Math.max(2, loc.x + e.getX() - this.fGrowBase.x);
/* 707 */       int height = Math.max(2, loc.y + e.getY() - this.fGrowBase.y);
/*     */       
/* 709 */       if (e.isShiftDown() && this.fImage != null) {
/*     */         
/* 711 */         float imgWidth = this.fImage.getWidth(this);
/* 712 */         float imgHeight = this.fImage.getHeight(this);
/* 713 */         if (imgWidth > 0.0F && imgHeight > 0.0F) {
/* 714 */           float prop = imgHeight / imgWidth;
/* 715 */           float pwidth = height / prop;
/* 716 */           float pheight = width * prop;
/* 717 */           if (pwidth > width) {
/* 718 */             width = (int)pwidth;
/*     */           } else {
/* 720 */             height = (int)pheight;
/*     */           } 
/*     */         } 
/*     */       } 
/* 724 */       resize(width, height);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent e) {
/* 729 */     this.fGrowBase = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent e) {
/* 735 */     if (e.getClickCount() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Icon makeIcon(String gifFile) throws IOException {
/* 759 */     InputStream resource = ImageViewCustom.class.getResourceAsStream(gifFile);
/*     */     
/* 761 */     if (resource == null) {
/* 762 */       System.err.println(ImageViewCustom.class.getName() + "/" + gifFile + " not found.");
/*     */       
/* 764 */       return null;
/*     */     } 
/* 766 */     BufferedInputStream in = new BufferedInputStream(resource);
/*     */     
/* 768 */     ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
/*     */     
/* 770 */     byte[] buffer = new byte[1024];
/*     */     int n;
/* 772 */     while ((n = in.read(buffer)) > 0) {
/* 773 */       out.write(buffer, 0, n);
/*     */     }
/* 775 */     in.close();
/* 776 */     out.flush();
/*     */     
/* 778 */     buffer = out.toByteArray();
/* 779 */     if (buffer.length == 0) {
/* 780 */       System.err.println("warning: " + gifFile + " is zero-length");
/*     */       
/* 782 */       return null;
/*     */     } 
/* 784 */     return new ImageIcon(buffer);
/*     */   }
/*     */   
/*     */   private void loadIcons() {
/*     */     try {
/* 789 */       if (sPendingImageIcon == null)
/* 790 */         sPendingImageIcon = makeIcon("icons/image-delayed.gif"); 
/* 791 */       if (sMissingImageIcon == null)
/* 792 */         sMissingImageIcon = makeIcon("icons/image-failed.gif"); 
/* 793 */     } catch (Exception x) {
/* 794 */       System.err.println("ImageView: Couldn't load image icons");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected StyleSheet getStyleSheet() {
/* 799 */     HTMLDocument doc = (HTMLDocument)getDocument();
/* 800 */     return doc.getStyleSheet();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\text\ImageViewCustom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */