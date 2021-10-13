package com.mservice.transaction.util;

import com.mservice.transaction.config.QLExpressContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wejam
 * @description
 * （1）打通了spring容器，通过扩展IExpressContext->QLExpressContext
 * 获取本地变量的时候，可以获取到spring的bean
 * （2）在runner初始化的时候，使用了函数映射功能：addFunctionOfServiceMethod
 * （3）在runner初始化的时候，使用了代码映射功能：addMacro
 * @date 2021/10/13 下午4:53
 */
@Service
public class QlExpressService {

    @Autowired
    private ApplicationContext applicationContext;

    private static final ExpressRunner runner;
    static {
        runner = new ExpressRunner();
    }
    private static boolean isInitialRunner = false;



    /**
     *
     * @param statement
     *            执行语句
     * @param context
     *            上下文
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object execute(String statement, Map<String, Object> context)
            throws Exception {
        initRunner(runner);
        IExpressContext expressContext = new QLExpressContext(context,
                applicationContext);
        return runner.execute(statement, expressContext, null, true, true);
    }

    private void initRunner(ExpressRunner runner) {
        if (isInitialRunner) {
            return;
        }
        synchronized (runner) {
            if (isInitialRunner) {
                return;
            }
            try {
                //函数映射
                runner.addFunctionOfServiceMethod("mid_price",applicationContext.getBean("marketDataServiceImpl"), "midPrice", new Class[] {String.class}, null);
                //代码映射
//                runner.addMacro("判定用户是否vip","userDO.salary>200000");

            } catch (Exception e) {
                throw new RuntimeException("初始化失败表达式", e);
            }
        }
        isInitialRunner = true;
    }
}
