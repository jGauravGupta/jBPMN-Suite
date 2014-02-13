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
 package org.netbeans.jbpmn.spec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tErrorEventDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tErrorEventDefinition">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEventDefinition">
 *       <attribute name="errorRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tErrorEventDefinition")
public class TErrorEventDefinition
    extends TEventDefinition
{

    @XmlAttribute
    protected String errorRef;//QName errorRef;

    /**
     * Gets the value of the errorRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getErrorRef() {
        return errorRef;
    }

    /**
     * Sets the value of the errorRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setErrorRef(String value) {
        this.errorRef = value;
    }
    
    
    
    
    
//    
//
//    /**
//     * @return the errorNameEmbedded
//     */
//    public String getErrorNameEmbedded() {
//        if (errorRef != null && !errorRef.isEmpty()) {
//            TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//            TError error = (TError) definitions.getRootElement(errorRef, TError.class);
//            if (error != null && error.getName() != null) {
//                return error.getName();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * @param errorNameEmbedded the errorNameEmbedded to set
//     */
//    public void setErrorNameEmbedded(String errorNameEmbedded) {
//        TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//        TError error = null;
//        if (errorRef != null && !errorRef.isEmpty()) {
//            error = (TError) definitions.getRootElement(errorRef, TError.class);
//        }
//        if (error == null) {
//            error = new TError();
//            errorRef = error.getId();
//            definitions.addRootElement(error);
//        }
//        if (errorNameEmbedded != null && !errorNameEmbedded.trim().isEmpty()) {
//            error.setName(errorNameEmbedded);
//        } else {
//            error.setName(null);
//        }
//
//    }
//    
////    void manageReferenceUnique(){
////        
////    }
//
//    /**
//     * @return the errorCodeEmbedded
//     */
//    public String getErrorCodeEmbedded() {
//         if (errorRef != null && !errorRef.isEmpty()) {
//            TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//            TError error = (TError) definitions.getRootElement(errorRef, TError.class);
//            if (error != null && error.getErrorCode()!= null) {
//                return error.getErrorCode();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * @param errorCodeEmbedded the errorCodeEmbedded to set
//     */
//    public void setErrorCodeEmbedded(String errorCodeEmbedded) {
//        TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//        TError error = null;
//        if (errorRef != null && !errorRef.isEmpty()) {
//            error = (TError) definitions.getRootElement(errorRef, TError.class);
//        }
//        if (error == null) {
//            error = new TError();
//            errorRef = error.getId();
//            definitions.addRootElement(error);
//        }
//        if (errorCodeEmbedded != null && !errorCodeEmbedded.trim().isEmpty()) {
//            error.setErrorCode(errorCodeEmbedded);
//        } else {
//            error.setErrorCode(null);
//        }
//    }
//    
    
    
    
    
    
    

}
