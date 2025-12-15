package board.backend.Auction.Infrastructure.Repository;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Infrastructure.Entity.AuctionMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDb Repository
 * 기본 CRUD + 커스텀 쿼리
 */
@Repository
public interface AuctionMongoRepository extends MongoRepository<AuctionMongoEntity,String> {

    /**
     * 상태로 경매 조회
     * @param status 경매 상태
     * @return 상태로 경매들을 필터링
     */
    List<AuctionMongoEntity> findByStatus(String status);

    /**
     * 판매자 ID로 조회
     * @param sellerId 판매 ID 조회
     * @return 판매자
     */
    List<AuctionMongoEntity> findBySellerId(String sellerId);

    /**
     * 입찰자 ID로 경매 조회
     * Bids 배열 안에 userId가 있는 경매 찾기
     * @param userId 유저 아이디
     * @return
     */
    @Query("{'bids.user_id' : ?0}")
    List<AuctionMongoEntity> findByBidderUserId(String userId);
}
