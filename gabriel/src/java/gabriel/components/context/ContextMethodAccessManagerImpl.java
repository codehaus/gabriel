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

import gabriel.Permission;
import gabriel.components.MethodAccessManager;

import java.util.List;
import java.util.Set;

/**
 * MethodAccessManager checks if a client is allowed to execute a method
 * by mapping method names to permissions. Principals may
 * be modified by context.
 * 
 * @author Stephan J. Schmidt
 * @version $Id: ContextMethodAccessManagerImpl.java,v 1.2 2004-07-08 08:07:26 stephan Exp $
 */

public class ContextMethodAccessManagerImpl
    implements ContextMethodAccessManager {

  private MethodAccessManager methodAccessManager;

  /**
   * Constructor with IoC dependency on AccessManager.
   *
   * @param methodAccessManager AccessManager which is used to check permissions for principals
   */
  public ContextMethodAccessManagerImpl(MethodAccessManager methodAccessManager) {
    this.methodAccessManager = methodAccessManager;
  }

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
  public boolean checkPermission(Set principals, String methodName, AccessContext context) {
    principals = context.modifyPrincipals(principals);
    return checkPermission(principals, methodName);
  }

  /**
   * Delegate
   */
  public void start() {
    methodAccessManager.start();
  }

  /**
   * Delegate
   */
  public void stop() {
    methodAccessManager.stop();
  }

  /**
   * Delegate
   */
  public void addMethods(Permission permission, List methodNames) {
    methodAccessManager.addMethods(permission, methodNames);
  }

  /**
   * Delegate
   */
  public void addMethod(Permission permission, Class klass, String method) {
    methodAccessManager.addMethod(permission, klass, method);
  }

  /**
   * Delegate
   */
  public void addMethod(Permission permission, String methodName) {
    methodAccessManager.addMethod(permission, methodName);
  }

  /**
   * Delegate
   */
  public void addMethods(Permission permission, String[] methodNames) {
    methodAccessManager.addMethods(permission, methodNames);
  }

  /**
   * Delegate
   */
  public Set getPermissions(String methodName) {
    return methodAccessManager.getPermissions(methodName);
  }

  /**
   * Delegate
   */
  public boolean checkPermission(Set principals, String methodName) {
    return methodAccessManager.checkPermission(principals, methodName);
  }

  /**
   * Delegate
   */
  public boolean checkPermission(Set principals, Class klass, String method) {
    return methodAccessManager.checkPermission(principals, klass, method);
  }
}
