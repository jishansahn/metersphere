package io.metersphere.api.controller;
import eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler;
public class test{
    public test(String name){
        //这个构造器仅有一个参数：name
        System.out.println("小狗的名字是 : " + name );
        RequestResponseWebSocketSampler ss=new RequestResponseWebSocketSampler();
        System.out.println("小狗的名字是 : " + ss );
    }
    public static void main(String[] args){
        // 下面的语句将创建一个Puppy对象
        test test = new test( "tommy" );
    }
}

