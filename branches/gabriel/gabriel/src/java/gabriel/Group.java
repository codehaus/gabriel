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

package gabriel;

import java.util.List;

/**
 * Group
 *
 * THIS IS NOT NEEDED I think.
 * In most (web) applications Users have several roles (principals)
 * like "Editor", "Admin" or may belong to groups (principals) like "Sales", "PR".
 * Very seldom do this roles or groups need to be containers.
 *
 * @author Stephan J. Schmidt
 * @version $Id: Group.java,v 1.1.1.1 2004-06-16 07:56:38 stephan Exp $
 */

public class Group extends Principal {
  private String name;
  private List members;

  public Group(String name) {
    super(name);
  }

  public boolean addMember(Principal principal) {
    return members.add(principal);
  }

  public boolean removeMember(Principal principal) {
    return members.remove(principal);
  }

  public boolean isMember(Principal principal) {
    return members.contains(principal);
  }

  public List members() {
    return members;
  }

  public String getName() {
    return name;
  }
}