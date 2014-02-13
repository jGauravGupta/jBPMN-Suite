/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jbpm.debugger.viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.type.UnknownTypeException;
import javax.swing.Action;
import org.netbeans.api.debugger.jpda.ObjectVariable;
import org.netbeans.spi.viewmodel.Models;
import org.netbeans.spi.viewmodel.NodeActionsProvider;
import org.netbeans.spi.viewmodel.NodeActionsProviderFilter;

public class LocalsViewActionsProviderFilter implements NodeActionsProviderFilter {

    public Action[] getActions(NodeActionsProvider original, Object node) throws UnknownTypeException, org.netbeans.spi.viewmodel.UnknownTypeException {
        Action[] actions = original.getActions(node);
        List myActions = new ArrayList();
        if (node instanceof ObjectVariable) {
            ObjectVariable objectVariable = (ObjectVariable) node;
            if (objectVariable.getClassType().getName().equals("org.jbpm.ruleflow.instance.RuleFlowProcessInstance")) {

                myActions.add(
                        Models.createAction(
                                "Open ProcessInstance Viewer",
                                new ProcessInstanceViewerAction(objectVariable),
                                Models.MULTISELECTION_TYPE_EXACTLY_ONE));
            }
        }
        if (myActions.isEmpty()) {
            return actions;
        }
        ArrayList actionToAdd = new ArrayList();
        actionToAdd.addAll(Arrays.asList(actions));
        actionToAdd.addAll(myActions);
        return (Action[]) actionToAdd.toArray(new Action[actionToAdd.size()]);
    }

    public void performDefaultAction(NodeActionsProvider original, Object node) throws UnknownTypeException, org.netbeans.spi.viewmodel.UnknownTypeException {
        original.performDefaultAction(node);
    }

}
