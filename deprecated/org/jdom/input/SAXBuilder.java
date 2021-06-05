/*      */ package org.jdom.input;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import org.jdom.DefaultJDOMFactory;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.JDOMException;
/*      */ import org.jdom.JDOMFactory;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.XMLFilter;
/*      */ import org.xml.sax.XMLReader;
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
/*      */ public class SAXBuilder
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: SAXBuilder.java,v $ $Revision: 1.92 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1 $";
/*      */   private static final String DEFAULT_SAX_DRIVER = "org.apache.xerces.parsers.SAXParser";
/*      */   private boolean validate;
/*      */   private boolean expand = true;
/*      */   private String saxDriverClass;
/*  111 */   private ErrorHandler saxErrorHandler = null;
/*      */ 
/*      */   
/*  114 */   private EntityResolver saxEntityResolver = null;
/*      */ 
/*      */   
/*  117 */   private DTDHandler saxDTDHandler = null;
/*      */ 
/*      */   
/*  120 */   private XMLFilter saxXMLFilter = null;
/*      */ 
/*      */   
/*  123 */   private JDOMFactory factory = (JDOMFactory)new DefaultJDOMFactory();
/*      */ 
/*      */   
/*      */   private boolean ignoringWhite = false;
/*      */ 
/*      */   
/*      */   private boolean ignoringBoundaryWhite = false;
/*      */ 
/*      */   
/*  132 */   private HashMap features = new HashMap(5);
/*      */ 
/*      */   
/*  135 */   private HashMap properties = new HashMap(5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reuseParser = true;
/*      */ 
/*      */ 
/*      */   
/*  144 */   private XMLReader saxParser = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder() {
/*  152 */     this(false);
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
/*      */   public SAXBuilder(boolean validate) {
/*  165 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder(String saxDriverClass) {
/*  176 */     this(saxDriverClass, false);
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
/*      */   public SAXBuilder(String saxDriverClass, boolean validate) {
/*  190 */     this.saxDriverClass = saxDriverClass;
/*  191 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDriverClass() {
/*  200 */     return this.saxDriverClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JDOMFactory getFactory() {
/*  208 */     return this.factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFactory(JDOMFactory factory) {
/*  218 */     this.factory = factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getValidation() {
/*  227 */     return this.validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidation(boolean validate) {
/*  237 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  245 */     return this.saxErrorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  254 */     this.saxErrorHandler = errorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  263 */     return this.saxEntityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver entityResolver) {
/*  272 */     this.saxEntityResolver = entityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  281 */     return this.saxDTDHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDHandler(DTDHandler dtdHandler) {
/*  290 */     this.saxDTDHandler = dtdHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLFilter getXMLFilter() {
/*  299 */     return this.saxXMLFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXMLFilter(XMLFilter xmlFilter) {
/*  308 */     this.saxXMLFilter = xmlFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIgnoringElementContentWhitespace() {
/*  319 */     return this.ignoringWhite;
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
/*  334 */     this.ignoringWhite = ignoringWhite;
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
/*  347 */     return this.ignoringBoundaryWhite;
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
/*      */   public void setIgnoringBoundaryWhitespace(boolean ignoringBoundaryWhite) {
/*  367 */     this.ignoringBoundaryWhite = ignoringBoundaryWhite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReuseParser() {
/*  378 */     return this.reuseParser;
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
/*      */   public void setReuseParser(boolean reuseParser) {
/*  394 */     this.reuseParser = reuseParser;
/*  395 */     this.saxParser = null;
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
/*      */   public void setFeature(String name, boolean value) {
/*  415 */     this.features.put(name, new Boolean(value));
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
/*      */   public void setProperty(String name, Object value) {
/*  435 */     this.properties.put(name, value);
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
/*      */   public Document build(InputSource in) throws JDOMException, IOException {
/*  450 */     SAXHandler contentHandler = null;
/*      */ 
/*      */     
/*      */     try {
/*  454 */       contentHandler = createContentHandler();
/*  455 */       configureContentHandler(contentHandler);
/*      */       
/*  457 */       XMLReader parser = this.saxParser;
/*  458 */       if (parser == null) {
/*      */         
/*  460 */         parser = createParser();
/*      */ 
/*      */         
/*  463 */         if (this.saxXMLFilter != null) {
/*      */           
/*  465 */           XMLFilter root = this.saxXMLFilter;
/*  466 */           while (root.getParent() instanceof XMLFilter) {
/*  467 */             root = (XMLFilter)root.getParent();
/*      */           }
/*  469 */           root.setParent(parser);
/*      */ 
/*      */           
/*  472 */           parser = this.saxXMLFilter;
/*      */         } 
/*      */ 
/*      */         
/*  476 */         configureParser(parser, contentHandler);
/*      */         
/*  478 */         if (this.reuseParser == true) {
/*  479 */           this.saxParser = parser;
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  485 */         configureParser(parser, contentHandler);
/*      */       } 
/*      */ 
/*      */       
/*  489 */       parser.parse(in);
/*      */       
/*  491 */       return contentHandler.getDocument();
/*      */     }
/*  493 */     catch (SAXParseException e) {
/*  494 */       Document doc = contentHandler.getDocument();
/*  495 */       if (!doc.hasRootElement()) {
/*  496 */         doc = null;
/*      */       }
/*      */       
/*  499 */       String systemId = e.getSystemId();
/*  500 */       if (systemId != null) {
/*  501 */         throw new JDOMParseException("Error on line " + e.getLineNumber() + " of document " + systemId, e, doc);
/*      */       }
/*      */       
/*  504 */       throw new JDOMParseException("Error on line " + e.getLineNumber(), e, doc);
/*      */ 
/*      */     
/*      */     }
/*  508 */     catch (SAXException e) {
/*  509 */       throw new JDOMParseException("Error in building: " + e.getMessage(), e, contentHandler.getDocument());
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  516 */       contentHandler = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SAXHandler createContentHandler() {
/*  526 */     SAXHandler contentHandler = new SAXHandler(this.factory);
/*  527 */     return contentHandler;
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
/*      */   protected void configureContentHandler(SAXHandler contentHandler) {
/*  540 */     contentHandler.setExpandEntities(this.expand);
/*  541 */     contentHandler.setIgnoringElementContentWhitespace(this.ignoringWhite);
/*  542 */     contentHandler.setIgnoringBoundaryWhitespace(this.ignoringBoundaryWhite);
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
/*      */   protected XMLReader createParser() throws JDOMException {
/*  558 */     XMLReader parser = null;
/*  559 */     if (this.saxDriverClass != null) {
/*      */       
/*      */       try {
/*  562 */         parser = XMLReaderFactory.createXMLReader(this.saxDriverClass);
/*      */ 
/*      */         
/*  565 */         setFeaturesAndProperties(parser, true);
/*      */       }
/*  567 */       catch (SAXException e) {
/*  568 */         throw new JDOMException("Could not load " + this.saxDriverClass, e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/*  577 */         Class factoryClass = Class.forName("org.jdom.input.JAXPParserFactory");
/*      */ 
/*      */         
/*  580 */         Method createParser = factoryClass.getMethod("createParser", new Class[] { boolean.class, Map.class, Map.class });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  585 */         parser = (XMLReader)createParser.invoke(null, new Object[] { new Boolean(this.validate), this.features, this.properties });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  590 */         setFeaturesAndProperties(parser, false);
/*      */       }
/*  592 */       catch (JDOMException e) {
/*  593 */         throw e;
/*      */       }
/*  595 */       catch (NoClassDefFoundError e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  601 */       catch (Exception e) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (parser == null) {
/*      */       try {
/*  610 */         parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
/*      */         
/*  612 */         this.saxDriverClass = parser.getClass().getName();
/*      */ 
/*      */         
/*  615 */         setFeaturesAndProperties(parser, true);
/*      */       }
/*  617 */       catch (SAXException e) {
/*  618 */         throw new JDOMException("Could not load default SAX parser: org.apache.xerces.parsers.SAXParser", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  623 */     return parser;
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
/*      */   protected void configureParser(XMLReader parser, SAXHandler contentHandler) throws JDOMException {
/*  642 */     parser.setContentHandler(contentHandler);
/*      */     
/*  644 */     if (this.saxEntityResolver != null) {
/*  645 */       parser.setEntityResolver(this.saxEntityResolver);
/*      */     }
/*      */     
/*  648 */     if (this.saxDTDHandler != null) {
/*  649 */       parser.setDTDHandler(this.saxDTDHandler);
/*      */     } else {
/*  651 */       parser.setDTDHandler(contentHandler);
/*      */     } 
/*      */     
/*  654 */     if (this.saxErrorHandler != null) {
/*  655 */       parser.setErrorHandler(this.saxErrorHandler);
/*      */     } else {
/*  657 */       parser.setErrorHandler(new BuilderErrorHandler());
/*      */     } 
/*      */ 
/*      */     
/*  661 */     boolean lexicalReporting = false;
/*      */     try {
/*  663 */       parser.setProperty("http://xml.org/sax/handlers/LexicalHandler", contentHandler);
/*      */       
/*  665 */       lexicalReporting = true;
/*  666 */     } catch (SAXNotSupportedException e) {
/*      */     
/*  668 */     } catch (SAXNotRecognizedException e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  673 */     if (!lexicalReporting) {
/*      */       try {
/*  675 */         parser.setProperty("http://xml.org/sax/properties/lexical-handler", contentHandler);
/*      */ 
/*      */         
/*  678 */         lexicalReporting = true;
/*  679 */       } catch (SAXNotSupportedException e) {
/*      */       
/*  681 */       } catch (SAXNotRecognizedException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  687 */     if (!this.expand) {
/*      */       try {
/*  689 */         parser.setProperty("http://xml.org/sax/properties/declaration-handler", contentHandler);
/*      */       
/*      */       }
/*  692 */       catch (SAXNotSupportedException e) {
/*      */       
/*  694 */       } catch (SAXNotRecognizedException e) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFeaturesAndProperties(XMLReader parser, boolean coreFeatures) throws JDOMException {
/*  704 */     Iterator iter = this.features.keySet().iterator();
/*  705 */     while (iter.hasNext()) {
/*  706 */       String name = iter.next();
/*  707 */       Boolean value = (Boolean)this.features.get(name);
/*  708 */       internalSetFeature(parser, name, value.booleanValue(), name);
/*      */     } 
/*      */ 
/*      */     
/*  712 */     iter = this.properties.keySet().iterator();
/*  713 */     while (iter.hasNext()) {
/*  714 */       String name = iter.next();
/*  715 */       internalSetProperty(parser, name, this.properties.get(name), name);
/*      */     } 
/*      */     
/*  718 */     if (coreFeatures) {
/*      */       
/*      */       try {
/*  721 */         internalSetFeature(parser, "http://xml.org/sax/features/validation", this.validate, "Validation");
/*      */       
/*      */       }
/*  724 */       catch (JDOMException e) {
/*      */ 
/*      */ 
/*      */         
/*  728 */         if (this.validate) {
/*  729 */           throw e;
/*      */         }
/*      */       } 
/*      */       
/*  733 */       internalSetFeature(parser, "http://xml.org/sax/features/namespaces", true, "Namespaces");
/*      */ 
/*      */       
/*  736 */       internalSetFeature(parser, "http://xml.org/sax/features/namespace-prefixes", true, "Namespace prefixes");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  749 */     try { if (parser.getFeature("http://xml.org/sax/features/external-general-entities") != this.expand) {
/*  750 */         parser.setFeature("http://xml.org/sax/features/external-general-entities", this.expand);
/*      */       } }
/*      */     
/*  753 */     catch (SAXNotRecognizedException e) {  }
/*  754 */     catch (SAXNotSupportedException e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void internalSetFeature(XMLReader parser, String feature, boolean value, String displayName) throws JDOMException {
/*      */     try {
/*  764 */       parser.setFeature(feature, value);
/*  765 */     } catch (SAXNotSupportedException e) {
/*  766 */       throw new JDOMException(displayName + " feature not supported for SAX driver " + parser.getClass().getName());
/*      */     }
/*  768 */     catch (SAXNotRecognizedException e) {
/*  769 */       throw new JDOMException(displayName + " feature not recognized for SAX driver " + parser.getClass().getName());
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
/*      */   private void internalSetProperty(XMLReader parser, String property, Object value, String displayName) throws JDOMException {
/*      */     try {
/*  783 */       parser.setProperty(property, value);
/*  784 */     } catch (SAXNotSupportedException e) {
/*  785 */       throw new JDOMException(displayName + " property not supported for SAX driver " + parser.getClass().getName());
/*      */     }
/*  787 */     catch (SAXNotRecognizedException e) {
/*  788 */       throw new JDOMException(displayName + " property not recognized for SAX driver " + parser.getClass().getName());
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
/*      */   public Document build(InputStream in) throws JDOMException, IOException {
/*  807 */     return build(new InputSource(in));
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
/*      */   public Document build(File file) throws JDOMException, IOException {
/*      */     try {
/*  825 */       URL url = fileToURL(file);
/*  826 */       return build(url);
/*  827 */     } catch (MalformedURLException e) {
/*  828 */       throw new JDOMException("Error in building", e);
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
/*      */   public Document build(URL url) throws JDOMException, IOException {
/*  846 */     String systemID = url.toExternalForm();
/*  847 */     return build(new InputSource(systemID));
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
/*      */   public Document build(InputStream in, String systemId) throws JDOMException, IOException {
/*  866 */     InputSource src = new InputSource(in);
/*  867 */     src.setSystemId(systemId);
/*  868 */     return build(src);
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
/*      */   public Document build(Reader characterStream) throws JDOMException, IOException {
/*  888 */     return build(new InputSource(characterStream));
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
/*      */   public Document build(Reader characterStream, String systemId) throws JDOMException, IOException {
/*  910 */     InputSource src = new InputSource(characterStream);
/*  911 */     src.setSystemId(systemId);
/*  912 */     return build(src);
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
/*      */   public Document build(String systemId) throws JDOMException, IOException {
/*  928 */     return build(new InputSource(systemId));
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
/*      */   private static URL fileToURL(File file) throws MalformedURLException {
/*  961 */     StringBuffer buffer = new StringBuffer();
/*  962 */     String path = file.getAbsolutePath();
/*      */ 
/*      */     
/*  965 */     if (File.separatorChar != '/') {
/*  966 */       path = path.replace(File.separatorChar, '/');
/*      */     }
/*      */ 
/*      */     
/*  970 */     if (!path.startsWith("/")) {
/*  971 */       buffer.append('/');
/*      */     }
/*      */ 
/*      */     
/*  975 */     int len = path.length();
/*  976 */     for (int i = 0; i < len; i++) {
/*  977 */       char c = path.charAt(i);
/*  978 */       if (c == ' ') {
/*  979 */         buffer.append("%20");
/*  980 */       } else if (c == '#') {
/*  981 */         buffer.append("%23");
/*  982 */       } else if (c == '%') {
/*  983 */         buffer.append("%25");
/*  984 */       } else if (c == '&') {
/*  985 */         buffer.append("%26");
/*  986 */       } else if (c == ';') {
/*  987 */         buffer.append("%3B");
/*  988 */       } else if (c == '<') {
/*  989 */         buffer.append("%3C");
/*  990 */       } else if (c == '=') {
/*  991 */         buffer.append("%3D");
/*  992 */       } else if (c == '>') {
/*  993 */         buffer.append("%3E");
/*  994 */       } else if (c == '?') {
/*  995 */         buffer.append("%3F");
/*  996 */       } else if (c == '~') {
/*  997 */         buffer.append("%7E");
/*      */       } else {
/*  999 */         buffer.append(c);
/*      */       } 
/*      */     } 
/*      */     
/* 1003 */     if (!path.endsWith("/") && file.isDirectory()) {
/* 1004 */       buffer.append('/');
/*      */     }
/*      */ 
/*      */     
/* 1008 */     return new URL("file", "", buffer.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExpandEntities() {
/* 1018 */     return this.expand;
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
/*      */   public void setExpandEntities(boolean expand) {
/* 1045 */     this.expand = expand;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/input/SAXBuilder.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */