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

package gabriel.test.dynaop;

import dynaop.Interceptor;
import dynaop.Invocation;

import gabriel.Principal;
import gabriel.Permission;
import gabriel.acl.Acl;
import gabriel.acl.AclEntry;

public class AccessInterceptor implements Interceptor {
  private Acl acl;

  public AccessInterceptor() {
    Principal owner = new Principal("owner");
    acl = new Acl(owner, "TestAcl");
    Principal principal = new Principal("AllowedPrincipal");
    AclEntry entry = new AclEntry(principal);
    entry.addPermission(new Permission("SET_NAME"));
    acl.addEntry(owner, entry);
  }

  public boolean checkPermission(Principal principal, String className, String methodName) {
    // Perhaps use a CallPermission("class.method");
    // Perhaps with signature
    // Making methodName -> invocation will introduce dependency on Dynaop
    // Use a Component to check permission -> method mapping
    // Group some methods to Permissions like "POST_BLOG"

    // CallAccessManager.checkPermission(MethodName, Principal)

    if (acl.checkPermission(principal, new Permission("SET_NAME"))) {
      return true;
    } else {
      return false;
    }
  }

  public Object intercept(Invocation invocation) throws Throwable {
    Object result = null;
    if (! invocation.getMethod().getName().equals("setOwner") && invocation.getArguments() != null) {
      Object first = invocation.getArguments()[0];
      if (first instanceof Principal) {
        Principal principal = (Principal) first;
        if (checkPermission(principal, invocation.getMethod().getDeclaringClass().getName(), invocation.getMethod().getName())) {
          result = invocation.proceed();
        } else {
          throw new SecurityException("Access denied to "+invocation.getMethod().getName() + " for "+principal.getName());
        }
      }
    } else {
      result = invocation.proceed();
    }

    return result;
  }
}
