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
import gabriel.components.AccessManager;
import gabriel.components.MethodAccessManager;
import gabriel.components.MethodAccessManagerImpl;
import gabriel.components.MethodStore;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.*;

public class CallAccessManagerTest extends MockObjectTestCase {
  private MethodAccessManager callManager;
  private Mock mockAccessManager;
  private Mock mockMethodStore;

  public static Test suite() {
    return new TestSuite(AccessManagerTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();

    mockAccessManager = mock(AccessManager.class);
    mockMethodStore = mock(MethodStore.class);
    callManager = new MethodAccessManagerImpl((AccessManager) mockAccessManager.proxy(),
        (MethodStore) mockMethodStore.proxy());
  }

  public void testCheckPermission() {

    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");
    Set principals = new HashSet();
    principals.add(principal);
    mockAccessManager.expects(once()).method("checkPermission").with(same(principals), same(permission)).will(returnValue(true));
    mockMethodStore.expects(once()).method("getMap").will(returnValue(new HashMap()));
    callManager.addMethods(permission, new String[]{"TestClass.method1"});
    assertTrue("Granted permission to call TestClass.method1.", callManager.checkPermission(principals, "TestClass.method1"));
  }

  public void testAddPermissionsWithList() {
    List permissions = Arrays.asList(new String[]{"TestClass.method1", "TestClass.method2"});
    callManager.addMethods(new Permission("TestPermission"), permissions);
    assertTrue("TestPermission is needed for method1",
        callManager.getPermissions("TestClass.method1").contains(new Permission("TestPermission")));
    assertTrue("TestPermission is needed for method2",
        callManager.getPermissions("TestClass.method2").contains(new Permission("TestPermission")));
  }

  public void testAddPermissionsWithArray() {
    String[] permissions = new String[]{"TestClass.method1", "TestClass.method2"};
    callManager.addMethods(new Permission("TestPermission"), permissions);
    assertTrue("TestPermission is needed for method1",
        callManager.getPermissions("TestClass.method1").contains(new Permission("TestPermission")));
    assertTrue("TestPermission is needed for method2",
        callManager.getPermissions("TestClass.method2").contains(new Permission("TestPermission")));
  }

}