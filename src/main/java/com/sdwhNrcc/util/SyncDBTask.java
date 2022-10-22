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
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Desktop dt = Desktop.getDesktop();
			dt.browse(new URI("http://127.0.0.1:8080/SdwhNrcc/api/goSyncDBRun"));
			//dt.browse(new URI("http://"+Constant.SERVICE_IP+":8080/PositionPhZY/phone/goPage?page=syncDBRun"));
			//dt.browse(new URI("http://localhost:8080/PositionPhZY/phone/goPage?page=syncDBRun"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
