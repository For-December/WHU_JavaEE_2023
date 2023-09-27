package edu.whu.framework;
import edu.whu.demo.MyClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BootstrapServiceTest {


    /**
     *  测试正常情况
     *
     *  方法上标注throws BootstrapException是因为start抛出的BootstrapException是一个checked exception，编译器要求必须捕获或者抛出。
     *  被测程序正确时，执行这个测试用例并不会发生异常。如果抛出异常，测试用例不通过，说明程序有错误。
     */
    @Test
    public void testMyClass() throws BootstrapException{
        Object obj = BootstrapService.start("/myapp.properties");
        assertNotNull(obj);
        assertEquals(MyClass.class,obj.getClass());
        assertEquals("online",((MyClass)obj).getStatus());
    }

    /**
     *  测试属性加载时的各种错误情况
     */
    @Test
    public void testLoadPropFile(){
        //测试不存在的属性文件
        BootstrapException exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-x.properties");
        });
        assertEquals(BootstrapException.ErrorType.FILE_NOTFOUND,exception.getErrorType());

        //测试不合法的属性文件
        exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-malform.properties");
        });
        assertEquals(BootstrapException.ErrorType.PROP_READ_ERROR,exception.getErrorType());

        //测试boostrapClass属性不存在的属性文件
        exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-key-error.properties");
        });
        assertEquals(BootstrapException.ErrorType.PROP_READ_ERROR,exception.getErrorType());
    }

    /**
     *  测试创建对象的各种错误
     */
    @Test
    public void testCreatObject() {
        //测试不存在类错误
        BootstrapException exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-class-notfound.properties");
        });
        assertEquals(BootstrapException.ErrorType.CLASS_NOTFOUND,exception.getErrorType());

        //测试抽象类
        exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-abstract-class.properties");
        });
        assertEquals(BootstrapException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

        //测试构造函数为私有的情况
        exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-private-constructor.properties");
        });
        assertEquals(BootstrapException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

        //测试不存在无参构造函数错误
        exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-no-parameterless-constructor.properties");
        });
        assertEquals(BootstrapException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

    }

    /**
     *  测试方法调用的各种情况
     */
    @Test
    public void testMethodInvoke() throws BootstrapException {
        //静态方法可以正确调用
        Object obj = BootstrapService.start("/myapp-static-method.properties");
        assertNotNull(obj);

        //私有方法也能够正确调用
        obj = BootstrapService.start("/myapp-private-method.properties");
        assertNotNull(obj);

        //InitMethod带参数
        BootstrapException exception=assertThrows(BootstrapException.class,()->{
            BootstrapService.start("/myapp-initmethod-withparam.properties");
        });
        assertEquals(BootstrapException.ErrorType.INIT_METHOD_ERROR,exception.getErrorType());
    }


}
