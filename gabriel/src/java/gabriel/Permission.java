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

package gabriel;

/**
 * Permission grants access to the execution of actions.
 *
 * @author Stephan J. Schmidt
 * @version $Id: Permission.java,v 1.2 2004-06-24 07:26:21 stephan Exp $
 */

public class Permission {
  private String name;

  /**
   * Default constructor.
   *
   * @param name Name of the permission
   */
  public Permission(String name) {
    this.name = name;
  }

  /**
   * Checks if one permission implies the other, for example
   * permission to read /config might imply the permission
   * to read /config/special
   * <p/>
   * Permission implies another permission if both are equal.
   * Override this for more complex permissions.
   *
   * @param other Permission to check if it is implied by this permission
   * @return true if this permission implies the passed permission
   */
  public boolean implies(Permission other) {
    return this.equals(other);
  }

  /**
   * Simple equals. Two permissions are equal if their
   * names are equal
   *
   * @param o Permission to check equality
   * @return true if permissions are equal
   */
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Permission)) return false;

    final Permission permission = (Permission) o;

    if (name != null ? !name.equals(permission.name) : permission.name != null) return false;

    return true;
  }

  public int hashCode() {
    return (name != null ? name.hashCode() : 0);
  }
}