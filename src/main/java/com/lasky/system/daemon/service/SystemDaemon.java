package com.lasky.system.daemon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lasky.constants.Common;
import com.lasky.system.daemon.WorkerThread;
import com.lasky.utilities.Utility;


@Service
public class SystemDaemon implements WorkerThread {
	private static Logger logger = LogManager.getLogger(SystemDaemon.class);
	private static Utility utility = Utility.getOnlyInstance();
	private static int regularIntervalInMillis = Common.IntegerConstants.SystemDaemonRegularIntervalMillisTime.value();
	
	private boolean isActive = false;

	@Override
	public void run() {
		logger.info("SystemDaemon.run() {");
		this.isActive = true;
		
		boolean sleepException = false;
		int sleepTries = 5;
		while(this.isActive) {
			sleepException = false;
			try {
				Thread.sleep(regularIntervalInMillis);
			} catch (InterruptedException interruptedException) {
				sleepException = true;
				utility.logFormattedException(logger, interruptedException);
				logger.error("Unable to sleep the SystemDaemon Thread will terminate after " + sleepTries + " tries");
			} catch (Exception exception) {
				sleepException = true;
				utility.logFormattedException(logger, exception);
				logger.error("Unable to sleep the SystemDaemon Thread will terminate after " + sleepTries + " tries");
			} finally {
				if (sleepException) {
					--sleepTries;
				} else {
					sleepTries = 5;
				}
			}
			
			// do the tasks...
			try {
				logger.info("SystemDaemon is running System.gc() [Garbage Collection]...");
				System.gc();
			} catch (Exception exception) {
				utility.logFormattedException(logger, exception);
				logger.error("Unable to run System Garbage Collection!");
			}
		}
		
		synchronized(this) {
			this.isActive = false;
		}
		logger.info("} SystemDaemon.run(ENDED/DIED) ");
	}

	@Override
	public boolean forceUpdate() {
		boolean result = false;
		
		// TODO: Implementation here...
		
		return result;
	}

	@Override
	public boolean isWorkerActive() {
		return this.isActive;
	}

	@Override
	public void gracefulStop() {
		synchronized(this) {
			this.isActive = false;
		}
	}

}
