/*
 *      Copyright 2001-2004 Fraunhofer Gesellschaft, Munich, Germany, for its
 *      Fraunhofer Institute Computer Architecture and Software Technology
 *      (FIRST), Berlin, Germany
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package gabriel.components;

import gabriel.Principal;
import gabriel.acl.Acl;

/**
 * AclStore manages Acl lists like creating, removing and persistent storage.
 *
 * @author Stephan J. Schmidt
 * @version $Id: AclStore.java,v 1.2 2004-07-08 08:07:26 stephan Exp $
 */
public interface AclStore {
  /**
   * Get an Acl from the store.
   *
   * @param owner Principal who owns the Acl
   * @param name  Name of the Acl
   * @return Requested Acl
   */
  
  public Acl getAcl(Principal owner, String name);
  /**
   * Store an Acl in the store
   *
   * @param name Name of the Acl
   * @param acl Acl to store
   */
  public void putAcl(String name, Acl acl);
}
