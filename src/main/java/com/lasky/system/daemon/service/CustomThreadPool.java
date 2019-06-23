package com.lasky.system.daemon.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lasky.system.daemon.WorkerThread;
import com.lasky.utilities.Utility;

@Service
public class CustomThreadPool {
	private static Logger logger = LogManager.getLogger(CustomThreadPool.class);
	private static int numberOfThreads = 5;
	private ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
	
	public CustomThreadPool() {
		
	}
	
	public boolean addWorker(WorkerThread workerThread) {
		boolean result = false;
		try {
			executor.execute(workerThread);
			result = true;
		} catch (Exception exception) {
			Utility.getOnlyInstance().logFormattedExceptionStackTrace(logger, exception);
		}
		
		return result;
	}
}
