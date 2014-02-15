/**
 * 
 */
package com.meteorite.core.parser.capub;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.meteorite.core.util.UtilFile;
import com.meteorite.core.util.jaxb.JAXBUtil;

/**
 * @author wei_jc
 *
 */
@XmlRootElement
public class CapubBean {
	private int startId;
	private int endId;
	private int currentId;
	private int count;
	
	private Set<String> noFoundList = new HashSet<String>();
	private File capubFile;

	public CapubBean() {
		
	}
	public CapubBean(int startId, int endId) {
		super();
		this.startId = startId;
		this.endId = endId;
		this.currentId = endId;
		
		capubFile = new File(CapubBO.capubDir, startId + "-" + endId + ".xml");
		
		if(capubFile.exists()) {
			try {
				CapubBean bean = JAXBUtil.unmarshal(UtilFile.readString(capubFile), CapubBean.class);
				this.setStartId(bean.getStartId());
				this.setEndId(bean.getEndId());
				this.currentId = bean.getCurrentId();
				this.setNoFoundList(bean.getNoFoundList());
				this.count = bean.getCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			save();
		}
	}
	
	public void save() {
		try {
			String str = JAXBUtil.marshalToString(this, CapubBean.class);
			UtilFile.write(str.getBytes("UTF-8"), capubFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@XmlAttribute
	public int getStartId() {
		return startId;
	}
	public void setStartId(int startId) {
		this.startId = startId;
	}
	
	@XmlAttribute
	public int getEndId() {
		return endId;
	}
	public void setEndId(int endId) {
		this.endId = endId;
	}
	
	@XmlAttribute
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	
	@XmlAttribute
	public int getCount() {
		count = endId - currentId;
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Set<String> getNoFoundList() {
		return noFoundList;
	}
	public void setNoFoundList(Set<String> noFoundList) {
		this.noFoundList = noFoundList;
	}
	
	public void addNotFound(String id) {
		noFoundList.add(id);
	}
}
