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

package gabriel.test.components;

import gabriel.Permission;
import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.components.AccessManager;
import gabriel.components.AccessManagerImpl;
import gabriel.components.AclStore;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.MockObjectTestCase;

import java.util.HashSet;
import java.util.Set;

public class AccessManagerTest extends MockObjectTestCase {
  private AccessManager accessManager;

  public static Test suite() {
    return new TestSuite(AccessManagerTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    accessManager = new AccessManagerImpl(new AclStore() {
      public Acl getAcl(Principal owner, String name) {
        return new Acl(owner, name);
      }
    });
  }

  public void testCheckPermission() {
    Principal principal = new Principal("TestPrincipal");
    Permission permission = new Permission("TestPermission");
    accessManager.addPermission(principal, permission);
    Set principals = new HashSet();
    principals.add(principal);
    assertTrue("TestPrincipal has TestPermission.",
        accessManager.checkPermission(principals, permission));
  }
}
