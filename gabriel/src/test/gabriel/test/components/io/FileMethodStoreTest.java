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

import gabriel.components.parser.MethodParser;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

public class FileMethodStoreTest extends MockObjectTestCase {

  private Mock mockMethodParser;

  public static Test suite() {
    return new TestSuite(FileMethodStoreTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
    mockMethodParser = mock(MethodParser.class);
  }

//  public void testParse() {
//    FileMethodStore store = new FileMethodStore((MethodParser) mockMethodParser.proxy());
//    store.parse(new StringReader("PM1 { Class.M1 }"));
//  }
}
