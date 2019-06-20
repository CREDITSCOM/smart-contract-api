package com.credits.scapi.v0;

@Deprecated
public interface ExtensionStandard extends BasicStandard {

    void register();

    boolean buyTokens(String amount);
}
