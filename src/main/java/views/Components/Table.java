package views.Components;

import Annotations.DisplayedField;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Table{
  public static <T> void table(ArrayList<T> objects){
    System.out.print("|  No  ");
    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null)
        System.out.printf("|%15s  ", displayedField.displayName());
    }
    System.out.println("|");
    System.out.print("|______");
    for(Field field : objects.getFirst().getClass().getDeclaredFields()){
      DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
      if(displayedField != null)
        System.out.print("__________________");
    }
    System.out.println("_");
    for(int i = 0; i < objects.size(); i++){
      System.out.printf("|%6d", i);
      for(Field field : objects.get(i).getClass().getDeclaredFields()){
        DisplayedField displayedField = field.getAnnotation(DisplayedField.class);
        if(displayedField != null){
          try{
            field.setAccessible(true);
            System.out.printf("|%15s  ", field.get(objects.get(i)));
          }catch(IllegalArgumentException | IllegalAccessException e){
          }
        }
      }
      System.out.println("|");
    }
  }
}
