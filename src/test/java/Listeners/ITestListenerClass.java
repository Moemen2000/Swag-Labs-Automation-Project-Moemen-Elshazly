package Listeners;

import Utilities.LogUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestListenerClass implements ITestListener {

    public void onTestStart(ITestResult result) {
        LogUtils.info_Log("Test Case " + result.getName() + "Started");
    }

    public void onTestSuccess(ITestResult result) {
        LogUtils.info_Log("Test Case " + result.getName() + "Passed");
    }

    public void onTestSkipped(ITestResult result) {
        LogUtils.info_Log("Test Case " + result.getName() + "Skipped");
    }

}
