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

package gabriel.components.parser;

import gabriel.Permission;
import gabriel.Principal;
import gabriel.acl.Acl;
import gabriel.acl.AclEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Parse an ACL from a String with a { } block representation.
 *
 * @author stephan
 * @version $id$
 */
public class AclParserImpl implements AclParser {
  /**
   * Parse an Acl description. Needs to throw an exception.
   * <code>
   * Principal { Permission Permission }
   * Principal { Permission }
   * </code>
   *
   * @param owner Principal who owns the Acl
   * @param name  Name of the Acl
   * @param input Description source
   * @return Parsed Acl
   */
  public Acl parse(Principal owner, String name, String input) {
    boolean negative = false;

    Principal principal = null;
    List permissions = new ArrayList();

    Acl acl = new Acl(owner, name);

    int PERMISSIONS = 1;
    int PRINCIPAL = 2;

    int state = PRINCIPAL;

    for (StringTokenizer stringTokenizer = new StringTokenizer(input, " \n{}", true);
         stringTokenizer.hasMoreTokens();) {

      String t = stringTokenizer.nextToken();

      if (" ".equals(t) || "\n".equals(t)) {
        // do nothing
      } else if ("{".equals(t)) {
        state = PERMISSIONS;
      } else if ("}".equals(t)) {
        state = PRINCIPAL;

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
      } else if (state == PRINCIPAL) {
        if (t.startsWith("-")) {
          negative = true;
          t = t.substring(1);
        }
        // handle "- principal" and "-principal"
        if (!"".equals(t)) {
          principal = new Principal(t);
        }
      } else if (state == PERMISSIONS) {
        permissions.add(new Permission(t));
      }
    }
    return acl;
  }
}
