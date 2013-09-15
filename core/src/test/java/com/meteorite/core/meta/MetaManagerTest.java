package com.meteorite.core.meta;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.core.util.JAXBUtil;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaManagerTest {
    @Test
    public void testToMeta() throws Exception {
        String str = "aaa";
        try {
            MetaManager.toMeta(str);
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("不能将非MetaElement的对象【java.lang.String】转化为Meta对象！"));
        }

        ProjectConfig projectConfig = SystemManager.getInstance().createProjectConfig("taobao");
        Meta meta = MetaManager.toMeta(projectConfig);
        assertThat(meta, notNullValue());
        assertThat(meta.getName(), equalTo("ProjectConfig"));
        assertThat(meta.getDisplayName(), equalTo("项目配置"));
        assertThat(meta.getDesc(), equalTo(""));
        assertThat(meta.isValid(), equalTo(true));
        assertThat(meta.getFileds().size(), equalTo(4));

        String metaXml = JAXBUtil.marshalToString(meta, Meta.class);
        System.out.println(metaXml);
    }

    @Test
    public void testToForm() throws Exception {
        ProjectConfig projectConfig = SystemManager.getInstance().createProjectConfig("taobao");
        Meta meta = MetaManager.toMeta(projectConfig);
        MetaForm metaForm = MetaManager.toForm(meta);

        String metaXml = JAXBUtil.marshalToString(metaForm, MetaForm.class);
        System.out.println(metaXml);
    }
}
