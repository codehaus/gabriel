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

import java.util.HashSet;
import java.util.Set;

/**
 * Subject represents an actor. Each subject has several
 * principals like "Admin", "Editor", "Stephan"
 *
 * @author Stephan J. Schmidt
 * @version $Id: Subject.java,v 1.1 2004-06-24 07:26:21 stephan Exp $
 */

public class Subject {

  private static ThreadLocal subject = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new Subject("Unknown");
    }
  };

  private String name;
  private Set principals;

  /**
   * Set threadlocal with current subject.
   *
   * @param newSubject Current subject
   */
  public static void set(Subject newSubject) {
    subject.set(newSubject);
  }

  /**
   * Get current subject from threadlocal.
   *
   * @return Current subject
   */
  public static Subject get() {
    return (Subject) subject.get();
  }

  /**
   * Creates a new subject with a name.
   *
   * @param name Name of the subject
   */
  public Subject(String name) {
    this.name = name;
    this.principals = new HashSet();
  }

  /**
   * Return name of the subject.
   *
   * @return Name of the subject
   */
  public String getName() {
    return name;
  }

  /**
   * Get all principals the subject possesses.
   *
   * @return Set of principals
   */

  public Set getPrincipals() {
    return principals;
  }

  /**
   * Set new principals.
   *
   * @param principals New set of principals
   */
  public void setPrincipals(Set principals) {
    this.principals = new HashSet(principals);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Subject)) return false;

    final Subject subject = (Subject) o;

    if (!name.equals(subject.name)) return false;
    if (!principals.equals(subject.principals)) return false;

    return true;
  }

  public int hashCode() {
    return name.hashCode() + principals.hashCode();
  }
}