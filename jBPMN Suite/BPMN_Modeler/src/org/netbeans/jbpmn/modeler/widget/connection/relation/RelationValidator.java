/** Copyright [2014] Gaurav Gupta
   *
   *Licensed under the Apache License, Version 2.0 (the "License");
   *you may not use this file except in compliance with the License.
   *You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   *Unless required by applicable law or agreed to in writing, software
   *distributed under the License is distributed on an "AS IS" BASIS,
   *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   *See the License for the specific language governing permissions and
   *limitations under the License.
   */
 package org.netbeans.jbpmn.modeler.widget.connection.relation;

import org.netbeans.modeler.widget.connection.relation.IRelationProxy;
import org.netbeans.jbpmn.core.widget.EndEventWidget;
import org.netbeans.jbpmn.core.widget.GroupWidget;
import org.netbeans.jbpmn.core.widget.StartEventWidget;
import org.netbeans.jbpmn.core.widget.TextAnnotationWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationNodeWidget;
import org.netbeans.modeler.widget.connection.relation.IRelationValidator;

/**
 * RelationValidator manages the dispatch of relation validation events, while
 * also having the meta layer validate the proposed relationship against rules.
 */
public class RelationValidator implements IRelationValidator {

    /**
     *
     */
   

    /**
     *
     * Called to validate the proposed relationship. Calling this method will
     * result in the firing of the IRelationValidatorEventsSink methods.
     *
     * @param proxy[in] The proxy to validate
     *
     * @return HRESULT
     *
     */
    @Override
    public boolean validateRelation(IRelationProxy proxy) {
        boolean valid = true;
        if (proxy.getTarget() == proxy.getSource()) {
            valid = false;
        } else if (proxy.getTarget() instanceof StartEventWidget) {
            valid = false;
            //proxy.getTarget().setStatus(NodeWidgetStatus.INVALID);
        } else if (proxy.getSource() instanceof EndEventWidget) {
            valid = false;
        }else if (proxy.getSource() instanceof GroupWidget && !(proxy.getTarget() instanceof TextAnnotationWidget)) {
            valid = false;
        }else if (proxy.getTarget() instanceof GroupWidget && !(proxy.getSource() instanceof TextAnnotationWidget)) {
            valid = false;
        }else if (proxy.getTarget() instanceof ConversationNodeWidget && proxy.getSource() instanceof ConversationNodeWidget) {
            valid = false;
        }
        
         
        
        
        return valid;
    }
}
