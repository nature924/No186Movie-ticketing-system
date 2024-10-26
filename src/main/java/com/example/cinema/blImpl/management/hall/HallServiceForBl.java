package com.example.cinema.blImpl.management.hall;

import com.example.cinema.po.Hall;
/**
 * @author fjj
 * @date 2019/4/28 12:27 AM
 */
public interface HallServiceForBl {
    /**
     * 搜索影厅
     * @param id
     * @return
     */
    Hall getHallById(int id);
}
