package com.yuntian.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Notice;
import com.yuntian.service.NoticeRepository;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/notice",method={RequestMethod.POST,RequestMethod.GET})
public class NoticeController  extends CommonController{
    @Autowired
    private NoticeRepository noticeRepository;

    @RequestMapping("/save")
    public Notice save(@RequestBody Notice notice) {
    	notice = noticeRepository.save(notice);
        return notice;
    }
    @RequestMapping("/delete")
    public boolean delete(Long id) {
    	noticeRepository.delete(id);
        return true;
    }
    @RequestMapping("/get")
    public Notice get(Long id) {
    	
    	Notice notice = noticeRepository.findOne(id);
        return notice;
    }
    @RequestMapping("/getbystatus")
    public List<Notice> getbytype() {
    	boolean status = true;
    	List<Notice> notices = noticeRepository.findByStatus(status);
        return notices;
    }
    @RequestMapping("/list")
    public List<Notice> getall() {
    	List<Notice> notices = (List<Notice>)noticeRepository.findAll();
        return notices;
    }
 
}
