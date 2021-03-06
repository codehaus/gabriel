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

package gabriel.test.subject;

import gabriel.Subject;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmock.MockObjectTestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author stephan
 * @version $id$
 */

public class SubjectTest extends MockObjectTestCase {

  public static Test suite() {
    return new TestSuite(SubjectTest.class);
  }

  public void testSetPrincipalsUsesNewSet() {
    Subject subject = new Subject("TestSubject");
    Set principals = new HashSet();
    subject.setPrincipals(principals);
    assertNotSame("Subject uses new set for principals", principals, subject.getPrincipals());
  }

  public void testGetName() {
    Subject subject = new Subject("TestSubject");
    assertEquals("Subject reports correct name", "TestSubject", subject.getName());
  }

  public void testEquals() {
    Subject subject1 = new Subject("TestSubject");
    Subject subject2 = new Subject("TestSubject");
    assertTrue("Two subjects are equal", subject1.equals(subject2));
  }

  public void testTheadLocal() {
    Subject subject = new Subject("TestSubject");

    // A better test should use a new thread and make sure
    // that each thread sees a differnt subject

    Subject.set(subject);
    Subject other = Subject.get();
    assertSame("Subject static returns same subject", subject, other);
  }

  public void testHashcode() {
    Subject s1 = new Subject("TestSubject");
    Subject s2 = new Subject("TestSubject");

    assertTrue("Hashcode is equal for subjects with same name", s1.hashCode() == s2.hashCode());
  }

  public void testNullHashcode() {
    Subject s1 = new Subject(null);

    assertEquals("Hashcode works with null name", 0, s1.hashCode());
  }
}
