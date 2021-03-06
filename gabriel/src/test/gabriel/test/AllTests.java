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

package gabriel.test;

import gabriel.test.acl.AllAclTests;
import gabriel.test.components.AllComponentsTests;
import gabriel.test.dynaop.AllDynaopTests;
import gabriel.test.subject.AllSubjectTests;
import gabriel.test.subject.PrincipalTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

  public static Test suite() throws Exception {
//    DirectorySuiteBuilder builder = new DirectorySuiteBuilder();
//    builder.setFilter(new SimpleTestFilter() {
//        public boolean include(Class clazz) {
//            return super.include(clazz);
//        }
//    });
//    System.err.println(new File(".").getAbsolutePath());
//    return builder.suite("src/test/");
    TestSuite s = new TestSuite();
    s.addTest(AllAclTests.suite());
    s.addTest(AllDynaopTests.suite());
    s.addTest(AllComponentsTests.suite());
    s.addTest(AllSubjectTests.suite());
    s.addTest(PrincipalTest.suite());
    s.addTest(PermissionTest.suite());
    return s;

  }

}
