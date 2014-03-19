package com.meteorite.core.util;

import com.meteorite.core.meta.model.Meta;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UObjectTest {
    @Test
    public void testClone() throws Exception {
        Meta meta = new Meta();
        meta.setName("Meta");
        meta.setDisplayName("元数据");
        meta.setValid(true);

        Meta target = new Meta();
        UObject.clone(meta, target);
        assertThat(target.getName(), equalTo(meta.getName()));
        assertThat(target.getDisplayName(), equalTo(meta.getDisplayName()));
        assertThat(target.isValid(), equalTo(meta.isValid()));
    }
}
