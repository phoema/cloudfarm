package com.yuntian.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
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
import com.yuntian.domain.FarmState;
import com.yuntian.domain.Pkage_Product;
import com.yuntian.domain.Product;
import com.yuntian.domain.Stock;
import com.yuntian.domain.StockHistory;
import com.yuntian.domain.StockPK;
import com.yuntian.domain.SysUser;
import com.yuntian.service.FarmRepository;
import com.yuntian.service.StockHistoryRepository;
import com.yuntian.service.StockRepository;
import com.yuntian.service.SysUserRepository;
import com.yuntian.util.FarmException;
import com.yuntian.util.FarmUtils;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/farm",method={RequestMethod.POST,RequestMethod.GET})
public class FarmController  extends CommonController{
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    @Autowired
    private SysUserRepository userInfoRepository;

	public static Hashtable<String, String> statedict = new Hashtable<String, String>();
	static {
		statedict.put("0", "等待期");
		statedict.put("1", "播种期");
		statedict.put("2", "萌芽期");
		statedict.put("3", "生长期");
		statedict.put("4", "结果期");
		statedict.put("5", "收获期");
		statedict.put("6", "已关闭");
	}    
	static {
		statedict.put("WAIT", "等待期");
		statedict.put("SEED", "播种期");
		statedict.put("BUD", "萌芽期");
		statedict.put("GROW", "生长期");
		statedict.put("FRUIT", "结果期");
		statedict.put("HARVEST", "收获期");
		statedict.put("CLOSED", "已关闭");
	}    
    @RequestMapping("/save")
    public Farm save(@RequestBody Farm farm) throws FarmException {
    	if(farm.getUserid()==null && farm.getUsername()!=null){
    		SysUser user = userInfoRepository.findByUsername(farm.getUsername());
    		if(user != null){
    			farm.setUserid(user.getUid());
    		}else{
    			throw new FarmException("用户不存在");
    		}
    	}
    	if(farm.getState() == null){
    		farm.setState(FarmState.SEED);
    	}
    	farm = farmRepository.save(farm);
        return farm;
    }
    @RequestMapping("/get")
    public Farm get(Long id) throws Exception {
    	
    	Farm farm = farmRepository.findOne(Long.valueOf(id));
    	//farm.getPkage().getPkage_Product().
    	// 农田状态翻译
    	if(farm != null && farm.getState() != null){
    		farm.setState_cn(FarmUtils.statedict.get(farm.getState().toString()));
    	}
    	// 计算农田产量预期
    	farm = this.expect(farm);
        return farm;
    }
    @RequestMapping("/getbyuserid")
    public List<Farm> getbyuserid(Long uid) throws Exception {
    	List<Farm> farms = farmRepository.findByuser_uidOrderByCreatetimeDesc(uid);
    	for(Farm farm :farms){
    		farm = this.expect(farm);
    	}
        return farms;
    }
    @RequestMapping("/list")
    public List<Farm> getall() {
    	List<Farm> farms = (List<Farm>)farmRepository.findAll();
        return farms;
    }
    /**
     * 农田各产品预期产量 在product.real里
     * @return
     * @throws FarmException 
     */
    @RequestMapping("/expect")
    public Farm expect(Long farmid) throws Exception {
    	Farm farm = farmRepository.findOne(farmid);
    	return this.expect(farm);
   }
    /**
     * 计算农田的产量预期
     * @param farm
     * @throws Exception 
     */
    private Farm expect(Farm farm) throws Exception{
    	if(farm == null){
    		throw new FarmException("未找到对应云田");
    	}
    	// 由于farm的Pkage对象是指针型，list会修改串号，这里new一个。
    	List<Long> reallist = new ArrayList<Long>();
    	// 系数公式 (max-min)*(A+B+C+D)/400
    	double formula = (double)(farm.getFeiwodu()+farm.getHaichongdu()+farm.getShirundu()+farm.getZacaodu())/400;
    	//farm.getPkage().setPkage_Product((List<Pkage_Product>)BeanUtils.cloneBean(farm.getPkage().getPkage_Product()));
    	 //(max-min)*(A+B+C+D)/400
    	for(Pkage_Product pkageproduct : farm.getPkage().getPkage_Product()){
    		
    		double real = pkageproduct.getMin() + (pkageproduct.getMax()-pkageproduct.getMin())*formula;
    		//pkageproduct.setReal((long)Math.floor(real));
    		reallist.add((long)Math.floor(real));
    		
    	}
    	farm.setReallist(reallist);
		return farm;
    }
    /**
     * @throws FarmException 
     * 收获农田产品入仓库
     */
    @Transactional
    @RequestMapping("/harvest")
    public Farm harvest(Long farmid) throws Exception{
    	// 计算收获产量
    	Farm farm = this.expect(farmid);
    	// 如果不是收获期，不允许收获
    	if (farm.getState() != FarmState.HARVEST){
    		throw new FarmException("云田未到收获期");
    	}
    	long userid = farm.getUserid();
    	
    	for(int i=0;i<farm.getPkage().getPkage_Product().size();i++){
    		Pkage_Product pkageproduct = farm.getPkage().getPkage_Product().get(i);
    		Product product = pkageproduct.getProduct();
    		long productid = product.getId();
    		
            // 仓库收获
        	Stock stock = stockRepository.findOne(new StockPK(userid,productid));
        	if(stock == null){
        		stock = new Stock();
        		stock.setStock((long)0);
        		stock.setProductid(productid);
        		stock.setUserid(userid);
        	}
        	// 追加实际产量
        	stock.setStock(stock.getStock() + farm.getReallist().get(i));
        	stock.setName(product.getName());
        	stock.setType(product.getType());
        	stock.setUpdatetime(new Date());
        	stockRepository.save(stock);
            // 仓库履历
        	StockHistory stockhis = new StockHistory();
        	stockhis.setUserid(userid);
        	stockhis.setProductid(productid);
        	stockhis.setProductname(product.getName());
        	stockhis.setProducttype(product.getType());
        	stockhis.setOpertype("收获"+product.getName() + "-" + product.getType());
        	stockhis.setNote("农田ＩＤ：" + farmid.toString());
        	// 实际产量
        	stockhis.setStock(farm.getReallist().get(i));
        	stockHistoryRepository.save(stockhis);
    	}
    	// 农田关闭
    	farm.setState(FarmState.CLOSED);
    	farm = farmRepository.save(farm);
    	return farm;
    }   
}
