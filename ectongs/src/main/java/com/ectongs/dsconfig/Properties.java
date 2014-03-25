package com.ectongs.dsconfig;

import com.ectongs.http.DataListOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class Properties implements Initializable {
    @FXML
    private TextField colIdTF;
    @FXML
    private TextField displayNameTF;
    @FXML
    private ChoiceBox<DataListOption> editStyleCB;
    @FXML
    private CheckBox isUpdateCB;
    @FXML
    private ChoiceBox<DataListOption> typeCB;
    @FXML
    private TextField classAttrTF;
    @FXML
    private TextField groupNameTF;
    @FXML
    private CheckBox isGroupOpenCB;
    @FXML
    private CheckBox usePreValueCB;
    @FXML
    private CheckBox isDisplayCB;
    @FXML
    private CheckBox isReadonlyCB;
    @FXML
    private CheckBox isRequireCB;
    @FXML
    private CheckBox isSingleLineCB;
    @FXML
    private TextField colSpanTF;
    @FXML
    private TextField sortNumTF;
    @FXML
    private TextField widthTF;
    @FXML
    private TextField heightTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataListSender formatSender = new DataListSender("base.datalist.dscolumnformatlist", null);
        DataListOption[] formatOptions = formatSender.getOptions();
        typeCB.getItems().addAll(formatOptions);

        DataListSender editStyleSender = new DataListSender("base.datalist.editstylelist", null);
        DataListOption[] editStyleOptions = editStyleSender.getOptions();
        editStyleCB.getItems().addAll(editStyleOptions);
    }
}
