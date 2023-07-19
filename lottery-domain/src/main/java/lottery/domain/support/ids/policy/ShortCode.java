package lottery.domain.support.ids.policy;

import lottery.domain.support.ids.IIDGenerator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

/**
 * 短码生成策略，仅支持很小的调用量，用于生成活动配置类编号，保证全局唯一
 */
@Component
public class ShortCode implements IIDGenerator {
    @Override
    public synchronized long nextId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        StringBuilder idStr = new StringBuilder();
        idStr.append(year-2020).append(hour).append(String.format("%02d", week)).
                append(day).append(String.format("%03d",new Random().nextInt(1000)));
        return Long.parseLong(idStr.toString());
    }
}
