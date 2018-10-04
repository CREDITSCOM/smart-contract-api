package com.credits.wallet.desktop.utils;

import com.credits.leveldb.client.data.SmartContractData;
import com.credits.wallet.desktop.AppState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.credits.wallet.desktop.testUtils.FakeData.addressBase58;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectKeeperTest {

    ConcurrentHashMap<String,SmartContractData> someData = new ConcurrentHashMap<>();
    ObjectKeeper<ConcurrentHashMap<String,SmartContractData>> objectKeeper;

    @Before
    public void setUp() throws IOException {
        objectKeeper = new ObjectKeeper<>("obj.ser");
        AppState.account = addressBase58;
        deleteDirectoryStream(ObjectKeeper.cacheDirectory);
        someData.put("1",new SmartContractData(null, null, "aaa", null, "bad hash",null));
    }


    @After
    public void after() throws IOException {
        deleteDirectoryStream(ObjectKeeper.cacheDirectory);
    }

    @Test
    public void serializeThenDeserialize(){
        objectKeeper.serialize(someData);
        assertTrue(objectKeeper.getSerializedObjectPath().toFile().exists());

        Map restoredObject = objectKeeper.deserialize();
        assertEquals(someData, restoredObject);
    }

    @Test
    public void deserializeThenSerialize() {
        objectKeeper.serialize(someData);
        objectKeeper.deserializeThenSerialize(
            objectKeeper.new Modifier(){
            @Override
            ConcurrentHashMap<String, SmartContractData> modify(ConcurrentHashMap<String, SmartContractData> restoredObject) {
                restoredObject.put("2", new SmartContractData(null, null, "BBB", null, "bad hash",null));
                return restoredObject;
            }
        });
        assertEquals(2, someData.size());
    }

    @Test
    public void usingSerializedObject(){
        objectKeeper.serialize(someData);
        ConcurrentHashMap<String, SmartContractData> restoredObject = objectKeeper.deserialize();
        restoredObject.put("2", new SmartContractData(null, null, "BBB", null, "bad hash",null));
        objectKeeper.serialize(restoredObject);
        restoredObject = objectKeeper.deserialize();
        assertEquals(2, restoredObject.size());
    }

    void deleteDirectoryStream(Path path) throws IOException {
        if(path.toFile().exists()) {
            Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        }
    }
}
