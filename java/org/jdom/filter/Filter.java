package org.jdom.filter;

import java.io.Serializable;

public interface Filter extends Serializable {
  boolean matches(Object paramObject);
}


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/filter/Filter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */