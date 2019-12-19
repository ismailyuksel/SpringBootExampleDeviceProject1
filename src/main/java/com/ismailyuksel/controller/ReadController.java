package com.ismailyuksel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismailyuksel.model.MobileDevice;
import com.ismailyuksel.util.JsonUtil;

@RestController
public class ReadController {
	
	@Autowired
	private Gson gson;

    @GetMapping("/import/device")
    public boolean getAllContact()  {
    	String json = null;
		try {
			json = JsonUtil.readJsonFromUrl("https://raw.githubusercontent.com/assessment-projects/code-challenge/master/devices.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TypeToken<List<MobileDevice>> token = new TypeToken<List<MobileDevice>>(){};
		List<MobileDevice> personList = gson.fromJson(json, token.getType());
		System.out.println(personList);
        return true;
    }


}
