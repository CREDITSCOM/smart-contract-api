package com.credits.scapi.internal.v0;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class SmartContractApi implements Serializable {

    abstract void sendTransaction(String from, String to, double amount, double fee, byte... userData);

    abstract Object invokeExternalContract(String contractAddress, String method, Object... params);

    abstract byte[] getSeed();

    abstract BigDecimal getBalance(String addressBase58);

    protected abstract String payable(BigDecimal amount, byte[] userData);
}
