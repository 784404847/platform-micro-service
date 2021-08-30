package com.mservice.transaction.dao;

import com.mservice.transaction.model.TransFlowMg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/8/30 下午4:23
 */
@Component
public class TransFlowMGDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveTransFlow(TransFlowMg transFlowMg){
        mongoTemplate.save(transFlowMg);
    }

    public void removeTransFlow(Long id){
        mongoTemplate.remove(id);
    }

    public void updateDemo(TransFlowMg transFlowMg){

        Query q = new Query(Criteria.where("id").is(transFlowMg.getId()));

        Update update = new Update();
        update.set("transactionAmount", "100");
        update.set("transactionTime", LocalDateTime.now());
        update.set("flowStatus", transFlowMg.getFlowStatus());

        mongoTemplate.updateFirst(q,update,TransFlowMg.class);
    }

    public TransFlowMg findById(Long id){
        Query q = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(q,TransFlowMg.class);
    }

}
