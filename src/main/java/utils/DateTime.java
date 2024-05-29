package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime{
  public static String getDateAfterFormated(Date date){
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    return formatter.format(date);
  }
}
