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
package gabriel.test.acl;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import gabriel.acl.AclEntry;
import gabriel.Principal;
import gabriel.Permission;

public class AclEntryTest extends TestCase {
  private AclEntry entry;
  private Principal principal;
  private Permission permission;

  public static Test suite() {
    return new TestSuite(AclEntryTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    principal = new Principal("TestPrincipal");
    entry = new AclEntry(principal);
    permission = new Permission("TestPermission");
  }

  public void testPrincipalProperty() {
    Principal newPrincipal = new Principal("NewPrincipal");
    entry.setPrincipal(newPrincipal);
    assertEquals("New Principal was set.", newPrincipal, entry.getPrincipal());
  }

  public void testNegativePermission() {
    entry.setNegativePermissions();
    assertTrue("Entry has negative permissions.", entry.isNegative());
  }

  public void testAddOnePermission() {
    entry.addPermission(permission);
    assertEquals("Entry has one permission.", 1, entry.permissions().size());
  }

  public void testAddPermission() {
    entry.addPermission(permission);
    assertTrue("Entry contains added permission.",  entry.permissions().contains(permission));
  }

  public void testRemovePermission() {
    entry.addPermission(permission);
    assertTrue("Entry returns true on remove.",  entry.removePermission(permission));
    assertTrue("Entry does not contain removed permission.", ! entry.permissions().contains(permission));
  }

  public void testPermissionOnlyAddedOnce() {
    entry.addPermission(permission);
    entry.addPermission(permission);
    assertEquals("Entry has one permission.", 1, entry.permissions().size());
  }


  public void testCheckPermission() {
    entry.addPermission(permission);
    assertTrue("Entry has permission.", entry.checkPermission(permission));
  }
}