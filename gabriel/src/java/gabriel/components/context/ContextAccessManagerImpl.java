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
import gabriel.Principal;
import gabriel.components.AccessManager;

import java.util.List;
import java.util.Set;

/**
 * ContextAccessManager checks for access rights with permissions
 * in a certain context.
 *
 * @author Stephan J. Schmidt
 * @version $Id: ContextAccessManagerImpl.java,v 1.3 2004-06-24 10:22:44 stephan Exp $
 */

public class ContextAccessManagerImpl implements ContextAccessManager {
  private AccessManager accessManager;

  /**
   * Default constructor.
   */
  public ContextAccessManagerImpl(AccessManager accessManager) {
    this.accessManager = accessManager;
  }

  /**
   * Delegate
   */
  public boolean checkPermission(Set principals, Permission permission) {
    return accessManager.checkPermission(principals, permission);
  }

  /**
   * Delegate
   */
  public void addPermission(Principal principal, Permission permission) {
    accessManager.addPermission(principal, permission);
  }

  /**
   * Delegate
   */
  public void addPermission(Principal principal, List permissions) {
    accessManager.addPermission(principal, permissions);
  }

  /**
   * Check if a principal posseses the permission
   * Perhaps return int with -1,0,1 for denied,neutral,allow
   * The context could contain ownership and resources
   * so ContextAccessManager modifies the principals.
   *
   * @param principals Set of principal to check permission for
   * @param permission Permission to check
   * @param context    AccessContext to consider when checking for a permission
   * @return true if principal has permission
   */
  public boolean checkPermission(Set principals, Permission permission, AccessContext context) {
    principals = context.modifyPrincipals(principals);
    return accessManager.checkPermission(principals, permission);
  }
}
