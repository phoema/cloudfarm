package com.yuntian.web.api;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Master;
import com.yuntian.domain.MasterPK;
import com.yuntian.service.MasterRepository;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/master",method={RequestMethod.POST,RequestMethod.GET})
public class MasterController extends CommonController {
    @Autowired
    private MasterRepository masterRepository;
    
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");

    @RequestMapping("/save")
    public Master save(@RequestBody Master master) {

        return masterRepository.save(master);
    }
    @RequestMapping("/get")
    public Master get(String type, String key) {
    	
    	Master master = masterRepository.findOne(new MasterPK(type,key));
        return master;
    }
    @RequestMapping("/list")
    public List<Master> getall() {
    	List<Master> list = (List<Master>)masterRepository.findAll();
        return list;
    }
    // 获取通知公告
    @RequestMapping("/getnotice")
    public Master getnotice() {
    	String type = "NOTICE"; String key = "1";
    	Master master = masterRepository.findOne(new MasterPK(type,key));
        return master;
    }
    // 获取通知公告
    @RequestMapping("/savenotice")
    public Master savenotice(@RequestBody String notice) {
    	String type = "NOTICE"; String key = "1";
    	Master master = new Master();
    	master.setMtype(type);
    	master.setMkey(key);
    	master.setValue(notice);
    	return masterRepository.save(master);
    }
    // 获取联系我们
    @RequestMapping("/getcontact")
    public List<Master> getcontact() {
    	String mtype = "CONTACT";
    	List<Master> list = masterRepository.findByMtype(mtype);
    	return list;
    }
    // 获取联系我们
    @RequestMapping("/savecontact")
    public List<Master> savecontact() {
    	String type = "CONTACT";
    	List<Master> list = masterRepository.findByMtype(type);
    	return list;
    }
 
    

}