package com.lasky.system.daemon;

public interface WorkerThread extends Runnable {
	boolean forceUpdate();
	boolean isWorkerActive();
	void gracefulStop();
}


