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
import gabriel.Principal;
import gabriel.components.context.AccessContext;
import gabriel.components.context.ContextAccessManager;
import gabriel.components.context.ContextAccessManagerImpl;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.HashSet;
import java.util.Set;

public class ContextAccessMangerTest extends MockObjectTestCase {
  private Mock mockContext;
  private ContextAccessManager contextAccessManager;

  public static Test suite() {
    return new TestSuite(ContextAccessMangerTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();

    mockContext = mock(AccessContext.class);
    contextAccessManager = new ContextAccessManagerImpl();
  }


  public void testCallsModifyOnContext() {
    Permission permission = new Permission("TestPermission");
    Principal principal = new Principal("TestPrincipal");
    Set principals = new HashSet();
    principals.add(principal);

    mockContext.expects(once()).method("modifyPrincipal").with(same(principals));

    contextAccessManager.checkPermission(principals,
        permission, (AccessContext)
        mockContext.proxy());
  }
}