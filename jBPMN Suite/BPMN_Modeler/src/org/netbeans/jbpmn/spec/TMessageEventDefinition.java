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
package org.netbeans.jbpmn.spec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.jbpmn.spec.extend.MessageHandler;
import org.netbeans.jbpmn.spec.extend.OperationHandler;

/**
 * <p>
 * Java class for tMessageEventDefinition complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tMessageEventDefinition">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEventDefinition">
 *       <sequence>
 *         <element name="operationRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="messageRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "tMessageEventDefinition", propOrder = {
//    "operationRef"
//})
@XmlType(name = "tMessageEventDefinition")
public class TMessageEventDefinition
        extends TEventDefinition implements MessageHandler, OperationHandler {

    protected String operationRef;//QName operationRef;
    @XmlAttribute
    protected String messageRef;//QName messageRef;

    /**
     * Gets the value of the operationRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getOperationRef() {
        return operationRef;
    }

    /**
     * Sets the value of the operationRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setOperationRef(String value) {
        this.operationRef = value;
    }

    /**
     * Gets the value of the messageRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getMessageRef() {
        return messageRef;
    }

    /**
     * Sets the value of the messageRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setMessageRef(String value) {
        this.messageRef = value;
    }

//    /**
//     * @return the messageNameEmbedded
//     */
//    public String getMessageNameEmbedded() {
//        if (messageRef != null && !messageRef.isEmpty()) {
//            TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//            TMessage message = (TMessage) definitions.getRootElement(messageRef, TMessage.class);
//            if (message != null && message.getName() != null) {
//                return message.getName();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * @param messageNameEmbedded the messageNameEmbedded to set
//     */
//    public void setMessageNameEmbedded(String messageNameEmbedded) {
//        TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//        TMessage message = null;
//        if (messageRef != null && !messageRef.isEmpty()) {
//            message = (TMessage) definitions.getRootElement(messageRef, TMessage.class);
//        }
//        if (message == null) {
//            message = new TMessage();
//            messageRef = message.getId();
//            definitions.addRootElement(message);
//        }
//        if (messageNameEmbedded != null && !messageNameEmbedded.trim().isEmpty()) {
//            message.setName(messageNameEmbedded);
//        } else {
//            message.setName(null);
//        }
//
//    }
}
