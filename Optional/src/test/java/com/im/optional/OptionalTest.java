package com.im.optional;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@SpringBootTest
public class OptionalTest {

    //TODO  12 个 核心方法

    //TODO empty()  empty 方法返回一个不包含值的 Optional 实例，单独使用没什么意义，主要和其他方法搭配使用。
    @Test
    public void emptyTest() {
        Optional optional = Optional.empty();
        System.out.println(optional);
     }


    //TODO of()  of 方法会返回一个 Optional 实例，如果传入的值非空,会返回包含指定值的对象；如果传入空，会立刻抛出空指针异常。
    @Test
    public void ofTest() {
        Optional optional = Optional.of("hello world");
        System.out.println(optional);
        System.out.println(optional.get());
        // 为空情况下，会抛空指针异常
        Optional optionalNullCase = Optional.of(null);
        /*
        System.out.println(optional);
        -- 输出结果
        Exception in thread "main" java.lang.NullPointerException
        at java.util.Objects.requireNonNull(Objects.java:203)
        at java.util.Optional.<init>(Optional.java:96)
        at java.util.Optional.of(Optional.java:108)
        */
    }

    /*
     TODO ofNullable()   ofNullable 方法会返回一个 Optional 实例，如果传入的值非空，
       会返回包含指定值的对象；如果传入空，会返回不包含任何值的 empty 对象，
       也就是最开始介绍的Optional.empty()对象。
    */
    @Test
    public void ofNullTest() {
        Optional optional = Optional.ofNullable(null);
        System.out.println(optional);
        /*输出结果 Optional.empty*/

    }


    //TODO isPresent()   方法用来判断实例是否包含值，如果包含非空值，返回 true，否则返回 false。
    @Test
    public void isPresentTest() {
        // 非空值，返回true
        boolean isPresent = Optional.of("hello world").isPresent();
        System.out.println(isPresent);

        // 空值，返回false
        boolean isPresentNull = Optional.ofNullable(null).isPresent();
        System.out.println(isPresentNull);
    }


    //TODO get() 如果实例包含非空值，则返回当前值；否则抛出 NoSushElementException 异常。
    @Test
    public void getTest() {
        // 非空值，返回当前值
        Object res = Optional.ofNullable("hello world").get();
        System.out.println(res);
        /* -- 输出结果  hello world */

        // 空值，会抛出 NoSushElementException 异常
        Object resNull = Optional.ofNullable(null).get();

    }


    //TODO  ifPresent()  ifPresent 方法作用是当实例包含非空值时，执行传入的 Consumer，比如调用一些其他方法；如果包含的值为空，不执行任何操作。
    @Test
    public void ifPresent() {
        Optional.of("hello world")
                .ifPresent(res -> {
                    System.out.println(res);
                });
    }


    //TODO  filter() 用于过滤不符合条件的值，接收一个Predicate参数，如果符合条件，会返回当前的Optional实例，否则返回 empty 实例。
    @Test
    public void filterTest() {
        Optional.of("hello world")
                .filter(x -> x.equals("hello world"))
                .ifPresent(System.out::println);
    }

    /*
     TODO map() map 方法是链式调用避免空指针的核心方法，当实例包含值时，
        对值执行传入的Function函数接口方法，并返回一个代表结果值新的Optional实例，
        也就是将返回的结果再次包装成Optional对象。
    */
    @Test
    public void mapTest() {
        Optional.ofNullable("hello world")
                .map(m -> {
                    if(m.contains(" "))
                        return m.replace(" ", "++");
                    return m;
                }).ifPresent(m -> {
                    System.out.println(m);
                });
    }

    //TODO flatMap() flatMap 方法与 map 方法类似，唯一不同的地方在于：需要手动将返回的值，包装成Optional实例，并且参数值不允许为空。
    @Test
    public void flatMapTest() {
        Optional.ofNullable("hello world")
                .flatMap(f -> {
                    if(f.contains(" ")){
                        f = f.replace(" ", "_+_");
                    }
                    return Optional.of(f);   //返回为Optional
                }).ifPresent(
                        f -> {
                            System.out.println(f);
                        }
                );
    }

    //TODO  orElse() orElse 方法作用是如果实例包含非空值，那么返回当前值；否则返回指定的默认值。
    @Test
    public void orElseTest() {
        Object res = Optional.ofNullable(null).orElse("hello world");
        System.out.println(res);
    }

    //TODO orElseGet() orElseGet 方法作用是如果实例包含非空值，返回这个值；否则，它会执行作为参数传入的Supplier函数式接口方法，并返回其执行结果。
    @Test
    public void orElseGetTest() {
        Object res = Optional.ofNullable(null).orElseGet(
                () -> {
                    return "error";
                });
        System.out.println(res);
    }

    //TODO  orElseThrow()  方法作用是如果实例包含非空值，返回这个值；否则，它会执行作为参数传入的异常类。
    @Test
    public void orElseThrowTest() {
        Optional.ofNullable(null).orElseThrow( () -> new RuntimeException("error"));
    }

    @Test
    public void Combat() {
        User user = new User();
       String hobbyName = "";
       user.setHobby(new Hobby(1L,"hobbyName"));

        if(ObjectUtils.isEmpty(user)){
            if (ObjectUtils.isEmpty(user.getHobby())) {
                hobbyName =  user.getHobby().getName();
            }
        }

       //Optional 写法
        String hobbbyName = Optional.ofNullable(user)
                .map(User::getHobby)
                .map(Hobby::getName)
                .orElse("empty hobby");

        System.out.println(hobbbyName);
    }


    @Data
    public class User {
        private Long id;
        private String name;
        private Integer age;
        private String email;
        private Hobby hobby;
    }

    @Data
    public class Hobby {
        private Long hobbyId;
        private String name;

        public Hobby(long l, String hobbyName) {
            this.hobbyId = l;
            this.name = hobbyName;
        }
    }

    public String getName(User user)  throws Exception{
        if(user!=null){
            if(user.getHobby()!=null){
                Hobby hobby = user.getHobby();
                if(hobby.getName()!=null){
                    return hobby.getName();
                }
            }
        }
        throw new RuntimeException("取值错误");
    }
    public String getNameFor(User user)  throws Exception{
        return Optional.ofNullable(user).map(User::getHobby).map(Hobby::getName).orElseThrow(() -> new RuntimeException("NPE"));
    }


    // 查看源码   Optional、 of()、 ofNullable()、
    @Test
    public void test2(User user) {
        Optional.of(user).get();
        Optional.ofNullable(user).get();
    }

    public User getUser(User user) throws Exception{
        if(user!=null){
            String name = user.getName();
            if("zhangsan".equals(name)){
                return user;
            }
        }else{
            user = new User();
            user.setName("zhangsan");
            return user;
        }
        return null;
    }

    public User getUserFor(User user) throws Exception{
        return Optional.ofNullable(user)
                .filter(u -> "zhangshurnang".equals(user.getName()))
                .orElseGet(() -> {
                    User user1 = new User();
                    user1.setName("zhangsan");
                    return user1;
                });
    }








    }
