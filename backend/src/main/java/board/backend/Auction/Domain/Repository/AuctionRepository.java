package board.backend.Auction.Domain.Repository;


import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Enum.AuctionStatus;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auth.Domain.Model.Vo.UserId;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/*
  Auction Aggregate Repository
  Domain Layer - 인터페이스 정의
 */
public interface AuctionRepository {

    // == create / Update ======

    /**
     * 경매 저장 (생성 또는 엄데이트 )
     * @param auction
     * @return
     */
    Auction save(Auction auction);

    /**
     * ID로 경매 조회
     * @param id
     * @return
     */
    Optional<Auction> findById(AuctionId id);

    /**
     * 모든 경매 조회
     * @return
     */
    List<Auction> findAll();

    /**
     * 진행중인 경매만 조회
     * @return
     */
    List<Auction> findAllActive();

    /**
     * 상태별 경매 조회
     * @param status
     * @return
     */
    List<Auction> findByStatus(AuctionStatus status);

    /**
     * 판매자 ID로 경먀조회
     * @param userId
     * @return
     */
    List<Auction> findBySellerId(UserId userId);

    /**
     * 사용자가 입찰한 경매 조회
     */
    List<Auction> findByBidderId(UserId userId);

    // ========== Delete ==========

    /**
     * 경매 삭제
     * @param id 삭제할 경매 ID
     */
    void delete(AuctionId id);

    // ========== Exists ==========

    /**
     * 경매 존재 여부 확인
     * @param id 경매 ID
     * @return 존재 여부
     */
    boolean existsById(AuctionId id);
}
