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

import gabriel.Principal;
import gabriel.Permission;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Access control list for managing permissions. ACLs contain
 * entries which map principals to permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: Acl.java,v 1.1.1.1 2004-06-16 07:56:38 stephan Exp $
 */

public class Acl {
  private String name;
  private List entries;
  private Principal owner;

  public Acl(Principal owner, String name) {
    entries = new ArrayList();
    this.name = name;
    this.owner = owner;
  }

  /**
   * Set the name for the ACL.
   *
   * @param owner Principal which owns the ACL
   * @param name New name of the ACL
   * @throws SecurityException
   */
  public void setName(Principal owner, String name) throws SecurityException {
    if (owner != this.owner) throw new SecurityException(owner + " is not owner of " + name);

    this.name = name;
  }

  /**
   * Return name for the ACL
   *
   * @return Name of the ACL
   */
  public String getName() {
    return name;
  }

  /**
   * Add an AclEntry for the ACL.
   *
   * @param owner Principal which owns the ACL
   * @param aclEntry AclEntry to add
   * @return true if AclEntry was added
   * @throws SecurityException
   */
  public boolean addEntry(Principal owner, AclEntry aclEntry) throws SecurityException {
    if (owner != this.owner) throw new SecurityException(owner + " is not owner of " + name);

    return entries.add(aclEntry);
  }

  /**
   * Remove an AclEntry from the ACL
   *
   * @param owner Principal which owns the ACL
   * @param aclEntry AclEntry to remove
   * @return true if AclEntry was removed
   * @throws SecurityException
   */
  public boolean removeEntry(Principal owner, AclEntry aclEntry) throws SecurityException {
    if (owner != this.owner) throw new SecurityException(owner + " is not owner of " + name);

    return entries.remove(aclEntry);
  }

//  public List getPermissions(Principal principal) {
//    // Find all entries from entries.
//    // Perhaps keep aclentries sorted by principal
//    return null;
//  }

  /**
   * Return all AclEntries
   *
   * @return List of AclEntries
   */
  public List entries() {
    return entries;
  }

  /**
   * Check if the prinicpal has the permission
   *
   * @param principal Principal to check permission for
   * @param checkPermission Permission to check
   * @return true if principal has permission
   */
  public boolean checkPermission(Principal principal, Permission checkPermission) {
    boolean hasPermission = false;
    boolean hasNotPermission = false;

    Iterator iterator = entries.iterator();
    while (iterator.hasNext()) {
      AclEntry entry = (AclEntry) iterator.next();
      if (entry.getPrincipal().equals(principal)
          && entry.checkPermission(checkPermission)) {
        if (entry.isNegative()) {
          hasNotPermission = true;
        } else {
          hasPermission = true;
        }
      }
    }
    // hasNotPermission cancels hasPermission
    return hasPermission && (! hasNotPermission);
  }

  /**
   * Set new owner for ACL
   *
   * @param owner Principal which currently owns the ACL
   * @param newOwner Principal to become new owner
   * @throws SecurityException
   */
  public void setOwner(Principal owner, Principal newOwner) throws SecurityException {
    if (owner != this.owner) throw new SecurityException(owner + " is not owner of " + name);

    this.owner = owner;
  }

  /**
   * Check if principal is owner of the ACL
   *
   * @param onwer Principal to check ownership for
   * @return true if principal is owner
   */
  public boolean isOwner(Principal onwer) {
    if (owner != this.owner) return false;
    return true;
  }
}