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

import gabriel.components.MethodStore;
import gabriel.components.parser.MethodParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

/**
 * Reads method access mappings from files.
 *
 * @author stephan
 * @version $id$
 */
public class FileMethodStore implements MethodStore {

  private MethodParser parser;

  public FileMethodStore(MethodParser parser) {
    this.parser = parser;
  }

  /**
   * Construct the complete name for the file
   * which stores the mappings.
   *
   * @param name Name of the map
   * @return complete name with path for the file
   */
  protected String getName(String name) {
    return "/gabriel/" + name + ".acl";
  }

  /**
   * Get the method map with the permissions
   * to methods mappings.
   * <p/>
   * todo: should throw exception
   *
   * @return Method map
   */
  public Map getMap(String name) {
    try {
      Reader reader = new InputStreamReader(
          FileMethodStore.class.getResourceAsStream(getName(name)));
      return parse(reader);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void putMap(String name, Map map) {
    // What the f*** should I do to write to a resource?
  }

  /**                S
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

    return parser.parse(input);
  }

}
