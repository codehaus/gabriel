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

import java.util.*;

/**
 * Parse a method to permission mapping
 *
 * @author stephan
 * @version $id$
 */
public class MethodParserImpl implements MethodParser {
  /**
   * Parse a method map from a string.
   * <p/>
   * <code>
   * Permission { Method Method }
   * Permission { Method }
   * </code>
   * <p/>
   * Returns Method -> { Permission, Permission }.
   *
   * @param input String with method mapping
   * @return Method map
   */
  public Map parse(String input) {
    Permission permission = null;
    String method = null;

    int METHODS = 1;
    int PERMISSIONS = 2;
    int state = PERMISSIONS;

    Map methodMap = new HashMap();

    for (StringTokenizer stringTokenizer = new StringTokenizer(input, " \n{}", true);
         stringTokenizer.hasMoreTokens();) {

      String t = stringTokenizer.nextToken();

      if (" ".equals(t) || "\n".equals(t)) {
        // do nothing
      } else if ("{".equals(t)) {
        state = METHODS;
      } else if ("}".equals(t)) {
        state = PERMISSIONS;
      } else if (state == PERMISSIONS) {
        permission = new Permission(t);
      } else if (state == METHODS) {
        if (null != permission) {
          method = t;
          Set permissions;
          if (methodMap.containsKey(method)) {
            permissions = (Set) methodMap.get(method);
          } else {
            permissions = new HashSet();
            methodMap.put(method, permissions);
          }
          permissions.add(permission);
        }
      }
    }
    return methodMap;
  }
}
