package com.yuntian.web.api;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Address;
import com.yuntian.domain.SysUser;
import com.yuntian.service.AddressRepository;
import com.yuntian.service.FarmRepository;
import com.yuntian.service.PkageRepository;
import com.yuntian.util.FarmException;
import com.yuntian.util.FarmUtils;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/address",method={RequestMethod.POST,RequestMethod.GET})
public class AddressController extends CommonController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PkageRepository pkageRepository;
    @Autowired
    private FarmRepository farmRepository;
    
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");

    @RequestMapping("/save")
    public Address save(@RequestBody Address address) {
    	// 如果没有userid，默认是自己的地址
    	if(address.getUserid() == null){
    		SysUser curuser = (SysUser)FarmUtils.getCurUser();
    		
    		address.setUserid(curuser.getUid());
    	}
        return addressRepository.save(address);
    }
    @RequestMapping("/get")
    public Address get(Long id) {
    	
    	Address address = addressRepository.findOne(id);
        return address;
    }
    @RequestMapping("/mydelete")
    public boolean delete(Long id) throws FarmException {
    	boolean ret = false;
    	//安全问题：确保删除的是自己的地址
    	Address address = addressRepository.findOne(id);
		SysUser curuser = (SysUser)FarmUtils.getCurUser();
    	//安全问题：确保删除的是自己的地址
		if(curuser.getUid() == address.getUserid()){
			addressRepository.delete(id);
			ret = true;
		}else{
			throw new FarmException("只能删除自己的配送地址");
		}
    	
        return ret;
    }
    @RequestMapping("/getbyuserid")
    public List<Address> getbyuserid(Long uid) {
    	SysUser user = new SysUser();
    	user.setUid(uid);
    	List<Address> Scores = addressRepository.findByUserid(uid);
        return Scores;
    }
    @RequestMapping("/list")
    public List<Address> getall() {
    	List<Address> addressList = (List<Address>)addressRepository.findAll();
        return addressList;
    }
 

}