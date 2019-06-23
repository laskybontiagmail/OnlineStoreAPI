package com.lasky.system.daemon;

import org.springframework.stereotype.Service;


@Service
public class UserLoginDaemon implements WorkerThread {
	
	//TODO: daemon to invalidate invalid UserLoginSession objects
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean forceUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWorkerActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void gracefulStop() {
		// TODO Auto-generated method stub

	}

}
