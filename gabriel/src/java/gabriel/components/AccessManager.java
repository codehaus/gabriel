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

import java.util.List;
import java.util.Set;

import org.picocontainer.Startable;

/**
 * AccessManager checks for access rights with permissions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: AccessManager.java,v 1.3 2004-07-08 08:07:25 stephan Exp $
 */
public interface AccessManager extends Startable {
  /**
   * Check if a principal posseses the permission.
   * Perhaps return int with -1,0,1 for denied,neutral,allow.
   *
   * @param principals Set of principal to check permission for
   * @param permission Permission to check
   * @return true if principal has permission
   */
  public boolean checkPermission(Set principals, Permission permission);

  /**
   * Add a permission for a principal.
   *
   * @param principal  Principal to give permission
   * @param permission Permission to give
   */
  public void addPermission(Principal principal, Permission permission);

  /**
   * Add a list of permission for a principal.
   *
   * @param principal   Principal to give permissions
   * @param permissions List of permissions to give
   */
  public void addPermission(Principal principal, List permissions);
}
