package me.ele.pts.soa;

import me.ele.pts.api.OrderApiService;
import me.ele.pts.dto.ElemeOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

/**
 * Created by joshua on 16/11/14.
 * 接口实现,业务逻辑定义
 * 可以加一层BIZ层, service抽象成webapi, biz写详细业务逻辑
 */
//@Service
public class OrderApiServiceImpl implements OrderApiService {
    //    @Autowired
//    private ElemeOrderDao elemeOrderDao;
//
    public ElemeOrder getById(Long id) {
//        elemeOrderDao.getById();
        return null;
    }

    //运行: java -cp service/target/service-1.0-SNAPSHOT.jar:dao/target/dao-1.0-SNAPSHOT.jar:api/target/api-1.0-SNAPSHOT.jar me.ele.pts.soa.OrderApiServiceImpl
    public static void main(String[] args) {
        System.out.println("Hello, world");
    }
}
