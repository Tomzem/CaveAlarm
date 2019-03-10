package com.tomzem.lintlib.registry;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.tomzem.lintlib.detector.MethodDetector;
import com.tomzem.lintlib.detector.MethodDetector2;

import java.util.Arrays;
import java.util.List;

public class MyIssueRegister extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                MethodDetector.ISSUE
        );
    }
}
