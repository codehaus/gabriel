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
 * Principal.
 * <p/>
 * If we use flat principals without sub-groups, perhaps call
 * this Role.
 *
 * @author Stephan J. Schmidt
 * @version $Id: Principal.java,v 1.3 2004-07-12 12:27:33 stephan Exp $
 */

public class Principal {

  private String name;

  /**
   * Creates a principal with a name.
   *
   * @param name Name of the principal
   */
  public Principal(String name) {
    this.name = name;
  }

  /**
   * Return name of the principal.
   *
   * @return Name of the principal
   */
  public String getName() {
    return name;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Principal)) return false;

    final Principal principal = (Principal) o;

    if (!name.equals(principal.name)) return false;

    return true;
  }

  public int hashCode() {
    return name.hashCode();
  }

  public String toString() {
    return "(" + name + ")";
  }
}