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

import dynaop.*;

import gabriel.Principal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InterceptorTest extends TestCase {

  public static Test suite() {
    return new TestSuite(InterceptorTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }


  public void testInterceptor() {
    Aspects aspects = new Aspects();
    aspects.interceptor(Pointcuts.instancesOf(SecureObjectImpl.class),
        Pointcuts.membersOf(SecureObject.class), new AccessInterceptor());
    ProxyFactory proxyFactory = ProxyFactory.getInstance(aspects);

    SecureObject object = new SecureObjectImpl("TestName");
    SecureObject wrapped = (SecureObject) proxyFactory.wrap(object);
    // An interceptor has been registered in the dynaop.bsh file for this method
    Principal notAllowed = new Principal("NotAllowedPrincipal");
    try {
      wrapped.setName(notAllowed, "NewTestName");
      fail("Access should be denied to method setName() for NotAllowedPrincipal");
    } catch (SecurityException e) {
      // System.out.println(e.getMessage());
    }
    assertEquals("Name not set by wrong principal.", "TestName", object.getName());

    Principal allowed = new Principal("AllowedPrincipal");
    wrapped.setName(allowed, "NewTestName");
    assertEquals("Name set by allowed principal.", "NewTestName", object.getName());
  }

}
