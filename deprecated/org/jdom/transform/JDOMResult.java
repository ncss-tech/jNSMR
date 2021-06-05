/*     */ package org.jdom.transform;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import org.jdom.DefaultJDOMFactory;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMFactory;
/*     */ import org.jdom.input.SAXHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
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
/*     */ public class JDOMResult
/*     */   extends SAXResult
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: JDOMResult.java,v $ $Revision: 1.24 $ $Date: 2007/11/10 05:29:02 $ $Name: jdom_1_1 $";
/*     */   public static final String JDOM_FEATURE = "http://org.jdom.transform.JDOMResult/feature";
/* 126 */   private Object result = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean queried = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private JDOMFactory factory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JDOMResult() {
/* 145 */     DocumentBuilder builder = new DocumentBuilder(this);
/*     */ 
/*     */     
/* 148 */     super.setHandler(builder);
/* 149 */     super.setLexicalHandler(builder);
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
/*     */   public void setResult(List result) {
/* 169 */     this.result = result;
/* 170 */     this.queried = false;
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
/*     */   public List getResult() {
/* 186 */     List nodes = Collections.EMPTY_LIST;
/*     */ 
/*     */     
/* 189 */     retrieveResult();
/*     */     
/* 191 */     if (this.result instanceof List) {
/* 192 */       nodes = (List)this.result;
/*     */     
/*     */     }
/* 195 */     else if (this.result instanceof Document && !this.queried) {
/* 196 */       List content = ((Document)this.result).getContent();
/* 197 */       nodes = new ArrayList(content.size());
/*     */       
/* 199 */       while (content.size() != 0) {
/*     */         
/* 201 */         Object o = content.remove(0);
/* 202 */         nodes.add(o);
/*     */       } 
/* 204 */       this.result = nodes;
/*     */     } 
/*     */     
/* 207 */     this.queried = true;
/*     */     
/* 209 */     return nodes;
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
/*     */   public void setDocument(Document document) {
/* 228 */     this.result = document;
/* 229 */     this.queried = false;
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
/*     */   public Document getDocument() {
/* 253 */     Document doc = null;
/*     */ 
/*     */     
/* 256 */     retrieveResult();
/*     */     
/* 258 */     if (this.result instanceof Document) {
/* 259 */       doc = (Document)this.result;
/*     */     
/*     */     }
/* 262 */     else if (this.result instanceof List && !this.queried) {
/*     */       try {
/*     */         DefaultJDOMFactory defaultJDOMFactory;
/* 265 */         JDOMFactory f = getFactory();
/* 266 */         if (f == null) defaultJDOMFactory = new DefaultJDOMFactory();
/*     */         
/* 268 */         doc = defaultJDOMFactory.document(null);
/* 269 */         doc.setContent((List)this.result);
/*     */         
/* 271 */         this.result = doc;
/*     */       }
/* 273 */       catch (RuntimeException ex1) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     this.queried = true;
/*     */     
/* 281 */     return doc;
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
/*     */   public void setFactory(JDOMFactory factory) {
/* 296 */     this.factory = factory;
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
/*     */   public JDOMFactory getFactory() {
/* 310 */     return this.factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void retrieveResult() {
/* 318 */     if (this.result == null) {
/* 319 */       setResult(((DocumentBuilder)getHandler()).getResult());
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
/*     */   public void setHandler(ContentHandler handler) {}
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
/*     */   public void setLexicalHandler(LexicalHandler handler) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FragmentHandler
/*     */     extends SAXHandler
/*     */   {
/* 356 */     private Element dummyRoot = new Element("root", null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FragmentHandler(JDOMFactory factory) {
/* 362 */       super(factory);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 367 */       pushElement(this.dummyRoot);
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
/*     */     public List getResult() {
/*     */       try {
/* 380 */         flushCharacters();
/*     */       }
/* 382 */       catch (SAXException e) {}
/* 383 */       return getDetachedContent(this.dummyRoot);
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
/*     */     private List getDetachedContent(Element elt) {
/* 395 */       List content = elt.getContent();
/* 396 */       List nodes = new ArrayList(content.size());
/*     */       
/* 398 */       while (content.size() != 0) {
/*     */         
/* 400 */         Object o = content.remove(0);
/* 401 */         nodes.add(o);
/*     */       } 
/* 403 */       return nodes;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class DocumentBuilder
/*     */     extends XMLFilterImpl
/*     */     implements LexicalHandler
/*     */   {
/*     */     private JDOMResult.FragmentHandler saxHandler;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean startDocumentReceived;
/*     */ 
/*     */ 
/*     */     
/*     */     private final JDOMResult this$0;
/*     */ 
/*     */ 
/*     */     
/*     */     public DocumentBuilder(JDOMResult this$0) {
/* 427 */       this.this$0 = this$0;
/*     */       this.saxHandler = null;
/*     */       this.startDocumentReceived = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List getResult() {
/* 438 */       List result = null;
/*     */       
/* 440 */       if (this.saxHandler != null) {
/*     */         
/* 442 */         result = this.saxHandler.getResult();
/*     */ 
/*     */         
/* 445 */         this.saxHandler = null;
/*     */ 
/*     */         
/* 448 */         this.startDocumentReceived = false;
/*     */       } 
/* 450 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void ensureInitialization() throws SAXException {
/* 456 */       if (!this.startDocumentReceived) {
/* 457 */         startDocument();
/*     */       }
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
/*     */     public void startDocument() throws SAXException {
/* 476 */       this.startDocumentReceived = true;
/*     */ 
/*     */       
/* 479 */       this.this$0.setResult(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       this.saxHandler = new JDOMResult.FragmentHandler(this.this$0.getFactory());
/* 486 */       setContentHandler((ContentHandler)this.saxHandler);
/*     */ 
/*     */       
/* 489 */       super.startDocument();
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
/*     */     public void startElement(String nsURI, String localName, String qName, Attributes atts) throws SAXException {
/* 518 */       ensureInitialization();
/* 519 */       super.startElement(nsURI, localName, qName, atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 528 */       ensureInitialization();
/* 529 */       super.startPrefixMapping(prefix, uri);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void characters(char[] ch, int start, int length) throws SAXException {
/* 538 */       ensureInitialization();
/* 539 */       super.characters(ch, start, length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 548 */       ensureInitialization();
/* 549 */       super.ignorableWhitespace(ch, start, length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void processingInstruction(String target, String data) throws SAXException {
/* 558 */       ensureInitialization();
/* 559 */       super.processingInstruction(target, data);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void skippedEntity(String name) throws SAXException {
/* 567 */       ensureInitialization();
/* 568 */       super.skippedEntity(name);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 591 */       ensureInitialization();
/* 592 */       this.saxHandler.startDTD(name, publicId, systemId);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endDTD() throws SAXException {
/* 602 */       this.saxHandler.endDTD();
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
/*     */     public void startEntity(String name) throws SAXException {
/* 616 */       ensureInitialization();
/* 617 */       this.saxHandler.startEntity(name);
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
/*     */     public void endEntity(String name) throws SAXException {
/* 629 */       this.saxHandler.endEntity(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startCDATA() throws SAXException {
/* 639 */       ensureInitialization();
/* 640 */       this.saxHandler.startCDATA();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endCDATA() throws SAXException {
/* 650 */       this.saxHandler.endCDATA();
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
/*     */     public void comment(char[] ch, int start, int length) throws SAXException {
/* 665 */       ensureInitialization();
/* 666 */       this.saxHandler.comment(ch, start, length);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/transform/JDOMResult.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */