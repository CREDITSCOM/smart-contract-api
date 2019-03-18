package com.credits.pojo.apiexec;

import com.credits.general.pojo.ApiResponseData;
import com.credits.general.pojo.ByteCodeObjectData;

import java.util.Arrays;
import java.util.List;

public class SmartContractGetResultData extends ApiResponseData {

    private static final long serialVersionUID = 4691482244448740351L;

    public final List<ByteCodeObjectData> byteCodeObjects;
    public byte[] contractState;
    public final boolean stateCanModify;

    public SmartContractGetResultData(ApiResponseData apiResponseData, List<ByteCodeObjectData> byteCodeObjects,
        byte[] contractState, boolean stateCanModify) {
        super(apiResponseData);
        this.byteCodeObjects = byteCodeObjects;
        this.contractState = contractState;
        this.stateCanModify = stateCanModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SmartContractGetResultData that = (SmartContractGetResultData) o;

        if (stateCanModify != that.stateCanModify) {
            return false;
        }
        if (byteCodeObjects != null ? !byteCodeObjects.equals(that.byteCodeObjects) : that.byteCodeObjects != null) {
            return false;
        }
        return Arrays.equals(contractState, that.contractState);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (byteCodeObjects != null ? byteCodeObjects.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(contractState);
        result = 31 * result + (stateCanModify ? 1 : 0);
        return result;
    }
}
