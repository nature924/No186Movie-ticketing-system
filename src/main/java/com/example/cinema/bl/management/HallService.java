package com.example.cinema.bl.management;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.po.Hall;
/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    ResponseVO addHall(Hall addHallForm);

    ResponseVO updateHall(Hall editHallForm);
}
