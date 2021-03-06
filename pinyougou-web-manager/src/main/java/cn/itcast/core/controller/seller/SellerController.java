package cn.itcast.core.controller.seller;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.seller.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 显示未审核的商家
     * @param page
     * @param rows
     * @param seller
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult search(int page, int rows, @RequestBody Seller seller){
        return sellerService.search(page,rows,seller);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public Seller findOne(String id){
        return sellerService.findOne(id);
    }

    /**
     * 审核申请状态
     * @param sellerId
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus.do")
    public Result updateStatus(String sellerId,String status){
        try {
            sellerService.updateStatus(sellerId,status);
            return new Result(true,"审核完毕");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"审核失败");
        }
    }
}
