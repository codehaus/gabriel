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

import gabriel.Permission;
import gabriel.Principal;

/**
 * ContextAccessManager checks for access rights with permissions
 * in a certain context
 *
 * @author Stephan J. Schmidt
 * @version $Id: ContextAccessManager.java,v 1.1 2004-06-18 08:39:40 stephan Exp $
 */
public interface ContextAccessManager {
    /**
     * Check if a principal posseses the permission
     * Perhaps return int with -1,0,1 for denied,neutral,allow
     * The context could contain ownership and resources
     * so ContextAccessManager modifies the principals
     *
     * @param principal  Principal to check permission for
     * @param permission Permission to check
     * @param context    AccessContext to consider when checking for a permission
     * @return true if principal has permission
     */
    public boolean checkPermission(Principal principal, Permission permission, AccessContext context);
}
