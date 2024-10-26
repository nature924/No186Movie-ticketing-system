package com.example.cinema.data.management;

import com.example.cinema.po.Movie;
import com.example.cinema.vo.MovieForm;
import com.example.cinema.vo.MovieVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/3/12 6:26 PM
 */
@Mapper
public interface MovieMapper {
    /**
     * 插入一条电影信息
     * @param addMovieForm
     * @return
     */
    int insertOneMovie(MovieForm addMovieForm);

    /**
     * 根据id查找电影
     * @param id
     * @return
     */
    Movie selectMovieById(@Param("id") int id);

    /**
     * 根据id和userId查找电影
     * @param id
     * @param userId
     * @return
     */
    Movie selectMovieByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    /**
     * 展示所有电影
     * @return
     */
    List<Movie> selectAllMovie();

    /**
     * 展示所有电影(不包括已经下架的)
     * @return
     */
    List<Movie> selectOtherMoviesExcludeOff();

    /**
     * 根据关键字搜索电影
     * @param keyword
     * @return
     */
    List<Movie> selectMovieByKeyword(@Param("keyword") String keyword);

    /**
     * 批量更新电影状态
     * @param movieIdList
     * @return
     */
    int updateMovieStatusBatch(List<Integer> movieIdList);

    /**
     * 修改电影
     * @param updateMovieForm
     * @return
     */
    int updateMovie(MovieForm updateMovieForm);
}
