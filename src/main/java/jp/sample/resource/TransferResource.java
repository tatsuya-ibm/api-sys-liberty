package jp.sample.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.sample.dto.ErrorResponse;
import jp.sample.dto.StatusResponse;
import jp.sample.dto.TransferRequest;
import jp.sample.exception.BusinessException;
import jp.sample.service.TransferService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 振込APIリソース
 */
@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TransferResource {
    
    private static final Logger logger = Logger.getLogger(TransferResource.class.getName());
    
    @Inject
    private TransferService transferService;
    
    /**
     * 振込トランザクション作成API
     * 
     * @param request 振込リクエスト
     * @return ステータスレスポンス
     */
    @POST
    public Response createTransfer(TransferRequest request) {
        try {
            logger.info("POST /transfers - " + request);
            
            // パラメータチェック
            if (request == null || request.getDestBankCode() == null || 
                request.getDestBranchCode() == null || request.getDestAccountNumber() == null ||
                request.getAmount() == null) {
                ErrorResponse error = new ErrorResponse("0005", "必須パラメータが不足しています");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            
            transferService.createTransfer(
                request.getDestBankCode(),
                request.getDestBranchCode(),
                request.getDestAccountNumber(),
                request.getAmount()
            );
            
            StatusResponse response = new StatusResponse(0);
            return Response.ok(response).build();
            
        } catch (BusinessException e) {
            logger.warning("Business error: " + e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getErrorCode(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "System error", e);
            ErrorResponse error = new ErrorResponse("9999", "システムエラーが発生しました");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}

// Made with Bob
