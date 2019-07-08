package geizi.cbb.service.impl;

import geizi.cbb.dao.IAccountDao;
import geizi.cbb.domain.Account;
import geizi.cbb.service.IAccountService;
import geizi.cbb.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户的业务层实现类
 *
 * 事务控制应该都在业务层
 */
@Service
public class IAccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private TransactionManager transactionManager;

    /**
     * 需要使用ThreadLocal对象吧Connection和当前线程绑定，从而使得一个线程中只有一个控制事务的对象
     * 下述代码就将整个转账的流程都放置在了一个事务中，只有在完成转账的整个操作之后才会将事务进行提交
     * 否则会进行事务的回滚
     * @param sourceName 转出账户名称
     * @param targetName 转入账户名称
     * @param money 转账金额
     */
    public void transfer(String sourceName, String targetName, Float money) {
        try {
            //1.开启事务
            transactionManager.beginTransaction();
            //2.执行操作

            //2.1.根据转出账户名称查询
            Account source = accountDao.findAccountByName(sourceName);
            //2.2.根据转入账户名称查询
            Account target = accountDao.findAccountByName(targetName);
            //2.3.转出账户减钱
            source.setMoney(source.getMoney() - money);
            //2.4.转入账户加钱
            target.setMoney(target.getMoney() + money);
            //2.5.更新转出账户
            accountDao.updateAccount(source);
            //2.6.更新转入账户
            accountDao.updateAccount(target);

            //3.提交事务
            transactionManager.commit();
            //4.返回结果
        } catch (Exception e) {
            //5.回滚操作
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //6.释放连接
            transactionManager.release();
        }


    }
}
