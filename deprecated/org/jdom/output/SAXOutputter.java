/*      */ package org.jdom.output;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jdom.Attribute;
/*      */ import org.jdom.CDATA;
/*      */ import org.jdom.Comment;
/*      */ import org.jdom.Content;
/*      */ import org.jdom.DocType;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.Element;
/*      */ import org.jdom.EntityRef;
/*      */ import org.jdom.JDOMException;
/*      */ import org.jdom.Namespace;
/*      */ import org.jdom.ProcessingInstruction;
/*      */ import org.jdom.Text;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ import org.xml.sax.helpers.AttributesImpl;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ import org.xml.sax.helpers.XMLReaderFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAXOutputter
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: SAXOutputter.java,v $ $Revision: 1.40 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1 $";
/*      */   private static final String NAMESPACES_SAX_FEATURE = "http://xml.org/sax/features/namespaces";
/*      */   private static final String NS_PREFIXES_SAX_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
/*      */   private static final String VALIDATION_SAX_FEATURE = "http://xml.org/sax/features/validation";
/*      */   private static final String LEXICAL_HANDLER_SAX_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
/*      */   private static final String DECL_HANDLER_SAX_PROPERTY = "http://xml.org/sax/properties/declaration-handler";
/*      */   private static final String LEXICAL_HANDLER_ALT_PROPERTY = "http://xml.org/sax/handlers/LexicalHandler";
/*      */   private static final String DECL_HANDLER_ALT_PROPERTY = "http://xml.org/sax/handlers/DeclHandler";
/*  132 */   private static final String[] attrTypeToNameMap = new String[] { "CDATA", "CDATA", "ID", "IDREF", "IDREFS", "ENTITY", "ENTITIES", "NMTOKEN", "NMTOKENS", "NOTATION", "NMTOKEN" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ContentHandler contentHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ErrorHandler errorHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DTDHandler dtdHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityResolver entityResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LexicalHandler lexicalHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DeclHandler declHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean declareNamespaces = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reportDtdEvents = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   private JDOMLocator locator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXOutputter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXOutputter(ContentHandler contentHandler) {
/*  201 */     this(contentHandler, null, null, null, null);
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
/*      */   public SAXOutputter(ContentHandler contentHandler, ErrorHandler errorHandler, DTDHandler dtdHandler, EntityResolver entityResolver) {
/*  220 */     this(contentHandler, errorHandler, dtdHandler, entityResolver, null);
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
/*      */   public SAXOutputter(ContentHandler contentHandler, ErrorHandler errorHandler, DTDHandler dtdHandler, EntityResolver entityResolver, LexicalHandler lexicalHandler) {
/*  241 */     this.contentHandler = contentHandler;
/*  242 */     this.errorHandler = errorHandler;
/*  243 */     this.dtdHandler = dtdHandler;
/*  244 */     this.entityResolver = entityResolver;
/*  245 */     this.lexicalHandler = lexicalHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentHandler(ContentHandler contentHandler) {
/*  255 */     this.contentHandler = contentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContentHandler getContentHandler() {
/*  265 */     return this.contentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  274 */     this.errorHandler = errorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  284 */     return this.errorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDHandler(DTDHandler dtdHandler) {
/*  293 */     this.dtdHandler = dtdHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  303 */     return this.dtdHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver entityResolver) {
/*  312 */     this.entityResolver = entityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  322 */     return this.entityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLexicalHandler(LexicalHandler lexicalHandler) {
/*  331 */     this.lexicalHandler = lexicalHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/*  341 */     return this.lexicalHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDeclHandler(DeclHandler declHandler) {
/*  350 */     this.declHandler = declHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/*  360 */     return this.declHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReportNamespaceDeclarations() {
/*  371 */     return this.declareNamespaces;
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
/*      */   public void setReportNamespaceDeclarations(boolean declareNamespaces) {
/*  383 */     this.declareNamespaces = declareNamespaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReportDTDEvents() {
/*  392 */     return this.reportDtdEvents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReportDTDEvents(boolean reportDtdEvents) {
/*  403 */     this.reportDtdEvents = reportDtdEvents;
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
/*      */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  446 */     if ("http://xml.org/sax/features/namespace-prefixes".equals(name)) {
/*      */       
/*  448 */       setReportNamespaceDeclarations(value);
/*      */     
/*      */     }
/*  451 */     else if ("http://xml.org/sax/features/namespaces".equals(name)) {
/*  452 */       if (value != true)
/*      */       {
/*  454 */         throw new SAXNotSupportedException(name);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  459 */     else if ("http://xml.org/sax/features/validation".equals(name)) {
/*      */       
/*  461 */       setReportDTDEvents(value);
/*      */     }
/*      */     else {
/*      */       
/*  465 */       throw new SAXNotRecognizedException(name);
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
/*      */ 
/*      */   
/*      */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  486 */     if ("http://xml.org/sax/features/namespace-prefixes".equals(name))
/*      */     {
/*  488 */       return this.declareNamespaces;
/*      */     }
/*      */     
/*  491 */     if ("http://xml.org/sax/features/namespaces".equals(name))
/*      */     {
/*  493 */       return true;
/*      */     }
/*      */     
/*  496 */     if ("http://xml.org/sax/features/validation".equals(name))
/*      */     {
/*  498 */       return this.reportDtdEvents;
/*      */     }
/*      */ 
/*      */     
/*  502 */     throw new SAXNotRecognizedException(name);
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
/*      */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  541 */     if ("http://xml.org/sax/properties/lexical-handler".equals(name) || "http://xml.org/sax/handlers/LexicalHandler".equals(name)) {
/*      */       
/*  543 */       setLexicalHandler((LexicalHandler)value);
/*      */     
/*      */     }
/*  546 */     else if ("http://xml.org/sax/properties/declaration-handler".equals(name) || "http://xml.org/sax/handlers/DeclHandler".equals(name)) {
/*      */       
/*  548 */       setDeclHandler((DeclHandler)value);
/*      */     } else {
/*      */       
/*  551 */       throw new SAXNotRecognizedException(name);
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
/*      */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  570 */     if ("http://xml.org/sax/properties/lexical-handler".equals(name) || "http://xml.org/sax/handlers/LexicalHandler".equals(name))
/*      */     {
/*  572 */       return getLexicalHandler();
/*      */     }
/*      */     
/*  575 */     if ("http://xml.org/sax/properties/declaration-handler".equals(name) || "http://xml.org/sax/handlers/DeclHandler".equals(name))
/*      */     {
/*  577 */       return getDeclHandler();
/*      */     }
/*      */     
/*  580 */     throw new SAXNotRecognizedException(name);
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
/*      */   public void output(Document document) throws JDOMException {
/*  595 */     if (document == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  600 */     documentLocator(document);
/*      */ 
/*      */     
/*  603 */     startDocument();
/*      */ 
/*      */     
/*  606 */     if (this.reportDtdEvents) {
/*  607 */       dtdEvents(document);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  612 */     Iterator i = document.getContent().iterator();
/*  613 */     while (i.hasNext()) {
/*  614 */       Object obj = i.next();
/*      */ 
/*      */       
/*  617 */       this.locator.setNode(obj);
/*      */       
/*  619 */       if (obj instanceof Element) {
/*      */         
/*  621 */         element(document.getRootElement(), new NamespaceStack()); continue;
/*      */       } 
/*  623 */       if (obj instanceof ProcessingInstruction) {
/*      */         
/*  625 */         processingInstruction((ProcessingInstruction)obj); continue;
/*      */       } 
/*  627 */       if (obj instanceof Comment)
/*      */       {
/*  629 */         comment(((Comment)obj).getText());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  634 */     endDocument();
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
/*      */   public void output(List nodes) throws JDOMException {
/*  655 */     if (nodes == null || nodes.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  660 */     documentLocator(null);
/*      */ 
/*      */     
/*  663 */     startDocument();
/*      */ 
/*      */     
/*  666 */     elementContent(nodes, new NamespaceStack());
/*      */ 
/*      */     
/*  669 */     endDocument();
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
/*      */   public void output(Element node) throws JDOMException {
/*  681 */     if (node == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  686 */     documentLocator(null);
/*      */ 
/*      */     
/*  689 */     startDocument();
/*      */ 
/*      */     
/*  692 */     elementContent((Content)node, new NamespaceStack());
/*      */ 
/*      */     
/*  695 */     endDocument();
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
/*      */   public void outputFragment(List nodes) throws JDOMException {
/*  717 */     if (nodes == null || nodes.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  722 */     elementContent(nodes, new NamespaceStack());
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
/*      */   public void outputFragment(Content node) throws JDOMException {
/*  744 */     if (node == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  749 */     elementContent(node, new NamespaceStack());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dtdEvents(Document document) throws JDOMException {
/*  760 */     DocType docType = document.getDocType();
/*      */ 
/*      */     
/*  763 */     if (docType != null && (this.dtdHandler != null || this.declHandler != null)) {
/*      */ 
/*      */ 
/*      */       
/*  767 */       String dtdDoc = (new XMLOutputter()).outputString(docType);
/*      */ 
/*      */       
/*      */       try {
/*  771 */         createDTDParser().parse(new InputSource(new StringReader(dtdDoc)));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  777 */       catch (SAXParseException e) {
/*      */ 
/*      */       
/*  780 */       } catch (SAXException e) {
/*  781 */         throw new JDOMException("DTD parsing error", e);
/*      */       }
/*  783 */       catch (IOException e) {
/*  784 */         throw new JDOMException("DTD parsing error", e);
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
/*      */ 
/*      */   
/*      */   private void documentLocator(Document document) {
/*  801 */     this.locator = new JDOMLocator();
/*  802 */     String publicID = null;
/*  803 */     String systemID = null;
/*      */     
/*  805 */     if (document != null) {
/*  806 */       DocType docType = document.getDocType();
/*  807 */       if (docType != null) {
/*  808 */         publicID = docType.getPublicID();
/*  809 */         systemID = docType.getSystemID();
/*      */       } 
/*      */     } 
/*  812 */     this.locator.setPublicId(publicID);
/*  813 */     this.locator.setSystemId(systemID);
/*  814 */     this.locator.setLineNumber(-1);
/*  815 */     this.locator.setColumnNumber(-1);
/*      */     
/*  817 */     this.contentHandler.setDocumentLocator(this.locator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void startDocument() throws JDOMException {
/*      */     try {
/*  828 */       this.contentHandler.startDocument();
/*      */     }
/*  830 */     catch (SAXException se) {
/*  831 */       throw new JDOMException("Exception in startDocument", se);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endDocument() throws JDOMException {
/*      */     try {
/*  843 */       this.contentHandler.endDocument();
/*      */ 
/*      */       
/*  846 */       this.locator = null;
/*      */     }
/*  848 */     catch (SAXException se) {
/*  849 */       throw new JDOMException("Exception in endDocument", se);
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
/*      */   private void processingInstruction(ProcessingInstruction pi) throws JDOMException {
/*  863 */     if (pi != null) {
/*  864 */       String target = pi.getTarget();
/*  865 */       String data = pi.getData();
/*      */       try {
/*  867 */         this.contentHandler.processingInstruction(target, data);
/*      */       }
/*  869 */       catch (SAXException se) {
/*  870 */         throw new JDOMException("Exception in processingInstruction", se);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void element(Element element, NamespaceStack namespaces) throws JDOMException {
/*  888 */     int previouslyDeclaredNamespaces = namespaces.size();
/*      */ 
/*      */     
/*  891 */     Attributes nsAtts = startPrefixMapping(element, namespaces);
/*      */ 
/*      */     
/*  894 */     startElement(element, nsAtts);
/*      */ 
/*      */     
/*  897 */     elementContent(element.getContent(), namespaces);
/*      */ 
/*      */     
/*  900 */     if (this.locator != null) {
/*  901 */       this.locator.setNode(element);
/*      */     }
/*      */ 
/*      */     
/*  905 */     endElement(element);
/*      */ 
/*      */     
/*  908 */     endPrefixMapping(namespaces, previouslyDeclaredNamespaces);
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
/*      */   private Attributes startPrefixMapping(Element element, NamespaceStack namespaces) throws JDOMException {
/*  927 */     AttributesImpl nsAtts = null;
/*      */     
/*  929 */     Namespace ns = element.getNamespace();
/*  930 */     if (ns != Namespace.XML_NAMESPACE) {
/*  931 */       String prefix = ns.getPrefix();
/*  932 */       String uri = namespaces.getURI(prefix);
/*  933 */       if (!ns.getURI().equals(uri)) {
/*  934 */         namespaces.push(ns);
/*  935 */         nsAtts = addNsAttribute(nsAtts, ns);
/*      */         try {
/*  937 */           this.contentHandler.startPrefixMapping(prefix, ns.getURI());
/*      */         }
/*  939 */         catch (SAXException se) {
/*  940 */           throw new JDOMException("Exception in startPrefixMapping", se);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  947 */     List additionalNamespaces = element.getAdditionalNamespaces();
/*  948 */     if (additionalNamespaces != null) {
/*  949 */       Iterator itr = additionalNamespaces.iterator();
/*  950 */       while (itr.hasNext()) {
/*  951 */         ns = itr.next();
/*  952 */         String prefix = ns.getPrefix();
/*  953 */         String uri = namespaces.getURI(prefix);
/*  954 */         if (!ns.getURI().equals(uri)) {
/*  955 */           namespaces.push(ns);
/*  956 */           nsAtts = addNsAttribute(nsAtts, ns);
/*      */           try {
/*  958 */             this.contentHandler.startPrefixMapping(prefix, ns.getURI());
/*      */           }
/*  960 */           catch (SAXException se) {
/*  961 */             throw new JDOMException("Exception in startPrefixMapping", se);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  967 */     return nsAtts;
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
/*      */   private void endPrefixMapping(NamespaceStack namespaces, int previouslyDeclaredNamespaces) throws JDOMException {
/*  984 */     while (namespaces.size() > previouslyDeclaredNamespaces) {
/*  985 */       String prefix = namespaces.pop();
/*      */       try {
/*  987 */         this.contentHandler.endPrefixMapping(prefix);
/*      */       }
/*  989 */       catch (SAXException se) {
/*  990 */         throw new JDOMException("Exception in endPrefixMapping", se);
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
/*      */ 
/*      */   
/*      */   private void startElement(Element element, Attributes nsAtts) throws JDOMException {
/* 1007 */     String namespaceURI = element.getNamespaceURI();
/* 1008 */     String localName = element.getName();
/* 1009 */     String rawName = element.getQualifiedName();
/*      */ 
/*      */     
/* 1012 */     AttributesImpl atts = (nsAtts != null) ? new AttributesImpl(nsAtts) : new AttributesImpl();
/*      */ 
/*      */     
/* 1015 */     List attributes = element.getAttributes();
/* 1016 */     Iterator i = attributes.iterator();
/* 1017 */     while (i.hasNext()) {
/* 1018 */       Attribute a = i.next();
/* 1019 */       atts.addAttribute(a.getNamespaceURI(), a.getName(), a.getQualifiedName(), getAttributeTypeName(a.getAttributeType()), a.getValue());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1027 */       this.contentHandler.startElement(namespaceURI, localName, rawName, atts);
/*      */     }
/* 1029 */     catch (SAXException se) {
/* 1030 */       throw new JDOMException("Exception in startElement", se);
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
/*      */   private void endElement(Element element) throws JDOMException {
/* 1043 */     String namespaceURI = element.getNamespaceURI();
/* 1044 */     String localName = element.getName();
/* 1045 */     String rawName = element.getQualifiedName();
/*      */     
/*      */     try {
/* 1048 */       this.contentHandler.endElement(namespaceURI, localName, rawName);
/*      */     }
/* 1050 */     catch (SAXException se) {
/* 1051 */       throw new JDOMException("Exception in endElement", se);
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
/*      */   private void elementContent(List content, NamespaceStack namespaces) throws JDOMException {
/* 1065 */     for (Iterator i = content.iterator(); i.hasNext(); ) {
/* 1066 */       Object obj = i.next();
/*      */       
/* 1068 */       if (obj instanceof Content) {
/* 1069 */         elementContent((Content)obj, namespaces);
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1075 */       handleError(new JDOMException("Invalid element content: " + obj));
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
/*      */   private void elementContent(Content node, NamespaceStack namespaces) throws JDOMException {
/* 1092 */     if (this.locator != null) {
/* 1093 */       this.locator.setNode(node);
/*      */     }
/*      */     
/* 1096 */     if (node instanceof Element) {
/* 1097 */       element((Element)node, namespaces);
/*      */     }
/* 1099 */     else if (node instanceof CDATA) {
/* 1100 */       cdata(((CDATA)node).getText());
/*      */     }
/* 1102 */     else if (node instanceof Text) {
/*      */       
/* 1104 */       characters(((Text)node).getText());
/*      */     }
/* 1106 */     else if (node instanceof ProcessingInstruction) {
/*      */       
/* 1108 */       processingInstruction((ProcessingInstruction)node);
/*      */     }
/* 1110 */     else if (node instanceof Comment) {
/*      */       
/* 1112 */       comment(((Comment)node).getText());
/*      */     }
/* 1114 */     else if (node instanceof EntityRef) {
/*      */       
/* 1116 */       entityRef((EntityRef)node);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1122 */       handleError(new JDOMException("Invalid element content: " + node));
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
/*      */   private void cdata(String cdataText) throws JDOMException {
/*      */     try {
/* 1135 */       if (this.lexicalHandler != null) {
/* 1136 */         this.lexicalHandler.startCDATA();
/* 1137 */         characters(cdataText);
/* 1138 */         this.lexicalHandler.endCDATA();
/*      */       } else {
/*      */         
/* 1141 */         characters(cdataText);
/*      */       }
/*      */     
/* 1144 */     } catch (SAXException se) {
/* 1145 */       throw new JDOMException("Exception in CDATA", se);
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
/*      */   private void characters(String elementText) throws JDOMException {
/* 1157 */     char[] c = elementText.toCharArray();
/*      */     try {
/* 1159 */       this.contentHandler.characters(c, 0, c.length);
/*      */     }
/* 1161 */     catch (SAXException se) {
/* 1162 */       throw new JDOMException("Exception in characters", se);
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
/*      */   private void comment(String commentText) throws JDOMException {
/* 1174 */     if (this.lexicalHandler != null) {
/* 1175 */       char[] c = commentText.toCharArray();
/*      */       try {
/* 1177 */         this.lexicalHandler.comment(c, 0, c.length);
/* 1178 */       } catch (SAXException se) {
/* 1179 */         throw new JDOMException("Exception in comment", se);
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
/*      */   private void entityRef(EntityRef entity) throws JDOMException {
/* 1193 */     if (entity != null) {
/*      */       
/*      */       try {
/*      */         
/* 1197 */         this.contentHandler.skippedEntity(entity.getName());
/*      */       }
/* 1199 */       catch (SAXException se) {
/* 1200 */         throw new JDOMException("Exception in entityRef", se);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributesImpl addNsAttribute(AttributesImpl atts, Namespace ns) {
/* 1218 */     if (this.declareNamespaces) {
/* 1219 */       if (atts == null) {
/* 1220 */         atts = new AttributesImpl();
/*      */       }
/*      */       
/* 1223 */       String prefix = ns.getPrefix();
/* 1224 */       if (prefix.equals("")) {
/* 1225 */         atts.addAttribute("", "", "xmlns", "CDATA", ns.getURI());
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1232 */         atts.addAttribute("", "", "xmlns:" + ns.getPrefix(), "CDATA", ns.getURI());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     return atts;
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
/*      */   private static String getAttributeTypeName(int type) {
/* 1256 */     if (type < 0 || type >= attrTypeToNameMap.length) {
/* 1257 */       type = 0;
/*      */     }
/* 1259 */     return attrTypeToNameMap[type];
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
/*      */   private void handleError(JDOMException exception) throws JDOMException {
/* 1279 */     if (this.errorHandler != null) {
/*      */       try {
/* 1281 */         this.errorHandler.error(new SAXParseException(exception.getMessage(), null, (Exception)exception));
/*      */       
/*      */       }
/* 1284 */       catch (SAXException se) {
/* 1285 */         if (se.getException() instanceof JDOMException) {
/* 1286 */           throw (JDOMException)se.getException();
/*      */         }
/*      */         
/* 1289 */         throw new JDOMException(se.getMessage(), se);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1294 */       throw exception;
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
/*      */   protected XMLReader createParser() throws Exception {
/* 1308 */     XMLReader parser = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1315 */       Class factoryClass = Class.forName("javax.xml.parsers.SAXParserFactory");
/*      */ 
/*      */ 
/*      */       
/* 1319 */       Method newParserInstance = factoryClass.getMethod("newInstance", null);
/*      */       
/* 1321 */       Object factory = newParserInstance.invoke(null, null);
/*      */ 
/*      */       
/* 1324 */       Method newSAXParser = factoryClass.getMethod("newSAXParser", null);
/* 1325 */       Object jaxpParser = newSAXParser.invoke(factory, null);
/*      */ 
/*      */       
/* 1328 */       Class parserClass = jaxpParser.getClass();
/* 1329 */       Method getXMLReader = parserClass.getMethod("getXMLReader", null);
/*      */       
/* 1331 */       parser = (XMLReader)getXMLReader.invoke(jaxpParser, null);
/* 1332 */     } catch (ClassNotFoundException e) {
/*      */     
/* 1334 */     } catch (InvocationTargetException e) {
/*      */     
/* 1336 */     } catch (NoSuchMethodException e) {
/*      */     
/* 1338 */     } catch (IllegalAccessException e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1344 */     if (parser == null) {
/* 1345 */       parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
/*      */     }
/*      */     
/* 1348 */     return parser;
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
/*      */   private XMLReader createDTDParser() throws JDOMException {
/* 1363 */     XMLReader parser = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1368 */       parser = createParser();
/*      */     }
/* 1370 */     catch (Exception ex1) {
/* 1371 */       throw new JDOMException("Error in SAX parser allocation", ex1);
/*      */     } 
/*      */ 
/*      */     
/* 1375 */     if (getDTDHandler() != null) {
/* 1376 */       parser.setDTDHandler(getDTDHandler());
/*      */     }
/* 1378 */     if (getEntityResolver() != null) {
/* 1379 */       parser.setEntityResolver(getEntityResolver());
/*      */     }
/* 1381 */     if (getLexicalHandler() != null) {
/*      */       try {
/* 1383 */         parser.setProperty("http://xml.org/sax/properties/lexical-handler", getLexicalHandler());
/*      */       
/*      */       }
/* 1386 */       catch (SAXException ex1) {
/*      */         try {
/* 1388 */           parser.setProperty("http://xml.org/sax/handlers/LexicalHandler", getLexicalHandler());
/*      */         }
/* 1390 */         catch (SAXException ex2) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1395 */     if (getDeclHandler() != null) {
/*      */       try {
/* 1397 */         parser.setProperty("http://xml.org/sax/properties/declaration-handler", getDeclHandler());
/*      */       
/*      */       }
/* 1400 */       catch (SAXException ex1) {
/*      */         try {
/* 1402 */           parser.setProperty("http://xml.org/sax/handlers/DeclHandler", getDeclHandler());
/*      */         }
/* 1404 */         catch (SAXException ex2) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     parser.setErrorHandler(new DefaultHandler());
/*      */     
/* 1413 */     return parser;
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
/*      */   public JDOMLocator getLocator() {
/* 1437 */     return (this.locator != null) ? new JDOMLocator(this.locator) : null;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/output/SAXOutputter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */