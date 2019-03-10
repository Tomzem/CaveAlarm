package com.tomzem.lintlib.rules;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintFix;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;
import com.tomzem.lintlib.detector.MethodDetector;
import com.tomzem.lintlib.detector.MethodDetector2;

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UExpression;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.tomzem.lintlib.detector.MethodDetector.ISSUE;

/**
 * @author Tomze
 * @time 2019年03月05日 22:32
 * @desc
 */
public class LogRule extends MethodDetector2 {

    private static final String ISSUE_ID = "123";
    private static final String ISSUE_DESCRIPTION = "123";
    private static final String ISSUE_EXPLANATION = "123";
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    private static final int ISSUE_PRIORITY = 9;
    private static final Severity ISSUE_SEVERITY = Severity.ERROR;
    private static final String LOG_PACKAGE = "android.util.Log";

    private static final Class<? extends Detector> DETECTOR_CLASS = MethodDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;
    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    @Override
    public void setIssueId() {
        MethodDetector2 method = new LogRule();
        method.ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_DESCRIPTION,
            ISSUE_EXPLANATION,
            ISSUE_CATEGORY,
            ISSUE_PRIORITY,
            ISSUE_SEVERITY,
            IMPLEMENTATION
        );
    }

    @Override
    public List<String> getMethods() {
        return Arrays.asList("i", "d", "v");
    }

    @Override
    public void method(JavaContext context, UCallExpression node, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, LOG_PACKAGE)) {
            List<UExpression> args = node.getValueArguments();
            UExpression duration = null;
            if (args.size() == 2) {
                duration = args.get(1);
            }
            LintFix fix = null;
            if (duration != null) {
                String replace;
                replace = "LogUtils.i(" + duration + ");";
                fix = LintFix.create().name("Replace with LogUtils")
                        .replace()
                        .with(replace)
                        .build();
            }
            if (fix != null) {
                context.report(ISSUE, node, context.getLocation(node), ISSUE_DESCRIPTION, fix);
            }
        }
    }
}
