package com.example.cinema.controller.management;

import com.example.cinema.bl.management.ScheduleService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleBatchDeleteForm;
import com.example.cinema.vo.ScheduleForm;
import com.example.cinema.vo.ScheduleViewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**排片管理
 * @author fjj
 * @date 2019/4/11 4:13 PM
 */
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
    public ResponseVO addSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.addSchedule(scheduleForm);
    }

    @RequestMapping(value = "/schedule/update", method = RequestMethod.POST)
    public ResponseVO updateSchedule(@RequestBody ScheduleForm scheduleForm){
        return scheduleService.updateSchedule(scheduleForm);
    }

    @RequestMapping(value = "/schedule/search", method = RequestMethod.GET)
    public ResponseVO searchSchedule(@RequestParam int hallId, @RequestParam Date startDate){
        //这里传递startDate参数时，前端传的是用/分隔的时间，例如startDate=2019/04/12
        return scheduleService.searchScheduleSevenDays(hallId, startDate);
    }

    @RequestMapping(value = "/schedule/search/audience", method = RequestMethod.GET)
    public ResponseVO searchAudienceSchedule(@RequestParam int movieId){
        return scheduleService.searchAudienceSchedule(movieId);
    }

    @RequestMapping(value = "/schedule/view/set", method = RequestMethod.POST)
    public ResponseVO setScheduleView(@RequestBody  ScheduleViewForm scheduleViewForm){
        return scheduleService.setScheduleView(scheduleViewForm);
    }

    @RequestMapping(value = "/schedule/view", method = RequestMethod.GET)
    public ResponseVO getScheduleView(){
        return scheduleService.getScheduleView();
    }



    @RequestMapping(value = "/schedule/delete/batch", method = RequestMethod.DELETE)
    public ResponseVO deleteBatchOfSchedule(@RequestBody ScheduleBatchDeleteForm scheduleBatchDeleteForm){
        return scheduleService.deleteBatchOfSchedule(scheduleBatchDeleteForm);
    }

    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public ResponseVO getScheduleById(@PathVariable int id){
        return scheduleService.getScheduleById(id);
    }



}
