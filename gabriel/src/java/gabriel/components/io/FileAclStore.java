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

import gabriel.Permission;
import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.acl.AclEntry;
import gabriel.components.AclStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Stores Acl in files with <name>.txt
 *
 * @author stephan
 * @version $id$
 */
public class FileAclStore implements AclStore {

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
      while (null != line) {
        line = reader.readLine();
        buffer.append(line);
        buffer.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String input = buffer.toString();

    return parse(owner, name, input);
  }

  private Acl parse(Principal owner, String name, String input) {
    boolean principalIsNext = true;
    boolean permissionsAreNext = false;
    boolean negative = false;

    Principal principal = null;
    List permissions = new ArrayList();

    Acl acl = new Acl(owner, name);

    for (StringTokenizer stringTokenizer = new StringTokenizer(input, " \n{}", true);
         stringTokenizer.hasMoreTokens();) {

      String t = stringTokenizer.nextToken();

      if (" ".equals(t) || "\n".equals(t)) {
        // do nothing
      } else if ("{".equals(t)) {
        permissionsAreNext = true;
        principalIsNext = false;
      } else if ("}".equals(t)) {
        permissionsAreNext = false;
        principalIsNext = true;

        AclEntry entry = new AclEntry(principal);
        Iterator iterator = permissions.iterator();
        while (iterator.hasNext()) {
          Permission permission = (Permission) iterator.next();
          entry.addPermission(permission);
        }
        if (negative) {
          entry.setNegativePermissions();
          negative = false;
        }
        acl.addEntry(owner, entry);
        permissions = new ArrayList();
        principal = null;
      } else if (principalIsNext) {
        if (t.startsWith("-")) {
          negative = true;
          t = t.substring(1);
        }
        // handle "- principal" and "-principal"
        if (!"".equals(t)) {
          principal = new Principal(t);
        }
      } else if (permissionsAreNext) {
        permissions.add(new Permission(t));
      }
    }
    return acl;
  }
}
