package com.credits.scapi.internal.v1;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class SmartContractApi implements Serializable {

    private static final long serialVersionUID = 3388422227270554012L;

    protected abstract void sendTransaction(String from, String to, double amount, byte... userData);

    protected abstract Object invokeExternalContract(String contractAddress, String method, Object... params);

    protected abstract byte[] getSeed();

    protected abstract BigDecimal getBalance(String addressBase58);

    protected abstract long getBlockchainTimeMills();

    protected abstract String payable(BigDecimal amount, byte[] userData);
}
