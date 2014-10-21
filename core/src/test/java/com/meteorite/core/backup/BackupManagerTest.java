package com.meteorite.core.backup;

import com.meteorite.core.config.SystemManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackupManagerTest {

    @Test
    public void testBackup() throws Exception {
        SystemManager.getInstance().init();
        BackupManager.getInstance().backup(null);
    }

    @Test
    public void testRestore() throws Exception {
        BackupManager.getInstance().restore(null, null);
    }
}