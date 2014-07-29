package com.meteorite.core.datasource.ftp;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * FTP 数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FtpDataSource implements DataSource {
    private FTPClient client;

    public FtpDataSource(String ip, String user, String password) throws IOException {
        client = new FTPClient();
        client.connect(ip);
        boolean isSuccess = client.login(user, password);
        System.out.println(isSuccess);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public DataSourceType getType() {
        return null;
    }

    @Override
    public Meta getProperties() {
        return null;
    }

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {

    }

    @Override
    public INavTreeNode getNavTree() throws Exception {
        return null;
    }

    @Override
    public INavTreeNode getNavTree(String parent) throws Exception {
        return null;
    }

    @Override
    public void load() throws Exception {
        /*String[] names = client.listNames("/wei_jc");
        for (String name : names) {
            System.out.println(name);
        }*/
        System.out.println(client.getReplyCode());
        System.out.println(FTPReply.isPositiveCompletion(client.getReplyCode()));
        System.out.println(client.list());
        System.out.println(client.getStatus());
        FTPFile[] files = client.listFiles();
        for (FTPFile file : files) {
            System.out.println(file.getLink());
        }
    }

    private void iterator(String path) {

    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public ResourceItem getResource(String path) {
        return null;
    }

    @Override
    public void save(Map<String, IValue> valueMap) throws Exception {

    }

    @Override
    public void save(IPDB pdb) throws Exception {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
