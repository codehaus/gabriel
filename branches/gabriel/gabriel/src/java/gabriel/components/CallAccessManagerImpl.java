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
import gabriel.Permission;

import java.util.*;

/**
 * CallAccessManager checks if a client is allowed to execute a method
 * by mapping method names to permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: CallAccessManagerImpl.java,v 1.1.1.1 2004-06-16 07:56:38 stephan Exp $
 */

public class CallAccessManagerImpl implements CallAccessManager {
  private AccessManager accessManager;
  private Map methodMap;

  public CallAccessManagerImpl(AccessManager accessManager) {
    this.accessManager = accessManager;
    methodMap = new HashMap();
  }

  /**
   * Add a list of method names for a permission.
   * Method names should follow "class.methodName"
   *
   * @param permission Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */
  public void addMethods(Permission permission, List methodNames) {
    Iterator iterator = methodNames.iterator();
    while (iterator.hasNext()) {
      String methodName = (String) iterator.next();
      Set permissions = null;
      if (methodMap.containsKey(methodName)) {
        permissions = (Set) methodMap.get(methodName);
        permissions.add(permission);
      } else {
        permissions = new HashSet();
        permissions.add(permission);
        methodMap.put(methodName, permissions);
      }
    }
  }

    /**
   * Add an array of method names for a permission.
   * Convinience method.
   *
   * @param permission Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */
  public void addMethods(Permission permission, String[] methodNames) {
    addMethods(permission, Arrays.asList(methodNames));
  }

   /**
   * Get all permissions from which one is needed to execute the method
   *
   * @param methodName Method name to get the permission from
   * @return Set of permissions which restrict method execution
   */
  public Set getPermissions(String methodName) {
    if (methodMap.containsKey(methodName)) {
      return Collections.unmodifiableSet((Set) methodMap.get(methodName));
    } else {
      return new HashSet();
    }
  }

    /**
   * Check if a principal can execute a method.
   * The principal needs one permission for the method to
   * be allowed to execute it.
   *
   * @param principal Principal to check access to the method
   * @param methodName Name of the method to check
   * @return true if principal is allowed to execute the method
   */
  public boolean checkPermission(Principal principal, String methodName) {
    // get all Permissions for methodName
    // check all Permissions with accessManager and principal
    Set permissions = (Set) methodMap.get(methodName);
    Iterator iterator = permissions.iterator();
    boolean hasPermission = false;

    while (iterator.hasNext()) {
      Permission permission = (Permission) iterator.next();
      hasPermission = hasPermission || accessManager.checkPermission(principal, permission);
    }
    return hasPermission;
  }
}
