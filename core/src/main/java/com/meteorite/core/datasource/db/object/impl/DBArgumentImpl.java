package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.DBArgument;
import com.meteorite.core.datasource.db.object.DBDataType;
import com.meteorite.core.datasource.db.object.DBMethod;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBArgumentImpl extends DBObjectImpl implements DBArgument {
    private DBDataType dataType;
    private DBMethod method;
    private int position;
    private int sequence;
    private boolean isInput;
    private boolean isOutput;

    public DBArgumentImpl() {
        this.setObjectType(DBObjectType.ARGUMENT);
    }

    @Override
    public DBDataType getDataType() {
        return dataType;
    }

    @Override
    public DBMethod getMethod() {
        return method;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    @Override
    public boolean isInput() {
        return isInput;
    }

    @Override
    public boolean isOutput() {
        return isOutput;
    }

    @Override
    public String getIcon() {
        if (isInput && isOutput) {
            return DBIcons.DBO_ARGUMENT_IN_OUT;
        } else if (isInput) {
            return DBIcons.DBO_ARGUMENT_IN;
        } else if (isOutput) {
            return DBIcons.DBO_ARGUMENT_OUT;
        }
        return super.getIcon();
    }

    public void setDataType(DBDataType dataType) {
        this.dataType = dataType;
    }

    public void setMethod(DBMethod method) {
        this.method = method;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setInput(boolean isInput) {
        this.isInput = isInput;
    }

    public void setOutput(boolean isOutput) {
        this.isOutput = isOutput;
    }
}
