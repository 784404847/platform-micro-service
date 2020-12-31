package com.mservice.common.util.col;

import com.alibaba.fastjson.JSON;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.google.common.collect.Lists;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/16 下午4:34
 */
public class ModelUtil {

    public static String diffFiled(Object object1,Object object2){

        Equator equator = new GetterBaseEquator(true);
        List<FieldInfo> diffFields = equator.getDiffFields(object1, object2);
        List<FiledData> filedDataList = Lists.newArrayList();
        FiledData filedData;
        for(FieldInfo fieldInfo:diffFields){
            filedData = new FiledData();
            filedData.setFiledName(fieldInfo.getFieldName());
            filedData.setFirstVal(fieldInfo.getFirstVal());
            filedData.setSecondVal(fieldInfo.getSecondVal());
            filedDataList.add(filedData);
        }

        return null;
    }

    @Data
    public static class FiledData {
       private String filedName;
       private Object firstVal;
       private Object secondVal;
    }

    public static void main(String[] args) {

        FiledData filedData1 = new FiledData();
        filedData1.setFiledName("1");
        filedData1.setFirstVal("1");
        filedData1.setSecondVal("2");
        FiledData filedData2 = new FiledData();
        filedData2.setFiledName("2");
        filedData2.setFirstVal("2");
        filedData2.setSecondVal("2");
        Equator equator = new GetterBaseEquator(true);
        List<FieldInfo> diffFields = equator.getDiffFields(filedData1, filedData2);

        System.out.println(JSON.toJSON(diffFields));
    }

}
