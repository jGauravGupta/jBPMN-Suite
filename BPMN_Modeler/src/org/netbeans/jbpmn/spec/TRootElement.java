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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.modeler.specification.model.document.IDefinitionElement;
import org.netbeans.modeler.specification.model.document.IRootElement;

/**
 * <p>
 * Java class for tRootElement complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tRootElement">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRootElement")
@XmlSeeAlso({
    TItemDefinition.class,
    TCategory.class,
    TEndPoint.class,
    TPartnerRole.class,
    TPartnerEntity.class,
    TCollaboration.class,
    TSignal.class,
    TEventDefinition.class,
    TDataStore.class,
    TError.class,
    TCorrelationProperty.class,
    TMessage.class,
    TEscalation.class,
    TCallableElement.class,
    TResource.class,
    TInterface.class

})
public abstract class TRootElement
        extends TBaseElement implements IRootElement {
//@XmlTransient
//    IDefinitionElement definitionElement ;//TBF_CODE ref is attached for only TEventDefinition and for other still need to fix .
//    // Mainly Created for TEventDefinition Class
//    //that need to access TError , TEsclation etc;
//    @Override
//    public IDefinitionElement getDefinitionElement(){
//        return definitionElement;
//    }
//    @Override
//    public void setDefinitionElement(IDefinitionElement definitionElement){
//        this.definitionElement = definitionElement;
//    }

}
