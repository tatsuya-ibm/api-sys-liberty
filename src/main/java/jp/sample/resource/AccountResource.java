package jp.sample.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.sample.dto.AccountResponse;
import jp.sample.dto.DepositRequest;
import jp.sample.dto.ErrorResponse;
import jp.sample.dto.StatusResponse;
import jp.sample.dto.WithdrawRequest;
import jp.sample.exception.BusinessException;
import jp.sample.service.AccountService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 口座APIリソース
 */
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AccountResource {
    
    private static final Logger logger = Logger.getLogger(AccountResource.class.getName());
    
    @Inject
    private AccountService accountService;
    
    /**
     * 口座情報取得API
     * 
     * @param branchCode 支店番号
     * @param accountNumber 口座番号
     * @return 口座情報
     */
    @GET
    public Response getAccount(@QueryParam("branchCode") String branchCode,
                              @QueryParam("accountNumber") String accountNumber) {
        try {
            logger.info("GET /accounts - branchCode=" + branchCode + ", accountNumber=" + accountNumber);
            
            // パラメータチェック
            if (branchCode == null || branchCode.isEmpty() || 
                accountNumber == null || accountNumber.isEmpty()) {
                ErrorResponse error = new ErrorResponse("0005", "必須パラメータが不足しています");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            
            AccountResponse response = accountService.getAccount(branchCode, accountNumber);
            return Response.ok(response).build();
            
        } catch (BusinessException e) {
            logger.warning("Business error: " + e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getErrorCode(), e.getMessage());
            
            if ("0001".equals(e.getErrorCode())) {
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "System error", e);
            ErrorResponse error = new ErrorResponse("9999", "システムエラーが発生しました");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    
    /**
     * 入金API
     * 
     * @param request 入金リクエスト
     * @return ステータスレスポンス
     */
    @POST
    @Path("/deposit")
    public Response deposit(DepositRequest request) {
        try {
            logger.info("POST /accounts/deposit - " + request);
            
            // パラメータチェック
            if (request == null || request.getBranchCode() == null || 
                request.getAccountNumber() == null || request.getAmount() == null) {
                ErrorResponse error = new ErrorResponse("0005", "必須パラメータが不足しています");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            
            accountService.deposit(request.getBranchCode(), request.getAccountNumber(), request.getAmount());
            StatusResponse response = new StatusResponse(0);
            return Response.ok(response).build();
            
        } catch (BusinessException e) {
            logger.warning("Business error: " + e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getErrorCode(), e.getMessage());
            
            if ("0001".equals(e.getErrorCode())) {
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "System error", e);
            ErrorResponse error = new ErrorResponse("9999", "システムエラーが発生しました");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    
    /**
     * 出金API
     * 
     * @param request 出金リクエスト
     * @return ステータスレスポンス
     */
    @POST
    @Path("/withdraw")
    public Response withdraw(WithdrawRequest request) {
        try {
            logger.info("POST /accounts/withdraw - " + request);
            
            // パラメータチェック
            if (request == null || request.getBranchCode() == null || 
                request.getAccountNumber() == null || request.getAmount() == null) {
                ErrorResponse error = new ErrorResponse("0005", "必須パラメータが不足しています");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            
            accountService.withdraw(request.getBranchCode(), request.getAccountNumber(), request.getAmount());
            StatusResponse response = new StatusResponse(0);
            return Response.ok(response).build();
            
        } catch (BusinessException e) {
            logger.warning("Business error: " + e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getErrorCode(), e.getMessage());
            
            if ("0001".equals(e.getErrorCode())) {
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "System error", e);
            ErrorResponse error = new ErrorResponse("9999", "システムエラーが発生しました");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}

// Made with Bob
