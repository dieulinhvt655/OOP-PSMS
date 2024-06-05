package views.Components;

import Annotations.DisplayedField;
import Annotations.IsDate;
import utils.DateTime;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.System.out;

public class Table{

  public static <T> void table(List<T> objects){//[{a:1, b:"jkk"},{a:4},{a:0}]
    if(objects.isEmpty()) throw new NoSuchElementException("The table is empty");
    out.print(" ______");

    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null)
        out.print("_______________________");
    }
    out.println(" ");

    //[a,b]
    out.print("|  No  ");
    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null){
        out.printf("|%20s  ", displayedField.displayName());
      }
    }
    out.println("|");
    out.print("|______");
    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null)
        out.print("_______________________");
    }
    out.println("|");
    for(int i = 0; i < objects.size(); i++){
      out.printf("|%6d", i + 1);
      for(Field field : objects.get(i).getClass().getDeclaredFields()){
        DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
        IsDate isDate = field.getAnnotation(IsDate.class);
        if(displayedField != null){
          try{
            field.setAccessible(true);
            var value = field.get(objects.get(i));
            out.printf("|%20s  ", isDate != null && value != null ? DateTime.getDateAfterFormated((Date) value) : value);
          }catch(IllegalArgumentException | IllegalAccessException e){
          }
        }
      }
      out.println("|");
    }
    System.out.print(" ------");
    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null)
        System.out.print("-----------------------");
    }
    System.out.println(" ");
  }

  public static <T> void table(List<T> objects, String title){
    out.println(title.toUpperCase());
    table(objects);
  }
}
