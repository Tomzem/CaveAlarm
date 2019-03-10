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

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.tomzem.lintlib.detector.MethodDetector.ISSUE;

/**
 * @author Tomze
 * @time 2019年03月05日 20:43
 * @desc
 */
public class MethodRule {
    private static final Class<? extends Detector> DETECTOR_CLASS = MethodDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;
    private static final String TOAST_PACKAGE = "android.widget.Toast";
    private static final String LOG_PACKAGE = "android.util.Log";
    private static final String ISSUE_DESCRIPTION1 = "You should use our{ToastUtils}";
    private static final String ISSUE_DESCRIPTION2 = "You should use our{LogUtils}";
    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );
    private static final List<Issue> issueList = new ArrayList<>();
    public static List<Issue> getIssues() {
        Issue issue1 = Issue.create(
                "MethodDetector1",
                "You should use our{ToastUtils}",
                "应该使用ToastUtils",
                Category.CORRECTNESS,
                9, Severity.ERROR,IMPLEMENTATION
        );
        Issue issue2 = Issue.create(
                "MethodDetector2",
                "You should use our{LogUtils}",
                "应该使用LogUtils",
                Category.CORRECTNESS,
                9, Severity.ERROR,IMPLEMENTATION
        );
        issueList.add(issue1);
        issueList.add(issue2);
        return issueList;
    }

    public static List<String> getMethodList() {
        return Arrays.asList("makeText", "show", "i", "d", "v");
    }

    public static void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, TOAST_PACKAGE)) {
            List<UExpression> args = node.getValueArguments();
            UExpression duration = null;
            if (args.size() == 3) {
                duration = args.get(2);
            }
            LintFix fix = null;
            if (duration != null) {
                String replace;
                if ("Toast.LENGTH_LONG".equals(duration.toString())) {
                    replace = "ToastUtils.showLong(" + args.get(0).toString() + ", " + args.get(1).toString() + ");";
                } else {
                    replace = "ToastUtils.showShort(" + args.get(0).toString() + ", " + args.get(1).toString() + ");";
                }
                fix = LintFix.create().name("Replace with ToastUtils")
                        .replace()
                        .with(replace)
                        .build();
            }
            if (fix != null) {
                context.report(ISSUE, node, context.getLocation(node), ISSUE_DESCRIPTION1, fix);
            }
        } else if (context.getEvaluator().isMemberInClass(method, LOG_PACKAGE)) {
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
                context.report(ISSUE, node, context.getLocation(node), ISSUE_DESCRIPTION2, fix);
            }
        }
    }
}
