package com.cloud.springcloudmasterservice.config.dynamicds.transcation;//package com.example.multiaop.aop;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.interceptor.*;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class TranscationConfig {
//    private static final int TX_METHOD_TIMEOUT = 5;
//    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.example.multiaop.service.*.*(..))";
//
//    @Autowired
//    private JtaTransactionManager transactionManager;
//
//    @Bean
//    public TransactionInterceptor txAdvice() {
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//
//        /* 只读事务，不做更新操作 */
//        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
//        readOnlyTx.setReadOnly(true);
//        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
//
//        /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
//        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
//        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
//        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        requiredTx.setTimeout(TX_METHOD_TIMEOUT);
//        Map<String, TransactionAttribute> txMap = new HashMap<>(10);
//
//        txMap.put("add*", requiredTx);
//        txMap.put("save*", requiredTx);
//        txMap.put("insert*", requiredTx);
//        txMap.put("update*", requiredTx);
//        txMap.put("delete*", requiredTx);
//
//        txMap.put("get*", readOnlyTx);
//        txMap.put("query*", readOnlyTx);
//        txMap.put("list*", readOnlyTx);
//        txMap.put("find*", readOnlyTx);
//        source.setNameMap(txMap);
//        return new TransactionInterceptor(transactionManager, source);
//    }
//
//    /**
//     * 切点
//     *
//     * @return
//     */
//    @Bean
//    public Advisor txAdviceAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
//        return new DefaultPointcutAdvisor(pointcut, txAdvice());
//    }
//
//}
