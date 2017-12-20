package com.yuntian.web.api;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Pkage_Product;
import com.yuntian.domain.Product;
import com.yuntian.service.PkageProductRepository;
import com.yuntian.service.ProductRepository;
import com.yuntian.util.FarmException;
import com.yuntian.web.CommonController;

@Slf4j
@RestController
@RequestMapping(value="/api/product",method={RequestMethod.POST,RequestMethod.GET})
public class ProductController extends CommonController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PkageProductRepository pkageProductRepository;

    @RequestMapping("/save")
    public Product save(@RequestBody Product product) {
    	product = productRepository.save(product);
        return product;
    }

	/**
	 * 获取指定产品
	 * @return
	 */
	@RequestMapping("/get")
	public Product get(Long id) {

		Product product = productRepository.findOne(id);
		return product;
	}
	/**
	 * 获取所有产品列表
	 * @return
	 */
	@RequestMapping("/list")
	public List<Product> list() {

		List<Product> product = (List<Product>) productRepository.findAll();
		return product;
	}
     /**
      * 删除产品master表中指定产品
      * @param id
      * @throws FarmException 套餐中如果存在，则不删除
      */
     @RequestMapping("/delete")
    public  void delete(Long id) throws FarmException {
    	// 删除之前确认套餐表里是否有产品关联
    	List<Pkage_Product> list = pkageProductRepository.findByProduct_id(id);
    	if(list == null || list.size() > 0){
    		String errorstr = "";
    		for(Pkage_Product pp : list){
    			errorstr += pp.getPkage().getName() +";";
    		}
    		errorstr ="此产品在以下套餐中存在，请先清理套餐：" + errorstr;
    		log.info(errorstr);
    		throw new FarmException(errorstr);
    	}
    	productRepository.delete(id);
    }

    

}