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

package gabriel.test.components.io;

import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.components.io.FileAclStore;
import gabriel.components.parser.AclParser;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.io.StringReader;

public class FileAclStoreTest extends MockObjectTestCase {

  private Mock mockAclParser;

  public static Test suite() {
    return new TestSuite(FileAclStoreTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    mockAclParser = mock(AclParser.class);
  }

  public void testParse() {
    Acl acl = new Acl(new Principal("Owner"), "TestAcl");
    mockAclParser.expects(once()).method("parse").with(ANYTHING, ANYTHING, eq("P1 {\n PM1 \n }\n")).will(returnValue(acl));
    FileAclStore store = new FileAclStore((AclParser) mockAclParser.proxy());
    assertEquals("Returns correct method map", acl, store.parse(new Principal("Owner"), "TestAcl", new StringReader("P1 {\n PM1 \n }")));
  }

}
