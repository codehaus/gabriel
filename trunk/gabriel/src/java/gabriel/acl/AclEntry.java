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

package gabriel.acl;

import gabriel.Permission;
import gabriel.Principal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AclEntry which maps a principal to permissions. Entries can
 * be negative which will cancel the permission for a principal.
 *
 * @author Stephan J. Schmidt
 * @version $Id: AclEntry.java,v 1.3 2004-07-12 12:27:33 stephan Exp $
 */

public class AclEntry {
  private List permissions;
  private Principal principal;
  private boolean negative;

  /**
   * Creates a new AclEntry from a principal.
   *
   * @param principal Principal which this AclEntry is about
   */
  public AclEntry(Principal principal) {
    this.principal = principal;
    this.permissions = new ArrayList();
    negative = false;
  }

  /**
   * Set principal of the entry.
   *
   * @param principal New principal
   */
  public void setPrincipal(Principal principal) {
    this.principal = principal;
  }

  /**
   * Get principal from entry.
   *
   * @return Principal which is assigned to permission
   */
  public Principal getPrincipal() {
    return principal;
  }

  /**
   * Permissions in this entry are negative.
   */
  public void setNegativePermissions() {
    negative = true;
  }

  /**
   * Check if the permissions in this entry are negative.
   *
   * @return true if the permissions are negative
   */
  public boolean isNegative() {
    return negative;
  }

  /**
   * Add a permission to the entry.
   * Shouldn't this also check for an owner?
   *
   * @param permission Permission to add
   * @return true if permission was added
   */

  public boolean addPermission(Permission permission) {
    if (!permissions.contains(permission)) {
      return permissions.add(permission);
    }
    return true;
  }

  /**
   * Remove permission from the entry.
   *
   * @param permission Permission to remove
   * @return true if permission was removed
   */
  public boolean removePermission(Permission permission) {
    return permissions.remove(permission);
  }

  /**
   * Check if the AclEntry has the permission.
   *
   * @param checkPermission Permission to check
   * @return true if AclEntry has the permission
   */
  public boolean checkPermission(Permission checkPermission) {
    boolean hasPermission = false;
    Iterator iterator = permissions.iterator();
    while (iterator.hasNext()) {
      Permission permission = (Permission) iterator.next();
      if (permission.implies(checkPermission)) {
        hasPermission = true;
      }
    }
    return hasPermission;
  }

  /**
   * Return all permission for this entry.
   *
   * @return List with permissions assigned to this entry
   */
  public List permissions() {
    return permissions;
  }

  public String toString() {
    return (isNegative() ? "-" : "") + "(" + principal + ": " + permissions + ")";
  }
}