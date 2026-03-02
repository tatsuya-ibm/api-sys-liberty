package jp.sample.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jp.sample.entity.Account;
import jp.sample.entity.AccountId;

import java.util.Optional;

/**
 * 口座データアクセスオブジェクト
 */
@ApplicationScoped
public class AccountDao {
    
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;
    
    /**
     * 支店番号と口座番号で口座を検索
     * 
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @return 口座情報（存在しない場合はOptional.empty()）
     */
    public Optional<Account> findByBranchAndAccount(String branchCode, String accountNumber) {
        AccountId id = new AccountId(branchCode, accountNumber);
        Account account = em.find(Account.class, id);
        return Optional.ofNullable(account);
    }
    
    /**
     * 口座情報を更新
     * 
     * @param account 更新する口座情報
     */
    public void update(Account account) {
        em.merge(account);
    }
    
    /**
     * 口座情報を作成（テスト用）
     * 
     * @param account 作成する口座情報
     */
    public void create(Account account) {
        em.persist(account);
    }
}

// Made with Bob
