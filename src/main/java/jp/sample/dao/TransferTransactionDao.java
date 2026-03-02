package jp.sample.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jp.sample.entity.TransferTransaction;

/**
 * 振込トランザクションデータアクセスオブジェクト
 */
@ApplicationScoped
public class TransferTransactionDao {
    
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;
    
    /**
     * 振込トランザクションを作成
     * 
     * @param transaction 作成する振込トランザクション
     */
    public void create(TransferTransaction transaction) {
        em.persist(transaction);
    }
}

// Made with Bob
