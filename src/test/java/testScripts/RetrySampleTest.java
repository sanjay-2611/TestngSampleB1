package testScripts;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetrySampleTest implements IRetryAnalyzer{

	private int retryCount =0;
	private static final int maxRetryCount=3;
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(!result.isSuccess()) {
			if(retryCount < maxRetryCount) {
				retryCount++;
				return true;
			}
		}
		return false;
	}
	

}
