package com.ectongs.ideaplugin.configs;

import com.ectongs.ideaplugin.EctongsApplicationPlugin;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsConfigurable implements Configurable {
    private JTextField tfJsdocDir;
    private JButton btnSelectJsdocDir;
    private JTextField tfOutDir;
    private JButton btnSelectOutDir;
    private JPanel root;

    @Nls
    @Override
    public String getDisplayName() {
        return "ectongs";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return root;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        String nodejsDir = tfJsdocDir.getText();
        String outDir = tfOutDir.getText();


//        Runtime.getRuntime().exec();
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

    private void copyJsdocTemplate() {
        File tmpDir = new File(EctongsApplicationPlugin.BASE_DIR, "jsdoc-template");
        if(!tmpDir.exists()) {
            tmpDir.mkdirs();

            // 复制模板

        }
    }
}
