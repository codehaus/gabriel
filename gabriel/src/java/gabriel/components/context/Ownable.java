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

/**
 * Addes ownership to an object.
 *
 * @author stephan
 * @version $id$
 */
public interface Ownable {
  /**
   * Set new owner.
   *
   * @param owner Principal who is new owner
   */
  public void setOwner(Principal owner);

  /**
   * Return current owner.
   *
   * @return Principal who is current owner
   */
  public Principal getOwner();

  /**
   * Check if principal is owner.
   *
   * @param principal Principal to check
   * @return true if principal is owner
   */
  public boolean isOwner(Principal principal);
}
