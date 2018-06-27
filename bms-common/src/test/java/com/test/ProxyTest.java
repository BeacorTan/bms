package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @create 2018-06-27 9:49
 **/
public class ProxyTest {

    public static void main(String[] args) {
        Person proxyP = new ProxyFactory<Person>().getProxy(new Emp());
        proxyP.motion();

    }
}

class ProxyFactory<T> {


    public T getProxy(T t) {
        // t.getClass().getInterfaces()  获取实现接口
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new MyInvocationHandler(t));
    }

    class MyInvocationHandler implements InvocationHandler {

        private T t;

        public MyInvocationHandler(T t) {
            this.t = t;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.println("切面开始");
            Object object = method.invoke(t, objects);
            System.out.println("切面结束");
            return object;
        }
    }

}

interface Person {

    void motion();
}

class Emp implements Person {

    @Override
    public void motion() {

        System.out.println("跑步");
    }
}
