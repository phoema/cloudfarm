package com.yuntian.web.api;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Farm;
import com.yuntian.domain.FarmState;
import com.yuntian.domain.Pkage;
import com.yuntian.domain.Product;
import com.yuntian.domain.Score;
import com.yuntian.domain.Stock;
import com.yuntian.domain.StockHistory;
import com.yuntian.domain.StockPK;
import com.yuntian.domain.SysUser;
import com.yuntian.service.FarmRepository;
import com.yuntian.service.PkageRepository;
import com.yuntian.service.ProductRepository;
import com.yuntian.service.ScoreRepository;
import com.yuntian.service.StockHistoryRepository;
import com.yuntian.service.StockRepository;
import com.yuntian.service.SysUserRepository;
import com.yuntian.util.FarmException;
import com.yuntian.util.FarmUtils;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/score",method={RequestMethod.POST,RequestMethod.GET})
public class ScoreController extends CommonController {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private PkageRepository pkageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SysUserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");

    @RequestMapping("/save")
    public Score save(@RequestBody Score score) throws FarmException {
    	if(score.getUsername() != null && score.getUserid()==null){
    		SysUser user = userRepository.findByUsername(score.getUsername());
    		if(user != null ){
    			score.setUserid(user.getUid());
    			score.setUser(user);
    		}else{
    			throw new FarmException("用户不存在");
    		}
    	}
        return scoreRepository.save(score);
    }
    @RequestMapping("/get")
    public Score get(Long id) {
    	
    	Score Score = scoreRepository.findOne(id);
        return Score;
    }
//    @RequestMapping("/getbyuserid")
//    public List<Score> getbyuserid(Long uid) {
//    	SysUser user = new SysUser();
//    	user.setUid(uid);
//    	List<Score> Scores = scoreRepository.findByUser(user);
//        return Scores;
//    }
    @RequestMapping("/list")
    public List<Score> getall() {
    	List<Score> Scores = (List<Score>)scoreRepository.findAll();
        return Scores;
    }
    @RequestMapping("/getbyuserid")
    public List<Score> getbyuserid(Long uid) {
    	List<Score> list = (List<Score>)scoreRepository.findByuserid(uid);
        return list;
    }
    /**
     * 签到积分，正数
     * @return
     * @throws Exception
     */
    @RequestMapping("/sign")
    public Score sign() throws Exception {
    	// 获取当前用户
		SysUser obj = (SysUser)FarmUtils.getCurUser();
		if(obj == null) throw new FarmException("没有登录");
		Score score = new Score();
		//scoreRepository.findByUseridAndNameAndCreateTimeLike((long)1,"签到","2016-08-03");
//    	Score = scoreRepository.save(Score);
        return null;
    }
    /**
     * 开通农田
     * @param farm
     * @throws FarmException 
     */
    @Transactional
    @RequestMapping("/openfarm")
    public Farm openfarm(@RequestBody Farm farm) throws FarmException{
    	//基本信息获取，农场数量，用户信息，套餐信息
    	long count = farmRepository.countByUser_uid(farm.getUserid());
    	SysUser user = userRepository.findOne(farm.getUserid());
       	Pkage pkage = pkageRepository.findOne(farm.getPkageid());
       	if(pkage.getScore()>user.getScores()){
       		throw new FarmException(String.format("剩余积分%s，积分不足。",user.getScores()));
       	}
    	if(farm.getState() == null){
    		farm.setState(FarmState.SEED);
    	}
    	farm.setName("我的农场" +(count+1));
    	// 农场开通
    	farm = farmRepository.save(farm);
    	
 		Score score = new Score();
		score.setName(String.format("开通农场-id-%s-name-%s",farm.getId(),farm.getName()));
		score.setUserid(farm.getUserid());
		score.setScore(-pkage.getScore());
		// 积分扣除
		scoreRepository.save(score);
		return farm;
    }
    /**
     * 产品购买
     * @param score.parm [productid,count]
     * @throws FarmException 
     */
    @Transactional
    @RequestMapping("/productbuy")
    public Score productbuy(@RequestBody Score score) throws FarmException{
    	// 获取当前用户
    	// productid ---parm1 count-- pram2
    	long productid = Long.parseLong(score.getParm()[0]);
    	long count = Long.parseLong(score.getParm()[1]);
    	
    	// 获取当前用户
		SysUser obj = (SysUser)FarmUtils.getCurUser();

		if(obj == null) {
			throw new FarmException("没有登录");
		}
		Long userid = obj.getUid();
    	SysUser user = userRepository.findOne(userid);
    	Product product = productRepository.findOne(productid);

		if(user.getScores() < product.getScore()*count){
       		throw new FarmException(String.format("剩余积分%s，积分不足。",user.getScores()));
		}
		score.setUserid(userid);
		score.setName("产品兑换："+product.getName()+"-" + product.getType() +":"+ product.getScore() + "X"+ count);
		score.setScore(-product.getScore()*count);
		// 积分扣除
		scoreRepository.save(score);
        // 仓库入库
    	Stock stock = stockRepository.findOne(new StockPK(userid,productid));
    	if(stock == null){
    		stock = new Stock();
    		stock.setProductid(productid);
    		stock.setUserid(userid);
    		stock.setStock((long)0);
    	}
    	stock.setStock(stock.getStock()+count);
    	stockRepository.save(stock);
        // 仓库履历
    	StockHistory stockhis = new StockHistory();
    	stockhis.setUserid(userid);
    	stockhis.setProductid(productid);
    	stockhis.setOpertype("兑换");
    	stockhis.setNote("产品兑换："+product.getName()+"-" + product.getType() +":"+ product.getScore() + "X"+ count);
    	stockhis.setStock(count);
    	stockHistoryRepository.save(stockhis);


		return score;
    }

}