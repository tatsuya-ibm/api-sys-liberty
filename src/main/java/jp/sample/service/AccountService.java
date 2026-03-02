package jp.sample.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.sample.dao.AccountDao;
import jp.sample.dao.TransactionHistoryDao;
import jp.sample.dto.AccountResponse;
import jp.sample.dto.TransactionHistoryDto;
import jp.sample.entity.Account;
import jp.sample.entity.TransactionHistory;
import jp.sample.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * 口座サービス
 */
@ApplicationScoped
@Transactional
public class AccountService {
    
    private static final Logger logger = Logger.getLogger(AccountService.class.getName());
    
    @Inject
    private AccountDao accountDao;
    
    @Inject
    private TransactionHistoryDao historyDao;
    
    /**
     * 口座情報を取得
     * 
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @return 口座情報
     * @throws BusinessException 口座が見つからない場合
     */
    public AccountResponse getAccount(String branchCode, String accountNumber) {
        logger.info("口座情報取得: branchCode=" + branchCode + ", accountNumber=" + accountNumber);
        
        Account account = accountDao.findByBranchAndAccount(branchCode, accountNumber)
            .orElseThrow(() -> new BusinessException("0001", "指定された口座が見つかりません"));
        
        // 入出金履歴を取得してDTOに変換
        List<TransactionHistory> histories = historyDao.findByBranchAndAccount(branchCode, accountNumber);
        List<TransactionHistoryDto> historyDtos = histories.stream()
            .map(h -> {
                // 入金はプラス、出金はマイナスに変換
                BigDecimal signedAmount = "DEPOSIT".equals(h.getTransactionType())
                    ? h.getAmount()
                    : h.getAmount().negate();
                String comment = "DEPOSIT".equals(h.getTransactionType()) ? "入金" : "出金";
                String transactionDate = h.getTransactionAt().toLocalDate().toString();
                return new TransactionHistoryDto(transactionDate, signedAmount, comment);
            })
            .collect(Collectors.toList());
        
        return new AccountResponse(
            account.getBranchCode(),
            account.getAccountNumber(),
            account.getAccountName(),
            account.getBalance(),
            historyDtos
        );
    }
    
    /**
     * 入金処理
     * 
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @param amount 入金額
     * @throws BusinessException 口座が見つからない、または入金額が不正な場合
     */
    public void deposit(String branchCode, String accountNumber, BigDecimal amount) {
        logger.info("入金処理: branchCode=" + branchCode + ", accountNumber=" + accountNumber + ", amount=" + amount);
        
        // 入金額のバリデーション
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("0002", "入金額は正の数である必要があります");
        }
        
        // 口座の存在確認
        Account account = accountDao.findByBranchAndAccount(branchCode, accountNumber)
            .orElseThrow(() -> new BusinessException("0001", "指定された口座が見つかりません"));
        
        // 残高更新
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountDao.update(account);
        
        // 履歴記録
        TransactionHistory history = new TransactionHistory(
            branchCode,
            accountNumber,
            "DEPOSIT",
            amount,
            newBalance
        );
        historyDao.create(history);
        
        logger.info("入金完了: 新残高=" + newBalance);
    }
    
    /**
     * 出金処理
     * 
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @param amount 出金額
     * @throws BusinessException 口座が見つからない、出金額が不正、または残高不足の場合
     */
    public void withdraw(String branchCode, String accountNumber, BigDecimal amount) {
        logger.info("出金処理: branchCode=" + branchCode + ", accountNumber=" + accountNumber + ", amount=" + amount);
        
        // 出金額のバリデーション
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("0006", "出金額は正の数である必要があります");
        }
        
        // 口座の存在確認
        Account account = accountDao.findByBranchAndAccount(branchCode, accountNumber)
            .orElseThrow(() -> new BusinessException("0001", "指定された口座が見つかりません"));
        
        // 残高不足チェック
        if (account.getBalance().compareTo(amount) < 0) {
            String message = String.format(
                "残高不足です。現在残高: %.2f円、出金額: %.2f円",
                account.getBalance(),
                amount
            );
            throw new BusinessException("0003", message);
        }
        
        // 残高更新
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountDao.update(account);
        
        // 履歴記録
        TransactionHistory history = new TransactionHistory(
            branchCode,
            accountNumber,
            "WITHDRAW",
            amount,
            newBalance
        );
        historyDao.create(history);
        
        logger.info("出金完了: 新残高=" + newBalance);
    }
}

// Made with Bob
