package com.meteorite.fxbase.ui.dialog;

import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.dialog.Dialog.ActionTrait;
import org.controlsfx.dialog.Dialog.DialogAction;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * A convenience class that implements the {@link org.controlsfx.control.action.Action} and {@link org.controlsfx.dialog.Dialog.DialogAction} interfaces and provides
 * a simpler API. It is highly recommended to use this class rather than
 * implement the {@link org.controlsfx.control.action.Action} or the {@link org.controlsfx.dialog.Dialog.DialogAction} interfaces directly.
 *
 * <p>To better understand how to use actions, and where they fit within the
 * JavaFX ecosystem, refer to the {@link org.controlsfx.control.action.Action} class documentation.
 *
 * @see org.controlsfx.control.action.Action
 * @see org.controlsfx.dialog.Dialog.DialogAction
 */
public abstract class AbstractDialogAction extends AbstractAction implements DialogAction {
    
    private final EnumSet<ActionTrait> traits;

    /**
     * Creates a dialog action with given text and traits
     * @param text
     * @param traits
     */
    public AbstractDialogAction(String text, ActionTrait... traits) {
        super(text);
        this.traits = (traits == null || traits.length == 0) ? 
                EnumSet.noneOf(ActionTrait.class) : 
                EnumSet.copyOf(Arrays.asList(traits));
    }

    /**
     * Creates a dialog action with given text and common set of traits: CLOSING and DEFAULT
     * @param text
     */
    public AbstractDialogAction(String text) {
        this(text, ActionTrait.CLOSING, ActionTrait.DEFAULT);
    }
    
    
    /** {@inheritDoc} */
    @Override public boolean hasTrait(ActionTrait trait) {
        return traits.contains(trait);
    }

}
