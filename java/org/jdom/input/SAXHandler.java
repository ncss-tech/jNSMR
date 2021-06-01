/*      */ package org.jdom.input;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.jdom.Attribute;
/*      */ import org.jdom.Content;
/*      */ import org.jdom.DefaultJDOMFactory;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.Element;
/*      */ import org.jdom.EntityRef;
/*      */ import org.jdom.JDOMFactory;
/*      */ import org.jdom.Namespace;
/*      */ import org.jdom.Parent;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAXHandler
/*      */   extends DefaultHandler
/*      */   implements LexicalHandler, DeclHandler, DTDHandler
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: SAXHandler.java,v $ $Revision: 1.73 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1 $";
/*   84 */   private static final Map attrNameToTypeMap = new HashMap(13);
/*      */ 
/*      */ 
/*      */   
/*      */   private Document document;
/*      */ 
/*      */ 
/*      */   
/*      */   private Element currentElement;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean atRoot;
/*      */ 
/*      */   
/*      */   private boolean inDTD = false;
/*      */ 
/*      */   
/*      */   private boolean inInternalSubset = false;
/*      */ 
/*      */   
/*      */   private boolean previousCDATA = false;
/*      */ 
/*      */   
/*      */   private boolean inCDATA = false;
/*      */ 
/*      */   
/*      */   private boolean expand = true;
/*      */ 
/*      */   
/*      */   private boolean suppress = false;
/*      */ 
/*      */   
/*  117 */   private int entityDepth = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private List declaredNamespaces;
/*      */ 
/*      */   
/*  124 */   private StringBuffer internalSubset = new StringBuffer();
/*      */ 
/*      */   
/*  127 */   private TextBuffer textBuffer = new TextBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map externalEntities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JDOMFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoringWhite = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoringBoundaryWhite = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Locator locator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  161 */     attrNameToTypeMap.put("CDATA", new Integer(1));
/*      */     
/*  163 */     attrNameToTypeMap.put("ID", new Integer(2));
/*      */     
/*  165 */     attrNameToTypeMap.put("IDREF", new Integer(3));
/*      */     
/*  167 */     attrNameToTypeMap.put("IDREFS", new Integer(4));
/*      */     
/*  169 */     attrNameToTypeMap.put("ENTITY", new Integer(5));
/*      */     
/*  171 */     attrNameToTypeMap.put("ENTITIES", new Integer(6));
/*      */     
/*  173 */     attrNameToTypeMap.put("NMTOKEN", new Integer(7));
/*      */     
/*  175 */     attrNameToTypeMap.put("NMTOKENS", new Integer(8));
/*      */     
/*  177 */     attrNameToTypeMap.put("NOTATION", new Integer(9));
/*      */     
/*  179 */     attrNameToTypeMap.put("ENUMERATION", new Integer(10));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXHandler() {
/*  189 */     this(null);
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
/*      */   public SAXHandler(JDOMFactory factory) {
/*  201 */     if (factory != null) {
/*  202 */       this.factory = factory;
/*      */     } else {
/*  204 */       this.factory = (JDOMFactory)new DefaultJDOMFactory();
/*      */     } 
/*      */     
/*  207 */     this.atRoot = true;
/*  208 */     this.declaredNamespaces = new ArrayList();
/*  209 */     this.externalEntities = new HashMap();
/*      */     
/*  211 */     this.document = this.factory.document(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pushElement(Element element) {
/*  222 */     if (this.atRoot) {
/*  223 */       this.document.setRootElement(element);
/*  224 */       this.atRoot = false;
/*      */     } else {
/*      */       
/*  227 */       this.factory.addContent((Parent)this.currentElement, (Content)element);
/*      */     } 
/*  229 */     this.currentElement = element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document getDocument() {
/*  238 */     return this.document;
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
/*      */   public JDOMFactory getFactory() {
/*  250 */     return this.factory;
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
/*      */   public void setExpandEntities(boolean expand) {
/*  263 */     this.expand = expand;
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
/*      */   public boolean getExpandEntities() {
/*  276 */     return this.expand;
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
/*      */   public void setIgnoringElementContentWhitespace(boolean ignoringWhite) {
/*  291 */     this.ignoringWhite = ignoringWhite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoringBoundaryWhitespace(boolean ignoringBoundaryWhite) {
/*  302 */     this.ignoringBoundaryWhite = ignoringBoundaryWhite;
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
/*      */   public boolean getIgnoringBoundaryWhitespace() {
/*  315 */     return this.ignoringBoundaryWhite;
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
/*      */   public boolean getIgnoringElementContentWhitespace() {
/*  329 */     return this.ignoringWhite;
/*      */   }
/*      */   
/*      */   public void startDocument() {
/*  333 */     if (this.locator != null) {
/*  334 */       this.document.setBaseURI(this.locator.getSystemId());
/*      */     }
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
/*      */   public void externalEntityDecl(String name, String publicID, String systemID) throws SAXException {
/*  351 */     this.externalEntities.put(name, new String[] { publicID, systemID });
/*      */     
/*  353 */     if (!this.inInternalSubset)
/*      */       return; 
/*  355 */     this.internalSubset.append("  <!ENTITY ").append(name);
/*      */     
/*  357 */     appendExternalId(publicID, systemID);
/*  358 */     this.internalSubset.append(">\n");
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
/*      */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
/*  375 */     if (!this.inInternalSubset)
/*      */       return; 
/*  377 */     this.internalSubset.append("  <!ATTLIST ").append(eName).append(' ').append(aName).append(' ').append(type).append(' ');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     if (valueDefault != null) {
/*  385 */       this.internalSubset.append(valueDefault);
/*      */     } else {
/*  387 */       this.internalSubset.append('"').append(value).append('"');
/*      */     } 
/*      */ 
/*      */     
/*  391 */     if (valueDefault != null && valueDefault.equals("#FIXED")) {
/*  392 */       this.internalSubset.append(" \"").append(value).append('"');
/*      */     }
/*      */ 
/*      */     
/*  396 */     this.internalSubset.append(">\n");
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
/*      */   public void elementDecl(String name, String model) throws SAXException {
/*  408 */     if (!this.inInternalSubset)
/*      */       return; 
/*  410 */     this.internalSubset.append("  <!ELEMENT ").append(name).append(' ').append(model).append(">\n");
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
/*      */   public void internalEntityDecl(String name, String value) throws SAXException {
/*  428 */     if (!this.inInternalSubset)
/*      */       return; 
/*  430 */     this.internalSubset.append("  <!ENTITY ");
/*  431 */     if (name.startsWith("%")) {
/*  432 */       this.internalSubset.append("% ").append(name.substring(1));
/*      */     } else {
/*  434 */       this.internalSubset.append(name);
/*      */     } 
/*  436 */     this.internalSubset.append(" \"").append(value).append("\">\n");
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
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/*  455 */     if (this.suppress)
/*      */       return; 
/*  457 */     flushCharacters();
/*      */     
/*  459 */     if (this.atRoot) {
/*  460 */       this.factory.addContent((Parent)this.document, (Content)this.factory.processingInstruction(target, data));
/*      */     } else {
/*  462 */       this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.processingInstruction(target, data));
/*      */     } 
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
/*      */   public void skippedEntity(String name) throws SAXException {
/*  479 */     if (name.startsWith("%"))
/*      */       return; 
/*  481 */     flushCharacters();
/*      */     
/*  483 */     this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.entityRef(name));
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
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  496 */     if (this.suppress)
/*      */       return; 
/*  498 */     Namespace ns = Namespace.getNamespace(prefix, uri);
/*  499 */     this.declaredNamespaces.add(ns);
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
/*      */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*  522 */     if (this.suppress)
/*      */       return; 
/*  524 */     Element element = null;
/*      */     
/*  526 */     if (namespaceURI != null && !namespaceURI.equals("")) {
/*  527 */       String prefix = "";
/*      */ 
/*      */       
/*  530 */       if (!qName.equals(localName)) {
/*  531 */         int split = qName.indexOf(":");
/*  532 */         prefix = qName.substring(0, split);
/*      */       } 
/*  534 */       Namespace elementNamespace = Namespace.getNamespace(prefix, namespaceURI);
/*      */       
/*  536 */       element = this.factory.element(localName, elementNamespace);
/*      */     } else {
/*  538 */       element = this.factory.element(localName);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  543 */     if (this.declaredNamespaces.size() > 0) {
/*  544 */       transferNamespaces(element);
/*      */     }
/*      */ 
/*      */     
/*  548 */     for (int i = 0, len = atts.getLength(); i < len; i++) {
/*  549 */       Attribute attribute = null;
/*      */       
/*  551 */       String attLocalName = atts.getLocalName(i);
/*  552 */       String attQName = atts.getQName(i);
/*  553 */       int attType = getAttributeType(atts.getType(i));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  559 */       if (!attQName.startsWith("xmlns:") && !attQName.equals("xmlns")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  565 */         if ("".equals(attLocalName) && attQName.indexOf(":") == -1) {
/*  566 */           attribute = this.factory.attribute(attQName, atts.getValue(i), attType);
/*  567 */         } else if (!attQName.equals(attLocalName)) {
/*  568 */           String attPrefix = attQName.substring(0, attQName.indexOf(":"));
/*  569 */           Namespace attNs = Namespace.getNamespace(attPrefix, atts.getURI(i));
/*      */ 
/*      */           
/*  572 */           attribute = this.factory.attribute(attLocalName, atts.getValue(i), attType, attNs);
/*      */         } else {
/*      */           
/*  575 */           attribute = this.factory.attribute(attLocalName, atts.getValue(i), attType);
/*      */         } 
/*      */         
/*  578 */         this.factory.setAttribute(element, attribute);
/*      */       } 
/*      */     } 
/*  581 */     flushCharacters();
/*      */     
/*  583 */     if (this.atRoot) {
/*  584 */       this.document.setRootElement(element);
/*  585 */       this.atRoot = false;
/*      */     } else {
/*  587 */       this.factory.addContent((Parent)getCurrentElement(), (Content)element);
/*      */     } 
/*  589 */     this.currentElement = element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transferNamespaces(Element element) {
/*  599 */     Iterator i = this.declaredNamespaces.iterator();
/*  600 */     while (i.hasNext()) {
/*  601 */       Namespace ns = i.next();
/*  602 */       if (ns != element.getNamespace()) {
/*  603 */         element.addNamespaceDeclaration(ns);
/*      */       }
/*      */     } 
/*  606 */     this.declaredNamespaces.clear();
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
/*      */   public void characters(char[] ch, int start, int length) throws SAXException {
/*  620 */     if (this.suppress || length == 0) {
/*      */       return;
/*      */     }
/*  623 */     if (this.previousCDATA != this.inCDATA) {
/*  624 */       flushCharacters();
/*      */     }
/*      */     
/*  627 */     this.textBuffer.append(ch, start, length);
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
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*  642 */     if (!this.ignoringWhite) {
/*  643 */       characters(ch, start, length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flushCharacters() throws SAXException {
/*  654 */     if (this.ignoringBoundaryWhite) {
/*  655 */       if (!this.textBuffer.isAllWhitespace()) {
/*  656 */         flushCharacters(this.textBuffer.toString());
/*      */       }
/*      */     } else {
/*      */       
/*  660 */       flushCharacters(this.textBuffer.toString());
/*      */     } 
/*  662 */     this.textBuffer.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flushCharacters(String data) throws SAXException {
/*  673 */     if (data.length() == 0) {
/*  674 */       this.previousCDATA = this.inCDATA;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  689 */     if (this.previousCDATA) {
/*  690 */       this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.cdata(data));
/*      */     } else {
/*      */       
/*  693 */       this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.text(data));
/*      */     } 
/*      */     
/*  696 */     this.previousCDATA = this.inCDATA;
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
/*      */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*  714 */     if (this.suppress)
/*      */       return; 
/*  716 */     flushCharacters();
/*      */     
/*  718 */     if (!this.atRoot) {
/*  719 */       Parent p = this.currentElement.getParent();
/*  720 */       if (p instanceof Document) {
/*  721 */         this.atRoot = true;
/*      */       } else {
/*      */         
/*  724 */         this.currentElement = (Element)p;
/*      */       } 
/*      */     } else {
/*      */       
/*  728 */       throw new SAXException("Ill-formed XML document (missing opening tag for " + localName + ")");
/*      */     } 
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
/*      */   public void startDTD(String name, String publicID, String systemID) throws SAXException {
/*  747 */     flushCharacters();
/*      */     
/*  749 */     this.factory.addContent((Parent)this.document, (Content)this.factory.docType(name, publicID, systemID));
/*  750 */     this.inDTD = true;
/*  751 */     this.inInternalSubset = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDTD() throws SAXException {
/*  761 */     this.document.getDocType().setInternalSubset(this.internalSubset.toString());
/*  762 */     this.inDTD = false;
/*  763 */     this.inInternalSubset = false;
/*      */   }
/*      */   
/*      */   public void startEntity(String name) throws SAXException {
/*  767 */     this.entityDepth++;
/*      */     
/*  769 */     if (this.expand || this.entityDepth > 1) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  775 */     if (name.equals("[dtd]")) {
/*  776 */       this.inInternalSubset = false;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  781 */     if (!this.inDTD && !name.equals("amp") && !name.equals("lt") && !name.equals("gt") && !name.equals("apos") && !name.equals("quot"))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  788 */       if (!this.expand) {
/*  789 */         String pub = null;
/*  790 */         String sys = null;
/*  791 */         String[] ids = (String[])this.externalEntities.get(name);
/*  792 */         if (ids != null) {
/*  793 */           pub = ids[0];
/*  794 */           sys = ids[1];
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  803 */         if (!this.atRoot) {
/*  804 */           flushCharacters();
/*  805 */           EntityRef entity = this.factory.entityRef(name, pub, sys);
/*      */ 
/*      */           
/*  808 */           this.factory.addContent((Parent)getCurrentElement(), (Content)entity);
/*      */         } 
/*  810 */         this.suppress = true;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void endEntity(String name) throws SAXException {
/*  816 */     this.entityDepth--;
/*  817 */     if (this.entityDepth == 0)
/*      */     {
/*      */       
/*  820 */       this.suppress = false;
/*      */     }
/*  822 */     if (name.equals("[dtd]")) {
/*  823 */       this.inInternalSubset = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startCDATA() throws SAXException {
/*  833 */     if (this.suppress)
/*      */       return; 
/*  835 */     this.inCDATA = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endCDATA() throws SAXException {
/*  842 */     if (this.suppress)
/*      */       return; 
/*  844 */     this.previousCDATA = true;
/*  845 */     this.inCDATA = false;
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
/*      */   public void comment(char[] ch, int start, int length) throws SAXException {
/*  862 */     if (this.suppress)
/*      */       return; 
/*  864 */     flushCharacters();
/*      */     
/*  866 */     String commentText = new String(ch, start, length);
/*  867 */     if (this.inDTD && this.inInternalSubset && !this.expand) {
/*  868 */       this.internalSubset.append("  <!--").append(commentText).append("-->\n");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  873 */     if (!this.inDTD && !commentText.equals("")) {
/*  874 */       if (this.atRoot) {
/*  875 */         this.factory.addContent((Parent)this.document, (Content)this.factory.comment(commentText));
/*      */       } else {
/*  877 */         this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.comment(commentText));
/*      */       } 
/*      */     }
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
/*      */   public void notationDecl(String name, String publicID, String systemID) throws SAXException {
/*  892 */     if (!this.inInternalSubset)
/*      */       return; 
/*  894 */     this.internalSubset.append("  <!NOTATION ").append(name);
/*      */     
/*  896 */     appendExternalId(publicID, systemID);
/*  897 */     this.internalSubset.append(">\n");
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
/*      */   public void unparsedEntityDecl(String name, String publicID, String systemID, String notationName) throws SAXException {
/*  912 */     if (!this.inInternalSubset)
/*      */       return; 
/*  914 */     this.internalSubset.append("  <!ENTITY ").append(name);
/*      */     
/*  916 */     appendExternalId(publicID, systemID);
/*  917 */     this.internalSubset.append(" NDATA ").append(notationName);
/*      */     
/*  919 */     this.internalSubset.append(">\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendExternalId(String publicID, String systemID) {
/*  930 */     if (publicID != null) {
/*  931 */       this.internalSubset.append(" PUBLIC \"").append(publicID).append('"');
/*      */     }
/*      */ 
/*      */     
/*  935 */     if (systemID != null) {
/*  936 */       if (publicID == null) {
/*  937 */         this.internalSubset.append(" SYSTEM ");
/*      */       } else {
/*      */         
/*  940 */         this.internalSubset.append(' ');
/*      */       } 
/*  942 */       this.internalSubset.append('"').append(systemID).append('"');
/*      */     } 
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
/*      */   public Element getCurrentElement() throws SAXException {
/*  955 */     if (this.currentElement == null) {
/*  956 */       throw new SAXException("Ill-formed XML document (multiple root elements detected)");
/*      */     }
/*      */     
/*  959 */     return this.currentElement;
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
/*      */   private static int getAttributeType(String typeName) {
/*  975 */     Integer type = (Integer)attrNameToTypeMap.get(typeName);
/*  976 */     if (type == null) {
/*  977 */       if (typeName != null && typeName.length() > 0 && typeName.charAt(0) == '(')
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  982 */         return 10;
/*      */       }
/*      */       
/*  985 */       return 0;
/*      */     } 
/*      */     
/*  988 */     return type.intValue();
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
/*      */   public void setDocumentLocator(Locator locator) {
/* 1005 */     this.locator = locator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locator getDocumentLocator() {
/* 1016 */     return this.locator;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/input/SAXHandler.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */