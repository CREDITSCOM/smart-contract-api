package com.credits.scapi.v0;

import java.io.Serializable;
import java.math.BigDecimal;

@Deprecated
public abstract class SmartContract implements Serializable {

    protected final transient long accessId = 0;
    protected final transient String initiator = null;
    protected final String contractAddress = null;

    final protected void sendTransaction(String from, String to, double amount, double fee, byte... userData) {
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

