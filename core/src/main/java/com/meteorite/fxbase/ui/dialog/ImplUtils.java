package com.meteorite.fxbase.ui.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ImplUtils {
    private ImplUtils() {
        // no-op
    }

    public static void injectAsRootPane(Scene scene, Parent injectedParent, boolean useReflection) {
        Parent originalParent = scene.getRoot();
        scene.setRoot(injectedParent);

        if (originalParent != null) {
            getChildren(injectedParent, useReflection).add(0, originalParent);

            // copy in layout properties, etc, so that the dialogStack displays
            // properly in (hopefully) whatever layout the owner node is in
            injectedParent.getProperties().putAll(originalParent.getProperties());
        }
    }

    // parent is where we want to inject the injectedParent. We then need to
    // set the child of the injectedParent to include parent.
    // The end result is that we've forced in the injectedParent node above parent.
    public static void injectPane(Parent parent, Parent injectedParent, boolean useReflection) {
        if (parent == null) {
            throw new IllegalArgumentException("parent can not be null"); //$NON-NLS-1$
        }

        List<Node> ownerParentChildren = getChildren(parent.getParent(), useReflection);

        // we've got the children list, now we need to insert a temporary
        // layout container holding our dialogs and opaque layer / effect
        // in place of the owner (the owner will become a child of the dialog
        // stack)
        int ownerPos = ownerParentChildren.indexOf(parent);
        ownerParentChildren.remove(ownerPos);
        ownerParentChildren.add(ownerPos, injectedParent);

        // now we install the parent as a child of the injectedParent
        getChildren(injectedParent, useReflection).add(0, parent);

        // copy in layout properties, etc, so that the dialogStack displays
        // properly in (hopefully) whatever layout the owner node is in
        injectedParent.getProperties().putAll(parent.getProperties());
    }

    public static void stripRootPane(Scene scene, Parent originalParent, boolean useReflection) {
        Parent oldParent = scene.getRoot();
        getChildren(oldParent, useReflection).remove(originalParent);
        originalParent.getStyleClass().remove("root"); //$NON-NLS-1$
        scene.setRoot(originalParent);
    }

    public static List<Node> getChildren(Node n, boolean useReflection) {
        return n instanceof Parent ? getChildren((Parent)n, useReflection) : FXCollections.observableArrayList(new ArrayList<Node>());
    }

    public static List<Node> getChildren(Parent p, boolean useReflection) {
        ObservableList<Node> children = null;

        // previously we used reflection immediately, now we try to avoid reflection
        // by checking the type of the Parent. Still not great...
        if (p instanceof Pane) {
            // This should cover the majority of layout containers, including
            // AnchorPane, FlowPane, GridPane, HBox, Pane, StackPane, TilePane, VBox
            children = ((Pane)p).getChildren();
        } else if (p instanceof Group) {
            children = ((Group)p).getChildren();
        } else if (p instanceof Control) {
            Control c = (Control) p;
            Skin<?> s = c.getSkin();
            children = s instanceof SkinBase ? ((SkinBase<?>)s).getChildren() : getChildrenReflectively(p);
        } else if (useReflection) {
            // we really want to avoid using this!!!!
            children = getChildrenReflectively(p);
        }

        if (children == null) {
            throw new RuntimeException("Unable to get children for Parent of type " + p.getClass() +  //$NON-NLS-1$
                    ". useReflection is set to " + useReflection); //$NON-NLS-1$
        }

        return children == null ? FXCollections.observableArrayList(new ArrayList<Node>()) : children;
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<Node> getChildrenReflectively(Parent p) {
        ObservableList<Node> children = null;

        try {
            Method getChildrenMethod = Parent.class.getDeclaredMethod("getChildren"); //$NON-NLS-1$

            if (getChildrenMethod != null) {
                if (! getChildrenMethod.isAccessible()) {
                    getChildrenMethod.setAccessible(true);
                }
                children = (ObservableList<Node>) getChildrenMethod.invoke(p);
            } else {
                // uh oh, trouble
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to get children for Parent of type " + p.getClass(), e); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unable to get children for Parent of type " + p.getClass(), e); //$NON-NLS-1$
        }

        return children;
    }
}
