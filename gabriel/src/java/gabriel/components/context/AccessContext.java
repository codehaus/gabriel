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

package gabriel.components.context;

import java.util.List;


/**
 * Stores the context for access checks
 *
 * @author Stephan J. Schmidt
 * @version $Id: AccessContext.java,v 1.2 2004-06-18 11:29:41 stephan Exp $
 */
public interface AccessContext {
    /**
     * Modify the list of principals depending on the context
     *
     * @param principals Original principal list
     * @return modified principal list
     */
    public List modifyPrincipal(List principals);
}