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
import gabriel.components.io.FileAclStore;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.core.MockObjectSupportTestCase;

import java.io.StringReader;

public class AclStoreTest extends MockObjectSupportTestCase {

  private Principal owner;
  private String name;

  public static Test suite() {
    return new TestSuite(AclStoreTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    owner = new Principal("Owner");
    name = "TestName";
  }

  public void testParse() {
    FileAclStore manager = new FileAclStore();
    String source = "PP1 { PM1 PM2 \n" +
        "                  PM3 }" +
        "            PP2 { PM1 }";

    Acl acl = manager.parse(owner, name, new StringReader(source));

    assertEquals("PP1 has PM1",
        1, acl.checkPermission(new Principal("PP1"), new Permission("PM1")));
    assertEquals("PP1 has PM2",
        1, acl.checkPermission(new Principal("PP1"), new Permission("PM2")));
    assertEquals("PP1 has PM3",
        1, acl.checkPermission(new Principal("PP1"), new Permission("PM3")));
    assertEquals("PP2 has PM1",
        1, acl.checkPermission(new Principal("PP2"), new Permission("PM1")));
  }

  public void testNegativePermissions() {
    FileAclStore manager = new FileAclStore();
    String source = "-PP1 { PM1 }";

    Acl acl = manager.parse(owner, name, new StringReader(source));

    assertEquals("PP1 is denied PM1",
        -1, acl.checkPermission(new Principal("PP1"), new Permission("PM1")));

  }
}
