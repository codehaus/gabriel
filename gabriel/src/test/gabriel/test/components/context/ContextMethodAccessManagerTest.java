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

package gabriel.test.components.context;

import gabriel.Permission;
import gabriel.components.MethodAccessManager;
import gabriel.components.context.AccessContext;
import gabriel.components.context.ContextMethodAccessManager;
import gabriel.components.context.ContextMethodAccessManagerImpl;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.ArrayList;
import java.util.HashSet;

public class ContextMethodAccessManagerTest extends MockObjectTestCase {
  private ContextMethodAccessManager contextMethodAccessManager;
  private Mock mockMethodAccessManager;
  private Mock mockContext;

  public static Test suite() {
    return new TestSuite(ContextMethodAccessManagerTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();

    mockMethodAccessManager = mock(MethodAccessManager.class);
    contextMethodAccessManager = new ContextMethodAccessManagerImpl((MethodAccessManager) mockMethodAccessManager.proxy());
    mockContext = mock(AccessContext.class);
  }

  public void testCheckPermissionWithContext() {
    mockMethodAccessManager.expects(once()).method("checkPermission").will(returnValue(true));
    mockContext.expects(once()).method("modifyPrincipals").will(returnValue(new HashSet()));
    contextMethodAccessManager.checkPermission(new HashSet(), "Testclass.method1", (AccessContext) mockContext.proxy());
  }


  public void testDelegatesStart() {
    mockMethodAccessManager.expects(once()).method("start");
    contextMethodAccessManager.start();
  }

  public void testDelegatesStop() {
    mockMethodAccessManager.expects(once()).method("stop");
    contextMethodAccessManager.stop();
  }

  public void testDelegatesAddMethodWithClass() {
    mockMethodAccessManager.expects(once()).method("addMethod");
    contextMethodAccessManager.addMethod(new Permission("TestPermission"), ContextMethodAccessManagerTest.class, "method1");
  }

  public void testDelegatesAddMethod() {
    mockMethodAccessManager.expects(once()).method("addMethod");
    contextMethodAccessManager.addMethod(new Permission("TestPermission"), "Testclass.method1");
  }

  public void testDelegatesAddMethods() {
    mockMethodAccessManager.expects(once()).method("addMethods");
    contextMethodAccessManager.addMethods(new Permission("TestPermission"), new String[]{"Testclass.method1"});
  }

  public void testDelegatesAddMethodsWithList() {
    mockMethodAccessManager.expects(once()).method("addMethods");
    contextMethodAccessManager.addMethods(new Permission("TestPermission"), new ArrayList());
  }

  public void testDelegatesCheckPermission() {
    mockMethodAccessManager.expects(once()).method("checkPermission").will(returnValue(true));
    contextMethodAccessManager.checkPermission(new HashSet(), "Testclass.method1");
  }

  public void testDelegatesCheckPermissionWithClass() {
    mockMethodAccessManager.expects(once()).method("checkPermission").will(returnValue(true));
    contextMethodAccessManager.checkPermission(new HashSet(), ContextMethodAccessManagerTest.class, "method1");
  }

  public void testDelegatesGetPermissions() {
    mockMethodAccessManager.expects(once()).method("getPermissions").will(returnValue(new HashSet()));
    contextMethodAccessManager.getPermissions("Testclass.method1");
  }

}