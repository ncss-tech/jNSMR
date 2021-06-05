/*     */ package org.jdom;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Namespace
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Namespace.java,v $ $Revision: 1.43 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1 $";
/*     */   private static HashMap namespaces;
/*  97 */   public static final Namespace NO_NAMESPACE = new Namespace("", "");
/*     */ 
/*     */   
/* 100 */   public static final Namespace XML_NAMESPACE = new Namespace("xml", "http://www.w3.org/XML/1998/namespace");
/*     */ 
/*     */ 
/*     */   
/*     */   private String prefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private String uri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 114 */     namespaces = new HashMap(16);
/*     */ 
/*     */     
/* 117 */     namespaces.put(new NamespaceKey(NO_NAMESPACE), NO_NAMESPACE);
/* 118 */     namespaces.put(new NamespaceKey(XML_NAMESPACE), XML_NAMESPACE);
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
/*     */   public static Namespace getNamespace(String prefix, String uri) {
/* 134 */     if (prefix == null || prefix.trim().equals("")) {
/*     */       
/* 136 */       if (uri == null || uri.trim().equals("")) {
/* 137 */         return NO_NAMESPACE;
/*     */       }
/* 139 */       prefix = "";
/*     */     }
/* 141 */     else if (uri == null || uri.trim().equals("")) {
/* 142 */       uri = "";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     NamespaceKey lookup = new NamespaceKey(prefix, uri);
/* 150 */     Namespace preexisting = (Namespace)namespaces.get(lookup);
/* 151 */     if (preexisting != null) {
/* 152 */       return preexisting;
/*     */     }
/*     */     
/*     */     String reason;
/*     */     
/* 157 */     if ((reason = Verifier.checkNamespacePrefix(prefix)) != null) {
/* 158 */       throw new IllegalNameException(prefix, "Namespace prefix", reason);
/*     */     }
/* 160 */     if ((reason = Verifier.checkNamespaceURI(uri)) != null) {
/* 161 */       throw new IllegalNameException(uri, "Namespace URI", reason);
/*     */     }
/*     */ 
/*     */     
/* 165 */     if (!prefix.equals("") && uri.equals("")) {
/* 166 */       throw new IllegalNameException("", "namespace", "Namespace URIs must be non-null and non-empty Strings");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (prefix.equals("xml")) {
/* 177 */       throw new IllegalNameException(prefix, "Namespace prefix", "The xml prefix can only be bound to http://www.w3.org/XML/1998/namespace");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (uri.equals("http://www.w3.org/XML/1998/namespace")) {
/* 185 */       throw new IllegalNameException(uri, "Namespace URI", "The http://www.w3.org/XML/1998/namespace must be bound to the xml prefix.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     Namespace ns = new Namespace(prefix, uri);
/* 192 */     namespaces.put(lookup, ns);
/* 193 */     return ns;
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
/*     */   public static Namespace getNamespace(String uri) {
/* 205 */     return getNamespace("", uri);
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
/*     */   private Namespace(String prefix, String uri) {
/* 217 */     this.prefix = prefix;
/* 218 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 227 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 236 */     return this.uri;
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
/*     */   public boolean equals(Object ob) {
/* 248 */     if (this == ob) {
/* 249 */       return true;
/*     */     }
/* 251 */     if (ob instanceof Namespace) {
/* 252 */       return this.uri.equals(((Namespace)ob).uri);
/*     */     }
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return "[Namespace: prefix \"" + this.prefix + "\" is mapped to URI \"" + this.uri + "\"]";
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
/*     */   public int hashCode() {
/* 276 */     return this.uri.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/Namespace.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */