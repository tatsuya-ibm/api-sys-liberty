package jp.sample.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jp.sample.entity.TransactionHistory;

import java.util.List;

/**
 * 入出金履歴データアクセスオブジェクト
 */
@ApplicationScoped
public class TransactionHistoryDao {
    
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;
    
    /**
     * 入出金履歴を作成
     *
     * @param history 作成する入出金履歴
     */
    public void create(TransactionHistory history) {
        em.persist(history);
    }
    
    /**
     * 口座の入出金履歴を取得（取引日時の降順）
     *
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @return 入出金履歴リスト
     */
    public List<TransactionHistory> findByBranchAndAccount(String branchCode, String accountNumber) {
        return em.createQuery(
                "SELECT t FROM TransactionHistory t " +
                "WHERE t.branchCode = :branchCode AND t.accountNumber = :accountNumber " +
                "ORDER BY t.transactionAt DESC",
                TransactionHistory.class)
            .setParameter("branchCode", branchCode)
            .setParameter("accountNumber", accountNumber)
            .getResultList();
    }
}

// Made with Bob
