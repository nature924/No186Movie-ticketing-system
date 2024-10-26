package com.example.cinema.bl.statistics;

import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/28 3:07 PM
 */
public interface MovieLikeService {
    /**
     * 想看电影
     * @param userId
     * @param movieId
     * @return
     */
    ResponseVO likeMovie(int userId, int movieId);

    /**
     * 取消想看电影
     * @param userId
     * @param movieId
     * @return
     */
    ResponseVO unLikeMovie(int userId,int movieId);

    /**
     * 统计想看电影的人数
     * @param movieId
     * @return
     */
    ResponseVO getCountOfLikes(int movieId);

    /**
     * 获得电影每日的想看人数
     * @param movieId
     * @return
     */
    ResponseVO getLikeNumsGroupByDate(int movieId);
}
