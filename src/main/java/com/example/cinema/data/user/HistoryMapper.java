package com.example.cinema.data.user;
import com.example.cinema.po.historyItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface HistoryMapper {
      List<historyItem> getHistoryByUserId(int userId);

     void insertHistory(historyItem history);
}
