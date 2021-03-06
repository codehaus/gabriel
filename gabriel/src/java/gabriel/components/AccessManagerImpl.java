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
import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.acl.AclEntry;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * AccessManager checks for access rights with permissions.
 * Simple implementation of AccessManager. This implementation
 * uses ACL lists with entries to manage mappings from principals
 * to permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: AccessManagerImpl.java,v 1.6 2005-05-11 14:53:39 stephan Exp $
 */
public class AccessManagerImpl implements AccessManager {
  private Acl acl;
  private Principal owner;
  private AclStore store;

  /**
   * Default constructor. Probably should manage several
   * ACL lists by name with one default list "Default".
   */
  public AccessManagerImpl(AclStore store) {
    this.store = store;

    owner = new Principal("owner");
    acl = store.getAcl(owner, getDefaultAclName());
  }

  protected String getDefaultAclName() {
    return "access";
  }

  public void start() {
    // do nothing
  }

  public void stop() {
    store.putAcl(getDefaultAclName(), acl);
  }

  /**
   * Check if a principal posseses the permission.
   * Perhaps return int with -1,0,1 for denied,neutral,allow.
   *
   * @param principals Set of principal to check permission for
   * @param permission Permission to check
   * @return true if principal has permission
   */
  public boolean checkPermission(Set principals, Permission permission) {
    return acl.checkPermission(principals, permission);
  }

  /**
   * Add an AclEntry to the Acl. Adds
   * dependency on AclEntry.
   *
   * @param entry AclEntry to add
   */
  public void addEntry(AclEntry entry) {
    acl.addEntry(owner, entry);
  }

  /**
   * Add a list of permissions for a principal.
   *
   * @param principal   Principal to give permissions
   * @param permissions List of permissions to give
   */
  public void addPermission(Principal principal, List permissions) {
    AclEntry entry = new AclEntry(principal);

    Iterator iterator = permissions.iterator();
    while (iterator.hasNext()) {
      Permission permission = (Permission) iterator.next();
      entry.addPermission(permission);
    }
    addEntry(entry);
  }

  /**
   * Add a list of negative permissions for a principal.
   *
   * @param principal   Principal to give permissions
   * @param permissions List of permissions to give
   */
  public void addNegativePermission(Principal principal, List permissions) {
    AclEntry entry = new AclEntry(principal);
    entry.setNegativePermissions();

    Iterator iterator = permissions.iterator();
    while (iterator.hasNext()) {
      Permission permission = (Permission) iterator.next();
      entry.addPermission(permission);
    }
    addEntry(entry);
  }


  /**
   * Add a permission for a principal.
   *
   * @param principal  Principal to give permission
   * @param permission Permission to give
   */
  public void addPermission(Principal principal, Permission permission) {
    AclEntry entry = new AclEntry(principal);
    entry.addPermission(permission);
    addEntry(entry);
  }
}
