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

package gabriel.components.dynaop;

import dynaop.Interceptor;
import dynaop.Invocation;
import gabriel.Subject;
import gabriel.components.context.AccessContext;
import gabriel.components.context.ContextMethodAccessManager;
import gabriel.components.context.Ownable;
import gabriel.components.context.OwnerAccessContext;

import java.util.Set;

public class OwnableAccessInterceptor implements Interceptor {
  private ContextMethodAccessManager manager;

  public OwnableAccessInterceptor(ContextMethodAccessManager manager) {
    super();
    this.manager = manager;
  }

  public Object intercept(Invocation invocation) throws Throwable {
    Object result;

    Ownable ownable = (Ownable) invocation.getProxy();
    AccessContext context = new OwnerAccessContext(ownable);

    Set principals = Subject.get().getPrincipals();

    String className = invocation.getMethod().getDeclaringClass().getName();
    String methodName = invocation.getMethod().getName();

    if (manager.checkPermission(principals, className + "." + methodName, context)) {
      result = invocation.proceed();
    } else {
      throw new SecurityException("Access denied to " + invocation.getMethod().getName());
    }

    return result;
  }
}
