package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceImpl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.data.management.*;
import com.example.cinema.po.AudiencePrice;
import com.example.cinema.po.MovieScheduleTime;
import com.example.cinema.po.MovieTotalBoxOffice;
import com.example.cinema.po.Ticket;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Movie;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override

    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
            Date current = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            current = calendar.getTime();
            List<Movie> movies=movieMapper.selectAllMovie();
            List<Integer> movieIds=new ArrayList<>();
            for(int i=0;i<movies.size();i++){
                movieIds.add(movies.get(i).getId());
            }
            List<ScheduleItem> scheduleItems=scheduleMapper.selectScheduleByMovieIdList(movieIds);
            List<ScheduleItem> scheduleItemss=new ArrayList<>();
            for(int i=0;i<scheduleItems.size();i++){
                if(scheduleItems.get(i).getStartTime().after(date)&&scheduleItems.get(i).getStartTime().before(current)){
                    scheduleItemss.add(scheduleItems.get(i));
                }
            }
            int[] totalseats=new int[movies.size()];
            for(int i=0;i<movies.size();i++){
                for(int j=0;j<scheduleItemss.size();j++){
                    if(scheduleItemss.get(j).getMovieId().equals(movies.get(i).getId())){
                        int hallseat=hallMapper.selectHallById(scheduleItemss.get(j).getHallId()).getRow()*hallMapper.selectHallById(scheduleItemss.get(j).getHallId()).getColumn();
                        totalseats[i]=totalseats[i]+hallseat;
                    }
                }
            }
            List<Ticket> tickets=statisticsMapper.selectTicketsSaled();
            List<Ticket> tickets2=new ArrayList<>();
            List<Ticket> tickets1=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                Ticket t=ticketMapper.selectTicketById(tickets.get(i).getId());
                tickets2.add(t);
            }
            int[] movieTicketSaled=new int[movies.size()];
            for(int i=0;i<tickets2.size();i++){
                ScheduleItem sc=scheduleMapper.selectScheduleById(tickets2.get(i).getScheduleId());
                if(sc.getStartTime().after(date)&&sc.getStartTime().before(current)){
                    tickets1.add(tickets2.get(i));
                }
            }
            for(int i=0;i<movies.size();i++){
                for(int j=0;j<tickets1.size();j++){
                    ScheduleItem sc=scheduleMapper.selectScheduleById(tickets1.get(j).getScheduleId());
                    if(sc.getMovieId()==movies.get(i).getId()){
                        movieTicketSaled[i]=movieTicketSaled[i]+1;
                    }
                }
            }
            List<MovieWithRateVO> movieWithRateVOS=new ArrayList<>();
            for(int i=0;i<movies.size();i++){
                movieWithRateVOS.add(new MovieWithRateVO(movies.get(i)));
            }
            for(int i=0;i<movies.size();i++){
                if(totalseats[i]!=0){
                    MovieWithRateVO mov=movieWithRateVOS.get(i);
                    mov.setRate((double)movieTicketSaled[i]/totalseats[i]);
                }
            }
            int totalseat=0;
            int totalsaled=0;
            for(int i=0;i<totalseats.length;i++){
                totalseat=totalseat+totalseats[i];
                totalsaled=totalsaled+movieTicketSaled[i];
            }
            MovieWithRateVO movietotal=new MovieWithRateVO();
            if(totalseat!=0){ movietotal.setRate((double)totalsaled/totalseat);}
            movietotal.setName("总计");
            movieWithRateVOS.add(movietotal);
            return ResponseVO.buildSuccess(movieWithRateVOS);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        try{
            Date pastDate=getNumDayAfterDate(new Date(System.currentTimeMillis()),-days);
            Timestamp timestamp=new Timestamp(pastDate.getTime());
            /*System.out.println(timestamp);*/
            List<Ticket> tickets=statisticsMapper.selectPopularMovies(timestamp);
            /*for(Ticket i:tickets){
                System.out.println(i.getUserId()+"   "+i.getScheduleId());
            }*/
            HashMap<String,Integer> movieWithBoxOffice=new HashMap<>();
            for(int i=0;i<tickets.size();i++){
                int scheduleId=tickets.get(i).getScheduleId();//System.out.println(scheduleId);
                ScheduleItem scheduleItem=scheduleService.getScheduleItemById(scheduleId);/*System.out.println(scheduleItem.equals(null));*/
                String movieName=scheduleItem.getMovieName();
                int boxOffice=(int)scheduleItem.getFare();
                if(!movieWithBoxOffice.containsKey(movieName)){
                    movieWithBoxOffice.put(movieName,boxOffice);
                }
                else{
                    int x=movieWithBoxOffice.get(movieName);
                    movieWithBoxOffice.put(movieName,x+boxOffice);
                }
            }
            List<Map.Entry<String,Integer>> rankedPopularMovies=new ArrayList<>(movieWithBoxOffice.entrySet());
            //System.out.println(rankedPopularMovies);
            Collections.sort(rankedPopularMovies, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            List<PopularMovieVO> popularMovies=new ArrayList<>();
            int x=Math.min(movieNum,rankedPopularMovies.size());
            for(int i=0;i<x;i++){
                PopularMovieVO popularMovieVO=new PopularMovieVO();
                popularMovieVO.setMovieName(rankedPopularMovies.get(i).getKey());
                popularMovieVO.setEffectiveDate(days);
                popularMovieVO.setBoxOffice(rankedPopularMovies.get(i).getValue());
                popularMovies.add(popularMovieVO);
            }
            //System.out.println(popularMovies.get(0).getMovieName() instanceof String);
            return ResponseVO.buildSuccess(popularMovies);
        } catch(Exception e){
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



    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }
}
