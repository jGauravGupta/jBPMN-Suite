//package org.netbeans.jbpmn.spec.namespace;
//
//
////package org.omg.bpmn.spec.namespace;
////
////
//import java.util.HashMap;
//import java.util.Map;
////import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
////import org.eclipse.persistence.oxm.NamespacePrefixMapper;
//
//
//public class BPMNNamespacePrefixMapper extends NamespacePrefixMapper {
//
//   
//    private static final Map<String , String> namespace;
//    static {
//        namespace = new HashMap<String, String>();
//        namespace.put("http://www.omg.org/spec/BPMN/20100524/MODEL","");
//        namespace.put("http://www.omg.org/spec/BPMN/20100524/DI","bpmndi");
//        namespace.put("http://www.omg.org/spec/DD/20100524/DC","omgdc");
//        namespace.put("http://www.omg.org/spec/DD/20100524/DI","omgdi");
//        namespace.put("http://www.w3.org/2001/XMLSchema-instance","xsi");
//        namespace.put("http://jcp.org/en/jsr/detail?id=270" , "java");
//    }
//    
//    @Override
//    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
//        if (namespace.get(namespaceUri) == null) {
//            return "";
//        } else {
//            return namespace.get(namespaceUri);
//        }
//    }
//    
//    
//}