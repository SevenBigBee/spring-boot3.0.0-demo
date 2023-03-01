package com.laijava.easyRules;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.laijava.easyRules.vo.SrcObj;
import com.laijava.easyRules.vo.SrcObjItem;
import com.laijava.easyRules.vo.TargetObj;
import com.laijava.easyRules.vo.TargetObjItem;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.JsonRuleDefinitionReader;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.mvel2.ParserContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通过规则引擎，将一个类对象的值转换到另一个类对象上,可以用在接口适配上，将多个接口的对象统一转换到一个对象中，由实施运维人员自定义转换规则，
 * 可以及时生效
 */
public class App {
    public static String jsonRule = "[\n" +
            "  {\n" +
            "    \"name\": \"编码转换\",\n" +
            "    \"description\": \"将SrcObjItem中的key字段转换为TargetObjItem中的code\",\n" +
            "    \"priority\": 1,\n" +
            "    \"condition\": \"null != srcItem.key\",\n" +
            "    \"actions\": [\n" +
            "      \"System.out.println(\\\"srcItem.key\\\");\",\n" +
            "      \"targetItem.setCode(srcItem.key);\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"值转换\",\n" +
            "    \"description\": \"将SrcObjItem中的name字段转换为TargetObjItem中的value\",\n" +
            "    \"priority\": 2,\n" +
            "    \"condition\": \"null != srcItem.value\",\n" +
            "    \"actions\": [\n" +
            "      \"System.out.println(\\\"Shop: Sorry, you are not allowed to buy alcohol\\\");\",\n" +
            "      \"targetItem.setValue(srcItem.value);\"\n" +
            "    ]\n" +
            "  }\n" +
            "]";

    public static void main(String[] args) throws Exception {
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        ParserContext context =new ParserContext();
        // 设置上下文信息，yml公式将会读取其中内容
        context.addImport("TargetObj", TargetObj.class);
        // 使用yaml格式的自定义规则
        Rules yamlRules = ruleFactory.createRules(new FileReader(App.class.getClassLoader().getResource("objectRelation.yml").getFile()));
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        Facts facts = new Facts();


        // 创建测试数据
        SrcObj srcObj = new SrcObj();
        srcObj.setName("名称");
        srcObj.setRemark("备注信息");
        srcObj.setSex("男");
        List<SrcObjItem> items = new ArrayList<>();
        srcObj.setItems(items);

        for (int i = 0; i < 10 ; i ++){
            SrcObjItem item = new SrcObjItem();
            item.setKey("key" + i);
            item.setValue(UUID.randomUUID().toString());
            items.add(item);
        }


        TargetObj targetObj = new TargetObj();
        List<TargetObjItem> targetObjItems = new ArrayList<>();
        targetObj.setBody(targetObjItems);
        facts.put("src",srcObj);
        facts.put("target",targetObj);
        // 设置监听事件

        rulesEngine.registerRuleListener(new MyRulesListener());
        rulesEngine.registerRulesEngineListener(new MyRuleEngineListener());
        rulesEngine.fire(yamlRules, facts);
        if (null != srcObj.getItems()){
            // 使用json格式的自定义规则
            MVELRuleFactory jsonRuleFactory = new MVELRuleFactory(new JsonRuleDefinitionReader());
            Rules jsonRules = jsonRuleFactory.createRules( new StringReader(jsonRule));
            for(SrcObjItem srcObjItem : srcObj.getItems()){
                Facts itemFacts = new Facts();
                itemFacts.put("srcItem",srcObjItem);
                TargetObjItem targetObjItem = new TargetObjItem();
                itemFacts.put("targetItem",targetObjItem);
                rulesEngine.fire(jsonRules, itemFacts);
                targetObjItems.add(targetObjItem);
            }
        }

        System.out.println(JSON.toJSONString(targetObj));
    }
}
