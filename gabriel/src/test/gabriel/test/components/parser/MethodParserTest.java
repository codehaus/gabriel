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

package gabriel.test.components.parser;

import gabriel.Permission;
import gabriel.components.parser.MethodParser;
import gabriel.components.parser.MethodParserImpl;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.core.MockObjectSupportTestCase;

import java.util.Map;
import java.util.Set;

public class MethodParserTest extends MockObjectSupportTestCase {

  public static Test suite() {
    return new TestSuite(MethodParserTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testParsePermissionsAndMethods() {
    MethodParser parser = new MethodParserImpl();

    String source = "PM1 { M1 M2 \n" +
        "                  M3 }" +
        "            PM2 { M1 }";

    Map methodMap = parser.parse(source);

    assertTrue("PM1 contains M1", ((Set) methodMap.get("M1")).contains(new Permission("PM1")));
    assertTrue("PM1 contains M2", ((Set) methodMap.get("M2")).contains(new Permission("PM1")));
    assertTrue("PM1 contains M3", ((Set) methodMap.get("M3")).contains(new Permission("PM1")));
    assertTrue("PM2 contains M1", ((Set) methodMap.get("M1")).contains(new Permission("PM2")));
  }

  public void testHandleEmptyMethodBlocks() {
    MethodParser parser = new MethodParserImpl();

    String source = "PM1 { } PM2 { M2 }";

    Map methodMap = parser.parse(source);
    assertTrue("PM2 contains M2", ((Set) methodMap.get("M2")).contains(new Permission("PM2")));
  }
}
