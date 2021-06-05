/*      */ package org.jdom;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Verifier
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: Verifier.java,v $ $Revision: 1.55 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1 $";
/*      */   
/*      */   public static String checkElementName(String name) {
/*      */     String reason;
/*   92 */     if ((reason = checkXMLName(name)) != null) {
/*   93 */       return reason;
/*      */     }
/*      */ 
/*      */     
/*   97 */     if (name.indexOf(":") != -1) {
/*   98 */       return "Element names cannot contain colons";
/*      */     }
/*      */ 
/*      */     
/*  102 */     return null;
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
/*      */   public static String checkAttributeName(String name) {
/*      */     String reason;
/*  116 */     if ((reason = checkXMLName(name)) != null) {
/*  117 */       return reason;
/*      */     }
/*      */ 
/*      */     
/*  121 */     if (name.indexOf(":") != -1) {
/*  122 */       return "Attribute names cannot contain colons";
/*      */     }
/*      */ 
/*      */     
/*  126 */     if (name.equals("xmlns")) {
/*  127 */       return "An Attribute name may not be \"xmlns\"; use the Namespace class to manage namespaces";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  132 */     return null;
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
/*      */   public static String checkCharacterData(String text) {
/*  154 */     if (text == null) {
/*  155 */       return "A null is not a legal XML value";
/*      */     }
/*      */ 
/*      */     
/*  159 */     for (int i = 0, len = text.length(); i < len; i++) {
/*      */       
/*  161 */       int ch = text.charAt(i);
/*      */ 
/*      */       
/*  164 */       if (ch >= 55296 && ch <= 56319) {
/*      */         
/*  166 */         i++;
/*  167 */         if (i < len) {
/*  168 */           char low = text.charAt(i);
/*  169 */           if (low < '?' || low > '?') {
/*  170 */             return "Illegal Surrogate Pair";
/*      */           }
/*      */ 
/*      */           
/*  174 */           ch = 65536 + (ch - 55296) * 1024 + low - 56320;
/*      */         } else {
/*      */           
/*  177 */           return "Surrogate Pair Truncated";
/*      */         } 
/*      */       } 
/*      */       
/*  181 */       if (!isXMLCharacter(ch))
/*      */       {
/*      */ 
/*      */         
/*  185 */         return "0x" + Integer.toHexString(ch) + " is not a legal XML character";
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  191 */     return null;
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
/*      */   public static String checkCDATASection(String data) {
/*  203 */     String reason = null;
/*  204 */     if ((reason = checkCharacterData(data)) != null) {
/*  205 */       return reason;
/*      */     }
/*      */     
/*  208 */     if (data.indexOf("]]>") != -1) {
/*  209 */       return "CDATA cannot internally contain a CDATA ending delimiter (]]>)";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  214 */     return null;
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
/*      */   public static String checkNamespacePrefix(String prefix) {
/*  227 */     if (prefix == null || prefix.equals("")) {
/*  228 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  232 */     char first = prefix.charAt(0);
/*  233 */     if (isXMLDigit(first)) {
/*  234 */       return "Namespace prefixes cannot begin with a number";
/*      */     }
/*      */     
/*  237 */     if (first == '$') {
/*  238 */       return "Namespace prefixes cannot begin with a dollar sign ($)";
/*      */     }
/*      */     
/*  241 */     if (first == '-') {
/*  242 */       return "Namespace prefixes cannot begin with a hyphen (-)";
/*      */     }
/*      */     
/*  245 */     if (first == '.') {
/*  246 */       return "Namespace prefixes cannot begin with a period (.)";
/*      */     }
/*      */     
/*  249 */     if (prefix.toLowerCase().startsWith("xml")) {
/*  250 */       return "Namespace prefixes cannot begin with \"xml\" in any combination of case";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  255 */     for (int i = 0, len = prefix.length(); i < len; i++) {
/*  256 */       char c = prefix.charAt(i);
/*  257 */       if (!isXMLNameCharacter(c)) {
/*  258 */         return "Namespace prefixes cannot contain the character \"" + c + "\"";
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  264 */     if (prefix.indexOf(":") != -1) {
/*  265 */       return "Namespace prefixes cannot contain colons";
/*      */     }
/*      */ 
/*      */     
/*  269 */     return null;
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
/*      */   public static String checkNamespaceURI(String uri) {
/*  282 */     if (uri == null || uri.equals("")) {
/*  283 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  287 */     char first = uri.charAt(0);
/*  288 */     if (Character.isDigit(first)) {
/*  289 */       return "Namespace URIs cannot begin with a number";
/*      */     }
/*      */     
/*  292 */     if (first == '$') {
/*  293 */       return "Namespace URIs cannot begin with a dollar sign ($)";
/*      */     }
/*      */     
/*  296 */     if (first == '-') {
/*  297 */       return "Namespace URIs cannot begin with a hyphen (-)";
/*      */     }
/*      */ 
/*      */     
/*  301 */     return null;
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
/*      */   public static String checkNamespaceCollision(Namespace namespace, Namespace other) {
/*  316 */     String reason = null;
/*  317 */     String p1 = namespace.getPrefix();
/*  318 */     String u1 = namespace.getURI();
/*  319 */     String p2 = other.getPrefix();
/*  320 */     String u2 = other.getURI();
/*  321 */     if (p1.equals(p2) && !u1.equals(u2)) {
/*  322 */       reason = "The namespace prefix \"" + p1 + "\" collides";
/*      */     }
/*  324 */     return reason;
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
/*      */   public static String checkNamespaceCollision(Attribute attribute, Element element) {
/*  338 */     Namespace namespace = attribute.getNamespace();
/*  339 */     String prefix = namespace.getPrefix();
/*  340 */     if ("".equals(prefix)) {
/*  341 */       return null;
/*      */     }
/*      */     
/*  344 */     return checkNamespaceCollision(namespace, element);
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
/*      */   public static String checkNamespaceCollision(Namespace namespace, Element element) {
/*  358 */     String reason = checkNamespaceCollision(namespace, element.getNamespace());
/*      */     
/*  360 */     if (reason != null) {
/*  361 */       return reason + " with the element namespace prefix";
/*      */     }
/*      */     
/*  364 */     reason = checkNamespaceCollision(namespace, element.getAdditionalNamespaces());
/*      */     
/*  366 */     if (reason != null) {
/*  367 */       return reason;
/*      */     }
/*      */     
/*  370 */     reason = checkNamespaceCollision(namespace, element.getAttributes());
/*  371 */     if (reason != null) {
/*  372 */       return reason;
/*      */     }
/*      */     
/*  375 */     return null;
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
/*      */   public static String checkNamespaceCollision(Namespace namespace, Attribute attribute) {
/*  389 */     String reason = checkNamespaceCollision(namespace, attribute.getNamespace());
/*      */     
/*  391 */     if (reason != null) {
/*  392 */       reason = reason + " with an attribute namespace prefix on the element";
/*      */     }
/*  394 */     return reason;
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
/*      */   public static String checkNamespaceCollision(Namespace namespace, List list) {
/*  408 */     if (list == null) {
/*  409 */       return null;
/*      */     }
/*      */     
/*  412 */     String reason = null;
/*  413 */     Iterator i = list.iterator();
/*  414 */     while (reason == null && i.hasNext()) {
/*  415 */       Object obj = i.next();
/*  416 */       if (obj instanceof Attribute) {
/*  417 */         reason = checkNamespaceCollision(namespace, (Attribute)obj); continue;
/*      */       } 
/*  419 */       if (obj instanceof Element) {
/*  420 */         reason = checkNamespaceCollision(namespace, (Element)obj); continue;
/*      */       } 
/*  422 */       if (obj instanceof Namespace) {
/*  423 */         reason = checkNamespaceCollision(namespace, (Namespace)obj);
/*  424 */         if (reason != null) {
/*  425 */           reason = reason + " with an additional namespace declared by the element";
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  430 */     return reason;
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
/*      */   public static String checkProcessingInstructionTarget(String target) {
/*      */     String reason;
/*  444 */     if ((reason = checkXMLName(target)) != null) {
/*  445 */       return reason;
/*      */     }
/*      */ 
/*      */     
/*  449 */     if (target.indexOf(":") != -1) {
/*  450 */       return "Processing instruction targets cannot contain colons";
/*      */     }
/*      */ 
/*      */     
/*  454 */     if (target.equalsIgnoreCase("xml")) {
/*  455 */       return "Processing instructions cannot have a target of \"xml\" in any combination of case. (Note that the \"<?xml ... ?>\" declaration at the beginning of a document is not a processing instruction and should not be added as one; it is written automatically during output, e.g. by XMLOutputter.)";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  464 */     return null;
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
/*      */   public static String checkProcessingInstructionData(String data) {
/*  479 */     String reason = checkCharacterData(data);
/*      */     
/*  481 */     if (reason == null && 
/*  482 */       data.indexOf("?>") >= 0) {
/*  483 */       return "Processing instructions cannot contain the string \"?>\"";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  488 */     return reason;
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
/*      */   public static String checkCommentData(String data) {
/*  500 */     String reason = null;
/*  501 */     if ((reason = checkCharacterData(data)) != null) {
/*  502 */       return reason;
/*      */     }
/*      */     
/*  505 */     if (data.indexOf("--") != -1) {
/*  506 */       return "Comments cannot contain double hyphens (--)";
/*      */     }
/*  508 */     if (data.endsWith("-")) {
/*  509 */       return "Comment data cannot end with a hyphen.";
/*      */     }
/*      */ 
/*      */     
/*  513 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isXMLPublicIDCharacter(char c) {
/*  520 */     if (c >= 'a' && c <= 'z') return true; 
/*  521 */     if (c >= '?' && c <= 'Z') return true; 
/*  522 */     if (c >= '\'' && c <= ';') return true;
/*      */     
/*  524 */     if (c == ' ') return true; 
/*  525 */     if (c == '!') return true; 
/*  526 */     if (c == '=') return true; 
/*  527 */     if (c == '#') return true; 
/*  528 */     if (c == '$') return true; 
/*  529 */     if (c == '_') return true; 
/*  530 */     if (c == '%') return true; 
/*  531 */     if (c == '\n') return true; 
/*  532 */     if (c == '\r') return true; 
/*  533 */     if (c == '\t') return true;
/*      */     
/*  535 */     return false;
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
/*      */   public static String checkPublicID(String publicID) {
/*  547 */     String reason = null;
/*      */     
/*  549 */     if (publicID == null) return null;
/*      */ 
/*      */     
/*  552 */     for (int i = 0; i < publicID.length(); i++) {
/*  553 */       char c = publicID.charAt(i);
/*  554 */       if (!isXMLPublicIDCharacter(c)) {
/*  555 */         reason = c + " is not a legal character in public IDs";
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  560 */     return reason;
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
/*      */   public static String checkSystemLiteral(String systemLiteral) {
/*  573 */     String reason = null;
/*      */     
/*  575 */     if (systemLiteral == null) return null;
/*      */ 
/*      */     
/*  578 */     if (systemLiteral.indexOf('\'') != -1 && systemLiteral.indexOf('"') != -1) {
/*      */       
/*  580 */       reason = "System literals cannot simultaneously contain both single and double quotes.";
/*      */     }
/*      */     else {
/*      */       
/*  584 */       reason = checkCharacterData(systemLiteral);
/*      */     } 
/*      */     
/*  587 */     return reason;
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
/*      */   public static String checkXMLName(String name) {
/*  600 */     if (name == null || name.length() == 0 || name.trim().equals(""))
/*      */     {
/*  602 */       return "XML names cannot be null or empty";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  607 */     char first = name.charAt(0);
/*  608 */     if (!isXMLNameStartCharacter(first)) {
/*  609 */       return "XML names cannot begin with the character \"" + first + "\"";
/*      */     }
/*      */ 
/*      */     
/*  613 */     for (int i = 1, len = name.length(); i < len; i++) {
/*  614 */       char c = name.charAt(i);
/*  615 */       if (!isXMLNameCharacter(c)) {
/*  616 */         return "XML names cannot contain the character \"" + c + "\"";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  621 */     return null;
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
/*      */   public static String checkURI(String uri) {
/*  636 */     if (uri == null || uri.equals("")) {
/*  637 */       return null;
/*      */     }
/*      */     
/*  640 */     for (int i = 0; i < uri.length(); i++) {
/*  641 */       char test = uri.charAt(i);
/*  642 */       if (!isURICharacter(test)) {
/*  643 */         String msgNumber = "0x" + Integer.toHexString(test);
/*  644 */         if (test <= '\t') msgNumber = "0x0" + Integer.toHexString(test); 
/*  645 */         return "URIs cannot contain " + msgNumber;
/*      */       } 
/*  647 */       if (test == '%') {
/*      */         try {
/*  649 */           char firstDigit = uri.charAt(i + 1);
/*  650 */           char secondDigit = uri.charAt(i + 2);
/*  651 */           if (!isHexDigit(firstDigit) || !isHexDigit(secondDigit))
/*      */           {
/*  653 */             return "Percent signs in URIs must be followed by exactly two hexadecimal digits.";
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  658 */         catch (StringIndexOutOfBoundsException e) {
/*  659 */           return "Percent signs in URIs must be followed by exactly two hexadecimal digits.";
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  666 */     return null;
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
/*      */   public static boolean isHexDigit(char c) {
/*  686 */     if (c >= '0' && c <= '9') return true; 
/*  687 */     if (c >= 'A' && c <= 'F') return true; 
/*  688 */     if (c >= 'a' && c <= 'f') return true;
/*      */     
/*  690 */     return false;
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
/*      */   public static boolean isURICharacter(char c) {
/*  704 */     if (c >= 'a' && c <= 'z') return true; 
/*  705 */     if (c >= 'A' && c <= 'Z') return true; 
/*  706 */     if (c >= '0' && c <= '9') return true; 
/*  707 */     if (c == '/') return true; 
/*  708 */     if (c == '-') return true; 
/*  709 */     if (c == '.') return true; 
/*  710 */     if (c == '?') return true; 
/*  711 */     if (c == ':') return true; 
/*  712 */     if (c == '@') return true; 
/*  713 */     if (c == '&') return true; 
/*  714 */     if (c == '=') return true; 
/*  715 */     if (c == '+') return true; 
/*  716 */     if (c == '$') return true; 
/*  717 */     if (c == ',') return true; 
/*  718 */     if (c == '%') return true;
/*      */     
/*  720 */     if (c == '_') return true; 
/*  721 */     if (c == '!') return true; 
/*  722 */     if (c == '~') return true; 
/*  723 */     if (c == '*') return true; 
/*  724 */     if (c == '\'') return true; 
/*  725 */     if (c == '(') return true; 
/*  726 */     if (c == ')') return true; 
/*  727 */     return false;
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
/*      */   public static boolean isXMLCharacter(int c) {
/*  741 */     if (c == 10) return true; 
/*  742 */     if (c == 13) return true; 
/*  743 */     if (c == 9) return true;
/*      */     
/*  745 */     if (c < 32) return false;  if (c <= 55295) return true; 
/*  746 */     if (c < 57344) return false;  if (c <= 65533) return true; 
/*  747 */     if (c < 65536) return false;  if (c <= 1114111) return true;
/*      */     
/*  749 */     return false;
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
/*      */   public static boolean isXMLNameCharacter(char c) {
/*  764 */     return (isXMLLetter(c) || isXMLDigit(c) || c == '.' || c == '-' || c == '_' || c == ':' || isXMLCombiningChar(c) || isXMLExtender(c));
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
/*      */   public static boolean isXMLNameStartCharacter(char c) {
/*  782 */     return (isXMLLetter(c) || c == '_' || c == ':');
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
/*      */   public static boolean isXMLLetterOrDigit(char c) {
/*  797 */     return (isXMLLetter(c) || isXMLDigit(c));
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
/*      */   public static boolean isXMLLetter(char c) {
/*  814 */     if (c < 'A') return false;  if (c <= 'Z') return true; 
/*  815 */     if (c < 'a') return false;  if (c <= 'z') return true; 
/*  816 */     if (c < 'À') return false;  if (c <= 'Ö') return true; 
/*  817 */     if (c < 'Ø') return false;  if (c <= 'ö') return true; 
/*  818 */     if (c < 'ø') return false;  if (c <= 'ÿ') return true; 
/*  819 */     if (c < 'Ā') return false;  if (c <= 'ı') return true; 
/*  820 */     if (c < 'Ĵ') return false;  if (c <= 'ľ') return true; 
/*  821 */     if (c < 'Ł') return false;  if (c <= 'ň') return true; 
/*  822 */     if (c < 'Ŋ') return false;  if (c <= 'ž') return true; 
/*  823 */     if (c < 'ƀ') return false;  if (c <= 'ǃ') return true; 
/*  824 */     if (c < 'Ǎ') return false;  if (c <= 'ǰ') return true; 
/*  825 */     if (c < 'Ǵ') return false;  if (c <= 'ǵ') return true; 
/*  826 */     if (c < 'Ǻ') return false;  if (c <= 'ȗ') return true; 
/*  827 */     if (c < 'ɐ') return false;  if (c <= 'ʨ') return true; 
/*  828 */     if (c < 'ʻ') return false;  if (c <= 'ˁ') return true; 
/*  829 */     if (c == 'Ά') return true; 
/*  830 */     if (c < 'Έ') return false;  if (c <= 'Ί') return true; 
/*  831 */     if (c == 'Ό') return true; 
/*  832 */     if (c < 'Ύ') return false;  if (c <= 'Ρ') return true; 
/*  833 */     if (c < 'Σ') return false;  if (c <= 'ώ') return true; 
/*  834 */     if (c < 'ϐ') return false;  if (c <= 'ϖ') return true; 
/*  835 */     if (c == 'Ϛ') return true; 
/*  836 */     if (c == 'Ϝ') return true; 
/*  837 */     if (c == 'Ϟ') return true; 
/*  838 */     if (c == 'Ϡ') return true; 
/*  839 */     if (c < 'Ϣ') return false;  if (c <= 'ϳ') return true; 
/*  840 */     if (c < 'Ё') return false;  if (c <= 'Ќ') return true; 
/*  841 */     if (c < 'Ў') return false;  if (c <= 'я') return true; 
/*  842 */     if (c < 'ё') return false;  if (c <= 'ќ') return true; 
/*  843 */     if (c < 'ў') return false;  if (c <= 'ҁ') return true; 
/*  844 */     if (c < 'Ґ') return false;  if (c <= 'ӄ') return true; 
/*  845 */     if (c < 'Ӈ') return false;  if (c <= 'ӈ') return true; 
/*  846 */     if (c < 'Ӌ') return false;  if (c <= 'ӌ') return true; 
/*  847 */     if (c < 'Ӑ') return false;  if (c <= 'ӫ') return true; 
/*  848 */     if (c < 'Ӯ') return false;  if (c <= 'ӵ') return true; 
/*  849 */     if (c < 'Ӹ') return false;  if (c <= 'ӹ') return true; 
/*  850 */     if (c < 'Ա') return false;  if (c <= 'Ֆ') return true; 
/*  851 */     if (c == 'ՙ') return true; 
/*  852 */     if (c < 'ա') return false;  if (c <= 'ֆ') return true; 
/*  853 */     if (c < 'א') return false;  if (c <= 'ת') return true; 
/*  854 */     if (c < 'װ') return false;  if (c <= 'ײ') return true; 
/*  855 */     if (c < 'ء') return false;  if (c <= 'غ') return true; 
/*  856 */     if (c < 'ف') return false;  if (c <= 'ي') return true; 
/*  857 */     if (c < 'ٱ') return false;  if (c <= 'ڷ') return true; 
/*  858 */     if (c < 'ں') return false;  if (c <= 'ھ') return true; 
/*  859 */     if (c < 'ۀ') return false;  if (c <= 'ێ') return true; 
/*  860 */     if (c < 'ې') return false;  if (c <= 'ۓ') return true; 
/*  861 */     if (c == 'ە') return true; 
/*  862 */     if (c < 'ۥ') return false;  if (c <= 'ۦ') return true; 
/*  863 */     if (c < 'अ') return false;  if (c <= 'ह') return true; 
/*  864 */     if (c == 'ऽ') return true; 
/*  865 */     if (c < 'क़') return false;  if (c <= 'ॡ') return true; 
/*  866 */     if (c < 'অ') return false;  if (c <= 'ঌ') return true; 
/*  867 */     if (c < 'এ') return false;  if (c <= 'ঐ') return true; 
/*  868 */     if (c < 'ও') return false;  if (c <= 'ন') return true; 
/*  869 */     if (c < 'প') return false;  if (c <= 'র') return true; 
/*  870 */     if (c == 'ল') return true; 
/*  871 */     if (c < 'শ') return false;  if (c <= 'হ') return true; 
/*  872 */     if (c < 'ড়') return false;  if (c <= 'ঢ়') return true; 
/*  873 */     if (c < 'য়') return false;  if (c <= 'ৡ') return true; 
/*  874 */     if (c < 'ৰ') return false;  if (c <= 'ৱ') return true; 
/*  875 */     if (c < 'ਅ') return false;  if (c <= 'ਊ') return true; 
/*  876 */     if (c < 'ਏ') return false;  if (c <= 'ਐ') return true; 
/*  877 */     if (c < 'ਓ') return false;  if (c <= 'ਨ') return true; 
/*  878 */     if (c < 'ਪ') return false;  if (c <= 'ਰ') return true; 
/*  879 */     if (c < 'ਲ') return false;  if (c <= 'ਲ਼') return true; 
/*  880 */     if (c < 'ਵ') return false;  if (c <= 'ਸ਼') return true; 
/*  881 */     if (c < 'ਸ') return false;  if (c <= 'ਹ') return true; 
/*  882 */     if (c < 'ਖ਼') return false;  if (c <= 'ੜ') return true; 
/*  883 */     if (c == 'ਫ਼') return true; 
/*  884 */     if (c < 'ੲ') return false;  if (c <= 'ੴ') return true; 
/*  885 */     if (c < 'અ') return false;  if (c <= 'ઋ') return true; 
/*  886 */     if (c == 'ઍ') return true; 
/*  887 */     if (c < 'એ') return false;  if (c <= 'ઑ') return true; 
/*  888 */     if (c < 'ઓ') return false;  if (c <= 'ન') return true; 
/*  889 */     if (c < 'પ') return false;  if (c <= 'ર') return true; 
/*  890 */     if (c < 'લ') return false;  if (c <= 'ળ') return true; 
/*  891 */     if (c < 'વ') return false;  if (c <= 'હ') return true; 
/*  892 */     if (c == 'ઽ') return true; 
/*  893 */     if (c == 'ૠ') return true; 
/*  894 */     if (c < 'ଅ') return false;  if (c <= 'ଌ') return true; 
/*  895 */     if (c < 'ଏ') return false;  if (c <= 'ଐ') return true; 
/*  896 */     if (c < 'ଓ') return false;  if (c <= 'ନ') return true; 
/*  897 */     if (c < 'ପ') return false;  if (c <= 'ର') return true; 
/*  898 */     if (c < 'ଲ') return false;  if (c <= 'ଳ') return true; 
/*  899 */     if (c < 'ଶ') return false;  if (c <= 'ହ') return true; 
/*  900 */     if (c == 'ଽ') return true; 
/*  901 */     if (c < 'ଡ଼') return false;  if (c <= 'ଢ଼') return true; 
/*  902 */     if (c < 'ୟ') return false;  if (c <= 'ୡ') return true; 
/*  903 */     if (c < 'அ') return false;  if (c <= 'ஊ') return true; 
/*  904 */     if (c < 'எ') return false;  if (c <= 'ஐ') return true; 
/*  905 */     if (c < 'ஒ') return false;  if (c <= 'க') return true; 
/*  906 */     if (c < 'ங') return false;  if (c <= 'ச') return true; 
/*  907 */     if (c == 'ஜ') return true; 
/*  908 */     if (c < 'ஞ') return false;  if (c <= 'ட') return true; 
/*  909 */     if (c < 'ண') return false;  if (c <= 'த') return true; 
/*  910 */     if (c < 'ந') return false;  if (c <= 'ப') return true; 
/*  911 */     if (c < 'ம') return false;  if (c <= 'வ') return true; 
/*  912 */     if (c < 'ஷ') return false;  if (c <= 'ஹ') return true; 
/*  913 */     if (c < 'అ') return false;  if (c <= 'ఌ') return true; 
/*  914 */     if (c < 'ఎ') return false;  if (c <= 'ఐ') return true; 
/*  915 */     if (c < 'ఒ') return false;  if (c <= 'న') return true; 
/*  916 */     if (c < 'ప') return false;  if (c <= 'ళ') return true; 
/*  917 */     if (c < 'వ') return false;  if (c <= 'హ') return true; 
/*  918 */     if (c < 'ౠ') return false;  if (c <= 'ౡ') return true; 
/*  919 */     if (c < 'ಅ') return false;  if (c <= 'ಌ') return true; 
/*  920 */     if (c < 'ಎ') return false;  if (c <= 'ಐ') return true; 
/*  921 */     if (c < 'ಒ') return false;  if (c <= 'ನ') return true; 
/*  922 */     if (c < 'ಪ') return false;  if (c <= 'ಳ') return true; 
/*  923 */     if (c < 'ವ') return false;  if (c <= 'ಹ') return true; 
/*  924 */     if (c == 'ೞ') return true; 
/*  925 */     if (c < 'ೠ') return false;  if (c <= 'ೡ') return true; 
/*  926 */     if (c < 'അ') return false;  if (c <= 'ഌ') return true; 
/*  927 */     if (c < 'എ') return false;  if (c <= 'ഐ') return true; 
/*  928 */     if (c < 'ഒ') return false;  if (c <= 'ന') return true; 
/*  929 */     if (c < 'പ') return false;  if (c <= 'ഹ') return true; 
/*  930 */     if (c < 'ൠ') return false;  if (c <= 'ൡ') return true; 
/*  931 */     if (c < 'ก') return false;  if (c <= 'ฮ') return true; 
/*  932 */     if (c == 'ะ') return true; 
/*  933 */     if (c < 'า') return false;  if (c <= 'ำ') return true; 
/*  934 */     if (c < 'เ') return false;  if (c <= 'ๅ') return true; 
/*  935 */     if (c < 'ກ') return false;  if (c <= 'ຂ') return true; 
/*  936 */     if (c == 'ຄ') return true; 
/*  937 */     if (c < 'ງ') return false;  if (c <= 'ຈ') return true; 
/*  938 */     if (c == 'ຊ') return true; 
/*  939 */     if (c == 'ຍ') return true; 
/*  940 */     if (c < 'ດ') return false;  if (c <= 'ທ') return true; 
/*  941 */     if (c < 'ນ') return false;  if (c <= 'ຟ') return true; 
/*  942 */     if (c < 'ມ') return false;  if (c <= 'ຣ') return true; 
/*  943 */     if (c == 'ລ') return true; 
/*  944 */     if (c == 'ວ') return true; 
/*  945 */     if (c < 'ສ') return false;  if (c <= 'ຫ') return true; 
/*  946 */     if (c < 'ອ') return false;  if (c <= 'ຮ') return true; 
/*  947 */     if (c == 'ະ') return true; 
/*  948 */     if (c < 'າ') return false;  if (c <= 'ຳ') return true; 
/*  949 */     if (c == 'ຽ') return true; 
/*  950 */     if (c < 'ເ') return false;  if (c <= 'ໄ') return true; 
/*  951 */     if (c < 'ཀ') return false;  if (c <= 'ཇ') return true; 
/*  952 */     if (c < 'ཉ') return false;  if (c <= 'ཀྵ') return true; 
/*  953 */     if (c < 'Ⴀ') return false;  if (c <= 'Ⴥ') return true; 
/*  954 */     if (c < 'ა') return false;  if (c <= 'ჶ') return true; 
/*  955 */     if (c == 'ᄀ') return true; 
/*  956 */     if (c < 'ᄂ') return false;  if (c <= 'ᄃ') return true; 
/*  957 */     if (c < 'ᄅ') return false;  if (c <= 'ᄇ') return true; 
/*  958 */     if (c == 'ᄉ') return true; 
/*  959 */     if (c < 'ᄋ') return false;  if (c <= 'ᄌ') return true; 
/*  960 */     if (c < 'ᄎ') return false;  if (c <= 'ᄒ') return true; 
/*  961 */     if (c == 'ᄼ') return true; 
/*  962 */     if (c == 'ᄾ') return true; 
/*  963 */     if (c == 'ᅀ') return true; 
/*  964 */     if (c == 'ᅌ') return true; 
/*  965 */     if (c == 'ᅎ') return true; 
/*  966 */     if (c == 'ᅐ') return true; 
/*  967 */     if (c < 'ᅔ') return false;  if (c <= 'ᅕ') return true; 
/*  968 */     if (c == 'ᅙ') return true; 
/*  969 */     if (c < 'ᅟ') return false;  if (c <= 'ᅡ') return true; 
/*  970 */     if (c == 'ᅣ') return true; 
/*  971 */     if (c == 'ᅥ') return true; 
/*  972 */     if (c == 'ᅧ') return true; 
/*  973 */     if (c == 'ᅩ') return true; 
/*  974 */     if (c < 'ᅭ') return false;  if (c <= 'ᅮ') return true; 
/*  975 */     if (c < 'ᅲ') return false;  if (c <= 'ᅳ') return true; 
/*  976 */     if (c == 'ᅵ') return true; 
/*  977 */     if (c == 'ᆞ') return true; 
/*  978 */     if (c == 'ᆨ') return true; 
/*  979 */     if (c == 'ᆫ') return true; 
/*  980 */     if (c < 'ᆮ') return false;  if (c <= 'ᆯ') return true; 
/*  981 */     if (c < 'ᆷ') return false;  if (c <= 'ᆸ') return true; 
/*  982 */     if (c == 'ᆺ') return true; 
/*  983 */     if (c < 'ᆼ') return false;  if (c <= 'ᇂ') return true; 
/*  984 */     if (c == 'ᇫ') return true; 
/*  985 */     if (c == 'ᇰ') return true; 
/*  986 */     if (c == 'ᇹ') return true; 
/*  987 */     if (c < 'Ḁ') return false;  if (c <= 'ẛ') return true; 
/*  988 */     if (c < 'Ạ') return false;  if (c <= 'ỹ') return true; 
/*  989 */     if (c < 'ἀ') return false;  if (c <= 'ἕ') return true; 
/*  990 */     if (c < 'Ἐ') return false;  if (c <= 'Ἕ') return true; 
/*  991 */     if (c < 'ἠ') return false;  if (c <= 'ὅ') return true; 
/*  992 */     if (c < 'Ὀ') return false;  if (c <= 'Ὅ') return true; 
/*  993 */     if (c < 'ὐ') return false;  if (c <= 'ὗ') return true; 
/*  994 */     if (c == 'Ὑ') return true; 
/*  995 */     if (c == 'Ὓ') return true; 
/*  996 */     if (c == 'Ὕ') return true; 
/*  997 */     if (c < 'Ὗ') return false;  if (c <= 'ώ') return true; 
/*  998 */     if (c < 'ᾀ') return false;  if (c <= 'ᾴ') return true; 
/*  999 */     if (c < 'ᾶ') return false;  if (c <= 'ᾼ') return true; 
/* 1000 */     if (c == 'ι') return true; 
/* 1001 */     if (c < 'ῂ') return false;  if (c <= 'ῄ') return true; 
/* 1002 */     if (c < 'ῆ') return false;  if (c <= 'ῌ') return true; 
/* 1003 */     if (c < 'ῐ') return false;  if (c <= 'ΐ') return true; 
/* 1004 */     if (c < 'ῖ') return false;  if (c <= 'Ί') return true; 
/* 1005 */     if (c < 'ῠ') return false;  if (c <= 'Ῥ') return true; 
/* 1006 */     if (c < 'ῲ') return false;  if (c <= 'ῴ') return true; 
/* 1007 */     if (c < 'ῶ') return false;  if (c <= 'ῼ') return true; 
/* 1008 */     if (c == 'Ω') return true; 
/* 1009 */     if (c < 'K') return false;  if (c <= 'Å') return true; 
/* 1010 */     if (c == '℮') return true; 
/* 1011 */     if (c < 'ↀ') return false;  if (c <= 'ↂ') return true; 
/* 1012 */     if (c == '〇') return true; 
/* 1013 */     if (c < '〡') return false;  if (c <= '〩') return true; 
/* 1014 */     if (c < 'ぁ') return false;  if (c <= 'ゔ') return true; 
/* 1015 */     if (c < 'ァ') return false;  if (c <= 'ヺ') return true; 
/* 1016 */     if (c < 'ㄅ') return false;  if (c <= 'ㄬ') return true; 
/* 1017 */     if (c < '一') return false;  if (c <= '龥') return true; 
/* 1018 */     if (c < '가') return false;  if (c <= '힣') return true;
/*      */     
/* 1020 */     return false;
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
/*      */   public static boolean isXMLCombiningChar(char c) {
/* 1035 */     if (c < '̀') return false;  if (c <= 'ͅ') return true; 
/* 1036 */     if (c < '͠') return false;  if (c <= '͡') return true; 
/* 1037 */     if (c < '҃') return false;  if (c <= '҆') return true; 
/* 1038 */     if (c < '֑') return false;  if (c <= '֡') return true;
/*      */     
/* 1040 */     if (c < '֣') return false;  if (c <= 'ֹ') return true; 
/* 1041 */     if (c < 'ֻ') return false;  if (c <= 'ֽ') return true; 
/* 1042 */     if (c == 'ֿ') return true; 
/* 1043 */     if (c < 'ׁ') return false;  if (c <= 'ׂ') return true;
/*      */     
/* 1045 */     if (c == 'ׄ') return true; 
/* 1046 */     if (c < 'ً') return false;  if (c <= 'ْ') return true; 
/* 1047 */     if (c == 'ٰ') return true; 
/* 1048 */     if (c < 'ۖ') return false;  if (c <= 'ۜ') return true;
/*      */     
/* 1050 */     if (c < '۝') return false;  if (c <= '۟') return true; 
/* 1051 */     if (c < '۠') return false;  if (c <= 'ۤ') return true; 
/* 1052 */     if (c < 'ۧ') return false;  if (c <= 'ۨ') return true;
/*      */     
/* 1054 */     if (c < '۪') return false;  if (c <= 'ۭ') return true; 
/* 1055 */     if (c < 'ँ') return false;  if (c <= 'ः') return true; 
/* 1056 */     if (c == '़') return true; 
/* 1057 */     if (c < 'ा') return false;  if (c <= 'ौ') return true;
/*      */     
/* 1059 */     if (c == '्') return true; 
/* 1060 */     if (c < '॑') return false;  if (c <= '॔') return true; 
/* 1061 */     if (c < 'ॢ') return false;  if (c <= 'ॣ') return true; 
/* 1062 */     if (c < 'ঁ') return false;  if (c <= 'ঃ') return true;
/*      */     
/* 1064 */     if (c == '়') return true; 
/* 1065 */     if (c == 'া') return true; 
/* 1066 */     if (c == 'ি') return true; 
/* 1067 */     if (c < 'ী') return false;  if (c <= 'ৄ') return true; 
/* 1068 */     if (c < 'ে') return false;  if (c <= 'ৈ') return true;
/*      */     
/* 1070 */     if (c < 'ো') return false;  if (c <= '্') return true; 
/* 1071 */     if (c == 'ৗ') return true; 
/* 1072 */     if (c < 'ৢ') return false;  if (c <= 'ৣ') return true; 
/* 1073 */     if (c == 'ਂ') return true; 
/* 1074 */     if (c == '਼') return true;
/*      */     
/* 1076 */     if (c == 'ਾ') return true; 
/* 1077 */     if (c == 'ਿ') return true; 
/* 1078 */     if (c < 'ੀ') return false;  if (c <= 'ੂ') return true; 
/* 1079 */     if (c < 'ੇ') return false;  if (c <= 'ੈ') return true;
/*      */     
/* 1081 */     if (c < 'ੋ') return false;  if (c <= '੍') return true; 
/* 1082 */     if (c < 'ੰ') return false;  if (c <= 'ੱ') return true; 
/* 1083 */     if (c < 'ઁ') return false;  if (c <= 'ઃ') return true; 
/* 1084 */     if (c == '઼') return true;
/*      */     
/* 1086 */     if (c < 'ા') return false;  if (c <= 'ૅ') return true; 
/* 1087 */     if (c < 'ે') return false;  if (c <= 'ૉ') return true; 
/* 1088 */     if (c < 'ો') return false;  if (c <= '્') return true;
/*      */     
/* 1090 */     if (c < 'ଁ') return false;  if (c <= 'ଃ') return true; 
/* 1091 */     if (c == '଼') return true; 
/* 1092 */     if (c < 'ା') return false;  if (c <= 'ୃ') return true; 
/* 1093 */     if (c < 'େ') return false;  if (c <= 'ୈ') return true;
/*      */     
/* 1095 */     if (c < 'ୋ') return false;  if (c <= '୍') return true; 
/* 1096 */     if (c < 'ୖ') return false;  if (c <= 'ୗ') return true; 
/* 1097 */     if (c < 'ஂ') return false;  if (c <= 'ஃ') return true;
/*      */     
/* 1099 */     if (c < 'ா') return false;  if (c <= 'ூ') return true; 
/* 1100 */     if (c < 'ெ') return false;  if (c <= 'ை') return true; 
/* 1101 */     if (c < 'ொ') return false;  if (c <= '்') return true; 
/* 1102 */     if (c == 'ௗ') return true;
/*      */     
/* 1104 */     if (c < 'ఁ') return false;  if (c <= 'ః') return true; 
/* 1105 */     if (c < 'ా') return false;  if (c <= 'ౄ') return true; 
/* 1106 */     if (c < 'ె') return false;  if (c <= 'ై') return true;
/*      */     
/* 1108 */     if (c < 'ొ') return false;  if (c <= '్') return true; 
/* 1109 */     if (c < 'ౕ') return false;  if (c <= 'ౖ') return true; 
/* 1110 */     if (c < 'ಂ') return false;  if (c <= 'ಃ') return true;
/*      */     
/* 1112 */     if (c < 'ಾ') return false;  if (c <= 'ೄ') return true; 
/* 1113 */     if (c < 'ೆ') return false;  if (c <= 'ೈ') return true; 
/* 1114 */     if (c < 'ೊ') return false;  if (c <= '್') return true;
/*      */     
/* 1116 */     if (c < 'ೕ') return false;  if (c <= 'ೖ') return true; 
/* 1117 */     if (c < 'ം') return false;  if (c <= 'ഃ') return true; 
/* 1118 */     if (c < 'ാ') return false;  if (c <= 'ൃ') return true;
/*      */     
/* 1120 */     if (c < 'െ') return false;  if (c <= 'ൈ') return true; 
/* 1121 */     if (c < 'ൊ') return false;  if (c <= '്') return true; 
/* 1122 */     if (c == 'ൗ') return true; 
/* 1123 */     if (c == 'ั') return true;
/*      */     
/* 1125 */     if (c < 'ิ') return false;  if (c <= 'ฺ') return true; 
/* 1126 */     if (c < '็') return false;  if (c <= '๎') return true; 
/* 1127 */     if (c == 'ັ') return true; 
/* 1128 */     if (c < 'ິ') return false;  if (c <= 'ູ') return true;
/*      */     
/* 1130 */     if (c < 'ົ') return false;  if (c <= 'ຼ') return true; 
/* 1131 */     if (c < '່') return false;  if (c <= 'ໍ') return true; 
/* 1132 */     if (c < '༘') return false;  if (c <= '༙') return true; 
/* 1133 */     if (c == '༵') return true;
/*      */     
/* 1135 */     if (c == '༷') return true; 
/* 1136 */     if (c == '༹') return true; 
/* 1137 */     if (c == '༾') return true; 
/* 1138 */     if (c == '༿') return true; 
/* 1139 */     if (c < 'ཱ') return false;  if (c <= '྄') return true;
/*      */     
/* 1141 */     if (c < '྆') return false;  if (c <= 'ྋ') return true; 
/* 1142 */     if (c < 'ྐ') return false;  if (c <= 'ྕ') return true; 
/* 1143 */     if (c == 'ྗ') return true; 
/* 1144 */     if (c < 'ྙ') return false;  if (c <= 'ྭ') return true;
/*      */     
/* 1146 */     if (c < 'ྱ') return false;  if (c <= 'ྷ') return true; 
/* 1147 */     if (c == 'ྐྵ') return true; 
/* 1148 */     if (c < '⃐') return false;  if (c <= '⃜') return true; 
/* 1149 */     if (c == '⃡') return true;
/*      */     
/* 1151 */     if (c < '〪') return false;  if (c <= '〯') return true; 
/* 1152 */     if (c == '゙') return true; 
/* 1153 */     if (c == '゚') return true;
/*      */     
/* 1155 */     return false;
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
/*      */   public static boolean isXMLExtender(char c) {
/* 1169 */     if (c < '¶') return false;
/*      */ 
/*      */     
/* 1172 */     if (c == '·') return true; 
/* 1173 */     if (c == 'ː') return true; 
/* 1174 */     if (c == 'ˑ') return true; 
/* 1175 */     if (c == '·') return true; 
/* 1176 */     if (c == 'ـ') return true; 
/* 1177 */     if (c == 'ๆ') return true; 
/* 1178 */     if (c == 'ໆ') return true; 
/* 1179 */     if (c == '々') return true;
/*      */     
/* 1181 */     if (c < '〱') return false;  if (c <= '〵') return true; 
/* 1182 */     if (c < 'ゝ') return false;  if (c <= 'ゞ') return true; 
/* 1183 */     if (c < 'ー') return false;  if (c <= 'ヾ') return true;
/*      */     
/* 1185 */     return false;
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
/*      */   public static boolean isXMLDigit(char c) {
/* 1199 */     if (c < '0') return false;  if (c <= '9') return true; 
/* 1200 */     if (c < '٠') return false;  if (c <= '٩') return true; 
/* 1201 */     if (c < '۰') return false;  if (c <= '۹') return true; 
/* 1202 */     if (c < '०') return false;  if (c <= '९') return true;
/*      */     
/* 1204 */     if (c < '০') return false;  if (c <= '৯') return true; 
/* 1205 */     if (c < '੦') return false;  if (c <= '੯') return true; 
/* 1206 */     if (c < '૦') return false;  if (c <= '૯') return true;
/*      */     
/* 1208 */     if (c < '୦') return false;  if (c <= '୯') return true; 
/* 1209 */     if (c < '௧') return false;  if (c <= '௯') return true; 
/* 1210 */     if (c < '౦') return false;  if (c <= '౯') return true;
/*      */     
/* 1212 */     if (c < '೦') return false;  if (c <= '೯') return true; 
/* 1213 */     if (c < '൦') return false;  if (c <= '൯') return true; 
/* 1214 */     if (c < '๐') return false;  if (c <= '๙') return true;
/*      */     
/* 1216 */     if (c < '໐') return false;  if (c <= '໙') return true; 
/* 1217 */     if (c < '༠') return false;  if (c <= '༩') return true;
/*      */     
/* 1219 */     return false;
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
/*      */   public static boolean isXMLWhitespace(char c) {
/* 1231 */     if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
/* 1232 */       return true;
/*      */     }
/* 1234 */     return false;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/Verifier.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */