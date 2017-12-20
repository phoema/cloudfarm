package com.yuntian.web.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Product;
import com.yuntian.domain.Send;
import com.yuntian.domain.SendState;
import com.yuntian.domain.Stock;
import com.yuntian.domain.StockHistory;
import com.yuntian.domain.StockPK;
import com.yuntian.service.ProductRepository;
import com.yuntian.service.SendRepository;
import com.yuntian.service.StockHistoryRepository;
import com.yuntian.service.StockRepository;
import com.yuntian.util.FarmException;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/send",method={RequestMethod.POST,RequestMethod.GET})
public class SendController extends CommonController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    @Autowired
    private SendRepository sendRepository;
    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private FarmRepository farmRepository;
    
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 配送实施 1、仓库减值 2、记录仓库履历 3、记录配送记录
	 * @param send
	 * @return
	 * @throws FarmException
	 */
	@Transactional
    @RequestMapping("/save")
    public Send save(@RequestBody Send send) throws FarmException {
		return this.saveone(send);
    }
    @Transactional
    @RequestMapping("/saves")
    public void saves(@RequestBody List<Send> sends) throws FarmException {
    	for(Send send : sends){
    		if(send.getStock() > 0){
        		this.saveone(send);
    		}
    	}
    }
    @RequestMapping("/get")
    public Send get(Long id) {
    	return sendRepository.findOne(id);
//    	StockHistory stockhis = stockHistoryRepository.findOne(id);
//    	return this.change(stockhis);
    }
    @RequestMapping("/list")
    public List<Send> getall() {
    	return (List<Send>)sendRepository.findAll();
//    	List<StockHistory> list = stockHistoryRepository.findByOpertype("配送");
//    	return this.change(list);
    }
    @RequestMapping("/getbyuserid")
    public List<Send> getbyuserid(Long uid) {
    	return sendRepository.findByUserid(uid);
//    	List<StockHistory> list = stockHistoryRepository.findByUseridAndOpertype(uid, "配送");
//    	return this.change(list);
    }
    
    private Send change(StockHistory stockhis){
    	Send sendhis = new Send();
    	BeanUtils.copyProperties(stockhis, sendhis);
    	// 将库存取值取正数，负负得正
    	sendhis.setStock(-sendhis.getStock());
    	sendhis.setAddress(stockhis.getNote());
    	return sendhis;
   	
    }
    private List<Send> change(List<StockHistory> list){
    	List<Send> sendlist = new ArrayList<Send>();
    	for(StockHistory stockhis : list){
        	sendlist.add(this.change(stockhis));
    	}
        return sendlist;
   	
    }

    private Send saveone(Send send) throws FarmException{
    	Long productid = send.getProductid();
    	Long userid = send.getUserid();
        // 仓库减值
    	Stock stock = stockRepository.findOne(new StockPK(userid,productid));
    	if(stock.getStock() < send.getStock() || send.getStock()<=0){
    		throw new FarmException("配送数量不符合要求");
    	}
    	
    	Product product = productRepository.findOne(productid);
    	stock.setStock(stock.getStock()-send.getStock());
    	stockRepository.save(stock);
        // 仓库履历-配送
    	StockHistory stockhis = new StockHistory();
    	stockhis.setUserid(userid);
    	stockhis.setProductid(productid);
    	stockhis.setProductname(product.getName());
    	stockhis.setProducttype(product.getType());
    	stockhis.setProduct(product);
    	stockhis.setOpertype("配送");
    	stockhis.setNote(send.getAddress());
    	stockhis.setStock(-send.getStock());
    	stockHistoryRepository.save(stockhis);
        // 配送记录
    	send.setDetail("等待配送");
    	send.setState(SendState.WAIT);
    	send.setProduct(product);
    	send.setProductname(product.getName());
    	send.setProducttype(product.getType());
    	sendRepository.save(send);
        return send;
    }
    
    @RequestMapping("/updatedetail")
    public Send updatedetail(Long id, String detail){
    	Send send = sendRepository.findOne(id);
    	send.setDetail(detail);
    	return sendRepository.save(send);
    }
    @RequestMapping("/updatestate")
    public Send updatestate(Long id, SendState state){
    	Send send = sendRepository.findOne(id);
    	send.setState(state);;
    	return sendRepository.save(send);
    }

}