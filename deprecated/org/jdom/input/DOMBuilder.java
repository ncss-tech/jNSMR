/*     */ package org.jdom.input;
/*     */ 
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Content;
/*     */ import org.jdom.DefaultJDOMFactory;
/*     */ import org.jdom.DocType;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.EntityRef;
/*     */ import org.jdom.JDOMFactory;
/*     */ import org.jdom.Namespace;
/*     */ import org.jdom.Parent;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMBuilder
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: DOMBuilder.java,v $ $Revision: 1.60 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1 $";
/*     */   private String adapterClass;
/*  87 */   private JDOMFactory factory = (JDOMFactory)new DefaultJDOMFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMBuilder(String adapterClass) {
/* 106 */     this.adapterClass = adapterClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFactory(JDOMFactory factory) {
/* 116 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JDOMFactory getFactory() {
/* 124 */     return this.factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document build(Document domDocument) {
/* 134 */     Document doc = this.factory.document(null);
/* 135 */     buildTree(domDocument, doc, null, true);
/* 136 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element build(Element domElement) {
/* 146 */     Document doc = this.factory.document(null);
/* 147 */     buildTree(domElement, doc, null, true);
/* 148 */     return doc.getRootElement(); } private void buildTree(Node node, Document doc, Element current, boolean atRoot) { NodeList nodes; int i; String nodeName; int size; String prefix;
/*     */     String localName;
/*     */     int colon;
/*     */     Namespace ns;
/*     */     String uri;
/*     */     Element element;
/*     */     NamedNodeMap attributeList;
/*     */     int attsize;
/*     */     int j;
/*     */     NodeList children;
/*     */     String data;
/*     */     String cdata;
/*     */     EntityRef entity;
/*     */     DocumentType domDocType;
/*     */     String publicID;
/*     */     String systemID;
/*     */     String internalDTD;
/*     */     DocType docType;
/* 166 */     switch (node.getNodeType()) {
/*     */       case 9:
/* 168 */         nodes = node.getChildNodes();
/* 169 */         for (i = 0, size = nodes.getLength(); i < size; i++) {
/* 170 */           buildTree(nodes.item(i), doc, current, true);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 1:
/* 175 */         nodeName = node.getNodeName();
/* 176 */         prefix = "";
/* 177 */         localName = nodeName;
/* 178 */         colon = nodeName.indexOf(':');
/* 179 */         if (colon >= 0) {
/* 180 */           prefix = nodeName.substring(0, colon);
/* 181 */           localName = nodeName.substring(colon + 1);
/*     */         } 
/*     */ 
/*     */         
/* 185 */         ns = null;
/* 186 */         uri = node.getNamespaceURI();
/* 187 */         if (uri == null) {
/* 188 */           ns = (current == null) ? Namespace.NO_NAMESPACE : current.getNamespace(prefix);
/*     */         }
/*     */         else {
/*     */           
/* 192 */           ns = Namespace.getNamespace(prefix, uri);
/*     */         } 
/*     */         
/* 195 */         element = this.factory.element(localName, ns);
/*     */         
/* 197 */         if (atRoot) {
/*     */           
/* 199 */           doc.setRootElement(element);
/*     */         } else {
/*     */           
/* 202 */           this.factory.addContent((Parent)current, (Content)element);
/*     */         } 
/*     */ 
/*     */         
/* 206 */         attributeList = node.getAttributes();
/* 207 */         attsize = attributeList.getLength();
/*     */         
/* 209 */         for (j = 0; j < attsize; j++) {
/* 210 */           Attr att = (Attr)attributeList.item(j);
/*     */           
/* 212 */           String attname = att.getName();
/* 213 */           if (attname.startsWith("xmlns")) {
/* 214 */             String attPrefix = "";
/* 215 */             colon = attname.indexOf(':');
/* 216 */             if (colon >= 0) {
/* 217 */               attPrefix = attname.substring(colon + 1);
/*     */             }
/*     */             
/* 220 */             String attvalue = att.getValue();
/*     */             
/* 222 */             Namespace declaredNS = Namespace.getNamespace(attPrefix, attvalue);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 230 */             if (prefix.equals(attPrefix)) {
/* 231 */               element.setNamespace(declaredNS);
/*     */             } else {
/*     */               
/* 234 */               this.factory.addNamespaceDeclaration(element, declaredNS);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 240 */         for (j = 0; j < attsize; j++) {
/* 241 */           Attr att = (Attr)attributeList.item(j);
/*     */           
/* 243 */           String attname = att.getName();
/*     */           
/* 245 */           if (!attname.startsWith("xmlns")) {
/* 246 */             String attPrefix = "";
/* 247 */             String attLocalName = attname;
/* 248 */             colon = attname.indexOf(':');
/* 249 */             if (colon >= 0) {
/* 250 */               attPrefix = attname.substring(0, colon);
/* 251 */               attLocalName = attname.substring(colon + 1);
/*     */             } 
/*     */             
/* 254 */             String attvalue = att.getValue();
/*     */ 
/*     */             
/* 257 */             Namespace attns = null;
/* 258 */             if ("".equals(attPrefix)) {
/* 259 */               attns = Namespace.NO_NAMESPACE;
/*     */             } else {
/*     */               
/* 262 */               attns = element.getNamespace(attPrefix);
/*     */             } 
/*     */             
/* 265 */             Attribute attribute = this.factory.attribute(attLocalName, attvalue, attns);
/*     */             
/* 267 */             this.factory.setAttribute(element, attribute);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 274 */         children = node.getChildNodes();
/* 275 */         if (children != null) {
/* 276 */           int k = children.getLength();
/* 277 */           for (int m = 0; m < k; m++) {
/* 278 */             Node item = children.item(m);
/* 279 */             if (item != null) {
/* 280 */               buildTree(item, doc, element, false);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 3:
/* 287 */         data = node.getNodeValue();
/* 288 */         this.factory.addContent((Parent)current, (Content)this.factory.text(data));
/*     */         break;
/*     */       
/*     */       case 4:
/* 292 */         cdata = node.getNodeValue();
/* 293 */         this.factory.addContent((Parent)current, (Content)this.factory.cdata(cdata));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 298 */         if (atRoot) {
/* 299 */           this.factory.addContent((Parent)doc, (Content)this.factory.processingInstruction(node.getNodeName(), node.getNodeValue()));
/*     */           
/*     */           break;
/*     */         } 
/* 303 */         this.factory.addContent((Parent)current, (Content)this.factory.processingInstruction(node.getNodeName(), node.getNodeValue()));
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 310 */         if (atRoot) {
/* 311 */           this.factory.addContent((Parent)doc, (Content)this.factory.comment(node.getNodeValue())); break;
/*     */         } 
/* 313 */         this.factory.addContent((Parent)current, (Content)this.factory.comment(node.getNodeValue()));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 318 */         entity = this.factory.entityRef(node.getNodeName());
/* 319 */         this.factory.addContent((Parent)current, (Content)entity);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 327 */         domDocType = (DocumentType)node;
/* 328 */         publicID = domDocType.getPublicId();
/* 329 */         systemID = domDocType.getSystemId();
/* 330 */         internalDTD = domDocType.getInternalSubset();
/*     */         
/* 332 */         docType = this.factory.docType(domDocType.getName());
/* 333 */         docType.setPublicID(publicID);
/* 334 */         docType.setSystemID(systemID);
/* 335 */         docType.setInternalSubset(internalDTD);
/*     */         
/* 337 */         this.factory.addContent((Parent)doc, (Content)docType);
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/input/DOMBuilder.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */