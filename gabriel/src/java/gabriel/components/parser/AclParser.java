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

import gabriel.Principal;
import gabriel.acl.Acl;

/**
 * Parse an ACL from a String with a { } block representation.
 *
 * @author stephan
 * @version $id$
 */
public interface AclParser {
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
  public Acl parse(Principal owner, String name, String input);
}
