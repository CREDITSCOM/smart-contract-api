package com.credits.wallet.desktop.utils.sourcecode.codeArea.autocomplete;

import com.credits.wallet.desktop.utils.sourcecode.JavaReflect;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditsProposalsPopup extends Popup {
    private ListView<ProposalItem> listView = new ListView();

    private final static Logger LOGGER = LoggerFactory.getLogger(CreditsProposalsPopup.class);

    public static final List<String> javaKeywords =
        Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for",
            "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "String",
            "Boolean", "Integer", "Float", "Byte", "Short", "Long", "Character", "Double");

    public static Map<Method, String> parentsMethods = new HashMap<>();
    public static Map<Field, String> parentsFields = new HashMap<>();

    private static void defaultProposalsInit() {
        try {
            getFieldsAndMethodsFromSourceCode("SmartContract");
            getFieldsAndMethodsFromSourceCode("BasicStandard");
            getFieldsAndMethodsFromSourceCode("ExtensionStandard");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error on proposal popup init", e);
        }
    }

    private static void getFieldsAndMethodsFromSourceCode(String parentClass) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(parentClass);
        Map<Field, String> declaredFields = JavaReflect.getDeclaredFields(clazz);
        parentsFields.putAll(declaredFields);
        Map<Method, String> declaredMethods = JavaReflect.getDeclaredMethods(clazz);
        parentsMethods.putAll(declaredMethods);
    }


    public CreditsProposalsPopup() {
        super();
        defaultProposalsInit();
        listView.setMaxHeight(110);

        listView.addEventHandler(KeyEvent.KEY_PRESSED, (k) -> {
            if (k.getCode().equals(KeyCode.ESCAPE)) {
                this.clear();
                this.hide();
            }
        });

        listView.addEventHandler(KeyEvent.KEY_PRESSED, (k) -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                doProposalItemAction();
            }
        });

        listView.focusedProperty().addListener((observable, old, newPropertyValue) -> {
            if (!newPropertyValue) {
                CreditsProposalsPopup.this.hide();
            }
        });

        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, (k) -> {
            if (k.getButton() == MouseButton.PRIMARY) {
                doProposalItemAction();
            }
        });

        this.getContent().add(listView);
    }

    private void doProposalItemAction() {
        ProposalItem proposalItem = listView.getSelectionModel().getSelectedItem();
        proposalItem.action();
        this.hide();
    }

    public void clearAndHide() {
        this.clear();
        this.hide();
    }

    public void addItem(ProposalItem element) {
        listView.getItems().add(element);
    }

    public void clear() {
        listView.getItems().clear();
    }

    public boolean isEmpty() {
        return this.listView.getItems().isEmpty();
    }
}
