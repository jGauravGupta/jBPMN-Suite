/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.jbpmn.spec.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MultilineString extends XmlAdapter<String, String> {

    @Override
    public String marshal(String input) throws Exception {
        return input == null ? null : input.replaceAll("\n", "\\\\n");//"&#xA;"
    }

    @Override
    public String unmarshal(String output) throws Exception {
        return output == null ? null : output.replaceAll("\\\\n", "\n");//"&#xA;"
    }
}
