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

import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.core.MockObjectSupportTestCase;
import org.nanocontainer.dynaop.DynaopComponentAdapterFactory;
import org.picocontainer.defaults.DefaultComponentAdapterFactory;
import org.picocontainer.defaults.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import dynaop.Aspects;
import dynaop.Pointcuts;
import dynaop.util.Closure;
import gabriel.Principal;

public class DynaopPicoTest extends MockObjectSupportTestCase {
  public static Test suite() {
    return new TestSuite(DynaopPicoTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testAnnotateWithMixin() {
    Aspects aspects = new Aspects();
    aspects.interceptor(Pointcuts.instancesOf(SecureObject.class),
                        Pointcuts.ALL_METHODS, new AccessInterceptor());
    aspects.mixin(Pointcuts.instancesOf(SecureObject.class),
        SecureMixin.class, new Closure() {
          public void execute(Object o) {
          }
        });

    DynaopComponentAdapterFactory factory = new DynaopComponentAdapterFactory(
        new DefaultComponentAdapterFactory(), aspects);
    MutablePicoContainer container = new DefaultPicoContainer(factory);
    container.registerComponentImplementation(SecureObject.class, SecureObjectImpl.class);

    SecureObject object = (SecureObject) container.getComponentInstance(SecureObject.class);
    assertEquals("Object has default name.", "Empty", object.getName());
    Ownable ownable = (Ownable) object;
    Principal owner = new Principal("Owner");
    ownable.setOwner(owner);
    assertEquals("Mixin contains owner.", owner, ownable.getOwner());

    Principal notAllowed = new Principal("NotAllowedPrincipal");
    try {
      object.setName(notAllowed, "NewTestName");
      fail("Access should be denied to method setName() for NotAllowedPrincipal");
    } catch (SecurityException e) {
      // System.out.println(e.getMessage());
    }
    assertEquals("Name not set by wrong principal.", "Empty", object.getName());

  }
}
