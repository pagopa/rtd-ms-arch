package eu.sia.meda.connector.jpa.custom.h2;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueTimestampTimeZone;

public class ToChar {

    String toChar(java.sql.Timestamp date, String pattern) throws Exception {

        pattern = pattern.replaceAll("YY","yy");

        pattern = pattern.replaceAll("DD","dd");

        pattern = pattern.replaceAll("HH24|hh24","HH");

        pattern = pattern.replaceAll("HH?!24|hh?!24","KK");

        pattern = pattern.replaceAll("MON|mon","MMM");

        pattern = pattern.replaceAll("MI|mi","mm");

        pattern = pattern.replaceAll("SS|ss","ss");

        pattern = pattern.replaceAll("AM|PM","aa");

        SimpleDateFormat sm = new SimpleDateFormat(pattern);

        return sm.format(date);



    }


}
