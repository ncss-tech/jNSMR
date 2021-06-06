/*     */ package org.jdom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jdom.filter.Filter;
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
/*     */ 
/*     */ 
/*     */ public class Document
/*     */   implements Parent
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Document.java,v $ $Revision: 1.85 $ $Date: 2007/11/10 05:28:58 $ $Name: jdom_1_1 $";
/*  83 */   ContentList content = new ContentList(this);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected String baseURI = null;
/*     */ 
/*     */   
/*  91 */   private HashMap propertyMap = null;
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
/*     */   public Document() {}
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
/*     */   public Document(Element rootElement, DocType docType, String baseURI) {
/* 116 */     if (rootElement != null) {
/* 117 */       setRootElement(rootElement);
/*     */     }
/* 119 */     if (docType != null) {
/* 120 */       setDocType(docType);
/*     */     }
/* 122 */     if (baseURI != null) {
/* 123 */       setBaseURI(baseURI);
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
/*     */ 
/*     */   
/*     */   public Document(Element rootElement, DocType docType) {
/* 140 */     this(rootElement, docType, null);
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
/*     */   public Document(Element rootElement) {
/* 154 */     this(rootElement, null, null);
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
/*     */   public Document(List content) {
/* 169 */     setContent(content);
/*     */   }
/*     */   
/*     */   public int getContentSize() {
/* 173 */     return this.content.size();
/*     */   }
/*     */   
/*     */   public int indexOf(Content child) {
/* 177 */     return this.content.indexOf(child);
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
/*     */   public boolean hasRootElement() {
/* 205 */     return !(this.content.indexOfFirstElement() < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getRootElement() {
/* 216 */     int index = this.content.indexOfFirstElement();
/* 217 */     if (index < 0) {
/* 218 */       throw new IllegalStateException("Root element not set");
/*     */     }
/* 220 */     return (Element)this.content.get(index);
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
/*     */   public Document setRootElement(Element rootElement) {
/* 234 */     int index = this.content.indexOfFirstElement();
/* 235 */     if (index < 0) {
/* 236 */       this.content.add((E)rootElement);
/*     */     } else {
/*     */       
/* 239 */       this.content.set(index, rootElement);
/*     */     } 
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element detachRootElement() {
/* 250 */     int index = this.content.indexOfFirstElement();
/* 251 */     if (index < 0)
/* 252 */       return null; 
/* 253 */     return (Element)removeContent(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocType getDocType() {
/* 264 */     int index = this.content.indexOfDocType();
/* 265 */     if (index < 0) {
/* 266 */       return null;
/*     */     }
/*     */     
/* 269 */     return (DocType)this.content.get(index);
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
/*     */ 
/*     */   
/*     */   public Document setDocType(DocType docType) {
/* 287 */     if (docType == null) {
/*     */       
/* 289 */       int i = this.content.indexOfDocType();
/* 290 */       if (i >= 0) this.content.remove(i); 
/* 291 */       return this;
/*     */     } 
/*     */     
/* 294 */     if (docType.getParent() != null) {
/* 295 */       throw new IllegalAddException(docType, "The DocType already is attached to a document");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 300 */     int docTypeIndex = this.content.indexOfDocType();
/* 301 */     if (docTypeIndex < 0) {
/* 302 */       this.content.add(0, docType);
/*     */     } else {
/*     */       
/* 305 */       this.content.set(docTypeIndex, docType);
/*     */     } 
/*     */     
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document addContent(Content child) {
/* 319 */     this.content.add((E)child);
/* 320 */     return this;
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
/*     */   public Document addContent(Collection c) {
/* 335 */     this.content.addAll(c);
/* 336 */     return this;
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
/*     */   public Document addContent(int index, Content child) {
/* 350 */     this.content.add(index, child);
/* 351 */     return this;
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
/*     */ 
/*     */   
/*     */   public Document addContent(int index, Collection c) {
/* 369 */     this.content.addAll(index, c);
/* 370 */     return this;
/*     */   }
/*     */   
/*     */   public List cloneContent() {
/* 374 */     int size = getContentSize();
/* 375 */     List list = new ArrayList(size);
/* 376 */     for (int i = 0; i < size; i++) {
/* 377 */       Content child = getContent(i);
/* 378 */       list.add(child.clone());
/*     */     } 
/* 380 */     return list;
/*     */   }
/*     */   
/*     */   public Content getContent(int index) {
/* 384 */     return (Content)this.content.get(index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getContent() {
/* 407 */     if (!hasRootElement())
/* 408 */       throw new IllegalStateException("Root element not set"); 
/* 409 */     return this.content;
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
/*     */   
/*     */   public List getContent(Filter filter) {
/* 426 */     if (!hasRootElement())
/* 427 */       throw new IllegalStateException("Root element not set"); 
/* 428 */     return this.content.getView(filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List removeContent() {
/* 437 */     List old = new ArrayList(this.content);
/* 438 */     this.content.clear();
/* 439 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List removeContent(Filter filter) {
/* 449 */     List old = new ArrayList();
/* 450 */     Iterator itr = this.content.getView(filter).iterator();
/* 451 */     while (itr.hasNext()) {
/* 452 */       Content child = itr.next();
/* 453 */       old.add(child);
/* 454 */       itr.remove();
/*     */     } 
/* 456 */     return old;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document setContent(Collection newContent) {
/* 494 */     this.content.clearAndSet(newContent);
/* 495 */     return this;
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
/*     */   public final void setBaseURI(String uri) {
/* 508 */     this.baseURI = uri;
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
/*     */   public final String getBaseURI() {
/* 520 */     return this.baseURI;
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
/*     */ 
/*     */   
/*     */   public Document setContent(int index, Content child) {
/* 538 */     this.content.set(index, child);
/* 539 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document setContent(int index, Collection collection) {
/* 559 */     this.content.remove(index);
/* 560 */     this.content.addAll(index, collection);
/* 561 */     return this;
/*     */   }
/*     */   
/*     */   public boolean removeContent(Content child) {
/* 565 */     return this.content.remove(child);
/*     */   }
/*     */   
/*     */   public Content removeContent(int index) {
/* 569 */     return (Content)this.content.remove(index);
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
/*     */   public Document setContent(Content child) {
/* 600 */     this.content.clear();
/* 601 */     this.content.add((E)child);
/* 602 */     return this;
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
/*     */   public String toString() {
/* 616 */     StringBuffer stringForm = (new StringBuffer()).append("[Document: ");
/*     */ 
/*     */     
/* 619 */     DocType docType = getDocType();
/* 620 */     if (docType != null) {
/* 621 */       stringForm.append(docType.toString()).append(", ");
/*     */     } else {
/*     */       
/* 624 */       stringForm.append(" No DOCTYPE declaration, ");
/*     */     } 
/*     */     
/* 627 */     Element rootElement = getRootElement();
/* 628 */     if (rootElement != null) {
/* 629 */       stringForm.append("Root is ").append(rootElement.toString());
/*     */     } else {
/*     */       
/* 632 */       stringForm.append(" No root element");
/*     */     } 
/*     */     
/* 635 */     stringForm.append("]");
/*     */     
/* 637 */     return stringForm.toString();
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
/*     */   public final boolean equals(Object ob) {
/* 649 */     return (ob == this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 658 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 667 */     Document doc = null;
/*     */     
/*     */     try {
/* 670 */       doc = (Document)super.clone();
/* 671 */     } catch (CloneNotSupportedException ce) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 677 */     doc.content = new ContentList(doc);
/*     */ 
/*     */ 
/*     */     
/* 681 */     for (int i = 0; i < this.content.size(); i++) {
/* 682 */       Object obj = this.content.get(i);
/* 683 */       if (obj instanceof Element) {
/* 684 */         Element element = (Element)((Element)obj).clone();
/* 685 */         doc.content.add((E)element);
/*     */       }
/* 687 */       else if (obj instanceof Comment) {
/* 688 */         Comment comment = (Comment)((Comment)obj).clone();
/* 689 */         doc.content.add((E)comment);
/*     */       }
/* 691 */       else if (obj instanceof ProcessingInstruction) {
/* 692 */         ProcessingInstruction pi = (ProcessingInstruction)((ProcessingInstruction)obj).clone();
/*     */         
/* 694 */         doc.content.add((E)pi);
/*     */       }
/* 696 */       else if (obj instanceof DocType) {
/* 697 */         DocType dt = (DocType)((DocType)obj).clone();
/* 698 */         doc.content.add((E)dt);
/*     */       } 
/*     */     } 
/*     */     
/* 702 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getDescendants() {
/* 711 */     return new DescendantIterator(this);
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
/*     */   public Iterator getDescendants(Filter filter) {
/* 724 */     return new FilterIterator(new DescendantIterator(this), filter);
/*     */   }
/*     */   
/*     */   public Parent getParent() {
/* 728 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDocument() {
/* 737 */     return this;
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
/*     */   public void setProperty(String id, Object value) {
/* 749 */     if (this.propertyMap == null) {
/* 750 */       this.propertyMap = new HashMap();
/*     */     }
/* 752 */     this.propertyMap.put(id, value);
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
/*     */   public Object getProperty(String id) {
/* 764 */     if (this.propertyMap == null) return null; 
/* 765 */     return this.propertyMap.get(id);
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/Document.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */