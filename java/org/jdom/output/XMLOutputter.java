/*      */ package org.jdom.output;
/*      */ 
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.io.Writer;
/*      */ import java.util.List;
/*      */ import org.jdom.Attribute;
/*      */ import org.jdom.CDATA;
/*      */ import org.jdom.Comment;
/*      */ import org.jdom.DocType;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.Element;
/*      */ import org.jdom.EntityRef;
/*      */ import org.jdom.Namespace;
/*      */ import org.jdom.ProcessingInstruction;
/*      */ import org.jdom.Text;
/*      */ import org.jdom.Verifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLOutputter
/*      */   implements Cloneable
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: XMLOutputter.java,v $ $Revision: 1.116 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1 $";
/*  121 */   private Format userFormat = Format.getRawFormat();
/*      */ 
/*      */   
/*  124 */   protected static final Format preserveFormat = Format.getRawFormat();
/*      */ 
/*      */   
/*  127 */   protected Format currentFormat = this.userFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean escapeOutput = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLOutputter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLOutputter(Format format) {
/*  149 */     this.userFormat = (Format)format.clone();
/*  150 */     this.currentFormat = this.userFormat;
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
/*      */   public XMLOutputter(XMLOutputter that) {
/*  162 */     this.userFormat = (Format)that.userFormat.clone();
/*  163 */     this.currentFormat = this.userFormat;
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
/*      */   public void setFormat(Format newFormat) {
/*  176 */     this.userFormat = (Format)newFormat.clone();
/*  177 */     this.currentFormat = this.userFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Format getFormat() {
/*  185 */     return (Format)this.userFormat.clone();
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
/*      */   public void output(Document doc, OutputStream out) throws IOException {
/*  202 */     Writer writer = makeWriter(out);
/*  203 */     output(doc, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(DocType doctype, OutputStream out) throws IOException {
/*  213 */     Writer writer = makeWriter(out);
/*  214 */     output(doctype, writer);
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
/*      */   public void output(Element element, OutputStream out) throws IOException {
/*  226 */     Writer writer = makeWriter(out);
/*  227 */     output(element, writer);
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
/*      */   public void outputElementContent(Element element, OutputStream out) throws IOException {
/*  242 */     Writer writer = makeWriter(out);
/*  243 */     outputElementContent(element, writer);
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
/*      */   public void output(List list, OutputStream out) throws IOException {
/*  257 */     Writer writer = makeWriter(out);
/*  258 */     output(list, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(CDATA cdata, OutputStream out) throws IOException {
/*  268 */     Writer writer = makeWriter(out);
/*  269 */     output(cdata, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(Text text, OutputStream out) throws IOException {
/*  280 */     Writer writer = makeWriter(out);
/*  281 */     output(text, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(Comment comment, OutputStream out) throws IOException {
/*  291 */     Writer writer = makeWriter(out);
/*  292 */     output(comment, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(ProcessingInstruction pi, OutputStream out) throws IOException {
/*  303 */     Writer writer = makeWriter(out);
/*  304 */     output(pi, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(EntityRef entity, OutputStream out) throws IOException {
/*  314 */     Writer writer = makeWriter(out);
/*  315 */     output(entity, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Writer makeWriter(OutputStream out) throws UnsupportedEncodingException {
/*  324 */     return makeWriter(out, this.userFormat.encoding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Writer makeWriter(OutputStream out, String enc) throws UnsupportedEncodingException {
/*  334 */     if ("UTF-8".equals(enc)) {
/*  335 */       enc = "UTF8";
/*      */     }
/*      */     
/*  338 */     Writer writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(out), enc));
/*      */ 
/*      */ 
/*      */     
/*  342 */     return writer;
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
/*      */   public void output(Document doc, Writer out) throws IOException {
/*  364 */     printDeclaration(out, doc, this.userFormat.encoding);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  369 */     List content = doc.getContent();
/*  370 */     int size = content.size();
/*  371 */     for (int i = 0; i < size; i++) {
/*  372 */       Object obj = content.get(i);
/*      */       
/*  374 */       if (obj instanceof Element) {
/*  375 */         printElement(out, doc.getRootElement(), 0, createNamespaceStack());
/*      */       
/*      */       }
/*  378 */       else if (obj instanceof Comment) {
/*  379 */         printComment(out, (Comment)obj);
/*      */       }
/*  381 */       else if (obj instanceof ProcessingInstruction) {
/*  382 */         printProcessingInstruction(out, (ProcessingInstruction)obj);
/*      */       }
/*  384 */       else if (obj instanceof DocType) {
/*  385 */         printDocType(out, doc.getDocType());
/*      */ 
/*      */         
/*  388 */         out.write(this.currentFormat.lineSeparator);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  395 */       newline(out);
/*  396 */       indent(out, 0);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  401 */     out.write(this.currentFormat.lineSeparator);
/*      */     
/*  403 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(DocType doctype, Writer out) throws IOException {
/*  413 */     printDocType(out, doctype);
/*  414 */     out.flush();
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
/*      */   public void output(Element element, Writer out) throws IOException {
/*  428 */     printElement(out, element, 0, createNamespaceStack());
/*  429 */     out.flush();
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
/*      */   public void outputElementContent(Element element, Writer out) throws IOException {
/*  444 */     List content = element.getContent();
/*  445 */     printContentRange(out, content, 0, content.size(), 0, createNamespaceStack());
/*      */     
/*  447 */     out.flush();
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
/*      */   public void output(List list, Writer out) throws IOException {
/*  461 */     printContentRange(out, list, 0, list.size(), 0, createNamespaceStack());
/*      */     
/*  463 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(CDATA cdata, Writer out) throws IOException {
/*  473 */     printCDATA(out, cdata);
/*  474 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(Text text, Writer out) throws IOException {
/*  485 */     printText(out, text);
/*  486 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(Comment comment, Writer out) throws IOException {
/*  496 */     printComment(out, comment);
/*  497 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(ProcessingInstruction pi, Writer out) throws IOException {
/*  508 */     boolean currentEscapingPolicy = this.currentFormat.ignoreTrAXEscapingPIs;
/*      */ 
/*      */     
/*  511 */     this.currentFormat.setIgnoreTrAXEscapingPIs(true);
/*  512 */     printProcessingInstruction(out, pi);
/*  513 */     this.currentFormat.setIgnoreTrAXEscapingPIs(currentEscapingPolicy);
/*      */     
/*  515 */     out.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void output(EntityRef entity, Writer out) throws IOException {
/*  525 */     printEntityRef(out, entity);
/*  526 */     out.flush();
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
/*      */   public String outputString(Document doc) {
/*  540 */     StringWriter out = new StringWriter();
/*      */     try {
/*  542 */       output(doc, out);
/*  543 */     } catch (IOException e) {}
/*  544 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(DocType doctype) {
/*  555 */     StringWriter out = new StringWriter();
/*      */     try {
/*  557 */       output(doctype, out);
/*  558 */     } catch (IOException e) {}
/*  559 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(Element element) {
/*  570 */     StringWriter out = new StringWriter();
/*      */     try {
/*  572 */       output(element, out);
/*  573 */     } catch (IOException e) {}
/*  574 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(List list) {
/*  584 */     StringWriter out = new StringWriter();
/*      */     try {
/*  586 */       output(list, out);
/*  587 */     } catch (IOException e) {}
/*  588 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(CDATA cdata) {
/*  599 */     StringWriter out = new StringWriter();
/*      */     try {
/*  601 */       output(cdata, out);
/*  602 */     } catch (IOException e) {}
/*  603 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(Text text) {
/*  614 */     StringWriter out = new StringWriter();
/*      */     try {
/*  616 */       output(text, out);
/*  617 */     } catch (IOException e) {}
/*  618 */     return out.toString();
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
/*      */   public String outputString(Comment comment) {
/*  630 */     StringWriter out = new StringWriter();
/*      */     try {
/*  632 */       output(comment, out);
/*  633 */     } catch (IOException e) {}
/*  634 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(ProcessingInstruction pi) {
/*  645 */     StringWriter out = new StringWriter();
/*      */     try {
/*  647 */       output(pi, out);
/*  648 */     } catch (IOException e) {}
/*  649 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String outputString(EntityRef entity) {
/*  660 */     StringWriter out = new StringWriter();
/*      */     try {
/*  662 */       output(entity, out);
/*  663 */     } catch (IOException e) {}
/*  664 */     return out.toString();
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
/*      */   protected void printDeclaration(Writer out, Document doc, String encoding) throws IOException {
/*  682 */     if (!this.userFormat.omitDeclaration) {
/*      */       
/*  684 */       out.write("<?xml version=\"1.0\"");
/*  685 */       if (!this.userFormat.omitEncoding) {
/*  686 */         out.write(" encoding=\"" + encoding + "\"");
/*      */       }
/*  688 */       out.write("?>");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  693 */       out.write(this.currentFormat.lineSeparator);
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
/*      */   protected void printDocType(Writer out, DocType docType) throws IOException {
/*  706 */     String publicID = docType.getPublicID();
/*  707 */     String systemID = docType.getSystemID();
/*  708 */     String internalSubset = docType.getInternalSubset();
/*  709 */     boolean hasPublic = false;
/*      */     
/*  711 */     out.write("<!DOCTYPE ");
/*  712 */     out.write(docType.getElementName());
/*  713 */     if (publicID != null) {
/*  714 */       out.write(" PUBLIC \"");
/*  715 */       out.write(publicID);
/*  716 */       out.write("\"");
/*  717 */       hasPublic = true;
/*      */     } 
/*  719 */     if (systemID != null) {
/*  720 */       if (!hasPublic) {
/*  721 */         out.write(" SYSTEM");
/*      */       }
/*  723 */       out.write(" \"");
/*  724 */       out.write(systemID);
/*  725 */       out.write("\"");
/*      */     } 
/*  727 */     if (internalSubset != null && !internalSubset.equals("")) {
/*  728 */       out.write(" [");
/*  729 */       out.write(this.currentFormat.lineSeparator);
/*  730 */       out.write(docType.getInternalSubset());
/*  731 */       out.write("]");
/*      */     } 
/*  733 */     out.write(">");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printComment(Writer out, Comment comment) throws IOException {
/*  744 */     out.write("<!--");
/*  745 */     out.write(comment.getText());
/*  746 */     out.write("-->");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printProcessingInstruction(Writer out, ProcessingInstruction pi) throws IOException {
/*  757 */     String target = pi.getTarget();
/*  758 */     boolean piProcessed = false;
/*      */     
/*  760 */     if (!this.currentFormat.ignoreTrAXEscapingPIs) {
/*  761 */       if (target.equals("javax.xml.transform.disable-output-escaping")) {
/*  762 */         this.escapeOutput = false;
/*  763 */         piProcessed = true;
/*      */       }
/*  765 */       else if (target.equals("javax.xml.transform.enable-output-escaping")) {
/*  766 */         this.escapeOutput = true;
/*  767 */         piProcessed = true;
/*      */       } 
/*      */     }
/*  770 */     if (!piProcessed) {
/*  771 */       String rawData = pi.getData();
/*      */ 
/*      */       
/*  774 */       if (!"".equals(rawData)) {
/*  775 */         out.write("<?");
/*  776 */         out.write(target);
/*  777 */         out.write(" ");
/*  778 */         out.write(rawData);
/*  779 */         out.write("?>");
/*      */       } else {
/*      */         
/*  782 */         out.write("<?");
/*  783 */         out.write(target);
/*  784 */         out.write("?>");
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
/*      */   protected void printEntityRef(Writer out, EntityRef entity) throws IOException {
/*  799 */     out.write("&");
/*  800 */     out.write(entity.getName());
/*  801 */     out.write(";");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printCDATA(Writer out, CDATA cdata) throws IOException {
/*  811 */     String str = (this.currentFormat.mode == Format.TextMode.NORMALIZE) ? cdata.getTextNormalize() : ((this.currentFormat.mode == Format.TextMode.TRIM) ? cdata.getText().trim() : cdata.getText());
/*      */ 
/*      */ 
/*      */     
/*  815 */     out.write("<![CDATA[");
/*  816 */     out.write(str);
/*  817 */     out.write("]]>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printText(Writer out, Text text) throws IOException {
/*  827 */     String str = (this.currentFormat.mode == Format.TextMode.NORMALIZE) ? text.getTextNormalize() : ((this.currentFormat.mode == Format.TextMode.TRIM) ? text.getText().trim() : text.getText());
/*      */ 
/*      */ 
/*      */     
/*  831 */     out.write(escapeElementEntities(str));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printString(Writer out, String str) throws IOException {
/*  839 */     if (this.currentFormat.mode == Format.TextMode.NORMALIZE) {
/*  840 */       str = Text.normalizeString(str);
/*      */     }
/*  842 */     else if (this.currentFormat.mode == Format.TextMode.TRIM) {
/*  843 */       str = str.trim();
/*      */     } 
/*  845 */     out.write(escapeElementEntities(str));
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
/*      */   protected void printElement(Writer out, Element element, int level, NamespaceStack namespaces) throws IOException {
/*  862 */     List attributes = element.getAttributes();
/*  863 */     List content = element.getContent();
/*      */ 
/*      */     
/*  866 */     String space = null;
/*  867 */     if (attributes != null) {
/*  868 */       space = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
/*      */     }
/*      */ 
/*      */     
/*  872 */     Format previousFormat = this.currentFormat;
/*      */     
/*  874 */     if ("default".equals(space)) {
/*  875 */       this.currentFormat = this.userFormat;
/*      */     }
/*  877 */     else if ("preserve".equals(space)) {
/*  878 */       this.currentFormat = preserveFormat;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  883 */     out.write("<");
/*  884 */     printQualifiedName(out, element);
/*      */ 
/*      */     
/*  887 */     int previouslyDeclaredNamespaces = namespaces.size();
/*      */ 
/*      */     
/*  890 */     printElementNamespace(out, element, namespaces);
/*      */ 
/*      */     
/*  893 */     printAdditionalNamespaces(out, element, namespaces);
/*      */ 
/*      */     
/*  896 */     if (attributes != null) {
/*  897 */       printAttributes(out, attributes, element, namespaces);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  904 */     int start = skipLeadingWhite(content, 0);
/*  905 */     int size = content.size();
/*  906 */     if (start >= size) {
/*      */       
/*  908 */       if (this.currentFormat.expandEmptyElements) {
/*  909 */         out.write("></");
/*  910 */         printQualifiedName(out, element);
/*  911 */         out.write(">");
/*      */       } else {
/*      */         
/*  914 */         out.write(" />");
/*      */       } 
/*      */     } else {
/*      */       
/*  918 */       out.write(">");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  924 */       if (nextNonText(content, start) < size) {
/*      */         
/*  926 */         newline(out);
/*  927 */         printContentRange(out, content, start, size, level + 1, namespaces);
/*      */         
/*  929 */         newline(out);
/*  930 */         indent(out, level);
/*      */       }
/*      */       else {
/*      */         
/*  934 */         printTextRange(out, content, start, size);
/*      */       } 
/*  936 */       out.write("</");
/*  937 */       printQualifiedName(out, element);
/*  938 */       out.write(">");
/*      */     } 
/*      */ 
/*      */     
/*  942 */     while (namespaces.size() > previouslyDeclaredNamespaces) {
/*  943 */       namespaces.pop();
/*      */     }
/*      */ 
/*      */     
/*  947 */     this.currentFormat = previousFormat;
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
/*      */   private void printContentRange(Writer out, List content, int start, int end, int level, NamespaceStack namespaces) throws IOException {
/*  971 */     int index = start;
/*  972 */     while (index < end) {
/*  973 */       boolean firstNode = (index == start);
/*  974 */       Object next = content.get(index);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  979 */       if (next instanceof Text || next instanceof EntityRef) {
/*  980 */         int first = skipLeadingWhite(content, index);
/*      */         
/*  982 */         index = nextNonText(content, first);
/*      */ 
/*      */         
/*  985 */         if (first < index) {
/*  986 */           if (!firstNode)
/*  987 */             newline(out); 
/*  988 */           indent(out, level);
/*  989 */           printTextRange(out, content, first, index);
/*      */         } 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  997 */       if (!firstNode) {
/*  998 */         newline(out);
/*      */       }
/*      */       
/* 1001 */       indent(out, level);
/*      */       
/* 1003 */       if (next instanceof Comment) {
/* 1004 */         printComment(out, (Comment)next);
/*      */       }
/* 1006 */       else if (next instanceof Element) {
/* 1007 */         printElement(out, (Element)next, level, namespaces);
/*      */       }
/* 1009 */       else if (next instanceof ProcessingInstruction) {
/* 1010 */         printProcessingInstruction(out, (ProcessingInstruction)next);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1018 */       index++;
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
/*      */   private void printTextRange(Writer out, List content, int start, int end) throws IOException {
/* 1038 */     String previous = null;
/*      */ 
/*      */     
/* 1041 */     start = skipLeadingWhite(content, start);
/*      */     
/* 1043 */     int size = content.size();
/* 1044 */     if (start < size) {
/*      */       
/* 1046 */       end = skipTrailingWhite(content, end);
/*      */       
/* 1048 */       for (int i = start; i < end; i++) {
/* 1049 */         String next; Object node = content.get(i);
/*      */ 
/*      */ 
/*      */         
/* 1053 */         if (node instanceof Text) {
/* 1054 */           next = ((Text)node).getText();
/*      */         }
/* 1056 */         else if (node instanceof EntityRef) {
/* 1057 */           next = "&" + ((EntityRef)node).getValue() + ";";
/*      */         } else {
/*      */           
/* 1060 */           throw new IllegalStateException("Should see only CDATA, Text, or EntityRef");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1065 */         if (next != null && !"".equals(next)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1071 */           if (previous != null && (
/* 1072 */             this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM))
/*      */           {
/* 1074 */             if (endsWithWhite(previous) || startsWithWhite(next))
/*      */             {
/* 1076 */               out.write(" ");
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1082 */           if (node instanceof CDATA) {
/* 1083 */             printCDATA(out, (CDATA)node);
/*      */           }
/* 1085 */           else if (node instanceof EntityRef) {
/* 1086 */             printEntityRef(out, (EntityRef)node);
/*      */           } else {
/*      */             
/* 1089 */             printString(out, next);
/*      */           } 
/*      */           
/* 1092 */           previous = next;
/*      */         } 
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
/*      */   private void printNamespace(Writer out, Namespace ns, NamespaceStack namespaces) throws IOException {
/* 1107 */     String prefix = ns.getPrefix();
/* 1108 */     String uri = ns.getURI();
/*      */ 
/*      */     
/* 1111 */     if (uri.equals(namespaces.getURI(prefix))) {
/*      */       return;
/*      */     }
/*      */     
/* 1115 */     out.write(" xmlns");
/* 1116 */     if (!prefix.equals("")) {
/* 1117 */       out.write(":");
/* 1118 */       out.write(prefix);
/*      */     } 
/* 1120 */     out.write("=\"");
/* 1121 */     out.write(escapeAttributeEntities(uri));
/* 1122 */     out.write("\"");
/* 1123 */     namespaces.push(ns);
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
/*      */   protected void printAttributes(Writer out, List attributes, Element parent, NamespaceStack namespaces) throws IOException {
/* 1141 */     for (int i = 0; i < attributes.size(); i++) {
/* 1142 */       Attribute attribute = attributes.get(i);
/* 1143 */       Namespace ns = attribute.getNamespace();
/* 1144 */       if (ns != Namespace.NO_NAMESPACE && ns != Namespace.XML_NAMESPACE)
/*      */       {
/* 1146 */         printNamespace(out, ns, namespaces);
/*      */       }
/*      */       
/* 1149 */       out.write(" ");
/* 1150 */       printQualifiedName(out, attribute);
/* 1151 */       out.write("=");
/*      */       
/* 1153 */       out.write("\"");
/* 1154 */       out.write(escapeAttributeEntities(attribute.getValue()));
/* 1155 */       out.write("\"");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printElementNamespace(Writer out, Element element, NamespaceStack namespaces) throws IOException {
/* 1166 */     Namespace ns = element.getNamespace();
/* 1167 */     if (ns == Namespace.XML_NAMESPACE) {
/*      */       return;
/*      */     }
/* 1170 */     if (ns != Namespace.NO_NAMESPACE || namespaces.getURI("") != null)
/*      */     {
/* 1172 */       printNamespace(out, ns, namespaces);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void printAdditionalNamespaces(Writer out, Element element, NamespaceStack namespaces) throws IOException {
/* 1179 */     List list = element.getAdditionalNamespaces();
/* 1180 */     if (list != null) {
/* 1181 */       for (int i = 0; i < list.size(); i++) {
/* 1182 */         Namespace additional = list.get(i);
/* 1183 */         printNamespace(out, additional, namespaces);
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
/*      */   private void newline(Writer out) throws IOException {
/* 1197 */     if (this.currentFormat.indent != null) {
/* 1198 */       out.write(this.currentFormat.lineSeparator);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void indent(Writer out, int level) throws IOException {
/* 1209 */     if (this.currentFormat.indent == null || this.currentFormat.indent.equals("")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1214 */     for (int i = 0; i < level; i++) {
/* 1215 */       out.write(this.currentFormat.indent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skipLeadingWhite(List content, int start) {
/* 1224 */     if (start < 0) {
/* 1225 */       start = 0;
/*      */     }
/*      */     
/* 1228 */     int index = start;
/* 1229 */     int size = content.size();
/* 1230 */     if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE || this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM)
/*      */     {
/*      */       
/* 1233 */       while (index < size) {
/* 1234 */         if (!isAllWhitespace(content.get(index))) {
/* 1235 */           return index;
/*      */         }
/* 1237 */         index++;
/*      */       } 
/*      */     }
/* 1240 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skipTrailingWhite(List content, int start) {
/* 1248 */     int size = content.size();
/* 1249 */     if (start > size) {
/* 1250 */       start = size;
/*      */     }
/*      */     
/* 1253 */     int index = start;
/* 1254 */     if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE || this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM)
/*      */     {
/*      */       
/* 1257 */       while (index >= 0 && 
/* 1258 */         isAllWhitespace(content.get(index - 1)))
/*      */       {
/* 1260 */         index--;
/*      */       }
/*      */     }
/* 1263 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int nextNonText(List content, int start) {
/* 1271 */     if (start < 0) {
/* 1272 */       start = 0;
/*      */     }
/*      */     
/* 1275 */     int index = start;
/* 1276 */     int size = content.size();
/* 1277 */     while (index < size) {
/* 1278 */       Object node = content.get(index);
/* 1279 */       if (!(node instanceof Text) && !(node instanceof EntityRef)) {
/* 1280 */         return index;
/*      */       }
/* 1282 */       index++;
/*      */     } 
/* 1284 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAllWhitespace(Object obj) {
/* 1289 */     String str = null;
/*      */     
/* 1291 */     if (obj instanceof String) {
/* 1292 */       str = (String)obj;
/*      */     }
/* 1294 */     else if (obj instanceof Text) {
/* 1295 */       str = ((Text)obj).getText();
/*      */     } else {
/* 1297 */       if (obj instanceof EntityRef) {
/* 1298 */         return false;
/*      */       }
/*      */       
/* 1301 */       return false;
/*      */     } 
/*      */     
/* 1304 */     for (int i = 0; i < str.length(); i++) {
/* 1305 */       if (!Verifier.isXMLWhitespace(str.charAt(i)))
/* 1306 */         return false; 
/*      */     } 
/* 1308 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean startsWithWhite(String str) {
/* 1313 */     if (str != null && str.length() > 0 && Verifier.isXMLWhitespace(str.charAt(0)))
/*      */     {
/*      */       
/* 1316 */       return true;
/*      */     }
/* 1318 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean endsWithWhite(String str) {
/* 1323 */     if (str != null && str.length() > 0 && Verifier.isXMLWhitespace(str.charAt(str.length() - 1)))
/*      */     {
/*      */       
/* 1326 */       return true;
/*      */     }
/* 1328 */     return false;
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
/*      */   public String escapeAttributeEntities(String str) {
/* 1345 */     EscapeStrategy strategy = this.currentFormat.escapeStrategy;
/*      */     
/* 1347 */     StringBuffer buffer = null;
/* 1348 */     for (int i = 0; i < str.length(); i++) {
/* 1349 */       String entity; char ch = str.charAt(i);
/* 1350 */       switch (ch) {
/*      */         case '<':
/* 1352 */           entity = "&lt;";
/*      */           break;
/*      */         case '>':
/* 1355 */           entity = "&gt;";
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case '"':
/* 1363 */           entity = "&quot;";
/*      */           break;
/*      */         case '&':
/* 1366 */           entity = "&amp;";
/*      */           break;
/*      */         case '\r':
/* 1369 */           entity = "&#xD;";
/*      */           break;
/*      */         case '\t':
/* 1372 */           entity = "&#x9;";
/*      */           break;
/*      */         case '\n':
/* 1375 */           entity = "&#xA;";
/*      */           break;
/*      */         default:
/* 1378 */           if (strategy.shouldEscape(ch)) {
/* 1379 */             entity = "&#x" + Integer.toHexString(ch) + ";";
/*      */             break;
/*      */           } 
/* 1382 */           entity = null;
/*      */           break;
/*      */       } 
/*      */       
/* 1386 */       if (buffer == null) {
/* 1387 */         if (entity != null)
/*      */         {
/*      */           
/* 1390 */           buffer = new StringBuffer(str.length() + 20);
/*      */ 
/*      */           
/* 1393 */           buffer.append(str.substring(0, i));
/* 1394 */           buffer.append(entity);
/*      */         }
/*      */       
/*      */       }
/* 1398 */       else if (entity == null) {
/* 1399 */         buffer.append(ch);
/*      */       } else {
/*      */         
/* 1402 */         buffer.append(entity);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1410 */     return (buffer == null) ? str : buffer.toString();
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
/*      */   public String escapeElementEntities(String str) {
/* 1424 */     if (!this.escapeOutput) return str;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1429 */     EscapeStrategy strategy = this.currentFormat.escapeStrategy;
/*      */     
/* 1431 */     StringBuffer buffer = null;
/* 1432 */     for (int i = 0; i < str.length(); i++) {
/* 1433 */       String entity; char ch = str.charAt(i);
/* 1434 */       switch (ch) {
/*      */         case '<':
/* 1436 */           entity = "&lt;";
/*      */           break;
/*      */         case '>':
/* 1439 */           entity = "&gt;";
/*      */           break;
/*      */         case '&':
/* 1442 */           entity = "&amp;";
/*      */           break;
/*      */         case '\r':
/* 1445 */           entity = "&#xD;";
/*      */           break;
/*      */         case '\n':
/* 1448 */           entity = this.currentFormat.lineSeparator;
/*      */           break;
/*      */         default:
/* 1451 */           if (strategy.shouldEscape(ch)) {
/* 1452 */             entity = "&#x" + Integer.toHexString(ch) + ";";
/*      */             break;
/*      */           } 
/* 1455 */           entity = null;
/*      */           break;
/*      */       } 
/*      */       
/* 1459 */       if (buffer == null) {
/* 1460 */         if (entity != null)
/*      */         {
/*      */           
/* 1463 */           buffer = new StringBuffer(str.length() + 20);
/*      */ 
/*      */           
/* 1466 */           buffer.append(str.substring(0, i));
/* 1467 */           buffer.append(entity);
/*      */         }
/*      */       
/*      */       }
/* 1471 */       else if (entity == null) {
/* 1472 */         buffer.append(ch);
/*      */       } else {
/*      */         
/* 1475 */         buffer.append(entity);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1483 */     return (buffer == null) ? str : buffer.toString();
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
/*      */   public Object clone() {
/*      */     try {
/* 1496 */       return super.clone();
/*      */     }
/* 1498 */     catch (CloneNotSupportedException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1503 */       throw new RuntimeException(e.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1514 */     StringBuffer buffer = new StringBuffer();
/* 1515 */     for (int i = 0; i < this.userFormat.lineSeparator.length(); i++) {
/* 1516 */       char ch = this.userFormat.lineSeparator.charAt(i);
/* 1517 */       switch (ch) { case '\r':
/* 1518 */           buffer.append("\\r"); break;
/*      */         case '\n':
/* 1520 */           buffer.append("\\n"); break;
/*      */         case '\t':
/* 1522 */           buffer.append("\\t"); break;
/*      */         default:
/* 1524 */           buffer.append("[" + ch + "]");
/*      */           break; }
/*      */ 
/*      */     
/*      */     } 
/* 1529 */     return "XMLOutputter[omitDeclaration = " + this.userFormat.omitDeclaration + ", " + "encoding = " + this.userFormat.encoding + ", " + "omitEncoding = " + this.userFormat.omitEncoding + ", " + "indent = '" + this.userFormat.indent + "'" + ", " + "expandEmptyElements = " + this.userFormat.expandEmptyElements + ", " + "lineSeparator = '" + buffer.toString() + "', " + "textMode = " + this.userFormat.mode + "]";
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
/*      */   private NamespaceStack createNamespaceStack() {
/* 1548 */     return new NamespaceStack(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class NamespaceStack
/*      */     extends NamespaceStack
/*      */   {
/*      */     private final XMLOutputter this$0;
/*      */     
/*      */     protected NamespaceStack(XMLOutputter this$0) {
/* 1558 */       this.this$0 = this$0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printQualifiedName(Writer out, Element e) throws IOException {
/* 1566 */     if (e.getNamespace().getPrefix().length() == 0) {
/* 1567 */       out.write(e.getName());
/*      */     } else {
/*      */       
/* 1570 */       out.write(e.getNamespace().getPrefix());
/* 1571 */       out.write(58);
/* 1572 */       out.write(e.getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void printQualifiedName(Writer out, Attribute a) throws IOException {
/* 1579 */     String prefix = a.getNamespace().getPrefix();
/* 1580 */     if (prefix != null && !prefix.equals("")) {
/* 1581 */       out.write(prefix);
/* 1582 */       out.write(58);
/* 1583 */       out.write(a.getName());
/*      */     } else {
/*      */       
/* 1586 */       out.write(a.getName());
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/output/XMLOutputter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */