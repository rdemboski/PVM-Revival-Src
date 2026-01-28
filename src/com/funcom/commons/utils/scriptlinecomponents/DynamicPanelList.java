/*     */ package com.funcom.commons.utils.scriptlinecomponents;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.util.Iterator;
/*     */ import javax.swing.AbstractCellEditor;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicPanelList<T extends JPanel>
/*     */   implements Iterable<T>
/*     */ {
/*     */   private JTable _list;
/*     */   protected TableRendererAndEditor _rendererAndEditor;
/*     */   protected DefaultTableModel _dataModel;
/*     */   private Component _lastSelectedComponent;
/*     */   private final JPanel _panel;
/*     */   
/*     */   public DynamicPanelList() {
/*  30 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicPanelList(DefaultTableModel dataModel) {
/*  35 */     if (dataModel == null) {
/*  36 */       this._list = new JTable();
/*  37 */       this._dataModel = (DefaultTableModel)this._list.getModel();
/*  38 */       this._dataModel.addColumn("DUMMY");
/*     */     } else {
/*     */       
/*  41 */       this._dataModel = dataModel;
/*  42 */       this._list = new JTable(this._dataModel);
/*     */     } 
/*  44 */     this._rendererAndEditor = new TableRendererAndEditor();
/*  45 */     this._list.getColumnModel().getColumn(0).setCellEditor(this._rendererAndEditor);
/*  46 */     this._list.getColumnModel().getColumn(0).setCellRenderer(this._rendererAndEditor);
/*  47 */     setRowHeight(40);
/*     */     
/*  49 */     this._panel = new JPanel();
/*  50 */     this._panel.setLayout(new BorderLayout());
/*  51 */     this._panel.add(this._list);
/*     */   }
/*     */   
/*     */   public void setRowHeight(int height) {
/*  55 */     this._list.setRowHeight(height);
/*     */   }
/*     */   
/*     */   public JPanel getVisibleComponent() {
/*  59 */     return this._panel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelCellEditing() {
/*  64 */     this._rendererAndEditor.cancelCellEditing();
/*     */   }
/*     */   
/*     */   public int getIndexOf(T panel) {
/*  68 */     for (int t = 0; t < this._dataModel.getRowCount(); t++) {
/*  69 */       if (panel == this._dataModel.getValueAt(t, 0)) return t; 
/*     */     } 
/*  71 */     return -1;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  75 */     this._dataModel.setRowCount(0);
/*  76 */     this._rendererAndEditor.cancelCellEditing();
/*     */   }
/*     */   
/*     */   public void remove(T panel) {
/*  80 */     int i = getIndexOf(panel);
/*  81 */     if (i != -1) {
/*  82 */       this._dataModel.removeRow(i);
/*  83 */       this._rendererAndEditor.cancelCellEditing();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(T panel) {
/*  88 */     this._dataModel.addRow(new Object[] { panel });
/*     */   }
/*     */   
/*     */   public void movePanelUp(T panel) {
/*  92 */     int pos = getIndexOf(panel);
/*  93 */     if (pos != 0) {
/*  94 */       Object temp = this._dataModel.getValueAt(pos - 1, 0);
/*  95 */       this._dataModel.setValueAt(panel, pos - 1, 0);
/*  96 */       this._dataModel.setValueAt(temp, pos, 0);
/*  97 */       cancelCellEditing();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void movePanelDown(T panel) {
/* 102 */     int pos = getIndexOf(panel);
/* 103 */     if (pos < this._dataModel.getRowCount() - 1) {
/* 104 */       Object temp = this._dataModel.getValueAt(pos + 1, 0);
/* 105 */       this._dataModel.setValueAt(panel, pos + 1, 0);
/* 106 */       this._dataModel.setValueAt(temp, pos, 0);
/* 107 */       cancelCellEditing();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 113 */     return new Iterator<T>()
/*     */       {
/* 115 */         int pos = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 118 */           return (this.pos < DynamicPanelList.this._dataModel.getRowCount());
/*     */         }
/*     */ 
/*     */         
/*     */         public T next() {
/* 123 */           return (T)DynamicPanelList.this._dataModel.getValueAt(this.pos++, 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 128 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected class TableRendererAndEditor
/*     */     extends AbstractCellEditor
/*     */     implements TableCellEditor, TableCellRenderer {
/*     */     public Object getCellEditorValue() {
/* 137 */       return DynamicPanelList.this._lastSelectedComponent;
/*     */     }
/*     */     
/*     */     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
/* 141 */       DynamicPanelList.this._lastSelectedComponent = (JPanel)value;
/* 142 */       return (JPanel)value;
/*     */     }
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 146 */       if (!(value instanceof JPanel)) System.out.println("BAD!!! getTableCellRendererComponent " + value.toString()); 
/* 147 */       return (JPanel)value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\DynamicPanelList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */