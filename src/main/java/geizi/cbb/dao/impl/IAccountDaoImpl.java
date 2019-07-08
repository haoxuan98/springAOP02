package geizi.cbb.dao.impl;

import geizi.cbb.dao.IAccountDao;
import geizi.cbb.domain.Account;
import geizi.cbb.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.util.List;

@Controller("iAccountDao")
public class IAccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner queryRunner;

    @Autowired
    private ConnectionUtils connectionUtils;


    public Account findAccountByName(String accountName) {
        try{
            //因为放弃掉了自动注入数据源，所以这里使用的query方法第一个参数为执行操作的连接
            List<Account> accounts = queryRunner.query(connectionUtils.getThreadConnection(), "select * from user_table where accountName=?",
                    new BeanListHandler<Account>(Account.class), accountName);
            if (accounts == null || accounts.size() ==0) {
                return null;
            }
            if (accounts.size() > 1) {
                throw new RuntimeException("结果不唯一，数据有问题");
            }
            return accounts.get(0);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Account> findAll() {
        try {
            return queryRunner.query(connectionUtils.getThreadConnection(),"select * from user_table", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            queryRunner.update(connectionUtils.getThreadConnection(),"update user_table set accountName=?, money=? where id = ?",
                    account.getAccountName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
