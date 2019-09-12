package com.wzvtcsoft.common;

import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.OneToMany;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;
import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

/**
 * @author swxu_2005@163.com
 */
public class BeanUtil {

  private static final String JAVASSIST_PROXY_NAME_PATTERN = "$HibernateProxy$";

  /**
   * 针对实体类中存在@OneToMany注解时的对象复制
   */
  @SuppressWarnings("unchecked")
  public static void copyProperties(Object source, Object target, boolean copyOneToMany) {
    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");
    Class<?> actualEditable = target.getClass();
    // 若是hibernate的javassist代理对象，则找到其父类
    if (actualEditable.getName().contains(JAVASSIST_PROXY_NAME_PATTERN)) {
      actualEditable = actualEditable.getSuperclass();
    }
    Field[] fields = actualEditable.getDeclaredFields();

    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    for (PropertyDescriptor targetPd : targetPds) {
      String propertyName = targetPd.getName();
      boolean isOneToMany = false;
      for (Field field : fields) {
        if (Objects.equals(field.getName(), propertyName) && field.getAnnotation(OneToMany.class) != null) {
//          System.out.println("当前属性：" + propertyName + " 有OneToMany");
          isOneToMany = true;
          break;
        }
      }
      if (isOneToMany) {
        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
        Method targetReadMethod = targetPd.getReadMethod();
        Method sourceReadMethod = sourcePd.getReadMethod();
        if (copyOneToMany && targetReadMethod != null && sourceReadMethod != null) {
          try {
            Collection sourceValue = (Collection) sourceReadMethod.invoke(source);
            Collection targetValue = (Collection) targetReadMethod.invoke(target);
            if (sourceValue != null) {
              targetValue.clear();
              targetValue.addAll(sourceValue);
            }
          } catch (Exception e) {
            throw new FatalBeanException("拷贝" + propertyName + "属性失败", e);
          }
        }
      } else {
        // 以下基本与Spring的BeanUtils.copyProperties方法相同
        Method writeMethod = targetPd.getWriteMethod();
        if (writeMethod != null) {
          PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
          if (sourcePd != null) {
            Method readMethod = sourcePd.getReadMethod();
            if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
              try {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                  readMethod.setAccessible(true);
                }

                Object value = readMethod.invoke(source);
                // 如果从source读到的某属性为null，则不写到target中
                if (value == null) {
                  continue;
                }

                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                  writeMethod.setAccessible(true);
                }

                writeMethod.invoke(target, value);
              } catch (Throwable e) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", e);
              }
            }
          }
        }
      }
    }
  }

  public static void copyProperties(Object source, Object target) {
    copyProperties(source, target, true);
  }

}
