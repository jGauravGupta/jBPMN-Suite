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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tScriptTask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tScriptTask">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tTask">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}script" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="scriptFormat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "scriptTask") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tScriptTask", propOrder = {
    "script"
})
public class TScriptTask
    extends TTask
{

      @XmlElement
   // @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    protected String script;//TScript script;
    @XmlAttribute
    protected String scriptFormat;

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link TScript }
     *     
     */
   //  @XmlCDATA
    public String getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link TScript }
     *     
     */
    //  @XmlCDATA
    public void setScript(String value) {
        this.script = value;
    }

    /**
     * Gets the value of the scriptFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScriptFormat() {
        return scriptFormat;
    }

    /**
     * Sets the value of the scriptFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScriptFormat(String value) {
        this.scriptFormat = value;
    }

}
