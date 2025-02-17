package Listeners;

import DriverFactory_ThreadLocal.DriverFactory;
import Utilities.LogUtils;
import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if (testResult.getStatus()==ITestResult.FAILURE)
        {
            LogUtils.info_Log("Test Case " + testResult.getName() + "Failed");

            Utility.taking_Full_Screenshot(DriverFactory.getDriver(),testResult.getName());
        }
    }
}
