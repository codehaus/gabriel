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

import gabriel.Principal;
import gabriel.components.context.AccessContext;
import gabriel.components.context.Ownable;
import gabriel.components.context.OwnerAccessContext;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.HashSet;
import java.util.Set;

public class OwnerAccessContextTest extends MockObjectTestCase {
  public static Test suite() {
    return new TestSuite(OwnerAccessContextTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testDoNotModifyPrincipalsIfNotOwner() {
    Mock ownable = mock(Ownable.class);
    ownable.expects(once()).method("getOwner").will(returnValue(new Principal("NotOwner")));

    AccessContext context = new OwnerAccessContext((Ownable) ownable.proxy());
    Set principals = new HashSet();
    principals.add(new Principal("TestOwner"));
    context.modifyPrincipals(principals);
    assertTrue("Owner principal was not added to list.",
        !principals.contains(new Principal("Owner")));
  }

  public void testDoNotModifyIfAlreadyOwner() {
    Mock ownable = mock(Ownable.class);

    AccessContext context = new OwnerAccessContext((Ownable) ownable.proxy());
    Set principals = new HashSet();
    principals.add(new Principal("TestOwner"));
    principals.add(new Principal("Owner"));

    context.modifyPrincipals(principals);
    assertTrue("Owner principal was not added to list.",
        principals.size() == 2);
  }

  public void testModifyPrincipalsIfOwner() {
    Mock ownable = mock(Ownable.class);
    ownable.expects(once()).method("getOwner").will(returnValue(new Principal("TestOwner")));

    AccessContext context = new OwnerAccessContext((Ownable) ownable.proxy());
    Set principals = new HashSet();
    principals.add(new Principal("TestOwner"));
    context.modifyPrincipals(principals);
    assertTrue("Owner principal was added to list.",
        principals.contains(new Principal("Owner")));
  }
}