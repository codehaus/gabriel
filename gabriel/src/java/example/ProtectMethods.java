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

package example;

import dynaop.Aspects;
import dynaop.Pointcuts;
import dynaop.ProxyFactory;
import gabriel.Principal;
import gabriel.Subject;
import gabriel.components.*;
import gabriel.components.dynaop.AccessInterceptor;
import gabriel.components.io.FileAclStore;
import gabriel.components.io.FileMethodStore;

import java.util.HashSet;
import java.util.Set;

/**
 * Shows how to protect the methods of an object.
 *
 * @author stephan
 * @version $id$
 */
public class ProtectMethods {
  public static void main(String[] args) {

    // First we setup some components which manage access
    // checking for us. We use versions that use files
    // to store mappings
    AclStore aclStore = new FileAclStore();
    MethodStore methodStore = new FileMethodStore();

    AccessManager manager = new AccessManagerImpl(aclStore);
    MethodAccessManager methodAccessManager = new MethodAccessManagerImpl(manager, methodStore);

    // The we setup our aop to wrap objects
    Aspects aspects = new Aspects();
    aspects.interceptor(Pointcuts.instancesOf(SecureObject.class),
        Pointcuts.ALL_METHODS, new AccessInterceptor(methodAccessManager));

    ProxyFactory proxyFactory = ProxyFactory.getInstance(aspects);


    Subject subject = new Subject("We");
    Set principals = new HashSet();
    subject.setPrincipals(principals);
    Subject.set(subject);

    // We create an object which has protected methods
    SecureObject object = (SecureObject) proxyFactory.wrap(new SecureObjectImpl("Dont change me!"));
    System.out.println(object.getName());
    try {
      object.setName("Changed!");
    } catch (Exception e) {
      System.out.println("Access denied for us to call setName()");
    }

    // We are now member of group "ExampleUser
    principals.add(new Principal("ExampleUser"));
    // We have to set the principals again as they are copied not referenced
    // inside subject.
    subject.setPrincipals(principals);
    object.setName("Changed!");
  }
}
