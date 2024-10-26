package com.example.cinema.blImpl.management.schedule;
import java.util.Date;

import com.example.cinema.bl.management.ScheduleService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Movie;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fjj
 * @date 2019/4/11 4:14 PM
 */
@Service
public class ScheduleServiceImpl implements ScheduleService, ScheduleServiceForBl {
    private static final String TIME_CONFLICT_ERROR_MESSAGE = "时间段冲突";
    private static final String CROSS_DAYS_ERROR_MESSAGE = "起止时间不能跨天";
    private static final String DATE_INTERVAL_LESS_THAN_LENGTH_ERROR_MESSAGE = "起止时间段不能少于电影时长或结束时间不能早于开始时间";
    private static final String BEFORE_NOW_TIME_ERROR_MESSAGE = "排片日期不能早于当前时间";
    private static final String BEFORE_START_DATE_ERROR_MESSAGE = "排片时间不能早于电影上映时间";
    private static final String MOVIE_NOT_EXIST_ERROR_MESSAGE = "电影不存在";
    private static final String HALL_NOT_EXIST_ERROR_MESSAGE = "影厅不存在";
    private static final String VIEW_COUNT_ERROR_MESSAGE = "排片可见限制数错误";
    private static final String ID_LIST_NULL_ERROR_MESSAGE = "id列表不可为空";
    private static final String VIEW_CONFLICT_ERROR_MESSAGE = "有排片信息已对观众可见，无法删除或修改";


    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private MovieServiceForBl movieServiceForBl;
    @Autowired
    private HallServiceForBl hallServiceForBl;


