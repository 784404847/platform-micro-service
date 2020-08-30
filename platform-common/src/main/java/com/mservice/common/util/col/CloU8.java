package com.mservice.common.util.col;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jdk 1.8 集合类工具
 *
 * @author wuwenjun
 */
@Slf4j
public class CloU8 {


    /**
     * 获取几个属性集合
     *
     * @param list
     * @param fieldFun
     * @param <L>
     * @param <V>
     * @return
     */
    public static <L, V> StreamEx<V> pump(Collection<L> list, Function<L, V> fieldFun) {

        if (CollectionUtils.isEmpty(list) || fieldFun == null) {
            return StreamEx.empty();
        }

        return StreamEx.of(list).map(fieldFun);
    }


    /**
     * 按照key值归并 list （注意key重复问题）
     *
     * @param list
     * @param keyFun
     * @param <K>
     * @param <L>
     * @return
     */
    public static <K, L> Map<K, L> index(Collection<L> list, Function<L, K> keyFun) {

        if (CollectionUtils.isEmpty(list) || keyFun == null) {
            return Maps.newHashMap();
        }

        return StreamEx.of(list).toMap(keyFun, Function.identity(), (_k1, k2) -> k2);
    }

    /**
     * 按照key 归并 value
     *
     * @param list
     * @param keyFun
     * @param valueFun
     * @param <L>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <L, K, V> Map<K, V> index(Collection<L> list, Function<L, K> keyFun, Function<L, V> valueFun) {

        Preconditions.checkNotNull(keyFun, " key function cannot be null");
        Preconditions.checkNotNull(keyFun, " value function cannot be null");

        if (list == null || list.isEmpty()) {
            return Maps.newHashMap();
        }

        try {
            return StreamEx.of(list).toMap(keyFun, valueFun);
        } catch (Exception ex) {
            log.warn("1个key对应多个value转换异常，使用传统集合操作转换");
            return index7(list, keyFun, valueFun);
        }
    }

    /**
     * java 1.7 归并按照key归并value
     *
     * @param collection
     * @param keyFun
     * @param valueFun
     * @param <L>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <L, K, V> Map<K, V> index7(Collection<L> collection, Function<L, K> keyFun, Function<L, V> valueFun) {

        if (keyFun == null || valueFun == null) {
            throw new RuntimeException("converter is null");
        }

        HashMap<K, V> map = new HashMap<>();

        if (collection == null || collection.isEmpty()) {
            return map;
        }

        for (L t : collection) {
            K key = keyFun.apply(t);

            if (key == null) {
                continue;
            }

            map.put(key, valueFun.apply(t));
        }
        return map;
    }


    /**
     * 集合按照属性做MAP归并索引
     *
     * @param list
     * @param keyFun 获取T属性作为Key
     * @param <K>
     * @param <L>
     * @return
     */
    public static <K, L> Map<K, List<L>> indexMerge(Collection<L> list,
                                                    Function<L, K> keyFun) {
        if (CollectionUtils.isEmpty(list) || keyFun == null) {
            return Maps.newHashMap();
        }
        return StreamEx.of(list)
                .groupingBy(keyFun);
    }

    /**
     * 集合按照属性做MAP归并索引(key,value函数自定义)
     *
     * @param list
     * @param mapKey   获取T属性作为Key
     * @param mapValue 获取T属性作为Value归并
     * @param <K>
     * @param <V>
     * @param <L>
     * @return
     */
    public static <K, V, L> Map<K, List<V>> indexMerge(Collection<L> list,
                                                       Function<L, K> mapKey, Function<L, V> mapValue) {

        if (CollectionUtils.isEmpty(list) || mapKey == null || mapValue == null) {
            return Maps.newHashMap();
        }

        return EntryStream.of(StreamEx.of(list).groupingBy(mapKey))
                .mapValues(t -> StreamEx.of(t).map(mapValue).<List<V>>toList())
                .toMap();
    }

    public static <K, V, L> Map<K, Set<V>> indexMergeSet(Collection<L> list,
                                                         Function<L, K> mapKey, Function<L, V> mapValue) {

        if (CollectionUtils.isEmpty(list) || mapKey == null || mapValue == null) {
            return Maps.newHashMap();
        }

        return EntryStream.of(StreamEx.of(list).groupingBy(mapKey))
                .mapValues(t -> StreamEx.of(t).map(mapValue).toSet())
                .toMap();
    }

    /**
     * 按照指定的过滤条件过滤Map-List
     *
     * @param list
     * @param mapKey
     * @param predicate
     * @param <K>
     * @param <L>
     * @return
     */
    public static <K, L> Map<K, List<L>> filterMerge(Collection<L> list,
                                                     Function<L, K> mapKey, Predicate<L> predicate) {

        if (CollectionUtils.isEmpty(list) || mapKey == null) {
            return Maps.newHashMap();
        }

        return StreamEx.of(list)
                .filter(predicate)
                .groupingBy(mapKey);
    }

    /**
     * 获取集合中的某一个元素
     *
     * @param list     数组
     * @param fieldFun 需要获取的值
     * @param <L>>
     * @param <V>
     * @return
     */
    public static <L, V> Stream<V> pump8(Collection<L> list, Function<L, V> fieldFun) {

        if (CollectionUtils.isEmpty(list) || fieldFun == null) {
            return Stream.empty();
        }
        return list.stream().map(fieldFun);
    }

    /**
     * 原生jdk
     * 将list转为map ，适用于 k l 一对一情况
     *
     * @param list
     * @param keyFun
     * @param <K>
     * @param <L>
     * @return
     */
    public static <K, L> Map<K, L> index8(Collection<L> list,
                                          Function<L, K> keyFun) {

        if (CollectionUtils.isEmpty(list) || keyFun == null) {
            return Maps.newHashMap();
        }

        return list.stream().collect(Collectors.toMap(keyFun, Function.identity(), (key1, key2) -> key2));
    }


    /**
     * 原生jdk
     *
     * @param list
     * @param keyFun
     * @param <K>
     * @param <L>
     * @return
     */
    public static <K, L> Map<K, List<L>> indexMerge8(Collection<L> list, Function<L, K> keyFun) {

        if (CollectionUtils.isEmpty(list) || keyFun == null) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyFun));
    }

    /*test*/
    public static void main(String[] args) {
        List<Foo> list = Lists.newArrayList(
                new Foo().setNo(1).setName("test1").setPrice(new BigDecimal(1)),
                new Foo().setNo(1).setName("test2").setPrice(new BigDecimal(2)),
                new Foo().setNo(2).setName("test1").setPrice(new BigDecimal(3)),
                new Foo().setNo(3).setName("t3").setPrice(new BigDecimal(4)),
                new Foo().setNo(3).setName("t4").setPrice(new BigDecimal(2)),
                new Foo().setNo(4).setName("t5").setPrice(new BigDecimal(4))
        );
//        Set<String> collect = pump(list, Foo::getName).collect(Collectors.toSet());
//        System.out.print(JSON.toJSONString(collect));

        System.out.print(JSON.toJSONString(indexMerge(list, Foo::getName)));

//
//        System.out.println(indexMerge(list, Foo::getNo, Foo::getName));

//        System.out.println(filterMerge(list, Foo::getNo, foo -> foo.price.compareTo(new BigDecimal(3))<0));

    }

    public static class Foo {
        private Integer no;
        private String name;
        private BigDecimal price;

        public Integer getNo() {
            return no;
        }

        public Foo setNo(Integer no) {
            this.no = no;
            return this;
        }

        public String getName() {
            return name;
        }

        public Foo setName(String name) {
            this.name = name;
            return this;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public Foo setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

    }

}
