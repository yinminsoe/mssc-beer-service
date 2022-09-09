package ym.course.micro.msscbeerservice.web.mapper;

import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp ts){
        LocalDateTime ldt =ts.toLocalDateTime();
        return OffsetDateTime.of(ldt.getYear(),
                ldt.getMonthValue(),
                ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(),
                ldt.getSecond(), ldt.getNano(), ZoneOffset.UTC);
    }

    public Timestamp asTimestamp(OffsetDateTime odt){
      return  Timestamp.valueOf(odt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }
}
