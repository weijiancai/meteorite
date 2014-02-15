package com.meteorite.core.util.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author wei_jc
 */
@XmlRootElement
public class ListBean<T> {
    private List<T> list;

    public ListBean() {}

    public ListBean(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
