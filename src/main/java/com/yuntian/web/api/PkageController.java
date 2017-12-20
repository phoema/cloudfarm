package com.yuntian.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Pkage;
import com.yuntian.domain.Pkage_Product;
import com.yuntian.service.PkageProductRepository;
import com.yuntian.service.PkageRepository;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/pkage",method={RequestMethod.POST,RequestMethod.GET})
public class PkageController extends CommonController {
    @Autowired
    private PkageRepository pkageRepository;
    @Autowired
    PkageProductRepository pkageProductRepository;
    @RequestMapping("/save")
    public Pkage save(@RequestBody Pkage pkage) {
    	pkage = pkageRepository.save(pkage);
        return pkage;
    }
    @RequestMapping("/delete")
    public boolean delete(Long id) {
    	pkageRepository.delete(id);
        return true;
    }
    @RequestMapping("/get")
    public Pkage get(Long id) {
    	
    	Pkage pkage = pkageRepository.findOne(id);
    	//farm.getPkage().getPkage_Product().
    	return pkage;
    }
    @RequestMapping("/list")
    public List<Pkage> getall() {
    	
    	List<Pkage> list = (List<Pkage>)pkageRepository.findAll();
    	return list;
    }
    
    
    //套餐产品一览表操作-删除
    @RequestMapping("/delpkageproduct")
    public boolean delpkageproduct(Long id) {
    	pkageProductRepository.delete(id);
    	return true;
    }
    //套餐产品一览表操作-更新追加
   @RequestMapping("/savepkageproduct")
    public Pkage_Product savepkageproduct(@RequestBody Pkage_Product pp) {
    	pkageProductRepository.save(pp);
    	return pp;
    }

 
}
