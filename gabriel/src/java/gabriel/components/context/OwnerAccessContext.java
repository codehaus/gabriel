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

package gabriel.components.context;

import gabriel.Principal;

import java.util.Set;


/**
 * AccessContext which adds a Owner principal to the set if
 * the owner of an object is in the original set.
 *
 * @author Stephan J. Schmidt
 * @version $Id: OwnerAccessContext.java,v 1.1 2004-06-24 07:26:21 stephan Exp $
 */
public class OwnerAccessContext implements AccessContext {
  private static Principal owner = new Principal("Owner");
  private Ownable owned;

  /**
   * Creates OwnerAccessContex from Ownable.
   *
   * @param owned An object wih an owner
   */
  public OwnerAccessContext(Ownable owned) {
    this.owned = owned;
  }

  /**
   * Modify the list of principals depending on the context.
   * Set is modified by method. Perhaps a new set
   * should be created.
   *
   * @param principals Original principal list
   * @return modified principal list
   */
  public Set modifyPrincipals(Set principals) {
    if (principals.contains(owner)) return principals;

    if (principals.contains(owned.getOwner())) {
      principals.add(owner);
    }
    return principals;
  }
}
