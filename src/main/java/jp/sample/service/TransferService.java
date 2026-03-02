package jp.sample.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.sample.dao.TransferTransactionDao;
import jp.sample.entity.TransferTransaction;
import jp.sample.exception.BusinessException;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * 振込サービス
 */
@ApplicationScoped
@Transactional
public class TransferService {
    
    private static final Logger logger = Logger.getLogger(TransferService.class.getName());
    
    @Inject
    private TransferTransactionDao transferDao;
    
    /**
     * 振込トランザクションを作成
     * 
     * @param destBankCode 宛先銀行コード
     * @param destBranchCode 宛先支店番号
     * @param destAccountNumber 宛先口座番号
     * @param amount 振込金額
     * @throws BusinessException 振込金額が不正な場合
     */
    public void createTransfer(String destBankCode, String destBranchCode, 
                              String destAccountNumber, BigDecimal amount) {
        logger.info("振込トランザクション作成: destBankCode=" + destBankCode + 
                   ", destBranchCode=" + destBranchCode + 
                   ", destAccountNumber=" + destAccountNumber + 
                   ", amount=" + amount);
        
        // 振込金額のバリデーション
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("0004", "振込金額は正の数である必要があります");
        }
        
        // 振込トランザクション作成
        TransferTransaction transaction = new TransferTransaction(
            destBankCode,
            destBranchCode,
            destAccountNumber,
            amount
        );
        transferDao.create(transaction);
        
        logger.info("振込トランザクション作成完了: id=" + transaction.getId());
    }
}

// Made with Bob
