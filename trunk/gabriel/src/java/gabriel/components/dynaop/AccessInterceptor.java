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
import gabriel.components.CallAccessManager;

import java.util.Set;

public class AccessInterceptor implements Interceptor {
  private CallAccessManager manager;

  public AccessInterceptor(CallAccessManager manager) {
    this.manager = manager;
  }

  public boolean checkPermission(Set principals, String className, String methodName) {
    if (manager.checkPermission(principals, className + "." + methodName)) {
      return true;
    } else {
      return false;
    }
  }

  public Object intercept(Invocation invocation) throws Throwable {
    Object result;

    String className = invocation.getMethod().getDeclaringClass().getName();
    String methodName = invocation.getMethod().getName();

    Set principals = Subject.get().getPrincipals();

    if (checkPermission(principals, className, methodName)) {
      result = invocation.proceed();
    } else {
      throw new SecurityException("Access denied to " + invocation.getMethod().getName());
    }

    return result;
  }
}
