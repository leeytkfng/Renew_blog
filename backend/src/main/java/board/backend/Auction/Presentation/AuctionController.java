package board.backend.Auction.Presentation;

import board.backend.Auction.Application.Command.CreateAuctionCommand;
import board.backend.Auction.Application.Command.PlaceBidCommand;
import board.backend.Auction.Application.Command.handler.CreateAuctionCommandHandler;
import board.backend.Auction.Application.Command.handler.PlaceBidCommandHandler;
import board.backend.Auction.Application.Dto.*;
import board.backend.Auction.Application.Query.GetAuctionListQuery;
import board.backend.Auction.Application.Query.GetAuctionQuery;
import board.backend.Auction.Application.Query.handler.GetAuctionListQueryHandler;
import board.backend.Auction.Application.Query.handler.GetAuctionQueryHandler;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
@Slf4j
public class AuctionController {

    private final CreateAuctionCommandHandler createAuctionCommandHandler;
    private final PlaceBidCommandHandler placeBidCommandHandler;
    private final GetAuctionQueryHandler getAuctionQueryHandler;
    private final GetAuctionListQueryHandler getAuctionListQueryHandler;

    /**
     * 경매 등록
     * Post /api/auctions
     */
    @PostMapping
    public ResponseEntity<CreateAuctionResponse> createAuction(
            @RequestBody CreateAuctionRequest request) {
        log.info("┌─ [API Request] POST /api/auctions");

        try {
            //현재 인증된 사용자 ID 가져오기
            UserId sellerId = getCurrentUserId();

            // Request -> command 변환
            CreateAuctionCommand  command = new CreateAuctionCommand(
                    request.getAddress(),
                    request.getPropertyType(),
                    request.getArea(),
                    request.getDescription(),
                    request.getImageUrls(),
                    request.getStartPrice(),
                    request.getEndTime()
            );

            // Command Handler 실행
            CreateAuctionResponse response = createAuctionCommandHandler.handle(command, sellerId);

            log.info("└─ [API Response] POST /api/auctions - Success: auctionId={}", response.getAuctionId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("└─ [API Response] POST /api/auctions - Validation Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auctions - Error: {}", e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 경매 목록 조회
     * GET /api/auctions?status=ACTIVE
     * @param status 경매 상태
     * @return
     */
    @GetMapping
    public ResponseEntity<AuctionListResponse> getAuctionList(
            @RequestParam(required = false) String status) {
        log.info("┌─ [API Request] GET /api/auctions - status={}", status);

        try {
            GetAuctionListQuery query = new GetAuctionListQuery(status);
            AuctionListResponse response = getAuctionListQueryHandler.handle(query);
            log.info("└─ [API Response] GET /api/auctions - Success: count={}", response.getTotalCount());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("└─ [API Response] GET /api/auctions - Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 경매 상세 조회
     * @param auctionId 경매 Id
     * @param
     * @return
     */
    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDetailResponse> getAuction(
            @PathVariable String auctionId
    ) {
        log.info("┌─ [API Request] GET /api/auctions/{}", auctionId);

        try {
            GetAuctionQuery query = new GetAuctionQuery(auctionId);
            AuctionDetailResponse response = getAuctionQueryHandler.handle(query);
            log.info("└─ [API Response] GET /api/auctions/{} - Success", auctionId);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("└─ [API Response] GET /api/auctions/{} - Not Found", auctionId);
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] GET /api/auctions/{} - Error: {}", auctionId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 입찰하기
     * Post /api/auctions/{auctionId}/bids;

     */
    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<PlaceBidResponse> placeBid(
            @PathVariable String auctionId,
            @RequestBody PlaceBIdRequest request
            ) {
        log.info("┌─ [API Request] POST /api/auctions/{}/bids - amount={}",
                auctionId, request.getBidAmount());

        try {
            // 현재 인증된 사용자 Id가져오기
            UserId userId = getCurrentUserId();

            // Request -> Command 변환
            PlaceBidCommand command = new PlaceBidCommand(
                    auctionId,
                    request.getBidAmount()
            );

            //Command Handler 실행
            PlaceBidResponse response = placeBidCommandHandler.handle(command, userId);

            log.info("└─ [API Response] POST /api/auctions/{}/bids - Success: currentPrice={}",
                    auctionId, response.getCurrentPrice());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("└─ [API Response] POST /api/auctions/{}/bids - Error: {}", auctionId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("└─ [API Response] POST /api/auctions/{}/bids - Error: {}", auctionId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 현재 사용중인 사용자 id;
     * @return
     */
    private UserId getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }

        String userId = authentication.getName();
        return UserId.of(userId);
    }
}
