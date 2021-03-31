package com.study;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletionOnTimeOut {
	public static final int TIMEOUT_IN_SEC= 3;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = 
				doWork("TestingExample").completeOnTimeout("Demo Examples",TIMEOUT_IN_SEC, TimeUnit.SECONDS)
				.whenComplete((result,error) ->{
					if(error == null)
						System.out.println("The result is " + result);
					else
						//This statment will never run
						System.out.println("Sorry Timeout in " + TIMEOUT_IN_SEC + " seconds");
				});
		String content;
		content = future.get();
		System.out.println("RESULT >> " + content);
	}
	
	private static CompletableFuture<String> doWork(String s)
	{
		return CompletableFuture.supplyAsync(()->{
			for(var i=1; i<=5;i++)
			{
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("Running Inside Do work method.." + i+"s");
			}
			return s +".rahul";
		});
		
	}

}
