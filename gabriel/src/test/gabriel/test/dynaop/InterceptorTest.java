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

package gabriel.test.dynaop;

import dynaop.Aspects;
import dynaop.Pointcuts;
import dynaop.ProxyFactory;
import dynaop.util.Closure;
import example.SecureObject;
import example.SecureObjectImpl;
import gabriel.Subject;
import gabriel.components.MethodAccessManager;
import gabriel.components.context.ContextMethodAccessManager;
import gabriel.components.dynaop.AccessInterceptor;
import gabriel.components.dynaop.OwnableAccessInterceptor;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.HashSet;
import java.util.Set;

public class InterceptorTest extends MockObjectTestCase {

  private Mock contextCallAccessManager;
  private Mock callAccessManager;

  private Set principals;

  private Aspects aspects;
  private ProxyFactory proxyFactory;

  public static Test suite() {
    return new TestSuite(InterceptorTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    this.contextCallAccessManager = mock(ContextMethodAccessManager.class);
    this.callAccessManager = mock(MethodAccessManager.class);

    principals = new HashSet();
    Subject subject = Subject.get();
    subject.setPrincipals(principals);

    aspects = new Aspects();
  }

  public void testDenyOwnableAccessInterceptor() {

    aspects.interceptor(Pointcuts.instancesOf(SecureObject.class),
        Pointcuts.ALL_METHODS,
        new OwnableAccessInterceptor((ContextMethodAccessManager) contextCallAccessManager.proxy()));

    aspects.mixin(Pointcuts.instancesOf(SecureObject.class),
        OwnableMixin.class, new Closure() {
          public void execute(Object o) {
          }
        });

    proxyFactory = ProxyFactory.getInstance(aspects);

    contextCallAccessManager.
        expects(once()).
        method("checkPermission").
        with(eq(principals),
            eq("example.SecureObject.setName"),
            ANYTHING).
        will(returnValue(false));

    SecureObject object = new SecureObjectImpl("TestName");
    SecureObject wrapped = (SecureObject) proxyFactory.wrap(object);

    try {
      wrapped.setName("NewTestName");
      fail("Access should be denied to method setName() for NotAllowedPrincipal");
    } catch (SecurityException e) {
      // System.out.println(e.getMessage());
    }
    assertEquals("Name not set by wrong principal.", "TestName", object.getName());
  }

  public void testDenyAccessInterceptor() {
    callAccessManager.
        expects(once()).
        method("checkPermission").
        with(eq(principals),
            eq("example.SecureObject.setName")).
        will(returnValue(false));

    aspects.interceptor(Pointcuts.instancesOf(SecureObject.class),
        Pointcuts.ALL_METHODS, new AccessInterceptor((MethodAccessManager) callAccessManager.proxy()));

    proxyFactory = ProxyFactory.getInstance(aspects);

    SecureObject object = new SecureObjectImpl("TestName");
    SecureObject wrapped = (SecureObject) proxyFactory.wrap(object);
    try {
      wrapped.setName("NewTestName");
      fail("Access should be denied to method setName() for NotAllowedPrincipal");
    } catch (SecurityException e) {
      // System.out.println(e.getMessage());
    }
    assertEquals("Name not set by wrong principal.", "TestName", object.getName());
  }
}
