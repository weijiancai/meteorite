package com.ectongs.ideaplugin.configs;

import com.ectongs.ideaplugin.EctongsApplicationPlugin;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.meteorite.core.util.UFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsConfigurable implements Configurable {
    private JTextField tfJsdocDir;
    private JButton btnSelectJsdocDir;
    private JTextField tfJsFile;
    private JButton btnSelectJsFile;
    private JPanel root;
    private JButton btnSelectOutDir;
    private JTextField tfOutDir;

    private String tplDir;

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
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String jsDocDir = tfJsdocDir.getText();
        String jsFile= tfJsFile.getText();
        String outDir = tfOutDir.getText();

        copyJsdocTemplate();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(jsDocDir).append(" ").append(jsFile).append(" -t ").append(tplDir).append(" -d ").append(outDir);
            File batFile = new File(EctongsApplicationPlugin.BASE_DIR, "jsDoc.bat");
            PrintWriter pw = new PrintWriter(new FileWriter(batFile));
            pw.write(sb.toString());
            pw.flush();
            pw.close();
            Process process = Runtime.getRuntime().exec(batFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
//            batFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            UFile.copyTreeFromClassPath("template/ectong/", tmpDir);
        }

        tplDir = tmpDir.getAbsolutePath() + "/template/ectong/";
    }
}
