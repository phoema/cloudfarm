package com.yuntian.web.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmAction;
import com.yuntian.domain.FarmState;
import com.yuntian.service.FarmActionRepository;
import com.yuntian.service.FarmRepository;
import com.yuntian.util.FarmException;
import com.yuntian.web.CommonController;

/**
 * 云田田地操作业务
 * @author jiahh 2016年8月23日
 *
 */
@RestController
@RequestMapping(value="/api/farmaction",method={RequestMethod.POST,RequestMethod.GET})
public class FarmActionController  extends CommonController{
    @Autowired
    private FarmActionRepository farmActionRepository;
    @Autowired
    private FarmRepository farmRepository;

    @RequestMapping("/save")
    public FarmAction save(@RequestBody FarmAction farm) {
    	
    	
    	farm = farmActionRepository.save(farm);
        return farm;
    }
    @RequestMapping("/get")
    public FarmAction get(Long id) {
    	
    	FarmAction farm = farmActionRepository.findOne(id);
    	//farm.getPkage().getPkage_Product().
        return farm;
    }
     @RequestMapping("/list")
    public List<FarmAction> getall() {
    	List<FarmAction> farms = (List<FarmAction>)farmActionRepository.findAll();
        return farms;
    }

    
    /**
     * @throws Exception 
     * 记录每次农田操作，并增加农田体检数据
     */   
    @RequestMapping("/action")
    @Transactional
    public Farm action(Long farmid,String action) throws Exception{
    	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
    	Date date=new Date();
    	String datestr = dateFormater.format(date);
    	// 对farm表进行更新
    	Farm farm = farmRepository.findOne(farmid);
    	if(farm == null) throw new FarmException("未找到对应云田");
    	if(farm.getState()==FarmState.CLOSED) throw new FarmException("指定云田已关闭");
    	FarmAction his = null;
    	// 判断是否存在
    	his = farmActionRepository.findByFarmidAndActionAndCreateday(farmid, action, Long.parseLong(datestr));
    	if(his == null){
    		his = new FarmAction();
    		his.setFarmid(farmid);
    		his.setAction(action);
    		his.setCreatetime(new Date());
    		// 插入新数据履历
    		farmActionRepository.save(his);
    	}else{
    		// TODO 正式环境放开异常控制
//    		throw new FarmException("今日已进行过此操作");
    	}
    	// 农田套用套餐模式的情况下：每天增长100/套餐.cycleday的程度
    	if("LAND".equals(action)){
        	double pram = Math.floor(farm.getZacaodu() + 100/farm.getPkage().getCycleday());
        	pram = pram > 100.0  ? 100 : pram;
        	farm.setZacaodu((long)pram);
    	}else if("WATER".equals(action)){
        	double pram = Math.floor(farm.getShirundu() + 100/farm.getPkage().getCycleday());
        	pram = pram > 100.0  ? 100 : pram;
        	farm.setShirundu((long)pram);
    	}else if("FEED".equals(action)){
        	double pram = Math.floor(farm.getFeiwodu()  + 100/farm.getPkage().getCycleday());
        	pram = pram > 100.0  ? 100 : pram;
        	farm.setFeiwodu((long)pram);
    	}else if("BUG".equals(action)){
        	double pram = Math.floor(farm.getHaichongdu()  + 100/farm.getPkage().getCycleday());
        	pram = pram > 100.0  ? 100 : pram;
       	farm.setHaichongdu((long)pram);
    	}else{
    		throw new FarmException("未定义操作");
    	}
    	
    	// TODO  农田自定义地块产品的情况下
    	farm = farmRepository.save(farm);
    	return farm;
    }
}
