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

package gabriel.components.io;

import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.components.AclStore;
import gabriel.components.parser.AclParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Stores Acl in files with <name>.acl
 *
 * @author stephan
 * @version $id$
 */
public class FileAclStore implements AclStore {

  private AclParser parser;

  public FileAclStore(AclParser parser) {
    this.parser = parser;
  }

  // throws AclNotFoundException
  public Acl getAcl(Principal owner, String name) {
    Reader input = null;
    try {
      input = new InputStreamReader(FileAclStore.class.getResourceAsStream("/" + name + ".acl"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return parse(owner, name, input);
  }

  /**
   * Parse an Acl description. Needs to throw an exception.
   * <code>
   * Principal { Permission Permission }
   * Principal { Permission }
   * </code>
   *
   * @param owner Principal who owns the Acl
   * @param name  Name of the Acl
   * @param in    Description source
   * @return Parsed Acl
   */
  public Acl parse(Principal owner, String name, Reader in) {
    BufferedReader reader = new BufferedReader(in);
    StringBuffer buffer = new StringBuffer();

    try {
      String line = "";
      while (null != (line = reader.readLine())) {
        buffer.append(line);
        buffer.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String input = buffer.toString();

    return parser.parse(owner, name, input);
  }
}
