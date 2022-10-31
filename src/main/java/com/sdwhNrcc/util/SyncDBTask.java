package com.sdwhNrcc.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class SyncDBTask extends TimerTask {
	
	private int systemFlag;
	
	public SyncDBTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SyncDBTask(int systemFlag) {
		this.systemFlag = systemFlag;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String page=null;
			System.out.println("systemFlag==="+systemFlag);
			switch (systemFlag) {
			case Constant.WFPXHGYXGS:
				page="pxhgSyncDBRun";
				break;
			case Constant.SDFLXCLKJYXGS:
				page="flxclSyncDBRun";
				break;
			case Constant.WFRZJXHYXGS:
				page="rzjxhSyncDBRun";
				break;
			}
			
			Desktop dt = Desktop.getDesktop();
			dt.browse(new URI("http://127.0.0.1:8080/SdwhNrcc/api/goPage?page="+page));
			//dt.browse(new URI("http://"+Constant.SERVICE_IP+":8080/SdwhNrcc/api/goPage?page=pxhgSyncDBRun"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
