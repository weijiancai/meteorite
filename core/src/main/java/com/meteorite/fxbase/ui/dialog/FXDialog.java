package com.meteorite.fxbase.ui.dialog;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.Effect;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.tools.Platform;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class FXDialog {
    /**************************************************************************
     *
     * Static fields
     *
     **************************************************************************/

    /**
     * Defines a native dialog style.
     * The dialogs rendered using this style will have a native title bar.
     */
    public static final String STYLE_CLASS_NATIVE = "native"; //$NON-NLS-1$

    /**
     * Defines a cross-platform dialog style.
     * The dialogs rendered using this style will have a cross-platform title bar.
     */
    public static final String STYLE_CLASS_CROSS_PLATFORM = "cross-platform"; //$NON-NLS-1$

    /**
     * Defines a dialog style with no decorations.
     * The dialogs rendered using this style will not have a title bar.
     */
    public static final String STYLE_CLASS_UNDECORATED = "undecorated"; //$NON-NLS-1$

    protected static final URL DIALOGS_CSS_URL = FXDialog.class.getResource("old-dialogs.css"); //$NON-NLS-1$
    protected static final int HEADER_HEIGHT = 28;

    public static final List<String> COMMON_STYLE_CLASSES;
    static {
        COMMON_STYLE_CLASSES = Collections.unmodifiableList(
                Arrays.asList("root", "dialog", "decorated-root", "windows", "heavyweight", "lightweight")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    }



    /**************************************************************************
     *
     * Private fields
     *
     **************************************************************************/

    protected BorderPane root;
    protected HBox windowBtns;
    protected Button closeButton;
    protected Button minButton;
    protected Button maxButton;
    protected Rectangle resizeCorner;
    protected Label titleLabel;
    protected ToolBar dialogTitleBar;
    protected double mouseDragDeltaX = 0;
    protected double mouseDragDeltaY = 0;

    protected StackPane lightweightDialog;
    protected boolean modal = false;



    // shake support
    private double initialX = 0;
    private final DoubleProperty shakeProperty = new SimpleDoubleProperty(this, "shakeProperty", 0.0) { //$NON-NLS-1$
        @Override protected void invalidated() {
            setX(initialX + shakeProperty.get() * 25);
        }
    };



    /**************************************************************************
     *
     * Constructors
     *
     **************************************************************************/

    protected FXDialog() {
        // no-op, but we expect subclasses to call init(...) once they have
        // initialised their abstract property methods.
    }



    /**************************************************************************
     *
     * Public API
     *
     **************************************************************************/

    protected final void init(String title) {
        titleProperty().set(title);

        root = new BorderPane();

        lightweightDialog = new StackPane() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                if (resizeCorner != null) {
                    resizeCorner.relocate(getWidth() - resizeCorner.getWidth(),
                            getHeight() - resizeCorner.getHeight());
                }
            }
        };
        lightweightDialog.getChildren().add(root);
        lightweightDialog.getStyleClass().addAll("dialog", "decorated-root",  //$NON-NLS-1$ //$NON-NLS-2$
                Platform.getCurrent().getPlatformId());


        // --- resize corner
        resizeCorner = new Rectangle(10, 10);
        resizeCorner.getStyleClass().add("window-resize-corner"); //$NON-NLS-1$
        resizeCorner.setManaged(false);
        lightweightDialog.getChildren().add(resizeCorner);


        // --- titlebar (only used for cross-platform look)
        dialogTitleBar = new ToolBar();
        dialogTitleBar.getStyleClass().add("window-header"); //$NON-NLS-1$
        dialogTitleBar.setPrefHeight(HEADER_HEIGHT);
        dialogTitleBar.setMinHeight(HEADER_HEIGHT);
        dialogTitleBar.setMaxHeight(HEADER_HEIGHT);

        titleLabel = new Label();
        titleLabel.setMaxHeight(Double.MAX_VALUE);
        titleLabel.getStyleClass().add("window-title"); //$NON-NLS-1$
        titleLabel.setText(titleProperty().get());

        titleProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable valueModel) {
                titleLabel.setText(titleProperty().get());
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // add close min max
        closeButton = new WindowButton("close"); //$NON-NLS-1$
        closeButton.setFocusTraversable(false);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                FXDialog.this.hide();
            }
        });
        minButton = new WindowButton("minimize"); //$NON-NLS-1$
        minButton.setFocusTraversable(false);
        minButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                setIconified(isIconified());
            }
        });

        maxButton = new WindowButton("maximize"); //$NON-NLS-1$
        maxButton.setFocusTraversable(false);

        windowBtns = new HBox(3);
        windowBtns.getStyleClass().add("window-buttons"); //$NON-NLS-1$
        windowBtns.getChildren().addAll(minButton, maxButton, closeButton);

        dialogTitleBar.getItems().addAll(titleLabel, spacer, windowBtns);
        root.setTop(dialogTitleBar);


        // --- listeners
        getStyleClass().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                while (c.next()) {
                    updateStageStyle(new ArrayList<String>(c.getRemoved()),
                            new ArrayList<String>(c.getAddedSubList()));
                }
            }
        });

        resizableProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                updateResizable();
            }
        });
        updateResizable();

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> valueModel, Boolean oldValue, Boolean newValue) {
                boolean active = ((ReadOnlyBooleanProperty)valueModel).get();
                lightweightDialog.pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, active);
            }
        });



        // update the stage style based on set style class (although they are most
        // likely empty right now, but we have a listener above to deal with changes).
        updateStageStyle(null, getStyleClass());
    }

    private void updateResizable() {
        resizeCorner.setVisible(resizableProperty().get());

        if (maxButton != null) {
            maxButton.setVisible(resizableProperty().get());

            if (resizableProperty().get()) {
                if (! windowBtns.getChildren().contains(maxButton)) {
                    windowBtns.getChildren().add(1, maxButton);
                }
            } else {
                windowBtns.getChildren().remove(maxButton);
            }
        }
    }



    public void shake() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(2);

        KeyValue keyValue0 = new KeyValue(shakeProperty, 0.0, Interpolator.EASE_BOTH);
        KeyValue keyValue1 = new KeyValue(shakeProperty, -1.0, Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(shakeProperty, 1.0, Interpolator.EASE_BOTH);

        initialX = getX();

        final double sectionDuration = 50;
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, keyValue0),
                new KeyFrame(Duration.millis(sectionDuration),     keyValue1),
                new KeyFrame(Duration.millis(sectionDuration * 3), keyValue2),
                new KeyFrame(Duration.millis(sectionDuration * 4), keyValue0)
        );
        timeline.play();
    }

    public ObservableList<String> getStyleClass() {
        return lightweightDialog.getStyleClass();
    }

    protected boolean isNativeStyleClassSet() {
        return getStyleClass().contains(STYLE_CLASS_NATIVE);
    }

    protected boolean isCrossPlatformStyleClassSet() {
        return getStyleClass().contains(STYLE_CLASS_CROSS_PLATFORM);
    }

    protected boolean isUndecoratedStyleClassSet() {
        return getStyleClass().contains(STYLE_CLASS_UNDECORATED);
    }

    protected void setCrossPlatformStyleEnabled(boolean enabled) {
        dialogTitleBar.setVisible(enabled);
        dialogTitleBar.setManaged(enabled);
    }

    protected void setNativeStyleEnabled(boolean enabled) {
        dialogTitleBar.setVisible(false);
        dialogTitleBar.setManaged(false);
    }

    protected void setUndecoratedStyleEnabled(boolean enabled) {
        dialogTitleBar.setVisible(false);
        dialogTitleBar.setManaged(false);
    }



    /***************************************************************************
     *
     * Abstract API
     *
     **************************************************************************/

    public abstract void show();

    public abstract void hide();

    public abstract Window getWindow();

    public abstract void sizeToScene();

    public abstract double getX();

    public abstract void setX(double x);

    // --- resizable
    abstract BooleanProperty resizableProperty();


    // --- focused
    abstract ReadOnlyBooleanProperty focusedProperty();


    // --- title
    abstract StringProperty titleProperty();

    // --- content
    public abstract void setContentPane(Pane pane);

    // --- root
    public abstract Node getRoot();

    public abstract ObservableList<String> getStylesheets();


    // --- width
    /**
     * Property representing the width of the dialog.
     */
    abstract ReadOnlyDoubleProperty widthProperty();


    // --- height
    /**
     * Property representing the height of the dialog.
     */
    abstract ReadOnlyDoubleProperty heightProperty();


    /**
     * Sets whether the dialog can be iconified (minimized)
     * @param iconifiable if dialog should be iconifiable
     */
    abstract void setIconifiable(boolean iconifiable);

    abstract void setIconified(boolean iconified);

    abstract boolean isIconified();

    /**
     * Sets whether the dialog can be closed
     * @param closable if dialog should be closable
     */
    abstract void setClosable( boolean closable );

    abstract void setModal(boolean modal);

    abstract boolean isModal();

    abstract void setEffect(Effect e);



    /***************************************************************************
     *
     * Implementation
     *
     **************************************************************************/

    private boolean updateLock = false;
    private void updateStageStyle(List<? extends String> removedStyles, List<? extends String> addedStyles) {
        if (updateLock) {
            return;
        }

        updateLock = true;
//        ObservableList<String> styleClasses = getStyleClass();

        if (removedStyles != null && ! removedStyles.isEmpty()) {
            // remove styling
            // TODO handle!
        }

        if (addedStyles != null && ! addedStyles.isEmpty()) {
            // add styling
            for (String newStyle : addedStyles) {
                if (newStyle.equals("cross-platform")) {
                    setCrossPlatformStyleEnabled(true);
                } else if (newStyle.equals("native")) {
                    setNativeStyleEnabled(true);
                } else if (newStyle.equals("undecorated")) {
                    setUndecoratedStyleEnabled(true);
                } else {
                }
            }
        }

        updateLock = false;
    }



    /***************************************************************************
     *
     * Support Classes
     *
     **************************************************************************/

    private static class WindowButton extends Button {
        WindowButton(String name) {
            getStyleClass().setAll("window-button"); //$NON-NLS-1$
            getStyleClass().add("window-"+name+"-button"); //$NON-NLS-1$ //$NON-NLS-2$
            StackPane graphic = new StackPane();
            graphic.getStyleClass().setAll("graphic"); //$NON-NLS-1$
            setGraphic(graphic);
            setMinSize(17, 17);
            setPrefSize(17, 17);
        }
    }



    /***************************************************************************
     *
     * Stylesheet Handling
     *
     **************************************************************************/
    protected static final PseudoClass ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active"); //$NON-NLS-1$
}
