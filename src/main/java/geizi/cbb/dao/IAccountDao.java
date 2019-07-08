package geizi.cbb.dao;

import geizi.cbb.domain.Account;

import java.util.List;

public interface IAccountDao {
    /**
     * 通过名称查询
     * @param accountName 用户名称
     * @return 查询到的用户信息
     */
    Account findAccountByName(String accountName);

    List<Account> findAll();

    void updateAccount(Account account);
}
