package com.tomzem.lintlib.detector;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.intellij.psi.PsiMethod;
import com.tomzem.lintlib.rules.MethodRule;

import org.jetbrains.uast.UCallExpression;
import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月05日 20:30
 * @desc
 */
public class MethodDetector extends Detector implements Detector.UastScanner {

    public static Issue ISSUE = null;
    static {
        for (Issue issue : MethodRule.getIssues()) {
            ISSUE = issue;
        }
    }

    @Override
    public List<String> getApplicableMethodNames() {
        return MethodRule.getMethodList();
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        MethodRule.visitMethod(context, node, method);
    }
}
