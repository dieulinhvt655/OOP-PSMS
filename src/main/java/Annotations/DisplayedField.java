package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Giữ lại annotation này tại runtime
@Target(ElementType.FIELD)          // Annotation này chỉ áp dụng cho các trường (field)
public @interface DisplayedField {
  // Bạn có thể thêm các thuộc tính vào đây nếu cần, ví dụ:
  String displayName() default "Default Name";
}