    @Override
    public ResponseVO addSchedule(ScheduleForm scheduleForm) {
        try {
            ResponseVO responseVO = preCheck(scheduleForm);
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            scheduleMapper.insertOneSchedule(scheduleForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateSchedule(ScheduleForm scheduleForm) {
        try {
            ResponseVO responseVO = preCheck(scheduleForm);
            if(!responseVO.getSuccess()){
                return responseVO;
            }
            //在修改时要检查想要修改的排片信息是否已被观众可见，若可见则无法修改
            if(isAudienceCanView(Arrays.asList(scheduleForm.getId()))){
                return ResponseVO.buildFailure(VIEW_CONFLICT_ERROR_MESSAGE);
            }

            scheduleMapper.updateScheduleById(scheduleForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getScheduleById(int id) {
        try {
            ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(id);
            if(scheduleItem != null){
                return ResponseVO.buildSuccess(new ScheduleItemVO(scheduleItem));
            }else{
                return ResponseVO.buildSuccess(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getScheduleView() {
        try {
            return ResponseVO.buildSuccess(scheduleMapper.selectView());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchAudienceSchedule(int movieId) {
        try{
            //根据view中设置的排片可见限制
            int days = scheduleMapper.selectView();
            List<ScheduleItem> scheduleItems = scheduleMapper.selectScheduleByMovieId(movieId);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date endDate = getNumDayAfterDate(today, days);

            List<ScheduleItem> result = new ArrayList<>();
            for(ScheduleItem s : scheduleItems){
                if(s.getStartTime().before(endDate) && s.getStartTime().after(new Date())){
                    result.add(s);
                }
            }
            int interval = 1;
            if(result.size() > 0){
                interval = (int)((result.get(result.size() - 1).getStartTime().getTime() - today.getTime()) / (1000 * 3600 * 24)) + 1;
            }

            return ResponseVO.buildSuccess(getScheduleVOList(interval, today, result));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<ScheduleItem> getScheduleByMovieIdList(List<Integer> movieIdList) {
        try {
            return scheduleMapper.selectScheduleByMovieIdList(movieIdList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ScheduleItem getScheduleItemById(int id) {
        try {
            return scheduleMapper.selectScheduleById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO searchScheduleSevenDays(int hallId, Date startDate) {
        try {
            // 处理查询表单的起止时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = simpleDateFormat.parse(simpleDateFormat.format(startDate));
            Date endDate = getNumDayAfterDate(startDate, 7);

            // 按照日期整理ScheduleItem
            List<ScheduleItem> scheduleItemList = scheduleMapper.selectSchedule(hallId, startDate, endDate);

            return ResponseVO.buildSuccess(getScheduleVOList(7, startDate, scheduleItemList));
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO setScheduleView(ScheduleViewForm scheduleViewForm) {
        try{
            if(scheduleViewForm.getDay() < 0){
                return ResponseVO.buildFailure(VIEW_COUNT_ERROR_MESSAGE);
            }

            int num = scheduleMapper.selectViewCount();
            if(num == 0){
                scheduleMapper.insertOneView(scheduleViewForm);
            }
            else if(num == 1){
                scheduleMapper.updateOneView(scheduleViewForm);
            }
            else {
                return ResponseVO.buildFailure(VIEW_COUNT_ERROR_MESSAGE);
            }
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteBatchOfSchedule(ScheduleBatchDeleteForm scheduleBatchDeleteForm) {
        try{
            List<Integer> scheduleIdList = scheduleBatchDeleteForm.getScheduleIdList();
            if(scheduleIdList.size() == 0){
                return ResponseVO.buildFailure(ID_LIST_NULL_ERROR_MESSAGE);
            }

            if(isAudienceCanView(scheduleIdList)){
                return ResponseVO.buildFailure(VIEW_CONFLICT_ERROR_MESSAGE);
            }
            scheduleMapper.deleteScheduleBatch(scheduleIdList);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    public Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }


    /**
     * 新增或修改排片信息的公共前置检查
     * @param scheduleForm
     * @return
     */
    ResponseVO preCheck(ScheduleForm scheduleForm){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 检查排片时间是否早于当前时间
            if(scheduleForm.getStartTime().before(new Date())){
                return ResponseVO.buildFailure(BEFORE_NOW_TIME_ERROR_MESSAGE);
            }
            // 处理排片跨天错误
            if(!simpleDateFormat.format(scheduleForm.getStartTime()).equals(simpleDateFormat.format(scheduleForm.getEndTime()))){
                return ResponseVO.buildFailure(CROSS_DAYS_ERROR_MESSAGE);
            }

            //检查影厅是否存在
            if(hallServiceForBl.getHallById(scheduleForm.getHallId()) == null){
                return ResponseVO.buildFailure(HALL_NOT_EXIST_ERROR_MESSAGE);
            }

            // 检查电影是否存在
            Movie movie = movieServiceForBl.getMovieById(scheduleForm.getMovieId());
            if(movie == null){
                return ResponseVO.buildFailure(MOVIE_NOT_EXIST_ERROR_MESSAGE);
            }

            // 检查电影的上映时间是否和排片时间匹配
            if(scheduleForm.getStartTime().before(movie.getStartDate())){
                return ResponseVO.buildFailure(BEFORE_START_DATE_ERROR_MESSAGE);
            }

            // 校验排片时间段合法性
            int minutes = movie.getLength();
            Calendar calendarStartTime = Calendar.getInstance();
            calendarStartTime.setTime(scheduleForm.getStartTime());
            calendarStartTime.add(Calendar.MINUTE, minutes);
            Date endTime = calendarStartTime.getTime();
            if(scheduleForm.getEndTime().before(endTime)){
                return ResponseVO.buildFailure(DATE_INTERVAL_LESS_THAN_LENGTH_ERROR_MESSAGE);
            }

            // 检查该排片时间段是否和其他排片信息冲突
            int id = scheduleForm.getId() == null? 0 : scheduleForm.getId();
            if(0 != scheduleMapper.selectScheduleConflictByHallIdAndTime(scheduleForm.getHallId(), scheduleForm.getStartTime(), scheduleForm.getEndTime(),id).size()){
                return ResponseVO.buildFailure(TIME_CONFLICT_ERROR_MESSAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseVO.buildSuccess();
    }

    boolean isAudienceCanView(List<Integer> scheduleIdList) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        Date endDate = getNumDayAfterDate(today, scheduleMapper.selectView());

        List<ScheduleItem> scheduleList = scheduleMapper.selectScheduleBatch(scheduleIdList);
        for(ScheduleItem s : scheduleList){
            if(s.getEndTime().before(endDate) && s.getEndTime().after(today)){
                return true;
            }

        }

        return false;
    }

    List<ScheduleVO> getScheduleVOList(int interval,Date startDate, List<ScheduleItem> scheduleItemList ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ScheduleVO> scheduleVOList = new ArrayList<>();
        for(int i = 0; i < interval; i++){
            Date date = getNumDayAfterDate(startDate, i);
            ScheduleVO scheduleVO = new ScheduleVO();
            scheduleVO.setDate(date);
            List<ScheduleItemVO> scheduleItems = new ArrayList<>();
            List<ScheduleItemVO> scheduleItemVOList = scheduleItemList2ScheduleItemVOList(scheduleItemList);
            for(ScheduleItemVO scheduleItem : scheduleItemVOList){
                Date startTime = simpleDateFormat.parse(simpleDateFormat.format(scheduleItem.getStartTime()));
                if(date.equals(startTime)){
                    scheduleItems.add(scheduleItem);
                }
            }
            scheduleVO.setScheduleItemList(scheduleItems);
            scheduleVOList.add(scheduleVO);
        }
        return scheduleVOList;
    }



    private List<ScheduleItemVO> scheduleItemList2ScheduleItemVOList(List<ScheduleItem> scheduleItemList){
        List<ScheduleItemVO> scheduleItemVOList = new ArrayList<>();
        for(ScheduleItem scheduleItem : scheduleItemList){
            scheduleItemVOList.add(new ScheduleItemVO(scheduleItem));
        }
        return scheduleItemVOList;
    }
}
