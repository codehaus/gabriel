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

import gabriel.Permission;

import java.util.*;

import org.picocontainer.Startable;

/**
 * MethodAccessManager checks if a client is allowed to execute a method
 * by mapping method names to permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: MethodAccessManagerImpl.java,v 1.4 2004-07-08 08:07:26 stephan Exp $
 */

public class MethodAccessManagerImpl implements MethodAccessManager, Startable {
  private AccessManager accessManager;
  private MethodStore store;
  private Map methodMap;

  /**
   * Creates MethodAccessManager from an AccessManager.
   *
   * @param accessManager AccessManager to use for permission checking
   */

  public MethodAccessManagerImpl(AccessManager accessManager, MethodStore store) {
    this.store = store;
    this.accessManager = accessManager;
  }

  /**
   * Return the name of the default map.
   *
   * @return the name of the default map
   */
  protected String getDefaultMapName() {
    return "methods";
  }

  /**
   * Start the MethodAccessManager and load the method mapping.
   */
  public void start() {
    methodMap = store.getMap(getDefaultMapName());
  }

  /**
   * Stop the MethodAccessmanager and store the possibly changed method mapping.
   */
  public void stop() {
    store.putMap(getDefaultMapName(), methodMap);
  }

  /**
   * Add a method for a permission. The method is specified
   * by a class and the method name.
   *
   * @param permission Permission which is needed to execute method
   * @param klass      Class with the method
   * @param method     Name of the method to restirct access to
   */
  public void addMethod(Permission permission, Class klass, String method) {
    addMethod(permission, klass.getName() + "." + method);
  }

  /**
   * Add a method for a permission.
   * Method names should follow "class.methodName"
   *
   * @param permission Permission which is needed to execute method
   * @param methodName Name of method with access restrictions
   */

  public void addMethod(Permission permission, String methodName) {
    Set permissions;
    if (methodMap.containsKey(methodName)) {
      permissions = (Set) methodMap.get(methodName);
      permissions.add(permission);
    } else {
      permissions = new HashSet();
      permissions.add(permission);
      methodMap.put(methodName, permissions);
    }
  }

  /**
   * Add a list of method names for a permission.
   * Method names should follow "class.methodName"
   *
   * @param permission  Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */
  public void addMethods(Permission permission, List methodNames) {
    Iterator iterator = methodNames.iterator();
    while (iterator.hasNext()) {
      String methodName = (String) iterator.next();
      addMethod(permission, methodName);
    }
  }

  /**
   * Add an array of method names for a permission.
   * Convinience method.
   *
   * @param permission  Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */
  public void addMethods(Permission permission, String[] methodNames) {
    addMethods(permission, Arrays.asList(methodNames));
  }

  /**
   * Get all permissions from which one is needed to execute the method.
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
   * @param principals Set of principal to check access to the method
   * @param methodName Name of the method to check
   * @return true if principal is allowed to execute the method
   */
  public boolean checkPermission(Set principals, String methodName) {
    // get all Permissions for methodName
    // check all Permissions with accessManager and principal
    if (methodMap.containsKey(methodName)) {
      Set permissions = (Set) methodMap.get(methodName);
      Iterator iterator = permissions.iterator();
      boolean hasPermission = false;

      while (iterator.hasNext()) {
        Permission permission = (Permission) iterator.next();
        hasPermission = hasPermission || accessManager.checkPermission(principals, permission);
      }
      return hasPermission;
    } else {
      // If the method is not in the map, execution is allowed
      return true;
    }
  }

  /**
   * Check if a principal can execute a method.
   * The principal needs one permission for the method to
   * be allowed to execute it.
   *
   * @param principals Set of principals to check access to the method
   * @param klass      Class with the method
   * @param method     Name of the method to check
   * @return true if principal is allowed to execute the method
   */
  public boolean checkPermission(Set principals, Class klass, String method) {
    return checkPermission(principals, klass.getName() + "." + method);
  }
}
