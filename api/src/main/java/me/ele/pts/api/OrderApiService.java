package me.ele.pts.api;

import me.ele.pts.dto.ElemeOrder;
import me.ele.pts.exception.UserException;

/**
 * Created by joshua on 16/11/14.
 * 接口定义
 */
public interface OrderApiService {

    ElemeOrder getById(Long id);
}
