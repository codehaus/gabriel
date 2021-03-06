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

import gabriel.components.MethodAccessManager;

import java.util.Set;

/**
 * MethodAccessManager checks if a client is allowed to execute a method
 * by mapping method names to permissions. Principals may
 * be modified by context.
 *
 * @author Stephan J. Schmidt
 * @version $Id: ContextMethodAccessManager.java,v 1.1 2004-06-24 12:37:49 stephan Exp $
 */
public interface ContextMethodAccessManager extends MethodAccessManager {
  /**
   * Check if a principal can execute a method.
   * The principal needs one permission for the method to
   * be allowed to execute it. The principals are possibly
   * modified by the context.
   *
   * @param principals Set of principals to check access to the method
   * @param methodName Name of the method to check
   * @param context    AccessContext which modifies the set og principals
   * @return true if principal is allowed to execute the method
   */
  public boolean checkPermission(Set principals, String methodName, AccessContext context);
}
