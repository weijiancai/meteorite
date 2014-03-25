/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.dsconfig;

import cc.csdn.base.util.UtilString;
import com.ectongs.http.DataListOption;
import com.ectongs.http.HttpAccepter;
import com.ectongs.util.xml.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author wei_jc
 */
public class DataListSender {
    private DataListOption[] options;

    public DataListSender(String className, String retrieveArgs) {
        BaseHttpService service = new BaseHttpService("flex/DataListAction");
        service.addParameter("className", className);
        service.addParameter("dbi", "");
        service.addParameter("useCache", "false");
        if(!UtilString.isEmpty(retrieveArgs)) {
            service.addParameter("retrieveArgs", "retrieveArgs");
        }

        HttpAccepter accepter = service.send("A64F3E83DF79566103A66A14C27EA64D");
        execute(accepter);
    }

    protected void execute(HttpAccepter accepter) {
        String xmlStr = accepter.getData();
        Document doc = DomUtil.parse(xmlStr);
        if (null != doc) {
            NodeList nodeList = doc.getElementsByTagName("row");
            options = new DataListOption[nodeList.getLength() + 1];
            options[0] = new DataListOption("", " ");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NamedNodeMap namedNodeMap = node.getAttributes();
                String data = namedNodeMap.getNamedItem("data").getNodeValue();
                String label = namedNodeMap.getNamedItem("label").getNodeValue();
                options[i + 1] = new DataListOption(data, label);
            }
        }
    }
    
    public DataListOption[] getOptions() {
        return options;
    }
}
