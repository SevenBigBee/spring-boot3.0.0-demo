package com.laijava.easyRules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

/**
 * rule 运行的监听,此处我们可以方便进行数据审计记录（存储数据库，同时基于pipline模式，可视化分析任务状态，以及数据）
 */
public class MyRulesListener implements RuleListener {
    Log log = LogFactory.getLog(MyRulesListener.class);

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        return true;
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean b) {
        log.info("-----------------afterEvaluate-----------------");
        // log.info(rule.getName()+rule.getDescription()+facts.toString());
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        log.info("-----------------beforeExecute-----------------");

        // log.info(rule.getName()+rule.getDescription()+facts.toString());

    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        log.info("-----------------onSuccess-----------------");
        // log.info(rule.getName()+rule.getDescription()+facts.toString());

    }

    @Override
    public void onFailure(Rule rule, Facts facts, Exception e) {
        log.info("-----------------onFailure-----------------");
        log.info(rule.getName()+"----------"+rule.getDescription()+facts.toString()+e.toString());

    }
}
