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

import gabriel.Permission;
import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.acl.AclEntry;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashSet;
import java.util.Set;

public class AclTest extends TestCase {
  private Principal owner;
  private Acl acl;

  public static Test suite() {
    return new TestSuite(AclTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    owner = new Principal("Owner");
    acl = new Acl(owner, "TestAcl");
  }

  public void testGetName() {
    assertEquals("Acl name is correct.", "TestAcl", acl.getName());
  }

  public void testSetNameWithWrongOwner() {
    try {
      acl.setName(new Principal("Owner"), "NewTestName");
///CLOVER:OFF
      fail("Should raise an SecurityException");
///CLOVER:ON
    } catch (SecurityException e) {
    }
  }

  public void testSetNewName() {
    acl.setName(owner, "NewName");
    assertEquals("New name was set.", "NewName", acl.getName());
  }

  public void testNotOwner() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);
    assertTrue("Acl isOwner reports correcty owner.", !acl.isOwner(new Principal("NotOwner")));
  }

  public void testAddAclEntry() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);
    assertTrue("Acl contains added entry.", acl.entries().contains(entry));
  }

  public void testAddOneAclEntry() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);
    assertEquals("Acl contains one entry.", 1, acl.entries().size());
  }

  public void testAddEntryWithWrongOwner() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    try {
      acl.addEntry(new Principal("Owner"), entry);
///CLOVER:OFF
      fail("Should raise an SecurityException");
///CLOVER:ON
    } catch (SecurityException e) {
    }
  }

  public void testRemoveAclEntry() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);
    assertTrue("AclEntry says true to remove.", acl.removeEntry(owner, entry));
    assertTrue("Acl does not contain removed entry.", !acl.entries().contains(entry));
  }

  public void testRemoveEntryWithWrongOwner() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);
    try {
      acl.removeEntry(new Principal("Owner"), entry);
///CLOVER:OFF
      fail("Should raise an SecurityException");
///CLOVER:ON
    } catch (SecurityException e) {
    }
  }

  public void testSetNewOwner() {
    Principal newOwner = new Principal("NewOwner");
    acl.setOwner(owner, newOwner);
    assertTrue("New owner is now owner.", acl.isOwner(newOwner));
  }

  public void testSetNewOwnerWithWrongOwner() {
    Principal newOwner = new Principal("NewOwner");
    try {
      acl.setOwner(new Principal("WrongOwner"), newOwner);
///CLOVER:OFF
      fail("Should raise an SecurityException");
///CLOVER:ON
    } catch (SecurityException e) {
    }
  }

  public void testCheckPermissionWithoutPermission() {
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    acl.addEntry(owner, entry);

    Principal checkPrincipal = new Principal("TestPrincipal");
    Permission checkPermission = new Permission("TestPermission");
    assertEquals("Principal has not permission from entry", 0, acl.checkPermission(checkPrincipal, checkPermission));

  }


  public void testWithSetAndNoMatchingEntries() {
    Permission checkPermission = new Permission("TestPermission");
    Principal checkPrincipal = new Principal("TestPrincipal");
    Set principals = new HashSet();
    principals.add(checkPrincipal);

    assertEquals("Principal has not permission in empty acl", false, acl.checkPermission(principals, checkPermission));
  }

  public void testWithSetAndOneNegative() {
    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");

    AclEntry negative = new AclEntry(principal);
    negative.addPermission(permission);
    negative.setNegativePermissions();
    acl.addEntry(owner, negative);

    AclEntry positive = new AclEntry(principal);
    positive.addPermission(permission);
    acl.addEntry(owner, positive);

    Permission checkPermission = new Permission("TestPermission");
    Principal checkPrincipal = new Principal("TestPrincipal");
    Set principals = new HashSet();
    principals.add(checkPrincipal);

    assertEquals("Principal in set makes permissions negative", false, acl.checkPermission(principals, checkPermission));
  }

  public void testCheckPermission() {
    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    entry.addPermission(permission);
    acl.addEntry(owner, entry);

    Principal checkPrincipal = new Principal("TestPrincipal");
    Permission checkPermission = new Permission("TestPermission");
    assertEquals("Principal has permission from entry", 1, acl.checkPermission(checkPrincipal, checkPermission));
  }

  public void testNegativePermsissionCancelsPermission() {
    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    entry.addPermission(permission);
    acl.addEntry(owner, entry);

    AclEntry negativeEntry = new AclEntry(principal);
    negativeEntry.addPermission(permission);
    negativeEntry.setNegativePermissions();
    acl.addEntry(owner, negativeEntry);

    Principal checkPrincipal = new Principal("TestPrincipal");
    Permission checkPermission = new Permission("TestPermission");
    assertEquals("Principal does not have permission because of negative entry", -1, acl.checkPermission(checkPrincipal, checkPermission));
  }

  public void testToString() {
    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");
    AclEntry entry = new AclEntry(principal);
    entry.addPermission(permission);
    acl.addEntry(owner, entry);

    assertEquals("Acl serialized to string.", "(TestAcl:[((TestPrincipal): [(TestPermission)])])", acl.toString());
  }

}
