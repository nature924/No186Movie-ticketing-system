import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.user.HistoryMapper;
import com.example.cinema.po.VIPCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.cinema.po.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class cinemaTest{
    @Autowired
    TicketService ticketService;
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Test
    public void test1(){
        VIPCard vipCard=new VIPCard();
        vipCard.setUserId(7);
        vipCard.setBalance(100);
        vipCard.setName("nmsl");
        vipCard.setTotal(100);
        vipCardMapper.insertOneCard(vipCard);
        historyItem history =new historyItem();
        history.setMoney(100);
        history.setUserId(7);
        history.setKind(1);
        history.setDescription("nmsl");
        historyMapper.insertHistory(history);
        assertEquals (vipCardMapper.selectCardByUserId(7).getBalance(),historyMapper.getHistoryByUserId(7).get(0).getMoney(),0);
    }
}