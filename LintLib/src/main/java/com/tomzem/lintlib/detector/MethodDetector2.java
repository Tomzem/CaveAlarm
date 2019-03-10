package com.tomzem.lintlib.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;
import com.tomzem.lintlib.rules.LogRule;
import com.tomzem.lintlib.rules.MethodRule;

import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月05日 22:35
 * @desc
 */
public class MethodDetector2 extends Detector implements Detector.UastScanner {

    private static final String ISSUE_ID = "";
    private static final String ISSUE_DESCRIPTION = "";
    private static final String ISSUE_EXPLANATION = "";
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    private static final int ISSUE_PRIORITY = 9;
    private static final Severity ISSUE_SEVERITY = Severity.ERROR;
    private static final String CHECK_PACKAGE = "android.widget.Toast";

    private static final Class<? extends Detector> DETECTOR_CLASS = MethodDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;
    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

//    public static Issue ISSUE = Issue.create(
//            ISSUE_ID,
//            ISSUE_DESCRIPTION,
//            ISSUE_EXPLANATION,
//            ISSUE_CATEGORY,
//            ISSUE_PRIORITY,
//            ISSUE_SEVERITY,
//            IMPLEMENTATION
//    );

    public MethodDetector2() {
        new LogRule().setIssueId();
    }

    public static Issue ISSUE = null;

    public void setIssueId() {}

    public List<String> getMethods(){
        return Arrays.asList("", "");
    }

    public void method(JavaContext context, UCallExpression node, PsiMethod method){

    }

    @Override
    public List<String> getApplicableMethodNames() {
        return getMethods();
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        method(context, node, method);
    }
}