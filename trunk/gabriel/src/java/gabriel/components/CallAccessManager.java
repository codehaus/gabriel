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

import java.util.List;
import java.util.Set;

/**
 * CallAccessManager checks if a client is allowed to execute a method
 * by mapping method names to permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: CallAccessManager.java,v 1.2 2004-06-24 07:26:21 stephan Exp $
 */
public interface CallAccessManager {

  /**
   * Add a list of method names for a permission.
   * Method names should follow "class.methodName".
   *
   * @param permission  Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */
  public void addMethods(Permission permission, List methodNames);

  /**
   * Add a method for a permission. The method is specified
   * by a class and the method name.
   * <p/>
   * Example: addMethod(new Permission("POST"), Blog.class, "post");
   *
   * @param permission Permission which is needed to execute method
   * @param klass      Class with the method
   * @param method     Name of the method to restirct access to
   */
  public void addMethod(Permission permission, Class klass, String method);

  /**
   * Add a method for a permission.
   * Method names should follow "class.methodName"
   *
   * @param permission Permission which is needed to execute method
   * @param methodName Name of method with access restrictions
   */
  public void addMethod(Permission permission, String methodName);

  /**
   * Add an array of method names for a permission.
   * Convinience method.
   *
   * @param permission  Permission which is needed to execute method
   * @param methodNames Names of methods with access restrictions
   */

  public void addMethods(Permission permission, String[] methodNames);

  /**
   * Get all permissions from which one is needed to execute the method.
   *
   * @param methodName Method name to get the permission from
   * @return Set of permissions which restrict method execution
   */
  public Set getPermissions(String methodName);

  /**
   * Check if a principal can execute a method.
   * The principal needs one permission for the method to
   * be allowed to execute it.
   *
   * @param principals Set of principals to check access to the method
   * @param methodName Name of the method to check
   * @return true if principal is allowed to execute the method
   */
  public boolean checkPermission(Set principals, String methodName);

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
  public boolean checkPermission(Set principals, Class klass, String method);

}
