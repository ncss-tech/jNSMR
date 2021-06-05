/*      */ package org.jdom;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jdom.filter.ElementFilter;
/*      */ import org.jdom.filter.Filter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Element
/*      */   extends Content
/*      */   implements Parent
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: Element.java,v $ $Revision: 1.159 $ $Date: 2007/11/14 05:02:08 $ $Name: jdom_1_1 $";
/*      */   private static final int INITIAL_ARRAY_SIZE = 5;
/*      */   protected String name;
/*      */   protected transient Namespace namespace;
/*      */   protected transient List additionalNamespaces;
/*  106 */   AttributeList attributes = new AttributeList(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   ContentList content = new ContentList(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Element() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name, Namespace namespace) {
/*  141 */     setName(name);
/*  142 */     setNamespace(namespace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name) {
/*  153 */     this(name, (Namespace)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name, String uri) {
/*  168 */     this(name, Namespace.getNamespace("", uri));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name, String prefix, String uri) {
/*  184 */     this(name, Namespace.getNamespace(prefix, uri));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  193 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setName(String name) {
/*  205 */     String reason = Verifier.checkElementName(name);
/*  206 */     if (reason != null) {
/*  207 */       throw new IllegalNameException(name, "element", reason);
/*      */     }
/*  209 */     this.name = name;
/*  210 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Namespace getNamespace() {
/*  219 */     return this.namespace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setNamespace(Namespace namespace) {
/*  230 */     if (namespace == null) {
/*  231 */       namespace = Namespace.NO_NAMESPACE;
/*      */     }
/*      */     
/*  234 */     this.namespace = namespace;
/*  235 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespacePrefix() {
/*  245 */     return this.namespace.getPrefix();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceURI() {
/*  256 */     return this.namespace.getURI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Namespace getNamespace(String prefix) {
/*  271 */     if (prefix == null) {
/*  272 */       return null;
/*      */     }
/*      */     
/*  275 */     if ("xml".equals(prefix))
/*      */     {
/*  277 */       return Namespace.XML_NAMESPACE;
/*      */     }
/*      */ 
/*      */     
/*  281 */     if (prefix.equals(getNamespacePrefix())) {
/*  282 */       return getNamespace();
/*      */     }
/*      */ 
/*      */     
/*  286 */     if (this.additionalNamespaces != null) {
/*  287 */       for (int i = 0; i < this.additionalNamespaces.size(); i++) {
/*  288 */         Namespace ns = this.additionalNamespaces.get(i);
/*  289 */         if (prefix.equals(ns.getPrefix())) {
/*  290 */           return ns;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  296 */     if (this.parent instanceof Element) {
/*  297 */       return ((Element)this.parent).getNamespace(prefix);
/*      */     }
/*      */     
/*  300 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQualifiedName() {
/*  314 */     if ("".equals(this.namespace.getPrefix())) {
/*  315 */       return getName();
/*      */     }
/*      */     
/*  318 */     return this.namespace.getPrefix() + ':' + this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNamespaceDeclaration(Namespace additionalNamespace) {
/*  340 */     String reason = Verifier.checkNamespaceCollision(additionalNamespace, this);
/*  341 */     if (reason != null) {
/*  342 */       throw new IllegalAddException(this, additionalNamespace, reason);
/*      */     }
/*      */     
/*  345 */     if (this.additionalNamespaces == null) {
/*  346 */       this.additionalNamespaces = new ArrayList(5);
/*      */     }
/*      */     
/*  349 */     this.additionalNamespaces.add(additionalNamespace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNamespaceDeclaration(Namespace additionalNamespace) {
/*  363 */     if (this.additionalNamespaces == null) {
/*      */       return;
/*      */     }
/*  366 */     this.additionalNamespaces.remove(additionalNamespace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getAdditionalNamespaces() {
/*  383 */     if (this.additionalNamespaces == null) {
/*  384 */       return Collections.EMPTY_LIST;
/*      */     }
/*  386 */     return Collections.unmodifiableList(this.additionalNamespaces);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  398 */     StringBuffer buffer = new StringBuffer();
/*      */     
/*  400 */     Iterator iter = getContent().iterator();
/*  401 */     while (iter.hasNext()) {
/*  402 */       Content child = iter.next();
/*  403 */       if (child instanceof Element || child instanceof Text) {
/*  404 */         buffer.append(child.getValue());
/*      */       }
/*      */     } 
/*  407 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRootElement() {
/*  418 */     return this.parent instanceof Document;
/*      */   }
/*      */   
/*      */   public int getContentSize() {
/*  422 */     return this.content.size();
/*      */   }
/*      */   
/*      */   public int indexOf(Content child) {
/*  426 */     return this.content.indexOf(child);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  452 */     if (this.content.size() == 0) {
/*  453 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  457 */     if (this.content.size() == 1) {
/*  458 */       Object obj = this.content.get(0);
/*  459 */       if (obj instanceof Text) {
/*  460 */         return ((Text)obj).getText();
/*      */       }
/*      */       
/*  463 */       return "";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  468 */     StringBuffer textContent = new StringBuffer();
/*  469 */     boolean hasText = false;
/*      */     
/*  471 */     for (int i = 0; i < this.content.size(); i++) {
/*  472 */       Object obj = this.content.get(i);
/*  473 */       if (obj instanceof Text) {
/*  474 */         textContent.append(((Text)obj).getText());
/*  475 */         hasText = true;
/*      */       } 
/*      */     } 
/*      */     
/*  479 */     if (!hasText) {
/*  480 */       return "";
/*      */     }
/*      */     
/*  483 */     return textContent.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextTrim() {
/*  496 */     return getText().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextNormalize() {
/*  509 */     return Text.normalizeString(getText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildText(String name) {
/*  522 */     Element child = getChild(name);
/*  523 */     if (child == null) {
/*  524 */       return null;
/*      */     }
/*  526 */     return child.getText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildTextTrim(String name) {
/*  539 */     Element child = getChild(name);
/*  540 */     if (child == null) {
/*  541 */       return null;
/*      */     }
/*  543 */     return child.getTextTrim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildTextNormalize(String name) {
/*  556 */     Element child = getChild(name);
/*  557 */     if (child == null) {
/*  558 */       return null;
/*      */     }
/*  560 */     return child.getTextNormalize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildText(String name, Namespace ns) {
/*  573 */     Element child = getChild(name, ns);
/*  574 */     if (child == null) {
/*  575 */       return null;
/*      */     }
/*  577 */     return child.getText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildTextTrim(String name, Namespace ns) {
/*  590 */     Element child = getChild(name, ns);
/*  591 */     if (child == null) {
/*  592 */       return null;
/*      */     }
/*  594 */     return child.getTextTrim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChildTextNormalize(String name, Namespace ns) {
/*  607 */     Element child = getChild(name, ns);
/*  608 */     if (child == null) {
/*  609 */       return null;
/*      */     }
/*  611 */     return child.getTextNormalize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setText(String text) {
/*  629 */     this.content.clear();
/*      */     
/*  631 */     if (text != null) {
/*  632 */       addContent(new Text(text));
/*      */     }
/*      */     
/*  635 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getContent() {
/*  661 */     return this.content;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getContent(Filter filter) {
/*  677 */     return this.content.getView(filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List removeContent() {
/*  686 */     List old = new ArrayList(this.content);
/*  687 */     this.content.clear();
/*  688 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List removeContent(Filter filter) {
/*  698 */     List old = new ArrayList();
/*  699 */     Iterator iter = this.content.getView(filter).iterator();
/*  700 */     while (iter.hasNext()) {
/*  701 */       Content child = iter.next();
/*  702 */       old.add(child);
/*  703 */       iter.remove();
/*      */     } 
/*  705 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setContent(Collection newContent) {
/*  744 */     this.content.clearAndSet(newContent);
/*  745 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setContent(int index, Content child) {
/*  764 */     this.content.set(index, child);
/*  765 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parent setContent(int index, Collection newContent) {
/*  785 */     this.content.remove(index);
/*  786 */     this.content.addAll(index, newContent);
/*  787 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(String str) {
/*  801 */     return addContent(new Text(str));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(Content child) {
/*  811 */     this.content.add((E)child);
/*  812 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(Collection newContent) {
/*  827 */     this.content.addAll(newContent);
/*  828 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(int index, Content child) {
/*  842 */     this.content.add(index, child);
/*  843 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(int index, Collection newContent) {
/*  861 */     this.content.addAll(index, newContent);
/*  862 */     return this;
/*      */   }
/*      */   
/*      */   public List cloneContent() {
/*  866 */     int size = getContentSize();
/*  867 */     List list = new ArrayList(size);
/*  868 */     for (int i = 0; i < size; i++) {
/*  869 */       Content child = getContent(i);
/*  870 */       list.add(child.clone());
/*      */     } 
/*  872 */     return list;
/*      */   }
/*      */   
/*      */   public Content getContent(int index) {
/*  876 */     return (Content)this.content.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeContent(Content child) {
/*  885 */     return this.content.remove(child);
/*      */   }
/*      */   
/*      */   public Content removeContent(int index) {
/*  889 */     return (Content)this.content.remove(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setContent(Content child) {
/*  920 */     this.content.clear();
/*  921 */     this.content.add((E)child);
/*  922 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAncestor(Element element) {
/*  934 */     Parent p = element.getParent();
/*  935 */     while (p instanceof Element) {
/*  936 */       if (p == this) {
/*  937 */         return true;
/*      */       }
/*  939 */       p = p.getParent();
/*      */     } 
/*  941 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getAttributes() {
/*  956 */     return this.attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attribute getAttribute(String name) {
/*  969 */     return getAttribute(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attribute getAttribute(String name, Namespace ns) {
/*  983 */     return (Attribute)this.attributes.get(name, ns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String name) {
/*  997 */     return getAttributeValue(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String name, String def) {
/* 1012 */     return getAttributeValue(name, Namespace.NO_NAMESPACE, def);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String name, Namespace ns) {
/* 1027 */     return getAttributeValue(name, ns, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String name, Namespace ns, String def) {
/* 1043 */     Attribute attribute = (Attribute)this.attributes.get(name, ns);
/* 1044 */     if (attribute == null) {
/* 1045 */       return def;
/*      */     }
/*      */     
/* 1048 */     return attribute.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttributes(Collection newAttributes) {
/* 1095 */     this.attributes.clearAndSet(newAttributes);
/* 1096 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttributes(List newAttributes) {
/* 1107 */     return setAttributes(newAttributes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttribute(String name, String value) {
/* 1126 */     Attribute attribute = getAttribute(name);
/* 1127 */     if (attribute == null) {
/* 1128 */       Attribute newAttribute = new Attribute(name, value);
/* 1129 */       setAttribute(newAttribute);
/*      */     } else {
/* 1131 */       attribute.setValue(value);
/*      */     } 
/*      */     
/* 1134 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttribute(String name, String value, Namespace ns) {
/* 1157 */     Attribute attribute = getAttribute(name, ns);
/* 1158 */     if (attribute == null) {
/* 1159 */       Attribute newAttribute = new Attribute(name, value, ns);
/* 1160 */       setAttribute(newAttribute);
/*      */     } else {
/* 1162 */       attribute.setValue(value);
/*      */     } 
/*      */     
/* 1165 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttribute(Attribute attribute) {
/* 1181 */     this.attributes.add(attribute);
/* 1182 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAttribute(String name) {
/* 1195 */     return removeAttribute(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAttribute(String name, Namespace ns) {
/* 1210 */     return this.attributes.remove(name, ns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAttribute(Attribute attribute) {
/* 1222 */     return this.attributes.remove(attribute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1238 */     StringBuffer stringForm = (new StringBuffer(64)).append("[Element: <").append(getQualifiedName());
/*      */ 
/*      */ 
/*      */     
/* 1242 */     String nsuri = getNamespaceURI();
/* 1243 */     if (!"".equals(nsuri)) {
/* 1244 */       stringForm.append(" [Namespace: ").append(nsuri).append("]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1249 */     stringForm.append("/>]");
/*      */     
/* 1251 */     return stringForm.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 1267 */     Element element = (Element)super.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1280 */     element.content = new ContentList(element);
/* 1281 */     element.attributes = new AttributeList(element);
/*      */ 
/*      */     
/* 1284 */     if (this.attributes != null) {
/* 1285 */       for (int i = 0; i < this.attributes.size(); i++) {
/* 1286 */         Attribute attribute = (Attribute)this.attributes.get(i);
/* 1287 */         element.attributes.add(attribute.clone());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1292 */     if (this.additionalNamespaces != null) {
/* 1293 */       element.additionalNamespaces = new ArrayList(this.additionalNamespaces);
/*      */     }
/*      */ 
/*      */     
/* 1297 */     if (this.content != null) {
/* 1298 */       for (int i = 0; i < this.content.size(); i++) {
/* 1299 */         Content c = (Content)this.content.get(i);
/* 1300 */         element.content.add((E)c.clone());
/*      */       } 
/*      */     }
/*      */     
/* 1304 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 1312 */     out.defaultWriteObject();
/*      */ 
/*      */ 
/*      */     
/* 1316 */     out.writeObject(this.namespace.getPrefix());
/* 1317 */     out.writeObject(this.namespace.getURI());
/*      */     
/* 1319 */     if (this.additionalNamespaces == null) {
/* 1320 */       out.write(0);
/*      */     } else {
/*      */       
/* 1323 */       int size = this.additionalNamespaces.size();
/* 1324 */       out.write(size);
/* 1325 */       for (int i = 0; i < size; i++) {
/* 1326 */         Namespace additional = this.additionalNamespaces.get(i);
/* 1327 */         out.writeObject(additional.getPrefix());
/* 1328 */         out.writeObject(additional.getURI());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1336 */     in.defaultReadObject();
/*      */     
/* 1338 */     this.namespace = Namespace.getNamespace((String)in.readObject(), (String)in.readObject());
/*      */ 
/*      */     
/* 1341 */     int size = in.read();
/*      */     
/* 1343 */     if (size != 0) {
/* 1344 */       this.additionalNamespaces = new ArrayList(size);
/* 1345 */       for (int i = 0; i < size; i++) {
/* 1346 */         Namespace additional = Namespace.getNamespace((String)in.readObject(), (String)in.readObject());
/*      */         
/* 1348 */         this.additionalNamespaces.add(additional);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator getDescendants() {
/* 1359 */     return new DescendantIterator(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator getDescendants(Filter filter) {
/* 1372 */     Iterator iterator = new DescendantIterator(this);
/* 1373 */     return new FilterIterator(iterator, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getChildren() {
/* 1410 */     return this.content.getView((Filter)new ElementFilter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getChildren(String name) {
/* 1430 */     return getChildren(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getChildren(String name, Namespace ns) {
/* 1451 */     return this.content.getView((Filter)new ElementFilter(name, ns));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getChild(String name, Namespace ns) {
/* 1465 */     List elements = this.content.getView((Filter)new ElementFilter(name, ns));
/* 1466 */     Iterator iter = elements.iterator();
/* 1467 */     if (iter.hasNext()) {
/* 1468 */       return iter.next();
/*      */     }
/* 1470 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getChild(String name) {
/* 1483 */     return getChild(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeChild(String name) {
/* 1497 */     return removeChild(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeChild(String name, Namespace ns) {
/* 1512 */     ElementFilter elementFilter = new ElementFilter(name, ns);
/* 1513 */     List old = this.content.getView((Filter)elementFilter);
/* 1514 */     Iterator iter = old.iterator();
/* 1515 */     if (iter.hasNext()) {
/* 1516 */       iter.next();
/* 1517 */       iter.remove();
/* 1518 */       return true;
/*      */     } 
/*      */     
/* 1521 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeChildren(String name) {
/* 1535 */     return removeChildren(name, Namespace.NO_NAMESPACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeChildren(String name, Namespace ns) {
/* 1550 */     boolean deletedSome = false;
/*      */     
/* 1552 */     ElementFilter elementFilter = new ElementFilter(name, ns);
/* 1553 */     List old = this.content.getView((Filter)elementFilter);
/* 1554 */     Iterator iter = old.iterator();
/* 1555 */     while (iter.hasNext()) {
/* 1556 */       iter.next();
/* 1557 */       iter.remove();
/* 1558 */       deletedSome = true;
/*      */     } 
/*      */     
/* 1561 */     return deletedSome;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/Element.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */