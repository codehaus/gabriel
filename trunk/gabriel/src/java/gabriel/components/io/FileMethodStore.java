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
import gabriel.components.MethodStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Reads method access mappings from files.
 *
 * @author stephan
 * @version $id$
 */
public class FileMethodStore implements MethodStore {
  /**
   * Get the method map with the permissions
   * to methods mappings.
   * <p/>
   * todo: should throw exception
   *
   * @return Method map
   */
  public Map getMap() {
    try {
      Reader reader = new InputStreamReader(FileMethodStore.class.getResourceAsStream("/methods.acl"));
      return parse(reader);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Parse a method map from a reader.
   *
   * @param in Reader with the source
   * @return Method map
   */
  public Map parse(Reader in) {
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

    return parse(input);
  }

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
  private Map parse(String input) {
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
