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

package gabriel.test;

import gabriel.Permission;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.MockObjectTestCase;

/**
 * @author stephan
 * @version $id$
 */

public class PermissionTest extends MockObjectTestCase {

  public static Test suite() {
    return new TestSuite(PermissionTest.class);
  }

  public void testHashcode() {
    Permission p1 = new Permission("TestPermission");
    Permission p2 = new Permission("TestPermission");

    assertTrue("Hashcode is equal for permissions with same name", p1.hashCode() == p2.hashCode());
  }

  public void testNullHashcode() {
    Permission p1 = new Permission(null);

    assertEquals("Hashcode works with null name", 0, p1.hashCode());
  }

  public void testEquals() {
    Permission p1 = new Permission("TestPermission");
    Permission p2 = new Permission("TestPermission");

    assertTrue("Equals is equal for same permissions", p1.equals(p2));
  }


  public void testEqualsWorksWithOtherType() {
    Permission p1 = new Permission("TestPermission");

    assertTrue("Equals works with other type", !p1.equals("OtherType"));
  }
}
