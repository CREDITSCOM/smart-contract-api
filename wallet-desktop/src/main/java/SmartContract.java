import com.credits.client.node.pojo.PoolData;
import com.credits.client.node.pojo.TransactionData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public abstract class SmartContract implements Serializable {

    private static final long serialVersionUID = -7544650022718657167L;
    protected final transient long accessId;
    protected final transient String initiator;
    protected final String contractAddress;

    public SmartContract() {
        initiator = null;
        accessId = 0;
        contractAddress = null;
    }

    final protected BigDecimal getBalance(String address) {
        return null;
    }

    final protected TransactionData getTransaction(String transactionId) {
        return null;
    }

    final protected List<TransactionData> getTransactions(String address, long offset, long limit) {
        return null;
    }

    final protected List<PoolData> getPoolList(long offset, long limit) {
        return null;
    }

    final protected PoolData getPoolInfo(byte[] hash, long index) {
        return null;
    }

    final protected void sendTransaction(String target, double amount, double fee, byte[] userData) {}

    final protected byte[] getSeed() { return null;}

    final protected Object invokeExternalContract(String externalSmartContractAddress, String externalSmartContractMethod, List externalSmartContractParams){ return null;}
    }
