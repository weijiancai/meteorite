package com.ectongs.ideaplugin.binding;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Ds Class Annotator
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DsClassAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if (psiElement instanceof PsiClass) {
            PsiClass aClass = (PsiClass) psiElement;

            boolean flag = false;
            for(PsiClassType type : aClass.getSuperTypes()) {
                if("cc.csdn.base.db.dataobj.DataStore".equals(type.getInternalCanonicalText())) {
                    flag = true;
                }
            }
            if (flag) {
                Annotation boundClassAnnotation = holder.createInfoAnnotation(aClass.getNameIdentifier(), null);
                boundClassAnnotation.setGutterIconRenderer(new BoundIconRenderer(aClass));
            }
        }
    }
}
