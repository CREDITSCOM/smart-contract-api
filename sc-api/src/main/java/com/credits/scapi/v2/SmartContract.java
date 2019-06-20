package com.credits.scapi.v2;

import com.credits.scapi.internal.v0.SmartContractApi;

import java.math.BigDecimal;

public abstract class SmartContract extends SmartContractApi {

    protected final transient long accessId = 0;
    protected final transient String initiator = null;
    protected final String contractAddress = null;

    final void sendTransaction(String from, String to, double amount, double fee, byte... userData) {
    }

    final protected Object invokeExternalContract(String contractAddress, String method, Object... params) {
        return null;
    }

    final protected byte[] getSeed() {
        return null;
    }

    final protected BigDecimal getBalance(String addressBase58) {
        return null;
    }
}
