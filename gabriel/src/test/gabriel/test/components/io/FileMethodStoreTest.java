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

import gabriel.Permission;
import gabriel.components.io.FileMethodStore;
import gabriel.components.parser.MethodParser;
import gabriel.components.parser.MethodParserImpl;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileMethodStoreTest extends MockObjectTestCase {

  private Mock mockMethodParser;

  public static Test suite() {
    return new TestSuite(FileMethodStoreTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    mockMethodParser = mock(MethodParser.class);
  }

  public void testParse() {
    Map methodMap = new HashMap();
    mockMethodParser.expects(once()).method("parse").with(eq("PM1 {\n Class.M1\n }\n")).will(returnValue(methodMap));
    FileMethodStore store = new FileMethodStore((MethodParser) mockMethodParser.proxy());
    assertEquals("Returns correct method map", methodMap, store.parse(new StringReader("PM1 {\n Class.M1\n }")));
  }

  public void testGetMap() {
    FileMethodStore store = new FileMethodStore(new MethodParserImpl());
    Map methodMap = store.getMap("test_methods");

    assertTrue("Method map contains PM1 to Class1.method1 mapping", ((Set) methodMap.get("Class1.method1")).contains(new Permission("PM1")));
  }
}
