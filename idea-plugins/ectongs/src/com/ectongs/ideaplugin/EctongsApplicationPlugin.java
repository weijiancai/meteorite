package com.ectongs.ideaplugin;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsApplicationPlugin implements ApplicationComponent {
    public static final File BASE_DIR = new File(PathManager.getSystemPath(), "ectongs");

    public EctongsApplicationPlugin() {
    }

    public void initComponent() {
        if (!BASE_DIR.exists()) {
            BASE_DIR.mkdirs();
        }
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "EctongsApplicationPlugin";
    }
}
