package org.jdom.adapters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.jdom.DocType;
import org.jdom.JDOMException;
import org.w3c.dom.Document;

public interface DOMAdapter {
  Document getDocument(File paramFile, boolean paramBoolean) throws IOException, JDOMException;
  
  Document getDocument(InputStream paramInputStream, boolean paramBoolean) throws IOException, JDOMException;
  
  Document createDocument() throws JDOMException;
  
  Document createDocument(DocType paramDocType) throws JDOMException;
}


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/adapters/DOMAdapter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */