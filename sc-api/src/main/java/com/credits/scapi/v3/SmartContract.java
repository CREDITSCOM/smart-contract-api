package com.credits.scapi.v3;

import com.credits.scapi.internal.v1.SmartContractApi;

import java.math.BigDecimal;

public abstract class SmartContract extends SmartContractApi {

    private static final long serialVersionUID = -7107388825339899265L;
    protected final transient long accessId = 0;
    protected final transient String initiator = null;
    protected final String contractAddress = null;

    @Override
    protected final void sendTransaction(String from, String to, double amount, byte... userData) {
    }

    @Override
    protected final Object invokeExternalContract(String contractAddress, String method, Object... params) {
        return null;
    }

    @Override
    protected final byte[] getSeed() {
        return null;
    }

    @Override
    protected final BigDecimal getBalance(String addressBase58) {
        return null;
    }

    protected final long getBlockchainTimeMills() {
        return 0;
    }
}
